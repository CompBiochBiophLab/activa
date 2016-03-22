package com.o2hlink.activa.connection;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

//import com.gdevelop.gwt.syncrpc.SyncProxy;
import android.graphics.drawable.Drawable;

import com.gdevelop.gwt.syncrpc.SyncProxy;
import com.google.gwt.user.client.rpc.InvocationException;
import com.o2hlink.activ8.client.action.ExplorationListGetAction;
import com.o2hlink.activ8.client.action.HistoryGetAction;
import com.o2hlink.activ8.client.action.MessageNewAction;
import com.o2hlink.activ8.client.action.NoteNewAction;
import com.o2hlink.activ8.client.action.QuestionnaireListGetAction;
import com.o2hlink.activ8.client.action.SampleListGetAction;
import com.o2hlink.activ8.client.action.SpirometryGetAction;
import com.o2hlink.activ8.client.action.UserAddAction;
import com.o2hlink.activ8.client.action.UserListGetAction;
import com.o2hlink.activ8.client.action.UserLoginAction;
import com.o2hlink.activ8.client.action.UserNewAction;
import com.o2hlink.activ8.client.action.UserSearchAction;
import com.o2hlink.activ8.client.entity.Clinician;
import com.o2hlink.activ8.client.entity.Exploration;
import com.o2hlink.activ8.client.entity.HistoryRecord;
import com.o2hlink.activ8.client.entity.Note;
import com.o2hlink.activ8.client.entity.Patient;
import com.o2hlink.activ8.client.entity.Pulseoxymetry;
import com.o2hlink.activ8.client.entity.Researcher;
import com.o2hlink.activ8.client.entity.Sample;
import com.o2hlink.activ8.client.entity.SimpleMessage;
import com.o2hlink.activ8.client.entity.SixMinutesWalk;
import com.o2hlink.activ8.client.entity.Spirometry;
import com.o2hlink.activ8.client.entity.SpirometryFlow;
import com.o2hlink.activ8.client.entity.User;
import com.o2hlink.activ8.client.exception.ServerException;
import com.o2hlink.activ8.client.entity.Array;
import com.o2hlink.activ8.client.service.Service;
import com.o2hlink.activ8.common.entity.Measurement;
import com.o2hlink.activ8.common.entity.Sex;
import com.o2hlink.activa.Activa;
import com.o2hlink.activa.ActivaConfig;
import com.o2hlink.activa.ActivaUtil;
import com.o2hlink.activa.R;
import com.o2hlink.activa.connection.message.EventMessage;
import com.o2hlink.activa.connection.message.MapRequest;
import com.o2hlink.activa.connection.message.NoteMessage;
import com.o2hlink.activa.connection.message.O2MessageRequest;
import com.o2hlink.activa.connection.message.O2MessageSent;
import com.o2hlink.activa.connection.message.QuestMessage;
import com.o2hlink.activa.connection.message.Request;
import com.o2hlink.activa.connection.message.SensorMessage;
import com.o2hlink.activa.data.calendar.Event;
import com.o2hlink.activa.data.message.Contact;
import com.o2hlink.activa.data.message.O2UnregisteredMessage;
import com.o2hlink.activa.data.questionnaire.Questionnaire;
import com.o2hlink.activa.data.sensor.Sensor;
import com.o2hlink.activa.exceptions.ConnectionException;
import com.o2hlink.activa.exceptions.LoginException;
import com.o2hlink.activa.exceptions.PasswordException;
import com.o2hlink.activa.patient.ExerciseSample;
import com.o2hlink.activa.patient.PulseoximetrySample;
import com.o2hlink.activa.patient.SpirometrySample;

/**
 * @author Adrian Rejas<P>
 * 
 * This class deals with the protocol management. The application will deal with the server 
 * through this class. In a way, this is the protocol manager.<P>
 * This class is a singleton, that is, there can be just one instance of this manager 
 * during the running of the application.
 */
public class ProtocolManager {
	
	/**
	 * The instance of the connection with the server.
	 */
	public Connection serverConnection;
	
	/**
	 * The instance of the service offered by the server.
	 */
	public Service service;
	
	/**
	 * The state of the connection (true for connected and false for not connected)
	 */
	public boolean connected;
	
	/**
	 * The instance of the protocol manager (This class).
	 */
	public static ProtocolManager instance;
	
	/**
	 * The public constructor for the class.
	 */
	private ProtocolManager() {
		this.connected = false;
		serverConnection = Connection.getInstance(ActivaConfig.SERVERIP, ActivaConfig.SERVERPORT, ActivaConfig.SERVERPATH);
		this.service = (Service) SyncProxy.newProxyInstance(Service.class,ActivaConfig.SERVICES_URL,
        		"service", "25380C70BBABEE198469E1E2FCBD7FE7");
	}
	
	/**
	 * The method for getting the only instance of the class.
	 * @return The instance of the protocol manager.
	 */
	public static ProtocolManager getInstance() {
		if (ProtocolManager.instance == null) {
			ProtocolManager.instance = new ProtocolManager();
		}
		return ProtocolManager.instance;
	}
	
	/**
	 * Method for freeing the instance of the manager.
	 */
	public static void freeInstance() {
		ProtocolManager.instance = null;
	}
	
	/**
	 * This method will deal with the sending of a request and the receiving of a response.
	 * This method will be used by higher level methods of the protocol manager.
	 * @param type The type of request.
	 * @return A string representing the answer received at sending the request.
	 * @throws ConnectionException 
	 * @throws LoginException 
	 * @throws PasswordException 
	 */
	public String sendRequest(String type) throws ConnectionException, LoginException, PasswordException { 
		String answer = null;
		byte[] tosend, toreceive;
		Activa.myMobileManager.device.updateDateTime();
		Request request = new Request(type);
//		Message message = new Message(request.toMessageString());
			try {
//				tosend = message.toMessageString().getBytes();
				tosend = request.toMessageString().getBytes();
				toreceive = serverConnection.submitUnsecure(tosend, "?user=" + Activa.myMobileManager.user.getName() + "&pwd=" + Activa.myMobileManager.user.getPassword());
				answer = new String(toreceive, "UTF-8");
			} catch (IOException e) {
				throw new ConnectionException(e.getMessage(), 0);
			} catch (ConnectionException e) {
				throw new ConnectionException(e.getMessage(), 0);
			} catch (LoginException e) {
				throw new LoginException(e.getMessage());
			} catch (PasswordException e) {
				throw new PasswordException(e.getMessage());
			}
		
		return answer;
	}
	
