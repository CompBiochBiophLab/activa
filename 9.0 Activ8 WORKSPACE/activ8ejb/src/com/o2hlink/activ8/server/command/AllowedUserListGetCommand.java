package com.o2hlink.activ8.server.command;

import com.o2hlink.activ8.client.action.AllowedUserListGetAction;
import com.o2hlink.activ8.client.exception.ServerException;
import com.o2hlink.activ8.client.response.UserListResponse;

/**
 * @author Miguel Angel Hernandez
 **/
public class AllowedUserListGetCommand extends Command<AllowedUserListGetAction, UserListResponse> {
	/**
	 * 
	 **/
	@Override
	public UserListResponse execute(AllowedUserListGetAction action) throws ServerException {
		return new UserListResponse(getDispatcher().getPrivacySession().getAllowedUsers(action.getProvider()));
	}
}
