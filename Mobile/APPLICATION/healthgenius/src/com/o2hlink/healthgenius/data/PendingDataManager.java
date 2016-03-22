package com.o2hlink.healthgenius.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.StringTokenizer;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import com.o2hlink.activ8.client.action.Action;
import com.o2hlink.healthgenius.HealthGenius;
import com.o2hlink.healthgenius.HealthGeniusConfig;
import com.o2hlink.healthgenius.HealthGeniusUtil;
import com.o2hlink.healthgenius.data.calendar.Event;

public class PendingDataManager {
	
	private static PendingDataManager instance = null;
	
	public ArrayList<Event> pendingEvent;
	
	public ArrayList<Action<?>> pendingActions;
	
	public ArrayList<com.o2hlink.healthgenius.patient.Event> pendingPatientEvent;
	
	private PendingDataManager() {
		pendingEvent = new ArrayList<Event>();
		pendingPatientEvent = new ArrayList<com.o2hlink.healthgenius.patient.Event>();
		pendingActions = new ArrayList<Action<?>>();
	}
	
	public PendingDataManager(boolean done) {
		pendingEvent = new ArrayList<Event>();
		pendingPatientEvent = new ArrayList<com.o2hlink.healthgenius.patient.Event>();
		pendingActions = new ArrayList<Action<?>>();
	}
	
