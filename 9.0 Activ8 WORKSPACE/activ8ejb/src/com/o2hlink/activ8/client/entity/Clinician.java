/**
 * 
 */
package com.o2hlink.activ8.client.entity;

import com.o2hlink.activ8.common.entity.MappingClass;
import com.o2hlink.activ8.common.entity.MappingProperty;

/**
 * A clinic user
 * @author Miguel Angel Hernandez
 **/
@MappingClass(name="com.o2hlink.activ8.server.entity.Clinician")
public class Clinician extends User implements HasPapers, HasDatasets, HasProjects {
//FIELDS
	/**
	 * 
	 **/
	private Institution institution;
	/**
	 * 
	 **/
	private String profesionalId;
	/**
	 * 
	 **/
	private static final long serialVersionUID = -3916898560894817771L;
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
	@MappingProperty
	public Institution getInstitution() {
		return institution;
	}
	/**
	 * @param profesionalId the profesionalId to set
	 */
	public void setProfessionalId(String profesionalId) {
		this.profesionalId = profesionalId;
	}
	/**
	 * @return the profesionalId
	 */
	@MappingProperty
	public String getProfessionalId() {
		return profesionalId;
	}
}
