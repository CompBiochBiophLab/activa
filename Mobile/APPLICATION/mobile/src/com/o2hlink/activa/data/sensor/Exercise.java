package com.o2hlink.activa.data.sensor;

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
import com.o2hlink.activa.Activa;
import com.o2hlink.activa.ActivaUtil;
import com.o2hlink.activa.R;
import com.o2hlink.activa.background.SendSensorResult;
import com.o2hlink.activa.ui.UIManager;

public class Exercise extends com.o2hlink.activa.data.sensor.Sensor {
	
	public ExerciseRunnable exerciseRunnabe;
	
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
	
	int[] order = {com.o2hlink.activa.data.sensor.SensorManager.DATAID_HR_INIT, com.o2hlink.activa.data.sensor.SensorManager.DATAID_HR_FINAL, com.o2hlink.activa.data.sensor.SensorManager.DATAID_HR_AVRG, com.o2hlink.activa.data.sensor.SensorManager.DATAID_HR_PEAK, com.o2hlink.activa.data.sensor.SensorManager.DATAID_HR_LOW, -1,
		com.o2hlink.activa.data.sensor.SensorManager.DATAID_SO2_INIT, com.o2hlink.activa.data.sensor.SensorManager.DATAID_SO2_FINAL, com.o2hlink.activa.data.sensor.SensorManager.DATAID_SO2_AVRG, com.o2hlink.activa.data.sensor.SensorManager.DATAID_SO2_PEAK, com.o2hlink.activa.data.sensor.SensorManager.DATAID_SO2_LOW, -1,
		com.o2hlink.activa.data.sensor.SensorManager.DATAID_DISTANCE, com.o2hlink.activa.data.sensor.SensorManager.DATAID_STEPS, com.o2hlink.activa.data.sensor.SensorManager.DATAID_STOPS, -1,
		com.o2hlink.activa.data.sensor.SensorManager.DATAID_TIME_EXE, com.o2hlink.activa.data.sensor.SensorManager.DATAID_TIME_DATA};

	public int state;
	
	Hashtable<Long,String> timedMessages;

	private NumericQuestion borgAir;

	private NumericQuestion borgFatigue;

	public Exercise() {
		this.name = Activa.myLanguageManager.SENSORS_EXERCISE_TITLE;
		this.icon = R.drawable.sport;
		this.id = com.o2hlink.activa.data.sensor.SensorManager.ID_EXERCISE;
		this.instance = this;
		this.state = 0;
		this.borgAir = new NumericQuestion();
		this.borgAir.setId(-1l);
		this.borgAir.setName(Activa.myLanguageManager.PROGRAMS_WALKING_BORGAIR);
		this.borgAir.setOptional(false);
		this.borgFatigue = new NumericQuestion();
		this.borgFatigue.setId(-1l);
		this.borgFatigue.setName(Activa.myLanguageManager.PROGRAMS_WALKING_BORGFATIGUE);
		this.borgFatigue.setOptional(false);
		this.time = 7200000;
	}

	@Override
	public Sample getSample() {
		com.o2hlink.activ8.client.entity.Exercise sample = new com.o2hlink.activ8.client.entity.Exercise();
		sample.setFinalDyspnea(this.results.get(com.o2hlink.activa.data.sensor.SensorManager.DATAID_BORG_AIR_POST));
		sample.setFinalFatigue(this.results.get(com.o2hlink.activa.data.sensor.SensorManager.DATAID_BORG_FATIGUE_POST));
		sample.setInitialDyspnea(this.results.get(com.o2hlink.activa.data.sensor.SensorManager.DATAID_BORG_AIR_PRE));
		sample.setInitialFatigue(this.results.get(com.o2hlink.activa.data.sensor.SensorManager.DATAID_BORG_FATIGUE_PRE));
		firstquestans = Math.round(this.results.get(com.o2hlink.activa.data.sensor.SensorManager.DATAID_EXE_ONE));
		secquestans = Math.round(this.results.get(com.o2hlink.activa.data.sensor.SensorManager.DATAID_EXE_TWO));
		thrquestans = Math.round(this.results.get(com.o2hlink.activa.data.sensor.SensorManager.DATAID_EXE_THREE));
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
		sample.setDistance(new Double(this.results.get(com.o2hlink.activa.data.sensor.SensorManager.DATAID_DISTANCE)));
		sample.setSteps(Float.valueOf(this.results.get(com.o2hlink.activa.data.sensor.SensorManager.DATAID_STEPS)).intValue());
		sample.setStops(Float.valueOf(this.results.get(com.o2hlink.activa.data.sensor.SensorManager.DATAID_STOPS)).intValue());
		sample.setTime(this.results.get(com.o2hlink.activa.data.sensor.SensorManager.DATAID_TIME_EXE));
		sample.setHeartRateInitial(new Double(this.results.get(com.o2hlink.activa.data.sensor.SensorManager.DATAID_HR_INIT)));
		sample.setHeartRateFinal(new Double(this.results.get(com.o2hlink.activa.data.sensor.SensorManager.DATAID_HR_FINAL)));
		sample.setHeartRateAverage(new Double(this.results.get(com.o2hlink.activa.data.sensor.SensorManager.DATAID_HR_AVRG)));
		sample.setHeartRateLow(new Double(this.results.get(com.o2hlink.activa.data.sensor.SensorManager.DATAID_HR_LOW)));
		sample.setHeartRatePeak(new Double(this.results.get(com.o2hlink.activa.data.sensor.SensorManager.DATAID_HR_PEAK)));
		sample.setOxygenInitial(new Double(this.results.get(com.o2hlink.activa.data.sensor.SensorManager.DATAID_SO2_INIT)));
		sample.setOxygenFinal(new Double(this.results.get(com.o2hlink.activa.data.sensor.SensorManager.DATAID_SO2_FINAL)));
		sample.setOxygenAverage(new Double(this.results.get(com.o2hlink.activa.data.sensor.SensorManager.DATAID_SO2_AVRG)));
		sample.setOxygenLow(new Double(this.results.get(com.o2hlink.activa.data.sensor.SensorManager.DATAID_SO2_LOW)));
		sample.setOxygenPeak(new Double(this.results.get(com.o2hlink.activa.data.sensor.SensorManager.DATAID_SO2_PEAK)));
		sample.setDate(new Date());
		if (Activa.mySensorManager.eventAssociated != null)
			sample.setEvent(Activa.mySensorManager.eventAssociated.id);
		else sample.setEvent(null);
		return sample;
	}

