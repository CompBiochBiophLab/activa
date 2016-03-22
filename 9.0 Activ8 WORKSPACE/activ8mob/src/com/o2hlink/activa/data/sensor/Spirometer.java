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

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.AnimationDrawable;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.o2hlink.activa.Activa;
import com.o2hlink.activa.ActivaConfig;
import com.o2hlink.activa.ActivaUtil;
import com.o2hlink.activa.R;
import com.o2hlink.activa.background.MainThread;
import com.o2hlink.activa.background.SendSensorResult;
import com.o2hlink.activa.data.PendingDataManager;

public class Spirometer extends Sensor {
	
	public int [] flowLine;
	
	public int [] timeLine;
	
	public Spirometer() {
		this.name = Activa.myLanguageManager.SENSORS_SPIRO_TITLE;
		this.icon = R.drawable.spirometry;
		this.id = SensorManager.ID_SPIROMETER;
	}
	
	@Override
	public void finishMeasurements(boolean outcome, Hashtable<Integer, Float> results) {
		if (this.bluetoothPreviouslyConnected) Activa.mySensorManager.reinitBluetooth();
		else Activa.myBluetoothAdapter.disable();
		this.results = results;
		if (outcome) {
			float fvc = this.results.get(SensorManager.DATAID_FVC);
			float fev1 = this.results.get(SensorManager.DATAID_FEV1);
			float pef = this.results.get(SensorManager.DATAID_PEF);
			float lastfvc = Activa.mySensorManager.sensorLastList.get(SensorManager.DATAID_FVC);
			float lastfev1 = Activa.mySensorManager.sensorLastList.get(SensorManager.DATAID_FEV1);
			float lastpef = Activa.mySensorManager.sensorLastList.get(SensorManager.DATAID_PEF);
			if ((lastfvc==fvc)&&(lastfev1==fev1)&&(lastpef==pef)) {
				if ((Activa.mySensorManager.eventAssociated != null)||(Activa.myProgramManager.eventAssociated != null)) 
					Activa.myUIManager.loadScheduleDay(Activa.mySensorManager.eventAssociated.getDate()); 
				else if (Activa.mySensorManager.programassociated != null) 
					Activa.myUIManager.loadProgramList();
				else
					Activa.myUIManager.loadSensorList();
				Activa.myUIManager.loadInfoPopupLong(Activa.myLanguageManager.SENSORS_SPIRO_DATAREPEATED);
				return;
			}
			if (Activa.mySensorManager.eventAssociated != null) {
				Activa.mySensorManager.eventAssociated.state = 0;
				Activa.myCalendarManager.saveCalendar();
			}
		}
		Activa.myUIManager.finishSpirometry(this.name, outcome, this);
	}

