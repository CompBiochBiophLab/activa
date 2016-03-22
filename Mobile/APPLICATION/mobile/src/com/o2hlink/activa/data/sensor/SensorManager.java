package com.o2hlink.activa.data.sensor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import android.os.CountDownTimer;

import com.o2hlink.activ8.client.entity.Sample;
import com.o2hlink.activa.Activa;
import com.o2hlink.activa.ActivaConfig;
import com.o2hlink.activa.data.calendar.Event;
import com.o2hlink.activa.exceptions.NotUpdatedException;

/**
 * @author Adrian Rejas<P>
 *
 * This class will deal with the management of the different sensors to be executed.
 */
public class SensorManager {

	/**
	 * Static constants for sensor IDs.
	 */
	public static final long ID_PULSIOXYMETER = 5;
	public static final long ID_SIXMINUTES = 6;
	public static final long ID_EXERCISE = 7;
	public static final long ID_SPIROMETER = 2;
	public static final long ID_WEIGHT = 1;
	public static final long ID_ZEPHYR = 4;
	public static final long ID_BLOODPRESS = 8;
	public static final long ID_WEIGHTSCALE = 9;
	public static final long ID_STEPCOUNTER = 10;
	
	/**
	 * Static constants for Data IDs
	 */
	public static final int DATAID_FEV1 = 0;
	public static final int DATAID_FVC = 1;
	public static final int DATAID_PEF = 2;
	public static final int DATAID_IC = 3;
	public static final int DATAID_HR = 4;
	public static final int DATAID_BR = 5;
	public static final int DATAID_TEMP = 6;
	public static final int DATAID_SO2 = 7;
	public static final int DATAID_HR_AVRG = 8;
	public static final int DATAID_HR_PEAK = 9;
	public static final int DATAID_HR_LOW = 10;
	public static final int DATAID_SO2_AVRG = 11;
	public static final int DATAID_SO2_PEAK = 12;
	public static final int DATAID_SO2_LOW = 13;
	public static final int DATAID_BORG_AIR_PRE = 14;
	public static final int DATAID_BORG_AIR_POST = 15;
	public static final int DATAID_BORG_FATIGUE_PRE = 17;
	public static final int DATAID_BORG_FATIGUE_POST = 18;
	public static final int DATAID_TIME_EXE = 16;
	public static final int DATAID_CHALLENGE_CHOSEN = 19;
	public static final int DATAID_SYSPRESS = 20;
	public static final int DATAID_DIAPRESS = 21;
	public static final int DATAID_WEIGHT = 22;
	public static final int DATAID_STEPS = 23;
	public static final int DATAID_STOPS = 24;
	public static final int DATAID_DISTANCE = 25;
	public static final int DATAID_FEV1_TEO = 26;
	public static final int DATAID_FVC_TEO = 27;
	public static final int DATAID_PEF_TEO = 28;
	public static final int DATAID_EXE_ONE = 29;
	public static final int DATAID_EXE_TWO = 30;
	public static final int DATAID_EXE_THREE = 31;
	public static final int DATAID_HEIGHT = 32;
	public static final int DATAID_HR_INIT = 33;
	public static final int DATAID_HR_FINAL = 34;
	public static final int DATAID_SO2_INIT = 35;
	public static final int DATAID_SO2_FINAL = 36;
	public static final int DATAID_FLOWCHART = 101;
	public static final int DATAID_TIMECHART = 102;
	
	/**
	 * The following ones are DATA IDs which are not going to be sent, being bigger than 1000
	 */
	public static final int DATAID_TIME_DATA = 1001;
	
	/**
	 * Static constants for Unit IDs
	 */
	public static final int UNIT_NULL = -1;
	public static final int UNIT_LITER = 0;
	public static final int UNIT_BPM = 1;
	public static final int UNIT_RBPM = 2;	
	public static final int UNIT_PERCENT = 3;
	public static final int UNIT_CELSIUS = 4;
	public static final int UNIT_LITER_PER_SEC = 5;
	public static final int UNIT_SEC = 6;
	public static final int UNIT_MMHG = 7;
	public static final int UNIT_KG = 8;
	public static final int UNIT_METERS = 9;
	public static final int UNIT_CENTIMETERS = 10;
	
