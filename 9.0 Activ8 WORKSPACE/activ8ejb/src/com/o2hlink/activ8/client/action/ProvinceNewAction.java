package com.o2hlink.activ8.client.action;

import com.o2hlink.activ8.client.entity.Country;
import com.o2hlink.activ8.client.entity.Province;
import com.o2hlink.activ8.client.response.VoidResponse;

/**
 * @author Miguel Angel Hernandez
 **/
public class ProvinceNewAction implements Action<VoidResponse> {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = -7449011788741133985L;
	/**
	 * 
	 **/
	private Country country;
	/**
	 * 
	 **/
	private Province province;
//CONSTRUCTOR
	/**
	 * 
	 **/
	public ProvinceNewAction(){
		
	}
	/**
	 * 
	 **/
	public ProvinceNewAction(Country country, Province province){
		setCountry(country);
		setProvince(province);
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
	 * @param province the province to set
	 */
	public void setProvince(Province province) {
		this.province = province;
	}
	/**
	 * @return the province
	 */
	public Province getProvince() {
		return province;
	}
}
