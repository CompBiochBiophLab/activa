package com.o2hlink.activ8.server.entity;

import java.util.List;

import com.o2hlink.activ8.common.entity.HasIdentifier;

/**
 * Identifies if it has groups
 * @author Miguel Angel Hernandez
 **/
public interface HasGroups extends HasIdentifier {
	/**
	 * 
	 **/
	public List<Group> getGroups();
}
