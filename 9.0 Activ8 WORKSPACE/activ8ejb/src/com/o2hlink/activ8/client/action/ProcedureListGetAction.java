package com.o2hlink.activ8.client.action;

import com.o2hlink.activ8.client.response.ProcedureListResponse;

/**
 * Get the list of procedures. Alternatively add the locale.
 * @author Miguel Angel Hernandez
 **/
public class ProcedureListGetAction implements Action<ProcedureListResponse>, Cacheable{
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = -1555263156370246193L;
	/**
	 * 
	 **/
	private String locale;
//CONSTRUCTOR
	/**
	 * 
	 **/
	public ProcedureListGetAction(){
		setLocale("");
	}
	/**
	 * 
	 **/
	public ProcedureListGetAction(String locale){
		setLocale(locale);
	}
//METHODS
	public long getMaxAge() {
		return 1000000000L;
	}
	/**
	 * 
	 **/
	@Override
	public boolean equals(Object obj){
		if (obj instanceof ProcedureListGetAction){
			ProcedureListGetAction action = (ProcedureListGetAction)obj;
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
