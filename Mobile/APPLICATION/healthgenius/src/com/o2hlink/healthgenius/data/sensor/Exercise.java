package com.o2hlink.healthgenius.data.sensor;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Set;
import java.util.Vector;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.opengl.Visibility;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.os.Vibrator;
import android.os.PowerManager.WakeLock;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TableLayout.LayoutParams;

import com.o2hlink.activ8.client.entity.MultiQuestion;
import com.o2hlink.activ8.client.entity.NumericQuestion;
import com.o2hlink.activ8.client.entity.Sample;
import com.o2hlink.activ8.client.entity.SixMinutesWalk;
import com.o2hlink.activ8.common.entity.ExerciseType;
import com.o2hlink.healthgenius.HealthGenius;
import com.o2hlink.healthgenius.HealthGeniusUtil;
import com.o2hlink.healthgenius.R;
import com.o2hlink.healthgenius.background.SendSensorResult;
import com.o2hlink.healthgenius.ui.UIManager;

public class Exercise extends com.o2hlink.healthgenius.data.sensor.Sensor {
	
	public ExerciseRunnable exerciseRunnabe;
	
	public ExerciseNotRunnable exerciseNotRunnabe;
	
	public boolean track = false;
	
	public Exercise instance;
	
	public HashMap<Float,Float> hrtrack;

	public HashMap<Float,Float> so2track;
	
	float borgAirPre = 0.0f;
	
	float borgAirPost = 0.0f;
	
	float borgFatiguePre = 0.0f;
	
	float borgFatiguePost = 0.0f;
	
	int firstquestans = 0;
	
	int secquestans = 0;
	
	int thrquestans = 0;
	
	long time;
	
	WakeLock wakeLock;
	
	int[] order = {com.o2hlink.healthgenius.data.sensor.SensorManager.DATAID_HR_INIT, com.o2hlink.healthgenius.data.sensor.SensorManager.DATAID_HR_FINAL, com.o2hlink.healthgenius.data.sensor.SensorManager.DATAID_HR_AVRG, com.o2hlink.healthgenius.data.sensor.SensorManager.DATAID_HR_PEAK, com.o2hlink.healthgenius.data.sensor.SensorManager.DATAID_HR_LOW, -1,
		com.o2hlink.healthgenius.data.sensor.SensorManager.DATAID_SO2_INIT, com.o2hlink.healthgenius.data.sensor.SensorManager.DATAID_SO2_FINAL, com.o2hlink.healthgenius.data.sensor.SensorManager.DATAID_SO2_AVRG, com.o2hlink.healthgenius.data.sensor.SensorManager.DATAID_SO2_PEAK, com.o2hlink.healthgenius.data.sensor.SensorManager.DATAID_SO2_LOW, -1,
		com.o2hlink.healthgenius.data.sensor.SensorManager.DATAID_DISTANCE, com.o2hlink.healthgenius.data.sensor.SensorManager.DATAID_STEPS, com.o2hlink.healthgenius.data.sensor.SensorManager.DATAID_STOPS, -1,
		com.o2hlink.healthgenius.data.sensor.SensorManager.DATAID_TIME_EXE, com.o2hlink.healthgenius.data.sensor.SensorManager.DATAID_TIME_DATA};

	public int state;
	
	Hashtable<Long,String> timedMessages;

	private NumericQuestion borgAir;

	private NumericQuestion borgFatigue;

	public Exercise() {
		this.name = HealthGenius.myLanguageManager.SENSORS_EXERCISE_TITLE;
		this.icon = R.drawable.sport;
		this.id = com.o2hlink.healthgenius.data.sensor.SensorManager.ID_EXERCISE;
		this.instance = this;
		this.state = 0;
		this.borgAir = new NumericQuestion();
		this.borgAir.setId(-1l);
		this.borgAir.setName(HealthGenius.myLanguageManager.PROGRAMS_WALKING_BORGAIR);
		this.borgAir.setOptional(false);
		this.borgFatigue = new NumericQuestion();
		this.borgFatigue.setId(-1l);
		this.borgFatigue.setName(HealthGenius.myLanguageManager.PROGRAMS_WALKING_BORGFATIGUE);
		this.borgFatigue.setOptional(false);
		this.time = 7200000;
	}

	@Override
	public Sample getSample() {
		com.o2hlink.activ8.client.entity.Exercise sample = new com.o2hlink.activ8.client.entity.Exercise();
		sample.setFinalDyspnea(this.results.get(com.o2hlink.healthgenius.data.sensor.SensorManager.DATAID_BORG_AIR_POST));
		sample.setFinalFatigue(this.results.get(com.o2hlink.healthgenius.data.sensor.SensorManager.DATAID_BORG_FATIGUE_POST));
		sample.setInitialDyspnea(this.results.get(com.o2hlink.healthgenius.data.sensor.SensorManager.DATAID_BORG_AIR_PRE));
		sample.setInitialFatigue(this.results.get(com.o2hlink.healthgenius.data.sensor.SensorManager.DATAID_BORG_FATIGUE_PRE));
		firstquestans = Math.round(this.results.get(com.o2hlink.healthgenius.data.sensor.SensorManager.DATAID_EXE_ONE));
		secquestans = Math.round(this.results.get(com.o2hlink.healthgenius.data.sensor.SensorManager.DATAID_EXE_TWO));
		thrquestans = Math.round(this.results.get(com.o2hlink.healthgenius.data.sensor.SensorManager.DATAID_EXE_THREE));
		switch (firstquestans) {
			case 1:
				sample.setType(ExerciseType.WALKING_FLAT);
				break;
			case 2:
				sample.setType(ExerciseType.WALKING_SLOPE);
				break;
			case 3:
				sample.setType(ExerciseType.PHYSICAL_VIDEOGAME);
				break;
			case 4:
				sample.setType(ExerciseType.RUNNING);
				break;
			case 5:
				sample.setType(ExerciseType.BYKE);
				break;
			case 6:
				sample.setType(ExerciseType.SWIMMING);
				break;
			case 7:
				sample.setType(ExerciseType.GYM);
				break;
			default:
				sample.setType(ExerciseType.WALKING_FLAT);
				break;
		}
		sample.setPerceivedFatigue(thrquestans);
		sample.setPerceivedEffort(secquestans);
		sample.setDistance(new Double(this.results.get(com.o2hlink.healthgenius.data.sensor.SensorManager.DATAID_DISTANCE)));
		sample.setSteps(Float.valueOf(this.results.get(com.o2hlink.healthgenius.data.sensor.SensorManager.DATAID_STEPS)).intValue());
		sample.setStops(Float.valueOf(this.results.get(com.o2hlink.healthgenius.data.sensor.SensorManager.DATAID_STOPS)).intValue());
		sample.setTime(this.results.get(com.o2hlink.healthgenius.data.sensor.SensorManager.DATAID_TIME_EXE));
		sample.setHeartRateInitial(new Double(this.results.get(com.o2hlink.healthgenius.data.sensor.SensorManager.DATAID_HR_INIT)));
		sample.setHeartRateFinal(new Double(this.results.get(com.o2hlink.healthgenius.data.sensor.SensorManager.DATAID_HR_FINAL)));
		sample.setHeartRateAverage(new Double(this.results.get(com.o2hlink.healthgenius.data.sensor.SensorManager.DATAID_HR_AVRG)));
		sample.setHeartRateLow(new Double(this.results.get(com.o2hlink.healthgenius.data.sensor.SensorManager.DATAID_HR_LOW)));
		sample.setHeartRatePeak(new Double(this.results.get(com.o2hlink.healthgenius.data.sensor.SensorManager.DATAID_HR_PEAK)));
		sample.setOxygenInitial(new Double(this.results.get(com.o2hlink.healthgenius.data.sensor.SensorManager.DATAID_SO2_INIT)));
		sample.setOxygenFinal(new Double(this.results.get(com.o2hlink.healthgenius.data.sensor.SensorManager.DATAID_SO2_FINAL)));
		sample.setOxygenAverage(new Double(this.results.get(com.o2hlink.healthgenius.data.sensor.SensorManager.DATAID_SO2_AVRG)));
		sample.setOxygenLow(new Double(this.results.get(com.o2hlink.healthgenius.data.sensor.SensorManager.DATAID_SO2_LOW)));
		sample.setOxygenPeak(new Double(this.results.get(com.o2hlink.healthgenius.data.sensor.SensorManager.DATAID_SO2_PEAK)));
		sample.setDate(new Date());
		if (HealthGenius.mySensorManager.eventAssociated != null)
			sample.setEvent(HealthGenius.mySensorManager.eventAssociated.id);
		else sample.setEvent(null);
		return sample;
	}

