package com.o2hlink.pimtools.data.calendar;

import java.text.SimpleDateFormat;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import com.o2hlink.pimtools.Activa;
import com.o2hlink.pimtools.background.NotificationThread;
import com.o2hlink.pimtools.exceptions.NotUpdatedException;

public class CalendarManager {
	
	/**
	 * The only instance of the class (a singleton).
	 */
	public static CalendarManager instance;
	
	/**
	 * If the calendar is well built or not.
	 */
	public boolean valid = true;
	
	/**
	 * The set of events at the calendar.
	 */
	public Hashtable<String, Event> events;
	
	/**
	 * The set of service events at the calendar.
	 */
	public Hashtable<String, com.o2hlink.activ8.client.entity.Event> servicevents;
	
	/**
	 * The next event to be announced.
	 */
	public Event nextEvent;
	
	/**
	 * The private constructor.
	 */
	private CalendarManager() {
		this.events = new Hashtable<String,Event>();
		this.servicevents = new Hashtable<String, com.o2hlink.activ8.client.entity.Event>();
	}
	
	/**
	 * The public constructor for tests
	 */
	public CalendarManager(boolean connected) {
		this.events = new Hashtable<String,Event>();
		this.servicevents = new Hashtable<String, com.o2hlink.activ8.client.entity.Event>();
	}
	
	/**
	 * The getInstance method necessary for getting the singleton.
	 */
	public static CalendarManager getInstance() {
		if (CalendarManager.instance == null) CalendarManager.instance = new CalendarManager();
		return CalendarManager.instance;
	}
	
	/**
	 * Method for freeing the instance of the manager.
	 */
	public static void freeInstance() {
		CalendarManager.instance = null;
	}
	
	/**
	 * The method for extracting the Calendar.
	 */
	/*public void extractFromXML (String xml) {
		XmlPullParserFactory factory;
		Event currentEvent;
		try {
			factory = XmlPullParserFactory.newInstance();
	        factory.setNamespaceAware(true);
	        XmlPullParser info = factory.newPullParser();
	        this.valid = true;
	        boolean insideEvent = false;
	        boolean end = true; 
	        info.setInput(new StringReader(xml));
	        int event = info.getEventType();
        	currentEvent = new Event();	
        	currentEvent.setState(2);
	        while (event != XmlPullParser.END_DOCUMENT) {
	            if(event == XmlPullParser.START_DOCUMENT) {
	            } else if(event == XmlPullParser.END_DOCUMENT) {    	
	            } else if(event == XmlPullParser.START_TAG) {
	                if (info.getName().equalsIgnoreCase("AGENDA")) {
	                	if (info.getLineNumber() != 1) {
	                		this.valid = false;
	                		break;
	                	}
	                }
	                else if (info.getName().equalsIgnoreCase("EVENT")) {
	                	if (insideEvent) {
	                		this.valid = false;
	                		break;
	                	} else insideEvent = true;
	                }
	                else if (info.getName().equalsIgnoreCase("IDACCIO")) {
	                	if (!insideEvent) {
	                		this.valid = false;
	                		break;
	                	}
	                	currentEvent.setId(info.getAttributeValue(0));
	                }
	                else if (info.getName().equalsIgnoreCase("NOM")) {
	                	if (!insideEvent) {
	                		this.valid = false;
	                		break;
	                	}
	                	currentEvent.setName(info.getAttributeValue(0));
	                }
	                else if (info.getName().equalsIgnoreCase("DATAHORA")) {
	                	if (!insideEvent) {
	                		this.valid = false;
	                		break;
	                	}
	                	String dataString = info.getAttributeValue(0);
	                	Date date = new Date();
	                	Date now = new Date();
	                	date.setTime(0);
	                	date.setYear(Integer.parseInt(dataString.substring(0,4))-1900);
	                	date.setMonth(Integer.parseInt(dataString.substring(4,6))-1);
	                	date.setDate(Integer.parseInt(dataString.substring(6,8)));
//	                	date.setHours(Integer.parseInt(dataString.substring(8,10)));
	                	date.setHours(Integer.parseInt(dataString.substring(8,10))+2);
	                	date.setMinutes(Integer.parseInt(dataString.substring(10,12)));
	                	date.setSeconds(Integer.parseInt(dataString.substring(12,14)));
	                	currentEvent.setDate(date);
	                }
	                else if (info.getName().equalsIgnoreCase("STATUS")) {
	                	if (!insideEvent) {
	                		this.valid = false;
	                		break;
	                	}
	                	currentEvent.setState(Integer.parseInt(info.getAttributeValue(0)));
	                	if ((currentEvent.getState() == 2)&&(currentEvent.date.before(new Date()))) currentEvent.setState(1); 
	                }
	                else if (info.getName().equalsIgnoreCase("DONE")) {
	                	if (!insideEvent) {
	                		this.valid = false;
	                		break;
	                	}
	                	int aux = Integer.parseInt(info.getAttributeValue(0));
	                	if (aux > 0) currentEvent.setState(0);
	                }
	                else if (info.getName().equalsIgnoreCase("TIPUS")) {
	                	if (!insideEvent) {
	                		this.valid = false;
	                		break;
	                	}
	                	currentEvent.setType(Integer.parseInt(info.getAttributeValue(0)));
	                }
	                else if (info.getName().equalsIgnoreCase("SUBTIPUS")) {
	                	if (!insideEvent) {
	                		this.valid = false;
	                		break;
	                	}
	                	currentEvent.setSubtype(Integer.parseInt(info.getAttributeValue(0)));
	                }
	            } else if(event == XmlPullParser.END_TAG) {
	                if (info.getName().equalsIgnoreCase("EVENT")) {
	                	if (!insideEvent) {
	                		this.valid = false;
	                		break;
	                	}
	                	currentEvent.updateState();
	                	if (!this.events.containsKey(currentEvent.id)) this.events.put(currentEvent.id, currentEvent);
	                	currentEvent = new Event();	
	                	currentEvent.setState(2);
	                	insideEvent = false;
	                }
	                if (info.getName().equalsIgnoreCase("AGENDA")) {
	                	end = true;
	                }
	            }
	            event = info.next();
	        }
	        if (!end) this.valid = false;
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}*/
	
