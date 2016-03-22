package com.o2hlink.activ8.client.action;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;

import com.o2hlink.activ8.client.entity.Clinician;
import com.o2hlink.activ8.client.entity.HasPapers;
import com.o2hlink.activ8.client.entity.Researcher;
import com.o2hlink.activ8.client.response.PaperListResponse;

/**
 * Get the list of papers a provider has
 * @author Miguel Angel Hernandez
 **/
public class PaperListGetAction implements Action<PaperListResponse>, Cacheable {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = -2018470353493259828L;
	/**
	 * 
	 **/
	private HasPapers provider;
//CONSTRUCTOR
	/**
	 * 
	 **/
	public PaperListGetAction(){
		
	}
	/**
	 * 
	 **/
	public PaperListGetAction(HasPapers provider){
		setProvider(provider);
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
	 * 
	 **/
	public long getMaxAge() {
		return 600000;
	}
}
