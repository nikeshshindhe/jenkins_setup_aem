package com.rnd.loyaltydemo.core.bo;

import java.util.List;

public class GenderResponse extends GenericResponse{

	private List<Gender> genderList;

	public List<Gender> getGenderList() {
		return genderList;
	}

	public void setGenderList(List<Gender> genderList) {
		this.genderList = genderList;
	}
	
	
}
