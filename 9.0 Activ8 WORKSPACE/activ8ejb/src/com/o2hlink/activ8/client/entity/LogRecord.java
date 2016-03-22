package com.o2hlink.activ8.client.entity;

import java.io.Serializable;
import java.util.Date;

import com.o2hlink.activ8.common.entity.MappingClass;
import com.o2hlink.activ8.common.entity.MappingProperty;

/**
 * A log record
 * @author Miguel Angel Hernandez
 **/
@MappingClass(name="com.o2hlink.activ8.server.entity.LogRecord")
public class LogRecord implements Serializable, HasComments {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = 2910726442197332733L;
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
	private Date date;
//CONSTRUCTOR
	/**
	 * 
	 **/
	public LogRecord(){
		
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
}
