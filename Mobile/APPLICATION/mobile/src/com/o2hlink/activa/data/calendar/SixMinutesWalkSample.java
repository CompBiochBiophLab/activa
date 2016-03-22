package com.o2hlink.activa.data.calendar;

import java.util.ArrayList;
import java.util.List;

import android.text.Html;
import android.text.Spanned;

import com.o2hlink.activ8.client.entity.SixMinutesWalk;
import com.o2hlink.activ8.client.entity.SixMinutesWalkFlow;
import com.o2hlink.activ8.client.entity.SpirometryFlow;
import com.o2hlink.activa.Activa;
import com.o2hlink.activa.ActivaUtil;
import com.o2hlink.activa.data.sensor.SensorManager;

public class SixMinutesWalkSample extends SixMinutesWalk {
	
	/**
	 * Flow graph values
	 */
	public List<Double> hrtrack;
	
	/**
	 * Flow graph values
	 */
	public List<Double> so2track;
	
	/**
	 * Time graph values
	 */
	public List<Double> time;
	
	/**
	 * The public constructor.
	 * @param eventId
	 * @param date
	 */
	public SixMinutesWalkSample() {
		super();
		time = new ArrayList<Double>();
		hrtrack = new ArrayList<Double>();
		so2track = new ArrayList<Double>();
	}	

	public SixMinutesWalkSample(SixMinutesWalk walk, SixMinutesWalkFlow graphs) {
		super();
		time = new ArrayList<Double>();
		hrtrack = new ArrayList<Double>();
		so2track = new ArrayList<Double>();
		this.setDate(walk.getDate());
		this.setEvent(walk.getEvent());
		this.setHeartRateAverage(walk.getHeartRateAverage());
		this.setHeartRatePeak(walk.getHeartRatePeak());
		this.setHeartRateLow(walk.getHeartRateLow());
		this.setOxygenAverage(walk.getOxygenAverage());
		this.setOxygenPeak(walk.getOxygenPeak());
		this.setOxygenLow(walk.getOxygenLow());
		this.setInitialDyspnea(walk.getInitialDyspnea());
		this.setInitialFatigue(walk.getInitialFatigue());
		this.setFinalDyspnea(walk.getFinalDyspnea());
		this.setFinalFatigue(walk.getFinalFatigue());
		this.setDistance(walk.getDistance());
		this.setTime(walk.getTime());
		this.setSteps(walk.getSteps());
		this.setStops(walk.getStops());
		if (graphs != null ) {
			this.hrtrack.addAll(graphs.getHeartRate());
			this.so2track.addAll(graphs.getOxygenSaturation());
			this.time.addAll(graphs.getTime());
		}
	}	

	public SixMinutesWalkSample(SixMinutesWalk walk) {
		super();
		time = new ArrayList<Double>();
		hrtrack = new ArrayList<Double>();
		so2track = new ArrayList<Double>();
		this.setDate(walk.getDate());
		this.setEvent(walk.getEvent());
		this.setHeartRateAverage(walk.getHeartRateAverage());
		this.setHeartRatePeak(walk.getHeartRatePeak());
		this.setHeartRateLow(walk.getHeartRateLow());
		this.setOxygenAverage(walk.getOxygenAverage());
		this.setOxygenPeak(walk.getOxygenPeak());
		this.setOxygenLow(walk.getOxygenLow());
		this.setInitialDyspnea(walk.getInitialDyspnea());
		this.setInitialFatigue(walk.getInitialFatigue());
		this.setFinalDyspnea(walk.getFinalDyspnea());
		this.setFinalFatigue(walk.getFinalFatigue());
		this.setDistance(walk.getDistance());
		this.setTime(walk.getTime());
		this.setSteps(walk.getSteps());
		this.setStops(walk.getStops());
	}	
	
