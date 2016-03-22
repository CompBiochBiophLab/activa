/**
 * 
 */
package com.o2hlink.activ8.server.command;

import com.o2hlink.activ8.client.action.ContactRequestNewAction;
import com.o2hlink.activ8.client.exception.ServerException;
import com.o2hlink.activ8.client.response.VoidResponse;

/**
 * @author Miguel Angel Hernandez
 **/
public class ContactRequestNewCommand extends Command<ContactRequestNewAction, VoidResponse> {
	/**
	 * 
	 **/
	public VoidResponse execute(ContactRequestNewAction action) throws ServerException {
		getDispatcher().getUserSession().createRequest(action.getRequester(), action.getRequested(), action.getMessage());
		return new VoidResponse();
	}

}
