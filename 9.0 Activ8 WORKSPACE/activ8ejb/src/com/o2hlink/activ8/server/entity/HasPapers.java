package com.o2hlink.activ8.server.entity;

import java.util.List;

/**
 * Defines an author of papers
 * @author Miguel Angel Hernandez
 * @see	com.o2hlink.activ8.client.entity.HasPapers
 **/
public interface HasPapers {
	/**
	 * 
	 **/
	public String getAuthorName();
	/**
	 * 
	 **/
	public List<Paper> getPapers();
}
