package com.o2hlink.activ8.client.entity;

import java.io.Serializable;
import java.util.Date;

import com.o2hlink.activ8.common.entity.EventFrequency;
import com.o2hlink.activ8.common.entity.HasSummary;

/**
 * Event is an action which has an start and end time
 * @author Miguel Angel Hernandez
 **/
public class Event implements Serializable, HasLog, HasUsers, HasSummary {
//FIELDS
	/**
	 * 
	 **/
	private static final long serialVersionUID = -5463348116184201869L;
	/**
	 * 
	 **/
	private long id;
	/**
	 * 
	 **/
	private String name;
	/**
	 * 
	 **/
	private String description;
	/**
	 * 
	 **/
	private Date start;
	/**
	 * 
	 **/
	private Date end;
	/**
	 * 
	 **/
	private EventFrequency frequency;
	/**
	 * 
	 **/
	private Date endFrequency;
	/**
	 * 
	 **/
	private String url;
//CONSTRUCTOR
	/**
	 * 
	 **/
	public Event(){
		
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
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param start the start to set
	 */
	public void setStart(Date start) {
		this.start = start;
	}
	/**
	 * @return the start
	 */
	public Date getStart() {
		return start;
	}
	/**
	 * @param end the end to set
	 */
	public void setEnd(Date end) {
		this.end = end;
	}
	/**
	 * @return the end
	 */
	public Date getEnd() {
		return end;
	}
	/**
	 * @param frequency the frequency to set
	 */
	public void setFrequency(EventFrequency frequency) {
		this.frequency = frequency;
	}
	/**
	 * @return the frequency
	 */
	public EventFrequency getFrequency() {
		return frequency;
	}
	/**
	 * @param endFrequency the endFrequency to set
	 */
	public void setEndFrequency(Date endFrequency) {
		this.endFrequency = endFrequency;
	}
	/**
	 * @return the endFrequency
	 */
	public Date getEndFrequency() {
		return endFrequency;
	}
	/**
	 * @param url the url to set
	 */
	public void setURL(String url) {
		this.url = url;
	}
	/**
	 * @return the url
	 */
	public String getURL() {
		return url;
	}
}
