package com.o2hlink.activ8.client.response;

import java.util.List;

import com.o2hlink.activ8.client.entity.Province;

/**
 * Return a list of provinces.
 * @author Miguel Angel Hernandez
 **/
public class ProvinceListResponse implements Response {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = -7060932245858093205L;
	/**
	 * 
	 **/
	private List<Province> provinces;
//CONSTRUCTOR
	/**
	 * 
	 **/
	public ProvinceListResponse(){
		
	}
	/**
	 * 
	 **/
	public ProvinceListResponse(List<Province> provinces){
		setProvinces(provinces);
	}
//METHODS
	/**
	 * @param provinces the provinces to set
	 */
	public void setProvinces(List<Province> provinces) {
		this.provinces = provinces;
	}
	/**
	 * @return the provinces
	 */
	public List<Province> getProvinces() {
		return provinces;
	}
}
