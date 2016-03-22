package com.o2hlink.activ8.server.command;

import com.o2hlink.activ8.client.action.InstitutionListGetAction;
import com.o2hlink.activ8.client.exception.ServerException;
import com.o2hlink.activ8.client.response.InstitutionListResponse;

/**
 * @author Miguel Angel Hernandez
 **/
public class InstitutionListGetCommand extends Command<InstitutionListGetAction, InstitutionListResponse> {
	/**
	 * 
	 **/
	@Override
	public InstitutionListResponse execute(InstitutionListGetAction action) throws ServerException {
		return new InstitutionListResponse(getDispatcher().getInstitutionSession().getInstitutions(action.getDisease()));
	}

}
