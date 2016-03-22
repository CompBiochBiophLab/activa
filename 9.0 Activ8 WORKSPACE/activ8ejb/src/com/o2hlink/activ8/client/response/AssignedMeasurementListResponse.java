/**
 * 
 */
package com.o2hlink.activ8.client.response;

import java.util.List;

import com.o2hlink.activ8.client.entity.AssignedMeasurement;

/**
 * Return a list of measurements
 * @author Miguel Angel Hernandez
 **/
public class AssignedMeasurementListResponse implements Response {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = 4059609267572900979L;
	/**
	 * 
	 **/
	private List<AssignedMeasurement> measurements;
//CONSTRUCTOR
	/**
	 * Empty constructor to allow serialization
	 **/
	public AssignedMeasurementListResponse(){
		
	}
	/**
	 * 
	 **/
	public AssignedMeasurementListResponse(List<AssignedMeasurement> measurements){
		setMeasurements(measurements);
	}
//METHODS
	/**
	 * @param measurements the measurements to set
	 */
	public void setMeasurements(List<AssignedMeasurement> measurements) {
		this.measurements = measurements;
	}
	/**
	 * @return the measurements
	 */
	public List<AssignedMeasurement> getMeasurements() {
		return measurements;
	}
}