	@Override
	public String getSensorSampleForPending() {
		String returned = "<MEASUREMENT ID=\"" + com.o2hlink.healthgenius.data.sensor.SensorManager.ID_EXERCISE;
		returned += "\" DATE=\"" + HealthGeniusUtil.dateToXMLDate(this.sampleDate);
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

	public static com.o2hlink.healthgenius.data.sensor.Sensor getSampleFromPending(String xml) {
		Exercise sample = new Exercise();
		XmlPullParserFactory factory;
		try {
			factory = XmlPullParserFactory.newInstance();
	        factory.setNamespaceAware(true);
	        XmlPullParser info = factory.newPullParser();
	        info.setInput(new StringReader(xml));
	        int event = info.getEventType();
	        while (event != XmlPullParser.END_DOCUMENT) {
	            if(event == XmlPullParser.START_DOCUMENT) {
	            } else if(event == XmlPullParser.END_DOCUMENT) {    	
	            } else if(event == XmlPullParser.START_TAG) {
	                if (info.getName().equalsIgnoreCase("MEASUREMENT")) {  
	                	sample.sampleDate = HealthGeniusUtil.XMLDateToDate(info.getAttributeValue(info.getNamespace(), "DATE"));
	                	sample.sampleEventId = info.getAttributeValue(info.getNamespace(), "EVENT");
	                } else if (info.getName().equalsIgnoreCase("DATA")) {
	                	Integer key = Integer.parseInt(info.getAttributeValue(info.getNamespace(), "ID"));
	                	Float value = Float.parseFloat(info.getAttributeValue(info.getNamespace(), "VALUE"));
	                	sample.results.put(key, value);
	                }
	            } else if(event == XmlPullParser.END_TAG) {
	                if (info.getName().equalsIgnoreCase("PENDINGDATA")) {
	                	break;
	                }
	            }
	            event = info.next();
	        }
	        return sample;
		} catch (XmlPullParserException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public void finishMeasurements(boolean outcome, Hashtable<Integer, Float> result) {
		//TODO
		this.thread.interrupt();
		((Vibrator)HealthGenius.myApp.getSystemService(Context.VIBRATOR_SERVICE)).vibrate(3000);
    	if (this.bluetoothPreviouslyConnected) HealthGenius.mySensorManager.reinitBluetooth();
		else HealthGenius.myBluetoothAdapter.disable();
		this.results = result;
		try {
			if ((HealthGenius.mySensorManager.eventAssociated != null)&&true)  {
				HealthGenius.mySensorManager.eventAssociated.state = 0;
				HealthGenius.myCalendarManager.saveCalendar();
			}
			HealthGenius.myUIManager.UImeas.finishSensorMeasurement(name, outcome, instance, order);
		} catch (Exception e) {
			finishMeasurements(true, results);
		}
	}

	@Override
	public String getSensorGlobalResult() {
		String result;
		Float avrgSo2 = this.results.get(com.o2hlink.healthgenius.data.sensor.SensorManager.DATAID_SO2_AVRG);
		if (avrgSo2 < 10.0) {
			Float initSo2 = this.results.get(com.o2hlink.healthgenius.data.sensor.SensorManager.DATAID_SO2_INIT);
			Float finalSo2 = this.results.get(com.o2hlink.healthgenius.data.sensor.SensorManager.DATAID_SO2_FINAL);
			avrgSo2 = (initSo2 + finalSo2)/2;
		}
		if (avrgSo2 > 90.0) {
			result = "2:" + HealthGenius.myLanguageManager.SENSORS_EXERCISE_MESSAGE2_OLD;
		}
		else if (avrgSo2 > 85.0) {
			result = "1:" + HealthGenius.myLanguageManager.SENSORS_EXERCISE_MESSAGE1_OLD;
		}
		else {
			result = "0:" + HealthGenius.myLanguageManager.SENSORS_EXERCISE_MESSAGE0_OLD;
		}	
		return result;
	}

	@Override
	public String passSensorResultToXML() {
		String returned = "<EVENT ID=\"1\" DATETIME=\"" + HealthGenius.myMobileManager.device.getDateTime();
		returned += "\" IDGROUP=\"\" IDAGENDA=\"";
		if (HealthGenius.mySensorManager.eventAssociated != null) {
			Date dateid = new Date(Long.parseLong(HealthGenius.mySensorManager.eventAssociated.id)*1000 - 3600000l);
//			dateid.setTime(dateid.getTime() - dateid.getTimezoneOffset());
			returned +=  HealthGeniusUtil.dateToXMLDate(dateid) + "0" + "\">";		
		}
		else returned += "\">";
		Enumeration<Integer> keys = this.results.keys();
		while (keys.hasMoreElements()) {
			int key = keys.nextElement();
			returned += "<DATA ID=\"" + key + "\" VALUE=\"" + this.results.get(key);
			returned += "\" UNITS=\"" + com.o2hlink.healthgenius.data.sensor.SensorManager.getUnitIDForMeasurementID(key) + "\"/>";
		}
		returned += "</EVENT>";
		return returned;
	}

	@Override
	public void startMeasurement() {
		this.state = 0;
		HealthGenius.myUIManager.state = UIManager.UI_STATE_PROGRAM;
		nextStep();
	}
	
	public void nextStep() {
		this.state++;
		switch (this.state) {
			case 0:
				break;
			case 1:
				HealthGenius.myUIManager.loadScreen(R.layout.info);
				TextView text = (TextView) HealthGenius.myApp.findViewById(R.id.textInfo);
				text.setText(HealthGenius.myLanguageManager.PROGRAMS_EXERCISE_ADVERT0);
				CountDownTimer timer = new CountDownTimer(3000,1000) {
					@Override
					public void onTick(long millisUntilFinished) {
					}
					@Override
					public void onFinish() {
						loadBorgAir(true);
					}
				};
				timer.start();
				break;	
			case 2:
				loadBorgFatigue(true);
				break;	
			case 3:
				HealthGenius.myApp.setContentView(R.layout.yesnoquestion);
				TextView question = (TextView)HealthGenius.myApp.findViewById(R.id.question);
				question.append(HealthGenius.myLanguageManager.SENSORS_EXERCISE_CHOICE);
				ImageButton yes = (ImageButton)HealthGenius.myApp.findViewById(R.id.yes);
				ImageButton no = (ImageButton)HealthGenius.myApp.findViewById(R.id.no);
				yes.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						HealthGenius.myUIManager.loadScreen(R.layout.info);
						TextView text2 = (TextView) HealthGenius.myApp.findViewById(R.id.textInfo);
						text2.setText(HealthGenius.myLanguageManager.PROGRAMS_EXERCISE_ADVERT1);
						LinearLayout board = (LinearLayout) HealthGenius.myApp.findViewById(R.id.mainLayout);
						board.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View v) {
								((Exercise) HealthGenius.mySensorManager.sensorList.get(com.o2hlink.healthgenius.data.sensor.SensorManager.ID_EXERCISE)).track = true;
								startMeasurement(time, timedMessages);
							}
						});
					}
				});
				no.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						((Exercise) HealthGenius.mySensorManager.sensorList.get(com.o2hlink.healthgenius.data.sensor.SensorManager.ID_EXERCISE)).track = true;
						startNotMeasurement(time, timedMessages);
					}
				});
				break;	
			case 4:
				HealthGenius.myUIManager.loadScreen(R.layout.info);
				TextView text3 = (TextView) HealthGenius.myApp.findViewById(R.id.textInfo);
				text3.setText(HealthGenius.myLanguageManager.PROGRAMS_EXERCISE_ADVERT2);
				CountDownTimer timer3 = new CountDownTimer(3000,1000) {
					@Override
					public void onTick(long millisUntilFinished) {
					}
					@Override
					public void onFinish() {
						loadBorgAir(false);
					}
				};
				timer3.start();
				break;
			case 5:
				loadBorgFatigue(false);
				break;	
			case 6:
				finishMeasurements(true, this.results);
				break;	
			case 7:
				HealthGenius.myUIManager.loadScreen(R.layout.info);
				TextView text4 = (TextView) HealthGenius.myApp.findViewById(R.id.textInfo);
				text4.setText(HealthGenius.myLanguageManager.PROGRAMS_EXERCISE_ADVERT3);
				CountDownTimer timer4 = new CountDownTimer(3000,1000) {
					@Override
					public void onTick(long millisUntilFinished) {
					}
					@Override
					public void onFinish() {
						loadFirstQuest();
					}
				};
				timer4.start();
				break;
			case 8:
				loadSecQuest();
				break;
			case 9:
				loadThirdQuest();
				break;
			case 10:
				this.state = 0;
				((Exercise) HealthGenius.mySensorManager.sensorList.get(com.o2hlink.healthgenius.data.sensor.SensorManager.ID_EXERCISE)).results.put(com.o2hlink.healthgenius.data.sensor.SensorManager.DATAID_BORG_AIR_PRE, borgAirPre);
				((Exercise) HealthGenius.mySensorManager.sensorList.get(com.o2hlink.healthgenius.data.sensor.SensorManager.ID_EXERCISE)).results.put(com.o2hlink.healthgenius.data.sensor.SensorManager.DATAID_BORG_AIR_POST, borgAirPost);
				((Exercise) HealthGenius.mySensorManager.sensorList.get(com.o2hlink.healthgenius.data.sensor.SensorManager.ID_EXERCISE)).results.put(com.o2hlink.healthgenius.data.sensor.SensorManager.DATAID_BORG_FATIGUE_PRE, borgFatiguePre);
				((Exercise) HealthGenius.mySensorManager.sensorList.get(com.o2hlink.healthgenius.data.sensor.SensorManager.ID_EXERCISE)).results.put(com.o2hlink.healthgenius.data.sensor.SensorManager.DATAID_BORG_FATIGUE_POST, borgFatiguePost);
				((Exercise) HealthGenius.mySensorManager.sensorList.get(com.o2hlink.healthgenius.data.sensor.SensorManager.ID_EXERCISE)).results.put(com.o2hlink.healthgenius.data.sensor.SensorManager.DATAID_EXE_ONE, (float) firstquestans);
				((Exercise) HealthGenius.mySensorManager.sensorList.get(com.o2hlink.healthgenius.data.sensor.SensorManager.ID_EXERCISE)).results.put(com.o2hlink.healthgenius.data.sensor.SensorManager.DATAID_EXE_TWO, (float) secquestans);
				((Exercise) HealthGenius.mySensorManager.sensorList.get(com.o2hlink.healthgenius.data.sensor.SensorManager.ID_EXERCISE)).results.put(com.o2hlink.healthgenius.data.sensor.SensorManager.DATAID_EXE_THREE, (float) thrquestans);
				HealthGenius.myApp.setContentView(R.layout.sending);
				SendSensorResult sending = new SendSensorResult(HealthGenius.mySensorManager.sensorList.get(com.o2hlink.healthgenius.data.sensor.SensorManager.ID_EXERCISE));
				Thread thread = new Thread(sending, "SENDWALKING");
				thread.start();
		}
	}
	
	public void loadBorgAir(final boolean pre) {
		TextView questionText;
		ImageButton next;
		HealthGenius.myApp.setContentView(R.layout.numquestion);
		final String representation[] = {HealthGenius.myLanguageManager.BORG_0, HealthGenius.myLanguageManager.BORG_05, 
				HealthGenius.myLanguageManager.BORG_1, HealthGenius.myLanguageManager.BORG_1, 
				HealthGenius.myLanguageManager.BORG_2, HealthGenius.myLanguageManager.BORG_2, 
				HealthGenius.myLanguageManager.BORG_3, HealthGenius.myLanguageManager.BORG_3, 
				HealthGenius.myLanguageManager.BORG_4, HealthGenius.myLanguageManager.BORG_4, 
				HealthGenius.myLanguageManager.BORG_5, HealthGenius.myLanguageManager.BORG_5, 
				HealthGenius.myLanguageManager.BORG_5, HealthGenius.myLanguageManager.BORG_5, 
				HealthGenius.myLanguageManager.BORG_7, HealthGenius.myLanguageManager.BORG_7, 
				HealthGenius.myLanguageManager.BORG_7, HealthGenius.myLanguageManager.BORG_7, 
				HealthGenius.myLanguageManager.BORG_9, HealthGenius.myLanguageManager.BORG_9, 
				HealthGenius.myLanguageManager.BORG_10, HealthGenius.myLanguageManager.BORG_10};
		questionText = (TextView) HealthGenius.myApp.findViewById(R.id.questionText);
		final TextView numText = (TextView) HealthGenius.myApp.findViewById(R.id.numText);
		questionText.setText(this.borgAir.getName());
		numText.setText("0 - " + representation[0]);
		if (pre) borgAirPre = 0.0f;
		else borgAirPost = 0.0f;
		SeekBar seekbar = (SeekBar) HealthGenius.myApp.findViewById(R.id.seekbar);
		seekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {				
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				
			}
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				
			}
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				int selection = progress/5;
				if ((selection == 1)) {
					numText.setText("0.5 - " + representation[1]);
					if (pre) borgAirPre = 0.5f;
					else borgAirPost = 0.5f;
				}
				else {
					numText.setText("" + progress/10 + " - " + representation[selection]);	
					if (pre) borgAirPre = progress/10;
					else borgAirPost = progress/10;
				}
			}
		});
		ImageButton prev = (ImageButton) HealthGenius.myApp.findViewById(R.id.previous);
		prev.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				instance.prevStep();
			}
		});
		next = (ImageButton) HealthGenius.myApp.findViewById(R.id.next);
		next.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				instance.nextStep();
			}
		});
	}
	
	public void loadBorgFatigue(final boolean pre) {
		TextView questionText;
		ImageButton next;
		HealthGenius.myApp.setContentView(R.layout.numquestion);
		final String representation[] = {HealthGenius.myLanguageManager.BORG_0, HealthGenius.myLanguageManager.BORG_05, 
				HealthGenius.myLanguageManager.BORG_1, HealthGenius.myLanguageManager.BORG_1, 
				HealthGenius.myLanguageManager.BORG_2, HealthGenius.myLanguageManager.BORG_2, 
				HealthGenius.myLanguageManager.BORG_3, HealthGenius.myLanguageManager.BORG_3, 
				HealthGenius.myLanguageManager.BORG_4, HealthGenius.myLanguageManager.BORG_4, 
				HealthGenius.myLanguageManager.BORG_5, HealthGenius.myLanguageManager.BORG_5, 
				HealthGenius.myLanguageManager.BORG_5, HealthGenius.myLanguageManager.BORG_5, 
				HealthGenius.myLanguageManager.BORG_7, HealthGenius.myLanguageManager.BORG_7, 
				HealthGenius.myLanguageManager.BORG_7, HealthGenius.myLanguageManager.BORG_7, 
				HealthGenius.myLanguageManager.BORG_9, HealthGenius.myLanguageManager.BORG_9, 
				HealthGenius.myLanguageManager.BORG_10, HealthGenius.myLanguageManager.BORG_10};
		questionText = (TextView) HealthGenius.myApp.findViewById(R.id.questionText);
		final TextView numText = (TextView) HealthGenius.myApp.findViewById(R.id.numText);
		questionText.setText(this.borgFatigue.getName());
		numText.setText("0 - " + representation[0]);
		if (pre) borgFatiguePre = 0.0f;
		else borgFatiguePost = 0.0f;
		SeekBar seekbar = (SeekBar) HealthGenius.myApp.findViewById(R.id.seekbar);
		seekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {				
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				
			}
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				
			}
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				int selection = progress/5;
				if ((selection == 1)) {
					numText.setText("0.5 - " + representation[1]);
					if (pre) borgFatiguePre = 0.5f;
					else borgFatiguePost = 0.5f;
				}
				else {
					numText.setText("" + progress/10 + " - " + representation[selection]);	
					if (pre) borgFatiguePre = progress/10;
					else borgFatiguePost = progress/10;
				}
			}
		});
		ImageButton prev = (ImageButton) HealthGenius.myApp.findViewById(R.id.previous);
		prev.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				instance.prevStep();
			}
		});
		next = (ImageButton) HealthGenius.myApp.findViewById(R.id.next);
		next.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				instance.nextStep();
			}
		});
	}
	
	public void loadFirstQuest() {
		TextView questionText;
		ImageButton next;
		HealthGenius.myApp.setContentView(R.layout.monoquestion);
		questionText = (TextView) HealthGenius.myApp.findViewById(R.id.questionText);
		questionText.setText(HealthGenius.myLanguageManager.PROGRAMS_EXERCISE_FIRSTQUEST_TITLE);
		final String answers[] = {HealthGenius.myLanguageManager.PROGRAMS_EXERCISE_FIRSTQUEST_ANS1,
				HealthGenius.myLanguageManager.PROGRAMS_EXERCISE_FIRSTQUEST_ANS2,
				HealthGenius.myLanguageManager.PROGRAMS_EXERCISE_FIRSTQUEST_ANS3,
				HealthGenius.myLanguageManager.PROGRAMS_EXERCISE_FIRSTQUEST_ANS4,
				HealthGenius.myLanguageManager.PROGRAMS_EXERCISE_FIRSTQUEST_ANS5,
				HealthGenius.myLanguageManager.PROGRAMS_EXERCISE_FIRSTQUEST_ANS6,
				HealthGenius.myLanguageManager.PROGRAMS_EXERCISE_FIRSTQUEST_ANS7};
		RadioGroup options = (RadioGroup) HealthGenius.myApp.findViewById(R.id.options);
		for (String answer : answers) {
			RadioButton button = new RadioButton(HealthGenius.myApp);
			button.setText(answer);
			button.setTextColor(Color.BLACK);
			button.setId(Integer.parseInt(answer.substring(0, 1)));
			button.setLayoutParams(new LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT));
			options.addView(button);
		}
		options.setOnCheckedChangeListener(new OnCheckedChangeListener() {					
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				firstquestans = checkedId;
			}
		});
		options.check(1);
		firstquestans = 1;
		ImageButton prev = (ImageButton) HealthGenius.myApp.findViewById(R.id.previous);
		prev.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				instance.prevStep();
			}
		});
		next = (ImageButton) HealthGenius.myApp.findViewById(R.id.next);
		next.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				instance.nextStep();
			}
		});
	}
	
	public void loadSecQuest() {
		TextView questionText;
		ImageButton next;
		HealthGenius.myApp.setContentView(R.layout.monoquestion);
		questionText = (TextView) HealthGenius.myApp.findViewById(R.id.questionText);
		questionText.setText(HealthGenius.myLanguageManager.PROGRAMS_EXERCISE_SECQUEST_TITLE);
		final String answers[] = {HealthGenius.myLanguageManager.PROGRAMS_EXERCISE_SECQUEST_ANS1,
				HealthGenius.myLanguageManager.PROGRAMS_EXERCISE_SECQUEST_ANS2,
				HealthGenius.myLanguageManager.PROGRAMS_EXERCISE_SECQUEST_ANS3,
				HealthGenius.myLanguageManager.PROGRAMS_EXERCISE_SECQUEST_ANS4};
		RadioGroup options = (RadioGroup) HealthGenius.myApp.findViewById(R.id.options);
		for (String answer : answers) {
			RadioButton button = new RadioButton(HealthGenius.myApp);
			button.setText(answer);
			button.setTextColor(Color.BLACK);
			button.setId(Integer.parseInt(answer.substring(0, 1)));
			button.setLayoutParams(new LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT));
			options.addView(button);
		}
		options.setOnCheckedChangeListener(new OnCheckedChangeListener() {					
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				secquestans = checkedId;
			}
		});
		options.check(1);
		secquestans = 1;
		ImageButton prev = (ImageButton) HealthGenius.myApp.findViewById(R.id.previous);
		prev.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				instance.prevStep();
			}
		});
		next = (ImageButton) HealthGenius.myApp.findViewById(R.id.next);
		next.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				instance.nextStep();
			}
		});
	}
	
	public void loadThirdQuest() {
		TextView questionText;
		ImageButton next;
		HealthGenius.myApp.setContentView(R.layout.monoquestion);
		questionText = (TextView) HealthGenius.myApp.findViewById(R.id.questionText);
		questionText.setText(HealthGenius.myLanguageManager.PROGRAMS_EXERCISE_THIRDQUEST_TITLE);
		final String answers[] = {HealthGenius.myLanguageManager.PROGRAMS_EXERCISE_THIRDQUEST_ANS1,
				HealthGenius.myLanguageManager.PROGRAMS_EXERCISE_THIRDQUEST_ANS2,
				HealthGenius.myLanguageManager.PROGRAMS_EXERCISE_THIRDQUEST_ANS3,
				HealthGenius.myLanguageManager.PROGRAMS_EXERCISE_THIRDQUEST_ANS4};
		RadioGroup options = (RadioGroup) HealthGenius.myApp.findViewById(R.id.options);
		for (String answer : answers) {
			RadioButton button = new RadioButton(HealthGenius.myApp);
			button.setText(answer);
			button.setTextColor(Color.BLACK);
			button.setId(Integer.parseInt(answer.substring(0, 1)));
			button.setLayoutParams(new LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT));
			options.addView(button);
		}
		options.setOnCheckedChangeListener(new OnCheckedChangeListener() {					
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				thrquestans = checkedId;
			}
		});
		options.check(1);
		thrquestans = 1;
		ImageButton prev = (ImageButton) HealthGenius.myApp.findViewById(R.id.previous);
		prev.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				instance.prevStep();
			}
		});
		next = (ImageButton) HealthGenius.myApp.findViewById(R.id.next);
		next.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				instance.nextStep();
			}
		});
	}
	
	public void prevStep() {
		if (this.state <= 1) {
			HealthGenius.myUIManager.loadBoxOpen();
		}
		this.state -= 2;
		this.nextStep();
	}
	
	public void startMeasurement(long time, Hashtable<Long,String> timedMessages) {
		PowerManager pm = (PowerManager) HealthGenius.myApp.getSystemService(Context.POWER_SERVICE);
	    wakeLock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK|PowerManager.ACQUIRE_CAUSES_WAKEUP, "Exercise");
	    wakeLock.acquire();
	    HealthGenius.myApp.getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		this.results = new Hashtable<Integer, Float>();
		this.sampleDate = new Date();
		if (HealthGenius.mySensorManager.eventAssociated != null) {
			this.sampleEventId =  HealthGenius.mySensorManager.eventAssociated.id;		
		} else this.sampleEventId = null;
		HealthGenius.myBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		this.exerciseRunnabe = new ExerciseRunnable(this, time, timedMessages);
		this.thread = new Thread(this.exerciseRunnabe);
		this.thread.start();
	}
	
	public void startNotMeasurement(long time, Hashtable<Long,String> timedMessages) {
		PowerManager pm = (PowerManager) HealthGenius.myApp.getSystemService(Context.POWER_SERVICE);
	    wakeLock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK|PowerManager.ACQUIRE_CAUSES_WAKEUP, "Exercise");
	    wakeLock.acquire();
	    HealthGenius.myApp.getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		this.results = new Hashtable<Integer, Float>();
		this.sampleDate = new Date();
		if (HealthGenius.mySensorManager.eventAssociated != null) {
			this.sampleEventId =  HealthGenius.mySensorManager.eventAssociated.id;		
		} else this.sampleEventId = null;
		HealthGenius.myBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		this.exerciseNotRunnabe = new ExerciseNotRunnable(this, time);
		this.thread = new Thread(this.exerciseNotRunnabe);
		this.thread.start();
	}
	
}

