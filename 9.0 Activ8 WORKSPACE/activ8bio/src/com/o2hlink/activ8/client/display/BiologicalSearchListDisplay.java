package com.o2hlink.activ8.client.display;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.HasValue;

/**
 * @author Miguel Angel Hernandez
 **/
public interface BiologicalSearchListDisplay extends AsyncDisplay {
	/**
	 * 
	 **/
	public HasValue<String> getQuery();
	/**
	 * 
	 **/
	public HasClickHandlers getSearch();
}
