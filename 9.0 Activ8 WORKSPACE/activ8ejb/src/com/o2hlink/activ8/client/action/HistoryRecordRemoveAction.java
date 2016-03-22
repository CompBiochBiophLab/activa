package com.o2hlink.activ8.client.action;

import com.o2hlink.activ8.client.entity.HistoryRecord;
import com.o2hlink.activ8.client.entity.Patient;
import com.o2hlink.activ8.client.response.VoidResponse;

/**
 * @author Miguel Angel Hernandez
 **/
public class HistoryRecordRemoveAction implements Action<VoidResponse> {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = -2391685522110047862L;
	/**
	 * 
	 **/
	private Patient patient;
	/**
	 * 
	 **/
	private HistoryRecord historyRecord;
//CONSTRUCTOR
	/**
	 * 
	 **/
	public HistoryRecordRemoveAction(){
		
	}
	/**
	 * 
	 **/
	public HistoryRecordRemoveAction(Patient patient, HistoryRecord record){
		setPatient(patient);
		setHistoryRecord(record);
	}
//METHODS
	/**
	 * @param patient the patient to set
	 */
	public void setPatient(Patient patient) {
		this.patient = patient;
	}
	/**
	 * @return the patient
	 */
	public Patient getPatient() {
		return patient;
	}
	/**
	 * @param historyRecord the historyRecord to set
	 */
	public void setHistoryRecord(HistoryRecord historyRecord) {
		this.historyRecord = historyRecord;
	}
	/**
	 * @return the historyRecord
	 */
	public HistoryRecord getHistoryRecord() {
		return historyRecord;
	}
}
