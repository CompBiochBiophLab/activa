package com.o2hlink.activ8.client.entity;

import java.io.Serializable;
import java.util.Date;

import com.o2hlink.activ8.common.entity.MappingProperty;

/**
 * A sample is a measurement done by a user in a determined time
 * @author Miguel Angel Hernandez
 **/
abstract public class Sample implements Serializable {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = 4965684462203224729L;
	/**
	 * 
	 **/
	private Date date;
//CONSTRUCTOR
	/**
	 * 
	 **/
	public Sample(){
		
	}
//METHODS
	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}
	/**
	 * @return the date
	 */
	@MappingProperty
	public Date getDate() {
		return date;
	}
}
