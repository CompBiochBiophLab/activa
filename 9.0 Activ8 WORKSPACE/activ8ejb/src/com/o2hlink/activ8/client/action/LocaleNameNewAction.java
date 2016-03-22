package com.o2hlink.activ8.client.action;

import com.o2hlink.activ8.client.entity.HasLocales;
import com.o2hlink.activ8.client.response.VoidResponse;
import com.o2hlink.activ8.common.entity.HasName;

/**
 * Add a new locale
 * @author Miguel Angel Hernandez
 **/
public class LocaleNameNewAction implements Action<VoidResponse> {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = 68367645396792874L;
	/**
	 * 
	 **/
	private HasLocales locale;
	/**
	 * 
	 **/
	private HasName name;
//CONSTRUCTOR
	/**
	 * 
	 **/
	public LocaleNameNewAction(){
		
	}
	/**
	 * 
	 **/
	public LocaleNameNewAction(HasLocales locale, HasName name){
		setLocale(locale);
		setName(name);
	}
//METHODS
	/**
	 * @param locale the locale to set
	 */
	public void setLocale(HasLocales locale) {
		this.locale = locale;
	}
	/**
	 * @return the locale
	 */
	public HasLocales getLocale() {
		return locale;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(HasName name) {
		this.name = name;
	}
	/**
	 * @return the name
	 */
	public HasName getName() {
		return name;
	}
}
