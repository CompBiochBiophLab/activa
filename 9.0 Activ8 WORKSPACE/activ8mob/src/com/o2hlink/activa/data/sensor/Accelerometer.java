package com.o2hlink.activa.data.sensor;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Set;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorListener;
import android.hardware.SensorManager;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

import com.o2hlink.activa.Activa;
import com.o2hlink.activa.R;
import com.o2hlink.activa.data.program.ProgramManager;
import com.o2hlink.activa.ui.MyProgressBar;
import com.o2hlink.activa.ui.MyProgressBarBig;

public class Accelerometer implements Runnable {
	
	private long time;
	
	boolean finished;                
	
	float x;
    
	float y;
    
	float z;             
	
	float lastx;
    
	float lasty;
    
	float lastz; 
	
	float speed;
	
	float speedx;
	
	float speedy;
	
	float speedz;
	
	double g;
	
	float acceleration;
	
	float gravity;
	
	float gravityx;
	
	float gravityy;
	
	float gravityz;
	
	float distance;
	
	float distancex;
	
	float distancey;
	
	float distancez;
	
	CountDownTimer temporizer;
	
	long firstTime = 0;
	
	long lastTime = 0;
	
	long currentTime = 0;
	
	int count = 0;
	
	int steps = 0;
	
	Hashtable<Long,String> timedMessages;

	public Accelerometer(long time) {
		this.finished = true;
		this.time = time;
		double latitude = Activa.myMapManager.location.getLatitude();
		this.gravity = (float) (9.78f * (1 + 0.0053024*Math.pow(Math.sin(latitude),2) - 0.0000058*Math.pow(Math.sin(2*latitude),2)));
	}
	
//	@SuppressWarnings("deprecation")
//	@Override
//	public void run() {			
//		handler.sendEmptyMessage(1);
//		distance = 0.0f;
//		this.lastTime = System.currentTimeMillis();
//		SensorListener sensorListener = new SensorListener() {		
//			@Override
//			public void onSensorChanged(int sensor, float[] values) {
//				if (sensor == SensorManager.SENSOR_ACCELEROMETER) {
//					currentTime = System.currentTimeMillis();
//					if ((currentTime - lastTime) > 100) {
//						long difference = (long) (currentTime - lastTime);
//						lastTime = currentTime;		
//						x = values[SensorManager.DATA_X];
//						y = values[SensorManager.DATA_Y];
//						z = values[SensorManager.DATA_Z];
//						speed = (float) (Math.sqrt(Math.pow(x-lastx, 2) + Math.pow(y-lasty, 2) + Math.pow(z-lastz, 2)) * difference / 1000);
//						if (speed > 0.1) 
//							if ((lastx != 0)&&(lasty != 0)&&(lastz != 0)) distance += speed * difference;
//				        lastx = x;
//				        lasty = y;
//				        lastz = z;
////		                g = Math.sqrt((double)(z*z + x*x + y*y));
////		                g = g - gravity;
////		                distance += (g)*Math.pow((difference/1000), 2);
//		                handler.sendEmptyMessage(2);
//					}
//				}
//				// TODO Auto-generated method stub			
//			}			
//			@Override
//			public void onAccuracyChanged(int sensor, int accuracy) {
//				// TODO Auto-generated method stub				
//			}
//		};
//		Activa.myAccelerometerManager.registerListener(sensorListener, SensorManager.SENSOR_ACCELEROMETER, SensorManager.SENSOR_DELAY_UI);
//	}

//	@SuppressWarnings("deprecation")
//	@Override
//	public void run() {			
//		handler.sendEmptyMessage(1);
//		distance = 0.0f;
//		distancex = 0.0f;
//		distancey = 0.0f;
//		distancez = 0.0f;
//		gravityx = 0.0f;
//		gravityy = 0.0f;
//		gravityz = 0.0f;
//		count = 0;
//		this.lastTime = System.currentTimeMillis();
//		
//		firstTime = System.currentTimeMillis();
//		
//		SensorListener sensorListener = new SensorListener() {		
//			@Override
//			public void onSensorChanged(int sensor, float[] values) {
//				if (sensor == SensorManager.SENSOR_ACCELEROMETER) {
//					currentTime = System.currentTimeMillis();
//					if ((currentTime - firstTime) < 5000) {
//						x = values[SensorManager.DATA_X];
//						gravityx = (((gravityx * count) + x)/(count + 1));
//						y = values[SensorManager.DATA_Y];
//						gravityy = (((gravityy * count) + y)/(count + 1));
//						z = values[SensorManager.DATA_Z];
//						gravityz = (((gravityz * count) + z)/(count + 1));
//						gravity = (float) Math.sqrt(Math.pow(gravityx, 2) + Math.pow(gravityy, 2) + Math.pow(gravityz, 2));
//						count++;
//						lastx = x;
//						lasty = y;
//						lastz = z;
//						return;
//					}
//					if ((currentTime - lastTime) > 25) {
//						long difference = (long) (currentTime - lastTime);
//						lastTime = currentTime;		
//						x = values[SensorManager.DATA_X];
//						y = values[SensorManager.DATA_Y];
//						z = values[SensorManager.DATA_Z];
//						acceleration = (float) Math.abs((Math.sqrt(Math.pow(x - lastx, 2)) + Math.sqrt(Math.pow(y - lasty, 2)) + Math.sqrt(Math.pow(z - lastz, 2))));
//						speed = (float) (acceleration * difference / 1000);
//						if (speed > 0.1) 
//							distance += speed * difference / 1000;
//						lastx = x;
//						lasty = y;
//						lastz = z;
//				        handler.sendEmptyMessage(2);
//					}
//				}
//				// TODO Auto-generated method stub			
//			}			
//			@Override
//			public void onAccuracyChanged(int sensor, int accuracy) {
//				// TODO Auto-generated method stub				
//			}
//		};
//		Activa.myAccelerometerManager.registerListener(sensorListener, SensorManager.SENSOR_ACCELEROMETER, SensorManager.SENSOR_DELAY_UI);	
//	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void run() {			
		handler.sendEmptyMessage(1);
		distance = 0.0f;
		distancex = 0.0f;
		distancey = 0.0f;
		distancez = 0.0f;
		gravityx = 0.0f;
		gravityy = 0.0f;
		gravityz = 0.0f;
		count = 0;
		steps = 0;
		this.lastTime = System.currentTimeMillis();
		
