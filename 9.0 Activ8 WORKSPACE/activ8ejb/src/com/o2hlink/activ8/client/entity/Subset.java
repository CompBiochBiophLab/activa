package com.o2hlink.activ8.client.entity;

import java.io.Serializable;

import com.o2hlink.activ8.common.entity.HasDescription;
import com.o2hlink.activ8.common.entity.HasName;

/**
 * A subset is a collection of samples.
 * @author Miguel Angel Hernandez
 **/
public class Subset implements Serializable, HasName, HasDescription, HasSamples {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = 2721002835679272619L;
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
	private String description;
//CONSTRUCTOR
	/**
	 * 
	 **/
	public Subset(){
		
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
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
}
