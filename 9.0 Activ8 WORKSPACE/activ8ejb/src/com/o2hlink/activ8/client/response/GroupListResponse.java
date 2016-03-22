/**
 * 
 */
package com.o2hlink.activ8.client.response;

import java.util.List;

import com.o2hlink.activ8.client.entity.Group;

/**
 * Return a list of groups
 * @author Miguel Angel Hernandez
 **/
public class GroupListResponse implements Response {
//FIELDS
	/**
	 * 
	 **/
	private static final long serialVersionUID = 260002018504348711L;
	/**
	 * 
	 **/
	private List<Group> groups;
//CONSTRUCTOR
	/**
	 * Empty constructor to allow serialization
	 **/
	public GroupListResponse(){
		
	}
	public GroupListResponse(List<Group> groups){
		this.setGroups(groups);
	}
//METHODS
	/**
	 * @param groups the groups to set
	 */
	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}
	/**
	 * @return the groups
	 */
	public List<Group> getGroups() {
		return groups;
	}
}
