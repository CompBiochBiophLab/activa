package com.o2hlink.activ8.client.action;

import com.o2hlink.activ8.client.response.DrugListResponse;

/**
 * Get the list of drugs
 * @author Miguel Angel Hernandez
 **/
public class DrugListGetAction implements Action<DrugListResponse>, Cacheable {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = 1894317685615387397L;
	/**
	 * 
	 **/
	private String locale;
//CONSTRUCTOR
	/**
	 * 
	 **/
	public DrugListGetAction(){
		setLocale("");
	}
	/**
	 * 
	 **/
	public DrugListGetAction(String locale){
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
		if (obj instanceof DrugListGetAction){
			DrugListGetAction action = (DrugListGetAction) obj;
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
