package com.o2hlink.activ8.server.command;

import com.o2hlink.activ8.client.action.DiseaseListGetAction;
import com.o2hlink.activ8.client.exception.ServerException;
import com.o2hlink.activ8.client.response.DiseaseListResponse;

/**
 * @author Miguel Angel Hernandez
 **/
public class DiseaseListGetCommand extends Command<DiseaseListGetAction, DiseaseListResponse> {
	/**
	 * 
	 **/
	@Override
	public DiseaseListResponse execute(DiseaseListGetAction action) throws ServerException {
		return new DiseaseListResponse(getDispatcher().getDiseaseSession().getDiseases());
	}
}
