/**
 * 
 */
package com.o2hlink.activ8.server.command;

import com.o2hlink.activ8.client.action.ContactRequestRemoveAction;
import com.o2hlink.activ8.client.exception.ServerException;
import com.o2hlink.activ8.client.response.VoidResponse;

/**
 * @author Miguel Angel Hernandez
 **/
public class RemoveContactRequestCommand extends Command<ContactRequestRemoveAction, VoidResponse> {
	/**
	 * @param	action The action to perform
	 * @return	The response to the action
	 **/
	public VoidResponse execute(ContactRequestRemoveAction action) throws ServerException {
		getDispatcher().getUserSession().rejectRequest(action.getRequester(), action.getRequested());
		return new VoidResponse();
	}

}
