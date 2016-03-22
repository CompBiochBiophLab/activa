/**
 * 
 */
package com.o2hlink.activ8.client.action;

import com.o2hlink.activ8.client.entity.Event;
import com.o2hlink.activ8.client.entity.Measurement;
import com.o2hlink.activ8.client.entity.Patient;
import com.o2hlink.activ8.client.response.AssignedMeasurementResponse;

/**
 * Add a measurement to a given patient. Both patient and measurement exists.
 * @author Miguel Angel Hernandez
 **/
public class AssignedMeasurementNewAction implements Action<AssignedMeasurementResponse> {
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
	/**
	 * 
	 **/
	private Event event;
//CONSTRUCTOR
	/**
	 * 
	 **/
	public AssignedMeasurementNewAction(){
		
	}
	/**
	 * 
	 **/
	public AssignedMeasurementNewAction(Patient patient, Measurement measurement, Event event){
		setPatient(patient);
		setMeasurement(measurement);
		setEvent(event);
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
	/**
	 * @param event the event to set
	 */
	public void setEvent(Event event) {
		this.event = event;
	}
	/**
	 * @return the event
	 */
	public Event getEvent() {
		return event;
	}
}
