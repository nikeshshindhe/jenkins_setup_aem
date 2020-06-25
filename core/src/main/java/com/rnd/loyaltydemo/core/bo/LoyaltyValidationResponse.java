package com.rnd.loyaltydemo.core.bo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoyaltyValidationResponse extends GenericResponse{
	@SerializedName("isPwdExpired")
	@Expose
	private boolean isPwdExpired;
	
	@SerializedName("usernameValid")
	@Expose
	private boolean usernameValid;

	public boolean isPwdExpired() {
		return isPwdExpired;
	}

	public void setPwdExpired(boolean pwdExpired) {
		isPwdExpired = pwdExpired;
	}

	public boolean isUsernameValid() {
		return usernameValid;
	}

	public void setUsernameValid(boolean usernameValid) {
		this.usernameValid = usernameValid;
	}

	
	
	
}
