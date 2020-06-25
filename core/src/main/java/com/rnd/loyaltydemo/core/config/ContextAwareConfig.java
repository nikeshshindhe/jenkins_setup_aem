
package com.rnd.loyaltydemo.core.config;

import org.apache.sling.caconfig.annotation.Configuration;
import org.apache.sling.caconfig.annotation.Property;

/**
 * Sling Context Aware Configuration Contains all API configuration related to
 * loyalty service
 *
 */
@Configuration(label = "Sling Context Aware Configuration", description = "Sling Context Aware Configuration")
public @interface ContextAwareConfig {

	/**
	 * @return loyaltyDomain
	 */
	@Property(label = "Loyalty Domain URL ", description = "This holds the value of Loyalty Domain URL", order = 1)
	String loyaltyDomain() default "https://p2demx-pcapi.epsilonagilityloyalty.com";

	/**
	 * @return loyaltyProgramUserName
	 */
	@Property(label = "Loyalty Program Account Username", description = "This holds the value of Loyalty Program Account Username")
	String loyaltyProgramUserName() default "GRATIS_DEMO_KEY";

	/**
	 * @return loyaltyProgramPassword
	 */
	@Property(label = "Loyalty Program Account Password", description = "This holds the value of Loyalty Program Account Password")
	String loyaltyProgramPassword() default "LoyaltyCRM!";

	/**
	 * @return loyaltyProgramCode
	 */
	@Property(label = "Loyalty Program Code", description = "This holds the value of Loyalty Program Code")
	String loyaltyProgramCode() default "GRATIS";

	@Property(label = "Site Path to setup cookies", description = "ite Path to setup cookies", order = 1)
	String cookiePrefix() default "loyalty_enUS_";

	@Property(label = "Cookie Expiry in seconds", description = "Cookie Expiry in seconds", order = 1)
	int cookieExpiry() default 3600;
	
	@Property(label = "Refresh token cookie expiry in seconds", description = "Refresh token cookie expiry in seconds", order = 1)
	int refreshTokenExpiry() default 4800;
	
	/**
	 * @return Profile Image Path
	 */
	@Property(label = "Profile Image Path ", description = "Profile Image Path in DAM", order = 1)
	String loyaltyImageUploadFolder() default "/content/dam/uploads";

	/**
	 * @return loyaltyTokenEndPointURL
	 */
	@Property(label = "Loyalty Token Endpoint URL ", description = "This holds the value of Loyalty Endpoint URL for token generation", order = 1)
	String loyaltyTokenEndPointURL() default "/api/v1/authorization/profiles/tokens";

	/**
	 * @return loyaltyProfileEndPointURL
	 */
	@Property(label = "Loyalty Profile Endpoint URL ", description = "This holds the value of Loyalty Profile Endpoint URL ", order = 1)
	String loyaltyProfileEndPointURL() default "/api/v1/profiles/{profileId}";

	/**
	 * @return loyaltyPointBalanceEndPointURL
	 */
	@Property(label = "Loyalty Points Balance Endpoint URL ", description = "This holds the value of Loyalty Points Balance Endpoint URL ", order = 1)
	String loyaltyPointBalanceEndPointURL() default "/api/v1/profiles/{profileId}/points/balance?status=A";
    
	/**
	 * @return loyaltyUserReffralCodeEndPointURL.
	 */
	@Property(label = "Loyalty User RefferalCode Endpoint URL ", description = "This holds the value of Loyalty User ReffralCode Endpoint URL ", order = 1)
	String loyaltyUserReffralCodeEndPointURL() default "/api/v1/profiles/{profileId}/referral/code";
		
	/**
	 * @return loyaltyPointSummaryEndPointURL
	 */
	@Property(label = "Loyalty Points Summary Endpoint URL ", description = "This holds the value of Loyalty Points Summary Endpoint URL ", order = 1)
	String loyaltyPointSummaryEndPointURL() default "/api/v1/profiles/{profileId}/points/summary";

