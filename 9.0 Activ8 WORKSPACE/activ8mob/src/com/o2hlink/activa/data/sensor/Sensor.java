package com.o2hlink.activa.data.sensor;

import java.util.Hashtable;

/**
 * @author Adrian Rejas<P>
 * 
 * This interface defines a set of functions to be implemented by each sensor class.
 * 
 */
public abstract class Sensor {
	
	public String name;

	public int icon;
	
	public int id;
	
	public Thread thread;
	
	public Hashtable<Integer, Float> results;
	
	public boolean bluetoothPreviouslyConnected;
	
	public abstract void startMeasurement();
	
	public abstract void finishMeasurements(boolean outcome, Hashtable<Integer, Float> results);

	public abstract String passSensorResultToXML();
	
	public abstract String getSensorGlobalResult();
	
}
