package com.o2hlink.activ8.server.command;

import com.o2hlink.activ8.client.action.DatabaseListGetAction;
import com.o2hlink.activ8.client.exception.ServerException;
import com.o2hlink.activ8.client.response.StringListResponse;

/**
 * Retrieve the list of database plugins
 * @author Miguel Angel Hernandez
 **/
public class DatabaseListGetCommand extends Command<DatabaseListGetAction, StringListResponse> {
//METHODS
	/**
	 * 
	 **/
	@Override
	public StringListResponse execute(DatabaseListGetAction action) throws ServerException {
		return new StringListResponse(getDispatcher().getSearchSession().getPlugins());
	}
}
