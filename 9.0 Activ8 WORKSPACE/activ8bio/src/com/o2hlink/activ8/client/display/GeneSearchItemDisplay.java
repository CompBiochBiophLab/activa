package com.o2hlink.activ8.client.display;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.HasText;

/**
 * @author Miguel Angel Hernandez
 **/
public interface GeneSearchItemDisplay extends AsyncDisplay {
	/**
	 * 
	 **/
	public HasText getName();
	/**
	 * 
	 **/
	public HasClickHandlers getView();
	/**
	 * 
	 **/
	public void setGene(BiologicalPanelDisplay display);
}
