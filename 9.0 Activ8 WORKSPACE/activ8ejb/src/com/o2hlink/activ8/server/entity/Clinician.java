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
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.o2hlink.activ8.server.entity.HasProjects;
import com.o2hlink.activ8.common.entity.MappingClass;
import com.o2hlink.activ8.common.entity.MappingProperty;


/**
 * A clinician
 * @author Miguel Angel Hernandez
 **/
@Entity
@Table(name="clinician")
@PrimaryKeyJoinColumn(name="id", referencedColumnName="id")
@MappingClass(type=com.o2hlink.activ8.client.entity.Clinician.class)
public class Clinician extends User implements HasPapers, HasDatasets, HasProjects {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = 1148293038175484405L;
	/**
	 * 
	 **/
	private Institution institution;
	/**
	 * 
	 **/
	private String professionalId;
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
	 * 
	 **/
	public Clinician(){
		
	}
//METHODS
	/**
	 * @param institution the institution to set
	 */
	public void setInstitution(Institution institution) {
		this.institution = institution;
	}
	/**
	 * @return the institution
	 */
	@ManyToOne(
			targetEntity=Institution.class,
			cascade={},
			fetch=FetchType.EAGER)
	@JoinColumn(name="institution", nullable=true)
	@MappingProperty
	public Institution getInstitution() {
		return institution;
	}
	/**
	 * @param professionalId the professionalId to set
	 */
	public void setProfessionalId(String professionalId) {
		this.professionalId = professionalId;
	}
	/**
	 * @return the professionalId
	 */
	@Column(name="professionalid", nullable=true, length=64)
	@MappingProperty
	public String getProfessionalId() {
		return professionalId;
	}
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
		return getFirstName() + " " + getLastName();
	}
	/**
	 * 
	 **/
	@Transient
	public List<Paper> getPapers() {
		// TODO Auto-generated method stub
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
			name="clinician_project",
			joinColumns={@JoinColumn(name="clinician")},
			inverseJoinColumns={@JoinColumn(name="project")})
	public List<Project> getProjects() {
		return projects;
	}
}
