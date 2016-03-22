package com.o2hlink.activ8.server.command;

import com.o2hlink.activ8.client.action.InstitutionNewAction;
import com.o2hlink.activ8.client.exception.ServerException;
import com.o2hlink.activ8.client.response.InstitutionResponse;

/**
 * @author Miguel Angel Hernandez
 **/
public class InstitutionNewCommand extends Command<InstitutionNewAction, InstitutionResponse> {
	/**
	 * 
	 **/
	@Override
	public InstitutionResponse execute(InstitutionNewAction action) throws ServerException {
		return new InstitutionResponse(getDispatcher().getInstitutionSession().save(action.getInstitution()));
	}

}
