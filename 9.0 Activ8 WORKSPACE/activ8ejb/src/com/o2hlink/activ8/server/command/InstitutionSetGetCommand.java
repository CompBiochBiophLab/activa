package com.o2hlink.activ8.server.command;

import com.o2hlink.activ8.client.action.InstitutionSetGetAction;
import com.o2hlink.activ8.client.exception.ServerException;
import com.o2hlink.activ8.client.response.InstitutionListResponse;

/**
 * @author Miguel Angel Hernandez
 **/
public class InstitutionSetGetCommand extends Command<InstitutionSetGetAction, InstitutionListResponse> {
	/**
	 * 
	 **/
	@Override
	public InstitutionListResponse execute(InstitutionSetGetAction action) throws ServerException {
		return new InstitutionListResponse(getDispatcher().getInstitutionSession().getInstitutions(action.getNorthLatitude(), action.getSouthLatitude(), action.getEastLongitude(), action.getWestLongtiude()));
	}

}
