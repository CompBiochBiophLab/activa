/**
 * 
 */
package com.o2hlink.activ8.client.action;

import com.o2hlink.activ8.client.entity.Group;
import com.o2hlink.activ8.client.response.GroupResponse;

/**
 * Create a new group. A group is composed by people having a common goal.
 * @author Miguel Angel Hernandez
 **/
public class GroupNewAction implements Action<GroupResponse> {
//FIELDS
	/**
	 * 
	 **/
	private static final long serialVersionUID = 1074261571705038441L;
	/**
	 * 
	 **/
	private Group group;
//CONSTRUCTOR
	/**
	 * Empty constructor to allow serialization
	 **/
	public GroupNewAction(){
		
	}
	/**
	 * 
	 **/
	public GroupNewAction(Group group){
		setGroup(group);
	}
//METHODS
	/**
	 * @param group the group to set
	 */
	public void setGroup(Group group) {
		this.group = group;
	}
	/**
	 * @return the group
	 */
	public Group getGroup() {
		return group;
	}
}
