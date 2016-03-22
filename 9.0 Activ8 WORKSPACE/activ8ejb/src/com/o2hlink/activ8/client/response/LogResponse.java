/**
 * 
 */
package com.o2hlink.activ8.client.response;

import java.util.List;

import com.o2hlink.activ8.client.entity.LogRecord;

/**
 * Return a list of log record
 * @author Miguel Angel Hernandez
 **/
public class LogResponse implements Response {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = -2415016986805443141L;
	/**
	 * 
	 **/
	private List<LogRecord> log;
//CONSTRUCTOR
	/**
	 * Empty constructor to allow serialization
	 **/
	public LogResponse(){
		
	}
	/**
	 * 
	 **/
	public LogResponse(List<LogRecord> log){
		this.setLog(log);
	}
//METHODS
	/**
	 * @param log the actions to set
	 */
	public void setLog(List<LogRecord> log) {
		this.log = log;
	}
	/**
	 * @return the actions
	 */
	public List<LogRecord> getLog() {
		return log;
	}
}
