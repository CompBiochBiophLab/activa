package com.o2hlink.activ8.client.action;

import com.o2hlink.activ8.client.entity.Protein;
import com.o2hlink.activ8.client.response.ProteinRegionListResponse;

/**
 * Retrieves the regions of a protein.
 * @author Miguel Angel Hernandez
 **/
public class ProteinRegionListGetAction implements Action<ProteinRegionListResponse> {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = -1314347008122836598L;
	/**
	 * 
	 **/
	private Protein protein;
//CONSTRUCTOR
	/**
	 * 
	 **/
	public ProteinRegionListGetAction(){
		
	}
	/**
	 * 
	 **/
	public ProteinRegionListGetAction(Protein protein){
		setProtein(protein);
	}
//METHODS
	/**
	 * @param protein the protein to set
	 */
	public void setProtein(Protein protein) {
		this.protein = protein;
	}
	/**
	 * @return the protein
	 */
	public Protein getProtein() {
		return protein;
	}
}
