package com.rnd.loyaltydemo.core.bo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class State {

	@SerializedName("StateId")
    @Expose
	private String stateId;
	
	@SerializedName("StateCode")
    @Expose
	private String stateCode;
	
	@SerializedName("CountryCode")
    @Expose
	private String countryCode;
	
	@SerializedName("StateName")
    @Expose
	private String stateName;
	
	@SerializedName("languageCode")
    @Expose
	private String LanguageCode;
	
	@SerializedName("status")
    @Expose
	private String Status;
	
	@SerializedName("CreateUser")
    @Expose
	private String createUser;
	
	@SerializedName("CreateDate")
    @Expose
	private String createDate;
	
	@SerializedName("UpdateUser")
    @Expose
	private String updateUser;
	
	@SerializedName("UpdateDate")
    @Expose
	private String updateDate;

	public String getStateId() {
		return stateId;
	}

	public void setStateId(String stateId) {
		this.stateId = stateId;
	}

	public String getStateCode() {
		return stateCode;
	}

	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public String getLanguageCode() {
		return LanguageCode;
	}

	public void setLanguageCode(String languageCode) {
		LanguageCode = languageCode;
	}

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
	

}
