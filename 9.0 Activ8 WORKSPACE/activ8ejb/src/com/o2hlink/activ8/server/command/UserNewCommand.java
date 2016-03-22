/**
 * 
 */
package com.o2hlink.activ8.server.command;

import com.o2hlink.activ8.client.action.UserNewAction;
import com.o2hlink.activ8.client.exception.ServerException;
import com.o2hlink.activ8.client.response.UserResponse;

/**
 * @author Miguel Angel Hernandez
 **/
public class UserNewCommand extends Command<UserNewAction, UserResponse> {
	/**
	 * @param	action The action to perform
	 * @return	The response to the action
	 **/
	public UserResponse execute(UserNewAction action) throws ServerException {
		return new UserResponse(getDispatcher().getUserSession().save(action.getUser(), action.getPassword()));
	}
}
