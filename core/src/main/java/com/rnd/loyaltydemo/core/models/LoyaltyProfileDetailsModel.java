package com.rnd.loyaltydemo.core.models;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.acs.commons.util.CookieUtil;
import com.day.cq.wcm.api.Page;
import com.rnd.loyaltydemo.core.bo.AccessTokenResponse;
import com.rnd.loyaltydemo.core.bo.Address;
import com.rnd.loyaltydemo.core.bo.Email;
import com.rnd.loyaltydemo.core.bo.LoyaltyProfile;
import com.rnd.loyaltydemo.core.bo.Phone;
import com.rnd.loyaltydemo.core.bo.PointBalanceResponse;
import com.rnd.loyaltydemo.core.bo.ProgramProfileTier;
import com.rnd.loyaltydemo.core.config.ContextAwareConfig;
import com.rnd.loyaltydemo.core.constants.CookieConstants;
import com.rnd.loyaltydemo.core.service.LoyaltyService;
import com.rnd.loyaltydemo.core.utils.CommonUtil;
import com.rnd.loyaltydemo.core.utils.ContextAwareConfigUtilService;
import com.rnd.loyaltydemo.core.utils.CookiesUtil;
import com.rnd.loyaltydemo.core.utils.LocalizationUtil;

/**
 * The Class LoyaltyProfileModel.
 *
 * @author kshamitha
 *
 */
/**
 * @author kshamitha
 *
 */
