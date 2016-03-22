/**
 * 
 */
package com.o2hlink.activ8.server.command;

import com.o2hlink.activ8.client.action.PatientSamplesGetAction;
import com.o2hlink.activ8.client.exception.ServerException;
import com.o2hlink.activ8.client.response.SampleListResponse;

/**
 * @author Miguel Angel Hernandez
 **/
public class PatientSamplesGetCommand extends Command<PatientSamplesGetAction, SampleListResponse> {
	/**
	 * @param	action The action to be performed
	 * @return	The response to the action
	 **/
	public SampleListResponse execute(PatientSamplesGetAction action) throws ServerException {
		return new SampleListResponse(getDispatcher().getPatientSession().getSamples(action.getPatient(), action.getMeasurement()));
	}
}
