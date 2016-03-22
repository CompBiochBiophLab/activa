/**
 * 
 */
package com.o2hlink.activ8.server.command;

import com.o2hlink.activ8.client.action.AssignedProcedureListGetAction;
import com.o2hlink.activ8.client.exception.ServerException;
import com.o2hlink.activ8.client.response.ProcedureListResponse;

/**
 * @author Miguel Angel Hernandez
 **/
public class AssignedProcedureListGetCommand extends Command<AssignedProcedureListGetAction, ProcedureListResponse> {
	/**
	 * @param	action The action to be performed
	 * @return	The response to the action
	 **/
	public ProcedureListResponse execute(AssignedProcedureListGetAction action) throws ServerException {
		return new ProcedureListResponse(getDispatcher().getPatientSession().getProcedures(action.getRecord()));
	}
}
