package com.o2hlink.activ8.server.command;

import com.o2hlink.activ8.client.action.FeedNewAction;
import com.o2hlink.activ8.client.exception.ServerException;
import com.o2hlink.activ8.client.response.FeedResponse;

/**
 * 
 **/
public class FeedNewCommand extends Command<FeedNewAction, FeedResponse> {
	/**
	 * 
	 **/
	@Override
	public FeedResponse execute(FeedNewAction action) throws ServerException {
		return new FeedResponse(getDispatcher().getUserSession().addFeed(action.getUser(), action.getRss()));
	}
}
