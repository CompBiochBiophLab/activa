package com.o2hlink.healthgenius.mobile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.StringTokenizer;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.os.Environment;

import com.o2hlink.activ8.client.entity.Array;
import com.o2hlink.activ8.common.entity.Sex;
import com.o2hlink.healthgenius.HealthGenius;
import com.o2hlink.healthgenius.HealthGeniusConfig;
import com.o2hlink.healthgenius.HealthGeniusUtil;
import com.o2hlink.healthgenius.connection.ProtocolManager;
import com.o2hlink.healthgenius.exceptions.NotUpdatedException;

/**
 * @author Adrian Rejas<P>
 *
 * This class acts as a manager for the application environment (The mobile used for 
 * running the application and the user is running the application).
 */
public class MobileManager {

	/**
	 * The user is running the application.
	 */
	public User user;
	
	/**
	 * The user object used for callin web services.
	 */
	public com.o2hlink.activ8.client.entity.User userForServices;
	
	/**
	 * The mobile is running the application.
	 */
	public Device device;
	
	/**
	 * Instance of the manager.
	 */
	private static MobileManager instance;
	
	/**
	 * Password introduced at login screen.
	 */
	public String password;
	
	/**
	 * Password introduced at login screen.
	 */
	public String currentpassword;
	
	/**
	 * Pedometer integer value marking the calibration state of the pedometer.
	 */
	public int pedometerCalValue;
	
	/**
	 * The set of users available at this mobile.
	 */
	public Hashtable<String, User> users;
	
	/**
	 * If an user has been identified himself;
	 */
	public boolean identified;
	
	public Date promotionDate;
	
	public String promotionUrl;
	
	public String promotionString;
	
	public File promotionFolder;
	
	public File promotionBig;
	
	public File promotionMini;
	
	public File promotionMicro;
	
	/**
	 * Private constructor.
	 */
	private MobileManager() {
		this.user = User.getInstance(null, null);
		this.device = Device.getInstance();
		this.users = new Hashtable<String, User>();
		this.identified = false;
		this.pedometerCalValue = 30;
		this.promotionDate = new Date(0);
		File originalfolder = new File(Environment.getExternalStorageDirectory(), HealthGeniusConfig.ENVIRONMENT_FOLDER);
		this.promotionFolder = new File(originalfolder, "Promotion");
		if (!this.promotionFolder.exists())
			this.promotionFolder.mkdir();
		this.promotionBig = new File(this.promotionFolder, "promotion.jpg");
		this.promotionMini = new File(this.promotionFolder, "promotionmini.jpg");
		this.promotionMicro = new File(this.promotionFolder, "promotionmicro.jpg");
		this.getUsers();
		this.checkPromotion();
	}
	
	/**
	 * Method for getting the instance of the manager.
	 * @return
	 */
	public static MobileManager getInstance() {
		if (MobileManager.instance == null) {
			MobileManager.instance = new MobileManager();
		}
		return MobileManager.instance;
	}
	
	/**
	 * Method for freeing the instance of the manager.
	 */
	public static void freeInstance() {
		MobileManager.instance = null;
	}
	
	/**
	 * Public method for setting a new user running the application.
	 * @param name
	 * @param password
	 */
	public void setUser(User user) {
		this.user = user;
	}

	public boolean getUserFromPassword() {
		this.identified = false;
		boolean returned = false;
		
		String pass = this.password;
		if (pass.length() == 0) return returned;
		this.password = "" + pass.charAt(0);
		for (int i = 1; i < pass.length(); i ++) {
			if (pass.charAt(i) != pass.charAt(i-1)) this.password += pass.charAt(i);
		}
		if (users.containsKey(this.password)) {
			String name = users.get(this.password).getName();
			boolean notValid = name.equalsIgnoreCase("null");
			if ((name != null)&&(!notValid)) {
				setUser(users.get(this.password));
				pedometerCalValue = user.pedometerCalValue;
				this.identified= true;
				returned = true;
			} else {
				users.remove(this.password);
				saveUsers();
			}
		}
		return returned;
	}
	
	public Long getUserIdFromPassword() {
		String pass = this.password;
		if (pass.length() == 0) return null;
		this.password = "" + pass.charAt(0);
		for (int i = 1; i < pass.length(); i ++) {
			if (pass.charAt(i) != pass.charAt(i-1)) this.password += pass.charAt(i);
		}
		if (users.containsKey(this.password)) return users.get(this.password).getId();
		else return null;		
	}
	
