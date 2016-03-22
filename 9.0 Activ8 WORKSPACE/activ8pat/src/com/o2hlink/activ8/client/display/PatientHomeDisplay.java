package com.o2hlink.activ8.client.display;

import com.google.gwt.event.dom.client.HasClickHandlers;

/**
 * Displays the home of the patient
 * @author Miguel Angel Hernandez
 **/
public interface PatientHomeDisplay extends HomeDisplay {
	/**
	 * 
	 **/
	public HasClickHandlers getGlobe();
	/**
	 * 
	 **/
	public HasClickHandlers getCalendar();
	/**
	 * 
	 **/
	public HasClickHandlers getPhone();
	/**
	 * 
	 **/
	public HasClickHandlers getFeeds();
	/**
	 * 
	 **/
	public HasClickHandlers getEmail();
	/**
	 * 
	 **/
	public HasClickHandlers getNotes();
	/**
	 * 
	 **/
	public HasClickHandlers getMobilePhone();
	/**
	 * 
	 **/
	public HasClickHandlers getAssignedMeasurements();
	/**
	 * 
	 **/
	public HasClickHandlers getHistory();
}
