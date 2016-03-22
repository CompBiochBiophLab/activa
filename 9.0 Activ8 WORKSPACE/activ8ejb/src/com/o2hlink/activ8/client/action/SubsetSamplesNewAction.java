/**
 * 
 */
package com.o2hlink.activ8.client.action;

import java.util.List;

import com.o2hlink.activ8.client.entity.Sample;
import com.o2hlink.activ8.client.entity.Subset;
import com.o2hlink.activ8.client.response.VoidResponse;

/**
 * Given a Dataset (a group of samples, subsets and patients), add a list of subsets
 * @author Miguel Angel Hernandez
 **/
public class SubsetSamplesNewAction implements Action<VoidResponse> {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = -8542866453178611557L;
	/**
	 * 
	 **/
	private Subset subset;
	/**
	 * 
	 **/
	private List<Sample> samples;
//CONSTRUCTOR
	/**
	 * Empty constructor to allow serialization
	 **/
	public SubsetSamplesNewAction(){
		
	}
	/**
	 * 
	 **/
	public SubsetSamplesNewAction(Subset subset, List<Sample> samples){
		this.setSubset(subset);
		this.setSamples(samples);
	}
//METHODS
	/**
	 * @param subset the subset to set
	 */
	public void setSubset(Subset subset) {
		this.subset = subset;
	}
	/**
	 * @return the subset
	 */
	public Subset getSubset() {
		return subset;
	}
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
