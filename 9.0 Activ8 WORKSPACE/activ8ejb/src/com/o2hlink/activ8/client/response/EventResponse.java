package com.o2hlink.activ8.client.response;

import com.o2hlink.activ8.client.entity.Event;

/**
 * Return an event
 * @author Miguel Angel Hernandez
 **/
public class EventResponse implements Response {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = -4373758708708621159L;
	/**
	 * 
	 **/
	private Event event;
//CONSTRUCTOR
	/**
	 * Empty constructor to allow serialization
	 **/
	public EventResponse(){
		
	}
	/**
	 * 
	 **/
	public EventResponse(Event event){
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
