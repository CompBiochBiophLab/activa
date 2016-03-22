package com.o2hlink.activ8.client.entity;

import java.io.Serializable;

/**
 * An exon in a gene
 * @author Miguel Angel Hernandez
 **/
public class Exon implements Serializable {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = 489384878086848438L;
	/**
	 * 
	 **/
	private long start;
	/**
	 * 
	 **/
	private long end;
//CONSTRUCTOR
	/**
	 * 
	 **/
	public Exon(){
		
	}
//METHODS
	/**
	 * @param start the start to set
	 */
	public void setStart(long start) {
		this.start = start;
	}
	/**
	 * @return the start
	 */
	public long getStart() {
		return start;
	}
	/**
	 * @param end the end to set
	 */
	public void setEnd(long end) {
		this.end = end;
	}
	/**
	 * @return the end
	 */
	public long getEnd() {
		return end;
	}
}
