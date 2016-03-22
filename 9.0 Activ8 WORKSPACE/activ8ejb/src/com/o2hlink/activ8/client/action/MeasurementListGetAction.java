/**
 * 
 */
package com.o2hlink.activ8.client.action;

import com.o2hlink.activ8.client.response.MeasurementListResponse;

/**
 * Get a list of all the measurements in the system.
 * TODO: CUT THE SEARCH!
 * @author Miguel Angel Hernandez
 **/
public class MeasurementListGetAction implements Action<MeasurementListResponse>, Cacheable {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = -2736418883571330171L;
	/**
	 * 
	 **/
	private String locale;
//CONSTRUCTOR
	/**
	 * Empty constructor to allow serialization
	 **/
	public MeasurementListGetAction(){
		setLocale("");
	}
	/**
	 * 
	 **/
	public MeasurementListGetAction(String locale){
		setLocale(locale);
	}
//METHODS
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
		if (obj instanceof MeasurementListGetAction){
			MeasurementListGetAction action = (MeasurementListGetAction) obj;
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
}
