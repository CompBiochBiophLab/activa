package com.o2hlink.activ8.server.command;

import com.o2hlink.activ8.client.action.PaperListGetAction;
import com.o2hlink.activ8.client.exception.ServerException;
import com.o2hlink.activ8.client.response.PaperListResponse;

/**
 * @author Miguel Angel Hernandez
 **/
public class PaperListGetCommand extends Command<PaperListGetAction, PaperListResponse>{
	/**
	 * 
	 **/
	@Override
	public PaperListResponse execute(PaperListGetAction action) throws ServerException {
		return new PaperListResponse(getDispatcher().getUserSession().getPapers(action.getProvider()));
	}

}
