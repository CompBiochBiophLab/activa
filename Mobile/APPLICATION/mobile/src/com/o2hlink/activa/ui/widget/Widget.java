package com.o2hlink.activa.ui.widget;

import java.io.IOException;
import java.util.Date;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.content.Intent;
import android.net.Uri;

import com.o2hlink.activa.Activa;
import com.o2hlink.activa.ActivaConfig;
import com.o2hlink.activa.background.GetHistory;
import com.o2hlink.activa.exterior.ExteriorManager;
import com.o2hlink.activa.patient.Patient;

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
	public static final int FUNCTION_MAILS = 29;
	public static final int FUNCTION_CELLULAR = 30;
	public static final int FUNCTION_PATANDQUEST = 31;
	public static final int FUNCTION_PROJECTSANDQUEST = 32;
	public static final int FUNCTION_CALENDARANDPROJECTS = 33;
	public static final int FUNCTION_PAPERSANDQUEST = 34;
	
	public int id;
	
	public int height;
	public int width;
	public int top;
	public int left;
	
	public int cloudposition;
	
	public String name;
	
	public int function;
	
	public Widget(int id) {
		this.id = id;
		this.cloudposition = 0;
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
		        		String cloudpos = info.getAttributeValue(info.getNamespace(),"CLOUDPOS");
		        		if (cloudpos != null) returned.cloudposition = Integer.parseInt(cloudpos);
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
		        		String cloudpos = info.getAttributeValue(info.getNamespace(),"CLOUDPOS");
		        		if (cloudpos != null) Integer.parseInt(cloudpos);
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
				if (!Activa.myMobileManager.identified) Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.MAIN_NOTAVAILABLE);
				else Activa.myUIManager.loadScheduleDay(new Date());
				return;
			case FUNCTION_SENSORS:
				if (!Activa.myMobileManager.identified) Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.MAIN_NOTAVAILABLE);
				else Activa.myUIManager.loadSensorList();
				return;
			case FUNCTION_PROGRAMS:
				if (!Activa.myMobileManager.identified) Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.MAIN_NOTAVAILABLE);
				else Activa.myUIManager.showExternalLinks();
				return;
			case FUNCTION_QUESTS:
				if (!Activa.myMobileManager.identified) Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.MAIN_NOTAVAILABLE);
				else if (Activa.myMobileManager.userForServices instanceof com.o2hlink.activ8.client.entity.Patient) Activa.myUIManager.loadTotalQuestList();
				else Activa.myUIManager.loadSharedQuestionnaires();
				return;
			case FUNCTION_MESSAGES:
				if (!Activa.myMobileManager.identified) Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.MAIN_NOTAVAILABLE);
				else Activa.myUIManager.loadReceivedMessageList(0);
				return;
			case FUNCTION_NOTES:
				if (!Activa.myMobileManager.identified) Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.MAIN_NOTAVAILABLE);
				else Activa.myUIManager.loadNotes();
				return;
			case FUNCTION_MAP:
				if (!Activa.myMobileManager.identified) Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.MAIN_NOTAVAILABLE);
				else Activa.myUIManager.showMap();
				return;
			case FUNCTION_NEWS:
				if (!Activa.myMobileManager.identified) Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.MAIN_NOTAVAILABLE);
				else Activa.myUIManager.loadNewsList(true);
				return;
			case FUNCTION_PHONE:
				Activa.myExteriorManager.runApplication(ExteriorManager.APP_SKYPE);
				return;
			case FUNCTION_SMS:
				if (!Activa.myMobileManager.identified) Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.MAIN_NOTAVAILABLE);
				else Activa.myUIManager.loadReceivedMessageList(0);
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
			case FUNCTION_MAILS:
				Activa.myUIManager.loadMails();
				return;
			case FUNCTION_PATANDQUEST:
				Activa.myUIManager.loadPatAndQuests();
				return;
			case FUNCTION_PROJECTSANDQUEST:
				Activa.myUIManager.loadProjectsAndQuests();
				return;
			case FUNCTION_CALENDARANDPROJECTS:
				Activa.myUIManager.loadCalendarAndProjects();
				return;
			case FUNCTION_PAPERSANDQUEST:
				Activa.myUIManager.loadPapersAndQuest();
				return;
			case FUNCTION_CELLULAR:
				if (!Activa.myMobileManager.identified) Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.MAIN_NOTAVAILABLE);
				else {
					Intent in = new Intent(Intent.ACTION_VIEW);
					in.setData(Uri.parse(ActivaConfig.ACTIV8YOUTUBE_URL));
					Activa.myApp.startActivity(in);
				}
				return;
			case FUNCTION_PATIENTS:
				if (!Activa.myMobileManager.identified) Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.MAIN_NOTAVAILABLE);
				else Activa.myUIManager.showPatients();
				return;
			case FUNCTION_PROJECTS:
				if (!Activa.myMobileManager.identified) Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.MAIN_NOTAVAILABLE);
				else Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.MAIN_NOTAVAILABLE);
				return;
			case FUNCTION_PAPERS:
				if (!Activa.myMobileManager.identified) Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.MAIN_NOTAVAILABLE);
				else Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.MAIN_NOTAVAILABLE);
				return;
			case FUNCTION_BIOLOGICAL:
				if (!Activa.myMobileManager.identified) Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.MAIN_NOTAVAILABLE);
				else Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.MAIN_NOTAVAILABLE);
				return;
			case FUNCTION_TOOLS:
				if (!Activa.myMobileManager.identified) Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.MAIN_NOTAVAILABLE);
				else Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.MAIN_NOTAVAILABLE);
				return;
			case FUNCTION_PUBLICINFO:
				if (!Activa.myMobileManager.identified) Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.MAIN_NOTAVAILABLE);
				else Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.MAIN_NOTAVAILABLE);
				return;
			case FUNCTION_PERSONALITEMS:
				if (!Activa.myMobileManager.identified) Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.MAIN_NOTAVAILABLE);
				else Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.MAIN_NOTAVAILABLE);
				return;
			case FUNCTION_APPICATIONS:
				if (!Activa.myMobileManager.identified) Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.MAIN_NOTAVAILABLE);
				else Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.MAIN_NOTAVAILABLE);
				return;
			case FUNCTION_HISTORY:
				if (!Activa.myMobileManager.identified) Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.MAIN_NOTAVAILABLE);
				else if (!(Activa.myMobileManager.userForServices instanceof com.o2hlink.activ8.client.entity.Patient)) Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.MAIN_NOTAVAILABLE);
				else {
					GetHistory sending = new GetHistory(new Patient((com.o2hlink.activ8.client.entity.Patient) Activa.myMobileManager.userForServices), true);
					Thread thread = new Thread(sending, "GETHISTORY");
					thread.start();
				}
				return;
			case FUNCTION_QUESTIONNAIRECONTROL:
				if (!Activa.myMobileManager.identified) Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.MAIN_NOTAVAILABLE);
				else Activa.myUIManager.loadTotalQuestList();
				return;
			default:
				if (!Activa.myMobileManager.identified) Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.MAIN_NOTAVAILABLE);
				Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.MAIN_NOTAVAILABLE);
				return;
		}
	}
	
}
