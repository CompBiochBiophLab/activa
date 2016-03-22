package com.o2hlink.activ8.client.response;

import com.o2hlink.activ8.client.entity.HistoryRecord;

/**
 * Return a single history record.
 * @author Miguel Angel Hernandez
 **/
public class HistoryRecordResponse implements Response {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = -6689507672253393024L;
	/**
	 * 
	 **/
	private HistoryRecord record;
//CONSTRUCTOR
	/**
	 * 
	 **/
	public HistoryRecordResponse(){
		
	}
	/**
	 * 
	 **/
	public HistoryRecordResponse(HistoryRecord record){
		setRecord(record);
	}	
//METHODS
	/**
	 * @param record the record to set
	 */
	public void setRecord(HistoryRecord record) {
		this.record = record;
	}
	/**
	 * @return the record
	 */
	public HistoryRecord getRecord() {
		return record;
	}
}
