package com.o2hlink.activ8.client.action;

import com.o2hlink.activ8.client.response.DiseaseListResponse;

/**
 * Get the list of all disease
 * TODO: CUT THE SEARCH!!!
 * @author Miguel Angel Hernandez
 **/
public class DiseaseListGetAction implements Action<DiseaseListResponse>, Cacheable {
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
	public DiseaseListGetAction(){
		setLocale("");
	}
	/**
	 * 
	 **/
	public DiseaseListGetAction(String locale){
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
		if (obj instanceof DiseaseListGetAction){
			DiseaseListGetAction action = (DiseaseListGetAction)obj;
			return action.getLocale().equals(action.getLocale());
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
