package com.o2hlink.activ8.common.entity;

/**
 * Communication types
 **/
public enum AccountType {
	/**
	 * Skype
	 **/
	SKYPE(false, false),
	/**
	 * Google Account
	 **/
	GMAIL(true, true),
	/**
	 * SMS Provider
	 **/
	SMS(false, false),
	/**
	 * Flash video conference
	 **/
	VIDEO_CONFERENCE(false, false),
	/**
	 * Broadcasting
	 **/
	SHARE_DESKTOP(false, false),
	/**
	 * Windows live account
	 **/
	WINDOWS_LIVE(true, true),
	/**
	 * Yahoo account
	 **/
	YAHOO(true, false),
	/**
	 * Facebook account
	 **/
	FACEBOOK(false, false);
//FIELDS
	/**
	 * 
	 **/
	private final boolean messages;
	/**
	 * 
	 **/
	private final boolean calendar;
//CONSTRUCTOR
	/**
	 * @param	messages Has messages
	 * @param	calendar Has calendar
	 **/
	AccountType(boolean messages, boolean calendar){
		this.messages = messages;
		this.calendar = calendar;
	}
	/**
	 * @return the messages
	 */
	public boolean hasMessages() {
		return messages;
	}
	/**
	 * @return the calendar
	 */
	public boolean hasCalendar() {
		return calendar;
	}
}
