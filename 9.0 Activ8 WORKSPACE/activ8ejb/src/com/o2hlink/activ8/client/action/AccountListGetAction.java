package com.o2hlink.activ8.client.action;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;

import com.o2hlink.activ8.client.entity.Clinician;
import com.o2hlink.activ8.client.entity.Patient;
import com.o2hlink.activ8.client.entity.Researcher;
import com.o2hlink.activ8.client.entity.User;
import com.o2hlink.activ8.client.response.AccountListResponse;

/**
 * Retrieve any communication channel, i.e. skype, email, video conference, sms
 * @author Miguel Angel Hernandez
 **/
public class AccountListGetAction implements Action<AccountListResponse>, Cacheable {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = -3507164990828228276L;
	/**
	 * 
	 **/
	private User user;
//CONSTRUCTOR
	/**
	 * 
	 **/
	public AccountListGetAction(){
		
	}
	/**
	 * 
	 **/
	public AccountListGetAction(User user){
		setUser(user);
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
		if (obj instanceof AccountListGetAction){
			AccountListGetAction action = (AccountListGetAction)obj;
			return action.getUser().equals(getUser());
		}
		return false;
	}
	/**
	 * 
	 **/
	@Override
	public int hashCode(){
		return getUser().hashCode();
	}
}
