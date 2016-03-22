package com.o2hlink.activ8.client.view;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.o2hlink.activ8.client.display.ConfigurationThemeDisplay;
import com.o2hlink.activ8.client.i18n.Constants;

/**
 * @author Miguel Angel Hernandez
 **/
public class AccountThemeWidget extends BaseWidget implements ConfigurationThemeDisplay {
//FIELDS
	/**
	 * 
	 **/
	private Button change = new Button(Constants.instance.change());
//CONSTRUCTOR
	/**
	 * 
	 **/
	public AccountThemeWidget(){
		initWidget(new Label("Cannot change your theme here"));
	}
//METHODS
	/**
	 * 
	 **/
	@Override
	public HasClickHandlers getChange() {
		return change;
	}
	/**
	 * 
	 **/
	@Override
	public void notifyFailure(String message) {
		change.setEnabled(true);
	}
	/**
	 * 
	 **/
	@Override
	public void notifyLoading() {
		change.setEnabled(false);
	}
	/**
	 * 
	 **/
	@Override
	public void notifySuccess() {
		change.setEnabled(true);
	}
	/**
	 * 
	 **/
	@Override
	public String getValue() {
		return "";
	}
	/**
	 * 
	 **/
	@Override
	public void setValue(String value) {
	}
	/**
	 * 
	 **/
	@Override
	public void setValue(String value, boolean fireEvents) {
	}
	/**
	 * 
	 **/
	@Override
	public HandlerRegistration addValueChangeHandler(ValueChangeHandler<String> handler) {
		return null;
	}
}
