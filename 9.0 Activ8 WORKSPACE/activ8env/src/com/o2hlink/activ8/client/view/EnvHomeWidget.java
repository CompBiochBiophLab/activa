package com.o2hlink.activ8.client.view;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Widget;
import com.o2hlink.activ8.client.display.Display;
import com.o2hlink.activ8.client.display.EnvHomeDisplay;

/**
 * @author Miguel Angel Hernandez
 **/
public class EnvHomeWidget extends BaseWidget implements EnvHomeDisplay {
//FIELDS
	/**
	 * 
	 **/
	private AbsolutePanel container = new AbsolutePanel();
//CONSTRUCTOR
	/**
	 * 
	 **/
	public EnvHomeWidget(){
		container.setSize(Window.getClientWidth()+"px", (Window.getClientHeight()-MainBarWidget.getHeight())+"px");
		container.getElement().getStyle().setZIndex(0);
		initWidget(container);		
	}
//METHODS
	/**
	 * 
	 **/
	@Override
	public void add(Display display) {
		container.add((Widget) display, 0, 0);
	}
	/**
	 * 
	 **/	
	@Override
	public void clear() {
		container.clear();
	}
	/**
	 * 
	 **/
	@Override
	public void remove(Display display) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public String getTheme() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setTheme(String uri) {
		// TODO Auto-generated method stub
		
	}
}
