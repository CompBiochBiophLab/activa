package com.o2hlink.activa.news;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

import android.util.Xml;

import com.o2hlink.activa.Activa;
import com.o2hlink.activa.data.calendar.Event;
import com.o2hlink.activa.data.questionnaire.QuestManager;
import com.o2hlink.activa.map.Mark;

public class NewsManager {
	
	public Hashtable<Integer, Feed> feedslist;
	
	private static NewsManager instance;
	
	private NewsManager() {
		this.feedslist = new Hashtable<Integer, Feed>();
	}
	
	public static NewsManager getInstance() {
		if (NewsManager.instance == null) NewsManager.instance = new NewsManager();
		return NewsManager.instance;
	}
	
	public static void freeInstance() {
		instance = null;
	}
	
	public void getNews() {
		Activa.myProtocolManager.getNewsFeeds();
	}
	
	public void extractNewsFromXML(String xml) {
		int id = 0;
		XmlPullParserFactory factory;
		Event currentEvent;
		try {
			factory = XmlPullParserFactory.newInstance();
	        factory.setNamespaceAware(true);
	        XmlPullParser info = factory.newPullParser();
	        boolean insideEvent = false;
	        boolean end = true; 
	        info.setInput(new StringReader(xml));
	        int event = info.getEventType();
        	currentEvent = new Event();	
	        while (event != XmlPullParser.END_DOCUMENT) {
	            if(event == XmlPullParser.START_DOCUMENT) {
	            } else if(event == XmlPullParser.END_DOCUMENT) {    	
	            } else if(event == XmlPullParser.START_TAG) {
	                if (info.getName().equalsIgnoreCase("FEED")) {
	                	String url = info.getAttributeValue(info.getNamespace(), "URL");
	                	if (url != null) {
		                	this.feedslist.put(id, new Feed(url, id));
	                		id++;
	                	}
	                }
	            } else if(event == XmlPullParser.END_TAG) {
	                if (info.getName().equalsIgnoreCase("FEEDS")) {
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
	
	public void passFeedToXML() {
	    XmlSerializer serializer = Xml.newSerializer();
	    StringWriter writer = new StringWriter();
        try {
			serializer.setOutput(writer);
	        serializer.startDocument("UTF-8", true);
	        serializer.startTag("", "FEEDS");
	        Enumeration<Feed> enumeration = this.feedslist.elements();
	        while (enumeration.hasMoreElements()) {
	        	enumeration.nextElement().passFeedToXML(serializer, writer);
	        }
            serializer.endTag("", "FEEDS");
            serializer.endDocument();

		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void extractNewsFromInternalXML(String xml) {
		int id = 0;
		XmlPullParserFactory factory;
		Event currentEvent;
		try {
			factory = XmlPullParserFactory.newInstance();
	        factory.setNamespaceAware(true);
	        XmlPullParser info = factory.newPullParser();
	        boolean insideEvent = false;
	        boolean end = true; 
	        info.setInput(new StringReader(xml));
	        int event = info.getEventType();
        	currentEvent = new Event();	
	        while (event != XmlPullParser.END_DOCUMENT) {
	            if(event == XmlPullParser.START_DOCUMENT) {
	            } else if(event == XmlPullParser.END_DOCUMENT) {    	
	            } else if(event == XmlPullParser.START_TAG) {
	                if (info.getName().equalsIgnoreCase("FEED")) {
	                	String url = info.getAttributeValue(info.getNamespace(), "URL");
	                	if (url != null) {
		                	this.feedslist.put(id, new Feed(url, id));
	                		id++;
	                	}
	                }
	            } else if(event == XmlPullParser.END_TAG) {
	                if (info.getName().equalsIgnoreCase("FEEDS")) {
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
	
}
