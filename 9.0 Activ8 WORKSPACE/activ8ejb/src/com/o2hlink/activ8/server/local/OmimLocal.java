package com.o2hlink.activ8.server.local;

import java.util.List;

import javax.ejb.Local;

import com.o2hlink.activ8.client.entity.Disease;

/**
 * Expected operations on diseases
 * @author Miguel Angel Hernandez
 **/
@Local
public interface OmimLocal {
	/**
	 * 
	 **/
	public List<Disease> getDiseases(String query, int start, int length);
}
