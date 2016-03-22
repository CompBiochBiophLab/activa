package com.o2hlink.activa.map;

import java.util.Iterator;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ZoomControls;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;
import com.google.android.maps.Projection;
import com.o2hlink.activa.Activa;
import com.o2hlink.activa.R;
import com.o2hlink.activa.background.NotificationThread;
import com.o2hlink.activa.ui.UIManager;

public class ActivaMap extends MapActivity {

	public MapView mapView;
	TextView title;
	ImageButton back, refresh;
	public static Context mapContext;
	public static ActivaMap myMap;
	public MyLocationOverlay myLocationOverlay;
	public static boolean finished = false;
	
	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.map); 

		Activa.myUIManager.state = UIManager.UI_STATE_MAP;
        mapContext = this;
        myMap = this;
        title = (TextView) findViewById(R.id.startText);
        refresh = (ImageButton) findViewById(R.id.refresh);
        back = (ImageButton) findViewById(R.id.previous);
        title.setText(Activa.myLanguageManager.MAP_TITLE);
		mapView = (MapView) findViewById(R.id.mapview);
		mapView.setBuiltInZoomControls(true);
		drawMyPositionForFirstTime();
		updateOverlays();
		refresh.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				updateOverlays();
			}
		});
		back.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				Activa.myUIManager.state = UIManager.UI_STATE_MAIN;
				finished = true;
				myMap.finish();
				Activa.myUIManager.loadGeneratedSubenvironment(Activa.myUIManager.lastSubenv, true);
//				startActivity(new Intent(mapContext, Activa.class));
			}
		});
		LocationListener listener = new LocationListener() {
			@Override
			public void onStatusChanged(String provider, int status, Bundle extras) {
			}
			@Override
			public void onProviderEnabled(String provider) {
			}
			@Override
			public void onProviderDisabled(String provider) {
			}
			@Override
			public void onLocationChanged(Location location) {
				mapView.postInvalidate();
			}
		};
		Activa.myMapManager.setLocationListener(listener);
//		while (!finished) {
//			try {
//				Thread.sleep(3000);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//			Activa.myMapManager.getMapMarks();
//			refreshMyMarks();
//			mapView.invalidate();
//		}
		
//        Drawable icon = this.getResources().getDrawable(R.drawable.ownposition);
//        ActivaItemizedOverlay itemizedoverlay = new ActivaItemizedOverlay(icon, mapContext);       
//        GeoPoint myPosition = new GeoPoint((int)(Activa.myMapManager.myPosition.latitude*1000000),(int)(Activa.myMapManager.myPosition.longitude*1000000));
//		OverlayItem overlayitem = new OverlayItem(myPosition, Activa.myMapManager.myPosition.name, Activa.myMapManager.myPosition.text);		
//		itemizedoverlay.addMyPositionOverlay(overlayitem);
//		mapOverlays.add(itemizedoverlay);
		
//		refreshMyPosition();
		
//		MyLocationOverlay myLocationOverlay = new  MyLocationOverlay(this, mapView);
//		myLocationOverlay.enableMyLocation();
//		myLocationOverlay.getMyLocation();		
		
		
//		Iterator<Mark> it = Activa.myMapManager.myMarks.iterator();
//		while (it.hasNext()) {
//			Mark currentMark = it.next();
//			icon = this.getResources().getDrawable(R.drawable.position);
//	        itemizedoverlay = new ActivaItemizedOverlay(icon, mapContext);
//			myPosition = new GeoPoint((int)(currentMark.latitude*1000000),(int)(currentMark.longitude*1000000));
//			overlayitem = new OverlayItem(myPosition, currentMark.name, currentMark.text);		
//			itemizedoverlay.addOverlay(overlayitem);
//			mapOverlays.add(itemizedoverlay);
//		}
		
//		refreshMyMarks();	

//		RefreshPositionThread refreshPositionThread = RefreshPositionThread.getInstance();
//		Thread thread = new Thread(refreshPositionThread, "UPDATINGPOSITION");
//		thread.start();
	}
    
    @Override
	public void onDestroy() {
		super.onDestroy();
//		myLocationOverlay.disableMyLocation();
		this.finished = true;
		this.finish();
	}
    
//  Wait to Android 2.0
//  @Override
//	public void  onBackPressed  () { 
//		this.finish();
//		Activa.myApp.finish();
//  }
    
    @Override
	public boolean onKeyDown(int keyCode, KeyEvent  event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			this.finish();
			Activa.myApp.finish();
			break;
		default:
			super.onKeyDown(keyCode, event);
			break;
		}
    	return true;    	
    }
    
