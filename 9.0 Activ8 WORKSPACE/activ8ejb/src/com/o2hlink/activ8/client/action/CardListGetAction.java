package com.o2hlink.activ8.client.action;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;

import com.o2hlink.activ8.client.entity.HasCards;
import com.o2hlink.activ8.client.entity.Institution;
import com.o2hlink.activ8.client.response.CardListResponse;

/**
 * Given an object implementing {@link HasCards} get its contacts
 * @author Miguel Angel Hernandez
 **/
public class CardListGetAction implements Action<CardListResponse> {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = -2564064217675089178L;
	/**
	 * 
	 **/
	private HasCards provider;
//CONSTRUCTOR
	/**
	 * 
	 **/
	public CardListGetAction(){
		
	}
	/**
	 * 
	 **/
	public CardListGetAction(HasCards provider){
		setProvider(provider);
	}
//METHODS
	/**
	 * @param provider the provider to set
	 */
	public void setProvider(HasCards provider) {
		this.provider = provider;
	}
	/**
	 * @return the provider
	 */
	@XmlElements({
		@XmlElement(name="institution", type=Institution.class)
	})
	public HasCards getProvider() {
		return provider;
	}
}
