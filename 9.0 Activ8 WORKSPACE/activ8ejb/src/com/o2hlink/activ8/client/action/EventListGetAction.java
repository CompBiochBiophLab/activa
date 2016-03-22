package com.o2hlink.activ8.client.action;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;

import com.o2hlink.activ8.client.entity.Clinician;
import com.o2hlink.activ8.client.entity.Account;
import com.o2hlink.activ8.client.entity.Patient;
import com.o2hlink.activ8.client.entity.Researcher;
import com.o2hlink.activ8.client.entity.User;
import com.o2hlink.activ8.client.response.EventListResponse;

/**
 * Retrieve the list of events a user may have. Optionally it is possible to retrieve the events from an external system.
 * @author Miguel Angel Hernandez
 **/
public class EventListGetAction implements Action<EventListResponse>, Cacheable{
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = -6875097996327227059L;
	/**
	 * 
	 **/
	private User user;
	/**
	 * 
	 **/
	private Date start;
	/**
	 * 
	 **/
	private Date end;
	/**
	 * 
	 **/
	private Account account;
//CONSTRUCTOR
	/**
	 * 
	 **/
	public EventListGetAction(){
		
	}
	/**
	 * 
	 **/
	public EventListGetAction(User user, Date start, Date end){
		setUser(user);
		setStart(start);
		setEnd(end);
	}
	/**
	 * 
	 **/
	public EventListGetAction(User user, Date start, Date end, Account account){
		this(user, start, end);
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
	 * @param start the start to set
	 */
	public void setStart(Date start) {
		this.start = start;
	}
	/**
	 * @return the start
	 */
	public Date getStart() {
		return start;
	}
	/**
	 * @param end the end to set
	 */
	public void setEnd(Date end) {
		this.end = end;
	}
	/**
	 * @return the end
	 */
	public Date getEnd() {
		return end;
	}
	/**
	 * @param communication the communication to set
	 */
	public void setAccount(Account communication) {
		this.account = communication;
	}
	/**
	 * @return the communication
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
		if (obj instanceof EventListGetAction){
			EventListGetAction action = (EventListGetAction)obj;
			return getUser().equals(action.getUser()) && 
				   getStart().equals(action.getStart()) &&
				   getEnd().equals(action.getEnd()) && 
				   (getAccount()!=null)?getAccount().equals(action.getAccount()):(action.getAccount()==null);
		}
		return false;
	}
	/**
	 * 
	 **/
	@Override
	public int hashCode() {
		int hash = getUser().hashCode() ^ getStart().hashCode() ^ getEnd().hashCode();
		if (getAccount()!=null)
			hash = hash ^ getAccount().hashCode();
		return hash;
	}
}
