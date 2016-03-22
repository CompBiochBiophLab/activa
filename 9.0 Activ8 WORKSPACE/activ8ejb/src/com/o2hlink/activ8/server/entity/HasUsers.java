package com.o2hlink.activ8.server.entity;

import java.util.List;

/**
 * A users provider is an object which has users
 * @author Miguel Angel Hernandez
 * @see	com.o2hlink.activ8.client.entity.HasUsers
 **/
public interface HasUsers {
	/**
	 * 
	 **/
	public List<User> getUsers();
}
