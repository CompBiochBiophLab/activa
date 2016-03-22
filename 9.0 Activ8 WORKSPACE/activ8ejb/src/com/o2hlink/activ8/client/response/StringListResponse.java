package com.o2hlink.activ8.client.response;

import java.util.ArrayList;
import java.util.List;

/**
 * A list of responses
 * @author Miguel Angel Hernandez
 **/
public class StringListResponse implements Response {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = 1684904432861292261L;
	/**
	 * 
	 **/
	private List<String> strings = new ArrayList<String>();
//CONSTRUCTOR
	/**
	 *
	 **/
	public StringListResponse(){
		
	}
	/**
	 * 
	 **/
	public StringListResponse(List<String> strings){
		setStrings(strings);
	}
//METHODS
	/**
	 * @param strings the strings to set
	 */
	public void setStrings(List<String> strings) {
		this.strings = strings;
	}
	/**
	 * @return the strings
	 */
	public List<String> getStrings() {
		return strings;
	}
}
