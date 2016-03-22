package com.o2hlink.activ8.server.local;

import javax.ejb.Local;

import com.o2hlink.activ8.client.exception.ServerException;

/**
 * @author Miguel Angel Hernandez
 **/
@Local
public interface MobileLocal {
	/**
	 * 
	 **/
	public String process(String user, String pass, String request) throws ServerException;
}
