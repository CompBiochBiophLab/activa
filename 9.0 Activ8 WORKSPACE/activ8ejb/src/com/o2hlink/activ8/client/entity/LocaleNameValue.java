package com.o2hlink.activ8.client.entity;

import java.io.Serializable;

import com.o2hlink.activ8.common.entity.HasName;

/**
 * @author Miguel Angel Hernandez
 **/
public class LocaleNameValue implements Serializable, HasName {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = 776956800246393904L;
	/**
	 * 
	 **/
	private String name;
//CONSTRUCTOR
	/**
	 * 
	 **/
	public LocaleNameValue(){
		
	}
//METHODS
	/**
	 * 
	 **/
	public String getName() {
		return name;
	}
	/**
	 * 
	 **/
	public void setName(String name) {
		this.name = name;
	}
}