	private String dateToXMLDate(Date date) {
		String returned = "";
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		date.setHours(date.getHours());
		returned = formatter.format(date);
//		returned += date.getYear() + 1900;
//		if (date.getMonth() < 10) returned += "0" + (date.getMonth() + 1);
//		else returned += (date.getMonth() + 1);
//		if (date.getDate() < 10) returned += "0" + date.getDate();
//		else returned += date.getDate();
//		if (date.getHours() < 10) returned += "0" + (date.getHours() + 2);
//		else returned += (date.getHours() + 2);
//		if (date.getMinutes() < 10) returned += "0" + date.getMinutes();
//		else returned += date.getMinutes();
//		if (date.getSeconds() < 10) returned += "0" + date.getSeconds();
//		else returned += date.getSeconds();
		return returned;
	}
	
	public String passCalendarToXML() {
//		Date now = new Date();
		String returned = "";
		Enumeration<Event> enumer = events.elements();
		int count = 0;
		while (enumer.hasMoreElements()) {
			Event currentEvent = enumer.nextElement();
//			if (currentEvent.date.before(now)) {
			if (currentEvent.state != 2) {
				returned += "\t<EVENT>\n\t\t";
				returned += "<IDACCIO VALUE=\"" + currentEvent.id + "\"/>\n\t\t";
				returned += "<NOM VALUE=\"" + currentEvent.name + "\"/>\n\t\t";
				returned += "<DATAHORA VALUE=\"" + dateToXMLDate(new Date(currentEvent.date.getTime()-7200000)) + "\"/>\n\t\t";
				returned += "<STATUS VALUE=\"" + currentEvent.state + "\"/>\n\t\t";
				returned += "<TIPUS VALUE=\"" + currentEvent.type + "\"/>\n\t\t";
				returned += "<SUBTIPUS VALUE=\"" + currentEvent.subtype + "\"/>\n\t";
				returned += "</EVENT>\n";
				count++;
			}
		}
		returned = "<AGENDA COUNT=\"" + count + "\">\n" + returned + "</AGENDA>";
		return returned;
	}
	
//	public void getCalendar() {
//		try {
//			String filename = "activacalendar" + Activa.myMobileManager.user.getName() + ".xml";
//			String xmlCalendar = "";
//			File calendar = new File(Activa.rootFile, filename);
//			if (!calendar.exists()) {
//				calendar.createNewFile();
//			}
//			else {
//				FileInputStream fin = Activa.myApp.openFileInput(filename);
//				InputStreamReader isr = new InputStreamReader(fin,"UTF-8");          
//		        char[] inputBuffer = new char[255];         
//		        while (isr.read(inputBuffer) != -1) {
//		        	xmlCalendar += new String(inputBuffer);    
//		        }
//		        extractFromXML(xmlCalendar);
//		        isr.close();
//			}
//			Activa.myProtocolManager.getCalendar();
//			FileOutputStream fout = Activa.myApp.openFileOutput(filename, 0);
//			OutputStreamWriter osw = new OutputStreamWriter(fout);
//			osw.write(passCalendarToXML());
//			osw.close();
//			fout.close();
//			Thread thread = new Thread(Activa.myNotificationThread, "NOTIFICATIONS");
//			thread.start();
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
	
