package com.o2hlink.activ8.client.display;

import com.google.gwt.event.dom.client.HasClickHandlers;

/**
 * @author Miguel Angel Hernandez
 **/
public interface BiologicalModelActionSetDisplay extends Display {
	/**
	 * 
	 **/
	public HasClickHandlers getSelectZone();
	/**
	 * 
	 **/
	public HasClickHandlers getViewZone();
	/**
	 * 
	 **/
	public HasClickHandlers getCenter();
	/**
	 * 
	 **/
	public HasClickHandlers getViewModels();
}
