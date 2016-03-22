/**
 * 
 */
package com.o2hlink.activ8.client.response;

import com.o2hlink.activ8.client.entity.Patient;

/**
 * Return a list of patients
 * @author Miguel Angel Hernandez
 **/
public class PatientResponse implements Response {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = 5018595146350225584L;
	/**
	 * 
	 **/
	private Patient patient;
//CONSTRUCTOR
	/**
	 * Empty constructor to allow serialization
	 **/
	public PatientResponse(){
		
	}
	/**
	 * 
	 **/
	public PatientResponse(Patient patient){
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
}
