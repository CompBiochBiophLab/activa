package com.o2hlink.activa.ui.widget;

import java.io.IOException;
import java.util.Date;
import java.util.Hashtable;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import com.o2hlink.activa.Activa;
import com.o2hlink.activa.exterior.ExteriorManager;

public class Widget {
	
	public static final int FUNCTION_CALENDAR = 0;
	public static final int FUNCTION_SENSORS = 1;
	public static final int FUNCTION_PROGRAMS = 2;
	public static final int FUNCTION_QUESTS = 3;
	public static final int FUNCTION_MAP = 4;
	public static final int FUNCTION_MESSAGES = 5;
	public static final int FUNCTION_NOTES = 6;
	public static final int FUNCTION_NEWS = 7;
	public static final int FUNCTION_PHONE = 8;
	public static final int FUNCTION_SMS = 9;
	public static final int FUNCTION_SOCIALNETWORKS = 10;
	public static final int FUNCTION_MUSIC = 11;
	public static final int FUNCTION_VIDEO = 12;
	public static final int FUNCTION_PICTURES = 13;
	public static final int FUNCTION_GMAIL = 14;
	public static final int FUNCTION_EMAIL = 15;
	public static final int FUNCTION_PATIENTS = 16;
	public static final int FUNCTION_PROJECTS = 17;
	public static final int FUNCTION_TOOLS = 18;
	public static final int FUNCTION_BIOLOGICAL = 19;
	public static final int FUNCTION_PAPERS = 20;
	public static final int FUNCTION_WIKIPEDIA = 21;
	public static final int FUNCTION_GOOGLE = 22;
	public static final int FUNCTION_BING = 23;
	public static final int FUNCTION_PUBLICINFO = 24;
	public static final int FUNCTION_PERSONALITEMS = 25;
	public static final int FUNCTION_APPICATIONS = 26;
	public static final int FUNCTION_HISTORY = 27;
	public static final int FUNCTION_QUESTIONNAIRECONTROL = 28;
	
	public int id;
	
	public int height;
	public int width;
	public int top;
	public int left;
	
	public String name;
	
	public int function;
	
	public Widget(int id) {
		this.id = id;
	}
	
