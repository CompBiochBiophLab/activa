package com.o2hlink.activ8.client.response;

import com.o2hlink.activ8.client.entity.Project;

/**
 * Return a single project.
 * @author Miguel Angel Hernandez
 **/
public class ProjectResponse implements Response {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = -8098689674592616463L;
	/**
	 * 
	 **/
	private Project project;
//CONSTRUCTOR
	/**
	 * 
	 **/
	public ProjectResponse(){
		
	}
	/**
	 * 
	 **/
	public ProjectResponse(Project project){
		setProject(project);
	}
//METHODS
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
