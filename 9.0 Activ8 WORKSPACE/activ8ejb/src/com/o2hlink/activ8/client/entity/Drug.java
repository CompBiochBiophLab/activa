package com.o2hlink.activ8.client.entity;

import java.io.Serializable;

/**
 * A drug.
 * @author Miguel Angel Hernandez
 **/
public class Drug implements Serializable, HasLocales {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = 716433708814173467L;
	/**
	 * 
	 **/
	private long id;
	/**
	 * 
	 **/
	private String name;
//CONSTRUCTOR
	/**
	 * 
	 **/
	public Drug(){
		
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
}
