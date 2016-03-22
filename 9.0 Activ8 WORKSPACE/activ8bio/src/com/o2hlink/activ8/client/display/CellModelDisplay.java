package com.o2hlink.activ8.client.display;

import com.google.gwt.event.dom.client.HasClickHandlers;

/**
 * @author Miguel Angel Hernandez
 **/
public interface CellModelDisplay extends BiologicalModelDisplay {
	/**
	 * 
	 **/
	public HasClickHandlers getNucleoli();
	/**
	 * 
	 **/
	public HasClickHandlers getEndoplasmaticReticulum();
	/**
	 * 
	 **/
	public HasClickHandlers getGolgiComplex();
	/**
	 * 
	 **/
	public HasClickHandlers getLysosome();
	/**
	 * 
	 **/
	public HasClickHandlers getCytoskeleton();
	/**
	 * 
	 **/
	public HasClickHandlers getCytosol();
	/**
	 * 
	 **/
	public HasClickHandlers getPerixome();
	/**
	 * 
	 **/
	public HasClickHandlers getMitochondria();
	/**
	 * 
	 **/
	public HasClickHandlers getNuclearEnvelope();
	/**
	 * 
	 **/
	public HasClickHandlers getPlasmaMembrane();
	/**
	 * 
	 **/
	public void setPlasmaMembrane(BiologicalPanelDisplay display);
	/**
	 * 
	 **/
	public HasClickHandlers getNucleous();
	/**
	 * 
	 **/
	public void setNucleous(CellNucleusModelDisplay display);
}
