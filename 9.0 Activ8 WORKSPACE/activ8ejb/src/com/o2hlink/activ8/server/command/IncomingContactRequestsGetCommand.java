/**
 * 
 */
package com.o2hlink.activ8.server.command;

import com.o2hlink.activ8.client.action.IncomingContactRequestsGetAction;
import com.o2hlink.activ8.client.exception.ServerException;
import com.o2hlink.activ8.client.response.IncomingContactRequestListResponse;

/**
 * @author Miguel Angel Hernandez
 **/
public class IncomingContactRequestsGetCommand extends Command<IncomingContactRequestsGetAction, IncomingContactRequestListResponse> {
	/**
	 * @param	action The action to be performed
	 * @return	The response to the action
	 **/
	public IncomingContactRequestListResponse execute(IncomingContactRequestsGetAction action) throws ServerException {
		return new IncomingContactRequestListResponse(getDispatcher().getUserSession().getIncomingContactRequests(action.getUser()));
	}
}
