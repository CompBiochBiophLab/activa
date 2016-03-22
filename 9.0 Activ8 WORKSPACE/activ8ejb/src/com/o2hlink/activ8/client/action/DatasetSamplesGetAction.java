/**
 * 
 */
package com.o2hlink.activ8.client.action;

import com.o2hlink.activ8.client.entity.Dataset;
import com.o2hlink.activ8.client.response.SampleResponse;

/**
 * Get all samples linked to a particular dataset.
 * @author Miguel Angel Hernandez
 **/
public class DatasetSamplesGetAction implements Action<SampleResponse> {
//FIELDS
	/**
	 * 
	 **/
	private static final long serialVersionUID = -8535869037182087442L;
	/**
	 * 
	 **/
	private Dataset dataset;
//CONSTRUCTOR
	/**
	 * Empty constructor to allow serialization
	 **/
	public DatasetSamplesGetAction(){
		
	}
	/**
	 * 
	 **/
	public DatasetSamplesGetAction(Dataset dataset){
		this.setDataset(dataset);
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
}
