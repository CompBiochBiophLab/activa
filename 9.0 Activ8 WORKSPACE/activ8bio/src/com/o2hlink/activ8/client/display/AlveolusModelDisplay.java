package com.o2hlink.activ8.client.display;

import com.google.gwt.event.dom.client.HasClickHandlers;

/**
 * @author Miguel Angel Hernandez
 **/
public interface AlveolusModelDisplay extends BiologicalModelDisplay {
	/**
	 * 
	 **/
	public HasClickHandlers getAlveolus();
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
	public void setOrgan(OrgansModelDisplay display);
	/**
	 * 
	 **/
	public void setBronchus(BiologicalPanelDisplay display);
}
