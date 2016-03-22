package com.o2hlink.activ8.client.display;

import com.google.gwt.event.dom.client.HasClickHandlers;

/**
 * @author Miguel Angel Hernandez
 **/
public interface BronchusModelDisplay extends BiologicalModelDisplay {
	/**
	 * 
	 **/
	public HasClickHandlers getCilias();
	/**
	 * 
	 **/
	public HasClickHandlers getMucus();
	/**
	 * 
	 **/
	public HasClickHandlers getCellularWall();
	/**
	 * 
	 **/
	public void setCell(CellModelDisplay display);
}