	/**
	 * This method will deal with the sending of a request and the receiving of a response.
	 * This method will be used by higher level methods of the protocol manager.
	 * @param type The type of request.
	 * @return A string representing the answer received at sending the request.
	 * @throws ConnectionException 
	 * @throws LoginException 
	 * @throws PasswordException 
	 */
	public String sendRegisterRequest() throws ConnectionException, LoginException, PasswordException { 
		String answer = null;
		byte[] tosend, toreceive;
		Activa.myMobileManager.device.updateDateTime();
//		Request request = new Request(Request.TYPE_CONFIG, true);
		Request request = new Request(Request.TYPE_CONFIG, true);
//		Message message = new Message(request.toMessageString());
			try {
//				tosend = message.toMessageString().getBytes();
				tosend = request.toMessageString().getBytes();
				toreceive = serverConnection.submitUnsecure(tosend, "?user=" + Activa.myMobileManager.user.getName() + "&pwd=" + Activa.myMobileManager.user.getPassword());
				answer = new String(toreceive, "UTF-8");
			} catch (IOException e) {
				throw new ConnectionException(e.getMessage(), 0);
			} catch (ConnectionException e) {
				throw new ConnectionException(e.getMessage(), 0);
			} catch (LoginException e) {
				throw new LoginException(e.getMessage());
			} catch (PasswordException e) {
				throw new PasswordException(e.getMessage());
			}
		
		return answer;
	}
	
	/**
	 * This method will deal with the sending of a request and the receiving of a response.
	 * This method will be used by higher level methods of the protocol manager.
	 * @param type The type of request.
	 * @return A string representing the answer received at sending the request.
	 * @throws ConnectionException 
	 * @throws LoginException 
	 * @throws PasswordException 
	 */
	public String sendUserExistanceRequest() throws ConnectionException, LoginException, PasswordException { 
		String answer = null;
		byte[] tosend, toreceive;
		Activa.myMobileManager.device.updateDateTime();
//		Request request = new Request(Request.TYPE_CONFIG, true);
		Request request = new Request(Request.TYPE_CONFIG, false);
//		Message message = new Message(request.toMessageString());
			try {
//				tosend = message.toMessageString().getBytes();
				tosend = request.toMessageString().getBytes();
				toreceive = serverConnection.submitUnsecure(tosend, "?user=" + Activa.myMobileManager.user.getName() + "&pwd=" + Activa.myMobileManager.user.getPassword());
				answer = new String(toreceive);
			} catch (IOException e) {
				throw new ConnectionException(e.getMessage(), 0);
			} catch (ConnectionException e) {
				throw new ConnectionException(e.getMessage(), 0);
			} catch (LoginException e) {
				throw new LoginException(e.getMessage());
			} catch (PasswordException e) {
				throw new PasswordException(e.getMessage());
			}
		
		return answer;
	}
	
