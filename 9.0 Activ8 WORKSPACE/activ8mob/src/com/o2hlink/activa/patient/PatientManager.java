package com.o2hlink.activa.patient;

import java.util.Hashtable;

import com.o2hlink.activa.Activa;

public class PatientManager {

	/**
	 * Instance of the manager.
	 */
	private static PatientManager instance;
	
	/**
	 * Set of patients
	 */
	public Hashtable<Long, Patient> patientSet;
	
	private PatientManager() {
		this.patientSet = new Hashtable<Long, Patient>();
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
		Activa.myProtocolManager.getPatientList();
	}
	
}
