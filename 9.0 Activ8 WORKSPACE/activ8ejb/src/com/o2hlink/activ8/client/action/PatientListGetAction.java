/**
 * 
 */
package com.o2hlink.activ8.client.action;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;

import com.o2hlink.activ8.client.entity.Dataset;
import com.o2hlink.activ8.client.entity.HasPatients;
import com.o2hlink.activ8.client.response.PatientListResponse;

/**
 * Get all patients linked to a particular dataset.
 * @author Miguel Angel Hernandez
 **/
public class PatientListGetAction implements Action<PatientListResponse> {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = -6331259324602575110L;
	/**
	 * 
	 **/
	private HasPatients provider;
//CONSTRUCTOR
	/**
	 * Empty constructor to allow serialization
	 **/
	public PatientListGetAction(){
		
	}
	/**
	 * 
	 **/
	public PatientListGetAction(HasPatients provider){
		setProvider(provider);
	}
//METHODS
	/**
	 * @param provider the provider to set
	 */
	public void setProvider(HasPatients provider) {
		this.provider = provider;
	}
	/**
	 * @return the provider
	 */
	@XmlElements({
		@XmlElement(name="dataset", type=Dataset.class)
	})
	public HasPatients getProvider() {
		return provider;
	}
}