	public boolean extractUsersFromXML(String xml) {
		int count = 0, steplimit = 0, id = 0, type = 0, animations = 0;
		boolean returned = false, insideUser = false, created = false;
		String name = null, password = null, phonepass = null, sexstring = null, ambient = null;
		Date birthdate = new Date(0), lastupdate = new Date(0), laststepcalibration = new Date(0);
		String firstname = "", lastname = "", mail = "";
		Sex sex = Sex.NOT_SPECIFIED;
		int height = 0, pedometer = 30;
		float weight = 0;
		try {
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
	        factory.setNamespaceAware(true);
	        XmlPullParser info = factory.newPullParser();
	        
	        info.setInput(new StringReader(xml));
	        int event = info.getEventType();
	        while (event != XmlPullParser.END_DOCUMENT) {
	            if(event == XmlPullParser.START_DOCUMENT) {
	            	
	            } else if(event == XmlPullParser.END_DOCUMENT) {
	            	
	            } else if(event == XmlPullParser.START_TAG) {
	                if (info.getName().equalsIgnoreCase("PROMOTION")) {
	                	this.promotionDate = HealthGeniusUtil.XMLDateToDate(info.getAttributeValue(0));
	                	try{
	                		this.promotionUrl = info.getAttributeValue(1);
	                	} catch (Exception e) {
	                		this.promotionUrl = null;
	                	}
	                	try{
	                		this.promotionString = info.getAttributeValue(2);
	                	} catch (Exception e) {
	                		this.promotionString = "";
	                	}
		            }
	                if (info.getName().equalsIgnoreCase("USERS")) {
	                	count = Integer.parseInt(info.getAttributeValue(0));
		            }
		            else if (info.getName().equalsIgnoreCase("USER")) {
		              	if (!insideUser) insideUser = true;
		            }
		            else if (info.getName().equalsIgnoreCase("ID")) {
		               	if (!insideUser) {
		               		break;
		               	}
		              	id = Integer.parseInt(info.getAttributeValue(0));
		            }
		            else if (info.getName().equalsIgnoreCase("NAME")) {
		               	if (!insideUser) {
		               		break;
		               	}
		              	name = info.getAttributeValue(0);
		            }
		            else if (info.getName().equalsIgnoreCase("TYPE")) {
		               	if (!insideUser) {
		               		break;
		               	}
		              	type = Integer.parseInt(info.getAttributeValue(0));
		            }
		            else if (info.getName().equalsIgnoreCase("PASSWORD")) {
		                if (!insideUser) {
		                	break;
		                }
		              	password = info.getAttributeValue(0);
		            }
		            else if (info.getName().equalsIgnoreCase("FIRSTNAME")) {
		                if (!insideUser) {
		                	break;
		                }
		              	firstname = info.getAttributeValue(0);
		            }
		            else if (info.getName().equalsIgnoreCase("LASTNAME")) {
		                if (!insideUser) {
		                	break;
		                }
		              	lastname = info.getAttributeValue(0);
		            }
		            else if (info.getName().equalsIgnoreCase("BIRTHDATE")) {
		                if (!insideUser) {
		                	break;
		                }
		                birthdate = HealthGeniusUtil.XMLDateToDate(info.getAttributeValue(0));
		            }
		            else if (info.getName().equalsIgnoreCase("LASTUPDATE")) {
		                if (!insideUser) {
		                	break;
		                }
		                lastupdate = HealthGeniusUtil.XMLDateToDate(info.getAttributeValue(0));
		            }
		            else if (info.getName().equalsIgnoreCase("LASTSTEPCALIBRATION")) {
		                if (!insideUser) {
		                	break;
		                }
		                laststepcalibration = HealthGeniusUtil.XMLDateToDate(info.getAttributeValue(0));
		            }
		            else if (info.getName().equalsIgnoreCase("STEPLIMIT")) {
		                if (!insideUser) {
		                	break;
		                }
		              	steplimit = Integer.parseInt(info.getAttributeValue(0));
		            }
		            else if (info.getName().equalsIgnoreCase("SEX")) {
		                if (!insideUser) {
		                	break;
		                }
		              	sexstring = info.getAttributeValue(0);
		              	if (sexstring.equalsIgnoreCase("MALE")) sex = Sex.MALE;
		              	else if (sexstring.equalsIgnoreCase("FEMALE")) sex = Sex.FEMALE;
		              	else sex = Sex.NOT_SPECIFIED;
		            }
		            else if (info.getName().equalsIgnoreCase("CREATED")) {
		                if (!insideUser) {
		                	break;
		                }
		              	if (info.getAttributeValue(0).equalsIgnoreCase("TRUE")) created = true;
		              	else created = false;
		            }
		            else if (info.getName().equalsIgnoreCase("ANIMATIONS")) {
		                if (!insideUser) {
		                	break;
		                }
		              	animations = Integer.parseInt(info.getAttributeValue(0));
		            }
		            else if (info.getName().equalsIgnoreCase("HEIGHT")) {
		                if (!insideUser) {
		                	break;
		                }
		              	height = Integer.parseInt(info.getAttributeValue(0));
		            }
		            else if (info.getName().equalsIgnoreCase("WEIGHT")) {
		                if (!insideUser) {
		                	break;
		                }
		              	weight = Float.parseFloat(info.getAttributeValue(0));
		            }
		            else if (info.getName().equalsIgnoreCase("PEDOMETER")) {
		                if (!insideUser) {
		                	break;
		                }
		              	pedometer = Integer.parseInt(info.getAttributeValue(0));
		            }
		            else if (info.getName().equalsIgnoreCase("AMBIENT")) {
		                if (!insideUser) {
		                	break;
		                }
		              	ambient = info.getAttributeValue(0);
		            }
		            else if (info.getName().equalsIgnoreCase("MAIL")) {
		                if (!insideUser) {
		                	break;
		                }
		              	mail = info.getAttributeValue(0);
		            }
		            else if (info.getName().equalsIgnoreCase("PHONEPASS")) {
		                if (!insideUser) {
		                	break;
		                }
		              	phonepass = info.getAttributeValue(0);
		            }
	            } else if(event == XmlPullParser.END_TAG) {
	                if (info.getName().equalsIgnoreCase("USER")) {
	                	if (!insideUser) {
	                		break;
	                	}
	                	User user = new User(name, password, birthdate, sex, height, weight);
	                	user.setCreated(created);
	                	user.setId(id);
	                	user.setType(type);
	                	user.setLastupdate(lastupdate);
	                	user.setFirstname(firstname);
	                	user.setLastname(lastname);
	                	user.setLaststepcalibration(laststepcalibration);
	                	user.setSteplimit(steplimit);
	                	user.setAmbient(ambient);
	                	user.pedometerCalValue = pedometer;
	                	user.animations = animations;
	                	user.setMail(mail);
	                	this.users.put(phonepass, user);
	                	name = null;
	                	birthdate = null;
	                	lastupdate = new Date(0);
	                	laststepcalibration = new Date(0);
	                	steplimit = 0;
	                	type = 0;
	                	sex = Sex.NOT_SPECIFIED;
	                	height = 0;
	                	sex = null;
	                	pedometer = 30;
	                	id = 0;
	                	weight = 0;
	                	firstname = "";
	                	lastname = "";
	                	mail = "";
	                	password = null;
	                	animations = 0;
	                	ambient = null;
	                	phonepass = null;
	                	insideUser = false;
	                }
	                if (info.getName().equalsIgnoreCase("USERS")) {
	                	if (count == users.size()) returned = true;
	                	else returned = false;
	                }
	            }
	            event = info.next();
	        }
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returned;
	}
	
	public String passUsersToXML() {
		String returned = "";
		returned += "<PROMOTION DATE=\"" + HealthGeniusUtil.dateToXMLDate(this.promotionDate) + "\" URL=\"" + this.promotionUrl + "\" STRING=\"" + this.promotionString + "\"/>\n";
		returned += "<USERS COUNT=\"";
		returned += this.users.size();
		returned += "\">\n";
		Enumeration<String> enumer = this.users.keys();
		while (enumer.hasMoreElements()) {
			String currentPass = enumer.nextElement();
			User currentUser = this.users.get(currentPass);
			returned += "\t<USER>\n\t\t";
			returned += "<ID VALUE=\"" + currentUser.getId() + "\"/>\n\t\t";
			returned += "<NAME VALUE=\"" + currentUser.getName() + "\"/>\n\t\t";
			returned += "<TYPE VALUE=\"" + currentUser.getType() + "\"/>\n\t\t";
			returned += "<PASSWORD VALUE=\"" + currentUser.getPassword() + "\"/>\n\t\t";
			returned += "<FIRSTNAME VALUE=\"" + currentUser.getFirstname() + "\"/>\n\t\t";
			returned += "<LASTNAME VALUE=\"" + currentUser.getLastname() + "\"/>\n\t\t";
			returned += "<MAIL VALUE=\"" + currentUser.getMail() + "\"/>\n\t\t";
			returned += "<BIRTHDATE VALUE=\"" + HealthGeniusUtil.dateToXMLDate(currentUser.getBirthdate()) + "\"/>\n\t\t";
			returned += "<SEX VALUE=\"" + currentUser.getSex().toString() + "\"/>\n\t\t";
			returned += "<HEIGHT VALUE=\"" + currentUser.getHeight() + "\"/>\n\t\t";
			returned += "<WEIGHT VALUE=\"" + currentUser.getWeight() + "\"/>\n\t\t";
			returned += "<PHONEPASS VALUE=\"" + currentPass + "\"/>\n\t\t";
			returned += "<LASTUPDATE VALUE=\"" + HealthGeniusUtil.dateToXMLDate(currentUser.getLastupdate()) + "\"/>\n\t\t";
			returned += "<LASTSTEPCALIBRATION VALUE=\"" + HealthGeniusUtil.dateToXMLDate(currentUser.getLaststepcalibration()) + "\"/>\n\t\t";
			returned += "<STEPLIMIT VALUE=\"" + currentUser.getSteplimit() + "\"/>\n\t\t";
			returned += "<CREATED VALUE=\"" + (currentUser.isCreated()?"TRUE":"FALSE") + "\"/>\n\t\t";
			returned += "<ANIMATIONS VALUE=\"" + currentUser.animations + "\"/>\n\t\t";
			returned += "<AMBIENT VALUE=\"" + currentUser.getAmbient() + "\"/>\n\t\t";
			returned += "<PEDOMETER VALUE=\"" + currentUser.pedometerCalValue + "\"/>\n\t\t";
			returned += "</USER>\n";
		}
		returned += "</USERS>";
		return returned;
	}

	public void getUsers() {
		try {
			String xmlUsers = "";
			File folder = HealthGenius.myApp.getDir(HealthGeniusConfig.FILES_FOLDER, 0);
			File users = new File(folder, "activausers.xml");
			if (!users.exists()) {
				users.createNewFile();
	    		FileOutputStream fout = new FileOutputStream(users);
	    		OutputStreamWriter osw = new OutputStreamWriter(fout);
	    		osw.write(passUsersToXML());
	    		osw.close();
	    		fout.close();
				return;
			}
			else {
				FileInputStream fin = new FileInputStream(users);
				InputStreamReader isr = new InputStreamReader(fin,"UTF-8");          
		        char[] inputBuffer = new char[255];   
		        while ( isr.read(inputBuffer) != -1) {
		        	xmlUsers += new String(inputBuffer);    
		        }
		        extractUsersFromXML(xmlUsers);
		        isr.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void addUserWithPassword(String username, String password) {
    	try {
    		User user = new User(username, password);
        	this.users.put(this.password, user);
    		File folder = HealthGenius.myApp.getDir(HealthGeniusConfig.FILES_FOLDER, 0);
    		FileOutputStream fout = new FileOutputStream(new File(folder, "activausers.xml"));
    		OutputStreamWriter osw = new OutputStreamWriter(fout);
    		osw.write(passUsersToXML());
    		osw.close();
    		fout.close();
    		setUser(user);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void addUserWithPassword(String username, String password, Date birthdate, Sex sex, int height, float weight) {
    	try {
    		User user = new User(username, password, birthdate, sex, height, weight);
        	this.users.put(this.password, user);
    		File folder = HealthGenius.myApp.getDir(HealthGeniusConfig.FILES_FOLDER, 0);
    		FileOutputStream fout = new FileOutputStream(new File(folder, "activausers.xml"));
    		OutputStreamWriter osw = new OutputStreamWriter(fout);
    		osw.write(passUsersToXML());
    		osw.close();
    		fout.close();
    		setUser(user);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void addUserWithPassword(User user) {
    	try {
        	this.users.put(this.password, user);
    		File folder = HealthGenius.myApp.getDir(HealthGeniusConfig.FILES_FOLDER, 0);
    		FileOutputStream fout = new FileOutputStream(new File(folder, "activausers.xml"));
    		OutputStreamWriter osw = new OutputStreamWriter(fout);
    		osw.write(passUsersToXML());
    		osw.close();
    		fout.close();
    		setUser(user);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void saveUsers() {
    	try {
    		File folder = HealthGenius.myApp.getDir(HealthGeniusConfig.FILES_FOLDER, 0);
    		FileOutputStream fout = new FileOutputStream(new File(folder, "activausers.xml"));
    		OutputStreamWriter osw = new OutputStreamWriter(fout);
    		osw.write(passUsersToXML());
    		osw.close();
    		fout.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void checkPromotion() {
		try {
			String element = ProtocolManager.readFile(HealthGeniusConfig.PROMOTION_URL);
			if (element == null) return;
			StringTokenizer tokenizer = new StringTokenizer(element, "\n");
			Date date = HealthGeniusUtil.XMLDateToDate(tokenizer.nextToken());
	//		String promotionbigurl = tokenizer.nextToken();
	//		String promotionminiurl = tokenizer.nextToken();
			tokenizer.nextToken();
			tokenizer.nextToken();
			String promotionmicrourl = tokenizer.nextToken();
			this.promotionUrl = tokenizer.nextToken();
			this.promotionString = tokenizer.nextToken();
			if (HealthGenius.myApp.getResources().getConfiguration().locale.getLanguage().equals("es"))
				this.promotionString = tokenizer.nextToken();
			if (!this.promotionMicro.exists()) {
				if (ProtocolManager.downloadFile(promotionmicrourl, this.promotionMicro)) this.promotionDate = date;
			}
			else if (date.after(this.promotionDate)) {
				if (ProtocolManager.downloadFile(promotionmicrourl, this.promotionMicro)) this.promotionDate = date;
			}
			this.saveUsers();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean updateUserPassword(String oldpassText, String newpassText) {
		boolean result;
		try {
			result = HealthGenius.myProtocolManager.updateUserPassword(oldpassText, newpassText);
		} catch (NotUpdatedException e) {
			result = false;
			HealthGenius.myUIManager.UImisc.loadTextOnWindow(HealthGenius.myLanguageManager.TEXT_UPDATEVERSION);
			e.printStackTrace();
		}
		return result;
	}

	public boolean updateUserData() {
		boolean result;
		try {
			result = HealthGenius.myProtocolManager.updateUserData();
		} catch (NotUpdatedException e) {
			result = false;
			HealthGenius.myUIManager.UImisc.loadTextOnWindow(HealthGenius.myLanguageManager.TEXT_UPDATEVERSION);
			e.printStackTrace();
		}
		return result;
	}

	public boolean sendWeightAndHeight(float weightText, int heightText) {
		boolean result;
		try {
			result = HealthGenius.myProtocolManager.sendWeightAndHeight(weightText, heightText);
		} catch (NotUpdatedException e) {
			result = false;
			HealthGenius.myUIManager.UImisc.loadTextOnWindow(HealthGenius.myLanguageManager.TEXT_UPDATEVERSION);
			e.printStackTrace();
		}
		return result;
	}

	public boolean register() {
		boolean result;
		try {
			result = HealthGenius.myProtocolManager.register();
		} catch (NotUpdatedException e) {
			result = false;
			HealthGenius.myUIManager.UImisc.loadTextOnWindow(HealthGenius.myLanguageManager.TEXT_UPDATEVERSION);
			e.printStackTrace();
		}
		return result;
	}

	public boolean login() {
		boolean result;
		try {
			result = HealthGenius.myProtocolManager.login();
		} catch (NotUpdatedException e) {
			result = false;
			HealthGenius.myUIManager.UImisc.loadTextOnWindow(HealthGenius.myLanguageManager.TEXT_UPDATEVERSION);
			e.printStackTrace();
		}
		return result;
	}

	public boolean checkMeasurements() {
		boolean result;
		try {
			result = HealthGenius.myProtocolManager.checkMeasurements();
		} catch (NotUpdatedException e) {
			result = false;
			HealthGenius.myUIManager.UImisc.loadTextOnWindow(HealthGenius.myLanguageManager.TEXT_UPDATEVERSION);
			e.printStackTrace();
		}
		return result;
	}

	public int checkUserExistance() {
		int result;
		try {
			result = HealthGenius.myProtocolManager.checkUserExistance();
		} catch (NotUpdatedException e) {
			result = 3;
			HealthGenius.myUIManager.UImisc.loadTextOnWindow(HealthGenius.myLanguageManager.TEXT_UPDATEVERSION);
			e.printStackTrace();
		}
		return result;
	}
	
}
