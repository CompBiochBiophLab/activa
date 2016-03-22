/**
 * 
 */
package com.o2hlink.activ8.server.command;

import com.o2hlink.activ8.client.action.AssignedDrugRemoveAction;
import com.o2hlink.activ8.client.exception.ServerException;
import com.o2hlink.activ8.client.response.VoidResponse;

/**
 * PatientMeasurementRemoveCommand
 * @author Miguel Angel Hernandez
 **/
public class AssignedDrugRemoveCommand extends Command<AssignedDrugRemoveAction, VoidResponse> {
	/**
	 * @param	action The action to be performed
	 * @param	The response to the action
	 **/
	public VoidResponse execute(AssignedDrugRemoveAction action) throws ServerException {
		getDispatcher().getPatientSession().removeDrug(action.getRecord(), action.getDrug());
		return new VoidResponse();
	}
}
