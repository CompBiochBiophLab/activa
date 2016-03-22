package com.o2hlink.activ8.common.entity;

/**
 * Does the object is a message
 * @author Miguel Angel Hernandez
 **/
public interface IsMessage {
	/**
	 * Who is sending this message
	 * @return	The author
	 **/
	public String getFrom();
	/**
	 * What is the subject of this message
	 * @return	The subject
	 **/
	public String getSubject();
	/**
	 * Get the content of this message
	 * @return	The content message
	 **/
	public String getContent();
}
