/**
 * 
 */
package com.o2hlink.activ8.server.command;

import com.o2hlink.activ8.client.action.AssignedMeasurementListGetAction;
import com.o2hlink.activ8.client.exception.ServerException;
import com.o2hlink.activ8.client.response.AssignedMeasurementListResponse;

/**
 * @author Miguel Angel Hernandez
 **/
public class AssignedMeasurementListGetCommand extends Command<AssignedMeasurementListGetAction, AssignedMeasurementListResponse> {
	/**
	 * @param	action The action to be performed
	 * @return	The response to the action
	 **/
	public AssignedMeasurementListResponse execute(AssignedMeasurementListGetAction action) throws ServerException {
		return new AssignedMeasurementListResponse(getDispatcher().getPatientSession().getMeasurements(action.getPatient()));
	}
}
