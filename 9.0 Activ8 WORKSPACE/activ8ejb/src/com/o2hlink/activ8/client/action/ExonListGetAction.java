package com.o2hlink.activ8.client.action;

import com.o2hlink.activ8.client.entity.Gene;
import com.o2hlink.activ8.client.response.ExonListResponse;

/**
 * Get the list of exons in a gene
 * @author Miguel Angel Hernandez
 **/
public class ExonListGetAction implements Action<ExonListResponse>, Cacheable {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = 2264728754930749647L;
	/**
	 * 
	 **/
	private Gene gene;
//CONSTRUCTOR
	/**
	 * 
	 **/
	public ExonListGetAction(){
		
	}
	/**
	 * 
	 **/
	public ExonListGetAction(Gene gene){
		setGene(gene);
	}
//METHODS
	/**
	 * @param gene the gene to set
	 */
	public void setGene(Gene gene) {
		this.gene = gene;
	}
	/**
	 * @return the gene
	 */
	public Gene getGene() {
		return gene;
	}
	/**
	 * 
	 **/
	public long getMaxAge() {
		return 24*60*1000;
	}
	/**
	 * 
	 **/
	@Override
	public boolean equals(Object obj){
		if (obj instanceof ExonListGetAction){
			ExonListGetAction action = (ExonListGetAction)obj;
			return getGene().equals(action.getGene());
		}
		return false;
	}
	/**
	 * 
	 **/
	@Override
	public int hashCode(){
		return getGene().hashCode();
	}
}
