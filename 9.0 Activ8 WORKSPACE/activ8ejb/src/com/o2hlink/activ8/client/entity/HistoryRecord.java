package com.o2hlink.activ8.client.entity;

import java.io.Serializable;
import java.util.Date;


/**
 * An history record of a patient
 * @author Miguel Angel Hernandez
 **/
public class HistoryRecord implements Serializable, HasLog {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = 7365077397693425531L;
	/**
	 * 
	 **/
	private long id;
	/**
	 * 
	 **/
	private Disease disease;
	/**
	 * 
	 **/
	private Date date;
//CONSTRUCTOR
	/**
	 * 
	 **/
	public HistoryRecord(){
		
	}
//METHODS
	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}
	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}
	/**
	 * @param disease the disease to set
	 */
	public void setDisease(Disease disease) {
		this.disease = disease;
	}
	/**
	 * @return the disease
	 */
	public Disease getDisease() {
		return disease;
	}
	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}
	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}
}