class ExerciseRunnable implements Runnable {
	
	private Exercise exercise;
	
	private long time;

	private long timePassed;
	
	private long timeMeasured = 0;
	
	private float initSo2 = 0.0f;
	
	private float initHr = 0.0f;
	
	private float lastSo2 = 0.0f;
	
	private float lastHr = 0.0f;
	
	private float peakSo2 = 0.0f;
	
	private float peakHr = 0.0f;
	
	private float lowSo2 = 100.0f;
	
	private float lowHr = 160.0f;
	
	private float avrgSo2 = 0.0f;
	
	private float avrgHr = 0.0f;
	
	private float max_hr = 220.0f - ((System.currentTimeMillis() - HealthGenius.myMobileManager.user.getBirthdate().getTime())/(100*60*60*24*365));
	
	BroadcastReceiver myReceiver;

	BluetoothSocket socket;
	
    InputStream in;
    
    OutputStream out;
    
    BluetoothDevice hxm;
	
	boolean finished;
	
	boolean timeFinished;
	
	CountDownTimer temporizer;
	
	AnimationDrawable animation;
	
	SoundPool soundpool;
	
	HashMap<Integer, Integer> soundpoolmap;
	
	float streamVolume;
	
	int soundOn;
	
	private static final int FINISH_SOUND = 0;
	
	private static final int SYSTEMALERT_SOUND = 1;
	
	private static final int MEDICALALERT_SOUND = 2;
	
	private static final int NOTIFICATION_SOUND = 3;
	
	private static final int METRONOME_SOUND = 3;
	
	int lastStatus = 2;
	
	boolean notified = false;

	boolean runningMetro = false;
	
	long timeMetro = 1000;
	
	Hashtable<Long,String> timedMessages;
	
	SensorEventListener sensorListener;
	
	LocationListener locListener;
	
	Location lastLocation;
	
	private float speed;
	private float distance;
	private int steps = 0;
	private int stops = 0;
	private boolean stopped = true;
	
    private Date lasStepTime = new Date();
    private int stepsFollowed;
    private int stepsToRun = 5;
	
    private int mTimeLimit = 6000;
    private int mLimit;
    private float mLastValue = 0.0f;
    private float mScale[] = new float[2];
    private float mYOffset;

    private float mLastDirection = -1.0f;
    private float mLastExtremes[] = new float[2];
    private float mLastDiff = 0.0f;
    private int mLastMatch = -1;

	public ExerciseRunnable(Exercise exercise, long time, Hashtable<Long,String> timedMessages) {
		this.exercise = exercise;
		this.time = time;
		this.finished = true;
		this.timedMessages = timedMessages;
		this.soundpoolmap = new HashMap<Integer, Integer>();
		this.soundpool = new SoundPool(4, AudioManager.STREAM_RING, 0); 
		this.soundpoolmap.put(SYSTEMALERT_SOUND, soundpool.load(HealthGenius.myApp, R.raw.systemalert, 1));
		this.soundpoolmap.put(MEDICALALERT_SOUND, soundpool.load(HealthGenius.myApp, R.raw.medicalalert, 1));
		this.soundpoolmap.put(NOTIFICATION_SOUND, soundpool.load(HealthGenius.myApp, R.raw.notification, 1));
		this.soundpoolmap.put(FINISH_SOUND, soundpool.load(HealthGenius.myApp, R.raw.finish, 1));
		this.soundpoolmap.put(METRONOME_SOUND, soundpool.load(HealthGenius.myApp, R.raw.step, 1));
		this.soundOn = 1;
		int h = 480; // TODO: remove this constant
        mYOffset = h * 0.5f;
        mScale[0] = - (h * 0.5f * (1.0f / (SensorManager.STANDARD_GRAVITY * 2)));
        mScale[1] = - (h * 0.5f * (1.0f / (SensorManager.MAGNETIC_FIELD_EARTH_MAX)));
        this.mLimit = HealthGenius.myMobileManager.pedometerCalValue;
        this.exercise.hrtrack = new HashMap<Float,Float>();
        this.exercise.so2track = new HashMap<Float,Float>();
	}

