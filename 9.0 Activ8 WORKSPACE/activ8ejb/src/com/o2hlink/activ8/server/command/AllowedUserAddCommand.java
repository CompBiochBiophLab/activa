package com.o2hlink.activ8.server.command;

import com.o2hlink.activ8.client.action.AllowedUserAddAction;
import com.o2hlink.activ8.client.exception.ServerException;
import com.o2hlink.activ8.client.response.VoidResponse;

/**
 * @author Miguel Angel Hernandez
 **/
public class AllowedUserAddCommand extends Command<AllowedUserAddAction, VoidResponse> {
	/**
	 * 
	 **/
	@Override
	public VoidResponse execute(AllowedUserAddAction action) throws ServerException {
		getDispatcher().getPrivacySession().addAllowedUser(action.getProvider(), action.getUser());
		return new VoidResponse();
	}
}
