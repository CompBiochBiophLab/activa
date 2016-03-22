/**
 * 
 */
package com.o2hlink.activ8.server.command;

import com.o2hlink.activ8.client.action.LogGetAction;
import com.o2hlink.activ8.client.exception.ServerException;
import com.o2hlink.activ8.client.response.LogResponse;

/**
 * @author Miguel Angel Hernandez
 **/
public class LogGetCommand extends Command<LogGetAction, LogResponse> {
	/**
	 * @param	action The action to be performed
	 * @return	The response to the action
	 **/
	public LogResponse execute(LogGetAction action) throws ServerException {
		return new LogResponse(getDispatcher().getLogSession().getLog(action.getProvider()));
	}
}
