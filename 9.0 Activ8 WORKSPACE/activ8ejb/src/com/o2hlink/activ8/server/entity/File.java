package com.o2hlink.activ8.server.entity;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 * A file in database.
 * @author Miguel Angel Hernandez
 **/
@Entity
@Table(name="file")
public class File implements Serializable {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = 451044099401357237L;
	/**
	 * 
	 **/
	private long id;
	/**
	 * 
	 **/
	private byte[] content;
//CONSTRUCTOR
	/**
	 * 
	 **/
	public File(){
		
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
	@Column(name="id", unique=true, nullable=false, updatable=false) 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public long getId() {
		return id;
	}
	/**
	 * @param content the content to set
	 */
	public void setContent(byte[] content) {
		this.content = content;
	}
	/**
	 * @return the content
	 */
	@Basic(fetch=FetchType.LAZY)
	@Column(name="content", nullable=false, updatable=false)
	@Lob
	public byte[] getContent() {
		return content;
	}
}
