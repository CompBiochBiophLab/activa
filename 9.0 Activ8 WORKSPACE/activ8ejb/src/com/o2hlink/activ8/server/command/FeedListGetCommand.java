package com.o2hlink.activ8.server.command;

import com.o2hlink.activ8.client.action.FeedListGetAction;
import com.o2hlink.activ8.client.exception.ServerException;
import com.o2hlink.activ8.client.response.FeedListResponse;

/**
 * 
 **/
public class FeedListGetCommand extends Command<FeedListGetAction, FeedListResponse> {
	/**
	 * 
	 **/
	@Override
	public FeedListResponse execute(FeedListGetAction action) throws ServerException {
		return new FeedListResponse(getDispatcher().getUserSession().getFeeds(action.getUser()));
	}
}
