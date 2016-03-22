package com.o2hlink.pimtools.ui.widget;

import java.io.IOException;
import java.sql.Struct;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

import android.content.Intent;
import android.net.Uri;

import com.o2hlink.activ8.client.entity.Clinician;
import com.o2hlink.activ8.client.entity.Researcher;
import com.o2hlink.pimtools.Activa;
import com.o2hlink.pimtools.ActivaConfig;
import com.o2hlink.pimtools.background.GetDocuments;
import com.o2hlink.pimtools.background.GetHistory;
import com.o2hlink.pimtools.exterior.ExteriorManager;
import com.o2hlink.pimtools.patient.Patient;

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
	public static final int FUNCTION_DOCUMENTS = 35;
	public static final int FUNCTION_BOOK = 36;
	public static final int FUNCTION_CUSTOM = 37;
	
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
		String lang = Activa.myApp.getResources().getConfiguration().locale.getLanguage();
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
		        		returned.name = getNameFromFunction(lang, returned.function);
		        		if (returned.name.equalsIgnoreCase("Configurable")) returned.function = -1;
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
//				if (!Activa.myMobileManager.identified) Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.MAIN_NOTAVAILABLE);
//				else Activa.myUIManager.loadSensorList();
				return;
			case FUNCTION_PROGRAMS:
//				if (!Activa.myMobileManager.identified) Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.MAIN_NOTAVAILABLE);
//				else Activa.myUIManager.showExternalLinks();
				Activa.myUIManager.loadLinksExtended(Activa.myExteriorManager.externalApplicationsLinked, 
						Activa.myLanguageManager.MAIN_SELECT_LINKEDPROG, "");
				return;
			case FUNCTION_QUESTS:
				if (!Activa.myMobileManager.identified) Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.MAIN_NOTAVAILABLE);
//				else if (Activa.myMobileManager.userForServices instanceof com.o2hlink.activ8.client.entity.Patient) Activa.myUIManager.loadTotalQuestList();
				else Activa.myUIManager.loadSharedQuestionnaires();
				return;
			case FUNCTION_MESSAGES:
				if (!Activa.myMobileManager.identified) Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.MAIN_NOTAVAILABLE);
				else Activa.myUIManager.loadMessageList(0);
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
//				Activa.myExteriorManager.runApplication(ExteriorManager.APP_SKYPE);
				Activa.myUIManager.loadLinksExtended(Activa.myExteriorManager.externalPhoneLinked, 
						Activa.myLanguageManager.MAP_PHONE, "COMMUNICATION");
				return;
			case FUNCTION_SMS:
				if (!Activa.myMobileManager.identified) Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.MAIN_NOTAVAILABLE);
				else Activa.myUIManager.loadMessageList(0);
				return;
			case FUNCTION_EMAIL:
//				Activa.myExteriorManager.runApplication(ExteriorManager.APP_MAIL);
				Activa.myUIManager.loadLinksExtended(Activa.myExteriorManager.externalMailLinked, 
						Activa.myLanguageManager.MAIN_MAIL, "BUSINESS");
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
//				Activa.myExteriorManager.runApplication(ExteriorManager.APP_WIKIPEDIA);
				Activa.myUIManager.loadLinksExtended(Activa.myExteriorManager.externalInfoLinked, 
						Activa.myLanguageManager.MAIN_INFO, "BOOKS_AND_REFERENCE");
				return;
			case FUNCTION_SOCIALNETWORKS:
//				Activa.myUIManager.loadExternalSocialNetworks();
				Activa.myUIManager.loadLinksExtended(Activa.myExteriorManager.externalSocialLinked, 
						Activa.myLanguageManager.MAIN_SOCIAL, "SOCIAL");
				return;
			case FUNCTION_PICTURES:
//				Activa.myUIManager.loadPictures();
				Activa.myUIManager.loadLinksExtended(Activa.myExteriorManager.externalPicturesLinked, 
						Activa.myLanguageManager.MAIN_PICTURES, "PHOTOGRAPHY");
				return;
			case FUNCTION_MUSIC:
//				Activa.myUIManager.loadMusic();
				Activa.myUIManager.loadLinksExtended(Activa.myExteriorManager.externalMusicLinked, 
						Activa.myLanguageManager.MAIN_MUSIC, "MUSIC_AND_AUDIO");
				return;
			case FUNCTION_VIDEO:
