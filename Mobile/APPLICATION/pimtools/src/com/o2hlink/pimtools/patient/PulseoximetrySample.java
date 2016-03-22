package com.o2hlink.pimtools.patient;

import android.text.Html;
import android.text.Spanned;

import com.o2hlink.activ8.client.entity.Pulseoxymetry;
import com.o2hlink.pimtools.Activa;
import com.o2hlink.pimtools.ActivaUtil;
import com.o2hlink.pimtools.data.sensor.SensorManager;

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
			"<big><b>" + Activa.myLanguageManager.PATIENTS_HISTORY_PULSEOXIMETRY + " " + ActivaUtil.dateToReadableString(this.getDate()) + " " + ActivaUtil.timeToReadableString(this.getDate()) + "</b></big><br/><br/>" +
			"<b>" + Activa.myLanguageManager.SENSORS_DATA_HR + ": </b>" + (int)this.getHeartRate() + " " + SensorManager.getUnitForMeasurement(SensorManager.getUnitIDForMeasurementID(SensorManager.DATAID_HR)) + "<br/>" + 
			"<b>" + Activa.myLanguageManager.SENSORS_DATA_SO2 + ": </b>" + (int)this.getOxygenSaturation() + " " + SensorManager.getUnitForMeasurement(SensorManager.getUnitIDForMeasurementID(SensorManager.DATAID_SO2)) + "<br/>" 
		);
	}
	
}
