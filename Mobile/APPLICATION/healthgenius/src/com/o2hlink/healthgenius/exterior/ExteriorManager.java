package com.o2hlink.healthgenius.exterior;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import com.o2hlink.healthgenius.HealthGenius;
import com.o2hlink.healthgenius.HealthGeniusConfig;
import com.o2hlink.healthgenius.data.calendar.CalendarManager;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

public class ExteriorManager {

	public static final int APP_FACEBOOK = 0;
	public static final int APP_TWITTER = 1;
	public static final int APP_SKYPE = 2;
	public static final int APP_MUSIC = 3;
	public static final int APP_RADIOFM = 4;
	public static final int APP_SPOTIFY = 5;
	public static final int APP_DEEZER = 6;
	public static final int APP_LASTFM = 7;
	public static final int APP_VIDEOS = 8;
	public static final int APP_YOUTUBE = 9;
	public static final int APP_VIDEOCAMERA = 10;
	public static final int APP_PICTURES = 11;
	public static final int APP_CAMERA = 12;
	public static final int APP_SMS = 13;
	public static final int APP_MAIL = 14;
	public static final int APP_GMAIL = 15;
	public static final int APP_BING = 16;
	public static final int APP_GOOGLE = 17;
	public static final int APP_WIKIPEDIA = 18;
	public static final int APP_LINKEDIN = 19;
	public static final int APP_SLACKER = 20;
	public static final int APP_CONTACTS = 21;
	public static final int APP_CALENDAR = 22;
	
	public static final int INTENT_RESULT_IMAGESELECTED = 11;
	public static final int INTENT_RESULT_IMAGELOADED = 12;
	public static final int INTENT_RESULT_VIDEOSELECTED = 13;
	public static final int INTENT_RESULT_VIDEOLOADED = 14;
	
	public Hashtable<Integer, ExternalApp> externalApplicationsLinked;
	
	public static ExteriorManager instance;
	
	public ExternalApp calendar;
	
