package com.o2hlink.activ8.server.entity;

import java.util.List;

import com.o2hlink.activ8.common.entity.HasIdentifier;

/**
 * A log provider is an object which generates a log
 * @author Miguel Angel Hernandez
 * @see	com.o2hlink.activ8.client.entity.HasLog
 **/
public interface HasLog extends HasIdentifier {
	/**
	 * @return	The list of log records the provider has generated
	 **/
	public List<LogRecord> getLog();
}
