package com.o2hlink.activa.patient;

import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

import com.o2hlink.activa.Activa;
import com.o2hlink.activa.ActivaUtil;

import android.text.Html;
import android.text.Spanned;

public class Patient {
	
	/**
	 * ID of the patient
	 */
	public long id;
	
	/**
	 * Fistname of the patient
	 */
	public String firstname;
	
	/**
	 * Lastname of the patient
	 */
	public String lastname;
	
	/**
	 * Nationality of the patient
	 */
	public String nation;
	
	/**
	 * Email of the patient
	 */
	public String email;
	
	/**
	 * Date of birth
	 */
	public Date birthdate;
	
	/**
	 * True if the user is male, false if she's female.
	 */
	public boolean isMale;
	
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
	public ExerciseSample lastExercise;
	
	/**
	 * Last spirometry
	 */
	public SpirometrySample lastSpirometry;
	
	/**
	 * Constructor
	 */
	public Patient() {
		this.isMale = true;
		this.history = new Hashtable<Long, HistoryRecord>();
	}
	
	public Spanned getPatientPersonalData() {
		return Html.fromHtml(
			"<big><b>" + Activa.myLanguageManager.PATIENTS_HISTORY_PERSONALDATA + "</b></big><br /><br />" +
			"<b>" + Activa.myLanguageManager.PATIENTS_PERSONALDATA_NAME + ": </b>" + firstname + " " + lastname + "<br />" + 
			"<b>" + Activa.myLanguageManager.PATIENTS_PERSONALDATA_SEX + ": </b>" + (isMale?Activa.myLanguageManager.PSW_REG_MALE:Activa.myLanguageManager.PSW_REG_FEMALE) + "<br />" + 
			"<b>" + Activa.myLanguageManager.PATIENTS_PERSONALDATA_NATION + ": </b>" + nation + "<br />" + 
			"<b>" + Activa.myLanguageManager.PATIENTS_PERSONALDATA_BIRTHDATE + ": </b>" + ActivaUtil.dateToReadableString(birthdate) + "<br />" + 
			"<b>" + Activa.myLanguageManager.PATIENTS_PERSONALDATA_EMAIL + ": </b>" + email + "<br />" 
	    );
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
