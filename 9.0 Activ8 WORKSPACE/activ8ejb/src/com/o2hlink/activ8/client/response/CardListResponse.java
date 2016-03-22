package com.o2hlink.activ8.client.response;

import java.util.List;

import com.o2hlink.activ8.client.entity.Card;

/**
 * Return a list of contacts
 * @author Miguel Angel Hernandez
 **/
public class CardListResponse implements Response {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = 8702966780808350776L;
	/**
	 * 
	 **/
	private List<Card> contacts;
//CONSTRUCTOR
	/**
	 * 
	 **/
	public CardListResponse(){
		
	}
	/**
	 * 
	 **/
	public CardListResponse(List<Card> contacts){
		setContacts(contacts);
	}
//METHODS
	/**
	 * @param contact the contact to set
	 */
	public void setContacts(List<Card> contacts) {
		this.contacts = contacts;
	}
	/**
	 * @return the contact
	 */
	public List<Card> getContacts() {
		return contacts;
	}
}
