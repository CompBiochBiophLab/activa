package com.o2hlink.pimtools.patient;

import java.util.Date;
import java.util.Hashtable;

import com.o2hlink.activ8.client.entity.Array;
import com.o2hlink.activ8.client.entity.Disease;
import com.o2hlink.activ8.client.entity.Questionnaire;
import com.o2hlink.activ8.client.entity.Sample;
import com.o2hlink.activ8.common.entity.Measurement;
import com.o2hlink.pimtools.Activa;
import com.o2hlink.pimtools.exceptions.NotUpdatedException;

public class PatientManager {

	/**
	 * Instance of the manager.
	 */
	private static PatientManager instance;
	
	/**
	 * The current patient being handled.
	 */
	public Patient currentPat;
	
	/**
	 * Set of patients
	 */
	public Hashtable<Long, Patient> patientSet;
	
	/**
	 * Set of questionnaires of the actual patient;
	 */
	public Hashtable<Long, Questionnaire> currentPatQuestSet;
	
	/**
	 * Set of measurement events of the actual patient;
	 */
	public Hashtable<String, Event> currentPatMeasEventSet;
	
	/**
	 * Set of questionnaire events of the actual patient;
	 */
	public Hashtable<String, Event> currentPatQuestEventSet;
	
	private PatientManager() {
		this.patientSet = new Hashtable<Long, Patient>();
		this.currentPatMeasEventSet = new Hashtable<String, Event>();
		this.currentPatQuestEventSet = new Hashtable<String, Event>();
		this.currentPatQuestSet = new Hashtable<Long, Questionnaire>();
	}
	
	public static PatientManager getInstance() {
		if (instance == null) instance = new PatientManager();
		return instance;
	}
	
	public static void freeInstance() {
		instance = null;
	}
	
	public void getPatientList() {
		if (Activa.myMobileManager.user.getType() != 1) return;
		try {
			Activa.myProtocolManager.getPatientList();
		} catch (NotUpdatedException e) {
			Activa.myUIManager.loadTextOnWindow(Activa.myLanguageManager.TEXT_UPDATEVERSION);
			e.printStackTrace();
		}
	}

	public Array<Patient> SearchPatients(String query) {
		Array<Patient> result;
		try {
			result = Activa.myProtocolManager.searchPatients(query);
		} catch (NotUpdatedException e) {
			Activa.myUIManager.loadTextOnWindow(Activa.myLanguageManager.TEXT_UPDATEVERSION);
			e.printStackTrace();
			result = null;
		}
		return result;
	}

	public boolean AddPatient(com.o2hlink.activ8.client.entity.Patient patient) {
		boolean result;
		try {
			result = Activa.myProtocolManager.AddPatient(patient);
		} catch (NotUpdatedException e) {
			Activa.myUIManager.loadTextOnWindow(Activa.myLanguageManager.TEXT_UPDATEVERSION);
			e.printStackTrace();
			result = false;
		}
		return result;
	}

	public Array<Disease> searchDiseases(String query) {
		Array<Disease> result;
		try {
			result = Activa.myProtocolManager.searchDiseases(query);
		} catch (NotUpdatedException e) {
			Activa.myUIManager.loadTextOnWindow(Activa.myLanguageManager.TEXT_UPDATEVERSION);
			e.printStackTrace();
			result = null;
		}
		return result;
	}

	public boolean addHistoryRecord(Long patientId, Disease dis) {
		boolean result;
		try {
			result = Activa.myProtocolManager.addHistoryRecord(patientId, dis);
		} catch (NotUpdatedException e) {
			Activa.myUIManager.loadTextOnWindow(Activa.myLanguageManager.TEXT_UPDATEVERSION);
			e.printStackTrace();
			result = false;
		}
		return result;
	}

	public boolean addExploration(Long patientId, Long recordId, String description) {
		boolean result;
		try {
			result = Activa.myProtocolManager.addExploration(patientId, recordId, description);
		} catch (NotUpdatedException e) {
			Activa.myUIManager.loadTextOnWindow(Activa.myLanguageManager.TEXT_UPDATEVERSION);
			e.printStackTrace();
			result = false;
		}
		return result;
	}

	public boolean addMeasEvent(long id, Measurement meas, com.o2hlink.activ8.client.entity.Event eventtoadd) {
		boolean result;
		try {
			result = Activa.myProtocolManager.addMeasEvent(id, meas, eventtoadd);
		} catch (NotUpdatedException e) {
			Activa.myUIManager.loadTextOnWindow(Activa.myLanguageManager.TEXT_UPDATEVERSION);
			e.printStackTrace();
			result = false;
		}
		return result;
	}

	public boolean addQuestEvent(long id, Long quest, com.o2hlink.activ8.client.entity.Event eventtoadd) {
		boolean result;
		try {
			result = Activa.myProtocolManager.addQuestEvent(id, quest, eventtoadd);
		} catch (NotUpdatedException e) {
			Activa.myUIManager.loadTextOnWindow(Activa.myLanguageManager.TEXT_UPDATEVERSION);
			e.printStackTrace();
			result = false;
		}
		return result;
	}

	public boolean getPatientHistory(Patient patient) {
		boolean result;
		try {
			result = Activa.myProtocolManager.getPatientHistory(patient);
		} catch (NotUpdatedException e) {
			Activa.myUIManager.loadTextOnWindow(Activa.myLanguageManager.TEXT_UPDATEVERSION);
			e.printStackTrace();
			result = false;
		}
		return result;
	}

	public boolean getMeasurementSample(Event event) {
		boolean result;
		try {
			result = Activa.myProtocolManager.getMeasurementSample(event);
		} catch (NotUpdatedException e) {
			Activa.myUIManager.loadTextOnWindow(Activa.myLanguageManager.TEXT_UPDATEVERSION);
			e.printStackTrace();
			result = false;
		}
		return result;
	}

	public boolean getSpiroGraphs(Long userId, Date date, Sample sample) {
		boolean result;
		try {
			result = Activa.myProtocolManager.getSpiroGraphs(userId, date, sample);
		} catch (NotUpdatedException e) {
			Activa.myUIManager.loadTextOnWindow(Activa.myLanguageManager.TEXT_UPDATEVERSION);
			e.printStackTrace();
			result = false;
		}
		return result;
	}

	public boolean getSixMinutesGraphs(Long userId, Date date, Sample sample) {
		boolean result;
		try {
			result = Activa.myProtocolManager.getSixMinutesGraphs(userId, date, sample);
		} catch (NotUpdatedException e) {
			Activa.myUIManager.loadTextOnWindow(Activa.myLanguageManager.TEXT_UPDATEVERSION);
			e.printStackTrace();
			result = false;
		}
		return result;
	}

	public boolean getQuestSample(Event event) {
		boolean result;
		try {
			result = Activa.myProtocolManager.getQuestSample(event);
		} catch (NotUpdatedException e) {
			Activa.myUIManager.loadTextOnWindow(Activa.myLanguageManager.TEXT_UPDATEVERSION);
			e.printStackTrace();
			result = false;
		}
		return result;
	}
	
}
