package com.o2hlink.activ8.client.entity;

import java.io.Serializable;

import com.o2hlink.activ8.common.entity.AccountType;
import com.o2hlink.activ8.common.entity.MappingClass;
import com.o2hlink.activ8.common.entity.MappingProperty;

/**
 * A communication channel
 * @author Miguel Angel Hernandez
 **/
@MappingClass(name="com.o2hlink.activ8.server.entity.Account")
public class Account implements Serializable {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = -6601420630831111088L;
	/**
	 * 
	 **/
	private long id;
	/**
	 * 
	 **/
	private String username;
	/**
	 * 
	 **/
	private AccountType type;
//CONSTRUCTOR
	/**
	 * 
	 **/
	public Account(){
		
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
	 * @param account the account to set
	 */
	public void setUsername(String account) {
		this.username = account;
	}
	/**
	 * @return the account
	 */
	@MappingProperty
	public String getUsername() {
		return username;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(AccountType type) {
		this.type = type;
	}
	/**
	 * @return the type
	 */
	@MappingProperty
	public AccountType getType() {
		return type;
	}
}
