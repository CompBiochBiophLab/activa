package com.o2hlink.activ8.server.local;

import javax.ejb.Local;

/**
 * @author Miguel Angel Hernandez
 **/
@Local
public interface FileLocal {
	/**
	 * 
	 **/
	public long save(byte[] content);
	/**
	 * 
	 **/
	public void remove(long id);
}
