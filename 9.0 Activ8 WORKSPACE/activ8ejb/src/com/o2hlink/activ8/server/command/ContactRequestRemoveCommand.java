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
public class ContactRequestRemoveCommand extends Command<ContactRequestRemoveAction, VoidResponse> {
	/**
	 * 
	 **/
	public VoidResponse execute(ContactRequestRemoveAction action) throws ServerException {
		getDispatcher().getUserSession().rejectRequest(action.getRequester(), action.getRequested());
		return new VoidResponse();
	}

}
