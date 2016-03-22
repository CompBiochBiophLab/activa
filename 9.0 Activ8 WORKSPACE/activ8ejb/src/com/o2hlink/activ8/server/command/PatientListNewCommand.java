/**
 * 
 */
package com.o2hlink.activ8.server.command;

import com.o2hlink.activ8.client.action.PatientListNewAction;
import com.o2hlink.activ8.client.exception.ServerException;
import com.o2hlink.activ8.client.response.VoidResponse;

/**
 * @author Miguel Angel Hernandez
 **/
public class PatientListNewCommand extends Command<PatientListNewAction, VoidResponse> {
	/**
	 * @param	action The action to perform
	 **/
	public VoidResponse execute(PatientListNewAction action) throws ServerException {
		getDispatcher().getPatientSession().addPatients(action.getProvider(), action.getPatients());
		return new VoidResponse();
	}
}
