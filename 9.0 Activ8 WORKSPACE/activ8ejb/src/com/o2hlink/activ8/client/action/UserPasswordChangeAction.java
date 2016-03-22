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
import com.o2hlink.activ8.client.response.VoidResponse;

/**	
 * Creates a new user in the system
 * @author Miguel Angel Hernandez
 **/
public class UserPasswordChangeAction implements Action<VoidResponse> {
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
	private String currentPassword;
	/**
	 * 
	 **/
	private String newPassword;
//CONSTRUCTOR
	/**
	 * 
	 **/
	public UserPasswordChangeAction(){
		
	}
	/**
	 * 
	 **/
	public UserPasswordChangeAction(User user, String currentPassword, String newPassword){
		setUser(user);
		setCurrentPassword(currentPassword);
		setNewPassword(newPassword);
	}
//METHODS
	/**
	 * @param currentPassword the currentPassword to set
	 */
	public void setCurrentPassword(String currentPassword) {
		this.currentPassword = currentPassword;
	}
	/**
	 * @return the currentPassword
	 */
	public String getCurrentPassword() {
		return currentPassword;
	}
	/**
	 * @param newPassword the newPassword to set
	 */
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	/**
	 * @return the newPassword
	 */
	public String getNewPassword() {
		return newPassword;
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
