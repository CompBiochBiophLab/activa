package com.o2hlink.activ8.client.display;

import com.google.gwt.event.dom.client.HasClickHandlers;

/**
 * @author Miguel Angel Hernandez
 **/
public interface OrgansModelDisplay extends BiologicalModelDisplay {
	/**
	 * 
	 **/
	public HasClickHandlers getLung();
	/**
	 * 
	 **/
	public HasClickHandlers getHeart();
	/**
	 * 
	 **/
	public HasClickHandlers getStomach();
	/**
	 * 
	 **/
	public HasClickHandlers getSmallIntestine();
	/**
	 * 
	 **/
	public HasClickHandlers getLargeIntestine();
	/**
	 * 
	 **/
	public HasClickHandlers getPancreas();
	/**
	 * 
	 **/
	public HasClickHandlers getLiver();
	/**
	 * 
	 **/
	public void setLung(BiologicalPanelDisplay display);
}
