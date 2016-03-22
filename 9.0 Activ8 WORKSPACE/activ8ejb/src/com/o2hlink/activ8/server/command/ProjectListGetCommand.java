package com.o2hlink.activ8.server.command;

import com.o2hlink.activ8.client.action.ProjectListGetAction;
import com.o2hlink.activ8.client.exception.ServerException;
import com.o2hlink.activ8.client.response.ProjectListResponse;

/**
 * @author Miguel Angel Hernandez
 **/
public class ProjectListGetCommand extends Command<ProjectListGetAction, ProjectListResponse> {
	/**
	 * 
	 **/
	@Override
	public ProjectListResponse execute(ProjectListGetAction action) throws ServerException {
		return new ProjectListResponse(getDispatcher().getProjectSession().getProjects(action.getProvider()));
	}
}
