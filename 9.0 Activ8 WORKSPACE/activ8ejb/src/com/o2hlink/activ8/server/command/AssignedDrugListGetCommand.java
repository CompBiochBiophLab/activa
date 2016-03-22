/**
 * 
 */
package com.o2hlink.activ8.server.command;

import com.o2hlink.activ8.client.action.AssignedDrugListGetAction;
import com.o2hlink.activ8.client.exception.ServerException;
import com.o2hlink.activ8.client.response.DrugListResponse;

/**
 * @author Miguel Angel Hernandez
 **/
public class AssignedDrugListGetCommand extends Command<AssignedDrugListGetAction, DrugListResponse> {
	/**
	 * @param	action The action to be performed
	 * @return	The response to the action
	 **/
	public DrugListResponse execute(AssignedDrugListGetAction action) throws ServerException {
		return new DrugListResponse(getDispatcher().getPatientSession().getDrugs(action.getRecord()));
	}
}
