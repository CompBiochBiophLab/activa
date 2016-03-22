package com.o2hlink.activa.patient;

import java.util.Date;
import java.util.List;

import android.text.Html;
import android.text.Spanned;

import com.o2hlink.activa.Activa;
import com.o2hlink.activa.data.sensor.SensorManager;

public class SpirometrySample extends Sample {

	
	/**
	 * Force Expiratory volume at 1 second
	 */
	public double fev1;
	
	/**
	 * Force Vital Capacity
	 */
	public double fvc;
	
	/**
	 * Peak Expiratory Flow
	 */
	public double pef;
	
	/**
	 * Flow graph values
	 */
	public List<Double> flow;
	
	/**
	 * Time graph values
	 */
	public List<Double> time;
	
	/**
	 * The public constructor.
	 * @param eventId
	 * @param date
	 */
	public SpirometrySample(long eventId, Date date) {
		super(eventId, date);
	}	
	
	public Spanned getSpirometrySpanData() {
		return Html.fromHtml(
			"<big><b>" + Activa.myLanguageManager.PATIENTS_HISTORY_LAST + " " + Activa.myLanguageManager.PATIENTS_HISTORY_SPIROMETRY + "</b></big><br/><br/>" +
			"<b>" + Activa.myLanguageManager.SENSORS_DATA_FEV1 + ": </b>" + String.format("%.2f", fev1) + " " + SensorManager.getUnitForMeasurement(SensorManager.getUnitIDForMeasurementID(SensorManager.DATAID_FEV1)) + "<br/>" + 
			"<b>" + Activa.myLanguageManager.SENSORS_DATA_FVC + ": </b>" + String.format("%.2f", fvc) + " " + SensorManager.getUnitForMeasurement(SensorManager.getUnitIDForMeasurementID(SensorManager.DATAID_FVC)) + "<br/>" +
			"<b>" + Activa.myLanguageManager.SENSORS_DATA_PEF + ": </b>" + String.format("%.2f", pef) + " " + SensorManager.getUnitForMeasurement(SensorManager.getUnitIDForMeasurementID(SensorManager.DATAID_PEF)) + "<br/>" 
		);
	}

}
