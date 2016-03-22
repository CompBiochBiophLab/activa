package com.o2hlink.pimtools.news;

import java.io.IOException;
import java.io.StringReader;
import java.util.Hashtable;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import com.o2hlink.activ8.client.entity.Array;
import com.o2hlink.pimtools.Activa;
import com.o2hlink.pimtools.exceptions.NotUpdatedException;

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
		try {
			Activa.myProtocolManager.getNewsFeeds();
		} catch (NotUpdatedException e) {
			Activa.myUIManager.loadTextOnWindow(Activa.myLanguageManager.TEXT_UPDATEVERSION);
			e.printStackTrace();
		}
	}
	
	public void extractNewsFromXML(String xml) {
		int id = 0;
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

	public Array<com.o2hlink.activ8.client.entity.Feed> SearchFeeds(String query) {
		Array<com.o2hlink.activ8.client.entity.Feed> result;
		try {
			result = Activa.myProtocolManager.SearchFeeds(query);
		} catch (NotUpdatedException e) {
			Activa.myUIManager.loadTextOnWindow(Activa.myLanguageManager.TEXT_UPDATEVERSION);
			e.printStackTrace();
			result = null;
		}
		return result;
	}

	public boolean AddFeed(com.o2hlink.activ8.client.entity.Feed feed) {
		boolean result;
		try {
			result = Activa.myProtocolManager.AddFeed(feed);
		} catch (NotUpdatedException e) {
			Activa.myUIManager.loadTextOnWindow(Activa.myLanguageManager.TEXT_UPDATEVERSION);
			e.printStackTrace();
			result = false;
		}
		return result;
	}

	public boolean removeFeed(Feed feed) {
		boolean result;
		try {
			result = Activa.myProtocolManager.removeFeed(feed);
		} catch (NotUpdatedException e) {
			Activa.myUIManager.loadTextOnWindow(Activa.myLanguageManager.TEXT_UPDATEVERSION);
			e.printStackTrace();
			result = false;
		}
		return result;
	}
	
}
