package com.o2hlink.activ8.server.local;

import java.util.List;

import javax.ejb.Local;

import com.o2hlink.activ8.client.entity.AssignedMeasurement;
import com.o2hlink.activ8.client.entity.Drug;
import com.o2hlink.activ8.client.entity.Event;
import com.o2hlink.activ8.client.entity.HasPatients;
import com.o2hlink.activ8.client.entity.HistoryRecord;
import com.o2hlink.activ8.client.entity.Measurement;
import com.o2hlink.activ8.client.entity.Patient;
import com.o2hlink.activ8.client.entity.Procedure;
import com.o2hlink.activ8.client.entity.Sample;
import com.o2hlink.activ8.client.exception.InsertException;


/**
 * Local operations with a patient
 * @author Miguel Angel Hernandez
 **/
@Local
public interface PatientLocal {
	/**
	 * 
	 **/
	public void update(Patient patient);
	/**
	 * @param	patient The patient to retrieve the measurements
	 * @return	A list of measurements
	 **/
	public List<AssignedMeasurement> getMeasurements(Patient patient);
	/**
	 * Remove the measurement assigned to the patient. Notice this does not remove any sample taken.
	 * @param	patient The patient to remove the measurement
	 * @param	measurement The measurement to remove
	 **/
	public void removeMeasurement(Patient patient, Measurement measurement);
	/**
	 * @param	patient The patient to add the measurements
	 * @param	measurement The measurement
	 * @param	event	The event
	 **/
	public AssignedMeasurement createMeasurement(Patient patient, Measurement measurement, Event event) throws InsertException;
	/**
	 * @param	patient The patient to get the samples
	 * @param	measurement The measurement to get the samples
	 * @return	A list of samples
	 **/
	public List<Sample> getSamples(Patient patient, Measurement measurement);
	/**
	 * @param	provider An object which has patients
	 * @return	A list of patients
	 **/
	public List<Patient> getPatients(HasPatients provider);
	/**
	 * @param	dataset The dataset
	 * @param	patients The patients to add
	 **/
	public void addPatient(HasPatients provider, Patient patients);
	/**
	 * @param	dataset The dataset
	 * @param	patients The patients to add
	 **/
	public void addPatients(HasPatients provider, List<Patient> patients);
	/**
	 * @param	patient The patient
	 **/
	public List<HistoryRecord> getHistory(Patient patient);
	/**
	 * @param	patient The patient
	 * @param	record The history record
	 **/
	public HistoryRecord addHistory(Patient patient, HistoryRecord record);
	/**
	 * 
	 **/
	public void removeHistory(Patient patient, HistoryRecord record);
	/**
	 * 
	 **/
	public List<Drug> getDrugs();
	/**
	 * 
	 **/
	public List<Drug> getDrugs(HistoryRecord record);
	/**
	 * @param	record The history record
	 * @param	drug The drug
	 **/
	public void addDrug(HistoryRecord record, Drug drug);
	/**
	 * @param	record The history record
	 * @param	drug The drug
	 **/
	public void removeDrug(HistoryRecord record, Drug drug);
	/**
	 * 
	 **/
	public List<Procedure> getProcedures();
	/**
	 * 
	 **/
	public List<Procedure> getProcedures(HistoryRecord record);
	/**
	 * @param	record The history record
	 * @param	procedure The procedure
	 **/
	public void addProcedure(HistoryRecord record, Procedure procedure);
	/**
	 * @param	record The history record
	 * @param	procedure The procedure
	 **/
	public void removeProcedure(HistoryRecord record, Procedure procedure);
}
