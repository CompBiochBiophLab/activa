package com.o2hlink.activ8.client.action;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;

import com.o2hlink.activ8.client.entity.Clinician;
import com.o2hlink.activ8.client.entity.Event;
import com.o2hlink.activ8.client.entity.Group;
import com.o2hlink.activ8.client.entity.HasUsers;
import com.o2hlink.activ8.client.entity.Patient;
import com.o2hlink.activ8.client.entity.Researcher;
import com.o2hlink.activ8.client.response.UserListResponse;

/**
 * Retrieve all the users a {@link HasUsers} may have
 * @author Miguel Angel Hernandez
 **/
public class UserListGetAction implements Action<UserListResponse> {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = 668559656975179813L;
	/**
	 * 
	 **/
	private HasUsers provider;
//CONSTRUCTOR
	/**
	 * 
	 **/
	public UserListGetAction(){
		
	}
	/**
	 * 
	 **/
	public UserListGetAction(HasUsers provider){
		setProvider(provider);
	}
//METHODS
	/**
	 * @param provider the provider to set
	 */
	public void setProvider(HasUsers provider) {
		this.provider = provider;
	}
	/**
	 * @return the provider
	 */
	@XmlElements({
		@XmlElement(name="event", type=Event.class),
		@XmlElement(name="group", type=Group.class),
		@XmlElement(name="patient", type=Patient.class),
		@XmlElement(name="clinician", type=Clinician.class),
		@XmlElement(name="researcher", type=Researcher.class),
	})
	public HasUsers getProvider() {
		return provider;
	}
}
