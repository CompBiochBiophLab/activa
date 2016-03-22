package com.o2hlink.activ8.client.action;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;

import com.o2hlink.activ8.client.entity.Clinician;
import com.o2hlink.activ8.client.entity.Account;
import com.o2hlink.activ8.client.entity.Patient;
import com.o2hlink.activ8.client.entity.Researcher;
import com.o2hlink.activ8.client.entity.User;
import com.o2hlink.activ8.client.response.VoidResponse;

/**
 * Remove any communication channel, i.e. skype, email, video conference, sms
 * @author Miguel Angel Hernandez
 **/
public class AccountRemoveAction implements Action<VoidResponse> {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = -3507164990828228276L;
	/**
	 * 
	 **/
	private User user;
	/**
	 * 
	 **/
	private Account communication;
//CONSTRUCTOR
	/**
	 * 
	 **/
	public AccountRemoveAction(){
		
	}
	/**
	 * 
	 **/
	public AccountRemoveAction(User user, Account communication){
		setUser(user);
		setAccount(communication);
	}
//METHODS
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
		@XmlElement(name="researcher", type=Researcher.class),
	})
	public User getUser() {
		return user;
	}
	/**
	 * @param communication the communication to set
	 */
	public void setAccount(Account communication) {
		this.communication = communication;
	}
	/**
	 * @return the communication
	 */
	public Account getAccount() {
		return communication;
	}
}
