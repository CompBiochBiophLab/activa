package com.o2hlink.pimtools.patient;

import java.util.ArrayList;
import java.util.List;

import android.text.Html;
import android.text.Spanned;

import com.o2hlink.activ8.client.entity.Spirometry;
import com.o2hlink.activ8.client.entity.SpirometryFlow;
import com.o2hlink.pimtools.Activa;
import com.o2hlink.pimtools.ActivaUtil;
import com.o2hlink.pimtools.data.sensor.SensorManager;

public class SpirometrySample extends Spirometry {
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
	public SpirometrySample() {
		super();
		this.flow = new ArrayList<Double>();
		this.time = new ArrayList<Double>();
	}	

	public SpirometrySample(Spirometry spiro, SpirometryFlow graphs) {
		super();
		this.flow = new ArrayList<Double>();
		this.time = new ArrayList<Double>();
		this.setDate(spiro.getDate());
		this.setEvent(spiro.getEvent());
		this.setForcedExpiratoryVolume(spiro.getForcedExpiratoryVolume());
		this.setForcedVitalCapacity(spiro.getForcedVitalCapacity());
		this.setPeakExpiratoryFlow(spiro.getPeakExpiratoryFlow());
		if (graphs != null ) {
			this.flow.addAll(graphs.getFlow());
			this.time.addAll(graphs.getTime());
		}
	}

	public SpirometrySample(Spirometry spiro) {
		super();
		this.flow = new ArrayList<Double>();
		this.time = new ArrayList<Double>();
		this.setDate(spiro.getDate());
		this.setEvent(spiro.getEvent());
		this.setForcedExpiratoryVolume(spiro.getForcedExpiratoryVolume());
		this.setForcedVitalCapacity(spiro.getForcedVitalCapacity());
		this.setPeakExpiratoryFlow(spiro.getPeakExpiratoryFlow());
	}
	
	public Spanned getSpirometrySpanData() {
		return Html.fromHtml(
			"<big><b>" + Activa.myLanguageManager.PATIENTS_HISTORY_SPIROMETRY + " " + ActivaUtil.dateToReadableString(this.getDate()) + " " + ActivaUtil.timeToReadableString(this.getDate()) + "</b></big><br/><br/>" +
			"<b>" + Activa.myLanguageManager.SENSORS_DATA_FEV1 + ": </b>" + String.format("%.2f", this.getForcedExpiratoryVolume()) + " " + SensorManager.getUnitForMeasurement(SensorManager.getUnitIDForMeasurementID(SensorManager.DATAID_FEV1)) + "<br/>" + 
			"<b>" + Activa.myLanguageManager.SENSORS_DATA_FVC + ": </b>" + String.format("%.2f", this.getForcedVitalCapacity()) + " " + SensorManager.getUnitForMeasurement(SensorManager.getUnitIDForMeasurementID(SensorManager.DATAID_FVC)) + "<br/>" +
			"<b>" + Activa.myLanguageManager.SENSORS_DATA_PEF + ": </b>" + String.format("%.2f", this.getPeakExpiratoryFlow()) + " " + SensorManager.getUnitForMeasurement(SensorManager.getUnitIDForMeasurementID(SensorManager.DATAID_PEF)) + "<br/>" 
		);
	}

}
