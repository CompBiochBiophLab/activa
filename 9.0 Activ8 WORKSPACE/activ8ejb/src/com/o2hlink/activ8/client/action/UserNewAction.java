/**
 * 
 */
package com.o2hlink.activ8.client.action;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;

import com.o2hlink.activ8.client.entity.Clinician;
import com.o2hlink.activ8.client.entity.Patient;
import com.o2hlink.activ8.client.entity.Researcher;
import com.o2hlink.activ8.client.entity.User;
import com.o2hlink.activ8.client.response.UserResponse;

/**	
 * Creates a new user in the system
 * @author Miguel Angel Hernandez
 **/
public class UserNewAction implements Action<UserResponse> {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = -7153776276602075686L;
	/**
	 * 
	 **/
	private User user;
	/**
	 * 
	 **/
	private String password;
//METHODS
	/**
	 * 
	 **/
	public UserNewAction(){
		
	}
	/**
	 * 
	 **/
	public UserNewAction(User user, String password){
		setUser(user);
		setPassword(password);
	}
//METHODS
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
	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}
	/**
	 * @return the user
	 */
	@XmlElements({
		@XmlElement(name="patient", type=Patient.class),
		@XmlElement(name="clinician", type=Clinician.class),
		@XmlElement(name="researcher", type=Researcher.class)
	})
	public User getUser() {
		return user;
	}
}
