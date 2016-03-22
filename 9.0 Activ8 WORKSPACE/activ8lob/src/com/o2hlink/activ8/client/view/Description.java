package com.o2hlink.activ8.client.view;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.o2hlink.activ8.client.display.Display;
import com.o2hlink.activ8.client.i18n.Constants;
import com.o2hlink.activ8.client.resources.WebResources;
import com.o2hlink.activ8.client.view.BaseWidget;

/**
 * 
 **/
public class Description extends BaseWidget implements Display{
//FIELDS
	/**
	 * 
	 **/
	private Button new_account = new Button(Constants.instance.account_new());
//CONSTRUCTOR
	/**
	 * 
	 **/
	public Description(final String name, final String description, final String url){
		VerticalPanel panel = new VerticalPanel();
		Label title = new Label(name);
		title.addStyleName(WebResources.instance.css().labelBold());
		title.addStyleName(WebResources.instance.css().borderBottom());
		panel.add(title);
		Label label = new Label(description);
		label.addStyleName(WebResources.instance.css().label());
		panel.add(label);
		HorizontalPanel actions = new HorizontalPanel();
		Button button = new Button(Constants.instance.login());
		button.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event) {
				Window.Location.assign(url);
			}
		});
		actions.add(button);
		actions.add(new_account);
		panel.add(actions);
		initWidget(panel);
	}
//METHODS
	/**
	 * 
	 **/
	public HasClickHandlers getAccountNew(){
		return new_account;
	}
}