	@Override
	public void startMeasurement() {
		Date now = new Date();
		if (Activa.myMobileManager.user.getLastupdate().getTime() == 0) {
			Activa.myUIManager.loadRequestDataScreen();
			return;
		}
		else if (((now.getTime() - Activa.myMobileManager.user.getLastupdate().getTime())/86400000l) > ActivaConfig.USERS_UPDATING) {
			Activa.myApp.setContentView(R.layout.yesnoquestion);
			TextView question = (TextView)Activa.myApp.findViewById(R.id.question);
			question.setText(Activa.myLanguageManager.SENSORS_SPIRO_DATAREQUEST);
			ImageButton yes = (ImageButton)Activa.myApp.findViewById(R.id.yes);
			ImageButton no = (ImageButton)Activa.myApp.findViewById(R.id.no);
			yes.setOnClickListener(null);
			yes.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Activa.myUIManager.loadRequestDataScreen();
				}
			});
			no.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Activa.myMobileManager.user.setLastupdate(new Date());
					Activa.myMobileManager.addUserWithPassword(Activa.myMobileManager.user);
					if (Activa.mySensorManager.programassociated != null) {
						Activa.mySensorManager.programassociated.state--;
						Activa.mySensorManager.programassociated.nextStep();
					}
					else if (Activa.mySensorManager.eventAssociated != null) {
						Activa.myUIManager.loadScheduleDay(new Date());
						Activa.mySensorManager.sensorList.get(SensorManager.ID_SPIROMETER).startMeasurement();
					}
					else {
						Activa.myUIManager.loadSensorList();
						Activa.mySensorManager.sensorList.get(SensorManager.ID_SPIROMETER).startMeasurement();
					}
				}
			});
			return;
    	}
		this.results = new Hashtable<Integer, Float>();
		Activa.myBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		StartMeasurementSpirometer st = new StartMeasurementSpirometer(this);
		Thread thr = new Thread(st);
		this.thread = thr;
		thr.start();
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
			returned += "\" UNITS=\"" + SensorManager.getUnitIDForMeasurementID(key) + "\"/>";
		}
		returned += "<LIST ID=\"" + SensorManager.DATAID_FLOWCHART + "\" VALUE=\"" + ActivaUtil.arrayToCsv(this.flowLine);
		returned += "\" UNITS=\"" + SensorManager.UNIT_NULL + "\"/>";
		returned += "<LIST ID=\"" + SensorManager.DATAID_TIMECHART + "\" VALUE=\"" + ActivaUtil.arrayToCsv(this.timeLine);
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
		
		int countfvc = Activa.mySensorManager.sensorCountList.get(SensorManager.DATAID_FVC);
		int countfev1 = Activa.mySensorManager.sensorCountList.get(SensorManager.DATAID_FEV1);
		int countpef = Activa.mySensorManager.sensorCountList.get(SensorManager.DATAID_PEF);
		Activa.mySensorManager.sensorCountList.put(SensorManager.DATAID_FVC, countfvc + 1);
		Activa.mySensorManager.sensorCountList.put(SensorManager.DATAID_FEV1, countfev1 + 1);
		Activa.mySensorManager.sensorCountList.put(SensorManager.DATAID_PEF, countpef + 1);
		
		Activa.mySensorManager.sensorLastList.put(SensorManager.DATAID_FVC, fvc);
		Activa.mySensorManager.sensorLastList.put(SensorManager.DATAID_FEV1, fev1);
		Activa.mySensorManager.sensorLastList.put(SensorManager.DATAID_PEF, pef);
		
		float maxfvc = Activa.mySensorManager.sensorMaxList.get(SensorManager.DATAID_FVC);
		float maxfev1 = Activa.mySensorManager.sensorMaxList.get(SensorManager.DATAID_FVC);
		float maxpef = Activa.mySensorManager.sensorMaxList.get(SensorManager.DATAID_PEF);
		if (fvc > maxfvc) Activa.mySensorManager.sensorMaxList.put(SensorManager.DATAID_FVC, fvc);
		if (fev1 > maxfev1) Activa.mySensorManager.sensorMaxList.put(SensorManager.DATAID_FEV1, fev1);
		if (pef > maxpef) Activa.mySensorManager.sensorMaxList.put(SensorManager.DATAID_PEF, pef);
		
		Activa.mySensorManager.passSensorDBToFile();
		
		Date dateToCompare = new Date();
		int age = (int) ((dateToCompare.getTime() - Activa.myMobileManager.user.getBirthdate().getTime())/31536000000l);
		int height = Activa.myMobileManager.user.getHeight();
		float weight = Activa.myMobileManager.user.getWeight();
		
		if (Activa.myMobileManager.user.isMale()) {
			teoricFVC = (float) ((0.0678*height) - (0.0147*age) - 6.0548);
			teoricFEV1 = (float) ((0.0499*height) - (0.0211*age) - 3.837);
			teoricPEF = (float) ((0.0945*height) - (0.0209*age) - 5.7732);
		}
		else {
			teoricFVC = (float) ((0.0454*height) - (0.0211*age) - 2.8253);
			teoricFEV1 = (float) ((0.0317*height) - (0.025*age) - 1.2324);
			teoricPEF = (float) ((0.0448*height) - (0.0304*age) + 0.3496);
		}
		
		float percentage = ((pef/teoricPEF) + (fvc/teoricFVC) + (fev1/teoricFEV1))/3.0f;
		if (percentage > 0.8) {
			result = "2:" + Activa.myLanguageManager.SENSORS_SPIRO_MESSAGE2;
		}
		else if (percentage > 0.5) {
			result = "1:" + Activa.myLanguageManager.SENSORS_SPIRO_MESSAGE1;
		}
		else {
			result = "0:" + Activa.myLanguageManager.SENSORS_SPIRO_MESSAGE0;
		}
