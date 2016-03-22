/**
 * 
 */
package com.o2hlink.activ8.client.service;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.o2hlink.activ8.client.action.Action;
import com.o2hlink.activ8.client.entity.Entity;
import com.o2hlink.activ8.client.exception.ServerException;

/**
 * The service point for GWT client applications
 * @author Miguel Angel Hernandez
 **/
@RemoteServiceRelativePath("service")
public interface Service extends RemoteService {
	/**
	 * Dispatch any action and returns its corresponding response
	 * @param	action The action to dispatch
	 **/
	public <R extends Entity> R dispatch(Action<R> action) throws ServerException;
	/**
	 * Dispatch any set of actions and returns its corresponding responses
	 * Bug pending in code generation
	 * {@link http://code.google.com/p/google-web-toolkit/issues/detail?id=4438}
	 **/
	public ArrayList<Entity> dispatch(ArrayList<Action<Entity>> actions) throws ServerException;
}

