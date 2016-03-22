/**
 * 
 */
package com.o2hlink.activ8.client.entity;

import com.o2hlink.activ8.common.entity.MappingClass;
import com.o2hlink.activ8.common.entity.MappingProperty;

/**
 * A researcher user.
 * @author Miguel Angel Hernandez
 **/
@MappingClass(name="com.o2hlink.activ8.server.entity.Researcher")
public class Researcher extends User implements HasPapers, HasDatasets, HasProjects {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = 8898774956161773226L;
	/**
	 * 
	 **/
	private String publishName;
//CONSTRUCTOR
	/**
	 *
	 **/
	public Researcher(){
		
	}
//METHODS
	/**
	 * @param publishName the publishName to set
	 */
	public void setPublishName(String publishName) {
		this.publishName = publishName;
	}
	/**
	 * @return the publishName
	 */
	@MappingProperty
	public String getPublishName() {
		return publishName;
	}
}
