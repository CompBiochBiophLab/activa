package com.o2hlink.activ8.client.action;

import com.o2hlink.activ8.client.entity.Patient;
import com.o2hlink.activ8.client.response.HistoryRecordListResponse;

/**
 * Retrieves the clinical history of a patient.
 * @author Miguel Angel Hernandez
 **/
public class HistoryGetAction implements Action<HistoryRecordListResponse>, Cacheable{
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = -2391685522110047862L;
	/**
	 * 
	 **/
	private Patient patient;
//CONSTRUCTOR
	/**
	 * 
	 **/
	public HistoryGetAction(){
		
	}
	/**
	 * 
	 **/
	public HistoryGetAction(Patient patient){
		setPatient(patient);
	}
//METHODS
	/**
	 * @param patient the patient to set
	 */
	public void setPatient(Patient patient) {
		this.patient = patient;
	}
	/**
	 * @return the patient
	 */
	public Patient getPatient() {
		return patient;
	}
	/**
	 * 
	 **/
	public long getMaxAge() {
		return 60000;
	}
	/**
	 * 
	 **/
	@Override
	public boolean equals(Object obj){
		if (obj instanceof HistoryGetAction){
			HistoryGetAction id = (HistoryGetAction)obj;
			return getPatient().equals(id.getPatient());
		}
		return false;
	}
	/**
	 * 
	 **/
	@Override
	public int hashCode(){
		return getPatient().hashCode();
	}
}
