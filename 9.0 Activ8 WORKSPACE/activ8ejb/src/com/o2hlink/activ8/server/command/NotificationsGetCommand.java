/**
 * 
 */
package com.o2hlink.activ8.server.command;

import com.o2hlink.activ8.client.action.NotificationsGetAction;
import com.o2hlink.activ8.client.exception.ServerException;
import com.o2hlink.activ8.client.response.LogResponse;

/**
 * @author Miguel Angel Hernandez
 **/
public class NotificationsGetCommand extends Command<NotificationsGetAction, LogResponse> {
	/**
	 * @param	action The action to be performed
	 * @return	The response to the action
	 **/
	public LogResponse execute(NotificationsGetAction action) throws ServerException {
		return new LogResponse(getDispatcher().getLogSession().getNotifications(action.getUser()));
	}
}
