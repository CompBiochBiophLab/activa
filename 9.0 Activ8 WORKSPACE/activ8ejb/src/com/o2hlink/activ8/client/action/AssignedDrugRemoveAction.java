/**
 * 
 */
package com.o2hlink.activ8.client.action;

import com.o2hlink.activ8.client.entity.Drug;
import com.o2hlink.activ8.client.entity.HistoryRecord;
import com.o2hlink.activ8.client.response.VoidResponse;

/**
 * Remove an assigned drug from a history record
 * @author Miguel Angel Hernandez
 **/
public class AssignedDrugRemoveAction implements Action<VoidResponse> {
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
	private Drug drug;
//CONSTRUCTOR
	/**
	 * 
	 **/
	public AssignedDrugRemoveAction(){
		
	}
	/**
	 * 
	 **/
	public AssignedDrugRemoveAction(HistoryRecord record, Drug drug){
		setRecord(record);
		setDrug(drug);
	}
//METHODS
	/**
	 * @param drug the drug to set
	 */
	public void setDrug(Drug drug) {
		this.drug = drug;
	}
	/**
	 * @return the drug
	 */
	public Drug getDrug() {
		return drug;
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
