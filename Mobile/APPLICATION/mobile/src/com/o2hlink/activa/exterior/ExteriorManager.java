package com.o2hlink.activa.exterior;

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

import com.o2hlink.activa.Activa;
import com.o2hlink.activa.ActivaConfig;
import com.o2hlink.activa.R;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.provider.MediaStore;

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
	
	public Hashtable<Integer, ExternalApp> externalApplications;
	
	public Hashtable<Integer, ExternalApp> externalApplicationsLinked;
	
	public static ExteriorManager instance;
	
	private ExteriorManager () {
		initExternalApps();
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
        final List<ResolveInfo> apps = Activa.myApp.getPackageManager().queryIntentActivities(mainIntent, 0);
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
	
	public void runApplication (int appid) {
		ExternalApp app = externalApplications.get(appid);
		if (app.externalApplication) {
			if (isApplicationInstalled(app.packageName))
				app.startApplication();
			else app.redirectToMarket();
		} 
		else app.startApplication();
	}
	
	public void runApplication (ExternalApp app) {
		if (app.externalApplication) {
			if (isApplicationInstalled(app.packageName))
				app.startApplication();
			else app.redirectToMarket();
		} 
		else app.startApplication();
	}
	
	public void initExternalApps() {
		Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
		final List<ResolveInfo> apps = Activa.myApp.getPackageManager().queryIntentActivities(mainIntent, 0);
		this.externalApplications = new Hashtable<Integer, ExternalApp>();
		ExternalApp app = new ExternalApp();
		app.name = "Facebook";
		app.icon = R.drawable.facebook;
		app.packageName = "com.facebook.katana";
		app.activityName = "com.facebook.katana.LoginActivity";
		app.setActivity(new ComponentName("com.facebook.katana", "com.facebook.katana.LoginActivity"), 
                Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
		this.externalApplications.put(APP_FACEBOOK, app);
		app = new ExternalApp();
		app.name = "Twitter";
		app.icon = R.drawable.twitter;
		app.packageName = "com.twitter.android";
		app.activityName = "com.twitter.android.LoginActivity";
		app.setActivity(new ComponentName("com.twitter.android", "com.twitter.android.LoginActivity"), 
                Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
		this.externalApplications.put(APP_TWITTER, app);
		app = new ExternalApp();
		app.name = "LinkedIn";
		app.icon = R.drawable.twitter;
		app.packageName = "bostone.android.droidin";
		app.activityName = "bostone.android.droidin.activity.AutoLoginActivity";
		app.setActivity(new ComponentName("bostone.android.droidin", "bostone.android.droidin.activity.AutoLoginActivity"), 
                Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
		this.externalApplications.put(APP_LINKEDIN, app);
		app = new ExternalApp();
		app.name = "Skype";
		app.icon = 0;
		app.packageName = "com.skype.raider";
		app.activityName = "com.twitter.android.LoginActivity";
		app.setActivity(new ComponentName("com.skype.raider", "com.skype.raider.ui.SplashScreenActivity"), 
                Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
		this.externalApplications.put(APP_SKYPE, app);
		app = new ExternalApp();
		app.name = "Spotify";
		app.icon = R.drawable.spotify;
		app.packageName = "com.spotify.mobile.android.ui";
		app.activityName = "com.spotify.mobile.android.ui.Launcher";
		app.setActivity(new ComponentName("com.spotify.mobile.android.ui", "com.spotify.mobile.android.ui.Launcher"), 
                Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
		this.externalApplications.put(APP_SPOTIFY, app);
		app = new ExternalApp();
		app.name = "Deezer";
		app.icon = R.drawable.deezer;
		app.packageName = "deezer.android.app";
		app.activityName = "dz.ApplicationScreens";
		app.setActivity(new ComponentName("deezer.android.app", "dz.ApplicationScreens"), 
                Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
		this.externalApplications.put(APP_DEEZER, app);
		app = new ExternalApp();
		app.name = "LastFM";
		app.icon = R.drawable.lastfm;
		app.packageName = "fm.last.android";
		app.activityName = "fm.last.android.LastFm";
		app.setActivity(new ComponentName("fm.last.android", "fm.last.android.LastFm"), 
                Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
		this.externalApplications.put(APP_LASTFM, app);
		app = new ExternalApp();
		app.name = "Youtube";
		app.icon = R.drawable.youtube;
		app.packageName = "com.google.android.youtube";
		app.activityName = "com.google.android.youtube.HomeActivity";
		app.setActivity(new ComponentName("com.google.android.youtube", "com.google.android.youtube.HomeActivity"), 
                Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
		this.externalApplications.put(APP_YOUTUBE, app);
		app = new ExternalApp();
		app.name = Activa.myLanguageManager.MAIN_VIDCAM;
		app.icon = R.drawable.videocamera;
		app.externalApplication = false;
		app.packageName = null;
		app.activityName = null;
		Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
		app.setActivity(intent);
		this.externalApplications.put(APP_VIDEOCAMERA, app);
		app = new ExternalApp();
		app.name = Activa.myLanguageManager.MAIN_PICCAM;
		app.icon = R.drawable.camera;
		app.externalApplication = false;
		app.packageName = null;
		app.activityName = null;
		intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
		app.setActivity(intent);
		this.externalApplications.put(APP_CAMERA, app);
		app = new ExternalApp();
		app.name = Activa.myLanguageManager.MAIN_MUSICGAL;
		app.externalApplication = false;
		app.icon = R.drawable.music;
		app.packageName = null;
		app.activityName = null;
//		intent = new Intent(MediaStore.INTENT_ACTION_MUSIC_PLAYER);
        for (int i = 0; i < apps.size(); i++) {
			ResolveInfo info = apps.get(i);
			if (info.activityInfo.name.contains("Music")||info.activityInfo.name.contains("music")) {
				app.setActivity(new ComponentName(
	                    info.activityInfo.applicationInfo.packageName,
	                    info.activityInfo.name), 
		                Intent.FLAG_ACTIVITY_NEW_TASK
		                | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
				break;
			}
		}
		this.externalApplications.put(APP_MUSIC, app);
//		app = new ImageGallery();
		app = new ExternalApp();
		app.name = Activa.myLanguageManager.MAIN_PICTGAL;
		app.icon = R.drawable.pictures;
		app.externalApplication = false;
		app.packageName = null;
		app.activityName = null;
//		intent = new Intent(Intent.ACTION_GET_CONTENT);
//		intent.setType("image/*");
//		app.setActivity(intent);
        for (int i = 0; i < apps.size(); i++) {
			ResolveInfo info = apps.get(i);
			if (info.activityInfo.name.contains("Gallery")||info.activityInfo.name.contains("gallery")||
					info.activityInfo.name.contains("Album")||info.activityInfo.name.contains("album")||
					info.activityInfo.name.contains("Pictures")||info.activityInfo.name.contains("pictures")) {
				app.setActivity(new ComponentName(
	                    info.activityInfo.applicationInfo.packageName,
	                    info.activityInfo.name), 
		                Intent.FLAG_ACTIVITY_NEW_TASK
		                | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
				break;
			}
		}
		this.externalApplications.put(APP_PICTURES, app);
//		app = new VideoGallery();
//		app.name = Activa.myLanguageManager.MAIN_VIDGAL;
//		app.icon = R.drawable.videos;
//		app.externalApplication = false;
//		app.packageName = null;
//		intent = new Intent(Intent.ACTION_GET_CONTENT);
////		intent.addCategory(Intent.CATEGORY_OPENABLE);
//		intent.setType("video/*");
////		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//		app.setActivity(intent);
//		this.externalApplications.put(APP_VIDEOS, app);
		app = new ExternalApp();
		app.externalApplication = false;
		app.name = Activa.myLanguageManager.MAIN_SMSMESSAGES;
		app.icon = R.drawable.sms;
		app.packageName = "com.android.mms";
		app.activityName = "com.android.mms.ui.ConversationList";
		app.setActivity(new ComponentName("com.android.mms", "com.android.mms.ui.ConversationList"), 
                Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
		this.externalApplications.put(APP_SMS, app);
		app = new ExternalApp();
		app.externalApplication = false;
		app.name = Activa.myLanguageManager.MAIN_MAIL;
		app.icon = R.drawable.email;
		for (int i = 0; i < apps.size(); i++) {
			ResolveInfo info = apps.get(i);
			if (info.activityInfo.name.contains("Mail")||info.activityInfo.name.contains("mail")) {
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
		this.externalApplications.put(APP_MAIL, app);
		app = new ExternalApp();
		app.name = "Gmail";
		app.icon = R.drawable.gmail;
		app.packageName = "com.google.android.gm";
		app.activityName = "com.google.android.gm.ConversationListActivityGmail";
		app.setActivity(new ComponentName("com.google.android.gm", "com.google.android.gm.ConversationListActivityGmail"), 
                Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
		this.externalApplications.put(APP_GMAIL, app);
		app = new ExternalApp();
		app.name = "Wikipedia";
		app.icon = 0;
		app.packageName = "com.amazingdroid.content.wiki";
		app.activityName = "com.amazingdroid.content.wiki.Wiki";
		app.setActivity(new ComponentName("com.amazingdroid.content.wiki", "com.amazingdroid.content.wiki.Wiki"), 
                Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
		this.externalApplications.put(APP_WIKIPEDIA, app);
		app = new ExternalApp();
		app.externalApplication = false;
		app.name = "Google";
		app.icon = 0;
		app.packageName = null;
		app.activityName = null;
		Intent in = new Intent(Intent.ACTION_VIEW);
		in.setData(Uri.parse("http://www.google.com"));
		app.setActivity(in);
		this.externalApplications.put(APP_GOOGLE, app);
		app = new ExternalApp();
		app.externalApplication = false;
		app.name = "Bing";
		app.icon = 0;
		app.packageName = null;
		app.activityName = null;
		in = new Intent(Intent.ACTION_VIEW);
		in.setData(Uri.parse("http://www.bing.com"));
		app.setActivity(in);
		this.externalApplications.put(APP_BING, app);	
		app = new ExternalApp();
		app.name = "Slacker";
		app.icon = 0;
		app.packageName = "com.slacker.radio";
		app.activityName = "com.slacker.gui.SplashScreen";
		app.setActivity(new ComponentName("com.slacker.radio", "com.slacker.gui.SplashScreen"), 
                Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
		this.externalApplications.put(APP_SLACKER, app);
		app = new ExternalApp();
		app.externalApplication = false;
		app.name = Activa.myLanguageManager.MAIN_CONTACTS;
		app.icon = R.drawable.accounts;
		app.packageName = null;
		app.activityName = null;
		for (int i = 0; i < apps.size(); i++) {
			ResolveInfo info = apps.get(i);
			if (info.activityInfo.name.contains("Contact")||info.activityInfo.name.contains("contact")) {
				app.setActivity(new ComponentName(
	                    info.activityInfo.applicationInfo.packageName,
	                    info.activityInfo.name), 
		                Intent.FLAG_ACTIVITY_NEW_TASK
		                | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
				break;
			}
		}
		this.externalApplications.put(APP_CONTACTS, app);
		app = new ExternalApp();
		app.externalApplication = false;
		app.name = Activa.myLanguageManager.CALENDAR_TITLE;
		app.icon = R.drawable.calendar;
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
		this.externalApplications.put(APP_CALENDAR, app);
	}
	
	public List<ResolveInfo> getExternalAppsList() {
		Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
		return Activa.myApp.getPackageManager().queryIntentActivities(mainIntent, 0);
	}

	public void initExternalAppsLinked() {
		int count = 0;
		this.externalApplicationsLinked = new Hashtable<Integer, ExternalApp>();
		try {
			String xml = "";
			File folder = Activa.myApp.getDir(ActivaConfig.FILES_FOLDER, 0);
			File users = new File(folder, Activa.myMobileManager.user.getName() + "programs.xml");
			if (!users.exists()) {
				users.createNewFile();
	    		FileOutputStream fout = new FileOutputStream(users);
	    		OutputStreamWriter osw = new OutputStreamWriter(fout);
	    		osw.write(passExternalAppsToXML());
	    		osw.close();
	    		fout.close();
				return;
			}
			else {
				FileInputStream fin = new FileInputStream(users);
				InputStreamReader isr = new InputStreamReader(fin,"UTF-8");          
		        char[] inputBuffer = new char[255];   
		        while ( isr.read(inputBuffer) != -1) {
		        	xml += new String(inputBuffer);    
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
		                if (info.getName().equalsIgnoreCase("PROGRAMS")) {
		                	count = Integer.parseInt(info.getAttributeValue(info.getNamespace(),"COUNT"));
			            }
			            else if (info.getName().equalsIgnoreCase("PROGRAM")) {
			            	ExternalApp app = new ExternalApp();
			            	int id = Integer.parseInt(info.getAttributeValue(info.getNamespace(),"ID"));
			            	app.name = info.getAttributeValue(info.getNamespace(),"NAME");
			            	app.packageName = info.getAttributeValue(info.getNamespace(),"PACKAGE");
			            	app.activityName = info.getAttributeValue(info.getNamespace(),"ACTIVITY");
			            	app.icon = 0;
			            	app.setActivity(new ComponentName(app.packageName, app.activityName), 
			                        Intent.FLAG_ACTIVITY_NEW_TASK
			                        | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
			            	final PackageManager packageManager = Activa.myApp.getPackageManager();
			    		    List<ResolveInfo> list =
			    		            packageManager.queryIntentActivities(app.intent,
			    		                    PackageManager.MATCH_DEFAULT_ONLY);
			    		    if (list.size() > 0) {
			    		    	this.externalApplicationsLinked.put(id, app);
			    		    	count--;
			    		    }
			            }
		            } else if(event == XmlPullParser.END_TAG) {
		                if (info.getName().equalsIgnoreCase("PROGRAMS")) {
		                	if (count != this.externalApplicationsLinked.size()) this.externalApplicationsLinked.clear();
		                	break;
		                }
		            }
		            event = info.next();
		        }
		        isr.close();
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
		returned += "<PROGRAMS COUNT=\"";
		returned += this.externalApplicationsLinked.size();
		returned += "\">\n";
		Enumeration<Integer> enumer = this.externalApplicationsLinked.keys();
		while (enumer.hasMoreElements()) {
			int currentAppID = enumer.nextElement();
			ExternalApp currentApp = this.externalApplicationsLinked.get(currentAppID);
			returned += "\t<PROGRAM ID=\"" + currentAppID + "\" ";
			returned += "NAME=\"" + currentApp.name + "\" ";
			returned += "PACKAGE=\"" + currentApp.packageName + "\" ";
			returned += "ACTIVITY=\"" + currentApp.activityName + "\"/>\n";
		}
		returned += "</PROGRAMS>";
		return returned;
	}
	
	public void savePrograms() {
    	try {
    		File folder = Activa.myApp.getDir(ActivaConfig.FILES_FOLDER, 0);
    		FileOutputStream fout = new FileOutputStream(new File(folder, Activa.myMobileManager.user.getName() + "programs.xml"));
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
	
}
