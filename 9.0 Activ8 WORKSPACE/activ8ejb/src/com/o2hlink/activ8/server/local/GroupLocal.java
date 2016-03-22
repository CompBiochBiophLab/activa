package com.o2hlink.activ8.server.local;

import java.util.List;

import javax.ejb.Local;

import com.o2hlink.activ8.client.entity.Group;
import com.o2hlink.activ8.client.entity.HasGroups;
import com.o2hlink.activ8.client.entity.User;


/**
 * @author Miguel Angel Hernandez
 **/
@Local
public interface GroupLocal {
//METHODS
	/**
	 * Persist a group
	 **/
	public Group save(Group group, User administrator);
	/**
	 * @return	The total list of groups
	 **/
	public List<Group> getGroups();
	/**
	 * @param	id The user id
	 * @return	List The list of groups
	 **/
	public List<Group> getGroups(HasGroups user);
}
