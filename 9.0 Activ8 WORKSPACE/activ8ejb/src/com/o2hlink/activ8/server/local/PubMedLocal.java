package com.o2hlink.activ8.server.local;

import java.util.List;

import javax.ejb.Local;

import com.o2hlink.activ8.client.entity.Paper;

/**
 * Remote operations with PubMed
 * @author Miguel Angel Hernandez
 **/
@Local
public interface PubMedLocal {
	/**
	 * 
	 **/
	public List<Paper> getPapers(String query, int start, int offset);
}
