/**
 * 
 */
package com.o2hlink.activ8.client.action;

/**
 * Marker interface to indicate an action can be cacheable. An override of equals and hashcode is needed to compare.
 * @author Miguel Angel Hernandez
 **/
public interface Cacheable {
	/**
	 * @return The time in milliseconds to which the last result will be cached
	 **/
	public long getMaxAge();
}
