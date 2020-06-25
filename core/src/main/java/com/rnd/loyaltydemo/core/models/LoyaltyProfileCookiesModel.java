package com.rnd.loyaltydemo.core.models;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rnd.loyaltydemo.core.config.ContextAwareConfig;
import com.rnd.loyaltydemo.core.constants.CookieConstants;
import com.rnd.loyaltydemo.core.utils.ContextAwareConfigUtilService;
import com.rnd.loyaltydemo.core.utils.CookiesUtil;

/**
 * The Class LoyaltyProfileCookiesModel.
 *
 * @author pannem
 *
 */
@Model(adaptables = SlingHttpServletRequest.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class LoyaltyProfileCookiesModel {

	private static final Logger log = LoggerFactory.getLogger(LoyaltyProfileCookiesModel.class);

	@Inject
	private ContextAwareConfigUtilService caConfigUtilService;

	@Self
	private SlingHttpServletRequest request;

	private String cookiePrefix;
		
	private boolean loggedIn = Boolean.FALSE;

	/**
	 * Init LoyaltyProfileCookiesModel
	 * 
	 */
	@PostConstruct
	protected void init() {
		log.info("LoyaltyProfileCookiesModel :: init :: start");
		try {
			ContextAwareConfig contextAwareConfig = caConfigUtilService.getContextAwareConfig(request);
			cookiePrefix = contextAwareConfig.cookiePrefix();
			if (StringUtils.isNotBlank(CookiesUtil.getCookie(request, cookiePrefix, CookieConstants.ACCESS_TOKEN_COOKIE_NAME)) && 
					StringUtils.isNotBlank(CookiesUtil.getCookie(request, cookiePrefix, CookieConstants.PROFILE_ID_COOKIE_NAME))) {
				loggedIn = Boolean.TRUE;
			}
		} catch (final Exception e) {
			log.error("LoyaltyProfileCookiesModel : error  :{} ", e);
		}
		log.info("LoyaltyProfileCookiesModel :: init :: End");
	}
	
	public boolean isLoggedIn() {
		return loggedIn;
	}
	
	public String getUsername() {
		return CookiesUtil.getCookie(request, cookiePrefix, CookieConstants.USER_NAME_COOKIE_NAME);
	}
	
	public String getFirstName() {
		return CookiesUtil.getCookie(request, cookiePrefix, CookieConstants.USER_FIRST_NAME_COOKIE_NAME);
	}
	
	public String getLastName() {
		return CookiesUtil.getCookie(request, cookiePrefix, CookieConstants.USER_LAST_NAME_COOKIE_NAME);
	}
	
	public String getPrimaryEmail() {
		return CookiesUtil.getCookie(request, cookiePrefix, CookieConstants.USER_EMAIL_COOKIE_NAME);
	}
	
	public String getPrimaryPhone() {
		return CookiesUtil.getCookie(request, cookiePrefix, CookieConstants.USER_PHONE_COOKIE_NAME);
	}
	
	public String getUserTier() {
		return CookiesUtil.getCookie(request, cookiePrefix, CookieConstants.USER_TIER_COOKIE_NAME);
	}
	
	public String getPoints() {
		return CookiesUtil.getCookie(request, cookiePrefix, CookieConstants.USER_POINTS_COOKIE_NAME);
	}
	
	public String getPointsTypeDesc() {
		return CookiesUtil.getCookie(request, cookiePrefix, CookieConstants.USER_POINTS_TYPE_DESC_COOKIE_NAME);
	}
	
	public String getNextTier() {
		return CookiesUtil.getCookie(request, cookiePrefix, CookieConstants.USER_NEXTTIER_COOKIE_NAME);
	}
	
	public String getPointsToNextTier() {
		return CookiesUtil.getCookie(request, cookiePrefix, CookieConstants.USER_POINTSTONEXTTIER_COOKIE_NAME);
	}
	
	public String getInLinkedPool() {
		return CookiesUtil.getCookie(request, cookiePrefix, CookieConstants.USER_LINKEDPOOL_COOKIE_NAME);
	}
	
	public String getLoyaltyCookie() {
		return CookiesUtil.getCookie(request, cookiePrefix, CookieConstants.USER_FIRST_NAME_COOKIE_NAME);
	}	
}