	/**
	 * The instance of the class (it's a singleton).
	 */
	private static SensorManager instance;
	
	/**
	 * The event associated with the actual sensor measurement, if applicable. 
	 */
	public Event eventAssociated;
	
	/**
	 * The list of sensors to be used at the application.
	 */
	public Hashtable<Long, Sensor> sensorList;
	
	/**
	 * The list of sensors measurements count.
	 */
	public Hashtable<Integer, Integer> sensorCountList;
	
	/**
	 * The list of sensors last measurements.
	 */
	public Hashtable<Integer, Float> sensorLastList;
	
	/**
	 * The list of sensors measurements maximun.
	 */
	public Hashtable<Integer, Float> sensorMaxList;
	
	public boolean reiniting;
	
	/**
	 * The private constructor.
	 */
	private SensorManager() {
		this.reiniting = false;
		initSensorList();
		initSensorCountList();
		initSensorLastList();
		initSensorMaxList();
	}
	
	/**
	 * The method which initiates the list of sensors in a static way.
	 */
	private void initSensorList() {
		sensorList = new Hashtable<Long, Sensor>();
		sensorList.put(SensorManager.ID_WEIGHT, new WeightAndHeight());
		sensorList.put(SensorManager.ID_SPIROMETER, new Spirometer());
		sensorList.put(SensorManager.ID_PULSIOXYMETER, new PulseOximeter());
		sensorList.put(SensorManager.ID_SIXMINUTES, new SixMinutes());
		sensorList.put(SensorManager.ID_EXERCISE, new Exercise());
//		sensorList.put(SensorManager.ID_BLOODPRESS, new BloodPressure());
//		sensorList.put(SensorManager.ID_WEIGHTSCALE, new WeightScale());
//		sensorList.put(SensorManager.ID_STEPCOUNTER, new StepCounter());
//		sensorList.put(SensorManager.ID_ZEPHYR, new Zephyr());
	}
	
	private void initSensorCountList() {
		sensorCountList = new Hashtable<Integer, Integer>();
		sensorCountList.put(SensorManager.DATAID_FEV1, 0);
		sensorCountList.put(SensorManager.DATAID_BR, 0);
		sensorCountList.put(SensorManager.DATAID_FVC, 0);
		sensorCountList.put(SensorManager.DATAID_HR, 0);
		sensorCountList.put(SensorManager.DATAID_IC, 0);
		sensorCountList.put(SensorManager.DATAID_PEF, 0);
		sensorCountList.put(SensorManager.DATAID_SO2, 0);
		sensorCountList.put(SensorManager.DATAID_TEMP, 0);
	}

	private void initSensorLastList() {
		sensorLastList = new Hashtable<Integer, Float>();
		sensorLastList.put(SensorManager.DATAID_FEV1, 0.0f);
		sensorLastList.put(SensorManager.DATAID_BR, 0.0f);
		sensorLastList.put(SensorManager.DATAID_FVC, 0.0f);
		sensorLastList.put(SensorManager.DATAID_HR, 0.0f);
		sensorLastList.put(SensorManager.DATAID_IC, 0.0f);
		sensorLastList.put(SensorManager.DATAID_PEF, 0.0f);
		sensorLastList.put(SensorManager.DATAID_SO2, 0.0f);
		sensorLastList.put(SensorManager.DATAID_TEMP, 0.0f);
	}

	private void initSensorMaxList() {
		sensorMaxList = new Hashtable<Integer, Float>();
		sensorMaxList.put(SensorManager.DATAID_FEV1, 0.0f);
		sensorMaxList.put(SensorManager.DATAID_BR, 0.0f);
		sensorMaxList.put(SensorManager.DATAID_FVC, 0.0f);
		sensorMaxList.put(SensorManager.DATAID_HR, 0.0f);
		sensorMaxList.put(SensorManager.DATAID_IC, 0.0f);
		sensorMaxList.put(SensorManager.DATAID_PEF, 0.0f);
		sensorMaxList.put(SensorManager.DATAID_SO2, 0.0f);
		sensorMaxList.put(SensorManager.DATAID_TEMP, 0.0f);
	}
	
	/**
	 * A method for getting the instance of the class if existing or creating a new one.
	 */
	public static SensorManager getInstance() {
		if (SensorManager.instance == null) SensorManager.instance = new SensorManager();
		return SensorManager.instance;
	}
	
