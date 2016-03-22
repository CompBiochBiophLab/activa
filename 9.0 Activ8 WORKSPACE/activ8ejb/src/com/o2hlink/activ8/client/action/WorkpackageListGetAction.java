package com.o2hlink.activ8.client.action;

import com.o2hlink.activ8.client.entity.Project;
import com.o2hlink.activ8.client.response.WorkpackageListResponse;

/**
 * Get the list of workpackages in a project
 * @author Miguel Angel Hernandez
 **/
public class WorkpackageListGetAction implements Action<WorkpackageListResponse>, Cacheable {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = -5072358836125974966L;
	/**
	 * 
	 **/
	private Project project;
//CONSTRUCTOR
	/**
	 * 
	 **/
	public WorkpackageListGetAction(){
		
	}
	/**
	 * 
	 **/
	public WorkpackageListGetAction(Project project){
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
	/**
	 * 
	 **/
	public long getMaxAge() {
		return 3*60*1000;
	}	
	/**
	 * 
	 **/
	@Override
	public boolean equals(Object obj){
		if (obj instanceof WorkpackageListGetAction){
			WorkpackageListGetAction id = (WorkpackageListGetAction)obj;
			return getProject().equals(id.getProject());
		}
		return false;
	}
	/**
	 * 
	 **/
	@Override
	public int hashCode() {
		return getProject().hashCode();
	}
}
