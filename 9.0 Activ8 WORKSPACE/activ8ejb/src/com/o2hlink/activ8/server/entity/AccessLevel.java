package com.o2hlink.activ8.server.entity;

/**
 * Defines the access level
 * @author Miguel Angel Hernandez
 **/
public enum AccessLevel {
	/**
	 * Anybody (users or not users) can access it
	 **/
	PUBLIC,
	/**
	 * All users can access it
	 **/
	BASIC,
	/**
	 * Certain users can access it
	 **/
	ADVANCED,
	/**
	 * Restricted access per user
	 **/
	RESTRICTED,
	/**
	 * Only administrators can access it
	 **/
	ADMIN
}
