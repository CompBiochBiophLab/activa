package com.o2hlink.activ8.client.response;

import java.util.List;

import com.o2hlink.activ8.client.entity.Workpackage;

/**
 * Return a list of workpackages.
 * @author Miguel Angel Hernandez
 **/
public class WorkpackageListResponse implements Response {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = -8098689674592616463L;
	/**
	 * 
	 **/
	private List<Workpackage> workpackages;
//CONSTRUCTOR
	/**
	 * 
	 **/
	public WorkpackageListResponse(){
		
	}
	/**
	 * 
	 **/
	public WorkpackageListResponse(List<Workpackage> workpackages){
		setWorkpackages(workpackages);
	}
//METHODS
	/**
	 * @param workpackages the workpackages to set
	 */
	public void setWorkpackages(List<Workpackage> workpackages) {
		this.workpackages = workpackages;
	}
	/**
	 * @return the workpackages
	 */
	public List<Workpackage> getWorkpackages() {
		return workpackages;
	}
}
