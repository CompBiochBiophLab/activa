/**
 * 
 */
package com.o2hlink.activ8.client.response;

import java.util.List;

import com.o2hlink.activ8.client.entity.Dataset;

/**
 * Return a list of datasets
 * @author Miguel Angel Hernandez
 **/
public class DatasetListResponse implements Response {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = 4908513348662132627L;
	/**
	 * 
	 **/
	private List<Dataset> datasets;
//CONSTRUCTOR
	/**
	 * Empty constructor to allow serialization
	 **/
	public DatasetListResponse(){
		
	}
	/**
	 * 
	 **/
	public DatasetListResponse(List<Dataset> datasets){
		this.setDatasets(datasets);
	}
//METHODS
	/**
	 * @param datasets the datasets to set
	 **/
	public void setDatasets(List<Dataset> datasets) {
		this.datasets = datasets;
	}
	/**
	 * @return the datasets
	 **/
	public List<Dataset> getDatasets() {
		return datasets;
	}
}
