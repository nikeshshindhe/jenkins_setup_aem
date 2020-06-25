package com.rnd.loyaltydemo.core.service;

import java.util.List;

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
import com.rnd.loyaltydemo.core.bo.PointsSummaryResponse;
import com.rnd.loyaltydemo.core.bo.ProgramProfileTier;
import com.rnd.loyaltydemo.core.bo.ReferralCode;
import com.rnd.loyaltydemo.core.bo.State;
import com.rnd.loyaltydemo.core.bo.StatesResponse;
import com.rnd.loyaltydemo.core.config.ContextAwareConfig;

public interface LoyaltyService {

	AccessTokenResponse login(String username, String password, ContextAwareConfig config);
	ReferralCode getUserReffralCode(String profileId, String accessToken, ContextAwareConfig config);
	PointBalanceResponse getProfilePointBalance(final String profileId, final String accesToken,
            final ContextAwareConfig config);
	LoyaltyProfile getProfile(String accesToken, String profileId, ContextAwareConfig config);
	PointsSummaryResponse getProfilePointSummary(String loyaltyProfileID, String accesToken,
												  ContextAwareConfig config);
	ActivityByIdResponse getProfileOrderById(String transactionId, String profileId, String accessToken,
                                             ContextAwareConfig contextAwareConfig);
	ActivityByIdResponse getProfileTransactionsById(String transactionId, String profileId, String accessToken,
                                                    ContextAwareConfig contextAwareConfig);
	LoyaltyValidationResponse verifyUsername(String username, ContextAwareConfig contextAwareConfig);
	
	LoyaltyProfile addProfile(final String profileJson, final ContextAwareConfig config);
	
	ProgramProfileTier getProfileTierProgress(String accessToken, String profileId, ContextAwareConfig config);
	
    GenericResponse changePassword(String accessToken, String profileId,String userName, String requestBody, ContextAwareConfig config);
	
	GenericResponse updateGamerAlias(String profileId, String accessToken, String requestBody, ContextAwareConfig config);
	
	GenderResponse getGenderList(String status, ContextAwareConfig contextAwareConfig);
	
	CountriesResponse getCountries(final String status, ContextAwareConfig config);
	
	StatesResponse getStates(String status, String country, ContextAwareConfig contextAwareConfig);
	
	LoyaltyProfile updateProfile(String profileId, String accessToken, String profileDataRequestJson,
			ContextAwareConfig contextAwareConfig);
}
