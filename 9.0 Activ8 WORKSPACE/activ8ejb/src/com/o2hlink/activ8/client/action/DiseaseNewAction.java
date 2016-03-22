package com.o2hlink.activ8.client.action;

import com.o2hlink.activ8.client.entity.Disease;
import com.o2hlink.activ8.client.response.DiseaseResponse;

/**
 * Administrative action. Creates a new disease.
 * @author Miguel Angel Hernandez
 **/
public class DiseaseNewAction implements Action<DiseaseResponse> {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = 1894317685615387397L;
	/**
	 * 
	 **/
	private Disease disease;
//CONSTRUCTOR
	/**
	 * 
	 **/
	public DiseaseNewAction(){
		
	}
	/**
	 * 
	 **/
	public DiseaseNewAction(Disease disease){
		setDisease(disease);
	}
//METHODS
	/**
	 * @param disease the disease to set
	 */
	public void setDisease(Disease disease) {
		this.disease = disease;
	}
	/**
	 * @return the disease
	 */
	public Disease getDisease() {
		return disease;
	}
}
