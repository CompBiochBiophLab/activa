package com.o2hlink.healthgenius.ui;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageView.ScaleType;

import com.o2hlink.healthgenius.HealthGenius;
import com.o2hlink.healthgenius.R;
import com.o2hlink.healthgenius.exterior.ExternalApp;

public class UIManagerPrograms {
	
	public UIManager myUIManager;
	
	public UIManagerPrograms(UIManager ui) {
		myUIManager = ui;
	}
	
	/*public void loadLinksExtended(final Hashtable<Integer, ExternalApp> links, final String title, final String marketurl) {
		final ExternalApp[] items = new ExternalApp[links.size()];
		Enumeration<ExternalApp> apps = links.elements();
		int i = 0;
		while (apps.hasMoreElements()) {
			items[i] = apps.nextElement();
			i++;
		}
		ListAdapter adapter = new ArrayAdapter<ExternalApp>(HealthGenius.myApp,android.R.layout.select_dialog_item,
			    android.R.id.text1,items){
			public View getView(int position, View convertView, ViewGroup parent) {
				PackageManager manager = HealthGenius.myApp.getPackageManager();
				//User super class to create the View
			    View v = super.getView(position, convertView, parent);
			    TextView tv = (TextView)v.findViewById(android.R.id.text1);
			    ImageView image = new ImageView(HealthGenius.myApp);
			    //Put the image on the TextView
			    Drawable draw;
			    try {
			    	draw = manager.getActivityIcon(new ComponentName(items[position].packageName, items[position].activityName));
			    	image.setScaleType(ScaleType.FIT_XY);
				    image.setImageDrawable(manager.getActivityIcon(new ComponentName(items[position].packageName, items[position].activityName)));
				    image.setMaxWidth(45);
			    	image.setMaxHeight(45);
			    	image.setLayoutParams(new TableLayout.LayoutParams(45, 45));
			    } catch (NameNotFoundException e) {
			    	draw = null;
					image = null;
					e.printStackTrace();
				}
			    draw.setBounds(0, 0, 45, 45);
			    tv.setCompoundDrawables(draw, null, null, null);
			    //Add margin between image and text (support various screen densities)
			    int dp5 = (int) (5 * HealthGenius.myApp.getResources().getDisplayMetrics().density + 0.5f);
			    tv.setCompoundDrawablePadding(dp5);
			    return v;
			}
		};
		new AlertDialog.Builder(HealthGenius.myApp)
	    .setTitle(title)
	    .setAdapter(adapter, new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int item) {
	        	items[item].startApplication();
	        }
	    })
	    .setPositiveButton(HealthGenius.myApp.getResources().getString(R.string.addlink), new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				loadAddLinks(links, title, marketurl);
			}
		})
	    .setNegativeButton(HealthGenius.myApp.getResources().getString(R.string.removelink), new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				loadRemoveLinks(links, title, marketurl);
			}
		})
	    .show();
		Toast toast = Toast.makeText(HealthGenius.myApp, HealthGenius.myApp.getResources().getString(R.string.toastexternallinks), Toast.LENGTH_SHORT);
		toast.show();
	}*/
	
