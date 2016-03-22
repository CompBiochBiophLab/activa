package com.o2hlink.pimtools;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;

import com.o2hlink.pimtools.R;
import com.o2hlink.pimtools.background.DownloadFiles;
import com.o2hlink.pimtools.background.MainThread;
import com.o2hlink.pimtools.background.NotificationThread;
import com.o2hlink.pimtools.connection.ProtocolManager;
import com.o2hlink.pimtools.data.PendingDataManager;
import com.o2hlink.pimtools.data.calendar.CalendarManager;
import com.o2hlink.pimtools.data.message.MessageManager;
import com.o2hlink.pimtools.data.questionnaire.control.QuestControlManager;
import com.o2hlink.pimtools.data.sensor.PedometerCalibration;
import com.o2hlink.pimtools.data.sensor.SensorManager;
import com.o2hlink.pimtools.documents.DocumentsManager;
import com.o2hlink.pimtools.exceptions.NotUpdatedException;
import com.o2hlink.pimtools.exterior.ExteriorManager;
import com.o2hlink.pimtools.map.MapManager;
import com.o2hlink.pimtools.mobile.MobileManager;
import com.o2hlink.pimtools.news.NewsManager;
import com.o2hlink.pimtools.notes.NotesManager;
import com.o2hlink.pimtools.patient.PatientManager;
import com.o2hlink.pimtools.ui.UIManager;
import com.o2hlink.pimtools.ui.i18n.Resource;
import com.o2hlink.pimtools.ui.i18n.ResourceEnglish;
import com.o2hlink.pimtools.ui.i18n.ResourceSpanish;
import com.o2hlink.pimtools.ui.widget.Ambient;
import com.paypal.android.MEP.PayPal;
import com.paypal.android.MEP.PayPalActivity;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.AlertDialog.Builder;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
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

/**
 * @author Adrian Rejas<P>
 * 
 * 
 * This is the main class of the program. For the moment there aren't any structured program,
 * this is only for testing purposes.
 */

public class Activa extends Activity {
	
	public boolean portrait = true;
	
	public static ArrayList<String> ambientsdownloading;
	
	public boolean refreshing = false;
	
	public boolean refreshing_foreground = false;
	
	public static final boolean DEBUG_ALL = false;
	
	public static final boolean DEBUG = Activa.DEBUG_ALL | false;
    
    public static Activa myApp;
	
	public static Resource myLanguageManager;
    
    public static TelephonyManager myTelephonyManager;
    
    public static MobileManager myMobileManager;
    
    public static ProtocolManager myProtocolManager;
    
    public static UIManager myUIManager;
    
    public static CalendarManager myCalendarManager;

    public static SensorManager mySensorManager;
    
    public static MenuInflater myInflater;

	public static Menu myMenu;
	
	public static AlarmManager myAlarmManager;
	
	public static int myTaskID;
	
	public static NotificationManager myNotificationManager;
	
	public static PendingDataManager myPendingDataManager;
	
	public static MapManager myMapManager;
	
	public static MessageManager myMessageManager;
	
	public static NewsManager myNewsManager;
	
	public static NotesManager myNotesManager;
	
	public static MainThread myMainBackgroundThread;
	
	public static NotificationThread myNotificationThread;
	
	public static BluetoothAdapter myBluetoothAdapter;
	
	public static android.hardware.SensorManager myAccelerometerManager;
	
	public static ExteriorManager myExteriorManager;
	
	public static AudioManager myAudioManager;
	
	public static PatientManager myPatientManager;
	
	public static QuestControlManager myQuestControlManager;
	
	public static DocumentsManager myDocumentsManager;

	public static File rootFile;
	
