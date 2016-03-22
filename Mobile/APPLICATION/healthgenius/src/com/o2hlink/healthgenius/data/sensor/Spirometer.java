package com.o2hlink.healthgenius.data.sensor;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.o2hlink.activ8.client.entity.Sample;
import com.o2hlink.activ8.client.entity.Spirometry;
import com.o2hlink.activ8.client.entity.SpirometryFlow;
import com.o2hlink.activ8.common.entity.Sex;
import com.o2hlink.healthgenius.HealthGenius;
import com.o2hlink.healthgenius.HealthGeniusConfig;
import com.o2hlink.healthgenius.HealthGeniusUtil;
import com.o2hlink.healthgenius.R;

public class Spirometer extends Sensor {
	
	public int [] flowLine;
	
	public int [] timeLine;
	
	public Spirometer() {
		this.name = HealthGenius.myLanguageManager.SENSORS_SPIRO_TITLE;
		this.icon = R.drawable.spirometry;
		this.id = SensorManager.ID_SPIROMETER;
	}
	
	@Override
	public Sample getSample() {
		Spirometry sample = new Spirometry();
		float fvc = this.results.get(SensorManager.DATAID_FVC);
		float fev1 = this.results.get(SensorManager.DATAID_FEV1);
		float pef = this.results.get(SensorManager.DATAID_PEF);
		sample.setDate(new Date());
		if (HealthGenius.mySensorManager.eventAssociated != null)
			sample.setEvent(HealthGenius.mySensorManager.eventAssociated.id);
		else sample.setEvent(null);
		sample.setForcedExpiratoryVolume(fev1);
		sample.setForcedVitalCapacity(fvc);
		sample.setPeakExpiratoryFlow(pef);
		return sample;
	}

	@Override
	public String getSensorSampleForPending() {
		String returned = "<MEASUREMENT ID=\"" + com.o2hlink.healthgenius.data.sensor.SensorManager.ID_SPIROMETER;
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
		returned += "<LIST ID=\"" + SensorManager.DATAID_FLOWCHART + "\" VALUE=\"" + HealthGeniusUtil.arrayToCsv(this.flowLine) + "\"/>";
		returned += "<LIST ID=\"" + SensorManager.DATAID_TIMECHART + "\" VALUE=\"" + HealthGeniusUtil.arrayToCsv(this.timeLine) + "\"/>";
		returned += "</MEASUREMENT>";
		return returned;
	}

