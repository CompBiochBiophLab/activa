/**
 * 
 */
package com.o2hlink.activ8.client.response;

import com.o2hlink.activ8.client.entity.Measurement;

/**
 * Return a measurement
 * @author Miguel Angel Hernandez
 **/
public class MeasurementResponse implements Response {
//FIELDS
	/**
	 * 
	 **/
	private static final long serialVersionUID = 8824576135239118004L;
	/**
	 * 
	 **/
	private Measurement measurement;
//CONSTRUCTOR
	/**
	 * Empty constructor to allow serialization
	 **/
	public MeasurementResponse(){
		
	}
	/**
	 * 
	 **/
	public MeasurementResponse(Measurement measurement){
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
}
