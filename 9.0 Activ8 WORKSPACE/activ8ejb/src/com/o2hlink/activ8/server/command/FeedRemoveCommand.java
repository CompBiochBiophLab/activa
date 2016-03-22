package com.o2hlink.activ8.server.command;

import com.o2hlink.activ8.client.action.FeedRemoveAction;
import com.o2hlink.activ8.client.exception.ServerException;
import com.o2hlink.activ8.client.response.VoidResponse;

/**
 * 
 **/
public class FeedRemoveCommand extends Command<FeedRemoveAction, VoidResponse> {
	/**
	 * 
	 **/
	@Override
	public VoidResponse execute(FeedRemoveAction action) throws ServerException {
		getDispatcher().getUserSession().removeFeed(action.getUser(), action.getRss());
		return new VoidResponse();
	}
}
