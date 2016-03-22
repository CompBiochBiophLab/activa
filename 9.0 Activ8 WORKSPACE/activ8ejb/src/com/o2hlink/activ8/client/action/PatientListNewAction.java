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
import com.o2hlink.activ8.client.response.VoidResponse;

/**
 * Given a Dataset (a group of samples, subsets and patients), add a list of patients
 * @author Miguel Angel Hernandez
 **/
public class PatientListNewAction implements Action<VoidResponse> {
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
	private List<Patient> patients;
//CONSTRUCTOR
	/**
	 * Empty constructor to allow serialization
	 **/
	public PatientListNewAction(){
		
	}
	/**
	 * 
	 **/
	public PatientListNewAction(HasPatients provider, List<Patient> patients){
		setProvider(provider);
		setPatients(patients);
	}
//METHODS
	/**
	 * @param patients the patients to set
	 */
	public void setPatients(List<Patient> patients) {
		this.patients = patients;
	}
	/**
	 * @return the patients
	 */
	public List<Patient> getPatients() {
		return patients;
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
