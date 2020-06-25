package com.rnd.loyaltydemo.core.service.impl;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.rnd.loyaltydemo.core.bo.AccessTokenResponse;
import com.rnd.loyaltydemo.core.bo.ActivityByIdResponse;
import com.rnd.loyaltydemo.core.bo.Country;
import com.rnd.loyaltydemo.core.bo.CountriesResponse;
import com.rnd.loyaltydemo.core.bo.Gender;
import com.rnd.loyaltydemo.core.bo.GenderResponse;
import com.rnd.loyaltydemo.core.bo.GenericResponse;
import com.rnd.loyaltydemo.core.bo.LoyaltyProfile;
import com.rnd.loyaltydemo.core.bo.LoyaltyValidationResponse;
import com.rnd.loyaltydemo.core.bo.PointBalanceResponse;
import com.rnd.loyaltydemo.core.bo.PointsActivtyDetails;
import com.rnd.loyaltydemo.core.bo.PointsSummaryResponse;
import com.rnd.loyaltydemo.core.bo.ProgramProfileTier;
import com.rnd.loyaltydemo.core.bo.ReferralCode;
import com.rnd.loyaltydemo.core.bo.RestServiceResponse;
import com.rnd.loyaltydemo.core.bo.State;
import com.rnd.loyaltydemo.core.bo.StatesResponse;
import com.rnd.loyaltydemo.core.config.ContextAwareConfig;
import com.rnd.loyaltydemo.core.constants.LoyaltyDemoConstants;
import com.rnd.loyaltydemo.core.service.LoyaltyService;
import com.rnd.loyaltydemo.core.service.RESTServiceFramework;

/**
 * Loyalty Service Implementation
 * 
 */
@Component(service = LoyaltyService.class, name = "Loyalty Demo Service", immediate = true)
public class LoyaltyServiceImpl implements LoyaltyService {

	private static final String LOYALTY_PROFILE_ID = "{profileId}";

	private static final String LOYALTY_TRANSACTION_ID = "{transactionId}";

	private static final String ORDER_ID = "{orderId}";

	private static final String O_AUTH = "OAuth";

	private static final String BASIC = "Basic";

	private static final String CONTENT_TYPE = "Content-Type";

	private static final String ACCEPT_LANGUAGE = "Accept-Language";

	private static final String PROGRAM_CODE = "Program-Code";

	private static final String AUTHORIZATION = "Authorization";

	private static final String GRANT_TYPE = "grant_type";
	
	private static final String STATUS = "{status}";
	
	private static final String COUNTRY = "{countryCode}";
  
	private static final String LOYALTY_USERNAME = "{username}";

	private static final Logger log = LoggerFactory.getLogger(LoyaltyServiceImpl.class);

	@Reference
	RESTServiceFramework restService;

	@Reference
	private ResourceResolverFactory resolverFactory;

	/**
	 * Login method
	 * 
	 * @param username
	 * @param password
	 * @param config
	 * @return AccessTokenResponse
	 */
	@Override
	public AccessTokenResponse login(final String username, final String password, final ContextAwareConfig config) {
		log.debug("Start of Login API call method");
		final Map<String, String> headers = getBasicRequestHeaders(config,LoyaltyDemoConstants.APPLICATION_FORM_ENCODED);
		headers.put(GRANT_TYPE, LoyaltyDemoConstants.LOGIN_GRANT_TYPE);

		final StringBuilder requestBodyString = new StringBuilder();
		requestBodyString.append(GRANT_TYPE).append(LoyaltyDemoConstants.EQUAL)
				.append(LoyaltyDemoConstants.LOGIN_GRANT_TYPE);
		requestBodyString.append(LoyaltyDemoConstants.USERNAME).append(LoyaltyDemoConstants.EQUAL).append(username);
		requestBodyString.append(LoyaltyDemoConstants.PASSWORD).append(LoyaltyDemoConstants.EQUAL).append(password);
		requestBodyString.append(LoyaltyDemoConstants.RESPONSE_TYPE).append(LoyaltyDemoConstants.EQUAL)
				.append(LoyaltyDemoConstants.TOKEN);

		final RestServiceResponse loginResponse = restService.makePostWSCall(
				config.loyaltyDomain() + config.loyaltyTokenEndPointURL(), requestBodyString.toString(),
				headers);

		if (loginResponse != null && StringUtils.isNotBlank(loginResponse.getResponse())) {
			final Gson gson = new Gson();
			AccessTokenResponse accessTokenResponse = gson.fromJson(loginResponse.getResponse(),
					AccessTokenResponse.class);
			accessTokenResponse.setStatusCode(loginResponse.getStatusCode());
			return accessTokenResponse;
		}

		return null;
	}

