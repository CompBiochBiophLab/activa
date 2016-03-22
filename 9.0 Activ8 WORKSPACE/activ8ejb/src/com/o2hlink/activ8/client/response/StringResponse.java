package com.o2hlink.activ8.client.response;

/**
 * Return a list of strings.
 * @author Miguel Angel Hernandez
 **/
public class StringResponse implements Response {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = -1387720587394048354L;
	/**
	 * 
	 **/
	private String string;
//CONSTRUCTOR
	/**
	 * 
	 **/
	public StringResponse(){
		
	}
	/**
	 * 
	 **/
	public StringResponse(String string){
		setString(string);
	}
//METHODS
	/**
	 * @param string the string to set
	 */
	public void setString(String string) {
		this.string = string;
	}
	/**
	 * @return the string
	 */
	public String getString() {
		return string;
	}
}
