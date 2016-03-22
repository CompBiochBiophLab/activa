package com.o2hlink.zonated;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.o2hlink.zonated.background.MainThread;
import com.o2hlink.zonated.connection.ProtocolManager;
import com.o2hlink.zonated.data.PendingDataManager;
import com.o2hlink.zonated.data.contacts.ContactsManager;
import com.o2hlink.zonated.data.questionnaire.QuestControlManager;
import com.o2hlink.zonated.mobile.MobileManager;
import com.o2hlink.zonated.ui.UIManager;
import com.o2hlink.zonated.ui.i18n.Resource;
import com.o2hlink.zonated.ui.i18n.ResourceEnglish;
import com.o2hlink.zonated.ui.i18n.ResourceSpanish;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.AlertDialog.Builder;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class Zonated extends Activity {
	public boolean refreshing = false;
	
	public boolean refreshing_foreground = false;
	
	public static final boolean DEBUG_ALL = false;
	
	public static final boolean DEBUG = Zonated.DEBUG_ALL | false;
    
    public static Zonated myApp;
	
	public static Resource myLanguageManager;
    
    public static TelephonyManager myTelephonyManager;
    
    public static MobileManager myMobileManager;
    
    public static ProtocolManager myProtocolManager;
    
    public static ContactsManager myContactsManager;
    
    public static UIManager myUIManager;
    
    public static MenuInflater myInflater;

	public static Menu myMenu;
	
	public static int myTaskID;
	
	public static PendingDataManager myPendingDataManager;
	
	public static MainThread myMainBackgroundThread;
	
	public static QuestControlManager myQuestControlManager;

	public static File rootFile;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        myApp = this;
        myTaskID = this.getTaskId();
        myUIManager = UIManager.getInstance();
        myUIManager.UIlogin.loadInitScreen();
    }
    
    @Override
    public void onResume() {
    	super.onResume();
//		refreshScreen();
    }

    @Override
    public void onDestroy() {
    	try {
    		if (Zonated.myMainBackgroundThread.running) Zonated.myMainBackgroundThread.stop();
        	if (myMobileManager.identified) myMobileManager.addUserWithPassword(myMobileManager.user);
        	ProtocolManager.freeInstance();
        	MobileManager.freeInstance();
        	UIManager.freeInstance();
    	} catch (Exception e) {
			e.printStackTrace();
		}
		super.onDestroy();
		this.finish();
	}
    
    @Override
    public void onRestart() {
    	super.onRestart();
    	super.onWindowFocusChanged(true);
    }
    
    @Override
    public void onLowMemory() {
    	return;
    }

    @Override
	public void onBackPressed() {
    	try {
	    	if ((myUIManager.state == UIManager.UI_STATE_LOGIN)||
	    			(myUIManager.state == UIManager.UI_STATE_TOTALQUESTIONNAIRE)||
	    			(myUIManager.state == UIManager.UI_STATE_SUBENVIRONMENT)||
	    			(myUIManager.state == UIManager.UI_STATE_USERINFO)) {
	    		AlertDialog.Builder builder = new AlertDialog.Builder(Zonated.myApp);
				builder.setMessage(Zonated.myLanguageManager.MAIN_QUIT)
				       .setCancelable(false)
				       .setPositiveButton(Zonated.myLanguageManager.MAIN_QUIT_YES, new DialogInterface.OnClickListener() {
				           public void onClick(DialogInterface dialog, int id) {
								Zonated.myApp.onDestroy();
				           }
				       })
				       .setNegativeButton(Zonated.myLanguageManager.MAIN_QUIT_NO, new DialogInterface.OnClickListener() {
				           public void onClick(DialogInterface dialog, int id) {
				                dialog.cancel();
				           }
				       });
				AlertDialog alert = builder.create();
				alert.show();
	    	}
	    	else if (myUIManager.state == UIManager.UI_STATE_LOADING) return; 
	    	else if (myUIManager.state == UIManager.UI_STATE_REGISTERDATA) Zonated.myUIManager.UIlogin.loadLoginScreen();
	    	else if ((myUIManager.state == UIManager.UI_STATE_REGISTER)||(myUIManager.state == UIManager.UI_STATE_CHECKING)) Zonated.myUIManager.UIlogin.loadLoginScreen();
	    	else myUIManager.UIquest.loadSharedQuestionnaires();
    	} catch (Exception e) {
    		AlertDialog.Builder builder = new AlertDialog.Builder(Zonated.myApp);
			builder.setMessage(Zonated.myLanguageManager.MAIN_QUIT)
			       .setCancelable(false)
			       .setPositiveButton(Zonated.myLanguageManager.MAIN_QUIT_YES, new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {
							Zonated.myApp.onDestroy();
			           }
			       })
			       .setNegativeButton(Zonated.myLanguageManager.MAIN_QUIT_NO, new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {
			                dialog.cancel();
			           }
			       });
			AlertDialog alert = builder.create();
			alert.show();
		}
    }

    public boolean onOptionsItemSelected(MenuItem item) {
    	ImageView animationFrame;
    	final AnimationDrawable animation;
    	Runnable run;
    	Thread thread;
    	if (Zonated.myApp.refreshing) {
			Zonated.myUIManager.UImisc.loadInfoPopup(Zonated.myLanguageManager.MAIN_REFRESHING);
			return true;
		}
		else if (Zonated.myApp.refreshing_foreground) {
			((TextView) Zonated.myApp.findViewById(R.id.popupText)).setText(Zonated.myLanguageManager.MAIN_REFRESHING);
			return true;
		}
        switch (item.getItemId()) {
	        case R.id.too2hlink:
	            Zonated.myUIManager.UImisc.goToO2HLinkWebPage();
	            return true;
	        case R.id.tomobile:
	        	Intent in = new Intent(Intent.ACTION_VIEW);
				in.setData(Uri.parse(ZonatedConfig.ACTIV8YOUTUBE_URL));
				Zonated.myApp.startActivity(in);
	            return true;
	        case R.id.toconf:
	        	if (!Zonated.myMobileManager.identified) {
					Zonated.myUIManager.UImisc.loadInfoPopup(Zonated.myLanguageManager.MAIN_FORBIDDEN);
					return false;
				}
	            Zonated.myUIManager.UIoptions.showOptions();
	            return true;
	        case R.id.tocontact:
	            Zonated.myUIManager.UImisc.loadContactWithUs();
	            return true;
	        case R.id.toaboutus:
	            Zonated.myUIManager.UImisc.loadAboutUs();
	            return true;
	        }
	        return false;
    }
    
    public boolean onCreateOptionsMenu(Menu menu) {
    	Zonated.myMenu = menu;
    	Zonated.myInflater = getMenuInflater();
		Zonated.myInflater.inflate(R.menu.main, Zonated.myMenu);
        return true;
    }

//    public void onWindowsFocusChanged() {
//    	
//    }

	/**
	 * Returns the appropiate translations class <p>
	 * @param selectedLang	The param that states the selected Language
	 */
	public static Resource setLanguage(Locale locale) {
        String selectedLang = locale.getLanguage();
		if (selectedLang == null) {
			return new ResourceSpanish();
		}
		else {
			if(selectedLang.equals("es")) {
				return new ResourceSpanish();
			}
			else if(selectedLang.equals("en")) {
				return new ResourceEnglish();
			}
			else {
				//default is English
				return new ResourceEnglish();
			}
		}
	}
	
	public static void throwException(Exception ex) throws Exception {
		throw ex;
	}
	
	public Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			LayoutInflater inflater = (LayoutInflater) Zonated.myApp.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			final View layout = inflater.inflate(R.layout.toasttext,
			     (ViewGroup) Zonated.myApp.findViewById(R.id.toasttextroot));
			TextView textview = (TextView) layout.findViewById(R.id.searchexpl);
			textview.setText(msg.getData().getString("0"));
			Builder builder = new AlertDialog.Builder(Zonated.myApp);
			builder.setView(layout);
			AlertDialog alertDialog = builder.create();
			alertDialog.show();
		}
	};
}