/**
 * 
 */
package com.o2hlink.activ8.client.response;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;

import com.o2hlink.activ8.client.entity.Clinician;
import com.o2hlink.activ8.client.entity.Patient;
import com.o2hlink.activ8.client.entity.Researcher;
import com.o2hlink.activ8.client.entity.User;

/**
 * Returns a list of users
 * @author Miguel Angel Hernandez
 **/
public class UserListResponse implements Response {
//FIELDS
	/**
	 * 
	 **/
	private static final long serialVersionUID = -7401261863003189086L;
	/**
	 * 
	 **/
	private List<User> users;
//CONSTRUCTOR
	/**
	 * Empty constructor to allow serialization
	 **/
	public UserListResponse(){
		
	}
	/**
	 * 
	 **/
	public UserListResponse(List<User> users){
		this.setUsers(users);
	}
//METHODS
	/**
	 * @param users the users to set
	 */
	public void setUsers(List<User> users) {
		this.users = users;
	}
	/**
	 * @return the users
	 */
	@XmlElements({
		@XmlElement(name="patient", type=Patient.class),
		@XmlElement(name="clinician", type=Clinician.class),
		@XmlElement(name="researcher", type=Researcher.class)
	})
	public List<User> getUsers() {
		return users;
	}
}
