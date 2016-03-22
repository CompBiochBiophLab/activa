package com.o2hlink.activ8.client.response;

import com.o2hlink.activ8.client.entity.Disease;

/**
 * Return a single disease.
 * @author Miguel Angel Hernandez
 **/
public class DiseaseResponse implements Response {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = -541952059903520729L;
	/**
	 * 
	 **/
	private Disease disease;
//CONSTRUCTOR
	/**
	 * 
	 **/
	public DiseaseResponse(){
		
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
