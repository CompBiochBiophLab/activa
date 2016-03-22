package com.o2hlink.activ8.server.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * @author Miguel Angel Hernandez
 **/
@Embeddable
public class UserEventRequestId implements Serializable {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = 9183269114702254347L;
	/**
	 * 
	 **/
	private User user;
	/**
	 * 
	 **/
	private Event event;
//CONSTRUCTOR
	/**
	 * 
	 **/
	public UserEventRequestId(){
		
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
	@ManyToOne(
			targetEntity=User.class,
			cascade={CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinColumn(name="user")
	public User getUser() {
		return user;
	}
	/**
	 * @param event the event to set
	 */
	public void setEvent(Event event) {
		this.event = event;
	}
	/**
	 * @return the event
	 */
	@ManyToOne(
			targetEntity=Event.class,
			cascade={CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinColumn(name="event")
	public Event getEvent() {
		return event;
	}
	/**
	 * 
	 **/
	@Override
	public boolean equals(Object obj){
		if (obj instanceof UserEventRequestId){
			UserEventRequestId id = (UserEventRequestId)obj;
			return (user.equals(id.user) && event.equals(id.event));
		}
		return false;
	}
	/**
	 * 
	 **/
	@Override
	public int hashCode(){
		return user.hashCode() ^ event.hashCode();
	}
}
