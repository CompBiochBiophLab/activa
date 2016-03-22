package com.o2hlink.activ8.server.command;

import com.o2hlink.activ8.client.action.DrugListGetAction;
import com.o2hlink.activ8.client.exception.ServerException;
import com.o2hlink.activ8.client.response.DrugListResponse;

/**
 * @author Miguel Angel Hernandez
 **/
public class DrugListGetCommand extends Command<DrugListGetAction, DrugListResponse> {
//METHODS
	/**
	 * 
	 **/
	@Override
	public DrugListResponse execute(DrugListGetAction action) throws ServerException {
		return new DrugListResponse(getDispatcher().getPatientSession().getDrugs());
	}
}
