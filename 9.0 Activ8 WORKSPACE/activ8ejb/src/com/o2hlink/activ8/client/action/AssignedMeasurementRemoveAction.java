/**
 * 
 */
package com.o2hlink.activ8.client.action;

import com.o2hlink.activ8.client.entity.Measurement;
import com.o2hlink.activ8.client.entity.Patient;
import com.o2hlink.activ8.client.response.VoidResponse;

/**
 * Remove a measurement to a given patient. Both patient and measurement exists.
 * @author Miguel Angel Hernandez
 **/
public class AssignedMeasurementRemoveAction implements Action<VoidResponse> {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = 5756879188582039958L;
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
	 * 
	 **/
	public AssignedMeasurementRemoveAction(){
		
	}
	/**
	 * 
	 **/
	public AssignedMeasurementRemoveAction(Patient patient, Measurement measurement){
		setPatient(patient);
		setMeasurement(measurement);
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
}
