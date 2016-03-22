package com.o2hlink.activ8.server.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import javax.persistence.Entity;

/**
 * @author Miguel Angel Hernandez
 **/
@Entity
@Table(name="account_buddy")
public class AccountBuddy implements Serializable {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = 5104920026226439627L;
	/**
	 * 
	 **/
	private String username;
	/**
	 * 
	 **/
	private String name;
//CONSTRUCTOR
	/**
	 * 
	 **/
	public AccountBuddy(){
		
	}
//METHODS
	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * @return the username
	 */
	@Id 
	@Column(name="id", unique=true, nullable=false, updatable=false) 
	public String getUsername() {
		return username;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the name
	 */
	@Column(name="name", length=512)
	public String getName() {
		return name;
	}
}
