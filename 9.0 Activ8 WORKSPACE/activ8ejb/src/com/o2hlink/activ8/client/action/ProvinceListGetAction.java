package com.o2hlink.activ8.client.action;

import com.o2hlink.activ8.client.entity.Country;
import com.o2hlink.activ8.client.response.ProvinceListResponse;

/**
 * Retrieve the list of provinces per country.
 * @author Miguel Angel Hernandez
 **/
public class ProvinceListGetAction implements Action<ProvinceListResponse>, Cacheable {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = 2850736815251390260L;
	/**
	 * 
	 **/
	private Country country;
//CONSTRUCTOR
	/**
	 * 
	 **/
	public ProvinceListGetAction(){
		
	}
	/**
	 * 
	 **/
	public ProvinceListGetAction(Country country){
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
	/**
	 * 
	 **/
	public long getMaxAge() {
		return 1000000000L;
	}
	/**
	 * 
	 **/
	@Override
	public boolean equals(Object obj){
		if (obj instanceof ProvinceListGetAction){
			ProvinceListGetAction instance = (ProvinceListGetAction)obj;
			return getCountry().equals(instance.getCountry());
		}
		return false;
	}
	/**
	 * 
	 **/
	@Override
	public int hashCode(){
		return getCountry().hashCode();
	}
}