	public String getGlobalResult() {
		String result;
		if (lastSo2 == 0.0f) return "2:" + HealthGenius.myLanguageManager.SENSORS_OBTAINING_DATA;
		boolean timedMessage = false;
		if (lastSo2 > 90.0) {
			result = "2:";
		}
		else if (lastSo2 > 85.0) {
			result = "1:";
		}
		else {
			result = "0:";
		}	
		if (this.timedMessages != null) {
			Enumeration<Long> enumeration = this.timedMessages.keys();
			while (enumeration.hasMoreElements()) {
				long timer = enumeration.nextElement();
				long timer2 = Math.abs(this.timePassed - timer);
				if (timer2 <= 5000) {
					result += this.timedMessages.get(timer);
					if (!notified) {
						this.streamVolume = HealthGenius.myAudioManager.getStreamVolume(AudioManager.STREAM_RING);
						this.streamVolume = soundOn*(this.streamVolume/HealthGenius.myAudioManager.getStreamMaxVolume(AudioManager.STREAM_RING));
				        soundpool.play(soundpoolmap.get(NOTIFICATION_SOUND), streamVolume, streamVolume, 1, 0, 1f);
						notified = true;
					}
					timedMessage = true;
					break;
				}
			}
		}
		if (!timedMessage) {
			notified = false;
			if (lastSo2 > 90.0) {
				result += HealthGenius.myLanguageManager.SENSORS_EXERCISE_MESSAGE2;
			}
			else if (lastSo2 > 85.0) {
				result += HealthGenius.myLanguageManager.SENSORS_EXERCISE_MESSAGE1;
			}
			else {
				result += HealthGenius.myLanguageManager.SENSORS_EXERCISE_MESSAGE0;
			}
		}
		return result;
	}
	
	@Override
	public void run() {		
		boolean found = false;
		timeFinished = false;
		distance = -1f;
		speed = -1f;
		this.initHr = -1f;
		this.initSo2 = -1f;
		long timefromlastmeas = 0l;
		handler.sendEmptyMessage(0);
		long lastTrackUpdate = 0;
		if (HealthGenius.myBluetoothAdapter == null) {
			handler.sendEmptyMessage(3);
			return;
		}
		if (HealthGenius.mySensorManager.reiniting) {
			handler.sendEmptyMessage(31);
			while (HealthGenius.mySensorManager.reiniting) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			handler.sendEmptyMessage(0);
		}
		else if (!HealthGenius.myBluetoothAdapter.isEnabled()) {
			HealthGenius.myBluetoothAdapter.enable();
			this.exercise.bluetoothPreviouslyConnected = false;
			handler.sendEmptyMessage(4);
			while (!HealthGenius.myBluetoothAdapter.isEnabled()) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			handler.sendEmptyMessage(0);
		}
		else {
			this.exercise.bluetoothPreviouslyConnected = true;
		}
		Set<BluetoothDevice> pairedDevices = HealthGenius.myBluetoothAdapter.getBondedDevices();
		// If there are paired devices
		if (pairedDevices.size() > 0) {
		    // Loop through paired devices
		    for (BluetoothDevice device : pairedDevices) {
		        // Add the name and address to an array adapter to show in a ListView
		        String name = device.getName();
		        if (name.contains("Nonin")) {
		        	try {
		        		found = true;
//						socket = device.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
//						handler.sendEmptyMessage(5);
//						HealthGenius.myBluetoothAdapter.cancelDiscovery();
//						socket.connect();
		        		hxm = BluetoothAdapter.getDefaultAdapter().getRemoteDevice(device.getAddress());
		        		Method m;
						try {
							m = hxm.getClass().getMethod("createRfcommSocket", new Class[]{int.class});
			        		socket = (BluetoothSocket)m.invoke(hxm, Integer.valueOf(1));
			        		handler.sendEmptyMessage(5);
			        		socket.connect();
			        	} catch (Exception e) {
			        		handler.sendEmptyMessage(7);
							e.printStackTrace();
							break;
						}
						handler.sendEmptyMessage(6);
					    in = socket.getInputStream();
					    out = socket.getOutputStream();
					    byte[] retrieve = {0x44, 0x31};
					    out.write(retrieve);
					    byte [] ack = new byte [1];
					    in.read(ack);
					    if (ack[0] == 0x15) {
					    	handler.sendEmptyMessage(10);
					    	return;
					    }
						handler.sendEmptyMessage(1);
					    byte [] data = new byte [3];
				  		int count = 0;
				  		int lost = 0;
				  		int bad = 0;
				  		Runnable metronome = new Runnable() {
							@Override
							public void run() {
								while ((!timeFinished)&&(finished)) {
									streamVolume = HealthGenius.myAudioManager.getStreamVolume(AudioManager.STREAM_RING);
									streamVolume = soundOn*(streamVolume/HealthGenius.myAudioManager.getStreamMaxVolume(AudioManager.STREAM_RING));
							        if (runningMetro) soundpool.play(soundpoolmap.get(METRONOME_SOUND), streamVolume, streamVolume, 1, 0, 1f);
								    try {
										Thread.sleep(timeMetro);
									} catch (InterruptedException e) {
										e.printStackTrace();
									}
								 }
							}
						};
						Thread metro = new Thread(metronome);
						metro.start();
						handler.sendEmptyMessage(51);
						sensorListener = new SensorEventListener() {
							@Override
							public void onSensorChanged(SensorEvent event) {
								int j = (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) ? 1 : 0;
								if (j == 0) { 
				                    float vSum = 0;
				                    for (int i=0 ; i<3 ; i++) {
				                        final float v = mYOffset + event.values[i] * mScale[j];
				                        vSum += v;
				                    }
				                    float v = vSum / 3;
				                    
				                    float direction = (v > mLastValue ? 1 : (v < mLastValue ? -1 : 0));
				                    if (direction == - mLastDirection) {
				                        // Direction changed
				                        int extType = (direction > 0 ? 0 : 1); // minumum or maximum?
				                        mLastExtremes[extType] = mLastValue;
				                        float diff = Math.abs(mLastExtremes[extType] - mLastExtremes[1 - extType]);
				                        if (diff > mLimit) {
				                            boolean isAlmostAsLargeAsPrevious = diff > (mLastDiff*2/3);
				                            boolean isPreviousLargeEnough = mLastDiff > (diff/3);
				                            boolean isNotContra = (mLastMatch != 1 - extType);
				                            if (isAlmostAsLargeAsPrevious && isPreviousLargeEnough && isNotContra) {
				                                // TODO
				                            	steps++;
				                            	stepsFollowed++;
				                            	if (stepsFollowed >= stepsToRun) {
				                            		stopped = false;
				                            	}
				                            	lasStepTime = new Date();
				                            	handler.sendEmptyMessage(2);
				                            	mLastMatch = extType;
				                            }
				                            else {
				                                mLastMatch = -1;
				                            }
				                        }
				                        mLastDirection = direction;
				                        mLastDiff = diff;
				                    }
				                    mLastValue = v;
				                    Date now = new Date();
				                    if (((now.getTime() - lasStepTime.getTime()) > mTimeLimit)&&(!stopped)) {
				                    	stopped = true;
				                    	stops++;
				                    	stepsFollowed = 0;
				                    	handler.sendEmptyMessage(2);
				                    }
				                }
							}
							@Override
							public void onAccuracyChanged(Sensor sensor, int accuracy) {}
						};
						HealthGenius.myAccelerometerManager.registerListener(sensorListener, HealthGenius.myAccelerometerManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_FASTEST);
						while ((this.lastHr == 0.0f)&&(this.lastSo2 == 0.0f)) {
							try {
				  		    	Log.d("EXERCISE", "Reading ...");
				  		    	in.read(data);
				  		    	if ((data[0] != -128)&&(data[2] == -128)) {
					  		    	Log.d("EXERCISE", "Data unsyncronized ...");
				  		    		data[2] = data[1];
				  		    		data[1] = data[0];
				  		    		data[0] = -128;
				  		    	}
				  		    	if ((data[0] != -128)&&(data[1] == -128)) {
					  		    	Log.d("EXERCISE", "Data unsyncronized ...");
				  		    		data[1] = data[2];
				  		    		data[2] = data[0];
				  		    		data[0] = -128;
				  		    	}
				  		    	if ((data[0] & 0x7C)==0x00  && (data[0] & 0x03)!=0x03) {
				  		    		float hr = (float)(((data[0] & 0x03) << 7) + data[1]);
								    float so2 = (float)((int)data[2]);
								    if ((so2 < 100.0f)&&(so2 > 60.0f)&&(hr < max_hr)&&(hr > 30.0f)) {
								    	bad = 0;
								    	lost = 0;
						  		    	Log.d("EXERCISE", "Valid data ...");
								    	if (this.peakHr < hr) this.peakHr = hr;
								    	if (this.peakSo2 < so2) this.peakSo2 = so2;
								    	if (this.lowHr > hr) this.lowHr = hr;
								    	if (this.lowSo2 > so2) this.lowSo2 = so2;
								    	this.avrgHr = ((this.avrgHr * count) + hr)/(count + 1);
								    	this.avrgSo2 = ((this.avrgSo2 * count) + so2)/(count + 1);
								    	this.lastHr = hr;
									    this.lastSo2 = so2;
									    if (this.initHr == -1f) this.initHr = hr;
									    if (this.initSo2 == -1f) this.initSo2 = so2;
								    	this.exercise.hrtrack.put((float) 0, this.lastHr);
								    	this.exercise.so2track.put((float) 0, this.lastSo2);
									    this.timeMeasured += 1000;
								    	count++;
										handler.sendEmptyMessage(110);
								    }
				  		    	}
				  		    } catch (IOException e) {
				  		    	Log.d("EXERCISE", "Data lost ...");
				  		    	continue;
							}
						}
				  		long timeStart = System.currentTimeMillis();
						while ((!timeFinished)&&(this.finished)) {
				  		    try {
				  		    	Log.d("EXERCISE", "Reading ...");
				  		    	in.read(data);
				  		    	if ((data[0] != -128)&&(data[2] == -128)) {
					  		    	Log.d("EXERCISE", "Data unsyncronized ...");
				  		    		data[2] = data[1];
				  		    		data[1] = data[0];
				  		    		data[0] = -128;
				  		    	}
				  		    	if ((data[0] != -128)&&(data[1] == -128)) {
					  		    	Log.d("EXERCISE", "Data unsyncronized ...");
				  		    		data[1] = data[2];
				  		    		data[2] = data[0];
				  		    		data[0] = -128;
				  		    	}
				  		    } catch (IOException e) {
				  		    	lost++;
				  		    	Log.d("EXERCISE", "Data lost ...");
				  		    	if (lost == 5) ((Vibrator)HealthGenius.myApp.getSystemService(Context.VIBRATOR_SERVICE)).vibrate(300);
				  		    	if (lost == 15) {
					  		    	reinitBluetoothBlocking(0);
				  		    	}
					  		    try {
					  		    	Thread.sleep(1000);
								} catch (InterruptedException e1) {
									e1.printStackTrace();
								}
						  		this.timePassed = System.currentTimeMillis() - timeStart;
				  		    	continue;
							}
				  		    lost = 0;
						    if ((data[0] & 0x7C)!=0x00  || (data[0] & 0x03)==0x03)  {
				  		    	bad++;
				  		    	Log.d("EXERCISE", "Bad data" + Byte.toString(data[0]) + " ...");
								if (bad == 10) {
				  		    		Log.d("EXERCISE", "Bad data: warning 1 ...");
							    	handler.sendEmptyMessage(22);
				  		    	}
								if (bad == 15) {
					  		    	reinitBluetoothBlocking(0);
				  		    	}
					  	    }
					  	    else {
							    float hr = (float)(((data[0] & 0x03) << 7) + data[1]);
							    float so2 = (float)((int)data[2]);
							    if ((so2 < 100.0f)&&(so2 > 60.0f)&&(hr < max_hr)&&(hr > 30.0f)) {
							    	bad = 0;
							    	lost = 0;
					  		    	Log.d("EXERCISE", "Valid data ...");
							    	if (this.peakHr < hr) this.peakHr = hr;
							    	if (this.peakSo2 < so2) this.peakSo2 = so2;
							    	if (this.lowHr > hr) this.lowHr = hr;
							    	if (this.lowSo2 > so2) this.lowSo2 = so2;
							    	int times = ((int)(this.timePassed - timefromlastmeas))/1000;
							    	if (times < 1) times = 1;
							    	if (times == 1) {
								    	this.avrgHr = ((this.avrgHr * count) + hr)/(count + 1);
								    	this.avrgSo2 = ((this.avrgSo2 * count) + so2)/(count + 1);
								    	count++;
							    	}
							    	else {
							    		for (int i = 1; i <= times; i++) {
							    			float hrtemp = (lastHr + ((hr - lastHr)*i/times));
							    			float so2temp = (lastSo2 + ((so2 - lastSo2)*i/times));
									    	this.avrgHr = ((this.avrgHr * count) + hrtemp)/(count + 1);
									    	this.avrgSo2 = ((this.avrgSo2 * count) + so2temp)/(count + 1);
									    	count++;
										}
							    	}
								    this.lastHr = hr;
								    this.lastSo2 = so2;
							    	timefromlastmeas = this.timePassed;
								    this.timeMeasured += 1000;
									handler.sendEmptyMessage(2);
							    }
							    else {
					  		    	bad++;
					  		    	Log.d("EXERCISE", "Data out of bounds ...");
					  		    	if (bad == 5) {
					  		    		Log.d("EXERCISE", "Data out of bounds: warning 1 ...");
								    	handler.sendEmptyMessage(22);
					  		    	}
					  		    	if (bad == 10) ((Vibrator)HealthGenius.myApp.getSystemService(Context.VIBRATOR_SERVICE)).vibrate(300);
					  		    	if (bad == 15) {
						  		    	reinitBluetoothBlocking(0);
					  		    	}
							    }
					  	    }
						    if ((this.timePassed - lastTrackUpdate) >= 10000) {
						    	this.exercise.hrtrack.put(((float) this.timePassed/1000), this.lastHr);
						    	this.exercise.so2track.put(((float) this.timePassed/1000), this.lastSo2);
						    	lastTrackUpdate = this.timePassed;
						    }
				  		    try {
				  		    	Thread.sleep(1000);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
					  		this.timePassed = System.currentTimeMillis() - timeStart;
					    }
					    if (finished) {
			  		    	Log.d("EXERCISE", "Exercise finished ...");
					    	this.exercise.hrtrack.put(((float) this.timePassed/1000), this.lastHr);
					    	this.exercise.so2track.put(((float) this.timePassed/1000), this.lastSo2);
							LocationManager myLocationManager = (LocationManager)HealthGenius.myApp.getSystemService(Context.LOCATION_SERVICE);
							myLocationManager.removeUpdates(locListener);
					    	exercise.results.put(com.o2hlink.healthgenius.data.sensor.SensorManager.DATAID_DISTANCE, distance);
					    	exercise.results.put(com.o2hlink.healthgenius.data.sensor.SensorManager.DATAID_HR_AVRG, this.avrgHr);
					    	exercise.results.put(com.o2hlink.healthgenius.data.sensor.SensorManager.DATAID_HR_PEAK, this.peakHr);
					    	exercise.results.put(com.o2hlink.healthgenius.data.sensor.SensorManager.DATAID_HR_LOW, this.lowHr);
					    	exercise.results.put(com.o2hlink.healthgenius.data.sensor.SensorManager.DATAID_HR_INIT, this.initHr);
					    	exercise.results.put(com.o2hlink.healthgenius.data.sensor.SensorManager.DATAID_HR_FINAL, this.lastHr);
					    	exercise.results.put(com.o2hlink.healthgenius.data.sensor.SensorManager.DATAID_SO2_AVRG, this.avrgSo2);
					    	exercise.results.put(com.o2hlink.healthgenius.data.sensor.SensorManager.DATAID_SO2_PEAK, this.peakSo2);
					    	exercise.results.put(com.o2hlink.healthgenius.data.sensor.SensorManager.DATAID_SO2_LOW, this.lowSo2);
					    	exercise.results.put(com.o2hlink.healthgenius.data.sensor.SensorManager.DATAID_SO2_INIT, this.initSo2);
					    	exercise.results.put(com.o2hlink.healthgenius.data.sensor.SensorManager.DATAID_SO2_FINAL, this.lastSo2);
					    	exercise.results.put(com.o2hlink.healthgenius.data.sensor.SensorManager.DATAID_STEPS, (float)this.steps);
					    	exercise.results.put(com.o2hlink.healthgenius.data.sensor.SensorManager.DATAID_STOPS, (float)this.stops);
					    	exercise.results.put(com.o2hlink.healthgenius.data.sensor.SensorManager.DATAID_TIME_EXE, (float)(time/1000));
					    	exercise.results.put(com.o2hlink.healthgenius.data.sensor.SensorManager.DATAID_TIME_DATA, (float)(timeMeasured/1000));
						    handler.sendEmptyMessage(11);
					    }
					    else temporizer.cancel();
					    if (in != null) {
			                try {in.close();} catch (Exception e) {}
			                in = null;
				        }
				        if (out != null) {
				                try {out.close();} catch (Exception e) {}
				                out = null;
				        }
				        if (socket != null) {
				                try {socket.close();} catch (Exception e) {}
				                socket = null;
				        }
					} catch (IOException e) {
						if (finished) handler.sendEmptyMessage(7);
						e.printStackTrace();
					}
		        }
		    }
		}
        if (!found) handler.sendEmptyMessage(8);
	}
	
	private void reinitBluetoothBlocking(int intents) {
		HealthGenius.myBluetoothAdapter.disable();
		while (HealthGenius.myBluetoothAdapter.getState() == BluetoothAdapter.STATE_TURNING_OFF) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		HealthGenius.myBluetoothAdapter.enable();
		while (HealthGenius.myBluetoothAdapter.getState() == BluetoothAdapter.STATE_TURNING_ON) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		try {
			Thread.sleep(15000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		try {
			Method m = hxm.getClass().getMethod("createRfcommSocket", new Class[]{int.class});
    		socket = (BluetoothSocket)m.invoke(hxm, Integer.valueOf(1));
    		socket.connect();
    	    in = socket.getInputStream();
    	    out = socket.getOutputStream();
    	    byte[] retrieve = {0x44, 0x31};
    	    out.write(retrieve);
    	    byte [] ack = new byte [1];
    	    in.read(ack);
    	    if (ack[0] == 0x15) {
    	    	if (intents == 3) handler.sendEmptyMessage(7);
    	    	else reinitBluetoothBlocking(intents + 1);
    	    	return;
    	    }
    	} catch (Exception e) {
    		if (intents == 3) handler.sendEmptyMessage(7);
	    	else reinitBluetoothBlocking(intents + 1);
			e.printStackTrace();
		}
	}

	private Handler handler = new Handler() {
		AnimationDrawable animation;
		ImageView animationFrame;
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				animationFrame = (ImageView) HealthGenius.myApp.findViewById(R.id.animation);
				animationFrame.setVisibility(View.VISIBLE);
				animationFrame.setBackgroundResource(R.drawable.loading);
				animation = (AnimationDrawable) animationFrame.getBackground();	
				animation.start();				
				HealthGenius.myUIManager.UImisc.loadInfoPopupWithoutExiting(HealthGenius.myLanguageManager.SENSORS_SEARCHING);
				break;
			case 1:
				animation.stop();	
				HealthGenius.myUIManager.UImeas.loadExerciseScreen(HealthGenius.myLanguageManager.SENSORS_WAITING_DATA, time);
				ImageView animationFrame = (ImageView) HealthGenius.myApp.findViewById(R.id.loadingView);
				animationFrame.setVisibility(View.VISIBLE);
				animationFrame.setBackgroundResource(R.drawable.loadingsmall);
				animation = (AnimationDrawable) animationFrame.getBackground();
				animation.start();
				((TextView) HealthGenius.myApp.findViewById(R.id.startText)).setText(exercise.name);
				ImageButton cancel = (ImageButton) HealthGenius.myApp.findViewById(R.id.stop);
				cancel.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
//						temporizer.cancel();
						finished = false;
						try {
							socket.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
						LocationManager myLocationManager = (LocationManager)HealthGenius.myApp.getSystemService(Context.LOCATION_SERVICE);
						myLocationManager.removeUpdates(locListener);
						exercise.wakeLock.release();
						HealthGenius.myAccelerometerManager.unregisterListener(sensorListener);
						exercise.finishMeasurements(false, null);
					}
				});
				final ImageButton soundon = (ImageButton) HealthGenius.myApp.findViewById(R.id.soundon);
				final ImageButton soundoff = (ImageButton) HealthGenius.myApp.findViewById(R.id.soundoff);
				soundon.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						soundOn = 1;
						soundoff.setVisibility(View.VISIBLE);
						soundon.setVisibility(View.GONE);
					}
				});
				soundoff.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						soundOn = 0;
						soundon.setVisibility(View.VISIBLE);
						soundoff.setVisibility(View.GONE);
					}
				});
				ImageButton confirm = (ImageButton) HealthGenius.myApp.findViewById(R.id.good);
				confirm.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
