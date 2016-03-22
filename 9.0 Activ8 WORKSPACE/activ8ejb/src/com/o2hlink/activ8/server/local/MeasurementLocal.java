package com.o2hlink.activ8.server.local;

import java.util.List;

import javax.ejb.Local;

import com.o2hlink.activ8.client.entity.Measurement;

/**
 * @author Miguel Angel Hernandez
 **/
@Local
public interface MeasurementLocal {
//METHODS
	/**
	 * 
	 **/
	public Measurement get(long id);
	/**
	 * @return	The total list of measurements
	 **/
	public List<Measurement> getMeasurements();
	/**
	 * 
	 **/
	public Measurement save(Measurement measurement);
}
