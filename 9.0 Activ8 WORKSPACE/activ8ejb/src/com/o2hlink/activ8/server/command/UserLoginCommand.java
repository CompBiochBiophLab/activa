package com.o2hlink.activ8.server.command;

import com.o2hlink.activ8.client.action.UserLoginAction;
import com.o2hlink.activ8.client.exception.ServerException;
import com.o2hlink.activ8.client.response.UserResponse;

/**
 * @author Miguel Angel Hernandez
 **/
public class UserLoginCommand extends Command<UserLoginAction, UserResponse>{
	/**
	 * @param	action The action to be performed
	 * @return	The response to the action
	 **/
	public UserResponse execute(UserLoginAction action) throws ServerException {
		return new UserResponse(getDispatcher().getUserSession().get(action.getUsername(), action.getPassword()));
	}
}
