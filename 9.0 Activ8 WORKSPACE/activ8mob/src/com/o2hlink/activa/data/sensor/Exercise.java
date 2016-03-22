package com.o2hlink.activa.data.sensor;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Set;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.AnimationDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.media.SoundPool;
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
import android.view.View.OnLongClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.SeekBar.OnSeekBarChangeListener;

import com.o2hlink.activa.Activa;
import com.o2hlink.activa.R;
import com.o2hlink.activa.data.program.Walking;

public class Exercise extends com.o2hlink.activa.data.sensor.Sensor {
	
	public ExerciseRunnable exerciseRunnabe;
	
	public boolean track = false;
	
	public Exercise instance;
	
	WakeLock wakeLock;
	
	int[] order = {com.o2hlink.activa.data.sensor.SensorManager.DATAID_HR_AVRG, com.o2hlink.activa.data.sensor.SensorManager.DATAID_HR_PEAK, com.o2hlink.activa.data.sensor.SensorManager.DATAID_HR_LOW, -1,
		com.o2hlink.activa.data.sensor.SensorManager.DATAID_SO2_AVRG, com.o2hlink.activa.data.sensor.SensorManager.DATAID_SO2_PEAK, com.o2hlink.activa.data.sensor.SensorManager.DATAID_SO2_LOW, -1,
		com.o2hlink.activa.data.sensor.SensorManager.DATAID_DISTANCE, com.o2hlink.activa.data.sensor.SensorManager.DATAID_STEPS, com.o2hlink.activa.data.sensor.SensorManager.DATAID_STOPS, -1,
		com.o2hlink.activa.data.sensor.SensorManager.DATAID_TIME_EXE, com.o2hlink.activa.data.sensor.SensorManager.DATAID_TIME_DATA};

	public Exercise() {
		this.name = Activa.myLanguageManager.SENSORS_EXERCISE_TITLE;
		this.icon = R.drawable.pulseoximetry;
		this.id = com.o2hlink.activa.data.sensor.SensorManager.ID_EXERCISE;
		this.instance = this;
	}
	
