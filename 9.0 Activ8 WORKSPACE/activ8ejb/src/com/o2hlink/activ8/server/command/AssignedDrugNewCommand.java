/**
 * 
 */
package com.o2hlink.activ8.server.command;

import com.o2hlink.activ8.client.action.AssignedDrugNewAction;
import com.o2hlink.activ8.client.exception.ServerException;
import com.o2hlink.activ8.client.response.VoidResponse;

/**
 * @author Miguel Angel Hernandez
 **/
public class AssignedDrugNewCommand extends Command<AssignedDrugNewAction, VoidResponse> {
	/**
	 * @param	action The action to be performed
	 * @param	The response to the action
	 **/
	public VoidResponse execute(AssignedDrugNewAction action) throws ServerException {
		getDispatcher().getPatientSession().addDrug(action.getRecord(), action.getDrug());
		return new VoidResponse();
	}
}