//						temporizer.cancel();
						finished = false;
						try {
							socket.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
						LocationManager myLocationManager = (LocationManager)HealthGenius.myApp.getSystemService(Context.LOCATION_SERVICE);
						myLocationManager.removeUpdates(locListener);
						HealthGenius.myAccelerometerManager.unregisterListener(sensorListener);
						exercise.hrtrack.put(((float) timePassed/1000), lastHr);
				    	exercise.so2track.put(((float) timePassed/1000), lastSo2);
				    	exercise.results.put(com.o2hlink.healthgenius.data.sensor.SensorManager.DATAID_DISTANCE, distance);
				    	exercise.results.put(com.o2hlink.healthgenius.data.sensor.SensorManager.DATAID_HR_AVRG, avrgHr);
						exercise.results.put(com.o2hlink.healthgenius.data.sensor.SensorManager.DATAID_HR_PEAK, peakHr);
						exercise.results.put(com.o2hlink.healthgenius.data.sensor.SensorManager.DATAID_HR_LOW, lowHr);
				    	exercise.results.put(com.o2hlink.healthgenius.data.sensor.SensorManager.DATAID_HR_INIT, initHr);
				    	exercise.results.put(com.o2hlink.healthgenius.data.sensor.SensorManager.DATAID_HR_FINAL, lastHr);
						exercise.results.put(com.o2hlink.healthgenius.data.sensor.SensorManager.DATAID_SO2_AVRG, avrgSo2);
						exercise.results.put(com.o2hlink.healthgenius.data.sensor.SensorManager.DATAID_SO2_PEAK, peakSo2);
						exercise.results.put(com.o2hlink.healthgenius.data.sensor.SensorManager.DATAID_SO2_LOW, lowSo2);
				    	exercise.results.put(com.o2hlink.healthgenius.data.sensor.SensorManager.DATAID_SO2_INIT, initSo2);
				    	exercise.results.put(com.o2hlink.healthgenius.data.sensor.SensorManager.DATAID_SO2_FINAL, lastSo2);
						if (timePassed > time) timePassed = time;
						exercise.results.put(com.o2hlink.healthgenius.data.sensor.SensorManager.DATAID_TIME_EXE, (float)(timePassed/1000));
				    	exercise.results.put(com.o2hlink.healthgenius.data.sensor.SensorManager.DATAID_TIME_DATA, (float)(timeMeasured/1000));
				    	exercise.results.put(com.o2hlink.healthgenius.data.sensor.SensorManager.DATAID_STEPS, (float)steps);
				    	exercise.results.put(com.o2hlink.healthgenius.data.sensor.SensorManager.DATAID_STOPS, (float)stops);
				    	exercise.wakeLock.release();
						exercise.nextStep();
//						exercise.finishMeasurements(true, exercise.results);
					}
				});
				final SeekBar metronome = (SeekBar) HealthGenius.myApp.findViewById(R.id.metronome);
				metronome.setVisibility(View.INVISIBLE);
				final ImageButton metronomeon = (ImageButton) HealthGenius.myApp.findViewById(R.id.metronomeon);
				final ImageButton metronomeoff = (ImageButton) HealthGenius.myApp.findViewById(R.id.metronomeoff);
				metronomeon.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						runningMetro = true;
						metronome.setVisibility(View.VISIBLE);
						metronomeoff.setVisibility(View.VISIBLE);
						metronomeon.setVisibility(View.GONE);
					}
				});
				metronomeoff.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						runningMetro = false;
						metronome.setVisibility(View.INVISIBLE);
						metronomeon.setVisibility(View.VISIBLE);
						metronomeoff.setVisibility(View.GONE);
					}
				});
				metronome.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
					@Override
					public void onStopTrackingTouch(SeekBar seekBar) {
					}
					@Override
					public void onStartTrackingTouch(SeekBar seekBar) {
					}
					@Override
					public void onProgressChanged(SeekBar seekBar, int progress,
							boolean fromUser) {
						if (progress < 25) timeMetro = 2000;
						else if (progress < 50) timeMetro = 1000;
						else if (progress < 75) timeMetro = 500;
						else timeMetro = 250;
					}
				});
				break;
			case 110:
				temporizer = new CountDownTimer(time, 1000) {
					@Override
					public void onTick(long millisUntilFinished) {
					    int seconds = (int) ((time - millisUntilFinished) / 1000);
					    int minutes = seconds / 60;
					    seconds     = seconds % 60;
					    int hours = minutes / 60;
					    minutes = minutes % 60;
					    try {
					    	((TextView) HealthGenius.myApp.findViewById(R.id.timerText)).setText(String.format("%01d:%02d:%02d", hours, minutes, seconds));			
					    } catch (Exception e) {
					    	this.cancel();
						}
					}
					
					@Override
					public void onFinish() {
				    	timeFinished = true;
					}
				};
				temporizer.start();
				animation.start();
				String textGlobal2 = getGlobalResult();
				int status2 = Integer.parseInt(textGlobal2.substring(0,1));
				streamVolume = HealthGenius.myAudioManager.getStreamVolume(AudioManager.STREAM_RING);
				streamVolume = soundOn*(streamVolume/HealthGenius.myAudioManager.getStreamMaxVolume(AudioManager.STREAM_RING));
		        if (status2 < lastStatus) soundpool.play(soundpoolmap.get(FINISH_SOUND), streamVolume, streamVolume, 1, 0, 1f);
				lastStatus = status2;
				HealthGenius.myUIManager.UImeas.updateExerciseScreen((int)lastHr, (int)lastSo2, steps, stops, textGlobal2);
				break;
			case 2:
				animation.start();
				String textGlobal = getGlobalResult();
				int status = Integer.parseInt(textGlobal.substring(0,1));
				streamVolume = HealthGenius.myAudioManager.getStreamVolume(AudioManager.STREAM_RING);
				streamVolume = soundOn*(streamVolume/HealthGenius.myAudioManager.getStreamMaxVolume(AudioManager.STREAM_RING));
		        if (status < lastStatus) soundpool.play(soundpoolmap.get(FINISH_SOUND), streamVolume, streamVolume, 1, 0, 1f);
				lastStatus = status;
				HealthGenius.myUIManager.UImeas.updateExerciseScreen((int)lastHr, (int)lastSo2, steps, stops, textGlobal);
				break;
			case 3:
				animation.stop();
				HealthGenius.myUIManager.UImisc.loadInfoPopup(HealthGenius.myLanguageManager.SENSORS_NOBLUETOOTH);
				break;
			case 4:
				HealthGenius.myUIManager.UImisc.loadInfoPopupWithoutExiting(HealthGenius.myLanguageManager.SENSORS_BLUETOOTH_NOTCONNECTED);
				break;
			case 5:
				HealthGenius.myUIManager.UImisc.loadInfoPopupWithoutExiting(HealthGenius.myLanguageManager.SENSORS_BLUETOOTH_CONNECTING);
				break;
			case 6:
				HealthGenius.myUIManager.UImisc.loadInfoPopupWithoutExiting(HealthGenius.myLanguageManager.SENSORS_BLUETOOTH_CONFIG);
				break;
			case 7:
				if ((HealthGenius.mySensorManager.eventAssociated != null)) 
					HealthGenius.myUIManager.UIcalendar.loadScheduleDay(HealthGenius.mySensorManager.eventAssociated.getDate()); 
				else
					HealthGenius.myUIManager.UImeas.loadSensorList();
				if (exercise.bluetoothPreviouslyConnected) HealthGenius.mySensorManager.reinitBluetooth();
				else HealthGenius.myBluetoothAdapter.disable();
				if (sensorListener != null) HealthGenius.myAccelerometerManager.unregisterListener(sensorListener);
				if (exercise.wakeLock != null) exercise.wakeLock.release();
				HealthGenius.myUIManager.UImisc.loadInfoPopup(HealthGenius.myLanguageManager.SENSORS_BLUETOOTH_NOTCONNECTION);
				break;
			case 8:
				if ((HealthGenius.mySensorManager.eventAssociated != null)) 
					HealthGenius.myUIManager.UIcalendar.loadScheduleDay(HealthGenius.mySensorManager.eventAssociated.getDate()); 
				else
					HealthGenius.myUIManager.UImeas.loadSensorList();
				if (exercise.bluetoothPreviouslyConnected) HealthGenius.mySensorManager.reinitBluetooth();
				else HealthGenius.myBluetoothAdapter.disable();
				if (sensorListener != null) HealthGenius.myAccelerometerManager.unregisterListener(sensorListener);
				if (exercise.wakeLock != null) exercise.wakeLock.release();
				HealthGenius.myUIManager.UImisc.loadInfoPopup(HealthGenius.myLanguageManager.SENSORS_BLUETOOTH_NOTPAIRED);
				break;
			case 9:
				if ((HealthGenius.mySensorManager.eventAssociated != null)) 
					HealthGenius.myUIManager.UIcalendar.loadScheduleDay(HealthGenius.mySensorManager.eventAssociated.getDate()); 
				else
					HealthGenius.myUIManager.UImeas.loadSensorList();
				if (exercise.bluetoothPreviouslyConnected) HealthGenius.mySensorManager.reinitBluetooth();
				else HealthGenius.myBluetoothAdapter.disable();
				if (sensorListener != null) HealthGenius.myAccelerometerManager.unregisterListener(sensorListener);
				if (exercise.wakeLock != null) exercise.wakeLock.release();
				HealthGenius.myUIManager.UImisc.loadInfoPopup(HealthGenius.myLanguageManager.SENSORS_BLUETOOTH_NOTMEASURED);
				break;
			case 10:
				if ((HealthGenius.mySensorManager.eventAssociated != null)) 
					HealthGenius.myUIManager.UIcalendar.loadScheduleDay(HealthGenius.mySensorManager.eventAssociated.getDate()); 
				else
					HealthGenius.myUIManager.UImeas.loadSensorList();
				if (exercise.bluetoothPreviouslyConnected) HealthGenius.mySensorManager.reinitBluetooth();
				else HealthGenius.myBluetoothAdapter.disable();
				if (sensorListener != null) HealthGenius.myAccelerometerManager.unregisterListener(sensorListener);
				if (exercise.wakeLock != null) exercise.wakeLock.release();
				HealthGenius.myUIManager.UImisc.loadInfoPopup(HealthGenius.myLanguageManager.SENSORS_BLUETOOTH_NOTCONFIG);
				break;
			case 11:
				streamVolume = HealthGenius.myAudioManager.getStreamVolume(AudioManager.STREAM_RING);
				streamVolume = soundOn*(streamVolume/HealthGenius.myAudioManager.getStreamMaxVolume(AudioManager.STREAM_RING));
		        soundpool.play(soundpoolmap.get(FINISH_SOUND), streamVolume, streamVolume, 1, 0, 1f);
		        if (sensorListener != null) HealthGenius.myAccelerometerManager.unregisterListener(sensorListener);
				if (exercise.wakeLock != null) exercise.wakeLock.release();
				exercise.nextStep();
