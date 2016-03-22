package com.o2hlink.activ8.server.remote;

import javax.ejb.Remote;

import com.o2hlink.activ8.client.action.Action;
import com.o2hlink.activ8.client.exception.ServerException;
import com.o2hlink.activ8.client.response.Response;

/**
 * Dispatch an action and returns its respective response. Used for the command pattern facade of this application.
 * @author Miguel Angel Hernandez
 * @see	Action
 * @see	Response
 **/
@Remote
public interface DispatcherRemote {
	/**
	 * @param	action The action
	 * @return	The response
	 **/
	public <R extends Response> R dispatch(Action<R> action) throws ServerException;
}
