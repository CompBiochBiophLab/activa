package com.o2hlink.activ8.server.command;

import com.o2hlink.activ8.client.action.ProjectNewAction;
import com.o2hlink.activ8.client.exception.ServerException;
import com.o2hlink.activ8.client.response.ProjectResponse;

/**
 * @author Miguel Angel Hernandez
 **/
public class ProjectNewCommand extends Command<ProjectNewAction, ProjectResponse> {
	/**
	 * 
	 **/
	@Override
	public ProjectResponse execute(ProjectNewAction action) throws ServerException {
		return new ProjectResponse(getDispatcher().getProjectSession().save(action.getProject(), action.getOwner()));
	}
}
