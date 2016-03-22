package com.o2hlink.activ8.client.response;

import com.o2hlink.activ8.client.entity.Paper;

/**
 * Return a single paper.
 * @author Miguel Angel Hernandez
 **/
public class PaperResponse implements Response {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = -6840957328829915378L;
	/**
	 * 
	 **/
	private Paper paper;
//CONSTRUCTOR
	/**
	 * 
	 **/
	public PaperResponse(){
		
	}
	/**
	 * 
	 **/
	public PaperResponse(Paper paper){
		setPaper(paper);
	}
//METHODS
	/**
	 * @param paper the paper to set
	 */
	public void setPaper(Paper paper) {
		this.paper = paper;
	}
	/**
	 * @return the paper
	 */
	public Paper getPaper() {
		return paper;
	}
}
