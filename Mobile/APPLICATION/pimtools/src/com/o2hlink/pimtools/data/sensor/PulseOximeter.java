package com.o2hlink.pimtools.data.sensor;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Set;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.o2hlink.activ8.client.entity.Pulseoxymetry;
import com.o2hlink.activ8.client.entity.Sample;
import com.o2hlink.pimtools.R;
import com.o2hlink.pimtools.Activa;
import com.o2hlink.pimtools.ActivaUtil;
import com.o2hlink.pimtools.ui.UIManager;

public class PulseOximeter extends Sensor {
	
	public Exercise exercise;

	public PulseOximeter() {
		this.name = Activa.myLanguageManager.SENSORS_PULSEOXI_TITLE;
		this.icon = R.drawable.pulseoximetry;
		this.id = SensorManager.ID_PULSIOXYMETER;
		this.results  = new Hashtable<Integer, Float>();
	}

	@Override
	public Sample getSample() {
		Pulseoxymetry sample = new Pulseoxymetry();
		sample.setDate(new Date());
		if (Activa.mySensorManager.eventAssociated != null)
			sample.setEvent(Activa.mySensorManager.eventAssociated.id);
		else sample.setEvent(null);
		sample.setHeartRate(this.results.get(SensorManager.DATAID_HR));
		sample.setOxygenSaturation(this.results.get(SensorManager.DATAID_SO2));
		return sample;
	}

	@Override
	public String getSensorSampleForPending() {
		String returned = "<MEASUREMENT ID=\"" + com.o2hlink.pimtools.data.sensor.SensorManager.ID_PULSIOXYMETER;
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

	public static Sensor getSampleFromPending(String xml) {
		PulseOximeter sample = new PulseOximeter();
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
	public void finishMeasurements(boolean outcome, Hashtable<Integer, Float> results) {
		this.thread.interrupt();
		if (this.bluetoothPreviouslyConnected) Activa.mySensorManager.reinitBluetooth();
		else Activa.myBluetoothAdapter.disable();
		this.results = results;
		if ((Activa.mySensorManager.eventAssociated != null)&&outcome)  {
			Activa.mySensorManager.eventAssociated.state = 0;
			Activa.myCalendarManager.saveCalendar();
		}
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
		this.sampleDate = new Date();
		if (Activa.mySensorManager.eventAssociated != null) {
			this.sampleEventId =  Activa.mySensorManager.eventAssociated.id;		
		} else this.sampleEventId = null;
		Activa.myBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		StartMeasurementPulsioximetry st = new StartMeasurementPulsioximetry(this);
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
			returned += "\" UNITS=\"" + SensorManager.getUnitIDForMeasurementID(key) + "\"/>";
		}
		returned += "</EVENT>";
		return returned;
	}

	@Override
	public String getSensorGlobalResult() {
		String result;
		Float so2 = this.results.get(SensorManager.DATAID_SO2);
		int countso2 = Activa.mySensorManager.sensorCountList.get(SensorManager.DATAID_SO2);
		Activa.mySensorManager.sensorCountList.put(SensorManager.DATAID_SO2, countso2 + 1);
		float maxso2 = Activa.mySensorManager.sensorMaxList.get(SensorManager.DATAID_SO2);
		Activa.mySensorManager.sensorLastList.put(SensorManager.DATAID_SO2, so2);
		if (so2 > maxso2) Activa.mySensorManager.sensorMaxList.put(SensorManager.DATAID_SO2, so2);
		
		Activa.mySensorManager.passSensorDBToFile();
		
		if (so2 > 90.0) {
			result = "2:" + Activa.myLanguageManager.SENSORS_PULSEOXI_MESSAGE2;
		}
		else if (so2 > 85.0) {
			result = "1:" + Activa.myLanguageManager.SENSORS_PULSEOXI_MESSAGE1;
		}
		else {
			result = "0:" + Activa.myLanguageManager.SENSORS_PULSEOXI_MESSAGE0;
		}
		return result;
	}

}

class StartMeasurementPulsioximetry implements Runnable {
	
	private static final int MEASURE_WAIT_TIME = 30;
	
	private PulseOximeter pulseoximeter;
	
	BroadcastReceiver myReceiver;
	
	BluetoothSocket socket;
	
	InputStream in;
	
	OutputStream out;

	boolean finished;

    byte [] data = new byte [3];
	
	public StartMeasurementPulsioximetry(PulseOximeter pulsioximeter) {
		this.pulseoximeter = pulsioximeter;
	}

	@Override
	public void run() {		
		finished = false;
		boolean found = false;
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
			this.pulseoximeter.bluetoothPreviouslyConnected = false;
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
			this.pulseoximeter.bluetoothPreviouslyConnected = true;
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
					    in = socket.getInputStream();
					    out = socket.getOutputStream();
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
					    while (((System.currentTimeMillis() - timeStart) < (MEASURE_WAIT_TIME * 1000)) && (!goodMeasure) && (!finished)) {
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
								    pulseoximeter.results.put(SensorManager.DATAID_HR, hr);
								    pulseoximeter.results.put(SensorManager.DATAID_SO2, so2);
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
//						try {
//							if (in != null) {
//								in.close();
//								in = null;
//							}
//							if (out != null) {
//								out.close();
//								out = null;
//							}
//							if (socket != null) {
//								socket.close();
//								socket = null;
//							}
//						} catch (IOException e) {
//							e.printStackTrace();
//						}
						pulseoximeter.finishMeasurements(false, null);
					}
				});
				break;
			case 2:
				pulseoximeter.finishMeasurements(true, pulseoximeter.results); 
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
				if (pulseoximeter.bluetoothPreviouslyConnected) Activa.mySensorManager.reinitBluetooth();
				else Activa.myBluetoothAdapter.disable();
				Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.SENSORS_BLUETOOTH_NOTCONNECTION);
				break;
			case 8:
				if ((Activa.mySensorManager.eventAssociated != null)) 
					Activa.myUIManager.loadScheduleDay(Activa.mySensorManager.eventAssociated.getDate()); 
				else
					Activa.myUIManager.loadSensorList();
				if (pulseoximeter.bluetoothPreviouslyConnected) Activa.mySensorManager.reinitBluetooth();
				else Activa.myBluetoothAdapter.disable();
				Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.SENSORS_BLUETOOTH_NOTPAIRED);
				break;
			case 9:
				if ((Activa.mySensorManager.eventAssociated != null)) 
					Activa.myUIManager.loadScheduleDay(Activa.mySensorManager.eventAssociated.getDate()); 
				else
					Activa.myUIManager.loadSensorList();
				if (pulseoximeter.bluetoothPreviouslyConnected) Activa.mySensorManager.reinitBluetooth();
				else Activa.myBluetoothAdapter.disable();
				Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.SENSORS_BLUETOOTH_NOTMEASURED);
				break;
			case 10:
				if ((Activa.mySensorManager.eventAssociated != null)) 
					Activa.myUIManager.loadScheduleDay(Activa.mySensorManager.eventAssociated.getDate()); 
				else
					Activa.myUIManager.loadSensorList();
				if (pulseoximeter.bluetoothPreviouslyConnected) Activa.mySensorManager.reinitBluetooth();
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
	
}