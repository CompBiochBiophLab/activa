/**
 * 
 */
package com.o2hlink.activ8.server.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.PrimaryKeyJoinColumns;
import javax.persistence.Table;

/**
 * Height of a patient in meters
 * @author Miguel Angel Hernandez
 **/
@Entity
@Table(name="height")
@PrimaryKeyJoinColumns(
		{@PrimaryKeyJoinColumn(name="patient", referencedColumnName="patient"),
		 @PrimaryKeyJoinColumn(name="date", referencedColumnName="date"),
		 @PrimaryKeyJoinColumn(name="measurement", referencedColumnName="measurement")})
public class Height extends Sample {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = 5092388567789358506L;
	/**
	 * 
	 **/
	private double value;
//CONSTRUCTOR
	/**
	 * 
	 **/
	public Height(){
		
	}
//METHODS
	/**
	 * @param value the value to set
	 */
	public void setValue(double value) {
		this.value = value;
	}
	/**
	 * @return the value
	 **/
	@Column(name="value", nullable=false)
	public double getValue() {
		return value;
	}
}
