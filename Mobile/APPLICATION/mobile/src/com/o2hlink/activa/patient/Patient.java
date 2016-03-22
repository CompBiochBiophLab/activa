package com.o2hlink.activa.patient;

import java.util.Enumeration;
import java.util.Hashtable;

import com.o2hlink.activ8.common.entity.Sex;
import com.o2hlink.activa.Activa;
import com.o2hlink.activa.ActivaUtil;

import android.text.Html;
import android.text.Spanned;

public class Patient extends com.o2hlink.activ8.client.entity.Patient {
	
	/**
	 * History of the patient
	 */
	public Hashtable<Long, HistoryRecord> history;
	
	/**
	 * Last pulseoximetry
	 */
	public PulseoximetrySample lastPulseoximetry;
	
	/**
	 * Last exercise
	 */
	public SixMinutesWalkSample lastSixMinutes;
	
	/**
	 * Last spirometry
	 */
	public SpirometrySample lastSpirometry;

	public WeightHeightSample lastWeightHeight;

	public ExerciseSample lastExercise;
	
	/**
	 * Constructor
	 */
	public Patient() {
		super();
		this.history = new Hashtable<Long, HistoryRecord>();
	}
	
	public Patient(com.o2hlink.activ8.client.entity.Patient pat) {
		super();
		this.setId(pat.getId());
		this.setUsername(pat.getUsername());
		this.setFirstName(pat.getFirstName());
		this.setLastName(pat.getLastName());
		this.setBirthdate(pat.getBirthdate());
		this.setCountry(pat.getCountry());
		this.setSex(pat.getSex());
		this.setEmail(pat.getEmail());
		this.history = new Hashtable<Long, HistoryRecord>();
	}
	
	public Spanned getPatientPersonalData() {
		String ctrToAdd;
		if (this.getCountry() != null) ctrToAdd = this.getCountry().getName();
		else ctrToAdd = "-";
		String ret = "<big><b>" + Activa.myLanguageManager.PATIENTS_HISTORY_PERSONALDATA + "</b></big><br /><br />" +
			"<b>" + Activa.myLanguageManager.PATIENTS_PERSONALDATA_NAME + ": </b>" + this.getFirstName() + " " + this.getLastName() + "<br />" + 
			"<b>" + Activa.myLanguageManager.PATIENTS_PERSONALDATA_SEX + ": </b>" + ((this.getSex().equals(Sex.MALE))?Activa.myLanguageManager.PSW_REG_MALE:Activa.myLanguageManager.PSW_REG_FEMALE) + "<br />"; 
		ret += "<b>" + Activa.myLanguageManager.PATIENTS_PERSONALDATA_NATION + ": </b>" + ctrToAdd + "<br />";
		ret += "<b>" + Activa.myLanguageManager.PATIENTS_PERSONALDATA_BIRTHDATE + ": </b>" + ActivaUtil.dateToReadableString(this.getBirthdate()) + "<br />" + 
			"<b>" + Activa.myLanguageManager.PATIENTS_PERSONALDATA_EMAIL + ": </b>" + this.getEmail() + "<br />" ;
		return Html.fromHtml(ret);
	}
	
	public Spanned getMedicalRecordsSpan() {
		String span = "<br/><big><b>" + Activa.myLanguageManager.PATIENTS_HISTORY_MEDICALRECORD + "</b></big><br />";
		Enumeration<HistoryRecord> enumer = history.elements();
		while (enumer.hasMoreElements()) {
			HistoryRecord record = enumer.nextElement();
			span += record.getRecordSpanText();
		}
		return Html.fromHtml(span);
	}

}
