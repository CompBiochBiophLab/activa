package com.o2hlink.activ8.client.display;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.HasValue;

/**
 * Particular account patient options
 * @author Miguel Angel Hernandez
 **/
public interface AccountPatientDisplay extends AccountProfileDisplay {
//METHODS
	/**
	 * 
	 **/
	public HasValue<String> getBluetoothAddress();
	/**
	 * 
	 **/
	public HasClickHandlers getUpdate();
}
