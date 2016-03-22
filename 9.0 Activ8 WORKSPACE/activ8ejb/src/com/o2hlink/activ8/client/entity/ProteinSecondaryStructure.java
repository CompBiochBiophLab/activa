package com.o2hlink.activ8.client.entity;

import java.io.Serializable;

/**
 * @author Miguel Angel Hernandez
 **/
public class ProteinSecondaryStructure implements Serializable {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = -5698703489087076323L;
	/**
	 * 
	 **/
	private int start;
	/**
	 * 
	 **/
	private int end;
//CONSTRUCTOR
	/**
	 * 
	 **/
	public ProteinSecondaryStructure(){
		
	}
//METHODS
	/**
	 * @param start the start to set
	 */
	public void setStart(int start) {
		this.start = start;
	}
	/**
	 * @return the start
	 */
	public int getStart() {
		return start;
	}
	/**
	 * @param end the end to set
	 */
	public void setEnd(int end) {
		this.end = end;
	}
	/**
	 * @return the end
	 */
	public int getEnd() {
		return end;
	}
}