//				exercise.finishMeasurements(true, exercise.results);
				break;
			case 12:
				if ((HealthGenius.mySensorManager.eventAssociated != null)) 
					HealthGenius.myUIManager.UIcalendar.loadScheduleDay(HealthGenius.mySensorManager.eventAssociated.getDate()); 
				else
					HealthGenius.myUIManager.UImeas.loadSensorList();
				if (exercise.bluetoothPreviouslyConnected) HealthGenius.mySensorManager.reinitBluetooth();
				else HealthGenius.myBluetoothAdapter.disable();
				if (sensorListener != null) HealthGenius.myAccelerometerManager.unregisterListener(sensorListener);
				if (exercise.wakeLock != null) exercise.wakeLock.release();
				HealthGenius.myUIManager.UImisc.loadInfoPopup(HealthGenius.myLanguageManager.SENSORS_BLUETOOTH_BADMEAS);
				break;
			case 21:
				String textGlobal3 = "1:" + HealthGenius.myLanguageManager.SENSORS_EXERCISE_BADMESSAGE;
				HealthGenius.myUIManager.UImeas.updateExerciseScreen((int)lastHr, (int)lastSo2, steps, stops, textGlobal3);
				break;
			case 22:
				animation.stop();
				break;
			case 31:
				HealthGenius.myUIManager.UImisc.loadInfoPopupWithoutExiting(HealthGenius.myLanguageManager.SENSORS_CONFIG);
				break;
			case 51:
				LocationManager myLocationManager = (LocationManager)HealthGenius.myApp.getSystemService(Context.LOCATION_SERVICE); 
				final Criteria criteria = new Criteria();
                criteria.setAccuracy(Criteria.ACCURACY_FINE);
                criteria.setAltitudeRequired(false);
                criteria.setBearingRequired(false);
                criteria.setCostAllowed(true);
                criteria.setPowerRequirement(Criteria.POWER_LOW);
                String bestProvider = myLocationManager.getBestProvider(criteria, true);
                locListener = new LocationListener() {							
					@Override
					public void onStatusChanged(String provider, int status, Bundle extras) {
					}
					@Override
					public void onProviderEnabled(String provider) {
					}
					@Override
					public void onProviderDisabled(String provider) {
					}
					@Override
					public void onLocationChanged(Location location) {
						if (lastLocation == null) {
							lastLocation = location;
							distance = 0.0f;
							speed = location.getSpeed();
							HealthGenius.myUIManager.UImeas.updateExerciseScreen(speed, distance);
						}
						else {
							distance += location.distanceTo(lastLocation);
							lastLocation = location;
							speed = location.getSpeed();
							HealthGenius.myUIManager.UImeas.updateExerciseScreen(speed, distance);
						}
					}
				};
                if (bestProvider != null) myLocationManager.requestLocationUpdates(bestProvider, 5, 10, locListener);
				break;
			}
		}
	};
}

class ExerciseNotRunnable implements Runnable {
	
	Exercise exercise;
	
	Long time;
	
	long timePassed;
	
	boolean bluetoothPreviouslyConnected;
	
	boolean finished;
	
	boolean found;
	
	CountDownTimer temporizer;

	private float initHr;

	private float initSo2;

	private float finalHr;

	private float finalSo2;
	
	int soundOn;
	
	private static final int FINISH_SOUND = 0;
	
	private static final int SYSTEMALERT_SOUND = 1;
	
	private static final int MEDICALALERT_SOUND = 2;
	
	private static final int NOTIFICATION_SOUND = 3;
	
	private static final int METRONOME_SOUND = 3;
	
	int lastStatus = 2;
	
	boolean notified = false;

	boolean runningMetro = false;
	
	long timeMetro = 1000;
	
	Hashtable<Long,String> timedMessages;
	
	SensorEventListener sensorListener;
	
	LocationListener locListener;
	
	Location lastLocation;
	
	private float speed;
	private float distance = -1;
	private int steps = 0;
	private int stops = 0;
	private boolean stopped = true;
	
    private Date lasStepTime = new Date();
    private int stepsFollowed;
    private int stepsToRun = 5;
	
    private int mTimeLimit = 6000;
    private int mLimit;
    private float mLastValue = 0.0f;
    private float mScale[] = new float[2];
    private float mYOffset;

    private float mLastDirection = -1.0f;
    private float mLastExtremes[] = new float[2];
    private float mLastDiff = 0.0f;
    private int mLastMatch = -1;
    
    boolean timeFinished;

	protected int streamVolume;
	
	SoundPool soundpool;
	
	HashMap<Integer, Integer> soundpoolmap;
	
