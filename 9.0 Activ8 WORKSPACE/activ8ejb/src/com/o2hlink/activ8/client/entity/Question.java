package com.o2hlink.activ8.client.entity;

import java.io.Serializable;

import com.o2hlink.activ8.common.entity.MappingClass;
import com.o2hlink.activ8.common.entity.MappingProperty;

/**
 * A questionnaire is formed by a set of questions.
 * @author Miguel Angel Hernandez
 **/
@MappingClass(name="com.o2hlink.activ8.server.entity.Question")
public class Question implements Serializable {
//FIELDS
	/**
	 * 
	 **/
	private static final long serialVersionUID = -6438798449438490957L;
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
	private boolean optional;
	/**
	 * 
	 **/
	private Question next;
//CONSTRUCTOR
	/**
	 * 
	 **/
	public Question(){
		
	}
//METHODS
	/**
	 * @param next the next to set
	 */
	public void setNext(Question next) {
		this.next = next;
	}
	/**
	 * @return the next
	 */
	@MappingProperty
	public Question getNext() {
		return next;
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
	 * @param optional the optional to set
	 */
	public void setOptional(boolean optional) {
		this.optional = optional;
	}
	/**
	 * @return the optional
	 */
	@MappingProperty
	public boolean isOptional() {
		return optional;
	}
	/**
	 * 
	 **/
	public boolean isMultipleType() {
		return false;
	}
	/**
	 * 
	 **/
	public boolean isNumericType() {
		return false;
	}
	/**
	 * 
	 **/
	public boolean isTextType() {
		return false;
	}
}
