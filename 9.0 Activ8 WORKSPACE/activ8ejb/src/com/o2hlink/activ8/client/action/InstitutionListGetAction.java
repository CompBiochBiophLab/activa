package com.o2hlink.activ8.client.action;

import com.o2hlink.activ8.client.entity.Disease;
import com.o2hlink.activ8.client.response.InstitutionListResponse;

/**
 * Retrieve a list of institutions
 * @author Miguel Angel Hernandez
 **/
public class InstitutionListGetAction implements Action<InstitutionListResponse>, Cacheable {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = -4031782430115694849L;
	/**
	 * 
	 **/
	private Disease disease;
//CONSTRUCTOR
	/**
	 * 
	 **/
	public InstitutionListGetAction(){
		
	}
	/**
	 * 
	 **/
	public InstitutionListGetAction(Disease disease){
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
	/**
	 * 
	 **/
	public long getMaxAge() {
		return 60000;
	}
	/**
	 * 
	 **/
	@Override
	public boolean equals(Object obj){
		if (obj instanceof InstitutionListGetAction){
			//InstitutionListGetAction id = (InstitutionListGetAction)obj;
			return true;
		}
		return false;
	}
	/**
	 * 
	 **/
	@Override
	public int hashCode() {
		return 0;
	}
}
