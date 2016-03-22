package com.o2hlink.activ8.client.action;

import com.o2hlink.activ8.client.entity.Project;
import com.o2hlink.activ8.client.entity.Workpackage;
import com.o2hlink.activ8.client.response.WorkpackageResponse;

/**
 * Create a new workpackage in a project
 * @author Miguel Angel Hernandez
 **/
public class WorkpackageNewAction implements Action<WorkpackageResponse> {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = -8978468651844047727L;
	/**
	 * 
	 **/
	private Project project;
	/**
	 * 
	 **/
	private Workpackage workpackage;
//CONSTRUCTOR
	/**
	 * 
	 **/
	public WorkpackageNewAction(){
		
	}
	/**
	 * 
	 **/
	public WorkpackageNewAction(Project project, Workpackage workpackage){
		setProject(project);
		setWorkpackage(workpackage);
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
	 * @param workpackage the workpackage to set
	 */
	public void setWorkpackage(Workpackage workpackage) {
		this.workpackage = workpackage;
	}
	/**
	 * @return the workpackage
	 */
	public Workpackage getWorkpackage() {
		return workpackage;
	}
}
