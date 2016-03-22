package com.o2hlink.activ8.server.entity;

import java.util.List;

import com.o2hlink.activ8.common.entity.HasIdentifier;

/**
 * Identifies if an object has comments
 * @author Miguel Angel Hernandez
 **/
public interface HasComments extends HasIdentifier {
	/**
	 * 
	 **/
	public List<Comment> getComments();
}