	/**
	 * This method deals with the obtaining of the questionnaires and their parsing by
	 * the quest manager.
	 */
	public boolean getQuestionnaires() {
		String xmlQuestionnaires;
		try {
			xmlQuestionnaires = sendRequest(Request.TYPE_QUEST);
			Activa.myQuestManager.extractFromXML(xmlQuestionnaires);
		} catch (ConnectionException e) {
			e.printStackTrace();
			return false;
		} catch (LoginException e) {
			e.printStackTrace();
			return false;
		} catch (PasswordException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public void getQuestionnaires2() {
		String xmlQuestionnaires = "";
		try {
			InputStream fIn = Activa.myApp.getResources().openRawResource(R.raw.cuestionarios);
	        InputStreamReader isr = new InputStreamReader(fIn,"UTF-8");          
	        char[] inputBuffer = new char[255];              
	        while (isr.read(inputBuffer) != -1) {
	        	xmlQuestionnaires += new String(inputBuffer);
	        }
			Activa.myQuestManager.extractFromXML(xmlQuestionnaires);    
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This method deals with the obtaining of the marks to be shown at the Map winget.
	 */
	public boolean getMapMarks() {
		byte[] tosend, toreceive;
		Activa.myMobileManager.device.updateDateTime();
		MapRequest request = new MapRequest();
		try {
			tosend = request.toMessageString().getBytes();
			toreceive = serverConnection.submitUnsecure(tosend, "?user=" + Activa.myMobileManager.user.getName() + "&pwd=" + Activa.myMobileManager.user.getPassword());
			Activa.myMapManager.extractMarksFromXML(new String(toreceive));
			return true;
		} catch (ConnectionException e) {
			e.printStackTrace();
			return false;
		} catch (LoginException e) {
			e.printStackTrace();
			return false;
		} catch (PasswordException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public void getMapMarks2() {
		String xmlMap = "";
		try {
			InputStream fIn = Activa.myApp.getResources().openRawResource(R.raw.mapmarks);
	        InputStreamReader isr = new InputStreamReader(fIn,"UTF-8");          
	        char[] inputBuffer = new char[255];              
	        while (isr.read(inputBuffer) != -1) {
	        	xmlMap += new String(inputBuffer);
	        }
	        Activa.myMapManager.extractMarksFromXML(xmlMap);    
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * This method deals with the obtaining of the message directed to the user.
	 */
//	public boolean getContacts() {
//		byte[] tosend, toreceive;
//		Activa.myMobileManager.device.updateDateTime();
//		UserListGetAction getUsers = new UserListGetAction();
//		try {
//			Array<User> userlist = service.dispatch(getUsers);
//			for (User contact:userlist)
//				Activa.myMessageManager.contactListForServices.put(contact.getId(), contact);
//		} catch (ServerException e1) {
//			e1.printStackTrace();
//		}
//		Request request = new Request(Request.TYPE_CONTACTS);
//		try {
//			tosend = request.toMessageString().getBytes();
//			toreceive = serverConnection.submitUnsecure(tosend, "?user=" + Activa.myMobileManager.user.getName() + "&pwd=" + Activa.myMobileManager.user.getPassword());
//			Activa.myMessageManager.extractContactsFromXML(new String(toreceive));
//			return true;
//		} catch (ConnectionException e) {
//			e.printStackTrace();
//			return false;
//		} catch (LoginException e) {
//			e.printStackTrace();
//			return false;
//		} catch (PasswordException e) {
//			e.printStackTrace();
//			return false;
//		} catch (IOException e) {
//			e.printStackTrace();
//			return false;
//		}
//	}
	
	public boolean getContacts() {
		boolean returned = true;
		Activa.myMessageManager.contactList = new Hashtable<Long, Contact>();
		Activa.myMessageManager.contactListForServices = new Hashtable<Long, User>();
		Activa.myMobileManager.device.updateDateTime();
		UserListGetAction getUsers = new UserListGetAction(Activa.myMobileManager.userForServices);
		try {
			Array<User> userlist = service.dispatch(getUsers);
			for (User contact:userlist) {
				Activa.myMessageManager.contactListForServices.put(contact.getId(), contact);
				Contact contactToAdd = new Contact(contact.getUsername(), contact.getId());
				Activa.myMessageManager.contactList.put(contactToAdd.id, contactToAdd);
			}
			Activa.myMessageManager.contactListForServices.put(Activa.myMobileManager.userForServices.getId(), Activa.myMobileManager.userForServices);
			Contact contactToAdd = new Contact(Activa.myMobileManager.userForServices.getUsername(), Activa.myMobileManager.userForServices.getId());
			Activa.myMessageManager.contactList.put(contactToAdd.id, contactToAdd);			
		} catch (ServerException e1) {
			e1.printStackTrace();
			returned =false;
		} catch (InvocationException e) {
			e.printStackTrace();
			return false;
		}
		return returned;
	}
	
	/**
	 * This method deals with the obtaining of the message directed to the user.
	 */
	public boolean getMessages(Date date) {
		byte[] tosend, toreceive;
		Activa.myMobileManager.device.updateDateTime();
		O2MessageRequest request = new O2MessageRequest(date);
		try {
			tosend = request.toMessageString().getBytes();
			toreceive = serverConnection.submitUnsecure(tosend, "?user=" + Activa.myMobileManager.user.getName() + "&pwd=" + Activa.myMobileManager.user.getPassword());
			Activa.myMessageManager.extractMessagesFromXML(new String(toreceive));
			return true;
		} catch (ConnectionException e) {
			e.printStackTrace();
			return false;
		} catch (LoginException e) {
			e.printStackTrace();
			return false;
		} catch (PasswordException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public void getMessages2(Date date) {
		String xmlMap = "";
		try {
			InputStream fIn = Activa.myApp.getResources().openRawResource(R.raw.message);
	        InputStreamReader isr = new InputStreamReader(fIn,"UTF-8");          
	        char[] inputBuffer = new char[255];              
	        while (isr.read(inputBuffer) != -1) {
	        	xmlMap += new String(inputBuffer);
	        }
	        Activa.myMessageManager.extractMessagesFromXML(xmlMap);    
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This method deals with the obtaining of the calendar and the parsing by the calendar
	 * manager.
	 */
	public void getCalendar() {
		String xmlAgenda;
		try {
			xmlAgenda = sendRequest(Request.TYPE_AGENDA);
			Activa.myCalendarManager.extractFromXML(xmlAgenda);
		} catch (ConnectionException e) {
			e.printStackTrace();
		} catch (LoginException e) {
			e.printStackTrace();
		} catch (PasswordException e) {
			e.printStackTrace();
		}
	}	
	
	public void getCalendar2() {
		String xmlCalendar = "";
		try {
			InputStream fIn = Activa.myApp.getResources().openRawResource(R.raw.calendario);
	        InputStreamReader isr = new InputStreamReader(fIn,"UTF-8");          
	        char[] inputBuffer = new char[255];              
	        while (isr.read(inputBuffer) != -1) {
	        	xmlCalendar += new String(inputBuffer);
	        }
			Activa.myCalendarManager.extractFromXML(xmlCalendar);    
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/*public boolean login() {
		String xmlConfig;
		boolean returned = true;
		this.connected = true;
		try {
			UserLoginAction action = new UserLoginAction(Activa.myMobileManager.user.getName(), Activa.myMobileManager.user.getPassword());
			Activa.myMobileManager.userForServices = service.dispatch(action);
			xmlConfig = sendRequest(Request.TYPE_CONFIG);
			if (xmlConfig.length() == 0) throw new Exception();
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
	        factory.setNamespaceAware(true);
	        XmlPullParser info = factory.newPullParser();
	        info.setInput(new StringReader(xmlConfig));
	        int event = info.getEventType();
	        while (event != XmlPullParser.END_DOCUMENT) {
	            if(event == XmlPullParser.START_DOCUMENT) {
	            } else if(event == XmlPullParser.END_DOCUMENT) {    	
	            } else if(event == XmlPullParser.START_TAG) {
	                if (info.getName().equalsIgnoreCase("URL")) {
//	                	ActivaConfig.URL = info.getAttributeValue(info.getNamespace(), "VALUE");
	                }
	                else if (info.getName().equalsIgnoreCase("PATIENTID")) {
	                	Activa.myMobileManager.user.setId(Integer.parseInt(info.getAttributeValue(info.getNamespace(), "VALUE")));
	                }
	                else if (info.getName().equalsIgnoreCase("AGENDARANGE")) {
	                	ActivaConfig.SCHEDULE_HOURS = Integer.parseInt(info.getAttributeValue(info.getNamespace(), "VALUE"));
	                }
	                else if (info.getName().equalsIgnoreCase("USERINFO")) {
	                	Activa.myMobileManager.user.firstname = info.getAttributeValue(info.getNamespace(), "FIRSTNAME");
	                	Activa.myMobileManager.user.lastname = info.getAttributeValue(info.getNamespace(), "LASTNAME");
	                	Activa.myMobileManager.user.setBirthdate(ActivaUtil.XMLDateToDate(info.getAttributeValue(info.getNamespace(), "BIRTHDATE")));
	                	if (info.getAttributeValue(info.getNamespace(), "TYPE") != null)
	                		Activa.myMobileManager.user.setType(Integer.parseInt(info.getAttributeValue(info.getNamespace(), "TYPE")));
	                	Activa.myMobileManager.user.lastname = info.getAttributeValue(info.getNamespace(), "LASTNAME");
	                	String sex = info.getAttributeValue(info.getNamespace(), "SEX");
	                	if (sex.equalsIgnoreCase("MALE")) Activa.myMobileManager.user.setMale(true);
	                	else Activa.myMobileManager.user.setMale(false);
		            }
	            } else if(event == XmlPullParser.START_TAG) {
	            	if (info.getName().equalsIgnoreCase("CONFIG")) {
	            		break;
	            	}
	            }
	            event = info.next();
	        }
		} catch (ConnectionException e) {
			e.printStackTrace();
			this.connected = false;
			returned=false;
			return returned;
		} catch (LoginException e) {
			e.printStackTrace();
			this.connected = false;
			returned=false;
			return returned;
		} catch (PasswordException e) {
			e.printStackTrace();
			this.connected = false;
			returned=false;
			return returned;
		} catch (XmlPullParserException e) {
			e.printStackTrace();
			this.connected = false;
			returned=false;
			return returned;
		} catch (Exception e) {
			e.printStackTrace();
			this.connected = false;
			returned=false;
			return returned;
		}
		return returned;
	}*/
	
	/*public int checkUserExistance() {
		String xmlConfig;
		// Returned value: 0 for succesfful checking, 1 for unsuccesful checking, 2 for internal error
		int returned = 0;
		this.connected = true;
		try {
			xmlConfig = sendUserExistanceRequest();
			if (xmlConfig.length() == 0) throw new Exception();
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
	        factory.setNamespaceAware(true);
	        XmlPullParser info = factory.newPullParser();
	        info.setInput(new StringReader(xmlConfig));
	        int event = info.getEventType();
	        while (event != XmlPullParser.END_DOCUMENT) {
	            if(event == XmlPullParser.START_DOCUMENT) {
	            } else if(event == XmlPullParser.END_DOCUMENT) {    	
	            } else if(event == XmlPullParser.START_TAG) {
	                if (info.getName().equalsIgnoreCase("URL")) {
//	                	ActivaConfig.URL = info.getAttributeValue(info.getNamespace(), "VALUE");
	                }
	                else if (info.getName().equalsIgnoreCase("PATIENTID")) {
	                	Activa.myMobileManager.user.setId(Integer.parseInt(info.getAttributeValue(info.getNamespace(), "VALUE")));
	                }
	                else if (info.getName().equalsIgnoreCase("AGENDARANGE")) {
	                	ActivaConfig.SCHEDULE_HOURS = Integer.parseInt(info.getAttributeValue(info.getNamespace(), "VALUE"));
	                }
	                else if (info.getName().equalsIgnoreCase("USERINFO")) {
	                	Activa.myMobileManager.user.firstname = info.getAttributeValue(info.getNamespace(), "FIRSTNAME");
	                	Activa.myMobileManager.user.lastname = info.getAttributeValue(info.getNamespace(), "LASTNAME");
	                	Activa.myMobileManager.user.setBirthdate(ActivaUtil.XMLDateToDate(info.getAttributeValue(info.getNamespace(), "BIRTHDATE")));
	                	if (info.getAttributeValue(info.getNamespace(), "TYPE") != null)
	                		Activa.myMobileManager.user.setType(Integer.parseInt(info.getAttributeValue(info.getNamespace(), "TYPE")));
	                	String sex = info.getAttributeValue(info.getNamespace(), "SEX");
	                	if (sex.equalsIgnoreCase("MALE")) Activa.myMobileManager.user.setMale(true);
	                	else Activa.myMobileManager.user.setMale(false);
		            }
	            } else if(event == XmlPullParser.START_TAG) {
	            	if (info.getName().equalsIgnoreCase("CONFIG")) {
	            		break;
	            	}
	            }
	            event = info.next();
	        }
		} catch (ConnectionException e) {
			e.printStackTrace();
			this.connected = false;
			returned=1;
			return returned;
		} catch (LoginException e) {
			e.printStackTrace();
			this.connected = false;
			returned=1;
			return returned;
		} catch (PasswordException e) {
			e.printStackTrace();
			this.connected = false;
			returned=1;
			return returned;
		} catch (XmlPullParserException e) {
			e.printStackTrace();
			this.connected = false;
			returned=2;
			return returned;
		} catch (Exception e) {
			e.printStackTrace();
			this.connected = false;
			returned=2;
			return returned;
		}
		return returned;
	}*/
	
	public int checkUserExistance() {
		try {
			UserLoginAction action = new UserLoginAction(Activa.myMobileManager.user.getName(), Activa.myMobileManager.user.getPassword());
			Activa.myMobileManager.userForServices = service.dispatch(action);
			if (Activa.myMobileManager.userForServices == null) return 1;
			Activa.myMobileManager.user.setId(Activa.myMobileManager.userForServices.getId());
        	Activa.myMobileManager.user.firstname = Activa.myMobileManager.userForServices.getFirstName();
        	Activa.myMobileManager.user.lastname = Activa.myMobileManager.userForServices.getLastName();
        	Activa.myMobileManager.user.setBirthdate(Activa.myMobileManager.userForServices.getBirthdate());
        	if (Activa.myMobileManager.userForServices instanceof Patient) Activa.myMobileManager.user.setType(0);
        	else if (Activa.myMobileManager.userForServices instanceof Clinician) Activa.myMobileManager.user.setType(1);
        	else if (Activa.myMobileManager.userForServices instanceof Researcher) Activa.myMobileManager.user.setType(2);
        	else return 2;
        	if (Activa.myMobileManager.userForServices.getSex().equals(Sex.MALE)) Activa.myMobileManager.user.setMale(true);
        	else Activa.myMobileManager.user.setMale(false);
        	return 0;
		} catch (ServerException e) {
			e.printStackTrace();
			return 1;
		} catch (InvocationException e) {
			e.printStackTrace();
			return 2;
		}	 
	}
	
	public boolean login() {
		try {
			UserLoginAction action = new UserLoginAction(Activa.myMobileManager.user.getName(), Activa.myMobileManager.user.getPassword());
			Activa.myMobileManager.userForServices = service.dispatch(action);
			if (Activa.myMobileManager.userForServices == null) return false;
			Activa.myMobileManager.user.setId(Activa.myMobileManager.userForServices.getId());
        	Activa.myMobileManager.user.firstname = Activa.myMobileManager.userForServices.getFirstName();
        	Activa.myMobileManager.user.lastname = Activa.myMobileManager.userForServices.getLastName();
        	Activa.myMobileManager.user.setBirthdate(Activa.myMobileManager.userForServices.getBirthdate());
        	if (Activa.myMobileManager.userForServices instanceof Patient) Activa.myMobileManager.user.setType(0);
        	else if (Activa.myMobileManager.userForServices instanceof Clinician) Activa.myMobileManager.user.setType(1);
        	else if (Activa.myMobileManager.userForServices instanceof Researcher) Activa.myMobileManager.user.setType(2);
        	else return false;
        	if (Activa.myMobileManager.userForServices.getSex().equals(Sex.MALE)) Activa.myMobileManager.user.setMale(true);
        	else Activa.myMobileManager.user.setMale(false);
        	return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}	
	}
	
	public boolean register() {
		try {
			User user;
			switch (Activa.myMobileManager.user.getType()) {
				case 0:
					user = new Patient();
					break;
				case 1:
					user = new Clinician();
					break;
				case 2:
					user = new Researcher();
					break;
				default:
					return false;
			}
			user.setUsername(Activa.myMobileManager.user.getName());
			user.setFirstName(Activa.myMobileManager.user.getFirstname());
			user.setLastName(Activa.myMobileManager.user.getLastname());
			user.setBluetoothAddress(Activa.myMobileManager.device.getIdnumber());
			user.setBirthdate(Activa.myMobileManager.user.getBirthdate());
			if (Activa.myMobileManager.user.isMale()) user.setSex(Sex.MALE);
			else user.setSex(Sex.FEMALE);
			UserNewAction action = new UserNewAction(user, Activa.myMobileManager.user.getPassword());
			Activa.myMobileManager.userForServices = service.dispatch(action);
			if (Activa.myMobileManager.userForServices == null) return false;
			else return true;
		} catch (ServerException e) {
			e.printStackTrace();
			return false;
		} catch (InvocationException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/*public boolean register() {
		String xmlConfig;
		boolean returned = true;
		this.connected = true;
		try {
			xmlConfig = sendRegisterRequest();
			if (xmlConfig.length() == 0) throw new Exception();
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
	        factory.setNamespaceAware(true);
	        XmlPullParser info = factory.newPullParser();
	        info.setInput(new StringReader(xmlConfig));
	        int event = info.getEventType();
	        while (event != XmlPullParser.END_DOCUMENT) {
	            if(event == XmlPullParser.START_DOCUMENT) {
	            } else if(event == XmlPullParser.END_DOCUMENT) {    	
	            } else if(event == XmlPullParser.START_TAG) {
	                if (info.getName().equalsIgnoreCase("URL")) {
//	                	ActivaConfig.URL = info.getAttributeValue(info.getNamespace(), "VALUE");
	                }
	                else if (info.getName().equalsIgnoreCase("PATIENTID")) {
	                	Activa.myMobileManager.user.setId(Integer.parseInt(info.getAttributeValue(info.getNamespace(), "VALUE")));
	                }
	                else if (info.getName().equalsIgnoreCase("AGENDARANGE")) {
	                	ActivaConfig.SCHEDULE_HOURS = Integer.parseInt(info.getAttributeValue(info.getNamespace(), "VALUE"));
	                }
	                else if (info.getName().equalsIgnoreCase("USERINFO")) {
	                	Activa.myMobileManager.user.firstname = info.getAttributeValue(info.getNamespace(), "FIRSTNAME");
	                	Activa.myMobileManager.user.lastname = info.getAttributeValue(info.getNamespace(), "LASTNAME");
	                	Activa.myMobileManager.user.setBirthdate(ActivaUtil.XMLDateToDate(info.getAttributeValue(info.getNamespace(), "BIRTHDATE")));
	                	if (info.getAttributeValue(info.getNamespace(), "TYPE") != null)
	                		Activa.myMobileManager.user.setType(Integer.parseInt(info.getAttributeValue(info.getNamespace(), "TYPE")));
	                	String sex = info.getAttributeValue(info.getNamespace(), "SEX");
	                	if (sex.equalsIgnoreCase("MALE")) Activa.myMobileManager.user.setMale(true);
	                	else Activa.myMobileManager.user.setMale(false);
		            }
	            } else if(event == XmlPullParser.START_TAG) {
	            	if (info.getName().equalsIgnoreCase("CONFIG")) {
	            		break;
	            	}
	            }
	            event = info.next();
	        }
		} catch (ConnectionException e) {
			e.printStackTrace();
			this.connected = false;
			returned=false;
			return returned;
		} catch (LoginException e) {
			e.printStackTrace();
			this.connected = false;
			returned=false;
			return returned;
		} catch (PasswordException e) {
			e.printStackTrace();
			this.connected = false;
			returned=false;
			return returned;
		} catch (XmlPullParserException e) {
			e.printStackTrace();
			this.connected = false;
			returned=false;
			return returned;
		} catch (Exception e) {
			e.printStackTrace();
			this.connected = false;
			returned=false;
			return returned;
		}
		return returned;
	}*/
	
	/**
	 * This method sends the answers given to a concrete questionnaire, passed as
	 * parameter, towads the server, returning a boolean value notifying if the sending 
	 * was succesful or not.
	 * 
	 * @param questionnaire
	 * @return
	 */
	public boolean sendQuestAnswered(Questionnaire questionnaire) {
		byte[] tosend;
		boolean returned = true;
		Activa.myMobileManager.device.updateDateTime();
		QuestMessage questmessage = new QuestMessage(questionnaire);
//		Message message = new Message(request.toMessageString());
		try {
//			tosend = message.toMessageString().getBytes();
			tosend = questmessage.toMessageString().getBytes();
			serverConnection.submitUnsecure(tosend, "?user=" + Activa.myMobileManager.user.getName() + "&pwd=" + Activa.myMobileManager.user.getPassword());
		} catch (IOException e) {
			Activa.myPendingDataManager.addPendingData(questmessage.toMessageString());
			returned = false;
			return returned;
		} catch (ConnectionException e) {
			Activa.myPendingDataManager.addPendingData(questmessage.toMessageString());
			returned = false;
			return returned;
		} catch (LoginException e) {
			Activa.myPendingDataManager.addPendingData(questmessage.toMessageString());
			returned = false;
			return returned;
		} catch (PasswordException e) {
			Activa.myPendingDataManager.addPendingData(questmessage.toMessageString());
			returned = false;
			return returned;
		}		
		return returned;
	}
	
	/**
	 * This method sends the outcome of a concrete event (canceled = 0 if the event has 
	 * been done and canceled = 1 if it has passed 24 hours since programmed time for 
	 * the event and it was not done).
	 * 
	 * @param event
	 * @return
	 */
	public boolean sendEventOutcome(Event event) {
		byte[] tosend;
		boolean returned = true;
		Activa.myMobileManager.device.updateDateTime();
		EventMessage eventmessage = new EventMessage(event);
//		Message message = new Message(request.toMessageString());
		try {
//			tosend = message.toMessageString().getBytes();
			tosend = eventmessage.toMessageString().getBytes();
			serverConnection.submitUnsecure(tosend, "?user=" + Activa.myMobileManager.user.getName() + "&pwd=" + Activa.myMobileManager.user.getPassword());
		} catch (IOException e) {
			Activa.myPendingDataManager.addPendingData(eventmessage.toMessageString());
			returned = false;
			return returned;
		} catch (ConnectionException e) {
			Activa.myPendingDataManager.addPendingData(eventmessage.toMessageString());
			returned = false;
			return returned;
		} catch (LoginException e) {
			Activa.myPendingDataManager.addPendingData(eventmessage.toMessageString());
			returned = false;
			return returned;
		} catch (PasswordException e) {
			Activa.myPendingDataManager.addPendingData(eventmessage.toMessageString());
			returned = false;
			return returned;
		}		
		return returned;
	}

	/**
	 * The method sends an internal message to an user.
	 * 
	 * @param message
	 * @return
	 */
//	public boolean sendO2Message(O2UnregisteredMessage message) {
//		byte[] tosend;
//		boolean returned = true;
//		Activa.myMobileManager.device.updateDateTime();
//		O2MessageSent o2message = new O2MessageSent(message);
//		try {
//			tosend = o2message.toMessageString().getBytes();
//			serverConnection.submitUnsecure(tosend, "?user=" + Activa.myMobileManager.user.getName() + "&pwd=" + Activa.myMobileManager.user.getPassword());
//		} catch (IOException e) {
//			Activa.myPendingDataManager.addPendingData(o2message.toMessageString());
//			returned = false;
//			return returned;
//		} catch (ConnectionException e) {
//			Activa.myPendingDataManager.addPendingData(o2message.toMessageString());
//			returned = false;
//			return returned;
//		} catch (LoginException e) {
//			Activa.myPendingDataManager.addPendingData(o2message.toMessageString());
//			returned = false;
//			return returned;
//		} catch (PasswordException e) {
//			Activa.myPendingDataManager.addPendingData(o2message.toMessageString());
//			returned = false;
//			return returned;
//		}		
//		return returned;
//	}
	
	public boolean sendO2Message(O2UnregisteredMessage message) {
		boolean returned = true;
		MessageNewAction sendmessage = new MessageNewAction();
		sendmessage.setSender(Activa.myMobileManager.user.getId());
		sendmessage.setContent(message.text);
		SimpleMessage msg = new SimpleMessage();
		msg.setSubject(message.topic);
		sendmessage.setMessage(msg);
		Array<User> receivers = new Array<User>();
		for (int i = 0; i < message.receivers.length; i++) {
			receivers.add(Activa.myMessageManager.contactListForServices.get(message.receivers[i]));
		}
		sendmessage.setReceivers(receivers);
		try {
			service.dispatch(sendmessage);
		} catch (ServerException e) {
			e.printStackTrace();
			returned = false;
		} catch (InvocationException e) {
			e.printStackTrace();
			return false;
		}
		return returned;
	}
	
	public void sendPendingData(String pendData) {
		byte[] tosend;
//		Message message = new Message(request.toMessageString());
		try {
//			tosend = message.toMessageString().getBytes();
			tosend = pendData.getBytes();
			serverConnection.submitUnsecure(tosend, "?user=" + Activa.myMobileManager.user.getName() + "&pwd=" + Activa.myMobileManager.user.getPassword());
		} catch (IOException e) {
			Activa.myPendingDataManager.addPendingData(pendData);
		} catch (ConnectionException e) {
			Activa.myPendingDataManager.addPendingData(pendData);
		} catch (LoginException e) {
			Activa.myPendingDataManager.addPendingData(pendData);
		} catch (PasswordException e) {
			Activa.myPendingDataManager.addPendingData(pendData);
		}		
	}

	@SuppressWarnings("unchecked")
	public void refresh() {
		/* The updating of the configuration and the refreshing of connection */
		Activa.myApp.refreshing = true;
		boolean success = false;
		if (!Activa.myMobileManager.user.isCreated()) success = Activa.myProtocolManager.register();
		else success = Activa.myProtocolManager.login();
		if (success) {
			if (!Activa.myMobileManager.user.isCreated()) {
				Activa.myMobileManager.user.setCreated(true);
				Activa.myMobileManager.addUserWithPassword(Activa.myMobileManager.user);
			}
		    /* Send pending data */
		    ArrayList<String> tempList = (ArrayList<String>) Activa.myPendingDataManager.pendingData.clone();
		    Activa.myPendingDataManager.pendingData = new ArrayList<String>();
		    Iterator<String> it = tempList.iterator();
		    while (it.hasNext()) {
		    	String pendData = it.next();
		    	Activa.myProtocolManager.sendPendingData(pendData);
		    }
			/* The updating of the data */
			Activa.myQuestManager.getQuestionnaires();
			Activa.myQuestControlManager.getQuestionnaires();
		    Activa.myPatientManager.getPatientList();
		    Activa.myCalendarManager.getCalendar();
//		    Activa.myMapManager.getMapMarks();
		    Activa.myMessageManager.requestContactList();
		    Date date = new Date();
		    date.setDate(date.getDate() - 15);
		    Activa.myMessageManager.requestReceivedMessages(date);
		    /* Update event status and send event outcomes for canceled events */
		    Date dateLate = new Date();
		    dateLate.setHours(dateLate.getHours() - 24);
		    Enumeration<Event> enumeration = Activa.myCalendarManager.events.elements();
		    while (enumeration.hasMoreElements()) {
		    	Event event = enumeration.nextElement();
		    	event.updateState();
		    }
			Activa.myApp.refreshing = false;
		}
	}

	public boolean sendSensorMeasurement(Sensor sensor) {
		byte[] tosend;
		boolean returned = true;
		Activa.myMobileManager.device.updateDateTime();
		SensorMessage sensormessage = new SensorMessage(sensor);
		try {
			tosend = sensormessage.toMessageString().getBytes();
			serverConnection.submitUnsecure(tosend, "?user=" + Activa.myMobileManager.user.getName() + "&pwd=" + Activa.myMobileManager.user.getPassword());
		} catch (IOException e) {
			Activa.myPendingDataManager.addPendingData(sensormessage.toMessageString());
			returned = false;
			return returned;
		} catch (ConnectionException e) {
			Activa.myPendingDataManager.addPendingData(sensormessage.toMessageString());
			returned = false;
			return returned;
		} catch (LoginException e) {
			Activa.myPendingDataManager.addPendingData(sensormessage.toMessageString());
			returned = false;
			return returned;
		} catch (PasswordException e) {
			Activa.myPendingDataManager.addPendingData(sensormessage.toMessageString());
			returned = false;
			return returned;
		}		
		return returned;
	}

	public boolean sendSensorMeasurement(Sensor sensor, int deviceId) {
		byte[] tosend;
		boolean returned = true;
		Activa.myMobileManager.device.updateDateTime();
		SensorMessage sensormessage = new SensorMessage(sensor);
		try {
			tosend = sensormessage.toMessageString(deviceId).getBytes();
			serverConnection.submitUnsecure(tosend, "?user=" + Activa.myMobileManager.user.getName() + "&pwd=" + Activa.myMobileManager.user.getPassword());
		} catch (IOException e) {
			Activa.myPendingDataManager.addPendingData(sensormessage.toMessageString(deviceId));
			returned = false;
			return returned;
		} catch (ConnectionException e) {
			Activa.myPendingDataManager.addPendingData(sensormessage.toMessageString(deviceId));
			returned = false;
			return returned;
		} catch (LoginException e) {
			Activa.myPendingDataManager.addPendingData(sensormessage.toMessageString(deviceId));
			returned = false;
			return returned;
		} catch (PasswordException e) {
			Activa.myPendingDataManager.addPendingData(sensormessage.toMessageString(deviceId));
			returned = false;
			return returned;
		}		
		return returned;
	}
	
	public boolean getNewsFeeds() {
		byte[] tosend, toreceive;
		Activa.myMobileManager.device.updateDateTime();
		Request request = new Request(Request.TYPE_FEEDS);
		try {
			tosend = request.toMessageString().getBytes();
			toreceive = serverConnection.submitUnsecure(tosend, "?user=" + Activa.myMobileManager.user.getName() + "&pwd=" + Activa.myMobileManager.user.getPassword());
			Activa.myNewsManager.extractNewsFromXML(new String(toreceive));
			return true;
		} catch (ConnectionException e) {
			e.printStackTrace();
			return false;
		} catch (LoginException e) {
			e.printStackTrace();
			return false;
		} catch (PasswordException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public void getNewsFeeds2() {
		String xmlFeeds = "";
		try {
			InputStream fIn = Activa.myApp.getResources().openRawResource(R.raw.feeds);
	        InputStreamReader isr = new InputStreamReader(fIn,"UTF-8");          
	        char[] inputBuffer = new char[255];              
	        while (isr.read(inputBuffer) != -1) {
	        	xmlFeeds += new String(inputBuffer);
	        }
	        Activa.myNewsManager.extractNewsFromXML(xmlFeeds);    
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean getNotes() {
		byte[] tosend, toreceive;
		Activa.myMobileManager.device.updateDateTime();
		Request request = new Request(Request.TYPE_NOTES);
		try {
			tosend = request.toMessageString().getBytes();
			toreceive = serverConnection.submitUnsecure(tosend, "?user=" + Activa.myMobileManager.user.getName() + "&pwd=" + Activa.myMobileManager.user.getPassword());
			Activa.myNotesManager.extractNotesFromXML(new String(toreceive));
			return true;
		} catch (ConnectionException e) {
			e.printStackTrace();
			return false;
		} catch (LoginException e) {
			e.printStackTrace();
			return false;
		} catch (PasswordException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public void getNotes2() {
		String xmlFeeds = "";
		try {
			InputStream fIn = Activa.myApp.getResources().openRawResource(R.raw.notes);
	        InputStreamReader isr = new InputStreamReader(fIn,"UTF-8");          
	        char[] inputBuffer = new char[255];              
	        while (isr.read(inputBuffer) != -1) {
	        	xmlFeeds += new String(inputBuffer);
	        }
	        Activa.myNotesManager.extractNotesFromXML(xmlFeeds);    
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
//	public boolean sendNote(String text) {
//		byte[] tosend;
//		boolean returned = true;
//		Activa.myMobileManager.device.updateDateTime();
//		NoteMessage notemessage = new NoteMessage(text);
//		try {
//			tosend = notemessage.toMessageString().getBytes();
//			serverConnection.submitUnsecure(tosend, "?user=" + Activa.myMobileManager.user.getName() + "&pwd=" + Activa.myMobileManager.user.getPassword());
//		} catch (IOException e) {
//			Activa.myPendingDataManager.addPendingData(notemessage.toMessageString());
//			returned = false;
//			return returned;
//		} catch (ConnectionException e) {
//			Activa.myPendingDataManager.addPendingData(notemessage.toMessageString());
//			returned = false;
//			return returned;
//		} catch (LoginException e) {
//			Activa.myPendingDataManager.addPendingData(notemessage.toMessageString());
//			returned = false;
//			return returned;
//		} catch (PasswordException e) {
//			Activa.myPendingDataManager.addPendingData(notemessage.toMessageString());
//			returned = false;
//			return returned;
//		}		
//		return returned;
//	}

	public String getNewsFromFeed(String link) {
		HttpGet getMethod = null;
		InputStream is = null;
		byte[] data;
		HttpResponse response = null;
		HttpClient httpclient = null;
		int respCode = -9;
		
		String url = "http://ajax.googleapis.com/ajax/services/feed/load?q=" + URLEncoder.encode(link) + "&v=1.0";
		try {	
	        httpclient = new DefaultHttpClient();
			getMethod = new HttpGet(url);
			
			response = httpclient.execute(getMethod);
			respCode = response.getStatusLine().getStatusCode();
			if (respCode!=HttpURLConnection.HTTP_OK) {
				throw new ConnectionException(Activa.myLanguageManager.PROT_ERR_RESP_CODE, Connection.ERR_SYSTEM_NOT_INITIALIZED);
			}
			is = response.getEntity().getContent(); 
			int len = (int)response.getEntity().getContentLength();
			if (len > 0) {
				data = new byte[len];
				int off = 0;
				while (off < len) {
					off += is.read(data, off, len-off);
				}
			} else {
			      com.o2hlink.activa.connection.ByteBuffer strBuff = new com.o2hlink.activa.connection.ByteBuffer();
			      int lenReadData = 1;
			      while (lenReadData > 0) {
			            byte [] buffer = new byte [256];
			            lenReadData = is.read(buffer,0,256);
			            if (lenReadData > 0) {
			                  for (int ni = 0 ; ni < lenReadData; ni ++) { 
			                        strBuff.append(buffer[ni]);
			                  }
			            }
			      }
			      data = strBuff.getBytes();
			}
			return new String(data);
		} catch (Exception e) {
			return null;
		}
	}
	
	public Note sendNote (String text) {
		Note returned = null;
		Note note = new Note();
		note.setDate(new Date());
		note.setComment(text);
		NoteNewAction action = new NoteNewAction(Activa.myMobileManager.userForServices, note);
		try {
			returned = service.dispatch(action);
		} catch (ServerException e) {
			e.printStackTrace();
		} catch (InvocationException e) {
			e.printStackTrace();
		}
		return returned;
	}
	
	public boolean getPatientList() {
		boolean returned = true;
		Activa.myPatientManager.patientSet = new Hashtable<Long, com.o2hlink.activa.patient.Patient>();
		UserListGetAction getpatients = new UserListGetAction();
		getpatients.setProvider(Activa.myMobileManager.userForServices);
		try {
			Array<User> patients = service.dispatch(getpatients);
			for (User patient : patients) {
				if (patient instanceof Patient) {
					com.o2hlink.activa.patient.Patient pat = new com.o2hlink.activa.patient.Patient();
					pat.id = patient.getId();
					pat.firstname = patient.getFirstName();
					pat.lastname = patient.getLastName();
					pat.birthdate = patient.getBirthdate();
					if (patient.getCountry() != null) pat.nation = patient.getCountry().getName();
					else pat.nation = "";
					pat.email = patient.getEmail();
					Sex sex = patient.getSex();
					if (sex.equals(Sex.FEMALE)) {
						pat.isMale = false;
					}
					Activa.myPatientManager.patientSet.put(pat.id, pat);					
				}
			}
		} catch (ServerException e) {
			e.printStackTrace();
			returned = false;
		} catch (InvocationException e) {
			e.printStackTrace();
			return false;
		}
		return returned;
	}
	
	public boolean getPatientHistory (com.o2hlink.activa.patient.Patient patient) {
		boolean returned = true;
		patient.history = new Hashtable<Long, com.o2hlink.activa.patient.HistoryRecord>();
		try {
			HistoryGetAction getHistory = new HistoryGetAction(patient.id);
			Array<HistoryRecord> history = service.dispatch(getHistory);
			for (HistoryRecord record : history) {
				com.o2hlink.activa.patient.HistoryRecord rec = new com.o2hlink.activa.patient.HistoryRecord(record.getId(), record.getDisease().getName(), record.getDisease().getDescription(), record.getDate());
				rec.historyRecordForServices = record;
				ExplorationListGetAction getExplorations = new ExplorationListGetAction(record);
				Array<Exploration> explorations = service.dispatch(getExplorations);
				for (Exploration exploration : explorations) {
					com.o2hlink.activa.patient.Exploration expl = new com.o2hlink.activa.patient.Exploration(exploration.getId());
					expl.exploration = exploration.getDescription();
					expl.date = exploration.getDate();
					rec.explorations.put(expl.id, expl);
				}
				patient.history.put(rec.id, rec);
				
			}			
			SampleListGetAction getsamples = new SampleListGetAction(patient.id, Measurement.PULSEOXYMETRY);
			Array<Sample> samples = service.dispatch(getsamples);
			if (!samples.isEmpty()) {
				Sample lastone = null;
				for (Sample sample : samples) {
					if (lastone == null) {
						lastone = sample;
						continue;
					}
					if (lastone.getDate().before(sample.getDate())) {
						lastone = sample;
					}
				}
				patient.lastPulseoximetry = new PulseoximetrySample(lastone.getEventId(), lastone.getDate());
				patient.lastPulseoximetry.heartRate = (int) ((Pulseoxymetry)lastone).getHeartRate();
				patient.lastPulseoximetry.so2 = (int) ((Pulseoxymetry)lastone).getOxygenSaturation();
			}
			getsamples = new SampleListGetAction(patient.id, Measurement.SIX_MINUTES_WALK);
			samples = service.dispatch(getsamples);
			if (!samples.isEmpty()) {
				Sample lastone = null;
				for (Sample sample : samples) {
					if (lastone == null) {
						lastone = sample;
						continue;
					}
					if (lastone.getDate().before(sample.getDate())) {
						lastone = sample;
					}
				}
				patient.lastExercise = new ExerciseSample(lastone.getEventId(), lastone.getDate());
				patient.lastExercise.heartRateAvrg = ((SixMinutesWalk)lastone).getHeartRateAverage();
				patient.lastExercise.heartRatePeak = ((SixMinutesWalk)lastone).getHeartRatePeak();
				patient.lastExercise.heartRateLow = ((SixMinutesWalk)lastone).getHeartRateLow();
				patient.lastExercise.so2Avrg = ((SixMinutesWalk)lastone).getOxygenAverage();
				patient.lastExercise.so2Peak = ((SixMinutesWalk)lastone).getOxygenPeak();
				patient.lastExercise.so2Low = ((SixMinutesWalk)lastone).getOxygenLow();
				patient.lastExercise.initialDyspnea = ((SixMinutesWalk)lastone).getInitialDyspnea();
				patient.lastExercise.initialFatigue = ((SixMinutesWalk)lastone).getInitialFatigue();
				patient.lastExercise.finalDyspnea = ((SixMinutesWalk)lastone).getFinalDyspnea();
				patient.lastExercise.finalFatigue = ((SixMinutesWalk)lastone).getFinalFatigue();
				patient.lastExercise.distance = ((SixMinutesWalk)lastone).getDistance();
				patient.lastExercise.steps = ((SixMinutesWalk)lastone).getSteps();
				patient.lastExercise.stops = ((SixMinutesWalk)lastone).getStops();
				patient.lastExercise.time = (int) ((SixMinutesWalk)lastone).getTime();
			}
			getsamples = new SampleListGetAction(patient.id, Measurement.SPIROMETRY);
			samples = service.dispatch(getsamples);
			if (!samples.isEmpty()) {
				Sample lastone = null;
				for (Sample sample : samples) {
					if (lastone == null) {
						lastone = sample;
						continue;
					}
					if (lastone.getDate().before(sample.getDate())) {
						lastone = sample;
					}
				}
				patient.lastSpirometry = new SpirometrySample(lastone.getEventId(), lastone.getDate());
				patient.lastSpirometry.fev1 = ((Spirometry)lastone).getForcedExpiratoryVolume();
				patient.lastSpirometry.fvc = ((Spirometry)lastone).getForcedVitalCapacity();
				patient.lastSpirometry.pef = ((Spirometry)lastone).getPeakExpiratoryFlow();
				SpirometryGetAction getgraphs = new SpirometryGetAction(patient.id, lastone.getDate());
				SpirometryFlow graphs = service.dispatch(getgraphs);
				patient.lastSpirometry.flow = graphs.getFlow();
				patient.lastSpirometry.time = graphs.getTime();
			}
		} catch (ServerException e) {
			e.printStackTrace();
			returned = false;
		} catch (InvocationException e) {
			e.printStackTrace();
			return false;
		}
		return returned;
	}
	
	public boolean getQuestionnairesForClinician() {
		boolean returned = true;
		Activa.myQuestControlManager.questionnaires = new Hashtable<Long, com.o2hlink.activ8.client.entity.Questionnaire>();
		QuestionnaireListGetAction getquest = new QuestionnaireListGetAction(Activa.myMobileManager.user.getId());
		try {
			Array<com.o2hlink.activ8.client.entity.Questionnaire> questionnaires = service.dispatch(getquest);
			for (com.o2hlink.activ8.client.entity.Questionnaire questionnaire : questionnaires) {
				Activa.myQuestControlManager.questionnaires.put(questionnaire.getId(), questionnaire);
			}
		} catch (ServerException e) {
			e.printStackTrace();
			returned = false;
		} catch (InvocationException e) {
			e.printStackTrace();
			return false;
		}
		return returned;
	}
	
	public boolean downloadFile(String folderurl, String filename, File folder) {
		try {
			URL filetodownload = new URL(folderurl + "/" + filename);
		    HttpURLConnection connection = (HttpURLConnection) filetodownload.openConnection();
		    connection.setRequestMethod("GET");
		    connection.setDoOutput(true);
		    connection.connect();
		    FileOutputStream file;
		    file = new FileOutputStream(new File(folder, filename));
			InputStream in = connection.getInputStream();
		    byte[] buffer = new byte[1024];
		    int len1 = 0;
		    while ( (len1 = in.read(buffer)) > 0 ) {
		    	file.write(buffer,0, len1);
		    }
		    file.close();
		    return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public String readOnlineFile(String url) {
		HttpGet getMethod = null;
		InputStream is = null;
		byte[] data;
		HttpResponse response = null;
		HttpClient httpclient = null;
		int respCode = -9;
		try {	
	        httpclient = new DefaultHttpClient();
			getMethod = new HttpGet(url);
			
			response = httpclient.execute(getMethod);
			respCode = response.getStatusLine().getStatusCode();
			if (respCode!=HttpURLConnection.HTTP_OK) {
				throw new ConnectionException(Activa.myLanguageManager.PROT_ERR_RESP_CODE, Connection.ERR_SYSTEM_NOT_INITIALIZED);
			}
			is = response.getEntity().getContent(); 
			int len = (int)response.getEntity().getContentLength();
			if (len > 0) {
				data = new byte[len];
				int off = 0;
				while (off < len) {
					off += is.read(data, off, len-off);
				}
			} else {
			      com.o2hlink.activa.connection.ByteBuffer strBuff = new com.o2hlink.activa.connection.ByteBuffer();
			      int lenReadData = 1;
			      while (lenReadData > 0) {
			            byte [] buffer = new byte [256];
			            lenReadData = is.read(buffer,0,256);
			            if (lenReadData > 0) {
			                  for (int ni = 0 ; ni < lenReadData; ni ++) { 
			                        strBuff.append(buffer[ni]);
			                  }
			            }
			      }
			      data = strBuff.getBytes();
			}
			return new String(data);
		} catch (Exception e) {
			return null;
		}
	}
	
	public Drawable getDrawableFromFile(String folderurl, String filename) {
		try {
			URL filetodownload = new URL(folderurl + "/" + filename);
		    HttpURLConnection connection = (HttpURLConnection) filetodownload.openConnection();
		    connection.setRequestMethod("GET");
		    connection.setDoOutput(true);
		    connection.connect();
		    FileOutputStream file;
			InputStream in = connection.getInputStream();
		    return Drawable.createFromStream(in, "src");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public Array<User> SearchContacts (String query) {
		UserSearchAction action = new UserSearchAction(Activa.myMobileManager.userForServices.getId(), query);
		try {
			return service.dispatch(action);
		} catch (ServerException e) {
			e.printStackTrace();
			return null;
		}
	}

	public boolean AddContact (User user) {
		UserAddAction action = new UserAddAction(Activa.myMobileManager.userForServices, user);
		try {
			service.dispatch(action);
			Activa.myMessageManager.contactListForServices.put(user.getId(), user);
			Contact contactToAdd = new Contact(user.getUsername(), user.getId());
			Activa.myMessageManager.contactList.put(contactToAdd.id, contactToAdd);		
			return true;
		} catch (ServerException e) {
			e.printStackTrace();
			return false;
		}
	}
	
}