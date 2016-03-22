package com.o2hlink.activ8.client.display;

/**
 * A panel that enclose a search, model and others
 * @author Miguel Angel Hernandez
 **/
public interface BiologicalPanelDisplay extends Display {
	/**
	 * 
	 **/
	public void setSearch(BiologicalSearchListDisplay display);
	/**
	 * 
	 **/
	public void setActions(BiologicalModelActionSetDisplay display);
	/**
	 * 
	 **/
	public void setView(BiologicalModelDisplay display);
}
