package com.o2hlink.zonated.connection;

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
import com.o2hlink.activ8.client.action.QuestionnaireSampleListGetAction;
import com.o2hlink.activ8.client.action.QuestionnaireSearchAction;
import com.o2hlink.activ8.client.action.QuestionnaireShareListGetAction;
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
import com.o2hlink.zonated.Zonated;
import com.o2hlink.zonated.ZonatedConfig;
import com.o2hlink.zonated.data.Message;
import com.o2hlink.zonated.data.PendingDataManager;
import com.o2hlink.zonated.data.contacts.ContactsManager;
import com.o2hlink.zonated.data.questionnaire.QuestGlobalResult;
import com.o2hlink.zonated.exceptions.ConnectionException;
import com.o2hlink.zonated.exceptions.NotUpdatedException;

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
	
	public static String uploadServlet = ZonatedConfig.SERVICES_URL + "upload";
	
	public static String downloadServlet = ZonatedConfig.SERVICES_URL + "download";
	
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
		this.service = (Service) SyncProxy.newProxyInstance(Service.class,ZonatedConfig.SERVICES_URL,
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
			      com.o2hlink.zonated.connection.ByteBuffer strBuff = new com.o2hlink.zonated.connection.ByteBuffer();
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
	
	/*public boolean getContacts(ContactsManager messages) throws NotUpdatedException {
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
	}*/
	
	public boolean getContacts(ContactsManager contacts) throws NotUpdatedException {
		try {
			UserListGetAction getpatients = new UserListGetAction(user.getId(), user);
			Array<User> patients = service.dispatch(getpatients);
			for (User patient : patients) {
				if (patient instanceof Patient) {
					Contact contact = new Contact("" + patient.getId(), patient.getFirstName(), patient.getLastName());
					contacts.contactList.put(contact.getId(), contact);					
				}
			}
			Contact yourself = new Contact(Long.toString(user.getId()), user.getFirstName(), user.getLastName());
			contacts.contactList.put(yourself.getId(), yourself);	
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
	
	public int checkUserExistance() throws NotUpdatedException {
		try {
			UserLoginAction action = new UserLoginAction(Zonated.myMobileManager.user.getName(), Zonated.myMobileManager.user.getPassword());
			Zonated.myMobileManager.userForServices = service.dispatch(action);
			if (Zonated.myMobileManager.userForServices == null) return 1;
			Zonated.myMobileManager.user.setCreated(true);
			Zonated.myMobileManager.user.setId(Zonated.myMobileManager.userForServices.getId());
			Zonated.myMobileManager.user.firstname = Zonated.myMobileManager.userForServices.getFirstName();
			Zonated.myMobileManager.user.lastname = Zonated.myMobileManager.userForServices.getLastName();
			Zonated.myMobileManager.user.setBirthdate(Zonated.myMobileManager.userForServices.getBirthdate());
			Zonated.myMobileManager.user.setMail(Zonated.myMobileManager.userForServices.getEmail());
        	if (Zonated.myMobileManager.userForServices instanceof Patient) Zonated.myMobileManager.user.setType(0);
        	else if (Zonated.myMobileManager.userForServices instanceof Clinician) Zonated.myMobileManager.user.setType(1);
        	else if (Zonated.myMobileManager.userForServices instanceof Researcher) Zonated.myMobileManager.user.setType(2);
        	else return 2;
        	Zonated.myMobileManager.user.setSex(Zonated.myMobileManager.userForServices.getSex());
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
			UserLoginAction action = new UserLoginAction(Zonated.myMobileManager.user.getName(), Zonated.myMobileManager.user.getPassword());
			Zonated.myMobileManager.userForServices = service.dispatch(action);
			user = Zonated.myMobileManager.userForServices;
			if (Zonated.myMobileManager.userForServices == null) return false;
			Zonated.myMobileManager.user.setCreated(true);
			Zonated.myMobileManager.user.setId(Zonated.myMobileManager.userForServices.getId());
			Zonated.myMobileManager.user.firstname = Zonated.myMobileManager.userForServices.getFirstName();
			Zonated.myMobileManager.user.lastname = Zonated.myMobileManager.userForServices.getLastName();
			Zonated.myMobileManager.user.setBirthdate(Zonated.myMobileManager.userForServices.getBirthdate());
			Zonated.myMobileManager.user.setMail(Zonated.myMobileManager.userForServices.getEmail());
        	if (Zonated.myMobileManager.userForServices instanceof Patient) Zonated.myMobileManager.user.setType(0);
        	else if (Zonated.myMobileManager.userForServices instanceof Clinician) Zonated.myMobileManager.user.setType(1);
        	else if (Zonated.myMobileManager.userForServices instanceof Researcher) Zonated.myMobileManager.user.setType(2);
        	else return false;
			Zonated.myMobileManager.user.setSex(Zonated.myMobileManager.userForServices.getSex());
        	Zonated.myProtocolManager.connected = true;
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
		if (Zonated.myMobileManager.userForServices instanceof Patient) {
			try {
				MeasurementAssignListGetAction assignlist = new MeasurementAssignListGetAction(user.getId(), Zonated.myMobileManager.userForServices.getId());
				Array<Measurement> measlist = service.dispatch(assignlist);
				ArrayList<Action<Entity>> actions = new ArrayList<Action<Entity>>();	
				MeasurementAssignAction buffer;
				Action<?> action;
				if (!measlist.contains(Measurement.PULSEOXYMETRY)) {
					buffer = new MeasurementAssignAction(user.getId(), Zonated.myMobileManager.userForServices.getId(), Measurement.PULSEOXYMETRY);
					action = buffer;
					actions.add((Action<Entity>) action);
				}
				if (!measlist.contains(Measurement.SPIROMETRY)) {
					buffer = new MeasurementAssignAction(user.getId(), Zonated.myMobileManager.userForServices.getId(), Measurement.SPIROMETRY);
					action = buffer;
					actions.add((Action<Entity>) action);
				}
				if (!measlist.contains(Measurement.SIX_MINUTES_WALK)) {
					buffer = new MeasurementAssignAction(user.getId(), Zonated.myMobileManager.userForServices.getId(), Measurement.SIX_MINUTES_WALK);
					action = buffer;
					actions.add((Action<Entity>) action);
				}
				if (!measlist.contains(Measurement.WEIGHT_HEIGHT)) {
					buffer = new MeasurementAssignAction(user.getId(), Zonated.myMobileManager.userForServices.getId(), Measurement.WEIGHT_HEIGHT);
					action = buffer;
					actions.add((Action<Entity>) action);
				}
				if (!measlist.contains(Measurement.EXERCISE)) {
					buffer = new MeasurementAssignAction(user.getId(), Zonated.myMobileManager.userForServices.getId(), Measurement.EXERCISE);
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
			switch (Zonated.myMobileManager.user.getType()) {
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
			user.setUsername(Zonated.myMobileManager.user.getName());
			user.setFirstName(Zonated.myMobileManager.user.getFirstname());
			user.setLastName(Zonated.myMobileManager.user.getLastname());
			user.setBirthdate(Zonated.myMobileManager.user.getBirthdate());
			user.setCountry(Zonated.myMobileManager.user.country);
			user.setEmail(Zonated.myMobileManager.user.getMail());
			user.setSex(Zonated.myMobileManager.user.getSex());
			UserNewAction action = new UserNewAction(user, Zonated.myMobileManager.user.getPassword(), false);
			Zonated.myMobileManager.userForServices = service.dispatch(action);
			this.user = Zonated.myMobileManager.userForServices;
			VisibilityUpdateAction publicy = new VisibilityUpdateAction(Zonated.myMobileManager.userForServices.getId(), Zonated.myMobileManager.userForServices, VisibilityLevel.PUBLIC_VIEW);
			service.dispatch(publicy);
			ArrayList<Action<Entity>> actions = new ArrayList<Action<Entity>>();			
			if (Zonated.myMobileManager.userForServices instanceof Patient) {
				MeasurementAssignAction buffer = new MeasurementAssignAction(user.getId(), Zonated.myMobileManager.userForServices.getId(), Measurement.PULSEOXYMETRY);
				Action<?> action1 = buffer;
				actions.add((Action<Entity>) action1);
				buffer = new MeasurementAssignAction(user.getId(), Zonated.myMobileManager.userForServices.getId(), Measurement.SPIROMETRY);
				Action<?> action2 = buffer;
				actions.add((Action<Entity>) action2);
				buffer = new MeasurementAssignAction(user.getId(), Zonated.myMobileManager.userForServices.getId(), Measurement.SIX_MINUTES_WALK);
				Action<?> action3 = buffer;
				actions.add((Action<Entity>) action3);
				buffer = new MeasurementAssignAction(user.getId(), Zonated.myMobileManager.userForServices.getId(), Measurement.EXERCISE);
				Action<?> action4 = buffer;
				actions.add((Action<Entity>) action4);
				buffer = new MeasurementAssignAction(user.getId(), Zonated.myMobileManager.userForServices.getId(), Measurement.WEIGHT_HEIGHT);
				Action<?> action5 = buffer;
				actions.add((Action<Entity>) action5);
			}
			service.dispatch(actions);
			try {
				Questionnaire feedback = new Questionnaire();
				feedback.setId(Zonated.myLanguageManager.feedbackQuestId);
				QuestionnaireAssignAction assignfeedbackquest = new QuestionnaireAssignAction(Zonated.myMobileManager.userForServices.getId(), feedback);
				service.dispatch(assignfeedbackquest);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (Zonated.myMobileManager.userForServices == null) return false;
			else {
	        	Zonated.myProtocolManager.connected = true;
				Zonated.myMobileManager.user.setCreated(true);
        		Zonated.myMobileManager.saveUsers();
				return true;
			}
		} catch (DuplicateEntityException e) {
			Zonated.myMobileManager.user.setCreated(true);
    		Zonated.myMobileManager.saveUsers();
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
			UserPasswordChangeAction action = new UserPasswordChangeAction(Zonated.myMobileManager.userForServices.getId(), currentPassword, newPassword);
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
			Zonated.myMobileManager.userForServices.setUsername(Zonated.myMobileManager.user.getName());
			Zonated.myMobileManager.userForServices.setFirstName(Zonated.myMobileManager.user.getFirstname());
			Zonated.myMobileManager.userForServices.setEmail(Zonated.myMobileManager.user.getMail());
			Zonated.myMobileManager.userForServices.setLastName(Zonated.myMobileManager.user.getLastname());
			Zonated.myMobileManager.userForServices.setBirthdate(Zonated.myMobileManager.user.getBirthdate());
			Zonated.myMobileManager.userForServices.setCountry(Zonated.myMobileManager.user.country);
			Zonated.myMobileManager.userForServices.setSex(Zonated.myMobileManager.user.getSex());
			UserUpdateAction action = new UserUpdateAction(user.getId(), Zonated.myMobileManager.userForServices);
			service.dispatch(action);
			user = Zonated.myMobileManager.userForServices;
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
		Zonated.myApp.refreshing = true;
		boolean success = false;
		if (!Zonated.myMobileManager.user.isCreated()) success = Zonated.myProtocolManager.register();
		else success = Zonated.myProtocolManager.login();
		if (success) {
			if (!Zonated.myMobileManager.user.isCreated()) {
				Zonated.myMobileManager.user.setCreated(true);
				Zonated.myMobileManager.addUserWithPassword(Zonated.myMobileManager.user);
			}
			checkMeasurements();
		    /* Send pending data */
		    ArrayList<Action<?>> tempActions = (ArrayList<Action<?>>) Zonated.myPendingDataManager.pendingActions.clone();
		    Zonated.myPendingDataManager.pendingActions.clear();
		    Iterator<Action<?>> it6 = tempActions.iterator();
		    while (it6.hasNext()) {
		    	Action<?> pendAction = it6.next();
		    	try {
					if (!Zonated.myProtocolManager.dispatch(pendAction)) Zonated.myPendingDataManager.pendingActions.add(pendAction);
				} catch (NotUpdatedException e) {
					throw new NotUpdatedException();
				}
		    }
		    Zonated.myPendingDataManager.passPendingActionsToFile();
		    /* The updating of the data */
			Zonated.myQuestControlManager.getQuestionnaires();
		    Zonated.myContactsManager.requestContactList();
			Zonated.myContactsManager.requestEntryContactList();
		    /* Update event status and send event outcomes for canceled events */
		    Date dateLate = new Date();
		    dateLate.setHours(dateLate.getHours() - 24);
		    Zonated.myApp.refreshing = false;
		}
	}
	
	public Array<Questionnaire> SearchQuests (String query) throws NotUpdatedException {
		QuestionnaireSearchAction action = new QuestionnaireSearchAction(Zonated.myMobileManager.userForServices.getId(), query);
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

	/*public Array<Contact> SearchContacts (String query) throws NotUpdatedException {
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
	}*/
	
	public Array<Contact> SearchContacts (String query) throws NotUpdatedException {
		try {
			UserSearchAction action = new UserSearchAction(user.getId(), query);
			Array<User> users = service.dispatch(action);
			Array<Contact> contacts = new Array<Contact>();
			for (User pat:users) {
				if (pat instanceof Patient) contacts.add(new Contact("" + pat.getId(), pat.getFirstName(), pat.getLastName()));
			}
			return contacts;
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
			Zonated.myContactsManager.contactList.remove(contact.getId());
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
	
	public boolean getQuestList() throws NotUpdatedException {
		Zonated.myQuestControlManager.createdQuest.clear();
		try {
			QuestionnaireListGetAction action = new QuestionnaireListGetAction(Zonated.myMobileManager.userForServices.getId());
			Array<com.o2hlink.activ8.client.entity.Questionnaire> questionnaires = service.dispatch(action);
			Iterator<com.o2hlink.activ8.client.entity.Questionnaire> it = questionnaires.iterator();
			while (it.hasNext()) {
				com.o2hlink.activ8.client.entity.Questionnaire questionnaire = it.next();
				Zonated.myQuestControlManager.createdQuest.put(questionnaire.getId(), questionnaire);
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
		Zonated.myQuestControlManager.questionnaires.clear();
		try {
			QuestionnaireAssignListGetAction action = new QuestionnaireAssignListGetAction(Zonated.myMobileManager.userForServices.getId());
			Array<com.o2hlink.activ8.client.entity.Questionnaire> questionnaires = service.dispatch(action);
			Iterator<com.o2hlink.activ8.client.entity.Questionnaire> it = questionnaires.iterator();
			while (it.hasNext()) {
				com.o2hlink.activ8.client.entity.Questionnaire questionnaire = it.next();
				Zonated.myQuestControlManager.questionnaires.put(questionnaire.getId(), questionnaire);
			}
			if (!Zonated.myQuestControlManager.questionnaires.containsKey(Zonated.myLanguageManager.feedbackQuestId)) {
				try {
					Questionnaire feedback = new Questionnaire();
					feedback.setId(Zonated.myLanguageManager.feedbackQuestId);
					QuestionnaireAssignAction assignfeedbackquest = new QuestionnaireAssignAction(Zonated.myMobileManager.userForServices.getId(), feedback);
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
		Zonated.myQuestControlManager.activeQuestionnaire = Zonated.myQuestControlManager.questionnaires.get(questionnaire);
		if (Zonated.myQuestControlManager.activeQuestionnaire == null)
			Zonated.myQuestControlManager.activeQuestionnaire = Zonated.myQuestControlManager.createdQuest.get(questionnaire);
		Zonated.myQuestControlManager.activeQuestions.clear();
		Zonated.myQuestControlManager.activeAnswers.clear();
		Zonated.myQuestControlManager.activeConditions.clear();
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
				Zonated.myQuestControlManager.activeQuestions.put(question.getId(), question);
			}
			if (!answersactions.isEmpty()) {
				ArrayList<Entity> answers = service.dispatch(answersactions);
				for (int i = 0; i < answers.size(); i++) {
					Array<Answer> answerlist = (Array<Answer>) answers.get(i);
					Zonated.myQuestControlManager.activeAnswers.put(questions.get(i).getId(), answerlist);
				}
			}
			if (!conditionsactions.isEmpty()) {
				ArrayList<Entity> conditions = service.dispatch(conditionsactions);
				for (int i = 0; i < conditions.size(); i++) {
					Array<Condition> conditionlist = (Array<Condition>) conditions.get(i);
					Zonated.myQuestControlManager.activeConditions.put(questions.get(i).getId(), conditionlist);
				}
			}
			if (!numconditionsactions.isEmpty()) {
				ArrayList<Entity> numconditions = service.dispatch(numconditionsactions);
				for (int i = 0; i < numconditions.size(); i++) {
					Array<Condition> conditionlist = (Array<Condition>) numconditions.get(i);
					Zonated.myQuestControlManager.activeConditions.put(numquestions.get(i).getId(), conditionlist);
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
			action = new QuestionnaireAnswerAction(Zonated.myMobileManager.userForServices.getId(), questionnaire, sample);
			service.dispatch(action);
			return true;
		} catch (ServerException e) {
			if (action != null) {
				if (Zonated.myMobileManager.userForServices == null) action = new QuestionnaireAnswerAction(Zonated.myMobileManager.user.getId(), action.getQuestionnaire(), action.getSample());
				Zonated.myPendingDataManager.pendingActions.add(action);
				Zonated.myPendingDataManager.passPendingActionsToFile();
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
				if (Zonated.myMobileManager.userForServices == null) action = new QuestionnaireAnswerAction(Zonated.myMobileManager.user.getId(), action.getQuestionnaire(), action.getSample());
				Zonated.myPendingDataManager.pendingActions.add(action);
				Zonated.myPendingDataManager.passPendingActionsToFile();
			}
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			if (action != null) {
				if (Zonated.myMobileManager.userForServices == null) action = new QuestionnaireAnswerAction(Zonated.myMobileManager.user.getId(), action.getQuestionnaire(), action.getSample());
				Zonated.myPendingDataManager.pendingActions.add(action);
				Zonated.myPendingDataManager.passPendingActionsToFile();
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
			QuestionnaireNewAction action = new QuestionnaireNewAction(Zonated.myMobileManager.userForServices.getId(), quest);
			Questionnaire created = service.dispatch(action);
			Zonated.myQuestControlManager.createdQuest.put(created.getId(), created);
			Zonated.myQuestControlManager.activeQuestionnaire = created;
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
			QuestionnaireRemoveAction action = new QuestionnaireRemoveAction(Zonated.myMobileManager.userForServices.getId(), quest);
			service.dispatch(action);
			Zonated.myQuestControlManager.createdQuest.remove(quest);
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
			QuestionnaireUpdateAction action = new QuestionnaireUpdateAction(Zonated.myMobileManager.userForServices.getId(), quest);
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
			QuestionRemoveAction action = new QuestionRemoveAction(user.getId(), Zonated.myQuestControlManager.activeQuestionnaire.getId(), quest);
			service.dispatch(action);
			Zonated.myQuestControlManager.activeQuestions.remove(quest.getId());
			Enumeration<Question> elements = Zonated.myQuestControlManager.activeQuestions.elements();
			while (elements.hasMoreElements()) {
				Question qst = elements.nextElement();
				if (qst.getNext() == quest.getId()) {
					qst.setNext(null);
					QuestionUpdateAction act = new QuestionUpdateAction(user.getId(), Zonated.myQuestControlManager.activeQuestionnaire.getId(), qst);
					service.dispatch(act);
				}
			}
			Enumeration<Long> elementos = Zonated.myQuestControlManager.activeConditions.keys();
			while (elementos.hasMoreElements()) {
				Long key = elementos.nextElement();
				Array<Condition> conds = Zonated.myQuestControlManager.activeConditions.get(key);
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
			QuestionNewAction action = new QuestionNewAction(user.getId(), Zonated.myQuestControlManager.activeQuestionnaire.getId(), quest);
			Question question = service.dispatch(action);
			Zonated.myQuestControlManager.activeQuestions.put(question.getId(), question);
			Zonated.myQuestControlManager.activeAnswers.put(question.getId(), new Array<Answer>());
			Zonated.myQuestControlManager.activeConditions.put(question.getId(), new Array<Condition>());
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
			QuestionUpdateAction action = new QuestionUpdateAction(user.getId(), Zonated.myQuestControlManager.activeQuestionnaire.getId(), quest);
			service.dispatch(action);
			Zonated.myQuestControlManager.activeQuestions.put(quest.getId(), quest);
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
			Zonated.myQuestControlManager.activeAnswers.get(questId).remove(answer);
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
			Zonated.myQuestControlManager.activeAnswers.get(questId).add(ans);
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
			Zonated.myQuestControlManager.activeConditions.get(questId).remove(cond);
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
			Zonated.myQuestControlManager.activeConditions.get(questId).add(cond);
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
			VisibilityUpdateAction action = new VisibilityUpdateAction(Zonated.myMobileManager.userForServices.getId(), quest, VisibilityLevel.PUBLIC_VIEW);
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
			QuestionnaireAddAction action = new QuestionnaireAddAction(Zonated.myMobileManager.userForServices.getId(), quest);
			service.dispatch(action);
			Zonated.myQuestControlManager.createdQuest.put(quest.getId(), quest);
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
	
	public Array<Contact> getQuestionnairePeople(Questionnaire quest) throws NotUpdatedException {
		try {
			QuestionnaireShareListGetAction getcontacts = new QuestionnaireShareListGetAction(user.getId(), quest);
			return service.dispatch(getcontacts);
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
	
	public QuestionnaireSample getQuestionnaireSample(Long quest, Long userId) throws NotUpdatedException {
		try {
			QuestionnaireSample lastone = null;
			Date threemonthsago = new Date();
			threemonthsago.setMonth(threemonthsago.getMonth() - 3);
			Date now = new Date();
			QuestionnaireSampleListGetAction getsamples = new QuestionnaireSampleListGetAction(user.getId(), userId, quest, threemonthsago, now);
//			QuestionnaireSampleListGetAction getsamples = new QuestionnaireSampleListGetAction(user.getId(), 181l, quest, threemonthsago, now);
			Array<QuestionnaireSample> samples = service.dispatch(getsamples);
			if (!samples.isEmpty()) {
				for (QuestionnaireSample sample : samples) {
					if (lastone == null) {
						lastone = sample;
						continue;
					}
					if (lastone.getDate().before(sample.getDate())) {
						lastone = sample;
					}
				}
			}
			return lastone;
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
	
	@SuppressWarnings("unchecked")
	public Array<QuestionnaireSample> getQuestionnaireSample(Long quest, Array<Contact> users) throws NotUpdatedException {
		try {
			Array<QuestionnaireSample> returning = new Array<QuestionnaireSample>();
			ArrayList<Action<Entity>> actions = new ArrayList<Action<Entity>>();
			QuestionnaireSampleListGetAction buffer;
			Action<?> action;
			QuestionnaireSample lastone = null;
			Date threemonthsago = new Date();
			threemonthsago.setMonth(threemonthsago.getMonth() - 3);
			Date now = new Date();
			for (Contact contact : users) {
				Long userId = Long.parseLong(contact.getId());
				buffer = new QuestionnaireSampleListGetAction(user.getId(), userId, quest, threemonthsago, now);
				action = buffer;
				actions.add((Action<Entity>) action);
			}
			ArrayList<Entity> received = service.dispatch(actions);
			for (Entity entity : received) {
				Array<QuestionnaireSample> samples = (Array<QuestionnaireSample>) entity;
				if (!samples.isEmpty()) {
					for (QuestionnaireSample sample : samples) {
						if (lastone == null) {
							lastone = sample;
							continue;
						}
						if (lastone.getDate().before(sample.getDate())) {
							lastone = sample;
						}
					}
				}	
				if (lastone != null) returning.add(lastone);
			}
			return returning;
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
	
	/*public QuestGlobalResult getQuestionnaireGlobalResult(Questionnaire quest) throws NotUpdatedException {
		try {
			QuestionnaireAddAction action = new QuestionnaireAddAction(Zonated.myMobileManager.userForServices.getId(), quest);
			service.dispatch(action);
			Zonated.myQuestControlManager.createdQuest.put(quest.getId(), quest);
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
	}*/
	
}
