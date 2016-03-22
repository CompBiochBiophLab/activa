package com.o2hlink.activ8.common.entity;

/**
 * Frequency of an event
 * @author Miguel Angel Hernandez
 **/
public enum EventFrequency {
	/**
	 * No frequency
	 **/
	NONE,
	/**
	 * Daily frequency
	 **/
	DAILY,
	/**
	 * Weekly on weekdays
	 **/
	WEEKDAY,
	/**
	 * Weekly on Monday, Wednesday, Friday
	 **/
	MONWEDFRI,
	/**
	 * Weekly on Tuesday, Thursday
	 **/
	TUETHUR,
	/**
	 * Weekly frequency
	 **/
	WEEKLY,
	/**
	 * Monthly frequency
	 **/
	MONTHLY,
	/**
	 * Yearly frequency
	 **/
	YEARLY
}
