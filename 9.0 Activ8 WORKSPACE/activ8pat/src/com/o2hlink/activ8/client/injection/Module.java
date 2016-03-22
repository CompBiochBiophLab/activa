package com.o2hlink.activ8.client.injection;

import com.google.gwt.inject.client.AbstractGinModule;
import com.o2hlink.activ8.client.display.AccountProfileDisplay;
import com.o2hlink.activ8.client.display.AssignedMeasurementReadCalendarDisplay;
import com.o2hlink.activ8.client.display.AssignedMeasurementReadDisplay;
import com.o2hlink.activ8.client.display.ConfigurationThemeDisplay;
import com.o2hlink.activ8.client.display.HistoryReaderDisplay;
import com.o2hlink.activ8.client.display.HistoryRecordItemDisplay;
import com.o2hlink.activ8.client.display.HistoryRecordReaderDisplay;
import com.o2hlink.activ8.client.display.HomeDisplay;
import com.o2hlink.activ8.client.display.MeasurementListReadDisplay;
import com.o2hlink.activ8.client.presenter.AccountPatientPresenter;
import com.o2hlink.activ8.client.presenter.AccountProfilePresenter;
import com.o2hlink.activ8.client.presenter.HomePresenter;
import com.o2hlink.activ8.client.presenter.PatientHomePresenter;
import com.o2hlink.activ8.client.view.AccountPatientWidget;
import com.o2hlink.activ8.client.view.AccountThemeWidget;
import com.o2hlink.activ8.client.view.AssignedMeasurementReadCalendarWidget;
import com.o2hlink.activ8.client.view.HistoryReaderWidget;
import com.o2hlink.activ8.client.view.HistoryRecordItemWidget;
import com.o2hlink.activ8.client.view.HistoryRecordReaderWidget;
import com.o2hlink.activ8.client.view.AssignedMeasurementListReadWidget;
import com.o2hlink.activ8.client.view.AssignedMeasurementReadWidget;
import com.o2hlink.activ8.client.view.PatientHomeWidget;

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

		bind(AccountProfileDisplay.class).to(AccountPatientWidget.class);
		bind(AccountProfilePresenter.class).to(AccountPatientPresenter.class);
		bind(HomePresenter.class).to(PatientHomePresenter.class);
		bind(HomeDisplay.class).to(PatientHomeWidget.class);
		bind(ConfigurationThemeDisplay.class).to(AccountThemeWidget.class);
		
		bind(HistoryReaderDisplay.class).to(HistoryReaderWidget.class);
		bind(HistoryRecordItemDisplay.class).to(HistoryRecordItemWidget.class);
		bind(HistoryRecordReaderDisplay.class).to(HistoryRecordReaderWidget.class);
		bind(MeasurementListReadDisplay.class).to(AssignedMeasurementListReadWidget.class);
		bind(AssignedMeasurementReadDisplay.class).to(AssignedMeasurementReadWidget.class);
		bind(AssignedMeasurementReadCalendarDisplay.class).to(AssignedMeasurementReadCalendarWidget.class);
	}

}
