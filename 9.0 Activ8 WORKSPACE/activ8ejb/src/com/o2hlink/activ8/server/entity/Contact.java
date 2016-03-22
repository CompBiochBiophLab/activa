package com.o2hlink.activ8.server.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.o2hlink.activ8.common.entity.MappingClass;
import com.o2hlink.activ8.common.entity.MappingProperty;

/**
 * A reference to an external person from the system
 * @author Miguel Angel Hernandez
 **/
@Entity
@Table(name="contact")
@MappingClass(type=com.o2hlink.activ8.client.entity.Card.class)
public class Contact implements Serializable {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = -7778940959656360331L;
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
	private String email;
	/**
	 * 
	 **/
	private String phone;
//CONSTRUCTOR
	/**
	 * 
	 **/
	public Contact(){
		
	}
//METHODS
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the name
	 */
	@Column(name="name", length=128)
	@MappingProperty
	public String getName() {
		return name;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the email
	 */
	@Column(name="email", length=128)
	@MappingProperty
	public String getEmail() {
		return email;
	}
	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	/**
	 * @return the phone
	 */
	@Column(name="phone", length=64)
	@MappingProperty
	public String getPhone() {
		return phone;
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
	@Id
	@Column(name="id", unique=true, updatable=false)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@MappingProperty
	public long getId() {
		return id;
	}
}
