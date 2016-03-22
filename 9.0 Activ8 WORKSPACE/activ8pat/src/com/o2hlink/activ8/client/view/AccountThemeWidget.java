package com.o2hlink.activ8.client.view;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.o2hlink.activ8.client.display.ConfigurationThemeDisplay;
import com.o2hlink.activ8.client.i18n.Constants;
import com.o2hlink.activ8.client.resources.WebResources;

/**
 * @author Miguel Angel Hernandez
 **/
public class AccountThemeWidget extends BaseWidget implements ConfigurationThemeDisplay {
//FIELDS
	/**
	 * 
	 **/
	private String url = "";
	/**
	 * 
	 **/
	private Button change = new Button(Constants.instance.change());
	/**
	 * 
	 **/
	private FocusPanel last = null;
//CONSTRUCTOR
	/**
	 * 
	 **/
	public AccountThemeWidget(){
		HorizontalPanel gallery = new HorizontalPanel();
		gallery.setWidth("100%");
		gallery.add(addScene("Standard", "models/paciente.o3dtgz", "images/paciente_thumbnail.png"));
		gallery.add(addScene("Rose", "models/paciente_joven_chica.o3dtgz", "images/paciente_mujer_joven_thumbnail.jpg"));
		gallery.add(addScene("Blue", "models/paciente_joven_chico.o3dtgz", "images/paciente_hombre_joven_thumbnail.jpg"));
				
		VerticalPanel container = new VerticalPanel();
		container.add(gallery);
		container.add(change);
		initWidget(container);
	}
//METHODS
	/**
	 * 
	 **/
	public Widget addScene(String name, final String model, String thumbnail){
		VerticalPanel panel = new VerticalPanel();
		panel.add(new Image(thumbnail));
		panel.add(new Label(name));
		
		final FocusPanel container = new FocusPanel();
		container.add(panel);
		container.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				if (last != null)
					last.removeStyleName(WebResources.instance.css().border());
				last = container;
				last.addStyleName(WebResources.instance.css().border());
				url = model;
			}
		});
		return container;
	}
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
		return url;
	}
	/**
	 * 
	 **/
	@Override
	public void setValue(String value) {
		url = value;
	}
	/**
	 * 
	 **/
	@Override
	public void setValue(String value, boolean fireEvents) {
		setValue(value);
	}
	/**
	 * 
	 **/
	@Override
	public HandlerRegistration addValueChangeHandler(ValueChangeHandler<String> handler) {
		// TODO Auto-generated method stub
		return null;
	}
}
