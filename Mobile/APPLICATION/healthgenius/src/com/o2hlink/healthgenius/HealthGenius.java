package com.o2hlink.healthgenius;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.o2hlink.healthgenius.background.MainThread;
import com.o2hlink.healthgenius.background.NotificationThread;
import com.o2hlink.healthgenius.connection.ProtocolManager;
import com.o2hlink.healthgenius.data.PendingDataManager;
import com.o2hlink.healthgenius.data.calendar.CalendarManager;
import com.o2hlink.healthgenius.data.contacts.ContactsManager;
import com.o2hlink.healthgenius.data.questionnaire.control.QuestControlManager;
import com.o2hlink.healthgenius.data.sensor.PedometerCalibration;
import com.o2hlink.healthgenius.data.sensor.SensorManager;
import com.o2hlink.healthgenius.exterior.ExteriorManager;
import com.o2hlink.healthgenius.mobile.MobileManager;
import com.o2hlink.healthgenius.patient.PatientManager;
import com.o2hlink.healthgenius.ui.UIManager;
import com.o2hlink.healthgenius.ui.i18n.Resource;
import com.o2hlink.healthgenius.ui.i18n.ResourceEnglish;
import com.o2hlink.healthgenius.ui.i18n.ResourceSpanish;

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

public class HealthGenius extends Activity {
	public boolean refreshing = false;
	
	public boolean refreshing_foreground = false;
	
	public static final boolean DEBUG_ALL = false;
	
	public static final boolean DEBUG = HealthGenius.DEBUG_ALL | false;
    
    public static HealthGenius myApp;
	
	public static Resource myLanguageManager;
    
    public static TelephonyManager myTelephonyManager;
    
    public static MobileManager myMobileManager;
    
    public static ProtocolManager myProtocolManager;
    
    public static ContactsManager myContactsManager;
    
    public static UIManager myUIManager;
    
    public static CalendarManager myCalendarManager;

    public static SensorManager mySensorManager;
    
    public static MenuInflater myInflater;

	public static Menu myMenu;
	
	public static AlarmManager myAlarmManager;
	
	public static int myTaskID;
	
	public static NotificationManager myNotificationManager;
	
	public static PendingDataManager myPendingDataManager;
	
	public static MainThread myMainBackgroundThread;
	
	public static NotificationThread myNotificationThread;
	
	public static BluetoothAdapter myBluetoothAdapter;
	
	public static android.hardware.SensorManager myAccelerometerManager;
	
	public static ExteriorManager myExteriorManager;
	
	public static AudioManager myAudioManager;
	
