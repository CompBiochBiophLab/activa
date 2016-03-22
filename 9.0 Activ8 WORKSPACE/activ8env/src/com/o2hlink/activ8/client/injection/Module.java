package com.o2hlink.activ8.client.injection;

import com.google.gwt.inject.client.AbstractGinModule;
import com.o2hlink.activ8.client.display.AccountProfileDisplay;
import com.o2hlink.activ8.client.display.ConfigurationThemeDisplay;
import com.o2hlink.activ8.client.display.HomeDisplay;
import com.o2hlink.activ8.client.presenter.EnvHomePresenter;
import com.o2hlink.activ8.client.presenter.HomePresenter;
import com.o2hlink.activ8.client.view.AccountProfileWidget;
import com.o2hlink.activ8.client.view.AccountThemeWidget;
import com.o2hlink.activ8.client.view.EnvHomeWidget;

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
		install(new BiologicalModule());	
		
		bind(HomePresenter.class).to(EnvHomePresenter.class);
		bind(HomeDisplay.class).to(EnvHomeWidget.class);
		bind(AccountProfileDisplay.class).to(AccountProfileWidget.class);
		bind(ConfigurationThemeDisplay.class).to(AccountThemeWidget.class);
	}
}
