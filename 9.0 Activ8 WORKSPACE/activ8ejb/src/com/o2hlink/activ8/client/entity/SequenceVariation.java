package com.o2hlink.activ8.client.entity;

import java.io.Serializable;

/**
 * @author Miguel Angel Hernandez
 **/
public class SequenceVariation implements Serializable {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = 678363598253668566L;
	/**
	 * 
	 **/
	private String original;
	/**
	 * 
	 **/
	private String variation;
	/**
	 * 
	 **/
	private long position;
//CONSTRUCTOR
	/**
	 * 
	 **/
	public SequenceVariation() {
		
	}
//METHODS
	/**
	 * @param original the original to set
	 */
	public void setOriginal(String original) {
		this.original = original;
	}
	/**
	 * @return the original
	 */
	public String getOriginal() {
		return original;
	}
	/**
	 * @param variation the variation to set
	 */
	public void setVariation(String variation) {
		this.variation = variation;
	}
	/**
	 * @return the variation
	 */
	public String getVariation() {
		return variation;
	}
	/**
	 * @param position the position to set
	 */
	public void setPosition(long position) {
		this.position = position;
	}
	/**
	 * @return the position
	 */
	public long getPosition() {
		return position;
	}
}
