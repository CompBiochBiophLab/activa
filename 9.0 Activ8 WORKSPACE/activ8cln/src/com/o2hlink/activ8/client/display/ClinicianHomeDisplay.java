package com.o2hlink.activ8.client.display;

import com.google.gwt.event.dom.client.HasClickHandlers;

/**
 * Displays the home of a clinician
 * @author Miguel Angel Hernandez
 **/
public interface ClinicianHomeDisplay extends HomeDisplay {
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
	public HasClickHandlers getPapers();
	/**
	 * 
	 **/
	public HasClickHandlers getPatients();
	/**
	 * 
	 **/
	public HasClickHandlers getProjects();
	/**
	 * 
	 **/
	public HasClickHandlers getFunding();
	/**
	 * 
	 **/
	public HasClickHandlers getShopcart();
	/**
	 * 
	 **/
	public HasClickHandlers getDatasets();
	/**
	 * 
	 **/
	public HasClickHandlers createStudyGroupAccess();
}
