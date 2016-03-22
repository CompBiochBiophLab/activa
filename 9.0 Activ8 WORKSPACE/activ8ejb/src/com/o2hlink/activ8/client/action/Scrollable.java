package com.o2hlink.activ8.client.action;

/**
 * Marker interface to indicate an action can be cacheable. An override of equals and hashcode is needed to compare.
 * @author Miguel Angel Hernandez
 **/
public interface Scrollable {
	/**
	 * Sets the first record to retrieve
	 * @param	firt The first record to retrieve
	 **/
	public void setFirst(int first);
	/**
	 * Gets the first record to retrieve
	 * @return	The first record to retrieve
	 **/
	public int getFirst();
	/**
	 * Sets the last record to retrieve
	 * @param	last The last record to retrieve
	 **/
	public void setLast(int last);
	/**
	 * Gets the last record to retrieve
	 * @return	The last record to retrieve
	 **/
	public int getLast();
}
