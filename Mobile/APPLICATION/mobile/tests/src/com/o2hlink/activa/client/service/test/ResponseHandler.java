package com.o2hlink.activa.client.service.test;

import com.google.gwt.user.client.rpc.InvocationException;
import com.o2hlink.activ8.client.action.Action;
import com.o2hlink.activ8.client.entity.Entity;
import com.o2hlink.activ8.client.exception.ServerException;

public interface ResponseHandler<A extends Action<R>, R extends Entity> {
	/**
	 * @throws ServerException, InvocationException 
	 * 
	 **/
	public R get(A action) throws ServerException, InvocationException;
}
