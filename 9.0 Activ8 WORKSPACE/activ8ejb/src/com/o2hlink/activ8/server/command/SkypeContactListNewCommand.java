package com.o2hlink.activ8.server.command;

import com.o2hlink.activ8.client.action.SkypeContactListNewAction;
import com.o2hlink.activ8.client.exception.ServerException;
import com.o2hlink.activ8.client.response.VoidResponse;

/**
 * @author Miguel Angel Hernandez
 **/
public class SkypeContactListNewCommand extends Command<SkypeContactListNewAction, VoidResponse> {
	/**
	 * 
	 **/
	@Override
	public VoidResponse execute(SkypeContactListNewAction action) throws ServerException {
		getDispatcher().getUserSession().addAccountBuddies(action.getUser(), action.getFile());
		return new VoidResponse();
	}
}
