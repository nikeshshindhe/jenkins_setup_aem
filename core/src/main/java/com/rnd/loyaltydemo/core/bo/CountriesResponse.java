package com.rnd.loyaltydemo.core.bo;

import java.util.List;

public class CountriesResponse extends GenericResponse{

	private List<Country> countries;

	public List<Country> getCountries() {
		return countries;
	}

	public void setCountries(List<Country> countries) {
		this.countries = countries;
	}
	
	
}