	public void getCalendar() {
		this.events.clear();
		try {
			Activa.myProtocolManager.getCalendar(this);
		} catch (NotUpdatedException e) {
			Activa.myUIManager.loadTextOnWindow(Activa.myLanguageManager.TEXT_UPDATEVERSION);
			e.printStackTrace();
		}
		NotificationThread notificationThread = NotificationThread.getInstance();
		Thread thread = new Thread(notificationThread, "NOTIFICATIONS");
		thread.start();
	}
	
	public void synchronizeWithAndroidCalendar() {
		boolean calendarFound = false;		
		int auxId = 0;
		String[] projection = new String[] { "_id", "name" };
		Uri calendars = Uri.parse("content://calendar/calendars");		     
		Cursor managedCursor = Activa.myApp.managedQuery(calendars, projection, null, null, null);
		if (managedCursor.moveToFirst()) {
			 String calName; 
			 String calId; 
			 int nameColumn = managedCursor.getColumnIndex("name"); 
			 int idColumn = managedCursor.getColumnIndex("_id");
			 do {
			    calName = managedCursor.getString(nameColumn);
			    calId = managedCursor.getString(idColumn);
			    if (calName.equalsIgnoreCase("activa" + Activa.myMobileManager.user.getName())) {
			    	calendarFound = true;
			    	break;
			    }
		    	auxId++;
			 } while (managedCursor.moveToNext());
			 if (!calendarFound) {
				ContentValues event = new ContentValues();
				event.put("_id", Integer.toString(auxId));
				calId = Integer.toString(auxId);
				event.put("name", "activa" + Activa.myMobileManager.user.getName());
				Activa.myApp.getContentResolver().insert(calendars, event);
			 }
			 Uri eventsUri = Uri.parse("content://calendar/events");
			 Activa.myApp.getContentResolver().delete(eventsUri, "calendar_id = '" + calId + "'", null);
			 Enumeration<Event> enumeration = this.events.elements();
			 while (enumeration.hasMoreElements()) {
				 enumeration.nextElement().synchronizeWithAndroidCalendar(calId);
			 }
		}
	}
	
	public void saveCalendar() {
		String filename = "activacalendar" + Activa.myMobileManager.user.getName() + ".xml";
		FileOutputStream fout;
		try {
			fout = Activa.myApp.openFileOutput(filename, 0);
			OutputStreamWriter osw = new OutputStreamWriter(fout);
			osw.write(passCalendarToXML());
			osw.close();
			fout.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getEventId() {
		long key = 0;
		while (this.events.containsKey(String.valueOf(key))) {
			key--;
		}
		return String.valueOf(key);
	}

	public boolean getCalendar(Date start, Date end) {
		boolean result;
		try {
			result = Activa.myProtocolManager.getCalendar(this, start, end);
		} catch (NotUpdatedException e) {
			Activa.myUIManager.loadTextOnWindow(Activa.myLanguageManager.TEXT_UPDATEVERSION);
			e.printStackTrace();
			result = false;
		}
		return result;
	}

	public boolean AddEvent(com.o2hlink.activ8.client.entity.Event eventtoadd) {
		boolean result;
		try {
			result = Activa.myProtocolManager.AddEvent(eventtoadd);
		} catch (NotUpdatedException e) {
			Activa.myUIManager.loadTextOnWindow(Activa.myLanguageManager.TEXT_UPDATEVERSION);
			e.printStackTrace();
			result = false;
		}
		return result;
	}

	public boolean getNonMeasuringEvents(Date start, Date end) {
		boolean result;
		try {
			result = Activa.myProtocolManager.getNonMeasuringEvents(this, start, end);
		} catch (NotUpdatedException e) {
			Activa.myUIManager.loadTextOnWindow(Activa.myLanguageManager.TEXT_UPDATEVERSION);
			e.printStackTrace();
			result = false;
		}
		return result;
	}
	
}
