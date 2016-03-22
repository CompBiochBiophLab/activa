package com.o2hlink.activ8.client.entity;

import java.io.Serializable;

import com.o2hlink.activ8.common.entity.IsMessage;

/**
 * An incoming contact request to the professional network
 * @author Miguel Angel Hernandez
 **/
public class IncomingContactRequest implements Serializable, IsMessage {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = -2729255876103023298L;
	/**
	 * 
	 **/
	private User requester;
	/**
	 * 
	 **/
	private String message;
//CONSTRUCTOR
	/**
	 * 
	 **/
	public IncomingContactRequest(){
		
	}
//METHODS
	/**
	 * 
	 **/
	public String getContent() {
		return message;
	}
	/**
	 * 
	 **/
	public String getFrom() {
		return requester.getFirstName()+" "+requester.getLastName();
	}
	/**
	 * 
	 **/
	public String getSubject() {
		return "Add me!";
	}
	/**
	 * @param requester the requester to set
	 */
	public void setRequester(User requester) {
		this.requester = requester;
	}
	/**
	 * @return the requester
	 */
	public User getRequester() {
		return requester;
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
