/**
 * 
 */
package com.o2hlink.activ8.server.local;

import java.util.List;

import javax.ejb.Local;

import com.o2hlink.activ8.client.entity.Measurement;
import com.o2hlink.activ8.client.entity.Patient;
import com.o2hlink.activ8.client.entity.Sample;


/**
 * Remote operations on a sample
 * @author Miguel Angel Hernandez
 **/
@Local
public interface SampleLocal {
	/**
	 * @param	sample The sample to store
	 * @param	measurement The measurement to which this sample corresponds
	 * @param	patient The patient to which these samples corresponds
	 * @return	The sample stored
	 **/
	public Sample save(Sample sample, Measurement measurement, Patient patient);
	/**
	 * @param	samples A list of samples to store
	 * @param	measurement The measurement to which this sample corresponds
	 * @param	patient The patient to which these samples corresponds
	 * @return	The samples stored
	 **/
	public List<Sample> save(List<Sample> samples, Measurement measurement, Patient patient);
}
