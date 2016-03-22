package com.o2hlink.activ8.server.command;

import com.o2hlink.activ8.client.action.ProcedureListGetAction;
import com.o2hlink.activ8.client.exception.ServerException;
import com.o2hlink.activ8.client.response.ProcedureListResponse;

/**
 * @author Miguel Angel Hernandez
 **/
public class ProcedureListGetCommand extends Command<ProcedureListGetAction, ProcedureListResponse> {
//METHODS
	/**
	 * 
	 **/
	@Override
	public ProcedureListResponse execute(ProcedureListGetAction action) throws ServerException {
		return new ProcedureListResponse(getDispatcher().getPatientSession().getProcedures());
	}
}