	private ExteriorManager () {
		ExternalApp app = new ExternalApp();
		Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
		final List<ResolveInfo> apps = HealthGenius.myApp.getPackageManager().queryIntentActivities(mainIntent, 0);
		for (int i = 0; i < apps.size(); i++) {
			ResolveInfo info = apps.get(i);
			if (info.activityInfo.name.contains("calendar")||info.activityInfo.name.contains("Calendar")) {
				app.packageName = info.activityInfo.applicationInfo.packageName;
				app.activityName = info.activityInfo.name;
				app.setActivity(new ComponentName(
	                    info.activityInfo.applicationInfo.packageName,
	                    info.activityInfo.name), 
		                Intent.FLAG_ACTIVITY_NEW_TASK
		                | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
				break;
			}
		}
		calendar = app;
	}
	
	public static ExteriorManager getInstance() {
		if (instance != null) return instance;
		else return new ExteriorManager();
	}
	
	public static void freeInstance() {
		instance = null;
	}
	
	public boolean isApplicationInstalled(String packageName) {
		boolean returned = false;
		Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        final List<ResolveInfo> apps = HealthGenius.myApp.getPackageManager().queryIntentActivities(mainIntent, 0);
        Iterator<ResolveInfo> it = apps.iterator();
        while (it.hasNext()) {
        	ResolveInfo info = it.next();
        	if (packageName.equals(info.activityInfo.packageName)) {
        		returned = true;
        		break;
        	}
        }
        return returned;
	}
	
	public void runApplication (ExternalApp app) {
		if (app.externalApplication) {
			if (isApplicationInstalled(app.packageName))
				app.startApplication();
			else app.redirectToMarket();
		} 
		else app.startApplication();
	}
	
	public boolean initForFirstProgram() {
		try {
			PackageManager manager = HealthGenius.myApp.getPackageManager();
			Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
	        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
			final List<ResolveInfo> apps = manager.queryIntentActivities(mainIntent, 0);
			this.externalApplicationsLinked = new Hashtable<Integer, ExternalApp>();
			ExternalApp app;
	    	for (int i = 0; i < apps.size(); i++) {
				ResolveInfo info = apps.get(i);
				if (info.activityInfo.name.contains("pubmed")||info.activityInfo.name.contains("pubMed")||
						info.activityInfo.name.contains("Pubmed")||info.activityInfo.name.contains("PubMed")||
						info.activityInfo.name.contains("webmd")||info.activityInfo.name.contains("Webmd")||
						info.activityInfo.name.contains("webMd")||info.activityInfo.name.contains("WebMd")||
						info.activityInfo.name.contains("Medscape")||info.activityInfo.name.contains("medscape")||
						info.activityInfo.name.contains("qxmd")) {
					app = new ExternalApp();
			    	app.name = info.loadLabel(manager).toString();
			    	app.packageName = info.activityInfo.applicationInfo.packageName;
			    	app.activityName = info.activityInfo.name;
			    	app.icon = 0;
			    	app.setActivity(new ComponentName(app.packageName, app.activityName), 
			                Intent.FLAG_ACTIVITY_NEW_TASK
			                | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
					this.externalApplicationsLinked.put(getFreeLinkId(this.externalApplicationsLinked), app);
				}
			}
			return true;
		} catch (Exception e) {
			this.externalApplicationsLinked = new Hashtable<Integer, ExternalApp>();
			return false;
		}
	}
	
	public List<ResolveInfo> getExternalAppsList() {
		Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
		return HealthGenius.myApp.getPackageManager().queryIntentActivities(mainIntent, 0);
	}

	public void initExternalAppsLinked() {
		this.externalApplicationsLinked = new Hashtable<Integer, ExternalApp>();
		try {
			String xml = "";
			File folder = HealthGenius.myApp.getDir(HealthGeniusConfig.FILES_FOLDER, 0);
			File users = new File(folder, HealthGenius.myMobileManager.user.getName() + "programs.xml");
			if (!users.exists()) {
				users.createNewFile();
	    		FileOutputStream fout = new FileOutputStream(users);
	    		OutputStreamWriter osw = new OutputStreamWriter(fout);
	    		this.initForFirstProgram();
	    		osw.write(passExternalAppsToXML());
	    		osw.close();
	    		fout.close();
				return;
			}
			else {
				FileInputStream fin = new FileInputStream(users);
				InputStreamReader isr = new InputStreamReader(fin,"UTF-8");          
		        char[] inputBuffer = new char[255]; 
		        int read = isr.read(inputBuffer);
		        while (read  != -1) {
		        	xml += new String(inputBuffer, 0, read);    
		        	read = isr.read(inputBuffer);
		        }
				XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
		        factory.setNamespaceAware(true);
		        XmlPullParser info = factory.newPullParser();
		        info.setInput(new StringReader(xml));
		        int event = info.getEventType();
		        while (event != XmlPullParser.END_DOCUMENT) {
		            if(event == XmlPullParser.START_DOCUMENT) {
		            } else if(event == XmlPullParser.END_DOCUMENT) {
		            } else if(event == XmlPullParser.START_TAG) {
		               if (info.getName().equalsIgnoreCase("PROGRAM")) {
			            	ExternalApp app = new ExternalApp();
			            	int id = Integer.parseInt(info.getAttributeValue(info.getNamespace(),"ID"));
			            	app.name = info.getAttributeValue(info.getNamespace(),"NAME");
			            	app.packageName = info.getAttributeValue(info.getNamespace(),"PACKAGE");
			            	app.activityName = info.getAttributeValue(info.getNamespace(),"ACTIVITY");
			            	app.icon = 0;
			            	app.setActivity(new ComponentName(app.packageName, app.activityName), 
			                        Intent.FLAG_ACTIVITY_NEW_TASK
			                        | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
			            	final PackageManager packageManager = HealthGenius.myApp.getPackageManager();
			    		    List<ResolveInfo> list =
			    		            packageManager.queryIntentActivities(app.intent,
			    		                    PackageManager.MATCH_DEFAULT_ONLY);
			    		    if (list.size() > 0) {
			    		    	this.externalApplicationsLinked.put(id, app);
			    		    }
			            }
		            } else if(event == XmlPullParser.END_TAG) {
		            }
		            event = info.next();
		        }
		        isr.close();
		        if (this.externalApplicationsLinked.isEmpty()) this.initForFirstProgram();
	        	savePrograms();
			}
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String passExternalAppsToXML() {
		String returned = "";
		Enumeration<Integer> enumer = this.externalApplicationsLinked.keys();
		while (enumer.hasMoreElements()) {
			int currentAppID = enumer.nextElement();
			ExternalApp currentApp = this.externalApplicationsLinked.get(currentAppID);
			returned += "<PROGRAM ID=\"" + currentAppID + "\" ";
			returned += "NAME=\"" + currentApp.name + "\" ";
			returned += "PACKAGE=\"" + currentApp.packageName + "\" ";
			returned += "ACTIVITY=\"" + currentApp.activityName + "\"/>\n";
		}
		return returned;
	}
	
	public void savePrograms() {
    	try {
    		File folder = HealthGenius.myApp.getDir(HealthGeniusConfig.FILES_FOLDER, 0);
    		FileOutputStream fout = new FileOutputStream(new File(folder, HealthGenius.myMobileManager.user.getName() + "programs.xml"));
    		OutputStreamWriter osw = new OutputStreamWriter(fout);
    		osw.write(passExternalAppsToXML());
    		osw.close();
    		fout.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public int getFreeAppLinkId() {
		int id = 100;
		while (this.externalApplicationsLinked.containsKey(id)) id++;
		return id;
	}
	
	public int getFreeLinkId(Hashtable<Integer, ExternalApp> links) {
		int id = 100;
		while (links.containsKey(id)) id++;
		return id;
	}

	public void addProgram(ResolveInfo infoProgram, PackageManager manager) {
		ExternalApp app = new ExternalApp();
    	int id = HealthGenius.myExteriorManager.getFreeAppLinkId();
    	app.name = infoProgram.loadLabel(manager).toString();
    	app.packageName = infoProgram.activityInfo.applicationInfo.packageName;
    	app.activityName = infoProgram.activityInfo.name;
    	app.icon = 0;
    	app.setActivity(new ComponentName(app.packageName, app.activityName), 
                Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
    	HealthGenius.myExteriorManager.externalApplicationsLinked.put(id, app);
    	HealthGenius.myExteriorManager.savePrograms();
	}
	
}
