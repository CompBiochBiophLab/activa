package com.o2hlink.pimtools.notes;

import java.util.Date;

public class Note {
	
	public long id;
	
	public String text;
	
	public Date date;
	
	/**
	 * State of the note (0 for ok, 1 for pending, 2 for resent)
	 */
	public int state;
	
	public Note() {
		
	}
	
	public Note(int id) {
		this.id = id;
	}
	
	public Note(int id, String text) {
		this.id = id;
		this.text = text;
	}
	
	public Note(long l, String text, Date date) {
		this.id = l;
		this.text = text;
		this.date = date;
	}
	
}
