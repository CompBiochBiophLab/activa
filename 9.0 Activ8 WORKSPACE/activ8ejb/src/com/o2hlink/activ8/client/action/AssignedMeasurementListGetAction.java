/**
 * 
 */
package com.o2hlink.activ8.client.action;

import com.o2hlink.activ8.client.entity.Patient;
import com.o2hlink.activ8.client.response.AssignedMeasurementListResponse;

/**
 * Get the list of assigned measurements to a patient
 * @author Miguel Angel Hernandez
 **/
public class AssignedMeasurementListGetAction implements Action<AssignedMeasurementListResponse>, Cacheable {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = -5395615225102299346L;
	/**
	 * 
	 **/
	private Patient patient;
//CONSTRUCTOR
	/**
	 * Empty constructor to allow serialization
	 **/
	public AssignedMeasurementListGetAction(){
		
	}
	/**
	 * 
	 **/
	public AssignedMeasurementListGetAction(Patient patient){
		this.setPatient(patient);
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
		if (obj instanceof AssignedMeasurementListGetAction){
			AssignedMeasurementListGetAction id = (AssignedMeasurementListGetAction)obj;
			return getPatient().equals(id.getPatient());
		}
		return false;
	}
	/**
	 * 
	 **/
	@Override
	public int hashCode() {
		return getPatient().hashCode();
	}
}
