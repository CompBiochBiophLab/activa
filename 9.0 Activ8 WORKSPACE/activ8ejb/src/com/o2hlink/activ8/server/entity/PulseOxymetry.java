package com.o2hlink.activ8.server.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.PrimaryKeyJoinColumns;
import javax.persistence.Table;

/**
 * @author Miguel Angel Hernandez
 **/
@Entity
@Table(name="pulseoxymetry")
@PrimaryKeyJoinColumns(
		{@PrimaryKeyJoinColumn(name="patient", referencedColumnName="patient"),
		 @PrimaryKeyJoinColumn(name="date", referencedColumnName="date"),
		 @PrimaryKeyJoinColumn(name="measurement", referencedColumnName="measurement")})
public class PulseOxymetry extends Sample {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = 4425930614384112721L;
	/**
	 * 
	 **/
	private double heartRate;
	/**
	 * 
	 **/
	private double oxygenSaturation;
//CONSTRUCTOR
	/**
	 * 
	 **/
	public PulseOxymetry(){
		
	}
//METHODS
	/**
	 * @param heartRate the heartRate to set
	 */
	public void setHeartRate(double heartRate) {
		this.heartRate = heartRate;
	}
	/**
	 * @return the heartRate
	 */
	@Column(name="heartRate")
	public double getHeartRate() {
		return heartRate;
	}
	/**
	 * @param oxygenSaturation the oxygenSaturation to set
	 */
	public void setOxygenSaturation(double oxygenSaturation) {
		this.oxygenSaturation = oxygenSaturation;
	}
	/**
	 * @return the oxygenSaturation
	 */
	@Column(name="oxygenSaturation")
	public double getOxygenSaturation() {
		return oxygenSaturation;
	}
}
