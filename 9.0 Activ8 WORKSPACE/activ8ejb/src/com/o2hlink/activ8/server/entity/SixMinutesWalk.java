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
 * Six minutes walk
 * @author Miguel Angel Hernandez
 **/
@Entity
@Table(name="six_minutes_walk")
@PrimaryKeyJoinColumns(
		{@PrimaryKeyJoinColumn(name="patient", referencedColumnName="patient"),
		 @PrimaryKeyJoinColumn(name="date", referencedColumnName="date"),
		 @PrimaryKeyJoinColumn(name="measurement", referencedColumnName="measurement")})
public class SixMinutesWalk extends Sample {
//FIELDS
	/**
	 * 
	 **/
	private static final long serialVersionUID = 5889294134343171168L;
	/**
	 * 
	 **/
	private double distance;
	/**
	 * 
	 **/
	private double initialDyspnea;
	/**
	 * 
	 **/
	private double finalDyspnea;
	/**
	 * 
	 **/
	private double initialFatigue;
	/**
	 * 
	 **/
	private double finalFatigue;
	/**
	 * 
	 **/
	private int stops;
	/**
	 * 
	 **/
	private double initialCardiacFrequency;
	/**
	 * 
	 **/
	private double finalCardiacFrequency;
	/**
	 * 
	 **/
	private double initialOxygenSaturation;
	/**
	 * 
	 **/
	private double finalOxygenSaturation;
	/**
	 * 
	 **/
	private double oxygenInspiredFraction;
//CONSTRUCTOR
	/**
	 * 
	 **/
	public SixMinutesWalk(){
		
	}
//METHODS
	/**
	 * @param distance the distance to set
	 */
	public void setDistance(double distance) {
		this.distance = distance;
	}
	/**
	 * @return the distance
	 */
	@Column(name="distance")
	public double getDistance() {
		return distance;
	}
	/**
	 * @param initialDyspnea the initialDyspnea to set
	 */
	public void setInitialDyspnea(double initialDyspnea) {
		this.initialDyspnea = initialDyspnea;
	}
	/**
	 * @return the initialDyspnea
	 */
	@Column(name="initialDyspnea")
	public double getInitialDyspnea() {
		return initialDyspnea;
	}
	/**
	 * @param finalDyspnea the finalDyspnea to set
	 */
	public void setFinalDyspnea(double finalDyspnea) {
		this.finalDyspnea = finalDyspnea;
	}
	/**
	 * @return the finalDyspnea
	 */
	@Column(name="finalDyspnea")
	public double getFinalDyspnea() {
		return finalDyspnea;
	}
	/**
	 * @param initialFatigue the initialFatigue to set
	 */
	public void setInitialFatigue(double initialFatigue) {
		this.initialFatigue = initialFatigue;
	}
	/**
	 * @return the initialFatigue
	 */
	@Column(name="initialFatigue")
	public double getInitialFatigue() {
		return initialFatigue;
	}
	/**
	 * @param finalFatigue the finalFatigue to set
	 */
	public void setFinalFatigue(double finalFatigue) {
		this.finalFatigue = finalFatigue;
	}
	/**
	 * @return the finalFatigue
	 */
	@Column(name="finalFatigue")
	public double getFinalFatigue() {
		return finalFatigue;
	}
	/**
	 * @param stops the stops to set
	 */
	public void setStops(int stops) {
		this.stops = stops;
	}
	/**
	 * @return the stops
	 */
	@Column(name="stops")
	public int getStops() {
		return stops;
	}
	/**
	 * @param initialCardiacFrequency the initialCardiacFrequency to set
	 */
	public void setInitialCardiacFrequency(double initialCardiacFrequency) {
		this.initialCardiacFrequency = initialCardiacFrequency;
	}
	/**
	 * @return the initialCardiacFrequency
	 */
	@Column(name="initialCardiacFrequency")
	public double getInitialCardiacFrequency() {
		return initialCardiacFrequency;
	}
	/**
	 * @param finalCardiacFrequency the finalCardiacFrequency to set
	 */
	public void setFinalCardiacFrequency(double finalCardiacFrequency) {
		this.finalCardiacFrequency = finalCardiacFrequency;
	}
	/**
	 * @return the finalCardiacFrequency
	 */
	@Column(name="finalCardiacFrequency")
	public double getFinalCardiacFrequency() {
		return finalCardiacFrequency;
	}
	/**
	 * @param initialOxygenSaturation the initialOxygenSaturation to set
	 */
	public void setInitialOxygenSaturation(double initialOxygenSaturation) {
		this.initialOxygenSaturation = initialOxygenSaturation;
	}
	/**
	 * @return the initialOxygenSaturation
	 */
	@Column(name="initialOxygenSaturation")
	public double getInitialOxygenSaturation() {
		return initialOxygenSaturation;
	}
	/**
	 * @param finalOxygenSaturation the finalOxygenSaturation to set
	 */
	public void setFinalOxygenSaturation(double finalOxygenSaturation) {
		this.finalOxygenSaturation = finalOxygenSaturation;
	}
	/**
	 * @return the finalOxygenSaturation
	 */
	@Column(name="finalOxygenSaturation")
	public double getFinalOxygenSaturation() {
		return finalOxygenSaturation;
	}
	/**
	 * @param oxygenInspiredFraction the oxygenInspiredFraction to set
	 */
	public void setOxygenInspiredFraction(double oxygenInspiredFraction) {
		this.oxygenInspiredFraction = oxygenInspiredFraction;
	}
	/**
	 * @return the oxygenInspiredFraction
	 */
	@Column(name="oxygenInspiredFraction")
	public double getOxygenInspiredFraction() {
		return oxygenInspiredFraction;
	}
}
