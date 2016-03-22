/**
 * 
 */
package com.o2hlink.activ8.client.response;

import java.util.List;

import com.o2hlink.activ8.client.entity.Event;

/**
 * Return an event list
 * @author Miguel Angel Hernandez
 **/
public class EventListResponse implements Response {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = -8534080196704428387L;
	/**
	 * 
	 **/
	private List<Event> events;
//CONSTRUCTOR
	/**
	 * Empty constructor to allow serialization
	 */
	public EventListResponse(){
		
	}
	/**
	 * 
	 */
	public EventListResponse(List<Event> events){
		this.setEvents(events);
	}
//METHODS
	/**
	 * @param events the events to set
	 */
	public void setEvents(List<Event> events) {
		this.events = events;
	}
	/**
	 * @return the events
	 */
	public List<Event> getEvents() {
		return events;
	}
}
