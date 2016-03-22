package com.o2hlink.activ8.client.action;

import com.o2hlink.activ8.client.entity.Country;
import com.o2hlink.activ8.client.response.CountryResponse;

/**
 * Administrative action. Create a new country in the system.
 * @author Miguel Angel Hernandez
 **/
public class CountryNewAction implements Action<CountryResponse> {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = -4489196785967451301L;
	/**
	 * 
	 **/
	private Country country;
//CONSTRUCTOR
	/**
	 * 
	 **/
	public CountryNewAction(){
		
	}
	/**
	 * 
	 **/
	public CountryNewAction(Country country){
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
