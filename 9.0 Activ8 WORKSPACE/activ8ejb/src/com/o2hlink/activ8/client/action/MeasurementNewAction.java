/**
 * 
 */
package com.o2hlink.activ8.client.action;

import com.o2hlink.activ8.client.entity.Measurement;
import com.o2hlink.activ8.client.response.MeasurementResponse;

/**
 * Create a new measurement. New measurements include only new questionnaires.
 * @author Miguel Angel Hernandez
 **/
public class MeasurementNewAction implements Action<MeasurementResponse> {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = 9052412169346338403L;
	/**
	 * 
	 **/
	private Measurement measurement;
//CONSTRUCTOR
	/**
	 * Empty constructor to allow serialization
	 **/
	public MeasurementNewAction(){
		
	}
	/**
	 * 
	 **/
	public MeasurementNewAction(Measurement measurement){
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
