package com.o2hlink.activa.data.sensor;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Set;
import java.util.UUID;

import android.accounts.OperationCanceledException;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager.BadTokenException;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.o2hlink.activa.Activa;
import com.o2hlink.activa.ActivaUtil;
import com.o2hlink.activa.R;
import com.o2hlink.activa.background.MainThread;
import com.o2hlink.activa.background.SendSensorResult;
import com.o2hlink.activa.data.PendingDataManager;
import com.o2hlink.activa.ui.MyProgressBar;
import com.o2hlink.activa.ui.MyProgressBarBig;
import com.o2hlink.activa.ui.UIManager;

public class StepCounter extends com.o2hlink.activa.data.sensor.Sensor {
	
	public Exercise exercise;

	public StepCounter() {
		this.name = "Contador de pasos";
		this.icon = R.drawable.exercise;
		this.id = com.o2hlink.activa.data.sensor.SensorManager.ID_STEPCOUNTER;
	}
	
	@Override
	public void finishMeasurements(boolean outcome, Hashtable<Integer, Float> results) {
		this.thread.interrupt();
		this.results = results;
		if (Activa.mySensorManager.eventAssociated != null) Activa.mySensorManager.eventAssociated.state = 0;
		Activa.myUIManager.finishSensorMeasurement(this.name, outcome, this);
	}
	
//	@Override
//	public void finishMeasurements(boolean outcome, Hashtable<Integer, Float> results) {
//		Activa.myApp.finishActivity(SensorManager.ID_PULSIOXYMETER);
//		this.results = results;
//		if (Activa.mySensorManager.eventAssociated != null) Activa.mySensorManager.eventAssociated.state = 0;
//		Activa.myUIManager.finishSensorMeasurement(this.name, outcome, this);
//	}

//	public void finishExercise(boolean outcome, Hashtable<Integer, Float> results) {
//		this.thread.interrupt();
//		this.results = results;
//		if (Activa.myProgramManager.eventAssociated != null) Activa.myProgramManager.eventAssociated.state = 0;
//		Activa.myUIManager.finishExercise(outcome, this);		
//	}

	@Override
	public void startMeasurement() {
		this.results = new Hashtable<Integer, Float>();
//		StartMeasurementStepCounter st = new StartMeasurementStepCounter(this);
		StartMeasurementStepCounter st = new StartMeasurementStepCounter(this);
		Thread thr = new Thread(st);
		this.thread = thr;
		thr.start();
	}

//	@Override
//	public void startMeasurement() {
//		this.results = new Hashtable<Integer, Float>();
//		Activa.myBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
//		Activa.myApp.startActivityForResult(new Intent(Activa.myApp, com.o2hlink.activa.data.sensor.PulseOximeterActivity.class), SensorManager.ID_PULSIOXYMETER);
//	}
	
//	public void startExercise(long time, Hashtable<Long,String> timedMessages) {
//		this.results = new Hashtable<Integer, Float>();
//		Activa.myBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
//		this.exercise = new Exercise(this, time, timedMessages);
//		this.thread = new Thread(this.exercise);
//		this.thread.start();
//	}

	@Override
	public String passSensorResultToXML() {
		String returned = "<EVENT ID=\"1\" DATETIME=\"" + Activa.myMobileManager.device.getDateTime();
		returned += "\" IDGROUP=\"\" IDAGENDA=\"";
		if (Activa.mySensorManager.eventAssociated != null) returned += Activa.mySensorManager.eventAssociated.id + "\">";
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
	public String getSensorGlobalResult() {
		String result;
		Float systolic = this.results.get(com.o2hlink.activa.data.sensor.SensorManager.DATAID_SYSPRESS);
		Float diastolic = this.results.get(com.o2hlink.activa.data.sensor.SensorManager.DATAID_DIAPRESS);
		
		if ((systolic < 50.0)&&(diastolic < 80.0)) {
			result = "0:" + Activa.myLanguageManager.SENSORS_BLOODPRESS_MESSAGE7;
		}
		else if ((systolic < 60.0)&&(diastolic < 90.0)) {
			result = "2:" + Activa.myLanguageManager.SENSORS_BLOODPRESS_MESSAGE0;
		}
		else if ((systolic < 80.0)&&(diastolic < 120.0)) {
			result = "2:" + Activa.myLanguageManager.SENSORS_BLOODPRESS_MESSAGE1;
		}
		else if ((systolic < 85.0)&&(diastolic < 130.0)) {
			result = "2:" + Activa.myLanguageManager.SENSORS_BLOODPRESS_MESSAGE2;
		}
		else if ((systolic < 90.0)&&(diastolic < 140.0)) {
			result = "1:" + Activa.myLanguageManager.SENSORS_BLOODPRESS_MESSAGE3;
		}
		else if ((systolic < 100.0)&&(diastolic < 160.0)) {
			result = "1:" + Activa.myLanguageManager.SENSORS_BLOODPRESS_MESSAGE4;
		}
		else if ((systolic < 110.0)&&(diastolic < 180.0)) {
			result = "0:" + Activa.myLanguageManager.SENSORS_BLOODPRESS_MESSAGE5;
		}
		else {
			result = "0:" + Activa.myLanguageManager.SENSORS_BLOODPRESS_MESSAGE6;
		}
		return result;
	}

}

class StartMeasurementStepCounter implements Runnable {

