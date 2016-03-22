package com.o2hlink.activ8.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RootPanel;
import com.o2hlink.activ8.client.injection.Factory;
import com.o2hlink.activ8.client.presenter.ApplicationPresenter;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Activ8lob implements EntryPoint {
	/**
	 * 
	 **/
	private final Factory factory = GWT.create(Factory.class);
	/**
	 * 
	 **/
	public void onModuleLoad() {
		ApplicationPresenter presenter = factory.getApplication();
		presenter.setPanel(RootPanel.get("content"));
		presenter.bind();
	}
}