	/**
	 * Basic authorization request headers
	 * 
	 * @param config
	 * @return Map<String,String>
	 */
	private Map<String, String> getBasicRequestHeaders(final ContextAwareConfig config, String contentType) {
		final String credetialsString = config.loyaltyProgramUserName() + ":" + config.loyaltyProgramPassword();
		final byte[] authBytes = credetialsString.getBytes(StandardCharsets.UTF_8);
		final String encodedCredentials = Base64.getEncoder().encodeToString(authBytes);
		final Map<String, String> headers = new HashMap<>();
		headers.put(AUTHORIZATION, BASIC + StringUtils.SPACE + encodedCredentials);
		headers.put(PROGRAM_CODE, config.loyaltyProgramCode());
		headers.put(ACCEPT_LANGUAGE, LoyaltyDemoConstants.ACCEPT_LANGUAGE);
		if(StringUtils.isNotBlank(contentType)) {
			headers.put(CONTENT_TYPE, contentType);
		}
		return headers;
	}

	/**
	 * O_Auth authorization request headers
	 * 
	 * @param accesToken
	 * @param config
	 * @return Map<String, String>
	 */
	private Map<String, String> getoAuthReuestHeaders(final String accesToken, final ContextAwareConfig config) {
		final Map<String, String> headers = new HashMap<>();
		headers.put(AUTHORIZATION, O_AUTH + StringUtils.SPACE + accesToken);
		headers.put(PROGRAM_CODE, config.loyaltyProgramCode());
		headers.put(ACCEPT_LANGUAGE, LoyaltyDemoConstants.ACCEPT_LANGUAGE);
		return headers;
	}

	/**
	 * authorization request headers with Content-Type
	 * 
	 * @param accesToken
	 * @param config
	 * @return Map<String, String>
	 */
	private Map<String, String> getoAuthReuestHeadersWithContentType(final String accesToken,
			final ContextAwareConfig config) {
		final Map<String, String> headers = getoAuthReuestHeaders(accesToken, config);
		headers.put(CONTENT_TYPE, LoyaltyDemoConstants.APPLICATION_JSON);
		return headers;
	}

	/**
	 * get Referral Code from Loyalty Referral Code API.
	 * 
	 * @param accesToken
	 * @param loyaltyProfileID
	 * @param config
	 * @return referralCode
	 */
	@Override
	public ReferralCode getUserReffralCode(final String loyaltyProfileID, final String accesToken,
			final ContextAwareConfig config) {
		log.debug("Start of getUserReffralCode API call method");
		final RestServiceResponse responseObject = restService.makePostWSCall(
				config.loyaltyDomain()
						+ config.loyaltyUserReffralCodeEndPointURL().replace(LOYALTY_PROFILE_ID, loyaltyProfileID),
				getoAuthReuestHeadersWithContentType(accesToken, config));
		if (responseObject != null && StringUtils.isNotBlank(responseObject.getResponse())) {
			final Gson gson = new Gson();
			ReferralCode referralCode = gson.fromJson(responseObject.getResponse(), ReferralCode.class);
			referralCode.setStatusCode(responseObject.getStatusCode());
			return referralCode;
		}
		return null;

	}