	public static Sensor getSampleFromPending(String xml) {
		Spirometer sample = new Spirometer();
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
	                } else if (info.getName().equalsIgnoreCase("LIST")) {
	                	Integer key = Integer.parseInt(info.getAttributeValue(info.getNamespace(), "ID"));
	                	String value = info.getAttributeValue(info.getNamespace(), "VALUE");
	                	StringTokenizer tokenizer = new StringTokenizer(value, ",");
	                	int i = 0;
	                	switch (key) {
							case SensorManager.DATAID_FLOWCHART:
								while (tokenizer.hasMoreElements()) {
									sample.flowLine[i] = Integer.parseInt(tokenizer.nextToken());
								}
								break;
							case SensorManager.DATAID_TIMECHART:
								while (tokenizer.hasMoreElements()) {
									sample.timeLine[i] = Integer.parseInt(tokenizer.nextToken());
								}
								break;
							default:
								break;
						}
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
	
	public SpirometryFlow getFlows() {
		List<Double> flow = new ArrayList<Double>();
		List<Double> time = new ArrayList<Double>();
		for (int i = 0; i < this.flowLine.length; i++) {
			flow.add((double)(this.flowLine[i]));
		}
		for (int i = 0; i < this.timeLine.length; i++) {
			time.add((double)(this.timeLine[i]));
		}
		return new SpirometryFlow(flow, time);
	}
	
	@Override
	public void finishMeasurements(boolean outcome, Hashtable<Integer, Float> results) {
		if (this.bluetoothPreviouslyConnected) HealthGenius.mySensorManager.reinitBluetooth();
		else HealthGenius.myBluetoothAdapter.disable();
		this.results = results;
		if (outcome) {
			float fvc = this.results.get(SensorManager.DATAID_FVC);
			float fev1 = this.results.get(SensorManager.DATAID_FEV1);
			float pef = this.results.get(SensorManager.DATAID_PEF);
			float lastfvc = HealthGenius.mySensorManager.sensorLastList.get(SensorManager.DATAID_FVC);
			float lastfev1 = HealthGenius.mySensorManager.sensorLastList.get(SensorManager.DATAID_FEV1);
			float lastpef = HealthGenius.mySensorManager.sensorLastList.get(SensorManager.DATAID_PEF);
			if ((lastfvc==fvc)&&(lastfev1==fev1)&&(lastpef==pef)) {
				if (HealthGenius.mySensorManager.eventAssociated != null) 
					HealthGenius.myUIManager.UIcalendar.loadScheduleDay(HealthGenius.mySensorManager.eventAssociated.getDate()); 
				else
					HealthGenius.myUIManager.UImeas.loadSensorList();
				HealthGenius.myUIManager.UImisc.loadInfoPopupLong(HealthGenius.myLanguageManager.SENSORS_SPIRO_DATAREPEATED);
				return;
			}
			if (HealthGenius.mySensorManager.eventAssociated != null) {
				HealthGenius.mySensorManager.eventAssociated.state = 0;
				HealthGenius.myCalendarManager.saveCalendar();
			}
		}
		HealthGenius.myUIManager.UImeas.finishSpirometry(this.name, outcome, this);
	}

	@Override
	public void startMeasurement() {
		Date now = new Date();
		this.sampleDate = now;
		if (HealthGenius.mySensorManager.eventAssociated != null) {
			this.sampleEventId =  HealthGenius.mySensorManager.eventAssociated.id;		
		} else this.sampleEventId = null;
		if (HealthGenius.myMobileManager.user.getLastupdate().getTime() == 0) {
			HealthGenius.myUIManager.UIlogin.loadRequestDataScreen();
			return;
		}
		else if (((now.getTime() - HealthGenius.myMobileManager.user.getLastupdate().getTime())/86400000l) > HealthGeniusConfig.USERS_UPDATING) {
			HealthGenius.myApp.setContentView(R.layout.yesnoquestion);
			TextView question = (TextView)HealthGenius.myApp.findViewById(R.id.question);
			question.setText(HealthGenius.myLanguageManager.SENSORS_SPIRO_DATAREQUEST);
			ImageButton yes = (ImageButton)HealthGenius.myApp.findViewById(R.id.yes);
			ImageButton no = (ImageButton)HealthGenius.myApp.findViewById(R.id.no);
			yes.setOnClickListener(null);
			yes.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					HealthGenius.myUIManager.UIlogin.loadRequestDataScreen();
				}
			});
			no.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					HealthGenius.myMobileManager.user.setLastupdate(new Date());
					HealthGenius.myMobileManager.addUserWithPassword(HealthGenius.myMobileManager.user);
					if (HealthGenius.mySensorManager.eventAssociated != null) {
						HealthGenius.myUIManager.UIcalendar.loadScheduleDay(new Date());
						HealthGenius.mySensorManager.sensorList.get(SensorManager.ID_SPIROMETER).startMeasurement();
					}
					else {
						HealthGenius.myUIManager.UImeas.loadSensorList();
						HealthGenius.mySensorManager.sensorList.get(SensorManager.ID_SPIROMETER).startMeasurement();
					}
				}
			});
			return;
    	}
		this.results = new Hashtable<Integer, Float>();
		HealthGenius.myBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		StartMeasurementSpirometer st = new StartMeasurementSpirometer(this);
		Thread thr = new Thread(st);
		this.thread = thr;
		thr.start();
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
			returned += "\" UNITS=\"" + SensorManager.getUnitIDForMeasurementID(key) + "\"/>";
		}
		returned += "<LIST ID=\"" + SensorManager.DATAID_FLOWCHART + "\" VALUE=\"" + HealthGeniusUtil.arrayToCsv(this.flowLine);
		returned += "\" UNITS=\"" + SensorManager.UNIT_NULL + "\"/>";
		returned += "<LIST ID=\"" + SensorManager.DATAID_TIMECHART + "\" VALUE=\"" + HealthGeniusUtil.arrayToCsv(this.timeLine);
		returned += "\" UNITS=\"" + SensorManager.UNIT_NULL + "\"/>";
		returned += "</EVENT>";
		return returned;
	}

	@Override
	public String getSensorGlobalResult() {		
		String result;
		float teoricFVC = 0;
		float teoricFEV1 = 0;
		float teoricPEF = 0;
		
		float fvc = this.results.get(SensorManager.DATAID_FVC);
		float fev1 = this.results.get(SensorManager.DATAID_FEV1);
		float pef = this.results.get(SensorManager.DATAID_PEF);
		
		int countfvc = HealthGenius.mySensorManager.sensorCountList.get(SensorManager.DATAID_FVC);
		int countfev1 = HealthGenius.mySensorManager.sensorCountList.get(SensorManager.DATAID_FEV1);
		int countpef = HealthGenius.mySensorManager.sensorCountList.get(SensorManager.DATAID_PEF);
		HealthGenius.mySensorManager.sensorCountList.put(SensorManager.DATAID_FVC, countfvc + 1);
		HealthGenius.mySensorManager.sensorCountList.put(SensorManager.DATAID_FEV1, countfev1 + 1);
		HealthGenius.mySensorManager.sensorCountList.put(SensorManager.DATAID_PEF, countpef + 1);
		
		HealthGenius.mySensorManager.sensorLastList.put(SensorManager.DATAID_FVC, fvc);
		HealthGenius.mySensorManager.sensorLastList.put(SensorManager.DATAID_FEV1, fev1);
		HealthGenius.mySensorManager.sensorLastList.put(SensorManager.DATAID_PEF, pef);
		
		float maxfvc = HealthGenius.mySensorManager.sensorMaxList.get(SensorManager.DATAID_FVC);
		float maxfev1 = HealthGenius.mySensorManager.sensorMaxList.get(SensorManager.DATAID_FVC);
		float maxpef = HealthGenius.mySensorManager.sensorMaxList.get(SensorManager.DATAID_PEF);
		if (fvc > maxfvc) HealthGenius.mySensorManager.sensorMaxList.put(SensorManager.DATAID_FVC, fvc);
		if (fev1 > maxfev1) HealthGenius.mySensorManager.sensorMaxList.put(SensorManager.DATAID_FEV1, fev1);
		if (pef > maxpef) HealthGenius.mySensorManager.sensorMaxList.put(SensorManager.DATAID_PEF, pef);
		
		HealthGenius.mySensorManager.passSensorDBToFile();
		
		Date dateToCompare = new Date();
		if ((HealthGenius.myMobileManager.user.getBirthdate() == null)) {
			return "0:" + HealthGenius.myLanguageManager.SENSORS_DATA_LACKOF;
		}
		int age = (int) ((dateToCompare.getTime() - HealthGenius.myMobileManager.user.getBirthdate().getTime())/31536000000l);
		int height = HealthGenius.myMobileManager.user.getHeight();
		if (HealthGenius.myMobileManager.user.getSex().equals(Sex.MALE)) {
			teoricFVC = (float) ((0.0678*height) - (0.0147*age) - 6.0548);
			teoricFEV1 = (float) ((0.0499*height) - (0.0211*age) - 3.837);
			teoricPEF = (float) ((0.0945*height) - (0.0209*age) - 5.7732);
		}
		else if (HealthGenius.myMobileManager.user.getSex().equals(Sex.FEMALE))  {
			teoricFVC = (float) ((0.0454*height) - (0.0211*age) - 2.8253);
			teoricFEV1 = (float) ((0.0317*height) - (0.025*age) - 1.2324);
			teoricPEF = (float) ((0.0448*height) - (0.0304*age) + 0.3496);
		}
		else {
			result = "2:" + HealthGenius.myLanguageManager.SENSORS_DATA_LACKOF;
			return result;
		}
		float percentage = ((pef/teoricPEF) + (fvc/teoricFVC) + (fev1/teoricFEV1))/3.0f;
		if (percentage > 0.8) {
			result = "2:" + HealthGenius.myLanguageManager.SENSORS_SPIRO_MESSAGE2;
		}
		else if (percentage > 0.5) {
			result = "1:" + HealthGenius.myLanguageManager.SENSORS_SPIRO_MESSAGE1;
		}
		else {
			result = "0:" + HealthGenius.myLanguageManager.SENSORS_SPIRO_MESSAGE0;
		}
		return result;
	}

}

