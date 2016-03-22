/**
 * 
 */
package com.o2hlink.activ8.client.action;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;

import com.o2hlink.activ8.client.entity.Clinician;
import com.o2hlink.activ8.client.entity.HasGroups;
import com.o2hlink.activ8.client.entity.Patient;
import com.o2hlink.activ8.client.entity.Researcher;
import com.o2hlink.activ8.client.response.GroupListResponse;

/**
 * Given an object implementing {@link HasGroups} retrieve all its groups
 * @author Miguel Angel Hernandez
 **/
public class GroupListGetAction implements Action<GroupListResponse> {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = -4133381317072808760L;
	/**
	 * 
	 **/
	private HasGroups provider;
//CONSTRUCTOR
	/**
	 * Empty constructor to allow serialization
	 **/
	public GroupListGetAction(){
		
	}
	/**
	 * 
	 **/
	public GroupListGetAction(HasGroups provider){
		setProvider(provider);
	}
//METHODS
	/**
	 * @param provider the provider to set
	 */
	public void setProvider(HasGroups provider) {
		this.provider = provider;
	}
	/**
	 * @return the provider
	 */
	@XmlElements({
		@XmlElement(name="patient", type=Patient.class),
		@XmlElement(name="clinician", type=Clinician.class),
		@XmlElement(name="researcher", type=Researcher.class)
	})
	public HasGroups getProvider() {
		return provider;
	}
}
