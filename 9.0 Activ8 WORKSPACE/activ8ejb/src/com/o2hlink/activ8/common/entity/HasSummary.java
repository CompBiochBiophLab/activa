package com.o2hlink.activ8.common.entity;

/**
 * Does the object has a basic summary
 * @author Miguel Angel Hernandez
 **/
public interface HasSummary {
	/**
	 * @return	The name of the object
	 **/
	public String getName();
	/**
	 * @return	The description of the object
	 **/
	public String getDescription();
	/**
	 * @return	The url of the object
	 **/
	public String getURL();
}