	public ExerciseNotRunnable(Exercise exercise, long time) {
		this.exercise = exercise;
		this.time = time;
		this.finished = false;
		this.soundpoolmap = new HashMap<Integer, Integer>();
		this.soundpool = new SoundPool(4, AudioManager.STREAM_RING, 0); 
		this.soundpoolmap.put(SYSTEMALERT_SOUND, soundpool.load(HealthGenius.myApp, R.raw.systemalert, 1));
		this.soundpoolmap.put(MEDICALALERT_SOUND, soundpool.load(HealthGenius.myApp, R.raw.medicalalert, 1));
		this.soundpoolmap.put(NOTIFICATION_SOUND, soundpool.load(HealthGenius.myApp, R.raw.notification, 1));
		this.soundpoolmap.put(FINISH_SOUND, soundpool.load(HealthGenius.myApp, R.raw.finish, 1));
		this.soundpoolmap.put(METRONOME_SOUND, soundpool.load(HealthGenius.myApp, R.raw.step, 1));
		this.soundOn = 1;
		int h = 480; // TODO: remove this constant
        mYOffset = h * 0.5f;
        mScale[0] = - (h * 0.5f * (1.0f / (SensorManager.STANDARD_GRAVITY * 2)));
        mScale[1] = - (h * 0.5f * (1.0f / (SensorManager.MAGNETIC_FIELD_EARTH_MAX)));
        this.mLimit = HealthGenius.myMobileManager.pedometerCalValue;
	}

	@Override
	public void run() {
		handler.sendEmptyMessage(100);
	}
	
