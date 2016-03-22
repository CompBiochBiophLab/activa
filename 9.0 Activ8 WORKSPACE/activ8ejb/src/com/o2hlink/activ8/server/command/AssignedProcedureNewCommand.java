/**
 * 
 */
package com.o2hlink.activ8.server.command;

import com.o2hlink.activ8.client.action.AssignedProcedureNewAction;
import com.o2hlink.activ8.client.exception.ServerException;
import com.o2hlink.activ8.client.response.VoidResponse;

/**
 * @author Miguel Angel Hernandez
 **/
public class AssignedProcedureNewCommand extends Command<AssignedProcedureNewAction, VoidResponse> {
	/**
	 * @param	action The action to be performed
	 * @param	The response to the action
	 **/
	public VoidResponse execute(AssignedProcedureNewAction action) throws ServerException {
		/*getDispatcher().getPatientSession().createMeasurement(action.getPatient(), action.getMeasurement(), action.getEvent());
		return new VoidResponse();*/
		return null;
	}
}
