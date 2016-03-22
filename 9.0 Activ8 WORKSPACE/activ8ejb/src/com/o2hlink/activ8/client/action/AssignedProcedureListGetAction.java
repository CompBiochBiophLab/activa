/**
 * 
 */
package com.o2hlink.activ8.client.action;

import com.o2hlink.activ8.client.entity.HistoryRecord;
import com.o2hlink.activ8.client.response.ProcedureListResponse;

/**
 * Get the list of assigned procedures to a patient
 * @author Miguel Angel Hernandez
 **/
public class AssignedProcedureListGetAction implements Action<ProcedureListResponse>, Cacheable {
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
	public AssignedProcedureListGetAction(){
		
	}
	/**
	 * 
	 **/
	public AssignedProcedureListGetAction(HistoryRecord record){
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
		if (obj instanceof AssignedProcedureListGetAction){
			AssignedProcedureListGetAction id = (AssignedProcedureListGetAction)obj;
			return getRecord().equals(id.getRecord());
		}
		return false;
	}
	/**
	 * 
	 **/
	@Override
	public int hashCode() {
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