		firstTime = System.currentTimeMillis();
		
		SensorEventListener sensorListener = new SensorEventListener() {	
		    private int mLimit = 30;
		    private float mLastValues[] = new float[3*2];
		    private float mScale[] = new float[2];
		    private float mYOffset;

		    private float mLastDirections[] = new float[3*2];
		    private float mLastExtremes[][] = { new float[3*2], new float[3*2] };
		    private float mLastDiff[] = new float[3*2];
		    private int mLastMatch = -1;
		    
			@Override
			public void onSensorChanged(SensorEvent event) {
		        Sensor sensor = event.sensor;
		        synchronized (this) {
		            if (sensor.getType() == Sensor.TYPE_ORIENTATION) {
		            }
		            else {
		                int j = (sensor.getType() == SensorManager.SENSOR_ACCELEROMETER) ? 1 : 0;
		                if (j == 1) {
		                    float vSum = 0;
		                    for (int i=0 ; i<3 ; i++) {
		                        final float v = mYOffset + event.values[i] * mScale[j];
		                        vSum += v;
		                    }
		                    int k = 0;
		                    float v = vSum / 3;
		                    
		                    float direction = (v > mLastValues[k] ? 1 : (v < mLastValues[k] ? -1 : 0));
		                    if (direction == - mLastDirections[k]) {
		                        // Direction changed
		                        int extType = (direction > 0 ? 0 : 1); // minumum or maximum?
		                        mLastExtremes[extType][k] = mLastValues[k];
		                        float diff = Math.abs(mLastExtremes[extType][k] - mLastExtremes[1 - extType][k]);

		                        if (diff > mLimit) {
		                            
		                            boolean isAlmostAsLargeAsPrevious = diff > (mLastDiff[k]*2/3);
		                            boolean isPreviousLargeEnough = mLastDiff[k] > (diff/3);
		                            boolean isNotContra = (mLastMatch != 1 - extType);
		                            
		                            if (isAlmostAsLargeAsPrevious && isPreviousLargeEnough && isNotContra) {
		                                steps++;
		                                mLastMatch = extType;
		                            }
		                            else {
		                                mLastMatch = -1;
		                            }
		                        }
		                        mLastDiff[k] = diff;
		                    }
		                    mLastDirections[k] = direction;
		                    mLastValues[k] = v;
		                }
		            }
		        }
		        handler.sendEmptyMessage(2);
		    }	
			@Override
			public void onAccuracyChanged(Sensor sensor, int accuracy) {
				// TODO Auto-generated method stub
				
			}
		};
		Activa.myAccelerometerManager.registerListener(sensorListener, Activa.myAccelerometerManager.getDefaultSensor(SensorManager.SENSOR_ACCELEROMETER), SensorManager.SENSOR_DELAY_UI);	
	}

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case 0:
					Activa.myProgramManager.programList.get(ProgramManager.PROG_DISTANCE).nextStep();
					break;
				case 1:
					temporizer = Activa.myUIManager.loadExerciseScreen("", "Esperando datos ...", time);
					break;
				case 2:
					String result = "Pasos: " + steps + "\n\nDistancia: " + distance + "\n\nVelocidad: " + speed +  "\n\nAceleracion: " + acceleration;
					Activa.myUIManager.updateExerciseScreen(result, "2:Mostrando aceleracion");
					break;
			}
		}
	};
	
}