	public static PatientManager myPatientManager;
	
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
    		if (HealthGenius.myMainBackgroundThread.running) HealthGenius.myMainBackgroundThread.stop();
        	if (HealthGenius.myNotificationThread.running) HealthGenius.myNotificationThread.stop();
        	if (myMobileManager.identified) myMobileManager.addUserWithPassword(myMobileManager.user);
        	CalendarManager.freeInstance();
        	ProtocolManager.freeInstance();
        	SensorManager.freeInstance();
        	MobileManager.freeInstance();
        	UIManager.freeInstance();
        	SensorManager.freeInstance();
        	ExteriorManager.freeInstance();
        	PatientManager.freeInstance();
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
	    			(myUIManager.state == UIManager.UI_STATE_MAIN)||
	    			(myUIManager.state == UIManager.UI_STATE_SUBENVIRONMENT)||
	    			(myUIManager.state == UIManager.UI_STATE_USERINFO)) {
	    		AlertDialog.Builder builder = new AlertDialog.Builder(HealthGenius.myApp);
				builder.setMessage(HealthGenius.myLanguageManager.MAIN_QUIT)
				       .setCancelable(false)
				       .setPositiveButton(HealthGenius.myLanguageManager.MAIN_QUIT_YES, new DialogInterface.OnClickListener() {
				           public void onClick(DialogInterface dialog, int id) {
								HealthGenius.myApp.onDestroy();
				           }
				       })
				       .setNegativeButton(HealthGenius.myLanguageManager.MAIN_QUIT_NO, new DialogInterface.OnClickListener() {
				           public void onClick(DialogInterface dialog, int id) {
				                dialog.cancel();
				           }
				       });
				AlertDialog alert = builder.create();
				alert.show();
	    	}
	    	else if (myUIManager.state == UIManager.UI_STATE_LOADING) return; 
	    	else if (myUIManager.state == UIManager.UI_STATE_REGISTERDATA) HealthGenius.myUIManager.UIlogin.loadUserTypeChoiceScreen(-1);
	    	else if ((myUIManager.state == UIManager.UI_STATE_REGISTER)||(myUIManager.state == UIManager.UI_STATE_CHECKING)) HealthGenius.myUIManager.UIlogin.loadLoginScreen();
	    	else if (myUIManager.state == UIManager.UI_STATE_OPTIONS) myUIManager.loadBoxClosed(false, true);
	    	else myUIManager.loadBoxOpen();
    	} catch (Exception e) {
    		AlertDialog.Builder builder = new AlertDialog.Builder(HealthGenius.myApp);
			builder.setMessage(HealthGenius.myLanguageManager.MAIN_QUIT)
			       .setCancelable(false)
			       .setPositiveButton(HealthGenius.myLanguageManager.MAIN_QUIT_YES, new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {
							HealthGenius.myApp.onDestroy();
			           }
			       })
			       .setNegativeButton(HealthGenius.myLanguageManager.MAIN_QUIT_NO, new DialogInterface.OnClickListener() {
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
    	if (HealthGenius.myApp.refreshing) {
			HealthGenius.myUIManager.UImisc.loadInfoPopup(HealthGenius.myLanguageManager.MAIN_REFRESHING);
			return true;
		}
		else if (HealthGenius.myApp.refreshing_foreground) {
			((TextView) HealthGenius.myApp.findViewById(R.id.popupText)).setText(HealthGenius.myLanguageManager.MAIN_REFRESHING);
			return true;
		}
        switch (item.getItemId()) {
	        case R.id.too2hlink:
	            HealthGenius.myUIManager.UImisc.goToO2HLinkWebPage();
	            return true;
	        case R.id.tomobile:
	        	Intent in = new Intent(Intent.ACTION_VIEW);
				in.setData(Uri.parse(HealthGeniusConfig.ACTIV8YOUTUBE_URL));
				HealthGenius.myApp.startActivity(in);
	            return true;
	        case R.id.toconf:
	        	if (!HealthGenius.myMobileManager.identified) {
					HealthGenius.myUIManager.UImisc.loadInfoPopup(HealthGenius.myLanguageManager.MAIN_FORBIDDEN);
					return false;
				}
	            HealthGenius.myUIManager.UIoptions.showOptions();
	            return true;
	        case R.id.tocontact:
	            HealthGenius.myUIManager.UImisc.loadContactWithUs();
	            return true;
	        case R.id.toaboutus:
	            HealthGenius.myUIManager.UImisc.loadAboutUs();
	            return true;
	        case R.id.toautocalibration:
	            (new PedometerCalibration()).startCalibration();
	            return true;
	        case R.id.todayforpatientmeas:
				animationFrame = (ImageView) HealthGenius.myApp.findViewById(R.id.animation);
				animationFrame.setVisibility(View.VISIBLE);
				animationFrame.setBackgroundResource(R.drawable.loading);
				animation = (AnimationDrawable) animationFrame.getBackground();
				animation.start();
				((FrameLayout)HealthGenius.myApp.findViewById(R.id.layout)).invalidate();
				run = new Runnable() {
					@Override
					public void run() {
						handler.sendEmptyMessage(0);
					}
					private Handler handler = new Handler() {
						@Override
						public void handleMessage(Message msg) {
							switch (msg.what) {
								case 0:
									Date start = new Date();
									start.setTime((start.getTime()/86400000)*86400000);
									Date end = new Date(start.getTime());
									end.setDate(start.getDate() + 1);
									HealthGenius.myProtocolManager.getMeasuringEvents(HealthGenius.myPatientManager.currentPat.getId(), start, end);
						        	HealthGenius.myUIManager.UIpatient.loadScheduleDayForPatientMeas(new Date());
									break;
							}
						}
	
					};
				};
				thread = new Thread(run);
				thread.start();
	            return true;
	        case R.id.todayforpatientquest:
				animationFrame = (ImageView) HealthGenius.myApp.findViewById(R.id.animation);
				animationFrame.setVisibility(View.VISIBLE);
				animationFrame.setBackgroundResource(R.drawable.loading);
				animation = (AnimationDrawable) animationFrame.getBackground();
				animation.start();
				((FrameLayout)HealthGenius.myApp.findViewById(R.id.layout)).invalidate();
				run = new Runnable() {
					@Override
					public void run() {
						handler.sendEmptyMessage(0);
					}
					private Handler handler = new Handler() {
						@Override
						public void handleMessage(Message msg) {
							switch (msg.what) {
								case 0:
									Date start = new Date();
									start.setTime((start.getTime()/86400000)*86400000);
									Date end = new Date(start.getTime());
									end.setDate(start.getDate() + 1);
									HealthGenius.myProtocolManager.getMeasuringEvents(HealthGenius.myPatientManager.currentPat.getId(), start, end);
						        	HealthGenius.myUIManager.UIpatient.loadScheduleDayForPatientQuest(new Date());
									break;
							}
						}
	
					};
				};
				thread = new Thread(run);
				thread.start();
	            return true;
	        case R.id.toweekforpatientmeas:
				animationFrame = (ImageView) HealthGenius.myApp.findViewById(R.id.animation);
				animationFrame.setVisibility(View.VISIBLE);
				animationFrame.setBackgroundResource(R.drawable.loading);
				animation = (AnimationDrawable) animationFrame.getBackground();
				animation.start();
				run = new Runnable() {
					@Override
					public void run() {
						handler.sendEmptyMessage(0);
					}
					private Handler handler = new Handler() {
						@Override
						public void handleMessage(Message msg) {
							switch (msg.what) {
								case 0:
									int day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)-2;
									if (day == -1) day = 6;
									Date start = new Date();
									start.setDate(start.getDate() - day);
									Date end = new Date(start.getTime());
									end.setDate(end.getDate() + 7);
									HealthGenius.myProtocolManager.getMeasuringEvents(HealthGenius.myPatientManager.currentPat.getId(), start, end);
						        	HealthGenius.myUIManager.UIpatient.loadScheduleWeekForPatientMeas(new Date());
									break;
							}
						}
	
					};
				};
				thread = new Thread(run);
				thread.start();
	            return true;
	        case R.id.toweekforpatientquest:
				animationFrame = (ImageView) HealthGenius.myApp.findViewById(R.id.animation);
				animationFrame.setVisibility(View.VISIBLE);
				animationFrame.setBackgroundResource(R.drawable.loading);
				animation = (AnimationDrawable) animationFrame.getBackground();
				animation.start();
				run = new Runnable() {
					@Override
					public void run() {
						handler.sendEmptyMessage(0);
					}
					private Handler handler = new Handler() {
						@Override
						public void handleMessage(Message msg) {
							switch (msg.what) {
								case 0:
									int day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)-2;
									if (day == -1) day = 6;
									Date start = new Date();
									start.setDate(start.getDate() - day);
									Date end = new Date(start.getTime());
									end.setDate(end.getDate() + 7);
									HealthGenius.myProtocolManager.getQuestEvents(HealthGenius.myPatientManager.currentPat.getId(), start, end);
						        	HealthGenius.myUIManager.UIpatient.loadScheduleWeekForPatientQuest(new Date());
									break;
							}
						}
	
					};
				};
				thread = new Thread(run);
				thread.start();
	            return true;
	        case R.id.tomonthforpatientmeas:
	        	animationFrame = (ImageView) HealthGenius.myApp.findViewById(R.id.animation);
				animationFrame.setVisibility(View.VISIBLE);
				animationFrame.setBackgroundResource(R.drawable.loading);
				animation = (AnimationDrawable) animationFrame.getBackground();
				animation.start();
				run = new Runnable() {
					@Override
					public void run() {
						handler.sendEmptyMessage(0);
					}
					private Handler handler = new Handler() {
						@Override
						public void handleMessage(Message msg) {
							switch (msg.what) {
								case 0:
									Date start = new Date();
									start.setTime((start.getTime()/86400000)*86400000);
									start.setDate(1);
									Date end = new Date(start.getTime());
									end.setMonth(end.getMonth() + 1);
									HealthGenius.myProtocolManager.getMeasuringEvents(HealthGenius.myPatientManager.currentPat.getId(), start, end);
						        	HealthGenius.myUIManager.UIpatient.loadScheduleMonthForPatientMeas(new Date());
									break;
							}
						}
	
					};
				};
				thread = new Thread(run);
				thread.start();
	            return true;
	        case R.id.tomonthforpatientquest:
	        	animationFrame = (ImageView) HealthGenius.myApp.findViewById(R.id.animation);
				animationFrame.setVisibility(View.VISIBLE);
				animationFrame.setBackgroundResource(R.drawable.loading);
				animation = (AnimationDrawable) animationFrame.getBackground();
				animation.start();
				run = new Runnable() {
					@Override
					public void run() {
						handler.sendEmptyMessage(0);
					}
					private Handler handler = new Handler() {
						@Override
						public void handleMessage(Message msg) {
							switch (msg.what) {
								case 0:
									Date start = new Date();
									start.setTime((start.getTime()/86400000)*86400000);
									start.setDate(1);
									Date end = new Date(start.getTime());
									end.setMonth(end.getMonth() + 1);
									HealthGenius.myProtocolManager.getQuestEvents(HealthGenius.myPatientManager.currentPat.getId(), start, end);
						        	HealthGenius.myUIManager.UIpatient.loadScheduleMonthForPatientQuest(new Date());
									break;
							}
						}
	
					};
				};
				thread = new Thread(run);
				thread.start();
	            return true;
	        case R.id.tomanualcalibration:
	        	final CharSequence[] items = {HealthGenius.myLanguageManager.SENSORS_CALIBRATION_SELECT_ULTRAHIGH,
	        			HealthGenius.myLanguageManager.SENSORS_CALIBRATION_SELECT_VERYHIGH,
	        			HealthGenius.myLanguageManager.SENSORS_CALIBRATION_SELECT_HIGH,
	        			HealthGenius.myLanguageManager.SENSORS_CALIBRATION_SELECT_NORMAL,
	        			HealthGenius.myLanguageManager.SENSORS_CALIBRATION_SELECT_LOW,
	        			HealthGenius.myLanguageManager.SENSORS_CALIBRATION_SELECT_VERYLOW,
	        			HealthGenius.myLanguageManager.SENSORS_CALIBRATION_SELECT_ULTRALOW};
				AlertDialog.Builder builder = new AlertDialog.Builder(HealthGenius.myApp);
				builder.setTitle(HealthGenius.myLanguageManager.SENSORS_CALIBRATION_SELECT);
				builder.setItems(items, new DialogInterface.OnClickListener() {
				    public void onClick(DialogInterface dialog, int item) {
				    	myMobileManager.pedometerCalValue = 30 + item*5;
						myMobileManager.user.pedometerCalValue = 30 + item*5;
				    }
				});
				AlertDialog alert = builder.create();
				alert.show();
				return true;
	        case R.id.today:
				animationFrame = (ImageView) HealthGenius.myApp.findViewById(R.id.animation);
				animationFrame.setVisibility(View.VISIBLE);
				animationFrame.setBackgroundResource(R.drawable.loading);
				animation = (AnimationDrawable) animationFrame.getBackground();
				animation.start();
				run = new Runnable() {
					@Override
					public void run() {
						handler.sendEmptyMessage(0);
					}
					private Handler handler = new Handler() {
						@Override
						public void handleMessage(Message msg) {
							switch (msg.what) {
								case 0:
									Date start = new Date();
									start.setTime((start.getTime()/86400000)*86400000);
									Date end = new Date(start.getTime());
									end.setDate(start.getDate() + 1);
									HealthGenius.myCalendarManager.getNonMeasuringEvents(start, end);
						            HealthGenius.myUIManager.UIcalendar.loadScheduleDay(new Date());
									break;
							}
						}
	
					};
				};
				thread = new Thread(run);
				thread.start();
	            return true;
	        case R.id.toweek:
				animationFrame = (ImageView) HealthGenius.myApp.findViewById(R.id.animation);
				animationFrame.setVisibility(View.VISIBLE);
				animationFrame.setBackgroundResource(R.drawable.loading);
				animation = (AnimationDrawable) animationFrame.getBackground();
				animation.start();
				run = new Runnable() {
					@Override
					public void run() {
						handler.sendEmptyMessage(0);
					}
					private Handler handler = new Handler() {
						@Override
						public void handleMessage(Message msg) {
							switch (msg.what) {
								case 0:
									int day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)-2;
									if (day == -1) day = 6;
									Date start = new Date();
									start.setDate(start.getDate() - day);
									Date end = new Date(start.getTime());
									end.setDate(end.getDate() + 7);
									HealthGenius.myCalendarManager.getNonMeasuringEvents(start, end);
						            HealthGenius.myUIManager.UIcalendar.loadScheduleWeek(new Date());
									break;
							}
						}
	
					};
				};
				thread = new Thread(run);
				thread.start();
	            return true;
	        case R.id.tomonth:
	        	animationFrame = (ImageView) HealthGenius.myApp.findViewById(R.id.animation);
				animationFrame.setVisibility(View.VISIBLE);
				animationFrame.setBackgroundResource(R.drawable.loading);
				animation = (AnimationDrawable) animationFrame.getBackground();
				animation.start();
				run = new Runnable() {
					@Override
					public void run() {
						handler.sendEmptyMessage(0);
					}
					private Handler handler = new Handler() {
						@Override
						public void handleMessage(Message msg) {
							switch (msg.what) {
								case 0:
									Date start = new Date();
									start.setTime((start.getTime()/86400000)*86400000);
									start.setDate(1);
									Date end = new Date(start.getTime());
									end.setMonth(end.getMonth() + 1);
									HealthGenius.myCalendarManager.getNonMeasuringEvents(start, end);
						            HealthGenius.myUIManager.UIcalendar.loadScheduleMonth(new Date());
									break;
							}
						}
	
					};
				};
				thread = new Thread(run);
				thread.start();
	            return true;
	        
	        }
	        return false;
    }
    
    public boolean onCreateOptionsMenu(Menu menu) {
    	HealthGenius.myMenu = menu;
    	HealthGenius.myInflater = getMenuInflater();
		switch (myUIManager.state) {
			case UIManager.UI_STATE_MAIN:
			case UIManager.UI_STATE_SUBENVIRONMENT:
				HealthGenius.myInflater.inflate(R.menu.main, HealthGenius.myMenu);
				break;
			case UIManager.UI_STATE_SCHEDULE:
				HealthGenius.myInflater.inflate(R.menu.schedule, HealthGenius.myMenu);
				break;
			case UIManager.UI_STATE_SCHEDULEWEEK:
				HealthGenius.myInflater.inflate(R.menu.schedule, HealthGenius.myMenu);
				break;
			case UIManager.UI_STATE_SCHEDULEMONTH:
				HealthGenius.myInflater.inflate(R.menu.schedule, HealthGenius.myMenu);
				break;
			case UIManager.UI_STATE_SCHEDULEFORPATIENTMEAS:
				HealthGenius.myInflater.inflate(R.menu.scheduleforpatientformeas, HealthGenius.myMenu);
				break;
			case UIManager.UI_STATE_SCHEDULEWEEKFORPATIENTMEAS:
				HealthGenius.myInflater.inflate(R.menu.scheduleforpatientformeas, HealthGenius.myMenu);
				break;
			case UIManager.UI_STATE_SCHEDULEMONTHFORPATIENTMEAS:
				HealthGenius.myInflater.inflate(R.menu.scheduleforpatientformeas, HealthGenius.myMenu);
				break;
			case UIManager.UI_STATE_SCHEDULEFORPATIENTQUEST:
				HealthGenius.myInflater.inflate(R.menu.scheduleforpatientforquest, HealthGenius.myMenu);
				break;
			case UIManager.UI_STATE_SCHEDULEWEEKFORPATIENTQUEST:
				HealthGenius.myInflater.inflate(R.menu.scheduleforpatientforquest, HealthGenius.myMenu);
				break;
			case UIManager.UI_STATE_SCHEDULEMONTHFORPATIENTQUEST:
				HealthGenius.myInflater.inflate(R.menu.scheduleforpatientforquest, HealthGenius.myMenu);
				break;
			case UIManager.UI_STATE_TOTALSENSOR:
				HealthGenius.myInflater.inflate(R.menu.sensors, HealthGenius.myMenu);
				break;
		}
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
			LayoutInflater inflater = (LayoutInflater) HealthGenius.myApp.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			final View layout = inflater.inflate(R.layout.toasttext,
			     (ViewGroup) HealthGenius.myApp.findViewById(R.id.toasttextroot));
			TextView textview = (TextView) layout.findViewById(R.id.searchexpl);
			textview.setText(msg.getData().getString("0"));
			Builder builder = new AlertDialog.Builder(HealthGenius.myApp);
			builder.setView(layout);
			AlertDialog alertDialog = builder.create();
			alertDialog.show();
		}
	};
}