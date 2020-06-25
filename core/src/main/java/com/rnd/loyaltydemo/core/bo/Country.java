package com.rnd.loyaltydemo.core.bo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Country {

	@SerializedName("CountryId")
    @Expose
	private String countryId;
	
	@SerializedName("CountryCode")
    @Expose
	private String countryCode;
	
	@SerializedName("CurrencyCode")
    @Expose
	private String currencyCode;
	
	@SerializedName("CountryName")
    @Expose
	private String countryName;
	
	@SerializedName("RegionCode")
    @Expose
	private String regionCode;
	
	@SerializedName("CountryIsonCode")
    @Expose
	private String countryIsonCode;
	
	@SerializedName("CountryIso3Code")
    @Expose
	private String countryIso3Code;
	
	@SerializedName("Status")
    @Expose
	private String status;
	
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

	public String getCountryId() {
		return countryId;
	}

	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getRegionCode() {
		return regionCode;
	}

	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}

	public String getCountryIsonCode() {
		return countryIsonCode;
	}

	public void setCountryIsonCode(String countryIsonCode) {
		this.countryIsonCode = countryIsonCode;
	}

	public String getCountryIso3Code() {
		return countryIso3Code;
	}

	public void setCountryIso3Code(String countryIso3Code) {
		this.countryIso3Code = countryIso3Code;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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
