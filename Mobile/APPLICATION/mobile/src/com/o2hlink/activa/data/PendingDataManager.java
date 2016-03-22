package com.o2hlink.activa.data;

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
import com.o2hlink.activ8.client.entity.Contact;
import com.o2hlink.activ8.client.entity.SimpleMessage;
import com.o2hlink.activa.Activa;
import com.o2hlink.activa.ActivaConfig;
import com.o2hlink.activa.ActivaUtil;
import com.o2hlink.activa.data.calendar.Event;
import com.o2hlink.activa.notes.Note;

public class PendingDataManager {
	
	private static PendingDataManager instance = null;
	
	public ArrayList<Note> pendingNotes;
	
	public ArrayList<Event> pendingEvent;
	
	public ArrayList<Message> pendingMessages;
	
	public ArrayList<Action<?>> pendingActions;
	
	public ArrayList<com.o2hlink.activa.patient.Event> pendingPatientEvent;
	
	private PendingDataManager() {
		pendingNotes = new ArrayList<Note>();
		pendingEvent = new ArrayList<Event>();
		pendingPatientEvent = new ArrayList<com.o2hlink.activa.patient.Event>();
		pendingMessages = new ArrayList<Message>();
		pendingActions = new ArrayList<Action<?>>();
	}
	
	public PendingDataManager(boolean done) {
		pendingNotes = new ArrayList<Note>();
		pendingEvent = new ArrayList<Event>();
		pendingPatientEvent = new ArrayList<com.o2hlink.activa.patient.Event>();
		pendingMessages = new ArrayList<Message>();
		pendingActions = new ArrayList<Action<?>>();
	}
	
