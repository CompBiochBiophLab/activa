package com.o2hlink.activ8.client.response;

import java.util.List;

import com.o2hlink.activ8.client.entity.Country;

/**
 * Return a list of countries.
 * @author Miguel Angel Hernandez
 **/
public class CountryListResponse implements Response {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = -7060932245858093205L;
	/**
	 * 
	 **/
	private List<Country> countries;
//CONSTRUCTOR
	/**
	 * 
	 **/
	public CountryListResponse(){
		
	}
	/**
	 * 
	 **/
	public CountryListResponse(List<Country> countries){
		setCountries(countries);
	}
//METHODS
	/**
	 * @param countries the countries to set
	 */
	public void setCountries(List<Country> countries) {
		this.countries = countries;
	}
	/**
	 * @return the countries
	 */
	public List<Country> getCountries() {
		return countries;
	}
}