class StartMeasurementSpirometer implements Runnable {
	
	private Spirometer spirometer;
	
	BluetoothSocket socket;
	
	InputStream in;
	
	OutputStream out;
	
	byte[] deviceBootloaderVersion;
	
	byte[] deviceFirmwareVersion;
	
	byte[] hardwareVersion;
	
	byte[] deviceType;
	
	byte[] serialNumber;
	
	int recordNumber;
	
	boolean found;
	
	public final static byte[] GET_VERSION = { 
        0x02, // <STX>
        0x76, // v
	};
	
	public final static byte[] SET_PCMODE = { 
		0x02, // <STX>
		0x70, // p
	};
	
	public final static byte[] GET_DATETIME = { 
		0x02, // <STX>
		0x44, // D
	};
	
	public final static byte[] GOTO_LAST_RECORD = { 
		0x02, // <STX>
		0x4C, // L
	};
	
	public final static byte[] GET_RECORD_HEADER = { 
		0x02, // <STX>
		0x48, // H
	};
	
	public final static byte[] GET_RECORD_CURVES = { 
		0x02, // <STX>
		0x78, // x
	};
	
	public final static byte[] FINISH = {
	    0x02, // <STX>
	    0x34, // 4 
	};
	
	public final static byte[] RESET = {
	    0x02, // <STX>
	    0x30, // 4 
	};
	
