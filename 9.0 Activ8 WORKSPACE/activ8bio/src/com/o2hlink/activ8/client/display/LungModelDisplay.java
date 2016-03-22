package com.o2hlink.activ8.client.display;

import com.google.gwt.event.dom.client.HasClickHandlers;

/**
 * @author Miguel Angel Hernandez
 **/
public interface LungModelDisplay extends BiologicalModelDisplay {
	/**
	 * 
	 **/
	public HasClickHandlers getAlveolus();
	/**
	 * 
	 **/
	public void setAlveolus(BiologicalPanelDisplay display);
}
