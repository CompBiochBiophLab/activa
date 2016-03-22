package com.o2hlink.activ8.client.entity;

import java.io.Serializable;


/**
 * A request of a user toward a group
 * @author Miguel Angel Hernandez
 **/
public class UserGroupRequest implements Serializable {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = 1626272534090530726L;	
	/**
	 * 
	 **/
	private Group group;
	/**
	 * 
	 **/
	private String message;
//CONSTRUCTOR
	/**
	 * 
	 **/
	public UserGroupRequest(){
		
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
