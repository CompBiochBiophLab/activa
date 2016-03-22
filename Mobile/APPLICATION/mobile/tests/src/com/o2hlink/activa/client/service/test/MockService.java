package com.o2hlink.activa.client.service.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.google.gwt.user.client.rpc.InvocationException;
import com.o2hlink.activ8.client.action.Action;
import com.o2hlink.activ8.client.entity.Entity;
import com.o2hlink.activ8.client.exception.ServerException;
import com.o2hlink.activ8.client.service.Service;

/**
 * 
 **/
public class MockService implements Service {
	
	private Map<Class<?>, ResponseHandler<Action<Entity>, Entity>> res = new HashMap<Class<?>, ResponseHandler<Action<Entity>, Entity>>();
	
	public MockService() {
		res = new HashMap<Class<?>, ResponseHandler<Action<Entity>,Entity>>();
	}
	
	@SuppressWarnings("unchecked")
	public <A extends Action<R>, R extends Entity> void setResponseHandler(Class<A> clazz, ResponseHandler<A, R> handler) {
		res.put(clazz, (ResponseHandler<Action<Entity>, Entity>) handler);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <R extends Entity> R dispatch(Action<R> action) throws ServerException, InvocationException {
		ResponseHandler<Action<Entity>, Entity> handler = res.get(action.getClass());
		if (handler == null)
			throw new UnsupportedOperationException("No response handler was defined for action " + action.getClass());
		R response = (R) handler.get((Action<Entity>) action);
		return response;
	}
	
	@Override
	public ArrayList<Entity> dispatch(ArrayList<Action<Entity>> actions) throws ServerException, InvocationException {
		ArrayList<Entity> arrayResult = new ArrayList<Entity>();
		for (Action<Entity> action : actions) {
			ResponseHandler<Action<Entity>, Entity> handler = res.get(action.getClass());
			if (handler == null)
				throw new UnsupportedOperationException("No response handler was defined for action " + action.getClass());
			Entity response = (Entity) handler.get((Action<Entity>) action);
			arrayResult.add(response);
		}
		return arrayResult;
	}
	
}
