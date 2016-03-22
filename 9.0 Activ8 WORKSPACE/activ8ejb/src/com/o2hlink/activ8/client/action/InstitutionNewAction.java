package com.o2hlink.activ8.client.action;

import com.o2hlink.activ8.client.entity.Institution;
import com.o2hlink.activ8.client.response.InstitutionResponse;

/**
 * Action to create a new institution
 * @author Miguel Angel Hernandez
 **/
public class InstitutionNewAction implements Action<InstitutionResponse> {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = -5002076752849484917L;
	/**
	 * 
	 **/
	private Institution institution;
//CONSTRUCTOR
	/**
	 * 
	 **/
	public InstitutionNewAction(){
		
	}
	/**
	 * 
	 **/
	public InstitutionNewAction(Institution institution){
		setInstitution(institution);
	}
//METHODS
	/**
	 * @param institution the institution to set
	 */
	public void setInstitution(Institution institution) {
		this.institution = institution;
	}
	/**
	 * @return the institution
	 */
	public Institution getInstitution() {
		return institution;
	}
}
