/**
 * 
 */
package com.o2hlink.activ8.client.action;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;

import com.o2hlink.activ8.client.entity.Clinician;
import com.o2hlink.activ8.client.entity.Patient;
import com.o2hlink.activ8.client.entity.Researcher;
import com.o2hlink.activ8.client.entity.User;
import com.o2hlink.activ8.client.response.IncomingContactRequestListResponse;

/**
 * A new request for the professional network
 * @author Miguel Angel Hernandez
 **/
public class IncomingContactRequestsGetAction implements Action<IncomingContactRequestListResponse> {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = -1268880211602299356L;
	/**
	 * 
	 **/
	private User user;
//CONSTRUCTOR
	/**
	 * Empty constructor to allow serialization
	 **/
	public IncomingContactRequestsGetAction(){
		
	}
	/**
	 * 
	 **/
	public IncomingContactRequestsGetAction(User user){
		this.setUser(user);
	}
//METHODS
	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}
	/**
	 * @return the user
	 */
	@XmlElements({
		@XmlElement(name="patient", type=Patient.class),
		@XmlElement(name="clinician", type=Clinician.class),
		@XmlElement(name="researcher", type=Researcher.class)
	})
	public User getUser() {
		return user;
	}
}