	/**
	 * Profile Point Balance
	 * 
	 * @param loyaltyProfileID
	 * @param accesToken
	 * @param config
	 * @return PointBalance
	 */
	@Override
	public PointBalanceResponse getProfilePointBalance(final String loyaltyProfileID, final String accesToken,
			final ContextAwareConfig config) {
		log.debug("Start of getProfilePointBalance API call method");
		RestServiceResponse responseModel = restService.makeGetWSCall(
				config.loyaltyDomain()
						+ config.loyaltyPointBalanceEndPointURL().replace(LOYALTY_PROFILE_ID, loyaltyProfileID),
				getoAuthReuestHeaders(accesToken, config));
		if (responseModel != null && StringUtils.isNotBlank(responseModel.getResponse())) {
			final Gson gson = new Gson();
			PointBalanceResponse pointBalance = gson.fromJson(responseModel.getResponse(), PointBalanceResponse.class);
			pointBalance.setStatusCode(responseModel.getStatusCode());
			return pointBalance;
		}
		return null;
	}

	/**
	 * Profile Details
	 * 
	 * @param profileId
	 * @param accesToken
	 * @param config
	 * @return responseModel
	 */
	@Override
	public LoyaltyProfile getProfile(final String accesToken, final String profileId, final ContextAwareConfig config) {
		log.debug("Start of getProfile API call method");
		RestServiceResponse responseModel = restService.makeGetWSCall(
				config.loyaltyDomain() + config.loyaltyProfileEndPointURL().replace(LOYALTY_PROFILE_ID, profileId),
				getoAuthReuestHeaders(accesToken, config));
		if (responseModel != null && StringUtils.isNotBlank(responseModel.getResponse())) {
			final Gson gson = new Gson();
			LoyaltyProfile loyaltyProfile = gson.fromJson(responseModel.getResponse(), LoyaltyProfile.class);
			loyaltyProfile.setStatusCode(responseModel.getStatusCode());
			return loyaltyProfile;
		}
		return null;

	}

	/**
	 * Profile Point Summary
	 *
	 * @param loyaltyProfileID
	 * @param accesToken
	 * @param config
	 * @return PointsSummaryResponse
	 */
	@Override
	public PointsSummaryResponse getProfilePointSummary(final String loyaltyProfileID, final String accesToken,
														 final ContextAwareConfig config) {
		RestServiceResponse responseModel = restService.makeGetWSCall(
				config.loyaltyDomain() + config.loyaltyPointSummaryEndPointURL().replace(LOYALTY_PROFILE_ID, loyaltyProfileID),
				getoAuthReuestHeadersWithContentType(accesToken, config));

		if (responseModel != null && StringUtils.isNotBlank(responseModel.getResponse())) {
			final Gson gson = new Gson();
			PointsActivtyDetails pointSummaryResponse[] = gson.fromJson(responseModel.getResponse(), PointsActivtyDetails[].class);
			PointsSummaryResponse finalResponse=new PointsSummaryResponse();
			finalResponse.setResponse(pointSummaryResponse);
			finalResponse.setStatusCode(responseModel.getStatusCode());
			return finalResponse;
		}
		return null;
	}

	/**
	 * Profile Order By Id
	 *
	 * @param transactionId
	 * @param profileId
	 * @param accessToken
	 * @param contextAwareConfig
	 */
	@Override
	public ActivityByIdResponse getProfileOrderById(String transactionId, String profileId, String accessToken, ContextAwareConfig contextAwareConfig) {
		String replaceProfileId = contextAwareConfig.loyaltyDomain() + contextAwareConfig.loyaltyProfileOrderByIdURL().replace(LOYALTY_PROFILE_ID, profileId);
		String loyaltyTransactionsByIdURL = replaceProfileId.replace(ORDER_ID, transactionId);
		RestServiceResponse responseModel = restService.makeGetWSCall(loyaltyTransactionsByIdURL,
				getoAuthReuestHeadersWithContentType(accessToken, contextAwareConfig));
		if (responseModel != null && StringUtils.isNotBlank(responseModel.getResponse())) {
			final Gson gson = new Gson();
			ActivityByIdResponse activityByIdResponse = gson.fromJson(responseModel.getResponse(), ActivityByIdResponse.class);
			activityByIdResponse.setStatusCode(responseModel.getStatusCode());
			return activityByIdResponse;
		}
		return null;
	}

