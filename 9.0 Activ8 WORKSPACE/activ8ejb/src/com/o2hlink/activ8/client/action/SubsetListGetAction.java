/**
 * 
 */
package com.o2hlink.activ8.client.action;

import com.o2hlink.activ8.client.entity.Dataset;
import com.o2hlink.activ8.client.response.SubsetListResponse;

/**
 * Get all subsets found in a dataset.
 * @author Miguel Angel Hernandez
 **/
public class SubsetListGetAction implements Action<SubsetListResponse> {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = -4269725441469056515L;
	/**
	 * 
	 **/
	private Dataset dataset;
//CONSTRUCTOR
	/**
	 * Empty constructor to allow serialization
	 **/
	public SubsetListGetAction(){
		
	}
	/**
	 * 
	 **/
	public SubsetListGetAction(Dataset dataset){
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