	@Override
	public void finishMeasurements(boolean outcome, Hashtable<Integer, Float> result) {
		//TODO
		this.thread.interrupt();
		((Vibrator)Activa.myApp.getSystemService(Activa.myApp.VIBRATOR_SERVICE)).vibrate(3000);
    	if (this.bluetoothPreviouslyConnected) Activa.mySensorManager.reinitBluetooth();
		else Activa.myBluetoothAdapter.disable();
		this.results = result;
		if (track&&outcome){
			Activa.myUIManager.loadScreen(R.layout.sixminutesdata);
			((TextView) Activa.myApp.findViewById(R.id.startText)).setText(Activa.myLanguageManager.SENSORS_EXERCISE_TITLE);
			((TextView)Activa.myApp.findViewById(R.id.requestLaps)).setText(Activa.myLanguageManager.SENSORS_EXERCISE_TRACKTIMES);
			((TextView)Activa.myApp.findViewById(R.id.requestTrack)).setText(Activa.myLanguageManager.SENSORS_EXERCISE_TRACKLENGTH);
			final EditText laps = (EditText) Activa.myApp.findViewById(R.id.lapsText);
			final EditText size = (EditText) Activa.myApp.findViewById(R.id.trackText);
			ImageButton ok = (ImageButton) Activa.myApp.findViewById(R.id.ok);
			ok.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					try {
						float lapsnumber = Float.parseFloat(laps.getText().toString());
						float tracksize = Float.parseFloat(size.getText().toString());
						results.put(com.o2hlink.activa.data.sensor.SensorManager.DATAID_DISTANCE, lapsnumber*tracksize);
						if ((Activa.mySensorManager.eventAssociated != null)&&true)  {
							Activa.mySensorManager.eventAssociated.state = 0;
							Activa.myCalendarManager.saveCalendar();
						}
						Activa.myUIManager.finishSensorMeasurement(name, true, instance, order);
					} catch (Exception e) {
						finishMeasurements(true, results);
					}
				}
			});
		}
		else {
			if ((Activa.mySensorManager.eventAssociated != null)&&outcome)  {
				Activa.mySensorManager.eventAssociated.state = 0;
				Activa.myCalendarManager.saveCalendar();
			}
			Activa.myUIManager.finishSensorMeasurement(this.name, outcome, this, order);
		}
	}

	@Override
	public String getSensorGlobalResult() {
		String result;
		Float so2 = this.results.get(com.o2hlink.activa.data.sensor.SensorManager.DATAID_SO2);
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
	public void startMeasurement() {
		Activa.myUIManager.loadScreen(R.layout.sensortime);
		((TextView) Activa.myApp.findViewById(R.id.startText)).setText(Activa.myLanguageManager.SENSORS_EXERCISE_TITLE);
		((TextView) Activa.myApp.findViewById(R.id.textInfo)).setText(Activa.myLanguageManager.SENSORS_EXERCISE_TIME_ASKING);
		final CheckBox inLaps = ((CheckBox) Activa.myApp.findViewById(R.id.trackCheck));
		inLaps.setText(Activa.myLanguageManager.SENSORS_EXERCISE_CHECKTRACK);
		final EditText time = (EditText) Activa.myApp.findViewById(R.id.time);
		time.setText("00:00");
		ImageButton next = (ImageButton) Activa.myApp.findViewById(R.id.next);
		ImageButton back = (ImageButton) Activa.myApp.findViewById(R.id.back);
		ImageButton plus = (ImageButton) Activa.myApp.findViewById(R.id.plus);
		ImageButton minus = (ImageButton) Activa.myApp.findViewById(R.id.minus);
		next.setOnClickListener(new OnClickListener() {		
			@Override
			public void onClick(View v) {
				try {
					track = inLaps.isChecked();
					int seconds = Integer.parseInt(time.getText().toString().substring(0, 2))*60;
					seconds += Integer.parseInt(time.getText().toString().substring(3, 5));
					if (seconds == 0) seconds = 3600;
//					startMeasurement(seconds*1000, null);
					Walking walking = new Walking(seconds*1000);
					Activa.myProgramManager.eventAssociated = null;
					Activa.mySensorManager.eventAssociated = null;
					walking.startProgram();
				} catch (NumberFormatException e) {
					Activa.myUIManager.loadSensorList();
					Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.SENSORS_EXERCISE_TIME_FAILURE);
				}
			}
		});
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Activa.myUIManager.loadSensorList();
			}
		});
		plus.setOnClickListener(new OnClickListener() {		
			@Override
			public void onClick(View v) {
				try{ 
					int seconds = Integer.parseInt(time.getText().toString().substring(3, 5));
					int minutes = Integer.parseInt(time.getText().toString().substring(0, 2)) + 1;
					if (minutes > 59) minutes = 0;
					time.setText(String.format("%02d:%02d", minutes, seconds));
				} catch (Exception e) {
					time.setText("00:01");
				}
			}
		});
		minus.setOnClickListener(new OnClickListener() {		
			@Override
			public void onClick(View v) {
				try{ 
					int seconds = Integer.parseInt(time.getText().toString().substring(3, 5));
					int minutes = Integer.parseInt(time.getText().toString().substring(0, 2)) - 1;
					if (minutes < 0) minutes = 59;
					time.setText(String.format("%02d:%02d", minutes, seconds));
				} catch (Exception e) {
					time.setText("00:00");
				}
			}
		});
	}
	
	public void startMeasurement(long time, Hashtable<Long,String> timedMessages) {
		PowerManager pm = (PowerManager) Activa.myApp.getSystemService(Context.POWER_SERVICE);
	    wakeLock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK|PowerManager.ACQUIRE_CAUSES_WAKEUP, "Exercise");
	    wakeLock.acquire();
	    Activa.myApp.getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		this.results = new Hashtable<Integer, Float>();
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
		this.soundpool = new SoundPool(4, Activa.myAudioManager.STREAM_RING, 0); 
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
						this.streamVolume = Activa.myAudioManager.getStreamVolume(Activa.myAudioManager.STREAM_RING);
						this.streamVolume = soundOn*(this.streamVolume/Activa.myAudioManager.getStreamMaxVolume(Activa.myAudioManager.STREAM_RING));
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
		        		BluetoothDevice hxm = BluetoothAdapter.getDefaultAdapter().getRemoteDevice(device.getAddress());
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
						handler.sendEmptyMessage(1);
					    byte [] data = new byte [3];
				  		long timeStart = System.currentTimeMillis();
				  		int count = 0;
				  		int lost = 0;
				  		int bad = 0;
				  		this.timePassed = System.currentTimeMillis() - timeStart;
				  		Runnable metronome = new Runnable() {
							@Override
							public void run() {
								while ((!timeFinished)&&(finished)) {
									streamVolume = Activa.myAudioManager.getStreamVolume(Activa.myAudioManager.STREAM_RING);
									streamVolume = soundOn*(streamVolume/Activa.myAudioManager.getStreamMaxVolume(Activa.myAudioManager.STREAM_RING));
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
				  		    	if (lost == 10) ((Vibrator)Activa.myApp.getSystemService(Activa.myApp.VIBRATOR_SERVICE)).vibrate(300);
				  		    	if (lost == 20) ((Vibrator)Activa.myApp.getSystemService(Activa.myApp.VIBRATOR_SERVICE)).vibrate(300);
				  		    	if (lost > 30) {
					  		    	Log.d("EXERCISE", "Process killed: too many data lost ...");
									this.finished = false;
									handler.sendEmptyMessage(7);
									break;
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
								if (bad == 5) {
				  		    		Log.d("EXERCISE", "Bad data: warning 1 ...");
							    	handler.sendEmptyMessage(22);
				  		    	}
								if (bad == 10) ((Vibrator)Activa.myApp.getSystemService(Activa.myApp.VIBRATOR_SERVICE)).vibrate(300);
								if (bad == 20) ((Vibrator)Activa.myApp.getSystemService(Activa.myApp.VIBRATOR_SERVICE)).vibrate(300);
								if (bad == 30) ((Vibrator)Activa.myApp.getSystemService(Activa.myApp.VIBRATOR_SERVICE)).vibrate(300);
				  		    	if (bad == 30) {
				  		    		Log.d("EXERCISE", "Bad data: warning 2 ...");
//									streamVolume = Activa.myAudioManager.getStreamVolume(Activa.myAudioManager.STREAM_RING);
//									streamVolume = soundOn*(streamVolume/Activa.myAudioManager.getStreamMaxVolume(Activa.myAudioManager.STREAM_RING));
//							        soundpool.play(soundpoolmap.get(SYSTEMALERT_SOUND), streamVolume, streamVolume, 1, 0, 1f);
							    	handler.sendEmptyMessage(21);
				  		    	}
				  		    	if (bad == 40) ((Vibrator)Activa.myApp.getSystemService(Activa.myApp.VIBRATOR_SERVICE)).vibrate(300);
				  		    	if (bad == 50) ((Vibrator)Activa.myApp.getSystemService(Activa.myApp.VIBRATOR_SERVICE)).vibrate(300);
				  		    	if (bad == 60) {
				  		    		Log.d("EXERCISE", "Process killed: too many bad data ...");
									streamVolume = Activa.myAudioManager.getStreamVolume(Activa.myAudioManager.STREAM_RING);
									streamVolume = soundOn*(streamVolume/Activa.myAudioManager.getStreamMaxVolume(Activa.myAudioManager.STREAM_RING));
							        soundpool.play(soundpoolmap.get(SYSTEMALERT_SOUND), streamVolume, streamVolume, 1, 0, 1f);
									this.finished = false;
									handler.sendEmptyMessage(12);
									break;
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
							    	this.avrgHr = ((this.avrgHr * count) + hr)/(count + 1);
							    	this.avrgSo2 = ((this.avrgSo2 * count) + so2)/(count + 1);
								    this.lastHr = hr;
								    this.lastSo2 = so2;
								    this.timeMeasured += 1000;
							    	count++;
									handler.sendEmptyMessage(2);
							    }
							    else {
					  		    	bad++;
					  		    	Log.d("EXERCISE", "Data out of bounds ...");
					  		    	if (bad == 5) {
					  		    		Log.d("EXERCISE", "Data out of bounds: warning 1 ...");
								    	handler.sendEmptyMessage(22);
					  		    	}
					  		    	if (bad == 10) ((Vibrator)Activa.myApp.getSystemService(Activa.myApp.VIBRATOR_SERVICE)).vibrate(300);
					  		    	if (bad == 20) ((Vibrator)Activa.myApp.getSystemService(Activa.myApp.VIBRATOR_SERVICE)).vibrate(300);
					  		    	if (bad == 30) ((Vibrator)Activa.myApp.getSystemService(Activa.myApp.VIBRATOR_SERVICE)).vibrate(300);
					  		    	if (bad == 30) {
					  		    		Log.d("EXERCISE", "Data out of bounds: warning 2 ...");
//					  		    		streamVolume = Activa.myAudioManager.getStreamVolume(Activa.myAudioManager.STREAM_RING);
//										streamVolume = soundOn*(streamVolume/Activa.myAudioManager.getStreamMaxVolume(Activa.myAudioManager.STREAM_RING));
//								        soundpool.play(soundpoolmap.get(SYSTEMALERT_SOUND), streamVolume, streamVolume, 1, 0, 1f);
								    	handler.sendEmptyMessage(21);
					  		    	}
					  		    	if (bad == 40) ((Vibrator)Activa.myApp.getSystemService(Activa.myApp.VIBRATOR_SERVICE)).vibrate(300);
					  		    	if (bad == 50) ((Vibrator)Activa.myApp.getSystemService(Activa.myApp.VIBRATOR_SERVICE)).vibrate(300);
					  		    	if (bad == 60) {
					  		    		Log.d("EXERCISE", "Process killed: too many data out of bounds ...");
										this.finished = false;
										streamVolume = Activa.myAudioManager.getStreamVolume(Activa.myAudioManager.STREAM_RING);
										streamVolume = soundOn*(streamVolume/Activa.myAudioManager.getStreamMaxVolume(Activa.myAudioManager.STREAM_RING));
								        soundpool.play(soundpoolmap.get(SYSTEMALERT_SOUND), streamVolume, streamVolume, 1, 0, 1f);
										handler.sendEmptyMessage(12);
										break;
					  		    	}
							    }
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
					    	exercise.results.put(com.o2hlink.activa.data.sensor.SensorManager.DATAID_HR_AVRG, this.avrgHr);
					    	exercise.results.put(com.o2hlink.activa.data.sensor.SensorManager.DATAID_HR_PEAK, this.peakHr);
					    	exercise.results.put(com.o2hlink.activa.data.sensor.SensorManager.DATAID_HR_LOW, this.lowHr);
					    	exercise.results.put(com.o2hlink.activa.data.sensor.SensorManager.DATAID_SO2_AVRG, this.avrgSo2);
					    	exercise.results.put(com.o2hlink.activa.data.sensor.SensorManager.DATAID_SO2_PEAK, this.peakSo2);
					    	exercise.results.put(com.o2hlink.activa.data.sensor.SensorManager.DATAID_SO2_LOW, this.lowSo2);
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
				temporizer = new CountDownTimer(time, 1000) {
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
				    	timeFinished = true;
					}
				};
				temporizer.start();
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
						Activa.myAccelerometerManager.unregisterListener(sensorListener);
						exercise.results.put(com.o2hlink.activa.data.sensor.SensorManager.DATAID_HR_AVRG, avrgHr);
						exercise.results.put(com.o2hlink.activa.data.sensor.SensorManager.DATAID_HR_PEAK, peakHr);
						exercise.results.put(com.o2hlink.activa.data.sensor.SensorManager.DATAID_HR_LOW, lowHr);
						exercise.results.put(com.o2hlink.activa.data.sensor.SensorManager.DATAID_SO2_AVRG, avrgSo2);
						exercise.results.put(com.o2hlink.activa.data.sensor.SensorManager.DATAID_SO2_PEAK, peakSo2);
						exercise.results.put(com.o2hlink.activa.data.sensor.SensorManager.DATAID_SO2_LOW, lowSo2);
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
			case 2:
				animation.start();
				String textGlobal = getGlobalResult();
				int status = Integer.parseInt(textGlobal.substring(0,1));
				streamVolume = Activa.myAudioManager.getStreamVolume(Activa.myAudioManager.STREAM_RING);
				streamVolume = soundOn*(streamVolume/Activa.myAudioManager.getStreamMaxVolume(Activa.myAudioManager.STREAM_RING));
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
				else if (Activa.mySensorManager.programassociated != null) 
					Activa.myUIManager.loadProgramList();
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
				else if (Activa.mySensorManager.programassociated != null) 
					Activa.myUIManager.loadProgramList();
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
				else if (Activa.mySensorManager.programassociated != null) 
					Activa.myUIManager.loadProgramList();
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
				else if (Activa.mySensorManager.programassociated != null) 
					Activa.myUIManager.loadProgramList();
				else
					Activa.myUIManager.loadSensorList();
				if (exercise.bluetoothPreviouslyConnected) Activa.mySensorManager.reinitBluetooth();
				else Activa.myBluetoothAdapter.disable();
				if (sensorListener != null) Activa.myAccelerometerManager.unregisterListener(sensorListener);
				if (exercise.wakeLock != null) exercise.wakeLock.release();
				Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.SENSORS_BLUETOOTH_NOTCONFIG);
				break;
			case 11:
				streamVolume = Activa.myAudioManager.getStreamVolume(Activa.myAudioManager.STREAM_RING);
				streamVolume = soundOn*(streamVolume/Activa.myAudioManager.getStreamMaxVolume(Activa.myAudioManager.STREAM_RING));
		        soundpool.play(soundpoolmap.get(FINISH_SOUND), streamVolume, streamVolume, 1, 0, 1f);
		        if (sensorListener != null) Activa.myAccelerometerManager.unregisterListener(sensorListener);
				if (exercise.wakeLock != null) exercise.wakeLock.release();
				exercise.finishMeasurements(true, exercise.results);
				break;
			case 12:
				if ((Activa.mySensorManager.eventAssociated != null)) 
					Activa.myUIManager.loadScheduleDay(Activa.mySensorManager.eventAssociated.getDate()); 
				else if (Activa.mySensorManager.programassociated != null) 
					Activa.myUIManager.loadProgramList();
				else
					Activa.myUIManager.loadSensorList();
				if (exercise.bluetoothPreviouslyConnected) Activa.mySensorManager.reinitBluetooth();
				else Activa.myBluetoothAdapter.disable();
				if (sensorListener != null) Activa.myAccelerometerManager.unregisterListener(sensorListener);
				if (exercise.wakeLock != null) exercise.wakeLock.release();
				Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.SENSORS_BLUETOOTH_BADMEAS);
				break;
			case 21:
				String textGlobal2 = "1:" + Activa.myLanguageManager.SENSORS_EXERCISE_BADMESSAGE;
				Activa.myUIManager.updateExerciseScreen((int)lastHr, (int)lastSo2, steps, stops, textGlobal2);
				break;
			case 22:
				animation.stop();
				break;
			case 31:
				Activa.myUIManager.loadInfoPopupWithoutExiting(Activa.myLanguageManager.SENSORS_CONFIG);
				break;
			}
		}
	};
	
}
