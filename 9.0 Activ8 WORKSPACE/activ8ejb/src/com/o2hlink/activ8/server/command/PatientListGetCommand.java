/**
 * 
 */
package com.o2hlink.activ8.server.command;

import com.o2hlink.activ8.client.action.PatientListGetAction;
import com.o2hlink.activ8.client.exception.ServerException;
import com.o2hlink.activ8.client.response.PatientListResponse;

/**
 * @author Miguel Angel Hernandez
 **/
public class PatientListGetCommand extends Command<PatientListGetAction, PatientListResponse> {
	/**
	 * 
	 **/
	public PatientListResponse execute(PatientListGetAction action) throws ServerException {
		return new PatientListResponse(getDispatcher().getPatientSession().getPatients(action.getProvider()));
	}
}
