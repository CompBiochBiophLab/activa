package com.o2hlink.activ8.client.view;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.o2hlink.activ8.client.display.AccountPatientDisplay;
import com.o2hlink.activ8.client.i18n.Constants;

/**
 * @author Miguel Angel Hernandez
 **/
public class AccountPatientWidget extends BaseWidget implements AccountPatientDisplay {
//FIELDS
	/**
	 * 
	 **/
	private TextBox bluetoothAddress = new TextBox();
	/**
	 * 
	 **/
	private Button update = new Button(Constants.instance.update());
//CONSTRUCTOR
	/**
	 * 
	 **/
	public AccountPatientWidget(){
		Grid grid = new Grid(2, 2);
		grid.setWidget(0, 0, new Label("Bluetooth"));
		grid.setWidget(0, 1, bluetoothAddress);
		grid.setWidget(1, 1, update);
		initWidget(grid);
	}
//METHODS
	/**
	 * 
	 **/
	@Override
	public HasValue<String> getBluetoothAddress() {
		return bluetoothAddress;
	}
	/**
	 * 
	 **/
	@Override
	public HasClickHandlers getUpdate() {
		return update;
	}
	/**
	 * 
	 **/
	@Override
	public void notifyFailure(String message) {
		update.setEnabled(true);
	}
	/**
	 * 
	 **/
	@Override
	public void notifyLoading() {
		update.setEnabled(false);
	}
	/**
	 * 
	 **/
	@Override
	public void notifySuccess() {
		update.setEnabled(true);
	}
}
