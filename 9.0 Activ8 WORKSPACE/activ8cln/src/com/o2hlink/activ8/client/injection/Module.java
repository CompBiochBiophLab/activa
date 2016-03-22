package com.o2hlink.activ8.client.injection;

import com.google.gwt.inject.client.AbstractGinModule;
import com.o2hlink.activ8.client.display.AccountProfileDisplay;
import com.o2hlink.activ8.client.display.ConfigurationThemeDisplay;
import com.o2hlink.activ8.client.display.HomeDisplay;
import com.o2hlink.activ8.client.presenter.ClinicianHomePresenter;
import com.o2hlink.activ8.client.presenter.HomePresenter;
import com.o2hlink.activ8.client.view.AccountProfileWidget;
import com.o2hlink.activ8.client.view.AccountThemeWidget;
import com.o2hlink.activ8.client.view.ClinicianHomeWidget;

/**
 * @author Miguel Angel Hernandez
 **/
public class Module extends AbstractGinModule {
	/**
	 * 
	 **/
	@Override
	protected void configure() {
		install(new WebModule());
		install(new ExperimentalDataModule());
		install(new RemoteCareModule());
		install(new ProjectModule());
		install(new PapersModule());
		
		bind(HomePresenter.class).to(ClinicianHomePresenter.class);
		bind(HomeDisplay.class).to(ClinicianHomeWidget.class);
		bind(AccountProfileDisplay.class).to(AccountProfileWidget.class);
		bind(ConfigurationThemeDisplay.class).to(AccountThemeWidget.class);
	}

}
