/**
 * 
 */
package com.o2hlink.activ8.client.response;

import java.util.List;

import com.o2hlink.activ8.client.entity.Sample;

/**
 * Returns a list of samples
 * @author Miguel Angel Hernandez
 **/
public class SampleListResponse implements Response {
//FIELDS
	/**
	 * 
	 **/
	private static final long serialVersionUID = -126743288429072194L;
	/**
	 * 
	 **/
	private List<Sample> samples;
//CONSTRUCTOR
	/**
	 * Empty constructor to allow serialization
	 **/
	public SampleListResponse(){
		
	}
	/**
	 * 
	 **/
	public SampleListResponse(List<Sample> samples){
		this.setSamples(samples);
	}
//METHODS
	/**
	 * @param samples the samples to set
	 */
	public void setSamples(List<Sample> samples) {
		this.samples = samples;
	}
	/**
	 * @return the samples
	 */
	public List<Sample> getSamples() {
		return samples;
	}
}
