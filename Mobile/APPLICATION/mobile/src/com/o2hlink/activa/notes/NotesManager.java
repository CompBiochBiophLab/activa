package com.o2hlink.activa.notes;

import java.io.IOException;
import java.io.StringReader;
import java.util.Date;
import java.util.Hashtable;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import com.o2hlink.activa.Activa;
import com.o2hlink.activa.ActivaUtil;
import com.o2hlink.activa.exceptions.NotUpdatedException;

public class NotesManager {
	
	public Hashtable<Long, Note> notelist;
	
	private static NotesManager instance;
	
	private NotesManager() {
		this.notelist = new Hashtable<Long, Note>();
	}
	
	public static NotesManager getInstance() {
		if (NotesManager.instance == null) NotesManager.instance = new NotesManager();
		return NotesManager.instance;
	}
	
	public static void freeInstance() {
		instance = null;
	}
	
	public void getNotes() {
		try {
			Activa.myProtocolManager.getNotes();
		} catch (NotUpdatedException e) {
			Activa.myUIManager.loadTextOnWindow(Activa.myLanguageManager.TEXT_UPDATEVERSION);
			e.printStackTrace();
		}
	}
	
	public void extractNotesFromXML(String xml) {
		long id = 0;
		this.notelist.clear();
		XmlPullParserFactory factory;
		try {
			factory = XmlPullParserFactory.newInstance();
	        factory.setNamespaceAware(true);
	        XmlPullParser info = factory.newPullParser();
	        info.setInput(new StringReader(xml));
	        int event = info.getEventType();
	        while (event != XmlPullParser.END_DOCUMENT) {
	            if(event == XmlPullParser.START_DOCUMENT) {
	            } else if(event == XmlPullParser.END_DOCUMENT) {    	
	            } else if(event == XmlPullParser.START_TAG) {
	                if (info.getName().equalsIgnoreCase("NOTE")) {
	                	String text = info.getAttributeValue(info.getNamespace(), "TEXT");
	                	Date date = ActivaUtil.XMLDateToDate(info.getAttributeValue(info.getNamespace(), "DATE"));
	                	this.notelist.put(id, new Note(id, text, date));
	                	id++;
	                }
	            } else if(event == XmlPullParser.END_TAG) {
	                if (info.getName().equalsIgnoreCase("NOTES")) {
	                	break;
	                }
	            }
	            event = info.next();
	        }
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean addNote(String text) {
		boolean returned = false;
		com.o2hlink.activ8.client.entity.Note note;
		try {
			note = Activa.myProtocolManager.sendNote(text);
			if (note != null) {
				Note noteToAdd = new Note(note.getId(), note.getComment(), note.getDate());
				noteToAdd.state = 0;
				this.notelist.put(noteToAdd.id, noteToAdd);
				returned = true;
			}
		} catch (NotUpdatedException e) {
			e.printStackTrace();
		}
		return returned;
	}
	
	public long getNoteId() {
		long key = -1;
		while (this.notelist.containsKey(key)) {
			key--;
		}
		return key;
	}

	public com.o2hlink.activ8.client.entity.Note sendNote(String text) {
		com.o2hlink.activ8.client.entity.Note result;
		try {
			result = Activa.myProtocolManager.sendNote(text);
		} catch (NotUpdatedException e) {
			result = null;
			Activa.myUIManager.loadTextOnWindow(Activa.myLanguageManager.TEXT_UPDATEVERSION);
			e.printStackTrace();
		}
		return result;
	}
	
}
