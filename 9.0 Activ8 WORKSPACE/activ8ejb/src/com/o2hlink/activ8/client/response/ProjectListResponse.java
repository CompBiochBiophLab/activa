package com.o2hlink.activ8.client.response;

import java.util.List;

import com.o2hlink.activ8.client.entity.Project;

/**
 * Return a list of projects.
 * @author Miguel Angel Hernandez
 **/
public class ProjectListResponse implements Response {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = -8098689674592616463L;
	/**
	 * 
	 **/
	private List<Project> projects;
//CONSTRUCTOR
	/**
	 * 
	 **/
	public ProjectListResponse(){
		
	}
	/**
	 * 
	 **/
	public ProjectListResponse(List<Project> projects){
		setProjects(projects);
	}
//METHODS
	/**
	 * @param projects the projects to set
	 */
	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}
	/**
	 * @return the projects
	 */
	public List<Project> getProjects() {
		return projects;
	}
}
