package com.o2hlink.activ8.client.action;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;

import com.o2hlink.activ8.client.entity.Clinician;
import com.o2hlink.activ8.client.entity.Patient;
import com.o2hlink.activ8.client.entity.Researcher;
import com.o2hlink.activ8.client.entity.User;
import com.o2hlink.activ8.client.response.VoidResponse;

/**
 * Request a user to add it to your social network
 * @author Miguel Angel Hernandez
 **/
public class ContactRequestNewAction implements Action<VoidResponse> {
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
	/**
	 * 
	 **/
	private String message;
//CONSTRUCTOR
	/**
	 * Empty constructor to allow serialization
	 **/
	public ContactRequestNewAction(){
		
	}
	/**
	 * 
	 **/
	public ContactRequestNewAction(User requester, User requested, String message){
		this.setRequester(requester);
		this.setRequested(requested);
		this.setMessage(message);
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
		@XmlElement(name="researcherRequester", type=Researcher.class),
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
		@XmlElement(name="patient", type=Patient.class),
		@XmlElement(name="clinician", type=Clinician.class),
		@XmlElement(name="researcher", type=Researcher.class),
	})
	public User getRequested() {
		return requested;
	}
	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
}
