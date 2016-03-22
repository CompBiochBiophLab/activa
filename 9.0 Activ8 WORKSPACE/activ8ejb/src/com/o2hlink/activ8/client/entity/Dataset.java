package com.o2hlink.activ8.client.entity;

import java.io.Serializable;

import com.o2hlink.activ8.common.entity.MappingClass;
import com.o2hlink.activ8.common.entity.MappingProperty;

/**
 * A dataset is a group of patients, samples, subsets and users who can access that information.
 * @author Miguel Angel Hernandez
 **/
@MappingClass(name="com.o2hlink.activ8.server.entity.Dataset")
public class Dataset implements Serializable, HasLog, HasPrivacyPolicy, HasPatients, HasSubsets {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = 6965973358955788536L;	
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
	public Dataset(){
		
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
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the name
	 */
	@MappingProperty
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
	@MappingProperty
	public String getDescription() {
		return description;
	}
}
