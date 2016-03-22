package com.o2hlink.activ8.server.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.o2hlink.activ8.common.entity.MappingProperty;

/**
 * A project is composed of several work packages
 * @author Miguel Angel Hernandez
 **/
@Entity
@Table(name="workpackage")
public class Workpackage implements Serializable{
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = 8351479929014381911L;
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
	private Project project;
//CONSTRUCTOR
	/**
	 * 
	 **/
	public Workpackage(){
		
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
	@Column(name="name", length=128)
	@MappingProperty
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
	@Column(name="description", length=512)
	@MappingProperty
	public String getDescription() {
		return description;
	}
	/**
	 * @param project the project to set
	 */
	public void setProject(Project project) {
		this.project = project;
	}
	/**
	 * @return the project
	 */
	@ManyToOne(
			cascade={},
			fetch=FetchType.EAGER,
			optional=false,
			targetEntity=Project.class)
	@JoinColumn(name="project")
	public Project getProject() {
		return project;
	}
}
