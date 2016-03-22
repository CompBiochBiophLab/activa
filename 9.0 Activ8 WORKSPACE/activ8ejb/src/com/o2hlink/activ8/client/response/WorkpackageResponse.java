package com.o2hlink.activ8.client.response;

import com.o2hlink.activ8.client.entity.Workpackage;

/**
 * Return a single workpackage.
 * @author Miguel Angel Hernandez
 **/
public class WorkpackageResponse implements Response {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = -8098689674592616463L;
	/**
	 * 
	 **/
	private Workpackage workpackage;
//CONSTRUCTOR
	/**
	 * 
	 **/
	public WorkpackageResponse(){
		
	}
	/**
	 * 
	 **/
	public WorkpackageResponse(Workpackage workpackage){
		setWorkpackages(workpackage);
	}
//METHODS
	/**
	 * @param workpackages the workpackages to set
	 */
	public void setWorkpackages(Workpackage workpackage) {
		this.workpackage = workpackage;
	}
	/**
	 * @return the workpackages
	 */
	public Workpackage getWorkpackage() {
		return workpackage;
	}
}