	public static PayPal myPayPal;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	
        myApp = this;
        ambientsdownloading = new ArrayList<String>();
        myTaskID = this.getTaskId();
        myUIManager = UIManager.getInstance();
        myUIManager.loadInitScreen();
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) this.portrait = true;
		else this.portrait = false;
    }
    
    @Override
    public void onResume() {
    	super.onResume();
//		refreshScreen();
    }

    @Override
    public void onDestroy() {
    	try {
    		if (Activa.myMainBackgroundThread.running) Activa.myMainBackgroundThread.stop();
        	if (Activa.myNotificationThread.running) Activa.myNotificationThread.stop();
//        	myCalendarManager.saveCalendar();
        	if (myMobileManager.identified) myMobileManager.addUserWithPassword(myMobileManager.user);
//        	QuestManager.freeInstance();
        	CalendarManager.freeInstance();
        	MapManager.freeInstance();
        	ProtocolManager.freeInstance();
        	SensorManager.freeInstance();
        	MobileManager.freeInstance();
        	UIManager.freeInstance();
        	SensorManager.freeInstance();
        	MessageManager.freeInstance();
        	NewsManager.freeInstance();
        	NotesManager.freeInstance();
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
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) this.portrait = true;
		else this.portrait = false;			
//		refreshScreen();
	}

    @Override
	public void onBackPressed() {
    	try {
	    	if ((myUIManager.state == UIManager.UI_STATE_LOGIN)||
	    			(myUIManager.state == UIManager.UI_STATE_MAIN)||
	    			(myUIManager.state == UIManager.UI_STATE_SUBENVIRONMENT)||
	    			(myUIManager.state == UIManager.UI_STATE_USERINFO)) {
	    		AlertDialog.Builder builder = new AlertDialog.Builder(Activa.myApp);
				builder.setMessage(Activa.myLanguageManager.MAIN_QUIT)
				       .setCancelable(false)
				       .setPositiveButton(Activa.myLanguageManager.MAIN_QUIT_YES, new DialogInterface.OnClickListener() {
				           public void onClick(DialogInterface dialog, int id) {
								Activa.myApp.onDestroy();
				           }
				       })
				       .setNegativeButton(Activa.myLanguageManager.MAIN_QUIT_NO, new DialogInterface.OnClickListener() {
				           public void onClick(DialogInterface dialog, int id) {
				                dialog.cancel();
				           }
				       });
				AlertDialog alert = builder.create();
				alert.show();
	    	}
	    	else if (myUIManager.state == UIManager.UI_STATE_LOADING) return; 
	    	else if (myUIManager.state == UIManager.UI_STATE_REGISTERDATA) Activa.myUIManager.loadUserTypeChoiceScreen(-1);
	    	else if ((myUIManager.state == UIManager.UI_STATE_REGISTER)||(myUIManager.state == UIManager.UI_STATE_CHECKING)) Activa.myUIManager.loadLoginScreen();
	    	else myUIManager.loadGeneratedSubenvironment(myUIManager.lastSubenv, false);
    	} catch (Exception e) {
    		AlertDialog.Builder builder = new AlertDialog.Builder(Activa.myApp);
			builder.setMessage(Activa.myLanguageManager.MAIN_QUIT)
			       .setCancelable(false)
			       .setPositiveButton(Activa.myLanguageManager.MAIN_QUIT_YES, new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {
							Activa.myApp.onDestroy();
			           }
			       })
			       .setNegativeButton(Activa.myLanguageManager.MAIN_QUIT_NO, new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {
			                dialog.cancel();
			           }
			       });
			AlertDialog alert = builder.create();
			alert.show();
		}
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	super.onActivityResult(requestCode, resultCode, data);
    	if (requestCode == 220) {
    		Activa.myUIManager.loadGeneratedMainScreen(true, false, true);
    	}
    	else if (requestCode == 100) {
    		   switch(resultCode) {
    		   		case Activity.RESULT_OK:
//    		   			String payKey = data.getStringExtra(PayPalActivity.EXTRA_PAY_KEY);
						try {
							Activa.myProtocolManager.pursacheAmbients(Activa.myUIManager.ambientprocessing);
						} catch (NotUpdatedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						HashSet<String> files = Ambient.getFilesForDownloading(Activa.myUIManager.ambientprocessing.getUrlMobile());
						DownloadFiles run = new DownloadFiles(Activa.myUIManager.ambientname, Activa.myUIManager.ambientprocessing.getUrlMobile(), files);
						Thread thr = new Thread(run);
						thr.start();
						if (Activa.myUIManager.ambient != null) Activa.myUIManager.showAmbientsForLoading();
						else Activa.myUIManager.showAmbientsForLoading();
						Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.MAIN_PAYING_DOWNLOADING);
						break;
    		        case Activity.RESULT_CANCELED:
    		        	break;
    		        case PayPalActivity.RESULT_FAILURE:
    		        	String errorMessage = data.getStringExtra(PayPalActivity.EXTRA_ERROR_MESSAGE);
    		        	Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.MAIN_PAYING_FAIL + "\n\n" + errorMessage);
    		        	break;
    		  }
    	}
    	else switch (requestCode) {
			case ExteriorManager.INTENT_RESULT_IMAGESELECTED:
//				if (data != null) {
////			    	Intent intent = new Intent(Intent.ACTION_VIEW);
////				    intent.setData(data.getData());
////				    startActivityForResult(intent, ExteriorManager.INTENT_RESULT_IMAGELOADED);
//					com.o2hlink.activa.exterior.ImageViewer.image = data.getData();
//					Intent intent = new Intent(this, com.o2hlink.activa.exterior.ImageViewer.class);
//					startActivityForResult(intent, ExteriorManager.INTENT_RESULT_IMAGELOADED);
//		    	}
				break;
			case ExteriorManager.INTENT_RESULT_IMAGELOADED:
				myExteriorManager.externalApplications.get(ExteriorManager.APP_PICTURES).startApplication();
				break;
			case ExteriorManager.INTENT_RESULT_VIDEOSELECTED:
				if (data != null) {
			    	Intent intent = new Intent(Intent.ACTION_VIEW);
				    intent.setData(data.getData());
				    startActivityForResult(intent, ExteriorManager.INTENT_RESULT_VIDEOLOADED);
		    	}
				break;
			case ExteriorManager.INTENT_RESULT_VIDEOLOADED:
				myExteriorManager.externalApplications.get(ExteriorManager.APP_VIDEOS).startApplication();
				break;
			default:
				break;
		} 
    }
    
