package com.o2hlink.activ8.client.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Miguel Angel Hernandez
 **/
public class Project implements Serializable, HasLog, HasPrivacyPolicy, HasWorkpackages {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = -1643654485397557818L;
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
	private Date start;
	/**
	 * 
	 **/
	private Date end;
//CONSTRUCTOR
	/**
	 * 
	 **/
	public Project(){
		
	}
//METHODS
	/**
	 * 
	 **/
	public long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
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
	/**
	 * @param start the start to set
	 */
	public void setStart(Date start) {
		this.start = start;
	}
	/**
	 * @return the start
	 */
	public Date getStart() {
		return start;
	}
	/**
	 * @param end the end to set
	 */
	public void setEnd(Date end) {
		this.end = end;
	}
	/**
	 * @return the end
	 */
	public Date getEnd() {
		return end;
	}
}