	/**
	 * Method for freeing the instance of the manager.
	 */
	public static void freeInstance() {
		SensorManager.instance = null;
	}
	
	/**
	 * A method for starting the sensor measurement.
	 * 
	 * @param sensorId The ID of the sensor to be measured.
	 */
	public void startSensorMeasurement(long sensorId){
		this.sensorList.get(sensorId).startMeasurement();
	}
	
	public void reinitBluetooth() {
		this.reiniting = true;
		Activa.myBluetoothAdapter.disable();
		CountDownTimer timer = new CountDownTimer(10000,1000) {
			@Override
			public void onTick(long millisUntilFinished) {
			}
			@Override
			public void onFinish() {
				Activa.myBluetoothAdapter.enable();
				Activa.mySensorManager.reiniting = false;
			}
		};
		timer.start();
	}
	
	/**
	 * Get the ID of a concrete measurement.
	 * 
	 * @param meas The measurement expressed at String
	 * @return The measurement expressed at int
	 */
	/*public static int getIDForMeasurement(String meas) {
		if (meas.equalsIgnoreCase("FEV1")) return DATAID_FEV1;
		else if (meas.equalsIgnoreCase("FVC")) return DATAID_FVC; 
		else if (meas.equalsIgnoreCase("PEF")) return DATAID_PEF; 
		else if (meas.equalsIgnoreCase("IC")) return DATAID_IC; 
		else if (meas.equalsIgnoreCase("SO2")) return DATAID_SO2; 
		else if (meas.equalsIgnoreCase("Heart rate")) return DATAID_HR; 
		else if (meas.equalsIgnoreCase("Breathing rate")) return DATAID_BR; 
		else if (meas.equalsIgnoreCase("Temperature")) return DATAID_TEMP; 
		else return -1;
	}*/
	
	/**
	 * Get the ID of a unit to use for a measurement
	 * 
	 * @param measID The measurement ID
	 * @return The unit ID
	 */
	public static int getUnitIDForMeasurementID(int measID) {
		switch (measID) {
			case DATAID_FEV1:
			case DATAID_FVC:
			case DATAID_FEV1_TEO:
			case DATAID_FVC_TEO:
			case DATAID_IC:
				return UNIT_LITER;
			case DATAID_HR:
			case DATAID_HR_AVRG:
			case DATAID_HR_LOW:
			case DATAID_HR_PEAK:
			case DATAID_HR_INIT:
			case DATAID_HR_FINAL:
				return UNIT_BPM;
			case DATAID_BR:
				return UNIT_RBPM;
			case DATAID_SO2:
			case DATAID_SO2_AVRG:
			case DATAID_SO2_LOW:
			case DATAID_SO2_PEAK:
			case DATAID_SO2_INIT:
			case DATAID_SO2_FINAL:
				return UNIT_PERCENT;
			case DATAID_TEMP:
				return UNIT_CELSIUS;
			case DATAID_PEF:
			case DATAID_PEF_TEO:
				return UNIT_LITER_PER_SEC;
			case DATAID_TIME_EXE:
			case DATAID_TIME_DATA:
				return UNIT_SEC;
			case DATAID_SYSPRESS:
			case DATAID_DIAPRESS:
				return UNIT_MMHG;
			case DATAID_WEIGHT:
				return UNIT_KG;
			case DATAID_STEPS:
			case DATAID_STOPS:
				return UNIT_NULL;
			case DATAID_DISTANCE:
				return UNIT_METERS;
			case DATAID_HEIGHT:
				return UNIT_CENTIMETERS;
			default:
				return -1;
		}
	}
	
