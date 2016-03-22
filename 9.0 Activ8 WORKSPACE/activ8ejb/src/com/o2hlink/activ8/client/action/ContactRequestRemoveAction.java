package com.o2hlink.activ8.client.action;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;

import com.o2hlink.activ8.client.entity.Clinician;
import com.o2hlink.activ8.client.entity.Patient;
import com.o2hlink.activ8.client.entity.Researcher;
import com.o2hlink.activ8.client.entity.User;
import com.o2hlink.activ8.client.response.VoidResponse;

/**
 * Refuse a user request to become part of its social network
 * @author Miguel Angel Hernandez
 **/
public class ContactRequestRemoveAction implements Action<VoidResponse> {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = 1708080095310026894L;
	/**
	 * 
	 **/
	private User requester;
	/**
	 * 
	 **/
	private User requested;
//CONSTRUCTOR
	/**
	 * Empty constructor to allow serialization
	 **/
	public ContactRequestRemoveAction(){
		
	}
	/**
	 * 
	 **/
	public ContactRequestRemoveAction(User requester, User requested){
		setRequester(requester);
		setRequested(requested);
	}
//METHODS
	/**
	 * @param requester the requester to set
	 */
	public void setRequester(User requester) {
		this.requester = requester;
	}
	/**
	 * @return the requester
	 */
	@XmlElements({
		@XmlElement(name="patientRequester", type=Patient.class),
		@XmlElement(name="clinicianRequester", type=Clinician.class),
		@XmlElement(name="researcherRequester", type=Researcher.class)
	})
	public User getRequester() {
		return requester;
	}
	/**
	 * @param requested the requested to set
	 */
	public void setRequested(User requested) {
		this.requested = requested;
	}
	/**
	 * @return the requested
	 */
	@XmlElements({
		@XmlElement(name="patientRequested", type=Patient.class),
		@XmlElement(name="clinicianRequested", type=Clinician.class),
		@XmlElement(name="researcherRequested", type=Researcher.class)
	})
	public User getRequested() {
		return requested;
	}
}
