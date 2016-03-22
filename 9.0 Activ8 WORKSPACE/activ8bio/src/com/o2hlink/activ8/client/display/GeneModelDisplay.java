package com.o2hlink.activ8.client.display;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HasText;

/**
 * @author Miguel Angel Hernandez
 **/
public interface GeneModelDisplay extends AsyncDisplay, BiologicalModelDisplay {
	/**
	 * 
	 **/
	public HasText getStart();
	/**
	 * 
	 **/
	public HasText getEnd();
	/**
	 * 
	 **/
	public void addExon(long start, long end, ClickHandler handler);
	/**
	 * 
	 **/
	public void setExon(ExonModelDisplay display);
}
