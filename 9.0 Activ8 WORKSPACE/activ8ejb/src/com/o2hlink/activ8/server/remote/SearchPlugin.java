package com.o2hlink.activ8.server.remote;

import java.util.List;

import org.jboss.ejb3.annotation.Depends;
import org.jboss.ejb3.annotation.Management;

import com.o2hlink.activ8.common.entity.HasSummary;

/**
 * A search plugin, implementors must be inmmutable
 * @author Miguel Angel Hernandez
 **/
@Management
@Depends("jboss:service=SearchService")
public interface SearchPlugin {
	/**
	 * 
	 **/
	public void start() throws Exception;
	/**
	 * 
	 **/
	public void stop();
	/**
	 * 
	 **/
	public List<HasSummary> search(String query, int start, int offset);
	/**
	 * 
	 **/
	public String getDatabaseName();
}
