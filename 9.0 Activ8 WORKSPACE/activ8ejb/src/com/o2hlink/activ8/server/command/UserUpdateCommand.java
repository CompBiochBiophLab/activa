/**
 * 
 */
package com.o2hlink.activ8.server.command;

import com.o2hlink.activ8.client.action.UserUpdateAction;
import com.o2hlink.activ8.client.exception.ServerException;
import com.o2hlink.activ8.client.response.VoidResponse;

/**
 * @author Miguel Angel Hernandez
 **/
public class UserUpdateCommand extends Command<UserUpdateAction, VoidResponse> {
	/**
	 * @param	action The action to perform
	 * @return	The response to the action
	 **/
	public VoidResponse execute(UserUpdateAction action) throws ServerException {
		getDispatcher().getUserSession().update(action.getUser());
		return new VoidResponse();
	}
}
