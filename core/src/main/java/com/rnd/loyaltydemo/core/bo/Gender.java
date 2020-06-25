package com.rnd.loyaltydemo.core.bo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
/**
 * 
 * @author spathak2
 *
 */
public class Gender {

	
	@SerializedName("GenderId")
	@Expose
	private String genderId;
	
	@SerializedName("GenderCode")
	@Expose
	private String genderCode;
	
	@SerializedName("GenderDescription")
	@Expose
	private String genderDescription;
	
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

	public String getGenderId() {
		return genderId;
	}

	public void setGenderId(String genderId) {
		this.genderId = genderId;
	}

	public String getGenderCode() {
		return genderCode;
	}

	public void setGenderCode(String genderCode) {
		this.genderCode = genderCode;
	}

	public String getGenderDescription() {
		return genderDescription;
	}

	public void setGenderDescription(String genderDescription) {
		this.genderDescription = genderDescription;
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
