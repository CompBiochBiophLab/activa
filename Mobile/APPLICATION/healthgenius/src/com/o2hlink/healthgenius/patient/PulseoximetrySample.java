package com.o2hlink.healthgenius.patient;

import android.text.Html;
import android.text.Spanned;

import com.o2hlink.activ8.client.entity.Pulseoxymetry;
import com.o2hlink.healthgenius.HealthGenius;
import com.o2hlink.healthgenius.HealthGeniusUtil;
import com.o2hlink.healthgenius.data.sensor.SensorManager;

public class PulseoximetrySample extends com.o2hlink.activ8.client.entity.Pulseoxymetry{
	
	/**
	 * Public constructor
	 * @param eventId
	 * @param date
	 */
	public PulseoximetrySample() {
		super();
	}	
	
	public PulseoximetrySample(Pulseoxymetry pulseoximetry) {
		super();
		this.setDate(pulseoximetry.getDate());
		this.setEvent(pulseoximetry.getEvent());
		this.setHeartRate(pulseoximetry.getHeartRate());
		this.setOxygenSaturation(pulseoximetry.getOxygenSaturation());
	}	
	
	public Spanned getPulsioximetrySpanData() {
		return Html.fromHtml(
			"<big><b>" + HealthGenius.myLanguageManager.PATIENTS_HISTORY_PULSEOXIMETRY + " " + HealthGeniusUtil.dateToReadableString(this.getDate()) + " " + HealthGeniusUtil.timeToReadableString(this.getDate()) + "</b></big><br/><br/>" +
			"<b>" + HealthGenius.myLanguageManager.SENSORS_DATA_HR + ": </b>" + (int)this.getHeartRate() + " " + SensorManager.getUnitForMeasurement(SensorManager.getUnitIDForMeasurementID(SensorManager.DATAID_HR)) + "<br/>" + 
			"<b>" + HealthGenius.myLanguageManager.SENSORS_DATA_SO2 + ": </b>" + (int)this.getOxygenSaturation() + " " + SensorManager.getUnitForMeasurement(SensorManager.getUnitIDForMeasurementID(SensorManager.DATAID_SO2)) + "<br/>" 
		);
	}
	
}
