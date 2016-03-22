/**
 * 
 */
package com.o2hlink.activ8.server.command;

import com.o2hlink.activ8.client.action.Action;
import com.o2hlink.activ8.client.exception.ServerException;
import com.o2hlink.activ8.client.response.Response;
import com.o2hlink.activ8.server.local.DispatcherLocal;

/**
 * Command interface
 * Implementors must be immutable
 * @author Miguel Angel Hernandez
 **/
public abstract class Command<A extends Action<R>, R extends Response> {
//FIELDS
	/**
	 * 
	 **/
	private DispatcherLocal dispatcher;
//METHODS
	/**
	 * @param	action The action to perform
	 * @return	The response
	 * @throws	Exception Any exception it may happen
	 **/
	public abstract R execute(A action) throws ServerException;
	/**
	 * @param dispatcher the dispatcher to set
	 */
	public void setDispatcher(DispatcherLocal dispatcher) {
		this.dispatcher = dispatcher;
	}
	/**
	 * @return the dispatcher
	 */
	public DispatcherLocal getDispatcher() {
		return dispatcher;
	}
}