//    public void refreshMyPosition() {
//		List<Overlay> mapOverlays = mapView.getOverlays();
//    	Drawable icon = this.getResources().getDrawable(R.drawable.ownposition);
//        ActivaItemizedOverlay itemizedoverlay = new ActivaItemizedOverlay(icon, mapContext);       
//        GeoPoint myPosition = new GeoPoint((int)(Activa.myMapManager.myPosition.latitude*1000000),(int)(Activa.myMapManager.myPosition.longitude*1000000));
//		OverlayItem overlayitem = new OverlayItem(myPosition, Activa.myMapManager.myPosition.name, Activa.myMapManager.myPosition.text);		
//		itemizedoverlay.addMyPositionOverlay(overlayitem);
//		mapOverlays.add(itemizedoverlay);
//    }
    
    public void drawMyPosition() {
		List<Overlay> mapOverlays = mapView.getOverlays();
		myLocationOverlay = new  MyLocationOverlay(this, mapView);
		if (myLocationOverlay.enableMyLocation()) {
			mapOverlays.add(myLocationOverlay);
		}	
    }
    
    public void drawMyPositionForFirstTime() {
		List<Overlay> mapOverlays = mapView.getOverlays();
		myLocationOverlay = new  MyLocationOverlay(this, mapView);
		if (myLocationOverlay.enableMyLocation()) {
			myLocationOverlay.runOnFirstFix(new  Runnable(){
				public void  run(){
					mapView.getController().animateTo(myLocationOverlay.getMyLocation());
	            }
			});
			mapOverlays.add(myLocationOverlay);
		}	
    }
    
    public void refreshMyMarks() {
		List<Overlay> mapOverlays = mapView.getOverlays();
    	Iterator<Mark> it = Activa.myMapManager.myMarks.iterator();
    	mapOverlays.add(myLocationOverlay);
		while (it.hasNext()) {
			Mark currentMark = it.next();
			Drawable icon = ActivaMap.mapContext.getResources().getDrawable(R.drawable.position);
	        ActivaItemizedOverlay itemizedoverlay = new ActivaItemizedOverlay(icon, mapContext);
			GeoPoint myPosition = new GeoPoint((int)(currentMark.latitude*1000000),(int)(currentMark.longitude*1000000));
			OverlayItem overlayitem = new OverlayItem(myPosition, currentMark.name, currentMark.text);		
			itemizedoverlay.addOverlay(overlayitem);
			mapOverlays.add(itemizedoverlay);
		}
    }
    
    public void updateOverlays() {
    	new OverlayTask().execute();
    }
    
    public void updateMapViewLimits() {
    	GeoPoint mapCenter = mapView.getMapCenter();
		Activa.myMapManager.northLatitude = (double) (mapCenter.getLatitudeE6() + mapView.getLatitudeSpan()/2)/1e6;
		if (Activa.myMapManager.northLatitude > 90.0) Activa.myMapManager.northLatitude = 90.0;
		Activa.myMapManager.southLatitude = (double) (mapCenter.getLatitudeE6() - mapView.getLatitudeSpan()/2)/1e6;
		if (Activa.myMapManager.southLatitude < -90.0) Activa.myMapManager.southLatitude = -90.0;
		Activa.myMapManager.eastLongitude = (double) (mapCenter.getLongitudeE6() + mapView.getLongitudeSpan()/2)/1e6;
		if (Activa.myMapManager.eastLongitude > 180.0) Activa.myMapManager.eastLongitude -= 360.0;
		Activa.myMapManager.westLongitude = (double) (mapCenter.getLongitudeE6() - mapView.getLongitudeSpan()/2)/1e6;
		if (Activa.myMapManager.westLongitude < -180.0) Activa.myMapManager.westLongitude += 360.0;
		if (mapView.getLongitudeSpan() > 360.0) {
			Activa.myMapManager.westLongitude = -180.0;
			Activa.myMapManager.eastLongitude = 180.0;
		}			
    }
    
    class OverlayTask extends AsyncTask<Void, Void, Void> {
    	@Override
    	public void onPreExecute() {
    		mapView.getOverlays().clear();
    		mapView.invalidate();
    	}

    	@Override
    	public Void doInBackground(Void... unused) {
			if (Activa.myProtocolManager.connected) Activa.myMapManager.getMapMarks();
			return(null);
    	}

    	@Override
    	public void onPostExecute(Void unused) {
    		refreshMyMarks();
    		mapView.invalidate();
    	}
    }
	
}
