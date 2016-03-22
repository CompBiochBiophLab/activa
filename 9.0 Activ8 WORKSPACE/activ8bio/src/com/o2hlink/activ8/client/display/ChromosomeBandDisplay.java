package com.o2hlink.activ8.client.display;

import com.google.gwt.user.client.ui.HasText;

/**
 * @author Miguel Angel Hernandez
 **/
public interface ChromosomeBandDisplay extends Display {
	/**
	 * 
	 **/
	public HasText getName();
	/**
	 * 
	 **/
	public void setHref(String url);
	/**
	 * 
	 **/
	public void add(GeneSearchItemDisplay display);
}
