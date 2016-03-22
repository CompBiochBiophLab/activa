package com.o2hlink.activ8.client.action;

import com.o2hlink.activ8.client.entity.Drug;
import com.o2hlink.activ8.client.response.DrugResponse;

/**
 * Administrative action. Creates a new drug.
 * @author Miguel Angel Hernandez
 **/
public class DrugNewAction implements Action<DrugResponse> {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = 1894317685615387397L;
	/**
	 * 
	 **/
	private Drug drug;
//CONSTRUCTOR
	/**
	 * 
	 **/
	public DrugNewAction(){
		
	}
	/**
	 * 
	 **/
	public DrugNewAction(Drug drug){
		setDrug(drug);
	}
//METHODS
	/**
	 * @param drug the drug to set
	 */
	public void setDrug(Drug drug) {
		this.drug = drug;
	}
	/**
	 * @return the drug
	 */
	public Drug getDrug() {
		return drug;
	}
}
