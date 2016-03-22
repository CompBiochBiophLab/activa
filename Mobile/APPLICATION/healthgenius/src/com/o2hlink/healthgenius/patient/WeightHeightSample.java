package com.o2hlink.healthgenius.patient;

import android.text.Html;
import android.text.Spanned;

import com.o2hlink.activ8.client.entity.Pulseoxymetry;
import com.o2hlink.activ8.client.entity.WeightHeight;
import com.o2hlink.healthgenius.HealthGenius;
import com.o2hlink.healthgenius.HealthGeniusUtil;
import com.o2hlink.healthgenius.data.sensor.SensorManager;

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
			"<big><b>" + HealthGenius.myLanguageManager.SENSORS_WEIGHTHEIGTH_TITLE + " " + HealthGeniusUtil.dateToReadableString(this.getDate()) + " " + HealthGeniusUtil.timeToReadableString(this.getDate()) + "</b></big><br/><br/>" +
			"<b>" + HealthGenius.myLanguageManager.SENSORS_DATA_WEIGHT + ": </b>" + (int)this.getWeight() + " " + SensorManager.getUnitForMeasurement(SensorManager.getUnitIDForMeasurementID(SensorManager.DATAID_WEIGHT)) + "<br/>" + 
			"<b>" + HealthGenius.myLanguageManager.SENSORS_DATA_HEIGTH + ": </b>" + (int)this.getHeight() + " " + SensorManager.getUnitForMeasurement(SensorManager.getUnitIDForMeasurementID(SensorManager.DATAID_HEIGHT)) + "<br/>" 
		);
	}
	
}
