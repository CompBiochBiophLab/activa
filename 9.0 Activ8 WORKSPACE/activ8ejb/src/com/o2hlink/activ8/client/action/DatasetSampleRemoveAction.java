/**
 * 
 */
package com.o2hlink.activ8.client.action;

import java.io.Serializable;

import com.o2hlink.activ8.client.entity.Dataset;
import com.o2hlink.activ8.client.response.VoidResponse;

/**
 * @author Miguel Angel Hernandez
 **/
public class DatasetSampleRemoveAction implements Action<VoidResponse>, Serializable {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = 7220346620814017372L;
	/**
	 * 
	 **/
	private Dataset dataset;
//CONSTRUCTOR
	/**
	 * Empty constructor to allow serialization
	 **/
	public DatasetSampleRemoveAction(){
		
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
