package com.o2hlink.activ8.client.response;

import java.util.List;

import com.o2hlink.activ8.client.entity.Institution;

/**
 * @author Miguel Angel Hernandez
 **/
public class InstitutionListResponse implements Response {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = -3975923970201333715L;
	/**
	 * 
	 **/
	private List<Institution> institutions;
//CONSTRUCTOR
	/**
	 * 
	 **/
	public InstitutionListResponse(){
		
	}
	/**
	 * 
	 **/
	public InstitutionListResponse(List<Institution> institutions){
		setInstitutions(institutions);
	}
//METHODS
	/**
	 * @param institutions the institutions to set
	 */
	public void setInstitutions(List<Institution> institutions) {
		this.institutions = institutions;
	}
	/**
	 * @return the institutions
	 */
	public List<Institution> getInstitutions() {
		return institutions;
	}
}
