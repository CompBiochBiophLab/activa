package com.o2hlink.activ8.server.command;

import com.o2hlink.activ8.client.action.PatientUpdateAction;
import com.o2hlink.activ8.client.exception.ServerException;
import com.o2hlink.activ8.client.response.VoidResponse;

/**
 * @author Miguel Angel Hernandez
 **/
public class PatientUpdateCommand extends Command<PatientUpdateAction, VoidResponse> {
	/**
	 * 
	 **/
	@Override
	public VoidResponse execute(PatientUpdateAction action) throws ServerException {
		getDispatcher().getPatientSession().update(action.getUser());
		return new VoidResponse();
	}

}
