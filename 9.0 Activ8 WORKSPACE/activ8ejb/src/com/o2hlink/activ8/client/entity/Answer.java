package com.o2hlink.activ8.client.entity;

import java.io.Serializable;

import com.o2hlink.activ8.common.entity.MappingClass;
import com.o2hlink.activ8.common.entity.MappingProperty;

/**
 * An answer in a questionnaire with multiple answers (single/multiple choice)
 * @author Miguel Angel Hernandez
 */
@MappingClass(name="com.o2hlink.activ8.server.entity.Answer")
public class Answer implements Serializable {
//FIELDS
	/**
	 * 
	 **/
	private static final long serialVersionUID = -5859907659152887733L;
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
	private Double value;
//CONSTRUCTOR
	/**
	 * 
	 **/
	public Answer(){
		
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
	 * @param value the value to set
	 */
	public void setValue(Double value) {
		this.value = value;
	}
	/**
	 * @return the value
	 */
	@MappingProperty
	public Double getValue() {
		return value;
	}
}
