package com.o2hlink.activ8.client.entity;

import com.o2hlink.activ8.common.entity.MappingClass;
import com.o2hlink.activ8.common.entity.MappingProperty;

/**
 * A condition fired by a numeric threshold
 * @author Miguel Angel Hernandez
 **/
@MappingClass(name="com.o2hlink.activ8.server.entity.NumericCondition")
public class NumericCondition extends Condition {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = 2827006645466015731L;
	/**
	 * 
	 **/
	private Double threshold;
//CONSTRUCTOR
	/**
	 * 
	 **/
	public NumericCondition(){
		
	}
//METHODS
	/**
	 * @param threshold the threshold to set
	 */
	public void setThreshold(Double threshold) {
		this.threshold = threshold;
	}
	/**
	 * @return the threshold
	 */
	@MappingProperty
	public Double getThreshold() {
		return threshold;
	}
}
