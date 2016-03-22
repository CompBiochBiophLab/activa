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
 * A scientific publication
 * @author Miguel Angel Hernandez
 **/
@Entity
@Table(name="paper")
@MappingClass(type=com.o2hlink.activ8.client.entity.Paper.class)
public class Paper implements Serializable {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = 3110973553534121115L;
	/**
	 * 
	 **/
	private long id;
	/**
	 * 
	 **/
	private String citation;
	/**
	 * 
	 **/
	private String url;
//CONSTRUCTOR
	/**
	 * 
	 **/
	public Paper(){
		
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
	 * @param citation the citation to set
	 */
	public void setCitation(String citation) {
		this.citation = citation;
	}
	/**
	 * @return the citation
	 */
	@Column(name="citation", length=512)
	@MappingProperty
	public String getCitation() {
		return citation;
	}
	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	/**
	 * @return the url
	 */
	@Column(name="url", length=512)
	@MappingProperty
	public String getUrl() {
		return url;
	}
}
