package com.o2hlink.activ8.client.entity;

import java.io.Serializable;

import com.o2hlink.activ8.common.entity.HasName;
import com.o2hlink.activ8.common.entity.HasSummary;

/**
 * @author Miguel Angel Hernandez
 **/
public class Protein implements Serializable, HasName, HasSummary {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = -7641720598738776654L;
	/**
	 * 
	 **/
	private String id;
	/**
	 * 
	 **/
	private String name;
	/**
	 * 
	 **/
	private String URL;
//CONSTRUCTOR
	/**
	 * 
	 **/
	public Protein(){
		
	}
//METHODS
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the id
	 */
	public String getId() {
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
	/**
	 * 
	 **/
	public String getDescription() {
		return name;
	}
	/**
	 * @param uRL the uRL to set
	 */
	public void setURL(String uRL) {
		URL = uRL;
	}
	/**
	 * @return the uRL
	 */
	public String getURL() {
		return URL;
	}
}
