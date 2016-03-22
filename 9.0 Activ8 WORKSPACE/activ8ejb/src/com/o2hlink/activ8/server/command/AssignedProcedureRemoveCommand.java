/**
 * 
 */
package com.o2hlink.activ8.server.command;

import com.o2hlink.activ8.client.action.AssignedMeasurementRemoveAction;
import com.o2hlink.activ8.client.exception.ServerException;
import com.o2hlink.activ8.client.response.VoidResponse;

/**
 * PatientMeasurementRemoveCommand
 * @author Miguel Angel Hernandez
 **/
public class AssignedProcedureRemoveCommand extends Command<AssignedMeasurementRemoveAction, VoidResponse> {
	/**
	 * @param	action The action to be performed
	 * @param	The response to the action
	 **/
	public VoidResponse execute(AssignedMeasurementRemoveAction action) throws ServerException {
		getDispatcher().getPatientSession().removeMeasurement(action.getPatient(), action.getMeasurement());
		return new VoidResponse();
	}
}
