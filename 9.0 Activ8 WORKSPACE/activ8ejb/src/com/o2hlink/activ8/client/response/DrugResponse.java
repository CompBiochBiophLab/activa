/**
 * 
 */
package com.o2hlink.activ8.client.response;

import com.o2hlink.activ8.client.entity.Drug;

/**
 * Return a list of datasets
 * @author Miguel Angel Hernandez
 **/
public class DrugResponse implements Response {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = 4908513348662132627L;
	/**
	 * 
	 **/
	private Drug drug;
//CONSTRUCTOR
	/**
	 * Empty constructor to allow serialization
	 **/
	public DrugResponse(){
		
	}
	/**
	 * 
	 **/
	public DrugResponse(Drug drug){
		setDrug(drug);
	}
//METHODS
	/**
	 * @param drugs the drugs to set
	 */
	public void setDrug(Drug drug) {
		this.drug = drug;
	}
	/**
	 * @return the drugs
	 */
	public Drug getDrug() {
		return drug;
	}
}
