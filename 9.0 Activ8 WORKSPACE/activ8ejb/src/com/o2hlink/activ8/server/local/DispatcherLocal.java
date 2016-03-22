package com.o2hlink.activ8.server.local;

import javax.ejb.Local;

import com.o2hlink.activ8.client.action.Action;
import com.o2hlink.activ8.client.exception.ServerException;
import com.o2hlink.activ8.client.response.Response;
import com.o2hlink.activ8.server.remote.SearchRemote;

/**
 * Local operations with a dispatcher
 * @author Miguel Angel Hernandez
 **/
@Local
public interface DispatcherLocal {
	/**
	 * @param	action The action
	 * @return	The response
	 **/
	public <R extends Response> R dispatch(Action<R> action) throws ServerException;
	/**
	 * 
	 **/
	public CommentLocal getCommentSession();
	/**
	 * 
	 **/
	public MessageLocal getMessageSession();
	/**
	 * 
	 **/
	public CountryLocal getCountrySession();
	/**
	 * 
	 **/
	public DatasetLocal getDatasetSession();
	/**
	 * 
	 **/
	public EventLocal getEventSession();
	/**
	 * 
	 **/
	public GroupLocal getGroupSession();
	/**
	 * 
	 **/
	public LogLocal getLogSession();
	/**
	 * 
	 **/
	public MeasurementLocal getMeasurementSession();
	/**
	 * 
	 **/
	public PatientLocal getPatientSession();
	/**
	 * 
	 **/
	public PrivacyLocal getPrivacySession();
	/**
	 * 
	 **/
	public QuestionnaireLocal getQuestionnaireSession();
	/**
	 * 
	 **/
	public ResearcherLocal getResearcherSession();
	/**
	 * 
	 **/
	public SampleLocal getSampleSession();
	/**
	 * 
	 **/
	public SearchRemote getSearchSession();
	/**
	 * 
	 **/
	public UserLocal getUserSession();
	/**
	 * 
	 **/
	public InstitutionLocal getInstitutionSession();
	/**
	 * 
	 **/
	public DiseaseLocal getDiseaseSession();
	/**
	 * 
	 **/
	public ProjectLocal getProjectSession();
	/**
	 * 
	 **/
	public UniprotLocal getUniprotSession();
	/**
	 * 
	 **/
	public MobileLocal getMobileSession();
	/**
	 * 
	 **/
	public EntrezLocal getGeneSession();
	/**
	 * 
	 **/
	public FileLocal getFileSession();
}
