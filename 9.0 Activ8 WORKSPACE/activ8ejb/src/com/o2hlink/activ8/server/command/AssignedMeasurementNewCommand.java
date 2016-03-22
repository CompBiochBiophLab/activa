/**
 * 
 */
package com.o2hlink.activ8.server.command;

import com.o2hlink.activ8.client.action.AssignedMeasurementNewAction;
import com.o2hlink.activ8.client.exception.ServerException;
import com.o2hlink.activ8.client.response.AssignedMeasurementResponse;

/**
 * @author Miguel Angel Hernandez
 **/
public class AssignedMeasurementNewCommand extends Command<AssignedMeasurementNewAction, AssignedMeasurementResponse> {
	/**
	 * @param	action The action to be performed
	 * @param	The response to the action
	 **/
	public AssignedMeasurementResponse execute(AssignedMeasurementNewAction action) throws ServerException {
		return new AssignedMeasurementResponse(getDispatcher().getPatientSession().createMeasurement(action.getPatient(), action.getMeasurement(), action.getEvent()));
	}
}
