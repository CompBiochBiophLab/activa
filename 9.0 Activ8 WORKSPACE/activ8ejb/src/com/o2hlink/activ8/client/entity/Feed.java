package com.o2hlink.activ8.client.entity;

import java.io.Serializable;

import com.o2hlink.activ8.common.entity.MappingClass;
import com.o2hlink.activ8.common.entity.MappingProperty;

/**
 * A particular feed
 * @author Miguel Angel Hernandez
 **/
@MappingClass(name="com.o2hlink.activ8.server.entity.Feed")
public class Feed implements Serializable{
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = -7874231696217072032L;
	/**
	 * 
	 **/
	private long id;
	/**
	 * 
	 **/
	private String url;
//CONSTRUCTOR
	/**
	 * 
	 **/
	public Feed(){
		
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
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	/**
	 * @return the url
	 */
	@MappingProperty
	public String getUrl() {
		return url;
	}
}