	public static PendingDataManager getInstance() {
		if (PendingDataManager.instance == null) PendingDataManager.instance = new PendingDataManager();
		try {
			String filename = "activapendingdata" + Activa.myMobileManager.user.getName() + ".xml";
			String xmlPendingData = "";
    		File folder = Activa.myApp.getDir(ActivaConfig.FILES_FOLDER, 0);
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
		    		Activa.myPendingDataManager.pendingNotes = new ArrayList<Note>();
		    		Activa.myPendingDataManager.pendingEvent = new ArrayList<Event>();
		    		Activa.myPendingDataManager.pendingPatientEvent = new ArrayList<com.o2hlink.activa.patient.Event>();
		    		Activa.myPendingDataManager.pendingMessages = new ArrayList<Message>();
		    		Activa.myPendingDataManager.pendingActions = new ArrayList<Action<?>>();
		        	
		        }
		        isr.close();
			}
			instance.getPendingActionsFromFile();
//			FileOutputStream fout = Activa.myApp.openFileOutput(filename, 0);
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
	                if (info.getName().equalsIgnoreCase("NOTE")) {
	                	String string = info.getAttributeValue(info.getNamespace(), "TEXT");
	                	Date date = ActivaUtil.XMLDateToDate(info.getAttributeValue(info.getNamespace(),"DATE"));
	                	this.pendingNotes.add(new Note(Activa.myNotesManager.getNoteId(), string, date));	                	
	                } else if (info.getName().equalsIgnoreCase("MESSAGE")) {
	                	Message message = new Message();
	                	StringTokenizer sentokenizer = new StringTokenizer(info.getAttributeValue(info.getNamespace(), "SENDER"), ",");
	                	message.message.setSender(new Contact(sentokenizer.nextToken(), sentokenizer.nextToken(), sentokenizer.nextToken()));
	                	message.message.getReceivers().clear();
	                	String receiverlist = info.getAttributeValue(info.getNamespace(), "RECEIVERS");
	                	StringTokenizer tokenizer = new StringTokenizer(receiverlist, ";");
	                	while (tokenizer.hasMoreElements()) {
							String recstring = tokenizer.nextToken();
							StringTokenizer rectokenizer = new StringTokenizer(recstring, ",");
	                		message.message.getReceivers().add(new Contact(rectokenizer.nextToken(), rectokenizer.nextToken(), rectokenizer.nextToken()));
	                	}
	                	message.message.setDate(ActivaUtil.XMLDateToDate(info.getAttributeValue(info.getNamespace(),"DATE")));
	                	message.message.setSubject(info.getAttributeValue(info.getNamespace(), "SUBJECT"));
	                	message.content = info.getAttributeValue(info.getNamespace(), "CONTENT");
	                	this.pendingMessages.add(message);	                	
	                } else if (info.getName().equalsIgnoreCase("EVENT")) {
	                	String name = info.getAttributeValue(info.getNamespace(), "NAME");
	                	String desc = info.getAttributeValue(info.getNamespace(), "DESCRIPTION");
	                	int type = Integer.parseInt(info.getAttributeValue(info.getNamespace(), "TYPE"));
	                	int subtype = Integer.parseInt(info.getAttributeValue(info.getNamespace(), "SUBTYPE"));
	                	Date datestart = ActivaUtil.XMLDateToDate(info.getAttributeValue(info.getNamespace(),"DATESTART"));
	                	Date dateend = ActivaUtil.XMLDateToDate(info.getAttributeValue(info.getNamespace(),"DATEEND"));
	                	Event meeting = new Event(Activa.myCalendarManager.getEventId(), name, datestart, type, subtype, 0);
	                	meeting.description = desc;
	                	meeting.dateEnd = dateend;
	                	this.pendingEvent.add(meeting);
	                } else if (info.getName().equalsIgnoreCase("PATEVENT")) {
	                	String name = info.getAttributeValue(info.getNamespace(), "NAME");
	                	String desc = info.getAttributeValue(info.getNamespace(), "DESCRIPTION");
	                	int type = Integer.parseInt(info.getAttributeValue(info.getNamespace(), "TYPE"));
	                	int subtype = Integer.parseInt(info.getAttributeValue(info.getNamespace(), "SUBTYPE"));
	                	Date datestart = ActivaUtil.XMLDateToDate(info.getAttributeValue(info.getNamespace(),"DATESTART"));
	                	Date dateend = ActivaUtil.XMLDateToDate(info.getAttributeValue(info.getNamespace(),"DATEEND"));
	                	Long user = Long.parseLong(info.getAttributeValue(info.getNamespace(), "USER"));
	                	com.o2hlink.activa.patient.Event meeting = new com.o2hlink.activa.patient.Event(Activa.myCalendarManager.getEventId(), name, datestart, type, subtype, 0);
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
		returned += pendingActions.size() + pendingEvent.size() + pendingMessages.size() + pendingPatientEvent.size() + pendingNotes.size();
		returned += "\">\n";
		Iterator<Note> notirator = pendingNotes.iterator();
		while (notirator.hasNext()) {
			Note note = notirator.next();
			returned += "<NOTE TEXT=\"" + note.text + "\" ";
			returned += "DATE=\"" + ActivaUtil.dateToXMLDate(note.date) + "\"";
			returned += "/>\n";
		}
		Iterator<Event> eventirator = pendingEvent.iterator();
		while (eventirator.hasNext()) {
			Event event = eventirator.next();
			returned += "<EVENT NAME=\"" + event.name + "\" ";
			returned += "DESCRIPTION=\"" + event.description + "\" ";
			returned += "TYPE=\"" + event.type + "\" ";
			returned += "SUBTYPE=\"" + event.subtype + "\" ";
			returned += "DATESTART=\"" + ActivaUtil.dateToXMLDate(event.date) + "\" ";
			returned += "DATEEND=\"" + ActivaUtil.dateToXMLDate(event.dateEnd) + "\"";
			returned += "/>\n";
		}
		Iterator<Message> messageiterator = pendingMessages.iterator();
		while (messageiterator.hasNext()) {
			Message message = messageiterator.next();
			returned += "<MESSAGE SENDER=\"" + message.message.getSender().getId() + "," + message.message.getSender().getFirstName() + "," + message.message.getSender().getLastName() + "\" ";
			returned += "RECEIVERS=\"";
			Iterator<Contact> it = message.message.getReceivers().iterator();
			while (it.hasNext()) {
				Contact receiver = it.next();
				returned += receiver.getId() + "," + receiver.getFirstName() + "," + receiver.getLastName();
				if (it.hasNext()) returned += ";";
			}
			returned += "\" ";
			returned += "SUBJECT=\"" + message.message.getSubject() + "\" ";
			returned += "DATE=\"" + ActivaUtil.dateToXMLDate(message.message.getDate()) + "\" ";
			returned += "CONTENT=\"" + message.content + "\"";
			returned += "/>\n";
		}
		Iterator<com.o2hlink.activa.patient.Event> pateventirator = pendingPatientEvent.iterator();
		while (pateventirator.hasNext()) {
			com.o2hlink.activa.patient.Event event = pateventirator.next();
			returned += "<PATEVENT NAME=\"" + event.name + "\" ";
			returned += "DESCRIPTION=\"" + event.description + "\" ";
			returned += "TYPE=\"" + event.type + "\" ";
			returned += "SUBTYPE=\"" + event.subtype + "\" ";
			returned += "DATESTART=\"" + ActivaUtil.dateToXMLDate(event.date) + "\" ";
			returned += "DATEEND=\"" + ActivaUtil.dateToXMLDate(event.dateEnd) + "\" ";
			returned += "USER=\"" + event.userId + "\"";
			returned += "/>\n";
		}
		returned += "</PENDINGDATA>";
		return returned;
	}
	
	public void passPendingDataToFile() {
		File folder = Activa.myApp.getDir(ActivaConfig.FILES_FOLDER, 0);
		String filename = "activapendingdata" + Activa.myMobileManager.user.getName() + ".xml";
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
		File folder = Activa.myApp.getDir(ActivaConfig.FILES_FOLDER, 0);
		String filename = "activapendingactions" + Activa.myMobileManager.user.getName() + ".xml";
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
		File folder = Activa.myApp.getDir(ActivaConfig.FILES_FOLDER, 0);
		String filename = "activapendingactions" + Activa.myMobileManager.user.getName() + ".xml";
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
	
	public void addPendingMessage(SimpleMessage message, String content) {
		Message msg = new Message(message, content);
		this.pendingMessages.add(msg);
	}
	
}
