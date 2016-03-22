/**
 * 
 */
package com.o2hlink.activ8.client.response;

import java.util.List;

import com.o2hlink.activ8.client.entity.Patient;

/**
 * Return a list of patients
 * @author Miguel Angel Hernandez
 **/
public class PatientListResponse implements Response {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = 5018595146350225584L;
	/**
	 * 
	 **/
	private List<Patient> patients;
//CONSTRUCTOR
	/**
	 * Empty constructor to allow serialization
	 **/
	public PatientListResponse(){
		
	}
	/**
	 * 
	 **/
	public PatientListResponse(List<Patient> patients){
		this.setPatients(patients);
	}
//METHODS
	/**
	 * @param patients the patients to set
	 */
	public void setPatients(List<Patient> patients) {
		this.patients = patients;
	}
	/**
	 * @return the patients
	 */
	public List<Patient> getPatients() {
		return patients;
	}
}
