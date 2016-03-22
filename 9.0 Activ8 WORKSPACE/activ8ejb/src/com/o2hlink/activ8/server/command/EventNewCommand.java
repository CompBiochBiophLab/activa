/**
 * 
 */
package com.o2hlink.activ8.server.command;

import com.o2hlink.activ8.client.action.EventNewAction;
import com.o2hlink.activ8.client.exception.ServerException;
import com.o2hlink.activ8.client.response.EventListResponse;

/**
 * @author Miguel Angel Hernandez
 **/
public class EventNewCommand extends Command<EventNewAction, EventListResponse> {
	/**
	 * @param	action The action to sent
	 * @param	The event stored
	 **/
	public EventListResponse execute(EventNewAction action) throws ServerException {
		return new EventListResponse(getDispatcher().getEventSession().save(action.getEvent(), action.getUser()));
	}
}
