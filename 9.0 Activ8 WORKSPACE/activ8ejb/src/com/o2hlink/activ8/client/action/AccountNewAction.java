package com.o2hlink.activ8.client.action;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;

import com.o2hlink.activ8.client.entity.Clinician;
import com.o2hlink.activ8.client.entity.Account;
import com.o2hlink.activ8.client.entity.Patient;
import com.o2hlink.activ8.client.entity.Researcher;
import com.o2hlink.activ8.client.entity.User;
import com.o2hlink.activ8.client.response.AccountResponse;

/**
 * Add any communication channel, i.e. skype, email, video conference, sms
 * @author Miguel Angel Hernandez
 **/
public class AccountNewAction implements Action<AccountResponse> {
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
	private Account account;
	/**
	 * 
	 **/
	private String password;
//CONSTRUCTOR
	/**
	 * 
	 **/
	public AccountNewAction(){
		
	}
	/**
	 * 
	 **/
	public AccountNewAction(User user, Account communication, String password){
		setUser(user);
		setAccount(communication);
		setPassword(password);
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
	 * @param account the communication to set
	 */
	public void setAccount(Account account) {
		this.account = account;
	}
	/**
	 * @return the communication
	 */
	public Account getAccount() {
		return account;
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
