package com.o2hlink.activa.patient;

import java.util.Date;

import android.text.Html;
import android.text.Spanned;

import com.o2hlink.activa.Activa;
import com.o2hlink.activa.ActivaUtil;
import com.o2hlink.activa.data.sensor.SensorManager;

public class PulseoximetrySample extends Sample{

	/**
	 * Heart rate measurement
	 */
	public int heartRate;
	
	/**
	 * SO2 level measurement;
	 */
	public int so2;
	
	/**
	 * Public constructor
	 * @param eventId
	 * @param date
	 */
	public PulseoximetrySample(long eventId, Date date) {
		super(eventId, date);
	}	
	
	public Spanned getPulsioximetrySpanData() {
		return Html.fromHtml(
			"<big><b>" + Activa.myLanguageManager.PATIENTS_HISTORY_LAST + " " + Activa.myLanguageManager.PATIENTS_HISTORY_PULSEOXIMETRY + "</b></big><br/><br/>" +
			"<b>" + Activa.myLanguageManager.SENSORS_DATA_HR + ": </b>" + heartRate + " " + SensorManager.getUnitForMeasurement(SensorManager.getUnitIDForMeasurementID(SensorManager.DATAID_HR)) + "<br/>" + 
			"<b>" + Activa.myLanguageManager.SENSORS_DATA_SO2 + ": </b>" + so2 + " " + SensorManager.getUnitForMeasurement(SensorManager.getUnitIDForMeasurementID(SensorManager.DATAID_SO2)) + "<br/>" 
		);
	}
	
}
