/**
 * 
 */
package com.o2hlink.activ8.client.response;

import com.o2hlink.activ8.client.entity.Sample;

/**
 * Returns a single sample
 * @author Miguel Angel Hernandez
 **/
public class SampleResponse implements Response {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = -3820866878535704435L;
	/**
	 * 
	 **/
	private Sample sample;
//CONSTRUCTOR
	/**
	 * Empty constructor to allow serialization
	 **/
	public SampleResponse(){
		
	}
	/**
	 * 
	 **/
	public SampleResponse(Sample sample){
		this.setSample(sample);
	}
//METHODS
	/**
	 * @param sample the sample to set
	 */
	public void setSample(Sample sample) {
		this.sample = sample;
	}
	/**
	 * @return the sample
	 */
	public Sample getSample() {
		return sample;
	}
}