	public static PendingDataManager getInstance() {
		if (PendingDataManager.instance == null) PendingDataManager.instance = new PendingDataManager();
		try {
			String filename = "activapendingdata" + HealthGenius.myMobileManager.user.getName() + ".xml";
			String xmlPendingData = "";
    		File folder = HealthGenius.myApp.getDir(HealthGeniusConfig.FILES_FOLDER, 0);
			File pendingData = new File(folder, filename);
			if (!pendingData.exists()) {
				pendingData.createNewFile();
			}
			else {
				FileInputStream fin = new FileInputStream(pendingData);
				InputStreamReader isr = new InputStreamReader(fin,"UTF-8");          
		        char[] inputBuffer = new char[255];
		        while ( isr.read(inputBuffer) != -1) {
		        	xmlPendingData += new String(inputBuffer);    
		        }
		        if (!PendingDataManager.instance.extractPendingDataFromXML(xmlPendingData)) {
		    		HealthGenius.myPendingDataManager.pendingEvent = new ArrayList<Event>();
		    		HealthGenius.myPendingDataManager.pendingPatientEvent = new ArrayList<com.o2hlink.healthgenius.patient.Event>();
		    		HealthGenius.myPendingDataManager.pendingActions = new ArrayList<Action<?>>();
		        	
		        }
		        isr.close();
			}
			instance.getPendingActionsFromFile();
//			FileOutputStream fout = HealthGenius.myApp.openFileOutput(filename, 0);
//			OutputStreamWriter osw = new OutputStreamWriter(fout);
//			osw.write(PendingDataManager.instance.passPendingDataToXML());
//			osw.close();
//			fout.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return PendingDataManager.instance;
	}
	
	private boolean extractPendingDataFromXML(String xml) {
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
	                if (info.getName().equalsIgnoreCase("EVENT")) {
	                	String name = info.getAttributeValue(info.getNamespace(), "NAME");
	                	String desc = info.getAttributeValue(info.getNamespace(), "DESCRIPTION");
	                	int type = Integer.parseInt(info.getAttributeValue(info.getNamespace(), "TYPE"));
	                	int subtype = Integer.parseInt(info.getAttributeValue(info.getNamespace(), "SUBTYPE"));
	                	Date datestart = HealthGeniusUtil.XMLDateToDate(info.getAttributeValue(info.getNamespace(),"DATESTART"));
	                	Date dateend = HealthGeniusUtil.XMLDateToDate(info.getAttributeValue(info.getNamespace(),"DATEEND"));
	                	Event meeting = new Event(HealthGenius.myCalendarManager.getEventId(), name, datestart, type, subtype, 0);
	                	meeting.description = desc;
	                	meeting.dateEnd = dateend;
	                	this.pendingEvent.add(meeting);
	                } else if (info.getName().equalsIgnoreCase("PATEVENT")) {
	                	String name = info.getAttributeValue(info.getNamespace(), "NAME");
	                	String desc = info.getAttributeValue(info.getNamespace(), "DESCRIPTION");
	                	int type = Integer.parseInt(info.getAttributeValue(info.getNamespace(), "TYPE"));
	                	int subtype = Integer.parseInt(info.getAttributeValue(info.getNamespace(), "SUBTYPE"));
	                	Date datestart = HealthGeniusUtil.XMLDateToDate(info.getAttributeValue(info.getNamespace(),"DATESTART"));
	                	Date dateend = HealthGeniusUtil.XMLDateToDate(info.getAttributeValue(info.getNamespace(),"DATEEND"));
	                	Long user = Long.parseLong(info.getAttributeValue(info.getNamespace(), "USER"));
	                	com.o2hlink.healthgenius.patient.Event meeting = new com.o2hlink.healthgenius.patient.Event(HealthGenius.myCalendarManager.getEventId(), name, datestart, type, subtype, 0);
	                	meeting.description = desc;
	                	meeting.dateEnd = dateend;
	                	meeting.userId = user;
	                	this.pendingPatientEvent.add(meeting);
	                } 
	            } else if(event == XmlPullParser.END_TAG) {
	                if (info.getName().equalsIgnoreCase("PENDINGDATA")) {
	                	break;
	                }
	            }
	            event = info.next();
	        }
	        return true;
		} catch (XmlPullParserException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public String passPendingDataToXML() {
		String returned = "";
		returned += "<PENDINGDATA COUNT=\"";
		returned += pendingActions.size() + pendingEvent.size() + pendingPatientEvent.size();
		returned += "\">\n";
		Iterator<Event> eventirator = pendingEvent.iterator();
		while (eventirator.hasNext()) {
			Event event = eventirator.next();
			returned += "<EVENT NAME=\"" + event.name + "\" ";
			returned += "DESCRIPTION=\"" + event.description + "\" ";
			returned += "TYPE=\"" + event.type + "\" ";
			returned += "SUBTYPE=\"" + event.subtype + "\" ";
			returned += "DATESTART=\"" + HealthGeniusUtil.dateToXMLDate(event.date) + "\" ";
			returned += "DATEEND=\"" + HealthGeniusUtil.dateToXMLDate(event.dateEnd) + "\"";
			returned += "/>\n";
		}
		Iterator<com.o2hlink.healthgenius.patient.Event> pateventirator = pendingPatientEvent.iterator();
		while (pateventirator.hasNext()) {
			com.o2hlink.healthgenius.patient.Event event = pateventirator.next();
			returned += "<PATEVENT NAME=\"" + event.name + "\" ";
			returned += "DESCRIPTION=\"" + event.description + "\" ";
			returned += "TYPE=\"" + event.type + "\" ";
			returned += "SUBTYPE=\"" + event.subtype + "\" ";
			returned += "DATESTART=\"" + HealthGeniusUtil.dateToXMLDate(event.date) + "\" ";
			returned += "DATEEND=\"" + HealthGeniusUtil.dateToXMLDate(event.dateEnd) + "\" ";
			returned += "USER=\"" + event.userId + "\"";
			returned += "/>\n";
		}
		returned += "</PENDINGDATA>";
		return returned;
	}
	
	public void passPendingDataToFile() {
		File folder = HealthGenius.myApp.getDir(HealthGeniusConfig.FILES_FOLDER, 0);
		String filename = "activapendingdata" + HealthGenius.myMobileManager.user.getName() + ".xml";
		FileOutputStream fout;
		try {
			fout = new FileOutputStream(new File(folder, filename));
			OutputStreamWriter osw = new OutputStreamWriter(fout);
			osw.write(passPendingDataToXML());
			osw.close();
			fout.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void passPendingActionsToFile() {
		File folder = HealthGenius.myApp.getDir(HealthGeniusConfig.FILES_FOLDER, 0);
		String filename = "activapendingactions" + HealthGenius.myMobileManager.user.getName() + ".xml";
		FileOutputStream fout;
		try {
			fout = new FileOutputStream(new File(folder, filename));
			ObjectOutputStream os = new ObjectOutputStream(fout);
			for (Action<?> action : this.pendingActions) {
				os.writeObject(action);
			}
			os.close();
			fout.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void getPendingActionsFromFile() {
		File folder = HealthGenius.myApp.getDir(HealthGeniusConfig.FILES_FOLDER, 0);
		String filename = "activapendingactions" + HealthGenius.myMobileManager.user.getName() + ".xml";
		FileInputStream fin;
		try {
			File actions = new File(folder, filename);
			if (!actions.exists()) actions.createNewFile();
			else {
				fin = new FileInputStream(actions);
				ObjectInputStream is = new ObjectInputStream(fin);
				Action<?> action = (Action<?>)is.readObject();
				while(action != null) {
					this.pendingActions.add(action);
					action = (Action<?>)is.readObject();
				}
				is.close();
				fin.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
}
