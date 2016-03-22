package com.o2hlink.activ8.client.display;

import com.google.gwt.event.dom.client.HasClickHandlers;

/**
 * Displays a cell membrane with multiple proteins
 * @author Miguel Angel Hernandez
 **/
public interface CellMembraneModelDisplay extends BiologicalModelDisplay, ContainerDisplay<ProteinDisplay> {
	/**
	 * 
	 **/
	public HasClickHandlers getProtein();
}
