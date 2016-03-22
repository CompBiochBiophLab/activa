/**
 * 
 */
package com.o2hlink.activ8.server.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.o2hlink.activ8.common.entity.MappingClass;
import com.o2hlink.activ8.common.entity.MappingProperty;

/**
 * @author Miguel Angel Hernandez
 **/
@Entity
@Table(name="numcondition")
@PrimaryKeyJoinColumn(name="id", referencedColumnName="id")
@MappingClass(type=com.o2hlink.activ8.client.entity.NumericCondition.class)
public class NumericCondition extends Condition {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = -1455182583151182083L;
	/**
	 * 
	 **/
	private double threshold;
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
	public void setThreshold(double threshold) {
		this.threshold = threshold;
	}
	/**
	 * @return the threshold
	 */
	@Column(name="threshold")
	@MappingProperty
	public double getThreshold() {
		return threshold;
	}
}