	/**
	 * Profile Transactions By Id
	 *
	 * @param transactionId
	 * @param profileId
	 * @param accessToken
	 * @param contextAwareConfig
	 */
	@Override
	public ActivityByIdResponse getProfileTransactionsById(String transactionId, String profileId, String accessToken, ContextAwareConfig contextAwareConfig) {
		String replaceProfileId = contextAwareConfig.loyaltyDomain() +contextAwareConfig.loyaltyTransactionsByIdURL().replace(LOYALTY_PROFILE_ID, profileId);
		String loyaltyTransactionsByIdURL = replaceProfileId.replace(LOYALTY_TRANSACTION_ID, transactionId);
		RestServiceResponse responseModel = restService.makeGetWSCall(loyaltyTransactionsByIdURL,
				getoAuthReuestHeaders(accessToken, contextAwareConfig));
		if (responseModel != null && StringUtils.isNotBlank(responseModel.getResponse())) {
			final Gson gson = new Gson();
			ActivityByIdResponse activityByIdResponse = gson.fromJson(responseModel.getResponse(), ActivityByIdResponse.class);
			activityByIdResponse.setStatusCode(responseModel.getStatusCode());
			return activityByIdResponse;
		}
		return null;
	}
	
	private Map<String, String> getBasicRequestHeadersContentJson(final ContextAwareConfig config) {
		final String credetialsString = config.loyaltyProgramUserName() + ":" + config.loyaltyProgramPassword();
		final byte[] authBytes = credetialsString.getBytes(StandardCharsets.UTF_8);
		final String encodedCredentials = Base64.getEncoder().encodeToString(authBytes);
		final Map<String, String> headers = new HashMap<>();
		headers.put(AUTHORIZATION, BASIC + StringUtils.SPACE + encodedCredentials);
		headers.put(PROGRAM_CODE, config.loyaltyProgramCode());
		headers.put(ACCEPT_LANGUAGE, LoyaltyDemoConstants.ACCEPT_LANGUAGE);
		headers.put(CONTENT_TYPE, LoyaltyDemoConstants.APPLICATION_JSON);
		return headers;
	}
	
	public LoyaltyValidationResponse verifyUsername(String username, ContextAwareConfig config) {
		final Map<String, String> headers = getBasicRequestHeaders(config ,LoyaltyDemoConstants.APPLICATION_JSON);
	    	
		LoyaltyProfile loyaltyProfile = new LoyaltyProfile();
		loyaltyProfile.setUsername(username);
		final Gson userJsonObject = new Gson();    
	    final String requestBodyString = userJsonObject.toJson(loyaltyProfile).toString();
	
	    RestServiceResponse responseObject=  restService.makePostWSCall(config.loyaltyDomain() + config.loyaltyVerifyUsernameEndPointURL(),
	    		requestBodyString, headers);
	    LoyaltyValidationResponse response = null;
		if(responseObject != null && responseObject.getStatusCode() == HttpServletResponse.SC_NO_CONTENT) {
			response = new LoyaltyValidationResponse();
			response.setStatusCode(HttpServletResponse.SC_OK);
			response.setUsernameValid(true);
			
		}else if (responseObject != null && StringUtils.isNotBlank(responseObject.getResponse())) {
			final Gson gson = new Gson();
			response = gson.fromJson(responseObject.getResponse(), LoyaltyValidationResponse.class);
			response.setStatusCode(responseObject.getStatusCode());
			response.setUsernameValid(false);
		}	
		return response;
	}


