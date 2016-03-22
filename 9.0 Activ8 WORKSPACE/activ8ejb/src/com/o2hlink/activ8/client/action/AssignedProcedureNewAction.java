/**
 * 
 */
package com.o2hlink.activ8.client.action;

import com.o2hlink.activ8.client.entity.HistoryRecord;
import com.o2hlink.activ8.client.entity.Procedure;
import com.o2hlink.activ8.client.response.VoidResponse;

/**
 * Add a procedure to a given patient. Both patient and measurement exists.
 * @author Miguel Angel Hernandez
 **/
public class AssignedProcedureNewAction implements Action<VoidResponse> {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = 5756879188582039958L;
	/**
	 * 
	 **/
	private HistoryRecord record;
	/**
	 * 
	 **/
	private Procedure procedure;
//CONSTRUCTOR
	/**
	 * 
	 **/
	public AssignedProcedureNewAction(){
		
	}
	/**
	 * 
	 **/
	public AssignedProcedureNewAction(HistoryRecord record, Procedure procedure){
		setRecord(record);
		setProcedure(procedure);
	}
//METHODS
	/**
	 * @param procedure the procedure to set
	 */
	public void setProcedure(Procedure procedure) {
		this.procedure = procedure;
	}
	/**
	 * @return the procedure
	 */
	public Procedure getProcedure() {
		return procedure;
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
