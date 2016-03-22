package com.o2hlink.activ8.server.local;

import java.util.List;

import javax.ejb.Local;

import com.o2hlink.activ8.server.entity.HasLog;

/**
 * Local operations with a log
 * @author Miguel Angel Hernandez
 **/
@Local
public interface LogLocal {
	/**
	 * Create a log
	 * @param	owner The owner of the log
	 * @param	message The message of the log
	 **/
	public void create(HasLog provider, String message);
	/**
	 * 
	 **/
	public List<com.o2hlink.activ8.client.entity.LogRecord> getLog(com.o2hlink.activ8.client.entity.HasLog object);
	/**
	 * 
	 **/
	public List<com.o2hlink.activ8.client.entity.LogRecord> getNotifications(com.o2hlink.activ8.client.entity.User user);
}
