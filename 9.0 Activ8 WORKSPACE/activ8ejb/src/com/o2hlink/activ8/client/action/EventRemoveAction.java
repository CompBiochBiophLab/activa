/**
 * 
 */
package com.o2hlink.activ8.client.action;

import com.o2hlink.activ8.client.entity.Event;
import com.o2hlink.activ8.client.response.VoidResponse;

/**
 * Remove an event from the system
 * @author Miguel Angel Hernandez
 **/
public class EventRemoveAction implements Action<VoidResponse> {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = -6881430327852320905L;
	/**
	 * 
	 **/
	private Event event;
//CONSTRUCTOR
	/**
	 * 
	 **/
	public EventRemoveAction(){
		
	}
	/**
	 * 
	 **/
	public EventRemoveAction(Event event){
		setEvent(event);
	}
//METHODS
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
