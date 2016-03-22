package com.o2hlink.healthgenius.connection;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

//import com.gdevelop.gwt.syncrpc.SyncProxy;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import com.gdevelop.gwt.syncrpc.SyncProxy;
import com.google.api.translate.Language;
import com.google.api.translate.Translate;
import com.google.gwt.user.client.rpc.IncompatibleRemoteServiceException;
import com.google.gwt.user.client.rpc.InvocationException;
import com.o2hlink.activ8.client.action.AccountDeleteAction;
import com.o2hlink.activ8.client.action.AccountListGetAction;
import com.o2hlink.activ8.client.action.AccountNewAction;
import com.o2hlink.activ8.client.action.AccountTokenRequestAction;
import com.o2hlink.activ8.client.action.Action;
import com.o2hlink.activ8.client.action.AnswerListGetAction;
import com.o2hlink.activ8.client.action.AnswerNewAction;
import com.o2hlink.activ8.client.action.AnswerRemoveAction;
import com.o2hlink.activ8.client.action.AnswerUpdateAction;
import com.o2hlink.activ8.client.action.ConditionListGetAction;
import com.o2hlink.activ8.client.action.ConditionNewAction;
import com.o2hlink.activ8.client.action.ConditionRemoveAction;
import com.o2hlink.activ8.client.action.ConditionUpdateAction;
import com.o2hlink.activ8.client.action.ContactAcceptAction;
import com.o2hlink.activ8.client.action.ContactIncomingRequestListGetAction;
import com.o2hlink.activ8.client.action.ContactListGetAction;
import com.o2hlink.activ8.client.action.ContactRejectAction;
import com.o2hlink.activ8.client.action.ContactRemoveAction;
import com.o2hlink.activ8.client.action.ContactRequestAction;
import com.o2hlink.activ8.client.action.ContactSearchAction;
import com.o2hlink.activ8.client.action.DiseaseSearchAction;
import com.o2hlink.activ8.client.action.DocumentBinaryContentGetAction;
import com.o2hlink.activ8.client.action.DocumentListGetAction;
import com.o2hlink.activ8.client.action.DocumentRemoveAction;
import com.o2hlink.activ8.client.action.DocumentShareAction;
import com.o2hlink.activ8.client.action.DocumentTextContentGetAction;
import com.o2hlink.activ8.client.action.DocumentUploadAction;
import com.o2hlink.activ8.client.action.EventListGetAction;
import com.o2hlink.activ8.client.action.EventNewAction;
import com.o2hlink.activ8.client.action.ExplorationListGetAction;
import com.o2hlink.activ8.client.action.ExplorationNewAction;
import com.o2hlink.activ8.client.action.FeedAddAction;
import com.o2hlink.activ8.client.action.FeedListGetAction;
import com.o2hlink.activ8.client.action.FeedRemoveAction;
import com.o2hlink.activ8.client.action.FeedSearchAction;
import com.o2hlink.activ8.client.action.HistoryGetAction;
import com.o2hlink.activ8.client.action.HistoryRecordNewAction;
import com.o2hlink.activ8.client.action.MeasurementAssignAction;
import com.o2hlink.activ8.client.action.MeasurementAssignListGetAction;
import com.o2hlink.activ8.client.action.MeasurementEventListGetAction;
import com.o2hlink.activ8.client.action.MeasurementEventNewAction;
import com.o2hlink.activ8.client.action.MessageContentGetAction;
import com.o2hlink.activ8.client.action.MessageListGetAction;
import com.o2hlink.activ8.client.action.MessageNewAction;
import com.o2hlink.activ8.client.action.MessageSentListGetAction;
import com.o2hlink.activ8.client.action.NoteListGetAction;
import com.o2hlink.activ8.client.action.NoteNewAction;
import com.o2hlink.activ8.client.action.QuestionListGetAction;
import com.o2hlink.activ8.client.action.QuestionNewAction;
import com.o2hlink.activ8.client.action.QuestionRemoveAction;
import com.o2hlink.activ8.client.action.QuestionUpdateAction;
import com.o2hlink.activ8.client.action.QuestionnaireAddAction;
import com.o2hlink.activ8.client.action.QuestionnaireAnswerAction;
import com.o2hlink.activ8.client.action.QuestionnaireAssignAction;
import com.o2hlink.activ8.client.action.QuestionnaireAssignListGetAction;
import com.o2hlink.activ8.client.action.QuestionnaireEventListGetAction;
import com.o2hlink.activ8.client.action.QuestionnaireEventNewAction;
import com.o2hlink.activ8.client.action.QuestionnaireListGetAction;
import com.o2hlink.activ8.client.action.QuestionnaireNewAction;
import com.o2hlink.activ8.client.action.QuestionnaireRemoveAction;
import com.o2hlink.activ8.client.action.QuestionnaireSampleGetAction;
import com.o2hlink.activ8.client.action.QuestionnaireSearchAction;
import com.o2hlink.activ8.client.action.QuestionnaireUpdateAction;
import com.o2hlink.activ8.client.action.SampleGetAction;
import com.o2hlink.activ8.client.action.SampleListGetAction;
import com.o2hlink.activ8.client.action.SampleNewAction;
import com.o2hlink.activ8.client.action.SiteAddAction;
import com.o2hlink.activ8.client.action.SiteListGetAction;
import com.o2hlink.activ8.client.action.SiteNewAction;
import com.o2hlink.activ8.client.action.SiteRemoveAction;
import com.o2hlink.activ8.client.action.SiteSearchAction;
import com.o2hlink.activ8.client.action.SixMinutesWalkGetAction;
import com.o2hlink.activ8.client.action.SixMinutesWalkNewAction;
import com.o2hlink.activ8.client.action.SpirometryGetAction;
import com.o2hlink.activ8.client.action.SpirometryNewAction;
import com.o2hlink.activ8.client.action.ThemeAllGetAction;
import com.o2hlink.activ8.client.action.ThemeListGetAction;
import com.o2hlink.activ8.client.action.ThemeAddAction;
import com.o2hlink.activ8.client.action.UserAddAction;
import com.o2hlink.activ8.client.action.UserListGetAction;
import com.o2hlink.activ8.client.action.UserLoginAction;
import com.o2hlink.activ8.client.action.UserNewAction;
import com.o2hlink.activ8.client.action.UserPasswordChangeAction;
import com.o2hlink.activ8.client.action.UserRemoveAction;
import com.o2hlink.activ8.client.action.UserSearchAction;
import com.o2hlink.activ8.client.action.UserUpdateAction;
import com.o2hlink.activ8.client.action.VisibilityUpdateAction;
import com.o2hlink.activ8.client.entity.Account;
import com.o2hlink.activ8.client.entity.Answer;
import com.o2hlink.activ8.client.entity.Clinician;
import com.o2hlink.activ8.client.entity.Condition;
import com.o2hlink.activ8.client.entity.Contact;
import com.o2hlink.activ8.client.entity.ContactContactRequest;
import com.o2hlink.activ8.client.entity.Disease;
import com.o2hlink.activ8.client.entity.Document;
import com.o2hlink.activ8.client.entity.Entity;
import com.o2hlink.activ8.client.entity.Exercise;
import com.o2hlink.activ8.client.entity.Exploration;
import com.o2hlink.activ8.client.entity.Feed;
import com.o2hlink.activ8.client.entity.FileContent;
import com.o2hlink.activ8.client.entity.HistoryRecord;
import com.o2hlink.activ8.client.entity.Html;
import com.o2hlink.activ8.client.entity.Institution;
import com.o2hlink.activ8.client.entity.MultiQuestion;
import com.o2hlink.activ8.client.entity.Note;
import com.o2hlink.activ8.client.entity.NumericQuestion;
import com.o2hlink.activ8.client.entity.Patient;
import com.o2hlink.activ8.client.entity.Pulseoxymetry;
import com.o2hlink.activ8.client.entity.Question;
import com.o2hlink.activ8.client.entity.Questionnaire;
import com.o2hlink.activ8.client.entity.QuestionnaireSample;
import com.o2hlink.activ8.client.entity.Researcher;
import com.o2hlink.activ8.client.entity.Resource;
import com.o2hlink.activ8.client.entity.Sample;
import com.o2hlink.activ8.client.entity.SimpleMessage;
import com.o2hlink.activ8.client.entity.SixMinutesWalk;
import com.o2hlink.activ8.client.entity.SixMinutesWalkFlow;
import com.o2hlink.activ8.client.entity.Spirometry;
import com.o2hlink.activ8.client.entity.SpirometryFlow;
import com.o2hlink.activ8.client.entity.StringResponse;
import com.o2hlink.activ8.client.entity.User;
import com.o2hlink.activ8.client.entity.WeightHeight;
import com.o2hlink.activ8.client.exception.AuthenticationException;
import com.o2hlink.activ8.client.exception.DuplicateEntityException;
import com.o2hlink.activ8.client.exception.NotFoundEntityException;
import com.o2hlink.activ8.client.exception.ServerException;
import com.o2hlink.activ8.client.entity.Array;
import com.o2hlink.activ8.client.service.Service;
import com.o2hlink.activ8.common.entity.AccountType;
import com.o2hlink.activ8.common.entity.EventFrequency;
import com.o2hlink.activ8.common.entity.InstitutionCategory;
import com.o2hlink.activ8.common.entity.Measurement;
import com.o2hlink.activ8.common.entity.Sex;
import com.o2hlink.activ8.common.entity.VisibilityLevel;
import com.o2hlink.healthgenius.HealthGenius;
import com.o2hlink.healthgenius.HealthGeniusConfig;
import com.o2hlink.healthgenius.data.Message;
import com.o2hlink.healthgenius.data.PendingDataManager;
import com.o2hlink.healthgenius.data.calendar.CalendarManager;
import com.o2hlink.healthgenius.data.calendar.Event;
import com.o2hlink.healthgenius.data.calendar.ExerciseSample;
import com.o2hlink.healthgenius.data.contacts.ContactsManager;
import com.o2hlink.healthgenius.data.sensor.Sensor;
import com.o2hlink.healthgenius.data.sensor.SixMinutes;
import com.o2hlink.healthgenius.data.sensor.Spirometer;
import com.o2hlink.healthgenius.data.sensor.WeightAndHeight;
import com.o2hlink.healthgenius.exceptions.ConnectionException;
import com.o2hlink.healthgenius.exceptions.NotUpdatedException;
import com.o2hlink.healthgenius.patient.PulseoximetrySample;
import com.o2hlink.healthgenius.patient.SixMinutesWalkSample;
import com.o2hlink.healthgenius.patient.SpirometrySample;
import com.o2hlink.healthgenius.patient.WeightHeightSample;

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
	 * The instance of the service offered by the server.
	 */
	public Service service;
	
	public User user;
	
	public static String uploadServlet = HealthGeniusConfig.SERVICES_URL + "upload";
	
	public static String downloadServlet = HealthGeniusConfig.SERVICES_URL + "download";
	
	public static String dowloadkey = "844031d307db4c2f8c875c79b108357a";
	
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
		this.user = null;
		this.connected = false;
		this.service = (Service) SyncProxy.newProxyInstance(Service.class,HealthGeniusConfig.SERVICES_URL,
        		"service", "D30FA89D07222B23512EE9276D914CF4");
	}
	
	public ProtocolManager(boolean connected) {
		this.user = null;
		this.connected = connected;
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
	
	public boolean dispatch(Action<?> action) throws NotUpdatedException {
		try {
			service.dispatch(action);
			return true;
		} catch (InvocationException e) {
			if (e.getCause() != null) {
				try {
					throw e.getCause();
				} catch (IncompatibleRemoteServiceException e0) {
					throw new NotUpdatedException();
				} catch (Throwable e1) {
					e.printStackTrace();
				}
			}
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static String readFile(String url) {
		try {
			URLConnection con = new URL(url).openConnection();
			con.setConnectTimeout(10000);
			con.setReadTimeout(10000);
		    InputStream in = con.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(in,"UTF-8"));
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static boolean downloadFile(String folderurl, String filename, File folder) {
		try {
			URL filetodownload = new URL(folderurl + "/" + filename);
		    HttpURLConnection connection = (HttpURLConnection) filetodownload.openConnection();
		    connection.setConnectTimeout(10000);
		    connection.setReadTimeout(10000);
		    connection.setRequestMethod("GET");
		    connection.setDoOutput(true);
		    connection.connect();
		    FileOutputStream fout;
		    File file = new File(folder, filename);
		    if ((!file.exists())) file.createNewFile();
		    else file.delete();
		    fout = new FileOutputStream(file);
			InputStream in = connection.getInputStream();
		    byte[] buffer = new byte[1024];
		    int len1 = 0;
		    while ( (len1 = in.read(buffer)) > 0 ) {
		    	fout.write(buffer,0, len1);
		    }
		    fout.close();
		    return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static boolean downloadFile(String fileurl, File file) {
		try {
			if (!file.exists()) file.createNewFile();
			URL filetodownload = new URL(fileurl);
		    HttpURLConnection connection = (HttpURLConnection) filetodownload.openConnection();
		    connection.setConnectTimeout(10000);
		    connection.setReadTimeout(10000);
		    connection.setRequestMethod("GET");
		    connection.setDoOutput(true);
		    connection.connect();
		    FileOutputStream fout;
		    fout = new FileOutputStream(file);
			InputStream in = connection.getInputStream();
		    byte[] buffer = new byte[1024];
		    int len1 = 0;
		    while ( (len1 = in.read(buffer)) > 0 ) {
		    	fout.write(buffer,0, len1);
		    }
		    fout.close();
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
			HttpParams httpParams = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParams,
			5000);
			HttpConnectionParams.setSoTimeout(httpParams, 5000); 
			
	        httpclient = new DefaultHttpClient(httpParams);
			getMethod = new HttpGet(url);
			
			response = httpclient.execute(getMethod);
			respCode = response.getStatusLine().getStatusCode();
			if (respCode!=HttpURLConnection.HTTP_OK) {
				throw new ConnectionException("Problem at connecting to the file", 600);
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
			      com.o2hlink.healthgenius.connection.ByteBuffer strBuff = new com.o2hlink.healthgenius.connection.ByteBuffer();
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
	
	public boolean getContacts(ContactsManager messages) throws NotUpdatedException {
		try {
			messages.contactList = new Hashtable<String, Contact>();
			ContactListGetAction getContacts = new ContactListGetAction(user.getId());
			Array<Contact> contactlist = service.dispatch(getContacts);
			for (Contact contact:contactlist) {
				messages.contactList.put(contact.getId(), contact);
			}
			Contact yourself = new Contact(Long.toString(user.getId()), user.getFirstName(), user.getLastName());
			messages.contactList.put(yourself.getId(), yourself);	
			return true;
		} catch (ServerException e1) {
			e1.printStackTrace();
			return false;
		} catch (InvocationException e) {
			if (e.getCause() != null) {
				try {
					throw e.getCause();
				} catch (IncompatibleRemoteServiceException e0) {
					throw new NotUpdatedException();
				} catch (Throwable e1) {
					e.printStackTrace();
				}
			}
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean getEntryContacts(ContactsManager messages) throws NotUpdatedException {
		try {
			messages.entryContactList = new Array<ContactContactRequest>();
			ContactIncomingRequestListGetAction getContacts = new ContactIncomingRequestListGetAction(user.getId());
			messages.entryContactList = service.dispatch(getContacts);	
			return true;
		} catch (ServerException e1) {
			e1.printStackTrace();
			return false;
		} catch (InvocationException e) {
			if (e.getCause() != null) {
				try {
					throw e.getCause();
				} catch (IncompatibleRemoteServiceException e0) {
					throw new NotUpdatedException();
				} catch (Throwable e1) {
					e.printStackTrace();
				}
			}
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean AcceptContact(ContactsManager messages, ContactContactRequest contactRequest) throws NotUpdatedException {
		try {
			ContactAcceptAction action = new ContactAcceptAction(user.getId(), null, contactRequest);
			service.dispatch(action);	
			messages.entryContactList.remove(contactRequest);
			messages.contactList.put(contactRequest.getRequester().getId(), contactRequest.getRequester());
			return true;
		} catch (ServerException e1) {
			e1.printStackTrace();
			return false;
		} catch (InvocationException e) {
			if (e.getCause() != null) {
				try {
					throw e.getCause();
				} catch (IncompatibleRemoteServiceException e0) {
					throw new NotUpdatedException();
				} catch (Throwable e1) {
					e.printStackTrace();
				}
			}
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean RejectContact(ContactsManager messages, ContactContactRequest contactRequest) throws NotUpdatedException {
		try {
			ContactRejectAction action = new ContactRejectAction(user.getId(), null, contactRequest);
			service.dispatch(action);	
			messages.entryContactList.remove(contactRequest);
			return true;
		} catch (ServerException e1) {
			e1.printStackTrace();
			return false;
		} catch (InvocationException e) {
			if (e.getCause() != null) {
				try {
					throw e.getCause();
				} catch (IncompatibleRemoteServiceException e0) {
					throw new NotUpdatedException();
				} catch (Throwable e1) {
					e.printStackTrace();
				}
			}
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * This method deals with the obtaining of the calendar and the parsing by the calendar
	 * manager.
	 * @throws NotUpdatedException 
	 */
	public void getCalendar(CalendarManager calendar) throws NotUpdatedException {
		Date start = new Date();
		start.setMonth(start.getMonth() - 1);
		Date end = new Date();
		end.setMonth(end.getMonth() + 1);
		HealthGenius.myCalendarManager.events.clear();
		getCalendar(calendar, start, end);
	}	

	public boolean getCalendar(CalendarManager calendar, Date start , Date end) throws NotUpdatedException {
		boolean success = false, successmeas = false, successquest = false;
		success = getNonMeasuringEvents(calendar, start, end);
		if (HealthGenius.myMobileManager.userForServices instanceof Patient) {
			successmeas = getMeasuringEvents(calendar, start, end);
			successquest = getQuestEvents(calendar, start, end);
		}
		return (success&&successmeas&&successquest);
	}
	
	public int checkUserExistance() throws NotUpdatedException {
		try {
			UserLoginAction action = new UserLoginAction(HealthGenius.myMobileManager.user.getName(), HealthGenius.myMobileManager.user.getPassword());
			HealthGenius.myMobileManager.userForServices = service.dispatch(action);
			if (HealthGenius.myMobileManager.userForServices == null) return 1;
			HealthGenius.myMobileManager.user.setCreated(true);
			HealthGenius.myMobileManager.user.setId(HealthGenius.myMobileManager.userForServices.getId());
			HealthGenius.myMobileManager.user.firstname = HealthGenius.myMobileManager.userForServices.getFirstName();
			HealthGenius.myMobileManager.user.lastname = HealthGenius.myMobileManager.userForServices.getLastName();
			HealthGenius.myMobileManager.user.setBirthdate(HealthGenius.myMobileManager.userForServices.getBirthdate());
			HealthGenius.myMobileManager.user.setMail(HealthGenius.myMobileManager.userForServices.getEmail());
        	if (HealthGenius.myMobileManager.userForServices instanceof Patient) HealthGenius.myMobileManager.user.setType(0);
        	else if (HealthGenius.myMobileManager.userForServices instanceof Clinician) HealthGenius.myMobileManager.user.setType(1);
        	else if (HealthGenius.myMobileManager.userForServices instanceof Researcher) HealthGenius.myMobileManager.user.setType(2);
        	else return 2;
        	HealthGenius.myMobileManager.user.setSex(HealthGenius.myMobileManager.userForServices.getSex());
        	return 0;
		} catch (AuthenticationException e) {
			e.printStackTrace();
			return 1;
		} catch (NotFoundEntityException e) {
			e.printStackTrace();
			return 2;
		} catch (ServerException e) {
			e.printStackTrace();
			return 3;
		} catch (InvocationException e) {
			if (e.getCause() != null) {
				try {
					throw e.getCause();
				} catch (IncompatibleRemoteServiceException e0) {
					throw new NotUpdatedException();
				} catch (Throwable e1) {
					e.printStackTrace();
				}
			}
			e.printStackTrace();
			return 3;
		} catch (Exception e) {
			e.printStackTrace();
			return 3;
		}
	}
	
	public boolean login() throws NotUpdatedException {
		try {
			UserLoginAction action = new UserLoginAction(HealthGenius.myMobileManager.user.getName(), HealthGenius.myMobileManager.user.getPassword());
			HealthGenius.myMobileManager.userForServices = service.dispatch(action);
			user = HealthGenius.myMobileManager.userForServices;
			if (HealthGenius.myMobileManager.userForServices == null) return false;
			HealthGenius.myMobileManager.user.setCreated(true);
			HealthGenius.myMobileManager.user.setId(HealthGenius.myMobileManager.userForServices.getId());
			HealthGenius.myMobileManager.user.firstname = HealthGenius.myMobileManager.userForServices.getFirstName();
			HealthGenius.myMobileManager.user.lastname = HealthGenius.myMobileManager.userForServices.getLastName();
			HealthGenius.myMobileManager.user.setBirthdate(HealthGenius.myMobileManager.userForServices.getBirthdate());
			HealthGenius.myMobileManager.user.setMail(HealthGenius.myMobileManager.userForServices.getEmail());
        	if (HealthGenius.myMobileManager.userForServices instanceof Patient) HealthGenius.myMobileManager.user.setType(0);
        	else if (HealthGenius.myMobileManager.userForServices instanceof Clinician) HealthGenius.myMobileManager.user.setType(1);
        	else if (HealthGenius.myMobileManager.userForServices instanceof Researcher) HealthGenius.myMobileManager.user.setType(2);
        	else return false;
			HealthGenius.myMobileManager.user.setSex(HealthGenius.myMobileManager.userForServices.getSex());
        	HealthGenius.myProtocolManager.connected = true;
        	return true;
		} catch (Exception e) {
			if (e.getCause() != null) {
				try {
					throw e.getCause();
				} catch (IncompatibleRemoteServiceException e0) {
					throw new NotUpdatedException();
				} catch (Throwable e1) {
					e.printStackTrace();
				}
			}
			e.printStackTrace();
			return false;
		}	
	}
	
	@SuppressWarnings("unchecked")
	public boolean checkMeasurements() throws NotUpdatedException {
		if (HealthGenius.myMobileManager.userForServices instanceof Patient) {
			try {
				MeasurementAssignListGetAction assignlist = new MeasurementAssignListGetAction(user.getId(), HealthGenius.myMobileManager.userForServices.getId());
				Array<Measurement> measlist = service.dispatch(assignlist);
				ArrayList<Action<Entity>> actions = new ArrayList<Action<Entity>>();	
				MeasurementAssignAction buffer;
				Action<?> action;
				if (!measlist.contains(Measurement.PULSEOXYMETRY)) {
					buffer = new MeasurementAssignAction(user.getId(), HealthGenius.myMobileManager.userForServices.getId(), Measurement.PULSEOXYMETRY);
					action = buffer;
					actions.add((Action<Entity>) action);
				}
				if (!measlist.contains(Measurement.SPIROMETRY)) {
					buffer = new MeasurementAssignAction(user.getId(), HealthGenius.myMobileManager.userForServices.getId(), Measurement.SPIROMETRY);
					action = buffer;
					actions.add((Action<Entity>) action);
				}
				if (!measlist.contains(Measurement.SIX_MINUTES_WALK)) {
					buffer = new MeasurementAssignAction(user.getId(), HealthGenius.myMobileManager.userForServices.getId(), Measurement.SIX_MINUTES_WALK);
					action = buffer;
					actions.add((Action<Entity>) action);
				}
				if (!measlist.contains(Measurement.WEIGHT_HEIGHT)) {
					buffer = new MeasurementAssignAction(user.getId(), HealthGenius.myMobileManager.userForServices.getId(), Measurement.WEIGHT_HEIGHT);
					action = buffer;
					actions.add((Action<Entity>) action);
				}
				if (!measlist.contains(Measurement.EXERCISE)) {
					buffer = new MeasurementAssignAction(user.getId(), HealthGenius.myMobileManager.userForServices.getId(), Measurement.EXERCISE);
					action = buffer;
					actions.add((Action<Entity>) action);
				}
				service.dispatch(actions);
				return true;
			} catch (Exception e) {
				if (e.getCause() != null) {
					try {
						throw e.getCause();
					} catch (IncompatibleRemoteServiceException e0) {
						throw new NotUpdatedException();
					} catch (Throwable e1) {
						e.printStackTrace();
					}
				}
				e.printStackTrace();
				return false;
			}
		}
		else return true;
	}
	
	@SuppressWarnings("unchecked")
	public boolean register() throws NotUpdatedException {
		try {
			User user;
			switch (HealthGenius.myMobileManager.user.getType()) {
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
			user.setUsername(HealthGenius.myMobileManager.user.getName());
			user.setFirstName(HealthGenius.myMobileManager.user.getFirstname());
			user.setLastName(HealthGenius.myMobileManager.user.getLastname());
			user.setBirthdate(HealthGenius.myMobileManager.user.getBirthdate());
			user.setCountry(HealthGenius.myMobileManager.user.country);
			user.setEmail(HealthGenius.myMobileManager.user.getMail());
			user.setSex(HealthGenius.myMobileManager.user.getSex());
			UserNewAction action = new UserNewAction(user, HealthGenius.myMobileManager.user.getPassword(), false);
			HealthGenius.myMobileManager.userForServices = service.dispatch(action);
			this.user = HealthGenius.myMobileManager.userForServices;
			VisibilityUpdateAction publicy = new VisibilityUpdateAction(HealthGenius.myMobileManager.userForServices.getId(), HealthGenius.myMobileManager.userForServices, VisibilityLevel.PUBLIC_VIEW);
			service.dispatch(publicy);
			ArrayList<Action<Entity>> actions = new ArrayList<Action<Entity>>();			
			if (HealthGenius.myMobileManager.userForServices instanceof Patient) {
				MeasurementAssignAction buffer = new MeasurementAssignAction(user.getId(), HealthGenius.myMobileManager.userForServices.getId(), Measurement.PULSEOXYMETRY);
				Action<?> action1 = buffer;
				actions.add((Action<Entity>) action1);
				buffer = new MeasurementAssignAction(user.getId(), HealthGenius.myMobileManager.userForServices.getId(), Measurement.SPIROMETRY);
				Action<?> action2 = buffer;
				actions.add((Action<Entity>) action2);
				buffer = new MeasurementAssignAction(user.getId(), HealthGenius.myMobileManager.userForServices.getId(), Measurement.SIX_MINUTES_WALK);
				Action<?> action3 = buffer;
				actions.add((Action<Entity>) action3);
				buffer = new MeasurementAssignAction(user.getId(), HealthGenius.myMobileManager.userForServices.getId(), Measurement.EXERCISE);
				Action<?> action4 = buffer;
				actions.add((Action<Entity>) action4);
				buffer = new MeasurementAssignAction(user.getId(), HealthGenius.myMobileManager.userForServices.getId(), Measurement.WEIGHT_HEIGHT);
				Action<?> action5 = buffer;
				actions.add((Action<Entity>) action5);
			}
			service.dispatch(actions);
			try {
				Questionnaire feedback = new Questionnaire();
				feedback.setId(HealthGenius.myLanguageManager.feedbackQuestId);
				QuestionnaireAssignAction assignfeedbackquest = new QuestionnaireAssignAction(HealthGenius.myMobileManager.userForServices.getId(), feedback);
				service.dispatch(assignfeedbackquest);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (HealthGenius.myMobileManager.userForServices == null) return false;
			else {
	        	HealthGenius.myProtocolManager.connected = true;
				HealthGenius.myMobileManager.user.setCreated(true);
        		HealthGenius.myMobileManager.saveUsers();
				return true;
			}
		} catch (DuplicateEntityException e) {
			HealthGenius.myMobileManager.user.setCreated(true);
    		HealthGenius.myMobileManager.saveUsers();
			e.printStackTrace();
			return false;
		} catch (ServerException e) {
			e.printStackTrace();
			return false;
		} catch (InvocationException e) {
			if (e.getCause() != null) {
				try {
					throw e.getCause();
				} catch (IncompatibleRemoteServiceException e0) {
					throw new NotUpdatedException();
				} catch (Throwable e1) {
					e.printStackTrace();
				}
			}
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean updateUserPassword(String currentPassword, String newPassword) throws NotUpdatedException {
		try {
			UserPasswordChangeAction action = new UserPasswordChangeAction(HealthGenius.myMobileManager.userForServices.getId(), currentPassword, newPassword);
			service.dispatch(action);
			return true;
		} catch (ServerException e) {
			e.printStackTrace();
			return false;
		} catch (InvocationException e) {
			if (e.getCause() != null) {
				try {
					throw e.getCause();
				} catch (IncompatibleRemoteServiceException e0) {
					throw new NotUpdatedException();
				} catch (Throwable e1) {
					e.printStackTrace();
				}
			}
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean updateUserData() throws NotUpdatedException {
		try {
			HealthGenius.myMobileManager.userForServices.setUsername(HealthGenius.myMobileManager.user.getName());
			HealthGenius.myMobileManager.userForServices.setFirstName(HealthGenius.myMobileManager.user.getFirstname());
			HealthGenius.myMobileManager.userForServices.setEmail(HealthGenius.myMobileManager.user.getMail());
			HealthGenius.myMobileManager.userForServices.setLastName(HealthGenius.myMobileManager.user.getLastname());
			HealthGenius.myMobileManager.userForServices.setBirthdate(HealthGenius.myMobileManager.user.getBirthdate());
			HealthGenius.myMobileManager.userForServices.setCountry(HealthGenius.myMobileManager.user.country);
			HealthGenius.myMobileManager.userForServices.setSex(HealthGenius.myMobileManager.user.getSex());
			UserUpdateAction action = new UserUpdateAction(user.getId(), HealthGenius.myMobileManager.userForServices);
			service.dispatch(action);
			user = HealthGenius.myMobileManager.userForServices;
			return true;
		} catch (ServerException e) {
			e.printStackTrace();
			return false;
		} catch (InvocationException e) {
			if (e.getCause() != null) {
				try {
					throw e.getCause();
				} catch (IncompatibleRemoteServiceException e0) {
					throw new NotUpdatedException();
				} catch (Throwable e1) {
					e.printStackTrace();
				}
			}
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	public void refresh() throws NotUpdatedException {
		/* The updating of the configuration and the refreshing of connection */
		HealthGenius.myApp.refreshing = true;
		boolean success = false;
		if (!HealthGenius.myMobileManager.user.isCreated()) success = HealthGenius.myProtocolManager.register();
		else success = HealthGenius.myProtocolManager.login();
		if (success) {
			if (!HealthGenius.myMobileManager.user.isCreated()) {
				HealthGenius.myMobileManager.user.setCreated(true);
				HealthGenius.myMobileManager.addUserWithPassword(HealthGenius.myMobileManager.user);
			}
			checkMeasurements();
		    /* Send pending data */
		   ArrayList<Event> tempEvent = (ArrayList<Event>) HealthGenius.myPendingDataManager.pendingEvent.clone();
		    HealthGenius.myPendingDataManager.pendingEvent.clear();
		    Iterator<Event> it3 = tempEvent.iterator();
		    while (it3.hasNext()) {
		    	Event pendData = it3.next();
		    	com.o2hlink.activ8.client.entity.Event eventtoadd = new com.o2hlink.activ8.client.entity.Event();
				eventtoadd.setName(pendData.name);
				eventtoadd.setDescription(pendData.description);
				eventtoadd.setStart(pendData.date);
				eventtoadd.setEnd(pendData.dateEnd);
				eventtoadd.setFrequency(EventFrequency.NONE);
				eventtoadd.setEnd(pendData.dateEnd);
		    	if (HealthGenius.myProtocolManager.AddEvent(eventtoadd)) HealthGenius.myPendingDataManager.pendingEvent.add(pendData);
		    }
			ArrayList<com.o2hlink.healthgenius.patient.Event> tempPatEvent = (ArrayList<com.o2hlink.healthgenius.patient.Event>) HealthGenius.myPendingDataManager.pendingPatientEvent.clone();
		    HealthGenius.myPendingDataManager.pendingPatientEvent.clear();
		    Iterator<com.o2hlink.healthgenius.patient.Event> it4 = tempPatEvent.iterator();
		    while (it4.hasNext()) {
		    	com.o2hlink.healthgenius.patient.Event pendData = it4.next();
		    	com.o2hlink.activ8.client.entity.Event eventtoadd = new com.o2hlink.activ8.client.entity.Event();
				eventtoadd.setName(pendData.name);
				eventtoadd.setDescription(pendData.description);
				eventtoadd.setStart(pendData.date);
				eventtoadd.setEnd(pendData.dateEnd);
				eventtoadd.setFrequency(EventFrequency.NONE);
				eventtoadd.setEnd(pendData.dateEnd);
				boolean suc = false;
				switch (pendData.type) {
					case 0:
						Measurement meas = null;
						if (pendData.subtype.equals(com.o2hlink.healthgenius.data.sensor.SensorManager.ID_PULSIOXYMETER)) meas = Measurement.PULSEOXYMETRY; 
						else if (pendData.subtype.equals(com.o2hlink.healthgenius.data.sensor.SensorManager.ID_SPIROMETER)) meas = Measurement.SPIROMETRY;
						else if (pendData.subtype.equals(com.o2hlink.healthgenius.data.sensor.SensorManager.ID_EXERCISE)) meas = Measurement.SIX_MINUTES_WALK;
						if (meas != null) suc = HealthGenius.myProtocolManager.addMeasEvent(pendData.userId, meas, eventtoadd);
						break;
					case 1:
						suc = HealthGenius.myProtocolManager.addQuestEvent(pendData.userId, pendData.subtype, eventtoadd);
						break;
					default:
						break;
				}
		    	if (suc) HealthGenius.myPendingDataManager.pendingPatientEvent.add(pendData);
		    }
		    ArrayList<Action<?>> tempActions = (ArrayList<Action<?>>) HealthGenius.myPendingDataManager.pendingActions.clone();
		    HealthGenius.myPendingDataManager.pendingActions.clear();
		    Iterator<Action<?>> it6 = tempActions.iterator();
		    while (it6.hasNext()) {
		    	Action<?> pendAction = it6.next();
		    	try {
					if (!HealthGenius.myProtocolManager.dispatch(pendAction)) HealthGenius.myPendingDataManager.pendingActions.add(pendAction);
				} catch (NotUpdatedException e) {
					throw new NotUpdatedException();
				}
		    }
		    HealthGenius.myPendingDataManager.passPendingDataToFile();
		    HealthGenius.myPendingDataManager.passPendingActionsToFile();
		    /* The updating of the data */
			HealthGenius.myQuestControlManager.getQuestionnaires();
		    HealthGenius.myPatientManager.getPatientList();
		    HealthGenius.myCalendarManager.getCalendar();
		    HealthGenius.myContactsManager.requestContactList();
			HealthGenius.myContactsManager.requestEntryContactList();
		    /* Update event status and send event outcomes for canceled events */
		    Date dateLate = new Date();
		    dateLate.setHours(dateLate.getHours() - 24);
		    Enumeration<Event> enumeration = HealthGenius.myCalendarManager.events.elements();
		    while (enumeration.hasMoreElements()) {
		    	Event event = enumeration.nextElement();
		    	event.updateState();
		    }
			HealthGenius.myApp.refreshing = false;
		}
	}
	
	public boolean sendWeightAndHeight(float weight, float height) throws NotUpdatedException {
		WeightHeight sample = new WeightHeight();
		sample.setDate(new Date());
		sample.setEvent(null);
		sample.setWeight(weight);
		sample.setHeight(height);
		try {
			SampleNewAction action = new SampleNewAction(HealthGenius.myMobileManager.userForServices.getId(), Measurement.WEIGHT_HEIGHT, sample);
			service.dispatch(action);
			return true;
		} catch (Exception e) {
			if (e.getCause() != null) {
				try {
					throw e.getCause();
				} catch (IncompatibleRemoteServiceException e0) {
					throw new NotUpdatedException();
				} catch (Throwable e1) {
					e.printStackTrace();
				}
			}
			SampleNewAction action = new SampleNewAction(HealthGenius.myMobileManager.userForServices.getId(), Measurement.WEIGHT_HEIGHT, sample);
			HealthGenius.myPendingDataManager.pendingActions.add(action);
			HealthGenius.myPendingDataManager.passPendingActionsToFile();
			e.printStackTrace();
			return false;
		}
	}

	public boolean sendSensorMeasurement(Sensor sensor) throws NotUpdatedException {
		SampleNewAction action = null;
		SpirometryNewAction flows = null;
		SixMinutesWalkNewAction track = null;
		try {
			if (sensor.id == com.o2hlink.healthgenius.data.sensor.SensorManager.ID_PULSIOXYMETER)
				action = new SampleNewAction(HealthGenius.myMobileManager.userForServices.getId(), Measurement.PULSEOXYMETRY, sensor.getSample());
			else if (sensor.id == com.o2hlink.healthgenius.data.sensor.SensorManager.ID_SPIROMETER)
				action = new SampleNewAction(HealthGenius.myMobileManager.userForServices.getId(), Measurement.SPIROMETRY, sensor.getSample());
			else if (sensor.id == com.o2hlink.healthgenius.data.sensor.SensorManager.ID_SIXMINUTES)
				action = new SampleNewAction(HealthGenius.myMobileManager.userForServices.getId(), Measurement.SIX_MINUTES_WALK, sensor.getSample());
			else if (sensor.id == com.o2hlink.healthgenius.data.sensor.SensorManager.ID_EXERCISE)
				action = new SampleNewAction(HealthGenius.myMobileManager.userForServices.getId(), Measurement.EXERCISE, sensor.getSample());
			else if (sensor.id == com.o2hlink.healthgenius.data.sensor.SensorManager.ID_WEIGHT)
				action = new SampleNewAction(HealthGenius.myMobileManager.userForServices.getId(), Measurement.WEIGHT_HEIGHT, sensor.getSample());
			else action = null;
			service.dispatch(action);
			if (sensor.id == com.o2hlink.healthgenius.data.sensor.SensorManager.ID_SPIROMETER) {
				flows = new SpirometryNewAction(HealthGenius.myMobileManager.userForServices.getId(), action.getSample().getDate(), ((Spirometer)sensor).getFlows());
				action = null;
				service.dispatch(flows);
			}
			else if (sensor.id == com.o2hlink.healthgenius.data.sensor.SensorManager.ID_SIXMINUTES) {
				track = new SixMinutesWalkNewAction(HealthGenius.myMobileManager.userForServices.getId(), action.getSample().getDate(), ((SixMinutes)sensor).getGraphs());
				action = null;
				service.dispatch(track);
			}
			return true;
		} catch (ServerException e) {
			if (action != null) {
				if (HealthGenius.myMobileManager.userForServices == null) action = new SampleNewAction(HealthGenius.myMobileManager.user.getId(), action.getMeasurement(), action.getSample());
				HealthGenius.myPendingDataManager.pendingActions.add(action);
				if ((sensor.id == com.o2hlink.healthgenius.data.sensor.SensorManager.ID_SPIROMETER)&&(flows == null)) 
					flows = new SpirometryNewAction(HealthGenius.myMobileManager.userForServices.getId(), action.getSample().getDate(), ((Spirometer)sensor).getFlows());
			}
			if (flows != null) {
				if (HealthGenius.myMobileManager.userForServices == null) flows = new SpirometryNewAction(HealthGenius.myMobileManager.user.getId(), flows.getDate(), flows.getFlow());
				HealthGenius.myPendingDataManager.pendingActions.add(flows);
			}
			if (track != null) {
				if (HealthGenius.myMobileManager.userForServices == null) track = new SixMinutesWalkNewAction(HealthGenius.myMobileManager.user.getId(), track.getDate(), track.getFlow());
				HealthGenius.myPendingDataManager.pendingActions.add(track);
			}
			HealthGenius.myPendingDataManager.passPendingActionsToFile();
			e.printStackTrace();
			return false;
		} catch (InvocationException e) {
			if (e.getCause() != null) {
				try {
					throw e.getCause();
				} catch (IncompatibleRemoteServiceException e0) {
					throw new NotUpdatedException();
				} catch (Throwable e1) {
					e.printStackTrace();
				}
			}
			if (action != null) {
				if (HealthGenius.myMobileManager.userForServices == null) action = new SampleNewAction(HealthGenius.myMobileManager.user.getId(), action.getMeasurement(), action.getSample());
				HealthGenius.myPendingDataManager.pendingActions.add(action);
				if ((sensor.id == com.o2hlink.healthgenius.data.sensor.SensorManager.ID_SPIROMETER)&&(flows == null)) 
					flows = new SpirometryNewAction(HealthGenius.myMobileManager.userForServices.getId(), action.getSample().getDate(), ((Spirometer)sensor).getFlows());
			}
			if (flows != null) {
				if (HealthGenius.myMobileManager.userForServices == null) flows = new SpirometryNewAction(HealthGenius.myMobileManager.user.getId(), flows.getDate(), flows.getFlow());
				HealthGenius.myPendingDataManager.pendingActions.add(flows);
			}

			if (track != null) {
				if (HealthGenius.myMobileManager.userForServices == null) track = new SixMinutesWalkNewAction(HealthGenius.myMobileManager.user.getId(), track.getDate(), track.getFlow());
				HealthGenius.myPendingDataManager.pendingActions.add(track);
			}
			HealthGenius.myPendingDataManager.passPendingActionsToFile();
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			if (action != null) {
				if (HealthGenius.myMobileManager.userForServices == null) action = new SampleNewAction(HealthGenius.myMobileManager.user.getId(), action.getMeasurement(), action.getSample());
				HealthGenius.myPendingDataManager.pendingActions.add(action);
				if ((sensor.id == com.o2hlink.healthgenius.data.sensor.SensorManager.ID_SPIROMETER)&&(flows == null)) 
					flows = new SpirometryNewAction(HealthGenius.myMobileManager.userForServices.getId(), action.getSample().getDate(), ((Spirometer)sensor).getFlows());
			}
			if (flows != null) {
				if (HealthGenius.myMobileManager.userForServices == null) flows = new SpirometryNewAction(HealthGenius.myMobileManager.user.getId(), flows.getDate(), flows.getFlow());
				HealthGenius.myPendingDataManager.pendingActions.add(flows);
			}

			if (track != null) {
				if (HealthGenius.myMobileManager.userForServices == null) track = new SixMinutesWalkNewAction(HealthGenius.myMobileManager.user.getId(), track.getDate(), track.getFlow());
				HealthGenius.myPendingDataManager.pendingActions.add(track);
			}
			HealthGenius.myPendingDataManager.passPendingActionsToFile();
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean getPatientList() throws NotUpdatedException {
		boolean returned = true;
		HealthGenius.myPatientManager.patientSet = new Hashtable<Long, com.o2hlink.healthgenius.patient.Patient>();
		UserListGetAction getpatients = new UserListGetAction(user.getId(), HealthGenius.myMobileManager.userForServices);
		try {
			Array<User> patients = service.dispatch(getpatients);
			for (User patient : patients) {
				if (patient instanceof Patient) {
					com.o2hlink.healthgenius.patient.Patient pat = new com.o2hlink.healthgenius.patient.Patient((Patient)patient);
					HealthGenius.myPatientManager.patientSet.put(pat.getId(), pat);					
				}
			}
		} catch (ServerException e) {
			e.printStackTrace();
			returned = false;
		} catch (InvocationException e) {
			if (e.getCause() != null) {
				try {
					throw e.getCause();
				} catch (IncompatibleRemoteServiceException e0) {
					throw new NotUpdatedException();
				} catch (Throwable e1) {
					e.printStackTrace();
				}
			}
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			returned = false;
		} 
		return returned;
	}
	
	public Array<com.o2hlink.healthgenius.patient.Patient> searchPatients (String query) throws NotUpdatedException {
		try {
			UserSearchAction action = new UserSearchAction(user.getId(), query);
			Array<User> users = service.dispatch(action);
			Array<com.o2hlink.healthgenius.patient.Patient> patients = new Array<com.o2hlink.healthgenius.patient.Patient>();
			for (User pospat:users) {
				if (pospat instanceof Patient) patients.add(new com.o2hlink.healthgenius.patient.Patient((Patient) pospat));
			}
			return patients;
		} catch (Exception e) {
			if (e.getCause() != null) {
				try {
					throw e.getCause();
				} catch (IncompatibleRemoteServiceException e0) {
					throw new NotUpdatedException();
				} catch (Throwable e1) {
					e.printStackTrace();
				}
			}
			e.printStackTrace();
			return null;
		}
		
	}

	public boolean AddPatient (Patient patient) throws NotUpdatedException {
		try {
			Contact requester = new Contact(Long.toString(user.getId()), user.getFirstName(), user.getLastName());
			Contact requested = new Contact(Long.toString(patient.getId()), patient.getFirstName(), patient.getLastName());
			ContactContactRequest request = new ContactContactRequest(requester, requested, "");
			ContactRequestAction action = new ContactRequestAction(user.getId(), request);
			service.dispatch(action);
			return true;
		} catch (ServerException e) {
			e.printStackTrace();
			return false;
		} catch (InvocationException e) {
			if (e.getCause() != null) {
				try {
					throw e.getCause();
				} catch (IncompatibleRemoteServiceException e0) {
					throw new NotUpdatedException();
				} catch (Throwable e1) {
					e.printStackTrace();
				}
			}
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean removePatient (Patient patient) throws NotUpdatedException {
		try {
			Contact deleted = new Contact(Long.toString(patient.getId()), patient.getFirstName(), patient.getLastName());
			ContactRemoveAction action = new ContactRemoveAction(user.getId(), null, deleted);
			service.dispatch(action);
			HealthGenius.myPatientManager.patientSet.remove(user.getId());
			return true;
		} catch (ServerException e) {
			e.printStackTrace();
			return false;
		} catch (InvocationException e) {
			if (e.getCause() != null) {
				try {
					throw e.getCause();
				} catch (IncompatibleRemoteServiceException e0) {
					throw new NotUpdatedException();
				} catch (Throwable e1) {
					e.printStackTrace();
				}
			}
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean getPatientHistory (com.o2hlink.healthgenius.patient.Patient patient) throws NotUpdatedException {
		Date now = new Date();
		Date ayearago = new Date();
		ayearago.setYear(ayearago.getYear() - 1);
		boolean returned = true;
		patient.history = new Hashtable<Long, com.o2hlink.healthgenius.patient.HistoryRecord>();
		try {
			HistoryGetAction getHistory = new HistoryGetAction(user.getId(), patient.getId());
			Array<HistoryRecord> history = service.dispatch(getHistory);
			for (HistoryRecord record : history) {
				com.o2hlink.healthgenius.patient.HistoryRecord rec = new com.o2hlink.healthgenius.patient.HistoryRecord(record.getId(), record.getDisease().getName(), record.getDisease().getDescription(), record.getDate());
				rec.historyRecordForServices = record;
				rec.date = record.getDate();
				Translate.setHttpReferrer("http://www.o2hlink.com");
				String selectedLang = HealthGenius.myApp.getResources().getConfiguration().locale.getLanguage();
				if (selectedLang.equals("es")) rec.diseaseName = Translate.execute(record.getDisease().getName(), Language.ENGLISH, Language.SPANISH);
				else rec.diseaseName = record.getDisease().getName();
				ExplorationListGetAction getExplorations = new ExplorationListGetAction(user.getId(), record);
				Array<Exploration> explorations = service.dispatch(getExplorations);
				for (Exploration exploration : explorations) {
					com.o2hlink.healthgenius.patient.Exploration expl = new com.o2hlink.healthgenius.patient.Exploration(exploration.getId());
					expl.exploration = exploration.getDescription();
					expl.date = exploration.getDate();
					rec.explorations.put(expl.id, expl);
				}
				patient.history.put(rec.id, rec);
				
			}			
			SampleListGetAction getsamples = new SampleListGetAction(user.getId(), patient.getId(), Measurement.PULSEOXYMETRY, ayearago, now);
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
				patient.lastPulseoximetry = new PulseoximetrySample((Pulseoxymetry)lastone);
			}
			getsamples = new SampleListGetAction(user.getId(), patient.getId(), Measurement.WEIGHT_HEIGHT, ayearago, now);
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
				patient.lastWeightHeight = new WeightHeightSample((WeightHeight)lastone);
			}
			getsamples = new SampleListGetAction(user.getId(), patient.getId(), Measurement.EXERCISE, ayearago, now);
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
				patient.lastExercise = new com.o2hlink.healthgenius.patient.ExerciseSample((Exercise)lastone);
			}
			getsamples = new SampleListGetAction(user.getId(), patient.getId(), Measurement.SIX_MINUTES_WALK, ayearago, now);
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
				SixMinutesWalkGetAction gettrack = new SixMinutesWalkGetAction(user.getId(), patient.getId(), lastone.getDate());
				SixMinutesWalkFlow track = service.dispatch(gettrack);
				patient.lastSixMinutes = new SixMinutesWalkSample((SixMinutesWalk)lastone, track);
			}
			getsamples = new SampleListGetAction(user.getId(), patient.getId(), Measurement.SPIROMETRY, ayearago, now);
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
				SpirometryGetAction getgraphs = new SpirometryGetAction(user.getId(), patient.getId(), lastone.getDate());
				SpirometryFlow graphs = service.dispatch(getgraphs);
				patient.lastSpirometry = new SpirometrySample((Spirometry)lastone, graphs);
			}
		} catch (ServerException e) {
			e.printStackTrace();
			returned = false;
		} catch (InvocationException e) {
			if (e.getCause() != null) {
				try {
					throw e.getCause();
				} catch (IncompatibleRemoteServiceException e0) {
					throw new NotUpdatedException();
				} catch (Throwable e1) {
					e.printStackTrace();
				}
			}
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return returned;
	}

	public Array<Questionnaire> SearchQuests (String query) throws NotUpdatedException {
		QuestionnaireSearchAction action = new QuestionnaireSearchAction(HealthGenius.myMobileManager.userForServices.getId(), query);
		try {
			return service.dispatch(action);
		} catch (Exception e) {
			if (e.getCause() != null) {
				try {
					throw e.getCause();
				} catch (IncompatibleRemoteServiceException e0) {
					throw new NotUpdatedException();
				} catch (Throwable e1) {
					e.printStackTrace();
				}
			}
			e.printStackTrace();
			return null;
		}
	}

	public Array<Contact> SearchContacts (String query) throws NotUpdatedException {
		ContactSearchAction action = new ContactSearchAction(user.getId(), query);
		try {
			return service.dispatch(action);
		} catch (Exception e) {
			if (e.getCause() != null) {
				try {
					throw e.getCause();
				} catch (IncompatibleRemoteServiceException e0) {
					throw new NotUpdatedException();
				} catch (Throwable e1) {
					e.printStackTrace();
				}
			}
			e.printStackTrace();
			return null;
		}
	}

	public boolean addContact (Contact contact) throws NotUpdatedException {
		ContactContactRequest request = new ContactContactRequest(new Contact(Long.toString(user.getId()), user.getFirstName(), user.getLastName()), contact, "");
		ContactRequestAction action = new ContactRequestAction(user.getId(), request);
		try {
			service.dispatch(action);
			//TODO
			return true;
		} catch (Exception e) {
			if (e.getCause() != null) {
				try {
					throw e.getCause();
				} catch (IncompatibleRemoteServiceException e0) {
					throw new NotUpdatedException();
				} catch (Throwable e1) {
					e.printStackTrace();
				}
			}
			e.printStackTrace();
			return false;
		}
	}

	public boolean removeContact (Contact contact) throws NotUpdatedException {
		try {
			ContactRemoveAction action = new ContactRemoveAction(user.getId(), null, contact);
			service.dispatch(action);
			HealthGenius.myContactsManager.contactList.remove(contact.getId());
			return true;
		} catch (ServerException e) {
			e.printStackTrace();
			return false;
		} catch (InvocationException e) {
			if (e.getCause() != null) {
				try {
					throw e.getCause();
				} catch (IncompatibleRemoteServiceException e0) {
					throw new NotUpdatedException();
				} catch (Throwable e1) {
					e.printStackTrace();
				}
			}
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean getNonMeasuringEvents (CalendarManager calendar, Date start, Date end) throws NotUpdatedException {
		start.setHours(start.getHours() - 1);
		end.setHours(end.getHours() - 1);
		try {
			EventListGetAction action = new EventListGetAction(user.getId(), start, end);
			Array<com.o2hlink.activ8.client.entity.Event> events = service.dispatch(action);
			Iterator<com.o2hlink.activ8.client.entity.Event> it = events.iterator();
			while (it.hasNext()) {
				com.o2hlink.activ8.client.entity.Event serviceEvent = it.next();
				if (!calendar.events.containsKey(serviceEvent.getId())) {
					calendar.servicevents.put(serviceEvent.getId(), serviceEvent);
					Event event = new Event();
					event.id = serviceEvent.getId();
					event.date = serviceEvent.getStart();
					event.name = serviceEvent.getName();
					event.description = serviceEvent.getDescription();
					event.dateEnd = serviceEvent.getEnd();
					event.type = 2;
					event.subtype = -1l;
					if (event.dateEnd.after(new Date())) event.state = 2;
					else event.state = 0;
					calendar.events.put(event.id, event);
				}
			}
			return true;
		} catch (ServerException e) {
			e.printStackTrace();
			return false;
		} catch (InvocationException e) {
			if (e.getCause() != null) {
				try {
					throw e.getCause();
				} catch (IncompatibleRemoteServiceException e0) {
					throw new NotUpdatedException();
				} catch (Throwable e1) {
					e.printStackTrace();
				}
			}
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@SuppressWarnings("unchecked")
	public boolean getMeasuringEvents (CalendarManager calendar, Date start, Date end) throws NotUpdatedException {
		try {
			Date now = new Date();
			ArrayList<Action<Entity>> measurementactions = new ArrayList<Action<Entity>>();
			MeasurementEventListGetAction buffer = new MeasurementEventListGetAction(user.getId(), user.getId(), Measurement.PULSEOXYMETRY, start, end);
			Action<?> measurementaction = buffer;
			measurementactions.add((Action<Entity>) measurementaction);
			buffer = new MeasurementEventListGetAction(user.getId(), user.getId(), Measurement.SPIROMETRY, start, end);
			measurementaction = buffer;
			measurementactions.add((Action<Entity>) measurementaction);
			buffer = new MeasurementEventListGetAction(user.getId(), user.getId(), Measurement.SIX_MINUTES_WALK, start, end);
			measurementaction = buffer;
			measurementactions.add((Action<Entity>) measurementaction);
			buffer = new MeasurementEventListGetAction(user.getId(), user.getId(), Measurement.WEIGHT_HEIGHT, start, end);
			measurementaction = buffer;
			measurementactions.add((Action<Entity>) measurementaction);
			buffer = new MeasurementEventListGetAction(user.getId(), user.getId(), Measurement.EXERCISE, start, end);
			measurementaction = buffer;
			measurementactions.add((Action<Entity>) measurementaction);
			
			ArrayList<Entity> totalevents = service.dispatch(measurementactions);
			
			if (totalevents.size() != 5) return false;
			List<com.o2hlink.activ8.client.entity.Event> pulseoxyevents = (List<com.o2hlink.activ8.client.entity.Event>) totalevents.get(0);
			for (com.o2hlink.activ8.client.entity.Event serviceEvent : pulseoxyevents) {
				calendar.servicevents.put(serviceEvent.getId(), serviceEvent);
				Event event = new Event();
				event.id = serviceEvent.getId();
				event.date = serviceEvent.getStart();
				event.name = serviceEvent.getName();
				event.description = serviceEvent.getDescription();
				event.dateEnd = serviceEvent.getEnd();
				event.type = 0;
				event.subtype = com.o2hlink.healthgenius.data.sensor.SensorManager.ID_PULSIOXYMETER;
				if (serviceEvent.isDone()) event.state = 0;
				else if (event.dateEnd.after(now)) event.state = 2;
				else event.state = 1;
				calendar.events.put(event.id, event);
			}
			List<com.o2hlink.activ8.client.entity.Event> spiroevents = (List<com.o2hlink.activ8.client.entity.Event>) totalevents.get(1);
			for (com.o2hlink.activ8.client.entity.Event serviceEvent : spiroevents) {
				calendar.servicevents.put(serviceEvent.getId(), serviceEvent);
				Event event = new Event();
				event.id = serviceEvent.getId();
				event.date = serviceEvent.getStart();
				event.name = serviceEvent.getName();
				event.description = serviceEvent.getDescription();
				event.dateEnd = serviceEvent.getEnd();
				event.type = 0;
				event.subtype = com.o2hlink.healthgenius.data.sensor.SensorManager.ID_SPIROMETER;
				if (serviceEvent.isDone()) event.state = 0;
				else if (event.dateEnd.after(now)) event.state = 2;
				else event.state = 1;
				calendar.events.put(event.id, event);
			}
			List<com.o2hlink.activ8.client.entity.Event> sixminevents = (List<com.o2hlink.activ8.client.entity.Event>) totalevents.get(2);
			for (com.o2hlink.activ8.client.entity.Event serviceEvent : sixminevents) {
				calendar.servicevents.put(serviceEvent.getId(), serviceEvent);
				Event event = new Event();
				event.id = serviceEvent.getId();
				event.date = serviceEvent.getStart();
				event.name = serviceEvent.getName();
				event.description = serviceEvent.getDescription();
				event.dateEnd = serviceEvent.getEnd();
				event.type = 0;
				event.subtype = com.o2hlink.healthgenius.data.sensor.SensorManager.ID_SIXMINUTES;
				if (serviceEvent.isDone()) event.state = 0;
				else if (event.dateEnd.after(now)) event.state = 2;
				else event.state = 1;
				calendar.events.put(event.id, event);
			}
			List<com.o2hlink.activ8.client.entity.Event> weiheievents = (List<com.o2hlink.activ8.client.entity.Event>) totalevents.get(3);
			for (com.o2hlink.activ8.client.entity.Event serviceEvent : weiheievents) {
				calendar.servicevents.put(serviceEvent.getId(), serviceEvent);
				Event event = new Event();
				event.id = serviceEvent.getId();
				event.date = serviceEvent.getStart();
				event.name = serviceEvent.getName();
				event.description = serviceEvent.getDescription();
				event.dateEnd = serviceEvent.getEnd();
				event.type = 0;
				event.subtype = com.o2hlink.healthgenius.data.sensor.SensorManager.ID_WEIGHT;
				if (serviceEvent.isDone()) event.state = 0;
				else if (event.dateEnd.after(now)) event.state = 2;
				else event.state = 1;
				calendar.events.put(event.id, event);
			}
			List<com.o2hlink.activ8.client.entity.Event> exerevents = (List<com.o2hlink.activ8.client.entity.Event>) totalevents.get(4);
			for (com.o2hlink.activ8.client.entity.Event serviceEvent : exerevents) {
				calendar.servicevents.put(serviceEvent.getId(), serviceEvent);
				Event event = new Event();
				event.id = serviceEvent.getId();
				event.date = serviceEvent.getStart();
				event.name = serviceEvent.getName();
				event.description = serviceEvent.getDescription();
				event.dateEnd = serviceEvent.getEnd();
				event.type = 0;
				event.subtype = com.o2hlink.healthgenius.data.sensor.SensorManager.ID_EXERCISE;
				if (serviceEvent.isDone()) event.state = 0;
				else if (event.dateEnd.after(now)) event.state = 2;
				else event.state = 1;
				calendar.events.put(event.id, event);
			}
			return true;
		} catch (ServerException e) {
			e.printStackTrace();
			return false;
		} catch (InvocationException e) {
			if (e.getCause() != null) {
				try {
					throw e.getCause();
				} catch (IncompatibleRemoteServiceException e0) {
					throw new NotUpdatedException();
				} catch (Throwable e1) {
					e.printStackTrace();
				}
			}
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@SuppressWarnings("unchecked")
	public boolean getMeasuringEvents (Long patientId, Date start, Date end) {
		try {
			Date now = new Date();
			ArrayList<Action<Entity>> measurementactions = new ArrayList<Action<Entity>>();
			MeasurementEventListGetAction buffer = new MeasurementEventListGetAction(user.getId(), patientId, Measurement.PULSEOXYMETRY, start, end);
			Action<?> measurementaction = buffer;
			measurementactions.add((Action<Entity>) measurementaction);
			buffer = new MeasurementEventListGetAction(user.getId(), patientId, Measurement.SPIROMETRY, start, end);
			measurementaction = buffer;
			measurementactions.add((Action<Entity>) measurementaction);
			buffer = new MeasurementEventListGetAction(user.getId(), patientId, Measurement.SIX_MINUTES_WALK, start, end);
			measurementaction = buffer;
			measurementactions.add((Action<Entity>) measurementaction);
			buffer = new MeasurementEventListGetAction(user.getId(), patientId, Measurement.WEIGHT_HEIGHT, start, end);
			measurementaction = buffer;
			measurementactions.add((Action<Entity>) measurementaction);
			buffer = new MeasurementEventListGetAction(user.getId(), patientId, Measurement.EXERCISE, start, end);
			measurementaction = buffer;
			measurementactions.add((Action<Entity>) measurementaction);
			
			ArrayList<Entity> totalevents = service.dispatch(measurementactions);
			
			if (totalevents.size() != 5) return false;
			List<com.o2hlink.activ8.client.entity.Event> pulseoxyevents = (List<com.o2hlink.activ8.client.entity.Event>) totalevents.get(0);
			for (com.o2hlink.activ8.client.entity.Event serviceEvent : pulseoxyevents) {
				com.o2hlink.healthgenius.patient.Event event = new com.o2hlink.healthgenius.patient.Event();
				event.id = serviceEvent.getId();
				event.date = serviceEvent.getStart();
				event.name = serviceEvent.getName();
				event.description = serviceEvent.getDescription();
				event.dateEnd = serviceEvent.getEnd();
				event.type = 0;
				event.subtype = com.o2hlink.healthgenius.data.sensor.SensorManager.ID_PULSIOXYMETER;
				if (serviceEvent.isDone()) event.state = 0;
				else if (event.dateEnd.after(now)) event.state = 2;
				else event.state = 1;
				event.userId = patientId;
				HealthGenius.myPatientManager.currentPatMeasEventSet.put(event.id, event);
			}
			List<com.o2hlink.activ8.client.entity.Event> spiroevents = (List<com.o2hlink.activ8.client.entity.Event>) totalevents.get(1);
			for (com.o2hlink.activ8.client.entity.Event serviceEvent : spiroevents) {
				com.o2hlink.healthgenius.patient.Event event = new com.o2hlink.healthgenius.patient.Event();
				event.id = serviceEvent.getId();
				event.date = serviceEvent.getStart();
				event.name = serviceEvent.getName();
				event.description = serviceEvent.getDescription();
				event.dateEnd = serviceEvent.getEnd();
				event.type = 0;
				event.subtype = com.o2hlink.healthgenius.data.sensor.SensorManager.ID_SPIROMETER;
				if (serviceEvent.isDone()) event.state = 0;
				else if (event.dateEnd.after(now)) event.state = 2;
				else event.state = 1;
				event.userId = patientId;
				HealthGenius.myPatientManager.currentPatMeasEventSet.put(event.id, event);
			}
			List<com.o2hlink.activ8.client.entity.Event> sixminevents = (List<com.o2hlink.activ8.client.entity.Event>) totalevents.get(2);
			for (com.o2hlink.activ8.client.entity.Event serviceEvent : sixminevents) {
				com.o2hlink.healthgenius.patient.Event event = new com.o2hlink.healthgenius.patient.Event();
				event.id = serviceEvent.getId();
				event.date = serviceEvent.getStart();
				event.name = serviceEvent.getName();
				event.description = serviceEvent.getDescription();
				event.dateEnd = serviceEvent.getEnd();
				event.type = 0;
				event.subtype = com.o2hlink.healthgenius.data.sensor.SensorManager.ID_SIXMINUTES;
				if (serviceEvent.isDone()) event.state = 0;
				else if (event.dateEnd.after(now)) event.state = 2;
				else event.state = 1;
				event.userId = patientId;
				HealthGenius.myPatientManager.currentPatMeasEventSet.put(event.id, event);
			}
			List<com.o2hlink.activ8.client.entity.Event> weiheievents = (List<com.o2hlink.activ8.client.entity.Event>) totalevents.get(3);
			for (com.o2hlink.activ8.client.entity.Event serviceEvent : weiheievents) {
				com.o2hlink.healthgenius.patient.Event event = new com.o2hlink.healthgenius.patient.Event();
				event.id = serviceEvent.getId();
				event.date = serviceEvent.getStart();
				event.name = serviceEvent.getName();
				event.description = serviceEvent.getDescription();
				event.dateEnd = serviceEvent.getEnd();
				event.type = 0;
				event.subtype = com.o2hlink.healthgenius.data.sensor.SensorManager.ID_WEIGHT;
				if (serviceEvent.isDone()) event.state = 0;
				else if (event.dateEnd.after(now)) event.state = 2;
				else event.state = 1;
				event.userId = patientId;
				HealthGenius.myPatientManager.currentPatMeasEventSet.put(event.id, event);
			}
			List<com.o2hlink.activ8.client.entity.Event> exerevents = (List<com.o2hlink.activ8.client.entity.Event>) totalevents.get(4);
			for (com.o2hlink.activ8.client.entity.Event serviceEvent : exerevents) {
				com.o2hlink.healthgenius.patient.Event event = new com.o2hlink.healthgenius.patient.Event();
				event.id = serviceEvent.getId();
				event.date = serviceEvent.getStart();
				event.name = serviceEvent.getName();
				event.description = serviceEvent.getDescription();
				event.dateEnd = serviceEvent.getEnd();
				event.type = 0;
				event.subtype = com.o2hlink.healthgenius.data.sensor.SensorManager.ID_EXERCISE;
				if (serviceEvent.isDone()) event.state = 0;
				else if (event.dateEnd.after(now)) event.state = 2;
				else event.state = 1;
				event.userId = patientId;
				HealthGenius.myPatientManager.currentPatMeasEventSet.put(event.id, event);
			}
			return true;
		} catch (ServerException e) {
			e.printStackTrace();
			return false;
		} catch (InvocationException e) {
			if (e.getCause() != null) {
				try {
					throw e.getCause();
				} catch (IncompatibleRemoteServiceException e0) {
					HealthGenius.myUIManager.UImisc.loadTextOnWindow(HealthGenius.myLanguageManager.TEXT_UPDATEVERSION);
				} catch (Throwable e1) {
					e.printStackTrace();
				}
			}
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@SuppressWarnings("unchecked")
	public boolean getQuestEvents (CalendarManager calendar, Date start, Date end) {
		try {
			Date now = new Date();
			ArrayList<Action<Entity>> questeventsactions = new ArrayList<Action<Entity>>();
			ArrayList<Long> questInternalTrack = new ArrayList<Long>();
			QuestionnaireAssignListGetAction action = new QuestionnaireAssignListGetAction(user.getId());
			Array<com.o2hlink.activ8.client.entity.Questionnaire> questionnaires = service.dispatch(action);
			for (Iterator iterator = questionnaires.iterator(); iterator.hasNext();) {
				Questionnaire questionnaire = (Questionnaire) iterator.next();
				QuestionnaireEventListGetAction buffer = new QuestionnaireEventListGetAction(user.getId(), user.getId(), questionnaire.getId(), start, end);
				Action<?> questaction = buffer;
				questeventsactions.add((Action<Entity>) questaction);
				questInternalTrack.add(questionnaire.getId());
			}
			if (questeventsactions.isEmpty()) return true;
			ArrayList<Entity> totalevents = service.dispatch(questeventsactions);
			for (int i = 0; i < totalevents.size(); i++) {
				List<com.o2hlink.activ8.client.entity.Event> questevents = (List<com.o2hlink.activ8.client.entity.Event>) totalevents.get(i);
				for (com.o2hlink.activ8.client.entity.Event serviceEvent : questevents) {
					calendar.servicevents.put(serviceEvent.getId(), serviceEvent);
					Event event = new Event();
					event.id = serviceEvent.getId();
					event.date = serviceEvent.getStart();
					event.name = serviceEvent.getName();
					event.description = serviceEvent.getDescription();
					event.dateEnd = serviceEvent.getEnd();
					event.type = 1;
					event.subtype = questInternalTrack.get(i);
					if (serviceEvent.isDone()) event.state = 0;
					else if (event.dateEnd.after(now)) event.state = 2;
					else event.state = 1;
					calendar.events.put(event.id, event);
				}
			}
			return true;
		} catch (ServerException e) {
			e.printStackTrace();
			return false;
		} catch (InvocationException e) {
			if (e.getCause() != null) {
				try {
					throw e.getCause();
				} catch (IncompatibleRemoteServiceException e0) {
					HealthGenius.myUIManager.UImisc.loadTextOnWindow(HealthGenius.myLanguageManager.TEXT_UPDATEVERSION);
				} catch (Throwable e1) {
					e.printStackTrace();
				}
			}
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@SuppressWarnings("unchecked")
	public boolean getQuestEvents (Long patientId, Date start, Date end) {
		try {
			Date now = new Date();
			HealthGenius.myPatientManager.currentPatQuestSet.clear();
			ArrayList<Action<Entity>> questeventsactions = new ArrayList<Action<Entity>>();
			ArrayList<Long> questInternalTrack = new ArrayList<Long>();
			QuestionnaireAssignListGetAction action = new QuestionnaireAssignListGetAction(patientId);
			Array<com.o2hlink.activ8.client.entity.Questionnaire> questionnaires = service.dispatch(action);
			for (Iterator iterator = questionnaires.iterator(); iterator.hasNext();) {
				Questionnaire questionnaire = (Questionnaire) iterator.next();
				HealthGenius.myPatientManager.currentPatQuestSet.put(questionnaire.getId(), questionnaire);
				QuestionnaireEventListGetAction buffer = new QuestionnaireEventListGetAction(user.getId(), patientId, questionnaire.getId(), start, end);
				Action<?> questaction = buffer;
				questeventsactions.add((Action<Entity>) questaction);
				questInternalTrack.add(questionnaire.getId());
			}
			if (questeventsactions.isEmpty()) return true;
			ArrayList<Entity> totalevents = service.dispatch(questeventsactions);
			for (int i = 0; i < totalevents.size(); i++) {
				List<com.o2hlink.activ8.client.entity.Event> questevents = (List<com.o2hlink.activ8.client.entity.Event>) totalevents.get(i);
				for (com.o2hlink.activ8.client.entity.Event serviceEvent : questevents) {
					com.o2hlink.healthgenius.patient.Event event = new com.o2hlink.healthgenius.patient.Event();
					event.id = serviceEvent.getId();
					event.date = serviceEvent.getStart();
					event.name = serviceEvent.getName();
					event.description = serviceEvent.getDescription();
					event.dateEnd = serviceEvent.getEnd();
					event.type = 1;
					event.subtype = questInternalTrack.get(i);
					if (serviceEvent.isDone()) event.state = 0;
					else if (event.dateEnd.after(now)) event.state = 2;
					else event.state = 1;
					event.userId = patientId;
					HealthGenius.myPatientManager.currentPatQuestEventSet.put(event.id, event);
				}
			}
			return true;
		} catch (ServerException e) {
			e.printStackTrace();
			return false;
		} catch (InvocationException e) {
			if (e.getCause() != null) {
				try {
					throw e.getCause();
				} catch (IncompatibleRemoteServiceException e0) {
					HealthGenius.myUIManager.UImisc.loadTextOnWindow(HealthGenius.myLanguageManager.TEXT_UPDATEVERSION);
				} catch (Throwable e1) {
					e.printStackTrace();
				}
			}
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean getMeasurementSample(Event event) throws NotUpdatedException {
		try {
			SampleGetAction action = null;
			if (event.subtype.equals(com.o2hlink.healthgenius.data.sensor.SensorManager.ID_PULSIOXYMETER)) {
				action = new SampleGetAction(user.getId(), HealthGenius.myMobileManager.userForServices.getId(), Measurement.PULSEOXYMETRY, event.id);
			}
			else if (event.subtype.equals(com.o2hlink.healthgenius.data.sensor.SensorManager.ID_SPIROMETER)) {
				action = new SampleGetAction(user.getId(), HealthGenius.myMobileManager.userForServices.getId(), Measurement.SPIROMETRY, event.id);
			}
			else if (event.subtype.equals(com.o2hlink.healthgenius.data.sensor.SensorManager.ID_SIXMINUTES)) {
				action = new SampleGetAction(user.getId(), HealthGenius.myMobileManager.userForServices.getId(), Measurement.SIX_MINUTES_WALK, event.id);
			}	
			else if (event.subtype.equals(com.o2hlink.healthgenius.data.sensor.SensorManager.ID_WEIGHT)) {
				action = new SampleGetAction(user.getId(), HealthGenius.myMobileManager.userForServices.getId(), Measurement.WEIGHT_HEIGHT, event.id);
			}	
			else if (event.subtype.equals(com.o2hlink.healthgenius.data.sensor.SensorManager.ID_EXERCISE)) {
				action = new SampleGetAction(user.getId(), HealthGenius.myMobileManager.userForServices.getId(), Measurement.EXERCISE, event.id);
			}			
			if (action == null) return false;
			Sample sample = service.dispatch(action);
			if (sample == null) return false;
			if (event.subtype.equals(com.o2hlink.healthgenius.data.sensor.SensorManager.ID_PULSIOXYMETER)) {
				event.sample = new com.o2hlink.healthgenius.data.calendar.PulseoximetrySample((Pulseoxymetry)sample);
			}
			else if (event.subtype.equals(com.o2hlink.healthgenius.data.sensor.SensorManager.ID_SPIROMETER)) {
				event.sample = new com.o2hlink.healthgenius.data.calendar.SpirometrySample((Spirometry)sample);
			}
			else if (event.subtype.equals(com.o2hlink.healthgenius.data.sensor.SensorManager.ID_SIXMINUTES)) {
				event.sample = new com.o2hlink.healthgenius.data.calendar.SixMinutesWalkSample((SixMinutesWalk)sample);
			}
			else if (event.subtype.equals(com.o2hlink.healthgenius.data.sensor.SensorManager.ID_WEIGHT)) {
				event.sample = new com.o2hlink.healthgenius.data.calendar.WeightHeightSample((WeightHeight)sample);
			}
			else if (event.subtype.equals(com.o2hlink.healthgenius.data.sensor.SensorManager.ID_EXERCISE)) {
				event.sample = new com.o2hlink.healthgenius.data.calendar.ExerciseSample((Exercise)sample);
			}		
			if (event.sample == null) return false;
			HealthGenius.myCalendarManager.events.put(event.id, event);
			return true;
		} catch (ServerException e) {
			e.printStackTrace();
			return false;
		} catch (InvocationException e) {
			if (e.getCause() != null) {
				try {
					throw e.getCause();
				} catch (IncompatibleRemoteServiceException e0) {
					throw new NotUpdatedException();
				} catch (Throwable e1) {
					e.printStackTrace();
				}
			}
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean getMeasurementSample(com.o2hlink.healthgenius.patient.Event event) throws NotUpdatedException {
		try {
			SampleGetAction action = null;
			if (event.subtype.equals(com.o2hlink.healthgenius.data.sensor.SensorManager.ID_PULSIOXYMETER)) {
				action = new SampleGetAction(user.getId(), event.userId, Measurement.PULSEOXYMETRY, event.id);
			}
			else if (event.subtype.equals(com.o2hlink.healthgenius.data.sensor.SensorManager.ID_SPIROMETER)) {
				action = new SampleGetAction(user.getId(), event.userId, Measurement.SPIROMETRY, event.id);
			}
			else if (event.subtype.equals(com.o2hlink.healthgenius.data.sensor.SensorManager.ID_SIXMINUTES)) {
				action = new SampleGetAction(user.getId(), event.userId, Measurement.SIX_MINUTES_WALK, event.id);
			}	
			else if (event.subtype.equals(com.o2hlink.healthgenius.data.sensor.SensorManager.ID_WEIGHT)) {
				action = new SampleGetAction(user.getId(), event.userId, Measurement.WEIGHT_HEIGHT, event.id);
			}
			else if (event.subtype.equals(com.o2hlink.healthgenius.data.sensor.SensorManager.ID_EXERCISE)) {
				action = new SampleGetAction(user.getId(), event.userId, Measurement.EXERCISE, event.id);
			}				
			if (action == null) return false;
			Sample sample = service.dispatch(action);
			if (sample == null) return false;
			if (event.subtype.equals(com.o2hlink.healthgenius.data.sensor.SensorManager.ID_PULSIOXYMETER)) {
				event.sample = new PulseoximetrySample((Pulseoxymetry)sample);
			}
			else if (event.subtype.equals(com.o2hlink.healthgenius.data.sensor.SensorManager.ID_SPIROMETER)) {
				event.sample = new SpirometrySample((Spirometry)sample);
			}
			else if (event.subtype.equals(com.o2hlink.healthgenius.data.sensor.SensorManager.ID_SIXMINUTES)) {
				event.sample = new SixMinutesWalkSample((SixMinutesWalk)sample);
			}	
			else if (event.subtype.equals(com.o2hlink.healthgenius.data.sensor.SensorManager.ID_WEIGHT)) {
				event.sample = new WeightHeightSample((WeightHeight)sample);
			}
			else if (event.subtype.equals(com.o2hlink.healthgenius.data.sensor.SensorManager.ID_EXERCISE)) {
				event.sample = new ExerciseSample((Exercise)sample);
			}	
			if (event.sample == null) return false;
			HealthGenius.myPatientManager.currentPatMeasEventSet.put(event.id, event);
			return true;
		} catch (ServerException e) {
			e.printStackTrace();
			return false;
		} catch (InvocationException e) {
			if (e.getCause() != null) {
				try {
					throw e.getCause();
				} catch (IncompatibleRemoteServiceException e0) {
					throw new NotUpdatedException();
				} catch (Throwable e1) {
					e.printStackTrace();
				}
			}
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}	
	
	public boolean getSpiroGraphs(Date date, Sample sample) throws NotUpdatedException {
		try {
			SpirometryGetAction action = new SpirometryGetAction(user.getId(), HealthGenius.myMobileManager.userForServices.getId(), date);
			SpirometryFlow graphs = service.dispatch(action);
			((com.o2hlink.healthgenius.data.calendar.SpirometrySample) sample).flow = graphs.getFlow();
			((com.o2hlink.healthgenius.data.calendar.SpirometrySample) sample).time = graphs.getTime();
			return true;
		} catch (ServerException e) {
			e.printStackTrace();
			return false;
		} catch (InvocationException e) {
			if (e.getCause() != null) {
				try {
					throw e.getCause();
				} catch (IncompatibleRemoteServiceException e0) {
					throw new NotUpdatedException();
				} catch (Throwable e1) {
					e.printStackTrace();
				}
			}
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean getSpiroGraphs(Long patient, Date date, Sample sample) throws NotUpdatedException {
		try {
			SpirometryGetAction action = new SpirometryGetAction(user.getId(), patient, date);
			SpirometryFlow graphs = service.dispatch(action);
			((SpirometrySample) sample).flow = graphs.getFlow();
			((SpirometrySample) sample).time = graphs.getTime();
			return true;
		} catch (ServerException e) {
			e.printStackTrace();
			return false;
		} catch (InvocationException e) {
			if (e.getCause() != null) {
				try {
					throw e.getCause();
				} catch (IncompatibleRemoteServiceException e0) {
					throw new NotUpdatedException();
				} catch (Throwable e1) {
					e.printStackTrace();
				}
			}
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}	
	
	public boolean getSixMinutesGraphs(Date date, Sample sample) throws NotUpdatedException {
		try {
			SixMinutesWalkGetAction action = new SixMinutesWalkGetAction(user.getId(), HealthGenius.myMobileManager.userForServices.getId(), date);
			SixMinutesWalkFlow graphs = service.dispatch(action);
			((com.o2hlink.healthgenius.data.calendar.SixMinutesWalkSample) sample).hrtrack = graphs.getHeartRate();
			((com.o2hlink.healthgenius.data.calendar.SixMinutesWalkSample) sample).so2track = graphs.getOxygenSaturation();
			((com.o2hlink.healthgenius.data.calendar.SixMinutesWalkSample) sample).time = graphs.getTime();
			return true;
		} catch (ServerException e) {
			e.printStackTrace();
			return false;
		} catch (InvocationException e) {
			if (e.getCause() != null) {
				try {
					throw e.getCause();
				} catch (IncompatibleRemoteServiceException e0) {
					throw new NotUpdatedException();
				} catch (Throwable e1) {
					e.printStackTrace();
				}
			}
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean getSixMinutesGraphs(Long patient, Date date, Sample sample) throws NotUpdatedException {
		try {
			SixMinutesWalkGetAction action = new SixMinutesWalkGetAction(user.getId(), patient, date);
			SixMinutesWalkFlow graphs = service.dispatch(action);
			((SixMinutesWalkSample) sample).hrtrack = graphs.getHeartRate();
			((SixMinutesWalkSample) sample).so2track = graphs.getOxygenSaturation();
			((SixMinutesWalkSample) sample).time = graphs.getTime();
			return true;
		} catch (ServerException e) {
			e.printStackTrace();
			return false;
		} catch (InvocationException e) {
			if (e.getCause() != null) {
				try {
					throw e.getCause();
				} catch (IncompatibleRemoteServiceException e0) {
					throw new NotUpdatedException();
				} catch (Throwable e1) {
					e.printStackTrace();
				}
			}
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean getQuestSample (Event event) throws NotUpdatedException {
		try {
			QuestionnaireSampleGetAction action = new QuestionnaireSampleGetAction(user.getId(), HealthGenius.myMobileManager.userForServices.getId(), event.subtype, event.id);
			QuestionnaireSample sample = service.dispatch(action);
			if (sample == null) return false;
			event.questsample = new com.o2hlink.healthgenius.data.calendar.QuestionnaireSample(sample, event.subtype);
			HealthGenius.myCalendarManager.events.put(event.id, event);
			return true;
		} catch (ServerException e) {
			e.printStackTrace();
			return false;
		} catch (InvocationException e) {
			if (e.getCause() != null) {
				try {
					throw e.getCause();
				} catch (IncompatibleRemoteServiceException e0) {
					throw new NotUpdatedException();
				} catch (Throwable e1) {
					e.printStackTrace();
				}
			}
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean getQuestSample (com.o2hlink.healthgenius.patient.Event event) throws NotUpdatedException {
		try {
			QuestionnaireSampleGetAction action = new QuestionnaireSampleGetAction(user.getId(), event.userId, event.subtype, event.id);
			QuestionnaireSample sample = service.dispatch(action);
			if (sample == null) return false;
			event.questsample = new com.o2hlink.healthgenius.patient.QuestionnaireSample(sample, event.subtype);
			HealthGenius.myPatientManager.currentPatQuestEventSet.put(event.id, event);
			return true;
		} catch (ServerException e) {
			e.printStackTrace();
			return false;
		} catch (InvocationException e) {
			if (e.getCause() != null) {
				try {
					throw e.getCause();
				} catch (IncompatibleRemoteServiceException e0) {
					throw new NotUpdatedException();
				} catch (Throwable e1) {
					e.printStackTrace();
				}
			}
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean AddEvent (com.o2hlink.activ8.client.entity.Event event) throws NotUpdatedException {
		try {
			event.setDone(false);
			EventNewAction action = new EventNewAction(user.getId(), event);
			Array<com.o2hlink.activ8.client.entity.Event> events = service.dispatch(action);
			for (com.o2hlink.activ8.client.entity.Event ev : events) {
				Event eventToAdd = new Event();
				eventToAdd.id = ev.getId();
				eventToAdd.name = ev.getName();
				eventToAdd.description = ev.getDescription();
				eventToAdd.date = ev.getStart();
				eventToAdd.dateEnd = ev.getEnd();
				eventToAdd.type = 2;
				eventToAdd.subtype = -1l;
				HealthGenius.myCalendarManager.events.put(eventToAdd.id, eventToAdd);
			}
			return true;
		} catch (ServerException e) {
			e.printStackTrace();
			Event meeting = new Event(HealthGenius.myCalendarManager.getEventId(), event.getName(), event.getStart(), 2, 0, 0);
			meeting.dateEnd = event.getEnd();
			meeting.description = event.getDescription();
			HealthGenius.myPendingDataManager.pendingEvent.add(meeting);
			HealthGenius.myCalendarManager.events.put(meeting.id, meeting);
			HealthGenius.myPendingDataManager.passPendingDataToFile();
			return false;
		} catch (InvocationException e) {
			if (e.getCause() != null) {
				try {
					throw e.getCause();
				} catch (IncompatibleRemoteServiceException e0) {
					throw new NotUpdatedException();
				} catch (Throwable e1) {
					e.printStackTrace();
				}
			}
			e.printStackTrace();
			Event meeting = new Event(HealthGenius.myCalendarManager.getEventId(), event.getName(), event.getStart(), 2, 0, 0);
			meeting.dateEnd = event.getEnd();
			meeting.description = event.getDescription();
			HealthGenius.myPendingDataManager.pendingEvent.add(meeting);
			HealthGenius.myCalendarManager.events.put(meeting.id, meeting);
			HealthGenius.myPendingDataManager.passPendingDataToFile();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			Event meeting = new Event(HealthGenius.myCalendarManager.getEventId(), event.getName(), event.getStart(), 2, 0, 0);
			meeting.dateEnd = event.getEnd();
			meeting.description = event.getDescription();
			HealthGenius.myPendingDataManager.pendingEvent.add(meeting);
			HealthGenius.myCalendarManager.events.put(meeting.id, meeting);
			HealthGenius.myPendingDataManager.passPendingDataToFile();
			return false;
		}
	}
	
	public boolean addMeasEvent(Long patient, Measurement measurement, com.o2hlink.activ8.client.entity.Event event) throws NotUpdatedException {
		try {
			event.setDone(false);
			MeasurementEventNewAction action = new MeasurementEventNewAction(user.getId(), patient, measurement, event);
			Array<com.o2hlink.activ8.client.entity.Event> events = service.dispatch(action);
			for (com.o2hlink.activ8.client.entity.Event ev : events) {
				com.o2hlink.healthgenius.patient.Event eventToAdd = new com.o2hlink.healthgenius.patient.Event();
				eventToAdd.id = ev.getId();
				eventToAdd.name = ev.getName();
				eventToAdd.description = ev.getDescription();
				eventToAdd.date = ev.getStart();
				eventToAdd.dateEnd = ev.getEnd();
				eventToAdd.type = 0;
				if (measurement == Measurement.PULSEOXYMETRY) eventToAdd.subtype = com.o2hlink.healthgenius.data.sensor.SensorManager.ID_PULSIOXYMETER;
				else if (measurement == Measurement.SPIROMETRY)  eventToAdd.subtype = com.o2hlink.healthgenius.data.sensor.SensorManager.ID_SPIROMETER;
				else if (measurement == Measurement.SIX_MINUTES_WALK)  eventToAdd.subtype = com.o2hlink.healthgenius.data.sensor.SensorManager.ID_SIXMINUTES;
				else if (measurement == Measurement.WEIGHT_HEIGHT)  eventToAdd.subtype = com.o2hlink.healthgenius.data.sensor.SensorManager.ID_WEIGHT;
				else if (measurement == Measurement.EXERCISE)  eventToAdd.subtype = com.o2hlink.healthgenius.data.sensor.SensorManager.ID_EXERCISE;
				else  eventToAdd.subtype = -1l;
				HealthGenius.myPatientManager.currentPatMeasEventSet.put(eventToAdd.id, eventToAdd);
			}
			return true;
		} catch (ServerException e) {
			e.printStackTrace();
			com.o2hlink.healthgenius.patient.Event meeting = new com.o2hlink.healthgenius.patient.Event(HealthGenius.myCalendarManager.getEventId(), event.getName(), event.getStart(), 0, 0, 0);
			meeting.dateEnd = event.getEnd();
			meeting.description = event.getDescription();
			if (measurement.equals(Measurement.PULSEOXYMETRY)) meeting.subtype = com.o2hlink.healthgenius.data.sensor.SensorManager.ID_PULSIOXYMETER;
			else if (measurement.equals(Measurement.SPIROMETRY)) meeting.subtype = com.o2hlink.healthgenius.data.sensor.SensorManager.ID_SPIROMETER;
			else if (measurement.equals(Measurement.SIX_MINUTES_WALK)) meeting.subtype = com.o2hlink.healthgenius.data.sensor.SensorManager.ID_EXERCISE;
			else if (measurement == Measurement.WEIGHT_HEIGHT)  meeting.subtype = com.o2hlink.healthgenius.data.sensor.SensorManager.ID_WEIGHT;
			else if (measurement == Measurement.EXERCISE)  meeting.subtype = com.o2hlink.healthgenius.data.sensor.SensorManager.ID_EXERCISE;
			else  meeting.subtype = -1l;
			HealthGenius.myPendingDataManager.pendingPatientEvent.add(meeting);
			HealthGenius.myPatientManager.currentPatMeasEventSet.put(meeting.id, meeting);
			HealthGenius.myPendingDataManager.passPendingDataToFile();
			return false;
		} catch (InvocationException e) {
			if (e.getCause() != null) {
				try {
					throw e.getCause();
				} catch (IncompatibleRemoteServiceException e0) {
					throw new NotUpdatedException();
				} catch (Throwable e1) {
					e.printStackTrace();
				}
			}
			e.printStackTrace();
			com.o2hlink.healthgenius.patient.Event meeting = new com.o2hlink.healthgenius.patient.Event(HealthGenius.myCalendarManager.getEventId(), event.getName(), event.getStart(), 0, 0, 0);
			meeting.dateEnd = event.getEnd();
			meeting.description = event.getDescription();
			if (measurement.equals(Measurement.PULSEOXYMETRY)) meeting.subtype = com.o2hlink.healthgenius.data.sensor.SensorManager.ID_PULSIOXYMETER;
			else if (measurement.equals(Measurement.SPIROMETRY)) meeting.subtype = com.o2hlink.healthgenius.data.sensor.SensorManager.ID_SPIROMETER;
			else if (measurement.equals(Measurement.SIX_MINUTES_WALK)) meeting.subtype = com.o2hlink.healthgenius.data.sensor.SensorManager.ID_EXERCISE;
			else if (measurement == Measurement.WEIGHT_HEIGHT)  meeting.subtype = com.o2hlink.healthgenius.data.sensor.SensorManager.ID_WEIGHT;
			else if (measurement == Measurement.EXERCISE)  meeting.subtype = com.o2hlink.healthgenius.data.sensor.SensorManager.ID_EXERCISE;
			else  meeting.subtype = -1l;
			HealthGenius.myPendingDataManager.pendingPatientEvent.add(meeting);
			HealthGenius.myPatientManager.currentPatMeasEventSet.put(meeting.id, meeting);
			HealthGenius.myPendingDataManager.passPendingDataToFile();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			com.o2hlink.healthgenius.patient.Event meeting = new com.o2hlink.healthgenius.patient.Event(HealthGenius.myCalendarManager.getEventId(), event.getName(), event.getStart(), 0, 0, 0);
			meeting.dateEnd = event.getEnd();
			meeting.description = event.getDescription();
			if (measurement.equals(Measurement.PULSEOXYMETRY)) meeting.subtype = com.o2hlink.healthgenius.data.sensor.SensorManager.ID_PULSIOXYMETER;
			else if (measurement.equals(Measurement.SPIROMETRY)) meeting.subtype = com.o2hlink.healthgenius.data.sensor.SensorManager.ID_SPIROMETER;
			else if (measurement.equals(Measurement.SIX_MINUTES_WALK)) meeting.subtype = com.o2hlink.healthgenius.data.sensor.SensorManager.ID_EXERCISE;
			else if (measurement == Measurement.WEIGHT_HEIGHT)  meeting.subtype = com.o2hlink.healthgenius.data.sensor.SensorManager.ID_WEIGHT;
			else if (measurement == Measurement.EXERCISE)  meeting.subtype = com.o2hlink.healthgenius.data.sensor.SensorManager.ID_EXERCISE;
			else  meeting.subtype = -1l;
			HealthGenius.myPendingDataManager.pendingPatientEvent.add(meeting);
			HealthGenius.myPatientManager.currentPatMeasEventSet.put(meeting.id, meeting);
			HealthGenius.myPendingDataManager.passPendingDataToFile();
			return false;
		}
	}
	
	public boolean addQuestEvent(Long patient, Long questionnaire, com.o2hlink.activ8.client.entity.Event event) throws NotUpdatedException {
		try {
			event.setDone(false);
			QuestionnaireEventNewAction action = new QuestionnaireEventNewAction(user.getId(), patient, questionnaire, event);
			Array<com.o2hlink.activ8.client.entity.Event> events = service.dispatch(action);
			for (com.o2hlink.activ8.client.entity.Event ev : events) {
				com.o2hlink.healthgenius.patient.Event eventToAdd = new com.o2hlink.healthgenius.patient.Event();
				eventToAdd.id = ev.getId();
				eventToAdd.name = ev.getName();
				eventToAdd.description = ev.getDescription();
				eventToAdd.date = ev.getStart();
				eventToAdd.dateEnd = ev.getEnd();
				eventToAdd.type = 1;
				eventToAdd.subtype = questionnaire;
				HealthGenius.myPatientManager.currentPatQuestEventSet.put(eventToAdd.id, eventToAdd);
			}
			return true;
		} catch (ServerException e) {
			e.printStackTrace();
			com.o2hlink.healthgenius.patient.Event meeting = new com.o2hlink.healthgenius.patient.Event(HealthGenius.myCalendarManager.getEventId(), event.getName(), event.getStart(), 0, 0, 0);
			meeting.dateEnd = event.getEnd();
			meeting.description = event.getDescription();
			meeting.subtype = questionnaire;
			HealthGenius.myPendingDataManager.pendingPatientEvent.add(meeting);
			HealthGenius.myPatientManager.currentPatMeasEventSet.put(meeting.id, meeting);
			HealthGenius.myPendingDataManager.passPendingDataToFile();
			return false;
		} catch (InvocationException e) {
			if (e.getCause() != null) {
				try {
					throw e.getCause();
				} catch (IncompatibleRemoteServiceException e0) {
					throw new NotUpdatedException();
				} catch (Throwable e1) {
					e.printStackTrace();
				}
			}
			e.printStackTrace();
			com.o2hlink.healthgenius.patient.Event meeting = new com.o2hlink.healthgenius.patient.Event(HealthGenius.myCalendarManager.getEventId(), event.getName(), event.getStart(), 0, 0, 0);
			meeting.dateEnd = event.getEnd();
			meeting.description = event.getDescription();
			meeting.subtype = questionnaire;
			HealthGenius.myPendingDataManager.pendingPatientEvent.add(meeting);
			HealthGenius.myPatientManager.currentPatMeasEventSet.put(meeting.id, meeting);
			HealthGenius.myPendingDataManager.passPendingDataToFile();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			com.o2hlink.healthgenius.patient.Event meeting = new com.o2hlink.healthgenius.patient.Event(HealthGenius.myCalendarManager.getEventId(), event.getName(), event.getStart(), 0, 0, 0);
			meeting.dateEnd = event.getEnd();
			meeting.description = event.getDescription();
			meeting.subtype = questionnaire;
			HealthGenius.myPendingDataManager.pendingPatientEvent.add(meeting);
			HealthGenius.myPatientManager.currentPatMeasEventSet.put(meeting.id, meeting);
			HealthGenius.myPendingDataManager.passPendingDataToFile();
			return false;
		}
	}
	
	public boolean getQuestList() throws NotUpdatedException {
		HealthGenius.myQuestControlManager.createdQuest.clear();
		try {
			QuestionnaireListGetAction action = new QuestionnaireListGetAction(HealthGenius.myMobileManager.userForServices.getId());
			Array<com.o2hlink.activ8.client.entity.Questionnaire> questionnaires = service.dispatch(action);
			Iterator<com.o2hlink.activ8.client.entity.Questionnaire> it = questionnaires.iterator();
			while (it.hasNext()) {
				com.o2hlink.activ8.client.entity.Questionnaire questionnaire = it.next();
				HealthGenius.myQuestControlManager.createdQuest.put(questionnaire.getId(), questionnaire);
			}
			return true;
		} catch (ServerException e) {
			e.printStackTrace();
			return false;
		} catch (InvocationException e) {
			if (e.getCause() != null) {
				try {
					throw e.getCause();
				} catch (IncompatibleRemoteServiceException e0) {
					throw new NotUpdatedException();
				} catch (Throwable e1) {
					e.printStackTrace();
				}
			}
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean getAssignedQuestList() throws NotUpdatedException {
		HealthGenius.myQuestControlManager.questionnaires.clear();
		try {
			QuestionnaireAssignListGetAction action = new QuestionnaireAssignListGetAction(HealthGenius.myMobileManager.userForServices.getId());
			Array<com.o2hlink.activ8.client.entity.Questionnaire> questionnaires = service.dispatch(action);
			Iterator<com.o2hlink.activ8.client.entity.Questionnaire> it = questionnaires.iterator();
			while (it.hasNext()) {
				com.o2hlink.activ8.client.entity.Questionnaire questionnaire = it.next();
				HealthGenius.myQuestControlManager.questionnaires.put(questionnaire.getId(), questionnaire);
			}
			if (!HealthGenius.myQuestControlManager.questionnaires.containsKey(HealthGenius.myLanguageManager.feedbackQuestId)) {
				try {
					Questionnaire feedback = new Questionnaire();
					feedback.setId(HealthGenius.myLanguageManager.feedbackQuestId);
					QuestionnaireAssignAction assignfeedbackquest = new QuestionnaireAssignAction(HealthGenius.myMobileManager.userForServices.getId(), feedback);
					service.dispatch(assignfeedbackquest);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return true;
		} catch (ServerException e) {
			e.printStackTrace();
			return false;
		} catch (InvocationException e) {
			if (e.getCause() != null) {
				try {
					throw e.getCause();
				} catch (IncompatibleRemoteServiceException e0) {
					throw new NotUpdatedException();
				} catch (Throwable e1) {
					e.printStackTrace();
				}
			}
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@SuppressWarnings("unchecked")
	public boolean getQuestionnaire(long questionnaire) throws NotUpdatedException {
		HealthGenius.myQuestControlManager.activeQuestionnaire = HealthGenius.myQuestControlManager.questionnaires.get(questionnaire);
		if (HealthGenius.myQuestControlManager.activeQuestionnaire == null)
			HealthGenius.myQuestControlManager.activeQuestionnaire = HealthGenius.myQuestControlManager.createdQuest.get(questionnaire);
		HealthGenius.myQuestControlManager.activeQuestions.clear();
		HealthGenius.myQuestControlManager.activeAnswers.clear();
		HealthGenius.myQuestControlManager.activeConditions.clear();
		try {
			QuestionListGetAction action = new QuestionListGetAction(user.getId(), questionnaire);
			Array<Question> quests = service.dispatch(action);
			Iterator<Question> it = quests.iterator();
			ArrayList<Action<Entity>> answersactions = new ArrayList<Action<Entity>>();
			ArrayList<Action<Entity>> conditionsactions = new ArrayList<Action<Entity>>();
			ArrayList<Action<Entity>> numconditionsactions = new ArrayList<Action<Entity>>();
			ArrayList<Question> questions = new ArrayList<Question>();
			ArrayList<Question> numquestions = new ArrayList<Question>();
			while (it.hasNext()) {
				Question question = it.next();
				if (question instanceof MultiQuestion) {
					AnswerListGetAction buffer = new AnswerListGetAction(user.getId(), question.getId());
					Action<?> answeraction = buffer;
					answersactions.add((Action<Entity>) answeraction);
					ConditionListGetAction bufferc = new ConditionListGetAction(user.getId(), question.getId());
					Action<?> conditionaction = bufferc;
					conditionsactions.add((Action<Entity>) conditionaction);
					questions.add(question);
				}
				else if (question instanceof NumericQuestion) {
					ConditionListGetAction bufferc = new ConditionListGetAction(user.getId(), question.getId());
					Action<?> conditionaction = bufferc;
					numconditionsactions.add((Action<Entity>) conditionaction);
					numquestions.add(question);
				}
				HealthGenius.myQuestControlManager.activeQuestions.put(question.getId(), question);
			}
			if (!answersactions.isEmpty()) {
				ArrayList<Entity> answers = service.dispatch(answersactions);
				for (int i = 0; i < answers.size(); i++) {
					Array<Answer> answerlist = (Array<Answer>) answers.get(i);
					HealthGenius.myQuestControlManager.activeAnswers.put(questions.get(i).getId(), answerlist);
				}
			}
			if (!conditionsactions.isEmpty()) {
				ArrayList<Entity> conditions = service.dispatch(conditionsactions);
				for (int i = 0; i < conditions.size(); i++) {
					Array<Condition> conditionlist = (Array<Condition>) conditions.get(i);
					HealthGenius.myQuestControlManager.activeConditions.put(questions.get(i).getId(), conditionlist);
				}
			}
			if (!numconditionsactions.isEmpty()) {
				ArrayList<Entity> numconditions = service.dispatch(numconditionsactions);
				for (int i = 0; i < numconditions.size(); i++) {
					Array<Condition> conditionlist = (Array<Condition>) numconditions.get(i);
					HealthGenius.myQuestControlManager.activeConditions.put(numquestions.get(i).getId(), conditionlist);
				}
			}
			return true;
		} catch (ServerException e) {
			e.printStackTrace();
			return false;
		} catch (InvocationException e) {
			if (e.getCause() != null) {
				try {
					throw e.getCause();
				} catch (IncompatibleRemoteServiceException e0) {
					throw new NotUpdatedException();
				} catch (Throwable e1) {
					e.printStackTrace();
				}
			}
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean sendQuestionnaire(Long questionnaire, QuestionnaireSample sample) throws NotUpdatedException {
		QuestionnaireAnswerAction action = null;
		try {
			action = new QuestionnaireAnswerAction(HealthGenius.myMobileManager.userForServices.getId(), questionnaire, sample);
			service.dispatch(action);
			return true;
		} catch (ServerException e) {
			if (action != null) {
				if (HealthGenius.myMobileManager.userForServices == null) action = new QuestionnaireAnswerAction(HealthGenius.myMobileManager.user.getId(), action.getQuestionnaire(), action.getSample());
				HealthGenius.myPendingDataManager.pendingActions.add(action);
				HealthGenius.myPendingDataManager.passPendingActionsToFile();
			}
			e.printStackTrace();
			return false;
		} catch (InvocationException e) {
			if (e.getCause() != null) {
				try {
					throw e.getCause();
				} catch (IncompatibleRemoteServiceException e0) {
					throw new NotUpdatedException();
				} catch (Throwable e1) {
					e.printStackTrace();
				}
			}
			if (action != null) {
				if (HealthGenius.myMobileManager.userForServices == null) action = new QuestionnaireAnswerAction(HealthGenius.myMobileManager.user.getId(), action.getQuestionnaire(), action.getSample());
				HealthGenius.myPendingDataManager.pendingActions.add(action);
				HealthGenius.myPendingDataManager.passPendingActionsToFile();
			}
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			if (action != null) {
				if (HealthGenius.myMobileManager.userForServices == null) action = new QuestionnaireAnswerAction(HealthGenius.myMobileManager.user.getId(), action.getQuestionnaire(), action.getSample());
				HealthGenius.myPendingDataManager.pendingActions.add(action);
				HealthGenius.myPendingDataManager.passPendingActionsToFile();
			}
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean createQuestionnaire(String name) throws NotUpdatedException {
		try {
			Questionnaire quest = new Questionnaire();
			quest.setDescription(null);
			quest.setName(name);
			QuestionnaireNewAction action = new QuestionnaireNewAction(HealthGenius.myMobileManager.userForServices.getId(), quest);
			Questionnaire created = service.dispatch(action);
			HealthGenius.myQuestControlManager.createdQuest.put(created.getId(), created);
			HealthGenius.myQuestControlManager.activeQuestionnaire = created;
			return true;
		} catch (ServerException e) {
			e.printStackTrace();
			return false;
		} catch (InvocationException e) {
			if (e.getCause() != null) {
				try {
					throw e.getCause();
				} catch (IncompatibleRemoteServiceException e0) {
					throw new NotUpdatedException();
				} catch (Throwable e1) {
					e.printStackTrace();
				}
			}
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean removeQuestionnaire(Questionnaire quest) throws NotUpdatedException {
		try {
			QuestionnaireRemoveAction action = new QuestionnaireRemoveAction(HealthGenius.myMobileManager.userForServices.getId(), quest);
			service.dispatch(action);
			HealthGenius.myQuestControlManager.createdQuest.remove(quest);
			return true;
		} catch (ServerException e) {
			e.printStackTrace();
			return false;
		} catch (InvocationException e) {
			if (e.getCause() != null) {
				try {
					throw e.getCause();
				} catch (IncompatibleRemoteServiceException e0) {
					throw new NotUpdatedException();
				} catch (Throwable e1) {
					e.printStackTrace();
				}
			}
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean updateQuestionnaire(Questionnaire quest) throws NotUpdatedException {
		try {
			QuestionnaireUpdateAction action = new QuestionnaireUpdateAction(HealthGenius.myMobileManager.userForServices.getId(), quest);
			service.dispatch(action);
			return true;
		} catch (ServerException e) {
			e.printStackTrace();
			return false;
		} catch (InvocationException e) {
			if (e.getCause() != null) {
				try {
					throw e.getCause();
				} catch (IncompatibleRemoteServiceException e0) {
					throw new NotUpdatedException();
				} catch (Throwable e1) {
					e.printStackTrace();
				}
			}
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean shareQuestionnaire(ArrayList<Long> users, Questionnaire questionnaire) throws NotUpdatedException {
		try {
			for (int i = 0; i < users.size(); i++) {
				QuestionnaireAssignAction action = new QuestionnaireAssignAction(users.get(i), questionnaire);
				service.dispatch(action);
			}
			return true;
		} catch (ServerException e) {
			e.printStackTrace();
			return false;
		} catch (InvocationException e) {
			if (e.getCause() != null) {
				try {
					throw e.getCause();
				} catch (IncompatibleRemoteServiceException e0) {
					throw new NotUpdatedException();
				} catch (Throwable e1) {
					e.printStackTrace();
				}
			}
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean removeQuestion(Question quest) throws NotUpdatedException {
		try {
			QuestionRemoveAction action = new QuestionRemoveAction(user.getId(), HealthGenius.myQuestControlManager.activeQuestionnaire.getId(), quest);
			service.dispatch(action);
			HealthGenius.myQuestControlManager.activeQuestions.remove(quest.getId());
			Enumeration<Question> elements = HealthGenius.myQuestControlManager.activeQuestions.elements();
			while (elements.hasMoreElements()) {
				Question qst = elements.nextElement();
				if (qst.getNext() == quest.getId()) {
					qst.setNext(null);
					QuestionUpdateAction act = new QuestionUpdateAction(user.getId(), HealthGenius.myQuestControlManager.activeQuestionnaire.getId(), qst);
					service.dispatch(act);
				}
			}
			Enumeration<Long> elementos = HealthGenius.myQuestControlManager.activeConditions.keys();
			while (elementos.hasMoreElements()) {
				Long key = elementos.nextElement();
				Array<Condition> conds = HealthGenius.myQuestControlManager.activeConditions.get(key);
				for (Condition condition : conds) {
					if (condition.getNext() == quest.getId()) {
						condition.setNext(null);
						ConditionUpdateAction act = new ConditionUpdateAction(user.getId(), key, condition);
						service.dispatch(act);
					}					
				}
			}
			return true;
		} catch (ServerException e) {
			e.printStackTrace();
			return false;
		} catch (InvocationException e) {
			if (e.getCause() != null) {
				try {
					throw e.getCause();
				} catch (IncompatibleRemoteServiceException e0) {
					throw new NotUpdatedException();
				} catch (Throwable e1) {
					e.printStackTrace();
				}
			}
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public Question addQuestion(Question quest) throws NotUpdatedException {
		try {
			QuestionNewAction action = new QuestionNewAction(user.getId(), HealthGenius.myQuestControlManager.activeQuestionnaire.getId(), quest);
			Question question = service.dispatch(action);
			HealthGenius.myQuestControlManager.activeQuestions.put(question.getId(), question);
			HealthGenius.myQuestControlManager.activeAnswers.put(question.getId(), new Array<Answer>());
			HealthGenius.myQuestControlManager.activeConditions.put(question.getId(), new Array<Condition>());
			return question;
		} catch (ServerException e) {
			e.printStackTrace();
			return null;
		} catch (InvocationException e) {
			if (e.getCause() != null) {
				try {
					throw e.getCause();
				} catch (IncompatibleRemoteServiceException e0) {
					throw new NotUpdatedException();
				} catch (Throwable e1) {
					e.printStackTrace();
				}
			}
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public boolean updateQuestion(Question quest) throws NotUpdatedException {
		try {
			QuestionUpdateAction action = new QuestionUpdateAction(user.getId(), HealthGenius.myQuestControlManager.activeQuestionnaire.getId(), quest);
			service.dispatch(action);
			HealthGenius.myQuestControlManager.activeQuestions.put(quest.getId(), quest);
			return true;
		} catch (ServerException e) {
			e.printStackTrace();
			return false;
		} catch (InvocationException e) {
			if (e.getCause() != null) {
				try {
					throw e.getCause();
				} catch (IncompatibleRemoteServiceException e0) {
					throw new NotUpdatedException();
				} catch (Throwable e1) {
					e.printStackTrace();
				}
			}
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean removeAnswer(Long questId, Answer answer) throws NotUpdatedException {
		try {
			AnswerRemoveAction action = new AnswerRemoveAction(user.getId(), questId, answer);
			service.dispatch(action);
			HealthGenius.myQuestControlManager.activeAnswers.get(questId).remove(answer);
			return true;
		} catch (ServerException e) {
			e.printStackTrace();
			return false;
		} catch (InvocationException e) {
			if (e.getCause() != null) {
				try {
					throw e.getCause();
				} catch (IncompatibleRemoteServiceException e0) {
					throw new NotUpdatedException();
				} catch (Throwable e1) {
					e.printStackTrace();
				}
			}
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean addAnswer(Long questId, Answer answer) throws NotUpdatedException {
		try {
			AnswerNewAction action = new AnswerNewAction(user.getId(), questId, answer);
			Answer ans = service.dispatch(action);
			HealthGenius.myQuestControlManager.activeAnswers.get(questId).add(ans);
			return true;
		} catch (ServerException e) {
			e.printStackTrace();
			return false;
		} catch (InvocationException e) {
			if (e.getCause() != null) {
				try {
					throw e.getCause();
				} catch (IncompatibleRemoteServiceException e0) {
					throw new NotUpdatedException();
				} catch (Throwable e1) {
					e.printStackTrace();
				}
			}
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean updateAnswer(Long questId, Answer answer) throws NotUpdatedException {
		try {
			AnswerUpdateAction action = new AnswerUpdateAction(user.getId(), questId, answer);
			service.dispatch(action);
			return true;
		} catch (ServerException e) {
			e.printStackTrace();
			return false;
		} catch (InvocationException e) {
			if (e.getCause() != null) {
				try {
					throw e.getCause();
				} catch (IncompatibleRemoteServiceException e0) {
					throw new NotUpdatedException();
				} catch (Throwable e1) {
					e.printStackTrace();
				}
			}
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean removeCondition(Long questId, Condition cond) throws NotUpdatedException {
		try {
			ConditionRemoveAction action = new ConditionRemoveAction(user.getId(), questId, cond);
			service.dispatch(action);
			HealthGenius.myQuestControlManager.activeConditions.get(questId).remove(cond);
			return true;
		} catch (ServerException e) {
			e.printStackTrace();
			return false;
		} catch (InvocationException e) {
			if (e.getCause() != null) {
				try {
					throw e.getCause();
				} catch (IncompatibleRemoteServiceException e0) {
					throw new NotUpdatedException();
				} catch (Throwable e1) {
					e.printStackTrace();
				}
			}
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean addCondition(Long questId, Condition condition) throws NotUpdatedException {
		try {
			ConditionNewAction action = new ConditionNewAction(user.getId(), questId, condition);
			Condition cond = service.dispatch(action);
			HealthGenius.myQuestControlManager.activeConditions.get(questId).add(cond);
			return true;
		} catch (ServerException e) {
			e.printStackTrace();
			return false;
		} catch (InvocationException e) {
			if (e.getCause() != null) {
				try {
					throw e.getCause();
				} catch (IncompatibleRemoteServiceException e0) {
					throw new NotUpdatedException();
				} catch (Throwable e1) {
					e.printStackTrace();
				}
			}
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean updateCondition(Long questId, Condition condition) throws NotUpdatedException {
		try {
			ConditionUpdateAction action = new ConditionUpdateAction(user.getId(), questId, condition);
			service.dispatch(action);
			return true;
		} catch (ServerException e) {
			e.printStackTrace();
			return false;
		} catch (InvocationException e) {
			if (e.getCause() != null) {
				try {
					throw e.getCause();
				} catch (IncompatibleRemoteServiceException e0) {
					throw new NotUpdatedException();
				} catch (Throwable e1) {
					e.printStackTrace();
				}
			}
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean publishQuestionnaire(Questionnaire quest) throws NotUpdatedException {
		try {
			VisibilityUpdateAction action = new VisibilityUpdateAction(HealthGenius.myMobileManager.userForServices.getId(), quest, VisibilityLevel.PUBLIC_VIEW);
			service.dispatch(action);
			return true;
		} catch (ServerException e) {
			e.printStackTrace();
			return true;
		} catch (InvocationException e) {
			if (e.getCause() != null) {
				try {
					throw e.getCause();
				} catch (IncompatibleRemoteServiceException e0) {
					throw new NotUpdatedException();
				} catch (Throwable e1) {
					e.printStackTrace();
				}
			}
			e.printStackTrace();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return true;
		}
	}
	
	public boolean getPrivilegesOnQuestionnaire(Questionnaire quest) throws NotUpdatedException {
		try {
			QuestionnaireAddAction action = new QuestionnaireAddAction(HealthGenius.myMobileManager.userForServices.getId(), quest);
			service.dispatch(action);
			HealthGenius.myQuestControlManager.createdQuest.put(quest.getId(), quest);
			return true;
		} catch (ServerException e) {
			e.printStackTrace();
			return true;
		} catch (InvocationException e) {
			if (e.getCause() != null) {
				try {
					throw e.getCause();
				} catch (IncompatibleRemoteServiceException e0) {
					throw new NotUpdatedException();
				} catch (Throwable e1) {
					e.printStackTrace();
				}
			}
			e.printStackTrace();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return true;
		}
	}
	
	public Array<Disease> searchDiseases(String query) throws NotUpdatedException {
		try {
			DiseaseSearchAction action = new DiseaseSearchAction(HealthGenius.myMobileManager.userForServices.getId(), query, 0);
			return service.dispatch(action);
		} catch (ServerException e) {
			e.printStackTrace();
			return null;
		} catch (InvocationException e) {
			if (e.getCause() != null) {
				try {
					throw e.getCause();
				} catch (IncompatibleRemoteServiceException e0) {
					throw new NotUpdatedException();
				} catch (Throwable e1) {
					e.printStackTrace();
				}
			}
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public boolean addHistoryRecord(Long patient, Disease disease) throws NotUpdatedException {
		try {
			HistoryRecord record = new HistoryRecord();
			record.setDate(new Date());
			record.setDisease(disease);
			HistoryRecordNewAction action = new HistoryRecordNewAction(user.getId(), patient, record);
			record = service.dispatch(action);
			if (record == null) return false;
			com.o2hlink.healthgenius.patient.HistoryRecord rec = new com.o2hlink.healthgenius.patient.HistoryRecord(record.getId(), record.getDisease().getName(), record.getDisease().getDescription(), record.getDate());
			HealthGenius.myPatientManager.patientSet.get(patient).history.put(rec.id, rec);
			return true;
		} catch (ServerException e) {
			e.printStackTrace();
			return false;
		} catch (InvocationException e) {
			if (e.getCause() != null) {
				try {
					throw e.getCause();
				} catch (IncompatibleRemoteServiceException e0) {
					throw new NotUpdatedException();
				} catch (Throwable e1) {
					e.printStackTrace();
				}
			}
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean addExploration(Long patient, Long recordId, String description) throws NotUpdatedException {
		try {
			HistoryRecord record = HealthGenius.myPatientManager.patientSet.get(patient).history.get(recordId).historyRecordForServices;
			Exploration exploration = new Exploration();
			exploration.setDate(new Date());
			exploration.setDescription(description);
			ExplorationNewAction action = new ExplorationNewAction(user.getId(), record, exploration);
			exploration = service.dispatch(action);
			if (exploration == null) return false;
			com.o2hlink.healthgenius.patient.Exploration expl = new com.o2hlink.healthgenius.patient.Exploration(exploration.getId());
			expl.date = exploration.getDate();
			expl.exploration = exploration.getDescription();
			HealthGenius.myPatientManager.patientSet.get(patient).history.get(recordId).explorations.put(expl.id, expl);
			return true;
		} catch (ServerException e) {
			e.printStackTrace();
			return true;
		} catch (InvocationException e) {
			if (e.getCause() != null) {
				try {
					throw e.getCause();
				} catch (IncompatibleRemoteServiceException e0) {
					throw new NotUpdatedException();
//					HealthGenius.myUIManager.loadTextOnWindow(HealthGenius.myLanguageManager.TEXT_UPDATEVERSION);
				} catch (Throwable e1) {
					e.printStackTrace();
				}
			}
			e.printStackTrace();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return true;
		}
	}
	
}