//				Activa.myUIManager.loadVideos();
				Activa.myUIManager.loadLinksExtended(Activa.myExteriorManager.externalVideoLinked, 
						Activa.myLanguageManager.MAIN_VIDEOS, "MEDIA_AND_VIDEO");
				return;
			case FUNCTION_MAILS:
//				Activa.myUIManager.loadMails();
				Activa.myUIManager.loadLinksExtended(Activa.myExteriorManager.externalMailLinked, 
						Activa.myLanguageManager.MAIN_MAIL, "BUSINESS");
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
			case FUNCTION_DOCUMENTS:
				if (!Activa.myMobileManager.identified) Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.MAIN_NOTAVAILABLE);
				else {
					GetDocuments sending = new GetDocuments(true);
					Thread thread = new Thread(sending, "GETDOCUMENTS");
					thread.start();
				}
				return;
			case FUNCTION_PATIENTS:
//				if (!Activa.myMobileManager.identified) Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.MAIN_NOTAVAILABLE);
//				else Activa.myUIManager.showPatients();
				return;
			case FUNCTION_PROJECTS:
//				if (!Activa.myMobileManager.identified) Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.MAIN_NOTAVAILABLE);
//				else Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.MAIN_NOTAVAILABLE);
				return;
			case FUNCTION_PAPERS:
//				if (!Activa.myMobileManager.identified) Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.MAIN_NOTAVAILABLE);
//				else Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.MAIN_NOTAVAILABLE);
				return;
			case FUNCTION_BIOLOGICAL:
//				if (!Activa.myMobileManager.identified) Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.MAIN_NOTAVAILABLE);
////				else Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.MAIN_NOTAVAILABLE);
//				else Activa.myUIManager.goToVPHumanWebPage();
				return;
			case FUNCTION_TOOLS:
