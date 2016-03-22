/**
 * 
 */
package com.o2hlink.activ8.client.action;

import com.o2hlink.activ8.client.entity.Measurement;
import com.o2hlink.activ8.client.entity.Patient;
import com.o2hlink.activ8.client.entity.Sample;
import com.o2hlink.activ8.client.response.SampleResponse;

/**
 * Create a medical sample. A medical sample may involve a particular measurement of a measure.
 * @author Miguel Angel Hernandez
 **/
public class SampleNewAction implements Action<SampleResponse> {
//FIELDS
	/**
	 * 
	 **/
	private static final long serialVersionUID = 1928625865614316202L;
	/**
	 * 
	 **/
	private Measurement measurement;
	/**
	 * 
	 **/
	private Patient patient;
	/**
	 * 
	 **/
	private Sample sample;
//CONSTRUCTOR
	/**
	 * Empty constructor to allow serialization
	 **/
	public SampleNewAction(){
		
	}
	/**
	 * 
	 **/
	public SampleNewAction(Sample sample, Measurement measurement, Patient patient){
		setSample(sample);
		setMeasurement(measurement);
		setPatient(patient);
	}
//METHODS
	/**
	 * @param sample the sample to set
	 */
	public void setSample(Sample sample) {
		this.sample = sample;
	}
	/**
	 * @return the sample
	 */
	public Sample getSample() {
		return sample;
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
