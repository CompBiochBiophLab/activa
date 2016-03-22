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
 * @author Miguel Angel Hernandez
 **/
@Entity
@Table(name="comment")
@MappingClass(type=com.o2hlink.activ8.client.entity.Comment.class)
public class Comment implements Serializable {
//FIELS
	/**
	 * 
	 */
	private static final long serialVersionUID = -5743675760149590013L;
	/**
	 * 
	 **/
	private long id;
	/**
	 * 
	 **/
	private String comment;
//CONSTRUCTOR
	/**
	 * 
	 **/
	public Comment() {
		
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
	@Id
	@Column(name="id", nullable=false, updatable=false, unique=true)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@MappingProperty
	public long getId() {
		return id;
	}
	/**
	 * @param comment the comment to set
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}
	/**
	 * @return the comment
	 */
	@Column(name="comment", nullable=false)
	@MappingProperty
	public String getComment() {
		return comment;
	}
}