//		if (countpef < 10) {
//			result += " " + Activa.myLanguageManager.SENSORS_DATA_LACKOF;
//		}
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
		String info;
		found = false;
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
			this.spirometer.bluetoothPreviouslyConnected = false;
			handler.sendEmptyMessage(6);
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
			this.spirometer.bluetoothPreviouslyConnected = true;
		}
		Set<BluetoothDevice> pairedDevices = Activa.myBluetoothAdapter.getBondedDevices();
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
//								Activa.throwException(new Exception());
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
	    		        int FVCint = ActivaUtil.wordLEToInt(ActivaUtil.subArray(readableData,112,2),true);
	    		        float FVC = (float) (((float)FVCint)*(-625.0)/(float)1000000.0);
	    		        int FEV1int = ActivaUtil.wordLEToInt(ActivaUtil.subArray(readableData,108,2),true);
	    		        float FEV1 = (float) (((float)FEV1int)*(-625.0)/(float)1000000.0);
	    		        int PEFint = ActivaUtil.wordLEToInt(ActivaUtil.subArray(readableData,114,2),true);
	    		        float PEF = (float) (((float)PEFint)*(-625.0)/(float)1000000.0);
	    		        spirometer.results.put(SensorManager.DATAID_FVC, FVC);
	    		        spirometer.results.put(SensorManager.DATAID_FEV1, FEV1);
	    		        spirometer.results.put(SensorManager.DATAID_PEF, PEF);
	    		        // Get curves length
	    		        int fvLen = ActivaUtil.wordLEToInt(ActivaUtil.subArray(readableData, 148, 2),false);
	    		        int vtLen = ActivaUtil.wordLEToInt(ActivaUtil.subArray(readableData, 150, 2),false);
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
	    		        spirometer.flowLine = deltaDecompress(ActivaUtil.subArray(curveData, 0, fvLen));
	    		        spirometer.timeLine = deltaDecompress(ActivaUtil.subArray(curveData, fvLen, vtLen));
	    		        
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
        return ActivaUtil.subArray(buffer, 0, length);
    }
	
	@SuppressWarnings("unused")
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
//        Activa.myBluetoothAdapter.disable();
//        try {
//			Thread.sleep(5000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//        Activa.myBluetoothAdapter.enable();
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
				animationFrame = (ImageView) Activa.myApp.findViewById(R.id.animation);
				animationFrame.setVisibility(View.VISIBLE);
				animationFrame.setBackgroundResource(R.drawable.loading);
				animation = (AnimationDrawable) animationFrame.getBackground();	
				animation.start();			
				Activa.myUIManager.loadInfoPopupWithoutExiting(Activa.myLanguageManager.SENSORS_SEARCHING);
				break;
			case 1:				
				Activa.myUIManager.loadInfoPopupWithoutExiting(Activa.myLanguageManager.SENSORS_BLUETOOTH_CONNECTING);
				break;
			case 2:
				animation.stop();	
				Activa.myUIManager.loadScreen(R.layout.waiting);
				TextView start = (TextView) Activa.myApp.findViewById(R.id.startText);
				TextView text = (TextView) Activa.myApp.findViewById(R.id.infoText);
				start.setText("Espirometria");
				text.setText(Activa.myLanguageManager.SENSORS_BLUETOOTH_CONFIG);
				animationFrame = (ImageView) Activa.myApp.findViewById(R.id.progress);
				animationFrame.setVisibility(View.VISIBLE);
				animationFrame.setBackgroundResource(R.drawable.loadingbig);
				animation = (AnimationDrawable) animationFrame.getBackground();	
				animation.start();
				ImageButton stop = (ImageButton) Activa.myApp.findViewById(R.id.stop);
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
				TextView text2 = (TextView) Activa.myApp.findViewById(R.id.infoText);
				text2.setText(Activa.myLanguageManager.SENSORS_READING);
				break;
			case 4:
				animation.stop();
				spirometer.finishMeasurements(true, spirometer.results); 
				break;
			case 5:
				animation.stop();
				Activa.myUIManager.loadInfoPopupLong(Activa.myLanguageManager.SENSORS_NOBLUETOOTH);
				break;
			case 6:
				Activa.myUIManager.loadInfoPopupWithoutExiting(Activa.myLanguageManager.SENSORS_BLUETOOTH_NOTCONNECTED);
				break;
			case 7:
				if ((Activa.mySensorManager.eventAssociated != null)) 
					Activa.myUIManager.loadScheduleDay(Activa.mySensorManager.eventAssociated.getDate()); 
				else if (Activa.mySensorManager.programassociated != null) 
					Activa.myUIManager.loadProgramList();
				else
					Activa.myUIManager.loadSensorList();
				if (spirometer.bluetoothPreviouslyConnected) Activa.mySensorManager.reinitBluetooth();
				else Activa.myBluetoothAdapter.disable();
				Activa.myUIManager.loadInfoPopupLong(Activa.myLanguageManager.SENSORS_BLUETOOTH_NOTCONFIG);
				break;
			case 8:
				if ((Activa.mySensorManager.eventAssociated != null)) 
					Activa.myUIManager.loadScheduleDay(Activa.mySensorManager.eventAssociated.getDate()); 
				else if (Activa.mySensorManager.programassociated != null) 
					Activa.myUIManager.loadProgramList();
				else
					Activa.myUIManager.loadSensorList();
				if (spirometer.bluetoothPreviouslyConnected) Activa.mySensorManager.reinitBluetooth();
				else Activa.myBluetoothAdapter.disable();
				Activa.myUIManager.loadInfoPopupLong(Activa.myLanguageManager.SENSORS_BLUETOOTH_NOTCONNECTION);
				break;
			case 31:
				Activa.myUIManager.loadInfoPopupWithoutExiting(Activa.myLanguageManager.SENSORS_CONFIG);
				break;
			}
		}
	};

}
