package com.o2hlink.activ8.client.entity;

import java.io.Serializable;

/**
 * A lightweight representation of a user not created into the portal
 * @author Miguel Angel Hernandez
 **/
public class Card implements Serializable {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = -549984148422079889L;
	/**
	 * 
	 **/
	private long id;
	/**
	 * 
	 **/
	private String name;
	/**
	 * 
	 **/
	private String email;
	/**
	 * 
	 **/
	private String phone;
//CONSTRUCTOR
	/**
	 * 
	 **/
	public Card(){
		
	}
//METHODS
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}
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
}
