package com.o2hlink.activ8.client.entity;

import java.io.Serializable;

import com.o2hlink.activ8.common.entity.HasName;
import com.o2hlink.activ8.common.entity.HasSummary;
import com.o2hlink.activ8.common.entity.HasURL;

/**
 * @author Miguel Angel Hernandez
 **/
public class Gene implements Serializable, HasName, HasURL, HasSummary {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = 8111543787907875816L;
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
	private String url;
	/**
	 * 
	 **/
	private long start;
	/**
	 * 
	 **/
	private long end;
//CONSTRUCTOR
	/**
	 * 
	 **/
	public Gene(){
		
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
	/**
	 * @return the length
	 */
	public long getLength() {
		return end - start + 1;
	}
	/**
	 * @param start the start to set
	 */
	public void setStart(long start) {
		this.start = start;
	}
	/**
	 * @return the start
	 */
	public long getStart() {
		return start;
	}
	/**
	 * @param end the end to set
	 */
	public void setEnd(long end) {
		this.end = end;
	}
	/**
	 * @return the end
	 */
	public long getEnd() {
		return end;
	}
}