//    public void refreshScreen() {
//    	switch (myUIManager.state) {
//			case UIManager.UI_STATE_LOGIN:
//				myUIManager.loadLoginScreen();
//				break;
//			case UIManager.UI_STATE_MAIN:
//				Activa.myUIManager.loadGeneratedSubenvironment(Activa.myUIManager.lastSubenv, true);
//				break;
//			case UIManager.UI_STATE_TOTALQUESTIONNAIRE:
//				myUIManager.loadTotalQuestList();
//				break;
//			case UIManager.UI_STATE_TOTALSENSOR:
//				myUIManager.loadSensorList();
//				break;
//			case UIManager.UI_STATE_SCHEDULE:
//				myUIManager.loadScheduleDay(new Date());
//				break;
//			case UIManager.UI_STATE_SCHEDULEWEEK:
//				myUIManager.loadScheduleWeek(new Date());
//				break;
//			case UIManager.UI_STATE_SCHEDULEMONTH:
//				myUIManager.loadScheduleMonth(new Date());
//				break;
//			case UIManager.UI_STATE_TOTALPROGRAM:
//				myUIManager.loadProgramList	();
//				break;
//			case UIManager.UI_STATE_MAP:
//				myUIManager.showMap();
//				break;
//			case UIManager.UI_STATE_MESSAGE:
//				myUIManager.loadReceivedMessageList();
//				break;
//			case UIManager.UI_STATE_MESSAGEWRITING:
//				String receiverText = ((EditText) Activa.myApp.findViewById(R.id.receiver)).getText().toString();
//				String topicText = ((EditText) Activa.myApp.findViewById(R.id.topic)).getText().toString();
//				String textText = ((EditText) Activa.myApp.findViewById(R.id.text)).getText().toString();
//				Activa.myUIManager.refreshCreatingMessage(receiverText, topicText, textText);
//				break;
//			case UIManager.UI_STATE_MESSAGEREADING:
//				myUIManager.showReceivedMessage(Activa.myMessageManager.currentMessage, Activa.myMessageManager.currentContent);
//				break;
//			case UIManager.UI_STATE_QUESTION:
////				myUIManager.loadQuestion(myQuestManager.currentQuestion);
//				break;
//		}
//    }
    
    public boolean onOptionsItemSelected(MenuItem item) {
    	ImageView animationFrame;
    	final AnimationDrawable animation;
    	Runnable run;
    	Thread thread;
    	if (Activa.myApp.refreshing) {
			Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.MAIN_REFRESHING);
			return true;
		}
		else if (Activa.myApp.refreshing_foreground) {
			((TextView) Activa.myApp.findViewById(R.id.popupText)).setText(Activa.myLanguageManager.MAIN_REFRESHING);
			return true;
		}
        switch (item.getItemId()) {
	        case R.id.too2hlink:
	            Activa.myUIManager.goToO2HLinkWebPage();
	            return true;
	        case R.id.tomobile:
	        	Intent in = new Intent(Intent.ACTION_VIEW);
				in.setData(Uri.parse(ActivaConfig.ACTIV8YOUTUBE_URL));
				Activa.myApp.startActivity(in);
	            return true;
	        case R.id.toconf:
	        	if (!Activa.myMobileManager.identified) {
					Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.MAIN_FORBIDDEN);
					return false;
				}
	            Activa.myUIManager.showOptions();
	            return true;
	        case R.id.tocontact:
	            Activa.myUIManager.loadContactWithUs();
	            return true;
	        case R.id.toaboutus:
	            Activa.myUIManager.loadAboutUs();
	            return true;
	        case R.id.toautocalibration:
	            (new PedometerCalibration()).startCalibration();
	            return true;
	        case R.id.tomsgrec:
	        	Activa.myUIManager.loadReceivedMessageList(0);
	            return true;
	        case R.id.tomsgsent:
	        	Activa.myUIManager.loadSentMessageList(0);
	            return true;
	        case R.id.tocontacts:
	        	Activa.myUIManager.loadContactList(true);
	            return true;
	        case R.id.torefresh:
				animationFrame = (ImageView) Activa.myApp.findViewById(R.id.animation);
				animationFrame.setVisibility(View.VISIBLE);
				animationFrame.setBackgroundResource(R.drawable.loading);
				animation = (AnimationDrawable) animationFrame.getBackground();
				animation.start();
				((FrameLayout)Activa.myApp.findViewById(R.id.layout)).invalidate();
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
									Activa.myMessageManager.requestContactList();
									Activa.myMessageManager.requestEntryContactList();
									Activa.myMessageManager.requestReceivedMessages(0);
									Activa.myMessageManager.requestSentMessages(0);
						        	switch (Activa.myUIManager.state) {
										case UIManager.UI_STATE_MESSAGEREC:
											Activa.myUIManager.loadReceivedMessageList(0);
											break;
										case UIManager.UI_STATE_MESSAGESEN:
											Activa.myUIManager.loadSentMessageList(0);
											break;
										case UIManager.UI_STATE_CONTACTS:
											Activa.myUIManager.loadContactList(true);
											break;
										default:
											Activa.myUIManager.loadMessageList(0);
											break;
									}
									break;
							}
						}
	
					};
				};
				thread = new Thread(run);
				thread.start();
	            return true;
	        case R.id.todayforpatientmeas:
				animationFrame = (ImageView) Activa.myApp.findViewById(R.id.animation);
				animationFrame.setVisibility(View.VISIBLE);
				animationFrame.setBackgroundResource(R.drawable.loading);
				animation = (AnimationDrawable) animationFrame.getBackground();
				animation.start();
				((FrameLayout)Activa.myApp.findViewById(R.id.layout)).invalidate();
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
									Activa.myProtocolManager.getMeasuringEvents(Activa.myPatientManager.currentPat.getId(), start, end);
						        	Activa.myUIManager.loadScheduleDayForPatientMeas(new Date());
									break;
							}
						}
	
					};
				};
				thread = new Thread(run);
				thread.start();
	            return true;
	        case R.id.todayforpatientquest:
				animationFrame = (ImageView) Activa.myApp.findViewById(R.id.animation);
				animationFrame.setVisibility(View.VISIBLE);
				animationFrame.setBackgroundResource(R.drawable.loading);
				animation = (AnimationDrawable) animationFrame.getBackground();
				animation.start();
				((FrameLayout)Activa.myApp.findViewById(R.id.layout)).invalidate();
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
									Activa.myProtocolManager.getMeasuringEvents(Activa.myPatientManager.currentPat.getId(), start, end);
						        	Activa.myUIManager.loadScheduleDayForPatientQuest(new Date());
									break;
							}
						}
	
					};
				};
				thread = new Thread(run);
				thread.start();
	            return true;
	        case R.id.toweekforpatientmeas:
				animationFrame = (ImageView) Activa.myApp.findViewById(R.id.animation);
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
									Activa.myProtocolManager.getMeasuringEvents(Activa.myPatientManager.currentPat.getId(), start, end);
						        	Activa.myUIManager.loadScheduleWeekForPatientMeas(new Date());
									break;
							}
						}
	
					};
				};
				thread = new Thread(run);
				thread.start();
	            return true;
	        case R.id.toweekforpatientquest:
				animationFrame = (ImageView) Activa.myApp.findViewById(R.id.animation);
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
									Activa.myProtocolManager.getQuestEvents(Activa.myPatientManager.currentPat.getId(), start, end);
						        	Activa.myUIManager.loadScheduleWeekForPatientQuest(new Date());
									break;
							}
						}
	
					};
				};
				thread = new Thread(run);
				thread.start();
	            return true;
	        case R.id.tomonthforpatientmeas:
	        	animationFrame = (ImageView) Activa.myApp.findViewById(R.id.animation);
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
									Activa.myProtocolManager.getMeasuringEvents(Activa.myPatientManager.currentPat.getId(), start, end);
						        	Activa.myUIManager.loadScheduleMonthForPatientMeas(new Date());
									break;
							}
						}
	
					};
				};
				thread = new Thread(run);
				thread.start();
	            return true;
	        case R.id.tomonthforpatientquest:
	        	animationFrame = (ImageView) Activa.myApp.findViewById(R.id.animation);
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
									Activa.myProtocolManager.getQuestEvents(Activa.myPatientManager.currentPat.getId(), start, end);
						        	Activa.myUIManager.loadScheduleMonthForPatientQuest(new Date());
									break;
							}
						}
	
					};
				};
				thread = new Thread(run);
				thread.start();
	            return true;
	        case R.id.tomanualcalibration:
	        	final CharSequence[] items = {Activa.myLanguageManager.SENSORS_CALIBRATION_SELECT_ULTRAHIGH,
	        			Activa.myLanguageManager.SENSORS_CALIBRATION_SELECT_VERYHIGH,
	        			Activa.myLanguageManager.SENSORS_CALIBRATION_SELECT_HIGH,
	        			Activa.myLanguageManager.SENSORS_CALIBRATION_SELECT_NORMAL,
	        			Activa.myLanguageManager.SENSORS_CALIBRATION_SELECT_LOW,
	        			Activa.myLanguageManager.SENSORS_CALIBRATION_SELECT_VERYLOW,
	        			Activa.myLanguageManager.SENSORS_CALIBRATION_SELECT_ULTRALOW};
				AlertDialog.Builder builder = new AlertDialog.Builder(Activa.myApp);
				builder.setTitle(Activa.myLanguageManager.SENSORS_CALIBRATION_SELECT);
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
				animationFrame = (ImageView) Activa.myApp.findViewById(R.id.animation);
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
									Activa.myCalendarManager.getNonMeasuringEvents(start, end);
						            Activa.myUIManager.loadScheduleDay(new Date());
									break;
							}
						}
	
					};
				};
				thread = new Thread(run);
				thread.start();
	            return true;
	        case R.id.toweek:
				animationFrame = (ImageView) Activa.myApp.findViewById(R.id.animation);
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
									Activa.myCalendarManager.getNonMeasuringEvents(start, end);
						            Activa.myUIManager.loadScheduleWeek(new Date());
									break;
							}
						}
	
					};
				};
				thread = new Thread(run);
				thread.start();
	            return true;
	        case R.id.tomonth:
	        	animationFrame = (ImageView) Activa.myApp.findViewById(R.id.animation);
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
									Activa.myCalendarManager.getNonMeasuringEvents(start, end);
						            Activa.myUIManager.loadScheduleMonth(new Date());
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
    	Activa.myMenu = menu;
    	Activa.myInflater = getMenuInflater();
		switch (myUIManager.state) {
			case UIManager.UI_STATE_MAIN:
			case UIManager.UI_STATE_SUBENVIRONMENT:
				Activa.myInflater.inflate(R.menu.main, Activa.myMenu);
				break;
			case UIManager.UI_STATE_MESSAGE:
			case UIManager.UI_STATE_MESSAGEREADING:
			case UIManager.UI_STATE_MESSAGEWRITING:
				Activa.myInflater.inflate(R.menu.messages, Activa.myMenu);
				break;
			case UIManager.UI_STATE_SCHEDULE:
				Activa.myInflater.inflate(R.menu.schedule, Activa.myMenu);
				break;
			case UIManager.UI_STATE_SCHEDULEWEEK:
				Activa.myInflater.inflate(R.menu.schedule, Activa.myMenu);
				break;
			case UIManager.UI_STATE_SCHEDULEMONTH:
				Activa.myInflater.inflate(R.menu.schedule, Activa.myMenu);
				break;
			case UIManager.UI_STATE_SCHEDULEFORPATIENTMEAS:
				Activa.myInflater.inflate(R.menu.scheduleforpatientformeas, Activa.myMenu);
				break;
			case UIManager.UI_STATE_SCHEDULEWEEKFORPATIENTMEAS:
				Activa.myInflater.inflate(R.menu.scheduleforpatientformeas, Activa.myMenu);
				break;
			case UIManager.UI_STATE_SCHEDULEMONTHFORPATIENTMEAS:
				Activa.myInflater.inflate(R.menu.scheduleforpatientformeas, Activa.myMenu);
				break;
			case UIManager.UI_STATE_SCHEDULEFORPATIENTQUEST:
				Activa.myInflater.inflate(R.menu.scheduleforpatientforquest, Activa.myMenu);
				break;
			case UIManager.UI_STATE_SCHEDULEWEEKFORPATIENTQUEST:
				Activa.myInflater.inflate(R.menu.scheduleforpatientforquest, Activa.myMenu);
				break;
			case UIManager.UI_STATE_SCHEDULEMONTHFORPATIENTQUEST:
				Activa.myInflater.inflate(R.menu.scheduleforpatientforquest, Activa.myMenu);
				break;
			case UIManager.UI_STATE_TOTALSENSOR:
				Activa.myInflater.inflate(R.menu.sensors, Activa.myMenu);
				break;
			case UIManager.UI_STATE_MAP:
				Activa.myInflater.inflate(R.menu.map, Activa.myMenu);
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
			LayoutInflater inflater = (LayoutInflater) Activa.myApp.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			final View layout = inflater.inflate(R.layout.toasttext,
			     (ViewGroup) Activa.myApp.findViewById(R.id.toasttextroot));
			TextView textview = (TextView) layout.findViewById(R.id.searchexpl);
			textview.setText(msg.getData().getString("0"));
			Builder builder = new AlertDialog.Builder(Activa.myApp);
			builder.setView(layout);
			AlertDialog alertDialog = builder.create();
			alertDialog.show();
		}
	};
	
}