/**
 * 
 */
package com.o2hlink.activ8.server.command;

import com.o2hlink.activ8.client.action.ContactNewAction;
import com.o2hlink.activ8.client.exception.ServerException;
import com.o2hlink.activ8.client.response.VoidResponse;

/**
 * @author Miguel Angel Hernandez
 **/
public class ContactNewCommand extends Command<ContactNewAction, VoidResponse> {
	/**
	 * 
	 **/
	public VoidResponse execute(ContactNewAction action) throws ServerException {
		getDispatcher().getUserSession().acceptRequest(action.getRequester(), action.getRequested());
		return new VoidResponse();
	}
}