	public LoyaltyProfile addProfile(final String profileJson, final ContextAwareConfig config) {
		final Map<String, String> headers = getBasicRequestHeadersContentJson(config);
	    final RestServiceResponse responseObject = restService.makePostWSCall(config.loyaltyDomain() + config.loyaltyAddProfileEndPointURL(), profileJson,
	            headers);
	    if (responseObject != null && StringUtils.isNotBlank(responseObject.getResponse())) {
			final Gson gson = new Gson();
			LoyaltyProfile loyaltyProfile = gson.fromJson(responseObject.getResponse(), LoyaltyProfile.class);
			loyaltyProfile.setStatusCode(responseObject.getStatusCode());
			return loyaltyProfile;
		}
		return null;
    
	}
	
	@Override
    public ProgramProfileTier getProfileTierProgress(final String accesToken, String loyaltyProfileID,final ContextAwareConfig config) {
		ProgramProfileTier programProfileTier = null;
		final RestServiceResponse responseObject = restService.makeGetWSCall(config.loyaltyDomain()+ config.loyaltyTierProgressEndPointURL().replace(LOYALTY_PROFILE_ID, loyaltyProfileID),
        		getoAuthReuestHeaders(accesToken, config));
        
        if (responseObject != null && StringUtils.isNotBlank(responseObject.getResponse())) {
			final Gson gson = new Gson();
			programProfileTier = gson.fromJson(responseObject.getResponse(), ProgramProfileTier.class);
			programProfileTier.setStatusCode(responseObject.getStatusCode());
			return programProfileTier;
		}
        return null;
    }

	@Override
	public GenericResponse updateGamerAlias(String loyaltyProfileID, String accessToken, String requestBody,
			ContextAwareConfig config) {
		final RestServiceResponse responseObject = restService.makePutWSCall(config.loyaltyDomain()+ config.loyaltyGamerAliasEndPointURL().replace(LOYALTY_PROFILE_ID, loyaltyProfileID),requestBody,
                getoAuthReuestHeadersWithContentType(accessToken, config));
    	if (responseObject != null && StringUtils.isNotBlank(responseObject.getResponse())) {
			final Gson gson = new Gson();
			GenericResponse genericResponse = gson.fromJson(responseObject.getResponse(), GenericResponse.class);
			genericResponse.setStatusCode(responseObject.getStatusCode());
			return genericResponse;
		}
		return null;
	}
	public CountriesResponse getCountries(String status, final ContextAwareConfig config) {
		final Map<String, String> headers = getBasicRequestHeadersContentJson(config);
		final RestServiceResponse responseObject = restService.makeGetWSCall(config.loyaltyDomain() + config.loyaltyGetCountries().replace(STATUS, status), headers);
		final Gson gson = new Gson();
		CountriesResponse countriesResponse =null;
		if(null != responseObject && responseObject.getStatusCode() == HttpServletResponse.SC_OK
				&& StringUtils.isNotBlank(responseObject.getResponse())) {
			
			final java.lang.reflect.Type listType = new com.google.gson.reflect.TypeToken<java.util.List<Country>>() {
			            }.getType();
				List<Country> countryList = gson.fromJson(responseObject.getResponse(),listType); 
				countriesResponse = new CountriesResponse();
				countriesResponse.setCountries(countryList);
				countriesResponse.setStatusCode(responseObject.getStatusCode());
			} else if(null != responseObject && StringUtils.isNotBlank(responseObject.getResponse())){
				countriesResponse = gson.fromJson(responseObject.getResponse(), CountriesResponse.class);
				countriesResponse.setStatusCode(responseObject.getStatusCode());
			}
              return countriesResponse;
			}
	
