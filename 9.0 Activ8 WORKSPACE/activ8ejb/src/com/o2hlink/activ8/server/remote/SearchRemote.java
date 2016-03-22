package com.o2hlink.activ8.server.remote;

import java.util.List;

import javax.ejb.Remote;

import com.o2hlink.activ8.common.entity.HasSummary;

/**
 * A generic search service
 * @author Miguel Angel Hernandez
 **/
@Remote
public interface SearchRemote {
	/**
	 * @return	The plugins which this is search
	 **/
	public List<String> getPlugins();
	/**
	 * @param	query The query to search
	 * @param	first	The first register to get
	 * @param	last 	The last register to get
	 * @return	List&lt;Hit&gt; 
	 **/
	public List<HasSummary> search(String query, int first, int last);
}
