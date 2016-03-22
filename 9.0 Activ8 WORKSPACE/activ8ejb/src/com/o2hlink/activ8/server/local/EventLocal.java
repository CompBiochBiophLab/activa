package com.o2hlink.activ8.server.local;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import com.o2hlink.activ8.client.entity.Account;
import com.o2hlink.activ8.client.entity.Event;
import com.o2hlink.activ8.client.entity.User;
import com.o2hlink.activ8.client.exception.InsertException;

/**
 * @author Miguel Angel Hernandez
 **/
@Local
public interface EventLocal {
	/**
	 * Persist the event
	 * @param	event The event to store
	 * @param	user The user of the event
	 * @return	The list of events stored
	 **/
	public List<Event> save(Event event, User user) throws InsertException;
	/**
	 * Get all events from the user in a time interval
	 * @param	user The user
	 * @param	start The start date
	 * @param	end The end date
	 * @return	The list of events
	 **/
	public List<Event> getEvents(User user, Date start, Date end);
	/**
	 * Get all events from the user from an external account in a time interval
	 * @param	user The user
	 * @param	start The start date
	 * @param	end The end date
	 * @return	The list of events
	 **/
	public List<Event> getEvents(User user, Date start, Date end, Account account);
}