	public static Widget getWidget(XmlPullParser info, int id){
		Widget returned = new Widget(id);
		try {
			int event = info.getEventType();
		    while (event != XmlPullParser.END_DOCUMENT) {
		    	if(event == XmlPullParser.START_DOCUMENT) {
		    	} else if(event == XmlPullParser.END_DOCUMENT) {    	
		        } else if(event == XmlPullParser.START_TAG) {
		        	if (info.getName().equalsIgnoreCase("WIDGET")) {
		        		returned.function = Integer.parseInt(info.getAttributeValue(info.getNamespace(),"FUNCTION"));
		        		returned.name = info.getAttributeValue(info.getNamespace(),"NAME");
		        		returned.top = Integer.parseInt(info.getAttributeValue(info.getNamespace(),"TOP"));
		        		returned.left = Integer.parseInt(info.getAttributeValue(info.getNamespace(),"LEFT"));
		        		returned.width = Integer.parseInt(info.getAttributeValue(info.getNamespace(),"WIDTH"));
		        		returned.height = Integer.parseInt(info.getAttributeValue(info.getNamespace(),"HEIGHT"));
		            }
		        } 
		        else if(event == XmlPullParser.END_TAG) {
	            	if (info.getName().equalsIgnoreCase("WIDGET")) {
	            		break;
	            	}
	            }
	            event = info.next();
		    }
		} catch (XmlPullParserException e) {
			e.printStackTrace();
    		return null;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return returned;
	}
	
	public static boolean checkWidget(XmlPullParser info){
		try {
			int event = info.getEventType();
		    while (event != XmlPullParser.END_DOCUMENT) {
		    	if(event == XmlPullParser.START_DOCUMENT) {
		    	} else if(event == XmlPullParser.END_DOCUMENT) {    	
		        } else if(event == XmlPullParser.START_TAG) {
		        	if (info.getName().equalsIgnoreCase("WIDGET")) {
		        		Integer.parseInt(info.getAttributeValue(info.getNamespace(),"FUNCTION"));
		        		if (info.getAttributeValue(info.getNamespace(),"NAME") == null) return false;
		        		Integer.parseInt(info.getAttributeValue(info.getNamespace(),"TOP"));
		        		Integer.parseInt(info.getAttributeValue(info.getNamespace(),"LEFT"));
		        		Integer.parseInt(info.getAttributeValue(info.getNamespace(),"WIDTH"));
		        		Integer.parseInt(info.getAttributeValue(info.getNamespace(),"HEIGHT"));
		            }
		        } 
		        else if(event == XmlPullParser.END_TAG) {
	            	if (info.getName().equalsIgnoreCase("WIDGET")) {
	            		break;
	            	}
	            }
	            event = info.next();
		    }
		} catch (Exception e) {
			e.printStackTrace();
    		return false;
		}
		return true;
	}
	
	public void doFunction(Subenvironment sub) {
		switch (this.function) {
			case FUNCTION_CALENDAR:
				Activa.myUIManager.loadScheduleDay(new Date());
				return;
			case FUNCTION_SENSORS:
				Activa.myUIManager.loadSensorList();
				return;
			case FUNCTION_PROGRAMS:
				Activa.myUIManager.loadProgramList();
				return;
			case FUNCTION_QUESTS:
				Activa.myUIManager.loadTotalQuestList();
				return;
			case FUNCTION_MESSAGES:
				Activa.myUIManager.loadMessageList();
				return;
			case FUNCTION_NOTES:
				Activa.myUIManager.loadNotes();
				return;
			case FUNCTION_MAP:
				Activa.myUIManager.showMap();
				return;
			case FUNCTION_NEWS:
				Activa.myUIManager.loadNewsList(true);
				return;
			case FUNCTION_PHONE:
				Activa.myExteriorManager.runApplication(ExteriorManager.APP_SKYPE);
				return;
			case FUNCTION_SMS:
				Activa.myExteriorManager.runApplication(ExteriorManager.APP_SMS);
				return;
			case FUNCTION_EMAIL:
				Activa.myExteriorManager.runApplication(ExteriorManager.APP_MAIL);
				return;
			case FUNCTION_GMAIL:
				Activa.myExteriorManager.runApplication(ExteriorManager.APP_GMAIL);
				return;
			case FUNCTION_GOOGLE:
				Activa.myExteriorManager.runApplication(ExteriorManager.APP_GOOGLE);
				return;
			case FUNCTION_BING:
				Activa.myExteriorManager.runApplication(ExteriorManager.APP_BING);
				return;
			case FUNCTION_WIKIPEDIA:
				Activa.myExteriorManager.runApplication(ExteriorManager.APP_WIKIPEDIA);
				return;
			case FUNCTION_SOCIALNETWORKS:
				Activa.myUIManager.loadExternalSocialNetworks();
				return;
			case FUNCTION_PICTURES:
				Activa.myUIManager.loadPictures();
				return;
			case FUNCTION_MUSIC:
				Activa.myUIManager.loadMusic();
				return;
			case FUNCTION_VIDEO:
				Activa.myUIManager.loadVideos();
				return;
			case FUNCTION_PATIENTS:
				Activa.myUIManager.showPatients();
				return;
			case FUNCTION_PROJECTS:
				Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.MAIN_NOTAVAILABLE);
				return;
			case FUNCTION_PAPERS:
				Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.MAIN_NOTAVAILABLE);
				return;
			case FUNCTION_BIOLOGICAL:
				Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.MAIN_NOTAVAILABLE);
				return;
			case FUNCTION_TOOLS:
				Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.MAIN_NOTAVAILABLE);
				return;
			case FUNCTION_PUBLICINFO:
				Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.MAIN_NOTAVAILABLE);
				return;
			case FUNCTION_PERSONALITEMS:
				Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.MAIN_NOTAVAILABLE);
				return;
			case FUNCTION_APPICATIONS:
				Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.MAIN_NOTAVAILABLE);
				return;
			case FUNCTION_HISTORY:
				Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.MAIN_NOTAVAILABLE);
				return;
			case FUNCTION_QUESTIONNAIRECONTROL:
				Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.MAIN_NOTAVAILABLE);
				return;
			default:
				return;
		}
	}
	
}
