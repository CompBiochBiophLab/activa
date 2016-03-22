/**
 * 
 */
package com.o2hlink.activ8.client.response;

import java.util.List;

import com.o2hlink.activ8.client.entity.Measurement;

/**
 * Return a list of measurements
 * @author Miguel Angel Hernandez
 **/
public class MeasurementListResponse implements Response {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = 4059609267572900979L;
	/**
	 * 
	 **/
	private List<Measurement> measurements;
//CONSTRUCTOR
	/**
	 * Empty constructor to allow serialization
	 **/
	public MeasurementListResponse(){
		
	}
	/**
	 * 
	 **/
	public MeasurementListResponse(List<Measurement> measurements){
		this.setMeasurements(measurements);
	}
//METHODS
	/**
	 * @param measurements the measurements to set
	 */
	public void setMeasurements(List<Measurement> measurements) {
		this.measurements = measurements;
	}
	/**
	 * @return the measurements
	 */
	public List<Measurement> getMeasurements() {
		return measurements;
	}
}
