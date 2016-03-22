package com.o2hlink.activa.patient;

import java.util.Date;

import android.text.Html;
import android.text.Spanned;

import com.o2hlink.activa.Activa;
import com.o2hlink.activa.data.sensor.SensorManager;

public class ExerciseSample extends Sample {

	/**
	 * Heart rate values
	 */
	public double heartRateAvrg;

	public double heartRatePeak;

	public double heartRateLow;
	
	/**
	 * Oxygen saturation values
	 */
	public double so2Avrg;

	public double so2Peak;

	public double so2Low;
	
	/**
	 * Borg scale values
	 */
	
	public double initialDyspnea;

	public double initialFatigue;
	
	public double finalDyspnea;

	public double finalFatigue;
	
	/**
	 * Movement values.
	 */
	public int steps;
	
	public int stops;
	
	public double distance;
	
	/**
	 * Time values.
	 */
	public int time;
	
	/**
	 * The public constructor.
	 * @param eventId
	 * @param date
	 */
	public ExerciseSample(long eventId, Date date) {
		super(eventId, date);
	}	
	
	public Spanned getExerciseSpanData() {
		return Html.fromHtml(
			"<big><b>" + Activa.myLanguageManager.PATIENTS_HISTORY_LAST + " " + Activa.myLanguageManager.PATIENTS_HISTORY_EXERCISE + "</b></big><br/><br/>" +
			"<b>" + Activa.myLanguageManager.SENSORS_DATA_HR_AVRG + ": </b>" + String.format("%.1f", heartRateAvrg) + " " + SensorManager.getUnitForMeasurement(SensorManager.getUnitIDForMeasurementID(SensorManager.DATAID_HR_AVRG)) + "<br/>" + 
			"<b>" + Activa.myLanguageManager.SENSORS_DATA_HR_PEAK + ": </b>" + (int) heartRatePeak + " " + SensorManager.getUnitForMeasurement(SensorManager.getUnitIDForMeasurementID(SensorManager.DATAID_HR_PEAK)) + "<br/>" + 
			"<b>" + Activa.myLanguageManager.SENSORS_DATA_HR_LOW + ": </b>" + (int) heartRateLow + " " + SensorManager.getUnitForMeasurement(SensorManager.getUnitIDForMeasurementID(SensorManager.DATAID_HR_LOW)) + "<br/><br/>" + 
			"<b>" + Activa.myLanguageManager.SENSORS_DATA_SO2_AVRG + ": </b>" + String.format("%.1f", so2Avrg) + " " + SensorManager.getUnitForMeasurement(SensorManager.getUnitIDForMeasurementID(SensorManager.DATAID_SO2_AVRG)) + "<br/>" +
			"<b>" + Activa.myLanguageManager.SENSORS_DATA_SO2_PEAK + ": </b>" + (int) so2Peak + " " + SensorManager.getUnitForMeasurement(SensorManager.getUnitIDForMeasurementID(SensorManager.DATAID_SO2_PEAK)) + "<br/>" +
			"<b>" + Activa.myLanguageManager.SENSORS_DATA_SO2_LOW + ": </b>" + (int) so2Low + " " + SensorManager.getUnitForMeasurement(SensorManager.getUnitIDForMeasurementID(SensorManager.DATAID_SO2_LOW)) + "<br/><br/>" +
			"<b>" + Activa.myLanguageManager.SENSORS_DATA_INITIAL_DYS + ": </b>" + String.format("%.1f", initialDyspnea) + " " + SensorManager.getUnitForMeasurement(SensorManager.getUnitIDForMeasurementID(SensorManager.DATAID_BORG_AIR_PRE)) + "<br/>" +
			"<b>" + Activa.myLanguageManager.SENSORS_DATA_INITIAL_FAT + ": </b>" + String.format("%.1f", initialFatigue) + " " + SensorManager.getUnitForMeasurement(SensorManager.getUnitIDForMeasurementID(SensorManager.DATAID_BORG_FATIGUE_PRE)) + "<br/>" +
			"<b>" + Activa.myLanguageManager.SENSORS_DATA_FINAL_DYS + ": </b>" + String.format("%.1f", finalDyspnea) + " " + SensorManager.getUnitForMeasurement(SensorManager.getUnitIDForMeasurementID(SensorManager.DATAID_BORG_AIR_POST)) + "<br/>" +
			"<b>" + Activa.myLanguageManager.SENSORS_DATA_FINAL_FAT + ": </b>" + String.format("%.1f", finalFatigue) + " " + SensorManager.getUnitForMeasurement(SensorManager.getUnitIDForMeasurementID(SensorManager.DATAID_BORG_FATIGUE_POST)) + "<br/>" +
			"<b>" + Activa.myLanguageManager.SENSORS_DATA_STEPS + ": </b>" + steps + " " + SensorManager.getUnitForMeasurement(SensorManager.getUnitIDForMeasurementID(SensorManager.DATAID_STEPS)) + "<br/>" +
			"<b>" + Activa.myLanguageManager.SENSORS_DATA_STOPS + ": </b>" + stops + " " + SensorManager.getUnitForMeasurement(SensorManager.getUnitIDForMeasurementID(SensorManager.DATAID_STOPS)) + "<br/>" +
			"<b>" + Activa.myLanguageManager.SENSORS_DATA_DISTANCE + ": </b>" + (int) distance + " " + SensorManager.getUnitForMeasurement(SensorManager.getUnitIDForMeasurementID(SensorManager.DATAID_DISTANCE)) + "<br/><br/>" +
			"<b>" + Activa.myLanguageManager.SENSORS_DATA_TIME_EXE + ": </b>" + (int) time + " " + SensorManager.getUnitForMeasurement(SensorManager.getUnitIDForMeasurementID(SensorManager.DATAID_TIME_EXE)) + "<br/><br/>"
		);
	}

}
