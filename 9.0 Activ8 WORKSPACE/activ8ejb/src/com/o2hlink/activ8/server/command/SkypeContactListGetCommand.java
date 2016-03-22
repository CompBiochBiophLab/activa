package com.o2hlink.activ8.server.command;

import com.o2hlink.activ8.client.action.SkypeContactListGetAction;
import com.o2hlink.activ8.client.exception.ServerException;
import com.o2hlink.activ8.client.response.StringListResponse;

/**
 * @author Miguel Angel Hernandez
 **/
public class SkypeContactListGetCommand extends Command<SkypeContactListGetAction, StringListResponse> {
	/**
	 * 
	 **/
	@Override
	public StringListResponse execute(SkypeContactListGetAction action) throws ServerException {
		return new StringListResponse(getDispatcher().getUserSession().getAccountBuddies(action.getUser()));
	}
}
