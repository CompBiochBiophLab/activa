package com.o2hlink.activ8.client.action;

import com.o2hlink.activ8.client.response.UserResponse;

/**
 * Login action, user and password must be supplied. This is the only method to retrieve single users given an id or username for security reassons.
 * @author Miguel Angel Hernandez
 **/
public class UserLoginAction implements Action<UserResponse> {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = -4779560443729497281L;
	/**
	 * 
	 */
	private String username;
	/**
	 * 
	 */
	private String password;
//CONSTRUCTOR
	/**
	 * Empty constructor to allow serialization
	 **/
	public UserLoginAction(){
		
	}
	/**
	 * 
	 */
	public UserLoginAction(String username, String password){
		this.username = username;
		this.password = password;
	}
//METHODS
	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
}
