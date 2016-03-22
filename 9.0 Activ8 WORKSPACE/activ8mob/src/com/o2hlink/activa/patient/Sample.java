package com.o2hlink.activa.patient;

import java.util.Date;

public class Sample {
	
	/**
	 * The ID of the associated event if there are anyone.
	 */
	public long eventId;
	
	/**
	 * The date the sample was taken.
	 */
	public Date date;
	
	/**
	 * The public constructor
	 * @param eventId
	 * @param date
	 */
	public Sample(long eventId, Date date) {
		this.eventId = eventId;
		this.date = date;
	}
	
}
