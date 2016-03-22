package com.o2hlink.activ8.server.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Miguel Angel Hernandez
 **/
@Entity
@Table(name="language")
public class Language implements Serializable {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = 3843123652826880745L;
	/**
	 * 
	 **/
	private String id;
	/**
	 * 
	 **/
	private String name;
//CONSTRUCTOR
	/**
	 * 
	 **/
	public Language() {
		
	}
//METHODS
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the id
	 */
	@Id
	@Column(name="id", updatable=false, unique=true, nullable=false)
	public String getId() {
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
	@Column(name="name", length=64)
	public String getName() {
		return name;
	}
}
