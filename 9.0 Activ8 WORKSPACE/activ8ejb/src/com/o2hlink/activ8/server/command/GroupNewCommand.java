/**
 * 
 */
package com.o2hlink.activ8.server.command;

import com.o2hlink.activ8.client.action.GroupNewAction;
import com.o2hlink.activ8.client.exception.ServerException;
import com.o2hlink.activ8.client.response.GroupResponse;

/**
 * @author Miguel Angel Hernandez
 **/
public class GroupNewCommand extends Command<GroupNewAction, GroupResponse> {
	/**
	 * 
	 **/
	public GroupResponse execute(GroupNewAction action) throws ServerException {
		//com.o2hlink.activ8.server.entity.Group g = MapperManager.get().map(group);
		//com.o2hlink.activ8.server.entity.User u = MapperManager.get().map(administrator);
		//return MapperManager.get().map(bean.save(g, u));
		return null;
	}
}
