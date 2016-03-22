package com.o2hlink.activ8.server.command;

import com.o2hlink.activ8.client.action.ExonListGetAction;
import com.o2hlink.activ8.client.exception.ServerException;
import com.o2hlink.activ8.client.response.ExonListResponse;

/**
 * @author Miguel Angel Hernandez
 **/
public class ExonListGetCommand extends Command<ExonListGetAction, ExonListResponse> {
//METHODS
	/**
	 * 
	 **/
	@Override
	public ExonListResponse execute(ExonListGetAction action) throws ServerException {
		return new ExonListResponse(getDispatcher().getGeneSession().getExons(action.getGene()));
	}
}
