package com.o2hlink.pimtools.patient;

import android.text.Html;
import android.text.Spanned;

import com.o2hlink.activ8.client.entity.Pulseoxymetry;
import com.o2hlink.activ8.client.entity.WeightHeight;
import com.o2hlink.pimtools.Activa;
import com.o2hlink.pimtools.ActivaUtil;
import com.o2hlink.pimtools.data.sensor.SensorManager;

public class WeightHeightSample extends WeightHeight{
	
	/**
	 * Public constructor
	 * @param eventId
	 * @param date
	 */
	public WeightHeightSample() {
		super();
	}	
	
	public WeightHeightSample(WeightHeight weightheight) {
		super();
		this.setDate(weightheight.getDate());
		this.setEvent(weightheight.getEvent());
		this.setWeight(weightheight.getWeight());
		this.setHeight(weightheight.getHeight());
	}	
	
	public Spanned getWeightHeightSpanData() {
		return Html.fromHtml(
			"<big><b>" + Activa.myLanguageManager.SENSORS_WEIGHTHEIGTH_TITLE + " " + ActivaUtil.dateToReadableString(this.getDate()) + " " + ActivaUtil.timeToReadableString(this.getDate()) + "</b></big><br/><br/>" +
			"<b>" + Activa.myLanguageManager.SENSORS_DATA_WEIGHT + ": </b>" + (int)this.getWeight() + " " + SensorManager.getUnitForMeasurement(SensorManager.getUnitIDForMeasurementID(SensorManager.DATAID_WEIGHT)) + "<br/>" + 
			"<b>" + Activa.myLanguageManager.SENSORS_DATA_HEIGTH + ": </b>" + (int)this.getHeight() + " " + SensorManager.getUnitForMeasurement(SensorManager.getUnitIDForMeasurementID(SensorManager.DATAID_HEIGHT)) + "<br/>" 
		);
	}
	
}
