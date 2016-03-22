package com.o2hlink.activ8.client.action;

import com.o2hlink.activ8.client.response.CountryListResponse;

/**
 * Get the list of all countries in the system. An additional locale can be specified.
 * @author Miguel Angel Hernandez
 **/
public class CountryListGetAction implements Action<CountryListResponse>, Cacheable {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = 7271036453220525328L;
	/**
	 * 
	 **/
	private String locale;
//CONSTRUCTOR
	/**
	 * 
	 **/
	public CountryListGetAction(){
		setLocale("");
	}
	/**
	 * 
	 **/
	public CountryListGetAction(String locale){
		setLocale(locale);
	}
//METHODS
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
		if (obj instanceof CountryListGetAction){
			CountryListGetAction action = (CountryListGetAction)obj;
			return getLocale().equals(action.getLocale());
		}
		return false;
	}
	/**
	 * 
	 **/
	@Override
	public int hashCode(){
		return getLocale().hashCode();
	}
	/**
	 * @param locale the locale to set
	 */
	public void setLocale(String locale) {
		this.locale = locale;
	}
	/**
	 * @return the locale
	 */
	public String getLocale() {
		return locale;
	}
}
