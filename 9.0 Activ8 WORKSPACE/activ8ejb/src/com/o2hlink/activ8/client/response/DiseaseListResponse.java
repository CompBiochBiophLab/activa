/**
 * 
 */
package com.o2hlink.activ8.client.response;

import java.util.List;

import com.o2hlink.activ8.client.entity.Disease;

/**
 * Return a list of datasets
 * @author Miguel Angel Hernandez
 **/
public class DiseaseListResponse implements Response {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = 4908513348662132627L;
	/**
	 * 
	 **/
	private List<Disease> diseases;
//CONSTRUCTOR
	/**
	 * Empty constructor to allow serialization
	 **/
	public DiseaseListResponse(){
		
	}
	/**
	 * 
	 **/
	public DiseaseListResponse(List<Disease> diseases){
		setDiseases(diseases);
	}
//METHODS
	/**
	 * @param diseases the diseases to set
	 */
	public void setDiseases(List<Disease> diseases) {
		this.diseases = diseases;
	}
	/**
	 * @return the diseases
	 */
	public List<Disease> getDiseases() {
		return diseases;
	}
}
