package com.o2hlink.activ8.client.action;

import com.o2hlink.activ8.client.response.StringResponse;

/**
 * @author Miguel Angel Hernandez
 **/
public class MobileAction implements Action<StringResponse> {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = -7764778427858898027L;
	/**
	 * 
	 **/
	private String user;
	/**
	 * 
	 **/
	private String pass;
	/**
	 * 
	 **/
	private String xml;
//CONSTRUCTOR
	/**
	 * 
	 **/
	public MobileAction(){
		
	}
	/**
	 * 
	 **/
	public MobileAction(String user, String pass, String xml){
		setUser(user);
		setPass(pass);
		setXml(xml);
	}
//METHODS
	/**
	 * @param user the user to set
	 */
	public void setUser(String user) {
		this.user = user;
	}
	/**
	 * @return the user
	 */
	public String getUser() {
		return user;
	}
	/**
	 * @param pass the pass to set
	 */
	public void setPass(String pass) {
		this.pass = pass;
	}
	/**
	 * @return the pass
	 */
	public String getPass() {
		return pass;
	}
	/**
	 * @param xml the xml to set
	 */
	public void setXml(String xml) {
		this.xml = xml;
	}
	/**
	 * @return the xml
	 */
	public String getXml() {
		return xml;
	}
}