//				if (!Activa.myMobileManager.identified) Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.MAIN_NOTAVAILABLE);
//				else Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.MAIN_NOTAVAILABLE);
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
//				if (!Activa.myMobileManager.identified) Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.MAIN_NOTAVAILABLE);
//				else if (!(Activa.myMobileManager.userForServices instanceof com.o2hlink.activ8.client.entity.Patient)) Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.MAIN_NOTAVAILABLE);
//				else {
//					GetHistory sending = new GetHistory(new Patient((com.o2hlink.activ8.client.entity.Patient) Activa.myMobileManager.userForServices), true);
//					Thread thread = new Thread(sending, "GETHISTORY");
//					thread.start();
//				}
				return;
			case FUNCTION_QUESTIONNAIRECONTROL:
				if (!Activa.myMobileManager.identified) Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.MAIN_NOTAVAILABLE);
				else Activa.myUIManager.loadTotalQuestList();
				return;
			default:
				Activa.myUIManager.loadTextOnWindow(Activa.myLanguageManager.TEXT_CONFIGURABLE);
				return;
		}
	}

	public boolean passSubenvironemntToXML(XmlSerializer serializer) {
		try {
			serializer.startTag("", "WIDGET");
		    serializer.attribute("", "NAME", this.name);
		    serializer.attribute("", "WIDTH", Integer.toString(this.width));
		    serializer.attribute("", "HEIGHT", Integer.toString(this.height));
		    serializer.attribute("", "TOP", Integer.toString(this.top));
		    serializer.attribute("", "LEFT", Integer.toString(this.left));
		    serializer.attribute("", "FUNCTION", Integer.toString(this.function));
		    serializer.endTag("", "WIDGET");
		    return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static String getNameFromFunction(String lang, int function) {
		if (lang.equals("es")) {
			switch (function) {
				case FUNCTION_CALENDAR:
					return "Calendario";
				case FUNCTION_SENSORS:
					return "Configurable";
				case FUNCTION_PROGRAMS:
					return "Programas";
				case FUNCTION_QUESTS:
					return "Cuestionarios";
				case FUNCTION_MESSAGES:
					return "Mensajes";
				case FUNCTION_NOTES:
					return "Notas";
				case FUNCTION_MAP:
					return "Mapa";
				case FUNCTION_NEWS:
					return "Noticias";
				case FUNCTION_PHONE:
					return "Teléfono";
				case FUNCTION_SMS:
					return "SMS";
				case FUNCTION_EMAIL:
					return "Email";
				case FUNCTION_GMAIL:
					return "Gmail";
				case FUNCTION_GOOGLE:
					return "Google";
				case FUNCTION_BING:
					return "Bing";
				case FUNCTION_WIKIPEDIA:
					return "Informacion";
				case FUNCTION_SOCIALNETWORKS:
					return "Redes sociales";
				case FUNCTION_PICTURES:
					return "Imagenes";
				case FUNCTION_MUSIC:
					return "Musica";
				case FUNCTION_VIDEO:
					return "Video";
				case FUNCTION_MAILS:
					return "Mails";
				case FUNCTION_CELLULAR:
					return "Móvil";
				case FUNCTION_PATIENTS:
					return "Configurable";
				case FUNCTION_PROJECTS:
					return "Configurable";
				case FUNCTION_PAPERS:
					return "Configurable";
				case FUNCTION_BIOLOGICAL:
					return "Configurable";
				case FUNCTION_TOOLS:
					return "Configurable";
				case FUNCTION_HISTORY:
					return "Configurable";
				case FUNCTION_DOCUMENTS:
					return "Descargas";
				default:
					return "Configurable";
			}
		} else {
			switch (function) {
				case FUNCTION_CALENDAR:
					return "Calendar";
				case FUNCTION_SENSORS:
					return "Configurable";
				case FUNCTION_PROGRAMS:
					return "Programs";
				case FUNCTION_QUESTS:
					return "Questionnaires";
				case FUNCTION_MESSAGES:
					return "Messages";
				case FUNCTION_NOTES:
					return "Notes";
				case FUNCTION_MAP:
					return "Map";
				case FUNCTION_NEWS:
					return "News";
				case FUNCTION_PHONE:
					return "Phone";
				case FUNCTION_SMS:
					return "SMS";
				case FUNCTION_EMAIL:
					return "Email";
				case FUNCTION_GMAIL:
					return "Gmail";
				case FUNCTION_GOOGLE:
					return "Google";
				case FUNCTION_BING:
					return "Bing";
				case FUNCTION_WIKIPEDIA:
					return "Information";
				case FUNCTION_SOCIALNETWORKS:
					return "Social networks";
				case FUNCTION_PICTURES:
					return "Pictures";
				case FUNCTION_MUSIC:
					return "Music";
				case FUNCTION_VIDEO:
					return "Video";
				case FUNCTION_MAILS:
					return "Mails";
				case FUNCTION_CELLULAR:
					return "Cellular";
				case FUNCTION_PATIENTS:
					return "Configurable";
				case FUNCTION_PROJECTS:
					return "Configurable";
				case FUNCTION_PAPERS:
					return "Configurable";
				case FUNCTION_BIOLOGICAL:
					return "Configurable";
				case FUNCTION_TOOLS:
					return "Configurable";
				case FUNCTION_HISTORY:
					return "Configurable";
				case FUNCTION_DOCUMENTS:
					return "Downloads";
				default:
					return "Configurable";
			}
		}
	}
	
	public static CharSequence[] getFunctionNames() {
		String lang = Activa.myApp.getResources().getConfiguration().locale.getLanguage();
		if (Activa.myMobileManager.userForServices instanceof Patient) {
			if (lang.equals("es")) {
				CharSequence[] functionNames = {"Calendario", "Programas(C)", "Cuestionarios",
						"Mensajes", "Notas", "Mapa", "Noticias", "Teléfono(C)", "Google", "Bing",
						"Informacion(C)", "Redes sociales(C)", "Imagenes(C)", "Musica(C)", "Video(C)", "Mails(C)", "Móvil", "Descargas"};
				Integer[] functionIDs = {FUNCTION_CALENDAR, FUNCTION_PROGRAMS, FUNCTION_QUESTS,
						FUNCTION_MESSAGES, FUNCTION_NOTES, FUNCTION_MAP, FUNCTION_NEWS, FUNCTION_PHONE, 
						FUNCTION_GOOGLE, FUNCTION_BING, FUNCTION_WIKIPEDIA, 
						FUNCTION_SOCIALNETWORKS, FUNCTION_PICTURES, FUNCTION_MUSIC, FUNCTION_VIDEO, FUNCTION_MAILS,
						FUNCTION_CELLULAR, FUNCTION_DOCUMENTS};
				return functionNames;
			} else {
				CharSequence[] functionNames = {"Calendar", "Programs(C)", "Questionnaires",
						"Messages", "Notes", "Map", "News", "Phone(C)", "Google", "Bing",
						"Information(C)", "Social networks(C)", "Pictures(C)", "Music(C)", "Video(C)", "Mails(C)", "Cellular", "Downloads"};
				Integer[] functionIDs = {FUNCTION_CALENDAR, FUNCTION_PROGRAMS, FUNCTION_QUESTS,
						FUNCTION_MESSAGES, FUNCTION_NOTES, FUNCTION_MAP, FUNCTION_NEWS, FUNCTION_PHONE, 
						FUNCTION_GOOGLE, FUNCTION_BING, FUNCTION_WIKIPEDIA, 
						FUNCTION_SOCIALNETWORKS, FUNCTION_PICTURES, FUNCTION_MUSIC, FUNCTION_VIDEO, FUNCTION_MAILS,
						FUNCTION_CELLULAR, FUNCTION_DOCUMENTS};
				return functionNames;
			}
		}
		else if (Activa.myMobileManager.userForServices instanceof Clinician) {
			if (lang.equals("es")) {
				CharSequence[] functionNames = {"Calendario", "Programas(C)", "Cuestionarios",
						"Mensajes", "Notas", "Mapa", "Noticias", "Teléfono(C)", "Google", "Bing",
						"Informacion(C)", "Redes sociales(C)", "Imagenes(C)", "Musica(C)", "Video(C)", "Mails(C)", "Móvil", "Descargas"};
				Integer[] functionIDs = {FUNCTION_CALENDAR, FUNCTION_PROGRAMS, FUNCTION_QUESTS,
						FUNCTION_MESSAGES, FUNCTION_NOTES, FUNCTION_MAP, FUNCTION_NEWS, FUNCTION_PHONE, 
						FUNCTION_GOOGLE, FUNCTION_BING, FUNCTION_WIKIPEDIA, 
						FUNCTION_SOCIALNETWORKS, FUNCTION_PICTURES, FUNCTION_MUSIC, FUNCTION_VIDEO, FUNCTION_MAILS,
						FUNCTION_CELLULAR, FUNCTION_DOCUMENTS};
				return functionNames;
			} else {
				CharSequence[] functionNames = {"Calendar", "Programs(C)", "Questionnaires",
						"Messages", "Notes", "Map", "News", "Phone(C)", "Google", "Bing",
						"Information(C)", "Social networks(C)", "Pictures(C)", "Music(C)", "Video(C)", "Mails(C)", "Cellular", "Downloads"};
				Integer[] functionIDs = {FUNCTION_CALENDAR, FUNCTION_PROGRAMS, FUNCTION_QUESTS,
						FUNCTION_MESSAGES, FUNCTION_NOTES, FUNCTION_MAP, FUNCTION_NEWS, FUNCTION_PHONE, 
						FUNCTION_GOOGLE, FUNCTION_BING, FUNCTION_WIKIPEDIA, 
						FUNCTION_SOCIALNETWORKS, FUNCTION_PICTURES, FUNCTION_MUSIC, FUNCTION_VIDEO, FUNCTION_MAILS,
						FUNCTION_CELLULAR, FUNCTION_DOCUMENTS};
				return functionNames;
			}
		}
		else if (Activa.myMobileManager.userForServices instanceof Researcher) {
			if (lang.equals("es")) {
				CharSequence[] functionNames = {"Calendario", "Programas(C)", "Cuestionarios",
						"Mensajes", "Notas", "Mapa", "Noticias", "Teléfono(C)", "Google", "Bing",
						"Informacion(C)", "Redes sociales(C)", "Imagenes(C)", "Musica(C)", "Video(C)", "Mails(C)", "Móvil",
						"Descargas"};
				Integer[] functionIDs = {FUNCTION_CALENDAR, FUNCTION_PROGRAMS, FUNCTION_QUESTS,
						FUNCTION_MESSAGES, FUNCTION_NOTES, FUNCTION_MAP, FUNCTION_NEWS, FUNCTION_PHONE, 
						FUNCTION_GOOGLE, FUNCTION_BING, FUNCTION_WIKIPEDIA, 
						FUNCTION_SOCIALNETWORKS, FUNCTION_PICTURES, FUNCTION_MUSIC, FUNCTION_VIDEO, FUNCTION_MAILS,
						FUNCTION_CELLULAR, FUNCTION_DOCUMENTS};
				return functionNames;
			} else {
				CharSequence[] functionNames = {"Calendar", "Programs(C)", "Questionnaires",
						"Messages", "Notes", "Map", "News", "Phone(C)", "Google", "Bing",
						"Information(C)", "Social networks(C)", "Pictures(C)", "Music(C)", "Video(C)", "Mails(C)", "Cellular",
						"Downloads"};
				Integer[] functionIDs = {FUNCTION_CALENDAR, FUNCTION_PROGRAMS, FUNCTION_QUESTS,
						FUNCTION_MESSAGES, FUNCTION_NOTES, FUNCTION_MAP, FUNCTION_NEWS, FUNCTION_PHONE, 
						FUNCTION_GOOGLE, FUNCTION_BING, FUNCTION_WIKIPEDIA, 
						FUNCTION_SOCIALNETWORKS, FUNCTION_PICTURES, FUNCTION_MUSIC, FUNCTION_VIDEO, FUNCTION_MAILS,
						FUNCTION_CELLULAR, FUNCTION_DOCUMENTS};
				return functionNames;
			}
		}
		else {
			if (lang.equals("es")) {
				CharSequence[] functionNames = {"Calendario", "Programas(C)", "Cuestionarios",
						"Mensajes", "Notas", "Mapa", "Noticias", "Teléfono(C)", "Google", "Bing",
						"Informacion(C)", "Redes sociales(C)", "Imagenes(C)", "Musica(C)", "Video(C)", "Mails(C)", "Móvil", "Descargas"};
				Integer[] functionIDs = {FUNCTION_CALENDAR, FUNCTION_PROGRAMS, FUNCTION_QUESTS,
						FUNCTION_MESSAGES, FUNCTION_NOTES, FUNCTION_MAP, FUNCTION_NEWS, FUNCTION_PHONE,
						FUNCTION_GOOGLE, FUNCTION_BING, FUNCTION_WIKIPEDIA, 
						FUNCTION_SOCIALNETWORKS, FUNCTION_PICTURES, FUNCTION_MUSIC, FUNCTION_VIDEO, FUNCTION_MAILS,
						FUNCTION_CELLULAR, FUNCTION_DOCUMENTS};
				return functionNames;
			} else {
				CharSequence[] functionNames = {"Calendar", "Programs(C)", "Questionnaires",
						"Messages", "Notes", "Map", "News", "Phone(C)", "Google", "Bing",
						"Information(C)", "Social networks(C)", "Pictures(C)", "Music(C)", "Video(C)", "Mails(C)", "Cellular","Downloads"};
				Integer[] functionIDs = {FUNCTION_CALENDAR, FUNCTION_PROGRAMS, FUNCTION_QUESTS,
						FUNCTION_MESSAGES, FUNCTION_NOTES, FUNCTION_MAP, FUNCTION_NEWS, FUNCTION_PHONE, FUNCTION_GOOGLE,
						FUNCTION_BING, FUNCTION_WIKIPEDIA, 
						FUNCTION_SOCIALNETWORKS, FUNCTION_PICTURES, FUNCTION_MUSIC, FUNCTION_VIDEO, FUNCTION_MAILS,
						FUNCTION_CELLULAR, FUNCTION_DOCUMENTS};
				return functionNames;
			}
		}
	}
	
	public static Integer[] getFunctionIDs() {
		String lang = Activa.myApp.getResources().getConfiguration().locale.getLanguage();
		if (Activa.myMobileManager.userForServices instanceof Patient) {
			if (lang.equals("es")) {
				CharSequence[] functionNames = {"Calendario", "Programas", "Cuestionarios",
						"Mensajes", "Notas", "Mapa", "Noticias", "Teléfono", "Google", "Bing",
						"Wikipedia", "Redes sociales", "Imagenes", "Musica", "Video", "Mails", "Móvil", "Descargas"};
				Integer[] functionIDs = {FUNCTION_CALENDAR, FUNCTION_PROGRAMS, FUNCTION_QUESTS,
						FUNCTION_MESSAGES, FUNCTION_NOTES, FUNCTION_MAP, FUNCTION_NEWS, FUNCTION_PHONE, 
						FUNCTION_GOOGLE, FUNCTION_BING, FUNCTION_WIKIPEDIA, 
						FUNCTION_SOCIALNETWORKS, FUNCTION_PICTURES, FUNCTION_MUSIC, FUNCTION_VIDEO, FUNCTION_MAILS,
						FUNCTION_CELLULAR, FUNCTION_DOCUMENTS};
				return functionIDs;
			} else {
				CharSequence[] functionNames = {"Calendar", "Programs", "Questionnaires",
						"Messages", "Notes", "Map", "News", "Phone", "Google", "Bing",
						"Wikipedia", "Social networks", "Pictures", "Music", "Video", "Mails", "Cellular", "Downloads"};
				Integer[] functionIDs = {FUNCTION_CALENDAR, FUNCTION_PROGRAMS, FUNCTION_QUESTS,
						FUNCTION_MESSAGES, FUNCTION_NOTES, FUNCTION_MAP, FUNCTION_NEWS, FUNCTION_PHONE, 
						FUNCTION_GOOGLE, FUNCTION_BING, FUNCTION_WIKIPEDIA, 
						FUNCTION_SOCIALNETWORKS, FUNCTION_PICTURES, FUNCTION_MUSIC, FUNCTION_VIDEO, FUNCTION_MAILS,
						FUNCTION_CELLULAR, FUNCTION_DOCUMENTS};
				return functionIDs;
			}
		}
		else if (Activa.myMobileManager.userForServices instanceof Clinician) {
			if (lang.equals("es")) {
				CharSequence[] functionNames = {"Calendario", "Programas", "Cuestionarios",
						"Mensajes", "Notas", "Mapa", "Noticias", "Teléfono", "Google", "Bing",
						"Wikipedia", "Redes sociales", "Imagenes", "Musica", "Video", "Mails", "Móvil", "Descargas"};
				Integer[] functionIDs = {FUNCTION_CALENDAR, FUNCTION_PROGRAMS, FUNCTION_QUESTS,
						FUNCTION_MESSAGES, FUNCTION_NOTES, FUNCTION_MAP, FUNCTION_NEWS, FUNCTION_PHONE, 
						FUNCTION_GOOGLE, FUNCTION_BING, FUNCTION_WIKIPEDIA, 
						FUNCTION_SOCIALNETWORKS, FUNCTION_PICTURES, FUNCTION_MUSIC, FUNCTION_VIDEO, FUNCTION_MAILS,
						FUNCTION_CELLULAR, FUNCTION_DOCUMENTS};
				return functionIDs;
			} else {
				CharSequence[] functionNames = {"Calendar", "Programs", "Questionnaires",
						"Messages", "Notes", "Map", "News", "Phone", "Google", "Bing",
						"Wikipedia", "Social networks", "Pictures", "Music", "Video", "Mails", "Cellular", "Downloads"};
				Integer[] functionIDs = {FUNCTION_CALENDAR, FUNCTION_PROGRAMS, FUNCTION_QUESTS,
						FUNCTION_MESSAGES, FUNCTION_NOTES, FUNCTION_MAP, FUNCTION_NEWS, FUNCTION_PHONE, 
						FUNCTION_GOOGLE, FUNCTION_BING, FUNCTION_WIKIPEDIA, 
						FUNCTION_SOCIALNETWORKS, FUNCTION_PICTURES, FUNCTION_MUSIC, FUNCTION_VIDEO, FUNCTION_MAILS,
						FUNCTION_CELLULAR, FUNCTION_DOCUMENTS};
				return functionIDs;
			}
		}
		else if (Activa.myMobileManager.userForServices instanceof Researcher) {
			if (lang.equals("es")) {
				CharSequence[] functionNames = {"Calendario", "Programas", "Cuestionarios",
						"Mensajes", "Notas", "Mapa", "Noticias", "Teléfono", "Google", "Bing",
						"Wikipedia", "Redes sociales", "Imagenes", "Musica", "Video", "Mails", "Móvil",
						"Descargas"};
				Integer[] functionIDs = {FUNCTION_CALENDAR, FUNCTION_PROGRAMS, FUNCTION_QUESTS,
						FUNCTION_MESSAGES, FUNCTION_NOTES, FUNCTION_MAP, FUNCTION_NEWS, FUNCTION_PHONE, 
						FUNCTION_GOOGLE, FUNCTION_BING, FUNCTION_WIKIPEDIA, 
						FUNCTION_SOCIALNETWORKS, FUNCTION_PICTURES, FUNCTION_MUSIC, FUNCTION_VIDEO, FUNCTION_MAILS,
						FUNCTION_CELLULAR, FUNCTION_DOCUMENTS};
				return functionIDs;
			} else {
				CharSequence[] functionNames = {"Calendar", "Programs", "Questionnaires",
						"Messages", "Notes", "Map", "News", "Phone", "Google", "Bing",
						"Wikipedia", "Social networks", "Pictures", "Music", "Video", "Mails", "Cellular",
						"Downloads"};
				Integer[] functionIDs = {FUNCTION_CALENDAR, FUNCTION_PROGRAMS, FUNCTION_QUESTS,
						FUNCTION_MESSAGES, FUNCTION_NOTES, FUNCTION_MAP, FUNCTION_NEWS, FUNCTION_PHONE, 
						FUNCTION_GOOGLE, FUNCTION_BING, FUNCTION_WIKIPEDIA, 
						FUNCTION_SOCIALNETWORKS, FUNCTION_PICTURES, FUNCTION_MUSIC, FUNCTION_VIDEO, FUNCTION_MAILS,
						FUNCTION_CELLULAR, FUNCTION_DOCUMENTS};
				return functionIDs;
			}
		}
		else {
			if (lang.equals("es")) {
				CharSequence[] functionNames = {"Calendario", "Programas", "Cuestionarios",
						"Mensajes", "Notas", "Mapa", "Noticias", "Teléfono", "Google", "Bing",
						"Wikipedia", "Redes sociales", "Imagenes", "Musica", "Video", "Mails", "Móvil", "Descargas"};
				Integer[] functionIDs = {FUNCTION_CALENDAR, FUNCTION_PROGRAMS, FUNCTION_QUESTS,
						FUNCTION_MESSAGES, FUNCTION_NOTES, FUNCTION_MAP, FUNCTION_NEWS, FUNCTION_PHONE,
						FUNCTION_GOOGLE, FUNCTION_BING, FUNCTION_WIKIPEDIA, 
						FUNCTION_SOCIALNETWORKS, FUNCTION_PICTURES, FUNCTION_MUSIC, FUNCTION_VIDEO, FUNCTION_MAILS,
						FUNCTION_CELLULAR, FUNCTION_DOCUMENTS};
				return functionIDs;
			} else {
				CharSequence[] functionNames = {"Calendar", "Programs", "Questionnaires",
						"Messages", "Notes", "Map", "News", "Phone", "Google", "Bing",
						"Wikipedia", "Social networks", "Pictures", "Music", "Video", "Mails", "Cellular","Downloads"};
				Integer[] functionIDs = {FUNCTION_CALENDAR, FUNCTION_PROGRAMS, FUNCTION_QUESTS,
						FUNCTION_MESSAGES, FUNCTION_NOTES, FUNCTION_MAP, FUNCTION_NEWS, FUNCTION_PHONE, FUNCTION_GOOGLE,
						FUNCTION_BING, FUNCTION_WIKIPEDIA, 
						FUNCTION_SOCIALNETWORKS, FUNCTION_PICTURES, FUNCTION_MUSIC, FUNCTION_VIDEO, FUNCTION_MAILS,
						FUNCTION_CELLULAR, FUNCTION_DOCUMENTS};
				return functionIDs;
			}
		}
	}
	
}
