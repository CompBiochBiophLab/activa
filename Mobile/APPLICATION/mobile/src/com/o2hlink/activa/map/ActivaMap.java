package com.o2hlink.activa.map;

import java.io.IOException;
import java.util.Enumeration;
import java.util.List;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;
import com.o2hlink.activ8.client.entity.Array;
import com.o2hlink.activ8.client.entity.Institution;
import com.o2hlink.activa.Activa;
import com.o2hlink.activa.R;
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
	}
    
    @Override
	public void onDestroy() {
		super.onDestroy();
		ActivaMap.finished = true;
		this.finish();
	}
   
    public void drawMyPosition() {
		List<Overlay> mapOverlays = mapView.getOverlays();
		myLocationOverlay = new  MyLocationOverlay(this, mapView);
		if (myLocationOverlay.enableMyLocation()) {
			mapOverlays.add(myLocationOverlay);
		}	
    }
    
    public void drawMyPositionForFirstTime() {
		List<Overlay> mapOverlays = mapView.getOverlays();
		myLocationOverlay = new  FixedMyLocationOverlay(this, mapView);
		if (myLocationOverlay.enableMyLocation()) {
			myLocationOverlay.runOnFirstFix(new  Runnable(){
				public void  run(){
					mapView.getController().animateTo(myLocationOverlay.getMyLocation());
	            }
			});
			mapOverlays.add(myLocationOverlay);
		}	
    }
    
    public boolean onOptionsItemSelected(MenuItem item) {
    	if (Activa.myApp.refreshing) {
			Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.MAIN_REFRESHING);
			return true;
		}
		else if (Activa.myApp.refreshing_foreground) {
			((TextView) Activa.myApp.findViewById(R.id.popupText)).setText(Activa.myLanguageManager.MAIN_REFRESHING);
			return true;
		}
        switch (item.getItemId()) {
	        case R.id.tocreatesite:
	            addMarks();
	            return true;
	        case R.id.toseachsite:
	            searchMarks();
	            return true;
	        case R.id.toremovesite:
	            removeMarks();
	            return true;
	        default:
	            return false;
        }
    }
    
    public boolean onCreateOptionsMenu(Menu menu) {
    	MenuInflater inflater = getMenuInflater();
    	inflater.inflate(R.menu.map, menu);
        return true;
    }
    
    public void refreshMyMarks() {
		List<Overlay> mapOverlays = mapView.getOverlays();
    	Enumeration<Institution> en = Activa.myMapManager.institutions.elements();
    	mapOverlays.add(myLocationOverlay);
		while (en.hasMoreElements()) {
			Institution currentMark = en.nextElement();
			Drawable icon = ActivaMap.mapContext.getResources().getDrawable(R.drawable.position);
	        ActivaItemizedOverlay itemizedoverlay = new ActivaItemizedOverlay(icon, mapContext);
			GeoPoint myPosition = new GeoPoint((int)(currentMark.getLatitude()*1000000),(int)(currentMark.getLongitude()*1000000));
			String spinnet = currentMark.getAddress() + "\n";
			if ((currentMark.getPhone() != null)&&(currentMark.getPhone().equalsIgnoreCase("null"))) spinnet += currentMark.getPhone() + "\n"; 
			if ((currentMark.getEmail() != null)&&(currentMark.getEmail().equalsIgnoreCase("null"))) spinnet += currentMark.getEmail() + "\n"; 
			OverlayItem overlayitem = new OverlayItem(myPosition, currentMark.getName(), spinnet);		
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
    
    public void addMarks() {
		LayoutInflater inflater = (LayoutInflater) myMap.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		final View layout = inflater.inflate(R.layout.toastaddmark,
		                               (ViewGroup) myMap.findViewById(R.id.toastaddmarkroot));
		TextView title = (TextView) layout.findViewById(R.id.addtitle);
		title.setText(Activa.myLanguageManager.MAP_ADDTITLE);
		TextView namereq = (TextView) layout.findViewById(R.id.namerequested);
		namereq.setText(Activa.myLanguageManager.MAP_NAME);
		TextView descreq = (TextView) layout.findViewById(R.id.descrequest);
		descreq.setText(Activa.myLanguageManager.MAP_DESC);
		TextView addressreq = (TextView) layout.findViewById(R.id.addressrequest);
		addressreq.setText(Activa.myLanguageManager.MAP_ADDRESS);
		TextView mobilereq = (TextView) layout.findViewById(R.id.phonerequest);
		mobilereq.setText(Activa.myLanguageManager.MAP_PHONE);
		TextView emailreq = (TextView) layout.findViewById(R.id.emailrequest);
		emailreq.setText(Activa.myLanguageManager.MAP_EMAIL);
		ImageButton image = (ImageButton) layout.findViewById(R.id.addeventok);
		Builder builder = new AlertDialog.Builder(myMap);
		builder.setView(layout);
		final AlertDialog alertDialog = builder.create();
		alertDialog.show();
		image.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				final Institution site = new Institution();
				site.setName(((EditText)layout.findViewById(R.id.name)).getText().toString());
				site.setDescription(((EditText)layout.findViewById(R.id.description)).getText().toString());
				site.setAddress(((EditText)layout.findViewById(R.id.address)).getText().toString());
				site.setEmail(((EditText)layout.findViewById(R.id.email)).getText().toString());
				site.setPhone(((EditText)layout.findViewById(R.id.phone)).getText().toString());
				alertDialog.cancel();
				final ProgressDialog dialog = ProgressDialog.show(myMap, Activa.myLanguageManager.MAP_ADDTITLE, 
						"Loading addresses ... ", true);
				dialog.show();
				Runnable run =  new Runnable() {
					List<Address> addresses;
					Address selected;
					@Override
					public void run() {
						Geocoder geocoder = new Geocoder(myMap);
						try {
							addresses = geocoder.getFromLocationName(site.getAddress(), 10);
						} catch (IOException e) {
							e.printStackTrace();
							handler.sendEmptyMessage(2);
							return;
						}
						dialog.cancel();
						if (addresses == null) {
							handler.sendEmptyMessage(0);
						} else if (addresses.size() == 0) {
							handler.sendEmptyMessage(1);
						} else {
							handler.sendEmptyMessage(2);
						}
					}
					private Handler handler = new Handler() {
						@Override
						public void handleMessage(Message msg) {
							switch (msg.what) {
								case 0:
									Toast toast = Toast.makeText(myMap, Activa.myLanguageManager.CONNECTION_FAILED, Toast.LENGTH_SHORT);
									toast.show();
									break;
								case 1:
									Toast toast2 = Toast.makeText(myMap, Activa.myLanguageManager.NOTIFICATION_SEARCH_NOTFOUND, Toast.LENGTH_SHORT);
									toast2.show();
									break;
								case 2:
									final String[] items = new String[addresses.size()];
									for (int i = 0; i < addresses.size(); i++) {
										items[i] = "";
										String line = addresses.get(i).getFeatureName();
										if (line != null) items[i] += line + ", ";
										int j = 0;
										line = addresses.get(i).getAddressLine(j);
										while (line != null) {
											items[i] += line + " ";
											j++;
											line = addresses.get(i).getAddressLine(j);
										}
										line = addresses.get(i).getLocality();
										if (line != null) items[i] += line + ", ";
										line = addresses.get(i).getAdminArea();
										if (line != null) items[i] += line + ", ";
										line = addresses.get(i).getCountryName();
										if (line != null) items[i] += line + ", ";
										if (items[i].length() > 0) items[i].substring(0, items[i].length() - 1);
									}
									AlertDialog.Builder builder = new AlertDialog.Builder(myMap);
									builder.setTitle(Activa.myLanguageManager.NOTIFICATION_SEARCH_ADD);
									selected = addresses.get(0);
									builder.setSingleChoiceItems(items, 0, new android.content.DialogInterface.OnClickListener() {									
										@Override
										public void onClick(DialogInterface dialog, int which) {
											selected = addresses.get(which);
											
										}
									});
									builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
								           public void onClick(DialogInterface dialog, int id) {
								        	   if (selected != null) {
								        		   site.setLatitude(selected.getLatitude());
								        		   site.setLongitude(selected.getLongitude());
								        		   Activa.myMapManager.createMapSite(site);
								        	   }
								               dialog.cancel();
								               updateOverlays();
								           }
								    });
									AlertDialog alert = builder.create();
									alert.show();
									break;
							}
						}
	
					};
				};
				Thread thread = new Thread(run);
				thread.start();
			}
		});
	}

	public void removeMarks() {
		int i = 0;
		final CharSequence[] items = new CharSequence[Activa.myMapManager.institutions.size()];
		final Institution[] sites = new Institution[Activa.myMapManager.institutions.size()];
		final Array<Institution>sitesToRem = new Array<Institution>();
		Enumeration<Institution> elements = Activa.myMapManager.institutions.elements();
		while (elements.hasMoreElements()) {
			Institution site = elements.nextElement();
			items[i] = site.getName();
			sites[i] = site;
			i++;
		}
		AlertDialog.Builder builder = new AlertDialog.Builder(myMap);
		builder.setTitle(Activa.myLanguageManager.MAP_UNLINK);
		builder.setMultiChoiceItems(items, null, new OnMultiChoiceClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which, boolean isChecked) {
				if (isChecked) sitesToRem.add(sites[which]);
				else sitesToRem.remove(sites[which]);
			}
		});
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
	           public void onClick(DialogInterface dialog, int id) {
	        	   for (int i = 0; i < sitesToRem.size(); i++) {
	        		   Activa.myMapManager.removeMapSite(sitesToRem.get(i));
	        	   }
	               dialog.cancel();
	               updateOverlays();
	           }
	    });
		AlertDialog alert = builder.create();
		alert.show();
	}
	
	public void searchMarks() {
		LayoutInflater inflater = (LayoutInflater) myMap.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		final View layout = inflater.inflate(R.layout.toastsearch,
		                               (ViewGroup) myMap.findViewById(R.id.toastsearchroot));
		TextView text = (TextView) layout.findViewById(R.id.searchexpl);
		text.setText(Activa.myLanguageManager.MAP_SEARCH);
		ImageButton image = (ImageButton) layout.findViewById(R.id.searchok);
		Builder builder = new AlertDialog.Builder(myMap);
		builder.setView(layout);
		final AlertDialog alertDialog = builder.create();
		alertDialog.show();
		image.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				final String query = ((EditText)layout.findViewById(R.id.searchtext)).getText().toString();
				alertDialog.cancel();
				final ProgressDialog dialog = ProgressDialog.show(myMap, Activa.myLanguageManager.MAP_ADDTITLE, 
						"Loading sites ...", true);
				dialog.show();
				Runnable run =  new Runnable() {
					Array<Institution> sites;
					@Override
					public void run() {
						sites = Activa.myMapManager.searchMapSite(query);
						dialog.cancel();
						if (sites == null) {
							handler.sendEmptyMessage(0);
						} else if (sites.size() == 0) {
							handler.sendEmptyMessage(1);
						} else {
							handler.sendEmptyMessage(2);
						}
					}
					private Handler handler = new Handler() {
						@Override
						public void handleMessage(Message msg) {
							switch (msg.what) {
								case 0:
									Toast toast = Toast.makeText(myMap, Activa.myLanguageManager.CONNECTION_FAILED, Toast.LENGTH_SHORT);
									toast.show();
									break;
								case 1:
									Toast toast2 = Toast.makeText(myMap, "No sites were found.", Toast.LENGTH_SHORT);
									toast2.show();
									break;
								case 2:
									final CharSequence[] items = new CharSequence[sites.size()];
									final Array<Institution> sitesToAdd = new Array<Institution>();
									for (int i = 0; i < sites.size(); i++) {
										items[i] = sites.get(i).getName() + "\n" + sites.get(i).getAddress();
									}
									AlertDialog.Builder builder = new AlertDialog.Builder(myMap);
									builder.setTitle(Activa.myLanguageManager.NOTIFICATION_QUEST_ADD);
									builder.setMultiChoiceItems(items, null, new OnMultiChoiceClickListener() {
										@Override
										public void onClick(DialogInterface dialog, int which, boolean isChecked) {
											if (isChecked) sitesToAdd.add(sites.get(which));
											else sitesToAdd.remove(sites.get(which));
										}
									});
									builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
								           public void onClick(DialogInterface dialog, int id) {
								        	   for (int i = 0; i < sitesToAdd.size(); i++) {
								        		   Activa.myMapManager.addMapSite(sitesToAdd.get(i));
								        	   }
								               dialog.cancel();
								               updateOverlays();
								           }
								    });
									AlertDialog alert = builder.create();
									alert.show();
									break;
							}
						}
					};
				};
				Thread thread = new Thread(run);
				thread.start();
			}
		});
	}
	
}
