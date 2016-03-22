package com.o2hlink.activ8.client.entity;

import java.io.Serializable;
import java.util.List;

/**
 * An assigned measurement to a patient
 * @author Miguel Angel Hernandez
 **/
public class AssignedMeasurement implements Serializable {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = 8211269908111725851L;
	/**
	 * 
	 **/
	private Measurement measurement;
	/**
	 * 
	 **/
	private List<Event> events;
//CONSTRUCTOR
	/**
	 * 
	 **/
	public AssignedMeasurement(){
		
	}
//FIELDS
	/**
	 * @param measurement the measurement to set
	 */
	public void setMeasurement(Measurement measurement) {
		this.measurement = measurement;
	}
	/**
	 * @return the measurement
	 */
	public Measurement getMeasurement() {
		return measurement;
	}
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
