package com.o2hlink.activ8.client.display;

import com.google.gwt.event.dom.client.HasClickHandlers;

/**
 * @author Miguel Angel Hernandez
 **/
public interface LobbyHomeDisplay extends HomeDisplay {
	/**
	 * 
	 **/
	public HasClickHandlers getGlobe();
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
	public HasClickHandlers getPatientDoor();
	/**
	 * 
	 **/
	public HasClickHandlers getClinicianDoor();
	/**
	 * 
	 **/
	public HasClickHandlers getResearcherDoor();
}