	public final static byte[] ACK = { // ACK command
		0x02, // ACK
		0x30, // 0
	};
	public StartMeasurementSpirometer(Spirometer spirometer) {
		this.spirometer = spirometer;
	}

	@Override
	public void run() {	
		byte [] ack;
		byte [] data;
		deviceBootloaderVersion = new byte[8];
		deviceFirmwareVersion = new byte[8];
		hardwareVersion = new byte[2];
		deviceType = new byte[2];
		serialNumber = new byte[4];
		found = false;
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
			this.spirometer.bluetoothPreviouslyConnected = false;
			handler.sendEmptyMessage(6);
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
			this.spirometer.bluetoothPreviouslyConnected = true;
		}
		Set<BluetoothDevice> pairedDevices = HealthGenius.myBluetoothAdapter.getBondedDevices();
		// If there are paired devices
		if (pairedDevices.size() > 0) {
		    // Loop through paired devices
		    for (BluetoothDevice device : pairedDevices) {
		        // Add the name and address to an array adapter to show in a ListView
		        String name = device.getName();
		        if (name.contains("EasyBlueTooth")) {
					Method m;
					BluetoothDevice hxm = BluetoothAdapter.getDefaultAdapter().getRemoteDevice(device.getAddress());
	        		try {
	        			found = true;
						m = device.getClass().getMethod("createRfcommSocket", new Class[]{int.class});
		        		socket = (BluetoothSocket)m.invoke(hxm, Integer.valueOf(1));
		        		handler.sendEmptyMessage(1);
//		        		CountDownTimer timerToConnect = new CountDownTimer(15000, 1000) {
//							@Override
//							public void onTick(long millisUntilFinished) {
//							}
//							@Override
//							public void onFinish() {
//								HealthGenius.throwException(new Exception());
//							}
//						};
//						timerToConnect.start();
		        		socket.connect();	
//		        		timerToConnect.cancel();
						handler.sendEmptyMessage(2);
					    in = socket.getInputStream();
					    out = socket.getOutputStream();
	    			    // Get version data
	    			    out.write(GET_VERSION);
	    			    ack = new byte [2];
	    			    in.read(ack); 
	    			    if ((ack[1] == ACK[0])) {
	    			    	ack[0] = ack[1];
	    			    	ack[1] = (byte) in.read();
	    			    }
	    			    if ((ack[0] != ACK[0])&&(ack[1] != ACK[1])) {
	    			    	handler.sendEmptyMessage(7);
	    					out.write(RESET);
		    		        closeConnection();
		    		        return;
	    			    }
	    			    data = new byte [24];
	    			    in.read(data);
	    			    System.arraycopy(data, 0, deviceBootloaderVersion, 0, 8);
	    			    System.arraycopy(data, 8, deviceFirmwareVersion, 0, 8);
	    			    System.arraycopy(data, 16, hardwareVersion, 0, 2);
	    			    System.arraycopy(data, 18, deviceType, 0, 2);
	    			    System.arraycopy(data, 20, serialNumber, 0, 4);
	    			    // Wait a second
	    			    Thread.sleep(1000);
	    			    // Set PC Mode
	    			    out.write(SET_PCMODE);
	    			    // Wait a second
	    			    Thread.sleep(1000);
	    			    ack = new byte [2];
	    			    in.read(ack); 
	    			    if ((ack[1] == ACK[0])) {
	    			    	ack[0] = ack[1];
	    			    	ack[1] = (byte) in.read();
	    			    }
	    			    if ((ack[0] != ACK[0])||(ack[1] != ACK[1])) {
	    			    	handler.sendEmptyMessage(7);
	    					out.write(RESET);
		    		        closeConnection();
		    		        return;
	    			    }
	    			    // Wait a second
	    			    Thread.sleep(1000);
	    			    // Go to last record
	    			    out.write(GOTO_LAST_RECORD);
	    			    ack = new byte [2];
	    			    // Wait a second
	    			    Thread.sleep(1000);
	    			    in.read(ack);  
	    			    if ((ack[1] == ACK[0])) {
	    			    	ack[0] = ack[1];
	    			    	ack[1] = (byte) in.read();
	    			    }
	    			    if ((ack[0] != ACK[0])||(ack[1] != ACK[1])) {
	    			    	handler.sendEmptyMessage(7);
	    					out.write(RESET);
		    		        closeConnection();
		    		        return;
	    			    }
	    			    data = new byte [4];
	    			    in.read(data); 
	    			    recordNumber = Integer.parseInt(new String(data),16);
	    			    // Wait a second
	    			    Thread.sleep(1000);
	    			    // Get Record header
	    			    out.write(GET_RECORD_HEADER);
	    			    // Wait a second
	    			    Thread.sleep(1000);
	    			    // Wait a second
	    			    Thread.sleep(1000);
	    			    ack = new byte [2];
	    			    in.read(ack);  
	    			    if ((ack[1] == ACK[0])) {
	    			    	ack[0] = ack[1];
	    			    	ack[1] = (byte) in.read();
	    			    }
	    			    if ((ack[0] != ACK[0])||(ack[1] != ACK[1])) {
	    			    	handler.sendEmptyMessage(7);
	    					out.write(RESET);
		    		        closeConnection();
		    		        return;
	    			    }
	    			    data = new byte [4];
	    			    in.read(data); 
	    			    int recordLength = Integer.parseInt(new String(data),16);
		        		handler.sendEmptyMessage(3);
	    			    // Wait for two seconds
	    			    Thread.sleep(2000);
	    			    // Read record basic data
	    			    data = new byte [recordLength];
	    			    in.read(data);
	    		        byte [] readableData = new byte [recordLength/2];
	    		        for (int ni=0; ni < data.length; ni+=2) {
	    		              readableData [ni/2] = (byte) Integer.parseInt(new String(new byte [] {data[ni], data[ni+1]}),16);
	    		        }
	    		        int FVCint = HealthGeniusUtil.wordLEToInt(HealthGeniusUtil.subArray(readableData,112,2),true);
	    		        float FVC = (float) (((float)FVCint)*(-625.0)/(float)1000000.0);
	    		        int FEV1int = HealthGeniusUtil.wordLEToInt(HealthGeniusUtil.subArray(readableData,108,2),true);
	    		        float FEV1 = (float) (((float)FEV1int)*(-625.0)/(float)1000000.0);
	    		        int PEFint = HealthGeniusUtil.wordLEToInt(HealthGeniusUtil.subArray(readableData,114,2),true);
	    		        float PEF = (float) (((float)PEFint)*(-625.0)/(float)1000000.0);
	    		        spirometer.results.put(SensorManager.DATAID_FVC, FVC);
	    		        spirometer.results.put(SensorManager.DATAID_FEV1, FEV1);
	    		        spirometer.results.put(SensorManager.DATAID_PEF, PEF);
	    		        // Get curves length
	    		        int fvLen = HealthGeniusUtil.wordLEToInt(HealthGeniusUtil.subArray(readableData, 148, 2),false);
	    		        int vtLen = HealthGeniusUtil.wordLEToInt(HealthGeniusUtil.subArray(readableData, 150, 2),false);
	    		        // Wait a second
	    			    Thread.sleep(1000);
	    			    // Get Record header
	    			    out.write(GET_RECORD_CURVES);
	    			    // Wait a second
	    			    Thread.sleep(1000);
	    			    ack = new byte [2];
	    			    in.read(ack);  
	    			    if ((ack[1] == ACK[0])) {
	    			    	ack[0] = ack[1];
	    			    	ack[1] = (byte) in.read();
	    			    }
	    			    if ((ack[0] != ACK[0])||(ack[1] != ACK[1])) {
	    			    	handler.sendEmptyMessage(7);
	    					out.write(RESET);
		    		        closeConnection();
		    		        return;
	    			    } 
	    			    data = new byte [4];
	    			    in.read(data); 
	    			    int curvesLength = Integer.parseInt(new String(data),16);
	    			    // Wait for two seconds
	    			    Thread.sleep(2000);
	    			    // Read curves data
	    			    data = new byte [curvesLength];
	    			    in.read(data);
	    			    byte [] readableCurveData = new byte [curvesLength/2];
	    		        for (int ni=0; ni < data.length; ni+=2) {
	    		        	readableCurveData [ni/2] = (byte) Integer.parseInt(new String(new byte [] {data[ni], data[ni+1]}),16);
	    		        }
	    		        // Add 0,0 point to flow curve.
	    		        byte [] curveData = new byte[readableCurveData.length + 1];
	    		        curveData[0] = 0;
	    		        System.arraycopy(readableCurveData, 0, curveData, 1, readableCurveData.length);
	    		        //Decompress the curve as protocol
	    		        spirometer.flowLine = deltaDecompress(HealthGeniusUtil.subArray(curveData, 0, fvLen));
	    		        spirometer.timeLine = deltaDecompress(HealthGeniusUtil.subArray(curveData, fvLen, vtLen));
	    		        
	    		        // Finish
	    				out.write(RESET);
	    		        closeConnection();
				        handler.sendEmptyMessage(4);	
	    		    } catch (NullPointerException e) {
						// TODO Auto-generated catch block
	    		        closeConnection();
						e.printStackTrace();
	    		    } catch (SecurityException e) {
						// TODO Auto-generated catch block
	    		        closeConnection();
	    		        handler.sendEmptyMessage(8);
						e.printStackTrace();
					} catch (NoSuchMethodException e) {
						// TODO Auto-generated catch block
	    		        closeConnection();
	    		        handler.sendEmptyMessage(8);
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
	    		        closeConnection();
	    		        handler.sendEmptyMessage(8);
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
	    		        closeConnection();
	    		        handler.sendEmptyMessage(8);
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						// TODO Auto-generated catch block
	    		        closeConnection();
	    		        handler.sendEmptyMessage(8);
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
	    		        closeConnection();
	    		        handler.sendEmptyMessage(8);
						e.printStackTrace();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
	    		        closeConnection();
	    		        handler.sendEmptyMessage(8);
						e.printStackTrace();
					}   	
		        }
		    }
		}
        if (!found) handler.sendEmptyMessage(8);
	}
	
	public static int[] deltaDecompress(byte[] pbCompressed)
    {
        int value = 0;
        int length = 0;

        int[] buffer = new int[pbCompressed.length];
        for (int i = 0; i < pbCompressed.length; i++)
        {
            value += pbCompressed[i];
            if ((pbCompressed[i] != -128) && (pbCompressed[i] != +127))
            {
                buffer[length] = -value;
                length++;
            }
        }
        return HealthGeniusUtil.subArray(buffer, 0, length);
    }
	
	private void closeConnection() {
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
//        HealthGenius.myBluetoothAdapter.disable();
//        try {
//			Thread.sleep(5000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//        HealthGenius.myBluetoothAdapter.enable();
//        try {
//			Thread.sleep(5000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
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
				HealthGenius.myUIManager.UImisc.loadInfoPopupWithoutExiting(HealthGenius.myLanguageManager.SENSORS_BLUETOOTH_CONNECTING);
				break;
			case 2:
				animation.stop();	
				HealthGenius.myUIManager.loadScreen(R.layout.waiting);
				TextView start = (TextView) HealthGenius.myApp.findViewById(R.id.startText);
				TextView text = (TextView) HealthGenius.myApp.findViewById(R.id.infoText);
				start.setText("Espirometria");
				text.setText(HealthGenius.myLanguageManager.SENSORS_BLUETOOTH_CONFIG);
				animationFrame = (ImageView) HealthGenius.myApp.findViewById(R.id.progress);
				animationFrame.setVisibility(View.VISIBLE);
				animationFrame.setBackgroundResource(R.drawable.loadingbig);
				animation = (AnimationDrawable) animationFrame.getBackground();	
				animation.start();
				ImageButton stop = (ImageButton) HealthGenius.myApp.findViewById(R.id.stop);
				stop.setOnClickListener(new OnClickListener() {	
					@Override
					public void onClick(View v) {
						spirometer.finishMeasurements(false, null);
	    				try {
							out.write(RESET);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						closeConnection();
					}
				});
				break;
			case 3:
				TextView text2 = (TextView) HealthGenius.myApp.findViewById(R.id.infoText);
				text2.setText(HealthGenius.myLanguageManager.SENSORS_READING);
				break;
			case 4:
				animation.stop();
				spirometer.finishMeasurements(true, spirometer.results); 
				break;
			case 5:
				animation.stop();
				HealthGenius.myUIManager.UImisc.loadInfoPopupLong(HealthGenius.myLanguageManager.SENSORS_NOBLUETOOTH);
				break;
			case 6:
				HealthGenius.myUIManager.UImisc.loadInfoPopupWithoutExiting(HealthGenius.myLanguageManager.SENSORS_BLUETOOTH_NOTCONNECTED);
				break;
			case 7:
				if ((HealthGenius.mySensorManager.eventAssociated != null)) 
					HealthGenius.myUIManager.UIcalendar.loadScheduleDay(HealthGenius.mySensorManager.eventAssociated.getDate()); 
				else
					HealthGenius.myUIManager.UImeas.loadSensorList();
				if (spirometer.bluetoothPreviouslyConnected) HealthGenius.mySensorManager.reinitBluetooth();
				else HealthGenius.myBluetoothAdapter.disable();
				HealthGenius.myUIManager.UImisc.loadInfoPopupLong(HealthGenius.myLanguageManager.SENSORS_BLUETOOTH_NOTCONFIG);
				break;
			case 8:
				if ((HealthGenius.mySensorManager.eventAssociated != null)) 
					HealthGenius.myUIManager.UIcalendar.loadScheduleDay(HealthGenius.mySensorManager.eventAssociated.getDate()); 
				else
					HealthGenius.myUIManager.UImeas.loadSensorList();
				if (spirometer.bluetoothPreviouslyConnected) HealthGenius.mySensorManager.reinitBluetooth();
				else HealthGenius.myBluetoothAdapter.disable();
				HealthGenius.myUIManager.UImisc.loadInfoPopupLong(HealthGenius.myLanguageManager.SENSORS_BLUETOOTH_NOTCONNECTION);
				break;
			case 31:
				HealthGenius.myUIManager.UImisc.loadInfoPopupWithoutExiting(HealthGenius.myLanguageManager.SENSORS_CONFIG);
				break;
			}
		}
	};

}
