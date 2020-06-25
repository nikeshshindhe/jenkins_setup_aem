package com.rnd.loyaltydemo.core.bo;

public class RegistrationResponse extends GenericResponse{

	private boolean isNewUser;

	public boolean isNewUser() {
		return isNewUser;
	}

	public void setNewUser(boolean isNewUser) {
		this.isNewUser = isNewUser;
	}
	
	
}
