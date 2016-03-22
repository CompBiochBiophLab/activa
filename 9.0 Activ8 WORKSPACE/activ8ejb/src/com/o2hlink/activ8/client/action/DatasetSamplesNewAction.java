/**
 * 
 */
package com.o2hlink.activ8.client.action;

import java.util.List;

import com.o2hlink.activ8.client.entity.Dataset;
import com.o2hlink.activ8.client.entity.Sample;
import com.o2hlink.activ8.client.response.VoidResponse;

/**
 * Given a Dataset (a group of samples, subsets and patients), add a list of samples
 * @author Miguel Angel Hernandez
 **/
public class DatasetSamplesNewAction implements Action<VoidResponse> {
//FIELDS
	/**
	 * 
	 **/
	private static final long serialVersionUID = -3517058447267004553L;
	/**
	 * 
	 **/
	private Dataset dataset;
	/**
	 * 
	 **/
	private List<Sample> samples;
//CONSTRUCTOR
	/**
	 * Empty constructor to allow serialization
	 **/
	public DatasetSamplesNewAction(){
		
	}
	/**
	 * 
	 **/
	public DatasetSamplesNewAction(Dataset dataset, List<Sample> samples){
		this.setDataset(dataset);
		this.setSamples(samples);
	}
//METHODS
	/**
	 * @param dataset the dataset to set
	 */
	public void setDataset(Dataset dataset) {
		this.dataset = dataset;
	}
	/**
	 * @return the dataset
	 */
	public Dataset getDataset() {
		return dataset;
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
