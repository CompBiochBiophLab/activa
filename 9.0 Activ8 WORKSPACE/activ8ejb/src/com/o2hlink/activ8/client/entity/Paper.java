package com.o2hlink.activ8.client.entity;

import java.io.Serializable;

import com.o2hlink.activ8.common.entity.HasSummary;
import com.o2hlink.activ8.common.entity.MappingClass;
import com.o2hlink.activ8.common.entity.MappingProperty;

/**
 * A scientific publication
 * @author Miguel Angel Hernandez
 **/
@MappingClass(name="com.o2hlink.activ8.server.entity.Paper")
public class Paper implements Serializable, HasSummary {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = 6938984306415434686L;
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
	private String citation;
	/**
	 * 
	 **/
	private String url;
//CONSTRUCTOR
	/**
	 * 
	 **/
	public Paper(){
		
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
	 * @param citation the citation to set
	 */
	public void setCitation(String citation) {
		this.citation = citation;
	}
	/**
	 * @return the citation
	 */
	@MappingProperty
	public String getCitation() {
		return citation;
	}
	/**
	 * @param url the url to set
	 */
	public void setURL(String url) {
		this.url = url;
	}
	/**
	 * @return the url
	 */
	@MappingProperty
	public String getURL() {
		return url;
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
		return citation;
	}
}
