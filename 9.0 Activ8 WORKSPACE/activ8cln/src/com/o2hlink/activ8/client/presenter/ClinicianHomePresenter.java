package com.o2hlink.activ8.client.presenter;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.o2hlink.activ8.client.action.DatasetListGetAction;
import com.o2hlink.activ8.client.display.ClinicianHomeDisplay;
import com.o2hlink.activ8.client.entity.Clinician;
import com.o2hlink.activ8.client.entity.Dataset;
import com.o2hlink.activ8.client.event.DatasetNewEvent;
import com.o2hlink.activ8.client.event.DatasetRemoveEvent;
import com.o2hlink.activ8.client.event.LoadEvent;
import com.o2hlink.activ8.client.event.UnloadEvent;
import com.o2hlink.activ8.client.handler.DatasetNewHandler;
import com.o2hlink.activ8.client.handler.DatasetRemoveHandler;
import com.o2hlink.activ8.client.handler.LoadHandler;
import com.o2hlink.activ8.client.handler.UnloadHandler;
import com.o2hlink.activ8.client.response.DatasetListResponse;

/**
 * Presents the home of a clinician
 * @author Miguel Angel Hernandez
 **/
public class ClinicianHomePresenter extends HomePresenter implements DatasetNewHandler, DatasetRemoveHandler {
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
	private Provider<PaperListPresenter> papersProvider;
	/**
	 * 
	 **/
	@Inject
	private Provider<DatasetListPresenter> datasetsProvider;
	/**
	 * 
	 **/
	@Inject
	private Provider<DatasetPresenter> datasetProvider;
	/**
	 * 
	 **/
	@Inject
	private Provider<PatientListPresenter> patientsProvider;
	/**
	 * 
	 **/
	@Inject
	private Provider<ProjectListPresenter> projectsProvider;
//METHODS
	/**
	 * 
	 **/
	@Override
	public void bind(){
		super.bind();
		
		final ClinicianHomeDisplay display = (ClinicianHomeDisplay) getDisplay();
		
		display.getDatasets().addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event) {
				DatasetListPresenter presenter = getDatasetsProvider().get();
				presenter.bind();
				getDisplay().add(presenter.getDisplay());
			}
		});
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
		display.getPatients().addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				PatientListPresenter presenter = getPatientsProvider().get();
				presenter.setProvider(getApplication().getUser());
				presenter.bind();
				display.add(presenter.getDisplay());
			}
		});
		display.getPapers().addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				PaperListPresenter presenter = getPapersProvider().get();
				presenter.bind();
				display.add(presenter.getDisplay());
			}
		});
		display.getProjects().addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				ProjectListPresenter presenter = getProjectsProvider().get();
				presenter.bind();
				display.add(presenter.getDisplay());
			}			
		});
		getDisplay().addLoadHandler(new LoadHandler(){
			@Override
			public void onLoad(LoadEvent evt) {
				getService().dispatch(
					new DatasetListGetAction((Clinician)getApplication().getUser()), 
					new AsyncCallback<DatasetListResponse>(){
						@Override
						public void onFailure(Throwable caught) {					
						}
						@Override
						public void onSuccess(DatasetListResponse result) {
							for (final Dataset dataset:result.getDatasets())
								createDataset(dataset);
						}				
					});
			}
		});
		final ClinicianHomePresenter instance = this;
		getDisplay().addLoadHandler(new LoadHandler(){
			@Override
			public void onLoad(LoadEvent evt) {
				getApplication().getBus().addHandler(DatasetNewEvent.TYPE, instance);
				getApplication().getBus().addHandler(DatasetRemoveEvent.TYPE, instance);
			}
		});
		getDisplay().addUnloadHandler(new UnloadHandler(){
			@Override
			public void onUnload(UnloadEvent evt) {
				getApplication().getBus().removeHandler(DatasetNewEvent.TYPE, instance);
				getApplication().getBus().removeHandler(DatasetRemoveEvent.TYPE, instance);
			}
		});
	}
	/**
	 * @return the patientsProvider
	 */
	public Provider<PatientListPresenter> getPatientsProvider() {
		return patientsProvider;
	}
	/**
	 * @return the datasetProvider
	 */
	public Provider<DatasetPresenter> getDatasetProvider() {
		return datasetProvider;
	}
	/**
	 * @return the papersProvider
	 */
	public Provider<PaperListPresenter> getPapersProvider() {
		return papersProvider;
	}
	/**
	 * @return the projectsProvider
	 */
	public Provider<ProjectListPresenter> getProjectsProvider() {
		return projectsProvider;
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
	/**
	 * @return the datasetsProvider
	 */
	public Provider<DatasetListPresenter> getDatasetsProvider() {
		return datasetsProvider;
	}
	/**
	 * 
	 **/
	private void createDataset(final Dataset dataset){
		final ClinicianHomeDisplay display = (ClinicianHomeDisplay) getDisplay();
		
		display.createStudyGroupAccess().addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				DatasetPresenter presenter = getDatasetProvider().get();
				presenter.setDataset(dataset);
				presenter.bind();
				display.add(presenter.getDisplay()); 
			}
		});		
	}
	/**
	 * 
	 **/
	@Override
	public void onDatasetNew(DatasetNewEvent evt) {
		createDataset(evt.getDataset());
	}
	/**
	 * 
	 **/
	@Override
	public void onDatasetRemove(DatasetRemoveEvent evt) {
		// TODO Auto-generated method stub
	}
}
