package com.o2hlink.activacentral.data.sensor;

import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;

import android.text.InputType;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.o2hlink.activacentral.Activa;
import com.o2hlink.activacentral.ActivaUtil;
import com.o2hlink.activacentral.background.SendSensorResult;
import com.o2hlink.activ8.client.entity.Height;
import com.o2hlink.activ8.client.entity.Sample;
import com.o2hlink.activ8.client.entity.WeightHeight;
import com.o2hlink.activacentral.R;

public class WeightAndHeight extends Sensor {
	
	public float weight;
	
	public int heigth;
	
	public WeightAndHeight() {
		this.name = Activa.myLanguageManager.SENSORS_WEIGHTHEIGTH_TITLE;
		this.icon = R.drawable.weight;
		this.id = SensorManager.ID_WEIGHT;
		this.results  = new Hashtable<Integer, Float>();
	}

	@Override
	public void finishMeasurements(boolean outcome, Hashtable<Integer, Float> results) {
		if (outcome) {
			String result = getSensorGlobalResult();
			int resultInt = Integer.parseInt(result.substring(0, 1));
			result = result.substring(2);
			if ((Activa.mySensorManager.eventAssociated != null)&&true)  {
				Activa.mySensorManager.eventAssociated.state = 0;
				Activa.myCalendarManager.saveCalendar();
			}
			Activa.myMobileManager.user.setHeight(heigth);
			Activa.myMobileManager.user.setWeight(weight);
			Activa.myMobileManager.user.setLastupdate(new Date());
			Activa.myMobileManager.saveUsers();
			Activa.myUIManager.loadSensorGlobalResult(result, resultInt, this);
		}
	}

	@Override
	public Sample getSample() {
		WeightHeight sample = new WeightHeight();
		sample.setDate(new Date());
		if (Activa.mySensorManager.eventAssociated != null)
			sample.setEvent(Activa.mySensorManager.eventAssociated.id);
		else sample.setEvent(null);
		sample.setWeight(this.results.get(SensorManager.DATAID_WEIGHT));
		sample.setHeight(this.results.get(SensorManager.DATAID_HEIGHT));
		return sample;
	}

	@Override
	public String getSensorGlobalResult() {
		weight = this.results.get(SensorManager.DATAID_WEIGHT);
		heigth = Math.round(this.results.get(SensorManager.DATAID_HEIGHT));
		double bmi = weight/Math.pow((((double)heigth)/100), 2);
		if (bmi < 16.0)
			return "0:" + Activa.myLanguageManager.SENSORS_WEIGHT_MESSAGE0;
		else if (bmi < 18.5)
			return "1:" + Activa.myLanguageManager.SENSORS_WEIGHT_MESSAGE1;
		else if (bmi < 25)
			return "2:" + Activa.myLanguageManager.SENSORS_WEIGHT_MESSAGE2;
		else if (bmi < 30)
			return "1:" + Activa.myLanguageManager.SENSORS_WEIGHT_MESSAGE3;
		else if (bmi < 40)
			return "1:" + Activa.myLanguageManager.SENSORS_WEIGHT_MESSAGE4;
		else
			return "0:" + Activa.myLanguageManager.SENSORS_WEIGHT_MESSAGE5;
	}

	@Override
	public String getSensorSampleForPending() {
		String returned = "<MEASUREMENT ID=\"" + com.o2hlink.activacentral.data.sensor.SensorManager.ID_WEIGHT;
		returned += "\" DATE=\"" + ActivaUtil.dateToXMLDate(this.sampleDate);
		returned += "\" EVENT=\"";
		if (this.sampleEventId != null) {
			returned +=  this.sampleEventId + "\">";		
		}
		else returned += "\">";
		Enumeration<Integer> keys = this.results.keys();
		while (keys.hasMoreElements()) {
			int key = keys.nextElement();
			returned += "<DATA ID=\"" + key + "\" VALUE=\"" + this.results.get(key) + "\"/>";
		}
		returned += "</MEASUREMENT>";
		return returned;
	}

	@Override
	public String passSensorResultToXML() {
		String returned = "<EVENT ID=\"1\" DATETIME=\"" + Activa.myMobileManager.device.getDateTime();
		returned += "\" IDGROUP=\"\" IDAGENDA=\"";
		if (Activa.mySensorManager.eventAssociated != null) {
			Date dateid = new Date(Long.parseLong(Activa.mySensorManager.eventAssociated.id)*1000 - 3600000l);
			returned +=  ActivaUtil.dateToXMLDate(dateid) + "0" + "\">";		
		}
		else returned += "\">";
		Enumeration<Integer> keys = this.results.keys();
		while (keys.hasMoreElements()) {
			int key = keys.nextElement();
			returned += "<DATA ID=\"" + key + "\" VALUE=\"" + this.results.get(key);
			returned += "\" UNITS=\"" + SensorManager.getUnitIDForMeasurementID(key) + "\"/>";
		}
		returned += "</EVENT>";
		return returned;
	}

	@Override
	public void startMeasurement() {
		Activa.myApp.setContentView(R.layout.data);
		((TextView) Activa.myApp.findViewById(R.id.startText)).setText(Activa.myLanguageManager.PSW_REG_TITLE);
		((TextView) Activa.myApp.findViewById(R.id.requestText)).setText(Activa.myLanguageManager.PSW_REG_DATAREQUEST);
		((TextView) Activa.myApp.findViewById(R.id.requestWeight)).setText(Activa.myLanguageManager.PSW_REG_WEIGHT);
		((TextView) Activa.myApp.findViewById(R.id.requestHeight)).setText(Activa.myLanguageManager.PSW_REG_HEIGHT);
		final EditText weight = (EditText) Activa.myApp.findViewById(R.id.weightText);
		weight.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL);
		final EditText height = (EditText) Activa.myApp.findViewById(R.id.heightText);
		height.setInputType(InputType.TYPE_CLASS_NUMBER);
		ImageButton ok = (ImageButton) Activa.myApp.findViewById(R.id.next);
		ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try{
					int heightText = Integer.parseInt(height.getText().toString());
				    results.put(SensorManager.DATAID_HEIGHT, (float)heightText);
					float weightText = Float.parseFloat(weight.getText().toString().replaceAll(",", "."));
				    results.put(SensorManager.DATAID_WEIGHT, weightText);
					Activa.myMobileManager.user.setHeight(heightText);
					Activa.myMobileManager.user.setWeight(weightText);
					Activa.myMobileManager.user.setLastupdate(new Date());
					finishMeasurements(true, results);
				} catch (NumberFormatException e) {
					finishMeasurements(false, results);
				}
			}
		});
		ImageButton no = (ImageButton) Activa.myApp.findViewById(R.id.back);
		no.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Activa.myUIManager.loadGeneratedMainScreen(false, false, false);
			}
		});		
	}

}
