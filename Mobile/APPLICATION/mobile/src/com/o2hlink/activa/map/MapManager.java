package com.o2hlink.activa.map;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Hashtable;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import com.o2hlink.activ8.client.entity.Array;
import com.o2hlink.activ8.client.entity.Institution;
import com.o2hlink.activa.Activa;
import com.o2hlink.activa.exceptions.NotUpdatedException;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;

public class MapManager {

	public boolean showingMap = false;
	private static MapManager instance = null;
	public Mark myPosition = null;
	public ArrayList<Mark> myMarks = null;
	public Hashtable<Long, Institution> institutions;
	public String provider;
	public LocationManager myLocationManager;
	public Location location;
	public double northLatitude;
	public double southLatitude;
	public double eastLongitude;
	public double westLongitude;
	
	private MapManager () {
		this.showingMap = false;
		this.myMarks = new ArrayList<Mark>();
		this.institutions = new Hashtable<Long, Institution>();
		getMyPosition();
	}
	
	public MapManager (boolean set) {
		this.showingMap = false;
		this.myMarks = new ArrayList<Mark>();
		this.institutions = new Hashtable<Long, Institution>();
		if (set) getMyPosition();
	}

	public static MapManager getInstance() {
		if (MapManager.instance == null) MapManager.instance = new MapManager();
		return MapManager.instance;
	}
	
	/**
	 * Method for freeing the instance of the manager.
	 */
	public static void freeInstance() {
		MapManager.instance = null;
	}
	
//	public void showMap() {
//		this.showingMap = true;
//		Activa.myApp.startActivity(new Intent(Activa.myApp, com.o2hlink.activa.map.ActivaMap.class));
//	}

	public void getMapMarks() {
		ActivaMap.myMap.updateMapViewLimits();
		this.myMarks.clear();
		try {
			Activa.myProtocolManager.getMapMarks(this);
		} catch (NotUpdatedException e) {
			Activa.myUIManager.loadTextOnWindow(Activa.myLanguageManager.TEXT_UPDATEVERSION);
			e.printStackTrace();
		}
	}
	
	public void extractMarksFromXML(String xml) {
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
	                if (info.getName().equalsIgnoreCase("MARK")) {
	                	String name = info.getAttributeValue(info.getNamespace(), "TITLE");
//	                	String text = info.getAttributeValue(info.getNamespace(), "TEXT");
	                	String text = "";
	                	Double latitude = Double.parseDouble(info.getAttributeValue(info.getNamespace(), "LATITUDE"));
	                	Double longitude = Double.parseDouble(info.getAttributeValue(info.getNamespace(), "LONGITUDE"));
	                	this.myMarks.add(new Mark(longitude, latitude, name, text));
	                }
	            } else if(event == XmlPullParser.END_TAG) {
	            }
	            event = info.next();
	        }
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void getMyPosition() {
        this.myLocationManager = (LocationManager)Activa.myApp.getSystemService(Context.LOCATION_SERVICE); 
        this.provider = this.myLocationManager.getBestProvider(new Criteria(), true);
        if (this.provider != null) {
        	this.location = this.myLocationManager.getLastKnownLocation(this.provider);
            if (this.location != null) this.myPosition = new Mark(this.location.getLongitude(), this.location.getLatitude(), Activa.myMobileManager.user.getName(), "Usuario actual usando el movil");
        }
        else {
        	this.location = null;
        	this.myPosition = null;
        }
	}
	
	public void setLocationListener(LocationListener listener) {
		try {
			Activa.myMapManager.myLocationManager.requestLocationUpdates(Activa.myMapManager.provider, 0, 0, listener);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean createMapSite(Institution site) {
		boolean result;
		try {
			result = Activa.myProtocolManager.createMapSite(site);
		} catch (NotUpdatedException e) {
			Activa.myUIManager.loadTextOnWindow(Activa.myLanguageManager.TEXT_UPDATEVERSION);
			e.printStackTrace();
			result = false;
		}
		return result;
	}

	public boolean removeMapSite(Institution site) {
		boolean result;
		try {
			result = Activa.myProtocolManager.removeMapSite(site);
		} catch (NotUpdatedException e) {
			Activa.myUIManager.loadTextOnWindow(Activa.myLanguageManager.TEXT_UPDATEVERSION);
			e.printStackTrace();
			result = false;
		}
		return result;
	}

	public Array<Institution> searchMapSite(String query) {
		Array<Institution> result;
		try {
			result = Activa.myProtocolManager.searchMapSite(query);
		} catch (NotUpdatedException e) {
			Activa.myUIManager.loadTextOnWindow(Activa.myLanguageManager.TEXT_UPDATEVERSION);
			e.printStackTrace();
			result = null;
		}
		return result;
	}

	public boolean addMapSite(Institution site) {
		boolean result;
		try {
			result = Activa.myProtocolManager.addMapSite(site);
		} catch (NotUpdatedException e) {
			Activa.myUIManager.loadTextOnWindow(Activa.myLanguageManager.TEXT_UPDATEVERSION);
			e.printStackTrace();
			result = false;
		}
		return result;
	}
	
}
