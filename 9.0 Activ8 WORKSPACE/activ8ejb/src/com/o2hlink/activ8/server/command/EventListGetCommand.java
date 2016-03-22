/**
 * 
 */
package com.o2hlink.activ8.server.command;

import com.o2hlink.activ8.client.action.EventListGetAction;
import com.o2hlink.activ8.client.exception.ServerException;
import com.o2hlink.activ8.client.response.EventListResponse;

/**
 * Retrieve the list of events, internal and/or external
 * @author Miguel Angel Hernandez
 **/
public class EventListGetCommand extends Command<EventListGetAction, EventListResponse> {
	/**
	 * @param	action The action to sent
	 * @param	The event stored
	 **/
	public EventListResponse execute(EventListGetAction action) throws ServerException {
		if (action.getAccount()!=null)
			return new EventListResponse(getDispatcher().getEventSession().getEvents(action.getUser(), action.getStart(), action.getEnd(), action.getAccount()));
		return new EventListResponse(getDispatcher().getEventSession().getEvents(action.getUser(), action.getStart(), action.getEnd()));
	}
}
