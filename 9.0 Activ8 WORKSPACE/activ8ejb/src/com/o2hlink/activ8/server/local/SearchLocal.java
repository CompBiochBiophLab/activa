package com.o2hlink.activ8.server.local;

import javax.ejb.Local;

import com.o2hlink.activ8.server.remote.SearchPlugin;

/**
 * A generic search service
 * @author Miguel Angel Hernandez
 **/
@Local
public interface SearchLocal {
	/**
	 * 
	 **/
	public void addPlugin(SearchPlugin implementor);
	/**
	 * 
	 **/
	public void removePlugin(SearchPlugin implementor);
}
