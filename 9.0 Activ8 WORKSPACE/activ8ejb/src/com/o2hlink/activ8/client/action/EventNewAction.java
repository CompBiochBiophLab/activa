/**
 * 
 */
package com.o2hlink.activ8.client.action;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;

import com.o2hlink.activ8.client.entity.Clinician;
import com.o2hlink.activ8.client.entity.Event;
import com.o2hlink.activ8.client.entity.Patient;
import com.o2hlink.activ8.client.entity.Researcher;
import com.o2hlink.activ8.client.entity.User;
import com.o2hlink.activ8.client.response.EventListResponse;

/**
 * Adds an event to a user.
 * @author Miguel Angel Hernandez
 **/
public class EventNewAction implements Action<EventListResponse> {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = -6881430327852320905L;
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
	public EventNewAction(){
		
	}
	/**
	 * 
	 **/
	public EventNewAction(User user, Event event){
		setUser(user);
		setEvent(event);
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
	 * @param event the event to set
	 */
	public void setEvent(Event event) {
		this.event = event;
	}
	/**
	 * @return the event
	 */
	public Event getEvent() {
		return event;
	}
}