	/**
	 * Get the Name of the measurement
	 * 
	 * @param measID The measurement ID
	 * @return The measurement name
	 */
	public static String getMeasurementName(int measID) {
		switch (measID) {
			case DATAID_FEV1:
				return Activa.myLanguageManager.SENSORS_DATA_FEV1;
			case DATAID_FVC:
				return Activa.myLanguageManager.SENSORS_DATA_FVC;
			case DATAID_FEV1_TEO:
				return Activa.myLanguageManager.SENSORS_DATA_FEV1 + "(T)";
			case DATAID_FVC_TEO:
				return Activa.myLanguageManager.SENSORS_DATA_FVC + "(T)";
			case DATAID_IC:
				return Activa.myLanguageManager.SENSORS_DATA_IC;
			case DATAID_HR:
				return Activa.myLanguageManager.SENSORS_DATA_HR;
			case DATAID_HR_AVRG:
				return Activa.myLanguageManager.SENSORS_DATA_HR_AVRG;
			case DATAID_HR_LOW:
				return Activa.myLanguageManager.SENSORS_DATA_HR_LOW;
			case DATAID_HR_PEAK:
				return Activa.myLanguageManager.SENSORS_DATA_HR_PEAK;
			case DATAID_HR_INIT:
				return Activa.myLanguageManager.SENSORS_DATA_HR_INIT;
			case DATAID_HR_FINAL:
				return Activa.myLanguageManager.SENSORS_DATA_HR_FINAL;
			case DATAID_BR:
				return Activa.myLanguageManager.SENSORS_DATA_BR;
			case DATAID_SO2:
				return Activa.myLanguageManager.SENSORS_DATA_SO2;
			case DATAID_SO2_AVRG:
				return Activa.myLanguageManager.SENSORS_DATA_SO2_AVRG;
			case DATAID_SO2_PEAK:
				return Activa.myLanguageManager.SENSORS_DATA_SO2_PEAK;
			case DATAID_SO2_LOW:
				return Activa.myLanguageManager.SENSORS_DATA_SO2_LOW;
			case DATAID_SO2_INIT:
				return Activa.myLanguageManager.SENSORS_DATA_SO2_INIT;
			case DATAID_SO2_FINAL:
				return Activa.myLanguageManager.SENSORS_DATA_SO2_FINAL;
			case DATAID_HEIGHT:
				return Activa.myLanguageManager.SENSORS_DATA_SO2_FINAL;
			case DATAID_TEMP:
				return Activa.myLanguageManager.SENSORS_DATA_TEMP;
			case DATAID_PEF:
				return Activa.myLanguageManager.SENSORS_DATA_PEF;
			case DATAID_PEF_TEO:
				return Activa.myLanguageManager.SENSORS_DATA_PEF;
			case DATAID_TIME_DATA:
				return Activa.myLanguageManager.SENSORS_DATA_TIME_DATA;
			case DATAID_TIME_EXE:
				return Activa.myLanguageManager.SENSORS_DATA_TIME_EXE;
			case DATAID_SYSPRESS:
				return Activa.myLanguageManager.SENSORS_DATA_SYS_PRESS;
			case DATAID_DIAPRESS:
				return Activa.myLanguageManager.SENSORS_DATA_DIA_PRESS;
			case DATAID_WEIGHT:
				return Activa.myLanguageManager.SENSORS_DATA_WEIGHT;
			case DATAID_STEPS:
				return Activa.myLanguageManager.SENSORS_DATA_STEPS;
			case DATAID_STOPS:
				return Activa.myLanguageManager.SENSORS_DATA_STOPS;
			case DATAID_DISTANCE:
				return Activa.myLanguageManager.SENSORS_DATA_DISTANCE;
			default:
				return null;
		}
	}
	
	/**
	 * Get the Unit string from its ID
	 * 
	 * @param unitID The unit ID
	 * @return The Unit String
	 */
	public static String getUnitForMeasurement(int unitID) {
		switch (unitID) {
			case UNIT_LITER:
				return "l";
			case UNIT_BPM:
				return "bpm";
			case UNIT_RBPM:
				return "rbpm";
			case UNIT_PERCENT:
				return "%";
			case UNIT_CELSIUS:
				return "C";
			case UNIT_LITER_PER_SEC:
				return "l/sec";
			case UNIT_SEC:
				return "sec";
			case UNIT_MMHG:
				return "mmHg";
			case UNIT_KG:
				return "kg";
			case UNIT_METERS:
				return "m";
			case UNIT_CENTIMETERS:
				return "cm";
			default:
				return "";
		}
	}
	
