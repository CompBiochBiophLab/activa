package com.o2hlink.activ8.client.action;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;

import com.o2hlink.activ8.client.entity.Card;
import com.o2hlink.activ8.client.entity.HasCards;
import com.o2hlink.activ8.client.entity.Institution;
import com.o2hlink.activ8.client.response.CardResponse;

/**
 * Given an object implementing {@link HasCards} create a new contact
 * @author Miguel Angel Hernandez
 **/
public class CardNewAction implements Action<CardResponse> {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = -1746523450873322082L;
	/**
	 * 
	 **/
	private HasCards provider;
	/**
	 * 
	 **/
	private Card contact;
//CONSTRUCTOR
	/**
	 * 
	 **/
	public CardNewAction(){
		
	}
	/**
	 * 
	 **/
	public CardNewAction(HasCards provider, Card contact){
		setProvider(provider);
		setContact(contact);
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
	/**
	 * @param contact the contact to set
	 */
	public void setContact(Card contact) {
		this.contact = contact;
	}
	/**
	 * @return the contact
	 */
	public Card getContact() {
		return contact;
	}
}
