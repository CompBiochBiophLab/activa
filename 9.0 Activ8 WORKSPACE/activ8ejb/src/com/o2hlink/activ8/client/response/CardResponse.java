package com.o2hlink.activ8.client.response;

import com.o2hlink.activ8.client.entity.Card;

/**
 * Return a single contact.
 * @author Miguel Angel Hernandez
 **/
public class CardResponse implements Response {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = 8702966780808350776L;
	/**
	 * 
	 **/
	private Card contact;
//CONSTRUCTOR
	/**
	 * 
	 **/
	public CardResponse(){
		
	}
	/**
	 * 
	 **/
	public CardResponse(Card contact){
		setContact(contact);
	}
//METHODS
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