	public Spanned getExerciseSpanData() {
		return Html.fromHtml(
			"<big><b>" + Activa.myLanguageManager.PATIENTS_HISTORY_EXERCISE + " " + ActivaUtil.dateToReadableString(this.getDate()) + " " + ActivaUtil.timeToReadableString(this.getDate()) + "</b></big><br/><br/>" +
			"<b>" + Activa.myLanguageManager.SENSORS_DATA_HR_AVRG + ": </b>" + String.format("%.1f", this.getHeartRateAverage()) + " " + SensorManager.getUnitForMeasurement(SensorManager.getUnitIDForMeasurementID(SensorManager.DATAID_HR_AVRG)) + "<br/>" + 
			"<b>" + Activa.myLanguageManager.SENSORS_DATA_HR_PEAK + ": </b>" + (int) this.getHeartRatePeak() + " " + SensorManager.getUnitForMeasurement(SensorManager.getUnitIDForMeasurementID(SensorManager.DATAID_HR_PEAK)) + "<br/>" + 
			"<b>" + Activa.myLanguageManager.SENSORS_DATA_HR_LOW + ": </b>" + (int) this.getHeartRateLow() + " " + SensorManager.getUnitForMeasurement(SensorManager.getUnitIDForMeasurementID(SensorManager.DATAID_HR_LOW)) + "<br/><br/>" + 
			"<b>" + Activa.myLanguageManager.SENSORS_DATA_SO2_AVRG + ": </b>" + String.format("%.1f", this.getOxygenAverage()) + " " + SensorManager.getUnitForMeasurement(SensorManager.getUnitIDForMeasurementID(SensorManager.DATAID_SO2_AVRG)) + "<br/>" +
			"<b>" + Activa.myLanguageManager.SENSORS_DATA_SO2_PEAK + ": </b>" + (int) this.getOxygenPeak() + " " + SensorManager.getUnitForMeasurement(SensorManager.getUnitIDForMeasurementID(SensorManager.DATAID_SO2_PEAK)) + "<br/>" +
			"<b>" + Activa.myLanguageManager.SENSORS_DATA_SO2_LOW + ": </b>" + (int) this.getOxygenLow() + " " + SensorManager.getUnitForMeasurement(SensorManager.getUnitIDForMeasurementID(SensorManager.DATAID_SO2_LOW)) + "<br/><br/>" +
			"<b>" + Activa.myLanguageManager.SENSORS_DATA_INITIAL_DYS + ": </b>" + String.format("%.1f", this.getInitialDyspnea()) + " " + SensorManager.getUnitForMeasurement(SensorManager.getUnitIDForMeasurementID(SensorManager.DATAID_BORG_AIR_PRE)) + "<br/>" +
			"<b>" + Activa.myLanguageManager.SENSORS_DATA_INITIAL_FAT + ": </b>" + String.format("%.1f", this.getInitialFatigue()) + " " + SensorManager.getUnitForMeasurement(SensorManager.getUnitIDForMeasurementID(SensorManager.DATAID_BORG_FATIGUE_PRE)) + "<br/>" +
			"<b>" + Activa.myLanguageManager.SENSORS_DATA_FINAL_DYS + ": </b>" + String.format("%.1f", this.getFinalDyspnea()) + " " + SensorManager.getUnitForMeasurement(SensorManager.getUnitIDForMeasurementID(SensorManager.DATAID_BORG_AIR_POST)) + "<br/>" +
			"<b>" + Activa.myLanguageManager.SENSORS_DATA_FINAL_FAT + ": </b>" + String.format("%.1f", this.getFinalFatigue()) + " " + SensorManager.getUnitForMeasurement(SensorManager.getUnitIDForMeasurementID(SensorManager.DATAID_BORG_FATIGUE_POST)) + "<br/>" +
			"<b>" + Activa.myLanguageManager.SENSORS_DATA_STEPS + ": </b>" + this.getSteps() + " " + SensorManager.getUnitForMeasurement(SensorManager.getUnitIDForMeasurementID(SensorManager.DATAID_STEPS)) + "<br/>" +
			"<b>" + Activa.myLanguageManager.SENSORS_DATA_STOPS + ": </b>" + this.getStops() + " " + SensorManager.getUnitForMeasurement(SensorManager.getUnitIDForMeasurementID(SensorManager.DATAID_STOPS)) + "<br/>" +
			"<b>" + Activa.myLanguageManager.SENSORS_DATA_DISTANCE + ": </b>" + (int) this.getDistance() + " " + SensorManager.getUnitForMeasurement(SensorManager.getUnitIDForMeasurementID(SensorManager.DATAID_DISTANCE)) + "<br/><br/>" +
			"<b>" + Activa.myLanguageManager.SENSORS_DATA_TIME_EXE + ": </b>" + (int) this.getTime() + " " + SensorManager.getUnitForMeasurement(SensorManager.getUnitIDForMeasurementID(SensorManager.DATAID_TIME_EXE)) + "<br/><br/>"
		);
	}

}
