package com.o2hlink.activ8.common.entity;

import java.io.Serializable;

/**
 * @author Miguel Angel Hernandez
 **/
public class Email implements Serializable, IsMessage {
//METHODS
	/**
	 * 
	 */
	private static final long serialVersionUID = 4485439107908809154L;
	/**
	 * 
	 **/
	private String subject;
	/**
	 * 
	 **/
	private String from;
	/**
	 * 
	 **/
	private String content;
//CONSTRUCTOR
	/**
	 * 
	 **/
	public Email(){
		
	}
//METHODS
	/**
	 * @param subject the subject to set
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}
	/**
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}
	/**
	 * @param from the from to set
	 */
	public void setFrom(String from) {
		this.from = from;
	}
	/**
	 * @return the from
	 */
	public String getFrom() {
		return from;
	}
	/**
	 * @param message the message to set
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * @return the message
	 */
	public String getContent() {
		return content;
	}
}
