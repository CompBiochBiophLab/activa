/**
 * 
 */
package com.o2hlink.activ8.server.command;

import com.o2hlink.activ8.client.action.ContactRemoveAction;
import com.o2hlink.activ8.client.exception.ServerException;
import com.o2hlink.activ8.client.response.VoidResponse;

/**
 * @author Miguel Angel Hernandez
 **/
public class ContactRemoveCommand extends Command<ContactRemoveAction, VoidResponse> {
	/**
	 * 
	 **/
	public VoidResponse execute(ContactRemoveAction action) throws ServerException {
		getDispatcher().getUserSession().rejectRequest(action.getRequester(), action.getRequested());
		return new VoidResponse();
	}
}
