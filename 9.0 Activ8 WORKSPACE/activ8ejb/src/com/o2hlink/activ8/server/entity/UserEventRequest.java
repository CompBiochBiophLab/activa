package com.o2hlink.activ8.server.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Miguel Angel Hernandez
 **/
@Entity
@Table(name="user_event_request")
public class UserEventRequest implements Serializable {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = -507073921284983058L;
	/**
	 * 
	 **/
	private UserEventRequestId id;
	/**
	 * 
	 **/
	private String message;
//METHODS
	/**
	 * @param id the id to set
	 */
	public void setId(UserEventRequestId id) {
		this.id = id;
	}
	/**
	 * @return the id
	 */
	@EmbeddedId
	public UserEventRequestId getId() {
		return id;
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
	@Column(name="message", length=256)
	public String getMessage() {
		return message;
	}
}
