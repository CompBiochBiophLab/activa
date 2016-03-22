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
import android.location.Criteria;
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
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.SeekBar.OnSeekBarChangeListener;

import com.o2hlink.activa.Activa;
import com.o2hlink.activa.ActivaUtil;
import com.o2hlink.activa.R;
import com.o2hlink.activa.background.MainThread;
import com.o2hlink.activa.background.SendSensorResult;
import com.o2hlink.activa.data.PendingDataManager;
import com.o2hlink.activa.ui.MyProgressBar;
import com.o2hlink.activa.ui.MyProgressBarBig;
import com.o2hlink.activa.ui.UIManager;

public class PedometerCalibration2 {
	
	Thread thread;
	
	int mLimit;
	
	StartCalibration st;

	public PedometerCalibration2() {
		this.mLimit = 25;
	}
	
	public void finishCalibration(boolean outcome) {
		st.closeListeners();
		this.thread.interrupt();
		Activa.myUIManager.loadScreen(R.layout.info);
		if (outcome) ((TextView)Activa.myApp.findViewById(R.id.textInfo)).setText("Calibracion hecha");
		else ((TextView)Activa.myApp.findViewById(R.id.textInfo)).setText("Calibracion cancelada");
		CountDownTimer timer = new CountDownTimer(3000,1000) {
			@Override
			public void onTick(long millisUntilFinished) {}
			@Override
			public void onFinish() {
				Activa.myUIManager.loadSensorList();
			}
		};
		timer.start();
	}

	public void startCalibration() {
		st = new StartCalibration(this, 25);
		Thread thr = new Thread(st);
		this.thread = thr;
		thr.start();
	}
	
	public void reinitCalibration(int calibration, int seekbar) {
		st.closeListeners();
		this.thread.interrupt();
		st = new StartCalibration(this, calibration, seekbar);
		Thread thr = new Thread(st);
		this.thread = thr;
		thr.start();
	}

}

class StartCalibration implements Runnable {

	PedometerCalibration2 calibration;

	private StepCounter stepcounter;
	
	String name;
	
	long time = 60000;
	
	BroadcastReceiver myReceiver;
	
	BluetoothServerSocket serversocket;
	
	SensorEventListener sensorListener;
	
	LocationListener GPSListener;

	boolean finished;
	
	int seekbarInt;
	
	private int steps = 0;
	private int stops = 0;
	private float speed = 0.0f;
	private float distance = 0.0f;
	private float distancecalc = 0.0f;
	private Date lastUpdate = new Date();
	
	private boolean stopped = true;
	private Location lastLocation;
	
    private Date lasStepTime = new Date();
    private int stepsFollowed;
    private int stepsToRun = 5;
	
    private int mTimeLimit = 2000;
    private int mLimit;
    private float mLastValue = 0.0f;
    private float mScale[] = new float[2];
    private float mYOffset;

    private float mLastDirection = -1.0f;
    private float mLastExtremes[] = new float[2];
    private float mLastDiff = 0.0f;
    private int mLastMatch = -1;
	
	public StartCalibration(PedometerCalibration2 calibration) {
		this.calibration = calibration;
        int h = 480; // TODO: remove this constant
        mYOffset = h * 0.5f;
        mScale[0] = - (h * 0.5f * (1.0f / (SensorManager.STANDARD_GRAVITY * 2)));
        mScale[1] = - (h * 0.5f * (1.0f / (SensorManager.MAGNETIC_FIELD_EARTH_MAX)));
        this.mLimit = 50;
	}
	
	public StartCalibration(PedometerCalibration2 calibration, int mLimit) {
		this.calibration = calibration;
        int h = 480; // TODO: remove this constant
        mYOffset = h * 0.5f;
        mScale[0] = - (h * 0.5f * (1.0f / (SensorManager.STANDARD_GRAVITY * 2)));
        mScale[1] = - (h * 0.5f * (1.0f / (SensorManager.MAGNETIC_FIELD_EARTH_MAX)));	
        this.mLimit = mLimit;
	}
	
