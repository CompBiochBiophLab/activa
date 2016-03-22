/**
 * 
 */
package com.o2hlink.activ8.client.action;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;

import com.o2hlink.activ8.client.entity.Dataset;
import com.o2hlink.activ8.client.entity.HasPatients;
import com.o2hlink.activ8.client.entity.Patient;
import com.o2hlink.activ8.client.response.PatientResponse;

/**
 * Given a Dataset (a group of samples, subsets and patients), add a list of patients
 * @author Miguel Angel Hernandez
 **/
public class PatientNewAction implements Action<PatientResponse> {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = 1560145973355785733L;
	/**
	 * 
	 **/
	private HasPatients provider;
	/**
	 * 
	 **/
	private Patient patient;
//CONSTRUCTOR
	/**
	 * Empty constructor to allow serialization
	 **/
	public PatientNewAction(){
		
	}
	/**
	 * 
	 **/
	public PatientNewAction(HasPatients provider, List<Patient> patients){
		setProvider(provider);
		setPatient(patient);
	}
//METHODS
	/**
	 * @param patients the patients to set
	 */
	public void setPatient(Patient patient) {
		this.patient = patient;
	}
	/**
	 * @return the patients
	 */
	public Patient getPatient() {
		return patient;
	}
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
