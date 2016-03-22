/**
 * 
 */
package com.o2hlink.activ8.client.action;

import com.o2hlink.activ8.client.entity.Patient;
import com.o2hlink.activ8.client.response.VoidResponse;

/**	
 * Updates specific information of a patient
 * @author Miguel Angel Hernandez
 **/
public class PatientUpdateAction implements Action<VoidResponse> {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = -7153776276602075686L;
	/**
	 * 
	 **/
	private Patient user;
//CONSTRUCTOR
	/**
	 * 
	 **/
	public PatientUpdateAction(){
		
	}
	/**
	 * 
	 **/
	public PatientUpdateAction(Patient user){
		setUser(user);
	}
//METHODS
	/**
	 * @param user the user to set
	 */
	public void setUser(Patient user) {
		this.user = user;
	}
	/**
	 * @return the user
	 */
	public Patient getUser() {
		return user;
	}
}
