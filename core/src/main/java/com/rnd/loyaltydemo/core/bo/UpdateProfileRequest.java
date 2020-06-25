package com.rnd.loyaltydemo.core.bo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class UpdateProfileRequest extends LoyaltyProfile{


    @SerializedName("Password")
    @Expose
    private String password;
    
    @SerializedName("ReferralCode")
    @Expose
    private String ReferralCode;
    
    @SerializedName("ProfileReferralCodeId")
    @Expose
    private String profileReferralCodeId;
    
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getReferralCode() {
		return ReferralCode;
	}
	public void setReferralCode(String referralCode) {
		ReferralCode = referralCode;
	}
	public String getProfileReferralCodeId() {
		return profileReferralCodeId;
	}
	public void setProfileReferralCodeId(String profileReferralCodeId) {
		this.profileReferralCodeId = profileReferralCodeId;
	}    
   
}
