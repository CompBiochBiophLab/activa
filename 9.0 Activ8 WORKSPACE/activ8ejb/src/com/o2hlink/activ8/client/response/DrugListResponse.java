/**
 * 
 */
package com.o2hlink.activ8.client.response;

import java.util.List;

import com.o2hlink.activ8.client.entity.Drug;

/**
 * Return a list of datasets
 * @author Miguel Angel Hernandez
 **/
public class DrugListResponse implements Response {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = 4908513348662132627L;
	/**
	 * 
	 **/
	private List<Drug> drugs;
//CONSTRUCTOR
	/**
	 * Empty constructor to allow serialization
	 **/
	public DrugListResponse(){
		
	}
	/**
	 * 
	 **/
	public DrugListResponse(List<Drug> drugs){
		setDrugs(drugs);
	}
//METHODS
	/**
	 * @param drugs the drugs to set
	 */
	public void setDrugs(List<Drug> drugs) {
		this.drugs = drugs;
	}
	/**
	 * @return the drugs
	 */
	public List<Drug> getDrugs() {
		return drugs;
	}
}