	private StepCounter stepcounter;
	
	String name;
	
	long time = 60000;
	
	BroadcastReceiver myReceiver;
	
	BluetoothServerSocket serversocket;

	boolean finished;
	
	SensorEventListener sensorListener;
	
	private int steps = 0;
	private int stops = 0;
	private int stepsmine = 0;
	private float g = 0;
	private float maxg = 15;
	private float prevg = 0;
	private boolean atstep = false;
	private boolean upping = true;
	private boolean stopped = true;
	private Location prevLocation;
	private Location currLocation;
	private float distance = 0;
	
    private Date lasStepTime = new Date();
    private int stepsFollowed;
    private int stepsToRun = 5;
	
    private int mTimeLimit = 2000;
    private int mLimit;
    private int mLimitFixed = 45;
    private float mLastValue = 0.0f;
    private float mScale[] = new float[2];
    private float mYOffset;

    private float mLastDirection = -1.0f;
    private float mLastExtremes[] = new float[2];
    private float mLastDiff = 0.0f;
    private int mLastMatch = -1;
	
	public StartMeasurementStepCounter(StepCounter stepcounter) {
		this.stepcounter = stepcounter;
        int h = 480; // TODO: remove this constant
        mYOffset = h * 0.5f;
        mScale[0] = - (h * 0.5f * (1.0f / (SensorManager.STANDARD_GRAVITY * 2)));
        mScale[1] = - (h * 0.5f * (1.0f / (SensorManager.MAGNETIC_FIELD_EARTH_MAX)));
        this.mLimit = Activa.myMobileManager.pedometerCalValue;
	}
	
	@SuppressWarnings("unused")
	private void closeConnection() {	
	}
	
	@Override
	public void run() {		
		handler.sendEmptyMessage(0);
		prevLocation = Activa.myMapManager.location;
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
                            	handler.sendEmptyMessage(1);
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
                    	handler.sendEmptyMessage(1);
                    }
                }
			}
			@Override
			public void onAccuracyChanged(Sensor sensor, int accuracy) {}
		};
		Activa.myAccelerometerManager.registerListener(sensorListener, Activa.myAccelerometerManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_FASTEST);
	}

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				Activa.myUIManager.loadExerciseScreen("", Activa.myLanguageManager.SENSORS_WAITING_DATA, time);
				CountDownTimer temporizer = new CountDownTimer(time, 1000) {
					@Override
					public void onTick(long millisUntilFinished) {
					    int seconds = (int) ((time - millisUntilFinished) / 1000);
					    int minutes = seconds / 60;
					    seconds     = seconds % 60;
					    try {
					    	((TextView) Activa.myApp.findViewById(R.id.timerText)).setText(String.format("%02d:%02d", minutes, seconds));			
					    } catch (Exception e) {
					    	this.cancel();
						}
					}
					@Override
					public void onFinish() {
					}
				};
				temporizer.start();
				ImageButton cancel = (ImageButton) Activa.myApp.findViewById(R.id.stop);
				cancel.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
//						temporizer.cancel();
						finished = false;
						Activa.myAccelerometerManager.unregisterListener(sensorListener);
						stepcounter.finishMeasurements(false, null);
					}
				});
				break;
			case 1:
				String textResult = "Pasos: " + steps + "\nParadas: "
					+ stops + "\n\nDistancia en metros: " + (int)distance + 
					"\nVelocidad: " + Activa.myMapManager.location.getSpeed();
				Activa.myUIManager.updateExerciseScreen(textResult, "2:Contando pasos ...");
                break;
			}
		}
	};
	
}