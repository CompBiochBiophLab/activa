/**
 * 
 */
package com.o2hlink.activ8.client.response;

import java.util.List;

import com.o2hlink.activ8.client.entity.Subset;

/**
 * Return a subset list
 * @author Miguel Angel Hernandez
 **/
public class SubsetListResponse implements Response {
//FIELDS
	/**
	 * 
	 **/
	private static final long serialVersionUID = -1000561449283694037L;
	/**
	 * 
	 **/
	private List<Subset> subsets;
//CONSTRUCTOR
	/**
	 * Empty constructor to allow serialization
	 **/
	public SubsetListResponse(){
		
	}
	/**
	 * 
	 **/
	public SubsetListResponse(List<Subset> subsets){
		this.setSubsets(subsets);
	}
//METHODS
	/**
	 * @param subsets the subsets to set
	 */
	public void setSubsets(List<Subset> subsets) {
		this.subsets = subsets;
	}
	/**
	 * @return the subsets
	 */
	public List<Subset> getSubsets() {
		return subsets;
	}
}
