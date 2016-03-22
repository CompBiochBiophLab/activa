package com.o2hlink.activ8.client.entity;

import java.io.Serializable;

/**
 * A request of a user to another user to add it to its social network.
 * @author Miguel Angel Hernandez
 **/
public class UserUserRequest implements Serializable {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = 5372485592883166671L;
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
	 * 
	 **/
	public UserUserRequest(){
		
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