	public void loadAddLinks(final Hashtable<Integer, ExternalApp> links, final String title, final String marketurl) {
		final PackageManager manager = HealthGenius.myApp.getPackageManager();
		List<ResolveInfo> programs = HealthGenius.myExteriorManager.getExternalAppsList();
		final ExternalApp[] items = new ExternalApp[programs.size()];
		int i = 0;
		for (ResolveInfo resolveInfo : programs) {
			items[i] = new ExternalApp(resolveInfo, manager);
			i++;
		}
//		Hashtable<Integer, ExternalApp> appsToLink = new Hashtable<Integer, ExternalApp>();
		ListAdapter adapter = new ArrayAdapter<ExternalApp>(HealthGenius.myApp,android.R.layout.select_dialog_item,
			    android.R.id.text1,items){
			public View getView(int position, View convertView, ViewGroup parent) {
				//User super class to create the View
			    View v = super.getView(position, convertView, parent);
			    TextView tv = (TextView)v.findViewById(android.R.id.text1);
			    ImageView image = new ImageView(HealthGenius.myApp);
			    //Put the image on the TextView
			    Drawable draw;
			    try {
			    	draw = manager.getActivityIcon(new ComponentName(items[position].packageName, items[position].activityName));
			    	image.setScaleType(ScaleType.FIT_XY);
				    image.setImageDrawable(manager.getActivityIcon(new ComponentName(items[position].packageName, items[position].activityName)));
				    image.setMaxWidth(45);
			    	image.setMaxHeight(45);
			    	image.setLayoutParams(new TableLayout.LayoutParams(45, 45));
			    } catch (NameNotFoundException e) {
			    	draw = null;
					image = null;
					e.printStackTrace();
				}
			    draw.setBounds(0, 0, 45, 45);
			    tv.setCompoundDrawables(draw, null, null, null);
			    //Add margin between image and text (support various screen densities)
			    int dp5 = (int) (5 * HealthGenius.myApp.getResources().getDisplayMetrics().density + 0.5f);
			    tv.setCompoundDrawablePadding(dp5);
			    return v;
			}
		};
		new AlertDialog.Builder(HealthGenius.myApp)
	    .setTitle(HealthGenius.myLanguageManager.MAIN_SELECT_EXTERNALPROG)
	    .setAdapter(adapter, new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int item) {
	        	if (HealthGenius.myExteriorManager.externalApplicationsLinked.size() < 16) {
		        	links.put(HealthGenius.myExteriorManager.getFreeLinkId(links), items[item]);
					HealthGenius.myExteriorManager.savePrograms();
					Toast toast = Toast.makeText(HealthGenius.myApp, HealthGenius.myLanguageManager.MAIN_SELECT_PROGLINKED, 5000);
					toast.show();
			    	loadAddLinks(links, title, marketurl);
	        	}
	        	else {
	        		Toast toast = Toast.makeText(HealthGenius.myApp, HealthGenius.myLanguageManager.TEXT_MAXPROGRAMS, 5000);
					toast.show();
					myUIManager.loadBoxOpen();
	        	}
	        }
	    })
	    .setPositiveButton(HealthGenius.myApp.getResources().getString(R.string.oklink), new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				myUIManager.loadBoxOpen();
			}
		})
	    .setNegativeButton(HealthGenius.myApp.getResources().getString(R.string.tomarketlink), new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				try {
					if (marketurl.equalsIgnoreCase("")) {
						Intent intent = new Intent(Intent.ACTION_MAIN);
				        intent.addCategory(Intent.CATEGORY_LAUNCHER);
				        intent.setComponent(new ComponentName("com.android.vending", "com.android.vending.AssetBrowserActivity"));
				        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
						HealthGenius.myApp.startActivity(intent);	
					}
					else HealthGenius.myApp.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://market.android.com/apps/" + marketurl)));
			    } catch (Exception e) {
					e.printStackTrace();
				}
//				loadAddLinks(links, title, marketurl);
			}
		})
		.setOnCancelListener(new OnCancelListener() {
			@Override
			public void onCancel(DialogInterface dialog) {
				myUIManager.loadBoxOpen();
			}
		})
	    .show();
	}
	
	public void loadRemoveLinks(final Hashtable<Integer, ExternalApp> links, final String title, final String marketurl) {
		final CharSequence[] items = new CharSequence[links.size()];
		final Integer[] proglist = new Integer[links.size()];
		final ArrayList<Integer> progsToRemove = new ArrayList<Integer>();
		Enumeration<Integer> progenum = links.keys();
		int i = 0;
		while (progenum.hasMoreElements()) {
			int progid = progenum.nextElement();
			items[i] = links.get(progid).name;
			proglist[i] = progid;
			i++;
		}
		AlertDialog.Builder builder = new AlertDialog.Builder(HealthGenius.myApp);
		builder.setTitle(HealthGenius.myApp.getResources().getString(R.string.removelink));
		builder.setMultiChoiceItems(items, null, new OnMultiChoiceClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which, boolean isChecked) {
				if (isChecked) progsToRemove.add(proglist[which]);
				else progsToRemove.remove(proglist[which]);
			}
		});
		builder.setPositiveButton(HealthGenius.myApp.getResources().getString(R.string.oklink), new DialogInterface.OnClickListener() {
	           public void onClick(DialogInterface dialog, int id) {
	        	   for (int i = 0; i < progsToRemove.size(); i++) {
	        		   links.remove(progsToRemove.get(i));
	        	   }
	        	   HealthGenius.myExteriorManager.savePrograms();
	        	   myUIManager.loadBoxOpen();
	           }
	    });
		builder.setOnCancelListener(new OnCancelListener() {
			@Override
			public void onCancel(DialogInterface dialog) {
				myUIManager.loadBoxOpen();
			}
		});
		AlertDialog alert = builder.create();
		alert.show();
	}

}
