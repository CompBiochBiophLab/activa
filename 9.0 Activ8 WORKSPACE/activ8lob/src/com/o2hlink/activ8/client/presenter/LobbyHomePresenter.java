package com.o2hlink.activ8.client.presenter;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window.Location;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.o2hlink.activ8.client.display.LobbyHomeDisplay;

/**
 * @author Miguel Angel Hernandez
 **/
public class LobbyHomePresenter extends HomePresenter {
//FIELDS
	/**
	 * 
	 **/
	@Inject
	private Provider<ConfigurationPresenter> configuration;
//METHODS
	/**
	 * 
	 **/
	@Override
	public void bind(){
		LobbyHomeDisplay display = (LobbyHomeDisplay) getDisplay();
		
		display.getPatientDoor().addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event) {
				Location.assign("../../patient");
			}
		});
		display.getClinicianDoor().addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event) {
				Location.assign("../../clinician");
			}
		});
		display.getResearcherDoor().addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event) {
				Location.assign("../../researcher");
			}
		});
		display.getPhone().addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event) {
				ConfigurationPresenter configuration = getConfiguration().get();
				configuration.showAccounts();
				configuration.bind();
				getDisplay().add(configuration.getDisplay());
			}
		});
		display.getFeeds().addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event) {
				ConfigurationPresenter presenter = getConfiguration().get();
				presenter.showFeeds();
				presenter.bind();
				getDisplay().add(presenter.getDisplay());
			}
		});
		display.getGlobe().addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event) {
				ConfigurationPresenter presenter = getConfiguration().get();
				presenter.bind();
				getDisplay().add(presenter.getDisplay());
			}
		});
	}
	/**
	 * @return the communicationsProvider
	 */
	public Provider<ConfigurationPresenter> getConfiguration() {
		return configuration;
	}
}
