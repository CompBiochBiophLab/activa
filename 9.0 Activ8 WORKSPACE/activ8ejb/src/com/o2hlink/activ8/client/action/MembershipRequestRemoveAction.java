/**
 * 
 */
package com.o2hlink.activ8.client.action;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;

import com.o2hlink.activ8.client.entity.Clinician;
import com.o2hlink.activ8.client.entity.Group;
import com.o2hlink.activ8.client.entity.Patient;
import com.o2hlink.activ8.client.entity.Researcher;
import com.o2hlink.activ8.client.entity.User;
import com.o2hlink.activ8.client.response.VoidResponse;

/**
 * A user request to become member of a group
 * @author Miguel Angel Hernandez
 **/
public class MembershipRequestRemoveAction implements Action<VoidResponse> {
//FIELDS
	/**
	 * 
	 **/
	private static final long serialVersionUID = -4547510686310351245L;
	/**
	 * 
	 **/
	private Group group;
	/**
	 * 
	 **/
	private User user;
	/**
	 * 
	 **/
	private String message;
//CONSTRUCTOR
	/**
	 * Empty constructor to allow serialization
	 **/
	public MembershipRequestRemoveAction(){
		
	}
	/**
	 * 
	 **/
	public MembershipRequestRemoveAction(Group group, User user, String message){
		this.setGroup(group);
		this.setUser(user);
		this.setMessage(message);
	}
//METHODS
	/**
	 * @param group the group to set
	 */
	public void setGroup(Group group) {
		this.group = group;
	}
	/**
	 * @return the group
	 */
	public Group getGroup() {
		return group;
	}
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
