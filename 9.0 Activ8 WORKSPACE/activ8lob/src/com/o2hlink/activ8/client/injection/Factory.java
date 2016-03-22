package com.o2hlink.activ8.client.injection;

import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import com.o2hlink.activ8.client.presenter.ApplicationPresenter;

/**
 * @author Miguel Angel Hernandez
 **/
@GinModules(Module.class)
public interface Factory extends Ginjector {
	/**
	 * 
	 **/
	public ApplicationPresenter getApplication();
}
