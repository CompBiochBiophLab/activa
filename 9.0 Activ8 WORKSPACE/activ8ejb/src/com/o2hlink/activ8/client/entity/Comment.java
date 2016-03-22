package com.o2hlink.activ8.client.entity;

import java.io.Serializable;

/**
 * A comment
 * @author Miguel Angel Hernandez
 **/
public class Comment implements Serializable {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = -2166802643715525674L;
	/**
	 * 
	 **/
	private long id;
	/**
	 * 
	 **/
	private String comment;
//CONSTRUCTOR
	/**
	 * 
	 **/
	public Comment(){
		
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
	public long getId() {
		return id;
	}
	/**
	 * @param comment the comment to set
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}
	/**
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}
}