	/**
	 * @return loyaltyProfileOrderByIdURL
	 */
	@Property(label = "Loyalty Profile Order Details By ID Endpoint URL", description = "This holds the value of Loyalty Profile Order Details By ID Endpoint URl", order = 1)
	String loyaltyProfileOrderByIdURL() default "/api/v1/profiles/{profileId}/rewards/catalogs/orders/{orderId}";

	/**
	 * @return loyaltyTransactionsByIdURL
	 */
	@Property(label = "Loyalty Transaction Details By ID Endpoint URL", description = "This holds the value of Loyalty Transaction Details By ID Endpoint URl", order = 1)
	String loyaltyTransactionsByIdURL() default "/api/v1/profiles/{profileId}/transactions/{transactionId}";
	
	/**
	 * @return loyaltyVerifyUsernameEndPointURL
	 */
	
	@Property(label = "Loyalty Check username exist Endpoint URL ", description = "This holds the value of Check username exist Endpoint URL ", order = 1)
	String loyaltyVerifyUsernameEndPointURL() default "/api/v2/profiles/credentials/username/verify";
    
	/**
	 * @return loyaltyAddProfileEndPointURL
	 */
	@Property(label = "Loyalty Add profile Endpoint URL ", description = "This holds the value of Loyalty Add profile Endpoint URL ", order = 1)
	String loyaltyAddProfileEndPointURL() default "/api/v1/profiles";
	
	/**
	 * @return loyaltyTierProgressEndPointURL
	 */
	@Property(label = "Loyalty Tier Progress Endpoint URL ", description = "This holds the value of Loyalty Tier Progress Endpoint URL ", order = 1)
	String loyaltyTierProgressEndPointURL() default "/api/v1/profiles/{profileId}/dashboard/tiers/progress";
	
	/**
	 * @return loyaltyGamerAliasEndPointURL
	 */
	@Property(label = "Loyalty Gamer Alias Endpoint URL ", description = "This holds the value of Loyalty Gamer Alias Endpoint URL ", order = 1)
	String loyaltyGamerAliasEndPointURL() default "/api/v1/profiles/{profileId}/GamerAlias";
	
	/**
	 * @return loyaltyCardnumberPrefix
	 */
	@Property(label = "Loyalty Card Number Prefix ", description = "This Value Gets Appended to the card Number ", order = 1)
	String loyaltyCardnumberPrefix() default "AEM";
	
	/**
	 * @return loyaltySourceCode
	 */
	@Property(label = "Loyalty Source Code ", description = "This holds the value of Source Code ", order = 1)
	String loyaltySourceCode() default "WEB";
	
	/**
	 * @return loyaltyEnrollChannelCode
	 */
	@Property(label = "Loyalty Enroll Channel Code ", description = "This holds the value of Enroll Channel Code ", order = 1)
	String loyaltyEnrollChannelCode   () default "WEB";
	
	/**
	 * @return loyaltyAggregateEntityCode
	 */
	@Property(label = "Loyalty Profile's Aggretgate Entity Code ", description = "This holds the value of Profile's Aggretgate Entity Code ", order = 1)
	String loyaltyAggregateEntityCode() default "POINTSP";
	
	/**
	 * @return loyaltyGenderList
	 */
	@Property(label = "Get Gender List ", description = "This holds the list of gender", order = 1)
	String loyaltyGenderList() default "/api/v1/referencedata/profiles/genders?status={status}";
    
	/**
	 * @return loyaltyGetCountries
	 */
	@Property(label = "Get Countries List ", description = "This holds the list of countries", order = 1)
	String loyaltyGetCountries() default "/api/v1/referencedata/profiles/countries?status={status}";
	
	/**
	 * @return loyaltyGetStates
	 */
	@Property(label = "Get States List ", description = "This holds the list of states", order = 1)
	String loyaltyGetStates() default "/api/v1/referencedata/profiles/states?status={status}&countryCode={countryCode}";
	
  /**
	 * @return loyaltychangePasswordCode
	 */
	@Property(label = "Profile Change Password API Endpoint ", description = "This holds the value of Profile's changePassword URL ", order = 1)
    String changePasswordEndPointURL() default "/api/v1/authorization/profiles/{profileId}/{username}/password";
}