	public StartCalibration(PedometerCalibration2 calibration, int mLimit, int seekbar) {
		this.calibration = calibration;
        int h = 480; // TODO: remove this constant
        mYOffset = h * 0.5f;
        mScale[0] = - (h * 0.5f * (1.0f / (SensorManager.STANDARD_GRAVITY * 2)));
        mScale[1] = - (h * 0.5f * (1.0f / (SensorManager.MAGNETIC_FIELD_EARTH_MAX)));	
        this.mLimit = mLimit;
        this.seekbarInt = seekbar;
	}
	
	@Override
	public void run() {		
		handler.sendEmptyMessage(0);
		Activa.myMapManager.getMyPosition();
		lastLocation = Activa.myMapManager.location;
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
			public void onAccuracyChanged(Sensor sensor, int accuracy) {
				// TODO Auto-generated method stub
			}
		};
		Activa.myAccelerometerManager.registerListener(sensorListener, Activa.myAccelerometerManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_FASTEST);
	}
	
	public void closeListeners() {
		Activa.myAccelerometerManager.unregisterListener(sensorListener);
		if (GPSListener != null) {
			Activa.myMapManager.myLocationManager.removeUpdates(GPSListener);
			GPSListener = null;
		}
	}

	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			String textResult;
			switch (msg.what) {
			case 0:
				Activa.myUIManager.loadScreen(R.layout.calibration);
				((TextView)Activa.myApp.findViewById(R.id.startText)).setText("Calibracion");
				ImageButton cancel = (ImageButton) Activa.myApp.findViewById(R.id.stop);
				textResult = "Realize pruebas con la deteccion de pasos:\n\n"
					+ "Pasos: " + steps + "\nParadas: " + stops + "\nVelocidad: " + speed
					+ "\nDistancia: " + distance + "\nDistancia calculada: " + distancecalc;
				((TextView)Activa.myApp.findViewById(R.id.textResult)).setText(textResult);
				cancel.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
//						temporizer.cancel();
						finished = false;
						calibration.finishCalibration(false);
					}
				});
				ImageButton ok = (ImageButton) Activa.myApp.findViewById(R.id.good);
				ok.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
//						temporizer.cancel();
						finished = false;
						calibration.finishCalibration(true);
					}
				});
				final SeekBar calibrator = (SeekBar) Activa.myApp.findViewById(R.id.calibrator);
				calibrator.setProgress(seekbarInt);
				calibrator.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
					@Override
					public void onStopTrackingTouch(SeekBar seekBar) {
						steps = 0;
						stops = 0;
						String textResult = "Realize pruebas con la deteccion de pasos:\n\n"
							+ "Pasos: " + steps + "\nParadas: " + stops + "\nVelocidad: " + speed
							+ "\nDistancia: " + distance + "\nDistancia calculada: " + distancecalc;
						((TextView)Activa.myApp.findViewById(R.id.textResult)).setText(textResult);
						calibration.reinitCalibration(mLimit, seekBar.getProgress());
					}
					@Override
					public void onStartTrackingTouch(SeekBar seekBar) {
					}
					@Override
					public void onProgressChanged(SeekBar seekBar, int progress,
							boolean fromUser) {
						mLimit = 25 + progress/2;
					}
				});
				Activa.myMapManager.myLocationManager.requestLocationUpdates(Activa.myMapManager.myLocationManager.GPS_PROVIDER, 500, 3, GPSListener = new LocationListener() {
					@Override
					public void onStatusChanged(String provider, int status, Bundle extras) {}
					@Override
					public void onProviderEnabled(String provider) {}
					@Override
					public void onProviderDisabled(String provider) {}
					@Override
					public void onLocationChanged(Location location) {
						speed = location.getSpeed();
						distance += location.distanceTo(lastLocation);
						lastLocation = new Location(location);
						float lastSpeed = speed;
						Date now = new Date();
						long time = (now.getTime() - lastUpdate.getTime())/1000;
						distancecalc += lastSpeed*time;
					}
				});
				break;
			case 1:
				textResult = "Realize pruebas con la deteccion de pasos:\n\n"
					+ "Pasos: " + steps + "\nParadas: " + stops + "\nVelocidad: " + speed
					+ "\nDistancia: " + distance + "\nDistancia calculada: " + distancecalc;
				((TextView)Activa.myApp.findViewById(R.id.textResult)).setText(textResult);
                break;
			}
		}
	};
	
}