package com.o2hlink.activ8.client.display;

import com.google.gwt.event.dom.client.HasClickHandlers;

/**
 * @author Miguel Angel Hernandez
 **/
public interface CellNucleusModelDisplay extends BiologicalModelDisplay {
	/**
	 * 
	 **/
	public HasClickHandlers getNucleous();
	/**
	 * 
	 **/
	public HasClickHandlers getNucleoli();
	/**
	 * 
	 **/
	public HasClickHandlers getNuclearEnvelope();
	/**
	 * 
	 **/
	public void setChromosomes(ChromosomeSetModelDisplay display);
}
