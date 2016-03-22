package com.o2hlink.activ8.client.display;

import com.google.gwt.event.dom.client.HasClickHandlers;

/**
 * @author Miguel Angel Hernandez
 **/
public interface AvatarModelDisplay extends BiologicalModelDisplay {
	/**
	 * 
	 **/
	public HasClickHandlers getHead();
	/**
	 * 
	 **/
	public HasClickHandlers getArms();
	/**
	 * 
	 **/
	public HasClickHandlers getLegs();
	/**
	 * 
	 **/
	public HasClickHandlers getMouth();
	/**
	 * 
	 **/
	public HasClickHandlers getBack();
	/**
	 * 
	 **/
	public HasClickHandlers getTorax();
	/**
	 * 
	 **/
	public HasClickHandlers getAbdomen();
	/**
	 * 
	 **/
	public HasClickHandlers getEarNoseThroat();
	/**
	 * 
	 **/
	public HasClickHandlers getGroins();
	/**
	 * 
	 **/
	public void setOrgans(BiologicalPanelDisplay display);
}