	@Override
	public String getSensorSampleForPending() {
		String returned = "<MEASUREMENT ID=\"" + com.o2hlink.activa.data.sensor.SensorManager.ID_EXERCISE;
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

	public static com.o2hlink.activa.data.sensor.Sensor getSampleFromPending(String xml) {
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
	                	sample.sampleDate = ActivaUtil.XMLDateToDate(info.getAttributeValue(info.getNamespace(), "DATE"));
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
		((Vibrator)Activa.myApp.getSystemService(Context.VIBRATOR_SERVICE)).vibrate(3000);
    	if (this.bluetoothPreviouslyConnected) Activa.mySensorManager.reinitBluetooth();
		else Activa.myBluetoothAdapter.disable();
		this.results = result;
		try {
			if ((Activa.mySensorManager.eventAssociated != null)&&true)  {
				Activa.mySensorManager.eventAssociated.state = 0;
				Activa.myCalendarManager.saveCalendar();
			}
			Activa.myUIManager.finishSensorMeasurement(name, outcome, instance, order);
		} catch (Exception e) {
			finishMeasurements(true, results);
		}
	}

	@Override
	public String getSensorGlobalResult() {
		String result;
		Float avrgSo2 = this.results.get(com.o2hlink.activa.data.sensor.SensorManager.DATAID_SO2_AVRG);
		if (avrgSo2 > 90.0) {
			result = "2:" + Activa.myLanguageManager.SENSORS_EXERCISE_MESSAGE2_OLD;
		}
		else if (avrgSo2 > 85.0) {
			result = "1:" + Activa.myLanguageManager.SENSORS_EXERCISE_MESSAGE1_OLD;
		}
		else {
			result = "0:" + Activa.myLanguageManager.SENSORS_EXERCISE_MESSAGE0_OLD;
		}	
		return result;
	}

	@Override
	public String passSensorResultToXML() {
		String returned = "<EVENT ID=\"1\" DATETIME=\"" + Activa.myMobileManager.device.getDateTime();
		returned += "\" IDGROUP=\"\" IDAGENDA=\"";
		if (Activa.mySensorManager.eventAssociated != null) {
			Date dateid = new Date(Long.parseLong(Activa.mySensorManager.eventAssociated.id)*1000 - 3600000l);
//			dateid.setTime(dateid.getTime() - dateid.getTimezoneOffset());
			returned +=  ActivaUtil.dateToXMLDate(dateid) + "0" + "\">";		
		}
		else returned += "\">";
		Enumeration<Integer> keys = this.results.keys();
		while (keys.hasMoreElements()) {
			int key = keys.nextElement();
			returned += "<DATA ID=\"" + key + "\" VALUE=\"" + this.results.get(key);
			returned += "\" UNITS=\"" + com.o2hlink.activa.data.sensor.SensorManager.getUnitIDForMeasurementID(key) + "\"/>";
		}
		returned += "</EVENT>";
		return returned;
	}

	@Override
	public void startMeasurement() {
		this.state = 0;
		Activa.myUIManager.state = UIManager.UI_STATE_PROGRAM;
		nextStep();
	}
	
	public void nextStep() {
		this.state++;
		switch (this.state) {
			case 0:
				break;
			case 1:
				Activa.myUIManager.loadScreen(R.layout.info);
				TextView text = (TextView) Activa.myApp.findViewById(R.id.textInfo);
				text.setText(Activa.myLanguageManager.PROGRAMS_EXERCISE_ADVERT0);
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
				Activa.myUIManager.loadScreen(R.layout.info);
				TextView text2 = (TextView) Activa.myApp.findViewById(R.id.textInfo);
				text2.setText(Activa.myLanguageManager.PROGRAMS_EXERCISE_ADVERT1);
				LinearLayout board = (LinearLayout) Activa.myApp.findViewById(R.id.mainLayout);
				board.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						((Exercise) Activa.mySensorManager.sensorList.get(com.o2hlink.activa.data.sensor.SensorManager.ID_EXERCISE)).track = true;
						startMeasurement(time, timedMessages);
					}
				});
				break;	
			case 4:
				Activa.myUIManager.loadScreen(R.layout.info);
				TextView text3 = (TextView) Activa.myApp.findViewById(R.id.textInfo);
				text3.setText(Activa.myLanguageManager.PROGRAMS_EXERCISE_ADVERT2);
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
				Activa.myUIManager.loadScreen(R.layout.info);
				TextView text4 = (TextView) Activa.myApp.findViewById(R.id.textInfo);
				text4.setText(Activa.myLanguageManager.PROGRAMS_EXERCISE_ADVERT3);
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
			case 7:
				loadSecQuest();
				break;
			case 8:
				loadThirdQuest();
				break;
			case 9:
				this.state = 0;
				((Exercise) Activa.mySensorManager.sensorList.get(com.o2hlink.activa.data.sensor.SensorManager.ID_EXERCISE)).results.put(com.o2hlink.activa.data.sensor.SensorManager.DATAID_BORG_AIR_PRE, borgAirPre);
				((Exercise) Activa.mySensorManager.sensorList.get(com.o2hlink.activa.data.sensor.SensorManager.ID_EXERCISE)).results.put(com.o2hlink.activa.data.sensor.SensorManager.DATAID_BORG_AIR_POST, borgAirPost);
				((Exercise) Activa.mySensorManager.sensorList.get(com.o2hlink.activa.data.sensor.SensorManager.ID_EXERCISE)).results.put(com.o2hlink.activa.data.sensor.SensorManager.DATAID_BORG_FATIGUE_PRE, borgFatiguePre);
				((Exercise) Activa.mySensorManager.sensorList.get(com.o2hlink.activa.data.sensor.SensorManager.ID_EXERCISE)).results.put(com.o2hlink.activa.data.sensor.SensorManager.DATAID_BORG_FATIGUE_POST, borgFatiguePost);
				((Exercise) Activa.mySensorManager.sensorList.get(com.o2hlink.activa.data.sensor.SensorManager.ID_EXERCISE)).results.put(com.o2hlink.activa.data.sensor.SensorManager.DATAID_EXE_ONE, (float) firstquestans);
				((Exercise) Activa.mySensorManager.sensorList.get(com.o2hlink.activa.data.sensor.SensorManager.ID_EXERCISE)).results.put(com.o2hlink.activa.data.sensor.SensorManager.DATAID_EXE_TWO, (float) secquestans);
				((Exercise) Activa.mySensorManager.sensorList.get(com.o2hlink.activa.data.sensor.SensorManager.ID_EXERCISE)).results.put(com.o2hlink.activa.data.sensor.SensorManager.DATAID_EXE_THREE, (float) thrquestans);
				Activa.myApp.setContentView(R.layout.sending);
				SendSensorResult sending = new SendSensorResult(Activa.mySensorManager.sensorList.get(com.o2hlink.activa.data.sensor.SensorManager.ID_EXERCISE));
				Thread thread = new Thread(sending, "SENDWALKING");
				thread.start();
		}
	}
	
	public void loadBorgAir(final boolean pre) {
		TextView questionText;
		ImageButton next;
		Activa.myApp.setContentView(R.layout.numquestion);
		final String representation[] = {Activa.myLanguageManager.BORG_0, Activa.myLanguageManager.BORG_05, 
				Activa.myLanguageManager.BORG_1, Activa.myLanguageManager.BORG_1, 
				Activa.myLanguageManager.BORG_2, Activa.myLanguageManager.BORG_2, 
				Activa.myLanguageManager.BORG_3, Activa.myLanguageManager.BORG_3, 
				Activa.myLanguageManager.BORG_4, Activa.myLanguageManager.BORG_4, 
				Activa.myLanguageManager.BORG_5, Activa.myLanguageManager.BORG_5, 
				Activa.myLanguageManager.BORG_5, Activa.myLanguageManager.BORG_5, 
				Activa.myLanguageManager.BORG_7, Activa.myLanguageManager.BORG_7, 
				Activa.myLanguageManager.BORG_7, Activa.myLanguageManager.BORG_7, 
				Activa.myLanguageManager.BORG_9, Activa.myLanguageManager.BORG_9, 
				Activa.myLanguageManager.BORG_10, Activa.myLanguageManager.BORG_10};
		questionText = (TextView) Activa.myApp.findViewById(R.id.questionText);
		final TextView numText = (TextView) Activa.myApp.findViewById(R.id.numText);
		questionText.setText(this.borgAir.getName());
		numText.setText("0 - " + representation[0]);
		if (pre) borgAirPre = 0.0f;
		else borgAirPost = 0.0f;
		SeekBar seekbar = (SeekBar) Activa.myApp.findViewById(R.id.seekbar);
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
		ImageButton prev = (ImageButton) Activa.myApp.findViewById(R.id.previous);
		prev.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				instance.prevStep();
			}
		});
		next = (ImageButton) Activa.myApp.findViewById(R.id.next);
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
		Activa.myApp.setContentView(R.layout.numquestion);
		final String representation[] = {Activa.myLanguageManager.BORG_0, Activa.myLanguageManager.BORG_05, 
				Activa.myLanguageManager.BORG_1, Activa.myLanguageManager.BORG_1, 
				Activa.myLanguageManager.BORG_2, Activa.myLanguageManager.BORG_2, 
				Activa.myLanguageManager.BORG_3, Activa.myLanguageManager.BORG_3, 
				Activa.myLanguageManager.BORG_4, Activa.myLanguageManager.BORG_4, 
				Activa.myLanguageManager.BORG_5, Activa.myLanguageManager.BORG_5, 
				Activa.myLanguageManager.BORG_5, Activa.myLanguageManager.BORG_5, 
				Activa.myLanguageManager.BORG_7, Activa.myLanguageManager.BORG_7, 
				Activa.myLanguageManager.BORG_7, Activa.myLanguageManager.BORG_7, 
				Activa.myLanguageManager.BORG_9, Activa.myLanguageManager.BORG_9, 
				Activa.myLanguageManager.BORG_10, Activa.myLanguageManager.BORG_10};
		questionText = (TextView) Activa.myApp.findViewById(R.id.questionText);
		final TextView numText = (TextView) Activa.myApp.findViewById(R.id.numText);
		questionText.setText(this.borgFatigue.getName());
		numText.setText("0 - " + representation[0]);
		if (pre) borgFatiguePre = 0.0f;
		else borgFatiguePost = 0.0f;
		SeekBar seekbar = (SeekBar) Activa.myApp.findViewById(R.id.seekbar);
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
		ImageButton prev = (ImageButton) Activa.myApp.findViewById(R.id.previous);
		prev.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				instance.prevStep();
			}
		});
		next = (ImageButton) Activa.myApp.findViewById(R.id.next);
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
		Activa.myApp.setContentView(R.layout.monoquestion);
		questionText = (TextView) Activa.myApp.findViewById(R.id.questionText);
		questionText.setText(Activa.myLanguageManager.PROGRAMS_EXERCISE_FIRSTQUEST_TITLE);
		final String answers[] = {Activa.myLanguageManager.PROGRAMS_EXERCISE_FIRSTQUEST_ANS1,
				Activa.myLanguageManager.PROGRAMS_EXERCISE_FIRSTQUEST_ANS2,
				Activa.myLanguageManager.PROGRAMS_EXERCISE_FIRSTQUEST_ANS3,
				Activa.myLanguageManager.PROGRAMS_EXERCISE_FIRSTQUEST_ANS4,
				Activa.myLanguageManager.PROGRAMS_EXERCISE_FIRSTQUEST_ANS5,
				Activa.myLanguageManager.PROGRAMS_EXERCISE_FIRSTQUEST_ANS6,
				Activa.myLanguageManager.PROGRAMS_EXERCISE_FIRSTQUEST_ANS7};
		RadioGroup options = (RadioGroup) Activa.myApp.findViewById(R.id.options);
		for (String answer : answers) {
			RadioButton button = new RadioButton(Activa.myApp);
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
		ImageButton prev = (ImageButton) Activa.myApp.findViewById(R.id.previous);
		prev.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				instance.prevStep();
			}
		});
		next = (ImageButton) Activa.myApp.findViewById(R.id.next);
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
		Activa.myApp.setContentView(R.layout.monoquestion);
		questionText = (TextView) Activa.myApp.findViewById(R.id.questionText);
		questionText.setText(Activa.myLanguageManager.PROGRAMS_EXERCISE_SECQUEST_TITLE);
		final String answers[] = {Activa.myLanguageManager.PROGRAMS_EXERCISE_SECQUEST_ANS1,
				Activa.myLanguageManager.PROGRAMS_EXERCISE_SECQUEST_ANS2,
				Activa.myLanguageManager.PROGRAMS_EXERCISE_SECQUEST_ANS3,
				Activa.myLanguageManager.PROGRAMS_EXERCISE_SECQUEST_ANS4};
		RadioGroup options = (RadioGroup) Activa.myApp.findViewById(R.id.options);
		for (String answer : answers) {
			RadioButton button = new RadioButton(Activa.myApp);
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
		ImageButton prev = (ImageButton) Activa.myApp.findViewById(R.id.previous);
		prev.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				instance.prevStep();
			}
		});
		next = (ImageButton) Activa.myApp.findViewById(R.id.next);
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
		Activa.myApp.setContentView(R.layout.monoquestion);
		questionText = (TextView) Activa.myApp.findViewById(R.id.questionText);
		questionText.setText(Activa.myLanguageManager.PROGRAMS_EXERCISE_THIRDQUEST_TITLE);
		final String answers[] = {Activa.myLanguageManager.PROGRAMS_EXERCISE_THIRDQUEST_ANS1,
				Activa.myLanguageManager.PROGRAMS_EXERCISE_THIRDQUEST_ANS2,
				Activa.myLanguageManager.PROGRAMS_EXERCISE_THIRDQUEST_ANS3,
				Activa.myLanguageManager.PROGRAMS_EXERCISE_THIRDQUEST_ANS4};
		RadioGroup options = (RadioGroup) Activa.myApp.findViewById(R.id.options);
		for (String answer : answers) {
			RadioButton button = new RadioButton(Activa.myApp);
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
		ImageButton prev = (ImageButton) Activa.myApp.findViewById(R.id.previous);
		prev.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				instance.prevStep();
			}
		});
		next = (ImageButton) Activa.myApp.findViewById(R.id.next);
		next.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				instance.nextStep();
			}
		});
	}
	
	public void prevStep() {
		if (this.state <= 1) {
			Activa.myUIManager.loadGeneratedSubenvironment(Activa.myUIManager.lastSubenv, true);
		}
		this.state -= 2;
		this.nextStep();
	}
	
	public void startMeasurement(long time, Hashtable<Long,String> timedMessages) {
		PowerManager pm = (PowerManager) Activa.myApp.getSystemService(Context.POWER_SERVICE);
	    wakeLock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK|PowerManager.ACQUIRE_CAUSES_WAKEUP, "Exercise");
	    wakeLock.acquire();
	    Activa.myApp.getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		this.results = new Hashtable<Integer, Float>();
		this.sampleDate = new Date();
		if (Activa.mySensorManager.eventAssociated != null) {
			this.sampleEventId =  Activa.mySensorManager.eventAssociated.id;		
		} else this.sampleEventId = null;
		Activa.myBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		this.exerciseRunnabe = new ExerciseRunnable(this, time, timedMessages);
		this.thread = new Thread(this.exerciseRunnabe);
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
	
	private float max_hr = 220.0f - ((System.currentTimeMillis() - Activa.myMobileManager.user.getBirthdate().getTime())/(100*60*60*24*365));
	
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
		this.soundpoolmap.put(SYSTEMALERT_SOUND, soundpool.load(Activa.myApp, R.raw.systemalert, 1));
		this.soundpoolmap.put(MEDICALALERT_SOUND, soundpool.load(Activa.myApp, R.raw.medicalalert, 1));
		this.soundpoolmap.put(NOTIFICATION_SOUND, soundpool.load(Activa.myApp, R.raw.notification, 1));
		this.soundpoolmap.put(FINISH_SOUND, soundpool.load(Activa.myApp, R.raw.finish, 1));
		this.soundpoolmap.put(METRONOME_SOUND, soundpool.load(Activa.myApp, R.raw.step, 1));
		this.soundOn = 1;
		int h = 480; // TODO: remove this constant
        mYOffset = h * 0.5f;
        mScale[0] = - (h * 0.5f * (1.0f / (SensorManager.STANDARD_GRAVITY * 2)));
        mScale[1] = - (h * 0.5f * (1.0f / (SensorManager.MAGNETIC_FIELD_EARTH_MAX)));
        this.mLimit = Activa.myMobileManager.pedometerCalValue;
        this.exercise.hrtrack = new HashMap<Float,Float>();
        this.exercise.so2track = new HashMap<Float,Float>();
	}

	public String getGlobalResult() {
		String result;
		if (lastSo2 == 0.0f) return "2:" + Activa.myLanguageManager.SENSORS_OBTAINING_DATA;
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
						this.streamVolume = Activa.myAudioManager.getStreamVolume(AudioManager.STREAM_RING);
						this.streamVolume = soundOn*(this.streamVolume/Activa.myAudioManager.getStreamMaxVolume(AudioManager.STREAM_RING));
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
				result += Activa.myLanguageManager.SENSORS_EXERCISE_MESSAGE2;
			}
			else if (lastSo2 > 85.0) {
				result += Activa.myLanguageManager.SENSORS_EXERCISE_MESSAGE1;
			}
			else {
				result += Activa.myLanguageManager.SENSORS_EXERCISE_MESSAGE0;
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
		if (Activa.myBluetoothAdapter == null) {
			handler.sendEmptyMessage(3);
			return;
		}
		if (Activa.mySensorManager.reiniting) {
			handler.sendEmptyMessage(31);
			while (Activa.mySensorManager.reiniting) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			handler.sendEmptyMessage(0);
		}
		else if (!Activa.myBluetoothAdapter.isEnabled()) {
			Activa.myBluetoothAdapter.enable();
			this.exercise.bluetoothPreviouslyConnected = false;
			handler.sendEmptyMessage(4);
			while (!Activa.myBluetoothAdapter.isEnabled()) {
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
		Set<BluetoothDevice> pairedDevices = Activa.myBluetoothAdapter.getBondedDevices();
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
//						Activa.myBluetoothAdapter.cancelDiscovery();
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
									streamVolume = Activa.myAudioManager.getStreamVolume(AudioManager.STREAM_RING);
									streamVolume = soundOn*(streamVolume/Activa.myAudioManager.getStreamMaxVolume(AudioManager.STREAM_RING));
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
						Activa.myAccelerometerManager.registerListener(sensorListener, Activa.myAccelerometerManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_FASTEST);
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
				  		    	if (lost == 5) ((Vibrator)Activa.myApp.getSystemService(Context.VIBRATOR_SERVICE)).vibrate(300);
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
					  		    	if (bad == 10) ((Vibrator)Activa.myApp.getSystemService(Context.VIBRATOR_SERVICE)).vibrate(300);
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
							LocationManager myLocationManager = (LocationManager)Activa.myApp.getSystemService(Context.LOCATION_SERVICE);
							myLocationManager.removeUpdates(locListener);
					    	exercise.results.put(com.o2hlink.activa.data.sensor.SensorManager.DATAID_DISTANCE, distance);
					    	exercise.results.put(com.o2hlink.activa.data.sensor.SensorManager.DATAID_HR_AVRG, this.avrgHr);
					    	exercise.results.put(com.o2hlink.activa.data.sensor.SensorManager.DATAID_HR_PEAK, this.peakHr);
					    	exercise.results.put(com.o2hlink.activa.data.sensor.SensorManager.DATAID_HR_LOW, this.lowHr);
					    	exercise.results.put(com.o2hlink.activa.data.sensor.SensorManager.DATAID_HR_INIT, this.initHr);
					    	exercise.results.put(com.o2hlink.activa.data.sensor.SensorManager.DATAID_HR_FINAL, this.lastHr);
					    	exercise.results.put(com.o2hlink.activa.data.sensor.SensorManager.DATAID_SO2_AVRG, this.avrgSo2);
					    	exercise.results.put(com.o2hlink.activa.data.sensor.SensorManager.DATAID_SO2_PEAK, this.peakSo2);
					    	exercise.results.put(com.o2hlink.activa.data.sensor.SensorManager.DATAID_SO2_LOW, this.lowSo2);
					    	exercise.results.put(com.o2hlink.activa.data.sensor.SensorManager.DATAID_SO2_INIT, this.initSo2);
					    	exercise.results.put(com.o2hlink.activa.data.sensor.SensorManager.DATAID_SO2_FINAL, this.lastSo2);
					    	exercise.results.put(com.o2hlink.activa.data.sensor.SensorManager.DATAID_STEPS, (float)this.steps);
					    	exercise.results.put(com.o2hlink.activa.data.sensor.SensorManager.DATAID_STOPS, (float)this.stops);
					    	exercise.results.put(com.o2hlink.activa.data.sensor.SensorManager.DATAID_TIME_EXE, (float)(time/1000));
					    	exercise.results.put(com.o2hlink.activa.data.sensor.SensorManager.DATAID_TIME_DATA, (float)(timeMeasured/1000));
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
		Activa.myBluetoothAdapter.disable();
		while (Activa.myBluetoothAdapter.getState() == BluetoothAdapter.STATE_TURNING_OFF) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		Activa.myBluetoothAdapter.enable();
		while (Activa.myBluetoothAdapter.getState() == BluetoothAdapter.STATE_TURNING_ON) {
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
				animationFrame = (ImageView) Activa.myApp.findViewById(R.id.animation);
				animationFrame.setVisibility(View.VISIBLE);
				animationFrame.setBackgroundResource(R.drawable.loading);
				animation = (AnimationDrawable) animationFrame.getBackground();	
				animation.start();				
				Activa.myUIManager.loadInfoPopupWithoutExiting(Activa.myLanguageManager.SENSORS_SEARCHING);
				break;
			case 1:
				animation.stop();	
				Activa.myUIManager.loadExerciseScreen(Activa.myLanguageManager.SENSORS_WAITING_DATA, time);
				ImageView animationFrame = (ImageView) Activa.myApp.findViewById(R.id.loadingView);
				animationFrame.setVisibility(View.VISIBLE);
				animationFrame.setBackgroundResource(R.drawable.loadingsmall);
				animation = (AnimationDrawable) animationFrame.getBackground();
				animation.start();
				((TextView) Activa.myApp.findViewById(R.id.startText)).setText(exercise.name);
				ImageButton cancel = (ImageButton) Activa.myApp.findViewById(R.id.stop);
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
						LocationManager myLocationManager = (LocationManager)Activa.myApp.getSystemService(Context.LOCATION_SERVICE);
						myLocationManager.removeUpdates(locListener);
						exercise.wakeLock.release();
						Activa.myAccelerometerManager.unregisterListener(sensorListener);
						exercise.finishMeasurements(false, null);
					}
				});
				final ImageButton soundon = (ImageButton) Activa.myApp.findViewById(R.id.soundon);
				final ImageButton soundoff = (ImageButton) Activa.myApp.findViewById(R.id.soundoff);
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
				ImageButton confirm = (ImageButton) Activa.myApp.findViewById(R.id.good);
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
						LocationManager myLocationManager = (LocationManager)Activa.myApp.getSystemService(Context.LOCATION_SERVICE);
						myLocationManager.removeUpdates(locListener);
						Activa.myAccelerometerManager.unregisterListener(sensorListener);
						exercise.hrtrack.put(((float) timePassed/1000), lastHr);
				    	exercise.so2track.put(((float) timePassed/1000), lastSo2);
				    	exercise.results.put(com.o2hlink.activa.data.sensor.SensorManager.DATAID_DISTANCE, distance);
				    	exercise.results.put(com.o2hlink.activa.data.sensor.SensorManager.DATAID_HR_AVRG, avrgHr);
						exercise.results.put(com.o2hlink.activa.data.sensor.SensorManager.DATAID_HR_PEAK, peakHr);
						exercise.results.put(com.o2hlink.activa.data.sensor.SensorManager.DATAID_HR_LOW, lowHr);
				    	exercise.results.put(com.o2hlink.activa.data.sensor.SensorManager.DATAID_HR_INIT, initHr);
				    	exercise.results.put(com.o2hlink.activa.data.sensor.SensorManager.DATAID_HR_FINAL, lastHr);
						exercise.results.put(com.o2hlink.activa.data.sensor.SensorManager.DATAID_SO2_AVRG, avrgSo2);
						exercise.results.put(com.o2hlink.activa.data.sensor.SensorManager.DATAID_SO2_PEAK, peakSo2);
						exercise.results.put(com.o2hlink.activa.data.sensor.SensorManager.DATAID_SO2_LOW, lowSo2);
				    	exercise.results.put(com.o2hlink.activa.data.sensor.SensorManager.DATAID_SO2_INIT, initSo2);
				    	exercise.results.put(com.o2hlink.activa.data.sensor.SensorManager.DATAID_SO2_FINAL, lastSo2);
						if (timePassed > time) timePassed = time;
						exercise.results.put(com.o2hlink.activa.data.sensor.SensorManager.DATAID_TIME_EXE, (float)(timePassed/1000));
				    	exercise.results.put(com.o2hlink.activa.data.sensor.SensorManager.DATAID_TIME_DATA, (float)(timeMeasured/1000));
				    	exercise.results.put(com.o2hlink.activa.data.sensor.SensorManager.DATAID_STEPS, (float)steps);
				    	exercise.results.put(com.o2hlink.activa.data.sensor.SensorManager.DATAID_STOPS, (float)stops);
				    	exercise.wakeLock.release();
						exercise.finishMeasurements(true, exercise.results);
					}
				});
				final SeekBar metronome = (SeekBar) Activa.myApp.findViewById(R.id.metronome);
				metronome.setVisibility(View.INVISIBLE);
				final ImageButton metronomeon = (ImageButton) Activa.myApp.findViewById(R.id.metronomeon);
				final ImageButton metronomeoff = (ImageButton) Activa.myApp.findViewById(R.id.metronomeoff);
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
					    	((TextView) Activa.myApp.findViewById(R.id.timerText)).setText(String.format("%01d:%02d:%02d", hours, minutes, seconds));			
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
				streamVolume = Activa.myAudioManager.getStreamVolume(AudioManager.STREAM_RING);
				streamVolume = soundOn*(streamVolume/Activa.myAudioManager.getStreamMaxVolume(AudioManager.STREAM_RING));
		        if (status2 < lastStatus) soundpool.play(soundpoolmap.get(FINISH_SOUND), streamVolume, streamVolume, 1, 0, 1f);
				lastStatus = status2;
				Activa.myUIManager.updateExerciseScreen((int)lastHr, (int)lastSo2, steps, stops, textGlobal2);
				break;
			case 2:
				animation.start();
				String textGlobal = getGlobalResult();
				int status = Integer.parseInt(textGlobal.substring(0,1));
				streamVolume = Activa.myAudioManager.getStreamVolume(AudioManager.STREAM_RING);
				streamVolume = soundOn*(streamVolume/Activa.myAudioManager.getStreamMaxVolume(AudioManager.STREAM_RING));
		        if (status < lastStatus) soundpool.play(soundpoolmap.get(FINISH_SOUND), streamVolume, streamVolume, 1, 0, 1f);
				lastStatus = status;
				Activa.myUIManager.updateExerciseScreen((int)lastHr, (int)lastSo2, steps, stops, textGlobal);
				break;
			case 3:
				animation.stop();
				Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.SENSORS_NOBLUETOOTH);
				break;
			case 4:
				Activa.myUIManager.loadInfoPopupWithoutExiting(Activa.myLanguageManager.SENSORS_BLUETOOTH_NOTCONNECTED);
				break;
			case 5:
				Activa.myUIManager.loadInfoPopupWithoutExiting(Activa.myLanguageManager.SENSORS_BLUETOOTH_CONNECTING);
				break;
			case 6:
				Activa.myUIManager.loadInfoPopupWithoutExiting(Activa.myLanguageManager.SENSORS_BLUETOOTH_CONFIG);
				break;
			case 7:
				if ((Activa.mySensorManager.eventAssociated != null)) 
					Activa.myUIManager.loadScheduleDay(Activa.mySensorManager.eventAssociated.getDate()); 
				else
					Activa.myUIManager.loadSensorList();
				if (exercise.bluetoothPreviouslyConnected) Activa.mySensorManager.reinitBluetooth();
				else Activa.myBluetoothAdapter.disable();
				if (sensorListener != null) Activa.myAccelerometerManager.unregisterListener(sensorListener);
				if (exercise.wakeLock != null) exercise.wakeLock.release();
				Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.SENSORS_BLUETOOTH_NOTCONNECTION);
				break;
			case 8:
				if ((Activa.mySensorManager.eventAssociated != null)) 
					Activa.myUIManager.loadScheduleDay(Activa.mySensorManager.eventAssociated.getDate()); 
				else
					Activa.myUIManager.loadSensorList();
				if (exercise.bluetoothPreviouslyConnected) Activa.mySensorManager.reinitBluetooth();
				else Activa.myBluetoothAdapter.disable();
				if (sensorListener != null) Activa.myAccelerometerManager.unregisterListener(sensorListener);
				if (exercise.wakeLock != null) exercise.wakeLock.release();
				Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.SENSORS_BLUETOOTH_NOTPAIRED);
				break;
			case 9:
				if ((Activa.mySensorManager.eventAssociated != null)) 
					Activa.myUIManager.loadScheduleDay(Activa.mySensorManager.eventAssociated.getDate()); 
				else
					Activa.myUIManager.loadSensorList();
				if (exercise.bluetoothPreviouslyConnected) Activa.mySensorManager.reinitBluetooth();
				else Activa.myBluetoothAdapter.disable();
				if (sensorListener != null) Activa.myAccelerometerManager.unregisterListener(sensorListener);
				if (exercise.wakeLock != null) exercise.wakeLock.release();
				Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.SENSORS_BLUETOOTH_NOTMEASURED);
				break;
			case 10:
				if ((Activa.mySensorManager.eventAssociated != null)) 
					Activa.myUIManager.loadScheduleDay(Activa.mySensorManager.eventAssociated.getDate()); 
				else
					Activa.myUIManager.loadSensorList();
				if (exercise.bluetoothPreviouslyConnected) Activa.mySensorManager.reinitBluetooth();
				else Activa.myBluetoothAdapter.disable();
				if (sensorListener != null) Activa.myAccelerometerManager.unregisterListener(sensorListener);
				if (exercise.wakeLock != null) exercise.wakeLock.release();
				Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.SENSORS_BLUETOOTH_NOTCONFIG);
				break;
			case 11:
				streamVolume = Activa.myAudioManager.getStreamVolume(AudioManager.STREAM_RING);
				streamVolume = soundOn*(streamVolume/Activa.myAudioManager.getStreamMaxVolume(AudioManager.STREAM_RING));
		        soundpool.play(soundpoolmap.get(FINISH_SOUND), streamVolume, streamVolume, 1, 0, 1f);
		        if (sensorListener != null) Activa.myAccelerometerManager.unregisterListener(sensorListener);
				if (exercise.wakeLock != null) exercise.wakeLock.release();
				exercise.finishMeasurements(true, exercise.results);
				break;
			case 12:
				if ((Activa.mySensorManager.eventAssociated != null)) 
					Activa.myUIManager.loadScheduleDay(Activa.mySensorManager.eventAssociated.getDate()); 
				else
					Activa.myUIManager.loadSensorList();
				if (exercise.bluetoothPreviouslyConnected) Activa.mySensorManager.reinitBluetooth();
				else Activa.myBluetoothAdapter.disable();
				if (sensorListener != null) Activa.myAccelerometerManager.unregisterListener(sensorListener);
				if (exercise.wakeLock != null) exercise.wakeLock.release();
				Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.SENSORS_BLUETOOTH_BADMEAS);
				break;
			case 21:
				String textGlobal3 = "1:" + Activa.myLanguageManager.SENSORS_EXERCISE_BADMESSAGE;
				Activa.myUIManager.updateExerciseScreen((int)lastHr, (int)lastSo2, steps, stops, textGlobal3);
				break;
			case 22:
				animation.stop();
				break;
			case 31:
				Activa.myUIManager.loadInfoPopupWithoutExiting(Activa.myLanguageManager.SENSORS_CONFIG);
				break;
			case 51:
				LocationManager myLocationManager = (LocationManager)Activa.myApp.getSystemService(Context.LOCATION_SERVICE); 
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
							Activa.myUIManager.updateExerciseScreen(speed, distance);
						}
						else {
							distance += location.distanceTo(lastLocation);
							lastLocation = location;
							speed = location.getSpeed();
							Activa.myUIManager.updateExerciseScreen(speed, distance);
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

    byte [] data = new byte [3];
	
	public ExerciseNotRunnable(Exercise exercise, long time) {
		this.exercise = exercise;
		this.time = time;
		this.bluetoothPreviouslyConnected = false;
		finished = false;
		found = false;
	}

	@Override
	public void run() {
		
	}
	
	private void PulseOximetry(boolean atInit) {
    	BluetoothSocket socket = null;
		handler.sendEmptyMessage(0);
		if (Activa.myBluetoothAdapter == null) {
			handler.sendEmptyMessage(3);
			return;
		}
		if (Activa.mySensorManager.reiniting) {
			handler.sendEmptyMessage(31);
			while (Activa.mySensorManager.reiniting) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			handler.sendEmptyMessage(0);
		}
		else if (!Activa.myBluetoothAdapter.isEnabled()) {
			Activa.myBluetoothAdapter.enable();
			bluetoothPreviouslyConnected = false;
			handler.sendEmptyMessage(4);
			while (!Activa.myBluetoothAdapter.isEnabled()) {
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
		Set<BluetoothDevice> pairedDevices = Activa.myBluetoothAdapter.getBondedDevices();
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
//						Activa.myBluetoothAdapter.cancelDiscovery();
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
					    while (((System.currentTimeMillis() - timeStart) < (15 * 1000)) && (!goodMeasure) && (!finished)) {
						    byte[] data = null;
							in.read(data);
//						    handler.sendEmptyMessage(100);
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
									    exercise.results.put(com.o2hlink.activa.data.sensor.SensorManager.DATAID_HR_INIT, hr);
									    exercise.results.put(com.o2hlink.activa.data.sensor.SensorManager.DATAID_SO2_INIT, so2);
								    }
								    else {
									    exercise.results.put(com.o2hlink.activa.data.sensor.SensorManager.DATAID_HR_FINAL, hr);
									    exercise.results.put(com.o2hlink.activa.data.sensor.SensorManager.DATAID_SO2_FINAL, so2);
								    }
									handler.sendEmptyMessage(2);
									continue;
							    }
					  	    }
					    }
				        if ((!finished)&&(!goodMeasure)) {
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
						e.printStackTrace();
					}
		        }
		    }
		}
        if (!found) handler.sendEmptyMessage(8);
	}
	
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			AnimationDrawable animation = null;
			ImageView animationFrame;
			switch (msg.what) {
			case 0:
				animationFrame = (ImageView) Activa.myApp.findViewById(R.id.animation);
				animationFrame.setVisibility(View.VISIBLE);
				animationFrame.setBackgroundResource(R.drawable.loading);
				animation = (AnimationDrawable) animationFrame.getBackground();	
				animation.start();			
				Activa.myUIManager.loadInfoPopupWithoutExiting(Activa.myLanguageManager.SENSORS_SEARCHING);
				break;
			case 1:
				animation.stop();
				Activa.myUIManager.state = UIManager.UI_STATE_SENSORING;
				Activa.myUIManager.loadScreen(R.layout.waiting);
				TextView start = (TextView) Activa.myApp.findViewById(R.id.startText);
				TextView text = (TextView) Activa.myApp.findViewById(R.id.infoText);
				start.setText(Activa.myLanguageManager.SENSORS_PULSEOXI_TITLE);
				text.setText(Activa.myLanguageManager.SENSORS_BLUETOOTH_CONFIG);
				animationFrame = (ImageView) Activa.myApp.findViewById(R.id.progress);
				animationFrame.setVisibility(View.VISIBLE);
				animationFrame.setBackgroundResource(R.drawable.loadingbig);
				animation = (AnimationDrawable) animationFrame.getBackground();	
				animation.start();		
				ImageButton cancel = (ImageButton) Activa.myApp.findViewById(R.id.stop);
				cancel.setOnClickListener(new OnClickListener() {	
					@Override
					public void onClick(View v) {
						finished = true;
						exercise.finishMeasurements(false, null);
					}
				});
				break;
			case 2:
				setExerciseScreen();
				break;
			case 3:
				animation.stop();
				Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.SENSORS_NOBLUETOOTH);
				break;
			case 4:
				Activa.myUIManager.loadInfoPopupWithoutExiting(Activa.myLanguageManager.SENSORS_BLUETOOTH_NOTCONNECTED);
				break;
			case 5:
				Activa.myUIManager.loadInfoPopupWithoutExiting(Activa.myLanguageManager.SENSORS_BLUETOOTH_CONNECTING);
				break;
			case 6:
				TextView text3 = (TextView) Activa.myApp.findViewById(R.id.infoText);
				text3.setText(Activa.myLanguageManager.SENSORS_READING);
				break;
			case 7:
				if (Activa.mySensorManager.eventAssociated != null) 
					Activa.myUIManager.loadScheduleDay(Activa.mySensorManager.eventAssociated.getDate()); 
				else
					Activa.myUIManager.loadSensorList();
				if (bluetoothPreviouslyConnected) Activa.mySensorManager.reinitBluetooth();
				else Activa.myBluetoothAdapter.disable();
				Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.SENSORS_BLUETOOTH_NOTCONNECTION);
				break;
			case 8:
				if ((Activa.mySensorManager.eventAssociated != null)) 
					Activa.myUIManager.loadScheduleDay(Activa.mySensorManager.eventAssociated.getDate()); 
				else
					Activa.myUIManager.loadSensorList();
				if (bluetoothPreviouslyConnected) Activa.mySensorManager.reinitBluetooth();
				else Activa.myBluetoothAdapter.disable();
				Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.SENSORS_BLUETOOTH_NOTPAIRED);
				break;
			case 9:
				if ((Activa.mySensorManager.eventAssociated != null)) 
					Activa.myUIManager.loadScheduleDay(Activa.mySensorManager.eventAssociated.getDate()); 
				else
					Activa.myUIManager.loadSensorList();
				if (bluetoothPreviouslyConnected) Activa.mySensorManager.reinitBluetooth();
				else Activa.myBluetoothAdapter.disable();
				Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.SENSORS_BLUETOOTH_NOTMEASURED);
				break;
			case 10:
				if ((Activa.mySensorManager.eventAssociated != null)) 
					Activa.myUIManager.loadScheduleDay(Activa.mySensorManager.eventAssociated.getDate()); 
				else
					Activa.myUIManager.loadSensorList();
				if (bluetoothPreviouslyConnected) Activa.mySensorManager.reinitBluetooth();
				else Activa.myBluetoothAdapter.disable();
				Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.SENSORS_BLUETOOTH_NOTCONFIG);
				break;
			case 31:
				Activa.myUIManager.loadInfoPopupWithoutExiting(Activa.myLanguageManager.SENSORS_CONFIG);
				break;
			case 100:
				Toast toast = Toast.makeText(Activa.myApp, "DATA0: " + data[0] + ", DATA1: " + data[1] + ", DATA2: " + data[2], Toast.LENGTH_SHORT);
				toast.setDuration(1000);
				toast.show();
			}
		}
	};
	
	private void setExerciseScreen() {
		Activa.myUIManager.loadExerciseScreen(Activa.myLanguageManager.SENSORS_WAITING_DATA, time);
		((TextView) Activa.myApp.findViewById(R.id.startText)).setText(exercise.name);
		ImageButton cancel = (ImageButton) Activa.myApp.findViewById(R.id.stop);
		cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				temporizer.cancel();
				finished = false;
				exercise.finishMeasurements(false, null);
			}
		});
		final ImageButton soundon = (ImageButton) Activa.myApp.findViewById(R.id.soundon);
		final ImageButton soundoff = (ImageButton) Activa.myApp.findViewById(R.id.soundoff);
		soundon.setVisibility(View.GONE);
		soundoff.setVisibility(View.GONE);
		ImageButton confirm = (ImageButton) Activa.myApp.findViewById(R.id.good);
		confirm.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				temporizer.cancel();
				finished = true;
				exercise.results.put(com.o2hlink.activa.data.sensor.SensorManager.DATAID_DISTANCE, -1f);
		    	exercise.results.put(com.o2hlink.activa.data.sensor.SensorManager.DATAID_HR_AVRG, -1f);
				exercise.results.put(com.o2hlink.activa.data.sensor.SensorManager.DATAID_HR_PEAK, -1f);
				exercise.results.put(com.o2hlink.activa.data.sensor.SensorManager.DATAID_HR_LOW, -1f);
				exercise.results.put(com.o2hlink.activa.data.sensor.SensorManager.DATAID_SO2_AVRG, -1f);
				exercise.results.put(com.o2hlink.activa.data.sensor.SensorManager.DATAID_SO2_PEAK, -1f);
				exercise.results.put(com.o2hlink.activa.data.sensor.SensorManager.DATAID_SO2_LOW, -1f);
				if (timePassed > time) timePassed = time;
				exercise.results.put(com.o2hlink.activa.data.sensor.SensorManager.DATAID_TIME_EXE, (float)(timePassed/1000));
		    	exercise.results.put(com.o2hlink.activa.data.sensor.SensorManager.DATAID_TIME_DATA, -1f);
		    	exercise.results.put(com.o2hlink.activa.data.sensor.SensorManager.DATAID_STEPS, -1f);
		    	exercise.results.put(com.o2hlink.activa.data.sensor.SensorManager.DATAID_STOPS, -1f);

				Activa.myUIManager.loadScreen(R.layout.info);
				TextView text2 = (TextView) Activa.myApp.findViewById(R.id.textInfo);
				text2.setText(Activa.myLanguageManager.NOTIFICATION_TIMETO);
				LinearLayout board = (LinearLayout) Activa.myApp.findViewById(R.id.mainLayout);
				board.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						PulseOximetry(false);
					}
				});
			}
		});
		final SeekBar metronome = (SeekBar) Activa.myApp.findViewById(R.id.metronome);
		metronome.setVisibility(View.GONE);
		final ImageButton metronomeon = (ImageButton) Activa.myApp.findViewById(R.id.metronomeon);
		final ImageButton metronomeoff = (ImageButton) Activa.myApp.findViewById(R.id.metronomeoff);
		metronomeon.setVisibility(View.GONE);
		metronomeoff.setVisibility(View.GONE);
		temporizer = new CountDownTimer(time, 1000) {
			@Override
			public void onTick(long millisUntilFinished) {
			    int seconds = (int) ((time - millisUntilFinished) / 1000);
			    int minutes = seconds / 60;
			    seconds     = seconds % 60;
			    int hours = minutes / 60;
			    minutes = minutes % 60;
			    try {
			    	((TextView) Activa.myApp.findViewById(R.id.timerText)).setText(String.format("%01d:%02d:%02d", hours, minutes, seconds));			
			    } catch (Exception e) {
			    	this.cancel();
				}
			}
			@Override
			public void onFinish() {
				finished = true;
				exercise.results.put(com.o2hlink.activa.data.sensor.SensorManager.DATAID_DISTANCE, -1f);
		    	exercise.results.put(com.o2hlink.activa.data.sensor.SensorManager.DATAID_HR_AVRG, -1f);
				exercise.results.put(com.o2hlink.activa.data.sensor.SensorManager.DATAID_HR_PEAK, -1f);
				exercise.results.put(com.o2hlink.activa.data.sensor.SensorManager.DATAID_HR_LOW, -1f);
				exercise.results.put(com.o2hlink.activa.data.sensor.SensorManager.DATAID_SO2_AVRG, -1f);
				exercise.results.put(com.o2hlink.activa.data.sensor.SensorManager.DATAID_SO2_PEAK, -1f);
				exercise.results.put(com.o2hlink.activa.data.sensor.SensorManager.DATAID_SO2_LOW, -1f);
				if (timePassed > time) timePassed = time;
				exercise.results.put(com.o2hlink.activa.data.sensor.SensorManager.DATAID_TIME_EXE, (float)(timePassed/1000));
		    	exercise.results.put(com.o2hlink.activa.data.sensor.SensorManager.DATAID_TIME_DATA, -1f);
		    	exercise.results.put(com.o2hlink.activa.data.sensor.SensorManager.DATAID_STEPS, -1f);
		    	exercise.results.put(com.o2hlink.activa.data.sensor.SensorManager.DATAID_STOPS, -1f);
			}
		};
		temporizer.start();
	}
	
}
