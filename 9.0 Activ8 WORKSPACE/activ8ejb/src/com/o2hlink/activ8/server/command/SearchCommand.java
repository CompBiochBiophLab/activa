package com.o2hlink.activ8.server.command;

import com.o2hlink.activ8.client.action.SearchAction;
import com.o2hlink.activ8.client.exception.ServerException;
import com.o2hlink.activ8.client.response.SearchResponse;

/**
 * @author Miguel Angel Hernandez
 **/
public class SearchCommand extends Command<SearchAction, SearchResponse> {
	/**
	 * 
	 **/
	@Override
	public SearchResponse execute(SearchAction action) throws ServerException {
		return new SearchResponse(getDispatcher().getSearchSession().search(action.getQuery(), action.getFirst(), action.getLast()));
	}
}
