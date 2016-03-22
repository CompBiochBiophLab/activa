package com.o2hlink.activ8.client.action;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;

import com.o2hlink.activ8.client.entity.Clinician;
import com.o2hlink.activ8.client.entity.HasPapers;
import com.o2hlink.activ8.client.entity.Paper;
import com.o2hlink.activ8.client.entity.Researcher;
import com.o2hlink.activ8.client.response.VoidResponse;

/**
 * Creates a new paper associated to the provider
 * @author Miguel Angel Hernandez
 **/
public class PaperRemoveAction implements Action<VoidResponse> {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = 2386387884476082524L;
	/**
	 * 
	 **/
	private HasPapers provider;
	/**
	 * 
	 **/
	private Paper paper;
//CONSTRUCTOR
	/**
	 * 
	 **/
	public PaperRemoveAction(){
		
	}
	/**
	 * 
	 **/
	public PaperRemoveAction(HasPapers provider, Paper paper){
		setProvider(provider);
		setPaper(paper);
	}
//METHODS
	/**
	 * @param provider the provider to set
	 */
	public void setProvider(HasPapers provider) {
		this.provider = provider;
	}
	/**
	 * @return the provider
	 */
	@XmlElements({
		@XmlElement(name="clinician", type=Clinician.class),
		@XmlElement(name="researcher", type=Researcher.class),
	})
	public HasPapers getProvider() {
		return provider;
	}
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
