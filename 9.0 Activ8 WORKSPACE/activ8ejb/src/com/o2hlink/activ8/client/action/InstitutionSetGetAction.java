package com.o2hlink.activ8.client.action;

import com.o2hlink.activ8.client.response.InstitutionListResponse;

/**
 * Search places action
 **/
final public class InstitutionSetGetAction implements Action<InstitutionListResponse>, Cacheable {
//FIELDS
	/**
	 * 
	 **/
	private static final long serialVersionUID = -2380761241129513310L;
	/**
	 * 
	 **/
	private double northLatitude;
	/**
	 * 
	 **/
	private double southLatitude;
	/**
	 * 
	 **/
	private double eastLongitude;
	/**
	 * 
	 **/
	private double westLongtiude;	
//CONSTRUCTOR
	/**
	 * 
	 **/
	public InstitutionSetGetAction(){
		
	}
	/**
	 * @param	north The north latitude
	 * @param	south The south latitude
	 * @param	east The east longitude
	 * @param	west The west longitude
	 **/
	public InstitutionSetGetAction(double north, double south, double east, double west){
		northLatitude = north;
		southLatitude = south;
		eastLongitude = east;
		westLongtiude = west;
	}	
//METHODS
	/**
	 * 
	 **/
	public long getMaxAge() {
		return 5*60*1000;
	}
	/**
	 * 
	 **/
	public void setNorthLatitude(double northLatitude) {
		this.northLatitude = northLatitude;
	}
	/**
	 * 
	 **/
	public double getNorthLatitude() {
		return northLatitude;
	}
	/**
	 * 
	 **/
	public void setSouthLatitude(double southLatitude) {
		this.southLatitude = southLatitude;
	}
	/**
	 * 
	 **/
	public double getSouthLatitude() {
		return southLatitude;
	}
	/**
	 * 
	 **/
	public void setEastLongitude(double eastLongitude) {
		this.eastLongitude = eastLongitude;
	}
	/**
	 * 
	 **/
	public double getEastLongitude() {
		return eastLongitude;
	}
	/**
	 * 
	 **/
	public void setWestLongtiude(double westLongtiude) {
		this.westLongtiude = westLongtiude;
	}
	/**
	 * 
	 **/
	public double getWestLongtiude() {
		return westLongtiude;
	}
}
