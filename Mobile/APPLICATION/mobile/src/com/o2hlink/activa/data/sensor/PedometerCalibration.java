package com.o2hlink.activa.data.sensor;

import java.util.LinkedList;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

import com.o2hlink.activa.Activa;
import com.o2hlink.activa.R;

public class PedometerCalibration {
	
	public Exercise exercise;
	
	public Thread thread;

	public PedometerCalibration() {
	}
	
	public void finishCalibration(boolean outcome, float mLimit) {
		this.thread.interrupt();
		Activa.myMobileManager.pedometerCalValue = Math.round(mLimit);
		Activa.myMobileManager.user.pedometerCalValue = Math.round(mLimit);
		Activa.myMobileManager.saveUsers();
		Activa.myUIManager.loadScreen(R.layout.info);
		TextView text = (TextView)Activa.myApp.findViewById(R.id.textInfo);
		text.setText(Activa.myLanguageManager.SENSORS_CALIBRATION_DONE);
		if (mLimit <= 30) text.append(Activa.myLanguageManager.SENSORS_CALIBRATION_SELECT_ULTRAHIGH);
		else if (mLimit <= 35) text.append(Activa.myLanguageManager.SENSORS_CALIBRATION_SELECT_VERYHIGH);
		else if (mLimit <= 40) text.append(Activa.myLanguageManager.SENSORS_CALIBRATION_SELECT_HIGH);
		else if (mLimit <= 45) text.append(Activa.myLanguageManager.SENSORS_CALIBRATION_SELECT_NORMAL);
		else if (mLimit <= 50) text.append(Activa.myLanguageManager.SENSORS_CALIBRATION_SELECT_LOW);
		else if (mLimit <= 55) text.append(Activa.myLanguageManager.SENSORS_CALIBRATION_SELECT_VERYLOW);
		else text.append(Activa.myLanguageManager.SENSORS_CALIBRATION_SELECT_ULTRALOW);
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
//		StartMeasurementStepCounter st = new StartMeasurementStepCounter(this);
		AutoCalibration st = new AutoCalibration(this);
		Thread thr = new Thread(st);
		this.thread = thr;
		thr.start();
	}
	
}

class AutoCalibration implements Runnable {

	private int index;
	private float mYOffset;
    private float mScale[] = new float[2];
    private LinkedList<Float> mData;
    private float mLimits[] = new float[10];
    private float mLimit = 0;
    private PedometerCalibration calibrator;
    SensorEventListener sensorListener;
    
	public AutoCalibration(PedometerCalibration calibrator) {
		int h = 480; // TODO: remove this constant
		mData = new LinkedList<Float>();
        mYOffset = h * 0.5f;
        mScale[0] = - (h * 0.5f * (1.0f / (SensorManager.STANDARD_GRAVITY * 2)));
        mScale[1] = - (h * 0.5f * (1.0f / (SensorManager.MAGNETIC_FIELD_EARTH_MAX)));
        index = 0;
        this.calibrator = calibrator;
	}
	
	@Override
	public void run() {
		handler.sendEmptyMessage(0);
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		handler.sendEmptyMessage(2);
		sensorListener = new SensorEventListener() {
			@Override
			public void onSensorChanged(SensorEvent event) {
				if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                    float vSum = 0;
                    for (int i=0 ; i<3 ; i++) {
                        float v = mYOffset + event.values[i] * mScale[0];
                        vSum += v;
                    }
                    float v = vSum / 3;
                    mData.add(v);
                    index++;
    				if (index > 1000) mData.removeFirst();
				}
			}
			@Override
			public void onAccuracyChanged(Sensor sensor, int accuracy) {
			}
		};
		Activa.myAccelerometerManager.registerListener(sensorListener, Activa.myAccelerometerManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_FASTEST);
//		Activa.myAccelerometerManager.unregisterListener(sensorListener);
	}
	
	public void processData() {
		int j = 0;
		float diffsum = 0;
		float mLastValue = 0;
        int mLastDirection = 0;
        int extType = 0;
        float minDiff = 500.0f;
        float[] mLastExtremes = new float[2];
        mLastDirection = (mData.get(1) > mData.get(0) ? 1 : -1);
        mLastValue = mData.get(1);
		for (int i = 2; i < mData.size(); i++) {
			int direction = (mData.get(i) > mLastValue ? 1 : -1);
			if (direction == - mLastDirection) {
                // Direction changed
                extType = (direction > 0 ? 0 : 1); 
				mLastExtremes[extType] = mLastValue;
                float diff = Math.abs(mLastExtremes[extType] - mLastExtremes[1 - extType]);
                if (diff > 30){
                	if (j != 0) {
                		mLimits[j - 1] = diff;
                		if (diff < minDiff) minDiff = diff;
                    	diffsum += diff;
                	}
                	j++;
                	if (j > 10) break;
                }
                mLastDirection = direction;
            }
			mLastValue = mData.get(i);
		}
		float mLimitTemp = diffsum/j;
		mLimit = mLimitTemp;
//		mLimit = mLimit*3/4;
//		mLimit = ((mLimit < minDiff) ? mLimit : minDiff);
		handler.sendEmptyMessage(1);
	}
	
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				Activa.myUIManager.loadScreen(R.layout.follow);
				TextView text = (TextView) Activa.myApp.findViewById(R.id.textInfo);
				((ImageButton) Activa.myApp.findViewById(R.id.follow)).setVisibility(View.GONE);
				text.setTextSize(14);
				text.setText(Activa.myLanguageManager.SENSORS_CALIBRATION_INSTRUCTIONS);
				break;
			case 1: 
				calibrator.finishCalibration(true, mLimit);
				break;
			case 2:
				((Vibrator)Activa.myApp.getSystemService(Context.VIBRATOR_SERVICE)).vibrate(1000);
				ImageButton next = (ImageButton) Activa.myApp.findViewById(R.id.follow);
				next.setVisibility(View.VISIBLE);
				next.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						Activa.myAccelerometerManager.unregisterListener(sensorListener);
						processData();
					}
				});
				break;
			}
		}
	};
	
}