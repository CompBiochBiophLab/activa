package com.o2hlink.activ8.client.response;

import java.util.ArrayList;
import java.util.List;

import com.o2hlink.activ8.client.entity.Exon;

/**
 * The list of exons
 * @author Miguel Angel Hernandez
 **/
public class ExonListResponse implements Response {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = -3887500035863643204L;
	/**
	 * 
	 **/
	private List<Exon> exons = new ArrayList<Exon>();
//CONSTRUCTOR
	/**
	 * 
	 **/
	public ExonListResponse(){
		
	}
	/**
	 *
	 **/
	public ExonListResponse(List<Exon> exons){
		setExons(exons);
	}
//METHODS
	/**
	 * @param exons the exons to set
	 */
	public void setExons(List<Exon> exons) {
		this.exons = exons;
	}
	/**
	 * @return the exons
	 */
	public List<Exon> getExons() {
		return exons;
	}
}
