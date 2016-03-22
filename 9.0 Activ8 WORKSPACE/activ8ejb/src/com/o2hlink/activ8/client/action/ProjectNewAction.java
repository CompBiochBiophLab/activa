package com.o2hlink.activ8.client.action;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;

import com.o2hlink.activ8.client.entity.Clinician;
import com.o2hlink.activ8.client.entity.Patient;
import com.o2hlink.activ8.client.entity.Project;
import com.o2hlink.activ8.client.entity.Researcher;
import com.o2hlink.activ8.client.entity.User;
import com.o2hlink.activ8.client.response.ProjectResponse;

/**
 * 
 * @author Miguel Angel Hernandez
 **/
public class ProjectNewAction implements Action<ProjectResponse> {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = -8978468651844047727L;
	/**
	 * 
	 **/
	private User owner;
	/**
	 * 
	 **/
	private Project project;
//CONSTRUCTOR
	/**
	 * 
	 **/
	public ProjectNewAction(){
		
	}
	/**
	 * 
	 **/
	public ProjectNewAction(User owner, Project project){
		setOwner(owner);
		setProject(project);
	}
//METHODS
	/**
	 * @param owner the owner to set
	 */
	public void setOwner(User owner) {
		this.owner = owner;
	}
	/**
	 * @return the owner
	 */
	@XmlElements({
		@XmlElement(name="patient", type=Patient.class),
		@XmlElement(name="clinician", type=Clinician.class),
		@XmlElement(name="researcher", type=Researcher.class)
	})
	public User getOwner() {
		return owner;
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
	public Project getProject() {
		return project;
	}
}
