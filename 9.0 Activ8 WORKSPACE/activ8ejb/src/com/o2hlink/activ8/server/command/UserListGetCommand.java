/**
 * 
 */
package com.o2hlink.activ8.server.command;

import com.o2hlink.activ8.client.action.UserListGetAction;
import com.o2hlink.activ8.client.exception.ServerException;
import com.o2hlink.activ8.client.response.UserListResponse;

/**
 * @author Miguel Angel Hernandez
 **/
public class UserListGetCommand extends Command<UserListGetAction, UserListResponse> {
	/**
	 * @param	action The action to be performed
	 * @return	The response to the action
	 **/
	public UserListResponse execute(UserListGetAction action) throws ServerException {
		return new UserListResponse(getDispatcher().getUserSession().getUsers(action.getProvider()));
	}
}