	public StatesResponse getStates(String status, String country, ContextAwareConfig config) {
		final Map<String, String> headers = getBasicRequestHeadersContentJson(config);
		final RestServiceResponse responseObject = restService.makeGetWSCall(config.loyaltyDomain() + config.loyaltyGetStates().replace(STATUS, status).replace(COUNTRY, country), headers);
		final Gson gson = new Gson();
		StatesResponse statesResponse = null;
		if(null != responseObject && responseObject.getStatusCode() == HttpServletResponse.SC_OK 
				&& StringUtils.isNotBlank(responseObject.getResponse())) {
			final java.lang.reflect.Type listType = new com.google.gson.reflect.TypeToken<java.util.List<State>>() {
            }.getType();
            List<State> stateList = gson.fromJson(responseObject.getResponse(),listType); 
            statesResponse = new StatesResponse();
            statesResponse.setStates(stateList);
            statesResponse.setStatusCode(responseObject.getStatusCode());
		} else if(null != responseObject && StringUtils.isNotBlank(responseObject.getResponse())){
			statesResponse = gson.fromJson(responseObject.getResponse(), StatesResponse.class);
			statesResponse.setStatusCode(responseObject.getStatusCode());
		}
		
		 return statesResponse;
	}
	public GenderResponse getGenderList(String status, ContextAwareConfig config) {
		final Map<String, String> headers = getBasicRequestHeadersContentJson(config);
		final RestServiceResponse responseObject = restService.makeGetWSCall(config.loyaltyDomain() + config.loyaltyGenderList().replace(STATUS, status), headers);
		 
		
		final Gson gson = new Gson();
		GenderResponse genderResponse = null;
		if(null != responseObject && responseObject.getStatusCode() == HttpServletResponse.SC_OK 
				&& StringUtils.isNotBlank(responseObject.getResponse())) {
			final java.lang.reflect.Type listType = new com.google.gson.reflect.TypeToken<java.util.List<Gender>>() {
            }.getType();
            List<Gender> genderList = gson.fromJson(responseObject.getResponse(),listType); 
            genderResponse = new GenderResponse();
            genderResponse.setGenderList(genderList);
            genderResponse.setStatusCode(responseObject.getStatusCode());
		} else if(null != responseObject && StringUtils.isNotBlank(responseObject.getResponse())){
			genderResponse = gson.fromJson(responseObject.getResponse(), GenderResponse.class);
			genderResponse.setStatusCode(responseObject.getStatusCode());
		}
		return genderResponse;
	}
	
	public LoyaltyProfile updateProfile(String profileId, String accessToken, String profileDataRequestJson,
			ContextAwareConfig config) {
		final RestServiceResponse responseObject = restService.makePutWSCall( config.loyaltyDomain()
						+ config.loyaltyProfileEndPointURL().replace(LOYALTY_PROFILE_ID, profileId),profileDataRequestJson,
				getoAuthReuestHeadersWithContentType(accessToken, config));
		 if (responseObject != null && StringUtils.isNotBlank(responseObject.getResponse())) {
			final Gson gson = new Gson();
	        LoyaltyProfile loyaltyProfile = gson.fromJson(responseObject.getResponse(), LoyaltyProfile.class);
	        loyaltyProfile.setStatusCode(responseObject.getStatusCode());
			return loyaltyProfile;
		 }
		 return null;
	}

@Override
		public GenericResponse changePassword(String accessToken, String profileId,String userName, String requestBody, ContextAwareConfig config){
		   RestServiceResponse responseObject = restService.makePutWSCall(config.loyaltyDomain() + config.changePasswordEndPointURL().replace(LOYALTY_PROFILE_ID, profileId).replace(LOYALTY_USERNAME, userName), requestBody,
			          getoAuthReuestHeadersWithContentType(accessToken, config));
		   GenericResponse res;
		   if(responseObject != null && responseObject.getStatusCode() == HttpServletResponse.SC_NO_CONTENT) {
			   res=new GenericResponse();
				res.setStatusCode(HttpServletResponse.SC_OK);	
				return res;
		   }
		   else if (responseObject != null && StringUtils.isNotBlank(responseObject.getResponse())) { 
			  final Gson gson = new Gson();
			  res = gson.fromJson(responseObject.getResponse(),GenericResponse.class); 
		      res.setStatusCode(responseObject.getStatusCode());
		     return res; 
		     
		  }
	        return null;
	   }
}
