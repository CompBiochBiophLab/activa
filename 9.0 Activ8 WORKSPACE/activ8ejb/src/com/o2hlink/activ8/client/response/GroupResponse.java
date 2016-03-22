/**
 * 
 */
package com.o2hlink.activ8.client.response;

import com.o2hlink.activ8.client.entity.Group;

/**
 * Return a group
 * @author Miguel Angel Hernandez
 **/
public class GroupResponse implements Response {
//FIELDS
	/**
	 * 
	 **/
	private static final long serialVersionUID = 260002018504348711L;
	/**
	 * 
	 **/
	private Group group;
//CONSTRUCTOR
	/**
	 * Empty constructor to allow serialization
	 **/
	public GroupResponse(){
		
	}
	public GroupResponse(Group group){
		this.setGroup(group);
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
