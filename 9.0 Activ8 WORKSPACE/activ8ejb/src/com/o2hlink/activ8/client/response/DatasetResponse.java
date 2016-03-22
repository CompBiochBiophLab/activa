/**
 * 
 */
package com.o2hlink.activ8.client.response;

import com.o2hlink.activ8.client.entity.Dataset;

/**
 * Return a single dataset
 * @author Miguel Angel Hernandez
 **/
public class DatasetResponse implements Response {
//FIELDS
	/**
	 * 
	 **/
	private static final long serialVersionUID = -7070361497638728248L;
	/**
	 * 
	 **/
	private Dataset dataset;
//CONSTRUCTOR
	/**
	 * Empty constructor to allow serialization
	 **/
	public DatasetResponse(){
		
	}
	/**
	 * 
	 **/
	public DatasetResponse(Dataset dataset){
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
