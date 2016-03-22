package com.o2hlink.activ8.client.response;

import java.util.List;

import com.o2hlink.activ8.client.entity.Paper;

/**
 * Return a list of papers.
 * @author Miguel Angel Hernandez
 **/
public class PaperListResponse implements Response {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = -6840957328829915378L;
	/**
	 * 
	 **/
	private List<Paper> papers;
//CONSTRUCTOR
	/**
	 * 
	 **/
	public PaperListResponse(){
		
	}
	/**
	 * 
	 **/
	public PaperListResponse(List<Paper> papers){
		setPapers(papers);
	}
//METHODS
	/**
	 * @param papers the papers to set
	 */
	public void setPapers(List<Paper> papers) {
		this.papers = papers;
	}
	/**
	 * @return the papers
	 */
	public List<Paper> getPapers() {
		return papers;
	}
}
