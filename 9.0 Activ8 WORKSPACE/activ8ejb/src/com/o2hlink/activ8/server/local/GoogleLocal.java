package com.o2hlink.activ8.server.local;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import com.o2hlink.activ8.client.entity.Event;
import com.o2hlink.activ8.common.entity.Email;

/**
 * Remote operations on a google account
 * @author Miguel Angel Hernandez
 **/
@Local
public interface GoogleLocal {
	/**
	 * Get emails from a google account
	 * @param	account A google account, i.e. test@gmail.com
	 * @param	password The google account password
	 **/
	public List<Email> getEmails(String account, String password);
	/**
	 * Get events from a google account
	 * @param	account A google account, i.e. test@gmail.com
	 * @param	password The google account password
	 * @param	start	The start date
	 * @param	end	The end date
	 **/
	public List<Event> getEvents(String account, String password, Date start, Date end);
}