@Model(adaptables = SlingHttpServletRequest.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class LoyaltyProfileDetailsModel {

	private static final Logger log = LoggerFactory.getLogger(LoyaltyProfileDetailsModel.class);
	@Inject
	private LoyaltyService loyaltyService;

	@Inject
	private ContextAwareConfigUtilService caConfigUtilService;

	LoyaltyProfile loyaltyProfile;

	@Self
	private SlingHttpServletRequest request;

	@SlingObject
	private SlingHttpServletResponse response;

	private int points = 0;
	private String pointsTypeDesc = StringUtils.EMPTY;

	private boolean loggedIn = false;
	private String cookiePrefix;
	String cookiePath;
	
    @ScriptVariable
    private Page currentPage;

	private PointBalanceResponse pointBalance;
	private ProgramProfileTier programProfileTier;

	CompletableFuture<PointBalanceResponse> pointBalanceFuture;
	CompletableFuture<ProgramProfileTier> programProfileTierFuture;

	/**
	 * 
	 * 
	 */
	@PostConstruct
	protected void init() {
		log.info("LoyaltyProfileDetailsModel :: init :: start");
		try {
			ContextAwareConfig contextAwareConfig = caConfigUtilService.getContextAwareConfig(request);
			cookiePrefix = contextAwareConfig.cookiePrefix();
			cookiePath = CookieConstants.DEFAULT_COOKIE_PATH;
			AccessTokenResponse accessTokenResponse = CookiesUtil.getAccessTokenData(request, cookiePrefix);

			if (null != accessTokenResponse && StringUtils.isNotBlank(accessTokenResponse.getProfileId())
					&& StringUtils.isNotBlank(accessTokenResponse.getAccessToken())) {

				// Asynchronous Loyalty Profile API call
				CompletableFuture<LoyaltyProfile> loyaltyProfileFuture = CompletableFuture.supplyAsync(() -> {
					loyaltyProfile = getProfile(accessTokenResponse, contextAwareConfig);
					return loyaltyProfile;
				});

				// Block and get the result of loyaltyProfile future call
				loyaltyProfileFuture.get();

				if (null != loyaltyProfile && loyaltyProfile.getStatusCode() == HttpServletResponse.SC_OK) {

					// Asynchronous Point Balance API call
					pointBalanceFuture = CompletableFuture.supplyAsync(() -> {
						pointBalance = getPointBalance(accessTokenResponse, contextAwareConfig);
						return pointBalance;
					});

					// Asynchronous Profile Tier Details API call
					programProfileTierFuture = CompletableFuture.supplyAsync(() -> {
						programProfileTier = getProfileTierDetailsResponse(accessTokenResponse, contextAwareConfig);
						return programProfileTier;
					});

					processProfileDetails(contextAwareConfig);
					request.setAttribute("pointBalance", pointBalance);
					request.setAttribute("loyaltyProfile", loyaltyProfile);
					request.setAttribute("programProfileTier", programProfileTier);
					request.setAttribute(CookieConstants.LOGGED_IN, Boolean.TRUE);
					loggedIn = Boolean.TRUE;
				}
			} else if (CookieUtil.getCookie(request, cookiePrefix + CookieConstants.ACCESS_TOKEN_COOKIE_NAME) != null) {
				CookiesUtil.deleteCookie(request, response, cookiePrefix, CookieConstants.ACCESS_TOKEN_COOKIE_NAME,
						CookieConstants.DEFAULT_COOKIE_PATH, Boolean.FALSE, Boolean.TRUE);
			}

		} catch (final Exception e) {
			log.error("LoyaltyProfileDetailsModel : error  :{} ", e.getMessage());
		}
		log.info("LoyaltyProfileDetailsModel :: init :: End");
	}

	/**
	 * Get user profile and create the required cookie
	 * 
	 * @param loyaltyProfile
	 * @param contextAwareConfig
	 * 
	 */
	private void processProfileDetails(ContextAwareConfig contextAwareConfig) {
		if (loyaltyProfile != null) {
			try {
				// Block and get the result of pointBalance future call
				pointBalanceFuture.get();
				if (null != pointBalance && pointBalance.getStatusCode() == HttpServletResponse.SC_OK) {
					points = (int) Math.round(pointBalance.getPointsBalance().get(0).getPointAmount());
					pointsTypeDesc = pointBalance.getPointsBalance().get(0).getPointTypeShortDescription();
					if (Boolean.TRUE.equals(loyaltyProfile.getInLinkedPool())) {
						points = (int) Math.round(pointBalance.getPoolingPointsBalance().get(0).getPointAmount());
					}
				}
				// Block and get the result of programProfileTier future call
				programProfileTierFuture.get();
				if (null != programProfileTier && programProfileTier.getStatusCode() == HttpServletResponse.SC_OK
						&& programProfileTier.getIsMaxTier() != null
						&& programProfileTier.getIsMaxTier().equals(Boolean.FALSE)) {
					CommonUtil.getProfileTierDetails(programProfileTier,contextAwareConfig.loyaltyAggregateEntityCode());
				}
				setProfileDataCookies(contextAwareConfig);
			} catch (final Exception e) {
				log.error("LoyaltyProfileModel : createProfileCookie : Exception: {}", e);
			}
		}
	}

	private void setProfileDataCookies(ContextAwareConfig contextAwareConfig){
		request.setAttribute(CookieConstants.COOKIE_PREFIX, cookiePrefix);
		CookiesUtil.createCookie(response, cookiePrefix, CookieConstants.USER_NAME_COOKIE_NAME,
				loyaltyProfile.getUsername(),
				contextAwareConfig.cookieExpiry(), cookiePath, Boolean.FALSE, Boolean.FALSE);
		request.setAttribute(CookieConstants.USER_NAME_COOKIE_NAME, loyaltyProfile.getUsername());
		
		if (StringUtils.isNotBlank(loyaltyProfile.getFirstName())) {
			CookiesUtil.createCookie(response, cookiePrefix, CookieConstants.USER_FIRST_NAME_COOKIE_NAME,
					loyaltyProfile.getFirstName(),
					contextAwareConfig.cookieExpiry(), cookiePath, Boolean.FALSE, Boolean.FALSE);
			request.setAttribute(CookieConstants.USER_FIRST_NAME_COOKIE_NAME, loyaltyProfile.getFirstName());
		}
		
		if (StringUtils.isNotBlank(loyaltyProfile.getLastName())) {
			CookiesUtil.createCookie(response, cookiePrefix, CookieConstants.USER_LAST_NAME_COOKIE_NAME,
					loyaltyProfile.getLastName(),
					contextAwareConfig.cookieExpiry(), cookiePath, Boolean.FALSE, Boolean.FALSE);
			request.setAttribute(CookieConstants.USER_LAST_NAME_COOKIE_NAME, loyaltyProfile.getLastName());
		}
		
		CookiesUtil.createCookie(response, cookiePrefix, CookieConstants.USER_POINTS_COOKIE_NAME,
				Integer.toString(points), contextAwareConfig.cookieExpiry(), cookiePath, Boolean.FALSE,
				Boolean.FALSE);
		request.setAttribute(CookieConstants.USER_POINTS_COOKIE_NAME, Integer.toString(points));
		
		CookiesUtil.createCookie(response, cookiePrefix, CookieConstants.USER_POINTS_TYPE_DESC_COOKIE_NAME,
				pointsTypeDesc, contextAwareConfig.cookieExpiry(), cookiePath, Boolean.FALSE,
				Boolean.FALSE);
		request.setAttribute(CookieConstants.USER_POINTS_TYPE_DESC_COOKIE_NAME, pointsTypeDesc);

		if (StringUtils.isNotBlank(loyaltyProfile.getCardNumber())) {
			CookiesUtil.createCookie(response, cookiePrefix, CookieConstants.USER_CARDNUM_COOKIE_NAME,
					loyaltyProfile.getCardNumber(), contextAwareConfig.cookieExpiry(), cookiePath, Boolean.FALSE,
					Boolean.FALSE);
			request.setAttribute(CookieConstants.USER_CARDNUM_COOKIE_NAME, loyaltyProfile.getCardNumber());
		}
		
		if (StringUtils.isNotBlank(loyaltyProfile.getTierName())) {
			CookiesUtil.createCookie(response, cookiePrefix, CookieConstants.USER_TIER_COOKIE_NAME,
					loyaltyProfile.getTierName(),
					contextAwareConfig.cookieExpiry(), cookiePath, Boolean.FALSE, Boolean.FALSE);
			request.setAttribute(CookieConstants.USER_TIER_COOKIE_NAME, loyaltyProfile.getTierName());
		}
		
		CookiesUtil.createCookie(response, cookiePrefix, CookieConstants.USER_LINKEDPOOL_COOKIE_NAME,
				String.valueOf(loyaltyProfile.getInLinkedPool()), contextAwareConfig.cookieExpiry(), cookiePath,
				Boolean.FALSE, Boolean.FALSE);
		request.setAttribute(CookieConstants.USER_LINKEDPOOL_COOKIE_NAME,
				String.valueOf(loyaltyProfile.getInLinkedPool()));
		
		if (StringUtils.isNotBlank(loyaltyProfile.getGender())) {
			CookiesUtil.createCookie(response, cookiePrefix, CookieConstants.USER_GENDER_COOKIE_NAME,
					loyaltyProfile.getGender(), contextAwareConfig.cookieExpiry(), cookiePath,
					Boolean.FALSE, Boolean.FALSE);
			request.setAttribute(CookieConstants.USER_GENDER_COOKIE_NAME,
					loyaltyProfile.getGender());
		}
		
		if (StringUtils.isNotBlank(loyaltyProfile.getBirthDate())) {
			CookiesUtil.createCookie(response, cookiePrefix, CookieConstants.USER_DOB_COOKIE_NAME,
					loyaltyProfile.getBirthDate(), contextAwareConfig.cookieExpiry(), cookiePath,
					Boolean.FALSE, Boolean.FALSE);
			request.setAttribute(CookieConstants.USER_DOB_COOKIE_NAME,
					loyaltyProfile.getBirthDate());
		}
		
		if (StringUtils.isNotBlank(loyaltyProfile.getJoinDate())) {
			CookiesUtil.createCookie(response, cookiePrefix, CookieConstants.USER_JOIN_DATE_COOKIE_NAME,
					loyaltyProfile.getJoinDate(), contextAwareConfig.cookieExpiry(), cookiePath,
					Boolean.FALSE, Boolean.FALSE);
			request.setAttribute(CookieConstants.USER_JOIN_DATE_COOKIE_NAME,
					loyaltyProfile.getJoinDate());
		}
		
		if (StringUtils.isNotBlank(loyaltyProfile.getGamerAlias())) {
			CookiesUtil.createCookie(response, cookiePrefix, CookieConstants.USER_GAMER_ALIAS_COOKIE_NAME,
					loyaltyProfile.getGamerAlias(), contextAwareConfig.cookieExpiry(), cookiePath,
					Boolean.FALSE, Boolean.FALSE);
			request.setAttribute(CookieConstants.USER_GAMER_ALIAS_COOKIE_NAME,
					loyaltyProfile.getGamerAlias());
		}
		if (loyaltyProfile.getEmails() != null && !loyaltyProfile.getEmails().isEmpty()) {
			setEmailCookies(contextAwareConfig);
		}
		if (loyaltyProfile.getPhones() != null && !loyaltyProfile.getPhones().isEmpty()) {
			setPhoneCookies(contextAwareConfig);
		}
		if (loyaltyProfile.getAddresses() != null && !loyaltyProfile.getAddresses().isEmpty()) {
			setAddressCookies(contextAwareConfig);
		}
		setProfileTierCookies(contextAwareConfig);
	}

	private void setProfileTierCookies(ContextAwareConfig contextAwareConfig) {
		if (programProfileTier != null) {
			if (StringUtils.isNotBlank(programProfileTier.getNextTierName())) {
				CookiesUtil.createCookie(response, cookiePrefix, CookieConstants.USER_NEXTTIER_COOKIE_NAME,
						programProfileTier.getNextTierName(),
						contextAwareConfig.cookieExpiry(), cookiePath, Boolean.FALSE, Boolean.FALSE);
				request.setAttribute(CookieConstants.USER_NEXTTIER_COOKIE_NAME,
						programProfileTier.getNextTierName());
			}
			if (StringUtils.isNotBlank(programProfileTier.getPointsToNextTier())) {
				CookiesUtil.createCookie(response, cookiePrefix, CookieConstants.USER_POINTSTONEXTTIER_COOKIE_NAME,
						programProfileTier.getPointsToNextTier(),
						contextAwareConfig.cookieExpiry(), cookiePath, Boolean.FALSE, Boolean.FALSE);
				request.setAttribute(CookieConstants.USER_POINTSTONEXTTIER_COOKIE_NAME,
						programProfileTier.getPointsToNextTier());
			}
		}
	}

	private void setAddressCookies(ContextAwareConfig contextAwareConfig) {
		List<Address> primaryAddressList = loyaltyProfile.getAddresses().stream()
				.filter(addressListObj -> addressListObj.getIsPreferred().equals(Boolean.TRUE))
				.collect(Collectors.toList());
		if (primaryAddressList != null && !primaryAddressList.isEmpty()) {
			Address primaryAddress = primaryAddressList.get(0);
			CookiesUtil.createCookie(response, cookiePrefix, CookieConstants.USER_ADDRESSID_COOKIE_NAME,
					primaryAddress.getAddressId(),
					contextAwareConfig.cookieExpiry(), cookiePath, Boolean.FALSE, Boolean.FALSE);
			request.setAttribute(CookieConstants.USER_ADDRESSID_COOKIE_NAME,
					primaryAddress.getAddressId());
			if (StringUtils.isNotBlank(primaryAddress.getAddressLine1())) {
				CookiesUtil.createCookie(response, cookiePrefix, CookieConstants.USER_ADDRESSLINE1_COOKIE_NAME,
						primaryAddress.getAddressLine1(),
						contextAwareConfig.cookieExpiry(), cookiePath, Boolean.FALSE, Boolean.FALSE);
				request.setAttribute(CookieConstants.USER_ADDRESSLINE1_COOKIE_NAME,
						primaryAddress.getAddressLine1());
			}
			
			if (StringUtils.isNotBlank(primaryAddress.getAddressLine2())) {
				CookiesUtil.createCookie(response, cookiePrefix, CookieConstants.USER_ADDRESSLINE2_COOKIE_NAME,
						primaryAddress.getAddressLine2(),
						contextAwareConfig.cookieExpiry(), cookiePath, Boolean.FALSE, Boolean.FALSE);
				request.setAttribute(CookieConstants.USER_ADDRESSLINE2_COOKIE_NAME,
						primaryAddress.getAddressLine2());
			}
			
			if (StringUtils.isNotBlank(primaryAddress.getCity())) {
				CookiesUtil.createCookie(response, cookiePrefix, CookieConstants.USER_CITY_COOKIE_NAME,
						primaryAddress.getCity(),
						contextAwareConfig.cookieExpiry(), cookiePath, Boolean.FALSE, Boolean.FALSE);
				request.setAttribute(CookieConstants.USER_CITY_COOKIE_NAME,
						primaryAddress.getCity());
			}
			
			if (StringUtils.isNotBlank(primaryAddress.getPostalCode())) {
				CookiesUtil.createCookie(response, cookiePrefix, CookieConstants.USER_POSTALCODE_COOKIE_NAME,
						primaryAddress.getPostalCode(),
						contextAwareConfig.cookieExpiry(), cookiePath, Boolean.FALSE, Boolean.FALSE);
				request.setAttribute(CookieConstants.USER_POSTALCODE_COOKIE_NAME,
						primaryAddress.getPostalCode());
			}
			
			if (StringUtils.isNotBlank(primaryAddress.getStateCode())) {
				CookiesUtil.createCookie(response, cookiePrefix, CookieConstants.USER_STATECODE_COOKIE_NAME,
						primaryAddress.getStateCode(),
						contextAwareConfig.cookieExpiry(), cookiePath, Boolean.FALSE, Boolean.FALSE);
				request.setAttribute(CookieConstants.USER_STATECODE_COOKIE_NAME,
						primaryAddress.getStateCode());
			}
			
			if (StringUtils.isNotBlank(primaryAddress.getCountryCode())) {
				CookiesUtil.createCookie(response, cookiePrefix, CookieConstants.USER_COUNTRYCODE_COOKIE_NAME,
						primaryAddress.getCountryCode(),
						contextAwareConfig.cookieExpiry(), cookiePath, Boolean.FALSE, Boolean.FALSE);
				request.setAttribute(CookieConstants.USER_COUNTRYCODE_COOKIE_NAME,
						primaryAddress.getCountryCode());
			}
		}
	}

	private void setPhoneCookies(ContextAwareConfig contextAwareConfig) {
		List<Phone> primaryPhoneList = loyaltyProfile.getPhones().stream()
				.filter(phoneListObj -> phoneListObj.getIsPreferred().equals(Boolean.TRUE))
				.collect(Collectors.toList());
		if (primaryPhoneList != null && !primaryPhoneList.isEmpty()) {
			Phone primaryPhone = primaryPhoneList.get(0);
			CookiesUtil.createCookie(response, cookiePrefix, CookieConstants.USER_PHONEID_COOKIE_NAME,
					primaryPhone.getPhoneId(),
					contextAwareConfig.cookieExpiry(), cookiePath, Boolean.FALSE, Boolean.FALSE);
			request.setAttribute(CookieConstants.USER_PHONEID_COOKIE_NAME,
					primaryPhone.getPhoneId());
			if (StringUtils.isNotBlank(primaryPhone.getPhoneCountryCode())) {
				CookiesUtil.createCookie(response, cookiePrefix, CookieConstants.USER_PHONE_COUNTRYCODE_COOKIE_NAME,
						primaryPhone.getPhoneCountryCode(),
						contextAwareConfig.cookieExpiry(), cookiePath, Boolean.FALSE, Boolean.FALSE);
				request.setAttribute(CookieConstants.USER_PHONE_COUNTRYCODE_COOKIE_NAME,
						primaryPhone.getPhoneCountryCode());
			}
			CookiesUtil.createCookie(response, cookiePrefix, CookieConstants.USER_PHONE_COOKIE_NAME,
					primaryPhone.getPhoneNumber(),
					contextAwareConfig.cookieExpiry(), cookiePath, Boolean.FALSE, Boolean.FALSE);
			request.setAttribute(CookieConstants.USER_PHONE_COOKIE_NAME,
					primaryPhone.getPhoneNumber());
		}
	}

	private void setEmailCookies(ContextAwareConfig contextAwareConfig) {
		List<Email> primaryEmailList = loyaltyProfile.getEmails().stream()
				.filter(emailListObj -> emailListObj.getIsPreferred().equals(Boolean.TRUE))
				.collect(Collectors.toList());
		if (primaryEmailList != null && !primaryEmailList.isEmpty()) {
			Email primaryEmail = primaryEmailList.get(0);
			CookiesUtil.createCookie(response, cookiePrefix, CookieConstants.USER_EMAILID_COOKIE_NAME,
					primaryEmail.getEmailId(),
					contextAwareConfig.cookieExpiry(), cookiePath, Boolean.FALSE, Boolean.FALSE);
			request.setAttribute(CookieConstants.USER_EMAILID_COOKIE_NAME,
					primaryEmail.getEmailId());
			CookiesUtil.createCookie(response, cookiePrefix, CookieConstants.USER_EMAIL_COOKIE_NAME,
					primaryEmail.getEmailAddress(),
					contextAwareConfig.cookieExpiry(), cookiePath, Boolean.FALSE, Boolean.FALSE);
			request.setAttribute(CookieConstants.USER_EMAIL_COOKIE_NAME,
					primaryEmail.getEmailAddress());
		}
	}

	/**
	 * @param accessTokenResponse
	 * @param config
	 * @return PointBalance
	 */
	private PointBalanceResponse getPointBalance(final AccessTokenResponse accessTokenResponse,
			final ContextAwareConfig config) {
		try {
			return loyaltyService.getProfilePointBalance(accessTokenResponse.getProfileId(),
				accessTokenResponse.getAccessToken(), config);
		} catch (Exception e) {
			log.error("LoyaltyProfileDetailsModel : pointBalanceFuture init :{} ", e.getMessage());
		}
		return null;
	}

	/**
	 * @param accessTokenResponse
	 * @param config
	 * @return LoyaltyProfile
	 */
	private LoyaltyProfile getProfile(final AccessTokenResponse accessTokenResponse, final ContextAwareConfig config) {
		try {	
			return loyaltyService.getProfile(accessTokenResponse.getAccessToken(), accessTokenResponse.getProfileId(),
				config);
		} catch (Exception e) {
			log.error("LoyaltyProfileDetailsModel : loyaltyProfileFuture init :{} ", e.getMessage());
		}
		return null;
	}

	/**
	 * To fetch all the profile tier related details
	 * 
	 * @param accessTokenResponse
	 * @param config
	 * @return ProgramProfileTier
	 */
	private ProgramProfileTier getProfileTierDetailsResponse(final AccessTokenResponse accessTokenResponse,
			final ContextAwareConfig config) {
		try {
			return loyaltyService.getProfileTierProgress(accessTokenResponse.getAccessToken(),
				accessTokenResponse.getProfileId(), config);
		} catch (Exception e) {
			log.error("LoyaltyProfileDetailsModel : programProfileTierFuture init :{} ", e.getMessage());
		}
		return null;
	}

	public LoyaltyProfile getLoyaltyProfile() {
		return loyaltyProfile;
	}

	public int getLoyaltyPoints() {
		return points;
	}
	
	public String getLoyaltyPointsTypeDesc() {
		return pointsTypeDesc;
	}

	public boolean isLoggedIn() {
		return loggedIn;
	}

	public String getCookiePrefix() {
		return cookiePrefix;
	}
	
	public String getLocaleCode() {
		return LocalizationUtil.getLocale(currentPage).toString().replace("_", "-");
	}

}
