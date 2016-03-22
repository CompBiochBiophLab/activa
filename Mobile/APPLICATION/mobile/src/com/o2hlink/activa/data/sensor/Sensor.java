package com.o2hlink.activa.data.sensor;

import java.util.Date;
import java.util.Hashtable;

import com.o2hlink.activ8.client.entity.Sample;

/**
 * @author Adrian Rejas<P>
 * 
 * This interface defines a set of functions to be implemented by each sensor class.
 * 
 */
public abstract class Sensor {
	
	public String name;

	public int icon;
	
	public long id;
	
	public Date sampleDate;
	
	public String sampleEventId;
	
	public Thread thread;
	
	public Hashtable<Integer, Float> results;
	
	public boolean bluetoothPreviouslyConnected;
	
	public abstract void startMeasurement();
	
	public abstract void finishMeasurements(boolean outcome, Hashtable<Integer, Float> results);

	public abstract String passSensorResultToXML();
	
	public abstract String getSensorGlobalResult();
	
	public abstract Sample getSample();
	
	public abstract String getSensorSampleForPending();
	
}
