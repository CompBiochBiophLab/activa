package com.o2hlink.activa.patient;

import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;

import com.o2hlink.activa.Activa;
import com.o2hlink.activa.ActivaUtil;

public class HistoryRecord {
	
	public long id;
	
	public String diseaseName;
	
	public String diseaseDesc;
	
	public com.o2hlink.activ8.client.entity.HistoryRecord historyRecordForServices;
	
	public Hashtable<Long, Exploration> explorations;
	
	public Date date;
	
	public HistoryRecord(long id, String diseaseName, String diseaseDesc, Date date) {
		this.id = id;
		this.date = date;
		this.diseaseName = diseaseName;
		this.diseaseDesc = diseaseDesc;
		this.explorations = new Hashtable<Long, Exploration>();
	}
	
	public String getRecordSpanText() {
		String returning = "<br/><b>" + Activa.myLanguageManager.PATIENTS_MEDICALRECORD_DISEASE + ": </b>" + diseaseName + "<br/><br/>";
		Enumeration<Exploration> enumer = explorations.elements();
		while(enumer.hasMoreElements()) {
			Exploration exploration = enumer.nextElement();
			returning += "<b>- " + ActivaUtil.dateToReadableString(exploration.date) + ": </b>" + exploration.exploration + "<br/>";
		}
		return returning;
	}

}
