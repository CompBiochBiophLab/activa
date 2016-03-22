package com.o2hlink.activ8.client.entity;

import java.io.Serializable;

import com.o2hlink.activ8.common.entity.HasSummary;
import com.o2hlink.activ8.common.entity.MappingClass;
import com.o2hlink.activ8.common.entity.MappingProperty;

/**
 * A disease.
 * @author Miguel Angel Hernandez
 **/
@MappingClass(name="com.o2hlink.activ8.server.entity.Disease")
public class Disease implements Serializable, HasLocales, HasSummary {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = 9007624865737344590L;
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
	/**
	 * 
	 **/
	private String locus;
	/**
	 * 
	 **/
	private String url;
//CONSTRUCTOR
	/**
	 * 
	 **/
	public Disease(){
		
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
	/**
	 * @param locus the locus to set
	 */
	public void setLocus(String locus) {
		this.locus = locus;
	}
	/**
	 * @return the locus
	 */
	public String getLocus() {
		return locus;
	}
	/**
	 * 
	 **/
	public void setURL(String url) {
		this.url = url;
	}
	/**
	 * 
	 **/
	public String getURL() {
		return url;
	}
}
