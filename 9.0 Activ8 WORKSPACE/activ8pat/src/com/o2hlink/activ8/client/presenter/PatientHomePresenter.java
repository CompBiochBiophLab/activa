package com.o2hlink.activ8.client.presenter;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.o2hlink.activ8.client.display.PatientHomeDisplay;
import com.o2hlink.activ8.client.entity.Patient;
import com.o2hlink.activ8.client.view.VideoWidget;

/**
 * @author Miguel Angel Hernandez
 **/
public class PatientHomePresenter extends HomePresenter {
//FIELDS
	/**
	 * 
	 **/
	@Inject
	private Provider<FeedListPresenter> feedsProvider;
	/**
	 * 
	 **/
	@Inject
	private Provider<PhonePresenter> phoneProvider;
	/**
	 * 
	 **/
	@Inject
	private Provider<SiteListPresenter> mapProvider;
	/**
	 * 
	 **/
	@Inject
	private Provider<MessageListPresenter> emailProvider;
	/**
	 * 
	 **/
	@Inject
	private Provider<CalendarPresenter> calendarProvider;
	/**
	 * 
	 **/
	@Inject
	private Provider<CommentListPresenter> notesProvider;
	/**
	 * 
	 **/
	@Inject
	private Provider<HistoryReaderPresenter> historyProvider;
	/**
	 * 
	 **/
	@Inject
	private Provider<AssignedMeasurementListReadPresenter> measurementsProvider;
//METHODS
	/**
	 * 
	 **/
	@Override
	public void bind(){
		super.bind();
		final PatientHomeDisplay display = (PatientHomeDisplay) getDisplay();
		display.getCalendar().addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event) {
				CalendarPresenter presenter = getCalendarProvider().get();
				presenter.bind();
				getDisplay().add(presenter.getDisplay());	
			}
		});
		display.getFeeds().addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event) {
				FeedListPresenter presenter = getFeedsProvider().get();
				presenter.bind();
				getDisplay().add(presenter.getDisplay());	
			}
		});
		display.getGlobe().addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event) {
				SiteListPresenter presenter = getMapProvider().get();
				presenter.bind();
				getDisplay().add(presenter.getDisplay());
			}
		});
		display.getPhone().addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event) {
				PhonePresenter presenter = getPhoneProvider().get();
				presenter.bind();					
				getDisplay().add(presenter.getDisplay());				
			}
		});
		display.getEmail().addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event) {
				MessageListPresenter presenter = getEmailProvider().get();
				presenter.bind();					
				getDisplay().add(presenter.getDisplay());
			}
		});
		display.getNotes().addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event) {
				CommentListPresenter presenter = getNotesProvider().get();
				presenter.bind();
				getDisplay().add(presenter.getDisplay());
			}
		});		
		display.getAssignedMeasurements().addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				AssignedMeasurementListReadPresenter presenter = getMeasurementsProvider().get();
				presenter.setPatient((Patient) getApplication().getUser());
				presenter.bind();
				display.add(presenter.getDisplay());
			}
		});
		display.getMobilePhone().addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				display.add(new VideoWidget("<object width=\"480\" height=\"385\"><param name=\"movie\" value=\"http://www.youtube.com/v/lNCdLdfZzaQ&hl=es_MX&fs=1&\"></param><param name=\"allowFullScreen\" value=\"true\"></param><param name=\"allowscriptaccess\" value=\"always\"></param><embed src=\"http://www.youtube.com/v/lNCdLdfZzaQ&hl=es_MX&fs=1&\" type=\"application/x-shockwave-flash\" allowscriptaccess=\"always\" allowfullscreen=\"true\" width=\"480\" height=\"385\"></embed></object>"));
			}
		});
		display.getHistory().addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				HistoryReaderPresenter presenter = getHistoryProvider().get();
				presenter.setPatient((Patient) getApplication().getUser());
				presenter.bind();
				display.add(presenter.getDisplay());
			}
		});
	}
	/**
	 * @return the measurementsProvider
	 */
	public Provider<AssignedMeasurementListReadPresenter> getMeasurementsProvider() {
		return measurementsProvider;
	}
	/**
	 * @return the historyProvider
	 */
	public Provider<HistoryReaderPresenter> getHistoryProvider() {
		return historyProvider;
	}
	/**
	 * @return the calendarProvider
	 */
	public Provider<CalendarPresenter> getCalendarProvider() {
		return calendarProvider;
	}
	/**
	 * @return the feedsProvider
	 */
	public Provider<FeedListPresenter> getFeedsProvider() {
		return feedsProvider;
	}
	/**
	 * @return the phoneProvider
	 */
	public Provider<PhonePresenter> getPhoneProvider() {
		return phoneProvider;
	}
	/**
	 * @return the emailProvider
	 */
	public Provider<MessageListPresenter> getEmailProvider() {
		return emailProvider;
	}
	/**
	 * @return the mapProvider
	 */
	public Provider<SiteListPresenter> getMapProvider() {
		return mapProvider;
	}
	/**
	 * @return the notesProvider
	 */
	public Provider<CommentListPresenter> getNotesProvider() {
		return notesProvider;
	}
}
