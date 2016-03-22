/**
 * 
 */
package com.o2hlink.activ8.server.command;

import com.o2hlink.activ8.client.action.GroupListGetAction;
import com.o2hlink.activ8.client.exception.ServerException;
import com.o2hlink.activ8.client.response.GroupListResponse;

/**
 * @author Miguel Angel Hernandez
 **/
public class GroupListGetCommand extends Command<GroupListGetAction, GroupListResponse> {
	/**
	 * @param	action The action to be performed
	 * @return	The response to the action
	 **/
	public GroupListResponse execute(GroupListGetAction action) throws ServerException {
		return new GroupListResponse(getDispatcher().getGroupSession().getGroups(action.getProvider()));
	}
}
