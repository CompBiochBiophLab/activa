package com.o2hlink.activ8.client.response;

import java.util.List;

import com.o2hlink.activ8.client.entity.HistoryRecord;

/**
 * Return a clinical history.
 * @author Miguel Angel Hernandez
 **/
public class HistoryRecordListResponse implements Response {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = -6689507672253393024L;
	/**
	 * 
	 **/
	private List<HistoryRecord> records;
//CONSTRUCTOR
	/**
	 * 
	 **/
	public HistoryRecordListResponse(){
		
	}
	/**
	 * 
	 **/
	public HistoryRecordListResponse(List<HistoryRecord> records){
		setRecords(records);
	}	
//METHODS
	/**
	 * @param records the records to set
	 */
	public void setRecords(List<HistoryRecord> records) {
		this.records = records;
	}
	/**
	 * @return the records
	 */
	public List<HistoryRecord> getRecords() {
		return records;
	}
}
