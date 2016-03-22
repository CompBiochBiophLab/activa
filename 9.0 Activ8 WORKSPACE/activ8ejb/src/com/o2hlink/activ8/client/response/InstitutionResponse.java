package com.o2hlink.activ8.client.response;

import com.o2hlink.activ8.client.entity.Institution;

/**
 * @author Miguel Angel Hernandez
 **/
public class InstitutionResponse implements Response {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = -4068067917572644424L;
	/**
	 * 
	 **/
	private Institution institution;
//CONSTRUCTOR
	/**
	 * 
	 **/
	public InstitutionResponse(){
		
	}
	/**
	 * 
	 **/
	public InstitutionResponse(Institution institution){
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
