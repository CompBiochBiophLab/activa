package com.o2hlink.activ8.common.entity;

/**
 * Different privacy levels of the system
 * @author Miguel Angel Hernandez
 **/
public enum PrivacyLevel {
	/**
	 * Only persons whose access is granted
	 **/
	RESTRICTED,
	/**
	 * Only my close network (users in my professional network)
	 **/
	PRIVATE,
	/**
	 * Anyone can see it
	 **/
	PUBLIC
}
