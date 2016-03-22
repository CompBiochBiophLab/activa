/**
 * 
 */
package com.o2hlink.activ8.client.action;

import com.o2hlink.activ8.client.entity.Measurement;
import com.o2hlink.activ8.client.entity.Patient;
import com.o2hlink.activ8.client.response.SampleListResponse;

/**
 * Get a list of samples of a particular measurement.
 * @author Miguel Angel Hernandez
 **/
public class PatientSamplesGetAction implements Action<SampleListResponse> {
//FIELDS
	/**
	 * 
	 **/
	private static final long serialVersionUID = -4172935236466224483L;
	/**
	 * 
	 **/
	private Patient patient;
	/**
	 * 
	 **/
	private Measurement measurement;
//CONSTRUCTOR
	/**
	 * Empty constructor to allow serialization
	 **/
	public PatientSamplesGetAction(){
		
	}
	/**
	 * 
	 **/
	public PatientSamplesGetAction(Patient patient, Measurement measurement){
		this.setPatient(patient);
		this.setMeasurement(measurement);
	}
//METHODS
	/**
	 * @param measurement the measurement to set
	 */
	public void setMeasurement(Measurement measurement) {
		this.measurement = measurement;
	}
	/**
	 * @return the measurement
	 */
	public Measurement getMeasurement() {
		return measurement;
	}
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
