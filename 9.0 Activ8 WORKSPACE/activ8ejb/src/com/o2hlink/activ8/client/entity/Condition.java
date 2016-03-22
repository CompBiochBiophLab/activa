package com.o2hlink.activ8.client.entity;

import java.io.Serializable;

import com.o2hlink.activ8.common.entity.MappingClass;
import com.o2hlink.activ8.common.entity.MappingProperty;

/**
 * Jump conditions inside a questionnaire. Jump conditions allow us to jump to other questions in case we have found the answers given are directed in one way or another.
 * @author Miguel Angel Hernandez
 **/
@MappingClass(name="com.o2hlink.activ8.server.entity.Condition")
public class Condition implements Serializable {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = 2827006645466015731L;
	/**
	 * 
	 **/
	private long id;
	/**
	 * 
	 **/
	private Question next;
//CONSTRUCTOR
	/**
	 * 
	 **/
	public Condition(){
		
	}
//METHODS
	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}
	/**
	 * @return the id
	 */
	@MappingProperty
	public long getId() {
		return id;
	}
	/**
	 * @param next the next to set
	 */
	public void setNext(Question next) {
		this.next = next;
	}
	/**
	 * @return the next
	 */
	@MappingProperty
	public Question getNext() {
		return next;
	}
}
