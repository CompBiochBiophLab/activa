package com.o2hlink.activ8.client.response;

import java.util.List;

import com.o2hlink.activ8.client.entity.ProteinRegion;

/**
 * Return a list of protein regions.
 * @author Miguel Angel Hernandez
 **/
public class ProteinRegionListResponse implements Response {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = 7338338832409094853L;
	/**
	 * 
	 **/
	private List<ProteinRegion> regions;
//CONSTRUCTOR
	/**
	 * 
	 **/
	public ProteinRegionListResponse(){
		
	}
	/**
	 * 
	 **/
	public ProteinRegionListResponse(List<ProteinRegion> regions){
		setRegions(regions);
	}
//METHODS
	/**
	 * @param regions the regions to set
	 */
	public void setRegions(List<ProteinRegion> regions) {
		this.regions = regions;
	}
	/**
	 * @return the regions
	 */
	public List<ProteinRegion> getRegions() {
		return regions;
	}
}
