/**
 * 
 */
package com.o2hlink.activ8.client.action;

import com.o2hlink.activ8.client.entity.HistoryRecord;
import com.o2hlink.activ8.client.response.DrugListResponse;

/**
 * Get the list of assigned drugs of a history record
 * @author Miguel Angel Hernandez
 **/
public class AssignedDrugListGetAction implements Action<DrugListResponse>, Cacheable {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = -5395615225102299346L;
	/**
	 * 
	 **/
	private HistoryRecord record;
//CONSTRUCTOR
	/**
	 * Empty constructor to allow serialization
	 **/
	public AssignedDrugListGetAction(){
		
	}
	/**
	 * 
	 **/
	public AssignedDrugListGetAction(HistoryRecord record){
		setRecord(record);
	}
//METHODS
	/**
	 * 
	 **/
	public long getMaxAge() {
		return 60000;
	}
	/**
	 * 
	 **/
	@Override
	public boolean equals(Object obj){
		if (obj instanceof AssignedDrugListGetAction){
			AssignedDrugListGetAction id = (AssignedDrugListGetAction)obj;
			return getRecord().equals(id.getRecord());
		}
		return false;
	}
	/**
	 * 
	 **/
	@Override
	public int hashCode(){
		return getRecord().hashCode();
	}
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