	/**
	 * Extract the values of the previous obtained values for sensor measurements
	 * 
	 * @param xml
	 */
	public boolean extractSensorDBFromXML(String xml) {
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
	                if (info.getName().equalsIgnoreCase("DATA")) {
	                	int id = Integer.parseInt(info.getAttributeValue(0));
	                	int count = Integer.parseInt(info.getAttributeValue(1));
	                	Float last = Float.parseFloat(info.getAttributeValue(2));
	                	Float max = Float.parseFloat(info.getAttributeValue(3));
	                	this.sensorCountList.put(id, count);
	                	this.sensorLastList.put(id, last);
	                	this.sensorMaxList.put(id, max);
	                }
	            } else if(event == XmlPullParser.END_TAG) {
	                if (info.getName().equalsIgnoreCase("SENSORDB")) {
	                	return true;
	                }
	            }
	            event = info.next();
	        }
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public void getSensorDBFromFile() {
		try {
			File folder = Activa.myApp.getDir(ActivaConfig.FILES_FOLDER, 0);
			String filename = "activasensordb" + Activa.myMobileManager.user.getName() + ".xml";
			String xmlsensor = "";
			File pendingData = new File(folder, filename);
			if (!pendingData.exists()) {
				pendingData.createNewFile();
			}
			else {
				FileInputStream fin = new FileInputStream(pendingData);
				InputStreamReader isr = new InputStreamReader(fin,"UTF-8");          
		        char[] inputBuffer = new char[255]; 
		        while ( isr.read(inputBuffer) != -1) {
		        	xmlsensor += new String(inputBuffer);    
		        }
		        extractSensorDBFromXML(xmlsensor);
		        isr.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String passSensorDBToXML() {
		String returned = "";
		returned += "<SENSORDB>\n";
		Enumeration<Integer> enumerator = this.sensorLastList.keys();
		while (enumerator.hasMoreElements()) {
			int key = enumerator.nextElement();
			returned += "\t<DATA ID=\"" + key + "\" ";
			returned += "COUNT=\"" + this.sensorCountList.get(key) + "\" ";
			returned += "LAST=\"" + this.sensorLastList.get(key) + "\" ";
			returned += "MAX=\"" + this.sensorMaxList.get(key) + "\">\n";
		}
		returned += "</SENSORDB>";
		return returned;
	}
	
	public void passSensorDBToFile() {
		File folder = Activa.myApp.getDir(ActivaConfig.FILES_FOLDER, 0);
		String filename = "activasensordb" + Activa.myMobileManager.user.getName() + ".xml";
		FileOutputStream fout;
		try {
			fout = new FileOutputStream(new File(folder, filename));
			OutputStreamWriter osw = new OutputStreamWriter(fout);
			osw.write(passSensorDBToXML());
			osw.close();
			fout.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean sendSensorMeasurement(Sensor sensor) {
		boolean result;
		try {
			result = Activa.myProtocolManager.sendSensorMeasurement(sensor);
		} catch (NotUpdatedException e) {
			Activa.myUIManager.loadTextOnWindow(Activa.myLanguageManager.TEXT_UPDATEVERSION);
			e.printStackTrace();
			result = false;
		}
		return result;
	}

	public boolean getMeasurementSample(Event event) {
		boolean result;
		try {
			result = Activa.myProtocolManager.getMeasurementSample(event);
		} catch (NotUpdatedException e) {
			Activa.myUIManager.loadTextOnWindow(Activa.myLanguageManager.TEXT_UPDATEVERSION);
			e.printStackTrace();
			result = false;
		}
		return result;
	}

	public boolean getSpiroGraphs(Date date, Sample sample) {
		boolean result;
		try {
			result = Activa.myProtocolManager.getSpiroGraphs(date, sample);
		} catch (NotUpdatedException e) {
			Activa.myUIManager.loadTextOnWindow(Activa.myLanguageManager.TEXT_UPDATEVERSION);
			e.printStackTrace();
			result = false;
		}
		return result;
	}

	public boolean getSixMinutesGraphs(Date date, Sample sample) {
		boolean result;
		try {
			result = Activa.myProtocolManager.getSixMinutesGraphs(date, sample);
		} catch (NotUpdatedException e) {
			Activa.myUIManager.loadTextOnWindow(Activa.myLanguageManager.TEXT_UPDATEVERSION);
			e.printStackTrace();
			result = false;
		}
		return result;
	}
	
}