	public void pulseOximetry(boolean atInit) {
		boolean fin = false;
    	BluetoothSocket socket = null;
		handler.sendEmptyMessage(0);
		if (HealthGenius.myBluetoothAdapter == null) {
			handler.sendEmptyMessage(3);
			return;
		}
		if (HealthGenius.mySensorManager.reiniting) {
			handler.sendEmptyMessage(31);
			while (HealthGenius.mySensorManager.reiniting) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			handler.sendEmptyMessage(0);
		}
		else if (!HealthGenius.myBluetoothAdapter.isEnabled()) {
			HealthGenius.myBluetoothAdapter.enable();
			bluetoothPreviouslyConnected = false;
			handler.sendEmptyMessage(4);
			while (!HealthGenius.myBluetoothAdapter.isEnabled()) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			handler.sendEmptyMessage(0);
		}
		else {
			bluetoothPreviouslyConnected = true;
		}
		Set<BluetoothDevice> pairedDevices = HealthGenius.myBluetoothAdapter.getBondedDevices();
		// If there are paired devices
		if (pairedDevices.size() > 0) {
		    // Loop through paired devices
		    for (BluetoothDevice device : pairedDevices) {
		        // Add the name and address to an array adapter to show in a ListView
		        String name = device.getName();
		        if (name.contains("Nonin")) {
					try {
		        		found = true;
//						socket = device.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
//						handler.sendEmptyMessage(5);
//						HealthGenius.myBluetoothAdapter.cancelDiscovery();
//						socket.connect();
		        		BluetoothDevice hxm = BluetoothAdapter.getDefaultAdapter().getRemoteDevice(device.getAddress());
		        		Method m;
						try {
							m = hxm.getClass().getMethod("createRfcommSocket", new Class[]{int.class});
			        		socket = (BluetoothSocket)m.invoke(hxm, Integer.valueOf(1));
			        		handler.sendEmptyMessage(5);
			        		socket.connect();
			        	} catch (SecurityException e) {
							e.printStackTrace();
						} catch (NoSuchMethodException e) {
							e.printStackTrace();
						} catch (IllegalArgumentException e) {
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						} catch (InvocationTargetException e) {
							e.printStackTrace();
						}
						handler.sendEmptyMessage(1);
					    InputStream in = socket.getInputStream();
					    OutputStream out = socket.getOutputStream();
					    byte[] retrieve = { 0x44, 0x31};
					    out.write(retrieve);
					    byte [] ack = new byte [1];
					    in.read(ack);
					    if (ack[0] == 0x15) {
					    	handler.sendEmptyMessage(10);
					    	return;
					    }
						handler.sendEmptyMessage(6);
//					    byte [] data = new byte [3];
				  		long timeStart = System.currentTimeMillis();
				  		boolean goodMeasure = false;
					    while (((System.currentTimeMillis() - timeStart) < (15 * 1000)) && (!goodMeasure) && (!fin)) {
					    	byte [] data = new byte [3];
							in.read(data);
						    if ((data[0] != -128)&&(data[2] == -128)) {
			  		    		data[2] = data[1];
			  		    		data[1] = data[0];
			  		    		data[0] = -128;
			  		    	}
			  		    	if ((data[0] != -128)&&(data[1] == -128)) {
			  		    		data[1] = data[2];
			  		    		data[2] = data[0];
			  		    		data[0] = -128;
			  		    	}
				  		    if ((data[0] & 0x7C)!=0x00  || (data[0] & 0x03)==0x03)  {
				  		    	continue;
					  	    }
					  	    else {
							    float hr = (float)(((data[0] & 0x03) << 7) + data[1]);
							    float so2 = (float)((int)data[2]);
							    if ((so2 < 100.0f)&&(so2 > 60.0f)&&(hr < 220.0f)&&(hr > 20.0f)) {
								    goodMeasure = true;
								    if (atInit) {
									    exercise.results.put(com.o2hlink.healthgenius.data.sensor.SensorManager.DATAID_HR_INIT, hr);
									    exercise.results.put(com.o2hlink.healthgenius.data.sensor.SensorManager.DATAID_SO2_INIT, so2);
								    }
								    else {
									    exercise.results.put(com.o2hlink.healthgenius.data.sensor.SensorManager.DATAID_HR_FINAL, hr);
									    exercise.results.put(com.o2hlink.healthgenius.data.sensor.SensorManager.DATAID_SO2_FINAL, so2);
								    }
									if (atInit) handler.sendEmptyMessage(2);
									else handler.sendEmptyMessage(201);
									continue;
							    }
					  	    }
					    }
				        if ((!fin)&&(!goodMeasure)) {
					    	handler.sendEmptyMessage(9);
					    }
//					    in.close();
//					    out.close();
//					    socket.close();
				        if (in != null) {
				                try {in.close();} catch (Exception e) {}
				                in = null;
				        }
				        if (out != null) {
				                try {out.close();} catch (Exception e) {}
				                out = null;
				        }
				        if (socket != null) {
				                try {socket.close();} catch (Exception e) {}
				                socket = null;
				        }
					} catch (IOException e) {
						handler.sendEmptyMessage(7);
						try {
							socket.close();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
						e.printStackTrace();
					} catch (NullPointerException e) {
						handler.sendEmptyMessage(7);
						e.printStackTrace();
					}
		        }
		    }
		}
        if (!found) handler.sendEmptyMessage(8);
	}
	
	private Handler handler = new Handler() {
		AnimationDrawable animation;
		ImageView animationFrame;
		TextView start;
		TextView text;
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				animationFrame = (ImageView) HealthGenius.myApp.findViewById(R.id.popupImage);
				animationFrame.setVisibility(View.VISIBLE);
				animationFrame.setBackgroundResource(R.drawable.loading);
				animation = (AnimationDrawable) animationFrame.getBackground();	
				animation.start();			
				HealthGenius.myUIManager.UImisc.loadInfoPopupWithoutExiting(HealthGenius.myLanguageManager.SENSORS_SEARCHING);
//				Toast toast = Toast.makeText(HealthGenius.myApp, HealthGenius.myLanguageManager.SENSORS_SEARCHING, Toast.LENGTH_SHORT);
//				toast.show();
				break;
			case 1:
				animation.stop();
				HealthGenius.myUIManager.state = UIManager.UI_STATE_SENSORING;
				HealthGenius.myUIManager.loadScreen(R.layout.waiting);
				start = (TextView) HealthGenius.myApp.findViewById(R.id.startText);
				text = (TextView) HealthGenius.myApp.findViewById(R.id.infoText);
				start.setText(HealthGenius.myLanguageManager.SENSORS_PULSEOXI_TITLE);
				text.setText(HealthGenius.myLanguageManager.SENSORS_BLUETOOTH_CONFIG);
				animationFrame = (ImageView) HealthGenius.myApp.findViewById(R.id.progress);
				animationFrame.setVisibility(View.VISIBLE);
				animationFrame.setBackgroundResource(R.drawable.loadingbig);
				animation = (AnimationDrawable) animationFrame.getBackground();	
				animation.start();		
				ImageButton cancel = (ImageButton) HealthGenius.myApp.findViewById(R.id.stop);
				cancel.setOnClickListener(new OnClickListener() {	
					@Override
					public void onClick(View v) {
						finished = true;
						exercise.finishMeasurements(false, null);
					}
				});
//				Toast toast2 = Toast.makeText(HealthGenius.myApp, HealthGenius.myLanguageManager.SENSORS_BLUETOOTH_CONFIG, Toast.LENGTH_SHORT);
//				toast2.show();
				break;
			case 2:
				setExerciseScreen();
				break;
			case 3:
				animation.stop();
				HealthGenius.myUIManager.UImisc.loadInfoPopup(HealthGenius.myLanguageManager.SENSORS_NOBLUETOOTH);
				break;
			case 4:
//				Toast toast3 = Toast.makeText(HealthGenius.myApp, HealthGenius.myLanguageManager.SENSORS_BLUETOOTH_NOTCONNECTED, Toast.LENGTH_SHORT);
//				toast3.show();
				HealthGenius.myUIManager.UImisc.loadInfoPopupWithoutExiting(HealthGenius.myLanguageManager.SENSORS_BLUETOOTH_NOTCONNECTED);
				break;
			case 5:
//				Toast toast4 = Toast.makeText(HealthGenius.myApp, HealthGenius.myLanguageManager.SENSORS_BLUETOOTH_CONNECTING, Toast.LENGTH_SHORT);
//				toast4.show();
				HealthGenius.myUIManager.UImisc.loadInfoPopupWithoutExiting(HealthGenius.myLanguageManager.SENSORS_BLUETOOTH_CONNECTING);
				break;
			case 6:
				TextView text3 = (TextView) HealthGenius.myApp.findViewById(R.id.infoText);
				text3.setText(HealthGenius.myLanguageManager.SENSORS_READING);
				break;
			case 7:
				if (HealthGenius.mySensorManager.eventAssociated != null) 
					HealthGenius.myUIManager.UIcalendar.loadScheduleDay(HealthGenius.mySensorManager.eventAssociated.getDate()); 
				else
					HealthGenius.myUIManager.UImeas.loadSensorList();
				if (bluetoothPreviouslyConnected) HealthGenius.mySensorManager.reinitBluetooth();
				else HealthGenius.myBluetoothAdapter.disable();
				HealthGenius.myUIManager.UImisc.loadInfoPopup(HealthGenius.myLanguageManager.SENSORS_BLUETOOTH_NOTCONNECTION);
				break;
			case 8:
				if ((HealthGenius.mySensorManager.eventAssociated != null)) 
					HealthGenius.myUIManager.UIcalendar.loadScheduleDay(HealthGenius.mySensorManager.eventAssociated.getDate()); 
				else
					HealthGenius.myUIManager.UImeas.loadSensorList();
				if (bluetoothPreviouslyConnected) HealthGenius.mySensorManager.reinitBluetooth();
				else HealthGenius.myBluetoothAdapter.disable();
				HealthGenius.myUIManager.UImisc.loadInfoPopup(HealthGenius.myLanguageManager.SENSORS_BLUETOOTH_NOTPAIRED);
				break;
			case 9:
				if ((HealthGenius.mySensorManager.eventAssociated != null)) 
					HealthGenius.myUIManager.UIcalendar.loadScheduleDay(HealthGenius.mySensorManager.eventAssociated.getDate()); 
				else
					HealthGenius.myUIManager.UImeas.loadSensorList();
				if (bluetoothPreviouslyConnected) HealthGenius.mySensorManager.reinitBluetooth();
				else HealthGenius.myBluetoothAdapter.disable();
				HealthGenius.myUIManager.UImisc.loadInfoPopup(HealthGenius.myLanguageManager.SENSORS_BLUETOOTH_NOTMEASURED);
				break;
			case 10:
				if ((HealthGenius.mySensorManager.eventAssociated != null)) 
					HealthGenius.myUIManager.UIcalendar.loadScheduleDay(HealthGenius.mySensorManager.eventAssociated.getDate()); 
				else
					HealthGenius.myUIManager.UImeas.loadSensorList();
				if (bluetoothPreviouslyConnected) HealthGenius.mySensorManager.reinitBluetooth();
				else HealthGenius.myBluetoothAdapter.disable();
				HealthGenius.myUIManager.UImisc.loadInfoPopup(HealthGenius.myLanguageManager.SENSORS_BLUETOOTH_NOTCONFIG);
				break;
			case 31:
				HealthGenius.myUIManager.UImisc.loadInfoPopupWithoutExiting(HealthGenius.myLanguageManager.SENSORS_CONFIG);
				break;
			case 51:
				LocationManager myLocationManager = (LocationManager)HealthGenius.myApp.getSystemService(Context.LOCATION_SERVICE); 
				final Criteria criteria = new Criteria();
                criteria.setAccuracy(Criteria.ACCURACY_FINE);
                criteria.setAltitudeRequired(false);
                criteria.setBearingRequired(false);
                criteria.setCostAllowed(true);
                criteria.setPowerRequirement(Criteria.POWER_LOW);
                String bestProvider = myLocationManager.getBestProvider(criteria, true);
                locListener = new LocationListener() {							
					@Override
					public void onStatusChanged(String provider, int status, Bundle extras) {
					}
					@Override
					public void onProviderEnabled(String provider) {
					}
					@Override
					public void onProviderDisabled(String provider) {
					}
					@Override
					public void onLocationChanged(Location location) {
						if (lastLocation == null) {
							lastLocation = location;
							distance = 0.0f;
							speed = location.getSpeed();
							HealthGenius.myUIManager.UImeas.updateExerciseScreen(speed, distance);
						}
						else {
							distance += location.distanceTo(lastLocation);
							lastLocation = location;
							speed = location.getSpeed();
							HealthGenius.myUIManager.UImeas.updateExerciseScreen(speed, distance);
						}
					}
				};
                if (bestProvider != null) myLocationManager.requestLocationUpdates(bestProvider, 5, 10, locListener);
				break;
			case 71:
				animation.start();
				HealthGenius.myUIManager.UImeas.updateExerciseScreen(0, 0, steps, stops, "2: ");
				break;
			case 100:
				HealthGenius.myUIManager.loadScreen(R.layout.info);
				final TextView text2 = (TextView) HealthGenius.myApp.findViewById(R.id.textInfo);
				text2.setText(HealthGenius.myLanguageManager.SENSORS_EXERCISE_PREPULSI + ". " + HealthGenius.myLanguageManager.SENSORS_EXERCISE_PULSIINS);
				final LinearLayout board = (LinearLayout) HealthGenius.myApp.findViewById(R.id.mainLayout);
				OnClickListener listener = new OnClickListener() {
					@Override
					public void onClick(View v) {
						board.setClickable(false);
						text2.setClickable(false);
						pulseOximetry(true);
					}
				};
				board.setOnClickListener(listener);
				text2.setOnClickListener(listener);
				break;
			case 200:
				HealthGenius.myUIManager.loadScreen(R.layout.info);
				final TextView text1 = (TextView) HealthGenius.myApp.findViewById(R.id.textInfo);
				text1.setText(HealthGenius.myLanguageManager.SENSORS_EXERCISE_POSTPULSI + ". " + HealthGenius.myLanguageManager.SENSORS_EXERCISE_PULSIINS);
				final LinearLayout board2 = (LinearLayout) HealthGenius.myApp.findViewById(R.id.mainLayout);
				OnClickListener listener2 = new OnClickListener() {
					@Override
					public void onClick(View v) {
						board2.setClickable(false);
						text1.setClickable(false);
						pulseOximetry(false);
					}
				};
				board2.setOnClickListener(listener2);
				text1.setOnClickListener(listener2);
				break;
			case 201:
				exercise.nextStep();
			}
		}
	};
	
	private void setExerciseScreen() {
		HealthGenius.myUIManager.UImeas.loadExerciseScreen(HealthGenius.myLanguageManager.SENSORS_WAITING_DATA, time);
		((TextView) HealthGenius.myApp.findViewById(R.id.startText)).setText(exercise.name);
		ImageButton cancel = (ImageButton) HealthGenius.myApp.findViewById(R.id.stop);
		cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				temporizer.cancel();
				finished = false;
				exercise.finishMeasurements(false, null);
			}
		});
		ImageButton confirm = (ImageButton) HealthGenius.myApp.findViewById(R.id.good);
		confirm.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				temporizer.cancel();
				finished = true;
				exercise.results.put(com.o2hlink.healthgenius.data.sensor.SensorManager.DATAID_DISTANCE, distance);
		    	exercise.results.put(com.o2hlink.healthgenius.data.sensor.SensorManager.DATAID_HR_AVRG, -1f);
				exercise.results.put(com.o2hlink.healthgenius.data.sensor.SensorManager.DATAID_HR_PEAK, -1f);
				exercise.results.put(com.o2hlink.healthgenius.data.sensor.SensorManager.DATAID_HR_LOW, -1f);
				exercise.results.put(com.o2hlink.healthgenius.data.sensor.SensorManager.DATAID_SO2_AVRG, -1f);
				exercise.results.put(com.o2hlink.healthgenius.data.sensor.SensorManager.DATAID_SO2_PEAK, -1f);
				exercise.results.put(com.o2hlink.healthgenius.data.sensor.SensorManager.DATAID_SO2_LOW, -1f);
				if (timePassed > time) timePassed = time;
				exercise.results.put(com.o2hlink.healthgenius.data.sensor.SensorManager.DATAID_TIME_EXE, (float)(timePassed/1000));
		    	exercise.results.put(com.o2hlink.healthgenius.data.sensor.SensorManager.DATAID_TIME_DATA, -1f);
		    	exercise.results.put(com.o2hlink.healthgenius.data.sensor.SensorManager.DATAID_STEPS, (float)steps);
		    	exercise.results.put(com.o2hlink.healthgenius.data.sensor.SensorManager.DATAID_STOPS, (float)stops);
				handler.sendEmptyMessage(200);
			}
		});
		final ImageButton soundon = (ImageButton) HealthGenius.myApp.findViewById(R.id.soundon);
		final ImageButton soundoff = (ImageButton) HealthGenius.myApp.findViewById(R.id.soundoff);
		soundon.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				soundOn = 1;
				soundoff.setVisibility(View.VISIBLE);
				soundon.setVisibility(View.GONE);
			}
		});
		final SeekBar metronome = (SeekBar) HealthGenius.myApp.findViewById(R.id.metronome);
		metronome.setVisibility(View.GONE);
		final ImageButton metronomeon = (ImageButton) HealthGenius.myApp.findViewById(R.id.metronomeon);
		final ImageButton metronomeoff = (ImageButton) HealthGenius.myApp.findViewById(R.id.metronomeoff);
		metronomeon.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				runningMetro = true;
				metronome.setVisibility(View.VISIBLE);
				metronomeoff.setVisibility(View.VISIBLE);
				metronomeon.setVisibility(View.GONE);
			}
		});
		metronomeoff.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				runningMetro = false;
				metronome.setVisibility(View.INVISIBLE);
				metronomeon.setVisibility(View.VISIBLE);
				metronomeoff.setVisibility(View.GONE);
			}
		});
		metronome.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
			}
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
			}
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				if (progress < 25) timeMetro = 2000;
				else if (progress < 50) timeMetro = 1000;
				else if (progress < 75) timeMetro = 500;
				else timeMetro = 250;
			}
		});
		Runnable metronomerun = new Runnable() {
			@Override
			public void run() {
				while ((!timeFinished)&&(finished)) {
					streamVolume = HealthGenius.myAudioManager.getStreamVolume(AudioManager.STREAM_RING);
					streamVolume = soundOn*(streamVolume/HealthGenius.myAudioManager.getStreamMaxVolume(AudioManager.STREAM_RING));
			        if (runningMetro) soundpool.play(soundpoolmap.get(METRONOME_SOUND), streamVolume, streamVolume, 1, 0, 1f);
				    try {
						Thread.sleep(timeMetro);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				 }
			}
		};
		Thread metro = new Thread(metronomerun);
		metro.start();
		handler.sendEmptyMessage(51);
		sensorListener = new SensorEventListener() {
			@Override
			public void onSensorChanged(SensorEvent event) {
				int j = (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) ? 1 : 0;
				if (j == 0) { 
                    float vSum = 0;
                    for (int i=0 ; i<3 ; i++) {
                        final float v = mYOffset + event.values[i] * mScale[j];
                        vSum += v;
                    }
                    float v = vSum / 3;
                    
                    float direction = (v > mLastValue ? 1 : (v < mLastValue ? -1 : 0));
                    if (direction == - mLastDirection) {
                        // Direction changed
                        int extType = (direction > 0 ? 0 : 1); // minumum or maximum?
                        mLastExtremes[extType] = mLastValue;
                        float diff = Math.abs(mLastExtremes[extType] - mLastExtremes[1 - extType]);
                        if (diff > mLimit) {
                            boolean isAlmostAsLargeAsPrevious = diff > (mLastDiff*2/3);
                            boolean isPreviousLargeEnough = mLastDiff > (diff/3);
                            boolean isNotContra = (mLastMatch != 1 - extType);
                            if (isAlmostAsLargeAsPrevious && isPreviousLargeEnough && isNotContra) {
                                // TODO
                            	steps++;
                            	stepsFollowed++;
                            	if (stepsFollowed >= stepsToRun) {
                            		stopped = false;
                            	}
                            	lasStepTime = new Date();
                            	handler.sendEmptyMessage(71);
                            	mLastMatch = extType;
                            }
                            else {
                                mLastMatch = -1;
                            }
                        }
                        mLastDirection = direction;
                        mLastDiff = diff;
                    }
                    mLastValue = v;
                    Date now = new Date();
                    if (((now.getTime() - lasStepTime.getTime()) > mTimeLimit)&&(!stopped)) {
                    	stopped = true;
                    	stops++;
                    	stepsFollowed = 0;
                    	handler.sendEmptyMessage(71);
                    }
                }
			}
			@Override
			public void onAccuracyChanged(Sensor sensor, int accuracy) {}
		};
		HealthGenius.myAccelerometerManager.registerListener(sensorListener, HealthGenius.myAccelerometerManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_FASTEST);
		temporizer = new CountDownTimer(time, 1000) {
			@Override
			public void onTick(long millisUntilFinished) {
			    int seconds = (int) ((time - millisUntilFinished) / 1000);
			    timePassed = seconds*1000;
			    int minutes = seconds / 60;
			    seconds     = seconds % 60;
			    int hours = minutes / 60;
			    minutes = minutes % 60;
			    try {
			    	((TextView) HealthGenius.myApp.findViewById(R.id.timerText)).setText(String.format("%01d:%02d:%02d", hours, minutes, seconds));			
			    } catch (Exception e) {
			    	this.cancel();
				}
			}
			@Override
			public void onFinish() {
				finished = true;
				exercise.results.put(com.o2hlink.healthgenius.data.sensor.SensorManager.DATAID_DISTANCE, distance);
		    	exercise.results.put(com.o2hlink.healthgenius.data.sensor.SensorManager.DATAID_HR_AVRG, -1f);
				exercise.results.put(com.o2hlink.healthgenius.data.sensor.SensorManager.DATAID_HR_PEAK, -1f);
				exercise.results.put(com.o2hlink.healthgenius.data.sensor.SensorManager.DATAID_HR_LOW, -1f);
				exercise.results.put(com.o2hlink.healthgenius.data.sensor.SensorManager.DATAID_SO2_AVRG, -1f);
				exercise.results.put(com.o2hlink.healthgenius.data.sensor.SensorManager.DATAID_SO2_PEAK, -1f);
				exercise.results.put(com.o2hlink.healthgenius.data.sensor.SensorManager.DATAID_SO2_LOW, -1f);
				if (timePassed > time) timePassed = time;
				exercise.results.put(com.o2hlink.healthgenius.data.sensor.SensorManager.DATAID_TIME_EXE, (float)(timePassed/1000));
		    	exercise.results.put(com.o2hlink.healthgenius.data.sensor.SensorManager.DATAID_TIME_DATA, -1f);
		    	exercise.results.put(com.o2hlink.healthgenius.data.sensor.SensorManager.DATAID_STEPS, (float)steps);
		    	exercise.results.put(com.o2hlink.healthgenius.data.sensor.SensorManager.DATAID_STOPS, (float)stops);
		    	((Vibrator)HealthGenius.myApp.getSystemService(Context.VIBRATOR_SERVICE)).vibrate(3000);
		    	handler.sendEmptyMessage(200);
			}
		};
		temporizer.start();
	}
	
}
