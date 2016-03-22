/**
 * 
 */
package com.o2hlink.activ8.client.response;

import com.o2hlink.activ8.client.entity.AssignedMeasurement;

/**
 * Return a list of measurements
 * @author Miguel Angel Hernandez
 **/
public class AssignedMeasurementResponse implements Response {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = 4059609267572900979L;
	/**
	 * 
	 **/
	private AssignedMeasurement measurement;
//CONSTRUCTOR
	/**
	 * Empty constructor to allow serialization
	 **/
	public AssignedMeasurementResponse(){
		
	}
	/**
	 * 
	 **/
	public AssignedMeasurementResponse(AssignedMeasurement measurement){
		setMeasurement(measurement);
	}
//METHODS
	/**
	 * @param measurement the measurement to set
	 */
	public void setMeasurement(AssignedMeasurement measurement) {
		this.measurement = measurement;
	}
	/**
	 * @return the measurement
	 */
	public AssignedMeasurement getMeasurement() {
		return measurement;
	}
}
