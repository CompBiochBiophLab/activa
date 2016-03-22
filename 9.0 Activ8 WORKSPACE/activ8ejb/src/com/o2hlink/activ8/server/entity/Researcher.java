/**
 * 
 */
package com.o2hlink.activ8.server.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.o2hlink.activ8.server.entity.HasProjects;
import com.o2hlink.activ8.common.entity.MappingClass;
import com.o2hlink.activ8.common.entity.MappingProperty;


/**
 * @author Miguel Angel Hernandez
 **/
@Entity
@Table(name="researcher")
@PrimaryKeyJoinColumn(name="id", referencedColumnName="id")
@MappingClass(type=com.o2hlink.activ8.client.entity.Researcher.class)
public class Researcher extends User implements HasPapers, HasDatasets, HasProjects {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = 6960171476605940197L;
	/**
	 * 
	 **/
	private String publishName;
	/**
	 * 
	 **/
	private List<Dataset> datasets = new ArrayList<Dataset>();
	/**
	 * 
	 **/
	private List<Project> projects = new ArrayList<Project>();
//CONSTRUCTOR
	/**
	 * @param publishName the publishName to set
	 */
	public void setPublishName(String publishName) {
		this.publishName = publishName;
	}
	/**
	 * @return the publishName
	 */
	@Column(name="publishname", nullable=true)
	@MappingProperty
	public String getPublishName() {
		return publishName;
	}
	/**
	 * 
	 **/
	public Researcher(){
		
	}
//METHODS
	/**
	 * @param datasets the datasets to set
	 */
	public void setDatasets(List<Dataset> datasets) {
		this.datasets = datasets;
	}
	/**
	 * @return the datasets
	 */
	@ManyToMany(
			cascade={CascadeType.PERSIST},
			targetEntity=Dataset.class,
			mappedBy="allowedUsers")
	public List<Dataset> getDatasets() {
		return datasets;
	}
	/**
	 * 
	 **/
	@Transient
	public String getAuthorName() {
		return getFirstName()+" "+getLastName();
	}
	/**
	 * 
	 **/
	@Transient
	public List<Paper> getPapers() {
		return null;
	}
	/**
	 * @param projects the projects to set
	 */
	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}
	/**
	 * @return the projects
	 */
	@ManyToMany(
			cascade={},
			fetch=FetchType.LAZY,
			targetEntity=Project.class)
	@JoinTable(
			name="researcher_project",
			joinColumns={@JoinColumn(name="researcher")},
			inverseJoinColumns={@JoinColumn(name="project")})
	public List<Project> getProjects() {
		return projects;
	}
}
