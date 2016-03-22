package com.o2hlink.activ8.client.display;

import com.google.gwt.event.dom.client.HasClickHandlers;

/**
 * @author Miguel Angel Hernandez
 **/
public interface ChromosomeModelDisplay extends BiologicalModelDisplay, HasClickHandlers {
//METHODS
	/**
	 * Get the last band clicked
	 **/
	public String getBand();
	/**
	 * 
	 **/
	public void setBand(ChromosomeBandDisplay display);
}
