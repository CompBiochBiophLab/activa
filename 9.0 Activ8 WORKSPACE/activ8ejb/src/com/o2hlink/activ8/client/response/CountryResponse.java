package com.o2hlink.activ8.client.response;

import com.o2hlink.activ8.client.entity.Country;

/**
 * Return a single country.
 * @author Miguel Angel Hernandez
 **/
public class CountryResponse implements Response {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = -7060932245858093205L;
	/**
	 * 
	 **/
	private Country country;
//CONSTRUCTOR
	/**
	 * 
	 **/
	public CountryResponse(){
		
	}
	/**
	 * 
	 **/
	public CountryResponse(Country country){
		setCountry(country);
	}
//METHODS
	/**
	 * @param country the country to set
	 */
	public void setCountry(Country country) {
		this.country = country;
	}
	/**
	 * @return the country
	 */
	public Country getCountry() {
		return country;
	}
}
