package com.o2hlink.activ8.server.command;

import com.o2hlink.activ8.client.action.WorkpackageListGetAction;
import com.o2hlink.activ8.client.exception.ServerException;
import com.o2hlink.activ8.client.response.WorkpackageListResponse;

/**
 * @author Miguel Angel Hernandez
 **/
public class WorkpackageListGetCommand extends Command<WorkpackageListGetAction, WorkpackageListResponse> {
	/**
	 * 
	 **/
	@Override
	public WorkpackageListResponse execute(WorkpackageListGetAction action) throws ServerException {
		return new WorkpackageListResponse(getDispatcher().getProjectSession().getWorkpackages(action.getProject()));
	}
}
