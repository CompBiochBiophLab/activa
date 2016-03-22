package com.o2hlink.activ8.server.local;

import java.util.List;

import javax.ejb.Local;

import com.o2hlink.activ8.client.entity.Card;
import com.o2hlink.activ8.client.entity.Disease;
import com.o2hlink.activ8.client.entity.HasCards;
import com.o2hlink.activ8.client.entity.Institution;

/**
 * Local operations over an institution
 * @author Miguel Angel Hernandez
 **/
@Local
public interface InstitutionLocal {
	/**
	 * @param	institution the institution to persist
	 **/
	public Institution save(Institution institution);
	/**
	 * Get the list of institutions associated to a particular disease
	 * @param	disease
	 * @return	List of the institutions
	 **/
	public List<Institution> getInstitutions(Disease disease);
	/**
	 * Get a set of institutions
	 * @return	List of the institutions
	 **/
	public List<Institution> getInstitutions(double north, double south, double east, double west);
	/**
	 * @param	institution The institution to add the disease
	 * @param	disease The disease the institution is studying
	 **/
	public void addDisease(Institution institution, Disease disease);
	/**
	 * 
	 **/
	public Card addContact(HasCards provider, Card contact);
	/**
	 * 
	 **/
	public List<Card> getContacts(HasCards provider);
}
