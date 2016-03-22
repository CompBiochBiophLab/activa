/**
 * 
 */
package com.o2hlink.activ8.server.command;

import com.o2hlink.activ8.client.action.UserPasswordChangeAction;
import com.o2hlink.activ8.client.exception.ServerException;
import com.o2hlink.activ8.client.response.VoidResponse;

/**
 * @author Miguel Angel Hernandez
 **/
public class UserPasswordChangeCommand extends Command<UserPasswordChangeAction, VoidResponse> {
	/**
	 * @param	action The action to perform
	 * @return	The response to the action
	 **/
	public VoidResponse execute(UserPasswordChangeAction action) throws ServerException {
		getDispatcher().getUserSession().resetPassword(action.getUser(), action.getCurrentPassword(), action.getNewPassword());
		return new VoidResponse();
	}
}
