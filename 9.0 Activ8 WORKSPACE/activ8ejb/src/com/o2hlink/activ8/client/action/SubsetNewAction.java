/**
 * 
 */
package com.o2hlink.activ8.client.action;

import com.o2hlink.activ8.client.entity.Dataset;
import com.o2hlink.activ8.client.entity.Subset;
import com.o2hlink.activ8.client.response.VoidResponse;

/**
 * Get all subsets found in a dataset.
 * @author Miguel Angel Hernandez
 **/
public class SubsetNewAction implements Action<VoidResponse> {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = -4269725441469056515L;
	/**
	 * 
	 **/
	private Dataset dataset;
	/**
	 * 
	 **/
	private Subset subset;
//CONSTRUCTOR
	/**
	 * Empty constructor to allow serialization
	 **/
	public SubsetNewAction(){
		
	}
	/**
	 * 
	 **/
	public SubsetNewAction(Dataset dataset, Subset subset){
		setDataset(dataset);
		setSubset(subset);
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
}
