package com.o2hlink.activ8.server.command;

import com.o2hlink.activ8.client.action.WorkpackageNewAction;
import com.o2hlink.activ8.client.exception.ServerException;
import com.o2hlink.activ8.client.response.WorkpackageResponse;

/**
 * @author Miguel Angel Hernandez
 **/
public class WorkpackageNewCommand extends Command<WorkpackageNewAction, WorkpackageResponse> {
	/**
	 * 
	 **/
	@Override
	public WorkpackageResponse execute(WorkpackageNewAction action) throws ServerException {
		return new WorkpackageResponse(getDispatcher().getProjectSession().addWorkpackage(action.getProject(), action.getWorkpackage()));
	}
}
