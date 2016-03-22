package com.o2hlink.activ8.client.action;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;

import com.o2hlink.activ8.client.entity.Clinician;
import com.o2hlink.activ8.client.entity.Account;
import com.o2hlink.activ8.client.entity.Patient;
import com.o2hlink.activ8.client.entity.Researcher;
import com.o2hlink.activ8.client.entity.User;
import com.o2hlink.activ8.client.response.MessageListResponse;

/**
 * Retrieve the messages of a particular communication channel. If no one is specified, retrieved messages are from the internal system.
 * @author Miguel Angel Hernandez
 **/
public class MessageListGetAction implements Action<MessageListResponse>, Cacheable{
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = 627840812405932143L;
	/**
	 * 
	 **/
	private User user;
	/**
	 * 
	 **/
	private Account account;
//CONSTRUCTOR
	/**
	 * 
	 **/
	public MessageListGetAction(){
		
	}
	/**
	 * 
	 **/
	public MessageListGetAction(User user, Account account){
		setUser(user);
		setAccount(account);
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
		@XmlElement(name="researcher", type=Researcher.class)
	})
	public User getUser() {
		return user;
	}
	/**
	 * @param email the email to set
	 */
	public void setAccount(Account email) {
		this.account = email;
	}
	/**
	 * @return the email
	 */
	public Account getAccount() {
		return account;
	}
	/**
	 * 
	 **/
	public long getMaxAge() {
		return 60000;
	}
	/**
	 * 
	 **/
	@Override
	public boolean equals(Object obj){
		if (obj instanceof MessageListGetAction){
			MessageListGetAction action = (MessageListGetAction)obj;
			return getUser().equals(action.getUser()) &&
				  (getAccount()!=null)?getAccount().equals(action.getAccount()):(action.getAccount()==null);
		}
		return false;
	}
	/**
	 * 
	 **/
	@Override
	public int hashCode(){
		int hash = getUser().hashCode();
		if (getAccount() != null)
			hash ^= getAccount().hashCode();
		return hash;
	}
}
