package com.o2hlink.pimtools.ui;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Vector;
import java.util.Map.Entry;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.Matrix.ScaleToFit;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.media.AudioManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.AsyncTask.Status;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.Html;
import android.text.InputType;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.Html.ImageGetter;
import android.text.util.Linkify;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnKeyListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.webkit.MimeTypeMap;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AbsoluteLayout;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ImageView.ScaleType;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TabHost.TabContentFactory;
import android.widget.TabHost.TabSpec;
import android.widget.TableLayout.LayoutParams;

import com.google.api.translate.Language;
import com.google.api.translate.Translate;
import com.o2hlink.activ8.client.action.Scrollable;
import com.o2hlink.activ8.client.entity.Answer;
import com.o2hlink.activ8.client.entity.Array;
import com.o2hlink.activ8.client.entity.Clinician;
import com.o2hlink.activ8.client.entity.Condition;
import com.o2hlink.activ8.client.entity.Contact;
import com.o2hlink.activ8.client.entity.ContactContactRequest;
import com.o2hlink.activ8.client.entity.Disease;
import com.o2hlink.activ8.client.entity.MultiAnswer;
import com.o2hlink.activ8.client.entity.MultiCondition;
import com.o2hlink.activ8.client.entity.MultiQuestion;
import com.o2hlink.activ8.client.entity.NumericAnswer;
import com.o2hlink.activ8.client.entity.NumericCondition;
import com.o2hlink.activ8.client.entity.NumericQuestion;
import com.o2hlink.activ8.client.entity.Question;
import com.o2hlink.activ8.client.entity.Questionnaire;
import com.o2hlink.activ8.client.entity.QuestionnaireSample;
import com.o2hlink.activ8.client.entity.Researcher;
import com.o2hlink.activ8.client.entity.Sample;
import com.o2hlink.activ8.client.entity.SimpleMessage;
import com.o2hlink.activ8.client.entity.Spirometry;
import com.o2hlink.activ8.client.entity.TextAnswer;
import com.o2hlink.activ8.client.entity.TextQuestion;
import com.o2hlink.activ8.client.entity.WeightHeight;
import com.o2hlink.activ8.common.entity.AccountType;
import com.o2hlink.activ8.common.entity.Country;
import com.o2hlink.activ8.common.entity.EventFrequency;
import com.o2hlink.activ8.common.entity.Measurement;
import com.o2hlink.activ8.common.entity.Sex;
import com.o2hlink.activ8.common.entity.TimeZone;
import com.o2hlink.activ8.common.entity.VisibilityLevel;
import com.o2hlink.pimtools.R;
import com.o2hlink.pimtools.Activa;
import com.o2hlink.pimtools.ActivaConfig;
import com.o2hlink.pimtools.ActivaUtil;
import com.o2hlink.pimtools.background.DownloadFiles;
import com.o2hlink.pimtools.background.GetDocuments;
import com.o2hlink.pimtools.background.GetHistory;
import com.o2hlink.pimtools.background.GetNews;
import com.o2hlink.pimtools.background.InitialConnection;
import com.o2hlink.pimtools.background.MainThread;
import com.o2hlink.pimtools.background.NotificationThread;
import com.o2hlink.pimtools.background.RefreshingConnection;
import com.o2hlink.pimtools.background.SendNote;
import com.o2hlink.pimtools.background.SendO2Message;
import com.o2hlink.pimtools.background.SendQuestionnaire;
import com.o2hlink.pimtools.background.SendSensorResult;
import com.o2hlink.pimtools.background.SyncFiles;
import com.o2hlink.pimtools.background.UserCheckout;
import com.o2hlink.pimtools.background.UserCheckoutForRegister;
import com.o2hlink.pimtools.connection.ProtocolManager;
import com.o2hlink.pimtools.data.calendar.CalendarManager;
import com.o2hlink.pimtools.data.calendar.Event;
import com.o2hlink.pimtools.data.calendar.ExerciseSample;
import com.o2hlink.pimtools.data.calendar.PulseoximetrySample;
import com.o2hlink.pimtools.data.calendar.SixMinutesWalkSample;
import com.o2hlink.pimtools.data.calendar.SpirometrySample;
import com.o2hlink.pimtools.data.message.MessageManager;
import com.o2hlink.pimtools.data.questionnaire.control.QuestControlManager;
import com.o2hlink.pimtools.data.sensor.Exercise;
import com.o2hlink.pimtools.data.sensor.PulseOximeter;
import com.o2hlink.pimtools.data.sensor.Sensor;
import com.o2hlink.pimtools.data.sensor.SensorManager;
import com.o2hlink.pimtools.data.sensor.SixMinutes;
import com.o2hlink.pimtools.data.sensor.Spirometer;
import com.o2hlink.pimtools.data.sensor.WeightAndHeight;
import com.o2hlink.pimtools.documents.Document;
import com.o2hlink.pimtools.documents.DocumentsManager;
import com.o2hlink.pimtools.exceptions.NotUpdatedException;
import com.o2hlink.pimtools.exterior.ExteriorManager;
import com.o2hlink.pimtools.exterior.ExternalApp;
import com.o2hlink.pimtools.map.MapManager;
import com.o2hlink.pimtools.mobile.MobileManager;
import com.o2hlink.pimtools.mobile.User;
import com.o2hlink.pimtools.news.Feed;
import com.o2hlink.pimtools.news.New;
import com.o2hlink.pimtools.news.NewsManager;
import com.o2hlink.pimtools.notes.Note;
import com.o2hlink.pimtools.notes.NotesManager;
import com.o2hlink.pimtools.patient.HistoryRecord;
import com.o2hlink.pimtools.patient.Patient;
import com.o2hlink.pimtools.patient.PatientManager;
import com.o2hlink.pimtools.patient.WeightHeightSample;
import com.o2hlink.pimtools.ui.i18n.ResourceEnglish;
import com.o2hlink.pimtools.ui.widget.Ambient;
import com.o2hlink.pimtools.ui.widget.Subenvironment;
import com.o2hlink.pimtools.ui.widget.Widget;
import com.paypal.android.MEP.PayPal;
import com.paypal.android.MEP.PayPalInvoiceData;
import com.paypal.android.MEP.PayPalInvoiceItem;
import com.paypal.android.MEP.PayPalPayment;

/**
 * 
 * @author Adrian Rejas<P>
 * 
 * This class will deal with the management of the User Interface. The interfaces will be 
 * designed through XML documents. This class will deal with the charge of these interfaces
 * and the interaction with their elements, ordering to the rest of managers to change 
 * the layout or to do activities depending of these interactions.			
 *
 */
@SuppressWarnings("deprecation")
public class UIManager {
	
	/**
	 * The instance of the manager.
	 */
	private static UIManager instance;
	
	/**
	 * Different constants for defining the state of the UI.
	 */
	public static final int UI_STATE_LOADING = 0;
	public static final int UI_STATE_LOGIN = 1;
	public static final int UI_STATE_USERINFO = 2;
	public static final int UI_STATE_TOTALQUESTIONNAIRE = 3;
	public static final int UI_STATE_QUESTIONNAIREINIT = 4;
	public static final int UI_STATE_QUESTION = 5;
	public static final int UI_STATE_TOTALSENSOR = 6;
	public static final int UI_STATE_SCHEDULE = 7;
	public static final int UI_STATE_TOTALPROGRAM = 8;
	public static final int UI_STATE_SENSORING = 9;
	public static final int UI_STATE_SENSORINFO = 10;
	public static final int UI_STATE_MEASURING = 11;
	public static final int UI_STATE_MAIN = 12;
	public static final int UI_STATE_MANUALMEAS = 13;
	public static final int UI_STATE_TIMEDMEAS = 14;
	public static final int UI_STATE_PROGRAM = 15;
	public static final int UI_STATE_REGISTER = 16;
	public static final int UI_STATE_CHECKING = 165;
	public static final int UI_STATE_SCHEDULEWEEK = 17;
	public static final int UI_STATE_SCHEDULEMONTH = 18;
	public static final int UI_STATE_MAP = 19;
	public static final int UI_STATE_MESSAGE = 20;
	public static final int UI_STATE_MESSAGEWRITING = 21;
	public static final int UI_STATE_MESSAGEREADING = 22;
	public static final int UI_STATE_EXERCISE = 23;
	public static final int UI_STATE_NEWS = 24;
	public static final int UI_STATE_NOTES = 25;
	public static final int UI_STATE_PATIENTS = 26;
	public static final int UI_STATE_OPTIONS = 27;
	public static final int UI_STATE_SCHEDULEFORPATIENTMEAS = 28;
	public static final int UI_STATE_SCHEDULEWEEKFORPATIENTMEAS = 29;
	public static final int UI_STATE_SCHEDULEMONTHFORPATIENTMEAS = 30;
	public static final int UI_STATE_AMBIENTS = 31;
	public static final int UI_STATE_REGISTERDATA = 32;
	public static final int UI_STATE_SUBENVIRONMENT = 33;
	public static final int UI_STATE_SCHEDULEFORPATIENTQUEST = 34;
	public static final int UI_STATE_SCHEDULEWEEKFORPATIENTQUEST = 35;
	public static final int UI_STATE_SCHEDULEMONTHFORPATIENTQUEST = 36;
	public static final int UI_STATE_WEB = 37;
	public static final int UI_STATE_MESSAGEREC = 38;
	public static final int UI_STATE_MESSAGESEN = 39;
	public static final int UI_STATE_CONTACTS = 40;
	public static final int UI_STATE_DOCUMENTS = 41;
	public static final int UI_STATE_DOCUMENT = 42;
	

	static ArrayList<String> EUISOCodes = new ArrayList<String>();
	static {
		EUISOCodes.add("de");
		EUISOCodes.add("at");
		EUISOCodes.add("be");
		EUISOCodes.add("bg");
		EUISOCodes.add("cy");
		EUISOCodes.add("dk");
		EUISOCodes.add("si");
		EUISOCodes.add("es");
		EUISOCodes.add("ee");
		EUISOCodes.add("fi");
		EUISOCodes.add("fr");
		EUISOCodes.add("gr");
		EUISOCodes.add("hu");
		EUISOCodes.add("ie");
		EUISOCodes.add("it");
		EUISOCodes.add("lv");
		EUISOCodes.add("lt");
		EUISOCodes.add("lu");
		EUISOCodes.add("mt");
		EUISOCodes.add("nl");
		EUISOCodes.add("no");
		EUISOCodes.add("pl");
		EUISOCodes.add("pt");
		EUISOCodes.add("cz");
		EUISOCodes.add("ro");
		EUISOCodes.add("se");
		EUISOCodes.add("ch");
		EUISOCodes.add("ba");
		EUISOCodes.add("hr");
		EUISOCodes.add("sk");
		EUISOCodes.add("rs");
	}
	/**
	 * State of the UI (Screen is appearing).
	 */
	public int state;
	
	/**
	 * Previous state of the UI.
	 */
	public int prevState;
	
	/**
	 * The ambient used settings.
	 */
	public Ambient ambient;
	
	/**
	 * The last subenvironment entered.
	 */
	public Subenvironment lastSubenv;
	
	/**
	 * Static TextView for different purposes.
	 */
	public TextView genericTextView;
	
	public String ambientname;

	public Theme ambientprocessing;
	
	public String currentAmbUrl;
	
	public Array<Theme> ambients;
	
	public ArrayList<String> pursached;
	
	/**
	 * Private constructor.
	 */
	private UIManager() {
		this.state = UI_STATE_LOADING;
		this.prevState = -1;
	}
	
	/**
	 * Method for getting the instance of the manager.
	 */
	public static UIManager getInstance() {
		if (UIManager.instance == null) 
			UIManager.instance = new UIManager();
		return UIManager.instance;
	}
	
	/**
	 * Method for freeing the instance of the manager.
	 */
	public static void freeInstance() {
		UIManager.instance = null;
	}
	
	public void loadAmbient() {
		this.ambient = Ambient.getInstance();
		if (!this.ambient.getEnvironment(Activa.myMobileManager.user.getAmbient()))
				loadDefaultAmbient();
		this.ambient.passEnvironmentToXML();
	}
	
	public void loadDefaultAmbient() {
		this.ambient = Ambient.getInstance();
		this.ambient.getDefaultEnvironment();
	}
	
	public void loadScreen(int screen) {
		Activa.myApp.setContentView(screen);
	}
	
	public void goToO2HLinkWebPage() {
		Activa.myApp.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.o2hlink.com")));
	}
	
	public void goToVPHumanWebPage() {
		Activa.myApp.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.vphuman.com")));
	}
	
	public void loadInitScreen() {
		this.state = UI_STATE_USERINFO;
		Activa.myApp.setContentView(R.layout.init);
		CountDownTimer timer = new CountDownTimer(1000,1000) {
			@Override
			public void onTick(long millisUntilFinished) {
			}
			
			@Override
			public void onFinish() {
		        Activa.myLanguageManager = Activa.setLanguage(Activa.myApp.getResources().getConfiguration().locale);
		        Activa.rootFile = Environment.getExternalStorageDirectory();
		        Activa.myTelephonyManager = (TelephonyManager) Activa.myApp.getSystemService(Context.TELEPHONY_SERVICE);
		        Activa.myMobileManager = MobileManager.getInstance(); 
		        Activa.myProtocolManager = ProtocolManager.getInstance();
		        Activa.myCalendarManager = CalendarManager.getInstance();
		        Activa.mySensorManager = SensorManager.getInstance();
		        Activa.myAlarmManager = (AlarmManager)Activa.myApp.getSystemService(Context.ALARM_SERVICE);
		        Activa.myNotificationManager = (NotificationManager) Activa.myApp.getSystemService(Activity.NOTIFICATION_SERVICE);
		        Activa.myMapManager = MapManager.getInstance();
		        Activa.myMessageManager = MessageManager.getInstance();
		        Activa.myMainBackgroundThread = new MainThread();
		        Activa.myAccelerometerManager = (android.hardware.SensorManager)Activa.myApp.getSystemService(Context.SENSOR_SERVICE);
		        Activa.myAudioManager = (AudioManager)Activa.myApp.getSystemService(Context.AUDIO_SERVICE);
		        Activa.myNotificationThread = NotificationThread.getInstance();
		        Activa.myNewsManager = NewsManager.getInstance();
		        Activa.myExteriorManager = ExteriorManager.getInstance();
		        Activa.myNotesManager = NotesManager.getInstance();
		        Activa.myPatientManager = PatientManager.getInstance();
		        Activa.myQuestControlManager = QuestControlManager.getInstance();
		        Activa.myDocumentsManager = DocumentsManager.getInstance();
				if (Activa.myMobileManager.users.isEmpty()) 
					Activa.myUIManager.loadInitialProcessScreen();
				else 
					Activa.myUIManager.loadLoginScreen();
			}
		};
		timer.start();
	}
	
	public void loadInitialProcessScreen() {
		int width = Activa.myApp.getWindowManager().getDefaultDisplay().getWidth();
		this.state = UI_STATE_USERINFO;
		Activa.myApp.setContentView(R.layout.empty);
		LinearLayout block = (LinearLayout) Activa.myApp.findViewById(R.id.info);
		TextView title = new TextView(Activa.myApp);
		title.setTextColor(Color.BLACK);
		title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		title.setTypeface(Typeface.DEFAULT_BOLD);
		title.setText(Activa.myApp.getResources().getString(R.string.app_name) + " " + Activa.myApp.getResources().getString(R.string.app_version) + "\n");
		TextView text = new TextView(Activa.myApp);
		text.setTextColor(Color.BLACK);
		text.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
		text.setTypeface(Typeface.SANS_SERIF);
		text.setText(Activa.myApp.getResources().getString(R.string.registerwarning));
		RelativeLayout rel = new RelativeLayout(Activa.myApp);
		LinearLayout.LayoutParams par = new LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		par.setMargins(0, 10, 0, 10);
		rel.setLayoutParams(par);
		ImageButton create = new ImageButton(Activa.myApp);
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width*4/10, android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
		params.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
		create.setLayoutParams(params);
		create.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Activa.myUIManager.loadRegisterScreen(0);
			}
		});
		create.setBackgroundResource(R.drawable.iconbg);
		create.setImageResource(Activa.myLanguageManager.createAccFile);
		ImageButton enter = new ImageButton(Activa.myApp);
		params = new RelativeLayout.LayoutParams(width*4/10, android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
		params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
		enter.setLayoutParams(params);
		enter.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Activa.myUIManager.loadCheckUserScreen();
			}
		});
		enter.setBackgroundResource(R.drawable.iconbg);
		enter.setImageResource(Activa.myLanguageManager.accessAccFile);
		block.addView(title);
		block.addView(text);
		rel.addView(create);
		rel.addView(enter);
		block.addView(rel);
	}
	
	/**
	 * Load the login screen at the beginning of the program.
	 */
	public void loadLoginScreen() {
		Activa.myMobileManager.password = "";
		this.state = UI_STATE_LOGIN;
		Activa.myApp.setContentView(R.layout.password);
		((TextView) Activa.myApp.findViewById(R.id.startText)).setText(Activa.myLanguageManager.PSW_REQUEST);
		final FrameLayout board = (FrameLayout) Activa.myApp.findViewById(R.id.passLayout);
		board.addView(new PasswordView(Activa.myApp, 0));
		ImageButton add = (ImageButton) Activa.myApp.findViewById(R.id.add);
		add.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Activa.myUIManager.loadInitialProcessScreen();
			}
		});
	}
	
	public void loadCheckUserScreen() {
		this.state = UI_STATE_CHECKING;
		Activa.myApp.setContentView(R.layout.register);
		((TextView) Activa.myApp.findViewById(R.id.startText)).setText(Activa.myLanguageManager.PSW_REG_REQUEST);
		((TextView) Activa.myApp.findViewById(R.id.requestText)).setText(Activa.myLanguageManager.PSW_REG_ENTERDATA);
		((TextView) Activa.myApp.findViewById(R.id.requestUser)).setText(Activa.myLanguageManager.PSW_REG_USERNAME);
		((TextView) Activa.myApp.findViewById(R.id.requestPass)).setText(Activa.myLanguageManager.PSW_REG_PASSWORD);
		((TextView) Activa.myApp.findViewById(R.id.requestPassagain)).setVisibility(View.INVISIBLE);
		final EditText username = (EditText) Activa.myApp.findViewById(R.id.loginText);
		final EditText password = (EditText) Activa.myApp.findViewById(R.id.passwordText);
		final EditText passwordAgain = (EditText) Activa.myApp.findViewById(R.id.passwordTextagain);
		password.setOnKeyListener(new OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_ENTER) {
					InputMethodManager imm = (InputMethodManager)Activa.myApp.getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(passwordAgain.getWindowToken(), 0);
				}
				return false;
			}
		});
		passwordAgain.setVisibility(View.INVISIBLE);
		ImageButton ok = (ImageButton) Activa.myApp.findViewById(R.id.ok);
		ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					String userText = username.getText().toString();
					String passText = password.getText().toString();
					if (userText.equals("")) {
						throw new Exception();
					}
					if (passText.equals("")) {
						throw new Exception();
					}
					Activa.myMobileManager.user = new User(userText, passText);
					Activa.myMobileManager.user.setCreated(false);
					Thread trd = new Thread(new UserCheckout());
					trd.start();
				} catch (Exception e) {
					loadInfoPopup(Activa.myLanguageManager.PSW_REG_BADDATA);
				}
			}
		});
	}
	
	public void loadUserTypeChoiceScreen(final int type) {
		this.state = UI_STATE_REGISTER;
		Activa.myApp.setContentView(R.layout.usertype);
		((TextView) Activa.myApp.findViewById(R.id.startText)).setText(Activa.myApp.getResources().getString(R.string.usertypeexpl));
		if ((type >=0)&&(type <=2)) {
			((TextView) Activa.myApp.findViewById(R.id.explanationtitle)).setText(Activa.myApp.getResources().getStringArray(R.array.usertype)[type]);
			((TextView) Activa.myApp.findViewById(R.id.explanation)).setText(Activa.myApp.getResources().getStringArray(R.array.usertypewarn)[type]);
		}
		((ImageButton) Activa.myApp.findViewById(R.id.patient)).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				loadUserTypeChoiceScreen(0);
			}
		});
		((ImageButton) Activa.myApp.findViewById(R.id.clinician)).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				loadUserTypeChoiceScreen(1);
			}
		});
		((ImageButton) Activa.myApp.findViewById(R.id.researcher)).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				loadUserTypeChoiceScreen(2);
			}
		});
		((ImageButton) Activa.myApp.findViewById(R.id.ok)).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (type >= 0) loadRegisterScreen(type);
			}
		});
	}
	
	public void loadRegisterScreen(final int usertype) {
		this.state = UI_STATE_REGISTER;
		Activa.myApp.setContentView(R.layout.register);
		((TextView) Activa.myApp.findViewById(R.id.startText)).setText(Activa.myLanguageManager.PSW_REG_TITLE);
		((TextView) Activa.myApp.findViewById(R.id.requestText)).setText(Activa.myLanguageManager.PSW_REG_ENTERDATA);
		((TextView) Activa.myApp.findViewById(R.id.requestUser)).setText(Activa.myLanguageManager.PSW_REG_USERNAME);
		((TextView) Activa.myApp.findViewById(R.id.requestPass)).setText(Activa.myLanguageManager.PSW_REG_PASSWORD);
		((TextView) Activa.myApp.findViewById(R.id.requestPassagain)).setText(Activa.myLanguageManager.PSW_REG_PASSWORD_AGAIN);
		((RelativeLayout) Activa.myApp.findViewById(R.id.userterms)).setVisibility(View.VISIBLE);
		Spanned userterms = Html.fromHtml(Activa.myLanguageManager.PSW_REG_ACCEPT + "<a href=\"" + ActivaConfig.ACTIV8TERMSANDCONDS_URL + "\">" + Activa.myLanguageManager.MAIN_TERMSANDCONDS + "</a>");
		((TextView) Activa.myApp.findViewById(R.id.usertermstext)).setText(userterms);
		((TextView) Activa.myApp.findViewById(R.id.usertermstext)).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
		    	Intent in = new Intent(Intent.ACTION_VIEW);
				in.setData(Uri.parse(ActivaConfig.ACTIV8TERMSANDCONDS_URL));
				Activa.myApp.startActivity(in);
			}
		});
		final EditText username = (EditText) Activa.myApp.findViewById(R.id.loginText);
		final EditText password = (EditText) Activa.myApp.findViewById(R.id.passwordText);
		final EditText passwordAgain = (EditText) Activa.myApp.findViewById(R.id.passwordTextagain);
		passwordAgain.setOnKeyListener(new OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_ENTER) {
					InputMethodManager imm = (InputMethodManager)Activa.myApp.getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(passwordAgain.getWindowToken(), 0);
				}
				return false;
			}
		});
		ImageButton ok = (ImageButton) Activa.myApp.findViewById(R.id.ok);
		ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					CheckBox usertermscheck = (CheckBox) Activa.myApp.findViewById(R.id.usertermscheck);
					if (!usertermscheck.isChecked()) {
						Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.PSW_REG_ACCEPTTERMS);
						return;
					}
					String userText = username.getText().toString();
					String passText = password.getText().toString();
					String passAgain = passwordAgain.getText().toString();
					if (userText.equals("")) {
						throw new Exception();
					}
					if (passText.equals("")) {
						throw new Exception();
					}
					if (!passText.equals(passAgain)) {
						password.setText("");
						passwordAgain.setText("");
						throw new Exception();
					}
					Activa.myMobileManager.user = new User(userText, passText);
					Activa.myMobileManager.user.setType(usertype);
					Activa.myMobileManager.user.setCreated(false);
					Thread trd = new Thread(new UserCheckoutForRegister(usertype));
					trd.start();
				} catch (Exception e) {
					loadInfoPopup(Activa.myLanguageManager.PSW_REG_BADDATA);
				}
			}
		});
	}
	
	public void loadChangePassword() {
		Activa.myApp.setContentView(R.layout.register);
		((TextView) Activa.myApp.findViewById(R.id.startText)).setText(Activa.myLanguageManager.PSW_REG_PASSWORD_CHANGE);
		((TextView) Activa.myApp.findViewById(R.id.requestText)).setText(Activa.myLanguageManager.PSW_REG_USERNAME + " " + Activa.myMobileManager.user.getName());
		((TextView) Activa.myApp.findViewById(R.id.requestUser)).setText(Activa.myLanguageManager.PSW_REG_PASSWORD_OLD);
		((TextView) Activa.myApp.findViewById(R.id.requestPass)).setText(Activa.myLanguageManager.PSW_REG_PASSWORD_NEW);
		((TextView) Activa.myApp.findViewById(R.id.requestPassagain)).setText(Activa.myLanguageManager.PSW_REG_PASSWORD_NEW_AGAIN);
		final EditText username = (EditText) Activa.myApp.findViewById(R.id.loginText);
		final EditText password = (EditText) Activa.myApp.findViewById(R.id.passwordText);
		final EditText passwordAgain = (EditText) Activa.myApp.findViewById(R.id.passwordTextagain);
		ImageButton ok = (ImageButton) Activa.myApp.findViewById(R.id.ok);
		ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				final String oldpassText = username.getText().toString();
				final String newpassText = password.getText().toString();
				String newpassTextAgain = passwordAgain.getText().toString();
				if ((oldpassText.equals(""))||(newpassText.equals(""))||(newpassTextAgain.equals(""))) {
					loadInfoPopup(Activa.myLanguageManager.PSW_REG_BADDATA);
					return;
				}
				if (!oldpassText.equals(Activa.myMobileManager.user.getPassword())) {
					username.setText("");
					password.setText("");
					passwordAgain.setText("");
					loadInfoPopup(Activa.myLanguageManager.PSW_REG_PASSWORD_NEW_NOTMATCH);
					return;
				}
				if (!newpassText.equals(newpassTextAgain)) {
					password.setText("");
					passwordAgain.setText("");
					loadInfoPopup(Activa.myLanguageManager.PSW_REG_PASSWORD_NEW_NOTMATCH);
					return;
				}
				Runnable run = new Runnable() {
					@Override
					public void run() {
						handler.sendEmptyMessage(0);
						if (Activa.myMobileManager.updateUserPassword(oldpassText, newpassText)) {
							handler.sendEmptyMessage(1);
						}
						else handler.sendEmptyMessage(2);
					}
					private Handler handler = new Handler(){
						@Override
						public void handleMessage(Message msg) {
							ImageView animationFrame;
							AnimationDrawable animation;
							switch (msg.what) {
								case 0:
									Activa.myUIManager.loadInfoPopupWithoutExiting(Activa.myLanguageManager.CONNECTION_CONNECTING);
									animationFrame = (ImageView) Activa.myApp.findViewById(R.id.popupImage);
									animationFrame.setVisibility(View.VISIBLE);
									animationFrame.setBackgroundResource(R.drawable.loading);
									animation = (AnimationDrawable) animationFrame.getBackground();
									animation.start();
									break;
								case 1:
									animationFrame = (ImageView) Activa.myApp.findViewById(R.id.popupImage);
									animation = (AnimationDrawable) animationFrame.getBackground();
									animation.stop();
									animationFrame.setVisibility(View.GONE);
									Activa.myUIManager.removeInfoPopup();
									Activa.myMobileManager.user.setPassword(newpassText);
									Activa.myMobileManager.saveUsers();
									showOptions();
									break;
								case 2:
									animationFrame = (ImageView) Activa.myApp.findViewById(R.id.popupImage);
									animation = (AnimationDrawable) animationFrame.getBackground();
									animation.stop();
									animationFrame.setVisibility(View.GONE);
									Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.CONNECTION_FAILED);
									if (Activa.myMobileManager.user.getAmbient() == null) Activa.myUIManager.loadDefaultAmbient();
					        		else if (Activa.myMobileManager.user.getAmbient().equalsIgnoreCase("null")) Activa.myUIManager.loadDefaultAmbient();
					        		Activa.myUIManager.loadGeneratedMainScreen(true, false, false);
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
	
	public void loadRegisterDataScreen(final int type) {
		this.state = UI_STATE_REGISTERDATA;
		Activa.myApp.setContentView(R.layout.registerdata);
		((TextView) Activa.myApp.findViewById(R.id.startText)).setText(Activa.myLanguageManager.PSW_REG_TITLE);
		((TextView) Activa.myApp.findViewById(R.id.requestText)).setText(Activa.myLanguageManager.PSW_REG_DATAREQUEST);
		((TextView) Activa.myApp.findViewById(R.id.requestFirst)).setText(Activa.myLanguageManager.PSW_REG_FIRSTNAME);
		((TextView) Activa.myApp.findViewById(R.id.requestLast)).setText(Activa.myLanguageManager.PSW_REG_LASTNAME);
		((TextView) Activa.myApp.findViewById(R.id.requestDate)).setText(Activa.myLanguageManager.PSW_REG_DATE);
		((TextView) Activa.myApp.findViewById(R.id.requestEmail)).setText("E-Mail: ");
		((TextView) Activa.myApp.findViewById(R.id.requestRepeatEmail)).setText(Activa.myLanguageManager.PSW_REG_EMAILREPEAT);
		((TextView) Activa.myApp.findViewById(R.id.sexrequest)).setText(Activa.myLanguageManager.PSW_REG_SEX);
		((TextView) Activa.myApp.findViewById(R.id.countryrequest)).setText(Activa.myLanguageManager.PSW_REG_COUNTRY);
		final EditText first = (EditText) Activa.myApp.findViewById(R.id.firstText);
		final EditText last = (EditText) Activa.myApp.findViewById(R.id.lastText);
		final EditText mail = (EditText) Activa.myApp.findViewById(R.id.emailText);
		final EditText repeatmail = (EditText) Activa.myApp.findViewById(R.id.repeatEmailText);
		repeatmail.setOnKeyListener(new OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_ENTER) {
					InputMethodManager imm = (InputMethodManager)Activa.myApp.getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(repeatmail.getWindowToken(), 0);
				}
				return false;
			}
		});
		final DatePicker date = (DatePicker) Activa.myApp.findViewById(R.id.birthdate);
		final Spinner sex = (Spinner) Activa.myApp.findViewById(R.id.sex);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(Activa.myApp, R.array.sextype, android.R.layout.simple_spinner_item);
	    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    sex.setAdapter(adapter);
	    sex.setSelection(0);
		final Country[] countries = Country.values();
		final Spinner country = (Spinner) Activa.myApp.findViewById(R.id.country);
		ArrayList<String> countrystrings = new ArrayList<String>();
		for (Country ctr : countries) {
			countrystrings.add(ctr.getName());
		}
		ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(Activa.myApp, android.R.layout.simple_spinner_item, countrystrings);
	    adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    country.setAdapter(adapter3);
	    country.setSelection(0);
		ImageButton ok = (ImageButton) Activa.myApp.findViewById(R.id.ok);
		ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try{
					if (first.getText().toString().equals("")) throw new Exception();
					if (last.getText().toString().equals("")) throw new Exception();
					if (ActivaUtil.isAMail(mail.getText().toString())) throw new Exception();
					Activa.myMobileManager.user.setFirstname(first.getText().toString());
					Activa.myMobileManager.user.setLastname(last.getText().toString());
					if (countries != null) Activa.myMobileManager.user.setCountry(countries[country.getSelectedItemPosition()]);
					Activa.myMobileManager.user.setMail(mail.getText().toString());
					Date birthdate = new Date(0);
					birthdate.setYear(date.getYear() - 1900);
					birthdate.setMonth(date.getMonth());
					birthdate.setDate(date.getDayOfMonth());
					Date minbirthdate = new Date();
					minbirthdate.setMonth(minbirthdate.getMonth() - 1);
					if (mail.getText().equals(repeatmail.getText())) {
						loadInfoPopup(Activa.myLanguageManager.PSW_REG_EMAILDISMATCH);
						return;
					}
					if (birthdate.after(minbirthdate)) {
						loadInfoPopup(Activa.myLanguageManager.PSW_REG_MINBIRTH);
						return;
					}
					Activa.myMobileManager.user.setBirthdate(birthdate);
					switch (sex.getSelectedItemPosition()) {
						case 0:
							Activa.myMobileManager.user.setSex(Sex.NOT_SPECIFIED);
							break;
						case 1:
							Activa.myMobileManager.user.setSex(Sex.MALE);
							break;
						default:
							Activa.myMobileManager.user.setSex(Sex.FEMALE);
							break;
					}
					Activa.myMobileManager.user.setType(type);
					loadPasswordExplanation();
				} catch (Exception e) {
					Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.PSW_REG_BADDATA);
				} 
			}
		});
	}
	
	public void loadPasswordExplanation() {
		Activa.myApp.setContentView(R.layout.info);
		((TextView) Activa.myApp.findViewById(R.id.textInfo)).setText(Activa.myApp.getResources().getString(R.string.passwordwarning));
		CountDownTimer timer = new CountDownTimer(6000,1000) {
			@Override
			public void onTick(long millisUntilFinished) {}
			@Override
			public void onFinish() {
				loadMatrixPasswordForRegistering();
			}
		};
		timer.start();
	}
	
	public void loadDataScreenForChanging() {
		Activa.myApp.setContentView(R.layout.registerdata);
		((TextView) Activa.myApp.findViewById(R.id.startText)).setText("User data");
		((TextView) Activa.myApp.findViewById(R.id.requestText)).setText(Activa.myLanguageManager.PSW_REG_DATAREQUEST);
		((TextView) Activa.myApp.findViewById(R.id.requestFirst)).setText(Activa.myLanguageManager.PSW_REG_FIRSTNAME);
		((TextView) Activa.myApp.findViewById(R.id.requestLast)).setText(Activa.myLanguageManager.PSW_REG_LASTNAME);
		((TextView) Activa.myApp.findViewById(R.id.requestEmail)).setText("E-Mail: ");
		((TextView) Activa.myApp.findViewById(R.id.requestRepeatEmail)).setText(Activa.myLanguageManager.PSW_REG_EMAILREPEAT);
		((TextView) Activa.myApp.findViewById(R.id.requestDate)).setText(Activa.myLanguageManager.PSW_REG_DATE);
		((TextView) Activa.myApp.findViewById(R.id.sexrequest)).setText(Activa.myLanguageManager.PSW_REG_SEX);
		((TextView) Activa.myApp.findViewById(R.id.countryrequest)).setText(Activa.myLanguageManager.PSW_REG_COUNTRY);
		final EditText first = (EditText) Activa.myApp.findViewById(R.id.firstText);
		first.setText(Activa.myMobileManager.user.firstname);
		final EditText last = (EditText) Activa.myApp.findViewById(R.id.lastText);
		last.setText(Activa.myMobileManager.user.lastname);
		final EditText mail = (EditText) Activa.myApp.findViewById(R.id.emailText);
		final EditText repeatMail = (EditText) Activa.myApp.findViewById(R.id.repeatEmailText);
		if (Activa.myMobileManager.user.getMail() != null) {
			mail.setText(Activa.myMobileManager.user.getMail());
			repeatMail.setText(Activa.myMobileManager.user.getMail());
		}
		final DatePicker date = (DatePicker) Activa.myApp.findViewById(R.id.birthdate);
		if (Activa.myMobileManager.userForServices.getBirthdate() == null) date.init(1970, 1, 1, null);
		else date.init(Activa.myMobileManager.userForServices.getBirthdate().getYear() + 1900, 
				Activa.myMobileManager.userForServices.getBirthdate().getMonth(), 
				Activa.myMobileManager.userForServices.getBirthdate().getDate(), null);
		final Spinner sex = (Spinner) Activa.myApp.findViewById(R.id.sex);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(Activa.myApp, R.array.sextype, android.R.layout.simple_spinner_item);
	    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    sex.setAdapter(adapter);
		if (Activa.myMobileManager.user.getSex().equals(Sex.NOT_SPECIFIED)) sex.setSelection(0);
		else if (Activa.myMobileManager.user.getSex().equals(Sex.MALE)) sex.setSelection(1);
		else if (Activa.myMobileManager.user.getSex().equals(Sex.FEMALE)) sex.setSelection(2);
		else sex.setSelection(0);
		final Country[] countries = Country.values();
		final Spinner country = (Spinner) Activa.myApp.findViewById(R.id.country);
		ArrayList<String> countrystrings = new ArrayList<String>();
		int j = 0;
		int selected = 0;
		for (Country ctr : countries) {
			String thecountry = ctr.getName();
			String mycountry;
			try {
				mycountry = Activa.myMobileManager.userForServices.getCountry().getName();
			} catch (Exception e) {
				mycountry = null;
			}
			countrystrings.add(thecountry);
			if ((mycountry != null)&&(thecountry.equalsIgnoreCase(mycountry))) selected = j;
			j++;
		}
		ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(Activa.myApp, android.R.layout.simple_spinner_item, countrystrings);
	    adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    country.setAdapter(adapter3);
	    country.setSelection(0);
	    if (countries != null) country.setSelection(selected);
//	    if (Activa.myMobileManager.user.country != null) country.setEnabled(false);
		ImageButton ok = (ImageButton) Activa.myApp.findViewById(R.id.ok);
		ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try{
					if (first.getText().toString().equals("")) throw new Exception();
					if (last.getText().toString().equals("")) throw new Exception();
					if (ActivaUtil.isAMail(mail.getText().toString())) throw new Exception();
					Activa.myMobileManager.user.setFirstname(first.getText().toString());
					Activa.myMobileManager.user.setLastname(last.getText().toString());
					Activa.myMobileManager.user.setMail(mail.getText().toString());
					if (countries != null) Activa.myMobileManager.user.setCountry(countries[country.getSelectedItemPosition()]);
					Date birthdate = new Date(0);
					birthdate.setYear(date.getYear() - 1900);
					birthdate.setMonth(date.getMonth());
					birthdate.setDate(date.getDayOfMonth());
					Date minbirthdate = new Date();
					minbirthdate.setMonth(minbirthdate.getMonth() - 1);
					if (mail.getText().equals(repeatMail.getText())) {
						loadInfoPopup(Activa.myLanguageManager.PSW_REG_EMAILDISMATCH);
						return;
					}
					if (birthdate.after(minbirthdate)) {
						loadInfoPopup(Activa.myLanguageManager.PSW_REG_MINBIRTH);
						return;
					}
					Activa.myMobileManager.user.setBirthdate(birthdate);
					switch (sex.getSelectedItemPosition()) {
						case 0:
							Activa.myMobileManager.user.setSex(Sex.NOT_SPECIFIED);
							break;
						case 1:
							Activa.myMobileManager.user.setSex(Sex.MALE);
							break;
						default:
							Activa.myMobileManager.user.setSex(Sex.FEMALE);
							break;
					}
					Activa.myMobileManager.user.setCountry(countries[country.getSelectedItemPosition()]);
					Runnable run = new Runnable() {
						@Override
						public void run() {
							handler.sendEmptyMessage(0);
							if (Activa.myMobileManager.updateUserData()) {
								handler.sendEmptyMessage(1);
							}
							else handler.sendEmptyMessage(2);
						}
						private Handler handler = new Handler(){
							@Override
							public void handleMessage(Message msg) {
								ImageView animationFrame;
								AnimationDrawable animation;
								switch (msg.what) {
									case 0:
										Activa.myUIManager.loadInfoPopupWithoutExiting(Activa.myLanguageManager.CONNECTION_CONNECTING);
										animationFrame = (ImageView) Activa.myApp.findViewById(R.id.popupImage);
										animationFrame.setVisibility(View.VISIBLE);
										animationFrame.setBackgroundResource(R.drawable.loading);
										animation = (AnimationDrawable) animationFrame.getBackground();
										animation.start();
										break;
									case 1:
										animationFrame = (ImageView) Activa.myApp.findViewById(R.id.popupImage);
										animation = (AnimationDrawable) animationFrame.getBackground();
										animation.stop();
										animationFrame.setVisibility(View.GONE);
										Activa.myUIManager.removeInfoPopup();
										Activa.myMobileManager.saveUsers();
										showOptions();
										break;
									case 2:
										animationFrame = (ImageView) Activa.myApp.findViewById(R.id.popupImage);
										animation = (AnimationDrawable) animationFrame.getBackground();
										animation.stop();
										animationFrame.setVisibility(View.GONE);
										Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.CONNECTION_FAILED);
										if (Activa.myMobileManager.user.getAmbient() == null) Activa.myUIManager.loadDefaultAmbient();
						        		else if (Activa.myMobileManager.user.getAmbient().equalsIgnoreCase("null")) Activa.myUIManager.loadDefaultAmbient();
						        		Activa.myUIManager.loadGeneratedMainScreen(true, false, false);
										break;
								}
							}
						};
					};
					Thread thread = new Thread(run);
					thread.start();
				} catch (Exception e) {
					Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.PSW_REG_BADDATA);
				} 
			}
		});
	}
	
	public void loadAdditionalDataScreen (final boolean returntoopt) {
		Activa.myApp.setContentView(R.layout.registerwebdata);
		final Spinner language = (Spinner) Activa.myApp.findViewById(R.id.language);
		final ArrayList<TimeZone> timezonesviewed = new ArrayList<TimeZone>();
		((TextView)Activa.myApp.findViewById(R.id.startText)).setText(Activa.myApp.getResources().getString(R.string.registerewebdatastart));
		((TextView)Activa.myApp.findViewById(R.id.languagerequest)).setText(Activa.myApp.getResources().getString(R.string.languagerequest));
		((TextView)Activa.myApp.findViewById(R.id.timezonerequest)).setText(Activa.myApp.getResources().getString(R.string.timezonerequest));
		((TextView)Activa.myApp.findViewById(R.id.privacyrequest)).setText(Activa.myApp.getResources().getString(R.string.privacyrequest));
		((TextView) Activa.myApp.findViewById(R.id.countryrequest)).setText(Activa.myLanguageManager.PSW_REG_COUNTRY);
		final com.o2hlink.activ8.common.entity.Language[] languages = com.o2hlink.activ8.common.entity.Language.values();
		ArrayList<String> languagestrings = new ArrayList<String>();
		int j = 0;
		int selected = 0;
		for (com.o2hlink.activ8.common.entity.Language ctr : languages) {
			languagestrings.add(ctr.getName());
			if ((Activa.myMobileManager.userForServices.getLanguage() != null)&&(ctr.getName().equals(Activa.myMobileManager.userForServices.getLanguage().getName()))) selected = j;
			j++;
		}
		ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(Activa.myApp, android.R.layout.simple_spinner_item, languagestrings);
	    adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    language.setAdapter(adapter3);
	    language.setSelection(selected);
	    final Country[] countries = Country.values();
		final Spinner country = (Spinner) Activa.myApp.findViewById(R.id.country);
		ArrayList<String> countrystrings = new ArrayList<String>();
		j = 0;
		selected = 0;
		for (Country ctr : countries) {
			String thecountry = ctr.getName();
			String mycountry;
			try {
				mycountry = Activa.myMobileManager.userForServices.getCountry().getName();
			} catch (Exception e) {
				mycountry = null;
			}
			countrystrings.add(thecountry);
			if ((mycountry != null)&&(thecountry.equalsIgnoreCase(mycountry))) selected = j;
			j++;
		}
		ArrayAdapter<String> adapter5 = new ArrayAdapter<String>(Activa.myApp, android.R.layout.simple_spinner_item, countrystrings);
	    adapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    country.setAdapter(adapter5);
	    country.setSelection(0);
	    if (countries != null) country.setSelection(selected);
	    country.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected (AdapterView<?> parent, View view, int position, long id) {
			    final Spinner timezone = (Spinner) Activa.myApp.findViewById(R.id.timezone);
				final TimeZone[] timezones = TimeZone.values();
				ArrayList<String> timezonestrings = new ArrayList<String>();
				int j = 0;
				int selected = 0;
				timezonesviewed.clear();
				for (TimeZone tmz : timezones) {
					Country thecountry = countries[position];
					String thetimezone = tmz.getName();
					String mytimezone;
					try {
						mytimezone = Activa.myMobileManager.userForServices.getTimeZone().getName();
					} catch (Exception e) {
						mytimezone = null;
					}
					if ((thecountry == null)||(thecountry.equals(tmz.getCountry()))) {
						timezonestrings.add(thetimezone);
						timezonesviewed.add(tmz);
						if ((mytimezone != null)&&(thetimezone.equalsIgnoreCase(mytimezone))) selected = j;
						j++;
					}
				}
				if (timezonestrings.size() == 0) {
					for (TimeZone tmz : timezones) {
						String thetimezone = tmz.getName();
						String mytimezone;
						try {
							mytimezone = Activa.myMobileManager.userForServices.getTimeZone().getName();
						} catch (Exception e) {
							mytimezone = null;
						}
						timezonestrings.add(thetimezone);
						timezonesviewed.add(tmz);
						if ((mytimezone != null)&&(thetimezone.equalsIgnoreCase(mytimezone))) selected = j;
						j++;
					}
				}
				ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(Activa.myApp, android.R.layout.simple_spinner_item, timezonestrings);
			    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			    timezone.setAdapter(adapter2);
			    timezone.setSelection(selected);
			}
			@Override
			public void onNothingSelected (AdapterView<?> parent) {
			    final Spinner timezone = (Spinner) Activa.myApp.findViewById(R.id.timezone);
				final TimeZone[] timezones = TimeZone.values();
				ArrayList<String> timezonestrings = new ArrayList<String>();
				timezonesviewed.clear();
				int j = 0;
				int selected = 0;
				for (TimeZone tmz : timezones) {
					Country thecountry = null;
					String thetimezone = tmz.getName();
					String mytimezone;
					try {
						mytimezone = Activa.myMobileManager.userForServices.getTimeZone().getName();
					} catch (Exception e) {
						mytimezone = null;
					}
					if ((thecountry == null)||(thecountry.equals(tmz.getCountry()))) {
						timezonestrings.add(thetimezone);
						timezonesviewed.add(tmz);
						if ((mytimezone != null)&&(thetimezone.equalsIgnoreCase(mytimezone))) selected = j;
						j++;
					}
				}
				if (timezonestrings.size() == 0) {
					for (TimeZone tmz : timezones) {
						String thetimezone = tmz.getName();
						String mytimezone;
						try {
							mytimezone = Activa.myMobileManager.userForServices.getTimeZone().getName();
						} catch (Exception e) {
							mytimezone = null;
						}
						timezonestrings.add(thetimezone);
						timezonesviewed.add(tmz);
						if ((mytimezone != null)&&(thetimezone.equalsIgnoreCase(mytimezone))) selected = j;
						j++;
					}
				}
				ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(Activa.myApp, android.R.layout.simple_spinner_item, timezonestrings);
			    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			    timezone.setAdapter(adapter2);
			    timezone.setSelection(selected);
			}
		});
	    final Spinner timezone = (Spinner) Activa.myApp.findViewById(R.id.timezone);
		final TimeZone[] timezones = TimeZone.values();
		final ArrayList<String> timezonestrings = new ArrayList<String>();
		j = 0;
		selected = 0;
		timezonesviewed.clear();
		for (TimeZone tmz : timezones) {
			Country thecountry = Activa.myMobileManager.userForServices.getCountry();
			String thetimezone = tmz.getName();
			String mytimezone;
			try {
				mytimezone = Activa.myMobileManager.userForServices.getTimeZone().getName();
			} catch (Exception e) {
				mytimezone = null;
			}
			if ((thecountry == null)||(thecountry.equals(tmz.getCountry()))) {
				timezonestrings.add(thetimezone);
				timezonesviewed.add(tmz);
				if ((mytimezone != null)&&(thetimezone.equalsIgnoreCase(mytimezone))) selected = j;
				j++;
			}
		}
		if (timezonestrings.size() == 0) {
			for (TimeZone tmz : timezones) {
				String thetimezone = tmz.getName();
				String mytimezone;
				try {
					mytimezone = Activa.myMobileManager.userForServices.getTimeZone().getName();
				} catch (Exception e) {
					mytimezone = null;
				}
				timezonestrings.add(thetimezone);
				timezonesviewed.add(tmz);
				if ((mytimezone != null)&&(thetimezone.equalsIgnoreCase(mytimezone))) selected = j;
				j++;
			}
		}
		ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(Activa.myApp, android.R.layout.simple_spinner_item, timezonestrings);
	    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    timezone.setAdapter(adapter2);
	    timezone.setSelection(selected);
	    String[] privacylevel = Activa.myApp.getResources().getStringArray(R.array.privacylevel);
	    ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(Activa.myApp, android.R.layout.simple_spinner_item, privacylevel);
		final Spinner privacy = (Spinner) Activa.myApp.findViewById(R.id.privacy);
		if (Activa.myMobileManager.userForServices.getPrivacyLevel() == null) privacy.setSelection(0);
		else if (Activa.myMobileManager.userForServices.getPrivacyLevel().equals(VisibilityLevel.PUBLIC_VIEW)) privacy.setSelection(0);
		else if (Activa.myMobileManager.userForServices.getPrivacyLevel().equals(VisibilityLevel.PRIVATE)) privacy.setSelection(1);
		else privacy.setSelection(0);
		((ImageButton)Activa.myApp.findViewById(R.id.ok)).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Activa.myMobileManager.userForServices.setTimeZone(timezonesviewed.get(timezone.getSelectedItemPosition()));
				Activa.myMobileManager.userForServices.setLanguage(languages[language.getSelectedItemPosition()]);
				if (countries != null) {
					Activa.myMobileManager.userForServices.setCountry(countries[country.getSelectedItemPosition()]);
					Activa.myMobileManager.user.setCountry(countries[country.getSelectedItemPosition()]);
				}
				if (privacy.getSelectedItemPosition() == 0) Activa.myMobileManager.userForServices.setPrivacyLevel(VisibilityLevel.PUBLIC_VIEW);
				else Activa.myMobileManager.userForServices.setPrivacyLevel(VisibilityLevel.PRIVATE);
				Activa.myMobileManager.updateUserData();
				if (returntoopt) showOptions();
				else loadInitialVideo();
			}
		});
	}
	
	public void loadRequestDataScreen() {
		Activa.myApp.setContentView(R.layout.data);
		boolean birthAsked = false;
		birthAsked = true;
		((TextView) Activa.myApp.findViewById(R.id.startText)).setText(Activa.myLanguageManager.SENSORS_WEIGHTHEIGTH_TITLE);
		((TextView) Activa.myApp.findViewById(R.id.requestText)).setText("");
		((TextView) Activa.myApp.findViewById(R.id.requestWeight)).setText(Activa.myLanguageManager.PSW_REG_WEIGHT);
		((TextView) Activa.myApp.findViewById(R.id.requestHeight)).setText(Activa.myLanguageManager.PSW_REG_HEIGHT);
		final EditText weight = (EditText) Activa.myApp.findViewById(R.id.weightText);
		final EditText height = (EditText) Activa.myApp.findViewById(R.id.heightText);
		final EditText date = (EditText) Activa.myApp.findViewById(R.id.dateText);
		if (birthAsked) {
			((TextView) Activa.myApp.findViewById(R.id.requestDate)).setText(Activa.myLanguageManager.PSW_REG_DATE);
			((TextView) Activa.myApp.findViewById(R.id.requestWeight)).setVisibility(View.VISIBLE);
			date.setVisibility(View.VISIBLE);
		}
		ImageButton ok = (ImageButton) Activa.myApp.findViewById(R.id.next);
		ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try{
					int heightText = Integer.parseInt(height.getText().toString());
					float weightText = Float.parseFloat(weight.getText().toString().replaceAll(",", "."));
					Activa.myMobileManager.user.setHeight(heightText);
					Activa.myMobileManager.user.setWeight(weightText);
					if (date.getVisibility() == View.VISIBLE)
						try {
							Activa.myMobileManager.user.setBirthdate(ActivaUtil.universalReadableStringToDate(date.getText().toString()));
						} catch (Exception e) {
							e.printStackTrace();
						}
					Activa.myMobileManager.user.setLastupdate(new Date());
					Activa.myMobileManager.saveUsers();
					Activa.myMobileManager.sendWeightAndHeight(weightText, heightText);
					if (Activa.mySensorManager.eventAssociated != null) {
						Activa.myUIManager.loadScheduleDay(new Date());
						Activa.mySensorManager.sensorList.get(SensorManager.ID_SPIROMETER).startMeasurement();
					}
					else {
						Activa.myUIManager.loadSensorList();
						Activa.mySensorManager.sensorList.get(SensorManager.ID_SPIROMETER).startMeasurement();
					}
				} catch (NumberFormatException e) {
					Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.PSW_REG_BADDATA);
				}
			}
		});
		ImageButton no = (ImageButton) Activa.myApp.findViewById(R.id.back);
		no.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Activa.myUIManager.loadGeneratedMainScreen(false, false, false);
			}
		});
	}
	
	public void loadMatrixPasswordForRegistering() {
		Activa.myMobileManager.password = "";
		Activa.myApp.setContentView(R.layout.password);
		((TextView) Activa.myApp.findViewById(R.id.startText)).setText(Activa.myLanguageManager.PSW_REQUEST);
		ImageButton add = (ImageButton) Activa.myApp.findViewById(R.id.add);
		add.setVisibility(View.GONE);
		final FrameLayout board = (FrameLayout) Activa.myApp.findViewById(R.id.passLayout);
		board.addView(new PasswordView(Activa.myApp, 1, Activa.myMobileManager.user));
	}
	
	public void loadRepeatPassword(User user, String prevPassword) {
		Activa.myMobileManager.password = "";
		Activa.myApp.setContentView(R.layout.password);
		((TextView) Activa.myApp.findViewById(R.id.startText)).setText(Activa.myLanguageManager.PSW_REQUEST_REPEAT);
		ImageButton add = (ImageButton) Activa.myApp.findViewById(R.id.add);
		add.setVisibility(View.GONE);
		final FrameLayout board = (FrameLayout) Activa.myApp.findViewById(R.id.passLayout);
		board.addView(new PasswordView(Activa.myApp, 2, user, prevPassword));
	}
	
	public void loadMatrixPasswordForChanging() {
		Activa.myMobileManager.password = "";
		Activa.myApp.setContentView(R.layout.password);
		((TextView) Activa.myApp.findViewById(R.id.startText)).setText(Activa.myLanguageManager.PSW_REQUEST_OLD);
		ImageButton add = (ImageButton) Activa.myApp.findViewById(R.id.add);
		add.setVisibility(View.GONE);
		final FrameLayout board = (FrameLayout) Activa.myApp.findViewById(R.id.passLayout);
		board.addView(new PasswordView(Activa.myApp, 3, Activa.myMobileManager.user));
	}
	
	public void loadNewPasswordForChanging(User user, String prevPassword) {
		Activa.myMobileManager.password = "";
		Activa.myApp.setContentView(R.layout.password);
		((TextView) Activa.myApp.findViewById(R.id.startText)).setText(Activa.myLanguageManager.PSW_REQUEST_NEW);
		ImageButton add = (ImageButton) Activa.myApp.findViewById(R.id.add);
		add.setVisibility(View.GONE);
		final FrameLayout board = (FrameLayout) Activa.myApp.findViewById(R.id.passLayout);
		board.addView(new PasswordView(Activa.myApp, 4, user, prevPassword));
	}
	
	public void loadRepeatPasswordForChanging(User user, String prevPassword) {
		Activa.myMobileManager.password = "";
		Activa.myApp.setContentView(R.layout.password);
		((TextView) Activa.myApp.findViewById(R.id.startText)).setText(Activa.myLanguageManager.PSW_REQUEST_NEW_REPEAT);
		ImageButton add = (ImageButton) Activa.myApp.findViewById(R.id.add);
		add.setVisibility(View.GONE);
		final FrameLayout board = (FrameLayout) Activa.myApp.findViewById(R.id.passLayout);
		board.addView(new PasswordView(Activa.myApp, 5, user, prevPassword));
	}
	
	/**
	 * Load the info about the logged user.
	 */
	public void loadUserInfoScreen(final User user, final boolean register) {
		this.state = UI_STATE_USERINFO;
		Activa.myMobileManager.identified = true;
		Activa.myApp.setContentView(R.layout.info);
		TextView text = (TextView) Activa.myApp.findViewById(R.id.textInfo);
		text.append(Activa.myLanguageManager.PSW_INFO_USER + user.getName() + Activa.myLanguageManager.PSW_INFO_REGISTERED);
		CountDownTimer timer = new CountDownTimer(3000,1000) {
			@Override
			public void onTick(long millisUntilFinished) {
			}
			@Override
			public void onFinish() {
				if (register) {
					Runnable run = new Runnable() {
						@Override
						public void run() {
							handler.sendEmptyMessage(0);
							if (Activa.myMobileManager.register()) {
								handler.sendEmptyMessage(1);
							}
							else handler.sendEmptyMessage(2);
						}
						private Handler handler = new Handler(){
							@Override
							public void handleMessage(Message msg) {
								ImageView animationFrame;
								AnimationDrawable animation;
								switch (msg.what) {
									case 0:
										Activa.myUIManager.loadInfoPopupWithoutExiting(Activa.myLanguageManager.CONNECTION_REGISTERING);
										animationFrame = (ImageView) Activa.myApp.findViewById(R.id.popupImage);
										animationFrame.setVisibility(View.VISIBLE);
										animationFrame.setBackgroundResource(R.drawable.loading);
										animation = (AnimationDrawable) animationFrame.getBackground();
										animation.start();
										break;
									case 1:
										animationFrame = (ImageView) Activa.myApp.findViewById(R.id.popupImage);
										animation = (AnimationDrawable) animationFrame.getBackground();
										animation.stop();
										animationFrame.setVisibility(View.GONE);
										Activa.myUIManager.removeInfoPopup();
										loadAdditionalDataScreen(false);
										break;
									case 2:
										animationFrame = (ImageView) Activa.myApp.findViewById(R.id.popupImage);
										animation = (AnimationDrawable) animationFrame.getBackground();
										animation.stop();
										animationFrame.setVisibility(View.GONE);
										Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.CONNECTION_FAILED);
										if (Activa.myMobileManager.user.getAmbient() == null) Activa.myUIManager.loadDefaultAmbient();
						        		else if (Activa.myMobileManager.user.getAmbient().equalsIgnoreCase("null")) Activa.myUIManager.loadDefaultAmbient();
						        		Activa.myUIManager.loadGeneratedMainScreen(true, false, false);
										break;
								}
							}
						};
					};
					Thread thread = new Thread(run);
					thread.start();
				}
				else {
					Runnable run = new Runnable() {
						@Override
						public void run() {
							handler.sendEmptyMessage(0);
							if (Activa.myMobileManager.login()) {
								handler.sendEmptyMessage(1);
							}
							else handler.sendEmptyMessage(2);
						}
						private Handler handler = new Handler(){
							@Override
							public void handleMessage(Message msg) {
								ImageView animationFrame;
								AnimationDrawable animation;
								switch (msg.what) {
									case 0:
										Activa.myUIManager.loadInfoPopupWithoutExiting(Activa.myLanguageManager.CONNECTION_CONNECTING);
										animationFrame = (ImageView) Activa.myApp.findViewById(R.id.popupImage);
										animationFrame.setVisibility(View.VISIBLE);
										animationFrame.setBackgroundResource(R.drawable.loading);
										animation = (AnimationDrawable) animationFrame.getBackground();
										animation.start();
										break;
									case 1:
										animationFrame = (ImageView) Activa.myApp.findViewById(R.id.popupImage);
										animation = (AnimationDrawable) animationFrame.getBackground();
										animation.stop();
										animationFrame.setVisibility(View.GONE);
										Activa.myUIManager.removeInfoPopup();
										loadAdditionalDataScreen(false);
										break;
									case 2:
										animationFrame = (ImageView) Activa.myApp.findViewById(R.id.popupImage);
										animation = (AnimationDrawable) animationFrame.getBackground();
										animation.stop();
										animationFrame.setVisibility(View.GONE);
										Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.CONNECTION_FAILED);
										if (Activa.myMobileManager.user.getAmbient() == null) Activa.myUIManager.loadDefaultAmbient();
						        		else if (Activa.myMobileManager.user.getAmbient().equalsIgnoreCase("null")) Activa.myUIManager.loadDefaultAmbient();
						        		Activa.myUIManager.loadGeneratedMainScreen(true, false, false);
										break;
								}
							}
						};
					};
					Thread thread = new Thread(run);
					thread.start();
				}	
			}
		};
		timer.start();
	}
	
	public void loadInitialVideo() {
//		Intent videoClient = new Intent(Intent.ACTION_VIEW);
//	    videoClient.setData(Uri.parse(Activa.myLanguageManager.initialVideoURL));
//	    videoClient.setClassName("com.google.android.youtube", "com.google.android.youtube.PlayerActivity");
//	    try {
//	    	Activa.myApp.startActivityForResult(videoClient, 220);
//	    } catch (ActivityNotFoundException e) {
//	    	e.printStackTrace();
    		Activa.myUIManager.loadGeneratedMainScreen(true, false, true);
//	    }
	}
	
	public void loadTextOnWindow(final String text) {
		Message msg = new Message();
		Bundle data = new Bundle();
		data.putString("0", text);
		msg.setData(data);
		Activa.myApp.handler.sendMessage(msg);
	}
 	
	/**
	 * Load the main screen
	 */
	
	public void loadGeneratedMainScreen(boolean login, boolean animated, boolean firsttime) {
		this.state = UI_STATE_MAIN;
		if (this.ambient == null) loadAmbient();
		if (!this.ambient.loaded) loadDefaultAmbient();
		this.state = UI_STATE_MAIN;
		this.lastSubenv = null;
		if (this.ambient.loaded) {
			// Get display info
			Display display = Activa.myApp.getWindowManager().getDefaultDisplay(); 
			int realwidth = display.getWidth();
			int realheight = display.getHeight();
			int barheight = (int) (((float)40/(float)480)*(float)realheight);
			float indexheight = ((float)realheight/(float)this.ambient.height);
			float indexwidthtemp = 1.0f;
			int i = 0;
			Subenvironment explsub = this.ambient.subenvironments.get(i);
			while (explsub.expanded) {
				i++;
				explsub = this.ambient.subenvironments.get(i);
				if (explsub == null) break;
			}
			if (explsub != null) {
				indexwidthtemp = ((float)realwidth/(float)explsub.filewidth);
			}
			final float indexwidth = indexwidthtemp;
			// Load scenario
			Activa.myApp.setContentView(R.layout.scenario);
			HorizontalScrollView scroll = (HorizontalScrollView) Activa.myApp.findViewById(R.id.ScrollView);
			((ImageView) Activa.myApp.findViewById(R.id.left)).setVisibility(View.GONE);
			((ImageView) Activa.myApp.findViewById(R.id.right)).setVisibility(View.VISIBLE);
			scroll.setOnTouchListener(new OnTouchListener() {
				@Override
				public boolean onTouch(final View v, MotionEvent event) {
					Runnable run = new Runnable() {
						public void run() {
							try {
								Thread.sleep(500);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							handler.sendEmptyMessage(0);
						}
						private Handler handler = new Handler() {

							@Override
							public void handleMessage(Message msg) {
								int x = v.getScrollX();
								int max = ambient.width - Activa.myApp.getWindowManager().getDefaultDisplay().getWidth();
								if (x == 0) {
									((ImageView) Activa.myApp.findViewById(R.id.left)).setVisibility(View.GONE);
									((ImageView) Activa.myApp.findViewById(R.id.right)).setVisibility(View.VISIBLE);
								} else if (x >= max) {
									((ImageView) Activa.myApp.findViewById(R.id.left)).setVisibility(View.VISIBLE);
									((ImageView) Activa.myApp.findViewById(R.id.right)).setVisibility(View.GONE);
								} else {
									((ImageView) Activa.myApp.findViewById(R.id.left)).setVisibility(View.VISIBLE);
									((ImageView) Activa.myApp.findViewById(R.id.right)).setVisibility(View.VISIBLE);
								} 
							}
						};
					};
					Thread thr = new Thread(run);
					thr.start();
					return false;
				}
			});
//			Toast toast = Toast.makeText(Activa.myApp, "Height: " + realheight + ", width: " + realwidth + ", indexheight: " + indexheight + ", indexwidth: " + indexwidth, Toast.LENGTH_LONG);
//			toast.show();
			// Load main image
			final AbsoluteLayout layout = (AbsoluteLayout) Activa.myApp.findViewById(R.id.Background);
//			layout.setLayoutParams(new LayoutParams((int) (((float)this.ambient.width)*indexwidth), LayoutParams.FILL_PARENT));
//			layout.setBackgroundDrawable(this.ambient.getBackground());
			final ImageView scenario = new ImageView(Activa.myApp);
			scenario.setLayoutParams(new AbsoluteLayout.LayoutParams((int) (((float)this.ambient.width)*indexwidth), AbsoluteLayout.LayoutParams.FILL_PARENT, 0, 0));
			scenario.setScaleType(ScaleType.FIT_XY);
			scenario.setImageDrawable(this.ambient.getBackground());
			layout.addView(scenario);
			layout.invalidate();
			// Load the context menu
			if (Activa.myMenu != null) {
				Activa.myMenu.clear();
				Activa.myInflater.inflate(R.menu.main, Activa.myMenu);
			}
			// Set initial defading if necessary
			if (!animated) {
				AnimationSet set = new AnimationSet(true);
				set.setFillAfter(true);
				AlphaAnimation animation = new AlphaAnimation(0.0f, 1.0f);
				animation.setDuration(1000);
				animation.setFillAfter(true);
				set.addAnimation(animation);
				LayoutAnimationController controller = new LayoutAnimationController(set, 0);
				layout.setAnimation(controller.getAnimation());
			}			
			//Load the refresh button
			ImageButton miscbutton = (ImageButton) Activa.myApp.findViewById(R.id.miscbutton);
			miscbutton.setBackgroundResource(R.drawable.refresh);
			miscbutton.setVisibility(View.VISIBLE);
			miscbutton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (Activa.myMobileManager.identified) {
						v.setVisibility(View.GONE);
						Activa.myUIManager.state = UIManager.UI_STATE_LOADING;
						final RelativeLayout popupView = (RelativeLayout) Activa.myApp.findViewById(R.id.popupView);
						popupView.setVisibility(View.VISIBLE);
						RefreshingConnection refreshing = new RefreshingConnection();
						Thread thread = new Thread(refreshing, "REFRESH");
						thread.start();
					}
					else{
						Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.USER_NOID);
					}
				}
			});
			//Load the options button
			ImageButton optionsbutton = (ImageButton) Activa.myApp.findViewById(R.id.options);
			optionsbutton.setBackgroundResource(R.drawable.options);
			optionsbutton.setVisibility(View.VISIBLE);
			optionsbutton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Runnable run = new Runnable() {
						@Override
						public void run() {
							handler.sendEmptyMessage(0);
							try {
								//TODO
								ambients = Activa.myProtocolManager.getPursachedAmbients();
								ambients.addAll(Activa.myProtocolManager.getNotPursachedAmbients());
								File originalfolder = new File(Environment.getExternalStorageDirectory(), ActivaConfig.ENVIRONMENT_FOLDER);
								if (!originalfolder.exists()) originalfolder.mkdir();
								File ambientdetailsfolder = new File(originalfolder, "ActivaAmbientDetails");
								if (!ambientdetailsfolder.exists()) ambientdetailsfolder.mkdir();
								for (int i = 0; i < ambients.size(); i++) {
									String ambientname = ambients.get(i).getId();
									String ambienturl = ambients.get(i).getUrlMobile();
									String ambientdetailfile = ambientname.replace("-", "") + ".jpg";
									String ambientminifile = ambientname.replace("-", "") + "mini.jpg";
									File ambientdetail = new File(ambientdetailsfolder, ambientdetailfile);
									if (!ambientdetail.exists()) ProtocolManager.downloadFile(ambienturl + "/detail.jpg", ambientdetail);
									File ambientmini = new File(ambientdetailsfolder, ambientminifile);
									if (!ambientmini.exists()) ProtocolManager.downloadFile(ambienturl + "/mini.jpg", ambientmini);
								}
							} catch (Exception e) {
								ambients = null;
								e.printStackTrace();
							}
							if (ambients != null)
								handler.sendEmptyMessage(1);
							else handler.sendEmptyMessage(2);
						}
						private Handler handler = new Handler(){
							@Override
							public void handleMessage(Message msg) {
								ImageView animationFrame;
								AnimationDrawable animation;
								switch (msg.what) {
									case 0:
										Activa.myUIManager.loadInfoPopupWithoutExiting(Activa.myLanguageManager.MAIN_LOAD_AMBIENTS);
										animationFrame = (ImageView) Activa.myApp.findViewById(R.id.popupImage);
										animationFrame.setVisibility(View.VISIBLE);
										animationFrame.setBackgroundResource(R.drawable.loading);
										animation = (AnimationDrawable) animationFrame.getBackground();
										animation.start();
										break;
									case 1:
										Activa.myUIManager.showAmbientsForLoading();
										break;
									case 2:
										Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.CONNECTION_FAILED);
										animationFrame = (ImageView) Activa.myApp.findViewById(R.id.popupImage);
										animationFrame.setVisibility(View.GONE);
										animation = (AnimationDrawable) animationFrame.getBackground();
										animation.stop();
										break;
								}
							}
						};
					};
					Thread thread = new Thread(run);
					thread.start();
				}
			});
			// Load the links to the subenvironments
			Enumeration<Subenvironment> subenvironments = this.ambient.subenvironments.elements();
			while(subenvironments.hasMoreElements()) {
				final Subenvironment subenvironment = subenvironments.nextElement();
				ImageButton button = new ImageButton(Activa.myApp);
				button.setClickable(false);
				button.setBackgroundResource(R.drawable.bgtouch);
				int top = (int) ((subenvironment.top*indexheight) - barheight*((float)subenvironment.top/(float)this.ambient.height));
				int left = (int) (subenvironment.left*indexwidth);
				int height = (int) (subenvironment.height*indexheight);
				int width = (int) (subenvironment.width*indexwidth);
				android.widget.AbsoluteLayout.LayoutParams params = new android.widget.AbsoluteLayout.LayoutParams(width, height, left, top);
				android.widget.AbsoluteLayout.LayoutParams textparams = new android.widget.AbsoluteLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, left + width/3, top);
//				final TextView text = new TextView(Activa.myApp);
//				text.setLayoutParams(textparams);
//				text.setTextSize((float) 15.0);
//				text.setPadding(10, 0, 0, 0);
//				text.setTextColor(Color.BLACK);
//				text.setText(subenvironment.name);
//				text.setGravity(Gravity.CENTER);
				final Button butt = new Button(Activa.myApp);
				butt.setBackgroundResource(R.drawable.cloud);
				butt.setLayoutParams(textparams);
				butt.setTextSize((float) 15.0);
				butt.setPadding(10, 0, 0, 0);
				butt.setTextColor(Color.BLACK);
				butt.setText(subenvironment.name);
				butt.setGravity(Gravity.CENTER);
				butt.setClickable(false);
				layout.addView(butt);
				CountDownTimer timer = new CountDownTimer(10000, 1000) {
					@Override
					public void onTick(long millisUntilFinished) {}
					@Override
					public void onFinish() {
						butt.setVisibility(View.GONE);
					}
				};
				timer.start();
				button.setLayoutParams(params);
				button.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						for (int i = 0; i < layout.getChildCount(); i++) {
							if (layout.getChildAt(i) instanceof ImageView) continue;
							layout.getChildAt(i).setVisibility(View.GONE);
						}
						if (subenvironment.animationframes > 0) {
							final AnimationDrawable animation = subenvironment.getAnimationMainToSub();
							if (animation != null) {
								ImageView animationFrame = new ImageView(Activa.myApp);
								animationFrame.setLayoutParams(new android.widget.FrameLayout.LayoutParams((int) (((float)Activa.myUIManager.ambient.width)*indexwidth), android.widget.FrameLayout.LayoutParams.FILL_PARENT));
//								android.widget.FrameLayout.LayoutParams params = new android.widget.FrameLayout.LayoutParams(Activa.myUIManager.ambient.width, android.widget.FrameLayout.LayoutParams.FILL_PARENT);
//								android.widget.FrameLayout.LayoutParams params = new android.widget.FrameLayout.LayoutParams(android.widget.FrameLayout.LayoutParams.WRAP_CONTENT, android.widget.FrameLayout.LayoutParams.FILL_PARENT);
//								animationFrame.setLayoutParams(params);
								animationFrame.setScaleType(ScaleType.FIT_XY);
								((FrameLayout)Activa.myApp.findViewById(R.id.basiclayout)).addView(animationFrame);
								animationFrame.setBackgroundDrawable(animation);
								switch (subenvironment.shownfrom) {
									case 0:
										scrollToLeft(animationFrame,  500, 1000);
										break;
									case 1:
										int center = (Activa.myUIManager.ambient.width/2)-(subenvironment.filewidth/2);
										scrollToCenter(animationFrame,  500, 1000, center);
										break;
									case 2:
										int right = (Activa.myUIManager.ambient.width)-(subenvironment.filewidth);
										scrollToRight(animationFrame,  500, 1000, right);
										break;
									default:
										center = (Activa.myUIManager.ambient.width/2)-(subenvironment.filewidth/2);
										scrollToCenter(animationFrame,  500, 1000, center);
										break;
								}
								animation.start();
								CountDownTimer timer = new CountDownTimer(2000,1000) {
									@Override
									public void onTick(long millisUntilFinished) {
									}
									@Override
									public void onFinish() {
										animation.setCallback(null);
										for (int i = 0; i < animation.getNumberOfFrames(); i++) {
											animation.getFrame(i).setCallback(null);
										}
										System.gc();
										loadGeneratedSubenvironment(subenvironment, true);
									}
								};
								timer.start();
							}
							else {
								AnimationSet set = new AnimationSet(true);
								set.setFillAfter(true);
								AlphaAnimation animationsec = new AlphaAnimation(1.0f, 0.0f);
								animationsec.setDuration(1000);
								animationsec.setFillAfter(true);
								set.addAnimation(animationsec);
								LayoutAnimationController controller = new LayoutAnimationController(set, 0);
								layout.startAnimation(controller.getAnimation());
								CountDownTimer timer = new CountDownTimer(2000,1000) {
									@Override
									public void onTick(long millisUntilFinished) {
									}
									@Override
									public void onFinish() {
										loadGeneratedSubenvironment(subenvironment, false);
									}
								};
								timer.start();
							}
						}
						else {
							AnimationSet set = new AnimationSet(true);
							set.setFillAfter(true);
							AlphaAnimation animationsec = new AlphaAnimation(1.0f, 0.0f);
							animationsec.setDuration(1000);
							animationsec.setFillAfter(true);
							set.addAnimation(animationsec);
							LayoutAnimationController controller = new LayoutAnimationController(set, 0);
							layout.startAnimation(controller.getAnimation());
							CountDownTimer timer = new CountDownTimer(2000,1000) {
								@Override
								public void onTick(long millisUntilFinished) {
								}
								@Override
								public void onFinish() {
									loadGeneratedSubenvironment(subenvironment, false);
								}
							};
							timer.start();
						}
					}
				});
				layout.addView(button);
			}
			// Load the links to the widgets
			Enumeration<Widget> widgets = this.ambient.widgets.elements();
			while(widgets.hasMoreElements()) {
				final Widget widget = widgets.nextElement();
				ImageButton button = new ImageButton(Activa.myApp);
				button.setBackgroundResource(R.drawable.bgtouch);
				int top = (int) ((widget.top*indexheight) - barheight*((float)widget.top/(float)this.ambient.height));
				int left = (int) (widget.left*indexwidth);
				int height = (int) (widget.height*indexheight);
				int width = (int) (widget.width*indexwidth);
				android.widget.AbsoluteLayout.LayoutParams textparams;
				if (widget.function == Widget.FUNCTION_MESSAGES) 
					textparams = new android.widget.AbsoluteLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, left, top);
				else switch (widget.cloudposition) {
					case 0:
						textparams = new android.widget.AbsoluteLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, left, top);
						break;
					case 1:
						textparams = new android.widget.AbsoluteLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, left, top + height/2 - 10);
						break;
					case 2:
						textparams = new android.widget.AbsoluteLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, left, top + height - 25);
						break;
					case 3:
						textparams = new android.widget.AbsoluteLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, left - width, top);
						break;
					case 4:
						textparams = new android.widget.AbsoluteLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, left - width, top + height/2 - 10);
						break;
					case 5:
						textparams = new android.widget.AbsoluteLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, left - width, top + height - 25);
						break;
					default:
						textparams = new android.widget.AbsoluteLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, left, top);
						break;
				}
//				final TextView text = new TextView(Activa.myApp);
//				text.setLayoutParams(textparams);
//				text.setTextSize((float) 15.0);
//				text.setPadding(10, 0, 0, 0);
//				text.setTextColor(Color.BLACK);
//				text.setText(widget);
//				text.setGravity(Gravity.CENTER);
				final Button butt = new Button(Activa.myApp);
				butt.setBackgroundResource(R.drawable.cloud);
				butt.setLayoutParams(textparams);
				butt.setTextSize((float) 15.0);
				butt.setPadding(10, 0, 0, 0);
				butt.setTextColor(Color.BLACK);
				butt.setText(widget.name);
				butt.setGravity(Gravity.CENTER);
				butt.setClickable(false);
				if (widget.function != Widget.FUNCTION_SMS) layout.addView(butt);
				CountDownTimer timer2 = new CountDownTimer(8000, 1000) {
					@Override
					public void onTick(long millisUntilFinished) {}
					@Override
					public void onFinish() {
						butt.setVisibility(View.GONE);
					}
				};
				timer2.start();
				android.widget.AbsoluteLayout.LayoutParams params = new android.widget.AbsoluteLayout.LayoutParams(width, height, left, top);
				button.setLayoutParams(params);
				button.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						widget.doFunction(null);
					}
				});
				button.setOnLongClickListener(new OnLongClickListener() {
					@Override
					public boolean onLongClick(View v) {
						AlertDialog.Builder builder = new AlertDialog.Builder(Activa.myApp);
						builder.setTitle(Activa.myLanguageManager.MAIN_AMBIENTS_PICKFUNCTION);
						builder.setItems(Widget.getFunctionNames(), new DialogInterface.OnClickListener() {
						    public void onClick(DialogInterface dialog, int item) {
						        Integer subenvironmentID = -1;
								Integer widgetID = ambient.getWidgetID(widget);
								if ((widgetID == -1)) return;
								ambient.changeWidgetFunctionality(subenvironmentID, widgetID, Widget.getFunctionIDs()[item]);
								loadGeneratedMainScreen(false, false, false);
						    }
						});
						AlertDialog alert = builder.create();
						alert.show();
						return false;
					}
				});
				layout.addView(button);
			}
			// Make the initial connection if necessary
			if (login) {
				if (Activa.myMobileManager.identified) {
					this.state = UI_STATE_LOADING;
					((ImageButton)Activa.myApp.findViewById(R.id.miscbutton)).setVisibility(View.GONE);
					final RelativeLayout popupView = (RelativeLayout) Activa.myApp.findViewById(R.id.popupView);
					popupView.setVisibility(View.VISIBLE);
					InitialConnection initial = new InitialConnection();
					Thread thread = new Thread(initial, "LOGIN");
					thread.start();
				}
				else{
					Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.USER_NOID);
					((ImageButton)Activa.myApp.findViewById(R.id.miscbutton)).setVisibility(View.VISIBLE);
				}
				try {
					((TextView)Activa.myApp.findViewById(R.id.popupPromotionText)).setVisibility(View.VISIBLE);
					((TextView)Activa.myApp.findViewById(R.id.popupPromotionText)).setText(Activa.myMobileManager.promotionString);
					((ImageView)Activa.myApp.findViewById(R.id.popupPromotion)).setVisibility(View.VISIBLE);
					((ImageView)Activa.myApp.findViewById(R.id.popupPromotion)).setImageDrawable(Drawable.createFromStream(new FileInputStream(Activa.myMobileManager.promotionMicro), "src"));
					((ImageView)Activa.myApp.findViewById(R.id.popupPromotion)).setOnClickListener(new OnClickListener() {						
						@Override
						public void onClick(View v) {
							if (Activa.myMobileManager.promotionUrl != null) {
								if (Activa.myMobileManager.promotionUrl.equals("0")) {
									Runnable run = new Runnable() {
										@Override
										public void run() {
											handler.sendEmptyMessage(0);
											try {
												//TODO
												ambients = Activa.myProtocolManager.getPursachedAmbients();
												ambients.addAll(Activa.myProtocolManager.getNotPursachedAmbients());
												File originalfolder = new File(Environment.getExternalStorageDirectory(), ActivaConfig.ENVIRONMENT_FOLDER);
												if (!originalfolder.exists()) originalfolder.mkdir();
												File ambientdetailsfolder = new File(originalfolder, "ActivaAmbientDetails");
												if (!ambientdetailsfolder.exists()) ambientdetailsfolder.mkdir();
												for (int i = 0; i < ambients.size(); i++) {
													String ambientname = ambients.get(i).getId();
													String ambienturl = ambients.get(i).getUrlMobile();
													String ambientdetailfile = ambientname.replace("-", "") + ".jpg";
													String ambientminifile = ambientname.replace("-", "") + "mini.jpg";
													File ambientdetail = new File(ambientdetailsfolder, ambientdetailfile);
													if (!ambientdetail.exists()) ProtocolManager.downloadFile(ambienturl + "/detail.jpg", ambientdetail);
													File ambientmini = new File(ambientdetailsfolder, ambientminifile);
													if (!ambientmini.exists()) ProtocolManager.downloadFile(ambienturl + "/mini.jpg", ambientmini);
												}
											} catch (Exception e) {
												ambients = null;
												e.printStackTrace();
											}
											if (ambients != null)
												handler.sendEmptyMessage(1);
											else handler.sendEmptyMessage(2);
										}
										private Handler handler = new Handler(){
											@Override
											public void handleMessage(Message msg) {
												switch (msg.what) {
													case 0:
														Activa.myUIManager.loadInfoPopupWithoutExiting(Activa.myLanguageManager.MAIN_LOAD_AMBIENTS);
														break;
													case 1:
														Activa.myUIManager.showAmbientsForLoading();
														break;
													case 2:
														Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.CONNECTION_FAILED);
														break;
												}
											}
										};
									};
									Thread thread = new Thread(run);
									thread.start();
									return;
								}
								else if (Activa.myMobileManager.promotionUrl.equals("1")) {
									Runnable run = new Runnable() {
										@Override
										public void run() {
											boolean success = Activa.myQuestControlManager.getAssignedQuestList();
											if (!success) handler.sendEmptyMessage(2);
											if (Activa.myQuestControlManager.questionnaires.containsKey(145l)) {
												handler.sendEmptyMessage(0);
											}
											else if (Activa.myQuestControlManager.questionnaires.containsKey(146l)) {
												handler.sendEmptyMessage(1);
											}
											else handler.sendEmptyMessage(2);
										}
										private Handler handler = new Handler(){
											@Override
											public void handleMessage(Message msg) {
												switch (msg.what) {
													case 0:
														startQuestionnaire(Activa.myQuestControlManager.questionnaires.get(145l));
														break;
													case 1:
														startQuestionnaire(Activa.myQuestControlManager.questionnaires.get(146l));
														break;
													case 2:
														Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.CONNECTION_FAILED);
														break;
												}
											}
										};
									};
									Thread thread = new Thread(run);
									thread.start();
									return;
								}
								else {
									Intent in = new Intent(Intent.ACTION_VIEW);
									in.setData(Uri.parse(Activa.myMobileManager.promotionUrl));
									try {
										Activa.myApp.startActivity(in);
									} catch (Exception e) {}
								}
							}
						}
					});
//					((ImageView)Activa.myApp.findViewById(R.id.promotion)).setVisibility(View.VISIBLE);
//					((ImageView)Activa.myApp.findViewById(R.id.promotion)).setImageDrawable(Drawable.createFromStream(new FileInputStream(Activa.myMobileManager.promotionMini), "src"));
//					((ImageView)Activa.myApp.findViewById(R.id.promotion)).setOnClickListener(new OnClickListener() {						
//						@Override
//						public void onClick(View v) {
//							Intent in = new Intent(Intent.ACTION_VIEW);
//							in.setData(Uri.parse(Activa.myMobileManager.promotionUrl));
//							Activa.myApp.startActivity(in);
//						}
//					});
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
			// Make necessary to wait 1 second until moving to a subenvironment (need in order to avoid memory leaks)
			CountDownTimer timer = new CountDownTimer(1000, 1000) {
				@Override
				public void onTick(long millisUntilFinished) {}
				@Override
				public void onFinish() {
					for (int i = 0; i < layout.getChildCount(); i++) {
						layout.getChildAt(i).setClickable(true);
					}
				}
			};
			timer.start();
		}
	}
	
	public void loadGeneratedSubenvironment(final Subenvironment sub, final boolean animated) {
		this.state = UI_STATE_SUBENVIRONMENT;
		if (sub == null) {
			loadGeneratedMainScreen(false, false, false);
			return;
		}
		this.lastSubenv = sub;
		if (this.ambient.loaded) {
			// Load scenario
			if (sub.expanded) {
				Activa.myApp.setContentView(R.layout.scenario);
				switch (sub.shownfrom) {
					case 0:
						break;
					case 1:
						HorizontalScrollView scroll = (HorizontalScrollView) Activa.myApp.findViewById(R.id.ScrollView);
						int realwidth = Activa.myApp.getResources().getDisplayMetrics().widthPixels;
						int middle = ((scroll.getMaxScrollAmount() + realwidth) / 2) - (realwidth/2) ;
						scroll.scrollTo(-middle, 0);
						break;
					case 2:
						HorizontalScrollView scroll2 = (HorizontalScrollView) Activa.myApp.findViewById(R.id.ScrollView);
						int right = scroll2.getMaxScrollAmount();
						scroll2.scrollTo(-right, 0);
						break;
					default:
						break;
				}
				HorizontalScrollView scroll = (HorizontalScrollView) Activa.myApp.findViewById(R.id.ScrollView);
				((ImageView) Activa.myApp.findViewById(R.id.left)).setVisibility(View.GONE);
				((ImageView) Activa.myApp.findViewById(R.id.right)).setVisibility(View.VISIBLE);
				scroll.setOnTouchListener(new OnTouchListener() {
					@Override
					public boolean onTouch(final View v, MotionEvent event) {
						Runnable run = new Runnable() {
							public void run() {
								try {
									Thread.sleep(500);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
								handler.sendEmptyMessage(0);
							}
							private Handler handler = new Handler() {

								@Override
								public void handleMessage(Message msg) {
									int x = v.getScrollX();
									int max = sub.filewidth - Activa.myApp.getWindowManager().getDefaultDisplay().getWidth();
									try{ 
										if (x == 0) {
											((ImageView) Activa.myApp.findViewById(R.id.left)).setVisibility(View.GONE);
											((ImageView) Activa.myApp.findViewById(R.id.right)).setVisibility(View.VISIBLE);
										} else if (x >= max) {
											((ImageView) Activa.myApp.findViewById(R.id.left)).setVisibility(View.VISIBLE);
											((ImageView) Activa.myApp.findViewById(R.id.right)).setVisibility(View.GONE);
										} else {
											((ImageView) Activa.myApp.findViewById(R.id.left)).setVisibility(View.VISIBLE);
											((ImageView) Activa.myApp.findViewById(R.id.right)).setVisibility(View.VISIBLE);
										} 
									} catch (Exception e) {
									}
								}
							};
						};
						Thread thr = new Thread(run);
						thr.start();
						return false;
					}
				});
			}
			else Activa.myApp.setContentView(R.layout.scenariocompressed); 
			// Get display settings
			Display display = Activa.myApp.getWindowManager().getDefaultDisplay(); 
			int realwidth = display.getWidth();
			int realheight = display.getHeight();
			int barheight = (int) (((float)40/(float)480)*(float)realheight);
			float indexheight = ((float)realheight/(float)sub.fileheight);
//			float indexwidth = ((float)realwidth/(float)sub.filewidth);
//			if (sub.expanded)
//				if (indexwidth < 1) indexwidth = 1.0f;
			float indexwidthtemp = 1.0f;
			if (sub.expanded) {
				int i = 0;
				Subenvironment explsub = this.ambient.subenvironments.get(i);
				while (explsub.expanded) {
					i++;
					explsub = this.ambient.subenvironments.get(i);
					if (explsub == null) break;
				}
				if (explsub != null) {
					indexwidthtemp = ((float)realwidth/(float)explsub.filewidth);
				}
			}
			else indexwidthtemp = ((float)realwidth/(float)sub.filewidth);
			final float indexwidth = indexwidthtemp;
//			Toast toast = Toast.makeText(Activa.myApp, "Height: " + realheight + ", width: " + realwidth + ", indexheight: " + indexheight + ", indexwidth: " + indexwidth, Toast.LENGTH_LONG);
//			toast.show();
			// Load main image
			final AbsoluteLayout layout = (AbsoluteLayout) Activa.myApp.findViewById(R.id.Background);
			// Set if necessary the defading
			if (!animated) {
				AnimationSet set = new AnimationSet(true);
				set.setFillAfter(true);
				AlphaAnimation animation = new AlphaAnimation(0.0f, 1.0f);
				animation.setDuration(1000);
				animation.setFillAfter(true);
				set.addAnimation(animation);
				LayoutAnimationController controller = new LayoutAnimationController(set, 0);
				layout.setAnimation(controller.getAnimation());
			}
//			layout.setBackgroundDrawable(sub.getBackground());
			final ImageView scenario = new ImageView(Activa.myApp);
			scenario.setLayoutParams(new AbsoluteLayout.LayoutParams((int) (((float)sub.filewidth)*indexwidth), AbsoluteLayout.LayoutParams.FILL_PARENT, 0, 0));
			scenario.setScaleType(ScaleType.FIT_XY);
			scenario.setImageDrawable(sub.getBackground());
			layout.addView(scenario);
			layout.invalidate();
			// Load the context menu
			if (Activa.myMenu != null) {
				Activa.myMenu.clear();
				Activa.myInflater.inflate(R.menu.main, Activa.myMenu);
			}
			//Load the outside button
			ImageButton miscbutton = (ImageButton) Activa.myApp.findViewById(R.id.miscbutton);
			miscbutton.setVisibility(View.GONE);
			miscbutton.setBackgroundResource(R.drawable.outside);
			miscbutton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					for (int i = 0; i < layout.getChildCount(); i++) {
						if (layout.getChildAt(i) instanceof ImageView) continue;
						layout.getChildAt(i).setVisibility(View.GONE);
					}
					if (sub.animationframes > 0) {
						final AnimationDrawable animation = sub.getAnimationSubToMain();
						if (animation != null) {
							ImageView animationFrame = new ImageView(Activa.myApp);
							animationFrame.setLayoutParams(new android.widget.FrameLayout.LayoutParams((int) (((float)Activa.myUIManager.ambient.width)*indexwidth), android.widget.FrameLayout.LayoutParams.FILL_PARENT));
//							android.widget.FrameLayout.LayoutParams params = new android.widget.FrameLayout.LayoutParams(Activa.myUIManager.ambient.width, android.widget.FrameLayout.LayoutParams.FILL_PARENT);
//							animationFrame.setLayoutParams(params);
							animationFrame.setScaleType(ScaleType.FIT_XY);
							((FrameLayout)Activa.myApp.findViewById(R.id.basiclayout)).addView(animationFrame);
							animationFrame.setVisibility(View.VISIBLE);
							animationFrame.setBackgroundDrawable(animation);
							if (!sub.expanded) {
								switch (sub.shownfrom) {
									case 0:
										scrollToInitCompressedLeft(animationFrame,  500, 1000);
										break;
									case 1:
										int center = (Activa.myUIManager.ambient.width/2)-(sub.filewidth/2);
										scrollToInitCompressedMiddle(animationFrame,  500, 1000, center);
										break;
									case 2:
										int right = (Activa.myUIManager.ambient.width)-(sub.filewidth);
										scrollToInitCompressedRight(animationFrame,  500, 1000, right);
										break;
									default:
										break;
								}
							} else {
								scrollToInit(animationFrame,  500, 1000);
							}
							animation.start();
							CountDownTimer timer = new CountDownTimer(2000,1000) {
								@Override
								public void onTick(long millisUntilFinished) {
								}
								@Override
								public void onFinish() {
									animation.setCallback(null);
									for (int i = 0; i < animation.getNumberOfFrames(); i++) {
										animation.getFrame(i).setCallback(null);
									}
									System.gc();
									loadGeneratedMainScreen(false, true, false);
								}
							};
							timer.start();
						}
						else {
							AnimationSet set = new AnimationSet(true);
							set.setFillAfter(true);
							AlphaAnimation animationsec = new AlphaAnimation(1.0f, 0.0f);
							animationsec.setDuration(1000);
							animationsec.setFillAfter(true);
							set.addAnimation(animationsec);
							LayoutAnimationController controller = new LayoutAnimationController(set, 0);
							AbsoluteLayout layout = (AbsoluteLayout) Activa.myApp.findViewById(R.id.Background);
							layout.startAnimation(controller.getAnimation());
							CountDownTimer timer = new CountDownTimer(2000,1000) {
								@Override
								public void onTick(long millisUntilFinished) {
								}
								@Override
								public void onFinish() {
									loadGeneratedMainScreen(false, false, false);
								}
							};
							timer.start();
						}
					}
					else {
						AnimationSet set = new AnimationSet(true);
						set.setFillAfter(true);
						AlphaAnimation animationsec = new AlphaAnimation(1.0f, 0.0f);
						animationsec.setDuration(1000);
						animationsec.setFillAfter(true);
						set.addAnimation(animationsec);
						LayoutAnimationController controller = new LayoutAnimationController(set, 0);
						AbsoluteLayout layout = (AbsoluteLayout) Activa.myApp.findViewById(R.id.Background);
						layout.startAnimation(controller.getAnimation());
						CountDownTimer timer = new CountDownTimer(2000,1000) {
							@Override
							public void onTick(long millisUntilFinished) {
							}
							@Override
							public void onFinish() {
								loadGeneratedMainScreen(false, false, false);
							}
						};
						timer.start();
					}
				}
			});
			// Set the need for waiting 1 second until move back to the main environment (in order to avoid memory leaks)
			CountDownTimer timer = new CountDownTimer(1000, 1000) {
				@Override
				public void onTick(long millisUntilFinished) {}
				@Override
				public void onFinish() {
					ImageButton miscbutton = (ImageButton) Activa.myApp.findViewById(R.id.miscbutton);
					if (miscbutton != null) miscbutton.setVisibility(View.VISIBLE);
				}
			};
			timer.start();
			// Load the links to the widgets
			Enumeration<Widget> widgets = sub.widgets.elements();
			while(widgets.hasMoreElements()) {
				final Widget widget = widgets.nextElement();
				ImageButton button = new ImageButton(Activa.myApp);
				button.setBackgroundResource(R.drawable.bgtouch);
				int top = (int) ((widget.top*indexheight) - barheight*((float)widget.top/(float)sub.fileheight));
				int left = (int) (widget.left*indexwidth);
				int height = (int) (widget.height*indexheight);
				int width = (int) (widget.width*indexwidth);
				android.widget.AbsoluteLayout.LayoutParams textparams;
				if (widget.function == Widget.FUNCTION_MESSAGES) 
					textparams = new android.widget.AbsoluteLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, left, top);
				else switch (widget.cloudposition) {
					case 0:
						textparams = new android.widget.AbsoluteLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, left, top);
						break;
					case 1:
						textparams = new android.widget.AbsoluteLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, left, top + height/2 - 10);
						break;
					case 2:
						textparams = new android.widget.AbsoluteLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, left, top + height - 25);
						break;
					case 3:
						textparams = new android.widget.AbsoluteLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, left - width, top);
						break;
					case 4:
						textparams = new android.widget.AbsoluteLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, left - width, top + height/2 - 10);
						break;
					case 5:
						textparams = new android.widget.AbsoluteLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, left - width, top + height - 25);
						break;
					default:
						textparams = new android.widget.AbsoluteLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, left, top);
						break;
				}
//				final TextView text = new TextView(Activa.myApp);
//				text.setLayoutParams(textparams);
//				text.setTextSize((float) 15.0);
//				text.setPadding(10, 0, 0, 0);
//				text.setTextColor(Color.BLACK);
//				text.setText(widget);
//				text.setGravity(Gravity.CENTER);
				final Button butt = new Button(Activa.myApp);
				butt.setBackgroundResource(R.drawable.cloud);
				butt.setLayoutParams(textparams);
				butt.setTextSize((float) 15.0);
				butt.setPadding(10, 0, 0, 0);
				butt.setTextColor(Color.BLACK);
				butt.setText(widget.name);
				butt.setGravity(Gravity.CENTER);
				butt.setClickable(false);
				if (widget.function != Widget.FUNCTION_SMS) layout.addView(butt);
				CountDownTimer timer2 = new CountDownTimer(8000, 1000) {
					@Override
					public void onTick(long millisUntilFinished) {}
					@Override
					public void onFinish() {
						butt.setVisibility(View.GONE);
					}
				};
				timer2.start();
				android.widget.AbsoluteLayout.LayoutParams params = new android.widget.AbsoluteLayout.LayoutParams(width, height, left, top);
				button.setLayoutParams(params);
				button.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						widget.doFunction(sub);
					}
				});
				button.setOnLongClickListener(new OnLongClickListener() {
					@Override
					public boolean onLongClick(View v) {
						AlertDialog.Builder builder = new AlertDialog.Builder(Activa.myApp);
						builder.setTitle(Activa.myLanguageManager.MAIN_AMBIENTS_PICKFUNCTION);
						builder.setItems(Widget.getFunctionNames(), new DialogInterface.OnClickListener() {
						    public void onClick(DialogInterface dialog, int item) {
						        Integer subenvironmentID = ambient.getSubenvironmentID(sub);
								Integer widgetID = sub.getWidgetID(widget);
								if ((widgetID == -1)&&(subenvironmentID == -1)) return;
								ambient.changeWidgetFunctionality(subenvironmentID, widgetID, Widget.getFunctionIDs()[item]);
								loadGeneratedSubenvironment(sub, animated);
						    }
						});
						AlertDialog alert = builder.create();
						alert.show();
						return false;
					}
				});
				layout.addView(button);
			}
		}
	}
	
	private void scrollToCenter(View view, int time, int delay, int middle) {
		AnimationSet set = new AnimationSet(true);
		set.setFillAfter(true);
		HorizontalScrollView scroll = (HorizontalScrollView) Activa.myApp.findViewById(R.id.ScrollView);
		int x = scroll.getScrollX();
		TranslateAnimation animation = new TranslateAnimation(-x, -middle, 0, 0);
		animation.setDuration(time);
		animation.setFillAfter(true);
		set.addAnimation(animation);
		LayoutAnimationController controller = new LayoutAnimationController(set, delay);
		view.setAnimation(controller.getAnimation());
	}
	
	private void scrollToLeft(View view, int time, int delay) {
		AnimationSet set = new AnimationSet(true);
		set.setFillAfter(true);
		HorizontalScrollView scroll = (HorizontalScrollView) Activa.myApp.findViewById(R.id.ScrollView);
		int x = scroll.getScrollX();
		TranslateAnimation animation = new TranslateAnimation(-x, 0, 0, 0);
		animation.setDuration(time);
		animation.setFillAfter(true);
		set.addAnimation(animation);
		LayoutAnimationController controller = new LayoutAnimationController(set, delay);
		view.setAnimation(controller.getAnimation());
	}
	
	private void scrollToRight(View view, int time, int delay, int right) {
		AnimationSet set = new AnimationSet(true);
		set.setFillAfter(true);
		HorizontalScrollView scroll = (HorizontalScrollView) Activa.myApp.findViewById(R.id.ScrollView);
		int x = scroll.getScrollX();
		TranslateAnimation animation = new TranslateAnimation(-x, -right, 0, 0);
		animation.setDuration(time);
		animation.setFillAfter(true);
		set.addAnimation(animation);
		LayoutAnimationController controller = new LayoutAnimationController(set, delay);
		view.setAnimation(controller.getAnimation());
	}
	
	private void scrollToInit(View view, int time, int delay) {
		AnimationSet set = new AnimationSet(true);
		set.setFillAfter(true);
		HorizontalScrollView scroll = (HorizontalScrollView) Activa.myApp.findViewById(R.id.ScrollView);
		int x = scroll.getScrollX();
		TranslateAnimation animation = new TranslateAnimation(-x, 0, 0, 0);
		animation.setDuration(time);
		animation.setFillAfter(true);
		set.addAnimation(animation);
		LayoutAnimationController controller = new LayoutAnimationController(set, delay);
		view.setAnimation(controller.getAnimation());
	}
	
	private void scrollToInitCompressedLeft(View view, int time, int delay) {
		return;
	}
	
	private void scrollToInitCompressedMiddle(View view, int time, int delay, int middle) {
		AnimationSet set = new AnimationSet(true);
		set.setFillAfter(true);
		TranslateAnimation animation = new TranslateAnimation(-middle, 0, 0, 0);
		animation.setDuration(time);
		animation.setFillAfter(true);
		set.addAnimation(animation);
		LayoutAnimationController controller = new LayoutAnimationController(set, delay);
		view.setAnimation(controller.getAnimation());
	}
	
	private void scrollToInitCompressedRight(View view, int time, int delay, int right) {
		AnimationSet set = new AnimationSet(true);
		set.setFillAfter(true);
		TranslateAnimation animation = new TranslateAnimation(-right, 0, 0, 0);
		animation.setDuration(time);
		animation.setFillAfter(true);
		set.addAnimation(animation);
		LayoutAnimationController controller = new LayoutAnimationController(set, delay);
		view.setAnimation(controller.getAnimation());
	}
	
	/**
	 * Load the list of questionnaires to do.
	 */
	public void loadTotalQuestList() {
		if (Activa.myMobileManager.userForServices instanceof com.o2hlink.activ8.client.entity.Patient) Activa.myUIManager.loadQuestList();
		else Activa.myUIManager.loadSharedQuestionnaires();
	}
	
	public void loadQuestList() {
		this.state = UI_STATE_TOTALQUESTIONNAIRE;
		Activa.myApp.setContentView(R.layout.list);
		((TextView) Activa.myApp.findViewById(R.id.startText)).setText(Activa.myLanguageManager.QUEST_ASSIGNED_TITLE);
		TableLayout questlisting = (TableLayout)Activa.myApp.findViewById(R.id.listing);
		Enumeration<Questionnaire> enumer = Activa.myQuestControlManager.questionnaires.elements();
		while (enumer.hasMoreElements()) {			
			final Questionnaire quest = enumer.nextElement();
			TableRow buttonLayout = new TableRow(Activa.myApp);
			buttonLayout.setOrientation(TableRow.HORIZONTAL);
			buttonLayout.setGravity(Gravity.CENTER_VERTICAL);
			buttonLayout.setLayoutParams(new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));			
			ImageButton button = new ImageButton(Activa.myApp);
			button.setBackgroundResource(R.drawable.iconbg);
			button.setImageResource(R.drawable.quest);
			OnClickListener listener = new OnClickListener() {
				@Override
				public void onClick(View v) {
					Activa.myQuestControlManager.eventAssociated = null;
					startQuestionnaire(quest);
				}
			};
			TextView text = new TextView(Activa.myApp);
			text.append(quest.getName());
			text.setMaxWidth(240);
			text.setTextSize((float) 20);
			text.setTextColor(Color.BLACK);
			text.setTypeface(Typeface.SANS_SERIF);
			buttonLayout.addView(button);
			buttonLayout.addView(text);
			button.setOnClickListener(listener);
			text.setOnClickListener(listener);
			buttonLayout.setOnClickListener(listener);
			questlisting.addView(buttonLayout);
		}
		ImageButton back = (ImageButton) Activa.myApp.findViewById(R.id.previous);
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Activa.myUIManager.loadGeneratedSubenvironment(Activa.myUIManager.lastSubenv, true);
			}
		});
		ImageButton change = (ImageButton) Activa.myApp.findViewById(R.id.help);
		change.setImageResource(R.drawable.refreshing);
		change.setVisibility(View.VISIBLE);
		change.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				loadAssignedQuestionnaires();			
			}
		});
	}

	public void loadQuestion(final Question question) {
		this.state = UI_STATE_QUESTION;
		TextView questionText;
		ImageButton next;
		if (question == null) {
			loadTotalQuestList();
			loadInfoPopup("QUESTIONNAIRE MALFORMED");
		}
		if (question instanceof MultiQuestion) {
			MultiQuestion multiquest = (MultiQuestion) question;
			final MultiAnswer answer = new MultiAnswer();
			answer.setQuestion(question.getId());
			if (Activa.myQuestControlManager.activeAnswers.get(question.getId()).isEmpty()) {
				Activa.myApp.setContentView(R.layout.info);
				((RelativeLayout)Activa.myApp.findViewById(R.id.questcontrol)).setVisibility(View.VISIBLE);
				TextView text = (TextView) Activa.myApp.findViewById(R.id.textInfo); 
				text.setText(question.getName());
				text.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
				((ImageButton) Activa.myApp.findViewById(R.id.previous)).setVisibility(View.VISIBLE);
				next = (ImageButton) Activa.myApp.findViewById(R.id.next);
				next.setVisibility(View.VISIBLE);
				next.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						if (question.getNext() != null) Activa.myUIManager.loadQuestion(Activa.myQuestControlManager.activeQuestions.get(question.getNext()));	
						else Activa.myQuestControlManager.finishQuestionnaire();
					}
				});
		    }
			else if (multiquest.isMultiple()) {
				Activa.myApp.setContentView(R.layout.poliquestion);
				questionText = (TextView) Activa.myApp.findViewById(R.id.questionText);
				questionText.setText(multiquest.getName());
				final Array<Answer> answers = Activa.myQuestControlManager.activeAnswers.get(question.getId());
				next = (ImageButton) Activa.myApp.findViewById(R.id.next);
				LinearLayout answersGroupPoli = (LinearLayout) Activa.myApp.findViewById(R.id.options);
				for (int i = 0; i < answers.size(); i++) {
					CheckBox button = new CheckBox(Activa.myApp);
					button.setText(answers.get(i).getName());
					button.setTextColor(Color.BLACK);
					button.setId(i);
					button.setLayoutParams(new LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
					answersGroupPoli.addView(button);
					button.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {	
						@Override
						public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
							if (isChecked) {
								if (!answer.getAnswers().contains(answers.get(buttonView.getId()).getId())) answer.getAnswers().add(answers.get(buttonView.getId()).getId());
							}
							else if (answer.getAnswers().contains(answers.get(buttonView.getId()).getId())) answer.getAnswers().remove(answers.get(buttonView.getId()).getId()); 
						}
					});
				}
				next.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						if (answer.getAnswers().isEmpty()) if (!question.isOptional()) return;
						Activa.myQuestControlManager.activeAnswersAnswered.push(answer);
						Activa.myQuestControlManager.activeQuestionsAnswered.push(question);
						Long next = Activa.myQuestControlManager.calcMultiNextQuestion(question.getId(), answer);
						if (next == null) Activa.myQuestControlManager.finishQuestionnaire();
						else Activa.myUIManager.loadQuestion(Activa.myQuestControlManager.activeQuestions.get(next));
					}
				});
			}
			else {
				final Array<Answer> answers = Activa.myQuestControlManager.activeAnswers.get(question.getId());
		    	Activa.myApp.setContentView(R.layout.monoquestion);
				questionText = (TextView) Activa.myApp.findViewById(R.id.questionText);
				questionText.setText(multiquest.getName());
				RadioGroup answersGroup = (RadioGroup) Activa.myApp.findViewById(R.id.options);
				next = (ImageButton) Activa.myApp.findViewById(R.id.next);
				for (int i = 0; i < answers.size(); i++) {
					RadioButton button = new RadioButton(Activa.myApp);
					button.setText(answers.get(i).getName());
					button.setTextColor(Color.BLACK);
					button.setId(i);
					button.setLayoutParams(new LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT));
					answersGroup.addView(button);
				}
				answersGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {					
					@Override
					public void onCheckedChanged(RadioGroup group, int checkedId) {
						answer.getAnswers().clear();
						answer.getAnswers().add(answers.get(checkedId).getId());
					}
				});
				next.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						if (answer.getAnswers().isEmpty()) if (!question.isOptional()) return;
						Activa.myQuestControlManager.activeAnswersAnswered.push(answer);
						Activa.myQuestControlManager.activeQuestionsAnswered.push(question);
						Long next = Activa.myQuestControlManager.calcNextQuestion(question.getId(), answer);
						if (next == null) Activa.myQuestControlManager.finishQuestionnaire();
						else Activa.myUIManager.loadQuestion(Activa.myQuestControlManager.activeQuestions.get(next));
					}
				});
			}
	    }
	    else if (question instanceof NumericQuestion) {
	    	NumericQuestion numquest = (NumericQuestion) question;
	    	final NumericAnswer answer = new NumericAnswer();
			answer.setQuestion(question.getId());
			Activa.myApp.setContentView(R.layout.numquestion);
			final String representation[] = {Activa.myLanguageManager.BORG_0, Activa.myLanguageManager.BORG_05, 
					Activa.myLanguageManager.BORG_1, Activa.myLanguageManager.BORG_1, 
					Activa.myLanguageManager.BORG_2, Activa.myLanguageManager.BORG_2, 
					Activa.myLanguageManager.BORG_3, Activa.myLanguageManager.BORG_3, 
					Activa.myLanguageManager.BORG_4, Activa.myLanguageManager.BORG_4, 
					Activa.myLanguageManager.BORG_5, Activa.myLanguageManager.BORG_5, 
					Activa.myLanguageManager.BORG_5, Activa.myLanguageManager.BORG_5, 
					Activa.myLanguageManager.BORG_7, Activa.myLanguageManager.BORG_7, 
					Activa.myLanguageManager.BORG_7, Activa.myLanguageManager.BORG_7, 
					Activa.myLanguageManager.BORG_9, Activa.myLanguageManager.BORG_9, 
					Activa.myLanguageManager.BORG_10, Activa.myLanguageManager.BORG_10};
			questionText = (TextView) Activa.myApp.findViewById(R.id.questionText);
			final TextView numText = (TextView) Activa.myApp.findViewById(R.id.numText);
			questionText.setText(numquest.getName());
			numText.setText("0 - " + representation[0]);
			SeekBar seekbar = (SeekBar) Activa.myApp.findViewById(R.id.seekbar);
			seekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {				
				@Override
				public void onStopTrackingTouch(SeekBar seekBar) {
					
				}
				@Override
				public void onStartTrackingTouch(SeekBar seekBar) {
					
				}
				@Override
				public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
					int selection = progress/5;
					if ((selection == 1)) numText.setText("0.5 - " + representation[1]);
					else numText.setText("" + progress/10 + " - " + representation[selection]);
				}
			});
			next = (ImageButton) Activa.myApp.findViewById(R.id.next);
			next.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					SeekBar seekbar = (SeekBar) Activa.myApp.findViewById(R.id.seekbar);
					if ((seekbar.getProgress()/5) == 1) answer.setValue(0.5f);
					else answer.setValue((double)(seekbar.getProgress()/10)); 
					Activa.myQuestControlManager.activeAnswersAnswered.push(answer);
					Activa.myQuestControlManager.activeQuestionsAnswered.push(question);
					Long next = Activa.myQuestControlManager.calcNumericNextQuestion(question.getId(), answer);
					if (next != null) Activa.myUIManager.loadQuestion(Activa.myQuestControlManager.activeQuestions.get(next));
					else Activa.myQuestControlManager.finishQuestionnaire();
				}
			});
	    }
	    else if (question instanceof TextQuestion) {
	    	TextQuestion textquest = (TextQuestion) question;
	    	final TextAnswer answer = new TextAnswer();
			answer.setQuestion(question.getId());
			Activa.myApp.setContentView(R.layout.textquestion);
			questionText = (TextView) Activa.myApp.findViewById(R.id.questionText);
			questionText.setText(textquest.getName());
			next = (ImageButton) Activa.myApp.findViewById(R.id.next);
			next.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					EditText editable = (EditText) Activa.myApp.findViewById(R.id.editable);
					answer.setValue(editable.getText().toString());
					Activa.myQuestControlManager.activeAnswersAnswered.push(answer);
					Activa.myQuestControlManager.activeQuestionsAnswered.push(question);
					if (question.getNext() == null)	Activa.myQuestControlManager.finishQuestionnaire();
					else Activa.myUIManager.loadQuestion(Activa.myQuestControlManager.activeQuestions.get(question.getNext()));	
				}
			});
	    }
	    else {
			Activa.myApp.setContentView(R.layout.info);
			((RelativeLayout)Activa.myApp.findViewById(R.id.questcontrol)).setVisibility(View.VISIBLE);
			TextView text = (TextView) Activa.myApp.findViewById(R.id.textInfo); 
			text.setText(question.getName());
			text.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
			((ImageButton) Activa.myApp.findViewById(R.id.previous)).setVisibility(View.VISIBLE);
			next = (ImageButton) Activa.myApp.findViewById(R.id.next);
			next.setVisibility(View.VISIBLE);
			next.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (question.getNext() != null) Activa.myUIManager.loadQuestion(Activa.myQuestControlManager.activeQuestions.get(question.getNext()));	
					else Activa.myQuestControlManager.finishQuestionnaire();
				}
			});
	    }
		ImageButton back = (ImageButton) Activa.myApp.findViewById(R.id.previous);
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!Activa.myQuestControlManager.activeQuestionsAnswered.isEmpty()) {
					Question prev = Activa.myQuestControlManager.activeQuestionsAnswered.pop();
					Activa.myQuestControlManager.activeAnswersAnswered.pop();
					Activa.myUIManager.loadQuestion(prev);
				}
				else {
					Activa.myUIManager.loadTotalQuestList();
				}
			}
		});
		ImageButton quit = (ImageButton) Activa.myApp.findViewById(R.id.quit);
		quit.setVisibility(View.VISIBLE);
		quit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Activa.myUIManager.loadTotalQuestList();
			}
		});
	}
	
	/**
	 * This method start the filling of the selected as active questionnaire.
	 */
	
	public void startQuestionnaire(final Questionnaire quest) {
		Activa.myUIManager.state = UIManager.UI_STATE_QUESTIONNAIREINIT;
		Activa.myApp.setContentView(R.layout.yesnoquestion);
		TextView question = (TextView)Activa.myApp.findViewById(R.id.question);
		question.append(Activa.myLanguageManager.QUEST_START + quest.getName() + "?");
		ImageButton yes = (ImageButton)Activa.myApp.findViewById(R.id.yes);
		ImageButton no = (ImageButton)Activa.myApp.findViewById(R.id.no);
		yes.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Runnable run = new Runnable() {
					@Override
					public void run() {
						handler.sendEmptyMessage(0);
						if (Activa.myQuestControlManager.getQuestionnaire(quest.getId())) {
							if (Activa.myQuestControlManager.validateQuestionnaire()) handler.sendEmptyMessage(1);
							else handler.sendEmptyMessage(3);
						}
						else handler.sendEmptyMessage(2);
					}
					private Handler handler = new Handler(){
						@Override
						public void handleMessage(Message msg) {
							ImageView animationFrame;
							AnimationDrawable animation;
							switch (msg.what) {
								case 0:
									Activa.myUIManager.loadInfoPopupWithoutExiting(Activa.myLanguageManager.NOTIFICATION_DOWNLOADING + quest.getName() + " ...");
									animationFrame = (ImageView) Activa.myApp.findViewById(R.id.popupImage);
									animationFrame.setVisibility(View.VISIBLE);
									animationFrame.setBackgroundResource(R.drawable.loading);
									animation = (AnimationDrawable) animationFrame.getBackground();
									animation.start();
									break;
								case 1:
									animationFrame = (ImageView) Activa.myApp.findViewById(R.id.popupImage);
									animation = (AnimationDrawable) animationFrame.getBackground();
									animation.stop();
									animationFrame.setVisibility(View.GONE);
									Activa.myQuestControlManager.startQuestionnaire(quest);
									break;
								case 2:
									animationFrame = (ImageView) Activa.myApp.findViewById(R.id.popupImage);
									animation = (AnimationDrawable) animationFrame.getBackground();
									animation.stop();
									animationFrame.setVisibility(View.GONE);
									if (Activa.myQuestControlManager.eventAssociated == null) Activa.myUIManager.loadTotalQuestList();
									else Activa.myUIManager.loadScheduleDay(new Date());
									Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.CONNECTION_FAILED);
									break;
								case 3:
									animationFrame = (ImageView) Activa.myApp.findViewById(R.id.popupImage);
									animation = (AnimationDrawable) animationFrame.getBackground();
									animation.stop();
									animationFrame.setVisibility(View.GONE);
									if (Activa.myQuestControlManager.eventAssociated == null) Activa.myUIManager.loadTotalQuestList();
									else Activa.myUIManager.loadScheduleDay(new Date());
									Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.QUEST_NOTVALID);
									break;
							}
						}
					};
				};
				Thread thread = new Thread(run);
				thread.start();
			}
		});
		no.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (Activa.mySensorManager.eventAssociated != null) Activa.myUIManager.loadScheduleDay(Activa.mySensorManager.eventAssociated.getDate());
				else if (Activa.myMobileManager.userForServices instanceof com.o2hlink.activ8.client.entity.Patient) Activa.myUIManager.loadTotalQuestList();
				else Activa.myUIManager.loadSharedQuestionnaires();
			}
		});
	}

	public void loadQuestResult(double result) {
		Activa.myApp.setContentView(R.layout.resultimage);
		TextView text = (TextView) Activa.myApp.findViewById(R.id.textInfo);
		if (result < 0.0f) text.setText(Activa.myLanguageManager.QUEST_WEIGHT_NOTDONE);
		else text.setText(Activa.myLanguageManager.QUEST_WEIGHT_DONE + String.format("%.1f", result) + " %. ");
		ImageView resultText = (ImageView) Activa.myApp.findViewById(R.id.result);
		if (result < 0.0) {
		}
		else if (result < 33.33) {
			text.append(Activa.myLanguageManager.QUEST_WEIGHT_0);
			resultText.setBackgroundResource(R.drawable.lightgreen);
		}
		else if (result < 66.66) {
			text.append(Activa.myLanguageManager.QUEST_WEIGHT_1);
			resultText.setBackgroundResource(R.drawable.lightorange);
		}
		else {
			text.append(Activa.myLanguageManager.QUEST_WEIGHT_2);
			resultText.setBackgroundResource(R.drawable.lightwhite);
		}
		CountDownTimer timer = new CountDownTimer(3000, 1000) {
			@Override
			public void onTick(long millisUntilFinished) {}
			@Override
			public void onFinish() {
				Activa.myApp.setContentView(R.layout.sending);
				SendQuestionnaire sending = new SendQuestionnaire(Activa.myQuestControlManager.activeQuestionnaire.getId(), Activa.myQuestControlManager.getQuestToSend());
				Thread thread = new Thread(sending, "SENDQUESTIONNAIRE");
				thread.start();
			}
		};
		timer.start();
	}
	
	public void loadSensorList() {
		this.state = UI_STATE_TOTALSENSOR;
		int width = Activa.myApp.getResources().getDisplayMetrics().widthPixels - 130;
		Activa.myApp.setContentView(R.layout.list);
		if (Activa.myMenu != null) {
			Activa.myMenu.clear();
			Activa.myInflater.inflate(R.menu.sensors, Activa.myMenu);
		}
		((TextView) Activa.myApp.findViewById(R.id.startText)).append(Activa.myLanguageManager.SENSORS_TITLE);
		TableLayout sensorlisting = (TableLayout)Activa.myApp.findViewById(R.id.listing);
		Enumeration<Sensor> enumer = Activa.mySensorManager.sensorList.elements();
		Enumeration<Long> sensorIDs = Activa.mySensorManager.sensorList.keys();
		while (sensorIDs.hasMoreElements()) {
			final Sensor sensor = Activa.mySensorManager.sensorList.get(sensorIDs.nextElement());
			TableRow buttonLayout = new TableRow(Activa.myApp);
			buttonLayout.setOrientation(TableRow.HORIZONTAL);
			buttonLayout.setGravity(Gravity.CENTER_VERTICAL);
			buttonLayout.setLayoutParams(new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));			
			ImageButton button = new ImageButton(Activa.myApp);
			button.setBackgroundResource(R.drawable.iconbg);
			button.setImageResource(sensor.icon);
			OnClickListener listener = new OnClickListener() {			
				@Override
				public void onClick(View v) {
					if (Activa.myMenu != null) Activa.myMenu.clear();
					Activa.mySensorManager.eventAssociated = null;
					Activa.mySensorManager.startSensorMeasurement(sensor.id);
				}
			};
			button.setOnClickListener(listener);
			TextView text = new TextView(Activa.myApp);
			text.setLayoutParams(new android.widget.TableRow.LayoutParams(width, android.widget.TableRow.LayoutParams.WRAP_CONTENT));
			text.append(sensor.name);
			text.setTextSize((float) 20);
			text.setTextColor(Color.BLACK);
			text.setTypeface(Typeface.SANS_SERIF);
			text.setOnClickListener(listener);
			ImageButton helpsensor = new ImageButton(Activa.myApp);
			helpsensor.setLayoutParams(new android.widget.TableRow.LayoutParams(45, 45));
			helpsensor.setBackgroundResource(R.drawable.iconbg);
			helpsensor.setImageResource(R.drawable.help);
			helpsensor.setScaleType(ScaleType.FIT_XY);
			helpsensor.setOnClickListener(new OnClickListener() {			
				@Override
				public void onClick(View v) {
					String textToPut = "";
					if (sensor.id == SensorManager.ID_PULSIOXYMETER) {
						textToPut = Activa.myLanguageManager.TEXT_SENSORPAIR + Activa.myLanguageManager.TEXT_PULSEOXYMETRY;
					}
					else if (sensor.id == SensorManager.ID_SPIROMETER) {
						textToPut = Activa.myLanguageManager.TEXT_SENSORPAIR + Activa.myLanguageManager.TEXT_SPIROMETRY;
					}
					else if (sensor.id == SensorManager.ID_EXERCISE) {
						textToPut = Activa.myLanguageManager.TEXT_SENSORPAIR + Activa.myLanguageManager.TEXT_EXERCISE;
					}
					else if (sensor.id == SensorManager.ID_SIXMINUTES) {
						textToPut = Activa.myLanguageManager.TEXT_SENSORPAIR + Activa.myLanguageManager.TEXT_SIXMINUTESWALK;
					}
					else if (sensor.id == SensorManager.ID_WEIGHT) {
						textToPut = Activa.myLanguageManager.TEXT_SENSORPAIR + Activa.myLanguageManager.TEXT_WEIGHTHEIGHT;
					}
					LayoutInflater inflater = (LayoutInflater) Activa.myApp.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
					final View layout = inflater.inflate(R.layout.toasttext,
							(ViewGroup) Activa.myApp.findViewById(R.id.toasttextroot));
					TextView text = (TextView) layout.findViewById(R.id.searchexpl);
					text.setText(Html.fromHtml(textToPut));
					Builder builder = new AlertDialog.Builder(Activa.myApp);
					builder.setView(layout);
					final AlertDialog alertDialog = builder.create();
					alertDialog.show();
				}
			});
			button.setOnClickListener(listener);
			buttonLayout.addView(button);
			buttonLayout.addView(text);
			buttonLayout.addView(helpsensor);
			buttonLayout.setOnClickListener(listener);
			button.setOnClickListener(listener);
			text.setOnClickListener(listener);
			sensorlisting.addView(buttonLayout);
		}
		ImageButton help = (ImageButton) Activa.myApp.findViewById(R.id.help);
		help.setVisibility(View.INVISIBLE);
		ImageButton back = (ImageButton) Activa.myApp.findViewById(R.id.previous);
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Activa.myUIManager.loadGeneratedSubenvironment(Activa.myUIManager.lastSubenv, true);
			}
		});
	}
	
	public void loadScheduleDay(final Date dategiven) {
		TextView time;
		Event event = null; 
		Date date = new Date((dategiven.getTime()/3600000)*3600000);
		date.setHours(0);
		Date dateLater = new Date(date.getTime());
		dateLater.setHours(dateLater.getHours() + 1);
		TableRow buttonLayout;
		this.state = UI_STATE_SCHEDULE;
		if (Activa.myMenu != null) {
			Activa.myMenu.clear();
			Activa.myInflater.inflate(R.menu.schedule, Activa.myMenu);
		}
		Hashtable<Date, Event> eventsOrdered = new Hashtable<Date,Event>();
		Vector<Date> datesOrdered = new Vector<Date>();
		Enumeration<Event> enumer = Activa.myCalendarManager.events.elements();
		while (enumer.hasMoreElements()) {
			Event temp = enumer.nextElement();
			while (datesOrdered.contains(temp.date)) temp.date.setTime(temp.date.getTime() + 1);
			Timestamp stamp = new Timestamp(temp.date.getTime());
			datesOrdered.add(stamp);
			eventsOrdered.put(stamp, temp);
		}
		Collections.sort(datesOrdered);
		Activa.myApp.setContentView(R.layout.scheduleday);
		((TextView) Activa.myApp.findViewById(R.id.startText)).setText(Activa.myLanguageManager.CALENDAR_TITLE);
		ImageButton back = (ImageButton) Activa.myApp.findViewById(R.id.previous);
		ImageButton prev = (ImageButton) Activa.myApp.findViewById(R.id.previousday);
		ImageButton next = (ImageButton) Activa.myApp.findViewById(R.id.nextday);
		TextView dateText = (TextView) Activa.myApp.findViewById(R.id.date);
		dateText.setText(ActivaUtil.dateToReadableString(date));
		TableLayout schedule = (TableLayout)Activa.myApp.findViewById(R.id.schedule);
		for (int i = 0; i < 24; i++) {	
			buttonLayout = new TableRow(Activa.myApp);
			buttonLayout.setOrientation(TableRow.HORIZONTAL);
			buttonLayout.setGravity(Gravity.CENTER_VERTICAL);
			buttonLayout.setLayoutParams(new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, 40));			
			time = new TextView(Activa.myApp);
			time.setText(String.format("%02d:00", date.getHours()));
			time.setPadding(5, 10, 5, 10);
			time.setTextSize(20);
			time.setTextColor(Color.BLACK);
			time.setTypeface(Typeface.SANS_SERIF);
			buttonLayout.addView(time);
			for(int j = 0; j < datesOrdered.size(); j++) {
				event = eventsOrdered.get(datesOrdered.get(j));
				final String id = event.id;
				if (date.compareTo(event.getDate()) == 0) {
					TextView activity = new TextView(Activa.myApp);
					activity.setLayoutParams(new TableRow.LayoutParams(200, LayoutParams.WRAP_CONTENT));
					activity.setText(event.name);
					activity.setPadding(5, 10, 5, 10);
					activity.setTextSize(20);
					activity.setTextColor(Color.BLACK);
					activity.setTypeface(Typeface.SANS_SERIF);
					ImageView button = new ImageView(Activa.myApp);
					switch (event.state) {
						case 0:
							button.setBackgroundResource(R.drawable.oksmall);
							break;
						case 1:
							button.setBackgroundResource(R.drawable.cancel);
							break;
						case 2:
							button.setBackgroundResource(R.drawable.nextsmall);
							break;
					}
					if (event.type == 2) button.setBackgroundResource(R.drawable.event);
					buttonLayout.addView(activity);
					buttonLayout.addView(button);	
					time.setOnClickListener(new OnClickListener() {				
						@Override
						public void onClick(View v) {
							if (Activa.myMenu != null) Activa.myMenu.clear();
							Activa.myCalendarManager.events.get(id).doActivity();
						}
					});	
					activity.setOnClickListener(new OnClickListener() {				
						@Override
						public void onClick(View v) {
							if (Activa.myMenu != null) Activa.myMenu.clear();
							Activa.myCalendarManager.events.get(id).doActivity();
						}
					});
					button.setOnClickListener(new OnClickListener() {				
						@Override
						public void onClick(View v) {
							if (Activa.myMenu != null) Activa.myMenu.clear();
							Activa.myCalendarManager.events.get(id).doActivity();
						}
					});	
					buttonLayout.setOnClickListener(new OnClickListener() {				
						@Override
						public void onClick(View v) {
							if (Activa.myMenu != null) Activa.myMenu.clear();
							Activa.myCalendarManager.events.get(id).doActivity();
						}
					});
				}
				else if ((date.compareTo(event.getDate()) < 0)&&(dateLater.compareTo(event.getDate()) > 0)) {
					schedule.addView(buttonLayout);
					View separator = new View(Activa.myApp);
					LayoutParams separatorLayout = new LayoutParams(android.view.ViewGroup.LayoutParams.FILL_PARENT, 1);
					separator.setLayoutParams(separatorLayout);
					separator.setBackgroundColor(Color.BLACK);
					schedule.addView(separator);
					buttonLayout = new TableRow(Activa.myApp);
					buttonLayout.setOrientation(TableRow.HORIZONTAL);
					buttonLayout.setGravity(Gravity.CENTER_VERTICAL);
					buttonLayout.setLayoutParams(new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, 40));			
					time = new TextView(Activa.myApp);
					time.setPadding(5, 10, 5, 10);
					time.setTextSize(20);
					time.setTextColor(Color.BLACK);
					time.setTypeface(Typeface.SANS_SERIF);
					buttonLayout.addView(time);
					time.setText(String.format("%02d:%02d", event.date.getHours(), event.date.getMinutes()));
					TextView activity = new TextView(Activa.myApp);
					activity.setLayoutParams(new TableRow.LayoutParams(200, LayoutParams.WRAP_CONTENT));
					activity.setText(event.name);
					activity.setWidth(200);
					activity.setPadding(5, 10, 5, 10);
					activity.setTextSize(20);
					activity.setTextColor(Color.BLACK);
					activity.setTypeface(Typeface.SANS_SERIF);
					ImageView button = new ImageView(Activa.myApp);
					switch (event.state) {
						case 0:
							button.setBackgroundResource(R.drawable.oksmall);
							break;
						case 1:
							button.setBackgroundResource(R.drawable.cancel);
							break;
						case 2:
							button.setBackgroundResource(R.drawable.nextsmall);
							break;
					}
					if (event.type == 2) button.setBackgroundResource(R.drawable.event);
					buttonLayout.addView(activity);
					buttonLayout.addView(button);	
					time.setOnClickListener(new OnClickListener() {				
						@Override
						public void onClick(View v) {
							if (Activa.myMenu != null) Activa.myMenu.clear();
							Activa.myCalendarManager.events.get(id).doActivity();
						}
					});	
					activity.setOnClickListener(new OnClickListener() {				
						@Override
						public void onClick(View v) {
							if (Activa.myMenu != null) Activa.myMenu.clear();
							Activa.myCalendarManager.events.get(id).doActivity();
						}
					});
					button.setOnClickListener(new OnClickListener() {				
						@Override
						public void onClick(View v) {
							if (Activa.myMenu != null) Activa.myMenu.clear();
							Activa.myCalendarManager.events.get(id).doActivity();
						}
					});	
					buttonLayout.setOnClickListener(new OnClickListener() {				
						@Override
						public void onClick(View v) {
							if (Activa.myMenu != null) Activa.myMenu.clear();
							Activa.myCalendarManager.events.get(id).doActivity();
						}
					});	
				}
			}
			schedule.addView(buttonLayout);
			View separator = new View(Activa.myApp);
			LayoutParams separatorLayout = new LayoutParams(android.view.ViewGroup.LayoutParams.FILL_PARENT, 1);
			separator.setLayoutParams(separatorLayout);
			separator.setBackgroundColor(Color.BLACK);
			
			schedule.addView(separator);

			date.setHours(date.getHours() + 1);
			dateLater.setHours(dateLater.getHours() + 1);
		}
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Activa.myUIManager.loadGeneratedSubenvironment(Activa.myUIManager.lastSubenv, true);
			}
		});
		prev.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Runnable run = new Runnable() {
					@Override
					public void run() {
						handler.sendEmptyMessage(0);
						Date start = new Date((dategiven.getTime()/86400000)*86400000);
						Date end = new Date(start.getTime());
						start.setDate(start.getDate() - 1);
						if (Activa.myCalendarManager.getCalendar(start, end))
							handler.sendEmptyMessage(1);
						else handler.sendEmptyMessage(2);
					}
					private Handler handler = new Handler() {
						@Override
						public void handleMessage(Message msg) {
							Date nextDate;
							switch (msg.what) {
								case 0:
									ImageView animationFrame = (ImageView) Activa.myApp.findViewById(R.id.animation);
									animationFrame.setVisibility(View.VISIBLE);
									animationFrame.setBackgroundResource(R.drawable.loading);
									final AnimationDrawable animation = (AnimationDrawable) animationFrame.getBackground();
									animation.start();
									break;
								case 1:
									nextDate = new Date(dategiven.getTime());
									nextDate.setDate(nextDate.getDate() - 1);
									Activa.myUIManager.loadScheduleDay(nextDate);
									break;
								case 2:
									nextDate = new Date(dategiven.getTime());
									nextDate.setDate(nextDate.getDate() - 1);
									Activa.myUIManager.loadScheduleDay(nextDate);
									Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.CONNECTION_FAILED);
									break;
							}
						}

					};
				};
				Thread thread = new Thread(run);
				thread.start();
			}
		});
		next.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Runnable run = new Runnable() {
					@Override
					public void run() {
						handler.sendEmptyMessage(0);
						Date start = new Date((dategiven.getTime()/86400000)*86400000);
						start.setDate(start.getDate() + 1);
						Date end = new Date(start.getTime());
						end.setDate(end.getDate() + 1);
						if (Activa.myCalendarManager.getCalendar(start, end))
							handler.sendEmptyMessage(1);
						else handler.sendEmptyMessage(2);
					}
					private Handler handler = new Handler() {
						@Override
						public void handleMessage(Message msg) {
							Date nextDate;
							switch (msg.what) {
								case 0:
									ImageView animationFrame = (ImageView) Activa.myApp.findViewById(R.id.animation);
									animationFrame.setVisibility(View.VISIBLE);
									animationFrame.setBackgroundResource(R.drawable.loading);
									final AnimationDrawable animation = (AnimationDrawable) animationFrame.getBackground();
									animation.start();
									break;
								case 1:
									nextDate = new Date(dategiven.getTime());
									nextDate.setDate(nextDate.getDate() + 1);
									Activa.myUIManager.loadScheduleDay(nextDate);
									break;
								case 2:
									nextDate = new Date(dategiven.getTime());
									nextDate.setDate(nextDate.getDate() + 1);
									Activa.myUIManager.loadScheduleDay(nextDate);
									Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.CONNECTION_FAILED);
									break;
							}
						}

					};
				};
				Thread thread = new Thread(run);
				thread.start();
			}
		});
		ImageView tocal = (ImageView) Activa.myApp.findViewById(R.id.animation);
		tocal.setVisibility(View.VISIBLE);
		try {
	        PackageManager manager = Activa.myApp.getPackageManager();
			tocal.setBackgroundDrawable(manager.getActivityIcon(new ComponentName(Activa.myExteriorManager.externalApplications.get(ExteriorManager.APP_CALENDAR).packageName, Activa.myExteriorManager.externalApplications.get(ExteriorManager.APP_CALENDAR).activityName)));
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		tocal.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Activa.myExteriorManager.externalApplications.get(ExteriorManager.APP_CALENDAR).startApplication();
			}
		});
		ImageButton add = (ImageButton) Activa.myApp.findViewById(R.id.help);
		add.setImageResource(R.drawable.plus);
		add.setVisibility(View.VISIBLE);
		add.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				LayoutInflater inflater = (LayoutInflater) Activa.myApp.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				final View layout = inflater.inflate(R.layout.toastaddevent,
				                               (ViewGroup) Activa.myApp.findViewById(R.id.toastaddeventroot));
				TextView title = (TextView) layout.findViewById(R.id.addtitle);
				title.setText(Activa.myLanguageManager.NOTIFICATION_EVENT_TITLE);
				TextView namereq = (TextView) layout.findViewById(R.id.namerequested);
				namereq.setText(Activa.myLanguageManager.NOTIFICATION_EVENT_NAMEREQ);
				TextView descreq = (TextView) layout.findViewById(R.id.descrequest);
				descreq.setText(Activa.myLanguageManager.NOTIFICATION_EVENT_DESC);
				TextView startreq = (TextView) layout.findViewById(R.id.startrequest);
				startreq.setText(Activa.myLanguageManager.NOTIFICATION_EVENT_START);
				TextView endreq = (TextView) layout.findViewById(R.id.endrequest);
				endreq.setText(Activa.myLanguageManager.NOTIFICATION_EVENT_END);
				TextView freqreq = (TextView) layout.findViewById(R.id.freqrequest);
				freqreq.setText(Activa.myLanguageManager.NOTIFICATION_EVENT_FREQ);
				final Spinner frequency = (Spinner) layout.findViewById(R.id.freq);
			    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(Activa.myApp, R.array.freqarray, android.R.layout.simple_spinner_item);
			    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			    frequency.setAdapter(adapter);
			    ((LinearLayout)layout.findViewById(R.id.freqend)).setVisibility(View.GONE);
			    frequency.setOnItemSelectedListener(new OnItemSelectedListener() {
					@Override
					public void onItemSelected (AdapterView<?> parent, View view, int position, long id) {
						if (position == 0) ((LinearLayout)layout.findViewById(R.id.freqend)).setVisibility(View.GONE);
						else ((LinearLayout)layout.findViewById(R.id.freqend)).setVisibility(View.VISIBLE);
					}
					@Override
					public void onNothingSelected (AdapterView<?> parent) {
						((LinearLayout)layout.findViewById(R.id.freqend)).setVisibility(View.GONE);
					}
				});
				TextView freqendreq = (TextView) layout.findViewById(R.id.freqendrequest);
				freqendreq.setText(Activa.myLanguageManager.NOTIFICATION_EVENT_FREQEND);
				ImageButton image = (ImageButton) layout.findViewById(R.id.addeventok);
				Builder builder = new AlertDialog.Builder(Activa.myApp);
				builder.setView(layout);
				final AlertDialog alertDialog = builder.create();
				alertDialog.show();
				image.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						final com.o2hlink.activ8.client.entity.Event eventtoadd = new com.o2hlink.activ8.client.entity.Event();
						eventtoadd.setName(((EditText) layout.findViewById(R.id.name)).getText().toString());
						eventtoadd.setDescription(((EditText) layout.findViewById(R.id.description)).getText().toString());
						Date start = new Date(0);
						start.setYear(((DatePicker) layout.findViewById(R.id.startdate)).getYear() - 1900);
						start.setMonth(((DatePicker) layout.findViewById(R.id.startdate)).getMonth());
						start.setDate(((DatePicker) layout.findViewById(R.id.startdate)).getDayOfMonth());
						start.setHours(((TimePicker) layout.findViewById(R.id.starttime)).getCurrentHour());
						start.setMinutes(((TimePicker) layout.findViewById(R.id.starttime)).getCurrentMinute());
						eventtoadd.setStart(start);
						Date end = new Date(0);
						end.setYear(((DatePicker) layout.findViewById(R.id.enddate)).getYear() - 1900);
						end.setMonth(((DatePicker) layout.findViewById(R.id.enddate)).getMonth());
						end.setDate(((DatePicker) layout.findViewById(R.id.enddate)).getDayOfMonth());
						end.setHours(((TimePicker) layout.findViewById(R.id.endtime)).getCurrentHour());
						end.setMinutes(((TimePicker) layout.findViewById(R.id.endtime)).getCurrentMinute());
						eventtoadd.setEnd(end);
						switch (frequency.getSelectedItemPosition()) {
							case 0:
								eventtoadd.setFrequency(EventFrequency.NONE);
								break;
							case 1:
								eventtoadd.setFrequency(EventFrequency.DAILY);
								break;
							case 2:
								eventtoadd.setFrequency(EventFrequency.WEEKLY);
								break;
							case 3:
								eventtoadd.setFrequency(EventFrequency.MONTHLY);
								break;
							case 4:
								eventtoadd.setFrequency(EventFrequency.YEARLY);
								break;
							case 5:
								eventtoadd.setFrequency(EventFrequency.MONWEDFRI);
								break;
							case 6:
								eventtoadd.setFrequency(EventFrequency.TUETHUR);
								break;
							case 7:
								eventtoadd.setFrequency(EventFrequency.WEEKDAY);
								break;
							default:
								eventtoadd.setFrequency(EventFrequency.NONE);
								break;
						}
						Date freqend = new Date(0);
						freqend.setYear(((DatePicker) layout.findViewById(R.id.freqenddate)).getYear() - 1900);
						freqend.setMonth(((DatePicker) layout.findViewById(R.id.freqenddate)).getMonth());
						freqend.setDate(((DatePicker) layout.findViewById(R.id.freqenddate)).getDayOfMonth());
						freqend.setHours(((TimePicker) layout.findViewById(R.id.freqendtime)).getCurrentHour());
						freqend.setMinutes(((TimePicker) layout.findViewById(R.id.freqendtime)).getCurrentMinute());
						eventtoadd.setEndFrequency(freqend);
						final ProgressDialog dialog = ProgressDialog.show(Activa.myApp, Activa.myLanguageManager.NOTIFICATION_EVENT_TITLE, 
								Activa.myLanguageManager.NOTIFICATION_EVENT_ADDING, true);
						dialog.show();
						Runnable run =  new Runnable() {
							@Override
							public void run() {
								if (Activa.myCalendarManager.AddEvent(eventtoadd))
									handler.sendEmptyMessage(1);
								else 
									handler.sendEmptyMessage(0);
							}
							private Handler handler = new Handler() {
								@Override
								public void handleMessage(Message msg) {
									switch (msg.what) {
										case 0:
											Toast toast = Toast.makeText(Activa.myApp, Activa.myLanguageManager.CONNECTION_FAILED, Toast.LENGTH_LONG);
											Activa.myUIManager.loadScheduleDay(dategiven);
											dialog.cancel();
											toast.show();
											break;
										case 1:
											Activa.myUIManager.loadScheduleDay(dategiven);
											dialog.cancel();
											break;
									}
								}

							};
						};
						Thread thread = new Thread(run);
						thread.start();
						alertDialog.cancel();
					}
				});
			}
		});
	}
	
	public void loadScheduleWeek(final Date dategiven) {
		TextView time;
		Event event = null; 
		Calendar cal = Calendar.getInstance();
		cal.setTime(dategiven);
		Date date = new Date((dategiven.getTime()/3600000)*3600000);
		date.setHours(0);
		int day = cal.get(Calendar.DAY_OF_WEEK)-2;
		if (day == -1) day = 6;
		date.setDate(date.getDate() - day);
		Date dateLater = new Date(date.getTime());
		dateLater.setDate(date.getDate() + 1);
		TableRow buttonLayout;
		this.state = UI_STATE_SCHEDULEWEEK;
		if (Activa.myMenu != null) {
			Activa.myMenu.clear();
			Activa.myInflater.inflate(R.menu.schedule, Activa.myMenu);
		}
		Hashtable<Date, Event> eventsOrdered = new Hashtable<Date,Event>();
		Vector<Date> datesOrdered = new Vector<Date>();
		Enumeration<Event> enumer = Activa.myCalendarManager.events.elements();
		while (enumer.hasMoreElements()) {
			Event temp = enumer.nextElement();
			while (datesOrdered.contains(temp.date)) temp.date.setTime(temp.date.getTime() + 1);
			Timestamp stamp = new Timestamp(temp.date.getTime());
			datesOrdered.add(stamp);
			eventsOrdered.put(stamp, temp);
		}
		Collections.sort(datesOrdered);
		Activa.myApp.setContentView(R.layout.scheduleday);
		((TextView) Activa.myApp.findViewById(R.id.startText)).setText(Activa.myLanguageManager.CALENDAR_TITLE);
		ImageButton back = (ImageButton) Activa.myApp.findViewById(R.id.previous);
		ImageButton prev = (ImageButton) Activa.myApp.findViewById(R.id.previousday);
		ImageButton next = (ImageButton) Activa.myApp.findViewById(R.id.nextday);
		TextView dateText = (TextView) Activa.myApp.findViewById(R.id.date);
		dateText.setText(Activa.myLanguageManager.CALENDAR_WEEK + " " + cal.get(Calendar.WEEK_OF_YEAR) + " "+ Activa.myLanguageManager.CALENDAR_OF + " " + cal.get(Calendar.YEAR));
		TableLayout schedule = (TableLayout)Activa.myApp.findViewById(R.id.schedule);
		for (int i = 0; i < 7; i++) {	
			time = new TextView(Activa.myApp);	
			time.setText(ActivaUtil.dateToReadableString(date));
			time.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
			time.setPadding(5, 10, 5, 10);
			time.setTypeface(Typeface.DEFAULT_BOLD);
			time.setTextSize(20);
			time.setGravity(Gravity.CENTER);
			time.setTextColor(Color.BLACK);
			schedule.addView(time);		
			View separator = new View(Activa.myApp);
			LayoutParams separatorLayout = new LayoutParams(android.view.ViewGroup.LayoutParams.FILL_PARENT, 1);
			separator.setLayoutParams(separatorLayout);
			separator.setBackgroundColor(Color.BLACK);				
			schedule.addView(separator);
			for(int j = 0; j < datesOrdered.size(); j++) {
				event = eventsOrdered.get(datesOrdered.get(j));
				final String id = event.id;
				if ((date.compareTo(event.getDate()) <= 0)&&(dateLater.compareTo(event.getDate()) > 0)) {
					buttonLayout = new TableRow(Activa.myApp);
					buttonLayout.setOrientation(TableRow.HORIZONTAL);
					buttonLayout.setGravity(Gravity.CENTER_VERTICAL);
					buttonLayout.setLayoutParams(new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, 40));			
					time = new TextView(Activa.myApp);
					time.setPadding(5, 10, 5, 10);
					time.setTextSize(20);
					time.setTextColor(Color.BLACK);
					time.setTypeface(Typeface.SANS_SERIF);
					time.setText(String.format("%02d:%02d", event.date.getHours(), event.date.getMinutes()));
					buttonLayout.addView(time);
					TextView activity = new TextView(Activa.myApp);
					activity.setLayoutParams(new TableRow.LayoutParams(200, LayoutParams.WRAP_CONTENT));
					activity.setText(event.name);
					activity.setPadding(5, 10, 5, 10);
					activity.setTextSize(20);
					activity.setTextColor(Color.BLACK);
					activity.setTypeface(Typeface.SANS_SERIF);
					buttonLayout.addView(activity);
					ImageView button = new ImageView(Activa.myApp);
					switch (event.state) {
						case 0:
							button.setBackgroundResource(R.drawable.oksmall);
							break;
						case 1:
							button.setBackgroundResource(R.drawable.cancel);
							break;
						case 2:
							button.setBackgroundResource(R.drawable.nextsmall);
							break;
					}
					if (event.type == 2) button.setBackgroundResource(R.drawable.event);
					buttonLayout.addView(button);	
					schedule.addView(buttonLayout);
					time.setOnClickListener(new OnClickListener() {				
						@Override
						public void onClick(View v) {
							if (Activa.myMenu != null) Activa.myMenu.clear();
							Activa.myCalendarManager.events.get(id).doActivity();
						}
					});	
					activity.setOnClickListener(new OnClickListener() {				
						@Override
						public void onClick(View v) {
							if (Activa.myMenu != null) Activa.myMenu.clear();
							Activa.myCalendarManager.events.get(id).doActivity();
						}
					});
					button.setOnClickListener(new OnClickListener() {				
						@Override
						public void onClick(View v) {
							if (Activa.myMenu != null) Activa.myMenu.clear();
							Activa.myCalendarManager.events.get(id).doActivity();
						}
					});	
					buttonLayout.setOnClickListener(new OnClickListener() {				
						@Override
						public void onClick(View v) {
							if (Activa.myMenu != null) Activa.myMenu.clear();
							Activa.myCalendarManager.events.get(id).doActivity();
						}
					});	
					separator = new View(Activa.myApp);
					separatorLayout = new LayoutParams(android.view.ViewGroup.LayoutParams.FILL_PARENT, 1);
					separator.setLayoutParams(separatorLayout);
					separator.setBackgroundColor(Color.BLACK);
					schedule.addView(separator);
				}
			}
			date.setDate(date.getDate() + 1);
			dateLater.setDate(dateLater.getDate() + 1);
		}
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Activa.myUIManager.loadGeneratedSubenvironment(Activa.myUIManager.lastSubenv, true);
			}
		});
		prev.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Runnable run = new Runnable() {
					@Override
					public void run() {
						handler.sendEmptyMessage(0);
						int day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)-2;
						if (day == -1) day = 6;
						Date start = new Date((dategiven.getTime()/86400000)*86400000);
						start.setDate(start.getDate() - day - 7);
						Date end = new Date(start.getTime());
						end.setDate(end.getDate() + 7);
						if (Activa.myCalendarManager.getCalendar(start, end))
							handler.sendEmptyMessage(1);
						else handler.sendEmptyMessage(2);
					}
					private Handler handler = new Handler() {
						@Override
						public void handleMessage(Message msg) {
							Date nextDate;
							switch (msg.what) {
								case 0:
									ImageView animationFrame = (ImageView) Activa.myApp.findViewById(R.id.animation);
									animationFrame.setVisibility(View.VISIBLE);
									animationFrame.setBackgroundResource(R.drawable.loading);
									final AnimationDrawable animation = (AnimationDrawable) animationFrame.getBackground();
									animation.start();
									break;
								case 1:
									nextDate = new Date(dategiven.getTime());
									nextDate.setDate(nextDate.getDate() - 7);
									Activa.myUIManager.loadScheduleWeek(nextDate);
									break;
								case 2:
									nextDate = new Date(dategiven.getTime());
									nextDate.setDate(nextDate.getDate() - 7);
									Activa.myUIManager.loadScheduleWeek(nextDate);
									Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.CONNECTION_FAILED);
									break;
							}
						}

					};
				};
				Thread thread = new Thread(run);
				thread.start();
			}
		});
		next.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Runnable run = new Runnable() {
					@Override
					public void run() {
						handler.sendEmptyMessage(0);
						int day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)-2;
						if (day == -1) day = 6;
						Date start = new Date((dategiven.getTime()/86400000)*86400000);
						start.setDate(start.getDate() - day + 7);
						Date end = new Date(start.getTime());
						end.setDate(end.getDate() + 7);
						if (Activa.myCalendarManager.getCalendar(start, end))
							handler.sendEmptyMessage(1);
						else handler.sendEmptyMessage(2);
					}
					private Handler handler = new Handler() {
						@Override
						public void handleMessage(Message msg) {
							Date nextDate;
							switch (msg.what) {
								case 0:
									ImageView animationFrame = (ImageView) Activa.myApp.findViewById(R.id.animation);
									animationFrame.setVisibility(View.VISIBLE);
									animationFrame.setBackgroundResource(R.drawable.loading);
									final AnimationDrawable animation = (AnimationDrawable) animationFrame.getBackground();
									animation.start();
									break;
								case 1:
									nextDate = new Date(dategiven.getTime());
									nextDate.setDate(nextDate.getDate() + 7);
									Activa.myUIManager.loadScheduleWeek(nextDate);
									break;
								case 2:
									nextDate = new Date(dategiven.getTime());
									nextDate.setDate(nextDate.getDate() + 7);
									Activa.myUIManager.loadScheduleWeek(nextDate);
									Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.CONNECTION_FAILED);
									break;
							}
						}

					};
				};
				Thread thread = new Thread(run);
				thread.start();
			}
		});
		ImageView tocal = (ImageView) Activa.myApp.findViewById(R.id.animation);
		tocal.setVisibility(View.VISIBLE);
		try {
	        PackageManager manager = Activa.myApp.getPackageManager();
			tocal.setBackgroundDrawable(manager.getActivityIcon(new ComponentName(Activa.myExteriorManager.externalApplications.get(ExteriorManager.APP_CALENDAR).packageName, Activa.myExteriorManager.externalApplications.get(ExteriorManager.APP_CALENDAR).activityName)));
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		tocal.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Activa.myExteriorManager.externalApplications.get(ExteriorManager.APP_CALENDAR).startApplication();
			}
		});
		ImageButton add = (ImageButton) Activa.myApp.findViewById(R.id.help);
		add.setImageResource(R.drawable.plus);
		add.setVisibility(View.VISIBLE);
		add.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				LayoutInflater inflater = (LayoutInflater) Activa.myApp.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				final View layout = inflater.inflate(R.layout.toastaddevent,
				                               (ViewGroup) Activa.myApp.findViewById(R.id.toastaddeventroot));
				TextView title = (TextView) layout.findViewById(R.id.addtitle);
				title.setText(Activa.myLanguageManager.NOTIFICATION_EVENT_TITLE);
				TextView namereq = (TextView) layout.findViewById(R.id.namerequested);
				namereq.setText(Activa.myLanguageManager.NOTIFICATION_EVENT_NAMEREQ);
				TextView descreq = (TextView) layout.findViewById(R.id.descrequest);
				descreq.setText(Activa.myLanguageManager.NOTIFICATION_EVENT_DESC);
				TextView startreq = (TextView) layout.findViewById(R.id.startrequest);
				startreq.setText(Activa.myLanguageManager.NOTIFICATION_EVENT_START);
				TextView endreq = (TextView) layout.findViewById(R.id.endrequest);
				endreq.setText(Activa.myLanguageManager.NOTIFICATION_EVENT_END);
				TextView freqreq = (TextView) layout.findViewById(R.id.freqrequest);
				freqreq.setText(Activa.myLanguageManager.NOTIFICATION_EVENT_FREQ);
				final Spinner frequency = (Spinner) layout.findViewById(R.id.freq);
			    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(Activa.myApp, R.array.freqarray, android.R.layout.simple_spinner_item);
			    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			    frequency.setAdapter(adapter);
			    ((LinearLayout)layout.findViewById(R.id.freqend)).setVisibility(View.GONE);
			    frequency.setOnItemSelectedListener(new OnItemSelectedListener() {
					@Override
					public void onItemSelected (AdapterView<?> parent, View view, int position, long id) {
						if (position == 0) ((LinearLayout)layout.findViewById(R.id.freqend)).setVisibility(View.GONE);
						else ((LinearLayout)layout.findViewById(R.id.freqend)).setVisibility(View.VISIBLE);
					}
					@Override
					public void onNothingSelected (AdapterView<?> parent) {
						((LinearLayout)layout.findViewById(R.id.freqend)).setVisibility(View.GONE);
					}
				});
			    TextView freqendreq = (TextView) layout.findViewById(R.id.freqendrequest);
				freqendreq.setText(Activa.myLanguageManager.NOTIFICATION_EVENT_FREQEND);
				ImageButton image = (ImageButton) layout.findViewById(R.id.addeventok);
				Builder builder = new AlertDialog.Builder(Activa.myApp);
				builder.setView(layout);
				final AlertDialog alertDialog = builder.create();
				alertDialog.show();
				image.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						final com.o2hlink.activ8.client.entity.Event eventtoadd = new com.o2hlink.activ8.client.entity.Event();
						eventtoadd.setName(((EditText) layout.findViewById(R.id.name)).getText().toString());
						eventtoadd.setDescription(((EditText) layout.findViewById(R.id.description)).getText().toString());
						Date start = new Date(0);
						start.setYear(((DatePicker) layout.findViewById(R.id.startdate)).getYear() - 1900);
						start.setMonth(((DatePicker) layout.findViewById(R.id.startdate)).getMonth());
						start.setDate(((DatePicker) layout.findViewById(R.id.startdate)).getDayOfMonth());
						start.setHours(((TimePicker) layout.findViewById(R.id.starttime)).getCurrentHour());
						start.setMinutes(((TimePicker) layout.findViewById(R.id.starttime)).getCurrentMinute());
						eventtoadd.setStart(start);
						Date end = new Date(0);
						end.setYear(((DatePicker) layout.findViewById(R.id.enddate)).getYear() - 1900);
						end.setMonth(((DatePicker) layout.findViewById(R.id.enddate)).getMonth());
						end.setDate(((DatePicker) layout.findViewById(R.id.enddate)).getDayOfMonth());
						end.setHours(((TimePicker) layout.findViewById(R.id.endtime)).getCurrentHour());
						end.setMinutes(((TimePicker) layout.findViewById(R.id.endtime)).getCurrentMinute());
						eventtoadd.setEnd(end);
						switch (frequency.getSelectedItemPosition()) {
							case 0:
								eventtoadd.setFrequency(EventFrequency.NONE);
								break;
							case 1:
								eventtoadd.setFrequency(EventFrequency.DAILY);
								break;
							case 2:
								eventtoadd.setFrequency(EventFrequency.WEEKLY);
								break;
							case 3:
								eventtoadd.setFrequency(EventFrequency.MONTHLY);
								break;
							case 4:
								eventtoadd.setFrequency(EventFrequency.YEARLY);
								break;
							case 5:
								eventtoadd.setFrequency(EventFrequency.MONWEDFRI);
								break;
							case 6:
								eventtoadd.setFrequency(EventFrequency.TUETHUR);
								break;
							case 7:
								eventtoadd.setFrequency(EventFrequency.WEEKDAY);
								break;
							default:
								eventtoadd.setFrequency(EventFrequency.NONE);
								break;
						}
						Date freqend = new Date(0);
						freqend.setYear(((DatePicker) layout.findViewById(R.id.freqenddate)).getYear() - 1900);
						freqend.setMonth(((DatePicker) layout.findViewById(R.id.freqenddate)).getMonth());
						freqend.setDate(((DatePicker) layout.findViewById(R.id.freqenddate)).getDayOfMonth());
						freqend.setHours(((TimePicker) layout.findViewById(R.id.freqendtime)).getCurrentHour());
						freqend.setMinutes(((TimePicker) layout.findViewById(R.id.freqendtime)).getCurrentMinute());
						eventtoadd.setEndFrequency(freqend);
						final ProgressDialog dialog = ProgressDialog.show(Activa.myApp, Activa.myLanguageManager.NOTIFICATION_EVENT_TITLE, 
								Activa.myLanguageManager.NOTIFICATION_EVENT_ADDING, true);
						dialog.show();
						Runnable run =  new Runnable() {
							@Override
							public void run() {
								if (Activa.myCalendarManager.AddEvent(eventtoadd))
									handler.sendEmptyMessage(1);
								else 
									handler.sendEmptyMessage(0);
							}
							private Handler handler = new Handler() {
								@Override
								public void handleMessage(Message msg) {
									switch (msg.what) {
										case 0:
											Toast toast = Toast.makeText(Activa.myApp, Activa.myLanguageManager.CONNECTION_FAILED, Toast.LENGTH_SHORT);
											Activa.myUIManager.loadScheduleDay(dategiven);
											dialog.cancel();
											toast.show();
											break;
										case 1:
											int day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)-2;
											if (day == -1) day = 6;
											Date start = new Date((dategiven.getTime()/86400000)*86400000);
											start.setDate(start.getDate() - day + 7);
											Date end = new Date(start.getTime());
											end.setDate(end.getDate() + 7);
											Activa.myCalendarManager.getNonMeasuringEvents(start, end);
											Activa.myUIManager.loadScheduleWeek(dategiven);
											dialog.cancel();
											break;
									}
								}

							};
						};
						Thread thread = new Thread(run);
						thread.start();
						alertDialog.cancel();
					}
				});
			}
		});
	}
	
	public void loadScheduleMonth(final Date dategiven) {
		TextView time;
		View separator;
		LayoutParams separatorLayout;
		TableRow weekLayout;
		Enumeration<Event> enumeration;
		Event event = null; 
		Date date = new Date((dategiven.getTime()/3600000)*3600000);
		date.setHours(0);
		date.setDate(1);
		Date dateLater = new Date(date.getTime());
		dateLater.setDate(2);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int weekday = cal.get(Calendar.DAY_OF_WEEK)-2;
		if (weekday == -1) weekday = 6;
		Date dateLimit = new Date(date.getTime());
		dateLimit.setMonth(date.getMonth() + 1);
		this.state = UI_STATE_SCHEDULEMONTH;
		if (Activa.myMenu != null) {
			Activa.myMenu.clear();
			Activa.myInflater.inflate(R.menu.schedule, Activa.myMenu);
		}
		Activa.myApp.setContentView(R.layout.schedulemonth);
		((TextView) Activa.myApp.findViewById(R.id.startText)).setText(Activa.myLanguageManager.CALENDAR_TITLE);
		ImageButton back = (ImageButton) Activa.myApp.findViewById(R.id.previous);
		ImageButton prev = (ImageButton) Activa.myApp.findViewById(R.id.previousday);
		ImageButton next = (ImageButton) Activa.myApp.findViewById(R.id.nextday);
		TextView dateText = (TextView) Activa.myApp.findViewById(R.id.date);
		dateText.setText(ActivaUtil.monthOfDate(date) + " " + cal.get(Calendar.YEAR));
		TableLayout schedule = (TableLayout)Activa.myApp.findViewById(R.id.schedule);
		weekLayout = new TableRow(Activa.myApp);
		weekLayout.setOrientation(TableRow.HORIZONTAL);
		weekLayout.setGravity(Gravity.CENTER_VERTICAL);
		weekLayout.setLayoutParams(new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		separator = new View(Activa.myApp);
		separator.setLayoutParams(new TableRow.LayoutParams(1, 36));
		separator.setBackgroundColor(Color.BLACK);
		weekLayout.addView(separator);
		String[] weekdaynames = {Activa.myLanguageManager.WEEK_MONDAY,
				Activa.myLanguageManager.WEEK_TUESDAY,
				Activa.myLanguageManager.WEEK_WEDNESDAY,
				Activa.myLanguageManager.WEEK_THURSDAY,
				Activa.myLanguageManager.WEEK_FRYDAY,
				Activa.myLanguageManager.WEEK_SATURDAY,
				Activa.myLanguageManager.WEEK_SUNDAY}; 
		for (int i = 0; i < weekdaynames.length; i++) {
			time = new TextView(Activa.myApp);
			time.setGravity(Gravity.CENTER);
			time.setTextSize(20);
			time.setText(weekdaynames[i]);
			time.setTag(date.getDate());
			time.setTypeface(Typeface.DEFAULT_BOLD);
			time.setLayoutParams(new TableRow.LayoutParams(36, 36));	
			time.setTextColor(Color.BLACK);
			weekLayout.addView(time);
		}
		separator = new View(Activa.myApp);
		separator.setLayoutParams(new TableRow.LayoutParams(1, 36));
		separator.setBackgroundColor(Color.BLACK);
		weekLayout.addView(separator);
		schedule.addView(weekLayout);	
		separator = new View(Activa.myApp);
		separatorLayout = new LayoutParams(android.view.ViewGroup.LayoutParams.FILL_PARENT, 1);
		separator.setLayoutParams(separatorLayout);
		separator.setBackgroundColor(Color.BLACK);
		schedule.addView(separator);	
		weekLayout = new TableRow(Activa.myApp);
		weekLayout.setOrientation(TableRow.HORIZONTAL);
		weekLayout.setGravity(Gravity.CENTER_VERTICAL);
		weekLayout.setLayoutParams(new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		separator = new View(Activa.myApp);
		separator.setLayoutParams(new TableRow.LayoutParams(1, 36));
		separator.setBackgroundColor(Color.BLACK);
		weekLayout.addView(separator);
		for (int i = 0; i < weekday; i++) {		
			View space = new View(Activa.myApp);
			space.setLayoutParams(new TableRow.LayoutParams(36, 36));	
			weekLayout.addView(space);
		}
		while(date.before(dateLimit)) {	
			time = new TextView(Activa.myApp);
			time.setGravity(Gravity.CENTER);
			time.setTextSize(20);
			time.setText("" + date.getDate());
			time.setTag(date.getDate());
			time.setTypeface(Typeface.SANS_SERIF);
			time.setLayoutParams(new TableRow.LayoutParams(36, 36));	
			int state = 3;
			enumeration = Activa.myCalendarManager.events.elements();
			while (enumeration.hasMoreElements()) {
				event = enumeration.nextElement();
				if (event.type == 2) continue;
				if ((date.compareTo(event.getDate()) <= 0)&&(dateLater.compareTo(event.getDate()) > 0)) {
					if ((event.state == 0)&&(state == 3)) {
						state = 0;
						continue;
					}
					if ((event.state == 2)&&((state == 3)||(state == 0))) {
						state = 2;
						continue;
					}
					if ((event.state == 1)&&((state == 3)||(state == 0)||(state == 2))) {
						state = 1;
						break;
					}
				}
			}
			switch (state) {
				case 0:
					time.setTextColor(Color.GREEN);
					break;
				case 1:
					time.setTextColor(Color.RED);
					break;
				case 2:
					time.setTextColor(Color.parseColor("#ffad00"));
					break;
				case 3:
					time.setTextColor(Color.BLACK);
					break;
			}
			time.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Date newDate = new Date(dategiven.getTime());
					newDate.setDate((Integer)v.getTag());
					Activa.myUIManager.loadScheduleDay(newDate);
				}
			});
			weekLayout.addView(time);
			date.setDate(date.getDate() + 1);
			dateLater.setDate(dateLater.getDate() + 1);
			weekday++;
			if (weekday == 7) {		
				separator = new View(Activa.myApp);
				separator.setLayoutParams(new TableRow.LayoutParams(1, 36));
				separator.setBackgroundColor(Color.BLACK);
				weekLayout.addView(separator);
				schedule.addView(weekLayout);	
				separator = new View(Activa.myApp);
				separatorLayout = new LayoutParams(android.view.ViewGroup.LayoutParams.FILL_PARENT, 1);
				separator.setLayoutParams(separatorLayout);
				separator.setBackgroundColor(Color.BLACK);
				schedule.addView(separator);	
				weekday = 0;
				weekLayout = new TableRow(Activa.myApp);
				weekLayout.setOrientation(TableRow.HORIZONTAL);
				weekLayout.setGravity(Gravity.CENTER_VERTICAL);
				weekLayout.setLayoutParams(new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
				separator = new View(Activa.myApp);
				separator.setLayoutParams(new TableRow.LayoutParams(1, 36));
				separator.setBackgroundColor(Color.BLACK);
				weekLayout.addView(separator);
			}
		}
		for (int i = weekday; i < 7; i++) {	
			time = new TextView(Activa.myApp);
			time.setGravity(Gravity.CENTER);
			time.setTextSize(20);
			time.setTextColor(Color.BLACK);
			time.setTypeface(Typeface.SANS_SERIF);
			time.setLayoutParams(new TableRow.LayoutParams(36, 36));	
			weekLayout.addView(time);
		}	
		separator = new View(Activa.myApp);
		separator.setLayoutParams(new TableRow.LayoutParams(1, 36));
		separator.setBackgroundColor(Color.BLACK);
		weekLayout.addView(separator);
		schedule.addView(weekLayout);
		separator = new View(Activa.myApp);
		separatorLayout = new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, 1);
		separator.setLayoutParams(separatorLayout);
		separator.setBackgroundColor(Color.BLACK);
		schedule.addView(separator);	
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Activa.myUIManager.loadGeneratedSubenvironment(Activa.myUIManager.lastSubenv, true);
			}
		});
		prev.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Runnable run = new Runnable() {
					@Override
					public void run() {
						handler.sendEmptyMessage(0);
						Date start = new Date((dategiven.getTime()/86400000)*86400000);
						start.setDate(1);
						start.setMonth(start.getMonth() - 1);
						Date end = new Date(start.getTime());
						end.setMonth(end.getMonth() + 1);
						if (Activa.myCalendarManager.getCalendar(start, end))
							handler.sendEmptyMessage(1);
						else handler.sendEmptyMessage(2);
					}
					private Handler handler = new Handler() {
						@Override
						public void handleMessage(Message msg) {
							Date nextDate;
							switch (msg.what) {
								case 0:
									ImageView animationFrame = (ImageView) Activa.myApp.findViewById(R.id.animation);
									animationFrame.setVisibility(View.VISIBLE);
									animationFrame.setBackgroundResource(R.drawable.loading);
									final AnimationDrawable animation = (AnimationDrawable) animationFrame.getBackground();
									animation.start();
									break;
								case 1:
									nextDate = new Date(dategiven.getTime());
									nextDate.setMonth(nextDate.getMonth() - 1);
									Activa.myUIManager.loadScheduleMonth(nextDate);
									break;
								case 2:
									nextDate = new Date(dategiven.getTime());
									nextDate.setMonth(nextDate.getMonth() - 1);
									Activa.myUIManager.loadScheduleMonth(nextDate);
									Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.CONNECTION_FAILED);
									break;
							}
						}

					};
				};
				Thread thread = new Thread(run);
				thread.start();
			}
		});
		next.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Runnable run = new Runnable() {
					@Override
					public void run() {
						handler.sendEmptyMessage(0);
						Date start = new Date((dategiven.getTime()/86400000)*86400000);
						start.setDate(1);
						start.setMonth(start.getMonth() + 1);
						Date end = new Date(start.getTime());
						end.setMonth(end.getMonth() + 1);
						if (Activa.myCalendarManager.getCalendar(start, end))
							handler.sendEmptyMessage(1);
						else handler.sendEmptyMessage(2);
					}
					private Handler handler = new Handler() {
						@Override
						public void handleMessage(Message msg) {
							Date nextDate;
							switch (msg.what) {
								case 0:
									ImageView animationFrame = (ImageView) Activa.myApp.findViewById(R.id.animation);
									animationFrame.setVisibility(View.VISIBLE);
									animationFrame.setBackgroundResource(R.drawable.loading);
									final AnimationDrawable animation = (AnimationDrawable) animationFrame.getBackground();
									animation.start();
									break;
								case 1:
									nextDate = new Date(dategiven.getTime());
									nextDate.setMonth(nextDate.getMonth() + 1);
									Activa.myUIManager.loadScheduleMonth(nextDate);
									break;
								case 2:
									nextDate = new Date(dategiven.getTime());
									nextDate.setMonth(nextDate.getMonth() + 1);
									Activa.myUIManager.loadScheduleMonth(nextDate);
									Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.CONNECTION_FAILED);
									break;
							}
						}

					};
				};
				Thread thread = new Thread(run);
				thread.start();
			}
		});
		ImageView tocal = (ImageView) Activa.myApp.findViewById(R.id.animation);
		tocal.setVisibility(View.VISIBLE);
		try {
	        PackageManager manager = Activa.myApp.getPackageManager();
			tocal.setBackgroundDrawable(manager.getActivityIcon(new ComponentName(Activa.myExteriorManager.externalApplications.get(ExteriorManager.APP_CALENDAR).packageName, Activa.myExteriorManager.externalApplications.get(ExteriorManager.APP_CALENDAR).activityName)));
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		tocal.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Activa.myExteriorManager.externalApplications.get(ExteriorManager.APP_CALENDAR).startApplication();
			}
		});
		ImageButton add = (ImageButton) Activa.myApp.findViewById(R.id.help);
		add.setImageResource(R.drawable.plus);
		add.setVisibility(View.VISIBLE);
		add.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				LayoutInflater inflater = (LayoutInflater) Activa.myApp.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				final View layout = inflater.inflate(R.layout.toastaddevent,
				                               (ViewGroup) Activa.myApp.findViewById(R.id.toastaddeventroot));
				TextView title = (TextView) layout.findViewById(R.id.addtitle);
				title.setText(Activa.myLanguageManager.NOTIFICATION_EVENT_TITLE);
				TextView namereq = (TextView) layout.findViewById(R.id.namerequested);
				namereq.setText(Activa.myLanguageManager.NOTIFICATION_EVENT_NAMEREQ);
				TextView descreq = (TextView) layout.findViewById(R.id.descrequest);
				descreq.setText(Activa.myLanguageManager.NOTIFICATION_EVENT_DESC);
				TextView startreq = (TextView) layout.findViewById(R.id.startrequest);
				startreq.setText(Activa.myLanguageManager.NOTIFICATION_EVENT_START);
				TextView endreq = (TextView) layout.findViewById(R.id.endrequest);
				endreq.setText(Activa.myLanguageManager.NOTIFICATION_EVENT_END);
				TextView freqreq = (TextView) layout.findViewById(R.id.freqrequest);
				freqreq.setText(Activa.myLanguageManager.NOTIFICATION_EVENT_FREQ);
			    final Spinner frequency = (Spinner) layout.findViewById(R.id.freq);
			    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(Activa.myApp, R.array.freqarray, android.R.layout.simple_spinner_item);
			    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			    frequency.setAdapter(adapter);
				((LinearLayout)layout.findViewById(R.id.freqend)).setVisibility(View.GONE);
			    frequency.setOnItemSelectedListener(new OnItemSelectedListener() {
					@Override
					public void onItemSelected (AdapterView<?> parent, View view, int position, long id) {
						if (position == 0) ((LinearLayout)layout.findViewById(R.id.freqend)).setVisibility(View.GONE);
						else ((LinearLayout)layout.findViewById(R.id.freqend)).setVisibility(View.VISIBLE);
					}
					@Override
					public void onNothingSelected (AdapterView<?> parent) {
						((LinearLayout)layout.findViewById(R.id.freqend)).setVisibility(View.GONE);
					}
				});
				TextView freqendreq = (TextView) layout.findViewById(R.id.freqendrequest);
				freqendreq.setText(Activa.myLanguageManager.NOTIFICATION_EVENT_FREQEND);
				ImageButton image = (ImageButton) layout.findViewById(R.id.addeventok);
				Builder builder = new AlertDialog.Builder(Activa.myApp);
				builder.setView(layout);
				final AlertDialog alertDialog = builder.create();
				alertDialog.show();
				image.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						final com.o2hlink.activ8.client.entity.Event eventtoadd = new com.o2hlink.activ8.client.entity.Event();
						eventtoadd.setName(((EditText) layout.findViewById(R.id.name)).getText().toString());
						eventtoadd.setDescription(((EditText) layout.findViewById(R.id.description)).getText().toString());
						Date start = new Date(0);
						start.setYear(((DatePicker) layout.findViewById(R.id.startdate)).getYear() - 1900);
						start.setMonth(((DatePicker) layout.findViewById(R.id.startdate)).getMonth());
						start.setDate(((DatePicker) layout.findViewById(R.id.startdate)).getDayOfMonth());
						start.setHours(((TimePicker) layout.findViewById(R.id.starttime)).getCurrentHour());
						start.setMinutes(((TimePicker) layout.findViewById(R.id.starttime)).getCurrentMinute());
						eventtoadd.setStart(start);
						Date end = new Date(0);
						end.setYear(((DatePicker) layout.findViewById(R.id.enddate)).getYear() - 1900);
						end.setMonth(((DatePicker) layout.findViewById(R.id.enddate)).getMonth());
						end.setDate(((DatePicker) layout.findViewById(R.id.enddate)).getDayOfMonth());
						end.setHours(((TimePicker) layout.findViewById(R.id.endtime)).getCurrentHour());
						end.setMinutes(((TimePicker) layout.findViewById(R.id.endtime)).getCurrentMinute());
						eventtoadd.setEnd(end);
						switch (frequency.getSelectedItemPosition()) {
							case 0:
								eventtoadd.setFrequency(EventFrequency.NONE);
								break;
							case 1:
								eventtoadd.setFrequency(EventFrequency.DAILY);
								break;
							case 2:
								eventtoadd.setFrequency(EventFrequency.WEEKLY);
								break;
							case 3:
								eventtoadd.setFrequency(EventFrequency.MONTHLY);
								break;
							case 4:
								eventtoadd.setFrequency(EventFrequency.YEARLY);
								break;
							case 5:
								eventtoadd.setFrequency(EventFrequency.MONWEDFRI);
								break;
							case 6:
								eventtoadd.setFrequency(EventFrequency.TUETHUR);
								break;
							case 7:
								eventtoadd.setFrequency(EventFrequency.WEEKDAY);
								break;
							default:
								eventtoadd.setFrequency(EventFrequency.NONE);
								break;
						}
						Date freqend = new Date(0);
						freqend.setYear(((DatePicker) layout.findViewById(R.id.freqenddate)).getYear() - 1900);
						freqend.setMonth(((DatePicker) layout.findViewById(R.id.freqenddate)).getMonth());
						freqend.setDate(((DatePicker) layout.findViewById(R.id.freqenddate)).getDayOfMonth());
						freqend.setHours(((TimePicker) layout.findViewById(R.id.freqendtime)).getCurrentHour());
						freqend.setMinutes(((TimePicker) layout.findViewById(R.id.freqendtime)).getCurrentMinute());
						eventtoadd.setEndFrequency(freqend);
						final ProgressDialog dialog = ProgressDialog.show(Activa.myApp, Activa.myLanguageManager.NOTIFICATION_EVENT_TITLE, 
								Activa.myLanguageManager.NOTIFICATION_EVENT_ADDING, true);
						dialog.show();
						Runnable run =  new Runnable() {
							@Override
							public void run() {
								if (Activa.myCalendarManager.AddEvent(eventtoadd))
									handler.sendEmptyMessage(1);
								else 
									handler.sendEmptyMessage(0);
							}
							private Handler handler = new Handler() {
								@Override
								public void handleMessage(Message msg) {
									switch (msg.what) {
										case 0:
											Toast toast = Toast.makeText(Activa.myApp, Activa.myLanguageManager.CONNECTION_FAILED, Toast.LENGTH_SHORT);
											Activa.myUIManager.loadScheduleDay(dategiven);
											dialog.cancel();
											toast.show();
											break;
										case 1:
											Date start = new Date((dategiven.getTime()/86400000)*86400000);
											start.setDate(1);
											start.setMonth(start.getMonth() + 1);
											Date end = new Date(start.getTime());
											end.setMonth(end.getMonth() + 1);
											Activa.myCalendarManager.getNonMeasuringEvents(start, end);
											Activa.myUIManager.loadScheduleDay(dategiven);
											dialog.cancel();
											break;
									}
								}

							};
						};
						Thread thread = new Thread(run);
						thread.start();
						alertDialog.cancel();
					}
				});
			}
		});
	}
	
	/*public void sensoring (String sensor, Event event) {
		Activa.mySensorManager.eventAssociated = event;
		AnimSensorTimeTask timer = new AnimSensorTimeTask(2000, 125, sensor);
		timer.start();
	}*/

	public void finishSensorMeasurement(String sensorString, boolean outcome, final Sensor sensor) {
//        Activa.myBluetoothAdapter.disable();
		if (outcome) {
			Activa.myApp.setContentView(R.layout.sensorresult);
			TextView textTitle = (TextView) Activa.myApp.findViewById(R.id.startText);
			TextView text = (TextView) Activa.myApp.findViewById(R.id.textResult);
			((TextView) Activa.myApp.findViewById(R.id.resultsWord)).setText(Activa.myLanguageManager.SENSORS_RESULTSWORD);
			textTitle.setText(sensorString);
			text.setText("");
			Enumeration<Integer> keys = sensor.results.keys();
			while (keys.hasMoreElements()) {
				int key = keys.nextElement();
				String meas = SensorManager.getMeasurementName(key);
				if (meas != null) {
					text.append(meas + ": " + String.format("%.1f", (float)sensor.results.get(key)));
					text.append(" " + SensorManager.getUnitForMeasurement(SensorManager.getUnitIDForMeasurementID(key)));
					if (keys.hasMoreElements()) text.append("\n");
					if (key > 1000) sensor.results.remove(key);
				}
			}
			final ImageButton layout = (ImageButton) Activa.myApp.findViewById(R.id.ok);
			OnClickListener listener = new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					String result = sensor.getSensorGlobalResult();
					int resultInt = Integer.parseInt(result.substring(0, 1));
					String resultString = result.substring(2);
					loadSensorGlobalResult(resultString, resultInt, sensor);
				}
			};
			layout.setOnClickListener(listener);
			text.setOnClickListener(listener);
		}
		else {
			Activa.myApp.setContentView(R.layout.info);
			TextView text = (TextView) Activa.myApp.findViewById(R.id.textInfo);
			text.setText(Activa.myLanguageManager.SENSORS_CANCELLED);
			CountDownTimer timer = new CountDownTimer(6000, 1000) {
				@Override
				public void onTick(long millisUntilFinished) {}
				@Override
				public void onFinish() {
//					if (sensor.bluetoothPreviouslyConnected) 
//						Activa.myBluetoothAdapter.enable();
					Activa.myUIManager.loadGeneratedSubenvironment(Activa.myUIManager.lastSubenv, true);
				}
			};
			timer.start();
		}
	}
	
	public void finishSensorMeasurement(final String sensorString, boolean outcome, final Sensor sensor, int[] order) {
//      Activa.myBluetoothAdapter.disable();
		if (outcome) {
			Activa.myApp.setContentView(R.layout.sensorresult);
			TextView textTitle = (TextView) Activa.myApp.findViewById(R.id.startText);
			TextView text = (TextView) Activa.myApp.findViewById(R.id.textResult);
			((TextView) Activa.myApp.findViewById(R.id.resultsWord)).setText(Activa.myLanguageManager.SENSORS_RESULTSWORD);
			textTitle.setText(sensorString);
			text.setText("");
			for (int i=0; i < order.length; i++) {
				int key = order[i];
				if (key == -1) {
					text.append("\n");
					continue;
				}
				String meas = SensorManager.getMeasurementName(key);
				if (meas != null) {
					if ((SensorManager.getUnitIDForMeasurementID(key) != SensorManager.UNIT_SEC)&&(SensorManager.getUnitIDForMeasurementID(key) != SensorManager.UNIT_NULL)) text.append(meas + ": " + String.format("%.1f", (float)sensor.results.get(key)));
					else text.append(meas + ": " + Math.round((float)sensor.results.get(key)));
					text.append(" " + SensorManager.getUnitForMeasurement(SensorManager.getUnitIDForMeasurementID(key)));
					if (i != (order.length - 1)) text.append("\n");
					if (key > 1000) sensor.results.remove(key);
				}
			}
			final ImageButton layout = (ImageButton) Activa.myApp.findViewById(R.id.ok);
			OnClickListener listener = new OnClickListener() {
				@Override
				public void onClick(View v) {
					String result = sensor.getSensorGlobalResult();
					int resultInt = Integer.parseInt(result.substring(0, 1));
					String resultString = result.substring(2);
					loadSensorGlobalResult(resultString, resultInt, sensor);
				}
			};
			layout.setOnClickListener(listener);
			text.setOnClickListener(listener);
		}
		else {
			Activa.myApp.setContentView(R.layout.info);
			TextView text = (TextView) Activa.myApp.findViewById(R.id.textInfo);
			text.setText(Activa.myLanguageManager.SENSORS_CANCELLED);
			CountDownTimer timer = new CountDownTimer(6000, 1000) {
				@Override
				public void onTick(long millisUntilFinished) {}
				@Override
				public void onFinish() {
//					if (sensor.bluetoothPreviouslyConnected) 
//						Activa.myBluetoothAdapter.enable();
					Activa.myUIManager.loadGeneratedSubenvironment(Activa.myUIManager.lastSubenv, true);
				}
			};
			timer.start();
		}
	}
	
	public void finishWalkingTest(final String sensorString, boolean outcome, final SixMinutes sensor, int[] order) {
		if (outcome) {
			Activa.myApp.setContentView(R.layout.sensorresultwithgraph);
			TextView textTitle = (TextView) Activa.myApp.findViewById(R.id.startText);
			TextView text = (TextView) Activa.myApp.findViewById(R.id.textResult);
			((TextView) Activa.myApp.findViewById(R.id.resultsWord)).setText(Activa.myLanguageManager.SENSORS_RESULTSWORD);
			textTitle.setText(sensorString);
			text.setText("");
			Enumeration<Integer> keys = sensor.results.keys();
			while (keys.hasMoreElements()) {
				int key = keys.nextElement();
				String meas = SensorManager.getMeasurementName(key);
				text.append(meas + ": " + String.format("%.2f", (float)sensor.results.get(key)));
				text.append(" " + SensorManager.getUnitForMeasurement(SensorManager.getUnitIDForMeasurementID(key)));
				if (keys.hasMoreElements()) text.append("\n");
			}
			// Draw the spirometry graph
			final FrameLayout board = (FrameLayout) Activa.myApp.findViewById(R.id.graph);
			board.addView(new SixMinutesGraphViewWithCustomData(Activa.myApp, sensor.getGraphs().getHeartRate(), sensor.getGraphs().getOxygenSaturation(), sensor.getGraphs().getTime(), 250, 250));
			// A count down
			final ImageButton layout = (ImageButton) Activa.myApp.findViewById(R.id.ok);
			OnClickListener listener = new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					String result = sensor.getSensorGlobalResult();
					int resultInt = Integer.parseInt(result.substring(0, 1));
					String resultString = result.substring(2);
					loadSensorGlobalResult(resultString, resultInt, sensor);
				}
			};
			layout.setOnClickListener(listener);
		}
		else {
			Activa.myApp.setContentView(R.layout.info);
			TextView text = (TextView) Activa.myApp.findViewById(R.id.textInfo);
			text.setText(Activa.myLanguageManager.SENSORS_CANCELLED);
			CountDownTimer timer = new CountDownTimer(6000, 1000) {
				@Override
				public void onTick(long millisUntilFinished) {}
				@Override
				public void onFinish() {
//					if (sensor.bluetoothPreviouslyConnected) 
//						Activa.myBluetoothAdapter.enable();
					Activa.myUIManager.loadGeneratedSubenvironment(Activa.myUIManager.lastSubenv, true);
				}
			};
			timer.start();
		}
	}
	
	public void finishSpirometry(String sensorString, boolean outcome, final Spirometer sensor) {
//        Activa.myBluetoothAdapter.disable();
		if (outcome) {
			Activa.myApp.setContentView(R.layout.sensorresultwithgraph);
			TextView textTitle = (TextView) Activa.myApp.findViewById(R.id.startText);
			TextView text = (TextView) Activa.myApp.findViewById(R.id.textResult);
			((TextView) Activa.myApp.findViewById(R.id.resultsWord)).setText(Activa.myLanguageManager.SENSORS_RESULTSWORD);
			textTitle.setText(sensorString);
			text.setText("");
			Enumeration<Integer> keys = sensor.results.keys();
			while (keys.hasMoreElements()) {
				int key = keys.nextElement();
				String meas = SensorManager.getMeasurementName(key);
				text.append(meas + ": " + String.format("%.2f", (float)sensor.results.get(key)));
				text.append(" " + SensorManager.getUnitForMeasurement(SensorManager.getUnitIDForMeasurementID(key)));
				if (keys.hasMoreElements()) text.append("\n");
			}
			// Draw the spirometry graph
			final FrameLayout board = (FrameLayout) Activa.myApp.findViewById(R.id.graph);
			board.addView(new SpirometryGraphView(Activa.myApp, sensor));
			// A count down
			final ImageButton layout = (ImageButton) Activa.myApp.findViewById(R.id.ok);
			OnClickListener listener = new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					String result = sensor.getSensorGlobalResult();
					int resultInt = Integer.parseInt(result.substring(0, 1));
					String resultString = result.substring(2);
					loadSensorGlobalResult(resultString, resultInt, sensor);
				}
			};
			layout.setOnClickListener(listener);
		}
		else {
			Activa.myApp.setContentView(R.layout.info);
			TextView text = (TextView) Activa.myApp.findViewById(R.id.textInfo);
			text.setText(Activa.myLanguageManager.SENSORS_CANCELLED);
			CountDownTimer timer = new CountDownTimer(6000, 1000) {
				@Override
				public void onTick(long millisUntilFinished) {}
				@Override
				public void onFinish() {
//					if (sensor.bluetoothPreviouslyConnected) 
//						Activa.myBluetoothAdapter.enable();
					Activa.myUIManager.loadGeneratedSubenvironment(Activa.myUIManager.lastSubenv, true);
				}
			};
			timer.start();
		}
	}
	
	public void finishExercise(boolean outcome, final Exercise sensor) {
		if (outcome) {
			Activa.myApp.setContentView(R.layout.sensorresult);
			TextView textTitle = (TextView) Activa.myApp.findViewById(R.id.startText);
			TextView text = (TextView) Activa.myApp.findViewById(R.id.textResult);
			textTitle.setText(Activa.myLanguageManager.PROGRAMS_EXERCISE_TITLE);
			text.setText("");
			Enumeration<Integer> keys = sensor.results.keys();
			while (keys.hasMoreElements()) {
				int key = keys.nextElement();
				String meas = SensorManager.getMeasurementName(key);
				text.append(meas + ": " + String.format("%.1f", (float)sensor.results.get(key)));
				text.append(" " + SensorManager.getUnitForMeasurement(SensorManager.getUnitIDForMeasurementID(key)));
				if (keys.hasMoreElements()) text.append("\n");
			}
			CountDownTimer timer = new CountDownTimer(6000, 1000) {
				@Override
				public void onTick(long millisUntilFinished) {}
				@Override
				public void onFinish() {
//					Activa.myApp.setContentView(R.layout.sending);
//					SendSensorResult sending = new SendSensorResult(sensor);
//					Thread thread = new Thread(sending, "SENDQUESTIONNAIRE");
//					thread.start();
					String result = sensor.getSensorGlobalResult();
					int resultInt = Integer.parseInt(result.substring(0, 1));
					String resultString = result.substring(2);
					loadSensorGlobalResult(resultString, resultInt, sensor);
				}
			};
			timer.start();
		}
		else {
			Activa.myApp.setContentView(R.layout.info);
			TextView text = (TextView) Activa.myApp.findViewById(R.id.textInfo);
			text.setText(Activa.myLanguageManager.PROGRAMS_EXERCISE_CANCELLED);
			CountDownTimer timer = new CountDownTimer(6000, 1000) {
				@Override
				public void onTick(long millisUntilFinished) {}
				@Override
				public void onFinish() {
					Activa.myUIManager.loadGeneratedSubenvironment(Activa.myUIManager.lastSubenv, true);
				}
			};
			timer.start();
		}
	}
	
	public void loadSensorGlobalResult (String result, int resultInt, final Sensor sensor) {
		Activa.myApp.setContentView(R.layout.resultimage);
		TextView text = (TextView) Activa.myApp.findViewById(R.id.textInfo);
		text.setText(result);
		if (result.length() > 60) text.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18.0f); 
		ImageView image = (ImageView) Activa.myApp.findViewById(R.id.result);
		switch (resultInt) {
			case 2:
				image.setBackgroundResource(R.drawable.lightgreen);
				break;
			case 1:
				image.setBackgroundResource(R.drawable.lightorange);
				break;
			case 0:
				image.setBackgroundResource(R.drawable.lightwhite);
				break;
		}
		CountDownTimer timer = new CountDownTimer(4000, 1000) {
			@Override
			public void onTick(long millisUntilFinished) {}
			@Override
			public void onFinish() {
				if (sensor instanceof SixMinutes) {
					((SixMinutes)sensor).nextStep();
				}
				else if (sensor instanceof Exercise) {
					((Exercise)sensor).nextStep();
				}
				else {
					Activa.myApp.setContentView(R.layout.sending);
					SendSensorResult sending = new SendSensorResult(sensor);
					Thread thread = new Thread(sending, "SENDSENSOR");
					thread.start();
				}
			}
		};
		timer.start();
	}
	
	public void loadSixMinutesScreen (String resultGlobal, final long time) {
		this.state = UIManager.UI_STATE_EXERCISE;
		Activa.myApp.setContentView(R.layout.sixminutes);
		TextView hrtitle = (TextView) Activa.myApp.findViewById(R.id.hrTitle);
		TextView hrtext = (TextView) Activa.myApp.findViewById(R.id.hrText);
		TextView so2title = (TextView) Activa.myApp.findViewById(R.id.so2Title);
		TextView so2text = (TextView) Activa.myApp.findViewById(R.id.so2Text);
		TextView stepstitle = (TextView) Activa.myApp.findViewById(R.id.stepsTitle);
		TextView stepstext = (TextView) Activa.myApp.findViewById(R.id.stepsText);
		TextView global = (TextView) Activa.myApp.findViewById(R.id.textGlobal);
		hrtitle.setText(Activa.myLanguageManager.SENSORS_EXERCISE_HEARTRATE);
		so2title.setText(Activa.myLanguageManager.SENSORS_EXERCISE_O2SAT);
		stepstitle.setText(Activa.myLanguageManager.SENSORS_EXERCISE_STEPSTOP);
		hrtext.setText("-");
		so2text.setText("-");
		stepstext.setText("0/0");
		global.setText(resultGlobal);
		ImageView image = (ImageView) Activa.myApp.findViewById(R.id.resultgreen);
		image.setVisibility(View.VISIBLE);
		int seconds = (int) (time / 1000);
	    int minutes = seconds / 60;
	    seconds     = seconds % 60;
	    ((TextView) Activa.myApp.findViewById(R.id.timerText)).setText(String.format("%02d:%02d", minutes, seconds));			
	}
	
	public void loadExerciseScreen (String resultGlobal, final long time) {
		this.state = UIManager.UI_STATE_EXERCISE;
		Activa.myApp.setContentView(R.layout.exercise);
		TextView hrtitle = (TextView) Activa.myApp.findViewById(R.id.hrTitle);
		TextView hrtext = (TextView) Activa.myApp.findViewById(R.id.hrText);
		TextView so2title = (TextView) Activa.myApp.findViewById(R.id.so2Title);
		TextView so2text = (TextView) Activa.myApp.findViewById(R.id.so2Text);
		TextView stepstitle = (TextView) Activa.myApp.findViewById(R.id.stepsTitle);
		TextView stepstext = (TextView) Activa.myApp.findViewById(R.id.stepsText);
		TextView global = (TextView) Activa.myApp.findViewById(R.id.textGlobal);
		TextView speedtext = (TextView) Activa.myApp.findViewById(R.id.speedText);
		TextView distancetext = (TextView) Activa.myApp.findViewById(R.id.distanceText);
		hrtitle.setText(Activa.myLanguageManager.SENSORS_EXERCISE_HEARTRATE);
		so2title.setText(Activa.myLanguageManager.SENSORS_EXERCISE_O2SAT);
		stepstitle.setText(Activa.myLanguageManager.SENSORS_EXERCISE_STEPSTOP);
		hrtext.setText("-");
		so2text.setText("-");
		stepstext.setText("0/0");
		speedtext.setText("-");
		distancetext.setText("-");
		global.setText(resultGlobal);
		ImageView image = (ImageView) Activa.myApp.findViewById(R.id.resultgreen);
		image.setVisibility(View.VISIBLE);
		int seconds = (int) (time / 1000);
	    int minutes = seconds / 60;
	    seconds     = seconds % 60;
	    int hours = minutes / 60;
	    minutes = minutes % 60;
	    ((TextView) Activa.myApp.findViewById(R.id.timerText)).setText(String.format("%01d:%02d:%02d", hours, minutes, seconds));			
	    		
	}
	
	public void updateExerciseScreen (int hr, int so2, int steps, int stops, String resultGlobal) {
		try {
			TextView global = (TextView) Activa.myApp.findViewById(R.id.textGlobal);
			int resultImage = Integer.parseInt(resultGlobal.substring(0,1));
			resultGlobal = resultGlobal.substring(2);
			global.setText(resultGlobal);
			TextView hrtext = (TextView) Activa.myApp.findViewById(R.id.hrText);
			TextView so2text = (TextView) Activa.myApp.findViewById(R.id.so2Text);
			TextView stepstext = (TextView) Activa.myApp.findViewById(R.id.stepsText);
			if (hr != 0) hrtext.setText(hr + " bpm");
			else hrtext.setText("-");
			if (so2 != 0) so2text.setText(so2 + " %");
			else so2text.setText("-");
			stepstext.setText(steps + "/" + stops);
			ImageView imagegreen = (ImageView) Activa.myApp.findViewById(R.id.resultgreen);
			ImageView imageorange = (ImageView) Activa.myApp.findViewById(R.id.resultorange);
			ImageView imagewhite = (ImageView) Activa.myApp.findViewById(R.id.resultwhite);
			switch (resultImage) {
				case 2:
					imagegreen.setVisibility(View.VISIBLE);
					imageorange.setVisibility(View.INVISIBLE);
					imagewhite.setVisibility(View.INVISIBLE);
					break;
				case 1:
					imagegreen.setVisibility(View.INVISIBLE);
					imageorange.setVisibility(View.VISIBLE);
					imagewhite.setVisibility(View.INVISIBLE);
					break;
				case 0:
					imagegreen.setVisibility(View.INVISIBLE);
					imageorange.setVisibility(View.INVISIBLE);
					imagewhite.setVisibility(View.VISIBLE);
					break;
			}
		} catch (NullPointerException e) {
		}
	}
	
	public void updateExerciseScreen (Float speed, Float distance) {
		try {
			TextView speedtext = (TextView) Activa.myApp.findViewById(R.id.speedText);
			TextView distancetext = (TextView) Activa.myApp.findViewById(R.id.distanceText);
			if (speed != null) speedtext.setText(String.format(".1f", speed) + " km/h");
			else speedtext.setText("-");
			if (distance != 0) distancetext.setText(Math.round(distance) + " m");
			else distancetext.setText("-");
		} catch (NullPointerException e) {
		}
	}
//TODO
	public void loadMessageList(final int init) {
		Activa.myUIManager.state = UIManager.UI_STATE_MESSAGE;
		if (Activa.myMenu != null) {
			Activa.myMenu.clear();
			Activa.myInflater.inflate(R.menu.messages, Activa.myMenu);
		}
		Activa.myApp.setContentView(R.layout.list);
		int realwidth = Activa.myApp.getResources().getDisplayMetrics().widthPixels;
		((TextView) Activa.myApp.findViewById(R.id.startText)).setText(Activa.myLanguageManager.MESSAGES_TITLE);
		Hashtable<Date, SimpleMessage> messagesOrdered = new Hashtable<Date, SimpleMessage>();
		final Hashtable<Date, Boolean> receivedorsent = new Hashtable<Date, Boolean>();
		Vector<Date> datesOrdered = new Vector<Date>();
		Enumeration<SimpleMessage> enumerrec = Activa.myMessageManager.messagesReceived.elements();
		while (enumerrec.hasMoreElements()) {
			SimpleMessage temp = enumerrec.nextElement();
			Date tempdate = temp.getDate();
			while (datesOrdered.contains(tempdate)) tempdate.setTime(tempdate.getTime() + 1);
			datesOrdered.add(tempdate);
			messagesOrdered.put(tempdate, temp);
			receivedorsent.put(tempdate, true);
		}
		Enumeration<SimpleMessage> enumersent = Activa.myMessageManager.messagesSent.elements();
		while (enumersent.hasMoreElements()) {
			SimpleMessage temp = enumersent.nextElement();
			Date tempdate = temp.getDate();
			while (datesOrdered.contains(tempdate)) tempdate.setTime(tempdate.getTime() + 1);
			datesOrdered.add(tempdate);
			messagesOrdered.put(tempdate, temp);
			receivedorsent.put(tempdate, false);
		}
		Collections.sort(datesOrdered);
		Collections.reverse(datesOrdered);
		Date today = new Date();
		today.setMinutes(0);
		today.setSeconds(0);
		today.setHours(0);
		Enumeration<Date> enumeration = datesOrdered.elements();
		TableLayout messagelist = (TableLayout)Activa.myApp.findViewById(R.id.listing);
		while (enumeration.hasMoreElements()) {
			//TODO
			final Date key = enumeration.nextElement();
			final SimpleMessage message = messagesOrdered.get(key);
			TableRow buttonLayout = new TableRow(Activa.myApp);
			buttonLayout.setOrientation(TableRow.HORIZONTAL);
			buttonLayout.setGravity(Gravity.CENTER_VERTICAL);
			buttonLayout.setLayoutParams(new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));			
			TextView time = new TextView(Activa.myApp);	
			time.setLayoutParams(new TableRow.LayoutParams(realwidth*7/20, LayoutParams.WRAP_CONTENT));
			time.setPadding(5, 10, 5, 10);
			time.setTypeface(Typeface.DEFAULT_BOLD);
			time.setTextSize(17);
			time.setGravity(Gravity.LEFT);
			time.setTextColor(Color.BLACK);
			if (message.getDate().after(today)) time.setText(ActivaUtil.timeToReadableString(message.getDate()));
			else time.setText(ActivaUtil.dateToReadableString(message.getDate()));
			TextView topic = new TextView(Activa.myApp);	
			topic.setLayoutParams(new TableRow.LayoutParams(realwidth*9/20, LayoutParams.WRAP_CONTENT));
			topic.setPadding(3, 10, 3, 10);
			topic.setTypeface(Typeface.DEFAULT);
			topic.setTextSize(17);
			topic.setGravity(Gravity.LEFT);
			topic.setTextColor(Color.BLACK);
			String topicText = message.getSubject();
			if (topicText.length() > 17) topicText = topicText.substring(0, 14) + "...";
			topic.setText(topicText);
			ImageView recorsent = new ImageView(Activa.myApp);
			recorsent.setLayoutParams(new TableRow.LayoutParams(realwidth*3/20, realwidth*3/20));
			if (receivedorsent.get(key)) recorsent.setImageResource(R.drawable.mailreceived);
			else recorsent.setImageResource(R.drawable.mailsent);
			recorsent.setScaleType(ScaleType.FIT_XY);
			OnClickListener gotomsg = new OnClickListener() {			
				@Override
				public void onClick(View v) {
					Runnable run = new Runnable() {
						String content;
						@Override
						public void run() {
							handler.sendEmptyMessage(0);
							content = Activa.myMessageManager.getMessage(message);
							if (content != null) {
								handler.sendEmptyMessage(1);
							}
							else handler.sendEmptyMessage(2);
						}
						private Handler handler = new Handler(){
							@Override
							public void handleMessage(Message msg) {
								ImageView animationFrame;
								AnimationDrawable animation;
								switch (msg.what) {
									case 0:
										animationFrame = (ImageView) Activa.myApp.findViewById(R.id.animation);
										animationFrame.setVisibility(View.VISIBLE);
										animationFrame.setBackgroundResource(R.drawable.loading);
										animation = (AnimationDrawable) animationFrame.getBackground();
										animation.start();
										break;
									case 1:
										animationFrame = (ImageView) Activa.myApp.findViewById(R.id.animation);
										animation = (AnimationDrawable) animationFrame.getBackground();
										animation.stop();
										animationFrame.setVisibility(View.GONE);
										if (receivedorsent.get(key)) Activa.myUIManager.showReceivedMessage(message, content);
										else Activa.myUIManager.showSentMessage(message, content);
										break;
									case 2:
										animationFrame = (ImageView) Activa.myApp.findViewById(R.id.animation);
										animation = (AnimationDrawable) animationFrame.getBackground();
										animation.stop();
										animationFrame.setVisibility(View.GONE);
										Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.CONNECTION_FAILED);
										break;
								}
							}
						};
					};
					Thread thread = new Thread(run);
					thread.start();
				}
			};
			topic.setOnClickListener(gotomsg);
			time.setOnClickListener(gotomsg);
			recorsent.setOnClickListener(gotomsg);
			buttonLayout.addView(time);		
			buttonLayout.addView(topic);	
			buttonLayout.addView(recorsent);	
			buttonLayout.setOnClickListener(gotomsg);
			messagelist.addView(buttonLayout);
			View separator = new View(Activa.myApp);
			LayoutParams separatorLayout = new LayoutParams(android.view.ViewGroup.LayoutParams.FILL_PARENT, 1);
			separator.setLayoutParams(separatorLayout);
			separator.setBackgroundColor(Color.BLACK);
			messagelist.addView(separator);
		}
		if ((Activa.myMessageManager.messagesReceived.size() > (init + 20))||(Activa.myMessageManager.messagesSent.size() > (init + 20))) {
			TextView more = new TextView(Activa.myApp);	
			more.setLayoutParams(new TableRow.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
			more.setPadding(5, 10, 5, 10);
			more.setTypeface(Typeface.DEFAULT_BOLD);
			more.setTextSize(17);
			more.setGravity(Gravity.LEFT);
			more.setTextColor(Color.BLACK);
			more.setText(Activa.myLanguageManager.TEXT_LOADMOREMESSAGE);
			more.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Runnable run = new Runnable() {
						String content;
						@Override
						public void run() {
							handler.sendEmptyMessage(0);
							Activa.myMessageManager.requestReceivedMessages(init + 20);
							Activa.myMessageManager.requestSentMessages(init + 20);
							handler.sendEmptyMessage(1);
						}
						private Handler handler = new Handler(){
							@Override
							public void handleMessage(Message msg) {
								ImageView animationFrame;
								AnimationDrawable animation;
								switch (msg.what) {
									case 0:
										animationFrame = (ImageView) Activa.myApp.findViewById(R.id.animation);
										animationFrame.setVisibility(View.VISIBLE);
										animationFrame.setBackgroundResource(R.drawable.loading);
										animation = (AnimationDrawable) animationFrame.getBackground();
										animation.start();
										break;
									case 1:
										animationFrame = (ImageView) Activa.myApp.findViewById(R.id.animation);
										animation = (AnimationDrawable) animationFrame.getBackground();
										animation.stop();
										animationFrame.setVisibility(View.GONE);
										Activa.myUIManager.loadMessageList(init + 20);
										break;
								}
							}
						};
					};
					Thread thread = new Thread(run);
					thread.start();
				}
			});
			messagelist.addView(more);
		}
		ImageButton back = (ImageButton) Activa.myApp.findViewById(R.id.previous);
		ImageButton create = (ImageButton) Activa.myApp.findViewById(R.id.help);
		create.setVisibility(View.VISIBLE);
		create.setImageResource(R.drawable.mailcreate);
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Activa.myUIManager.loadGeneratedSubenvironment(Activa.myUIManager.lastSubenv, true);
			}
		});
		create.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Activa.myUIManager.createMessage(null);
			}
		});
		ImageView tosms = (ImageView) Activa.myApp.findViewById(R.id.animation);
		tosms.setVisibility(View.VISIBLE);
		try {
	        PackageManager manager = Activa.myApp.getPackageManager();
	        tosms.setBackgroundDrawable(manager.getActivityIcon(new ComponentName(Activa.myExteriorManager.externalApplications.get(ExteriorManager.APP_SMS).packageName, Activa.myExteriorManager.externalApplications.get(ExteriorManager.APP_SMS).activityName)));
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		tosms.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Activa.myExteriorManager.externalApplications.get(ExteriorManager.APP_SMS).startApplication();
			}
		});
		/*final ScrollView scroller = (ScrollView) Activa.myApp.findViewById(R.id.scroller);
		scroller.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if ((Activa.myMessageManager.messagesReceived.size() >= 20) && (event.getAction() == MotionEvent.ACTION_UP)) {
					int prev = scroller.getScrollY();
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					int now = scroller.getScrollY();
					if ((now <= (prev + 10))&&(now >= (prev - 10))) {
						Activa.myMessageManager.requestReceivedMessages(init + 20);
						Activa.myUIManager.loadMessageList(init + 20);
						((ScrollView) Activa.myApp.findViewById(R.id.scroller)).scrollTo(0, now);
					}
				}
				return false;
			}
		});*/
	}

	public void loadReceivedMessageList(final int init) {
		Activa.myUIManager.state = UIManager.UI_STATE_MESSAGEREC;
		if (Activa.myMenu != null) {
			Activa.myMenu.clear();
			Activa.myInflater.inflate(R.menu.messages, Activa.myMenu);
		}
		Activa.myApp.setContentView(R.layout.list);
//		int realwidth = Activa.myApp.getResources().getDisplayMetrics().widthPixels;
		((TextView) Activa.myApp.findViewById(R.id.startText)).setText(Activa.myLanguageManager.MESSAGES_TITLE_REC);
		//TABS
		/*LinearLayout tabs = (LinearLayout) Activa.myApp.findViewById(R.id.tabs);
		LinearLayout tab = new LinearLayout(Activa.myApp);
		tab.setOrientation(LinearLayout.VERTICAL);
		tab.setLayoutParams(new LinearLayout.LayoutParams(realwidth/5, LinearLayout.LayoutParams.FILL_PARENT));
		tab.setBackgroundResource(R.drawable.tab_selected);
		ImageButton but = new ImageButton(Activa.myApp);
		android.widget.LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, 40);
		params.setMargins(0, 5, 0, 4);
		but.setLayoutParams(params);
		but.setBackgroundResource(R.drawable.iconbg);
		but.setImageResource(R.drawable.msgreceived);
		OnClickListener listener = new OnClickListener() {
			@Override
			public void onClick(View v) {
				loadReceivedMessageList(0);
			}
		};
		tab.setOnClickListener(listener);
		but.setOnClickListener(listener);
		tab.addView(but);
		tabs.addView(tab);
		tab = new LinearLayout(Activa.myApp);
		tab.setOrientation(LinearLayout.VERTICAL);
		tab.setLayoutParams(new LinearLayout.LayoutParams(realwidth/5, LinearLayout.LayoutParams.FILL_PARENT));
		tab.setBackgroundResource(R.drawable.tab_not_selected);
		but = new ImageButton(Activa.myApp);
		params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, 40);
		params.setMargins(0, 5, 0, 4);
		but.setLayoutParams(params);
		but.setBackgroundResource(R.drawable.iconbg);
		but.setImageResource(R.drawable.msgsent);
		listener = new OnClickListener() {
			@Override
			public void onClick(View v) {
				loadSentMessageList(0);
			}
		};
		tab.setOnClickListener(listener);
		but.setOnClickListener(listener);
		tab.addView(but);
		tabs.addView(tab);
		tab = new LinearLayout(Activa.myApp);
		tab.setOrientation(LinearLayout.VERTICAL);
		tab.setLayoutParams(new LinearLayout.LayoutParams(realwidth/5, LinearLayout.LayoutParams.FILL_PARENT));
		tab.setBackgroundResource(R.drawable.tab_not_selected);
		but = new ImageButton(Activa.myApp);
		params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, 40);
		params.setMargins(0, 5, 0, 4);
		but.setLayoutParams(params);
		but.setBackgroundResource(R.drawable.iconbg);
		but.setImageResource(R.drawable.msgcontacts);
		listener = new OnClickListener() {
			@Override
			public void onClick(View v) {
				loadContactList(true);
			}
		};
		tab.setOnClickListener(listener);
		but.setOnClickListener(listener);
		tab.addView(but);
		tabs.addView(tab);
		tab = new LinearLayout(Activa.myApp);
		tab.setOrientation(LinearLayout.VERTICAL);
		tab.setLayoutParams(new LinearLayout.LayoutParams(realwidth/5, LinearLayout.LayoutParams.FILL_PARENT));
		tab.setBackgroundResource(R.drawable.tab_not_selected);
		but = new ImageButton(Activa.myApp);
		params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, 40);
		params.setMargins(0, 5, 0, 4);
		but.setLayoutParams(params);
		but.setBackgroundResource(R.drawable.iconbg);
		but.setImageResource(R.drawable.msgcontactsentering);
		listener = new OnClickListener() {
			@Override
			public void onClick(View v) {
				loadEntryContactList(true);
			}
		};
		tab.setOnClickListener(listener);
		but.setOnClickListener(listener);
		tab.addView(but);
		tabs.addView(tab);
		tab = new LinearLayout(Activa.myApp);
		tab.setOrientation(LinearLayout.VERTICAL);
		tab.setLayoutParams(new LinearLayout.LayoutParams(realwidth/5, LinearLayout.LayoutParams.FILL_PARENT));
		tab.setBackgroundResource(R.drawable.tab_not_selected);
		but = new ImageButton(Activa.myApp);
		params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, 40);
		params.setMargins(0, 5, 0, 4);
		but.setLayoutParams(params);
		but.setBackgroundResource(R.drawable.iconbg);
		but.setImageResource(R.drawable.refreshmenu);
		listener = new OnClickListener() {
			@Override
			public void onClick(View v) {
				ImageView animationFrame = (ImageView) Activa.myApp.findViewById(R.id.animation);
				animationFrame.setVisibility(View.VISIBLE);
				animationFrame.setBackgroundResource(R.drawable.loading);
				AnimationDrawable animation = (AnimationDrawable) animationFrame.getBackground();
				animation.start();
				((FrameLayout)Activa.myApp.findViewById(R.id.layout)).invalidate();
				Runnable run = new Runnable() {
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
						        	Activa.myUIManager.loadReceivedMessageList(0);
									break;
							}
						}
	
					};
				};
				Thread thread = new Thread(run);
				thread.start();
	            return;
			}
		};
		tab.setOnClickListener(listener);
		but.setOnClickListener(listener);
		tab.addView(but);
		tabs.addView(tab);*/
		//FOLLOW
		Hashtable<Date, SimpleMessage> messagesOrdered = new Hashtable<Date, SimpleMessage>();
		Vector<Date> datesOrdered = new Vector<Date>();
		Enumeration<SimpleMessage> enumer = Activa.myMessageManager.messagesReceived.elements();
		while (enumer.hasMoreElements()) {
			SimpleMessage temp = enumer.nextElement();
			Date tempdate = temp.getDate();
			while (datesOrdered.contains(tempdate)) tempdate.setTime(tempdate.getTime() + 1);
			datesOrdered.add(tempdate);
			messagesOrdered.put(tempdate, temp);
		}
		Collections.sort(datesOrdered);
		Collections.reverse(datesOrdered);
		Date today = new Date();
		today.setMinutes(0);
		today.setSeconds(0);
		today.setHours(0);
		Enumeration<Date> enumeration = datesOrdered.elements();
		TableLayout messagelist = (TableLayout)Activa.myApp.findViewById(R.id.listing);
		while (enumeration.hasMoreElements()) {
			//TODO
			final SimpleMessage message = messagesOrdered.get(enumeration.nextElement());
			TableRow buttonLayout = new TableRow(Activa.myApp);
			buttonLayout.setOrientation(TableRow.HORIZONTAL);
			buttonLayout.setGravity(Gravity.CENTER_VERTICAL);
			buttonLayout.setLayoutParams(new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));			
			TextView time = new TextView(Activa.myApp);	
			time.setLayoutParams(new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			time.setPadding(5, 10, 5, 10);
			time.setTypeface(Typeface.DEFAULT_BOLD);
			time.setTextSize(20);
			time.setGravity(Gravity.LEFT);
			time.setTextColor(Color.BLACK);
			if (message.getDate().after(today)) time.setText(ActivaUtil.timeToReadableString(message.getDate()));
			else time.setText(ActivaUtil.dateToReadableString(message.getDate()));
			TextView topic = new TextView(Activa.myApp);	
			topic.setLayoutParams(new TableRow.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
			topic.setPadding(3, 10, 3, 10);
			topic.setTypeface(Typeface.DEFAULT);
			topic.setTextSize(20);
			topic.setGravity(Gravity.LEFT);
			topic.setTextColor(Color.BLACK);
			String topicText = message.getSubject();
			if (topicText.length() > 20) topicText = topicText.substring(0, 17) + "...";
			topic.setText(topicText);
			OnClickListener gotomsg = new OnClickListener() {			
				@Override
				public void onClick(View v) {
					Runnable run = new Runnable() {
						String content;
						@Override
						public void run() {
							handler.sendEmptyMessage(0);
							content = Activa.myMessageManager.getMessage(message);
							if (content != null) {
								handler.sendEmptyMessage(1);
							}
							else handler.sendEmptyMessage(2);
						}
						private Handler handler = new Handler(){
							@Override
							public void handleMessage(Message msg) {
								ImageView animationFrame;
								AnimationDrawable animation;
								switch (msg.what) {
									case 0:
										animationFrame = (ImageView) Activa.myApp.findViewById(R.id.animation);
										animationFrame.setVisibility(View.VISIBLE);
										animationFrame.setBackgroundResource(R.drawable.loading);
										animation = (AnimationDrawable) animationFrame.getBackground();
										animation.start();
										break;
									case 1:
										animationFrame = (ImageView) Activa.myApp.findViewById(R.id.animation);
										animation = (AnimationDrawable) animationFrame.getBackground();
										animation.stop();
										animationFrame.setVisibility(View.GONE);
										Activa.myUIManager.showReceivedMessage(message, content);
										break;
									case 2:
										animationFrame = (ImageView) Activa.myApp.findViewById(R.id.animation);
										animation = (AnimationDrawable) animationFrame.getBackground();
										animation.stop();
										animationFrame.setVisibility(View.GONE);
										Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.CONNECTION_FAILED);
										break;
								}
							}
						};
					};
					Thread thread = new Thread(run);
					thread.start();
				}
			};
			topic.setOnClickListener(gotomsg);
			time.setOnClickListener(gotomsg);
			buttonLayout.addView(time);		
			buttonLayout.addView(topic);	
			buttonLayout.setOnClickListener(gotomsg);
			messagelist.addView(buttonLayout);
			View separator = new View(Activa.myApp);
			LayoutParams separatorLayout = new LayoutParams(android.view.ViewGroup.LayoutParams.FILL_PARENT, 1);
			separator.setLayoutParams(separatorLayout);
			separator.setBackgroundColor(Color.BLACK);
			messagelist.addView(separator);
		}
		if ((Activa.myMessageManager.messagesReceived.size() > (init + 20))) {
			TextView more = new TextView(Activa.myApp);	
			more.setLayoutParams(new TableRow.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
			more.setPadding(5, 10, 5, 10);
			more.setTypeface(Typeface.DEFAULT_BOLD);
			more.setTextSize(17);
			more.setGravity(Gravity.LEFT);
			more.setTextColor(Color.BLACK);
			more.setText(Activa.myLanguageManager.TEXT_LOADMOREMESSAGE);
			more.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Runnable run = new Runnable() {
						String content;
						@Override
						public void run() {
							handler.sendEmptyMessage(0);
							Activa.myMessageManager.requestReceivedMessages(init + 20);
							handler.sendEmptyMessage(1);
						}
						private Handler handler = new Handler(){
							@Override
							public void handleMessage(Message msg) {
								ImageView animationFrame;
								AnimationDrawable animation;
								switch (msg.what) {
									case 0:
										animationFrame = (ImageView) Activa.myApp.findViewById(R.id.animation);
										animationFrame.setVisibility(View.VISIBLE);
										animationFrame.setBackgroundResource(R.drawable.loading);
										animation = (AnimationDrawable) animationFrame.getBackground();
										animation.start();
										break;
									case 1:
										animationFrame = (ImageView) Activa.myApp.findViewById(R.id.animation);
										animation = (AnimationDrawable) animationFrame.getBackground();
										animation.stop();
										animationFrame.setVisibility(View.GONE);
										Activa.myUIManager.loadReceivedMessageList(init + 20);
										break;
								}
							}
						};
					};
					Thread thread = new Thread(run);
					thread.start();
				}
			});
			messagelist.addView(more);
		}
		ImageButton back = (ImageButton) Activa.myApp.findViewById(R.id.previous);
		ImageButton create = (ImageButton) Activa.myApp.findViewById(R.id.help);
		create.setVisibility(View.VISIBLE);
		create.setImageResource(R.drawable.mailcreate);
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Activa.myUIManager.loadMessageList(0);
			}
		});
		create.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Activa.myUIManager.createMessage(null);
			}
		});
		ImageView tosms = (ImageView) Activa.myApp.findViewById(R.id.animation);
		tosms.setVisibility(View.VISIBLE);
		try {
	        PackageManager manager = Activa.myApp.getPackageManager();
	        tosms.setBackgroundDrawable(manager.getActivityIcon(new ComponentName(Activa.myExteriorManager.externalApplications.get(ExteriorManager.APP_SMS).packageName, Activa.myExteriorManager.externalApplications.get(ExteriorManager.APP_SMS).activityName)));
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		tosms.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Activa.myExteriorManager.externalApplications.get(ExteriorManager.APP_SMS).startApplication();
			}
		});
	}

	public void loadSentMessageList(final int init) {
		Activa.myUIManager.state = UIManager.UI_STATE_MESSAGESEN;
		if (Activa.myMenu != null) {
			Activa.myMenu.clear();
			Activa.myInflater.inflate(R.menu.messages, Activa.myMenu);
		}
		Activa.myApp.setContentView(R.layout.list);
//		int realwidth = Activa.myApp.getResources().getDisplayMetrics().widthPixels;
		((TextView) Activa.myApp.findViewById(R.id.startText)).setText(Activa.myLanguageManager.MESSAGES_TITLE_SENT);
		//TABS
		/*LinearLayout tabs = (LinearLayout) Activa.myApp.findViewById(R.id.tabs);
		LinearLayout tab = new LinearLayout(Activa.myApp);
		tab.setOrientation(LinearLayout.VERTICAL);
		tab.setLayoutParams(new LinearLayout.LayoutParams(realwidth/5, LinearLayout.LayoutParams.FILL_PARENT));
		tab.setBackgroundResource(R.drawable.tab_not_selected);
		ImageButton but = new ImageButton(Activa.myApp);
		android.widget.LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, 40);
		params.setMargins(0, 5, 0, 4);
		but.setLayoutParams(params);
		but.setBackgroundResource(R.drawable.iconbg);
		but.setImageResource(R.drawable.msgreceived);
		OnClickListener listener = new OnClickListener() {
			@Override
			public void onClick(View v) {
				loadReceivedMessageList(0);
			}
		};
		tab.setOnClickListener(listener);
		but.setOnClickListener(listener);
		tab.addView(but);
		tabs.addView(tab);
		tab = new LinearLayout(Activa.myApp);
		tab.setOrientation(LinearLayout.VERTICAL);
		tab.setLayoutParams(new LinearLayout.LayoutParams(realwidth/5, LinearLayout.LayoutParams.FILL_PARENT));
		tab.setBackgroundResource(R.drawable.tab_selected);
		but = new ImageButton(Activa.myApp);
		params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, 40);
		params.setMargins(0, 5, 0, 4);
		but.setLayoutParams(params);
		but.setBackgroundResource(R.drawable.iconbg);
		but.setImageResource(R.drawable.msgsent);
		listener = new OnClickListener() {
			@Override
			public void onClick(View v) {
				loadSentMessageList(0);
			}
		};
		tab.setOnClickListener(listener);
		but.setOnClickListener(listener);
		tab.addView(but);
		tabs.addView(tab);
		tab = new LinearLayout(Activa.myApp);
		tab.setOrientation(LinearLayout.VERTICAL);
		tab.setLayoutParams(new LinearLayout.LayoutParams(realwidth/5, LinearLayout.LayoutParams.FILL_PARENT));
		tab.setBackgroundResource(R.drawable.tab_not_selected);
		but = new ImageButton(Activa.myApp);
		params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, 40);
		params.setMargins(0, 5, 0, 4);
		but.setLayoutParams(params);
		but.setBackgroundResource(R.drawable.iconbg);
		but.setImageResource(R.drawable.msgcontacts);
		listener = new OnClickListener() {
			@Override
			public void onClick(View v) {
				loadContactList(true);
			}
		};
		tab.setOnClickListener(listener);
		but.setOnClickListener(listener);
		tab.addView(but);
		tabs.addView(tab);
		tab = new LinearLayout(Activa.myApp);
		tab.setOrientation(LinearLayout.VERTICAL);
		tab.setLayoutParams(new LinearLayout.LayoutParams(realwidth/5, LinearLayout.LayoutParams.FILL_PARENT));
		tab.setBackgroundResource(R.drawable.tab_not_selected);
		but = new ImageButton(Activa.myApp);
		params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, 40);
		params.setMargins(0, 5, 0, 4);
		but.setLayoutParams(params);
		but.setBackgroundResource(R.drawable.iconbg);
		but.setImageResource(R.drawable.msgcontactsentering);
		listener = new OnClickListener() {
			@Override
			public void onClick(View v) {
				loadEntryContactList(true);
			}
		};
		tab.setOnClickListener(listener);
		but.setOnClickListener(listener);
		tab.addView(but);
		tabs.addView(tab);
		tab = new LinearLayout(Activa.myApp);
		tab.setOrientation(LinearLayout.VERTICAL);
		tab.setLayoutParams(new LinearLayout.LayoutParams(realwidth/5, LinearLayout.LayoutParams.FILL_PARENT));
		tab.setBackgroundResource(R.drawable.tab_not_selected);
		but = new ImageButton(Activa.myApp);
		params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, 40);
		params.setMargins(0, 5, 0, 4);
		but.setLayoutParams(params);
		but.setBackgroundResource(R.drawable.iconbg);
		but.setImageResource(R.drawable.refreshmenu);
		listener = new OnClickListener() {
			@Override
			public void onClick(View v) {
				ImageView animationFrame = (ImageView) Activa.myApp.findViewById(R.id.animation);
				animationFrame.setVisibility(View.VISIBLE);
				animationFrame.setBackgroundResource(R.drawable.loading);
				AnimationDrawable animation = (AnimationDrawable) animationFrame.getBackground();
				animation.start();
				((FrameLayout)Activa.myApp.findViewById(R.id.layout)).invalidate();
				Runnable run = new Runnable() {
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
						        	Activa.myUIManager.loadSentMessageList(0);
									break;
							}
						}
	
					};
				};
				Thread thread = new Thread(run);
				thread.start();
	            return;
			}
		};
		tab.setOnClickListener(listener);
		but.setOnClickListener(listener);
		tab.addView(but);
		tabs.addView(tab);*/
		//FOLLOW
		Hashtable<Date, SimpleMessage> messagesOrdered = new Hashtable<Date, SimpleMessage>();
		Vector<Date> datesOrdered = new Vector<Date>();
		Enumeration<SimpleMessage> enumer = Activa.myMessageManager.messagesSent.elements();
		while (enumer.hasMoreElements()) {
			SimpleMessage temp = enumer.nextElement();
			Date tempdate = temp.getDate();
			while (datesOrdered.contains(tempdate)) tempdate.setTime(tempdate.getTime() + 1);
			datesOrdered.add(tempdate);
			messagesOrdered.put(tempdate, temp);
		}
		Collections.sort(datesOrdered);
		Collections.reverse(datesOrdered);
		Date today = new Date();
		today.setMinutes(0);
		today.setSeconds(0);
		today.setHours(0);
		Enumeration<Date> enumeration = datesOrdered.elements();
		TableLayout messagelist = (TableLayout)Activa.myApp.findViewById(R.id.listing);
		while (enumeration.hasMoreElements()) {
			final SimpleMessage message = messagesOrdered.get(enumeration.nextElement());
			TableRow buttonLayout = new TableRow(Activa.myApp);
			buttonLayout.setOrientation(TableRow.HORIZONTAL);
			buttonLayout.setGravity(Gravity.CENTER_VERTICAL);
			buttonLayout.setLayoutParams(new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));			
			TextView time = new TextView(Activa.myApp);	
			time.setLayoutParams(new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			time.setPadding(5, 10, 5, 10);
			time.setTypeface(Typeface.DEFAULT_BOLD);
			time.setTextSize(20);
			time.setGravity(Gravity.LEFT);
			time.setTextColor(Color.BLACK);
			if (message.getDate().after(today)) time.setText(ActivaUtil.timeToReadableString(message.getDate()));
			else time.setText(ActivaUtil.dateToReadableString(message.getDate()));
			TextView topic = new TextView(Activa.myApp);	
			topic.setLayoutParams(new TableRow.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
			topic.setPadding(3, 10, 3, 10);
			topic.setTypeface(Typeface.DEFAULT);
			topic.setTextSize(20);
			topic.setGravity(Gravity.LEFT);
			topic.setTextColor(Color.BLACK);
			String topicText = message.getSubject();
			if (topicText.length() > 20) topicText = topicText.substring(0, 17) + "...";
			topic.setText(topicText);
			OnClickListener listener2 = new OnClickListener() {			
				@Override
				public void onClick(View v) {
					Runnable run = new Runnable() {
						String content;
						@Override
						public void run() {
							handler.sendEmptyMessage(0);
							content = Activa.myMessageManager.getMessage(message);
							if (content != null) {
								handler.sendEmptyMessage(1);
							}
							else handler.sendEmptyMessage(2);
						}
						private Handler handler = new Handler(){
							@Override
							public void handleMessage(Message msg) {
								ImageView animationFrame;
								AnimationDrawable animation;
								switch (msg.what) {
									case 0:
										animationFrame = (ImageView) Activa.myApp.findViewById(R.id.animation);
										animationFrame.setVisibility(View.VISIBLE);
										animationFrame.setBackgroundResource(R.drawable.loading);
										animation = (AnimationDrawable) animationFrame.getBackground();
										animation.start();
										break;
									case 1:
										animationFrame = (ImageView) Activa.myApp.findViewById(R.id.animation);
										animation = (AnimationDrawable) animationFrame.getBackground();
										animation.stop();
										animationFrame.setVisibility(View.GONE);
										Activa.myUIManager.showSentMessage(message, content);
										break;
									case 2:
										animationFrame = (ImageView) Activa.myApp.findViewById(R.id.animation);
										animation = (AnimationDrawable) animationFrame.getBackground();
										animation.stop();
										animationFrame.setVisibility(View.GONE);
										Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.CONNECTION_FAILED);
										break;
								}
							}
						};
					};
					Thread thread = new Thread(run);
					thread.start();
				}
			};
			topic.setOnClickListener(listener2);
			time.setOnClickListener(listener2);
			buttonLayout.addView(time);		
			buttonLayout.addView(topic);	
			buttonLayout.setOnClickListener(listener2);
			messagelist.addView(buttonLayout);
			View separator = new View(Activa.myApp);
			LayoutParams separatorLayout = new LayoutParams(android.view.ViewGroup.LayoutParams.FILL_PARENT, 1);
			separator.setLayoutParams(separatorLayout);
			separator.setBackgroundColor(Color.BLACK);
			messagelist.addView(separator);
		}
		if ((Activa.myMessageManager.messagesSent.size() > (init + 20))) {
			TextView more = new TextView(Activa.myApp);	
			more.setLayoutParams(new TableRow.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
			more.setPadding(5, 10, 5, 10);
			more.setTypeface(Typeface.DEFAULT_BOLD);
			more.setTextSize(17);
			more.setGravity(Gravity.LEFT);
			more.setTextColor(Color.BLACK);
			more.setText(Activa.myLanguageManager.TEXT_LOADMOREMESSAGE);
			more.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Runnable run = new Runnable() {
						String content;
						@Override
						public void run() {
							handler.sendEmptyMessage(0);
							Activa.myMessageManager.requestSentMessages(init + 20);
							handler.sendEmptyMessage(1);
						}
						private Handler handler = new Handler(){
							@Override
							public void handleMessage(Message msg) {
								ImageView animationFrame;
								AnimationDrawable animation;
								switch (msg.what) {
									case 0:
										animationFrame = (ImageView) Activa.myApp.findViewById(R.id.animation);
										animationFrame.setVisibility(View.VISIBLE);
										animationFrame.setBackgroundResource(R.drawable.loading);
										animation = (AnimationDrawable) animationFrame.getBackground();
										animation.start();
										break;
									case 1:
										animationFrame = (ImageView) Activa.myApp.findViewById(R.id.animation);
										animation = (AnimationDrawable) animationFrame.getBackground();
										animation.stop();
										animationFrame.setVisibility(View.GONE);
										Activa.myUIManager.loadSentMessageList(init + 20);
										break;
								}
							}
						};
					};
					Thread thread = new Thread(run);
					thread.start();
				}
			});
			messagelist.addView(more);
		}
		ImageButton back = (ImageButton) Activa.myApp.findViewById(R.id.previous);
		ImageButton create = (ImageButton) Activa.myApp.findViewById(R.id.help);
		create.setVisibility(View.VISIBLE);
		create.setImageResource(R.drawable.mailcreate);
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Activa.myUIManager.loadMessageList(0);
			}
		});
		create.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Activa.myUIManager.createMessage(null);
			}
		});
		ImageView tosms = (ImageView) Activa.myApp.findViewById(R.id.animation);
		tosms.setVisibility(View.VISIBLE);
		try {
	        PackageManager manager = Activa.myApp.getPackageManager();
	        tosms.setBackgroundDrawable(manager.getActivityIcon(new ComponentName(Activa.myExteriorManager.externalApplications.get(ExteriorManager.APP_SMS).packageName, Activa.myExteriorManager.externalApplications.get(ExteriorManager.APP_SMS).activityName)));
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		tosms.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Activa.myExteriorManager.externalApplications.get(ExteriorManager.APP_SMS).startApplication();
			}
		});
	}

	public void showReceivedMessage(final SimpleMessage message, final String content) {
		final int prevstate = Activa.myUIManager.state;
		Activa.myUIManager.state = UIManager.UI_STATE_MESSAGEREADING;
		if (Activa.myMenu != null) {
			Activa.myMenu.clear();
			Activa.myInflater.inflate(R.menu.messages, Activa.myMenu);
		}
		Activa.myMessageManager.currentMessage = message;
		Activa.myMessageManager.currentContent = content;
		Activa.myApp.setContentView(R.layout.messagetoreceive);
		((TextView) Activa.myApp.findViewById(R.id.startText)).setText(Activa.myLanguageManager.MESSAGES_TITLE);
		((TextView) Activa.myApp.findViewById(R.id.senderText)).setText(Activa.myLanguageManager.MESSAGES_SENDER);
		((TextView) Activa.myApp.findViewById(R.id.topicText)).setText(Activa.myLanguageManager.MESSAGES_TOPIC);
		((TextView) Activa.myApp.findViewById(R.id.dateText)).setText(Activa.myLanguageManager.MESSAGES_DATE);
		Contact sender = message.getSender();
		if (sender != null) ((TextView) Activa.myApp.findViewById(R.id.sender)).setText(sender.getFirstName() + " " + sender.getLastName());
		else ((TextView) Activa.myApp.findViewById(R.id.sender)).setText("USER ID " + message.getSender());
		((TextView) Activa.myApp.findViewById(R.id.topic)).setText(message.getSubject());
		((TextView) Activa.myApp.findViewById(R.id.text)).setText(content);
		((TextView) Activa.myApp.findViewById(R.id.date)).setText(ActivaUtil.dateToReadableString(message.getDate()) + ", " + ActivaUtil.timeToReadableString(message.getDate()));
		ImageButton back = (ImageButton) Activa.myApp.findViewById(R.id.previous);
		ImageButton response = (ImageButton) Activa.myApp.findViewById(R.id.response);
		ImageButton resend = (ImageButton) Activa.myApp.findViewById(R.id.resend);
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (prevstate == UIManager.UI_STATE_MESSAGEREC) Activa.myUIManager.loadReceivedMessageList(0);
				else Activa.myUIManager.loadMessageList(0); 
			}
		});
		response.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ArrayList<Contact> users = new ArrayList<Contact>();
				users.add(message.getSender());
				Activa.myUIManager.createMessage(users);
				String bwtopic = "RE: " + Activa.myMessageManager.currentMessage.getSubject();
				((EditText) Activa.myApp.findViewById(R.id.topic)).setText(bwtopic);
				String bwtext = "\n\nRE --------------------------------\n\nSender: ";
				bwtext += message.getSender().getFirstName() + " " + message.getSender().getLastName();
				bwtext += "\nReceiver: ";
				for (Contact user : message.getReceivers()) {
					bwtext += user.getFirstName() + " " + user.getLastName() + ",";
				}
				bwtext = bwtext.substring(0, (bwtext.length() - 1));
				bwtext += "\nTopic: " + message.getSubject() + "\n"
					+ "Date: " + ActivaUtil.dateToReadableString(message.getDate()) + ", " + ActivaUtil.timeToReadableString(message.getDate())
					+ "\n\n" + content;
				((EditText) Activa.myApp.findViewById(R.id.text)).setText(bwtext);
			}
		});
		resend.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Activa.myUIManager.createMessage(null);
				String bwtopic = "FW: " + Activa.myMessageManager.currentMessage.getSubject();
				((EditText) Activa.myApp.findViewById(R.id.topic)).setText(bwtopic);
				String bwtext = "\n\nFW --------------------------------\n\nSender: ";
				bwtext += message.getSender().getFirstName() + " " + message.getSender().getLastName();
				bwtext += "\nReceiver: ";
				for (Contact user : message.getReceivers()) {
					bwtext += user.getFirstName() + " " + user.getLastName() + ",";
				}
				bwtext = bwtext.substring(0, (bwtext.length() - 1));
				bwtext += "\nTopic: " + message.getSubject() + "\n"
					+ "Date: " + ActivaUtil.dateToReadableString(message.getDate()) + ", " + ActivaUtil.timeToReadableString(message.getDate())
					+ "\n\n" + content;
				((EditText) Activa.myApp.findViewById(R.id.text)).setText(bwtext);
			}
		});
	}

	public void showSentMessage(final SimpleMessage message, final String content) {
		final int prevstate = Activa.myUIManager.state;
		Activa.myUIManager.state = UIManager.UI_STATE_MESSAGEREADING;
		if (Activa.myMenu != null) {
			Activa.myMenu.clear();
			Activa.myInflater.inflate(R.menu.messages, Activa.myMenu);
		}
		Activa.myMessageManager.currentMessage = message;
		Activa.myMessageManager.currentContent = content;
		Activa.myApp.setContentView(R.layout.messagetoreceive);
		((TextView) Activa.myApp.findViewById(R.id.startText)).setText(Activa.myLanguageManager.MESSAGES_TITLE_SENT);
		((TextView) Activa.myApp.findViewById(R.id.senderText)).setText(Activa.myLanguageManager.MESSAGES_RECEIVER);
		((TextView) Activa.myApp.findViewById(R.id.topicText)).setText(Activa.myLanguageManager.MESSAGES_TOPIC);
		((TextView) Activa.myApp.findViewById(R.id.dateText)).setText(Activa.myLanguageManager.MESSAGES_DATE);
		TextView receivers = (TextView) Activa.myApp.findViewById(R.id.sender);
		for (Contact receiver : message.getReceivers()) {
			receivers.append(receiver.getFirstName() + " " + receiver.getLastName() + ", ");
		}
		if (receivers.getText().length() > 0) receivers.setText(receivers.getText().subSequence(0, (receivers.getText().length() - 2)));
		((TextView) Activa.myApp.findViewById(R.id.topic)).setText(message.getSubject());
		((TextView) Activa.myApp.findViewById(R.id.text)).setText(content);
		((TextView) Activa.myApp.findViewById(R.id.date)).setText(ActivaUtil.dateToReadableString(message.getDate()) + ", " + ActivaUtil.timeToReadableString(message.getDate()));
		ImageButton back = (ImageButton) Activa.myApp.findViewById(R.id.previous);
		ImageButton response = (ImageButton) Activa.myApp.findViewById(R.id.response);
		ImageButton resend = (ImageButton) Activa.myApp.findViewById(R.id.resend);
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (prevstate == UIManager.UI_STATE_MESSAGEREC) Activa.myUIManager.loadSentMessageList(0);
				else Activa.myUIManager.loadMessageList(0);
			}
		});
		response.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ArrayList<Contact> users = new ArrayList<Contact>();
				users.add(message.getSender());
				Activa.myUIManager.createMessage(users);
				String bwtopic = "RE: " + Activa.myMessageManager.currentMessage.getSubject();
				((EditText) Activa.myApp.findViewById(R.id.topic)).setText(bwtopic);
				String bwtext = "\n\nRE --------------------------------\n\nSender: ";
				bwtext += message.getSender().getFirstName() + " " + message.getSender().getLastName();
				bwtext += "\nReceiver: ";
				for (Contact user : message.getReceivers()) {
					bwtext += user.getFirstName() + " " + user.getLastName() + ",";
				}
				bwtext = bwtext.substring(0, (bwtext.length() - 1));
				bwtext += "\nTopic: " + message.getSubject() + "\n"
					+ "Date: " + ActivaUtil.dateToReadableString(message.getDate()) + ", " + ActivaUtil.timeToReadableString(message.getDate())
					+ "\n\n" + content;
				((EditText) Activa.myApp.findViewById(R.id.text)).setText(bwtext);
			}
		});
		resend.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Activa.myUIManager.createMessage(null);
				String bwtopic = "FW: " + Activa.myMessageManager.currentMessage.getSubject();
				((EditText) Activa.myApp.findViewById(R.id.topic)).setText(bwtopic);
				String bwtext = "\n\nFW --------------------------------\n\nSender: ";
				bwtext += message.getSender().getFirstName() + " " + message.getSender().getLastName();
				bwtext += "\nReceiver: ";
				for (Contact user : message.getReceivers()) {
					bwtext += user.getFirstName() + " " + user.getLastName() + ",";
				}
				bwtext = bwtext.substring(0, (bwtext.length() - 1));
				bwtext += "\nTopic: " + message.getSubject() + "\n"
					+ "Date: " + ActivaUtil.dateToReadableString(message.getDate()) + ", " + ActivaUtil.timeToReadableString(message.getDate())
					+ "\n\n" + content;
				((EditText) Activa.myApp.findViewById(R.id.text)).setText(bwtext);
			}
		});
	}
	
	public void createMessage(final ArrayList<Contact> users) {
		final ArrayList<Contact> usersToSent = new ArrayList<Contact>();
		if (users != null) 
			for (Contact user : users) {
				usersToSent.add(user);
			}
		Activa.myUIManager.state = UIManager.UI_STATE_MESSAGEWRITING;
		if (Activa.myMenu != null) {
			Activa.myMenu.clear();
			Activa.myInflater.inflate(R.menu.messages, Activa.myMenu);
		}
		Activa.myApp.setContentView(R.layout.messagetosend);
		((TextView) Activa.myApp.findViewById(R.id.startText)).setText(Activa.myLanguageManager.MESSAGES_TITLE);
		((TextView) Activa.myApp.findViewById(R.id.receiverText)).setText(Activa.myLanguageManager.MESSAGES_RECEIVER);
		((TextView) Activa.myApp.findViewById(R.id.topicText)).setText(Activa.myLanguageManager.MESSAGES_TOPIC);
		ImageButton back = (ImageButton) Activa.myApp.findViewById(R.id.previous);
		ImageButton send = (ImageButton) Activa.myApp.findViewById(R.id.send);
		((EditText) Activa.myApp.findViewById(R.id.text)).addTextChangedListener(new TextWatcher() {
			int lastcharleft = 0;
			int charleft;
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				charleft = 1000 - s.length();
				if (charleft < 0) {
					charleft = 0;
					((EditText) Activa.myApp.findViewById(R.id.text)).setText(s.subSequence(0, 1000));
				}
			}
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
			@Override
			public void afterTextChanged(Editable s) {
				if (Math.abs(lastcharleft - charleft) > 10) {
					lastcharleft = charleft;
					Toast toast = Toast.makeText(Activa.myApp, String.valueOf(charleft) + " characters left ...", Toast.LENGTH_SHORT);
					toast.show();
				}
			}
		});
		final TextView receiver = ((TextView) Activa.myApp.findViewById(R.id.receiver));
		for (Contact user : usersToSent) {
			if (Activa.myMessageManager.contactList.containsKey(user.getId()))
				Activa.myMessageManager.contactList.put(user.getId(), user);
			if (user != null) receiver.append(user.getFirstName() + " " + user.getLastName());
			else receiver.append("UNIDENTIFIED");
			receiver.append(", ");
		}
		if (receiver.getText().length() > 0) receiver.setText(receiver.getText().subSequence(0, (receiver.getText().length() - 2)));
		receiver.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Enumeration<Contact> contacts = Activa.myMessageManager.contactList.elements();
				final CharSequence[] items = new CharSequence[Activa.myMessageManager.contactList.size()];
				final boolean[] selected = new boolean[Activa.myMessageManager.contactList.size()];
				final Contact[] users = new Contact[Activa.myMessageManager.contactList.size()];
				int j = 0;
				while (contacts.hasMoreElements()) {
					Contact contact = contacts.nextElement();
					items[j] = contact.getFirstName() + " " + contact.getLastName();
					users[j] = contact;
					if (usersToSent.contains(contact.getId())) {
						selected[j] = true;
					}
					else selected[j] = false;
					j++;
				}
				AlertDialog.Builder builder = new AlertDialog.Builder(Activa.myApp);
				builder.setTitle(Activa.myLanguageManager.MESSAGES_CONTACT_REQUEST);
				builder.setMultiChoiceItems(items, selected, new OnMultiChoiceClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which, boolean isChecked) {
						if (isChecked) usersToSent.add(users[which]);
						else usersToSent.remove(users[which]);
						receiver.setText("");
						for (Contact user : usersToSent) {
							receiver.append(user.getFirstName() + " " + user.getLastName() + ", ");
						}
						if (receiver.getText().length() > 0) receiver.setText(receiver.getText().subSequence(0, (receiver.getText().length() - 2)));
					}
				});
				AlertDialog alert = builder.create();
				alert.show();
			}
		});
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Activa.myUIManager.loadReceivedMessageList(0);
			}
		});
		send.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					String topic = ((EditText) Activa.myApp.findViewById(R.id.topic)).getText().toString();
					String content = ((EditText) Activa.myApp.findViewById(R.id.text)).getText().toString();
					Activa.myApp.setContentView(R.layout.sending);
					SimpleMessage message = new SimpleMessage();
					message.setDate(new Date());
					message.getReceivers().clear();
					for (Contact user : usersToSent) {
						message.getReceivers().add(user);
					}
					message.setSender(new Contact(Long.toString(Activa.myMobileManager.userForServices.getId()), Activa.myMobileManager.userForServices.getFirstName(), Activa.myMobileManager.userForServices.getLastName()));
					message.setSubject(topic);
					SendO2Message sending = new SendO2Message(message, content);
					Thread thread = new Thread(sending, "SENDO2MESSAGE");
					thread.start();
				} catch (Exception e) {
					((EditText) Activa.myApp.findViewById(R.id.receiver)).setText("");
					loadInfoPopup(Activa.myLanguageManager.MESSAGES_ERROR_RECEIVER);
				}
			}
		});
	}
	
	public void loadContactList(final boolean returntomain) {
		this.state = UI_STATE_CONTACTS;
		if (Activa.myMenu != null) {
			Activa.myMenu.clear();
			Activa.myInflater.inflate(R.menu.messages, Activa.myMenu);
		}
		Activa.myApp.setContentView(R.layout.list);
		((TextView) Activa.myApp.findViewById(R.id.startText)).setText(Activa.myLanguageManager.MESSAGES_CONTACTS);
		int realwidth = Activa.myApp.getResources().getDisplayMetrics().widthPixels;
		//TABS
		//FOLLOW
		TableLayout questlisting = (TableLayout)Activa.myApp.findViewById(R.id.listing);
		Enumeration<Contact> enumer = Activa.myMessageManager.contactList.elements();
		while (enumer.hasMoreElements()) {			
			final Contact contact = enumer.nextElement();
			if (contact.getId().equals(Long.toString(Activa.myMobileManager.userForServices.getId()))) continue;
			TableRow buttonLayout = new TableRow(Activa.myApp);
			buttonLayout.setOrientation(TableRow.HORIZONTAL);
			buttonLayout.setGravity(Gravity.CENTER_VERTICAL);
			buttonLayout.setLayoutParams(new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));			
			ImageButton button = new ImageButton(Activa.myApp);
			button.setBackgroundResource(R.drawable.iconbg);
			button.setImageResource(R.drawable.accounts);
			OnClickListener listener2 = new OnClickListener() {
				@Override
				public void onClick(View v) {
				}
			};
			TextView text = new TextView(Activa.myApp);
			text.append(contact.getFirstName() + " " + contact.getLastName());
			text.setWidth(realwidth/2);
			text.setTextSize((float) 20);
			text.setTextColor(Color.BLACK);
			text.setTypeface(Typeface.SANS_SERIF);
			ImageButton delete = new ImageButton(Activa.myApp);
			delete.setBackgroundResource(R.drawable.iconbg);
			delete.setImageResource(R.drawable.delete);
			delete.setScaleType(ScaleType.FIT_XY);
			delete.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					AlertDialog.Builder builder = new AlertDialog.Builder(Activa.myApp);
					builder.setMessage(Activa.myLanguageManager.TEXT_REMOVECONTACT)
					       .setCancelable(false)
					       .setPositiveButton(Activa.myLanguageManager.MAIN_QUIT_YES, new DialogInterface.OnClickListener() {
					           public void onClick(DialogInterface dialog, int id) {
					        	   Runnable run = new Runnable() {
										@Override
										public void run() {
											handler.sendEmptyMessage(0);
											if (Activa.myMessageManager.removeContact(contact)) {
												handler.sendEmptyMessage(1);
											}
											else handler.sendEmptyMessage(2);
										}
										private Handler handler = new Handler(){
											@Override
											public void handleMessage(Message msg) {
												ImageView animationFrame;
												AnimationDrawable animation;
												switch (msg.what) {
													case 0:
														animationFrame = (ImageView) Activa.myApp.findViewById(R.id.animation);
														animationFrame.setVisibility(View.VISIBLE);
														animationFrame.setBackgroundResource(R.drawable.loading);
														animation = (AnimationDrawable) animationFrame.getBackground();
														animation.start();
														break;
													case 1:
														animationFrame = (ImageView) Activa.myApp.findViewById(R.id.animation);
														animation = (AnimationDrawable) animationFrame.getBackground();
														animation.stop();
														animationFrame.setVisibility(View.GONE);
														Activa.myUIManager.loadContactList(true);
														break;
													case 2:
														animationFrame = (ImageView) Activa.myApp.findViewById(R.id.animation);
														animation = (AnimationDrawable) animationFrame.getBackground();
														animation.stop();
														animationFrame.setVisibility(View.GONE);
														Activa.myUIManager.loadInfoPopup("Remove failed");
														break;
												}
											}
										};
									};
									Thread thread = new Thread(run);
									thread.start();
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
			});
			buttonLayout.addView(button);
			buttonLayout.addView(text);
			buttonLayout.addView(delete, 50, 50);
			button.setOnClickListener(listener2);
			text.setOnClickListener(listener2);
			questlisting.addView(buttonLayout);
		}
		ImageButton back = (ImageButton) Activa.myApp.findViewById(R.id.previous);
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (returntomain) Activa.myUIManager.loadGeneratedSubenvironment(Activa.myUIManager.lastSubenv, true);
				else showOptions();
			}
		});
		ImageButton change = (ImageButton) Activa.myApp.findViewById(R.id.help);
		change.setImageResource(R.drawable.searchcontact);
		change.setVisibility(View.VISIBLE);
		change.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				searchContacts();			
			}
		});
		ImageView addquest = (ImageView) Activa.myApp.findViewById(R.id.animation);
		addquest.setVisibility(View.GONE);
		ImageView toentrycontacts = (ImageView) Activa.myApp.findViewById(R.id.animation);
		toentrycontacts.setVisibility(View.VISIBLE);
		toentrycontacts.setImageResource(R.drawable.msgcontactsentering);
		toentrycontacts.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Activa.myUIManager.loadEntryContactList(true);
			}
		});
	}
	//TODO
	public void loadEntryContactList (boolean returntocontacts) {
		final ArrayList<ContactContactRequest> ContactsToAccept = new ArrayList<ContactContactRequest>();
		final ArrayList<ContactContactRequest> ContactsToReject = new ArrayList<ContactContactRequest>();
		final ContactContactRequest[] items = new ContactContactRequest[Activa.myMessageManager.entryContactList.size()];
		Iterator<ContactContactRequest> cons = Activa.myMessageManager.entryContactList.iterator();
		int i = 0;
		while (cons.hasNext()) {
			items[i] = cons.next();
			i++;
		}
		ListView list = new ListView(Activa.myApp);
		ListAdapter adapter = new ArrayAdapter<ContactContactRequest>(Activa.myApp,android.R.layout.select_dialog_item,
			    android.R.id.text1,items){
			public View getView(final int position, View convertView, ViewGroup parent) {
				int realwidth = Activa.myApp.getResources().getDisplayMetrics().widthPixels;
				LinearLayout layout = new LinearLayout(Activa.myApp);
				layout.setLayoutParams(new ListView.LayoutParams(ListView.LayoutParams.FILL_PARENT, ListView.LayoutParams.WRAP_CONTENT));
			    layout.setOrientation(LinearLayout.HORIZONTAL);
				TextView tv = new TextView(Activa.myApp);
				tv.setText(items[position].getRequester().getFirstName() + " " + items[position].getRequester().getLastName());
			    //Add margin between image and text (support various screen densities)
			    int dp5 = (int) (5 * Activa.myApp.getResources().getDisplayMetrics().density + 0.5f);
			    tv.setCompoundDrawablePadding(dp5);
			    tv.setTextColor(Color.BLACK);
			    tv.setLayoutParams(new LinearLayout.LayoutParams(realwidth*1/2, LinearLayout.LayoutParams.WRAP_CONTENT));
			    final CheckBox checktrue = new CheckBox(Activa.myApp);
			    checktrue.setLayoutParams(new LinearLayout.LayoutParams(60, 60));
			    final CheckBox checkfalse = new CheckBox(Activa.myApp);
			    checkfalse.setLayoutParams(new LinearLayout.LayoutParams(60, 60));
			    checktrue.setButtonDrawable(R.drawable.checkboxtruewid);
			    checktrue.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
						if (isChecked) ContactsToAccept.add(items[position]);
						else if (ContactsToAccept.contains(items[position])) ContactsToAccept.remove(items[position]);
					}
				});
			    checkfalse.setButtonDrawable(R.drawable.checkboxfalsewid);
			    checkfalse.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//						if (checktrue.isChecked()) {
//							checktrue.setChecked(false);
//						}
						if (isChecked) ContactsToReject.add(items[position]);
						else if (ContactsToReject.contains(items[position])) ContactsToReject.remove(items[position]);
					}
				});
			    LinearLayout separator = new LinearLayout(Activa.myApp);
			    separator.setLayoutParams(new LinearLayout.LayoutParams(dp5, LinearLayout.LayoutParams.WRAP_CONTENT));
			    layout.addView(tv);
			    layout.addView(checktrue);
			    layout.addView(separator);
			    layout.addView(checkfalse);
			    return layout;
			}
		};
		list.setAdapter(adapter);
		new AlertDialog.Builder(Activa.myApp)
	    .setTitle(Activa.myLanguageManager.MESSAGES_ENTRYCONTACTS)
	    .setView(list)
	    .setInverseBackgroundForced(true)
	    .setPositiveButton(Activa.myApp.getResources().getString(R.string.oklink), new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Iterator<ContactContactRequest> iterator = ContactsToAccept.iterator();
				while (iterator.hasNext()) {
					ContactContactRequest request = iterator.next();
					Activa.myMessageManager.AcceptContact(request);
				}
				iterator = ContactsToReject.iterator();
				while (iterator.hasNext()) {
					Activa.myMessageManager.RejectContact(iterator.next());
				}
			}
		})
	    .setNegativeButton(Activa.myApp.getResources().getString(R.string.cancellink), new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		})
	    .show();
	}
	
	/*public void loadEntryContactList(final boolean returntomain) {
		this.state = UI_STATE_MESSAGE;
		if (Activa.myMenu != null) {
			Activa.myMenu.clear();
			Activa.myInflater.inflate(R.menu.messages, Activa.myMenu);
		}
		Activa.myApp.setContentView(R.layout.tablist);
		((TextView) Activa.myApp.findViewById(R.id.startText)).setText(Activa.myLanguageManager.MESSAGES_ENTRYCONTACTS);
		int realwidth = Activa.myApp.getResources().getDisplayMetrics().widthPixels;
		//TABS
		LinearLayout tabs = (LinearLayout) Activa.myApp.findViewById(R.id.tabs);
		LinearLayout tab = new LinearLayout(Activa.myApp);
		tab.setOrientation(LinearLayout.VERTICAL);
		tab.setLayoutParams(new LinearLayout.LayoutParams(realwidth/2, LinearLayout.LayoutParams.FILL_PARENT));
		tab.setBackgroundResource(R.drawable.tab_not_selected);
		ImageButton but = new ImageButton(Activa.myApp);
		android.widget.LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, 40);
		params.setMargins(0, 5, 0, 4);
		but.setLayoutParams(params);
		but.setBackgroundResource(R.drawable.iconbg);
		but.setImageResource(R.drawable.msgcontacts);
		OnClickListener listener = new OnClickListener() {
			@Override
			public void onClick(View v) {
				loadContactList(true);
			}
		};
		tab.setOnClickListener(listener);
		but.setOnClickListener(listener);
		tab.addView(but);
		tabs.addView(tab);
		tab = new LinearLayout(Activa.myApp);
		tab.setOrientation(LinearLayout.VERTICAL);
		tab.setLayoutParams(new LinearLayout.LayoutParams(realwidth/2, LinearLayout.LayoutParams.FILL_PARENT));
		tab.setBackgroundResource(R.drawable.tab_selected);
		but = new ImageButton(Activa.myApp);
		params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, 40);
		params.setMargins(0, 5, 0, 4);
		but.setLayoutParams(params);
		but.setBackgroundResource(R.drawable.iconbg);
		but.setImageResource(R.drawable.msgcontactsentering);
		listener = new OnClickListener() {
			@Override
			public void onClick(View v) {
				loadEntryContactList(true);
			}
		};
		tab.setOnClickListener(listener);
		but.setOnClickListener(listener);
		tab.addView(but);
		tabs.addView(tab);
		//FOLLOW
		TableLayout questlisting = (TableLayout)Activa.myApp.findViewById(R.id.listing);
		Iterator<ContactContactRequest> enumer = Activa.myMessageManager.entryContactList.iterator();
		while (enumer.hasNext()) {			
			final ContactContactRequest contact = enumer.next();
			TableRow buttonLayout = new TableRow(Activa.myApp);
			buttonLayout.setOrientation(TableRow.HORIZONTAL);
			buttonLayout.setGravity(Gravity.CENTER_VERTICAL);
			buttonLayout.setLayoutParams(new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));			
			ImageButton button = new ImageButton(Activa.myApp);
			button.setBackgroundResource(R.drawable.iconbg);
			button.setImageResource(R.drawable.accounts);
			OnClickListener listener2 = new OnClickListener() {
				@Override
				public void onClick(View v) {
				}
			};
			TextView text = new TextView(Activa.myApp);
			text.append(contact.getRequester().getFirstName() + " " + contact.getRequester().getLastName());
			text.setWidth(realwidth*2/5);
			text.setTextSize((float) 20);
			text.setTextColor(Color.BLACK);
			text.setTypeface(Typeface.SANS_SERIF);
			ImageButton accept = new ImageButton(Activa.myApp);
			accept.setBackgroundResource(R.drawable.iconbg);
			accept.setImageResource(R.drawable.ok);
			accept.setScaleType(ScaleType.FIT_XY);
			accept.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Runnable run = new Runnable() {
						@Override
						public void run() {
							handler.sendEmptyMessage(0);
							if (Activa.myMessageManager.AcceptContact(contact)) {
								handler.sendEmptyMessage(1);
							}
							else handler.sendEmptyMessage(2);
						}
						private Handler handler = new Handler(){
							@Override
							public void handleMessage(Message msg) {
								ImageView animationFrame;
								AnimationDrawable animation;
								switch (msg.what) {
									case 0:
										animationFrame = (ImageView) Activa.myApp.findViewById(R.id.animation);
										animationFrame.setVisibility(View.VISIBLE);
										animationFrame.setBackgroundResource(R.drawable.loading);
										animation = (AnimationDrawable) animationFrame.getBackground();
										animation.start();
										break;
									case 1:
										animationFrame = (ImageView) Activa.myApp.findViewById(R.id.animation);
										animation = (AnimationDrawable) animationFrame.getBackground();
										animation.stop();
										animationFrame.setVisibility(View.GONE);
										Activa.myUIManager.loadContactList(true);
										break;
									case 2:
										animationFrame = (ImageView) Activa.myApp.findViewById(R.id.animation);
										animation = (AnimationDrawable) animationFrame.getBackground();
										animation.stop();
										animationFrame.setVisibility(View.GONE);
										Activa.myUIManager.loadInfoPopup("Remove failed");
										break;
								}
							}
						};
					};
					Thread thread = new Thread(run);
					thread.start();
				}
			});
			ImageButton delete = new ImageButton(Activa.myApp);
			delete.setBackgroundResource(R.drawable.iconbg);
			delete.setImageResource(R.drawable.delete);
			delete.setScaleType(ScaleType.FIT_XY);
			delete.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					AlertDialog.Builder builder = new AlertDialog.Builder(Activa.myApp);
					builder.setMessage(Activa.myLanguageManager.TEXT_REJECTCONTACT)
					       .setCancelable(false)
					       .setPositiveButton(Activa.myLanguageManager.MAIN_QUIT_YES, new DialogInterface.OnClickListener() {
					           public void onClick(DialogInterface dialog, int id) {
					        	   Runnable run = new Runnable() {
										@Override
										public void run() {
											handler.sendEmptyMessage(0);
											if (Activa.myMessageManager.RejectContact(contact)) {
												handler.sendEmptyMessage(1);
											}
											else handler.sendEmptyMessage(2);
										}
										private Handler handler = new Handler(){
											@Override
											public void handleMessage(Message msg) {
												ImageView animationFrame;
												AnimationDrawable animation;
												switch (msg.what) {
													case 0:
														animationFrame = (ImageView) Activa.myApp.findViewById(R.id.animation);
														animationFrame.setVisibility(View.VISIBLE);
														animationFrame.setBackgroundResource(R.drawable.loading);
														animation = (AnimationDrawable) animationFrame.getBackground();
														animation.start();
														break;
													case 1:
														animationFrame = (ImageView) Activa.myApp.findViewById(R.id.animation);
														animation = (AnimationDrawable) animationFrame.getBackground();
														animation.stop();
														animationFrame.setVisibility(View.GONE);
														Activa.myUIManager.loadContactList(true);
														break;
													case 2:
														animationFrame = (ImageView) Activa.myApp.findViewById(R.id.animation);
														animation = (AnimationDrawable) animationFrame.getBackground();
														animation.stop();
														animationFrame.setVisibility(View.GONE);
														Activa.myUIManager.loadInfoPopup("Remove failed");
														break;
												}
											}
										};
									};
									Thread thread = new Thread(run);
									thread.start();
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
			});
			buttonLayout.addView(button);
			buttonLayout.addView(text);
			buttonLayout.addView(accept, 50, 50);
			buttonLayout.addView(delete, 50, 50);
			button.setOnClickListener(listener2);
			text.setOnClickListener(listener2);
			questlisting.addView(buttonLayout);
		}
		ImageButton back = (ImageButton) Activa.myApp.findViewById(R.id.previous);
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (returntomain) Activa.myUIManager.loadGeneratedSubenvironment(Activa.myUIManager.lastSubenv, true);
				else showOptions();
			}
		});
		ImageButton change = (ImageButton) Activa.myApp.findViewById(R.id.help);
		change.setImageResource(R.drawable.searchcontact);
		change.setVisibility(View.VISIBLE);
		change.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				searchContacts();			
			}
		});
		ImageView addquest = (ImageView) Activa.myApp.findViewById(R.id.animation);
		addquest.setVisibility(View.GONE);
		ImageView tosms = (ImageView) Activa.myApp.findViewById(R.id.animation);
		tosms.setVisibility(View.VISIBLE);
		try {
	        PackageManager manager = Activa.myApp.getPackageManager();
	        tosms.setBackgroundDrawable(manager.getActivityIcon(new ComponentName(Activa.myExteriorManager.externalApplications.get(ExteriorManager.APP_SMS).packageName, Activa.myExteriorManager.externalApplications.get(ExteriorManager.APP_SMS).activityName)));
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		tosms.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Activa.myExteriorManager.externalApplications.get(ExteriorManager.APP_SMS).startApplication();
			}
		});
	}*/
	
	public void searchContacts() {
		LayoutInflater inflater = (LayoutInflater) Activa.myApp.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		final View layout = inflater.inflate(R.layout.toastsearch,
		                               (ViewGroup) Activa.myApp.findViewById(R.id.toastsearchroot));
		TextView text = (TextView) layout.findViewById(R.id.searchexpl);
		text.setText(Activa.myLanguageManager.NOTIFICATION_SEARCH_EXPL);
		ImageButton image = (ImageButton) layout.findViewById(R.id.searchok);
		Builder builder = new AlertDialog.Builder(Activa.myApp);
		builder.setView(layout);
		final AlertDialog alertDialog = builder.create();
		alertDialog.show();
		image.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				final String query = ((EditText)layout.findViewById(R.id.searchtext)).getText().toString();
				alertDialog.cancel();
				final ProgressDialog dialog = ProgressDialog.show(Activa.myApp, Activa.myLanguageManager.NOTIFICATION_SEARCH_TITLE, 
						Activa.myLanguageManager.NOTIFICATION_SEARCH_SEARCHING, true);
				dialog.show();
				Runnable run =  new Runnable() {
					Array<Contact> users;
					@Override
					public void run() {
						users = Activa.myMessageManager.SearchContacts(query);
						dialog.cancel();
						if (users == null) {
							handler.sendEmptyMessage(0);
						} else if (users.size() == 0) {
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
									Toast toast = Toast.makeText(Activa.myApp, Activa.myLanguageManager.CONNECTION_FAILED, Toast.LENGTH_SHORT);
									toast.show();
									break;
								case 1:
									Toast toast2 = Toast.makeText(Activa.myApp, Activa.myLanguageManager.NOTIFICATION_SEARCH_NOTFOUND, Toast.LENGTH_SHORT);
									toast2.show();
									break;
								case 2:
									final CharSequence[] items = new CharSequence[users.size()];
									final Array<Contact>usersToAdd = new Array<Contact>();
									for (int i = 0; i < users.size(); i++) {
										items[i] = users.get(i).getFirstName() + " " + users.get(i).getLastName();
									}
									AlertDialog.Builder builder = new AlertDialog.Builder(Activa.myApp);
									builder.setTitle(Activa.myLanguageManager.NOTIFICATION_SEARCH_ADD);
									builder.setMultiChoiceItems(items, null, new OnMultiChoiceClickListener() {
										@Override
										public void onClick(DialogInterface dialog, int which, boolean isChecked) {
											if (isChecked) usersToAdd.add(users.get(which));
											else usersToAdd.remove(users.get(which));
										}
									});
									builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
								           public void onClick(DialogInterface dialog, int id) {
								        	   for (int i = 0; i < usersToAdd.size(); i++) {
								        		   Activa.myMessageManager.AddContact(usersToAdd.get(i));
								        		   loadContactList(true);
								        	   }
								               dialog.cancel();
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

	public void removeContacts() {
		int i = 0;
		final CharSequence[] items = new CharSequence[Activa.myMessageManager.contactList.size()];
		final Contact[] contactitems = new Contact[Activa.myMessageManager.contactList.size()];
		final Array<Contact>usersToRem = new Array<Contact>();
		Enumeration<Contact> elements = Activa.myMessageManager.contactList.elements();
		while (elements.hasMoreElements()) {
			Contact user = elements.nextElement();
			items[i] = user.getFirstName() + " " + user.getLastName();
			contactitems[i] = user;
			i++;
		}
		AlertDialog.Builder builder = new AlertDialog.Builder(Activa.myApp);
		builder.setTitle(Activa.myLanguageManager.NOTIFICATION_PATIENT_ADD);
		builder.setMultiChoiceItems(items, null, new OnMultiChoiceClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which, boolean isChecked) {
				if (isChecked) usersToRem.add(contactitems[which]);
				else usersToRem.remove(contactitems[which]);
			}
		});
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
	           public void onClick(DialogInterface dialog, int id) {
	        	   for (int i = 0; i < usersToRem.size(); i++) {
	        		   Activa.myMessageManager.removeContact(usersToRem.get(i));
	        	   }
	               dialog.cancel();
	           }
	    });
		AlertDialog alert = builder.create();
		alert.show();
	}

//	public void refreshCreatingMessage(String receiverText, String topicText, String textText) {
//		createMessage();
//		((EditText) Activa.myApp.findViewById(R.id.receiver)).setText(receiverText);
//		((EditText) Activa.myApp.findViewById(R.id.topic)).setText(topicText);
//		((EditText) Activa.myApp.findViewById(R.id.text)).setText(textText);
//	}

	public void loadInfoPopup(String string) {
		final RelativeLayout popupView = (RelativeLayout) Activa.myApp.findViewById(R.id.popupView);
		popupView.setVisibility(View.VISIBLE);
		TextView popupText = (TextView) Activa.myApp.findViewById(R.id.popupText);
		popupText.setText(string);
		CountDownTimer timer = new CountDownTimer(5000, 1000) {
			@Override
			public void onTick(long millisUntilFinished) {
			}
			@Override
			public void onFinish() {
				popupView.setVisibility(View.INVISIBLE);			
			}
		};
		timer.start();
	}

	public void loadInfoPopupLong(String string) {
		final RelativeLayout popupView = (RelativeLayout) Activa.myApp.findViewById(R.id.popupView);
		popupView.setVisibility(View.VISIBLE);
		TextView popupText = (TextView) Activa.myApp.findViewById(R.id.popupText);
		popupText.setText(string);
		CountDownTimer timer = new CountDownTimer(10000, 1000) {
			@Override
			public void onTick(long millisUntilFinished) {
			}
			@Override
			public void onFinish() {
				popupView.setVisibility(View.INVISIBLE);			
			}
		};
		timer.start();
	}
	
	public void loadInfoPopupWithoutExiting(String string) {
		final RelativeLayout popupView = (RelativeLayout) Activa.myApp.findViewById(R.id.popupView);
		popupView.setVisibility(View.VISIBLE);
		TextView popupText = (TextView) Activa.myApp.findViewById(R.id.popupText);
		popupText.setText(string);
	}
	
	public void removeInfoPopup() {
		final RelativeLayout popupView = (RelativeLayout) Activa.myApp.findViewById(R.id.popupView);
		popupView.setVisibility(View.GONE);
	}
	
	public void loadNewsList(boolean refresh) {
		this.state = UI_STATE_NEWS;
		Activa.myApp.setContentView(R.layout.list);
		((TextView) Activa.myApp.findViewById(R.id.startText)).setText(Activa.myLanguageManager.NEWS_TITLE);
		TableLayout rsslisting = (TableLayout)Activa.myApp.findViewById(R.id.listing);
		if (!refresh) {
			Enumeration<Feed> enumer = Activa.myNewsManager.feedslist.elements();
			while (enumer.hasMoreElements()) {			
				Feed feed = enumer.nextElement();
				TableRow buttonLayout = new TableRow(Activa.myApp);
				buttonLayout.setId(feed.id);
				buttonLayout.setOrientation(TableRow.HORIZONTAL);
				buttonLayout.setGravity(Gravity.CENTER_VERTICAL);
				buttonLayout.setLayoutParams(new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));			
				ImageButton button = new ImageButton(Activa.myApp);
				button.setBackgroundResource(R.drawable.iconbg);
				button.setImageResource(R.drawable.feed);
				button.setId(feed.id);
				TextView text = new TextView(Activa.myApp);
				text.append(feed.title);
				text.setMaxWidth(240);
				text.setTextSize((float) 20);
				text.setTextColor(Color.BLACK);
				text.setTypeface(Typeface.SANS_SERIF);
				text.setId(feed.id);
				buttonLayout.addView(button);
				buttonLayout.addView(text);
				OnClickListener listener = new OnClickListener() {
					@Override
					public void onClick(View v) {
						LoadFeedNewList(v.getId());
					}
				};
				button.setOnClickListener(listener);
				text.setOnClickListener(listener);
				buttonLayout.setOnClickListener(listener);
				rsslisting.addView(buttonLayout);
			}
		}
		else {
			GetNews sending = new GetNews();
			Thread thread = new Thread(sending, "GETNEWS");
			thread.start();
		}
		ImageButton back = (ImageButton) Activa.myApp.findViewById(R.id.previous);
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Activa.myUIManager.loadGeneratedSubenvironment(Activa.myUIManager.lastSubenv, true);
			}
		});
		ImageView search = (ImageView) Activa.myApp.findViewById(R.id.animation);
		search.setImageResource(R.drawable.searchcontact);
		search.setVisibility(View.VISIBLE);
		search.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				LayoutInflater inflater = (LayoutInflater) Activa.myApp.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				final View layout = inflater.inflate(R.layout.toastsearch,
				                               (ViewGroup) Activa.myApp.findViewById(R.id.toastsearchroot));
				TextView text = (TextView) layout.findViewById(R.id.searchexpl);
				text.setText(Activa.myLanguageManager.NOTIFICATION_FEEDSEARCH_EXPL);
				ImageButton image = (ImageButton) layout.findViewById(R.id.searchok);
				Builder builder = new AlertDialog.Builder(Activa.myApp);
				builder.setView(layout);
				final AlertDialog alertDialog = builder.create();
				alertDialog.show();
				image.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						final String query = ((EditText)layout.findViewById(R.id.searchtext)).getText().toString();
						alertDialog.cancel();
						final ProgressDialog dialog = ProgressDialog.show(Activa.myApp, Activa.myLanguageManager.NOTIFICATION_FEEDSEARCH_TITLE, 
								Activa.myLanguageManager.NOTIFICATION_FEEDSEARCH_SEARCHING, true);
						dialog.show();
						Runnable run =  new Runnable() {
							Array<com.o2hlink.activ8.client.entity.Feed> feeds;
							@Override
							public void run() {
								feeds = Activa.myNewsManager.SearchFeeds(query);
								dialog.cancel();
								if (feeds == null) {
									handler.sendEmptyMessage(0);
								} else if (feeds.size() == 0) {
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
											Toast toast = Toast.makeText(Activa.myApp, Activa.myLanguageManager.CONNECTION_FAILED, Toast.LENGTH_SHORT);
											toast.show();
											break;
										case 1:
											Toast toast2 = Toast.makeText(Activa.myApp, Activa.myLanguageManager.NOTIFICATION_FEEDSEARCH_NOTFOUND, Toast.LENGTH_SHORT);
											toast2.show();
											break;
										case 2:
											final CharSequence[] items = new CharSequence[feeds.size()];
											final Array<com.o2hlink.activ8.client.entity.Feed> feedsToAdd = new Array<com.o2hlink.activ8.client.entity.Feed>();
											for (int i = 0; i < feeds.size(); i++) {
												items[i] = Html.fromHtml(feeds.get(i).getName());
											}
											AlertDialog.Builder builder = new AlertDialog.Builder(Activa.myApp);
											builder.setTitle(Activa.myLanguageManager.NOTIFICATION_FEEDSEARCH_ADD);
											builder.setMultiChoiceItems(items, null, new OnMultiChoiceClickListener() {
												@Override
												public void onClick(DialogInterface dialog, int which, boolean isChecked) {
													if (isChecked) feedsToAdd.add(feeds.get(which));
													else feedsToAdd.remove(feeds.get(which));
												}
											});
											builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
										           public void onClick(DialogInterface dialog, int id) {
										        	   for (int i = 0; i < feedsToAdd.size(); i++) {
										        		   Activa.myNewsManager.AddFeed(feedsToAdd.get(i));
										        	   }
										               dialog.cancel();
										               Activa.myUIManager.loadNewsList(true);
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
		});
		ImageButton delete = (ImageButton) Activa.myApp.findViewById(R.id.help);
		delete.setImageResource(R.drawable.delete);
		delete.setVisibility(View.VISIBLE);
		delete.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				final CharSequence[] items = new CharSequence[Activa.myNewsManager.feedslist.size()];
				final Feed[] feedlist = new Feed[Activa.myNewsManager.feedslist.size()];
				final ArrayList<Feed> feedsToRemove = new ArrayList<Feed>();
				Enumeration<Feed> feedsenum = Activa.myNewsManager.feedslist.elements();
				int i = 0;
				while (feedsenum.hasMoreElements()) {
					Feed feedNow = feedsenum.nextElement();
					items[i] = Html.fromHtml(feedNow.title);
					feedlist[i] = feedNow;
					i++;
				}
				AlertDialog.Builder builder = new AlertDialog.Builder(Activa.myApp);
				builder.setTitle(Activa.myLanguageManager.NOTIFICATION_FEEDSEARCH_REMOVE);
				builder.setMultiChoiceItems(items, null, new OnMultiChoiceClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which, boolean isChecked) {
						if (isChecked) feedsToRemove.add(feedlist[which]);
						else feedsToRemove.remove(feedlist[which]);
					}
				});
				builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {
			        	   for (int i = 0; i < feedsToRemove.size(); i++) {
			        		   Activa.myNewsManager.removeFeed(feedsToRemove.get(i));
			        	   }
			               dialog.cancel();
			               Activa.myUIManager.loadNewsList(true);
			           }
			    });
				AlertDialog alert = builder.create();
				alert.show();
			}
		});
	}
	
	public void LoadFeedNewList(final int feedId) {
		this.state = UI_STATE_NEWS;
		Activa.myApp.setContentView(R.layout.list);
		((TextView) Activa.myApp.findViewById(R.id.startText)).setText(Activa.myNewsManager.feedslist.get(feedId).title);
		TableLayout rsslisting = (TableLayout)Activa.myApp.findViewById(R.id.listing);
		Enumeration<New> enumer = Activa.myNewsManager.feedslist.get(feedId).newslist.elements();
		while (enumer.hasMoreElements()) {
			New notice = enumer.nextElement();
			TableRow buttonLayout = new TableRow(Activa.myApp);
			buttonLayout.setId(notice.id);
			buttonLayout.setOrientation(TableRow.HORIZONTAL);
			buttonLayout.setGravity(Gravity.CENTER_VERTICAL);
			buttonLayout.setLayoutParams(new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));			
			ImageButton button = new ImageButton(Activa.myApp);
			button.setBackgroundResource(R.drawable.iconbg);
			button.setImageResource(R.drawable.notice);
			button.setId(notice.id);
			TextView text = new TextView(Activa.myApp);
			if (notice.title.length() < 50 ) text.append(notice.title);
			else text.append(notice.title.subSequence(0, 47) + "...");
			text.setMaxWidth(240);
			text.setTextSize((float) 20);
			text.setTextColor(Color.BLACK);
			text.setTypeface(Typeface.SANS_SERIF);
			text.setId(notice.id);
			buttonLayout.addView(button);
			buttonLayout.addView(text);
			OnClickListener listener = new OnClickListener() {
				@Override
				public void onClick(final View v) {
					Activa.myUIManager.loadInfoPopupWithoutExiting(Activa.myLanguageManager.NEWS_LOADING);
					ImageView animationFrame = (ImageView) Activa.myApp.findViewById(R.id.popupImage);
					animationFrame.setVisibility(View.VISIBLE);
					animationFrame.setBackgroundResource(R.drawable.loading);
					final AnimationDrawable animation = (AnimationDrawable) animationFrame.getBackground();
					animation.start();
					((FrameLayout)Activa.myApp.findViewById(R.id.layout)).invalidate();
					Runnable run = new Runnable() {
						@Override
						public void run() {
							handler.sendEmptyMessage(0);
						}
						private Handler handler = new Handler() {
							@Override
							public void handleMessage(Message msg) {
								switch (msg.what) {
									case 0:
										loadNew(v.getId(), feedId, true);
										break;
								}
							}

						};
					};
					Thread thread = new Thread(run);
					thread.start();
				}
			};
			button.setOnClickListener(listener);
			text.setOnClickListener(listener);
			buttonLayout.setOnClickListener(listener);
			rsslisting.addView(buttonLayout);
		}
		ImageButton back = (ImageButton) Activa.myApp.findViewById(R.id.previous);
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				loadNewsList(false);
			}
		});
	}
	
	public void loadNew(int newId, final int feedId, boolean forward) {
		Feed feed = Activa.myNewsManager.feedslist.get(feedId);
		if (newId < 0) newId = feed.newslist.size() - 1;
		if (newId >= feed.newslist.size()) newId = 0;
		New notice = feed.newslist.get(newId);
		while (notice == null) {
			if (forward) newId += 1;
			else newId -= 1;
			notice = feed.newslist.get(newId);
			if (newId < 0) newId = feed.newslist.size() - 1;
			if (newId >= feed.newslist.size()) newId = 0;
		}
		final int currentNewId = newId;
		Spanned span =  Html.fromHtml(notice.content, new ImageGetter() {                 
		    @Override
		    public Drawable getDrawable(String source) {
		        Drawable bmp;
				try {
					bmp = Drawable.createFromStream((InputStream)(new URL(source).getContent()), "src");
					int width = bmp.getIntrinsicWidth();
					if (width == -1) width = 100;
					int height = bmp.getIntrinsicHeight();
					if (height == -1) height = 100;
					bmp.setBounds(0, 0, width, height);
			        return bmp;
				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
				return null;
		    }
		}, null);
		Activa.myApp.setContentView(R.layout.newviewer);
		((TextView) Activa.myApp.findViewById(R.id.startText)).setText(feed.title);
		TextView count = (TextView) Activa.myApp.findViewById(R.id.newcount);
		TextView title = (TextView) Activa.myApp.findViewById(R.id.newtitle);
		TextView snippet = (TextView) Activa.myApp.findViewById(R.id.newsnippet);
//		ImageView photo = (ImageView) Activa.myApp.findViewById(R.id.newphoto);
		TextView text = (TextView) Activa.myApp.findViewById(R.id.newtext);
		TextView link = (TextView) Activa.myApp.findViewById(R.id.newlink);
		ImageButton prevnew = (ImageButton) Activa.myApp.findViewById(R.id.previousnew);
		ImageButton nextnew = (ImageButton) Activa.myApp.findViewById(R.id.nextnew);
		ImageButton back = (ImageButton) Activa.myApp.findViewById(R.id.previous);
		count.setText((newId + 1) + "/" + feed.newslist.size());
		title.setText(notice.title);
		snippet.setText(notice.date + "\n\n" + notice.snippet);
		text.setText(span);
		link.setText(notice.link);
		Linkify.addLinks(link, Linkify.ALL);
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				LoadFeedNewList(feedId);
			}
		});
		prevnew.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				((FrameLayout)Activa.myApp.findViewById(R.id.layout)).invalidate();
				Runnable run = new Runnable() {
					@Override
					public void run() {
						handler.sendEmptyMessage(1);
						handler.sendEmptyMessage(0);
					}
					private Handler handler = new Handler() {
						@Override
						public void handleMessage(Message msg) {
							switch (msg.what) {
								case 0:
									loadNew(currentNewId - 1, feedId, true);
									break;
								case 1:
									ImageView animationFrame = (ImageView) Activa.myApp.findViewById(R.id.animation);
									animationFrame.setVisibility(View.VISIBLE);
									animationFrame.setBackgroundResource(R.drawable.loading);
									final AnimationDrawable animation = (AnimationDrawable) animationFrame.getBackground();
									animation.start();
							}
						}

					};
				};
				Thread thread = new Thread(run);
				thread.start();
			}
		});
		nextnew.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				((FrameLayout)Activa.myApp.findViewById(R.id.layout)).invalidate();
				Runnable run = new Runnable() {
					@Override
					public void run() {
						handler.sendEmptyMessage(1);
						handler.sendEmptyMessage(0);
					}
					private Handler handler = new Handler() {
						@Override
						public void handleMessage(Message msg) {
							switch (msg.what) {
								case 0:
									loadNew(currentNewId + 1, feedId, true);
									break;
								case 1:
									ImageView animationFrame = (ImageView) Activa.myApp.findViewById(R.id.animation);
									animationFrame.setVisibility(View.VISIBLE);
									animationFrame.setBackgroundResource(R.drawable.loading);
									final AnimationDrawable animation = (AnimationDrawable) animationFrame.getBackground();
									animation.start();
							}
						}

					};
				};
				Thread thread = new Thread(run);
				thread.start();
			}
		});
	}
	
	public void loadNotes() {
		this.state = UI_STATE_NOTES;
		Activa.myApp.setContentView(R.layout.noteslist);
		((TextView) Activa.myApp.findViewById(R.id.startText)).setText(Activa.myLanguageManager.NOTES_TITLE);
		final TextView notecounter = (TextView) Activa.myApp.findViewById(R.id.notecounter);
		notecounter.setText(1000 + " characters left ...");
		Hashtable<Date, Note> notesOrdered = new Hashtable<Date,Note>();
		Vector<Date> datesOrdered = new Vector<Date>();
		Enumeration<Note> enumeration = Activa.myNotesManager.notelist.elements();
		while (enumeration.hasMoreElements()) {
			Note temp = enumeration.nextElement();
			if (datesOrdered.contains(temp.date)) temp.date.setTime(temp.date.getTime() + 1);
			datesOrdered.add(temp.date);
			notesOrdered.put(temp.date, temp);
		}
		Collections.sort(datesOrdered);
		Collections.reverse(datesOrdered);
		final EditText newnote = (EditText)Activa.myApp.findViewById(R.id.newnotetext);
		newnote.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				int charleft = 1000 - s.length();
				if (charleft < 0) {
					charleft = 0;
					newnote.setText(s.subSequence(0, 1000));
				}
				notecounter.setText(String.valueOf(charleft) + " characters left ...");
			}
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
			@Override
			public void afterTextChanged(Editable s) {}
		});
		LinearLayout notelist = (LinearLayout)Activa.myApp.findViewById(R.id.control);
		for(int i = 0; i < datesOrdered.size(); i++) {
			Note note = notesOrdered.get(datesOrdered.get(i));
			TextView time = new TextView(Activa.myApp);
			time.setText(ActivaUtil.dateToReadableString(note.date) + ", " + ActivaUtil.timeToReadableString(note.date));
			if (note.state == 1) time.append(" [PENDING]");
			else if (note.state == 2) time.append(" [RETRANSMITTED]");
			time.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
			time.setPadding(10, 5, 10, 5);
			time.setTextColor(Color.BLACK);
			time.setTypeface(Typeface.DEFAULT_BOLD);
			TextView text = new TextView(Activa.myApp);
			text.setText(note.text);
			text.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
			text.setPadding(10, 0, 10, 5);
			text.setTextColor(Color.BLACK);
			text.setTypeface(Typeface.SANS_SERIF);					
			View separator = new View(Activa.myApp);
			LayoutParams separatorLayout = new LayoutParams(android.view.ViewGroup.LayoutParams.FILL_PARENT, 1);
			separator.setLayoutParams(separatorLayout);
			separator.setBackgroundColor(Color.BLACK);
			notelist.addView(time);
			notelist.addView(text);
			notelist.addView(separator);
		}
		ImageButton addnote = (ImageButton) Activa.myApp.findViewById(R.id.addnote);
		addnote.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				SendNote sending = new SendNote(newnote.getText().toString());
				Thread thread = new Thread(sending, "SENDNOTE");
				thread.start();
			}
		});
		ImageButton back = (ImageButton) Activa.myApp.findViewById(R.id.previous);
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Activa.myUIManager.loadGeneratedSubenvironment(Activa.myUIManager.lastSubenv, true);
			}
		});
	}
	
	public void loadExternalSocialNetworks() {
		final CharSequence[] items = {Activa.myExteriorManager.externalApplications.get(ExteriorManager.APP_CONTACTS).name, 
				Activa.myExteriorManager.externalApplications.get(ExteriorManager.APP_FACEBOOK).name, 
				Activa.myExteriorManager.externalApplications.get(ExteriorManager.APP_TWITTER).name, 
				Activa.myExteriorManager.externalApplications.get(ExteriorManager.APP_LINKEDIN).name};
		AlertDialog.Builder builder = new AlertDialog.Builder(Activa.myApp);
		builder.setTitle(Activa.myLanguageManager.MAIN_SOCIAL);
		builder.setItems(items, new DialogInterface.OnClickListener() {
		    public void onClick(DialogInterface dialog, int item) {
		        switch (item) {
					case 0:
						Activa.myExteriorManager.runApplication(ExteriorManager.APP_CONTACTS);
						break;
					case 1:
						Activa.myExteriorManager.runApplication(ExteriorManager.APP_FACEBOOK);
						break;
					case 2:
						Activa.myExteriorManager.runApplication(ExteriorManager.APP_TWITTER);
						break;
					case 3:
						Activa.myExteriorManager.runApplication(ExteriorManager.APP_LINKEDIN);
						break;
					default:
						break;
				}
		    }
		});
		AlertDialog alert = builder.create();
		alert.show();
	}
	
	public void loadMusic() {
		final CharSequence[] items = {Activa.myExteriorManager.externalApplications.get(ExteriorManager.APP_MUSIC).name, 
				Activa.myExteriorManager.externalApplications.get(ExteriorManager.APP_SPOTIFY).name, 
				Activa.myExteriorManager.externalApplications.get(ExteriorManager.APP_DEEZER).name, 
				Activa.myExteriorManager.externalApplications.get(ExteriorManager.APP_LASTFM).name, 
				Activa.myExteriorManager.externalApplications.get(ExteriorManager.APP_SLACKER).name};
		AlertDialog.Builder builder = new AlertDialog.Builder(Activa.myApp);
		builder.setTitle(Activa.myLanguageManager.MAIN_MUSIC);
		builder.setItems(items, new DialogInterface.OnClickListener() {
		    public void onClick(DialogInterface dialog, int item) {
		        switch (item) {
					case 0:
						Activa.myExteriorManager.runApplication(ExteriorManager.APP_MUSIC);
						break;
					case 1:
						Activa.myExteriorManager.runApplication(ExteriorManager.APP_SPOTIFY);
						break;
					case 2:
						Activa.myExteriorManager.runApplication(ExteriorManager.APP_DEEZER);
						break;
					case 3:
						Activa.myExteriorManager.runApplication(ExteriorManager.APP_LASTFM);
						break;
					case 4:
						Activa.myExteriorManager.runApplication(ExteriorManager.APP_SLACKER);
						break;
					default:
						break;
				}
		    }
		});
		AlertDialog alert = builder.create();
		alert.show();
	}
	
	public void loadLinksExtended(final Hashtable<Integer, ExternalApp> links, final String title, final String marketurl) {
		final ExternalApp[] items = new ExternalApp[links.size()];
		Enumeration<ExternalApp> apps = links.elements();
		int i = 0;
		while (apps.hasMoreElements()) {
			items[i] = apps.nextElement();
			i++;
		}
		ListAdapter adapter = new ArrayAdapter<ExternalApp>(Activa.myApp,android.R.layout.select_dialog_item,
			    android.R.id.text1,items){
			public View getView(int position, View convertView, ViewGroup parent) {
				PackageManager manager = Activa.myApp.getPackageManager();
				//User super class to create the View
			    View v = super.getView(position, convertView, parent);
			    TextView tv = (TextView)v.findViewById(android.R.id.text1);
			    ImageView image = new ImageView(Activa.myApp);
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
			    int dp5 = (int) (5 * Activa.myApp.getResources().getDisplayMetrics().density + 0.5f);
			    tv.setCompoundDrawablePadding(dp5);
			    return v;
			}
		};
		new AlertDialog.Builder(Activa.myApp)
	    .setTitle(title)
	    .setAdapter(adapter, new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int item) {
	        	items[item].startApplication();
	        }
	    })
	    .setPositiveButton(Activa.myApp.getResources().getString(R.string.addlink), new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				loadAddLinks(links, title, marketurl);
			}
		})
	    .setNegativeButton(Activa.myApp.getResources().getString(R.string.removelink), new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				loadRemoveLinks(links, title, marketurl);
			}
		})
	    .show();
		Toast toast = Toast.makeText(Activa.myApp, Activa.myApp.getResources().getString(R.string.toastexternallinks), Toast.LENGTH_SHORT);
		toast.show();
	}
	
	public void loadAddLinks(final Hashtable<Integer, ExternalApp> links, final String title, final String marketurl) {
		final PackageManager manager = Activa.myApp.getPackageManager();
		List<ResolveInfo> programs = Activa.myExteriorManager.getExternalAppsList();
		final ExternalApp[] items = new ExternalApp[programs.size()];
		int i = 0;
		for (ResolveInfo resolveInfo : programs) {
			items[i] = new ExternalApp(resolveInfo, manager);
			i++;
		}
//		Hashtable<Integer, ExternalApp> appsToLink = new Hashtable<Integer, ExternalApp>();
		ListAdapter adapter = new ArrayAdapter<ExternalApp>(Activa.myApp,android.R.layout.select_dialog_item,
			    android.R.id.text1,items){
			public View getView(int position, View convertView, ViewGroup parent) {
				//User super class to create the View
			    View v = super.getView(position, convertView, parent);
			    TextView tv = (TextView)v.findViewById(android.R.id.text1);
			    ImageView image = new ImageView(Activa.myApp);
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
			    int dp5 = (int) (5 * Activa.myApp.getResources().getDisplayMetrics().density + 0.5f);
			    tv.setCompoundDrawablePadding(dp5);
			    return v;
			}
		};
		new AlertDialog.Builder(Activa.myApp)
	    .setTitle(Activa.myLanguageManager.MAIN_SELECT_EXTERNALPROG)
	    .setAdapter(adapter, new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int item) {
	        	links.put(Activa.myExteriorManager.getFreeLinkId(links), items[item]);
				Activa.myExteriorManager.savePrograms();
		    	loadInfoPopup(Activa.myLanguageManager.MAIN_SELECT_PROGLINKED);
		    	loadAddLinks(links, title, marketurl);
	        }
	    })
	    .setPositiveButton(Activa.myApp.getResources().getString(R.string.oklink), new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				loadLinksExtended(links, title, marketurl);
			}
		})
	    .setNegativeButton(Activa.myApp.getResources().getString(R.string.tomarketlink), new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				try {
					if (marketurl.equalsIgnoreCase("")) {
						Intent intent = new Intent(Intent.ACTION_MAIN);
				        intent.addCategory(Intent.CATEGORY_LAUNCHER);
				        intent.setComponent(new ComponentName("com.android.vending", "com.android.vending.AssetBrowserActivity"));
				        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
						Activa.myApp.startActivity(intent);	
					}
					else Activa.myApp.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://market.android.com/apps/" + marketurl)));
			    } catch (Exception e) {
					e.printStackTrace();
				}
//				loadAddLinks(links, title, marketurl);
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
		AlertDialog.Builder builder = new AlertDialog.Builder(Activa.myApp);
		builder.setTitle(Activa.myApp.getResources().getString(R.string.removelink));
		builder.setMultiChoiceItems(items, null, new OnMultiChoiceClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which, boolean isChecked) {
				if (isChecked) progsToRemove.add(proglist[which]);
				else progsToRemove.remove(proglist[which]);
			}
		});
		builder.setPositiveButton(Activa.myApp.getResources().getString(R.string.oklink), new DialogInterface.OnClickListener() {
	           public void onClick(DialogInterface dialog, int id) {
	        	   for (int i = 0; i < progsToRemove.size(); i++) {
	        		   links.remove(progsToRemove.get(i));
	        	   }
	        	   Activa.myExteriorManager.savePrograms();
			       loadLinksExtended(links, title, marketurl);
	           }
	    });
		AlertDialog alert = builder.create();
		alert.show();
	}
	
	public void loadPictures() {
		final CharSequence[] items = {Activa.myExteriorManager.externalApplications.get(ExteriorManager.APP_PICTURES).name, 
				Activa.myExteriorManager.externalApplications.get(ExteriorManager.APP_CAMERA).name};
		AlertDialog.Builder builder = new AlertDialog.Builder(Activa.myApp);
		builder.setTitle(Activa.myLanguageManager.MAIN_PICTURES);
		builder.setItems(items, new DialogInterface.OnClickListener() {
		    public void onClick(DialogInterface dialog, int item) {
		        switch (item) {
					case 0:
						Activa.myExteriorManager.runApplication(ExteriorManager.APP_PICTURES);
						break;
					case 1:
						Activa.myExteriorManager.runApplication(ExteriorManager.APP_CAMERA);
						break;
					default:
						break;
				}
		    }
		});
		AlertDialog alert = builder.create();
		alert.show();
	}
	
	public void loadVideos() {
		final CharSequence[] items = {Activa.myExteriorManager.externalApplications.get(ExteriorManager.APP_VIDEOCAMERA).name, 
				Activa.myExteriorManager.externalApplications.get(ExteriorManager.APP_YOUTUBE).name};
		AlertDialog.Builder builder = new AlertDialog.Builder(Activa.myApp);
		builder.setTitle(Activa.myLanguageManager.MAIN_VIDEOS);
		builder.setItems(items, new DialogInterface.OnClickListener() {
		    public void onClick(DialogInterface dialog, int item) {
		        switch (item) {
//					case 0:
//						Activa.myExteriorManager.runApplication(ExteriorManager.APP_VIDEOS);
//						break;
					case 0:
						Activa.myExteriorManager.runApplication(ExteriorManager.APP_VIDEOCAMERA);
						break;
					case 1:
						Activa.myExteriorManager.runApplication(ExteriorManager.APP_YOUTUBE);
						break;
					default:
						break;
				}
		    }
		});
		AlertDialog alert = builder.create();
		alert.show();
	}
	
	public void loadMails() {
		final CharSequence[] items = {Activa.myExteriorManager.externalApplications.get(ExteriorManager.APP_GMAIL).name, 
				Activa.myExteriorManager.externalApplications.get(ExteriorManager.APP_MAIL).name};
		AlertDialog.Builder builder = new AlertDialog.Builder(Activa.myApp);
		builder.setTitle("Email");
		builder.setItems(items, new DialogInterface.OnClickListener() {
		    public void onClick(DialogInterface dialog, int item) {
		        switch (item) {
//					case 0:
//						Activa.myExteriorManager.runApplication(ExteriorManager.APP_VIDEOS);
//						break;
					case 0:
						Activa.myExteriorManager.runApplication(ExteriorManager.APP_GMAIL);
						break;
					case 1:
						Activa.myExteriorManager.runApplication(ExteriorManager.APP_MAIL);
						break;
					default:
						break;
				}
		    }
		});
		AlertDialog alert = builder.create();
		alert.show();
	}
	
	public void loadPatAndQuests() {
		final CharSequence[] items = {Activa.myLanguageManager.PATIENTS_QUEST, 
				Activa.myLanguageManager.PATIENTS_TITLE};
		AlertDialog.Builder builder = new AlertDialog.Builder(Activa.myApp);
		builder.setTitle(Activa.myLanguageManager.PATIENTS_QUEST + " & " + Activa.myLanguageManager.PATIENTS_TITLE);
		builder.setItems(items, new DialogInterface.OnClickListener() {
		    public void onClick(DialogInterface dialog, int item) {
		        switch (item) {
					case 0:
						if (!Activa.myMobileManager.identified) Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.MAIN_NOTAVAILABLE);
						else if (Activa.myMobileManager.userForServices instanceof com.o2hlink.activ8.client.entity.Patient) Activa.myUIManager.loadTotalQuestList();
						else Activa.myUIManager.loadSharedQuestionnaires();
						break;
					case 1:
						if (!Activa.myMobileManager.identified) Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.MAIN_NOTAVAILABLE);
						else Activa.myUIManager.showPatients();
						break;
					default:
						break;
				}
		    }
		});
		AlertDialog alert = builder.create();
		alert.show();
	}
	
	public void loadProjectsAndQuests() {
		final CharSequence[] items = {Activa.myLanguageManager.MAIN_PROJECTS, 
				Activa.myLanguageManager.PATIENTS_QUEST};
		AlertDialog.Builder builder = new AlertDialog.Builder(Activa.myApp);
		builder.setTitle(Activa.myLanguageManager.MAIN_PROJECTS + " & " + Activa.myLanguageManager.PATIENTS_QUEST);
		builder.setItems(items, new DialogInterface.OnClickListener() {
		    public void onClick(DialogInterface dialog, int item) {
		        switch (item) {
					case 0:
						if (!Activa.myMobileManager.identified) Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.MAIN_NOTAVAILABLE);
						else Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.MAIN_NOTAVAILABLE);
						break;
					case 1:
						if (!Activa.myMobileManager.identified) Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.MAIN_NOTAVAILABLE);
						else if (Activa.myMobileManager.userForServices instanceof com.o2hlink.activ8.client.entity.Patient) Activa.myUIManager.loadTotalQuestList();
						else Activa.myUIManager.loadSharedQuestionnaires();
						break;
					default:
						break;
				}
		    }
		});
		AlertDialog alert = builder.create();
		alert.show();
	}
	
	public void loadCalendarAndProjects() {
		final CharSequence[] items = {Activa.myLanguageManager.MAIN_CALENDAR, 
				Activa.myLanguageManager.MAIN_PROJECTS};
		AlertDialog.Builder builder = new AlertDialog.Builder(Activa.myApp);
		builder.setTitle(Activa.myLanguageManager.MAIN_CALENDAR + " & " + Activa.myLanguageManager.MAIN_PROJECTS);
		builder.setItems(items, new DialogInterface.OnClickListener() {
		    public void onClick(DialogInterface dialog, int item) {
		        switch (item) {
					case 0:
						if (!Activa.myMobileManager.identified) Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.MAIN_NOTAVAILABLE);
						else Activa.myUIManager.loadScheduleDay(new Date());
						break;
					case 1:
						if (!Activa.myMobileManager.identified) Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.MAIN_NOTAVAILABLE);
						else Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.MAIN_NOTAVAILABLE);
						break;
					default:
						break;
				}
		    }
		});
		AlertDialog alert = builder.create();
		alert.show();
	}
	
	public void loadPapersAndQuest() {
		final CharSequence[] items = {Activa.myLanguageManager.MAIN_PAPERS, 
				Activa.myLanguageManager.PATIENTS_QUEST};
		AlertDialog.Builder builder = new AlertDialog.Builder(Activa.myApp);
		builder.setTitle(Activa.myLanguageManager.MAIN_PAPERS + " & " + Activa.myLanguageManager.PATIENTS_QUEST);
		builder.setItems(items, new DialogInterface.OnClickListener() {
		    public void onClick(DialogInterface dialog, int item) {
		        switch (item) {
					case 0:
						if (!Activa.myMobileManager.identified) Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.MAIN_NOTAVAILABLE);
						else Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.MAIN_NOTAVAILABLE);
						break;
					case 1:
						if (!Activa.myMobileManager.identified) Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.MAIN_NOTAVAILABLE);
						else if (Activa.myMobileManager.userForServices instanceof com.o2hlink.activ8.client.entity.Patient) Activa.myUIManager.loadTotalQuestList();
						else Activa.myUIManager.loadSharedQuestionnaires();
						break;
					default:
						break;
				}
		    }
		});
		AlertDialog alert = builder.create();
		alert.show();
	}
	
	public void loadContactWithUs() {
		final CharSequence[] items = {Activa.myLanguageManager.MAIN_SUPPORT, 
				Activa.myLanguageManager.MAIN_FACEBOOKPROFILE};
		AlertDialog.Builder builder = new AlertDialog.Builder(Activa.myApp);
		builder.setTitle(Activa.myLanguageManager.MAIN_CONTACUS);
		builder.setItems(items, new DialogInterface.OnClickListener() {
		    public void onClick(DialogInterface dialog, int item) {
		        switch (item) {
					case 0:
						String url="mailto:" + ActivaConfig.ACTIV8SUPPORT_URL + "?subject=PIMTool%20Beta%20support";
						Intent in = new Intent(Intent.ACTION_VIEW);
						in.setData(Uri.parse(url));
						Activa.myApp.startActivity(in);
						break;
					case 1:
						Intent in2 = new Intent(Intent.ACTION_VIEW);
						in2.setData(Uri.parse(ActivaConfig.ACTIV8FACEBOOK_URL));
						Activa.myApp.startActivity(in2);
						break;
					default:
						break;
				}
		    }
		});
		AlertDialog alert = builder.create();
		alert.show();
	}
	
	public void loadAboutUs() {
		AlertDialog.Builder builder = new AlertDialog.Builder(Activa.myApp);
		builder.setMessage(Html.fromHtml(String.format(Activa.myLanguageManager.TEXT_ABOUTUS, Activa.myApp.getResources().getString(R.string.app_version))));
		builder.setPositiveButton(Activa.myLanguageManager.MAIN_TERMSANDCONDS, new Dialog.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
	        	Intent in = new Intent(Intent.ACTION_VIEW);
				in.setData(Uri.parse(ActivaConfig.ACTIV8TERMSANDCONDS_URL));
				Activa.myApp.startActivity(in);
			}
		});
		builder.setNegativeButton(Activa.myLanguageManager.MAIN_PRIVACYCONDS, new Dialog.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
	        	Intent in = new Intent(Intent.ACTION_VIEW);
				in.setData(Uri.parse(ActivaConfig.ACTIV8TERMSANDCONDS_URL));
				Activa.myApp.startActivity(in);
			}
		});
		builder.show();
	}
	
	public void showPatients () {
		this.state = UI_STATE_PATIENTS;
		Activa.myApp.setContentView(R.layout.list);
		((TextView) Activa.myApp.findViewById(R.id.startText)).append(Activa.myLanguageManager.PATIENTS_TITLE);
		TableLayout patientlisting = (TableLayout)Activa.myApp.findViewById(R.id.listing);
		Enumeration<Patient> enumer = Activa.myPatientManager.patientSet.elements();
		while (enumer.hasMoreElements()) {
			final Patient patient = enumer.nextElement();
			TableRow buttonLayout = new TableRow(Activa.myApp);
			buttonLayout.setOrientation(TableRow.HORIZONTAL);
			buttonLayout.setGravity(Gravity.CENTER_VERTICAL);
			buttonLayout.setLayoutParams(new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));			
			ImageButton button = new ImageButton(Activa.myApp);
			button.setBackgroundResource(R.drawable.iconbg);
			button.setImageResource(R.drawable.patient);
			OnClickListener listener = new OnClickListener() {			
				@Override
				public void onClick(View v) {
					Activa.myPatientManager.currentPat = patient;
					loadPatientMenu(patient);
				}
			};
			button.setOnClickListener(listener);
			TextView text = new TextView(Activa.myApp);
			text.append(patient.getFirstName() + " " + patient.getLastName());
			text.setTextSize((float) 20);
			text.setTextColor(Color.BLACK);
			text.setTypeface(Typeface.SANS_SERIF);
			text.setOnClickListener(listener);
			buttonLayout.addView(button);
			buttonLayout.addView(text);
			buttonLayout.setOnClickListener(listener);
			button.setOnClickListener(listener);
			text.setOnClickListener(listener);
			patientlisting.addView(buttonLayout);
		}
		ImageButton back = (ImageButton) Activa.myApp.findViewById(R.id.previous);
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Activa.myUIManager.loadGeneratedSubenvironment(Activa.myUIManager.lastSubenv, true);
			}
		});
		ImageButton add = (ImageButton) Activa.myApp.findViewById(R.id.help);
		add.setImageResource(R.drawable.plus);
		add.setVisibility(View.VISIBLE);
		add.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				addPatients();			
			}
		});
		ImageView delete = (ImageView) Activa.myApp.findViewById(R.id.animation);
		delete.setImageResource(R.drawable.delete);
		delete.setVisibility(View.VISIBLE);
		delete.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				removePatients();			
			}
		});
	}
	
	public void addPatients() {
		LayoutInflater inflater = (LayoutInflater) Activa.myApp.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		final View layout = inflater.inflate(R.layout.toastsearch,
		                               (ViewGroup) Activa.myApp.findViewById(R.id.toastsearchroot));
		TextView text = (TextView) layout.findViewById(R.id.searchexpl);
		text.setText(Activa.myLanguageManager.NOTIFICATION_PATIENT_EXPL);
		ImageButton image = (ImageButton) layout.findViewById(R.id.searchok);
		Builder builder = new AlertDialog.Builder(Activa.myApp);
		builder.setView(layout);
		final AlertDialog alertDialog = builder.create();
		alertDialog.show();
		image.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				final String query = ((EditText)layout.findViewById(R.id.searchtext)).getText().toString();
				alertDialog.cancel();
				final ProgressDialog dialog = ProgressDialog.show(Activa.myApp, Activa.myLanguageManager.NOTIFICATION_PATIENT_TITLE, 
						Activa.myLanguageManager.NOTIFICATION_PATIENT_SEARCHING, true);
				dialog.show();
				Runnable run =  new Runnable() {
					Array<com.o2hlink.activ8.client.entity.User> users;
					Array<Patient> patients;
					@Override
					public void run() {
						patients = Activa.myPatientManager.SearchPatients(query);
						dialog.cancel();
						if (patients == null) {
							handler.sendEmptyMessage(0);
							return;
						}
						if (patients.size() == 0) {
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
									Toast toast = Toast.makeText(Activa.myApp, Activa.myLanguageManager.CONNECTION_FAILED, Toast.LENGTH_SHORT);
									toast.show();
									break;
								case 1:
									Toast toast2 = Toast.makeText(Activa.myApp, Activa.myLanguageManager.NOTIFICATION_PATIENT_NOTFOUND, Toast.LENGTH_SHORT);
									toast2.show();
									break;
								case 2:
									final CharSequence[] items = new CharSequence[patients.size()];
									final Array<com.o2hlink.activ8.client.entity.Patient>usersToAdd = new Array<com.o2hlink.activ8.client.entity.Patient>();
									for (int i = 0; i < patients.size(); i++) {
										items[i] = patients.get(i).getFirstName() + " " + patients.get(i).getLastName();
									}
									AlertDialog.Builder builder = new AlertDialog.Builder(Activa.myApp);
									builder.setTitle(Activa.myLanguageManager.NOTIFICATION_PATIENT_ADD);
									builder.setMultiChoiceItems(items, null, new OnMultiChoiceClickListener() {
										@Override
										public void onClick(DialogInterface dialog, int which, boolean isChecked) {
											if (isChecked) usersToAdd.add(patients.get(which));
											else usersToAdd.remove(patients.get(which));
										}
									});
									builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
								           public void onClick(DialogInterface dialog, int id) {
								        	   for (int i = 0; i < usersToAdd.size(); i++) {
								        		   Activa.myPatientManager.AddPatient(usersToAdd.get(i));
								        	   }
								               dialog.cancel();
								               showPatients();
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
	
	public void removePatients() {
		int i = 0;
		final CharSequence[] items = new CharSequence[Activa.myPatientManager.patientSet.size()];
		final com.o2hlink.activ8.client.entity.Patient[] patitems = new com.o2hlink.activ8.client.entity.Patient[Activa.myPatientManager.patientSet.size()];
		final Array<com.o2hlink.activ8.client.entity.Patient>usersToRem = new Array<com.o2hlink.activ8.client.entity.Patient>();
		Enumeration<Patient> elements = Activa.myPatientManager.patientSet.elements();
		while (elements.hasMoreElements()) {
			Patient patient = elements.nextElement();
			items[i] = patient.getFirstName() + " " + patient.getLastName();
			patitems[i] = patient;
			i++;
		}
		AlertDialog.Builder builder = new AlertDialog.Builder(Activa.myApp);
		builder.setTitle(Activa.myLanguageManager.NOTIFICATION_PATIENT_ADD);
		builder.setMultiChoiceItems(items, null, new OnMultiChoiceClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which, boolean isChecked) {
				if (isChecked) usersToRem.add(patitems[which]);
				else usersToRem.remove(patitems[which]);
			}
		});
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
	           public void onClick(DialogInterface dialog, int id) {
	        	   for (int i = 0; i < usersToRem.size(); i++) {
	        		   Activa.myPatientManager.AddPatient(usersToRem.get(i));
	        	   }
	               dialog.cancel();
	               showPatients();
	           }
	    });
		AlertDialog alert = builder.create();
		alert.show();
	}
	
	public void loadPatientMenu(final Patient patient) {
		this.state = UI_STATE_PATIENTS;
		Activa.myApp.setContentView(R.layout.list);
		((TextView) Activa.myApp.findViewById(R.id.startText)).append(patient.getFirstName() + " " + patient.getLastName());
		TableLayout options = (TableLayout)Activa.myApp.findViewById(R.id.listing);
		
		TableRow buttonLayout = new TableRow(Activa.myApp);
		buttonLayout.setOrientation(TableRow.HORIZONTAL);
		buttonLayout.setGravity(Gravity.CENTER_VERTICAL);
		buttonLayout.setLayoutParams(new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));			
		ImageButton button = new ImageButton(Activa.myApp);
		button.setBackgroundResource(R.drawable.iconbg);
		button.setImageResource(R.drawable.history);
		OnClickListener listener = new OnClickListener() {			
			@Override
			public void onClick(View v) {
				GetHistory sending = new GetHistory(patient, false);
				Thread thread = new Thread(sending, "GETHISTORY");
				thread.start();
			}
		};
		button.setOnClickListener(listener);
		TextView text = new TextView(Activa.myApp);
		text.append(Activa.myLanguageManager.PATIENTS_HISTORY);
		text.setTextSize((float) 20);
		text.setTextColor(Color.BLACK);
		text.setTypeface(Typeface.SANS_SERIF);
		text.setOnClickListener(listener);
		buttonLayout.addView(button);
		buttonLayout.addView(text);
		buttonLayout.setOnClickListener(listener);
		button.setOnClickListener(listener);
		text.setOnClickListener(listener);
		options.addView(buttonLayout);
		
		buttonLayout = new TableRow(Activa.myApp);
		buttonLayout.setOrientation(TableRow.HORIZONTAL);
		buttonLayout.setGravity(Gravity.CENTER_VERTICAL);
		buttonLayout.setLayoutParams(new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));			
		button = new ImageButton(Activa.myApp);
		button.setBackgroundResource(R.drawable.iconbg);
		button.setImageResource(R.drawable.sensor);
		listener = new OnClickListener() {			
			@Override
			public void onClick(View v) {
				Runnable run = new Runnable() {
					@Override
					public void run() {
						handler.sendEmptyMessage(0);
						Date start = new Date();
						start.setMonth(start.getMonth() - 1);
						Date end = new Date();
						end.setMonth(end.getMonth() + 1);
						boolean success = Activa.myProtocolManager.getMeasuringEvents(patient.getId(), start, end);
						if (success) {
							handler.sendEmptyMessage(1);
						}
						else handler.sendEmptyMessage(2);
					}
					private Handler handler = new Handler(){
						@Override
						public void handleMessage(Message msg) {
							ImageView animationFrame;
							AnimationDrawable animation;
							switch (msg.what) {
								case 0:
									Activa.myUIManager.loadInfoPopupWithoutExiting(Activa.myLanguageManager.CONNECTION_MEAS);
									animationFrame = (ImageView) Activa.myApp.findViewById(R.id.popupImage);
									animationFrame.setVisibility(View.VISIBLE);
									animationFrame.setBackgroundResource(R.drawable.loading);
									animation = (AnimationDrawable) animationFrame.getBackground();
									animation.start();
									break;
								case 1:
									animationFrame = (ImageView) Activa.myApp.findViewById(R.id.popupImage);
									animation = (AnimationDrawable) animationFrame.getBackground();
									animation.stop();
									animationFrame.setVisibility(View.GONE);
									Activa.myUIManager.removeInfoPopup();
									Activa.myUIManager.loadScheduleDayForPatientMeas(new Date());
									break;
								case 2:
									animationFrame = (ImageView) Activa.myApp.findViewById(R.id.popupImage);
									animation = (AnimationDrawable) animationFrame.getBackground();
									animation.stop();
									animationFrame.setVisibility(View.GONE);
									Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.CONNECTION_FAILED);
									break;
							}
						}
					};
				};
				Thread thread = new Thread(run);
				thread.start();
			}
		};
		button.setOnClickListener(listener);
		text = new TextView(Activa.myApp);
		text.append(Activa.myLanguageManager.PATIENTS_MEAS);
		text.setTextSize((float) 20);
		text.setTextColor(Color.BLACK);
		text.setTypeface(Typeface.SANS_SERIF);
		text.setOnClickListener(listener);
		buttonLayout.addView(button);
		buttonLayout.addView(text);
		buttonLayout.setOnClickListener(listener);
		button.setOnClickListener(listener);
		text.setOnClickListener(listener);
		options.addView(buttonLayout);
		
		buttonLayout = new TableRow(Activa.myApp);
		buttonLayout.setOrientation(TableRow.HORIZONTAL);
		buttonLayout.setGravity(Gravity.CENTER_VERTICAL);
		buttonLayout.setLayoutParams(new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));			
		button = new ImageButton(Activa.myApp);
		button.setBackgroundResource(R.drawable.iconbg);
		button.setImageResource(R.drawable.quest);
		listener = new OnClickListener() {			
			@Override
			public void onClick(View v) {
				Runnable run = new Runnable() {
					@Override
					public void run() {
						handler.sendEmptyMessage(0);
						Date start = new Date();
						start.setMonth(start.getMonth() - 1);
						Date end = new Date();
						end.setMonth(end.getMonth() + 1);
						boolean success = Activa.myProtocolManager.getQuestEvents(patient.getId(), start, end);
						if (success) {
							handler.sendEmptyMessage(1);
						}
						else handler.sendEmptyMessage(2);
					}
					private Handler handler = new Handler(){
						@Override
						public void handleMessage(Message msg) {
							ImageView animationFrame;
							AnimationDrawable animation;
							switch (msg.what) {
								case 0:
									Activa.myUIManager.loadInfoPopupWithoutExiting(Activa.myLanguageManager.CONNECTION_QUEST);
									animationFrame = (ImageView) Activa.myApp.findViewById(R.id.popupImage);
									animationFrame.setVisibility(View.VISIBLE);
									animationFrame.setBackgroundResource(R.drawable.loading);
									animation = (AnimationDrawable) animationFrame.getBackground();
									animation.start();
									break;
								case 1:
									animationFrame = (ImageView) Activa.myApp.findViewById(R.id.popupImage);
									animation = (AnimationDrawable) animationFrame.getBackground();
									animation.stop();
									animationFrame.setVisibility(View.GONE);
									Activa.myUIManager.removeInfoPopup();
									Activa.myUIManager.loadScheduleDayForPatientQuest(new Date());
									break;
								case 2:
									animationFrame = (ImageView) Activa.myApp.findViewById(R.id.popupImage);
									animation = (AnimationDrawable) animationFrame.getBackground();
									animation.stop();
									animationFrame.setVisibility(View.GONE);
									Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.CONNECTION_FAILED);
									break;
							}
						}
					};
				};
				Thread thread = new Thread(run);
				thread.start();
			}
		};
		button.setOnClickListener(listener);
		text = new TextView(Activa.myApp);
		text.append(Activa.myLanguageManager.PATIENTS_QUEST);
		text.setTextSize((float) 20);
		text.setTextColor(Color.BLACK);
		text.setTypeface(Typeface.SANS_SERIF);
		text.setOnClickListener(listener);
		buttonLayout.addView(button);
		buttonLayout.addView(text);
		buttonLayout.setOnClickListener(listener);
		button.setOnClickListener(listener);
		text.setOnClickListener(listener);
		options.addView(buttonLayout);
		
		ImageButton back = (ImageButton) Activa.myApp.findViewById(R.id.previous);
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showPatients();
			}
		});
	}
	
	public void showHistory(final Patient patient, final boolean returning) {
		this.state = UI_STATE_PATIENTS;
		int width = Activa.myApp.getResources().getDisplayMetrics().widthPixels;
		Activa.myApp.setContentView(R.layout.history);
		((TextView) Activa.myApp.findViewById(R.id.startText)).append(Activa.myLanguageManager.PATIENTS_HISTORY);
		
		LinearLayout content = (LinearLayout) Activa.myApp.findViewById(R.id.content);
		TextView text = new TextView(Activa.myApp);
		text.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		text.setTextSize((float) 14.0);
		text.setPadding(10, 0, 0, 0);
		text.setTextColor(Color.BLACK);
		text.setText(patient.getPatientPersonalData());
		content.addView(text);
		AbsoluteLayout separator = new AbsoluteLayout(Activa.myApp);
		separator.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, 1));
		separator.setBackgroundColor(Color.BLACK);
		content.addView(separator);

		content = (LinearLayout) Activa.myApp.findViewById(R.id.content);
		text = new TextView(Activa.myApp);
		text.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		text.setTextSize((float) 14.0);
		text.setPadding(10, 0, 0, 0);
		text.setTextColor(Color.BLACK);
		text.append(patient.getMedicalRecordsSpan());
		content.addView(text);
		if (Activa.myMobileManager.userForServices instanceof Clinician) {
			text = new TextView(Activa.myApp);
			text.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
			text.setTextSize((float) 14.0);
			text.setPadding(10, 0, 0, 0);
			text.setLinksClickable(true);
			text.setTextColor(Color.MAGENTA);
			text.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					addExploration(patient.getId(), returning);
				}
			});
			text.setText(Activa.myLanguageManager.PATIENTS_HISTORY_ADDEXPLORATION);
			content.addView(text);
			text = new TextView(Activa.myApp);
			text.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
			text.setTextSize((float) 14.0);
			text.setPadding(10, 0, 0, 0);
			text.setLinksClickable(true);
			text.setTextColor(Color.MAGENTA);
			text.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					addHistoryRecord(patient.getId(), returning);
				}
			});
			text.setText(Activa.myLanguageManager.PATIENTS_HISTORY_ADDRECORD);
			content.addView(text);
		}
		separator = new AbsoluteLayout(Activa.myApp);
		separator.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, 1));
		separator.setBackgroundColor(Color.BLACK);
		content.addView(separator);

		content = (LinearLayout) Activa.myApp.findViewById(R.id.content);
		text = new TextView(Activa.myApp);
		text.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		text.setTextSize((float) 14.0);
		text.setPadding(10, 0, 0, 0);
		text.setTextColor(Color.BLACK);
		if (patient.lastWeightHeight == null) text.append("No weight and height done.");
		else text.append(patient.lastWeightHeight.getWeightHeightSpanData());
		content.addView(text);
		separator = new AbsoluteLayout(Activa.myApp);
		separator.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, 1));
		separator.setBackgroundColor(Color.BLACK);
		content.addView(separator);

		content = (LinearLayout) Activa.myApp.findViewById(R.id.content);
		text = new TextView(Activa.myApp);
		text.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		text.setTextSize((float) 14.0);
		text.setPadding(10, 0, 0, 0);
		text.setTextColor(Color.BLACK);
		if (patient.lastPulseoximetry == null) text.append("No pulseoximetry done.");
		else text.append(patient.lastPulseoximetry.getPulsioximetrySpanData());
		content.addView(text);
		separator = new AbsoluteLayout(Activa.myApp);
		separator.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, 1));
		separator.setBackgroundColor(Color.BLACK);
		content.addView(separator);

		content = (LinearLayout) Activa.myApp.findViewById(R.id.content);
		text = new TextView(Activa.myApp);
		text.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		text.setTextSize((float) 14.0);
		text.setPadding(10, 0, 0, 0);
		text.setTextColor(Color.BLACK);
		if (patient.lastSixMinutes == null) text.append("No six minutes walk done.");
		else text.append(patient.lastSixMinutes.getExerciseSpanData());
		content.addView(text);
		if (patient.lastSixMinutes != null) {
			FrameLayout frame = new FrameLayout(Activa.myApp);
			frame.setLayoutParams(new LayoutParams(width*4/5, width*4/5));
			SixMinutesGraphViewWithCustomData graph = new SixMinutesGraphViewWithCustomData(Activa.myApp, patient.lastSixMinutes.hrtrack, patient.lastSixMinutes.so2track, patient.lastSixMinutes.time, width*4/5, width*4/5);
			frame.addView(graph);
			content.addView(frame);
		}
		separator = new AbsoluteLayout(Activa.myApp);
		separator.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, 1));
		separator.setBackgroundColor(Color.BLACK);
		content.addView(separator);

		content = (LinearLayout) Activa.myApp.findViewById(R.id.content);
		text = new TextView(Activa.myApp);
		text.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		text.setTextSize((float) 14.0);
		text.setPadding(10, 0, 0, 0);
		text.setTextColor(Color.BLACK);
		if (patient.lastSpirometry == null) text.append("No spirometry done.");
		else text.append(patient.lastSpirometry.getSpirometrySpanData());
		content.addView(text);
		if (patient.lastSpirometry != null) {
			FrameLayout frame = new FrameLayout(Activa.myApp);
			frame.setLayoutParams(new LayoutParams(width*4/5, width*4/5));
			SpirometryGraphViewWithCustomData graph = new SpirometryGraphViewWithCustomData(Activa.myApp, (Spirometry) patient.lastSpirometry, patient.lastSpirometry.flow, patient.lastSpirometry.time, width*4/5, width*4/5);
			frame.addView(graph);
			content.addView(frame);
		}
		separator = new AbsoluteLayout(Activa.myApp);
		separator.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, 1));
		separator.setBackgroundColor(Color.BLACK);
		content.addView(separator);

		content = (LinearLayout) Activa.myApp.findViewById(R.id.content);
		text = new TextView(Activa.myApp);
		text.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		text.setTextSize((float) 14.0);
		text.setPadding(10, 0, 0, 0);
		text.setTextColor(Color.BLACK);
		if (patient.lastExercise == null) text.append("No exercise done.");
		else text.append(patient.lastExercise.getExerciseSpanData());
		content.addView(text);
		separator = new AbsoluteLayout(Activa.myApp);
		separator.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, 1));
		separator.setBackgroundColor(Color.BLACK);
		content.addView(separator);
		
		ImageButton back = (ImageButton) Activa.myApp.findViewById(R.id.previous);
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!returning) loadPatientMenu(patient);
				else loadGeneratedSubenvironment(lastSubenv, true);
			}
		});
	}
	
	public void addHistoryRecord(final Long patientId, final boolean returning) {
		LayoutInflater inflater = (LayoutInflater) Activa.myApp.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		final View layout = inflater.inflate(R.layout.toastsearch,
		                               (ViewGroup) Activa.myApp.findViewById(R.id.toastsearchroot));
		TextView text = (TextView) layout.findViewById(R.id.searchexpl);
		text.setText(Activa.myLanguageManager.PATIENTS_HISTORY_SEARCHDIS);
		ImageButton image = (ImageButton) layout.findViewById(R.id.searchok);
		Builder builder = new AlertDialog.Builder(Activa.myApp);
		builder.setView(layout);
		final AlertDialog alertDialog = builder.create();
		alertDialog.show();
		image.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				alertDialog.cancel();
				final ProgressDialog dialog = ProgressDialog.show(Activa.myApp, Activa.myLanguageManager.PATIENTS_HISTORY, 
						Activa.myLanguageManager.PATIENTS_HISTORY_SEARCHINGDIS, true);
				dialog.show();
				String querytemp;
				String selectedLang = Activa.myApp.getResources().getConfiguration().locale.getLanguage();
				if (selectedLang.equals("es")) {
					Translate.setHttpReferrer("http://www.o2hlink.com");
					try {
						querytemp = Translate.execute(((EditText)layout.findViewById(R.id.searchtext)).getText().toString(), Language.SPANISH, Language.ENGLISH);
					} catch (Exception e) {
						querytemp = ((EditText)layout.findViewById(R.id.searchtext)).getText().toString();
						e.printStackTrace();
					}
				}
				else querytemp = ((EditText)layout.findViewById(R.id.searchtext)).getText().toString();
				final String query = querytemp;
				Runnable run =  new Runnable() {
					Array<Disease> diseases;
					Disease dis;
					@Override
					public void run() {
						diseases = Activa.myPatientManager.searchDiseases(query);
						dialog.cancel();
						if (diseases == null) {
							handler.sendEmptyMessage(0);
						} else if (diseases.size() == 0) {
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
									Toast toast = Toast.makeText(Activa.myApp, Activa.myLanguageManager.CONNECTION_FAILED, Toast.LENGTH_SHORT);
									toast.show();
									break;
								case 1:
									Toast toast2 = Toast.makeText(Activa.myApp, Activa.myLanguageManager.PATIENTS_HISTORY_NORECORDSFOUND, Toast.LENGTH_SHORT);
									toast2.show();
									break;
								case 2:
									String[] itemstemp = new String[diseases.size()];
									for (int i = 0; i < diseases.size(); i++) {
										itemstemp[i] = diseases.get(i).getName();
									}
									String selectedLang = Activa.myApp.getResources().getConfiguration().locale.getLanguage();
									if (selectedLang.equals("es")) {
										Translate.setHttpReferrer("http://www.o2hlink.com");
										try {
											itemstemp = Translate.execute(itemstemp, Language.ENGLISH, Language.SPANISH);
										} catch (Exception e) {
											e.printStackTrace();
										}
									}
									final String[] items = itemstemp;
									AlertDialog.Builder builder = new AlertDialog.Builder(Activa.myApp);
									builder.setTitle(Activa.myLanguageManager.PATIENTS_HISTORY_SELECTDIS);
									builder.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {								
										@Override
										public void onClick(DialogInterface dialog, int which) {
											dis = diseases.get(which);
										}
									});
									builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
								           public void onClick(DialogInterface dialog, int id) {
								        	   if (!Activa.myPatientManager.addHistoryRecord(patientId, dis)) {
								        		   Toast toast2 = Toast.makeText(Activa.myApp, Activa.myLanguageManager.PATIENTS_HISTORY_NORECORDSFOUND, Toast.LENGTH_SHORT);
								        		   toast2.show();
								        	   }
								               dialog.cancel();
								               showHistory(Activa.myPatientManager.patientSet.get(patientId), returning);
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
	
	public void addExploration(final Long patientId, final boolean returning) {
		if (Activa.myPatientManager.patientSet.get(patientId).history.isEmpty()) {
			Toast toast = Toast.makeText(Activa.myApp, Activa.myLanguageManager.PATIENTS_HISTORY_NORECORDS, Toast.LENGTH_SHORT);
			toast.show();
			return;
		}
		LayoutInflater inflater = (LayoutInflater) Activa.myApp.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		final View layout = inflater.inflate(R.layout.toastaddexploration,
		                               (ViewGroup) Activa.myApp.findViewById(R.id.toastaddexplorationroot));
		((TextView)layout.findViewById(R.id.addtitle)).setText(Activa.myLanguageManager.PATIENTS_HISTORY);
		((TextView)layout.findViewById(R.id.spinnerrequest)).setText(Activa.myLanguageManager.PATIENTS_HISTORY_SELECTREC);
		((TextView)layout.findViewById(R.id.textrequest)).setText(Activa.myLanguageManager.PATIENTS_HISTORY_TYPEEXPL);
		final EditText text = (EditText) layout.findViewById(R.id.text);
		final Spinner spinner = (Spinner) layout.findViewById(R.id.spinner);
		ImageButton ok = (ImageButton) layout.findViewById(R.id.addeventok);
		final ArrayList<String> spinnerArray = new ArrayList<String>();
		final ArrayList<com.o2hlink.activ8.client.entity.HistoryRecord> records = new ArrayList<com.o2hlink.activ8.client.entity.HistoryRecord>();
		Enumeration<HistoryRecord> recs = Activa.myPatientManager.patientSet.get(patientId).history.elements();
		while (recs.hasMoreElements()) {
			HistoryRecord hr = recs.nextElement();
			spinnerArray.add(hr.diseaseName + " - " + ActivaUtil.dateToReadableString(hr.date));
			records.add(hr.historyRecordForServices);
		}
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(Activa.myApp, android.R.layout.simple_spinner_item, spinnerArray );
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		Builder builder = new AlertDialog.Builder(Activa.myApp);
		builder.setView(layout);
		final AlertDialog alertDialog = builder.create();
		alertDialog.show();
		ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				alertDialog.cancel();
				final ProgressDialog dialog = ProgressDialog.show(Activa.myApp, Activa.myLanguageManager.PATIENTS_HISTORY, 
						Activa.myLanguageManager.PATIENTS_HISTORY_ADDINGEXPL, true);
				dialog.show();
				Runnable run = new Runnable() {
					@Override
					public void run() {
						Long recordId = records.get(spinner.getSelectedItemPosition()).getId();
						String description = text.getText().toString();
						if (Activa.myPatientManager.addExploration(patientId, recordId, description))
							handler.sendEmptyMessage(0);
						else handler.sendEmptyMessage(1);
					}
					private Handler handler = new Handler(){
						@Override
						public void handleMessage(Message msg) {
							switch (msg.what) {
								case 0:
									dialog.cancel();
									showHistory(Activa.myPatientManager.patientSet.get(patientId), returning);
									break;
								case 1:
									dialog.cancel();
									Toast toast = Toast.makeText(Activa.myApp, Activa.myLanguageManager.CONNECTION_FAILED, Toast.LENGTH_SHORT);
									toast.show();
									break;
								default:
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
	
	public void showMap() {
		Activa.myMapManager.showingMap = true;
		Activa.myApp.startActivity(new Intent(Activa.myApp, com.o2hlink.pimtools.map.ActivaMap.class));
	}
	
	/*public void showAmbientsForLoading() {
		this.state = UI_STATE_AMBIENTS;
		Activa.myApp.setContentView(R.layout.ambientlist);
		int width = Activa.myApp.getResources().getDisplayMetrics().widthPixels;
		((TextView) Activa.myApp.findViewById(R.id.startText)).append(Activa.myLanguageManager.MAIN_DOWNLOAD_ENVIRONMENT);
		TableLayout ambientlisting = (TableLayout)Activa.myApp.findViewById(R.id.listing);
		final String currency;
		final String paypalAccount;
		File originalfolder = new File(Environment.getExternalStorageDirectory(), ActivaConfig.ENVIRONMENT_FOLDER);
		if (!originalfolder.exists()) originalfolder.mkdir();
		File ambientdetailsfolder = new File(originalfolder, "ActivaAmbientDetails");
		if (!ambientdetailsfolder.exists()) ambientdetailsfolder.mkdir();
		TelephonyManager tMgr =(TelephonyManager)Activa.myApp.getSystemService(Context.TELEPHONY_SERVICE);
		String countryCode = tMgr.getNetworkCountryIso();
		if (countryCode == null) {
			currency = "USD";
			paypalAccount = ActivaConfig.PAYPAL_USA_ACCOUNT;
		}
		else if (countryCode.equalsIgnoreCase("GB")){
			currency = "GBP";
			paypalAccount = ActivaConfig.PAYPAL_SPAIN_ACCOUNT;
		} else if (EUISOCodes.contains(countryCode)) {
			currency = "EUR";
			paypalAccount = ActivaConfig.PAYPAL_SPAIN_ACCOUNT;
		} else{
			currency = "USD";
			paypalAccount = ActivaConfig.PAYPAL_USA_ACCOUNT;
		}
		for (int i = 0; i < ambients.size(); i++) {
			final String[] ambientinfo = ambients.get(i);
			final String ambientname = ambients.get(i)[0];
			final String ambienturl = ambients.get(i)[1];
			final String ambientprice = ambients.get(i)[2];
			String ambientdetailfile = ambientname.replace(" ", "") + ".jpg";
			String ambientminifile = ambientname.replace(" ", "") + "mini.jpg";
			File ambientdetail = new File(ambientdetailsfolder, ambientdetailfile);
			File ambientmini = new File(ambientdetailsfolder, ambientminifile);
			Drawable ambientpicturetemp = null, ambientminipic = null;
			try {
				ambientminipic = Drawable.createFromStream(new FileInputStream(ambientmini), "src");
				ambientpicturetemp = Drawable.createFromStream(new FileInputStream(ambientdetail), "src");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			final Drawable ambientpicture = ambientpicturetemp;
			// Ambient state (0: CURRENT, 1: READY, 2: CORRUPTED, 3: DOWNLOADING, 4: PURSACHED, 5: FREE/PRICE) 
			int tempAmbientState = 0;
			if ((this.ambient.name != null)&&(this.ambient.name.equalsIgnoreCase(ambientname))) tempAmbientState = 0;
			else if (Ambient.ambientDownloaded(ambientname)) {
				if (Activa.ambientsdownloading.contains(ambientname)) tempAmbientState = 3;
				else if (Ambient.getNumberOfFilesDownloaded(ambientname) == Ambient.getNumberOfFilesForDownloading(ambienturl)) tempAmbientState = 1;
				else tempAmbientState = 2;
			}
			else if (pursached.contains(ambienturl)) tempAmbientState = 4;
			else tempAmbientState = 5;
			final int ambientstate = tempAmbientState;
			TableRow buttonLayout = new TableRow(Activa.myApp);
			buttonLayout.setOrientation(TableRow.HORIZONTAL);
			buttonLayout.setGravity(Gravity.CENTER_VERTICAL);
			buttonLayout.setLayoutParams(new TableLayout.LayoutParams(new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT)));		
			ImageView image = new ImageView(Activa.myApp);
			TextView text = new TextView(Activa.myApp);
			TextView price = new TextView(Activa.myApp);
			image.setBackgroundResource(R.drawable.iconbg);
			if (ambientminipic != null) image.setImageDrawable(ambientminipic);
			image.setLayoutParams(new android.widget.TableRow.LayoutParams(width/5, android.widget.TableRow.LayoutParams.WRAP_CONTENT));
			OnClickListener infolistener = new OnClickListener() {
				@Override
				public void onClick(View v) {
					showAmbientInfo(ambientinfo, ambientpicture, ambientstate, currency, paypalAccount);
				}
			};
			OnClickListener loadlistener = new OnClickListener() {			
				@Override
				public void onClick(View v) {
					Activa.myMobileManager.user.setAmbient(ambientname);
					Activa.myMobileManager.saveUsers();
					loadAmbient();
					loadGeneratedMainScreen(true, false);
				}
			};
			image.setOnClickListener(infolistener);
			image.setPadding(5, 5, 5, 5);
			text.setLayoutParams(new android.widget.TableRow.LayoutParams(width/2 , android.widget.TableRow.LayoutParams.WRAP_CONTENT));
			text.setPadding(5, 5, 5, 5);
			text.setText(ambientname);
			text.setTextSize((float) 20);
			text.setTextColor(Color.BLACK);
			text.setTypeface(Typeface.SANS_SERIF);
			price.setLayoutParams(new android.widget.TableRow.LayoutParams(width*7/20, android.widget.TableRow.LayoutParams.WRAP_CONTENT));
			price.setPadding(5, 5, 5, 5);
			price.setTextSize((float)12);
			switch (ambientstate) {
				case 0:
					price.setText(Activa.myLanguageManager.MAIN_AMBSTATE_CURRENT);
					break;
				case 1:
					price.setText(Activa.myLanguageManager.MAIN_AMBSTATE_READY);
					text.setOnClickListener(loadlistener);
					price.setOnClickListener(loadlistener);
					buttonLayout.setOnClickListener(loadlistener);
					break;
				case 2:
					price.setText(Activa.myLanguageManager.MAIN_AMBSTATE_CORRUPTED);
					text.setOnClickListener(infolistener);
					price.setOnClickListener(infolistener);
					buttonLayout.setOnClickListener(infolistener);
					break;
				case 3:
					price.setText(Activa.myLanguageManager.MAIN_AMBSTATE_DOWNLOADING);
					break;
				case 4:
					price.setText(Activa.myLanguageManager.MAIN_AMBSTATE_PURSACHED);
					text.setOnClickListener(infolistener);
					price.setOnClickListener(infolistener);
					buttonLayout.setOnClickListener(infolistener);
					break;
				case 5:
				default:
					text.setOnClickListener(infolistener);
					price.setOnClickListener(infolistener);
					buttonLayout.setOnClickListener(infolistener);
					if ((ambientprice != null)&&(!ambientprice.equalsIgnoreCase("null"))) price.setText(ambientprice + " " + currency);
					else price.setText(Activa.myLanguageManager.MAIN_AMBSTATE_FREE);
					break;
			}
			price.setTextColor(Color.BLACK);
			price.setTypeface(Typeface.DEFAULT_BOLD);
			buttonLayout.addView(image);
			buttonLayout.addView(text);
			buttonLayout.addView(price);
			ambientlisting.addView(buttonLayout);
			View separator = new View(Activa.myApp);
			LayoutParams separatorLayout = new LayoutParams(android.view.ViewGroup.LayoutParams.FILL_PARENT, 1);
			separator.setLayoutParams(separatorLayout);
			separator.setBackgroundColor(Color.BLACK);
			ambientlisting.addView(separator);
		}
		TableRow buttonLayout = new TableRow(Activa.myApp);
		buttonLayout.setOrientation(TableRow.HORIZONTAL);
		buttonLayout.setGravity(Gravity.CENTER_VERTICAL);
		buttonLayout.setLayoutParams(new TableLayout.LayoutParams(new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT)));		
		ImageView image = new ImageView(Activa.myApp);
		image.setPadding(5, 5, 5, 5);
		image.setBackgroundResource(R.drawable.iconbg);
		image.setLayoutParams(new android.widget.TableRow.LayoutParams(width/5, android.widget.TableRow.LayoutParams.WRAP_CONTENT));
		if (Activa.myMobileManager.userForServices instanceof Patient) image.setImageResource(R.drawable.patscenario);
		else if (Activa.myMobileManager.userForServices instanceof Clinician) image.setImageResource(R.drawable.clinscenario);
		else if (Activa.myMobileManager.userForServices instanceof Researcher) image.setImageResource(R.drawable.resscenario);
		else image.setImageResource(R.drawable.patscenario);
		TextView text = new TextView(Activa.myApp);
		text.setLayoutParams(new android.widget.TableRow.LayoutParams(width*4/5, android.widget.TableRow.LayoutParams.WRAP_CONTENT));
		text.setPadding(5, 5, 5, 5);
		text.setText(Activa.myLanguageManager.MAIN_DEFAULT_ENVIRONMENT_LOAD);
		text.setTextSize((float) 20);
		text.setTextColor(Color.BLACK);
		text.setTypeface(Typeface.SANS_SERIF);
		text.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				loadDefaultAmbient();
				loadGeneratedMainScreen(true, false);
			}
		});
		buttonLayout.addView(image, 74, 58);
		buttonLayout.addView(text);
		((LinearLayout)Activa.myApp.findViewById(R.id.control)).addView(buttonLayout);
		((ImageButton) Activa.myApp.findViewById(R.id.previous)).setVisibility(View.GONE);
		View separator = new View(Activa.myApp);
		LayoutParams separatorLayout = new LayoutParams(android.view.ViewGroup.LayoutParams.FILL_PARENT, 1);
		separator.setLayoutParams(separatorLayout);
		separator.setBackgroundColor(Color.BLACK);
		((LinearLayout)Activa.myApp.findViewById(R.id.control)).addView(separator);
		((ImageButton) Activa.myApp.findViewById(R.id.plus)).setVisibility(View.GONE);		
		ImageButton back = (ImageButton) Activa.myApp.findViewById(R.id.previous);
		back.setVisibility(View.VISIBLE);
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				loadGeneratedSubenvironment(lastSubenv, true);
			}
		});
	}*/
	
	public void showAmbientsForLoading() {
		this.state = UI_STATE_AMBIENTS;
		Activa.myApp.setContentView(R.layout.ambientlist);
		int width = Activa.myApp.getResources().getDisplayMetrics().widthPixels;
		((TextView) Activa.myApp.findViewById(R.id.startText)).append(Activa.myLanguageManager.MAIN_DOWNLOAD_ENVIRONMENT);
//		TableLayout ambientlisting = (TableLayout)Activa.myApp.findViewById(R.id.listing);
		LinearLayout ambientlisting = (LinearLayout)Activa.myApp.findViewById(R.id.control);
		View separator = new View(Activa.myApp);
		LayoutParams separatorLayout = new LayoutParams(android.view.ViewGroup.LayoutParams.FILL_PARENT, 1);
		separator.setLayoutParams(separatorLayout);
		separator.setBackgroundColor(Color.BLACK);
		final String currency;
		final String paypalAccount;
		File originalfolder = new File(Environment.getExternalStorageDirectory(), ActivaConfig.ENVIRONMENT_FOLDER);
		if (!originalfolder.exists()) originalfolder.mkdir();
		File ambientdetailsfolder = new File(originalfolder, "ActivaAmbientDetails");
		if (!ambientdetailsfolder.exists()) ambientdetailsfolder.mkdir();
		TelephonyManager tMgr =(TelephonyManager)Activa.myApp.getSystemService(Context.TELEPHONY_SERVICE);
		String countryCode = tMgr.getNetworkCountryIso();
		if (countryCode == null) {
			currency = "USD";
			paypalAccount = ActivaConfig.PAYPAL_USA_ACCOUNT;
		}
		else if (countryCode.equalsIgnoreCase("GB")){
			currency = "GBP";
			paypalAccount = ActivaConfig.PAYPAL_SPAIN_ACCOUNT;
		} else if (EUISOCodes.contains(countryCode)) {
			currency = "EUR";
			paypalAccount = ActivaConfig.PAYPAL_SPAIN_ACCOUNT;
		} else{
			currency = "USD";
			paypalAccount = ActivaConfig.PAYPAL_USA_ACCOUNT;
		}
		for (int i = 0; i < ambients.size(); i++) {
			final String ambientname = ambients.get(i).getName();
			final String ambientid = ambients.get(i).getId();
			final Theme ambient = ambients.get(i);
			String ambientdetailfile = ambientid.replace("-", "") + ".jpg";
			String ambientminifile = ambientid.replace("-", "") + "mini.jpg";
			File ambientdetail = new File(ambientdetailsfolder, ambientdetailfile);
			File ambientmini = new File(ambientdetailsfolder, ambientminifile);
			Drawable ambientpicturetemp = null, ambientminipic = null;
			try {
				ambientminipic = Drawable.createFromStream(new FileInputStream(ambientmini), "src");
				ambientpicturetemp = Drawable.createFromStream(new FileInputStream(ambientdetail), "src");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			final Drawable ambientpicture = ambientpicturetemp;
			// Ambient state (0: CURRENT, 1: READY, 2: CORRUPTED, 3: DOWNLOADING, 4: PURSACHED, 5: FREE/PRICE) 
			int tempAmbientState = 0;
			if ((this.ambient.name != null)&&(this.ambient.name.equalsIgnoreCase(ambientname))) tempAmbientState = 0;
			else if (Ambient.ambientDownloaded(ambientname)) {
				if (Activa.ambientsdownloading.contains(ambientname)) tempAmbientState = 3;
				else if (Ambient.getNumberOfFilesDownloaded(ambientname) == Ambient.getNumberOfFilesForDownloading(ambient.getUrlMobile())) tempAmbientState = 1;
				else tempAmbientState = 2;
			}
			else if (ambient.isPurchased()) tempAmbientState = 4;
			else tempAmbientState = 5;
			final int ambientstate = tempAmbientState;
			TableRow buttonLayout = new TableRow(Activa.myApp);
			buttonLayout.setOrientation(TableRow.HORIZONTAL);
			buttonLayout.setGravity(Gravity.CENTER_VERTICAL);
			buttonLayout.setLayoutParams(new TableLayout.LayoutParams(new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT)));		
			ImageView image = new ImageView(Activa.myApp);
			TextView text = new TextView(Activa.myApp);
			TextView price = new TextView(Activa.myApp);
			image.setBackgroundResource(R.drawable.iconbg);
			if (ambientminipic != null) image.setImageDrawable(ambientminipic);
			image.setLayoutParams(new android.widget.TableRow.LayoutParams(width/5, android.widget.TableRow.LayoutParams.WRAP_CONTENT));
			OnClickListener infolistener = new OnClickListener() {
				@Override
				public void onClick(View v) {
					showAmbientInfo(ambient, ambientpicture, ambientstate, currency, paypalAccount);
				}
			};
			OnClickListener loadlistener = new OnClickListener() {			
				@Override
				public void onClick(View v) {
					Runnable run = new Runnable() {
						@Override
						public void run() {
							handler.sendEmptyMessage(0);
							if (Activa.myMobileManager.updateUsedTheme(ambient)) {
								Activa.myMobileManager.user.setAmbient(ambientname);
								Activa.myMobileManager.saveUsers();
								loadAmbient();
								handler.sendEmptyMessage(1);
							}
							else handler.sendEmptyMessage(2);
						}
						private Handler handler = new Handler(){
							@Override
							public void handleMessage(Message msg) {
								ImageView animationFrame;
								AnimationDrawable animation;
								switch (msg.what) {
									case 0:
										Activa.myUIManager.loadInfoPopupWithoutExiting(Activa.myLanguageManager.MAIN_AMBIENTS_LOADING);
										animationFrame = (ImageView) Activa.myApp.findViewById(R.id.popupImage);
										animationFrame.setVisibility(View.VISIBLE);
										animationFrame.setBackgroundResource(R.drawable.loading);
										animation = (AnimationDrawable) animationFrame.getBackground();
										animation.start();
										break;
									case 1:
										animationFrame = (ImageView) Activa.myApp.findViewById(R.id.popupImage);
										animation = (AnimationDrawable) animationFrame.getBackground();
										animation.stop();
										animationFrame.setVisibility(View.GONE);
										Activa.myUIManager.removeInfoPopup();
										loadGeneratedMainScreen(false, false, false);
										break;
									case 2:
										animationFrame = (ImageView) Activa.myApp.findViewById(R.id.popupImage);
										animation = (AnimationDrawable) animationFrame.getBackground();
										animation.stop();
										animationFrame.setVisibility(View.GONE);
										Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.CONNECTION_FAILED);
										break;
								}
							}
						};
					};
					Thread thread = new Thread(run);
					thread.start();
				}
			};
			image.setOnClickListener(infolistener);
			image.setPadding(5, 5, 5, 5);
			text.setLayoutParams(new android.widget.TableRow.LayoutParams(width/2 , android.widget.TableRow.LayoutParams.WRAP_CONTENT));
			text.setPadding(5, 5, 5, 5);
			text.setText(ambientname);
			text.setTextSize((float) 20);
			text.setTextColor(Color.BLACK);
			text.setTypeface(Typeface.SANS_SERIF);
			price.setLayoutParams(new android.widget.TableRow.LayoutParams(width*7/20, android.widget.TableRow.LayoutParams.WRAP_CONTENT));
			price.setPadding(5, 5, 5, 5);
			price.setTextSize((float)12);
			switch (ambientstate) {
				case 0:
					price.setText(Activa.myLanguageManager.MAIN_AMBSTATE_CURRENT);
					break;
				case 1:
					price.setText(Activa.myLanguageManager.MAIN_AMBSTATE_READY);
					text.setOnClickListener(loadlistener);
					price.setOnClickListener(loadlistener);
					buttonLayout.setOnClickListener(loadlistener);
					break;
				case 2:
					price.setText(Activa.myLanguageManager.MAIN_AMBSTATE_CORRUPTED);
					text.setOnClickListener(infolistener);
					price.setOnClickListener(infolistener);
					buttonLayout.setOnClickListener(infolistener);
					break;
				case 3:
					price.setText(Activa.myLanguageManager.MAIN_AMBSTATE_DOWNLOADING);
					break;
				case 4:
					price.setText(Activa.myLanguageManager.MAIN_AMBSTATE_PURSACHED);
					text.setOnClickListener(infolistener);
					price.setOnClickListener(infolistener);
					buttonLayout.setOnClickListener(infolistener);
					break;
				case 5:
				default:
					text.setOnClickListener(infolistener);
					price.setOnClickListener(infolistener);
					buttonLayout.setOnClickListener(infolistener);
					if (ambients.get(i).getCost() > 0.0d) price.setText(String.format("%.2f", ambients.get(i).getCost()) + " " + currency);
					else price.setText(Activa.myLanguageManager.MAIN_AMBSTATE_FREE);
					break;
			}
			price.setTextColor(Color.BLACK);
			price.setTypeface(Typeface.DEFAULT_BOLD);
			buttonLayout.addView(image);
			buttonLayout.addView(text);
			buttonLayout.addView(price);
			separator = new View(Activa.myApp);
			separatorLayout = new LayoutParams(android.view.ViewGroup.LayoutParams.FILL_PARENT, 1);
			separator.setLayoutParams(separatorLayout);
			separator.setBackgroundColor(Color.BLACK);
			ambientlisting.addView(buttonLayout);
			ambientlisting.addView(separator);
		}
		TableRow buttonLayout = new TableRow(Activa.myApp);
		buttonLayout.setOrientation(TableRow.HORIZONTAL);
		buttonLayout.setGravity(Gravity.CENTER_VERTICAL);
		buttonLayout.setLayoutParams(new TableLayout.LayoutParams(new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT)));		
		ImageView image = new ImageView(Activa.myApp);
		image.setPadding(5, 5, 5, 5);
		image.setBackgroundResource(R.drawable.iconbg);
		image.setLayoutParams(new android.widget.TableRow.LayoutParams(width/5, android.widget.TableRow.LayoutParams.WRAP_CONTENT));
		image.setImageResource(R.drawable.patscenario);
		TextView text = new TextView(Activa.myApp);
		text.setLayoutParams(new android.widget.TableRow.LayoutParams(width*4/5, android.widget.TableRow.LayoutParams.WRAP_CONTENT));
		text.setPadding(5, 5, 5, 5);
		text.setText(Activa.myLanguageManager.MAIN_DEFAULT_ENVIRONMENT_LOAD);
		text.setTextSize((float) 20);
		text.setTextColor(Color.BLACK);
		text.setTypeface(Typeface.SANS_SERIF);
		text.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Runnable run = new Runnable() {
					@Override
					public void run() {
						handler.sendEmptyMessage(0);
						if (Activa.myMobileManager.updateUsedTheme(null)) {
							Activa.myMobileManager.user.setAmbient(null);
							Activa.myMobileManager.saveUsers();
							loadDefaultAmbient();
							handler.sendEmptyMessage(1);
						}
						else handler.sendEmptyMessage(2);
					}
					private Handler handler = new Handler(){
						@Override
						public void handleMessage(Message msg) {
							ImageView animationFrame;
							AnimationDrawable animation;
							switch (msg.what) {
								case 0:
									Activa.myUIManager.loadInfoPopupWithoutExiting(Activa.myLanguageManager.MAIN_AMBIENTS_LOADING);
									animationFrame = (ImageView) Activa.myApp.findViewById(R.id.popupImage);
									animationFrame.setVisibility(View.VISIBLE);
									animationFrame.setBackgroundResource(R.drawable.loading);
									animation = (AnimationDrawable) animationFrame.getBackground();
									animation.start();
									break;
								case 1:
									animationFrame = (ImageView) Activa.myApp.findViewById(R.id.popupImage);
									animation = (AnimationDrawable) animationFrame.getBackground();
									animation.stop();
									animationFrame.setVisibility(View.GONE);
									Activa.myUIManager.removeInfoPopup();
									loadGeneratedMainScreen(false, false, false);
									break;
								case 2:
									animationFrame = (ImageView) Activa.myApp.findViewById(R.id.popupImage);
									animation = (AnimationDrawable) animationFrame.getBackground();
									animation.stop();
									animationFrame.setVisibility(View.GONE);
									Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.CONNECTION_FAILED);
									break;
							}
						}
					};
				};
				Thread thread = new Thread(run);
				thread.start();
			}
		});
		buttonLayout.addView(image, 74, 58);
		buttonLayout.addView(text);
//		((LinearLayout)Activa.myApp.findViewById(R.id.control)).addView(buttonLayout);
		((ImageButton) Activa.myApp.findViewById(R.id.previous)).setVisibility(View.GONE);
//		((LinearLayout)Activa.myApp.findViewById(R.id.control)).addView(separator);
		separator = new View(Activa.myApp);
		separatorLayout = new LayoutParams(TableLayout.LayoutParams.FILL_PARENT, 1);
		separator.setLayoutParams(separatorLayout);
		separator.setBackgroundColor(Color.BLACK);
		ambientlisting.addView(buttonLayout);
		ambientlisting.addView(separator);
		((ImageButton) Activa.myApp.findViewById(R.id.plus)).setVisibility(View.GONE);		
		ImageButton back = (ImageButton) Activa.myApp.findViewById(R.id.previous);
		back.setVisibility(View.VISIBLE);
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				loadGeneratedSubenvironment(lastSubenv, true);
			}
		});
	}
	
	public void showAmbientInfo(final Theme ambient, Drawable drawable, int ambientstate, final String currency, final String paypalAcc) {
		// Ambient state (0: CURRENT, 1: READY, 2: CORRUPTED, 3: DOWNLOADING, 4: PURSACHED, 5: FREE/PRICE) 
		final String ambientname = ambient.getName();
		final String ambienturl = ambient.getUrlMobile();
		final double ambientprice = ambient.getCost();
		final String ambientextra = ambient.getFeatures();
		final String ambientdesc = ambient.getDescription();
		LayoutInflater inflater = (LayoutInflater) Activa.myApp.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		final View layout = inflater.inflate(R.layout.toastseeambient,
		                               (ViewGroup) Activa.myApp.findViewById(R.id.toastseeambientroot));
		LinearLayout container = (LinearLayout) layout.findViewById(R.id.container);
		TextView title = new TextView(Activa.myApp);
		title.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		title.setPadding(15, 5, 15, 10);
		title.setText(ambientname);
		title.setTextSize((float) 24);
		title.setTextColor(Color.WHITE);
		title.setTypeface(Typeface.SANS_SERIF);
		ImageView image = new ImageView(Activa.myApp);
		image.setBackgroundResource(R.drawable.iconbg);
		image.setLayoutParams(new LayoutParams(android.view.ViewGroup.LayoutParams.FILL_PARENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT));
		image.setPadding(15, 0, 15, 0);
		image.setScaleType(ScaleType.FIT_XY);
		if (drawable != null) image.setImageDrawable(drawable);
		TextView text = new TextView(Activa.myApp);
		text.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		text.setPadding(15, 5, 15, 10);
		text.setText(ambientname);
		text.setTextSize((float) 14);
		text.setTextColor(Color.WHITE);
		text.setTypeface(Typeface.SANS_SERIF);
		Spanned span = Html.fromHtml(
				"<b>" + Activa.myLanguageManager.MAIN_AMBIENTS_NAME + ": </b>" + ambientname + "<br/>" + 
				"<b>" + Activa.myLanguageManager.MAIN_AMBIENTS_PRICE + ": </b>" + ((ambientprice == 0.0d)?Activa.myLanguageManager.MAIN_AMBSTATE_FREE:(String.format("%.2f", ambientprice) + " " + currency)) + "<br/>" + 
				"<b>" + Activa.myLanguageManager.MAIN_AMBIENTS_FEATURES + ": </b>" + ambientextra + "<br/>" + 
				"<b>" + Activa.myLanguageManager.MAIN_AMBIENTS_DESC + ": </b>" + ambientdesc + "<br/><br/>"
			);
		text.setText(span);
		container.addView(title);
		container.addView(image);
		container.addView(text);
		Builder builder = new AlertDialog.Builder(Activa.myApp);
		builder.setView(layout);
		final AlertDialog alertDialog = builder.create();
		alertDialog.show();
		ImageButton ok;
		ImageButton no;
		switch (ambientstate) {
			case 0:
				((ImageButton) layout.findViewById(R.id.paypal)).setVisibility(View.GONE);
				((LinearLayout) layout.findViewById(R.id.download)).setVisibility(View.GONE);
				break;
			case 1:
				text.append(Activa.myLanguageManager.MAIN_PAYING_LOADING);
				((LinearLayout) layout.findViewById(R.id.download)).setVisibility(View.VISIBLE);
				ok = (ImageButton) layout.findViewById(R.id.ok);
				no = (ImageButton) layout.findViewById(R.id.no);
				ok.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						Runnable run = new Runnable() {
							@Override
							public void run() {
								handler.sendEmptyMessage(0);
								if (Activa.myMobileManager.updateUsedTheme(ambient)) {
									Activa.myMobileManager.user.setAmbient(ambientname);
									Activa.myMobileManager.saveUsers();
									loadAmbient();
									handler.sendEmptyMessage(1);
								}
								else handler.sendEmptyMessage(2);
							}
							private Handler handler = new Handler(){
								@Override
								public void handleMessage(Message msg) {
									ImageView animationFrame;
									AnimationDrawable animation;
									switch (msg.what) {
										case 0:
											Activa.myUIManager.loadInfoPopupWithoutExiting(Activa.myLanguageManager.MAIN_AMBIENTS_LOADING);
											animationFrame = (ImageView) Activa.myApp.findViewById(R.id.popupImage);
											animationFrame.setVisibility(View.VISIBLE);
											animationFrame.setBackgroundResource(R.drawable.loading);
											animation = (AnimationDrawable) animationFrame.getBackground();
											animation.start();
											break;
										case 1:
											animationFrame = (ImageView) Activa.myApp.findViewById(R.id.popupImage);
											animation = (AnimationDrawable) animationFrame.getBackground();
											animation.stop();
											animationFrame.setVisibility(View.GONE);
											Activa.myUIManager.removeInfoPopup();
											loadGeneratedMainScreen(false, false, false);
											break;
										case 2:
											animationFrame = (ImageView) Activa.myApp.findViewById(R.id.popupImage);
											animation = (AnimationDrawable) animationFrame.getBackground();
											animation.stop();
											animationFrame.setVisibility(View.GONE);
											Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.CONNECTION_FAILED);
											break;
									}
								}
							};
						};
						Thread thread = new Thread(run);
						thread.start();
					}
				});
				no.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						alertDialog.cancel();
					}
				});
				break;
			case 2:
				text.append(Activa.myLanguageManager.MAIN_PAYING_PURSACHED);
				((LinearLayout) layout.findViewById(R.id.download)).setVisibility(View.VISIBLE);
				ok = (ImageButton) layout.findViewById(R.id.ok);
				no = (ImageButton) layout.findViewById(R.id.no);
				ok.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						HashSet<String> files = Ambient.getFilesForDownloading(ambienturl);
						DownloadFiles run = new DownloadFiles(ambientname, ambienturl, files);
						Thread thr = new Thread(run);
						thr.start();
						alertDialog.cancel();
						showAmbientsForLoading();
						loadInfoPopup(Activa.myLanguageManager.MAIN_PAYING_DOWNLOADING);
					}
				});
				no.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						alertDialog.cancel();
					}
				});
				break;
			case 3:
				break;
			case 4:
				text.append(Activa.myLanguageManager.MAIN_PAYING_PURSACHED);
				((LinearLayout) layout.findViewById(R.id.download)).setVisibility(View.VISIBLE);
				ok = (ImageButton) layout.findViewById(R.id.ok);
				no = (ImageButton) layout.findViewById(R.id.no);
				ok.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						HashSet<String> files = Ambient.getFilesForDownloading(ambienturl);
						DownloadFiles run = new DownloadFiles(ambientname, ambienturl, files);
						Thread thr = new Thread(run);
						thr.start();
						alertDialog.cancel();
						showAmbientsForLoading();
						loadInfoPopup(Activa.myLanguageManager.MAIN_PAYING_DOWNLOADING);
					}
				});
				no.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						alertDialog.cancel();
					}
				});
				break;
			case 5:
			default:
				if (ambientprice > 0.0d) {
					text.append(String.format(Activa.myLanguageManager.MAIN_PAYING_MESSAGE, (ambientprice + " " + currency)));
					ImageButton paypal = (ImageButton) layout.findViewById(R.id.paypal);
					paypal.setVisibility(View.VISIBLE);
					paypal.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							alertDialog.cancel();
							final ProgressDialog dialog = ProgressDialog.show(Activa.myApp, "PayPal", "Loading Paypal", true);
							dialog.show();
							Runnable run =  new Runnable() {
								@Override
								public void run() {
									PayPal myPayPal = PayPal.getInstance();
							        if (myPayPal == null) myPayPal = PayPal.initWithAppID(Activa.myApp, "APP-1XW38584E49695057", PayPal.ENV_LIVE);
							        PayPalPayment payment = new PayPalPayment();
							        payment.setSubtotal(new BigDecimal(ambientprice));
									payment.setCurrencyType(currency);
									payment.setRecipient(paypalAcc);
									payment.setPaymentType(PayPal.PAYMENT_TYPE_GOODS);
									payment.setDescription(ambientname + " for user " + Activa.myMobileManager.userForServices.getUsername());
//									payment.setIpnUrl(arg0);
//									PayPalInvoiceData invoicedata = new PayPalInvoiceData();
//									PayPalInvoiceItem item = new PayPalInvoiceItem();
//									item.setID(arg0);
//									ArrayList<PayPalInvoiceItem> invoiceitems = new ArrayList<PayPalInvoiceItem>();
//									invoiceitems.add(item);
//									invoicedata.setInvoiceItems(invoiceitems);
									Intent checkoutIntent = myPayPal.checkout(payment, Activa.myApp);
									Activa.myUIManager.ambientname = ambientname;
									Activa.myUIManager.ambientprocessing = ambient;
									Activa.myApp.startActivityForResult(checkoutIntent, 100);
									dialog.cancel();
								}
							};
							Thread thread = new Thread(run);
							thread.start();
						}
					});
				}
				else {
					text.append(Activa.myLanguageManager.MAIN_PAYING_FREE);
					((LinearLayout) layout.findViewById(R.id.download)).setVisibility(View.VISIBLE);
					ok = (ImageButton) layout.findViewById(R.id.ok);
					no = (ImageButton) layout.findViewById(R.id.no);
					ok.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
	    		   			try {
								Activa.myProtocolManager.pursacheAmbients(ambientprocessing);
							} catch (NotUpdatedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							HashSet<String> files = Ambient.getFilesForDownloading(ambienturl);
							DownloadFiles run = new DownloadFiles(ambientname, ambienturl, files);
							Thread thr = new Thread(run);
							thr.start();
							alertDialog.cancel();
							showAmbientsForLoading();
							loadInfoPopup(Activa.myLanguageManager.MAIN_PAYING_DOWNLOADING);
						}
					});
					no.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							alertDialog.cancel();
						}
					});
				}
				break;
		}
	}

	public void showExternalLinks() {
		this.state = UI_STATE_PROGRAM;
        final PackageManager manager = Activa.myApp.getPackageManager();
		Activa.myApp.setContentView(R.layout.list);
		((TextView) Activa.myApp.findViewById(R.id.startText)).append(Activa.myLanguageManager.MAIN_SELECT_LINKEDPROG);
		TableLayout ambientlisting = (TableLayout)Activa.myApp.findViewById(R.id.listing);
		Enumeration<ExternalApp> programs = Activa.myExteriorManager.externalApplicationsLinked.elements();
		while (programs.hasMoreElements()) {
			final ExternalApp program = programs.nextElement();
			List<ResolveInfo> list =
	            manager.queryIntentActivities(program.intent,
	            PackageManager.MATCH_DEFAULT_ONLY);
			if (list.size() <= 0) continue;
			TableRow buttonLayout = new TableRow(Activa.myApp);
			buttonLayout.setOrientation(TableRow.HORIZONTAL);
			buttonLayout.setGravity(Gravity.CENTER_VERTICAL);
			buttonLayout.setLayoutParams(new TableLayout.LayoutParams(new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT)));		
			TextView text = new TextView(Activa.myApp);
			text.setLayoutParams(new android.widget.TableRow.LayoutParams(250, android.widget.TableRow.LayoutParams.WRAP_CONTENT));
			text.append(program.name);
			text.setTextSize((float) 20);
			text.setTextColor(Color.BLACK);
			text.setTypeface(Typeface.SANS_SERIF);
			ImageView image = new ImageView(Activa.myApp);
			try {
				image.setImageDrawable(manager.getActivityIcon(new ComponentName(program.packageName, program.activityName)));
			} catch (NameNotFoundException e) {
				e.printStackTrace();
			}
			OnClickListener listener = new OnClickListener() {			
				@Override
				public void onClick(View v) {
					List<ResolveInfo> list =
			            manager.queryIntentActivities(program.intent,
			            PackageManager.MATCH_DEFAULT_ONLY);
					if (list.size() > 0) Activa.myApp.startActivity(program.intent);
					else Activa.myUIManager.showExternalLinks();
				}
			};
			image.setPadding(5, 5, 5, 5);
			image.setOnClickListener(listener);
			image.setScaleType(ScaleType.FIT_XY);
			text.setOnClickListener(listener);
			buttonLayout.addView(image, 64, 64);
			buttonLayout.addView(text);
			buttonLayout.setOnClickListener(listener);
			image.setOnClickListener(listener);
			text.setOnClickListener(listener);
			ambientlisting.addView(buttonLayout);
		}
		ImageButton back = (ImageButton) Activa.myApp.findViewById(R.id.previous);
		back.setVisibility(View.VISIBLE);
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Activa.myUIManager.loadGeneratedSubenvironment(Activa.myUIManager.lastSubenv, true);
			}
		});
		ImageView plus = (ImageView) Activa.myApp.findViewById(R.id.animation);
		plus.setImageResource(R.drawable.plus);
		plus.setVisibility(View.VISIBLE);
		plus.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showExternalProgramsToLink();
			}
		});
		ImageButton delete = (ImageButton) Activa.myApp.findViewById(R.id.help);
		delete.setImageResource(R.drawable.delete);
		delete.setVisibility(View.VISIBLE);
		delete.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				final CharSequence[] items = new CharSequence[Activa.myExteriorManager.externalApplicationsLinked.size()];
				final Integer[] proglist = new Integer[Activa.myExteriorManager.externalApplicationsLinked.size()];
				final ArrayList<Integer> progsToRemove = new ArrayList<Integer>();
				Enumeration<Integer> progenum = Activa.myExteriorManager.externalApplicationsLinked.keys();
				int i = 0;
				while (progenum.hasMoreElements()) {
					int progid = progenum.nextElement();
					items[i] = Activa.myExteriorManager.externalApplicationsLinked.get(progid).name;
					proglist[i] = progid;
					i++;
				}
				AlertDialog.Builder builder = new AlertDialog.Builder(Activa.myApp);
				builder.setTitle(Activa.myLanguageManager.NOTIFICATION_PROGRAMS_REMOVE);
				builder.setMultiChoiceItems(items, null, new OnMultiChoiceClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which, boolean isChecked) {
						if (isChecked) progsToRemove.add(proglist[which]);
						else progsToRemove.remove(proglist[which]);
					}
				});
				builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {
			        	   for (int i = 0; i < progsToRemove.size(); i++) {
			        		   Activa.myExteriorManager.externalApplicationsLinked.remove(progsToRemove.get(i));
			        	   }
			               dialog.cancel();
			               Activa.myUIManager.loadNewsList(true);
			           }
			    });
				AlertDialog alert = builder.create();
				alert.show();
			}
		});
	}
	 
	public void showExternalProgramsToLink() {
        final PackageManager manager = Activa.myApp.getPackageManager();
		Activa.myApp.setContentView(R.layout.ambientlist);
		((TextView) Activa.myApp.findViewById(R.id.startText)).append(Activa.myLanguageManager.MAIN_SELECT_EXTERNALPROG);
		TableLayout ambientlisting = (TableLayout)Activa.myApp.findViewById(R.id.listing);
		List<ResolveInfo> programs = Activa.myExteriorManager.getExternalAppsList();
		for (int i = 0; i < programs.size(); i++) {
			final ResolveInfo infoProgram = programs.get(i);
			TableRow buttonLayout = new TableRow(Activa.myApp);
			buttonLayout.setOrientation(TableRow.HORIZONTAL);
			buttonLayout.setGravity(Gravity.CENTER_VERTICAL);
			buttonLayout.setLayoutParams(new TableLayout.LayoutParams(new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT)));		
			TextView text = new TextView(Activa.myApp);
			text.setLayoutParams(new android.widget.TableRow.LayoutParams(250, android.widget.TableRow.LayoutParams.WRAP_CONTENT));
			text.append(infoProgram.loadLabel(manager).toString());
			text.setTextSize((float) 20);
			text.setTextColor(Color.BLACK);
			text.setTypeface(Typeface.SANS_SERIF);
			ImageView image = new ImageView(Activa.myApp);
			image.setImageDrawable(infoProgram.loadIcon(manager));
			OnClickListener listener = new OnClickListener() {			
				@Override
				public void onClick(View v) {
					Activa.myExteriorManager.addProgram(infoProgram, manager);
			    	loadInfoPopup(Activa.myLanguageManager.MAIN_SELECT_PROGLINKED);
				}
			};
			image.setPadding(5, 5, 5, 5);
			image.setOnClickListener(listener);
			image.setScaleType(ScaleType.FIT_XY);
			text.setOnClickListener(listener);
			buttonLayout.addView(image, 64, 64);
			buttonLayout.addView(text);
			buttonLayout.setOnClickListener(listener);
			image.setOnClickListener(listener);
			text.setOnClickListener(listener);
			ambientlisting.addView(buttonLayout);
		}
		ImageButton back = (ImageButton) Activa.myApp.findViewById(R.id.previous);
		back.setVisibility(View.VISIBLE);
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showExternalLinks();
			}
		});
		ImageButton plus = (ImageButton) Activa.myApp.findViewById(R.id.plus);
		plus.setImageResource(R.drawable.market);
		plus.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_MAIN);
		        intent.addCategory(Intent.CATEGORY_LAUNCHER);
		        intent.setComponent(new ComponentName("com.android.vending", "com.android.vending.AssetBrowserActivity"));
		        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
				Activa.myApp.startActivity(intent);				
			}
		});
	}
	
	public void showOptions() {
		this.state = UI_STATE_OPTIONS;
		int realwidth = Activa.myApp.getResources().getDisplayMetrics().widthPixels;
		Activa.myApp.setContentView(R.layout.list);
		((TextView) Activa.myApp.findViewById(R.id.startText)).setText(Activa.myLanguageManager.OPTIONS_TITLE);
		TableLayout listing = (TableLayout)Activa.myApp.findViewById(R.id.listing);
		
		TableRow buttonLayout = new TableRow(Activa.myApp);
		buttonLayout.setOrientation(TableRow.HORIZONTAL);
		buttonLayout.setGravity(Gravity.CENTER_VERTICAL);
		buttonLayout.setLayoutParams(new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));			
		ImageButton button = new ImageButton(Activa.myApp);
		button.setBackgroundResource(R.drawable.iconbg);
		button.setImageResource(R.drawable.synchronization);
		TextView text = new TextView(Activa.myApp);
		text.setLayoutParams(new android.widget.TableRow.LayoutParams(realwidth - 60, android.widget.TableRow.LayoutParams.WRAP_CONTENT));
		text.setPadding(10, 0, 10, 0);
		text.append(Activa.myLanguageManager.OPTIONS_ACCOUNTS);
		text.setTextSize((float) 20);
		text.setTextColor(Color.BLACK);
		text.setTypeface(Typeface.SANS_SERIF);	
		OnClickListener listener = new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (!Activa.myMobileManager.identified) {
					loadInfoPopupLong(Activa.myLanguageManager.OPTIONS_USERNOTCONNECTED);
					return;
				}
				showAccounts();
//				doAccountsSynchronizacion();
			}
		};
		buttonLayout.setOnClickListener(listener);
		button.setOnClickListener(listener);
		text.setOnClickListener(listener);
		buttonLayout.addView(button);
		buttonLayout.addView(text);
		listing.addView(buttonLayout);
		View separator = new View(Activa.myApp);
		separator.setLayoutParams(new LayoutParams(android.view.ViewGroup.LayoutParams.FILL_PARENT, 1));
		separator.setBackgroundColor(Color.BLACK);
		listing.addView(separator);
		
		buttonLayout = new TableRow(Activa.myApp);
		buttonLayout.setOrientation(TableRow.HORIZONTAL);
		buttonLayout.setGravity(Gravity.CENTER_VERTICAL);
		buttonLayout.setLayoutParams(new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));			
		button = new ImageButton(Activa.myApp);
		button.setBackgroundResource(R.drawable.iconbg);
		button.setImageResource(R.drawable.profile);
		text = new TextView(Activa.myApp);
		text.setLayoutParams(new android.widget.TableRow.LayoutParams(realwidth - 60, android.widget.TableRow.LayoutParams.WRAP_CONTENT));
		text.setPadding(10, 0, 10, 0);
		text.append(Activa.myLanguageManager.OPTIONS_PROFILE);
		text.setTextSize((float) 20);
		text.setTextColor(Color.BLACK);
		text.setTypeface(Typeface.SANS_SERIF);	
		listener = new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (!Activa.myMobileManager.identified) {
					loadInfoPopupLong(Activa.myLanguageManager.OPTIONS_USERNOTCONNECTED);
					return;
				}
				loadDataScreenForChanging();
			}
		};
		buttonLayout.setOnClickListener(listener);
		button.setOnClickListener(listener);
		text.setOnClickListener(listener);
		buttonLayout.addView(button, 60, 60);
		buttonLayout.addView(text);
		listing.addView(buttonLayout);
		separator = new View(Activa.myApp);
		separator.setLayoutParams(new LayoutParams(android.view.ViewGroup.LayoutParams.FILL_PARENT, 1));
		separator.setBackgroundColor(Color.BLACK);
		listing.addView(separator);
		
		buttonLayout = new TableRow(Activa.myApp);
		buttonLayout.setOrientation(TableRow.HORIZONTAL);
		buttonLayout.setGravity(Gravity.CENTER_VERTICAL);
		buttonLayout.setLayoutParams(new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));			
		button = new ImageButton(Activa.myApp);
		button.setBackgroundResource(R.drawable.iconbg);
		button.setImageResource(R.drawable.profile);
		text = new TextView(Activa.myApp);
		text.setLayoutParams(new android.widget.TableRow.LayoutParams(realwidth - 60, android.widget.TableRow.LayoutParams.WRAP_CONTENT));
		text.setPadding(10, 0, 10, 0);
		text.append(Activa.myLanguageManager.OPTIONS_WEB);
		text.setTextSize((float) 20);
		text.setTextColor(Color.BLACK);
		text.setTypeface(Typeface.SANS_SERIF);	
		listener = new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (!Activa.myMobileManager.identified) {
					loadInfoPopupLong(Activa.myLanguageManager.OPTIONS_USERNOTCONNECTED);
					return;
				}
				//TODO
				loadAdditionalDataScreen(true);
			}
		};
		buttonLayout.setOnClickListener(listener);
		button.setOnClickListener(listener);
		text.setOnClickListener(listener);
		buttonLayout.addView(button, 60, 60);
		buttonLayout.addView(text);
		listing.addView(buttonLayout);
		separator = new View(Activa.myApp);
		separator.setLayoutParams(new LayoutParams(android.view.ViewGroup.LayoutParams.FILL_PARENT, 1));
		separator.setBackgroundColor(Color.BLACK);
		listing.addView(separator);
		
		buttonLayout = new TableRow(Activa.myApp);
		buttonLayout.setOrientation(TableRow.HORIZONTAL);
		buttonLayout.setGravity(Gravity.CENTER_VERTICAL);
		buttonLayout.setLayoutParams(new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));			
		button = new ImageButton(Activa.myApp);
		button.setBackgroundResource(R.drawable.iconbg);
		button.setImageResource(R.drawable.password);
		text = new TextView(Activa.myApp);
		text.setLayoutParams(new android.widget.TableRow.LayoutParams(realwidth - 60, android.widget.TableRow.LayoutParams.WRAP_CONTENT));
		text.setPadding(10, 0, 10, 0);
		text.append(Activa.myLanguageManager.OPTIONS_USERPASS);
		text.setTextSize((float) 20);
		text.setTextColor(Color.BLACK);
		text.setTypeface(Typeface.SANS_SERIF);	
		listener = new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (!Activa.myMobileManager.identified) {
					loadInfoPopupLong(Activa.myLanguageManager.OPTIONS_USERNOTCONNECTED);
					return;
				}
				loadChangePassword();
			}
		};
		buttonLayout.setOnClickListener(listener);
		button.setOnClickListener(listener);
		text.setOnClickListener(listener);
		buttonLayout.addView(button);
		buttonLayout.addView(text);
		listing.addView(buttonLayout);
		separator = new View(Activa.myApp);
		separator.setLayoutParams(new LayoutParams(android.view.ViewGroup.LayoutParams.FILL_PARENT, 1));
		separator.setBackgroundColor(Color.BLACK);
		listing.addView(separator);
		
		buttonLayout = new TableRow(Activa.myApp);
		buttonLayout.setOrientation(TableRow.HORIZONTAL);
		buttonLayout.setGravity(Gravity.CENTER_VERTICAL);
		buttonLayout.setLayoutParams(new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));			
		button = new ImageButton(Activa.myApp);
		button.setBackgroundResource(R.drawable.iconbg);
		button.setImageResource(R.drawable.password);
		text = new TextView(Activa.myApp);
		text.setLayoutParams(new android.widget.TableRow.LayoutParams(realwidth - 60, android.widget.TableRow.LayoutParams.WRAP_CONTENT));
		text.setPadding(10, 0, 10, 0);
		text.append(Activa.myLanguageManager.OPTIONS_DRAWPASS);
		text.setTextSize((float) 20);
		text.setTextColor(Color.BLACK);
		text.setTypeface(Typeface.SANS_SERIF);	
		listener = new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (!Activa.myMobileManager.identified) {
					loadInfoPopupLong(Activa.myLanguageManager.OPTIONS_USERNOTCONNECTED);
					return;
				}
				loadMatrixPasswordForChanging();
			}
		};
		buttonLayout.setOnClickListener(listener);
		button.setOnClickListener(listener);
		text.setOnClickListener(listener);
		buttonLayout.addView(button);
		buttonLayout.addView(text);
		listing.addView(buttonLayout);
		separator = new View(Activa.myApp);
		separator.setLayoutParams(new LayoutParams(android.view.ViewGroup.LayoutParams.FILL_PARENT, 1));
		separator.setBackgroundColor(Color.BLACK);
		listing.addView(separator);
		
		/*buttonLayout = new TableRow(Activa.myApp);
		buttonLayout.setOrientation(TableRow.HORIZONTAL);
		buttonLayout.setGravity(Gravity.CENTER_VERTICAL);
		buttonLayout.setLayoutParams(new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));			
		button = new ImageButton(Activa.myApp);
		button.setBackgroundResource(R.drawable.iconbg);
		button.setImageResource(R.drawable.animations);
		text = new TextView(Activa.myApp);
		text.setLayoutParams(new android.widget.TableRow.LayoutParams(realwidth - 60, android.widget.TableRow.LayoutParams.WRAP_CONTENT));
		text.setPadding(10, 0, 10, 0);
		text.append(Activa.myLanguageManager.OPTIONS_ANIMATION);
		text.setTextSize((float) 20);
		text.setTextColor(Color.BLACK);
		text.setTypeface(Typeface.SANS_SERIF);	
		listener = new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				final CharSequence[] items = {Activa.myLanguageManager.OPTIONS_NOANIMATION, "x0.5", "x1", "x2"};
				AlertDialog.Builder builder = new AlertDialog.Builder(Activa.myApp);
				builder.setTitle(Activa.myLanguageManager.OPTIONS_ANIMATION_EXPL);
				builder.setCancelable(true);
				builder.setItems(items, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						switch (which) {
						case 0:
							Activa.myMobileManager.user.animations = 0;
							Activa.myMobileManager.saveUsers();
							break;
						case 1:
							dialog.cancel();
							AlertDialog.Builder builder = new AlertDialog.Builder(Activa.myApp);
							builder.setMessage(Activa.myLanguageManager.OPTIONS_ANIMATION_ADVICE)
						       .setCancelable(false)
						       .setPositiveButton("OK", new DialogInterface.OnClickListener() {
						           public void onClick(DialogInterface dialog, int id) {
										Activa.myMobileManager.user.animations = 1;
										Activa.myMobileManager.saveUsers();
						                dialog.cancel();
						           }
						       })
						       .setNegativeButton("No", new DialogInterface.OnClickListener() {
						           public void onClick(DialogInterface dialog, int id) {
						                dialog.cancel();
						           }
						       });
							AlertDialog alert = builder.create();
							alert.show();
							break;
						case 2:
							dialog.cancel();
							AlertDialog.Builder builder2 = new AlertDialog.Builder(Activa.myApp);
							builder2.setMessage(Activa.myLanguageManager.OPTIONS_ANIMATION_ADVICE)
						       .setCancelable(false)
						       .setPositiveButton("OK", new DialogInterface.OnClickListener() {
						           public void onClick(DialogInterface dialog, int id) {
										Activa.myMobileManager.user.animations = 2;
										Activa.myMobileManager.saveUsers();
						                dialog.cancel();
						           }
						       })
						       .setNegativeButton("No", new DialogInterface.OnClickListener() {
						           public void onClick(DialogInterface dialog, int id) {
						                dialog.cancel();
						           }
						       });
							AlertDialog alert2 = builder2.create();
							alert2.show();
							break;
						case 3:
							dialog.cancel();
							AlertDialog.Builder builder3 = new AlertDialog.Builder(Activa.myApp);
							builder3.setMessage(Activa.myLanguageManager.OPTIONS_ANIMATION_ADVICE)
						       .setCancelable(false)
						       .setPositiveButton("OK", new DialogInterface.OnClickListener() {
						           public void onClick(DialogInterface dialog, int id) {
										Activa.myMobileManager.user.animations = 4;
										Activa.myMobileManager.saveUsers();
						                dialog.cancel();
						           }
						       })
						       .setNegativeButton("No", new DialogInterface.OnClickListener() {
						           public void onClick(DialogInterface dialog, int id) {
						                dialog.cancel();
						           }
						       });
							AlertDialog alert3 = builder3.create();
							alert3.show();
							break;
						default:
							break;
						}						
					}
				});
				AlertDialog alert = builder.create();
				alert.show();
			}
		};
		buttonLayout.setOnClickListener(listener);
		button.setOnClickListener(listener);
		text.setOnClickListener(listener);
		buttonLayout.addView(button, 60, 60);
		buttonLayout.addView(text);
		listing.addView(buttonLayout);
		separator = new View(Activa.myApp);
		separator.setLayoutParams(new LayoutParams(android.view.ViewGroup.LayoutParams.FILL_PARENT, 1));
		separator.setBackgroundColor(Color.BLACK);
		listing.addView(separator);*/

		ImageButton back = (ImageButton) Activa.myApp.findViewById(R.id.previous);
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Activa.myUIManager.loadGeneratedSubenvironment(Activa.myUIManager.lastSubenv, true);
			}
		});
	}
	
	public void doAccountsSynchronizacion() {
		AccountManager accmng = AccountManager.get(Activa.myApp);
		Account[] initialAccounts = accmng.getAccounts();
		CharSequence[] itemsextra = new CharSequence[initialAccounts.length];
		final Account[] accounts = new Account[initialAccounts.length];
		final Hashtable<Integer, Account> accountsToAdd = new Hashtable<Integer, Account>();
		final Hashtable<Integer, com.o2hlink.activ8.client.entity.Account> accountsToSend = new Hashtable<Integer, com.o2hlink.activ8.client.entity.Account>();
		final Hashtable<Integer, String> usernameToSend = new Hashtable<Integer, String>();
		final Hashtable<Integer, String> passwordToSend = new Hashtable<Integer, String>();
		int j = 0;
		for (int i = 0; i < initialAccounts.length; i++) {
			if (initialAccounts[i].type.contains("google")) {
				itemsextra[j] = initialAccounts[i].name + " - GMail";
				accounts[j] = initialAccounts[i];
				j++;
			}
			else if (initialAccounts[i].type.contains("skype")) {
				itemsextra[j] = initialAccounts[i].name + " - Skype";
				accounts[j] = initialAccounts[i];
				j++;
			}
		}
		final CharSequence[] items = new CharSequence[j];
		for (int i = 0; i < items.length; i++) {
			items[i] = itemsextra[i];
		}
		AlertDialog.Builder builder = new AlertDialog.Builder(Activa.myApp);
		builder.setTitle(Activa.myLanguageManager.OPTIONS_ACCOUNT_SELECT);
		builder.setMultiChoiceItems(items, null, new OnMultiChoiceClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which, boolean isChecked) {
				if (isChecked) accountsToAdd.put(which, accounts[which]);
				else accountsToAdd.remove(which);
			}
		});
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
	           public void onClick(final DialogInterface dialog, int id) {
	        	   	Enumeration<Integer> accountstoaddkeys = accountsToAdd.keys();
	        	   	LayoutInflater inflater = (LayoutInflater) Activa.myApp.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		   			final View layout = inflater.inflate(R.layout.toastpasswords,
		   					(ViewGroup) Activa.myApp.findViewById(R.id.toastpasswordsroot));
		   			LinearLayout passwords = (LinearLayout) layout.findViewById(R.id.passwords);
		   			TextView text = (TextView) layout.findViewById(R.id.passwordsexpl);
		   			text.setText(Activa.myLanguageManager.OPTIONS_ACCOUNT_GETPASSWD);
		   			final Hashtable<Integer, EditText> edittexts = new Hashtable<Integer, EditText>();
		   			while (accountstoaddkeys.hasMoreElements()) {
		   				int key = accountstoaddkeys.nextElement();
		   				LinearLayout row = new LinearLayout(Activa.myApp);
		   				row.setLayoutParams(new android.view.ViewGroup.LayoutParams(android.view.ViewGroup.LayoutParams.FILL_PARENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT));
		   				row.setOrientation(LinearLayout.HORIZONTAL);
		   				TextView expl = new TextView(Activa.myApp);
		   				expl.setLayoutParams(new android.view.ViewGroup.LayoutParams(150, android.view.ViewGroup.LayoutParams.WRAP_CONTENT));
		   				expl.setText(items[key] + ": ");
		   				expl.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
		   				row.addView(expl);
		   				EditText pass = new EditText(Activa.myApp);
		   				pass.setLayoutParams(new android.view.ViewGroup.LayoutParams(android.view.ViewGroup.LayoutParams.FILL_PARENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT));
		   				pass.setId(key);
		   				pass.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
		   				row.addView(pass);
		   				passwords.addView(row);
		   				edittexts.put(key, pass);
		   			}
		   			ImageButton image = (ImageButton) layout.findViewById(R.id.passok);
		   			Builder builder = new AlertDialog.Builder(Activa.myApp);
		   			builder.setView(layout);
		   			final AlertDialog alertDialog = builder.create();
		   			alertDialog.show();
		   			image.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							alertDialog.cancel();
				            final ProgressDialog progdialog = ProgressDialog.show(Activa.myApp, Activa.myLanguageManager.OPTIONS_ACCOUNT_SYNCHRONIZE, 
										Activa.myLanguageManager.OPTIONS_ACCOUNT_SYNCHRONIZING, true);
				            progdialog.show();
							Runnable run =  new Runnable() {
									@Override
									public void run() {
						        	   	Enumeration<Integer> accountstoaddkeys = edittexts.keys();
										while (accountstoaddkeys.hasMoreElements()) {
											Integer accountkey = (Integer) accountstoaddkeys.nextElement();
											Account accountToAdd = accountsToAdd.get(accountkey);
											com.o2hlink.activ8.client.entity.Account account = new com.o2hlink.activ8.client.entity.Account();
											if (accountToAdd.type.contains("google")) {
												account.setType(AccountType.GMAIL);
											}
											else if (accountToAdd.type.contains("skype")) {
												account.setType(AccountType.SKYPE);
											}
											accountsToSend.put(accountkey, account);
											usernameToSend.put(accountkey, accountToAdd.name);
											passwordToSend.put(accountkey, edittexts.get(accountkey).getText().toString());
										}
										Activa.myMessageManager.AddAccounts(accountsToSend, usernameToSend, passwordToSend);
										progdialog.cancel();
										handler.sendEmptyMessage(0);
									}
									private Handler handler = new Handler() {
										@Override
										public void handleMessage(Message msg) {
											switch (msg.what) {
												default:
													showAccountsLoaded();
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
	    });
		AlertDialog alert = builder.create();
		alert.show();
	}
	
	public void doAccountSynchronization(final boolean atinit, final int selection) {
		LayoutInflater inflater = (LayoutInflater) Activa.myApp.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		final View layout = inflater.inflate(R.layout.toastaccounts,
				(ViewGroup) Activa.myApp.findViewById(R.id.toastaccountsroot));
		TextView title = (TextView) layout.findViewById(R.id.addtitle);
		title.setText(Activa.myLanguageManager.OPTIONS_ACCOUNT_SELECT);
		final LinearLayout container = (LinearLayout) layout.findViewById(R.id.container);
		final Spinner accounttype = (Spinner) layout.findViewById(R.id.spinner);
		ArrayList<String> strings = new ArrayList<String>();
		strings.add("Gmail");
		strings.add("Google apps");
		strings.add("LinkedIn");
		strings.add("Skype");
		strings.add("Twitter");
		strings.add("Youtube");
		strings.add("Windows Live");
		strings.add("Facebook");
		strings.add("Picasa");
//		strings.add("Last.fm");
	    ArrayAdapter<String> adapter = new ArrayAdapter<String>(Activa.myApp, android.R.layout.simple_spinner_item, strings);
	    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    accounttype.setAdapter(adapter);		
	    Builder builder = new AlertDialog.Builder(Activa.myApp);
		builder.setView(layout);
		final AlertDialog alertDialog = builder.create();
		alertDialog.show();
		accounttype.setOnItemSelectedListener(new OnItemSelectedListener() {
			ImageButton ok;
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				switch (position) {
				case 0: //GMAIL
					container.removeAllViews();
	   				ok = ((ImageButton) layout.findViewById(R.id.add));
	   				ok.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View arg0) {
							synchronizeDomain(AccountType.GMAIL, alertDialog, atinit, selection);
						}
					});
	   				addExistingAccounts(container, AccountType.GMAIL, R.drawable.gmail, alertDialog, atinit, selection);
					break;
				case 1: //GOOGLEAPPS
					container.removeAllViews();
	   				ok = ((ImageButton) layout.findViewById(R.id.add));
	   				ok.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View arg0) {
							synchronizeDomain(AccountType.GOOGLE, alertDialog, atinit, selection);
						}
					});
	   				addExistingAccounts(container, AccountType.GOOGLE, R.drawable.googleapps, alertDialog, atinit, selection);
					break;
				case 2: //LINKEDID
					container.removeAllViews();
	   				ok = ((ImageButton) layout.findViewById(R.id.add));
	   				ok.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View arg0) {
							Hashtable<Integer, String> parameters = new Hashtable<Integer, String>();
							synchronizeAccount(layout, AccountType.LINKEDIN, parameters, atinit, selection, alertDialog);
						}
					});
	   				addExistingAccounts(container, AccountType.LINKEDIN, R.drawable.linkedin, alertDialog, atinit, selection);
					break;
				case 3: //SKYPE
					container.removeAllViews();
	   				ok = ((ImageButton) layout.findViewById(R.id.add));
	   				ok.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View arg0) {
							synchronizeEmail(AccountType.SKYPE, alertDialog, atinit, selection);
						}
					});
	   				addExistingAccounts(container, AccountType.SKYPE, R.drawable.skype, alertDialog, atinit, selection);
	   				break;
				case 4: //TWITTER
					container.removeAllViews();
	   				ok = ((ImageButton) layout.findViewById(R.id.add));
	   				ok.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View arg0) {
							Hashtable<Integer, String> parameters = new Hashtable<Integer, String>();
							synchronizeAccount(layout, AccountType.TWITTER, parameters, atinit, selection, alertDialog);
						}
					});
	   				addExistingAccounts(container, AccountType.TWITTER, R.drawable.twitter, alertDialog, atinit, selection);
					break;
				case 5: //YOUTUBE
					container.removeAllViews();
	   				ok = ((ImageButton) layout.findViewById(R.id.add));
	   				ok.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View arg0) {
							Hashtable<Integer, String> parameters = new Hashtable<Integer, String>();
							synchronizeAccount(layout, AccountType.YOUTUBE, parameters, atinit, selection, alertDialog);
						}
					});
	   				addExistingAccounts(container, AccountType.YOUTUBE, R.drawable.youtube, alertDialog, atinit, selection);
					break;
				case 6: //WINDOWSLIVE
					container.removeAllViews();
	   				ok = ((ImageButton) layout.findViewById(R.id.add));
	   				ok.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View arg0) {
							synchronizeEmail(AccountType.WINDOWS_LIVE, alertDialog, atinit, selection);
						}
					});
	   				addExistingAccounts(container, AccountType.WINDOWS_LIVE, R.drawable.winlive, alertDialog, atinit, selection);
					break;
				case 7: //FACEBOOK
					container.removeAllViews();
	   				ok = ((ImageButton) layout.findViewById(R.id.add));
	   				ok.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View arg0) {
							Hashtable<Integer, String> parameters = new Hashtable<Integer, String>();
							synchronizeAccount(layout, AccountType.FACEBOOK, parameters, atinit, selection, alertDialog);
						}
					});
	   				addExistingAccounts(container, AccountType.FACEBOOK, R.drawable.facebook, alertDialog, atinit, selection);
					break;
				case 8: //PICASA
					container.removeAllViews();
	   				ok = ((ImageButton) layout.findViewById(R.id.add));
	   				ok.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View arg0) {
							Hashtable<Integer, String> parameters = new Hashtable<Integer, String>();
							synchronizeAccount(layout, AccountType.PICASA, parameters, atinit, selection, alertDialog);
						}
					});
	   				addExistingAccounts(container, AccountType.PICASA, R.drawable.picasa, alertDialog, atinit, selection);
					break;
				/*case 9: //LASTFM
					container.removeAllViews();
	   				ok = ((ImageButton) layout.findViewById(R.id.add));
	   				ok.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View arg0) {
							Hashtable<Integer, String> parameters = new Hashtable<Integer, String>();
							synchronizeAccount(layout, AccountType.LASTFM, parameters, atinit, alertDialog);
						}
					});
	   				addExistingAccounts(container, AccountType.LASTFM, R.drawable.lastfm, alertDialog, atinit, selection);
					break;*/
				default:
					container.removeAllViews();
					break;
			}
						
			}
			@Override
			public void onNothingSelected(AdapterView<?> parent) {}
		});
		ImageButton quit = ((ImageButton) layout.findViewById(R.id.ok));
		quit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				alertDialog.cancel();
			}
		});
		if (atinit) {
//			alertDialog.setOnCancelListener(new OnCancelListener() {
//				@Override
//				public void onCancel(DialogInterface arg0) {
//					if (Activa.myMobileManager.user.getAmbient() == null) Activa.myUIManager.loadDefaultAmbient();
//	        		else if (Activa.myMobileManager.user.getAmbient().equalsIgnoreCase("null")) Activa.myUIManager.loadDefaultAmbient();
//	        		Activa.myUIManager.loadGeneratedMainScreen(true, false);
//				}
//			});
		}
		accounttype.setSelection(selection);
	}
	
	public void addExistingAccounts(LinearLayout container, AccountType type, int iconId, final AlertDialog alertDialog2, final boolean atinit, final int selection) {
		boolean thereare = false;
		int screenwidth = Activa.myApp.getResources().getDisplayMetrics().widthPixels;
		Enumeration<com.o2hlink.activ8.client.entity.Account> accounts = Activa.myMessageManager.accountList.elements();
		TableRow buttonLayout = new TableRow(Activa.myApp);
		buttonLayout.setOrientation(TableRow.HORIZONTAL);
		buttonLayout.setGravity(Gravity.CENTER_VERTICAL);
		buttonLayout.setLayoutParams(new TableLayout.LayoutParams(new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT)));		
		TextView text = new TextView(Activa.myApp);
		text.setLayoutParams(new android.widget.TableRow.LayoutParams(android.widget.TableRow.LayoutParams.FILL_PARENT, android.widget.TableRow.LayoutParams.WRAP_CONTENT));
		text.append(Html.fromHtml("List of existing accounts of this type: "));
		text.setTextSize(TypedValue.COMPLEX_UNIT_SP, (float) 15);
		text.setTextColor(Color.WHITE);
		text.setTypeface(Typeface.SANS_SERIF);
		container.addView(text);
		while (accounts.hasMoreElements()) {
			final com.o2hlink.activ8.client.entity.Account account = accounts.nextElement();
			if (account.getType() != type) continue;
			thereare = true;
			buttonLayout = new TableRow(Activa.myApp);
			buttonLayout.setOrientation(TableRow.HORIZONTAL);
			buttonLayout.setGravity(Gravity.CENTER_VERTICAL);
			buttonLayout.setLayoutParams(new TableLayout.LayoutParams(new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT)));		
			text = new TextView(Activa.myApp);
			text.setLayoutParams(new android.widget.TableRow.LayoutParams(screenwidth/2, android.widget.TableRow.LayoutParams.WRAP_CONTENT));
			text.append(account.getType().name());
			text.setTextSize(TypedValue.COMPLEX_UNIT_SP, (float) 15);
			text.setTextColor(Color.WHITE);
			text.setTypeface(Typeface.SANS_SERIF);
			ImageView image = new ImageView(Activa.myApp);
			image.setPadding(5, 5, 5, 5);
			image.setScaleType(ScaleType.FIT_XY);
			image.setImageResource(iconId);
			ImageView cancel = new ImageView(Activa.myApp);
			cancel.setPadding(5, 5, 5, 5);
			cancel.setScaleType(ScaleType.FIT_XY);
			cancel.setImageResource(R.drawable.cancel);
			cancel.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					AlertDialog.Builder builder = new AlertDialog.Builder(Activa.myApp);
					builder.setMessage(Activa.myLanguageManager.MAIN_REMOVE_ACCOUNT)
					       .setCancelable(false)
					       .setPositiveButton(Activa.myLanguageManager.MAIN_QUIT_YES, new DialogInterface.OnClickListener() {
					           public void onClick(DialogInterface dialog, int id) {
					        	   Activa.myMessageManager.deleteAccount(account);
								   dialog.cancel();
								   alertDialog2.cancel();
								   doAccountSynchronization(atinit, selection);
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
			});
			if (!thereare) {
				TextView text2 = new TextView(Activa.myApp);
				text2.setLayoutParams(new android.widget.TableRow.LayoutParams(android.widget.TableRow.LayoutParams.FILL_PARENT, android.widget.TableRow.LayoutParams.WRAP_CONTENT));
				text2.append(Html.fromHtml("-"));
				text2.setTextSize(TypedValue.COMPLEX_UNIT_SP, (float) 15);
				text2.setTextColor(Color.WHITE);
				text2.setTypeface(Typeface.SANS_SERIF);
				container.addView(text2);
			}
			buttonLayout.addView(image, screenwidth/8, screenwidth/8);
			buttonLayout.addView(text);
			buttonLayout.addView(cancel, screenwidth/8, screenwidth/8);
			container.addView(buttonLayout);
		}
	}
	
	public void synchronizeEmail(final AccountType type, final AlertDialog alertDialog2, final boolean atinit, final int selection) {
		LayoutInflater inflater = (LayoutInflater) Activa.myApp.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		final View layout = inflater.inflate(R.layout.toastpasswords,
				(ViewGroup) Activa.myApp.findViewById(R.id.toastpasswordsroot));
		LinearLayout passwords = (LinearLayout) layout.findViewById(R.id.passwords);
		TextView text = (TextView) layout.findViewById(R.id.passwordsexpl);
		String acc = "";
		if (type == AccountType.GMAIL) acc = "Gmail";
		else if (type == AccountType.GMAIL) acc = "Windows Live";
		text.setText(String.format(Activa.myLanguageManager.OPTIONS_ACCOUNT_GETPASSWD, acc));
		final Hashtable<Integer, EditText> edittexts = new Hashtable<Integer, EditText>();
		LinearLayout mailrow = new LinearLayout(Activa.myApp);
		mailrow.setLayoutParams(new android.view.ViewGroup.LayoutParams(android.view.ViewGroup.LayoutParams.FILL_PARENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT));
		mailrow.setOrientation(LinearLayout.HORIZONTAL);
		TextView mailexpl = new TextView(Activa.myApp);
		mailexpl.setLayoutParams(new android.view.ViewGroup.LayoutParams(150, android.view.ViewGroup.LayoutParams.WRAP_CONTENT));
		mailexpl.setText("Mail: ");
		if (type.equals(AccountType.SKYPE)) mailexpl.setText("User: ");
		mailexpl.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
		mailrow.addView(mailexpl);
		EditText mailtext = new EditText(Activa.myApp);
		mailtext.setLayoutParams(new android.view.ViewGroup.LayoutParams(android.view.ViewGroup.LayoutParams.FILL_PARENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT));
		mailtext.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
		mailrow.addView(mailtext);
		passwords.addView(mailrow);
		edittexts.put(0, mailtext);
		LinearLayout passrow = new LinearLayout(Activa.myApp);
		passrow.setLayoutParams(new android.view.ViewGroup.LayoutParams(android.view.ViewGroup.LayoutParams.FILL_PARENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT));
		passrow.setOrientation(LinearLayout.HORIZONTAL);
		TextView passexpl = new TextView(Activa.myApp);
		passexpl.setLayoutParams(new android.view.ViewGroup.LayoutParams(150, android.view.ViewGroup.LayoutParams.WRAP_CONTENT));
		passexpl.setText("Password: ");
		passexpl.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
		passrow.addView(passexpl);
		EditText passtext = new EditText(Activa.myApp);
		passtext.setLayoutParams(new android.view.ViewGroup.LayoutParams(android.view.ViewGroup.LayoutParams.FILL_PARENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT));
		passtext.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
		passrow.addView(passtext);
		passwords.addView(passrow);
		edittexts.put(1, passtext);
		ImageButton image = (ImageButton) layout.findViewById(R.id.passok);
		Builder builder = new AlertDialog.Builder(Activa.myApp);
		builder.setView(layout);
		final AlertDialog alertDialog = builder.create();
		alertDialog.show();
		image.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				alertDialog.cancel();
		        final ProgressDialog progdialog = ProgressDialog.show(Activa.myApp, Activa.myLanguageManager.OPTIONS_ACCOUNT_SYNCHRONIZE, 
						Activa.myLanguageManager.OPTIONS_ACCOUNT_SYNCHRONIZING, true);
		        progdialog.show();
				Runnable run =  new Runnable() {
					@Override
					public void run() {
						com.o2hlink.activ8.client.entity.Account account = new com.o2hlink.activ8.client.entity.Account();
						account.setType(type);
						String mail =  edittexts.get(0).getText().toString();
						String password =  edittexts.get(1).getText().toString();
						Activa.myMessageManager.AddMailAccount(account, mail, password);
						handler.sendEmptyMessage(0);
					}
					private Handler handler = new Handler() {
						@Override
						public void handleMessage(Message msg) {
							switch (msg.what) {
								default:
									progdialog.cancel();
									alertDialog2.cancel();
									doAccountSynchronization(atinit, selection);
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
	
	public void synchronizeDomain(final AccountType type, final AlertDialog alertDialog2, final boolean atinit, final int selection) {
		LayoutInflater inflater = (LayoutInflater) Activa.myApp.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		final View layout = inflater.inflate(R.layout.toastpasswords,
				(ViewGroup) Activa.myApp.findViewById(R.id.toastpasswordsroot));
		LinearLayout passwords = (LinearLayout) layout.findViewById(R.id.passwords);
		TextView text = (TextView) layout.findViewById(R.id.passwordsexpl);
		String acc = "";
		if (type == AccountType.GOOGLE) acc = "Google Apps";
		text.setText(String.format(Activa.myLanguageManager.OPTIONS_ACCOUNT_GETDOMAIN, acc));
		final Hashtable<Integer, EditText> edittexts = new Hashtable<Integer, EditText>();
		LinearLayout mailrow = new LinearLayout(Activa.myApp);
		mailrow.setLayoutParams(new android.view.ViewGroup.LayoutParams(android.view.ViewGroup.LayoutParams.FILL_PARENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT));
		mailrow.setOrientation(LinearLayout.HORIZONTAL);
		TextView mailexpl = new TextView(Activa.myApp);
		mailexpl.setLayoutParams(new android.view.ViewGroup.LayoutParams(150, android.view.ViewGroup.LayoutParams.WRAP_CONTENT));
		mailexpl.setText("Domain: ");
		mailexpl.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
		mailrow.addView(mailexpl);
		EditText mailtext = new EditText(Activa.myApp);
		mailtext.setLayoutParams(new android.view.ViewGroup.LayoutParams(android.view.ViewGroup.LayoutParams.FILL_PARENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT));
		mailtext.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
		mailrow.addView(mailtext);
		passwords.addView(mailrow);
		edittexts.put(0, mailtext);
		ImageButton image = (ImageButton) layout.findViewById(R.id.passok);
		Builder builder = new AlertDialog.Builder(Activa.myApp);
		builder.setView(layout);
		final AlertDialog alertDialog = builder.create();
		alertDialog.show();
		image.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				alertDialog.cancel();
		        final ProgressDialog progdialog = ProgressDialog.show(Activa.myApp, Activa.myLanguageManager.OPTIONS_ACCOUNT_SYNCHRONIZE, 
						Activa.myLanguageManager.OPTIONS_ACCOUNT_SYNCHRONIZING, true);
		        progdialog.show();
				Runnable run =  new Runnable() {
					String url;
					@Override
					public void run() {
						com.o2hlink.activ8.client.entity.Account account = new com.o2hlink.activ8.client.entity.Account();
						account.setType(type);
						String domain =  edittexts.get(0).getText().toString();
						Hashtable<Integer, String> parameters = new Hashtable<Integer, String>();
						parameters.put(0, domain);
						String callback = ActivaConfig.ACTIV8CALLBACKFORACCOUNTS + "?user="
							+ Activa.myMobileManager.userForServices.getId() + "&domain="
							+ URLEncoder.encode(domain) + "&type=" + type.ordinal();
						url = Activa.myMessageManager.requestAccountToken(type, callback);
						if (url != null) handler.sendEmptyMessage(0);
						else handler.sendEmptyMessage(1);
					}
					private Handler handler = new Handler() {
						@Override
						public void handleMessage(Message msg) {
							switch (msg.what) {
								case 0:
//									Intent in = new Intent(Intent.ACTION_VIEW);
//									in.setData(Uri.parse(url));
//									Activa.myApp.startActivity(in);
									progdialog.cancel();
									LayoutInflater inflater = (LayoutInflater) Activa.myApp.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
									final View layout = inflater.inflate(R.layout.toastempty,
											(ViewGroup) Activa.myApp.findViewById(R.id.toastemptyroot));
									Builder builder = new AlertDialog.Builder(Activa.myApp);
									builder.setView(layout);
									final AlertDialog alertDialog = builder.create();
									MyWebView web = new MyWebView(Activa.myApp, alertDialog);
									web.setLayoutParams(new android.view.ViewGroup.LayoutParams(android.view.ViewGroup.LayoutParams.FILL_PARENT, android.view.ViewGroup.LayoutParams.FILL_PARENT));
									web.getSettings().setJavaScriptEnabled(true);
									web.loadUrl(url);
									web.requestFocus(View.FOCUS_DOWN);
									web.setWebViewClient(new WebViewClient() {
										@Override  
										public boolean shouldOverrideUrlLoading(WebView view, String url) {
											view.loadUrl(url);
											return true;
											
										}
									});
									((LinearLayout) layout.findViewById(R.id.container)).addView(web);
									alertDialog.show();
									alertDialog.setOnCancelListener(new OnCancelListener() {
										@Override
										public void onCancel(DialogInterface dialog) {
											Activa.myMessageManager.getAccountList();
											alertDialog2.cancel();
											doAccountSynchronization(atinit, selection);
										}
									});
									break;
								default:
									progdialog.cancel();
									Toast toast = Toast.makeText(Activa.myApp, Activa.myLanguageManager.CONNECTION_FAILED, Toast.LENGTH_SHORT);
									toast.show();
							}
						}
					};
				};
				Thread thread = new Thread(run);
				thread.start();
			}
		});
	}
	
	public void synchronizeAccount(View layout, final AccountType type, final Hashtable<Integer, String> parameters, final boolean atinit, final int selection, final AlertDialog alertDialog2) {
		final ProgressDialog progdialog = ProgressDialog.show(Activa.myApp, Activa.myLanguageManager.OPTIONS_ACCOUNT_SYNCHRONIZE, 
				Activa.myLanguageManager.OPTIONS_ACCOUNT_SYNCHRONIZING, true);
	    progdialog.show();
		Runnable run =  new Runnable() {
			String url;
			@Override
			public void run() {
				String callback = ActivaConfig.ACTIV8CALLBACKFORACCOUNTS + "?user="
					+ Activa.myMobileManager.userForServices.getId() + "&type=" + type.ordinal();
				url = Activa.myMessageManager.requestAccountToken(type, callback);
				if (url != null) handler.sendEmptyMessage(0);
				else handler.sendEmptyMessage(1);
			}
			private Handler handler = new Handler() {
				@Override
				public void handleMessage(Message msg) {
					switch (msg.what) {
						case 0:
							progdialog.cancel();
							LayoutInflater inflater = (LayoutInflater) Activa.myApp.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
							final View layout = inflater.inflate(R.layout.toastempty,
									(ViewGroup) Activa.myApp.findViewById(R.id.toastemptyroot));
							Builder builder = new AlertDialog.Builder(Activa.myApp);
							builder.setView(layout);
							final AlertDialog alertDialog = builder.create();MyWebView web = new MyWebView(Activa.myApp, alertDialog);
							web.setLayoutParams(new android.view.ViewGroup.LayoutParams(android.view.ViewGroup.LayoutParams.FILL_PARENT, android.view.ViewGroup.LayoutParams.FILL_PARENT));
							web.getSettings().setJavaScriptEnabled(true);
							web.loadUrl(url);
							web.requestFocus(View.FOCUS_DOWN);
							web.setWebViewClient(new WebViewClient() {
								@Override  
								public boolean shouldOverrideUrlLoading(WebView view, String url) {
									view.loadUrl(url);
									return true;
									
								}
							});
							((LinearLayout) layout.findViewById(R.id.container)).addView(web);
							alertDialog.show();
							alertDialog.setOnCancelListener(new OnCancelListener() {
								@Override
								public void onCancel(DialogInterface dialog) {
									Activa.myMessageManager.getAccountList();
									alertDialog2.cancel();
									doAccountSynchronization(atinit, selection);
								}
							});
							break;
						default:
							progdialog.cancel();
							Toast toast = Toast.makeText(Activa.myApp, Activa.myLanguageManager.CONNECTION_FAILED, Toast.LENGTH_SHORT);
							toast.show();
					}
				}
			};
		};
		Thread thread = new Thread(run);
		thread.start();
	}
	
	public void showAccounts() {
		this.state = UI_STATE_PROGRAM;
		loadInfoPopupWithoutExiting(Activa.myLanguageManager.OPTIONS_ACCOUNT_GET);
		final ImageView animationFrame = (ImageView) Activa.myApp.findViewById(R.id.popupImage);
		animationFrame.setVisibility(View.VISIBLE);
		animationFrame.setBackgroundResource(R.drawable.loading);
		final AnimationDrawable animation = (AnimationDrawable) animationFrame.getBackground();
		animation.start();
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				Activa.myMessageManager.getAccountList();
				handler.sendEmptyMessage(0);
			}
			private Handler handler = new Handler() {
				@Override
				public void handleMessage(Message msg) {
					switch (msg.what) {
						default:
							animation.stop();
							animationFrame.setVisibility(View.GONE);
							removeInfoPopup();
							doAccountSynchronization(false, 0);
							break;
					}
				}
			};
		};
		Thread thread = new Thread(runnable);
		thread.start();
	}
	
	public void showAccountsLoaded() {
		Activa.myApp.setContentView(R.layout.list);
		int screenwidth = Activa.myApp.getResources().getDisplayMetrics().widthPixels;
		((TextView) Activa.myApp.findViewById(R.id.startText)).append(Activa.myLanguageManager.OPTIONS_ACCOUNTS);
		TableLayout ambientlisting = (TableLayout)Activa.myApp.findViewById(R.id.listing);
		Enumeration<com.o2hlink.activ8.client.entity.Account> accounts = Activa.myMessageManager.accountList.elements();
		while (accounts.hasMoreElements()) {
			final com.o2hlink.activ8.client.entity.Account account = accounts.nextElement();
			TableRow buttonLayout = new TableRow(Activa.myApp);
			buttonLayout.setOrientation(TableRow.HORIZONTAL);
			buttonLayout.setGravity(Gravity.CENTER_VERTICAL);
			buttonLayout.setLayoutParams(new TableLayout.LayoutParams(new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT)));		
			TextView text = new TextView(Activa.myApp);
			text.setLayoutParams(new android.widget.TableRow.LayoutParams(screenwidth/2, android.widget.TableRow.LayoutParams.WRAP_CONTENT));
			text.append(account.getType().name());
			text.setTextSize(TypedValue.COMPLEX_UNIT_SP, (float) 15);
			text.setTextColor(Color.BLACK);
			text.setTypeface(Typeface.SANS_SERIF);
			ImageView image = new ImageView(Activa.myApp);
			image.setPadding(5, 5, 5, 5);
			image.setScaleType(ScaleType.FIT_XY);
			ImageView cancel = new ImageView(Activa.myApp);
			cancel.setPadding(5, 5, 5, 5);
			cancel.setScaleType(ScaleType.FIT_XY);
			cancel.setImageResource(R.drawable.cancel);
			cancel.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					AlertDialog.Builder builder = new AlertDialog.Builder(Activa.myApp);
					builder.setMessage(Activa.myLanguageManager.MAIN_REMOVE_ACCOUNT)
					       .setCancelable(false)
					       .setPositiveButton(Activa.myLanguageManager.MAIN_QUIT_YES, new DialogInterface.OnClickListener() {
					           public void onClick(DialogInterface dialog, int id) {
					        	   Activa.myMessageManager.deleteAccount(account);
								   dialog.cancel();
								   Activa.myUIManager.showAccountsLoaded();
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
			});
			if (account.getType() == AccountType.GMAIL) {
				image.setImageResource(R.drawable.gmail);
				text.append(" - GMail");
			}
			else if (account.getType() == AccountType.SKYPE) {
				image.setImageResource(R.drawable.skype);
				text.append(" - Skype");
			}
			buttonLayout.addView(image, screenwidth/5, screenwidth/5);
			buttonLayout.addView(text);
			buttonLayout.addView(cancel, screenwidth/5, screenwidth/5);
			ambientlisting.addView(buttonLayout);
		}
		ImageButton back = (ImageButton) Activa.myApp.findViewById(R.id.previous);
		back.setVisibility(View.VISIBLE);
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Activa.myUIManager.showOptions();
			}
		});
		ImageView plus = (ImageView) Activa.myApp.findViewById(R.id.animation);
		plus.setVisibility(View.GONE);
		ImageButton delete = (ImageButton) Activa.myApp.findViewById(R.id.help);
		delete.setImageResource(R.drawable.refreshing);
		delete.setVisibility(View.VISIBLE);
		delete.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Activa.myUIManager.doAccountsSynchronizacion();
			}
		});
	}
	
	public void loadSharedQuestionnaires() {
		this.state = UI_STATE_TOTALQUESTIONNAIRE;
		int realwidth = Activa.myApp.getResources().getDisplayMetrics().widthPixels;
		Activa.myApp.setContentView(R.layout.list);
		((TextView) Activa.myApp.findViewById(R.id.startText)).setText(Activa.myLanguageManager.QUEST_ASSIGNED_TITLE);
		TableLayout questlisting = (TableLayout)Activa.myApp.findViewById(R.id.listing);
		Enumeration<Questionnaire> enumer = Activa.myQuestControlManager.questionnaires.elements();
		while (enumer.hasMoreElements()) {			
			final Questionnaire quest = enumer.nextElement();
			TableRow buttonLayout = new TableRow(Activa.myApp);
			buttonLayout.setOrientation(TableRow.HORIZONTAL);
			buttonLayout.setGravity(Gravity.CENTER_VERTICAL);
			buttonLayout.setLayoutParams(new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));			
			ImageButton button = new ImageButton(Activa.myApp);
			button.setBackgroundResource(R.drawable.iconbg);
			button.setImageResource(R.drawable.quest);
			OnClickListener listener = new OnClickListener() {
				@Override
				public void onClick(View v) {
					Activa.myQuestControlManager.eventAssociated = null;
					Activa.myQuestControlManager.activeQuestionnaire = quest;
					startQuestionnaire(quest);
				}
			};
			TextView text = new TextView(Activa.myApp);
			text.append(quest.getName());
			text.setWidth(realwidth/2);
			text.setTextSize((float) 20);
			text.setTextColor(Color.BLACK);
			text.setTypeface(Typeface.SANS_SERIF);
			ImageButton share = new ImageButton(Activa.myApp);
			share.setBackgroundResource(R.drawable.iconbg);
			share.setImageResource(R.drawable.contacts);
			share.setScaleType(ScaleType.FIT_XY);
			share.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					shareQuestionnaire(quest);
				}
			});
			buttonLayout.addView(button);
			buttonLayout.addView(text);
			if (quest.getId() != Activa.myLanguageManager.feedbackQuestId) buttonLayout.addView(share, 50, 50);
			button.setOnClickListener(listener);
			text.setOnClickListener(listener);
			questlisting.addView(buttonLayout);
		}
		Enumeration<Questionnaire> enumer2 = Activa.myQuestControlManager.createdQuest.elements();
		while (enumer2.hasMoreElements()) {			
			final Questionnaire quest = enumer2.nextElement();
			TableRow buttonLayout = new TableRow(Activa.myApp);
			buttonLayout.setOrientation(TableRow.HORIZONTAL);
			buttonLayout.setGravity(Gravity.CENTER_VERTICAL);
			buttonLayout.setLayoutParams(new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));			
			ImageButton button = new ImageButton(Activa.myApp);
			button.setBackgroundResource(R.drawable.iconbg);
			button.setImageResource(R.drawable.quest);
			OnClickListener listener = new OnClickListener() {
				@Override
				public void onClick(View v) {
					Activa.myQuestControlManager.eventAssociated = null;
					Activa.myQuestControlManager.activeQuestionnaire = quest;
					startQuestionnaire(quest);
				}
			};
			TextView text = new TextView(Activa.myApp);
			text.append(quest.getName());
			text.setWidth(realwidth/2);
			text.setTextSize((float) 20);
			text.setTextColor(Color.BLACK);
			text.setTypeface(Typeface.SANS_SERIF);
			ImageButton share = new ImageButton(Activa.myApp);
			share.setBackgroundResource(R.drawable.iconbg);
			share.setImageResource(R.drawable.contacts);
			share.setScaleType(ScaleType.FIT_XY);
			share.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					shareQuestionnaire(quest);
				}
			});
			buttonLayout.addView(button);
			buttonLayout.addView(text);
			buttonLayout.addView(share, 50, 50);
			button.setOnClickListener(listener);
			text.setOnClickListener(listener);
			questlisting.addView(buttonLayout);
		}
		ImageButton back = (ImageButton) Activa.myApp.findViewById(R.id.previous);
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Activa.myUIManager.loadGeneratedSubenvironment(Activa.myUIManager.lastSubenv, true);
			}
		});
		ImageButton change = (ImageButton) Activa.myApp.findViewById(R.id.help);
		change.setImageResource(R.drawable.refreshing);
		change.setVisibility(View.VISIBLE);
		change.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				loadAssignedQuestionnaires();			
			}
		});
		ImageView addquest = (ImageView) Activa.myApp.findViewById(R.id.animation);
		addquest.setImageResource(R.drawable.searchcontact);
		addquest.setVisibility(View.VISIBLE);
		addquest.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				searchQuestionnaire();			
			}
		});
	}
	
	public void searchQuestionnaire() {
		LayoutInflater inflater = (LayoutInflater) Activa.myApp.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		final View layout = inflater.inflate(R.layout.toastsearch,
		                               (ViewGroup) Activa.myApp.findViewById(R.id.toastsearchroot));
		TextView text = (TextView) layout.findViewById(R.id.searchexpl);
		text.setText(Activa.myLanguageManager.NOTIFICATION_QUEST_EXPL);
		ImageButton image = (ImageButton) layout.findViewById(R.id.searchok);
		Builder builder = new AlertDialog.Builder(Activa.myApp);
		builder.setView(layout);
		final AlertDialog alertDialog = builder.create();
		alertDialog.show();
		image.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				final String query = ((EditText)layout.findViewById(R.id.searchtext)).getText().toString();
				alertDialog.cancel();
				final ProgressDialog dialog = ProgressDialog.show(Activa.myApp, Activa.myLanguageManager.NOTIFICATION_QUEST_TITLE, 
						Activa.myLanguageManager.NOTIFICATION_QUEST_SEARCHING, true);
				dialog.show();
				Runnable run =  new Runnable() {
					Array<Questionnaire> quests;
					@Override
					public void run() {
						quests = Activa.myQuestControlManager.SearchQuests(query);
						dialog.cancel();
						if (quests == null) {
							handler.sendEmptyMessage(0);
						} else if (quests.size() == 0) {
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
									Toast toast = Toast.makeText(Activa.myApp, Activa.myLanguageManager.CONNECTION_FAILED, Toast.LENGTH_SHORT);
									toast.show();
									break;
								case 1:
									Toast toast2 = Toast.makeText(Activa.myApp, Activa.myLanguageManager.NOTIFICATION_QUEST_NOTFOUND, Toast.LENGTH_SHORT);
									toast2.show();
									break;
								case 2:
									final CharSequence[] items = new CharSequence[quests.size()];
									final Array<Questionnaire> questsToAdd = new Array<Questionnaire>();
									for (int i = 0; i < quests.size(); i++) {
										items[i] = quests.get(i).getName();
									}
									AlertDialog.Builder builder = new AlertDialog.Builder(Activa.myApp);
									builder.setTitle(Activa.myLanguageManager.NOTIFICATION_QUEST_ADD);
									builder.setMultiChoiceItems(items, null, new OnMultiChoiceClickListener() {
										@Override
										public void onClick(DialogInterface dialog, int which, boolean isChecked) {
											if (isChecked) questsToAdd.add(quests.get(which));
											else questsToAdd.remove(quests.get(which));
										}
									});
									builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
								           public void onClick(DialogInterface dialog, int id) {
								        	   for (int i = 0; i < questsToAdd.size(); i++) {
								        		   ArrayList<Long> users = new ArrayList<Long>();
								        		   users.add(Activa.myMobileManager.userForServices.getId());
								        		   Activa.myQuestControlManager.shareQuestionnaire(users, questsToAdd.get(i));
//									        	   Activa.myProtocolManager.getPrivilegesOnQuestionnaire(questsToAdd.get(i));
								        	   }
								               dialog.cancel();
								               loadSharedQuestionnaires();
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
	
	public void loadAssignedQuestionnaires() {
		this.state = UI_STATE_TOTALQUESTIONNAIRE;
		int realwidth = Activa.myApp.getResources().getDisplayMetrics().widthPixels;
		Activa.myApp.setContentView(R.layout.list);
		((TextView) Activa.myApp.findViewById(R.id.startText)).setText(Activa.myLanguageManager.QUEST_CREATED_TITLE);
		TableLayout questlisting = (TableLayout)Activa.myApp.findViewById(R.id.listing);
		Enumeration<Questionnaire> enumer = Activa.myQuestControlManager.createdQuest.elements();
		while (enumer.hasMoreElements()) {			
			final Questionnaire quest = enumer.nextElement();
			TableRow buttonLayout = new TableRow(Activa.myApp);
			buttonLayout.setOrientation(TableRow.HORIZONTAL);
			buttonLayout.setGravity(Gravity.CENTER_VERTICAL);
			buttonLayout.setLayoutParams(new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));			
			ImageButton button = new ImageButton(Activa.myApp);
			button.setBackgroundResource(R.drawable.iconbg);
			button.setImageResource(R.drawable.quest);
			OnClickListener listener = new OnClickListener() {
				@Override
				public void onClick(View v) {
					Runnable run = new Runnable() {
						@Override
						public void run() {
							handler.sendEmptyMessage(0);
							if (Activa.myQuestControlManager.getQuestionnaire(quest.getId())) {
								handler.sendEmptyMessage(1);
							}
							else handler.sendEmptyMessage(2);
						}
						private Handler handler = new Handler(){
							@Override
							public void handleMessage(Message msg) {
								ImageView animationFrame;
								AnimationDrawable animation;
								switch (msg.what) {
									case 0:
										Activa.myUIManager.loadInfoPopupWithoutExiting(Activa.myLanguageManager.NOTIFICATION_DOWNLOADING + quest.getName() + " ...");
										animationFrame = (ImageView) Activa.myApp.findViewById(R.id.popupImage);
										animationFrame.setVisibility(View.VISIBLE);
										animationFrame.setBackgroundResource(R.drawable.loading);
										animation = (AnimationDrawable) animationFrame.getBackground();
										animation.start();
										break;
									case 1:
										animationFrame = (ImageView) Activa.myApp.findViewById(R.id.popupImage);
										animation = (AnimationDrawable) animationFrame.getBackground();
										animation.stop();
										animationFrame.setVisibility(View.GONE);
										Activa.myUIManager.removeInfoPopup();
										Activa.myUIManager.modifyQuestionnaire(quest);
										break;
									case 2:
										animationFrame = (ImageView) Activa.myApp.findViewById(R.id.popupImage);
										animation = (AnimationDrawable) animationFrame.getBackground();
										animation.stop();
										animationFrame.setVisibility(View.GONE);
										Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.CONNECTION_FAILED);
										break;
								}
							}
						};
					};
					Thread thread = new Thread(run);
					thread.start();
				}
			};
			TextView text = new TextView(Activa.myApp);
			text.append(quest.getName());
			text.setWidth(realwidth*2/5);
			text.setTextSize((float) 20);
			text.setTextColor(Color.BLACK);
			text.setTypeface(Typeface.SANS_SERIF);			
			ImageButton delete = new ImageButton(Activa.myApp);
			delete.setBackgroundResource(R.drawable.iconbg);
			delete.setScaleType(ScaleType.FIT_XY);
			delete.setImageResource(R.drawable.delete);
			delete.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Runnable run = new Runnable() {
						@Override
						public void run() {
							handler.sendEmptyMessage(0);
							if (Activa.myQuestControlManager.removeQuestionnaire(quest)) {
								handler.sendEmptyMessage(1);
							}
							else handler.sendEmptyMessage(2);
						}
						private Handler handler = new Handler(){
							@Override
							public void handleMessage(Message msg) {
								ImageView animationFrame;
								AnimationDrawable animation;
								switch (msg.what) {
									case 0:
										Activa.myUIManager.loadInfoPopupWithoutExiting("Removing questionnaire ...");
										animationFrame = (ImageView) Activa.myApp.findViewById(R.id.popupImage);
										animationFrame.setVisibility(View.VISIBLE);
										animationFrame.setBackgroundResource(R.drawable.loading);
										animation = (AnimationDrawable) animationFrame.getBackground();
										animation.start();
										break;
									case 1:
										animationFrame = (ImageView) Activa.myApp.findViewById(R.id.popupImage);
										animation = (AnimationDrawable) animationFrame.getBackground();
										animation.stop();
										animationFrame.setVisibility(View.GONE);
										Activa.myUIManager.removeInfoPopup();
										Activa.myUIManager.loadAssignedQuestionnaires();
										break;
									case 2:
										animationFrame = (ImageView) Activa.myApp.findViewById(R.id.popupImage);
										animation = (AnimationDrawable) animationFrame.getBackground();
										animation.stop();
										animationFrame.setVisibility(View.GONE);
										Activa.myUIManager.loadInfoPopup("Remove failed");
										break;
								}
							}
						};
					};
					Thread thread = new Thread(run);
					thread.start();
				}
			});			
			ImageButton share = new ImageButton(Activa.myApp);
			share.setBackgroundResource(R.drawable.iconbg);
			share.setImageResource(R.drawable.contacts);
			share.setScaleType(ScaleType.FIT_XY);
			share.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					shareQuestionnaire(quest);
				}
			});
			buttonLayout.addView(button);
			buttonLayout.addView(text);
			buttonLayout.addView(share, 50, 50);
			buttonLayout.addView(delete, 50, 50);
			button.setOnClickListener(listener);
			text.setOnClickListener(listener);
			questlisting.addView(buttonLayout);
		}
		ImageButton back = (ImageButton) Activa.myApp.findViewById(R.id.previous);
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Activa.myUIManager.loadGeneratedSubenvironment(Activa.myUIManager.lastSubenv, true);
			}
		});
		ImageButton change = (ImageButton) Activa.myApp.findViewById(R.id.help);
		change.setImageResource(R.drawable.refreshing);
		change.setVisibility(View.VISIBLE);
		change.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (Activa.myMobileManager.userForServices instanceof com.o2hlink.activ8.client.entity.Patient) Activa.myUIManager.loadTotalQuestList();
				else Activa.myUIManager.loadSharedQuestionnaires();
			}
		});
		ImageView addquest = (ImageView) Activa.myApp.findViewById(R.id.animation);
		addquest.setImageResource(R.drawable.plus);
		addquest.setVisibility(View.VISIBLE);
		addquest.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				createNewQuestionnaire();			
			}
		});
	}
	
	public void shareQuestionnaire(final Questionnaire quest) {
		final Long[] users = new Long[Activa.myMessageManager.contactList.size() + 1];
		final CharSequence[] items = new CharSequence[Activa.myMessageManager.contactList.size() + 1];
		final ArrayList<Long>usersToAdd = new ArrayList<Long>();
		int i = 1;
		users[0] = -1l;
		items[0] = "Make it public";
		Enumeration<Contact> enumerator = Activa.myMessageManager.contactList.elements();
		while (enumerator.hasMoreElements()) {
			Contact user = enumerator.nextElement();
			items[i] = user.getFirstName() + " " + user.getLastName();
			users[i] = Long.parseLong(user.getId());
			i++;
		}
		AlertDialog.Builder builder = new AlertDialog.Builder(Activa.myApp);
		builder.setTitle(Activa.myLanguageManager.NOTIFICATION_SEARCH_ADD);
		builder.setMultiChoiceItems(items, null, new OnMultiChoiceClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which, boolean isChecked) {
				if (isChecked) usersToAdd.add(users[which]);
				else usersToAdd.remove(users[which]);
			}
		});
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
	           public void onClick(DialogInterface dialog, int id) {
	        	   if (usersToAdd.contains(users[0])) {
	        		   usersToAdd.remove(users[0]);
	        		   Activa.myQuestControlManager.publishQuestionnaire(quest);
	        	   }
	        	   Activa.myQuestControlManager.shareQuestionnaire(usersToAdd, quest);
	        	   dialog.cancel();
	           }
	    });
		builder.setNegativeButton("SEARCH", new DialogInterface.OnClickListener() {
	           public void onClick(DialogInterface dialog, int id) {
	        	   searchContacts();
	           }
	    });
		AlertDialog alert = builder.create();
		alert.show();
	}
	
	public void createNewQuestionnaire() {
		LayoutInflater inflater = (LayoutInflater) Activa.myApp.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		final View layout = inflater.inflate(R.layout.toastaddquestionnaire,
		                               (ViewGroup) Activa.myApp.findViewById(R.id.toastaddquestionnaireroot));
		TextView title = (TextView) layout.findViewById(R.id.addtitle);
		title.setText(Activa.myLanguageManager.QUEST_CREATE_QUESTIONNAIRE);
		TextView namereq = (TextView) layout.findViewById(R.id.namerequested);
		namereq.setText(Activa.myLanguageManager.QUEST_NAME);
		ImageButton image = (ImageButton) layout.findViewById(R.id.addeventok);
		Builder builder = new AlertDialog.Builder(Activa.myApp);
		builder.setView(layout);
		final AlertDialog alertDialog = builder.create();
		alertDialog.show();
		image.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				final String quest = ((EditText) layout.findViewById(R.id.name)).getText().toString();
				final ProgressDialog dialog = ProgressDialog.show(Activa.myApp, Activa.myLanguageManager.QUEST_CREATE_QUESTIONNAIRE, 
						Activa.myLanguageManager.QUEST_CREATING_QUESTIONNAIRE, true);
				dialog.show();
				Runnable run =  new Runnable() {
					@Override
					public void run() {
						if (Activa.myQuestControlManager.createQuestionnaire(quest))
							handler.sendEmptyMessage(1);
						else 
							handler.sendEmptyMessage(0);
					}
					private Handler handler = new Handler() {
						@Override
						public void handleMessage(Message msg) {
							switch (msg.what) {
								case 0:
									Toast toast = Toast.makeText(Activa.myApp, Activa.myLanguageManager.CONNECTION_FAILED, Toast.LENGTH_SHORT);
									Activa.myUIManager.modifyQuestionnaire(Activa.myQuestControlManager.activeQuestionnaire);
									dialog.cancel();
									toast.show();
									break;
								case 1:
									Activa.myUIManager.loadAssignedQuestionnaires();
									dialog.cancel();
									break;
							}
						}

					};
				};
				Thread thread = new Thread(run);
				thread.start();
				alertDialog.cancel();
			}
		});
	}
	
	public void modifyQuestionnaire(final Questionnaire questionnaire) {
		Activa.myQuestControlManager.activeQuestionnaire = questionnaire;
		int realwidth = Activa.myApp.getResources().getDisplayMetrics().widthPixels;
		this.state = UI_STATE_TOTALQUESTIONNAIRE;
		Activa.myApp.setContentView(R.layout.list);
		((TextView) Activa.myApp.findViewById(R.id.startText)).setText(Activa.myLanguageManager.QUEST_CREATED_TITLE);
		TableLayout questlisting = (TableLayout)Activa.myApp.findViewById(R.id.listing);
		Enumeration<Question> enumer = Activa.myQuestControlManager.activeQuestions.elements();
		while (enumer.hasMoreElements()) {			
			final Question quest = enumer.nextElement();
			TableRow buttonLayout = new TableRow(Activa.myApp);
			buttonLayout.setOrientation(TableRow.HORIZONTAL);
			buttonLayout.setGravity(Gravity.CENTER_VERTICAL);
			TableLayout.LayoutParams layoutparams = new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
			layoutparams.setMargins(10, 0, 0, 0);
			buttonLayout.setLayoutParams(layoutparams);			
			ImageButton delete = new ImageButton(Activa.myApp);
			delete.setBackgroundResource(R.drawable.iconbg);
			delete.setScaleType(ScaleType.FIT_XY);
			delete.setImageResource(R.drawable.delete);
			delete.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Runnable run = new Runnable() {
						@Override
						public void run() {
							handler.sendEmptyMessage(0);
							if (Activa.myQuestControlManager.removeQuestion(quest)) {
								handler.sendEmptyMessage(1);
							}
							else handler.sendEmptyMessage(2);
						}
						private Handler handler = new Handler(){
							@Override
							public void handleMessage(Message msg) {
								ImageView animationFrame;
								AnimationDrawable animation;
								switch (msg.what) {
									case 0:
										Activa.myUIManager.loadInfoPopupWithoutExiting("Removing question ...");
										animationFrame = (ImageView) Activa.myApp.findViewById(R.id.popupImage);
										animationFrame.setVisibility(View.VISIBLE);
										animationFrame.setBackgroundResource(R.drawable.loading);
										animation = (AnimationDrawable) animationFrame.getBackground();
										animation.start();
										break;
									case 1:
										animationFrame = (ImageView) Activa.myApp.findViewById(R.id.popupImage);
										animation = (AnimationDrawable) animationFrame.getBackground();
										animation.stop();
										animationFrame.setVisibility(View.GONE);
										Activa.myUIManager.removeInfoPopup();
										Activa.myUIManager.modifyQuestionnaire(questionnaire);
										break;
									case 2:
										animationFrame = (ImageView) Activa.myApp.findViewById(R.id.popupImage);
										animation = (AnimationDrawable) animationFrame.getBackground();
										animation.stop();
										animationFrame.setVisibility(View.GONE);
										Activa.myUIManager.loadInfoPopup("Remove failed");
										break;
								}
							}
						};
					};
					Thread thread = new Thread(run);
					thread.start();
				}
			});			
			ImageButton modify = new ImageButton(Activa.myApp);
			modify.setBackgroundResource(R.drawable.iconbg);
			modify.setImageResource(R.drawable.modify);
			modify.setScaleType(ScaleType.FIT_XY);
			modify.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					modifyQuestion(quest);
				}
			});
			TextView text = new TextView(Activa.myApp);
			text.append(quest.getName());
			if ((questionnaire.getFirst() != null)&&(questionnaire.getFirst().equals(quest.getId()))) text.append("[FIRST QUESTION]");
			text.setWidth(realwidth*3/5);
			text.setTextSize((float) 20);
			text.setTextColor(Color.BLACK);
			text.setTypeface(Typeface.SANS_SERIF);
			buttonLayout.addView(text);
			buttonLayout.addView(modify, 50, 50);
			buttonLayout.addView(delete, 50, 50);
			questlisting.addView(buttonLayout);
		}
		ImageButton back = (ImageButton) Activa.myApp.findViewById(R.id.previous);
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Activa.myUIManager.loadGeneratedSubenvironment(Activa.myUIManager.lastSubenv, true);
			}
		});
		ImageButton change = (ImageButton) Activa.myApp.findViewById(R.id.help);
		change.setImageResource(R.drawable.first);
		change.setVisibility(View.VISIBLE);
		change.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setFirstQuestion();			
			}
		});
		ImageView addquest = (ImageView) Activa.myApp.findViewById(R.id.animation);
		addquest.setImageResource(R.drawable.plus);
		addquest.setVisibility(View.VISIBLE);
		addquest.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				createNewQuestion();			
			}
		});
	}
	
	public void setFirstQuestion() {
		Enumeration<Question> questions = Activa.myQuestControlManager.activeQuestions.elements();
		final CharSequence[] items = new CharSequence[Activa.myQuestControlManager.activeQuestions.size()];
		final Long[] longitems = new Long[Activa.myQuestControlManager.activeQuestions.size()];
		int j = 0;
		while (questions.hasMoreElements()) {
			Question question = questions.nextElement();
			items[j] = question.getName();
			longitems[j] = question.getId();
			j++;
		}
		AlertDialog.Builder builder = new AlertDialog.Builder(Activa.myApp);
		builder.setTitle(Activa.myLanguageManager.MESSAGES_CONTACT_REQUEST);
		builder.setItems(items, new DialogInterface.OnClickListener() {
		    public void onClick(DialogInterface dialog, int item) {
		    	Activa.myQuestControlManager.activeQuestionnaire.setFirst(longitems[item]);
				Runnable run = new Runnable() {
					@Override
					public void run() {
						handler.sendEmptyMessage(0);
						if (Activa.myQuestControlManager.updateQuestionnaire(Activa.myQuestControlManager.activeQuestionnaire))
							handler.sendEmptyMessage(1);
						else handler.sendEmptyMessage(2);
					}
					private Handler handler = new Handler(){
						@Override
						public void handleMessage(Message msg) {
							ImageView animationFrame;
							AnimationDrawable animation;
							switch (msg.what) {
								case 0:
									Activa.myUIManager.loadInfoPopupWithoutExiting("Updating questionnaire ...");
									animationFrame = (ImageView) Activa.myApp.findViewById(R.id.popupImage);
									animationFrame.setVisibility(View.VISIBLE);
									animationFrame.setBackgroundResource(R.drawable.loading);
									animation = (AnimationDrawable) animationFrame.getBackground();
									animation.start();
									break;
								case 1:
									animationFrame = (ImageView) Activa.myApp.findViewById(R.id.popupImage);
									animation = (AnimationDrawable) animationFrame.getBackground();
									animation.stop();
									animationFrame.setVisibility(View.GONE);
									Activa.myUIManager.removeInfoPopup();
									Activa.myUIManager.modifyQuestionnaire(Activa.myQuestControlManager.activeQuestionnaire);
									break;
								case 2:
									animationFrame = (ImageView) Activa.myApp.findViewById(R.id.popupImage);
									animation = (AnimationDrawable) animationFrame.getBackground();
									animation.stop();
									animationFrame.setVisibility(View.GONE);
									Activa.myUIManager.loadInfoPopup("Update failed");
									break;
							}
						}
					};
				};
				Thread thread = new Thread(run);
				thread.start();
		    }
		});
		AlertDialog alert = builder.create();
		alert.show();
	}
	
	public void createNewQuestion() {
		LayoutInflater inflater = (LayoutInflater) Activa.myApp.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		final View layout = inflater.inflate(R.layout.toastcreatequestion,
		                               (ViewGroup) Activa.myApp.findViewById(R.id.toastcreatequestionroot));
		TextView title = (TextView) layout.findViewById(R.id.addtitle);
		title.setText(Activa.myLanguageManager.QUEST_CREATE_QUESTION);
		TextView namereq = (TextView) layout.findViewById(R.id.typerequest);
		namereq.setText(Activa.myLanguageManager.QUEST_TYPE);
		final Spinner type = (Spinner) layout.findViewById(R.id.type);
	    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(Activa.myApp, R.array.questtype, android.R.layout.simple_spinner_item);
	    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    type.setAdapter(adapter);
		ImageButton image = (ImageButton) layout.findViewById(R.id.addeventok);
		Builder builder = new AlertDialog.Builder(Activa.myApp);
		builder.setView(layout);
		final AlertDialog alertDialog = builder.create();
		alertDialog.show();
		image.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				final Question question;
				switch (type.getSelectedItemPosition()) {
					case 0:
						question = new MultiQuestion();
						((MultiQuestion)question).setMultiple(false);
						break;
					case 1:
						question = new MultiQuestion();
						((MultiQuestion)question).setMultiple(true);
						break;
					case 2:
						question = new NumericQuestion();
						break;
					case 3:
						question = new TextQuestion();
						break;
					default:
						question = new Question();
						break;
				}
				question.setName("");
				Runnable run = new Runnable() {
					Question createdQuest;
					Question prevQest;
					@Override
					public void run() {
						handler.sendEmptyMessage(0);
						createdQuest = Activa.myQuestControlManager.addQuestion(question);
						if (createdQuest != null) {
							if (Activa.myQuestControlManager.activeQuestions.size() <= 1) {
								Activa.myQuestControlManager.activeQuestionnaire.setFirst(createdQuest.getId());
								if (Activa.myQuestControlManager.updateQuestionnaire(Activa.myQuestControlManager.activeQuestionnaire))
									handler.sendEmptyMessage(3);
								else handler.sendEmptyMessage(4);
							}
							else {
								prevQest = Activa.myQuestControlManager.activeQuestions.get(Activa.myQuestControlManager.lastQuestionId);
								if (prevQest != null) {
									prevQest.setNext(createdQuest.getId());
									if (Activa.myQuestControlManager.updateQuestion(prevQest))
										handler.sendEmptyMessage(1);
									else handler.sendEmptyMessage(5);
								}
								else handler.sendEmptyMessage(5);
							}
						}
						else handler.sendEmptyMessage(2);
					}
					private Handler handler = new Handler(){
						@Override
						public void handleMessage(Message msg) {
							ImageView animationFrame;
							AnimationDrawable animation;
							switch (msg.what) {
								case 0:
									Activa.myUIManager.loadInfoPopupWithoutExiting(Activa.myLanguageManager.QUEST_CREATING_QUESTION);
									animationFrame = (ImageView) Activa.myApp.findViewById(R.id.popupImage);
									animationFrame.setVisibility(View.VISIBLE);
									animationFrame.setBackgroundResource(R.drawable.loading);
									animation = (AnimationDrawable) animationFrame.getBackground();
									animation.start();
									break;
								case 1:
									animationFrame = (ImageView) Activa.myApp.findViewById(R.id.popupImage);
									animation = (AnimationDrawable) animationFrame.getBackground();
									animation.stop();
									animationFrame.setVisibility(View.GONE);
									Activa.myUIManager.removeInfoPopup();
									Activa.myQuestControlManager.lastQuestionId = createdQuest.getId();
									Activa.myUIManager.modifyQuestion(createdQuest);
									loadInfoPopup(String.format(Activa.myLanguageManager.QUEST_LINKED, prevQest.getName()));
									break;
								case 2:
									animationFrame = (ImageView) Activa.myApp.findViewById(R.id.popupImage);
									animation = (AnimationDrawable) animationFrame.getBackground();
									animation.stop();
									animationFrame.setVisibility(View.GONE);
									Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.QUEST_CREATING_FAILED);
									break;
								case 3:
									animationFrame = (ImageView) Activa.myApp.findViewById(R.id.popupImage);
									animation = (AnimationDrawable) animationFrame.getBackground();
									animation.stop();
									animationFrame.setVisibility(View.GONE);
									Activa.myUIManager.removeInfoPopup();
									Activa.myQuestControlManager.lastQuestionId = createdQuest.getId();
									Activa.myUIManager.modifyQuestion(createdQuest);
									loadInfoPopup(Activa.myLanguageManager.QUEST_FIRST);
									break;
								case 4:
									animationFrame = (ImageView) Activa.myApp.findViewById(R.id.popupImage);
									animation = (AnimationDrawable) animationFrame.getBackground();
									animation.stop();
									animationFrame.setVisibility(View.GONE);
									Activa.myUIManager.removeInfoPopup();
									Activa.myQuestControlManager.lastQuestionId = createdQuest.getId();
									Activa.myUIManager.modifyQuestion(createdQuest);
									loadInfoPopup(Activa.myLanguageManager.QUEST_NOTFIRST);
									break;
								case 5:
									animationFrame = (ImageView) Activa.myApp.findViewById(R.id.popupImage);
									animation = (AnimationDrawable) animationFrame.getBackground();
									animation.stop();
									animationFrame.setVisibility(View.GONE);
									Activa.myUIManager.removeInfoPopup();
									Activa.myQuestControlManager.lastQuestionId = createdQuest.getId();
									Activa.myUIManager.modifyQuestion(createdQuest);
									Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.QUEST_LINKED_FAILED);
									break;
							}
						}
					};
				};
				Thread thread = new Thread(run);
				thread.start();
				alertDialog.cancel();
			}
		});
	}
	
	public void modifyQuestion(Question question, String text, int position, boolean selected) {
		modifyQuestion(question);
		((EditText) Activa.myApp.findViewById(R.id.text)).setText(text);
		((Spinner) Activa.myApp.findViewById(R.id.next)).setSelection(position);
		((CheckBox) Activa.myApp.findViewById(R.id.optional)).setSelected(selected);
	}
	
	public void modifyQuestion(final Question question) {
		int selectedByFirst = 0, i = 0;
		int realwidth = Activa.myApp.getResources().getDisplayMetrics().widthPixels;
		this.state = UI_STATE_QUESTION;
		Activa.myApp.setContentView(R.layout.createquestion);
		((TextView) Activa.myApp.findViewById(R.id.startText)).setText(Activa.myLanguageManager.QUEST_CREATE_QUESTIONNAIRE);
		((TextView) Activa.myApp.findViewById(R.id.textrequest)).setText(Activa.myLanguageManager.QUEST_TEXT + ": ");
		((EditText) Activa.myApp.findViewById(R.id.text)).setText(question.getName());
		((TextView) Activa.myApp.findViewById(R.id.nextrequest)).setText(Activa.myLanguageManager.QUEST_NEXT + ": ");
		final Spinner next = (Spinner) Activa.myApp.findViewById(R.id.next);
		ArrayList<String> spinnerArray = new ArrayList<String>();
		final ArrayList<Long> idsArray = new ArrayList<Long>();
		Enumeration<Question> questions = Activa.myQuestControlManager.activeQuestions.elements();
	    spinnerArray.add("-");
	    idsArray.add(null);
	    while (questions.hasMoreElements()) {
	    	Question questToAdd = questions.nextElement();
	    	if (question.getId() == questToAdd.getId()) continue;
	    	if (question.getName().equals("")) continue;
	    	i++;
	    	if (question.getNext() != null)
	    		if (questToAdd.getId() == question.getNext()) selectedByFirst = i;
	    	spinnerArray.add(questToAdd.getName());
	    	idsArray.add(questToAdd.getId());
	    }
	    ArrayAdapter<String> adapter = new ArrayAdapter<String>(Activa.myApp, android.R.layout.simple_spinner_item, spinnerArray);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		next.setAdapter(adapter);
		next.setSelection(selectedByFirst);
	    if (question instanceof MultiQuestion) {
			((RelativeLayout) Activa.myApp.findViewById(R.id.optionallayout)).setVisibility(View.VISIBLE);
			((TextView) Activa.myApp.findViewById(R.id.optionalrequest)).setText(Activa.myLanguageManager.QUEST_OPTIONAL + ": ");
			((CheckBox) Activa.myApp.findViewById(R.id.optional)).setSelected(question.isOptional());
			((CheckBox) Activa.myApp.findViewById(R.id.optional)).setOnCheckedChangeListener(new android.widget.CompoundButton.OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					question.setOptional(isChecked);
				}
			});
		}
		if (question instanceof MultiQuestion) {
			Array<Answer> answers = Activa.myQuestControlManager.activeAnswers.get(question.getId());
			Array<Condition> conditions = Activa.myQuestControlManager.activeConditions.get(question.getId());
			((LinearLayout) Activa.myApp.findViewById(R.id.answers)).setVisibility(View.VISIBLE);
			((LinearLayout) Activa.myApp.findViewById(R.id.conditions)).setVisibility(View.VISIBLE);
			((RelativeLayout) Activa.myApp.findViewById(R.id.answershead)).setVisibility(View.VISIBLE);
			((RelativeLayout) Activa.myApp.findViewById(R.id.conditionhead)).setVisibility(View.VISIBLE);
			((TextView) Activa.myApp.findViewById(R.id.answersrequest)).setText(Activa.myLanguageManager.QUEST_ANSWERS + ": ");
			((TextView) Activa.myApp.findViewById(R.id.conditionrequest)).setText(Activa.myLanguageManager.QUEST_CONDITIONS + ": ");
			Iterator<Answer> ansit = answers.iterator();
			while (ansit.hasNext()) {
				final Answer answer = ansit.next();
				TableRow buttonLayout = new TableRow(Activa.myApp);
				buttonLayout.setOrientation(TableRow.HORIZONTAL);
				buttonLayout.setGravity(Gravity.CENTER_VERTICAL);
				buttonLayout.setLayoutParams(new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
				ImageButton delete = new ImageButton(Activa.myApp);
				delete.setImageResource(R.drawable.delete);
				delete.setScaleType(ScaleType.FIT_XY);
				delete.setBackgroundResource(R.drawable.iconbg);
				delete.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						Runnable run = new Runnable() {
							@Override
							public void run() {
								handler.sendEmptyMessage(0);
								if (Activa.myQuestControlManager.removeAnswer(question.getId(), answer)) {
									handler.sendEmptyMessage(1);
								}
								else handler.sendEmptyMessage(2);
							}
							private Handler handler = new Handler(){
								@Override
								public void handleMessage(Message msg) {
									ImageView animationFrame;
									AnimationDrawable animation;
									switch (msg.what) {
										case 0:
											animationFrame = (ImageView) Activa.myApp.findViewById(R.id.animation);
											animationFrame.setVisibility(View.VISIBLE);
											animationFrame.setBackgroundResource(R.drawable.loading);
											animation = (AnimationDrawable) animationFrame.getBackground();
											animation.start();
											break;
										case 1:
											animationFrame = (ImageView) Activa.myApp.findViewById(R.id.animation);
											animation = (AnimationDrawable) animationFrame.getBackground();
											animation.stop();
											animationFrame.setVisibility(View.GONE);
											Activa.myUIManager.removeInfoPopup();
											String text = ((EditText) Activa.myApp.findViewById(R.id.text)).getText().toString();
											int position = ((Spinner) Activa.myApp.findViewById(R.id.next)).getSelectedItemPosition();
											boolean checked = ((CheckBox) Activa.myApp.findViewById(R.id.optional)).isChecked();
											Activa.myUIManager.modifyQuestion(question, text, position, checked);
											break;
										case 2:
											animationFrame = (ImageView) Activa.myApp.findViewById(R.id.animation);
											animation = (AnimationDrawable) animationFrame.getBackground();
											animation.stop();
											animationFrame.setVisibility(View.GONE);
											Activa.myUIManager.loadInfoPopup("Remove failed");
											break;
									}
								}
							};
						};
						Thread thread = new Thread(run);
						thread.start();
					}
				});			
				ImageButton modify = new ImageButton(Activa.myApp);
				modify.setImageResource(R.drawable.modify);
				modify.setBackgroundResource(R.drawable.iconbg);
				modify.setScaleType(ScaleType.FIT_XY);
				modify.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						modifyAnswer(question, answer);
					}
				});
				TextView text = new TextView(Activa.myApp);
				text.append(answer.getName());
				text.setWidth(realwidth/2);
				text.setTextSize((float) 20);
				text.setTextColor(Color.BLACK);
				text.setTypeface(Typeface.SANS_SERIF);
				buttonLayout.addView(modify, 40, 40);
				buttonLayout.addView(delete, 40, 40);
				buttonLayout.addView(text);
				((LinearLayout) Activa.myApp.findViewById(R.id.answers)).addView(buttonLayout);
			}
			ImageButton addanswer = (ImageButton) Activa.myApp.findViewById(R.id.addanswer);
			addanswer.setVisibility(View.VISIBLE);
			addanswer.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					createNewAnswer(question);
				}
			});
			Iterator<Condition> condit = conditions.iterator();
			while (condit.hasNext()) {
				final MultiCondition condition = (MultiCondition) condit.next();
				TableRow buttonLayout = new TableRow(Activa.myApp);
				buttonLayout.setOrientation(TableRow.HORIZONTAL);
				buttonLayout.setGravity(Gravity.CENTER_VERTICAL);
				buttonLayout.setLayoutParams(new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
				ImageButton delete = new ImageButton(Activa.myApp);
				delete.setImageResource(R.drawable.delete);
				delete.setScaleType(ScaleType.FIT_XY);
				delete.setBackgroundResource(R.drawable.iconbg);
				delete.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						Runnable run = new Runnable() {
							@Override
							public void run() {
								handler.sendEmptyMessage(0);
								if (Activa.myQuestControlManager.removeCondition(question.getId(), condition)) {
									handler.sendEmptyMessage(1);
								}
								else handler.sendEmptyMessage(2);
							}
							private Handler handler = new Handler(){
								@Override
								public void handleMessage(Message msg) {
									ImageView animationFrame;
									AnimationDrawable animation;
									switch (msg.what) {
										case 0:
											animationFrame = (ImageView) Activa.myApp.findViewById(R.id.animation);
											animationFrame.setVisibility(View.VISIBLE);
											animationFrame.setBackgroundResource(R.drawable.loading);
											animation = (AnimationDrawable) animationFrame.getBackground();
											animation.start();
											break;
										case 1:
											animationFrame = (ImageView) Activa.myApp.findViewById(R.id.animation);
											animation = (AnimationDrawable) animationFrame.getBackground();
											animation.stop();
											animationFrame.setVisibility(View.GONE);
											Activa.myUIManager.removeInfoPopup();
											String text = ((EditText) Activa.myApp.findViewById(R.id.text)).getText().toString();
											int position = ((Spinner) Activa.myApp.findViewById(R.id.next)).getSelectedItemPosition();
											boolean checked = ((CheckBox) Activa.myApp.findViewById(R.id.optional)).isChecked();
											Activa.myUIManager.modifyQuestion(question, text, position, checked);
											break;
										case 2:
											animationFrame = (ImageView) Activa.myApp.findViewById(R.id.animation);
											animation = (AnimationDrawable) animationFrame.getBackground();
											animation.stop();
											animationFrame.setVisibility(View.GONE);
											Activa.myUIManager.loadInfoPopup("Remove failed");
											break;
									}
								}
							};
						};
						Thread thread = new Thread(run);
						thread.start();
					}
				});			
				ImageButton modify = new ImageButton(Activa.myApp);
				modify.setImageResource(R.drawable.modify);
				modify.setBackgroundResource(R.drawable.iconbg);
				modify.setScaleType(ScaleType.FIT_XY);
				modify.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						modifyMultiCondition(question, condition);
					}
				});
				TextView text = new TextView(Activa.myApp);
				if (condition.getNext() == null) text.append("Finish");
				else text.append("Jump to " + Activa.myQuestControlManager.activeQuestions.get(condition.getNext()).getName());
				text.setWidth(realwidth/2);
				text.setTextSize((float) 20);
				text.setTextColor(Color.BLACK);
				text.setTypeface(Typeface.SANS_SERIF);
				buttonLayout.addView(modify, 40, 40);
				buttonLayout.addView(delete, 40, 40);
				buttonLayout.addView(text);
				((LinearLayout) Activa.myApp.findViewById(R.id.conditions)).addView(buttonLayout);
			}
			ImageButton addcondition = (ImageButton) Activa.myApp.findViewById(R.id.addcondition);
			addcondition.setVisibility(View.VISIBLE);
			addcondition.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					createNewMultiCondition(question);
				}
			});
		}
		else if (question instanceof NumericQuestion) {
			Array<Condition> conditions = Activa.myQuestControlManager.activeConditions.get(question.getId());
			if (conditions == null) conditions = new Array<Condition>();
			((LinearLayout) Activa.myApp.findViewById(R.id.conditions)).setVisibility(View.VISIBLE);
			((RelativeLayout) Activa.myApp.findViewById(R.id.conditionhead)).setVisibility(View.VISIBLE);
			((TextView) Activa.myApp.findViewById(R.id.conditionrequest)).setText(Activa.myLanguageManager.QUEST_CONDITIONS + ": ");
			Iterator<Condition> condit = conditions.iterator();
			while (condit.hasNext()) {
				final NumericCondition condition = (NumericCondition) condit.next();
				TableRow buttonLayout = new TableRow(Activa.myApp);
				buttonLayout.setOrientation(TableRow.HORIZONTAL);
				buttonLayout.setGravity(Gravity.CENTER_VERTICAL);
				buttonLayout.setLayoutParams(new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
				ImageButton delete = new ImageButton(Activa.myApp);
				delete.setImageResource(R.drawable.delete);
				delete.setScaleType(ScaleType.FIT_XY);
				delete.setBackgroundResource(R.drawable.iconbg);
				delete.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						Runnable run = new Runnable() {
							@Override
							public void run() {
								handler.sendEmptyMessage(0);
								if (Activa.myQuestControlManager.removeCondition(question.getId(), condition)) {
									handler.sendEmptyMessage(1);
								}
								else handler.sendEmptyMessage(2);
							}
							private Handler handler = new Handler(){
								@Override
								public void handleMessage(Message msg) {
									ImageView animationFrame;
									AnimationDrawable animation;
									switch (msg.what) {
										case 0:
											animationFrame = (ImageView) Activa.myApp.findViewById(R.id.animation);
											animationFrame.setVisibility(View.VISIBLE);
											animationFrame.setBackgroundResource(R.drawable.loading);
											animation = (AnimationDrawable) animationFrame.getBackground();
											animation.start();
											break;
										case 1:
											animationFrame = (ImageView) Activa.myApp.findViewById(R.id.animation);
											animation = (AnimationDrawable) animationFrame.getBackground();
											animation.stop();
											animationFrame.setVisibility(View.GONE);
											Activa.myUIManager.removeInfoPopup();
											String text = ((EditText) Activa.myApp.findViewById(R.id.text)).getText().toString();
											int position = ((Spinner) Activa.myApp.findViewById(R.id.next)).getSelectedItemPosition();
											boolean checked = ((CheckBox) Activa.myApp.findViewById(R.id.optional)).isChecked();
											Activa.myUIManager.modifyQuestion(question, text, position, checked);
											break;
										case 2:
											animationFrame = (ImageView) Activa.myApp.findViewById(R.id.animation);
											animation = (AnimationDrawable) animationFrame.getBackground();
											animation.stop();
											animationFrame.setVisibility(View.GONE);
											Activa.myUIManager.loadInfoPopup("Remove failed");
											break;
									}
								}
							};
						};
						Thread thread = new Thread(run);
						thread.start();
					}
				});			
				ImageButton modify = new ImageButton(Activa.myApp);
				modify.setImageResource(R.drawable.modify);
				modify.setBackgroundResource(R.drawable.iconbg);
				modify.setScaleType(ScaleType.FIT_XY);
				modify.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						modifyNumericCondition(question, condition);
					}
				});
				TextView text = new TextView(Activa.myApp);
				if (condition.getNext() == null) text.append("Finish");
				else text.append("Jump to " + Activa.myQuestControlManager.activeQuestions.get(condition.getNext()).getName());
				text.setWidth(realwidth/2);
				text.setTextSize((float) 20);
				text.setTextColor(Color.BLACK);
				text.setTypeface(Typeface.SANS_SERIF);
				buttonLayout.addView(modify, 40, 40);
				buttonLayout.addView(delete, 40, 40);
				buttonLayout.addView(text);
				((LinearLayout) Activa.myApp.findViewById(R.id.conditions)).addView(buttonLayout);
			}
			ImageButton addcondition = (ImageButton) Activa.myApp.findViewById(R.id.addcondition);
			addcondition.setVisibility(View.VISIBLE);
			addcondition.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					createNewNumericCondition(question);
				}
			});
		}
		ImageButton save = (ImageButton) Activa.myApp.findViewById(R.id.help);
		save.setVisibility(View.VISIBLE);
		save.setImageResource(R.drawable.save);
		save.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Runnable run = new Runnable() {
					@Override
					public void run() {
						handler.sendEmptyMessage(0);
						question.setName(((EditText) Activa.myApp.findViewById(R.id.text)).getText().toString());
						int position = next.getSelectedItemPosition();
						question.setNext(idsArray.get(position));
						question.setOptional(((CheckBox) Activa.myApp.findViewById(R.id.optional)).isSelected());
						if (Activa.myQuestControlManager.updateQuestion(question)) {
							handler.sendEmptyMessage(1);
						}
						else handler.sendEmptyMessage(2);
					}
					private Handler handler = new Handler(){
						@Override
						public void handleMessage(Message msg) {
							ImageView animationFrame;
							AnimationDrawable animation;
							switch (msg.what) {
								case 0:
									animationFrame = (ImageView) Activa.myApp.findViewById(R.id.animation);
									animationFrame.setVisibility(View.VISIBLE);
									animationFrame.setBackgroundResource(R.drawable.loading);
									animation = (AnimationDrawable) animationFrame.getBackground();
									animation.start();
									break;
								case 1:
									animationFrame = (ImageView) Activa.myApp.findViewById(R.id.animation);
									animation = (AnimationDrawable) animationFrame.getBackground();
									animation.stop();
									animationFrame.setVisibility(View.GONE);
									Activa.myUIManager.removeInfoPopup();
									Activa.myUIManager.modifyQuestionnaire(Activa.myQuestControlManager.activeQuestionnaire);
									break;
								case 2:
									animationFrame = (ImageView) Activa.myApp.findViewById(R.id.animation);
									animation = (AnimationDrawable) animationFrame.getBackground();
									animation.stop();
									animationFrame.setVisibility(View.GONE);
									Activa.myUIManager.modifyQuestionnaire(Activa.myQuestControlManager.activeQuestionnaire);
									Activa.myUIManager.loadInfoPopup("Update failed");
									break;
							}
						}
					};
				};
				Thread thread = new Thread(run);
				thread.start();
			}
		});
		ImageButton exit = (ImageButton) Activa.myApp.findViewById(R.id.previous);
		exit.setVisibility(View.VISIBLE);
		exit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				modifyQuestionnaire(Activa.myQuestControlManager.activeQuestionnaire);
			}
		});
	}
	
	public void createNewAnswer(final Question question) {
		LayoutInflater inflater = (LayoutInflater) Activa.myApp.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		final View layout = inflater.inflate(R.layout.toastaddanswer,
		                               (ViewGroup) Activa.myApp.findViewById(R.id.toastaddanswerroot));
		TextView title = (TextView) layout.findViewById(R.id.addtitle);
		title.setText(Activa.myLanguageManager.QUEST_CREATE_ANSWER);
		TextView namereq = (TextView) layout.findViewById(R.id.namerequested);
		namereq.setText(Activa.myLanguageManager.QUEST_TEXT + ": ");
		TextView valuereq = (TextView) layout.findViewById(R.id.valuerequest);
		valuereq.setText(Activa.myLanguageManager.QUEST_WEIGHT + ": ");
		ImageButton image = (ImageButton) layout.findViewById(R.id.addeventok);
		((EditText) layout.findViewById(R.id.value)).setText("0.0");
		Builder builder = new AlertDialog.Builder(Activa.myApp);
		builder.setView(layout);
		final AlertDialog alertDialog = builder.create();
		alertDialog.show();
		image.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				final Answer answer = new Answer();
				answer.setName(((EditText) layout.findViewById(R.id.name)).getText().toString());
				try {
					double value = Double.parseDouble(((EditText) layout.findViewById(R.id.value)).getText().toString());
					answer.setValue(value);
				} catch (Exception e) {
					((EditText) layout.findViewById(R.id.value)).setText("");
					Toast toast = Toast.makeText(Activa.myApp, "The value must be a number", 3000);
					toast.show();
				}
				Runnable run = new Runnable() {
					@Override
					public void run() {
						handler.sendEmptyMessage(0);
						if (Activa.myQuestControlManager.addAnswer(question.getId(), answer)) {
							handler.sendEmptyMessage(1);
						}
						else handler.sendEmptyMessage(2);
					}
					private Handler handler = new Handler(){
						@Override
						public void handleMessage(Message msg) {
							ImageView animationFrame;
							AnimationDrawable animation;
							switch (msg.what) {
								case 0:
									animationFrame = (ImageView) Activa.myApp.findViewById(R.id.animation);
									animationFrame.setVisibility(View.VISIBLE);
									animationFrame.setBackgroundResource(R.drawable.loading);
									animation = (AnimationDrawable) animationFrame.getBackground();
									animation.start();
									break;
								case 1:
									animationFrame = (ImageView) Activa.myApp.findViewById(R.id.animation);
									animation = (AnimationDrawable) animationFrame.getBackground();
									animation.stop();
									animationFrame.setVisibility(View.GONE);
									Activa.myUIManager.removeInfoPopup();
									String text = ((EditText) Activa.myApp.findViewById(R.id.text)).getText().toString();
									int position = ((Spinner) Activa.myApp.findViewById(R.id.next)).getSelectedItemPosition();
									boolean checked = ((CheckBox) Activa.myApp.findViewById(R.id.optional)).isChecked();
									Activa.myUIManager.modifyQuestion(question, text, position, checked);
									break;
								case 2:
									animationFrame = (ImageView) Activa.myApp.findViewById(R.id.animation);
									animation = (AnimationDrawable) animationFrame.getBackground();
									animation.stop();
									animationFrame.setVisibility(View.GONE);
									Activa.myUIManager.loadInfoPopup("Add failed");
									break;
							}
						}
					};
				};
				Thread thread = new Thread(run);
				thread.start();
				alertDialog.cancel();
			}
		});
	}
	
	public void modifyAnswer(final Question question, final Answer answer) {
		LayoutInflater inflater = (LayoutInflater) Activa.myApp.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		final View layout = inflater.inflate(R.layout.toastaddanswer,
		                               (ViewGroup) Activa.myApp.findViewById(R.id.toastaddanswerroot));
		TextView title = (TextView) layout.findViewById(R.id.addtitle);
		title.setText(Activa.myLanguageManager.QUEST_CREATE_ANSWER);
		TextView namereq = (TextView) layout.findViewById(R.id.namerequested);
		namereq.setText(Activa.myLanguageManager.QUEST_TEXT + ": ");
		((EditText) layout.findViewById(R.id.name)).setText(answer.getName());
		TextView valuereq = (TextView) layout.findViewById(R.id.valuerequest);
		valuereq.setText(Activa.myLanguageManager.QUEST_WEIGHT + ": ");
		((EditText) layout.findViewById(R.id.value)).setText("" + answer.getValue());
		ImageButton image = (ImageButton) layout.findViewById(R.id.addeventok);
		Builder builder = new AlertDialog.Builder(Activa.myApp);
		builder.setView(layout);
		final AlertDialog alertDialog = builder.create();
		alertDialog.show();
		image.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				answer.setName(((EditText) layout.findViewById(R.id.name)).getText().toString());
				try {
					double value = Double.parseDouble(((EditText) layout.findViewById(R.id.value)).getText().toString());
					answer.setValue(value);
				} catch (Exception e) {
					((EditText) layout.findViewById(R.id.value)).setText("");
					Toast toast = Toast.makeText(Activa.myApp, "The weight must be a number. Now set to 0.0", 3000);
					toast.show();
				}
				Runnable run = new Runnable() {
					@Override
					public void run() {
						handler.sendEmptyMessage(0);
						if (Activa.myQuestControlManager.updateAnswer(question.getId(), answer)) {
							handler.sendEmptyMessage(1);
						}
						else handler.sendEmptyMessage(2);
					}
					private Handler handler = new Handler(){
						@Override
						public void handleMessage(Message msg) {
							ImageView animationFrame;
							AnimationDrawable animation;
							switch (msg.what) {
								case 0:
									animationFrame = (ImageView) Activa.myApp.findViewById(R.id.animation);
									animationFrame.setVisibility(View.VISIBLE);
									animationFrame.setBackgroundResource(R.drawable.loading);
									animation = (AnimationDrawable) animationFrame.getBackground();
									animation.start();
									break;
								case 1:
									animationFrame = (ImageView) Activa.myApp.findViewById(R.id.animation);
									animation = (AnimationDrawable) animationFrame.getBackground();
									animation.stop();
									animationFrame.setVisibility(View.GONE);
									Activa.myUIManager.removeInfoPopup();
									String text = ((EditText) Activa.myApp.findViewById(R.id.text)).getText().toString();
									int position = ((Spinner) Activa.myApp.findViewById(R.id.next)).getSelectedItemPosition();
									boolean checked = ((CheckBox) Activa.myApp.findViewById(R.id.optional)).isChecked();
									Activa.myUIManager.modifyQuestion(question, text, position, checked);
									break;
								case 2:
									animationFrame = (ImageView) Activa.myApp.findViewById(R.id.animation);
									animation = (AnimationDrawable) animationFrame.getBackground();
									animation.stop();
									animationFrame.setVisibility(View.GONE);
									Activa.myUIManager.loadInfoPopup("Updating failed");
									break;
							}
						}
					};
				};
				Thread thread = new Thread(run);
				thread.start();
				alertDialog.cancel();
			}
		});
	}
	
	public void createNewMultiCondition(final Question question) {
		int selectedByFirst = 0, i = 0;
		final MultiCondition condition = new MultiCondition();
		final List<Answer> answersToAdd = new ArrayList<Answer>();
		LayoutInflater inflater = (LayoutInflater) Activa.myApp.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		final View layout = inflater.inflate(R.layout.toastaddcondition,
		                               (ViewGroup) Activa.myApp.findViewById(R.id.toastaddconditionroot));
		((TextView) layout.findViewById(R.id.addtitle)).setText(Activa.myLanguageManager.QUEST_CREATE_CONDITION);
		((TextView) layout.findViewById(R.id.nextrequest)).setText(Activa.myLanguageManager.QUEST_NEXT + ": ");
		((TextView) layout.findViewById(R.id.answersrequest)).setText(Activa.myLanguageManager.QUEST_ANSWERS + ": ");
		final Spinner next = (Spinner)layout.findViewById(R.id.nextquest);
		ArrayList<String> spinnerArray = new ArrayList<String>();
		Enumeration<Question> questions = Activa.myQuestControlManager.activeQuestions.elements();
	    spinnerArray.add("-");
	    while (questions.hasMoreElements()) {
	    	i++;
	    	Question questToAdd = questions.nextElement();
	    	if (question.getId() == questToAdd.getId()) continue;
	    	if (question.getName().equals("")) continue;
	    	spinnerArray.add(questToAdd.getName());
	    }
	    ArrayAdapter<String> adapter = new ArrayAdapter<String>(Activa.myApp, android.R.layout.simple_spinner_item, spinnerArray);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		next.setAdapter(adapter);
		next.setSelection(selectedByFirst);
	    final LinearLayout answerList = (LinearLayout) layout.findViewById(R.id.answers);
	    Iterator<Answer> answers = Activa.myQuestControlManager.activeAnswers.get(question.getId()).iterator();
	    while (answers.hasNext()) {
			final Answer answer = answers.next();
			CheckBox checkbox = new CheckBox(Activa.myApp);
			checkbox.setText(answer.getName());
			checkbox.setOnCheckedChangeListener(new android.widget.CompoundButton.OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					if (isChecked) answersToAdd.add(answer);
					else answersToAdd.remove(answer);
				}
			});
			answerList.addView(checkbox);
		} 
		ImageButton image = (ImageButton) layout.findViewById(R.id.addeventok);
		Builder builder = new AlertDialog.Builder(Activa.myApp);
		builder.setView(layout);
		final AlertDialog alertDialog = builder.create();
		alertDialog.show();
		image.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				condition.getAnswers().clear();
				condition.getAnswers().addAll(answersToAdd);
				int position = next.getSelectedItemPosition();
				condition.setNext(Activa.myQuestControlManager.searchQuestionByName(next.getItemAtPosition(position).toString()));
				Runnable run = new Runnable() {
					@Override
					public void run() {
						handler.sendEmptyMessage(0);
						if (Activa.myQuestControlManager.addCondition(question.getId(), condition)) {
							handler.sendEmptyMessage(1);
						}
						else handler.sendEmptyMessage(2);
					}
					private Handler handler = new Handler(){
						@Override
						public void handleMessage(Message msg) {
							ImageView animationFrame;
							AnimationDrawable animation;
							switch (msg.what) {
								case 0:
									animationFrame = (ImageView) Activa.myApp.findViewById(R.id.animation);
									animationFrame.setVisibility(View.VISIBLE);
									animationFrame.setBackgroundResource(R.drawable.loading);
									animation = (AnimationDrawable) animationFrame.getBackground();
									animation.start();
									break;
								case 1:
									animationFrame = (ImageView) Activa.myApp.findViewById(R.id.animation);
									animation = (AnimationDrawable) animationFrame.getBackground();
									animation.stop();
									animationFrame.setVisibility(View.GONE);
									Activa.myUIManager.removeInfoPopup();
									String text = ((EditText) Activa.myApp.findViewById(R.id.text)).getText().toString();
									int position = ((Spinner) Activa.myApp.findViewById(R.id.next)).getSelectedItemPosition();
									boolean checked = ((CheckBox) Activa.myApp.findViewById(R.id.optional)).isChecked();
									Activa.myUIManager.modifyQuestion(question, text, position, checked);
									break;
								case 2:
									animationFrame = (ImageView) Activa.myApp.findViewById(R.id.animation);
									animation = (AnimationDrawable) animationFrame.getBackground();
									animation.stop();
									animationFrame.setVisibility(View.GONE);
									Activa.myUIManager.loadInfoPopup("Add failed");
									break;
							}
						}
					};
				};
				Thread thread = new Thread(run);
				thread.start();
				alertDialog.cancel();
			}
		});
	}
	
	public void modifyMultiCondition(final Question question, final MultiCondition condition) {
		int selectedByFirst = 0, i = 0;
		final List<Answer> answersToAdd = new ArrayList<Answer>();
		LayoutInflater inflater = (LayoutInflater) Activa.myApp.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		final View layout = inflater.inflate(R.layout.toastaddcondition,
		                               (ViewGroup) Activa.myApp.findViewById(R.id.toastaddconditionroot));
		((TextView) layout.findViewById(R.id.addtitle)).setText(Activa.myLanguageManager.QUEST_CREATE_CONDITION);
		((TextView) layout.findViewById(R.id.nextrequest)).setText(Activa.myLanguageManager.QUEST_NEXT + ": ");
		((TextView) layout.findViewById(R.id.answersrequest)).setText(Activa.myLanguageManager.QUEST_ANSWERS + ": ");
		final Spinner next = (Spinner) layout.findViewById(R.id.nextquest);
		ArrayList<String> spinnerArray = new ArrayList<String>();
		Enumeration<Question> questions = Activa.myQuestControlManager.activeQuestions.elements();
	    spinnerArray.add("-");
	    while (questions.hasMoreElements()) {
	    	i++;
	    	Question questToAdd = questions.nextElement();
	    	if (condition.getNext() != null)
	    		if (questToAdd.getId().equals(condition.getNext())) selectedByFirst = i;
	    	if (question.getId() == questToAdd.getId()) continue;
	    	if (question.getName().equals("")) continue;
	    	spinnerArray.add(questToAdd.getName());
	    }
	    ArrayAdapter<String> adapter = new ArrayAdapter<String>(Activa.myApp, android.R.layout.simple_spinner_item, spinnerArray);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		next.setAdapter(adapter);
		next.setSelection(selectedByFirst);
	    final LinearLayout answerList = (LinearLayout) layout.findViewById(R.id.answers);
	    Iterator<Answer> answers = Activa.myQuestControlManager.activeAnswers.get(question.getId()).iterator();
	    while (answers.hasNext()) {
			final Answer answer = answers.next();
			CheckBox checkbox = new CheckBox(Activa.myApp);
			checkbox.setText(answer.getName());
			for (Answer ans : condition.getAnswers()) {
				if (ans.getId().equals(answer.getId())) {
					checkbox.setChecked(true);
					answersToAdd.add(answer);
				}
			}
			checkbox.setOnCheckedChangeListener(new android.widget.CompoundButton.OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					if (isChecked) answersToAdd.add(answer);
					else answersToAdd.remove(answer);
				}
			});
			answerList.addView(checkbox);
		} 
		ImageButton image = (ImageButton) layout.findViewById(R.id.addeventok);
		Builder builder = new AlertDialog.Builder(Activa.myApp);
		builder.setView(layout);
		final AlertDialog alertDialog = builder.create();
		alertDialog.show();
		image.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				condition.getAnswers().clear();
				condition.getAnswers().addAll(answersToAdd);
				int position = next.getSelectedItemPosition();
				condition.setNext(Activa.myQuestControlManager.searchQuestionByName(next.getItemAtPosition(position).toString()));
				Runnable run = new Runnable() {
					@Override
					public void run() {
						handler.sendEmptyMessage(0);
						if (Activa.myQuestControlManager.updateCondition(question.getId(), condition)) {
							handler.sendEmptyMessage(1);
						}
						else handler.sendEmptyMessage(2);
					}
					private Handler handler = new Handler(){
						@Override
						public void handleMessage(Message msg) {
							ImageView animationFrame;
							AnimationDrawable animation;
							switch (msg.what) {
								case 0:
									animationFrame = (ImageView) Activa.myApp.findViewById(R.id.animation);
									animationFrame.setVisibility(View.VISIBLE);
									animationFrame.setBackgroundResource(R.drawable.loading);
									animation = (AnimationDrawable) animationFrame.getBackground();
									animation.start();
									break;
								case 1:
									animationFrame = (ImageView) Activa.myApp.findViewById(R.id.animation);
									animation = (AnimationDrawable) animationFrame.getBackground();
									animation.stop();
									animationFrame.setVisibility(View.GONE);
									Activa.myUIManager.removeInfoPopup();
									String text = ((EditText) Activa.myApp.findViewById(R.id.text)).getText().toString();
									int position = ((Spinner) Activa.myApp.findViewById(R.id.next)).getSelectedItemPosition();
									boolean checked = ((CheckBox) Activa.myApp.findViewById(R.id.optional)).isChecked();
									Activa.myUIManager.modifyQuestion(question, text, position, checked);
									break;
								case 2:
									animationFrame = (ImageView) Activa.myApp.findViewById(R.id.animation);
									animation = (AnimationDrawable) animationFrame.getBackground();
									animation.stop();
									animationFrame.setVisibility(View.GONE);
									Activa.myUIManager.loadInfoPopup("Add failed");
									break;
							}
						}
					};
				};
				Thread thread = new Thread(run);
				thread.start();
				alertDialog.cancel();
			}
		});
	}
	
	public void createNewNumericCondition(final Question question) {
		int selectedByFirst = 0, i = 0;
		final NumericCondition condition = new NumericCondition();
		LayoutInflater inflater = (LayoutInflater) Activa.myApp.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		final View layout = inflater.inflate(R.layout.toastaddnumericcondition,
		                               (ViewGroup) Activa.myApp.findViewById(R.id.toastaddnumericconditionroot));
		((TextView) layout.findViewById(R.id.addtitle)).setText(Activa.myLanguageManager.QUEST_CREATE_CONDITION);
		((TextView) layout.findViewById(R.id.nextrequest)).setText(Activa.myLanguageManager.QUEST_NEXT + ": ");
		((TextView) layout.findViewById(R.id.limitrequest)).setText(Activa.myLanguageManager.QUEST_THREESHOLD + ": ");
		final Spinner next = (Spinner) layout.findViewById(R.id.nextquest);
		ArrayList<String> spinnerArray = new ArrayList<String>();
		Enumeration<Question> questions = Activa.myQuestControlManager.activeQuestions.elements();
	    spinnerArray.add("-");
	    while (questions.hasMoreElements()) {
	    	i++;
	    	Question questToAdd = questions.nextElement();
	    	if (question.getId() == questToAdd.getId()) continue;
	    	if (question.getName().equals("")) continue;
	    	spinnerArray.add(questToAdd.getName());
	    }
	    ArrayAdapter<String> adapter = new ArrayAdapter<String>(Activa.myApp, android.R.layout.simple_spinner_item, spinnerArray);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		next.setAdapter(adapter);
		next.setSelection(selectedByFirst);
	    final SeekBar threshold = (SeekBar)layout.findViewById(R.id.limitquest);
		ImageButton image = (ImageButton) layout.findViewById(R.id.addeventok);
		Builder builder = new AlertDialog.Builder(Activa.myApp);
		builder.setView(layout);
		final AlertDialog alertDialog = builder.create();
		alertDialog.show();
		image.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				condition.setThreshold(((Integer)threshold.getProgress()).doubleValue()/10);
				int position = next.getSelectedItemPosition();
				condition.setNext(Activa.myQuestControlManager.searchQuestionByName(next.getItemAtPosition(position).toString()));
				Runnable run = new Runnable() {
					@Override
					public void run() {
						handler.sendEmptyMessage(0);
						if (Activa.myQuestControlManager.addCondition(question.getId(), condition)) {
							handler.sendEmptyMessage(1);
						}
						else handler.sendEmptyMessage(2);
					}
					private Handler handler = new Handler(){
						@Override
						public void handleMessage(Message msg) {
							ImageView animationFrame;
							AnimationDrawable animation;
							switch (msg.what) {
								case 0:
									Activa.myUIManager.loadInfoPopupWithoutExiting("Adding answer ...");
									animationFrame = (ImageView) Activa.myApp.findViewById(R.id.popupImage);
									animationFrame.setVisibility(View.VISIBLE);
									animationFrame.setBackgroundResource(R.drawable.loading);
									animation = (AnimationDrawable) animationFrame.getBackground();
									animation.start();
									break;
								case 1:
									animationFrame = (ImageView) Activa.myApp.findViewById(R.id.popupImage);
									animation = (AnimationDrawable) animationFrame.getBackground();
									animation.stop();
									animationFrame.setVisibility(View.GONE);
									Activa.myUIManager.removeInfoPopup();
									String text = ((EditText) Activa.myApp.findViewById(R.id.text)).getText().toString();
									int position = ((Spinner) Activa.myApp.findViewById(R.id.next)).getSelectedItemPosition();
									boolean checked = ((CheckBox) Activa.myApp.findViewById(R.id.optional)).isChecked();
									Activa.myUIManager.modifyQuestion(question, text, position, checked);
									break;
								case 2:
									animationFrame = (ImageView) Activa.myApp.findViewById(R.id.popupImage);
									animation = (AnimationDrawable) animationFrame.getBackground();
									animation.stop();
									animationFrame.setVisibility(View.GONE);
									Activa.myUIManager.loadInfoPopup("Add failed");
									break;
							}
						}
					};
				};
				Thread thread = new Thread(run);
				thread.start();
				alertDialog.cancel();
			}
		});
	}
	
	public void modifyNumericCondition(final Question question, final NumericCondition condition) {
		int selectedByFirst = 0, i = 0;
		LayoutInflater inflater = (LayoutInflater) Activa.myApp.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		final View layout = inflater.inflate(R.layout.toastaddnumericcondition,
		                               (ViewGroup) Activa.myApp.findViewById(R.id.toastaddnumericconditionroot));
		((TextView) layout.findViewById(R.id.addtitle)).setText(Activa.myLanguageManager.QUEST_CREATE_CONDITION);
		((TextView) layout.findViewById(R.id.nextrequest)).setText(Activa.myLanguageManager.QUEST_NEXT + ": ");
		((TextView) layout.findViewById(R.id.limitrequest)).setText(Activa.myLanguageManager.QUEST_THREESHOLD + ": ");
		final Spinner next = (Spinner) layout.findViewById(R.id.nextquest);
		ArrayList<String> spinnerArray = new ArrayList<String>();
		Enumeration<Question> questions = Activa.myQuestControlManager.activeQuestions.elements();
	    spinnerArray.add("-");
	    while (questions.hasMoreElements()) {
	    	i++;
	    	Question questToAdd = questions.nextElement();
	    	if (question.getNext() != null)
	    		if (questToAdd.getId().equals(question.getNext())) selectedByFirst = i;
	    	if (question.getId() == questToAdd.getId()) continue;
	    	if (question.getName().equals("")) continue;
	    	spinnerArray.add(questToAdd.getName());
	    }
	    ArrayAdapter<String> adapter = new ArrayAdapter<String>(Activa.myApp, android.R.layout.simple_spinner_item, spinnerArray);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		next.setAdapter(adapter);
		next.setSelection(selectedByFirst);
	    final SeekBar threshold = (SeekBar)layout.findViewById(R.id.limitquest);
	    threshold.setProgress((int)(condition.getThreshold()*10));
		ImageButton image = (ImageButton) layout.findViewById(R.id.addeventok);
		Builder builder = new AlertDialog.Builder(Activa.myApp);
		builder.setView(layout);
		final AlertDialog alertDialog = builder.create();
		alertDialog.show();
		image.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				condition.setThreshold(((Integer)threshold.getProgress()).doubleValue()/10);
				int position = next.getSelectedItemPosition();
				condition.setNext(Activa.myQuestControlManager.searchQuestionByName(next.getItemAtPosition(position).toString()));
				Runnable run = new Runnable() {
					@Override
					public void run() {
						handler.sendEmptyMessage(0);
						if (Activa.myQuestControlManager.addCondition(question.getId(), condition)) {
							handler.sendEmptyMessage(1);
						}
						else handler.sendEmptyMessage(2);
					}
					private Handler handler = new Handler(){
						@Override
						public void handleMessage(Message msg) {
							ImageView animationFrame;
							AnimationDrawable animation;
							switch (msg.what) {
								case 0:
									Activa.myUIManager.loadInfoPopupWithoutExiting("Adding answer ...");
									animationFrame = (ImageView) Activa.myApp.findViewById(R.id.popupImage);
									animationFrame.setVisibility(View.VISIBLE);
									animationFrame.setBackgroundResource(R.drawable.loading);
									animation = (AnimationDrawable) animationFrame.getBackground();
									animation.start();
									break;
								case 1:
									animationFrame = (ImageView) Activa.myApp.findViewById(R.id.popupImage);
									animation = (AnimationDrawable) animationFrame.getBackground();
									animation.stop();
									animationFrame.setVisibility(View.GONE);
									Activa.myUIManager.removeInfoPopup();
									String text = ((EditText) Activa.myApp.findViewById(R.id.text)).getText().toString();
									int position = ((Spinner) Activa.myApp.findViewById(R.id.next)).getSelectedItemPosition();
									boolean checked = ((CheckBox) Activa.myApp.findViewById(R.id.optional)).isChecked();
									Activa.myUIManager.modifyQuestion(question, text, position, checked);
									break;
								case 2:
									animationFrame = (ImageView) Activa.myApp.findViewById(R.id.popupImage);
									animation = (AnimationDrawable) animationFrame.getBackground();
									animation.stop();
									animationFrame.setVisibility(View.GONE);
									Activa.myUIManager.loadInfoPopup("Add failed");
									break;
							}
						}
					};
				};
				Thread thread = new Thread(run);
				thread.start();
				alertDialog.cancel();
			}
		});
	}
	
	public void loadScheduleDayForPatientMeas(final Date dategiven) {
		TextView time;
		com.o2hlink.pimtools.patient.Event event = null; 
		Date date = new Date((dategiven.getTime()/3600000)*3600000);
		date.setHours(0);
		Date dateLater = new Date(date.getTime());
		dateLater.setHours(dateLater.getHours() + 1);
		TableRow buttonLayout;
		this.state = UI_STATE_SCHEDULEFORPATIENTMEAS;
		if (Activa.myMenu != null) {
			Activa.myMenu.clear();
			Activa.myInflater.inflate(R.menu.scheduleforpatientformeas, Activa.myMenu);
		}
		Hashtable<Date, com.o2hlink.pimtools.patient.Event> eventsOrdered = new Hashtable<Date,com.o2hlink.pimtools.patient.Event>();
		Vector<Date> datesOrdered = new Vector<Date>();
		Enumeration<com.o2hlink.pimtools.patient.Event> enumer = Activa.myPatientManager.currentPatMeasEventSet.elements();
		while (enumer.hasMoreElements()) {
			com.o2hlink.pimtools.patient.Event temp = enumer.nextElement();
			while (datesOrdered.contains(temp.date)) temp.date.setTime(temp.date.getTime() + 1);
			Timestamp stamp = new Timestamp(temp.date.getTime());
			datesOrdered.add(stamp);
			eventsOrdered.put(stamp, temp);
		}
		Collections.sort(datesOrdered);
		Activa.myApp.setContentView(R.layout.scheduleday);
		((TextView) Activa.myApp.findViewById(R.id.startText)).setText(Activa.myLanguageManager.CALENDAR_MEAS);
		ImageButton back = (ImageButton) Activa.myApp.findViewById(R.id.previous);
		ImageButton prev = (ImageButton) Activa.myApp.findViewById(R.id.previousday);
		ImageButton next = (ImageButton) Activa.myApp.findViewById(R.id.nextday);
		TextView dateText = (TextView) Activa.myApp.findViewById(R.id.date);
		dateText.setText(ActivaUtil.dateToReadableString(date));
		TableLayout schedule = (TableLayout)Activa.myApp.findViewById(R.id.schedule);
		for (int i = 0; i < 24; i++) {	
			buttonLayout = new TableRow(Activa.myApp);
			buttonLayout.setOrientation(TableRow.HORIZONTAL);
			buttonLayout.setGravity(Gravity.CENTER_VERTICAL);
			buttonLayout.setLayoutParams(new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, 40));			
			time = new TextView(Activa.myApp);
			time.setText(String.format("%02d:00", date.getHours()));
			time.setPadding(5, 10, 5, 10);
			time.setTextSize(20);
			time.setTextColor(Color.BLACK);
			time.setTypeface(Typeface.SANS_SERIF);
			buttonLayout.addView(time);
			for(int j = 0; j < datesOrdered.size(); j++) {
				event = eventsOrdered.get(datesOrdered.get(j));
				final String id = event.id;
				if (date.compareTo(event.getDate()) == 0) {
					TextView activity = new TextView(Activa.myApp);
					activity.setLayoutParams(new TableRow.LayoutParams(200, LayoutParams.WRAP_CONTENT));
					activity.setText(event.name);
					activity.setPadding(5, 10, 5, 10);
					activity.setTextSize(20);
					activity.setTextColor(Color.BLACK);
					activity.setTypeface(Typeface.SANS_SERIF);
					ImageView button = new ImageView(Activa.myApp);
					if (event.type == 2) button.setBackgroundResource(R.drawable.event);
					else switch (event.state) {
						case 0:
							button.setBackgroundResource(R.drawable.oksmall);
							break;
						case 1:
							button.setBackgroundResource(R.drawable.cancel);
							break;
						case 2:
							button.setBackgroundResource(R.drawable.nextsmall);
							break;
					}
					buttonLayout.addView(activity);
					buttonLayout.addView(button);	
					time.setOnClickListener(new OnClickListener() {				
						@Override
						public void onClick(View v) {
							if (Activa.myMenu != null) Activa.myMenu.clear();
							Activa.myPatientManager.currentPatMeasEventSet.get(id).doActivity();
						}
					});	
					activity.setOnClickListener(new OnClickListener() {				
						@Override
						public void onClick(View v) {
							if (Activa.myMenu != null) Activa.myMenu.clear();
							Activa.myPatientManager.currentPatMeasEventSet.get(id).doActivity();
						}
					});
					button.setOnClickListener(new OnClickListener() {				
						@Override
						public void onClick(View v) {
							if (Activa.myMenu != null) Activa.myMenu.clear();
							Activa.myPatientManager.currentPatMeasEventSet.get(id).doActivity();
						}
					});	
					buttonLayout.setOnClickListener(new OnClickListener() {				
						@Override
						public void onClick(View v) {
							if (Activa.myMenu != null) Activa.myMenu.clear();
							Activa.myPatientManager.currentPatMeasEventSet.get(id).doActivity();
						}
					});
				}
				else if ((date.compareTo(event.getDate()) < 0)&&(dateLater.compareTo(event.getDate()) > 0)) {
					schedule.addView(buttonLayout);
					View separator = new View(Activa.myApp);
					LayoutParams separatorLayout = new LayoutParams(android.view.ViewGroup.LayoutParams.FILL_PARENT, 1);
					separator.setLayoutParams(separatorLayout);
					separator.setBackgroundColor(Color.BLACK);
					schedule.addView(separator);
					buttonLayout = new TableRow(Activa.myApp);
					buttonLayout.setOrientation(TableRow.HORIZONTAL);
					buttonLayout.setGravity(Gravity.CENTER_VERTICAL);
					buttonLayout.setLayoutParams(new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, 40));			
					time = new TextView(Activa.myApp);
					time.setPadding(5, 10, 5, 10);
					time.setTextSize(20);
					time.setTextColor(Color.BLACK);
					time.setTypeface(Typeface.SANS_SERIF);
					buttonLayout.addView(time);
					time.setText(String.format("%02d:%02d", event.date.getHours(), event.date.getMinutes()));
					TextView activity = new TextView(Activa.myApp);
					activity.setLayoutParams(new TableRow.LayoutParams(200, LayoutParams.WRAP_CONTENT));
					activity.setText(event.name);
					activity.setWidth(200);
					activity.setPadding(5, 10, 5, 10);
					activity.setTextSize(20);
					activity.setTextColor(Color.BLACK);
					activity.setTypeface(Typeface.SANS_SERIF);
					ImageView button = new ImageView(Activa.myApp);
					if (event.type == 2) button.setBackgroundResource(R.drawable.event);
					else switch (event.state) {
						case 0:
							button.setBackgroundResource(R.drawable.oksmall);
							break;
						case 1:
							button.setBackgroundResource(R.drawable.cancel);
							break;
						case 2:
							button.setBackgroundResource(R.drawable.nextsmall);
							break;
					}
					buttonLayout.addView(activity);
					buttonLayout.addView(button);	
					time.setOnClickListener(new OnClickListener() {				
						@Override
						public void onClick(View v) {
							if (Activa.myMenu != null) Activa.myMenu.clear();
							Activa.myPatientManager.currentPatMeasEventSet.get(id).doActivity();
						}
					});	
					activity.setOnClickListener(new OnClickListener() {				
						@Override
						public void onClick(View v) {
							if (Activa.myMenu != null) Activa.myMenu.clear();
							Activa.myPatientManager.currentPatMeasEventSet.get(id).doActivity();
						}
					});
					button.setOnClickListener(new OnClickListener() {				
						@Override
						public void onClick(View v) {
							if (Activa.myMenu != null) Activa.myMenu.clear();
							Activa.myPatientManager.currentPatMeasEventSet.get(id).doActivity();
						}
					});	
					buttonLayout.setOnClickListener(new OnClickListener() {				
						@Override
						public void onClick(View v) {
							if (Activa.myMenu != null) Activa.myMenu.clear();
							Activa.myPatientManager.currentPatMeasEventSet.get(id).doActivity();
						}
					});	
				}
			}
			schedule.addView(buttonLayout);
			View separator = new View(Activa.myApp);
			LayoutParams separatorLayout = new LayoutParams(android.view.ViewGroup.LayoutParams.FILL_PARENT, 1);
			separator.setLayoutParams(separatorLayout);
			separator.setBackgroundColor(Color.BLACK);
			
			schedule.addView(separator);

			date.setHours(date.getHours() + 1);
			dateLater.setHours(dateLater.getHours() + 1);
		}
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Activa.myUIManager.loadPatientMenu(Activa.myPatientManager.currentPat);
			}
		});
		prev.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Runnable run = new Runnable() {
					@Override
					public void run() {
						handler.sendEmptyMessage(0);
						Date start = new Date((dategiven.getTime()/86400000)*86400000);
						Date end = new Date(start.getTime());
						start.setDate(start.getDate() - 1);
						if (Activa.myProtocolManager.getMeasuringEvents(Activa.myPatientManager.currentPat.getId(), start, end))
							handler.sendEmptyMessage(1);
						else handler.sendEmptyMessage(2);
					}
					private Handler handler = new Handler() {
						@Override
						public void handleMessage(Message msg) {
							Date nextDate;
							switch (msg.what) {
								case 0:
									ImageView animationFrame = (ImageView) Activa.myApp.findViewById(R.id.animation);
									animationFrame.setVisibility(View.VISIBLE);
									animationFrame.setBackgroundResource(R.drawable.loading);
									final AnimationDrawable animation = (AnimationDrawable) animationFrame.getBackground();
									animation.start();
									break;
								case 1:
									nextDate = new Date(dategiven.getTime());
									nextDate.setDate(nextDate.getDate() - 1);
									Activa.myUIManager.loadScheduleDayForPatientMeas(nextDate);
									break;
								case 2:
									nextDate = new Date(dategiven.getTime());
									nextDate.setDate(nextDate.getDate() - 1);
									Activa.myUIManager.loadScheduleDayForPatientMeas(nextDate);
									Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.CONNECTION_FAILED);
									break;
							}
						}

					};
				};
				Thread thread = new Thread(run);
				thread.start();
			}
		});
		next.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Runnable run = new Runnable() {
					@Override
					public void run() {
						handler.sendEmptyMessage(0);
						Date start = new Date((dategiven.getTime()/86400000)*86400000);
						start.setDate(start.getDate() + 1);
						Date end = new Date(start.getTime());
						start.setDate(start.getDate() + 1);
						if (Activa.myProtocolManager.getMeasuringEvents(Activa.myPatientManager.currentPat.getId(), start, end))
							handler.sendEmptyMessage(1);
						else handler.sendEmptyMessage(2);
					}
					private Handler handler = new Handler() {
						@Override
						public void handleMessage(Message msg) {
							Date nextDate;
							switch (msg.what) {
								case 0:
									ImageView animationFrame = (ImageView) Activa.myApp.findViewById(R.id.animation);
									animationFrame.setVisibility(View.VISIBLE);
									animationFrame.setBackgroundResource(R.drawable.loading);
									final AnimationDrawable animation = (AnimationDrawable) animationFrame.getBackground();
									animation.start();
									break;
								case 1:
									nextDate = new Date(dategiven.getTime());
									nextDate.setDate(nextDate.getDate() + 1);
									Activa.myUIManager.loadScheduleDayForPatientMeas(nextDate);
									break;
								case 2:
									nextDate = new Date(dategiven.getTime());
									nextDate.setDate(nextDate.getDate() + 1);
									Activa.myUIManager.loadScheduleDayForPatientMeas(nextDate);
									Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.CONNECTION_FAILED);
									break;
							}
						}

					};
				};
				Thread thread = new Thread(run);
				thread.start();
			}
		});
		ImageButton add = (ImageButton) Activa.myApp.findViewById(R.id.help);
		add.setImageResource(R.drawable.plus);
		add.setVisibility(View.VISIBLE);
		add.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				LayoutInflater inflater = (LayoutInflater) Activa.myApp.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				final View layout = inflater.inflate(R.layout.toastaddpatevent,
				                               (ViewGroup) Activa.myApp.findViewById(R.id.toastpataddeventroot));
				TextView title = (TextView) layout.findViewById(R.id.addtitle);
				title.setText(Activa.myLanguageManager.NOTIFICATION_EVENT_TITLE);
				TextView namereq = (TextView) layout.findViewById(R.id.namerequested);
				namereq.setText(Activa.myLanguageManager.NOTIFICATION_EVENT_NAMEREQ);
				TextView descreq = (TextView) layout.findViewById(R.id.descrequest);
				descreq.setText(Activa.myLanguageManager.NOTIFICATION_EVENT_DESC);
				TextView typereq = (TextView) layout.findViewById(R.id.typerequest);
				typereq.setText(Activa.myLanguageManager.NOTIFICATION_EVENT_TYPE);
				final Spinner type = (Spinner) layout.findViewById(R.id.type);
				ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(Activa.myApp, R.array.meastype, android.R.layout.simple_spinner_item);
			    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			    type.setAdapter(adapter);
			    type.setSelection(0);
				TextView startreq = (TextView) layout.findViewById(R.id.startrequest);
				startreq.setText(Activa.myLanguageManager.NOTIFICATION_EVENT_START);
				TextView endreq = (TextView) layout.findViewById(R.id.endrequest);
				endreq.setText(Activa.myLanguageManager.NOTIFICATION_EVENT_END);
				TextView freqreq = (TextView) layout.findViewById(R.id.freqrequest);
				freqreq.setText(Activa.myLanguageManager.NOTIFICATION_EVENT_FREQ);
				final Spinner frequency = (Spinner) layout.findViewById(R.id.freq);
			    ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(Activa.myApp, R.array.freqarray, android.R.layout.simple_spinner_item);
			    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			    frequency.setAdapter(adapter2);
			    ((LinearLayout)layout.findViewById(R.id.freqend)).setVisibility(View.GONE);
			    frequency.setOnItemSelectedListener(new OnItemSelectedListener() {
					@Override
					public void onItemSelected (AdapterView<?> parent, View view, int position, long id) {
						if (position == 0) ((LinearLayout)layout.findViewById(R.id.freqend)).setVisibility(View.GONE);
						else ((LinearLayout)layout.findViewById(R.id.freqend)).setVisibility(View.VISIBLE);
					}
					@Override
					public void onNothingSelected (AdapterView<?> parent) {
						((LinearLayout)layout.findViewById(R.id.freqend)).setVisibility(View.GONE);
					}
				});
			    TextView freqendreq = (TextView) layout.findViewById(R.id.freqendrequest);
				freqendreq.setText(Activa.myLanguageManager.NOTIFICATION_EVENT_FREQEND);
				ImageButton image = (ImageButton) layout.findViewById(R.id.addeventok);
				Builder builder = new AlertDialog.Builder(Activa.myApp);
				builder.setView(layout);
				final AlertDialog alertDialog = builder.create();
				alertDialog.show();
				image.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						final Measurement meas;
						final com.o2hlink.activ8.client.entity.Event eventtoadd = new com.o2hlink.activ8.client.entity.Event();
						eventtoadd.setName(((EditText) layout.findViewById(R.id.name)).getText().toString());
						eventtoadd.setDescription(((EditText) layout.findViewById(R.id.description)).getText().toString());
						switch (type.getSelectedItemPosition()) {
							case 0:
								meas = Measurement.PULSEOXYMETRY;
								break;
							case 1:
								meas = Measurement.SPIROMETRY;
								break;
							case 2:
								meas = Measurement.SIX_MINUTES_WALK;
								break;
							case 3:
								meas = Measurement.WEIGHT_HEIGHT;
								break;
							case 4:
								meas = Measurement.EXERCISE;
								break;
							default:
								meas = null;
								break;
						}
						Date start = new Date(0);
						start.setYear(((DatePicker) layout.findViewById(R.id.startdate)).getYear() - 1900);
						start.setMonth(((DatePicker) layout.findViewById(R.id.startdate)).getMonth());
						start.setDate(((DatePicker) layout.findViewById(R.id.startdate)).getDayOfMonth());
						start.setHours(((TimePicker) layout.findViewById(R.id.starttime)).getCurrentHour());
						start.setMinutes(((TimePicker) layout.findViewById(R.id.starttime)).getCurrentMinute());
						eventtoadd.setStart(start);
						Date end = new Date(0);
						end.setYear(((DatePicker) layout.findViewById(R.id.enddate)).getYear() - 1900);
						end.setMonth(((DatePicker) layout.findViewById(R.id.enddate)).getMonth());
						end.setDate(((DatePicker) layout.findViewById(R.id.enddate)).getDayOfMonth());
						end.setHours(((TimePicker) layout.findViewById(R.id.endtime)).getCurrentHour());
						end.setMinutes(((TimePicker) layout.findViewById(R.id.endtime)).getCurrentMinute());
						eventtoadd.setEnd(end);
						switch (frequency.getSelectedItemPosition()) {
							case 0:
								eventtoadd.setFrequency(EventFrequency.NONE);
								break;
							case 1:
								eventtoadd.setFrequency(EventFrequency.DAILY);
								break;
							case 2:
								eventtoadd.setFrequency(EventFrequency.WEEKLY);
								break;
							case 3:
								eventtoadd.setFrequency(EventFrequency.MONTHLY);
								break;
							case 4:
								eventtoadd.setFrequency(EventFrequency.YEARLY);
								break;
							case 5:
								eventtoadd.setFrequency(EventFrequency.MONWEDFRI);
								break;
							case 6:
								eventtoadd.setFrequency(EventFrequency.TUETHUR);
								break;
							case 7:
								eventtoadd.setFrequency(EventFrequency.WEEKDAY);
								break;
							default:
								eventtoadd.setFrequency(EventFrequency.NONE);
								break;
						}
						Date freqend = new Date(0);
						freqend.setYear(((DatePicker) layout.findViewById(R.id.freqenddate)).getYear() - 1900);
						freqend.setMonth(((DatePicker) layout.findViewById(R.id.freqenddate)).getMonth());
						freqend.setDate(((DatePicker) layout.findViewById(R.id.freqenddate)).getDayOfMonth());
						freqend.setHours(((TimePicker) layout.findViewById(R.id.freqendtime)).getCurrentHour());
						freqend.setMinutes(((TimePicker) layout.findViewById(R.id.freqendtime)).getCurrentMinute());
						eventtoadd.setEndFrequency(freqend);
						final ProgressDialog dialog = ProgressDialog.show(Activa.myApp, Activa.myLanguageManager.NOTIFICATION_EVENT_TITLE, 
								Activa.myLanguageManager.NOTIFICATION_EVENT_ADDING, true);
						dialog.show();
						Runnable run =  new Runnable() {
							@Override
							public void run() {
								if (Activa.myPatientManager.addMeasEvent(Activa.myPatientManager.currentPat.getId(), meas, eventtoadd))
									handler.sendEmptyMessage(1);
								else 
									handler.sendEmptyMessage(0);
							}
							private Handler handler = new Handler() {
								@Override
								public void handleMessage(Message msg) {
									switch (msg.what) {
										case 0:
											Toast toast = Toast.makeText(Activa.myApp, Activa.myLanguageManager.CONNECTION_FAILED, Toast.LENGTH_SHORT);
											Activa.myUIManager.loadScheduleDayForPatientMeas(dategiven);
											dialog.cancel();
											toast.show();
											break;
										case 1:
											Activa.myUIManager.loadScheduleDayForPatientMeas(dategiven);
											dialog.cancel();
											break;
									}
								}

							};
						};
						Thread thread = new Thread(run);
						thread.start();
						alertDialog.cancel();
					}
				});
			}
		});
	}
	
	public void loadScheduleWeekForPatientMeas(final Date dategiven){
		TextView time;
		com.o2hlink.pimtools.patient.Event event = null; 
		Calendar cal = Calendar.getInstance();
		cal.setTime(dategiven);
		Date date = new Date((dategiven.getTime()/3600000)*3600000);
		date.setHours(0);
		int day = cal.get(Calendar.DAY_OF_WEEK)-2;
		if (day == -1) day = 6;
		date.setDate(date.getDate() - day);
		Date dateLater = new Date(date.getTime());
		dateLater.setDate(date.getDate() + 1);
		TableRow buttonLayout;
		this.state = UI_STATE_SCHEDULEWEEKFORPATIENTMEAS;
		if (Activa.myMenu != null) {
			Activa.myMenu.clear();
			Activa.myInflater.inflate(R.menu.scheduleforpatientformeas, Activa.myMenu);
		}
		Hashtable<Date, com.o2hlink.pimtools.patient.Event> eventsOrdered = new Hashtable<Date, com.o2hlink.pimtools.patient.Event>();
		Vector<Date> datesOrdered = new Vector<Date>();
		Enumeration<com.o2hlink.pimtools.patient.Event> enumer = Activa.myPatientManager.currentPatMeasEventSet.elements();
		while (enumer.hasMoreElements()) {
			com.o2hlink.pimtools.patient.Event temp = enumer.nextElement();
			while (datesOrdered.contains(temp.date)) temp.date.setTime(temp.date.getTime() + 1);
			Timestamp stamp = new Timestamp(temp.date.getTime());
			datesOrdered.add(stamp);
			eventsOrdered.put(stamp, temp);
		}
		Collections.sort(datesOrdered);
		Activa.myApp.setContentView(R.layout.scheduleday);
		((TextView) Activa.myApp.findViewById(R.id.startText)).setText(Activa.myLanguageManager.CALENDAR_MEAS);
		ImageButton back = (ImageButton) Activa.myApp.findViewById(R.id.previous);
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Activa.myUIManager.loadGeneratedSubenvironment(Activa.myUIManager.lastSubenv, true);
			}
		});
		ImageButton prev = (ImageButton) Activa.myApp.findViewById(R.id.previousday);
		ImageButton next = (ImageButton) Activa.myApp.findViewById(R.id.nextday);
		TextView dateText = (TextView) Activa.myApp.findViewById(R.id.date);
		dateText.setText(Activa.myLanguageManager.CALENDAR_WEEK + " " + cal.get(Calendar.WEEK_OF_YEAR) + " "+ Activa.myLanguageManager.CALENDAR_OF + " " + cal.get(Calendar.YEAR));
		TableLayout schedule = (TableLayout)Activa.myApp.findViewById(R.id.schedule);
		for (int i = 0; i < 7; i++) {	
			time = new TextView(Activa.myApp);	
			time.setText(ActivaUtil.dateToReadableString(date));
			time.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
			time.setPadding(5, 10, 5, 10);
			time.setTypeface(Typeface.DEFAULT_BOLD);
			time.setTextSize(20);
			time.setGravity(Gravity.CENTER);
			time.setTextColor(Color.BLACK);
			schedule.addView(time);		
			View separator = new View(Activa.myApp);
			LayoutParams separatorLayout = new LayoutParams(android.view.ViewGroup.LayoutParams.FILL_PARENT, 1);
			separator.setLayoutParams(separatorLayout);
			separator.setBackgroundColor(Color.BLACK);				
			schedule.addView(separator);
			for(int j = 0; j < datesOrdered.size(); j++) {
				event = eventsOrdered.get(datesOrdered.get(j));
				final String id = event.id;
				if ((date.compareTo(event.getDate()) <= 0)&&(dateLater.compareTo(event.getDate()) > 0)) {
					buttonLayout = new TableRow(Activa.myApp);
					buttonLayout.setOrientation(TableRow.HORIZONTAL);
					buttonLayout.setGravity(Gravity.CENTER_VERTICAL);
					buttonLayout.setLayoutParams(new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, 40));			
					time = new TextView(Activa.myApp);
					time.setPadding(5, 10, 5, 10);
					time.setTextSize(20);
					time.setTextColor(Color.BLACK);
					time.setTypeface(Typeface.SANS_SERIF);
					time.setText(String.format("%02d:%02d", event.date.getHours(), event.date.getMinutes()));
					buttonLayout.addView(time);
					TextView activity = new TextView(Activa.myApp);
					activity.setLayoutParams(new TableRow.LayoutParams(200, LayoutParams.WRAP_CONTENT));
					activity.setText(event.name);
					activity.setPadding(5, 10, 5, 10);
					activity.setTextSize(20);
					activity.setTextColor(Color.BLACK);
					activity.setTypeface(Typeface.SANS_SERIF);
					buttonLayout.addView(activity);
					ImageView button = new ImageView(Activa.myApp);
					if (event.type == 2) button.setBackgroundResource(R.drawable.event);
					else switch (event.state) {
						case 0:
							button.setBackgroundResource(R.drawable.oksmall);
							break;
						case 1:
							button.setBackgroundResource(R.drawable.cancel);
							break;
						case 2:
							button.setBackgroundResource(R.drawable.nextsmall);
							break;
					}
					buttonLayout.addView(button);	
					schedule.addView(buttonLayout);
					time.setOnClickListener(new OnClickListener() {				
						@Override
						public void onClick(View v) {
							if (Activa.myMenu != null) Activa.myMenu.clear();
							Activa.myPatientManager.currentPatMeasEventSet.get(id).doActivity();
						}
					});	
					activity.setOnClickListener(new OnClickListener() {				
						@Override
						public void onClick(View v) {
							if (Activa.myMenu != null) Activa.myMenu.clear();
							Activa.myPatientManager.currentPatMeasEventSet.get(id).doActivity();
						}
					});
					button.setOnClickListener(new OnClickListener() {				
						@Override
						public void onClick(View v) {
							if (Activa.myMenu != null) Activa.myMenu.clear();
							Activa.myPatientManager.currentPatMeasEventSet.get(id).doActivity();
						}
					});	
					buttonLayout.setOnClickListener(new OnClickListener() {				
						@Override
						public void onClick(View v) {
							if (Activa.myMenu != null) Activa.myMenu.clear();
							Activa.myPatientManager.currentPatMeasEventSet.get(id).doActivity();
						}
					});	
					separator = new View(Activa.myApp);
					separatorLayout = new LayoutParams(android.view.ViewGroup.LayoutParams.FILL_PARENT, 1);
					separator.setLayoutParams(separatorLayout);
					separator.setBackgroundColor(Color.BLACK);
					schedule.addView(separator);
				}
			}
			date.setDate(date.getDate() + 1);
			dateLater.setDate(dateLater.getDate() + 1);
		}
		prev.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Runnable run = new Runnable() {
					@Override
					public void run() {
						handler.sendEmptyMessage(0);
						int day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)-2;
						if (day == -1) day = 6;
						Date start = new Date((dategiven.getTime()/86400000)*86400000);
						start.setDate(start.getDate() - day - 7);
						Date end = new Date(start.getTime());
						end.setDate(end.getDate() + 7);
						if (Activa.myProtocolManager.getMeasuringEvents(Activa.myPatientManager.currentPat.getId(), start, end))
								handler.sendEmptyMessage(1);
						else handler.sendEmptyMessage(2);
					}
					private Handler handler = new Handler() {
						@Override
						public void handleMessage(Message msg) {
							Date nextDate;
							switch (msg.what) {
								case 0:
									ImageView animationFrame = (ImageView) Activa.myApp.findViewById(R.id.animation);
									animationFrame.setVisibility(View.VISIBLE);
									animationFrame.setBackgroundResource(R.drawable.loading);
									final AnimationDrawable animation = (AnimationDrawable) animationFrame.getBackground();
									animation.start();
									break;
								case 1:
									nextDate = new Date(dategiven.getTime());
									nextDate.setDate(nextDate.getDate() - 7);
									Activa.myUIManager.loadScheduleWeekForPatientMeas(nextDate);
									break;
								case 2:
									nextDate = new Date(dategiven.getTime());
									nextDate.setDate(nextDate.getDate() - 7);
									Activa.myUIManager.loadScheduleWeekForPatientMeas(nextDate);
									Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.CONNECTION_FAILED);
									break;
							}
						}

					};
				};
				Thread thread = new Thread(run);
				thread.start();
			}
		});
		next.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Runnable run = new Runnable() {
					@Override
					public void run() {
						handler.sendEmptyMessage(0);
						int day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)-2;
						if (day == -1) day = 6;
						Date start = new Date((dategiven.getTime()/86400000)*86400000);
						start.setDate(start.getDate() - day + 7);
						Date end = new Date(start.getTime());
						end.setDate(end.getDate() + 7);
						if (Activa.myProtocolManager.getMeasuringEvents(Activa.myPatientManager.currentPat.getId(), start, end))
							handler.sendEmptyMessage(1);
						else handler.sendEmptyMessage(2);
					}
					private Handler handler = new Handler() {
						@Override
						public void handleMessage(Message msg) {
							Date nextDate;
							switch (msg.what) {
								case 0:
									ImageView animationFrame = (ImageView) Activa.myApp.findViewById(R.id.animation);
									animationFrame.setVisibility(View.VISIBLE);
									animationFrame.setBackgroundResource(R.drawable.loading);
									final AnimationDrawable animation = (AnimationDrawable) animationFrame.getBackground();
									animation.start();
									break;
								case 1:
									nextDate = new Date(dategiven.getTime());
									nextDate.setDate(nextDate.getDate() + 7);
									Activa.myUIManager.loadScheduleWeekForPatientMeas(nextDate);
									break;
								case 2:
									nextDate = new Date(dategiven.getTime());
									nextDate.setDate(nextDate.getDate() + 7);
									Activa.myUIManager.loadScheduleWeekForPatientMeas(nextDate);
									Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.CONNECTION_FAILED);
									break;
							}
						}

					};
				};
				Thread thread = new Thread(run);
				thread.start();
			}
		});
		ImageButton add = (ImageButton) Activa.myApp.findViewById(R.id.help);
		add.setImageResource(R.drawable.plus);
		add.setVisibility(View.VISIBLE);
		add.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				LayoutInflater inflater = (LayoutInflater) Activa.myApp.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				final View layout = inflater.inflate(R.layout.toastaddpatevent,
                        (ViewGroup) Activa.myApp.findViewById(R.id.toastpataddeventroot));
				TextView title = (TextView) layout.findViewById(R.id.addtitle);
				title.setText(Activa.myLanguageManager.NOTIFICATION_EVENT_TITLE);
				TextView namereq = (TextView) layout.findViewById(R.id.namerequested);
				namereq.setText(Activa.myLanguageManager.NOTIFICATION_EVENT_NAMEREQ);
				TextView descreq = (TextView) layout.findViewById(R.id.descrequest);
				descreq.setText(Activa.myLanguageManager.NOTIFICATION_EVENT_DESC);
				TextView typereq = (TextView) layout.findViewById(R.id.typerequest);
				typereq.setText(Activa.myLanguageManager.NOTIFICATION_EVENT_TYPE);
				final Spinner type = (Spinner) layout.findViewById(R.id.type);
				ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(Activa.myApp, R.array.meastype, android.R.layout.simple_spinner_item);
			    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			    type.setAdapter(adapter);
			    type.setSelection(0);
				TextView startreq = (TextView) layout.findViewById(R.id.startrequest);
				startreq.setText(Activa.myLanguageManager.NOTIFICATION_EVENT_START);
				TextView endreq = (TextView) layout.findViewById(R.id.endrequest);
				endreq.setText(Activa.myLanguageManager.NOTIFICATION_EVENT_END);
				TextView freqreq = (TextView) layout.findViewById(R.id.freqrequest);
				freqreq.setText(Activa.myLanguageManager.NOTIFICATION_EVENT_FREQ);
				final Spinner frequency = (Spinner) layout.findViewById(R.id.freq);
			    ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(Activa.myApp, R.array.freqarray, android.R.layout.simple_spinner_item);
			    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			    frequency.setAdapter(adapter2);
			    ((LinearLayout)layout.findViewById(R.id.freqend)).setVisibility(View.GONE);
			    frequency.setOnItemSelectedListener(new OnItemSelectedListener() {
					@Override
					public void onItemSelected (AdapterView<?> parent, View view, int position, long id) {
						if (position == 0) ((LinearLayout)layout.findViewById(R.id.freqend)).setVisibility(View.GONE);
						else ((LinearLayout)layout.findViewById(R.id.freqend)).setVisibility(View.VISIBLE);
					}
					@Override
					public void onNothingSelected (AdapterView<?> parent) {
						((LinearLayout)layout.findViewById(R.id.freqend)).setVisibility(View.GONE);
					}
				});
			    TextView freqendreq = (TextView) layout.findViewById(R.id.freqendrequest);
				freqendreq.setText(Activa.myLanguageManager.NOTIFICATION_EVENT_FREQEND);
				ImageButton image = (ImageButton) layout.findViewById(R.id.addeventok);
				Builder builder = new AlertDialog.Builder(Activa.myApp);
				builder.setView(layout);
				final AlertDialog alertDialog = builder.create();
				alertDialog.show();
				image.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						final Measurement meas;
						final com.o2hlink.activ8.client.entity.Event eventtoadd = new com.o2hlink.activ8.client.entity.Event();
						eventtoadd.setName(((EditText) layout.findViewById(R.id.name)).getText().toString());
						eventtoadd.setDescription(((EditText) layout.findViewById(R.id.description)).getText().toString());
						switch (type.getSelectedItemPosition()) {
							case 0:
								meas = Measurement.PULSEOXYMETRY;
								break;
							case 1:
								meas = Measurement.SPIROMETRY;
								break;
							case 2:
								meas = Measurement.SIX_MINUTES_WALK;
								break;
							case 3:
								meas = Measurement.WEIGHT_HEIGHT;
								break;
							case 4:
								meas = Measurement.EXERCISE;
								break;
							default:
								meas = null;
								break;
						}
						Date start = new Date(0);
						start.setYear(((DatePicker) layout.findViewById(R.id.startdate)).getYear() - 1900);
						start.setMonth(((DatePicker) layout.findViewById(R.id.startdate)).getMonth());
						start.setDate(((DatePicker) layout.findViewById(R.id.startdate)).getDayOfMonth());
						start.setHours(((TimePicker) layout.findViewById(R.id.starttime)).getCurrentHour());
						start.setMinutes(((TimePicker) layout.findViewById(R.id.starttime)).getCurrentMinute());
						eventtoadd.setStart(start);
						Date end = new Date(0);
						end.setYear(((DatePicker) layout.findViewById(R.id.enddate)).getYear() - 1900);
						end.setMonth(((DatePicker) layout.findViewById(R.id.enddate)).getMonth());
						end.setDate(((DatePicker) layout.findViewById(R.id.enddate)).getDayOfMonth());
						end.setHours(((TimePicker) layout.findViewById(R.id.endtime)).getCurrentHour());
						end.setMinutes(((TimePicker) layout.findViewById(R.id.endtime)).getCurrentMinute());
						eventtoadd.setEnd(end);
						switch (frequency.getSelectedItemPosition()) {
							case 0:
								eventtoadd.setFrequency(EventFrequency.NONE);
								break;
							case 1:
								eventtoadd.setFrequency(EventFrequency.DAILY);
								break;
							case 2:
								eventtoadd.setFrequency(EventFrequency.WEEKLY);
								break;
							case 3:
								eventtoadd.setFrequency(EventFrequency.MONTHLY);
								break;
							case 4:
								eventtoadd.setFrequency(EventFrequency.YEARLY);
								break;
							case 5:
								eventtoadd.setFrequency(EventFrequency.MONWEDFRI);
								break;
							case 6:
								eventtoadd.setFrequency(EventFrequency.TUETHUR);
								break;
							case 7:
								eventtoadd.setFrequency(EventFrequency.WEEKDAY);
								break;
							default:
								eventtoadd.setFrequency(EventFrequency.NONE);
								break;
						}
						Date freqend = new Date(0);
						freqend.setYear(((DatePicker) layout.findViewById(R.id.freqenddate)).getYear() - 1900);
						freqend.setMonth(((DatePicker) layout.findViewById(R.id.freqenddate)).getMonth());
						freqend.setDate(((DatePicker) layout.findViewById(R.id.freqenddate)).getDayOfMonth());
						freqend.setHours(((TimePicker) layout.findViewById(R.id.freqendtime)).getCurrentHour());
						freqend.setMinutes(((TimePicker) layout.findViewById(R.id.freqendtime)).getCurrentMinute());
						eventtoadd.setEndFrequency(freqend);
						final ProgressDialog dialog = ProgressDialog.show(Activa.myApp, Activa.myLanguageManager.NOTIFICATION_EVENT_TITLE, 
								Activa.myLanguageManager.NOTIFICATION_EVENT_ADDING, true);
						dialog.show();
						Runnable run =  new Runnable() {
							@Override
							public void run() {
								if (Activa.myPatientManager.addMeasEvent(Activa.myPatientManager.currentPat.getId(), meas, eventtoadd))
									handler.sendEmptyMessage(1);
								else 
									handler.sendEmptyMessage(0);
							}
							private Handler handler = new Handler() {
								@Override
								public void handleMessage(Message msg) {
									switch (msg.what) {
										case 0:
											Toast toast = Toast.makeText(Activa.myApp, Activa.myLanguageManager.CONNECTION_FAILED, Toast.LENGTH_SHORT);
											Activa.myUIManager.loadScheduleWeekForPatientMeas(dategiven);
											dialog.cancel();
											toast.show();
											break;
										case 1:
											Activa.myUIManager.loadScheduleWeekForPatientMeas(dategiven);
											dialog.cancel();
											break;
									}
								}

							};
						};
						Thread thread = new Thread(run);
						thread.start();
						alertDialog.cancel();
					}
				});
			}
		});
	}
	
	public void loadScheduleMonthForPatientMeas(final Date dategiven) {
		TextView time;
		View separator;
		LayoutParams separatorLayout;
		TableRow weekLayout;
		Enumeration<com.o2hlink.pimtools.patient.Event> enumeration;
		com.o2hlink.pimtools.patient.Event event = null; 
		Date date = new Date((dategiven.getTime()/3600000)*3600000);
		date.setHours(0);
		date.setDate(1);
		Date dateLater = new Date(date.getTime());
		dateLater.setDate(2);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int weekday = cal.get(Calendar.DAY_OF_WEEK)-2;
		if (weekday == -1) weekday = 6;
		Date dateLimit = new Date(date.getTime());
		dateLimit.setMonth(date.getMonth() + 1);
		this.state = UI_STATE_SCHEDULEMONTHFORPATIENTMEAS;
		if (Activa.myMenu != null) {
			Activa.myMenu.clear();
			Activa.myInflater.inflate(R.menu.scheduleforpatientformeas, Activa.myMenu);
		}
		Activa.myApp.setContentView(R.layout.schedulemonth);
		((TextView) Activa.myApp.findViewById(R.id.startText)).setText(Activa.myLanguageManager.CALENDAR_MEAS);
		ImageButton back = (ImageButton) Activa.myApp.findViewById(R.id.previous);
		ImageButton prev = (ImageButton) Activa.myApp.findViewById(R.id.previousday);
		ImageButton next = (ImageButton) Activa.myApp.findViewById(R.id.nextday);
		TextView dateText = (TextView) Activa.myApp.findViewById(R.id.date);
		dateText.setText(ActivaUtil.monthOfDate(date) + " " + cal.get(Calendar.YEAR));
		TableLayout schedule = (TableLayout)Activa.myApp.findViewById(R.id.schedule);
		weekLayout = new TableRow(Activa.myApp);
		weekLayout.setOrientation(TableRow.HORIZONTAL);
		weekLayout.setGravity(Gravity.CENTER_VERTICAL);
		weekLayout.setLayoutParams(new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		separator = new View(Activa.myApp);
		separator.setLayoutParams(new TableRow.LayoutParams(1, 36));
		separator.setBackgroundColor(Color.BLACK);
		weekLayout.addView(separator);
		String[] weekdaynames = {Activa.myLanguageManager.WEEK_MONDAY,
				Activa.myLanguageManager.WEEK_TUESDAY,
				Activa.myLanguageManager.WEEK_WEDNESDAY,
				Activa.myLanguageManager.WEEK_THURSDAY,
				Activa.myLanguageManager.WEEK_FRYDAY,
				Activa.myLanguageManager.WEEK_SATURDAY,
				Activa.myLanguageManager.WEEK_SUNDAY}; 
		for (int i = 0; i < weekdaynames.length; i++) {
			time = new TextView(Activa.myApp);
			time.setGravity(Gravity.CENTER);
			time.setTextSize(20);
			time.setText(weekdaynames[i]);
			time.setTag(date.getDate());
			time.setTypeface(Typeface.DEFAULT_BOLD);
			time.setLayoutParams(new TableRow.LayoutParams(36, 36));	
			time.setTextColor(Color.BLACK);
			weekLayout.addView(time);
		}
		separator = new View(Activa.myApp);
		separator.setLayoutParams(new TableRow.LayoutParams(1, 36));
		separator.setBackgroundColor(Color.BLACK);
		weekLayout.addView(separator);
		schedule.addView(weekLayout);	
		separator = new View(Activa.myApp);
		separatorLayout = new LayoutParams(android.view.ViewGroup.LayoutParams.FILL_PARENT, 1);
		separator.setLayoutParams(separatorLayout);
		separator.setBackgroundColor(Color.BLACK);
		schedule.addView(separator);	
		weekLayout = new TableRow(Activa.myApp);
		weekLayout.setOrientation(TableRow.HORIZONTAL);
		weekLayout.setGravity(Gravity.CENTER_VERTICAL);
		weekLayout.setLayoutParams(new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		separator = new View(Activa.myApp);
		separator.setLayoutParams(new TableRow.LayoutParams(1, 36));
		separator.setBackgroundColor(Color.BLACK);
		weekLayout.addView(separator);
		for (int i = 0; i < weekday; i++) {		
			View space = new View(Activa.myApp);
			space.setLayoutParams(new TableRow.LayoutParams(36, 36));	
			weekLayout.addView(space);
		}
		while(date.before(dateLimit)) {	
			time = new TextView(Activa.myApp);
			time.setGravity(Gravity.CENTER);
			time.setTextSize(20);
			time.setText("" + date.getDate());
			time.setTag(date.getDate());
			time.setTypeface(Typeface.SANS_SERIF);
			time.setLayoutParams(new TableRow.LayoutParams(36, 36));	
			int state = 3;
			enumeration = Activa.myPatientManager.currentPatMeasEventSet.elements();
			while (enumeration.hasMoreElements()) {
				event = enumeration.nextElement();
				if ((date.compareTo(event.getDate()) <= 0)&&(dateLater.compareTo(event.getDate()) > 0)) {
					if (((event.type == 2)||(event.state == 0))&&(state == 3)) {
						state = 0;
						continue;
					}
					if ((event.state == 2)&&((state == 3)||(state == 0))) {
						state = 2;
						continue;
					}
					if ((event.state == 1)&&((state == 3)||(state == 0)||(state == 2))) {
						state = 1;
						break;
					}
				}
			}
			switch (state) {
				case 0:
					time.setTextColor(Color.GREEN);
					break;
				case 1:
					time.setTextColor(Color.RED);
					break;
				case 2:
					time.setTextColor(Color.parseColor("#ffad00"));
					break;
				case 3:
					time.setTextColor(Color.BLACK);
					break;
			}
			time.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Date newDate = new Date(dategiven.getTime());
					newDate.setDate((Integer)v.getTag());
					Activa.myUIManager.loadScheduleDayForPatientMeas(newDate);
				}
			});
			weekLayout.addView(time);
			date.setDate(date.getDate() + 1);
			dateLater.setDate(dateLater.getDate() + 1);
			weekday++;
			if (weekday == 7) {		
				separator = new View(Activa.myApp);
				separator.setLayoutParams(new TableRow.LayoutParams(1, 36));
				separator.setBackgroundColor(Color.BLACK);
				weekLayout.addView(separator);
				schedule.addView(weekLayout);	
				separator = new View(Activa.myApp);
				separatorLayout = new LayoutParams(android.view.ViewGroup.LayoutParams.FILL_PARENT, 1);
				separator.setLayoutParams(separatorLayout);
				separator.setBackgroundColor(Color.BLACK);
				schedule.addView(separator);	
				weekday = 0;
				weekLayout = new TableRow(Activa.myApp);
				weekLayout.setOrientation(TableRow.HORIZONTAL);
				weekLayout.setGravity(Gravity.CENTER_VERTICAL);
				weekLayout.setLayoutParams(new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
				separator = new View(Activa.myApp);
				separator.setLayoutParams(new TableRow.LayoutParams(1, 36));
				separator.setBackgroundColor(Color.BLACK);
				weekLayout.addView(separator);
			}
		}
		for (int i = weekday; i < 7; i++) {	
			time = new TextView(Activa.myApp);
			time.setGravity(Gravity.CENTER);
			time.setTextSize(20);
			time.setTextColor(Color.BLACK);
			time.setTypeface(Typeface.SANS_SERIF);
			time.setLayoutParams(new TableRow.LayoutParams(36, 36));	
			weekLayout.addView(time);
		}	
		separator = new View(Activa.myApp);
		separator.setLayoutParams(new TableRow.LayoutParams(1, 36));
		separator.setBackgroundColor(Color.BLACK);
		weekLayout.addView(separator);
		schedule.addView(weekLayout);
		separator = new View(Activa.myApp);
		separatorLayout = new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, 1);
		separator.setLayoutParams(separatorLayout);
		separator.setBackgroundColor(Color.BLACK);
		schedule.addView(separator);	
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Activa.myUIManager.loadPatientMenu(Activa.myPatientManager.currentPat);
			}
		});
		prev.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Runnable run = new Runnable() {
					@Override
					public void run() {
						handler.sendEmptyMessage(0);
						Date start = new Date((dategiven.getTime()/86400000)*86400000);
						start.setDate(1);
						start.setMonth(start.getMonth() - 1);
						Date end = new Date(start.getTime());
						end.setMonth(end.getMonth() + 1);
						if (Activa.myProtocolManager.getMeasuringEvents(Activa.myPatientManager.currentPat.getId(), start, end))
							handler.sendEmptyMessage(1);
						else handler.sendEmptyMessage(2);
					}
					private Handler handler = new Handler() {
						@Override
						public void handleMessage(Message msg) {
							Date nextDate;
							switch (msg.what) {
								case 0:
									ImageView animationFrame = (ImageView) Activa.myApp.findViewById(R.id.animation);
									animationFrame.setVisibility(View.VISIBLE);
									animationFrame.setBackgroundResource(R.drawable.loading);
									final AnimationDrawable animation = (AnimationDrawable) animationFrame.getBackground();
									animation.start();
									break;
								case 1:
									nextDate = new Date(dategiven.getTime());
									nextDate.setMonth(nextDate.getMonth() - 1);
									Activa.myUIManager.loadScheduleMonthForPatientMeas(nextDate);
									break;
								case 2:
									nextDate = new Date(dategiven.getTime());
									nextDate.setMonth(nextDate.getMonth() - 1);
									Activa.myUIManager.loadScheduleMonthForPatientMeas(nextDate);
									Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.CONNECTION_FAILED);
									break;
							}
						}

					};
				};
				Thread thread = new Thread(run);
				thread.start();
			}
		});
		next.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Runnable run = new Runnable() {
					@Override
					public void run() {
						handler.sendEmptyMessage(0);
						Date start = new Date((dategiven.getTime()/86400000)*86400000);
						start.setDate(1);
						start.setMonth(start.getMonth() + 1);
						Date end = new Date(start.getTime());
						end.setMonth(end.getMonth() + 1);
						if (Activa.myProtocolManager.getMeasuringEvents(Activa.myPatientManager.currentPat.getId(), start, end))
							handler.sendEmptyMessage(1);
						else handler.sendEmptyMessage(2);
					}
					private Handler handler = new Handler() {
						@Override
						public void handleMessage(Message msg) {
							Date nextDate;
							switch (msg.what) {
								case 0:
									ImageView animationFrame = (ImageView) Activa.myApp.findViewById(R.id.animation);
									animationFrame.setVisibility(View.VISIBLE);
									animationFrame.setBackgroundResource(R.drawable.loading);
									final AnimationDrawable animation = (AnimationDrawable) animationFrame.getBackground();
									animation.start();
									break;
								case 1:
									nextDate = new Date(dategiven.getTime());
									nextDate.setMonth(nextDate.getMonth() + 1);
									Activa.myUIManager.loadScheduleMonthForPatientMeas(nextDate);
									break;
								case 2:
									nextDate = new Date(dategiven.getTime());
									nextDate.setMonth(nextDate.getMonth() + 1);
									Activa.myUIManager.loadScheduleMonthForPatientMeas(nextDate);
									Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.CONNECTION_FAILED);
									break;
							}
						}

					};
				};
				Thread thread = new Thread(run);
				thread.start();
			}
		});
		ImageButton add = (ImageButton) Activa.myApp.findViewById(R.id.help);
		add.setImageResource(R.drawable.plus);
		add.setVisibility(View.VISIBLE);
		add.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				LayoutInflater inflater = (LayoutInflater) Activa.myApp.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				final View layout = inflater.inflate(R.layout.toastaddpatevent,
                        (ViewGroup) Activa.myApp.findViewById(R.id.toastpataddeventroot));
				TextView title = (TextView) layout.findViewById(R.id.addtitle);
				title.setText(Activa.myLanguageManager.NOTIFICATION_EVENT_TITLE);
				TextView namereq = (TextView) layout.findViewById(R.id.namerequested);
				namereq.setText(Activa.myLanguageManager.NOTIFICATION_EVENT_NAMEREQ);
				TextView descreq = (TextView) layout.findViewById(R.id.descrequest);
				descreq.setText(Activa.myLanguageManager.NOTIFICATION_EVENT_DESC);
				TextView typereq = (TextView) layout.findViewById(R.id.typerequest);
				typereq.setText(Activa.myLanguageManager.NOTIFICATION_EVENT_TYPE);
				final Spinner type = (Spinner) layout.findViewById(R.id.type);
				ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(Activa.myApp, R.array.meastype, android.R.layout.simple_spinner_item);
			    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			    type.setAdapter(adapter);
			    type.setSelection(0);
				TextView startreq = (TextView) layout.findViewById(R.id.startrequest);
				startreq.setText(Activa.myLanguageManager.NOTIFICATION_EVENT_START);
				TextView endreq = (TextView) layout.findViewById(R.id.endrequest);
				endreq.setText(Activa.myLanguageManager.NOTIFICATION_EVENT_END);
				TextView freqreq = (TextView) layout.findViewById(R.id.freqrequest);
				freqreq.setText(Activa.myLanguageManager.NOTIFICATION_EVENT_FREQ);
				final Spinner frequency = (Spinner) layout.findViewById(R.id.freq);
			    ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(Activa.myApp, R.array.freqarray, android.R.layout.simple_spinner_item);
			    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			    frequency.setAdapter(adapter2);
			    ((LinearLayout)layout.findViewById(R.id.freqend)).setVisibility(View.GONE);
			    frequency.setOnItemSelectedListener(new OnItemSelectedListener() {
					@Override
					public void onItemSelected (AdapterView<?> parent, View view, int position, long id) {
						if (position == 0) ((LinearLayout)layout.findViewById(R.id.freqend)).setVisibility(View.GONE);
						else ((LinearLayout)layout.findViewById(R.id.freqend)).setVisibility(View.VISIBLE);
					}
					@Override
					public void onNothingSelected (AdapterView<?> parent) {
						((LinearLayout)layout.findViewById(R.id.freqend)).setVisibility(View.GONE);
					}
				});
			    TextView freqendreq = (TextView) layout.findViewById(R.id.freqendrequest);
				freqendreq.setText(Activa.myLanguageManager.NOTIFICATION_EVENT_FREQEND);
				ImageButton image = (ImageButton) layout.findViewById(R.id.addeventok);
				Builder builder = new AlertDialog.Builder(Activa.myApp);
				builder.setView(layout);
				final AlertDialog alertDialog = builder.create();
				alertDialog.show();
				image.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						final Measurement meas;
						final com.o2hlink.activ8.client.entity.Event eventtoadd = new com.o2hlink.activ8.client.entity.Event();
						eventtoadd.setName(((EditText) layout.findViewById(R.id.name)).getText().toString());
						eventtoadd.setDescription(((EditText) layout.findViewById(R.id.description)).getText().toString());
						switch (type.getSelectedItemPosition()) {
							case 0:
								meas = Measurement.PULSEOXYMETRY;
								break;
							case 1:
								meas = Measurement.SPIROMETRY;
								break;
							case 2:
								meas = Measurement.SIX_MINUTES_WALK;
								break;
							case 3:
								meas = Measurement.WEIGHT_HEIGHT;
								break;
							case 4:
								meas = Measurement.EXERCISE;
								break;
							default:
								meas = null;
								break;
						}
						Date start = new Date(0);
						start.setYear(((DatePicker) layout.findViewById(R.id.startdate)).getYear() - 1900);
						start.setMonth(((DatePicker) layout.findViewById(R.id.startdate)).getMonth());
						start.setDate(((DatePicker) layout.findViewById(R.id.startdate)).getDayOfMonth());
						start.setHours(((TimePicker) layout.findViewById(R.id.starttime)).getCurrentHour());
						start.setMinutes(((TimePicker) layout.findViewById(R.id.starttime)).getCurrentMinute());
						eventtoadd.setStart(start);
						Date end = new Date(0);
						end.setYear(((DatePicker) layout.findViewById(R.id.enddate)).getYear() - 1900);
						end.setMonth(((DatePicker) layout.findViewById(R.id.enddate)).getMonth());
						end.setDate(((DatePicker) layout.findViewById(R.id.enddate)).getDayOfMonth());
						end.setHours(((TimePicker) layout.findViewById(R.id.endtime)).getCurrentHour());
						end.setMinutes(((TimePicker) layout.findViewById(R.id.endtime)).getCurrentMinute());
						eventtoadd.setEnd(end);
						switch (frequency.getSelectedItemPosition()) {
							case 0:
								eventtoadd.setFrequency(EventFrequency.NONE);
								break;
							case 1:
								eventtoadd.setFrequency(EventFrequency.DAILY);
								break;
							case 2:
								eventtoadd.setFrequency(EventFrequency.WEEKLY);
								break;
							case 3:
								eventtoadd.setFrequency(EventFrequency.MONTHLY);
								break;
							case 4:
								eventtoadd.setFrequency(EventFrequency.YEARLY);
								break;
							case 5:
								eventtoadd.setFrequency(EventFrequency.MONWEDFRI);
								break;
							case 6:
								eventtoadd.setFrequency(EventFrequency.TUETHUR);
								break;
							case 7:
								eventtoadd.setFrequency(EventFrequency.WEEKDAY);
								break;
							default:
								eventtoadd.setFrequency(EventFrequency.NONE);
								break;
						}
						Date freqend = new Date(0);
						freqend.setYear(((DatePicker) layout.findViewById(R.id.freqenddate)).getYear() - 1900);
						freqend.setMonth(((DatePicker) layout.findViewById(R.id.freqenddate)).getMonth());
						freqend.setDate(((DatePicker) layout.findViewById(R.id.freqenddate)).getDayOfMonth());
						freqend.setHours(((TimePicker) layout.findViewById(R.id.freqendtime)).getCurrentHour());
						freqend.setMinutes(((TimePicker) layout.findViewById(R.id.freqendtime)).getCurrentMinute());
						eventtoadd.setEndFrequency(freqend);
						final ProgressDialog dialog = ProgressDialog.show(Activa.myApp, Activa.myLanguageManager.NOTIFICATION_EVENT_TITLE, 
								Activa.myLanguageManager.NOTIFICATION_EVENT_ADDING, true);
						dialog.show();
						Runnable run =  new Runnable() {
							@Override
							public void run() {
								if (Activa.myPatientManager.addMeasEvent(Activa.myPatientManager.currentPat.getId(), meas, eventtoadd))
									handler.sendEmptyMessage(1);
								else 
									handler.sendEmptyMessage(0);
							}
							private Handler handler = new Handler() {
								@Override
								public void handleMessage(Message msg) {
									switch (msg.what) {
										case 0:
											Toast toast = Toast.makeText(Activa.myApp, Activa.myLanguageManager.CONNECTION_FAILED, Toast.LENGTH_SHORT);
											Activa.myUIManager.loadScheduleWeekForPatientMeas(dategiven);
											dialog.cancel();
											toast.show();
											break;
										case 1:
											Activa.myUIManager.loadScheduleWeekForPatientMeas(dategiven);
											dialog.cancel();
											break;
									}
								}

							};
						};
						Thread thread = new Thread(run);
						thread.start();
						alertDialog.cancel();
					}
				});
			}
		});
	}

	public void loadScheduleDayForPatientQuest(final Date dategiven) {
		TextView time;
		com.o2hlink.pimtools.patient.Event event = null; 
		Date date = new Date((dategiven.getTime()/3600000)*3600000);
		date.setHours(0);
		Date dateLater = new Date(date.getTime());
		dateLater.setHours(dateLater.getHours() + 1);
		TableRow buttonLayout;
		this.state = UI_STATE_SCHEDULEFORPATIENTQUEST;
		if (Activa.myMenu != null) {
			Activa.myMenu.clear();
			Activa.myInflater.inflate(R.menu.scheduleforpatientforquest, Activa.myMenu);
		}
		Hashtable<Date, com.o2hlink.pimtools.patient.Event> eventsOrdered = new Hashtable<Date,com.o2hlink.pimtools.patient.Event>();
		Vector<Date> datesOrdered = new Vector<Date>();
		Enumeration<com.o2hlink.pimtools.patient.Event> enumer = Activa.myPatientManager.currentPatQuestEventSet.elements();
		while (enumer.hasMoreElements()) {
			com.o2hlink.pimtools.patient.Event temp = enumer.nextElement();
			while (datesOrdered.contains(temp.date)) temp.date.setTime(temp.date.getTime() + 1);
			Timestamp stamp = new Timestamp(temp.date.getTime());
			datesOrdered.add(stamp);
			eventsOrdered.put(stamp, temp);
		}
		Collections.sort(datesOrdered);
		Activa.myApp.setContentView(R.layout.scheduleday);
		((TextView) Activa.myApp.findViewById(R.id.startText)).setText(Activa.myLanguageManager.CALENDAR_QUEST);
		((TextView) Activa.myApp.findViewById(R.id.startText)).setTextSize(24);
		ImageButton back = (ImageButton) Activa.myApp.findViewById(R.id.previous);
		ImageButton prev = (ImageButton) Activa.myApp.findViewById(R.id.previousday);
		ImageButton next = (ImageButton) Activa.myApp.findViewById(R.id.nextday);
		TextView dateText = (TextView) Activa.myApp.findViewById(R.id.date);
		dateText.setText(ActivaUtil.dateToReadableString(date));
		TableLayout schedule = (TableLayout)Activa.myApp.findViewById(R.id.schedule);
		for (int i = 0; i < 24; i++) {	
			buttonLayout = new TableRow(Activa.myApp);
			buttonLayout.setOrientation(TableRow.HORIZONTAL);
			buttonLayout.setGravity(Gravity.CENTER_VERTICAL);
			buttonLayout.setLayoutParams(new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, 40));			
			time = new TextView(Activa.myApp);
			time.setText(String.format("%02d:00", date.getHours()));
			time.setPadding(5, 10, 5, 10);
			time.setTextSize(20);
			time.setTextColor(Color.BLACK);
			time.setTypeface(Typeface.SANS_SERIF);
			buttonLayout.addView(time);
			for(int j = 0; j < datesOrdered.size(); j++) {
				event = eventsOrdered.get(datesOrdered.get(j));
				final String id = event.id;
				if (date.compareTo(event.getDate()) == 0) {
					TextView activity = new TextView(Activa.myApp);
					activity.setLayoutParams(new TableRow.LayoutParams(200, LayoutParams.WRAP_CONTENT));
					activity.setText(event.name);
					activity.setPadding(5, 10, 5, 10);
					activity.setTextSize(20);
					activity.setTextColor(Color.BLACK);
					activity.setTypeface(Typeface.SANS_SERIF);
					ImageView button = new ImageView(Activa.myApp);
					if (event.type == 2) button.setBackgroundResource(R.drawable.event);
					else switch (event.state) {
						case 0:
							button.setBackgroundResource(R.drawable.oksmall);
							break;
						case 1:
							button.setBackgroundResource(R.drawable.cancel);
							break;
						case 2:
							button.setBackgroundResource(R.drawable.nextsmall);
							break;
					}
					buttonLayout.addView(activity);
					buttonLayout.addView(button);	
					time.setOnClickListener(new OnClickListener() {				
						@Override
						public void onClick(View v) {
							if (Activa.myMenu != null) Activa.myMenu.clear();
							Activa.myPatientManager.currentPatQuestEventSet.get(id).doActivity();
						}
					});	
					activity.setOnClickListener(new OnClickListener() {				
						@Override
						public void onClick(View v) {
							if (Activa.myMenu != null) Activa.myMenu.clear();
							Activa.myPatientManager.currentPatQuestEventSet.get(id).doActivity();
						}
					});
					button.setOnClickListener(new OnClickListener() {				
						@Override
						public void onClick(View v) {
							if (Activa.myMenu != null) Activa.myMenu.clear();
							Activa.myPatientManager.currentPatQuestEventSet.get(id).doActivity();
						}
					});	
					buttonLayout.setOnClickListener(new OnClickListener() {				
						@Override
						public void onClick(View v) {
							if (Activa.myMenu != null) Activa.myMenu.clear();
							Activa.myPatientManager.currentPatQuestEventSet.get(id).doActivity();
						}
					});
				}
				else if ((date.compareTo(event.getDate()) < 0)&&(dateLater.compareTo(event.getDate()) > 0)) {
					schedule.addView(buttonLayout);
					View separator = new View(Activa.myApp);
					LayoutParams separatorLayout = new LayoutParams(android.view.ViewGroup.LayoutParams.FILL_PARENT, 1);
					separator.setLayoutParams(separatorLayout);
					separator.setBackgroundColor(Color.BLACK);
					schedule.addView(separator);
					buttonLayout = new TableRow(Activa.myApp);
					buttonLayout.setOrientation(TableRow.HORIZONTAL);
					buttonLayout.setGravity(Gravity.CENTER_VERTICAL);
					buttonLayout.setLayoutParams(new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, 40));			
					time = new TextView(Activa.myApp);
					time.setPadding(5, 10, 5, 10);
					time.setTextSize(20);
					time.setTextColor(Color.BLACK);
					time.setTypeface(Typeface.SANS_SERIF);
					buttonLayout.addView(time);
					time.setText(String.format("%02d:%02d", event.date.getHours(), event.date.getMinutes()));
					TextView activity = new TextView(Activa.myApp);
					activity.setLayoutParams(new TableRow.LayoutParams(200, LayoutParams.WRAP_CONTENT));
					activity.setText(event.name);
					activity.setWidth(200);
					activity.setPadding(5, 10, 5, 10);
					activity.setTextSize(20);
					activity.setTextColor(Color.BLACK);
					activity.setTypeface(Typeface.SANS_SERIF);
					ImageView button = new ImageView(Activa.myApp);
					if (event.type == 2) button.setBackgroundResource(R.drawable.event);
					else switch (event.state) {
						case 0:
							button.setBackgroundResource(R.drawable.oksmall);
							break;
						case 1:
							button.setBackgroundResource(R.drawable.cancel);
							break;
						case 2:
							button.setBackgroundResource(R.drawable.nextsmall);
							break;
					}
					buttonLayout.addView(activity);
					buttonLayout.addView(button);	
					time.setOnClickListener(new OnClickListener() {				
						@Override
						public void onClick(View v) {
							if (Activa.myMenu != null) Activa.myMenu.clear();
							Activa.myPatientManager.currentPatQuestEventSet.get(id).doActivity();
						}
					});	
					activity.setOnClickListener(new OnClickListener() {				
						@Override
						public void onClick(View v) {
							if (Activa.myMenu != null) Activa.myMenu.clear();
							Activa.myPatientManager.currentPatQuestEventSet.get(id).doActivity();
						}
					});
					button.setOnClickListener(new OnClickListener() {				
						@Override
						public void onClick(View v) {
							if (Activa.myMenu != null) Activa.myMenu.clear();
							Activa.myPatientManager.currentPatQuestEventSet.get(id).doActivity();
						}
					});	
					buttonLayout.setOnClickListener(new OnClickListener() {				
						@Override
						public void onClick(View v) {
							if (Activa.myMenu != null) Activa.myMenu.clear();
							Activa.myPatientManager.currentPatQuestEventSet.get(id).doActivity();
						}
					});	
				}
			}
			schedule.addView(buttonLayout);
			View separator = new View(Activa.myApp);
			LayoutParams separatorLayout = new LayoutParams(android.view.ViewGroup.LayoutParams.FILL_PARENT, 1);
			separator.setLayoutParams(separatorLayout);
			separator.setBackgroundColor(Color.BLACK);
			
			schedule.addView(separator);

			date.setHours(date.getHours() + 1);
			dateLater.setHours(dateLater.getHours() + 1);
		}
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Activa.myUIManager.loadPatientMenu(Activa.myPatientManager.currentPat);
			}
		});
		prev.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Runnable run = new Runnable() {
					@Override
					public void run() {
						handler.sendEmptyMessage(0);
						Date start = new Date((dategiven.getTime()/86400000)*86400000);
						Date end = new Date(start.getTime());
						start.setDate(start.getDate() - 1);
						if (Activa.myProtocolManager.getQuestEvents(Activa.myPatientManager.currentPat.getId(), start, end))
							handler.sendEmptyMessage(1);
						else handler.sendEmptyMessage(2);
					}
					private Handler handler = new Handler() {
						@Override
						public void handleMessage(Message msg) {
							Date nextDate;
							switch (msg.what) {
								case 0:
									ImageView animationFrame = (ImageView) Activa.myApp.findViewById(R.id.animation);
									animationFrame.setVisibility(View.VISIBLE);
									animationFrame.setBackgroundResource(R.drawable.loading);
									final AnimationDrawable animation = (AnimationDrawable) animationFrame.getBackground();
									animation.start();
									break;
								case 1:
									nextDate = new Date(dategiven.getTime());
									nextDate.setDate(nextDate.getDate() - 1);
									Activa.myUIManager.loadScheduleDayForPatientQuest(nextDate);
									break;
								case 2:
									nextDate = new Date(dategiven.getTime());
									nextDate.setDate(nextDate.getDate() - 1);
									Activa.myUIManager.loadScheduleDayForPatientQuest(nextDate);
									Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.CONNECTION_FAILED);
									break;
							}
						}

					};
				};
				Thread thread = new Thread(run);
				thread.start();
			}
		});
		next.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Runnable run = new Runnable() {
					@Override
					public void run() {
						handler.sendEmptyMessage(0);
						Date start = new Date((dategiven.getTime()/86400000)*86400000);
						start.setDate(start.getDate() + 1);
						Date end = new Date(start.getTime());
						start.setDate(start.getDate() + 1);
						if (Activa.myProtocolManager.getQuestEvents(Activa.myPatientManager.currentPat.getId(), start, end))
							handler.sendEmptyMessage(1);
						else handler.sendEmptyMessage(2);
					}
					private Handler handler = new Handler() {
						@Override
						public void handleMessage(Message msg) {
							Date nextDate;
							switch (msg.what) {
								case 0:
									ImageView animationFrame = (ImageView) Activa.myApp.findViewById(R.id.animation);
									animationFrame.setVisibility(View.VISIBLE);
									animationFrame.setBackgroundResource(R.drawable.loading);
									final AnimationDrawable animation = (AnimationDrawable) animationFrame.getBackground();
									animation.start();
									break;
								case 1:
									nextDate = new Date(dategiven.getTime());
									nextDate.setDate(nextDate.getDate() + 1);
									Activa.myUIManager.loadScheduleDayForPatientQuest(nextDate);
									break;
								case 2:
									nextDate = new Date(dategiven.getTime());
									nextDate.setDate(nextDate.getDate() + 1);
									Activa.myUIManager.loadScheduleDayForPatientQuest(nextDate);
									Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.CONNECTION_FAILED);
									break;
							}
						}

					};
				};
				Thread thread = new Thread(run);
				thread.start();
			}
		});
		ImageButton add = (ImageButton) Activa.myApp.findViewById(R.id.help);
		add.setImageResource(R.drawable.plus);
		add.setVisibility(View.VISIBLE);
		add.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				LayoutInflater inflater = (LayoutInflater) Activa.myApp.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				final View layout = inflater.inflate(R.layout.toastaddpatevent,
                        (ViewGroup) Activa.myApp.findViewById(R.id.toastpataddeventroot));
				TextView title = (TextView) layout.findViewById(R.id.addtitle);
				title.setText(Activa.myLanguageManager.NOTIFICATION_EVENT_TITLE);
				TextView namereq = (TextView) layout.findViewById(R.id.namerequested);
				namereq.setText(Activa.myLanguageManager.NOTIFICATION_EVENT_NAMEREQ);
				TextView descreq = (TextView) layout.findViewById(R.id.descrequest);
				descreq.setText(Activa.myLanguageManager.NOTIFICATION_EVENT_DESC);
				TextView typereq = (TextView) layout.findViewById(R.id.typerequest);
				typereq.setText(Activa.myLanguageManager.NOTIFICATION_EVENT_TYPE);
				final Spinner type = (Spinner) layout.findViewById(R.id.type);
				ArrayList<String> spinnerStrings = new ArrayList<String>();
				final ArrayList<Long> spinnerQuestIds = new ArrayList<Long>();
				Enumeration<Questionnaire> elements = Activa.myPatientManager.currentPatQuestSet.elements();
				while (elements.hasMoreElements()) {
					Questionnaire quest = elements.nextElement();
					spinnerStrings.add(quest.getName());
					spinnerQuestIds.add(quest.getId());
				}
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(Activa.myApp, android.R.layout.simple_spinner_item, spinnerStrings);
			    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			    type.setAdapter(adapter);
			    type.setSelection(0);
				TextView startreq = (TextView) layout.findViewById(R.id.startrequest);
				startreq.setText(Activa.myLanguageManager.NOTIFICATION_EVENT_START);
				TextView endreq = (TextView) layout.findViewById(R.id.endrequest);
				endreq.setText(Activa.myLanguageManager.NOTIFICATION_EVENT_END);
				TextView freqreq = (TextView) layout.findViewById(R.id.freqrequest);
				freqreq.setText(Activa.myLanguageManager.NOTIFICATION_EVENT_FREQ);
				final Spinner frequency = (Spinner) layout.findViewById(R.id.freq);
			    ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(Activa.myApp, R.array.freqarray, android.R.layout.simple_spinner_item);
			    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			    frequency.setAdapter(adapter2);
			    ((LinearLayout)layout.findViewById(R.id.freqend)).setVisibility(View.GONE);
			    frequency.setOnItemSelectedListener(new OnItemSelectedListener() {
					@Override
					public void onItemSelected (AdapterView<?> parent, View view, int position, long id) {
						if (position == 0) ((LinearLayout)layout.findViewById(R.id.freqend)).setVisibility(View.GONE);
						else ((LinearLayout)layout.findViewById(R.id.freqend)).setVisibility(View.VISIBLE);
					}
					@Override
					public void onNothingSelected (AdapterView<?> parent) {
						((LinearLayout)layout.findViewById(R.id.freqend)).setVisibility(View.GONE);
					}
				});
			    TextView freqendreq = (TextView) layout.findViewById(R.id.freqendrequest);
				freqendreq.setText(Activa.myLanguageManager.NOTIFICATION_EVENT_FREQEND);
				ImageButton image = (ImageButton) layout.findViewById(R.id.addeventok);
				Builder builder = new AlertDialog.Builder(Activa.myApp);
				builder.setView(layout);
				final AlertDialog alertDialog = builder.create();
				alertDialog.show();
				image.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						final Long quest;
						final com.o2hlink.activ8.client.entity.Event eventtoadd = new com.o2hlink.activ8.client.entity.Event();
						eventtoadd.setName(((EditText) layout.findViewById(R.id.name)).getText().toString());
						eventtoadd.setDescription(((EditText) layout.findViewById(R.id.description)).getText().toString());
						quest = spinnerQuestIds.get(type.getSelectedItemPosition());
						Date start = new Date(0);
						start.setYear(((DatePicker) layout.findViewById(R.id.startdate)).getYear() - 1900);
						start.setMonth(((DatePicker) layout.findViewById(R.id.startdate)).getMonth());
						start.setDate(((DatePicker) layout.findViewById(R.id.startdate)).getDayOfMonth());
						start.setHours(((TimePicker) layout.findViewById(R.id.starttime)).getCurrentHour());
						start.setMinutes(((TimePicker) layout.findViewById(R.id.starttime)).getCurrentMinute());
						eventtoadd.setStart(start);
						Date end = new Date(0);
						end.setYear(((DatePicker) layout.findViewById(R.id.enddate)).getYear() - 1900);
						end.setMonth(((DatePicker) layout.findViewById(R.id.enddate)).getMonth());
						end.setDate(((DatePicker) layout.findViewById(R.id.enddate)).getDayOfMonth());
						end.setHours(((TimePicker) layout.findViewById(R.id.endtime)).getCurrentHour());
						end.setMinutes(((TimePicker) layout.findViewById(R.id.endtime)).getCurrentMinute());
						eventtoadd.setEnd(end);
						switch (frequency.getSelectedItemPosition()) {
							case 0:
								eventtoadd.setFrequency(EventFrequency.NONE);
								break;
							case 1:
								eventtoadd.setFrequency(EventFrequency.DAILY);
								break;
							case 2:
								eventtoadd.setFrequency(EventFrequency.WEEKLY);
								break;
							case 3:
								eventtoadd.setFrequency(EventFrequency.MONTHLY);
								break;
							case 4:
								eventtoadd.setFrequency(EventFrequency.YEARLY);
								break;
							case 5:
								eventtoadd.setFrequency(EventFrequency.MONWEDFRI);
								break;
							case 6:
								eventtoadd.setFrequency(EventFrequency.TUETHUR);
								break;
							case 7:
								eventtoadd.setFrequency(EventFrequency.WEEKDAY);
								break;
							default:
								eventtoadd.setFrequency(EventFrequency.NONE);
								break;
						}
						Date freqend = new Date(0);
						freqend.setYear(((DatePicker) layout.findViewById(R.id.freqenddate)).getYear() - 1900);
						freqend.setMonth(((DatePicker) layout.findViewById(R.id.freqenddate)).getMonth());
						freqend.setDate(((DatePicker) layout.findViewById(R.id.freqenddate)).getDayOfMonth());
						freqend.setHours(((TimePicker) layout.findViewById(R.id.freqendtime)).getCurrentHour());
						freqend.setMinutes(((TimePicker) layout.findViewById(R.id.freqendtime)).getCurrentMinute());
						eventtoadd.setEndFrequency(freqend);
						final ProgressDialog dialog = ProgressDialog.show(Activa.myApp, Activa.myLanguageManager.NOTIFICATION_EVENT_TITLE, 
								Activa.myLanguageManager.NOTIFICATION_EVENT_ADDING, true);
						dialog.show();
						Runnable run =  new Runnable() {
							@Override
							public void run() {
								if (Activa.myPatientManager.addQuestEvent(Activa.myPatientManager.currentPat.getId(), quest, eventtoadd))
									handler.sendEmptyMessage(1);
								else 
									handler.sendEmptyMessage(0);
							}
							private Handler handler = new Handler() {
								@Override
								public void handleMessage(Message msg) {
									switch (msg.what) {
										case 0:
											Toast toast = Toast.makeText(Activa.myApp, Activa.myLanguageManager.CONNECTION_FAILED, Toast.LENGTH_SHORT);
											Activa.myUIManager.loadScheduleDayForPatientQuest(dategiven);
											dialog.cancel();
											toast.show();
											break;
										case 1:
											Activa.myUIManager.loadScheduleDayForPatientQuest(dategiven);
											dialog.cancel();
											break;
									}
								}

							};
						};
						Thread thread = new Thread(run);
						thread.start();
						alertDialog.cancel();
					}
				});
			}
		});
	}
	
	public void loadScheduleWeekForPatientQuest(final Date dategiven){
		TextView time;
		com.o2hlink.pimtools.patient.Event event = null; 
		Calendar cal = Calendar.getInstance();
		cal.setTime(dategiven);
		Date date = new Date((dategiven.getTime()/3600000)*3600000);
		date.setHours(0);
		int day = cal.get(Calendar.DAY_OF_WEEK)-2;
		if (day == -1) day = 6;
		date.setDate(date.getDate() - day);
		Date dateLater = new Date(date.getTime());
		dateLater.setDate(date.getDate() + 1);
		TableRow buttonLayout;
		this.state = UI_STATE_SCHEDULEWEEKFORPATIENTQUEST;
		if (Activa.myMenu != null) {
			Activa.myMenu.clear();
			Activa.myInflater.inflate(R.menu.scheduleforpatientforquest, Activa.myMenu);
		}
		Hashtable<Date, com.o2hlink.pimtools.patient.Event> eventsOrdered = new Hashtable<Date, com.o2hlink.pimtools.patient.Event>();
		Vector<Date> datesOrdered = new Vector<Date>();
		Enumeration<com.o2hlink.pimtools.patient.Event> enumer = Activa.myPatientManager.currentPatQuestEventSet.elements();
		while (enumer.hasMoreElements()) {
			com.o2hlink.pimtools.patient.Event temp = enumer.nextElement();
			while (datesOrdered.contains(temp.date)) temp.date.setTime(temp.date.getTime() + 1);
			Timestamp stamp = new Timestamp(temp.date.getTime());
			datesOrdered.add(stamp);
			eventsOrdered.put(stamp, temp);
		}
		Collections.sort(datesOrdered);
		Activa.myApp.setContentView(R.layout.scheduleday);
		((TextView) Activa.myApp.findViewById(R.id.startText)).setText(Activa.myLanguageManager.CALENDAR_TITLE);
		((TextView) Activa.myApp.findViewById(R.id.startText)).setTextSize(24);
		ImageButton back = (ImageButton) Activa.myApp.findViewById(R.id.previous);
		ImageButton prev = (ImageButton) Activa.myApp.findViewById(R.id.previousday);
		ImageButton next = (ImageButton) Activa.myApp.findViewById(R.id.nextday);
		TextView dateText = (TextView) Activa.myApp.findViewById(R.id.date);
		dateText.setText(Activa.myLanguageManager.CALENDAR_WEEK + " " + cal.get(Calendar.WEEK_OF_YEAR) + " "+ Activa.myLanguageManager.CALENDAR_OF + " " + cal.get(Calendar.YEAR));
		TableLayout schedule = (TableLayout)Activa.myApp.findViewById(R.id.schedule);
		for (int i = 0; i < 7; i++) {	
			time = new TextView(Activa.myApp);	
			time.setText(ActivaUtil.dateToReadableString(date));
			time.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
			time.setPadding(5, 10, 5, 10);
			time.setTypeface(Typeface.DEFAULT_BOLD);
			time.setTextSize(20);
			time.setGravity(Gravity.CENTER);
			time.setTextColor(Color.BLACK);
			schedule.addView(time);		
			View separator = new View(Activa.myApp);
			LayoutParams separatorLayout = new LayoutParams(android.view.ViewGroup.LayoutParams.FILL_PARENT, 1);
			separator.setLayoutParams(separatorLayout);
			separator.setBackgroundColor(Color.BLACK);				
			schedule.addView(separator);
			for(int j = 0; j < datesOrdered.size(); j++) {
				event = eventsOrdered.get(datesOrdered.get(j));
				final String id = event.id;
				if ((date.compareTo(event.getDate()) <= 0)&&(dateLater.compareTo(event.getDate()) > 0)) {
					buttonLayout = new TableRow(Activa.myApp);
					buttonLayout.setOrientation(TableRow.HORIZONTAL);
					buttonLayout.setGravity(Gravity.CENTER_VERTICAL);
					buttonLayout.setLayoutParams(new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, 40));			
					time = new TextView(Activa.myApp);
					time.setPadding(5, 10, 5, 10);
					time.setTextSize(20);
					time.setTextColor(Color.BLACK);
					time.setTypeface(Typeface.SANS_SERIF);
					time.setText(String.format("%02d:%02d", event.date.getHours(), event.date.getMinutes()));
					buttonLayout.addView(time);
					TextView activity = new TextView(Activa.myApp);
					activity.setLayoutParams(new TableRow.LayoutParams(200, LayoutParams.WRAP_CONTENT));
					activity.setText(event.name);
					activity.setPadding(5, 10, 5, 10);
					activity.setTextSize(20);
					activity.setTextColor(Color.BLACK);
					activity.setTypeface(Typeface.SANS_SERIF);
					buttonLayout.addView(activity);
					ImageView button = new ImageView(Activa.myApp);
					if (event.type == 2) button.setBackgroundResource(R.drawable.event);
					else switch (event.state) {
						case 0:
							button.setBackgroundResource(R.drawable.oksmall);
							break;
						case 1:
							button.setBackgroundResource(R.drawable.cancel);
							break;
						case 2:
							button.setBackgroundResource(R.drawable.nextsmall);
							break;
					}
					buttonLayout.addView(button);	
					schedule.addView(buttonLayout);
					time.setOnClickListener(new OnClickListener() {				
						@Override
						public void onClick(View v) {
							if (Activa.myMenu != null) Activa.myMenu.clear();
							Activa.myPatientManager.currentPatQuestEventSet.get(id).doActivity();
						}
					});	
					activity.setOnClickListener(new OnClickListener() {				
						@Override
						public void onClick(View v) {
							if (Activa.myMenu != null) Activa.myMenu.clear();
							Activa.myPatientManager.currentPatQuestEventSet.get(id).doActivity();
						}
					});
					button.setOnClickListener(new OnClickListener() {				
						@Override
						public void onClick(View v) {
							if (Activa.myMenu != null) Activa.myMenu.clear();
							Activa.myPatientManager.currentPatQuestEventSet.get(id).doActivity();
						}
					});	
					buttonLayout.setOnClickListener(new OnClickListener() {				
						@Override
						public void onClick(View v) {
							if (Activa.myMenu != null) Activa.myMenu.clear();
							Activa.myPatientManager.currentPatQuestEventSet.get(id).doActivity();
						}
					});	
					separator = new View(Activa.myApp);
					separatorLayout = new LayoutParams(android.view.ViewGroup.LayoutParams.FILL_PARENT, 1);
					separator.setLayoutParams(separatorLayout);
					separator.setBackgroundColor(Color.BLACK);
					schedule.addView(separator);
				}
			}
			date.setDate(date.getDate() + 1);
			dateLater.setDate(dateLater.getDate() + 1);
		}
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Activa.myUIManager.loadPatientMenu(Activa.myPatientManager.currentPat);
			}
		});
		prev.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Runnable run = new Runnable() {
					@Override
					public void run() {
						handler.sendEmptyMessage(0);
						int day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)-2;
						if (day == -1) day = 6;
						Date start = new Date((dategiven.getTime()/86400000)*86400000);
						start.setDate(start.getDate() - day - 7);
						Date end = new Date(start.getTime());
						end.setDate(end.getDate() + 7);
						if (Activa.myProtocolManager.getQuestEvents(Activa.myPatientManager.currentPat.getId(), start, end))
								handler.sendEmptyMessage(1);
						else handler.sendEmptyMessage(2);
					}
					private Handler handler = new Handler() {
						@Override
						public void handleMessage(Message msg) {
							Date nextDate;
							switch (msg.what) {
								case 0:
									ImageView animationFrame = (ImageView) Activa.myApp.findViewById(R.id.animation);
									animationFrame.setVisibility(View.VISIBLE);
									animationFrame.setBackgroundResource(R.drawable.loading);
									final AnimationDrawable animation = (AnimationDrawable) animationFrame.getBackground();
									animation.start();
									break;
								case 1:
									nextDate = new Date(dategiven.getTime());
									nextDate.setDate(nextDate.getDate() - 7);
									Activa.myUIManager.loadScheduleWeekForPatientQuest(nextDate);
									break;
								case 2:
									nextDate = new Date(dategiven.getTime());
									nextDate.setDate(nextDate.getDate() - 7);
									Activa.myUIManager.loadScheduleWeekForPatientQuest(nextDate);
									Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.CONNECTION_FAILED);
									break;
							}
						}

					};
				};
				Thread thread = new Thread(run);
				thread.start();
			}
		});
		next.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Runnable run = new Runnable() {
					@Override
					public void run() {
						handler.sendEmptyMessage(0);
						int day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)-2;
						if (day == -1) day = 6;
						Date start = new Date((dategiven.getTime()/86400000)*86400000);
						start.setDate(start.getDate() - day + 7);
						Date end = new Date(start.getTime());
						end.setDate(end.getDate() + 7);
						if (Activa.myProtocolManager.getQuestEvents(Activa.myPatientManager.currentPat.getId(), start, end))
							handler.sendEmptyMessage(1);
						else handler.sendEmptyMessage(2);
					}
					private Handler handler = new Handler() {
						@Override
						public void handleMessage(Message msg) {
							Date nextDate;
							switch (msg.what) {
								case 0:
									ImageView animationFrame = (ImageView) Activa.myApp.findViewById(R.id.animation);
									animationFrame.setVisibility(View.VISIBLE);
									animationFrame.setBackgroundResource(R.drawable.loading);
									final AnimationDrawable animation = (AnimationDrawable) animationFrame.getBackground();
									animation.start();
									break;
								case 1:
									nextDate = new Date(dategiven.getTime());
									nextDate.setDate(nextDate.getDate() + 7);
									Activa.myUIManager.loadScheduleWeekForPatientQuest(nextDate);
									break;
								case 2:
									nextDate = new Date(dategiven.getTime());
									nextDate.setDate(nextDate.getDate() + 7);
									Activa.myUIManager.loadScheduleWeekForPatientQuest(nextDate);
									Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.CONNECTION_FAILED);
									break;
							}
						}

					};
				};
				Thread thread = new Thread(run);
				thread.start();
			}
		});
		ImageButton add = (ImageButton) Activa.myApp.findViewById(R.id.help);
		add.setImageResource(R.drawable.plus);
		add.setVisibility(View.VISIBLE);
		add.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				LayoutInflater inflater = (LayoutInflater) Activa.myApp.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				final View layout = inflater.inflate(R.layout.toastaddpatevent,
                        (ViewGroup) Activa.myApp.findViewById(R.id.toastpataddeventroot));
				TextView title = (TextView) layout.findViewById(R.id.addtitle);
				title.setText(Activa.myLanguageManager.NOTIFICATION_EVENT_TITLE);
				TextView namereq = (TextView) layout.findViewById(R.id.namerequested);
				namereq.setText(Activa.myLanguageManager.NOTIFICATION_EVENT_NAMEREQ);
				TextView descreq = (TextView) layout.findViewById(R.id.descrequest);
				descreq.setText(Activa.myLanguageManager.NOTIFICATION_EVENT_DESC);
				TextView typereq = (TextView) layout.findViewById(R.id.typerequest);
				typereq.setText(Activa.myLanguageManager.NOTIFICATION_EVENT_TYPE);
				final Spinner type = (Spinner) layout.findViewById(R.id.type);
				ArrayList<String> spinnerStrings = new ArrayList<String>();
				final ArrayList<Long> spinnerQuestIds = new ArrayList<Long>();
				Enumeration<Questionnaire> elements = Activa.myPatientManager.currentPatQuestSet.elements();
				while (elements.hasMoreElements()) {
					Questionnaire quest = elements.nextElement();
					spinnerStrings.add(quest.getName());
					spinnerQuestIds.add(quest.getId());
				}
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(Activa.myApp, android.R.layout.simple_spinner_item, spinnerStrings);
			    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			    type.setAdapter(adapter);
			    type.setSelection(0);
				TextView startreq = (TextView) layout.findViewById(R.id.startrequest);
				startreq.setText(Activa.myLanguageManager.NOTIFICATION_EVENT_START);
				TextView endreq = (TextView) layout.findViewById(R.id.endrequest);
				endreq.setText(Activa.myLanguageManager.NOTIFICATION_EVENT_END);
				TextView freqreq = (TextView) layout.findViewById(R.id.freqrequest);
				freqreq.setText(Activa.myLanguageManager.NOTIFICATION_EVENT_FREQ);
				final Spinner frequency = (Spinner) layout.findViewById(R.id.freq);
			    ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(Activa.myApp, R.array.freqarray, android.R.layout.simple_spinner_item);
			    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			    frequency.setAdapter(adapter2);
			    ((LinearLayout)layout.findViewById(R.id.freqend)).setVisibility(View.GONE);
			    frequency.setOnItemSelectedListener(new OnItemSelectedListener() {
					@Override
					public void onItemSelected (AdapterView<?> parent, View view, int position, long id) {
						if (position == 0) ((LinearLayout)layout.findViewById(R.id.freqend)).setVisibility(View.GONE);
						else ((LinearLayout)layout.findViewById(R.id.freqend)).setVisibility(View.VISIBLE);
					}
					@Override
					public void onNothingSelected (AdapterView<?> parent) {
						((LinearLayout)layout.findViewById(R.id.freqend)).setVisibility(View.GONE);
					}
				});
			    TextView freqendreq = (TextView) layout.findViewById(R.id.freqendrequest);
				freqendreq.setText(Activa.myLanguageManager.NOTIFICATION_EVENT_FREQEND);
				ImageButton image = (ImageButton) layout.findViewById(R.id.addeventok);
				Builder builder = new AlertDialog.Builder(Activa.myApp);
				builder.setView(layout);
				final AlertDialog alertDialog = builder.create();
				alertDialog.show();
				image.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						final Long quest;
						final com.o2hlink.activ8.client.entity.Event eventtoadd = new com.o2hlink.activ8.client.entity.Event();
						eventtoadd.setName(((EditText) layout.findViewById(R.id.name)).getText().toString());
						eventtoadd.setDescription(((EditText) layout.findViewById(R.id.description)).getText().toString());
						quest = spinnerQuestIds.get(type.getSelectedItemPosition());
						Date start = new Date(0);
						start.setYear(((DatePicker) layout.findViewById(R.id.startdate)).getYear() - 1900);
						start.setMonth(((DatePicker) layout.findViewById(R.id.startdate)).getMonth());
						start.setDate(((DatePicker) layout.findViewById(R.id.startdate)).getDayOfMonth());
						start.setHours(((TimePicker) layout.findViewById(R.id.starttime)).getCurrentHour());
						start.setMinutes(((TimePicker) layout.findViewById(R.id.starttime)).getCurrentMinute());
						eventtoadd.setStart(start);
						Date end = new Date(0);
						end.setYear(((DatePicker) layout.findViewById(R.id.enddate)).getYear() - 1900);
						end.setMonth(((DatePicker) layout.findViewById(R.id.enddate)).getMonth());
						end.setDate(((DatePicker) layout.findViewById(R.id.enddate)).getDayOfMonth());
						end.setHours(((TimePicker) layout.findViewById(R.id.endtime)).getCurrentHour());
						end.setMinutes(((TimePicker) layout.findViewById(R.id.endtime)).getCurrentMinute());
						eventtoadd.setEnd(end);
						switch (frequency.getSelectedItemPosition()) {
							case 0:
								eventtoadd.setFrequency(EventFrequency.NONE);
								break;
							case 1:
								eventtoadd.setFrequency(EventFrequency.DAILY);
								break;
							case 2:
								eventtoadd.setFrequency(EventFrequency.WEEKLY);
								break;
							case 3:
								eventtoadd.setFrequency(EventFrequency.MONTHLY);
								break;
							case 4:
								eventtoadd.setFrequency(EventFrequency.YEARLY);
								break;
							case 5:
								eventtoadd.setFrequency(EventFrequency.MONWEDFRI);
								break;
							case 6:
								eventtoadd.setFrequency(EventFrequency.TUETHUR);
								break;
							case 7:
								eventtoadd.setFrequency(EventFrequency.WEEKDAY);
								break;
							default:
								eventtoadd.setFrequency(EventFrequency.NONE);
								break;
						}
						Date freqend = new Date(0);
						freqend.setYear(((DatePicker) layout.findViewById(R.id.freqenddate)).getYear() - 1900);
						freqend.setMonth(((DatePicker) layout.findViewById(R.id.freqenddate)).getMonth());
						freqend.setDate(((DatePicker) layout.findViewById(R.id.freqenddate)).getDayOfMonth());
						freqend.setHours(((TimePicker) layout.findViewById(R.id.freqendtime)).getCurrentHour());
						freqend.setMinutes(((TimePicker) layout.findViewById(R.id.freqendtime)).getCurrentMinute());
						eventtoadd.setEndFrequency(freqend);
						final ProgressDialog dialog = ProgressDialog.show(Activa.myApp, Activa.myLanguageManager.NOTIFICATION_EVENT_TITLE, 
								Activa.myLanguageManager.NOTIFICATION_EVENT_ADDING, true);
						dialog.show();
						Runnable run =  new Runnable() {
							@Override
							public void run() {
								if (Activa.myPatientManager.addQuestEvent(Activa.myPatientManager.currentPat.getId(), quest, eventtoadd))
									handler.sendEmptyMessage(1);
								else 
									handler.sendEmptyMessage(0);
							}
							private Handler handler = new Handler() {
								@Override
								public void handleMessage(Message msg) {
									switch (msg.what) {
										case 0:
											Toast toast = Toast.makeText(Activa.myApp, Activa.myLanguageManager.CONNECTION_FAILED, Toast.LENGTH_SHORT);
											Activa.myUIManager.loadScheduleWeekForPatientQuest(dategiven);
											dialog.cancel();
											toast.show();
											break;
										case 1:
											Activa.myUIManager.loadScheduleWeekForPatientQuest(dategiven);
											dialog.cancel();
											break;
									}
								}

							};
						};
						Thread thread = new Thread(run);
						thread.start();
						alertDialog.cancel();
					}
				});
			}
		});
	}
	
	public void loadScheduleMonthForPatientQuest(final Date dategiven) {
		TextView time;
		View separator;
		LayoutParams separatorLayout;
		TableRow weekLayout;
		Enumeration<com.o2hlink.pimtools.patient.Event> enumeration;
		com.o2hlink.pimtools.patient.Event event = null; 
		Date date = new Date((dategiven.getTime()/3600000)*3600000);
		date.setHours(0);
		date.setDate(1);
		Date dateLater = new Date(date.getTime());
		dateLater.setDate(2);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int weekday = cal.get(Calendar.DAY_OF_WEEK)-2;
		if (weekday == -1) weekday = 6;
		Date dateLimit = new Date(date.getTime());
		dateLimit.setMonth(date.getMonth() + 1);
		this.state = UI_STATE_SCHEDULEMONTHFORPATIENTQUEST;
		if (Activa.myMenu != null) {
			Activa.myMenu.clear();
			Activa.myInflater.inflate(R.menu.scheduleforpatientforquest, Activa.myMenu);
		}
		Activa.myApp.setContentView(R.layout.schedulemonth);
		((TextView) Activa.myApp.findViewById(R.id.startText)).setText(Activa.myLanguageManager.CALENDAR_TITLE);
		((TextView) Activa.myApp.findViewById(R.id.startText)).setTextSize(24);
		ImageButton back = (ImageButton) Activa.myApp.findViewById(R.id.previous);
		ImageButton prev = (ImageButton) Activa.myApp.findViewById(R.id.previousday);
		ImageButton next = (ImageButton) Activa.myApp.findViewById(R.id.nextday);
		TextView dateText = (TextView) Activa.myApp.findViewById(R.id.date);
		dateText.setText(ActivaUtil.monthOfDate(date) + " " + cal.get(Calendar.YEAR));
		TableLayout schedule = (TableLayout)Activa.myApp.findViewById(R.id.schedule);
		weekLayout = new TableRow(Activa.myApp);
		weekLayout.setOrientation(TableRow.HORIZONTAL);
		weekLayout.setGravity(Gravity.CENTER_VERTICAL);
		weekLayout.setLayoutParams(new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		separator = new View(Activa.myApp);
		separator.setLayoutParams(new TableRow.LayoutParams(1, 36));
		separator.setBackgroundColor(Color.BLACK);
		weekLayout.addView(separator);
		String[] weekdaynames = {Activa.myLanguageManager.WEEK_MONDAY,
				Activa.myLanguageManager.WEEK_TUESDAY,
				Activa.myLanguageManager.WEEK_WEDNESDAY,
				Activa.myLanguageManager.WEEK_THURSDAY,
				Activa.myLanguageManager.WEEK_FRYDAY,
				Activa.myLanguageManager.WEEK_SATURDAY,
				Activa.myLanguageManager.WEEK_SUNDAY}; 
		for (int i = 0; i < weekdaynames.length; i++) {
			time = new TextView(Activa.myApp);
			time.setGravity(Gravity.CENTER);
			time.setTextSize(20);
			time.setText(weekdaynames[i]);
			time.setTag(date.getDate());
			time.setTypeface(Typeface.DEFAULT_BOLD);
			time.setLayoutParams(new TableRow.LayoutParams(36, 36));	
			time.setTextColor(Color.BLACK);
			weekLayout.addView(time);
		}
		separator = new View(Activa.myApp);
		separator.setLayoutParams(new TableRow.LayoutParams(1, 36));
		separator.setBackgroundColor(Color.BLACK);
		weekLayout.addView(separator);
		schedule.addView(weekLayout);	
		separator = new View(Activa.myApp);
		separatorLayout = new LayoutParams(android.view.ViewGroup.LayoutParams.FILL_PARENT, 1);
		separator.setLayoutParams(separatorLayout);
		separator.setBackgroundColor(Color.BLACK);
		schedule.addView(separator);	
		weekLayout = new TableRow(Activa.myApp);
		weekLayout.setOrientation(TableRow.HORIZONTAL);
		weekLayout.setGravity(Gravity.CENTER_VERTICAL);
		weekLayout.setLayoutParams(new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		separator = new View(Activa.myApp);
		separator.setLayoutParams(new TableRow.LayoutParams(1, 36));
		separator.setBackgroundColor(Color.BLACK);
		weekLayout.addView(separator);
		for (int i = 0; i < weekday; i++) {		
			View space = new View(Activa.myApp);
			space.setLayoutParams(new TableRow.LayoutParams(36, 36));	
			weekLayout.addView(space);
		}
		while(date.before(dateLimit)) {	
			time = new TextView(Activa.myApp);
			time.setGravity(Gravity.CENTER);
			time.setTextSize(20);
			time.setText("" + date.getDate());
			time.setTag(date.getDate());
			time.setTypeface(Typeface.SANS_SERIF);
			time.setLayoutParams(new TableRow.LayoutParams(36, 36));	
			int state = 3;
			enumeration = Activa.myPatientManager.currentPatQuestEventSet.elements();
			while (enumeration.hasMoreElements()) {
				event = enumeration.nextElement();
				if ((date.compareTo(event.getDate()) <= 0)&&(dateLater.compareTo(event.getDate()) > 0)) {
					if (((event.type == 2)||(event.state == 0))&&(state == 3)) {
						state = 0;
						continue;
					}
					if ((event.state == 2)&&((state == 3)||(state == 0))) {
						state = 2;
						continue;
					}
					if ((event.state == 1)&&((state == 3)||(state == 0)||(state == 2))) {
						state = 1;
						break;
					}
				}
			}
			switch (state) {
				case 0:
					time.setTextColor(Color.GREEN);
					break;
				case 1:
					time.setTextColor(Color.RED);
					break;
				case 2:
					time.setTextColor(Color.parseColor("#ffad00"));
					break;
				case 3:
					time.setTextColor(Color.BLACK);
					break;
			}
			time.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Date newDate = new Date(dategiven.getTime());
					newDate.setDate((Integer)v.getTag());
					Activa.myUIManager.loadScheduleDayForPatientQuest(newDate);
				}
			});
			weekLayout.addView(time);
			date.setDate(date.getDate() + 1);
			dateLater.setDate(dateLater.getDate() + 1);
			weekday++;
			if (weekday == 7) {		
				separator = new View(Activa.myApp);
				separator.setLayoutParams(new TableRow.LayoutParams(1, 36));
				separator.setBackgroundColor(Color.BLACK);
				weekLayout.addView(separator);
				schedule.addView(weekLayout);	
				separator = new View(Activa.myApp);
				separatorLayout = new LayoutParams(android.view.ViewGroup.LayoutParams.FILL_PARENT, 1);
				separator.setLayoutParams(separatorLayout);
				separator.setBackgroundColor(Color.BLACK);
				schedule.addView(separator);	
				weekday = 0;
				weekLayout = new TableRow(Activa.myApp);
				weekLayout.setOrientation(TableRow.HORIZONTAL);
				weekLayout.setGravity(Gravity.CENTER_VERTICAL);
				weekLayout.setLayoutParams(new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
				separator = new View(Activa.myApp);
				separator.setLayoutParams(new TableRow.LayoutParams(1, 36));
				separator.setBackgroundColor(Color.BLACK);
				weekLayout.addView(separator);
			}
		}
		for (int i = weekday; i < 7; i++) {	
			time = new TextView(Activa.myApp);
			time.setGravity(Gravity.CENTER);
			time.setTextSize(20);
			time.setTextColor(Color.BLACK);
			time.setTypeface(Typeface.SANS_SERIF);
			time.setLayoutParams(new TableRow.LayoutParams(36, 36));	
			weekLayout.addView(time);
		}	
		separator = new View(Activa.myApp);
		separator.setLayoutParams(new TableRow.LayoutParams(1, 36));
		separator.setBackgroundColor(Color.BLACK);
		weekLayout.addView(separator);
		schedule.addView(weekLayout);
		separator = new View(Activa.myApp);
		separatorLayout = new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, 1);
		separator.setLayoutParams(separatorLayout);
		separator.setBackgroundColor(Color.BLACK);
		schedule.addView(separator);	
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Activa.myUIManager.loadPatientMenu(Activa.myPatientManager.currentPat);
			}
		});
		prev.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Runnable run = new Runnable() {
					@Override
					public void run() {
						handler.sendEmptyMessage(0);
						Date start = new Date((dategiven.getTime()/86400000)*86400000);
						start.setDate(1);
						start.setMonth(start.getMonth() - 1);
						Date end = new Date(start.getTime());
						end.setMonth(end.getMonth() + 1);
						if (Activa.myProtocolManager.getQuestEvents(Activa.myPatientManager.currentPat.getId(), start, end))
							handler.sendEmptyMessage(1);
						else handler.sendEmptyMessage(2);
					}
					private Handler handler = new Handler() {
						@Override
						public void handleMessage(Message msg) {
							Date nextDate;
							switch (msg.what) {
								case 0:
									ImageView animationFrame = (ImageView) Activa.myApp.findViewById(R.id.animation);
									animationFrame.setVisibility(View.VISIBLE);
									animationFrame.setBackgroundResource(R.drawable.loading);
									final AnimationDrawable animation = (AnimationDrawable) animationFrame.getBackground();
									animation.start();
									break;
								case 1:
									nextDate = new Date(dategiven.getTime());
									nextDate.setMonth(nextDate.getMonth() - 1);
									Activa.myUIManager.loadScheduleMonthForPatientQuest(nextDate);
									break;
								case 2:
									nextDate = new Date(dategiven.getTime());
									nextDate.setMonth(nextDate.getMonth() - 1);
									Activa.myUIManager.loadScheduleMonthForPatientQuest(nextDate);
									Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.CONNECTION_FAILED);
									break;
							}
						}

					};
				};
				Thread thread = new Thread(run);
				thread.start();
			}
		});
		next.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Runnable run = new Runnable() {
					@Override
					public void run() {
						handler.sendEmptyMessage(0);
						Date start = new Date((dategiven.getTime()/86400000)*86400000);
						start.setDate(1);
						start.setMonth(start.getMonth() + 1);
						Date end = new Date(start.getTime());
						end.setMonth(end.getMonth() + 1);
						if (Activa.myProtocolManager.getQuestEvents(Activa.myPatientManager.currentPat.getId(), start, end))
							handler.sendEmptyMessage(1);
						else handler.sendEmptyMessage(2);
					}
					private Handler handler = new Handler() {
						@Override
						public void handleMessage(Message msg) {
							Date nextDate;
							switch (msg.what) {
								case 0:
									ImageView animationFrame = (ImageView) Activa.myApp.findViewById(R.id.animation);
									animationFrame.setVisibility(View.VISIBLE);
									animationFrame.setBackgroundResource(R.drawable.loading);
									final AnimationDrawable animation = (AnimationDrawable) animationFrame.getBackground();
									animation.start();
									break;
								case 1:
									nextDate = new Date(dategiven.getTime());
									nextDate.setMonth(nextDate.getMonth() + 1);
									Activa.myUIManager.loadScheduleMonthForPatientQuest(nextDate);
									break;
								case 2:
									nextDate = new Date(dategiven.getTime());
									nextDate.setMonth(nextDate.getMonth() + 1);
									Activa.myUIManager.loadScheduleMonthForPatientQuest(nextDate);
									Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.CONNECTION_FAILED);
									break;
							}
						}

					};
				};
				Thread thread = new Thread(run);
				thread.start();
			}
		});
		ImageButton add = (ImageButton) Activa.myApp.findViewById(R.id.help);
		add.setImageResource(R.drawable.plus);
		add.setVisibility(View.VISIBLE);
		add.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				LayoutInflater inflater = (LayoutInflater) Activa.myApp.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				final View layout = inflater.inflate(R.layout.toastaddpatevent,
                        (ViewGroup) Activa.myApp.findViewById(R.id.toastpataddeventroot));
				TextView title = (TextView) layout.findViewById(R.id.addtitle);
				title.setText(Activa.myLanguageManager.NOTIFICATION_EVENT_TITLE);
				TextView namereq = (TextView) layout.findViewById(R.id.namerequested);
				namereq.setText(Activa.myLanguageManager.NOTIFICATION_EVENT_NAMEREQ);
				TextView descreq = (TextView) layout.findViewById(R.id.descrequest);
				descreq.setText(Activa.myLanguageManager.NOTIFICATION_EVENT_DESC);
				TextView typereq = (TextView) layout.findViewById(R.id.typerequest);
				typereq.setText(Activa.myLanguageManager.NOTIFICATION_EVENT_TYPE);
				final Spinner type = (Spinner) layout.findViewById(R.id.type);
				ArrayList<String> spinnerStrings = new ArrayList<String>();
				final ArrayList<Long> spinnerQuestIds = new ArrayList<Long>();
				Enumeration<Questionnaire> elements = Activa.myPatientManager.currentPatQuestSet.elements();
				while (elements.hasMoreElements()) {
					Questionnaire quest = elements.nextElement();
					spinnerStrings.add(quest.getName());
					spinnerQuestIds.add(quest.getId());
				}
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(Activa.myApp, android.R.layout.simple_spinner_item, spinnerStrings);
			    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			    type.setAdapter(adapter);
			    type.setSelection(0);
				TextView startreq = (TextView) layout.findViewById(R.id.startrequest);
				startreq.setText(Activa.myLanguageManager.NOTIFICATION_EVENT_START);
				TextView endreq = (TextView) layout.findViewById(R.id.endrequest);
				endreq.setText(Activa.myLanguageManager.NOTIFICATION_EVENT_END);
				TextView freqreq = (TextView) layout.findViewById(R.id.freqrequest);
				freqreq.setText(Activa.myLanguageManager.NOTIFICATION_EVENT_FREQ);
				final Spinner frequency = (Spinner) layout.findViewById(R.id.freq);
			    ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(Activa.myApp, R.array.freqarray, android.R.layout.simple_spinner_item);
			    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			    frequency.setAdapter(adapter2);
			    ((LinearLayout)layout.findViewById(R.id.freqend)).setVisibility(View.GONE);
			    frequency.setOnItemSelectedListener(new OnItemSelectedListener() {
					@Override
					public void onItemSelected (AdapterView<?> parent, View view, int position, long id) {
						if (position == 0) ((LinearLayout)layout.findViewById(R.id.freqend)).setVisibility(View.GONE);
						else ((LinearLayout)layout.findViewById(R.id.freqend)).setVisibility(View.VISIBLE);
					}
					@Override
					public void onNothingSelected (AdapterView<?> parent) {
						((LinearLayout)layout.findViewById(R.id.freqend)).setVisibility(View.GONE);
					}
				});
			    TextView freqendreq = (TextView) layout.findViewById(R.id.freqendrequest);
				freqendreq.setText(Activa.myLanguageManager.NOTIFICATION_EVENT_FREQEND);
				ImageButton image = (ImageButton) layout.findViewById(R.id.addeventok);
				Builder builder = new AlertDialog.Builder(Activa.myApp);
				builder.setView(layout);
				final AlertDialog alertDialog = builder.create();
				alertDialog.show();
				image.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						final Long quest;
						final com.o2hlink.activ8.client.entity.Event eventtoadd = new com.o2hlink.activ8.client.entity.Event();
						eventtoadd.setName(((EditText) layout.findViewById(R.id.name)).getText().toString());
						eventtoadd.setDescription(((EditText) layout.findViewById(R.id.description)).getText().toString());
						quest = spinnerQuestIds.get(type.getSelectedItemPosition());
						Date start = new Date(0);
						start.setYear(((DatePicker) layout.findViewById(R.id.startdate)).getYear() - 1900);
						start.setMonth(((DatePicker) layout.findViewById(R.id.startdate)).getMonth());
						start.setDate(((DatePicker) layout.findViewById(R.id.startdate)).getDayOfMonth());
						start.setHours(((TimePicker) layout.findViewById(R.id.starttime)).getCurrentHour());
						start.setMinutes(((TimePicker) layout.findViewById(R.id.starttime)).getCurrentMinute());
						eventtoadd.setStart(start);
						Date end = new Date(0);
						end.setYear(((DatePicker) layout.findViewById(R.id.enddate)).getYear() - 1900);
						end.setMonth(((DatePicker) layout.findViewById(R.id.enddate)).getMonth());
						end.setDate(((DatePicker) layout.findViewById(R.id.enddate)).getDayOfMonth());
						end.setHours(((TimePicker) layout.findViewById(R.id.endtime)).getCurrentHour());
						end.setMinutes(((TimePicker) layout.findViewById(R.id.endtime)).getCurrentMinute());
						eventtoadd.setEnd(end);
						switch (frequency.getSelectedItemPosition()) {
							case 0:
								eventtoadd.setFrequency(EventFrequency.NONE);
								break;
							case 1:
								eventtoadd.setFrequency(EventFrequency.DAILY);
								break;
							case 2:
								eventtoadd.setFrequency(EventFrequency.WEEKLY);
								break;
							case 3:
								eventtoadd.setFrequency(EventFrequency.MONTHLY);
								break;
							case 4:
								eventtoadd.setFrequency(EventFrequency.YEARLY);
								break;
							case 5:
								eventtoadd.setFrequency(EventFrequency.MONWEDFRI);
								break;
							case 6:
								eventtoadd.setFrequency(EventFrequency.TUETHUR);
								break;
							case 7:
								eventtoadd.setFrequency(EventFrequency.WEEKDAY);
								break;
							default:
								eventtoadd.setFrequency(EventFrequency.NONE);
								break;
						}
						Date freqend = new Date(0);
						freqend.setYear(((DatePicker) layout.findViewById(R.id.freqenddate)).getYear() - 1900);
						freqend.setMonth(((DatePicker) layout.findViewById(R.id.freqenddate)).getMonth());
						freqend.setDate(((DatePicker) layout.findViewById(R.id.freqenddate)).getDayOfMonth());
						freqend.setHours(((TimePicker) layout.findViewById(R.id.freqendtime)).getCurrentHour());
						freqend.setMinutes(((TimePicker) layout.findViewById(R.id.freqendtime)).getCurrentMinute());
						eventtoadd.setEndFrequency(freqend);
						final ProgressDialog dialog = ProgressDialog.show(Activa.myApp, Activa.myLanguageManager.NOTIFICATION_EVENT_TITLE, 
								Activa.myLanguageManager.NOTIFICATION_EVENT_ADDING, true);
						dialog.show();
						Runnable run =  new Runnable() {
							@Override
							public void run() {
								if (Activa.myPatientManager.addQuestEvent(Activa.myPatientManager.currentPat.getId(), quest, eventtoadd))
									handler.sendEmptyMessage(1);
								else 
									handler.sendEmptyMessage(0);
							}
							private Handler handler = new Handler() {
								@Override
								public void handleMessage(Message msg) {
									switch (msg.what) {
										case 0:
											Toast toast = Toast.makeText(Activa.myApp, Activa.myLanguageManager.CONNECTION_FAILED, Toast.LENGTH_SHORT);
											Activa.myUIManager.loadScheduleWeekForPatientMeas(dategiven);
											dialog.cancel();
											toast.show();
											break;
										case 1:
											Activa.myUIManager.loadScheduleWeekForPatientMeas(dategiven);
											dialog.cancel();
											break;
									}
								}

							};
						};
						Thread thread = new Thread(run);
						thread.start();
						alertDialog.cancel();
					}
				});
			}
		});
	}
	
	public void showMeasResults(final Sample sample, final boolean goback) {                                                            
		Activa.myApp.setContentView(R.layout.resultscreen);
		int width = Activa.myApp.getResources().getDisplayMetrics().widthPixels;
		((TextView) Activa.myApp.findViewById(R.id.startText)).setText(Activa.myLanguageManager.CALENDAR_MEASUREMENT_RESULT);
		((TextView) Activa.myApp.findViewById(R.id.startText)).setTextSize(24);
		TextView result = ((TextView) Activa.myApp.findViewById(R.id.result));
		ImageButton back = (ImageButton) Activa.myApp.findViewById(R.id.previous);
		if (sample instanceof PulseoximetrySample) {
			result.setText(((PulseoximetrySample)sample).getPulsioximetrySpanData());
		}
		if (sample instanceof com.o2hlink.pimtools.patient.PulseoximetrySample) {
			result.setText(((com.o2hlink.pimtools.patient.PulseoximetrySample)sample).getPulsioximetrySpanData());
		}
		if (sample instanceof SixMinutesWalkSample) {
			result.setText(((SixMinutesWalkSample)sample).getExerciseSpanData());
			LinearLayout content = (LinearLayout) Activa.myApp.findViewById(R.id.mainLayout);
			FrameLayout frame = new FrameLayout(Activa.myApp);
			frame.setLayoutParams(new LayoutParams(width*4/5, width*4/5));
			SixMinutesGraphViewWithCustomData graph = new SixMinutesGraphViewWithCustomData(Activa.myApp, ((SixMinutesWalkSample) sample).hrtrack, ((SixMinutesWalkSample) sample).so2track, ((SixMinutesWalkSample) sample).time, width*4/5, width*4/5);
			frame.addView(graph);
			content.addView(frame);
		}
		if (sample instanceof com.o2hlink.pimtools.patient.SixMinutesWalkSample) {
			result.setText(((com.o2hlink.pimtools.patient.SixMinutesWalkSample)sample).getExerciseSpanData());
			LinearLayout content = (LinearLayout) Activa.myApp.findViewById(R.id.mainLayout);
			FrameLayout frame = new FrameLayout(Activa.myApp);
			frame.setLayoutParams(new LayoutParams(width*4/5, width*4/5));
			SixMinutesGraphViewWithCustomData graph = new SixMinutesGraphViewWithCustomData(Activa.myApp, ((com.o2hlink.pimtools.patient.SixMinutesWalkSample) sample).hrtrack, ((com.o2hlink.pimtools.patient.SixMinutesWalkSample) sample).so2track, ((com.o2hlink.pimtools.patient.SixMinutesWalkSample) sample).time, width*4/5, width*4/5);
			frame.addView(graph);
			content.addView(frame);
		}
		if (sample instanceof SpirometrySample) {
			result.setText(((SpirometrySample)sample).getSpirometrySpanData());
			LinearLayout content = (LinearLayout) Activa.myApp.findViewById(R.id.mainLayout);
			FrameLayout frame = new FrameLayout(Activa.myApp);
			frame.setLayoutParams(new LayoutParams(width*4/5, width*4/5));
			SpirometryGraphViewWithCustomData graph = new SpirometryGraphViewWithCustomData(Activa.myApp, (Spirometry)sample, ((SpirometrySample) sample).flow, ((SpirometrySample) sample).time, width*4/5, width*4/5);
			frame.addView(graph);
			content.addView(frame);
		}
		if (sample instanceof com.o2hlink.pimtools.patient.SpirometrySample) {
			result.setText(((com.o2hlink.pimtools.patient.SpirometrySample)sample).getSpirometrySpanData());
			LinearLayout content = (LinearLayout) Activa.myApp.findViewById(R.id.mainLayout);
			FrameLayout frame = new FrameLayout(Activa.myApp);
			frame.setLayoutParams(new LayoutParams(width*4/5, width*4/5));
			SpirometryGraphViewWithCustomData graph = new SpirometryGraphViewWithCustomData(Activa.myApp, (Spirometry)sample, ((com.o2hlink.pimtools.patient.SpirometrySample) sample).flow, ((com.o2hlink.pimtools.patient.SpirometrySample) sample).time, width*4/5, width*4/5);
			frame.addView(graph);
			content.addView(frame);
		}
		if (sample instanceof WeightHeightSample) {
			result.setText(((WeightHeightSample)sample).getWeightHeightSpanData());
		}
		if (sample instanceof com.o2hlink.pimtools.patient.PulseoximetrySample) {
			result.setText(((com.o2hlink.pimtools.patient.WeightHeightSample)sample).getWeightHeightSpanData());
		}
		if (sample instanceof ExerciseSample) {
			result.setText(((ExerciseSample)sample).getExerciseSpanData());
		}
		if (sample instanceof com.o2hlink.pimtools.patient.ExerciseSample) {
			result.setText(((com.o2hlink.pimtools.patient.ExerciseSample)sample).getExerciseSpanData());
		}
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (goback) Activa.myUIManager.loadScheduleDay(sample.getDate());
				else Activa.myUIManager.loadScheduleDayForPatientMeas(sample.getDate());
			}
		});
	}
	
	public void showQuestResults(final QuestionnaireSample sample, final boolean goback, String name) {                                                                           
		Activa.myApp.setContentView(R.layout.resultscreen);
		((TextView) Activa.myApp.findViewById(R.id.startText)).setText(Activa.myLanguageManager.CALENDAR_MEASUREMENT_RESULT);
		((TextView) Activa.myApp.findViewById(R.id.startText)).setTextSize(24);
		TextView result = ((TextView) Activa.myApp.findViewById(R.id.result));
		result.setGravity(Gravity.LEFT);
		ImageButton back = (ImageButton) Activa.myApp.findViewById(R.id.previous);
		if (sample instanceof com.o2hlink.pimtools.patient.QuestionnaireSample) {
			result.setText(((com.o2hlink.pimtools.patient.QuestionnaireSample)sample).getQuestionnaireSpanData(name));
		}
		if (sample instanceof com.o2hlink.pimtools.data.calendar.QuestionnaireSample) {
			result.setText(((com.o2hlink.pimtools.data.calendar.QuestionnaireSample)sample).getQuestionnaireSpanData(name));
		}
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (goback) Activa.myUIManager.loadScheduleDay(sample.getDate());
				else Activa.myUIManager.loadScheduleDayForPatientQuest(sample.getDate());
			}
		});
	}
	
	public void showEventInfo(final Event event) {                                                                           
		Activa.myApp.setContentView(R.layout.resultscreen);
		((TextView) Activa.myApp.findViewById(R.id.startText)).setText(Activa.myLanguageManager.CALENDAR_MEASUREMENT_RESULT);
		((TextView) Activa.myApp.findViewById(R.id.startText)).setTextSize(24);
		TextView result = ((TextView) Activa.myApp.findViewById(R.id.result));
		result.setGravity(Gravity.LEFT);
		ImageButton back = (ImageButton) Activa.myApp.findViewById(R.id.previous);
		result.setText(Html.fromHtml("<b>" + Activa.myLanguageManager.NOTIFICATION_EVENT_NAMEREQ + "</b>" + event.name + "<br/>" + 
				"<b>" + Activa.myLanguageManager.NOTIFICATION_EVENT_DESC + "</b>" + event.description + "<br/>" + 
				"<b>" + Activa.myLanguageManager.NOTIFICATION_EVENT_START + "</b>" + ActivaUtil.dateToReadableString(event.date) + " " + ActivaUtil.timeToReadableString(event.date) + "<br/>" +
				"<b>" + Activa.myLanguageManager.NOTIFICATION_EVENT_END + "</b>" + ActivaUtil.dateToReadableString(event.dateEnd) + " " + ActivaUtil.timeToReadableString(event.dateEnd) + "<br/>"
		));
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Activa.myUIManager.loadScheduleDay(event.date);
			}
		});
	}

	
	//TODO
	public void loadDocumentScreen() {
		state = UI_STATE_DOCUMENTS;
		Activa.myApp.setContentView(R.layout.list);
		int realwidth = Activa.myApp.getResources().getDisplayMetrics().widthPixels;
		((TextView) Activa.myApp.findViewById(R.id.startText)).setText(Activa.myLanguageManager.DOCUMENTS_TITLE);
		TableLayout doclisting = (TableLayout)Activa.myApp.findViewById(R.id.listing);
		Enumeration<Document> enumer = Activa.myDocumentsManager.myDocuments.elements();
		while (enumer.hasMoreElements()) {			
			final Document doc = enumer.nextElement();
			TableRow buttonLayout = new TableRow(Activa.myApp);
			buttonLayout.setOrientation(TableRow.HORIZONTAL);
			buttonLayout.setGravity(Gravity.CENTER_VERTICAL);
			buttonLayout.setLayoutParams(new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));			
			ImageButton button = new ImageButton(Activa.myApp);
			button.setBackgroundResource(R.drawable.iconbg);
			button.setImageResource(R.drawable.document);
			OnClickListener listener = new OnClickListener() {
				@Override
				public void onClick(View v) {
					Activa.myUIManager.loadDocument(doc);
				}
			};
			TextView text = new TextView(Activa.myApp);
			text.append(doc.getName());
			text.setWidth(realwidth*3/5);
			text.setTextSize((float) 20);
			text.setTextColor(Color.BLACK);
			text.setTypeface(Typeface.SANS_SERIF);			
			ImageButton share = new ImageButton(Activa.myApp);
			share.setBackgroundResource(R.drawable.iconbg);
			share.setImageResource(R.drawable.contacts);
			share.setScaleType(ScaleType.FIT_XY);
			share.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (doc.getAccount() == null) shareDocument(doc);
					else shareDocumentTroughEmail(doc);
				}
			});
			buttonLayout.addView(button);
			buttonLayout.addView(text);
			buttonLayout.addView(share, 50, 50);
			button.setOnClickListener(listener);
			text.setOnClickListener(listener);
			buttonLayout.setOnClickListener(listener);
			doclisting.addView(buttonLayout);
		}
		ImageButton back = (ImageButton) Activa.myApp.findViewById(R.id.previous);
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				loadGeneratedSubenvironment(Activa.myUIManager.lastSubenv, true);
			}
		});
		ImageButton upload = (ImageButton) Activa.myApp.findViewById(R.id.help);
		upload.setImageResource(R.drawable.upload);
		upload.setVisibility(View.VISIBLE);
		upload.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (Activa.myDocumentsManager.activeDownloads > 3) loadInfoPopup(Activa.myLanguageManager.DOCUMENTS_MAXDOWNLOADS);
				else uploadDocument(Environment.getExternalStorageDirectory());
			}
		});
		ImageView sync = (ImageView) Activa.myApp.findViewById(R.id.animation);
		sync.setImageResource(R.drawable.sync);
		sync.setVisibility(View.VISIBLE);
		sync.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				File cameraFolder = new File(Environment.getExternalStorageDirectory(), "DCIM");
				final File[] imageArray;
				final String[] items;
				final ArrayList<File> images = new ArrayList<File>();
				final ArrayList<File> imagesToSync = new ArrayList<File>();
				for (File folder : cameraFolder.listFiles(new FolderFileFilter())) {
					File[] ims = folder.listFiles(new ImageFileFilter());
					for (int i = 0; i < ims.length; i++) {
						images.add(ims[i]);
					}
				}
				items = new String[images.size()];
				imageArray = new File[images.size()];
				int i = 0;
				for (File file : images) {
					items[i] = file.getName();
					imageArray[i] = file;
					i++;
				}
				AlertDialog.Builder builder = new AlertDialog.Builder(Activa.myApp);
				builder.setTitle(Activa.myLanguageManager.DOCUMENTS_SYNC);
				builder.setMultiChoiceItems(items, null, new OnMultiChoiceClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which, boolean isChecked) {
						if (isChecked) imagesToSync.add(imageArray[which]);
						else imagesToSync.remove(imageArray[which]);
					}
				});
				builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			        @SuppressWarnings("unchecked")
					public void onClick(DialogInterface dialog, int id) {
			        	new FileSyncImagesTask().execute(imagesToSync);
			        }
			    });
				AlertDialog alert = builder.create();
				alert.show();
			}
		});
	}
	
	protected void shareDocumentTroughEmail(final Document doc) {
		LayoutInflater inflater = (LayoutInflater) Activa.myApp.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		final View layout = inflater.inflate(R.layout.toastonefield,
		                               (ViewGroup) Activa.myApp.findViewById(R.id.toastonefieldroot));
		TextView title = (TextView) layout.findViewById(R.id.addtitle);
		title.setText(Activa.myLanguageManager.DOCUMENTS_SHARE);
		TextView expl = (TextView) layout.findViewById(R.id.addexplanation);
		expl.setVisibility(View.VISIBLE);
		expl.setText(Activa.myLanguageManager.DOCUMENTS_SHARE_EXPL);
		TextView namereq = (TextView) layout.findViewById(R.id.fieldonerequested);
		namereq.setText(Activa.myLanguageManager.PATIENTS_PERSONALDATA_EMAIL);
		final EditText emails = (EditText) layout.findViewById(R.id.fieldone);
		ImageButton image = (ImageButton) layout.findViewById(R.id.addeventok);
		Builder builder = new AlertDialog.Builder(Activa.myApp);
		builder.setView(layout);
		final AlertDialog alertDialog = builder.create();
		alertDialog.show();
		image.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ArrayList<Contact> usersToAdd = new ArrayList<Contact>();
				String[] users = emails.getText().toString().split(",|, ");
				for (String string : users) {
					Contact contact = new Contact(string, "", "");
					usersToAdd.add(contact);
				}
				Activa.myDocumentsManager.shareDocument(doc.getDocumentForExporting(), doc.getAccount(), usersToAdd);
				alertDialog.cancel();
			}
		});
	}

	public void shareDocument(final Document doc) {
		final Contact[] users = new Contact[Activa.myMessageManager.contactList.size()];
		final CharSequence[] items = new CharSequence[Activa.myMessageManager.contactList.size()];
		final ArrayList<Contact>usersToAdd = new ArrayList<Contact>();
		int i = 0;
		Enumeration<Contact> enumerator = Activa.myMessageManager.contactList.elements();
		while (enumerator.hasMoreElements()) {
			Contact user = enumerator.nextElement();
			items[i] = user.getFirstName() + " " + user.getLastName();
			users[i] = user;
			i++;
		}
		AlertDialog.Builder builder = new AlertDialog.Builder(Activa.myApp);
		builder.setTitle(Activa.myLanguageManager.DOCUMENTS_SHARE);
		builder.setMultiChoiceItems(items, null, new OnMultiChoiceClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which, boolean isChecked) {
				if (isChecked) usersToAdd.add(users[which]);
				else usersToAdd.remove(users[which]);
			}
		});
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
	           public void onClick(DialogInterface dialog, int id) {
	        	   Activa.myDocumentsManager.shareDocument(doc.getDocumentForExporting(), doc.getAccount(), usersToAdd);
	        	   dialog.cancel();
	           }
	    });
		builder.setNegativeButton("SEARCH", new DialogInterface.OnClickListener() {
	           public void onClick(DialogInterface dialog, int id) {
	        	   searchContacts();
	           }
	    });
		AlertDialog alert = builder.create();
		alert.show();
	}
	
	public void uploadDocument(File position) {
		if (position == null) position = Environment.getExternalStorageDirectory();
		if (position.getAbsolutePath().length() < Environment.getExternalStorageDirectory().getAbsolutePath().length()) position = Environment.getExternalStorageDirectory();
		final File[] files = new File[position.listFiles().length + 1];
		int i = 0;
		files[i] = position;
		i++;
		for (File file : position.listFiles()) {
			files[i] = file;
			i++;
		}
		ListAdapter adapter = new ArrayAdapter<File>(Activa.myApp,android.R.layout.select_dialog_item,
			    android.R.id.text1,files){
			public View getView(int position, View convertView, ViewGroup parent) {
				//User super class to create the View
				LinearLayout layout = new LinearLayout(Activa.myApp);
				layout.setLayoutParams(new ListView.LayoutParams(ListView.LayoutParams.FILL_PARENT, ListView.LayoutParams.WRAP_CONTENT));
			    layout.setOrientation(LinearLayout.HORIZONTAL);
				TextView tv = new TextView(Activa.myApp);
			    //Add margin between image and text (support various screen densities)
			    int dp5 = (int) (5 * Activa.myApp.getResources().getDisplayMetrics().density + 0.5f);
			    tv.setCompoundDrawablePadding(dp5);
			    tv.setTextColor(Color.BLACK);
			    tv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
			    if (position == 0) tv.setText("/..");
			    else tv.setText(files[position].getName());
			    ImageView image = new ImageView(Activa.myApp);
			    image.setScaleType(ScaleType.FIT_XY);
			    if (position == 0) image.setImageResource(R.drawable.folder);
			    else if (files[position].isDirectory()) image.setImageResource(R.drawable.folder);
			    else image.setImageResource(R.drawable.document);
				image.setMaxWidth(45);
			    image.setMaxHeight(45);
			    image.setLayoutParams(new TableLayout.LayoutParams(45, 45));
			    Drawable draw = image.getDrawable();
			    draw.setBounds(0, 0, 45, 45);
			    tv.setCompoundDrawables(draw, null, null, null);
			    layout.addView(tv);
			    return layout;
			}
		};
		new AlertDialog.Builder(Activa.myApp)
	    .setTitle(Activa.myLanguageManager.DOCUMENTS_UPLOAD)
	    .setAdapter(adapter, new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int item) {
			    if (item == 0) uploadDocument(files[item].getParentFile());
			    else if (files[item].isDirectory()) uploadDocument(files[item]);
			    else uploadFile(files[item]);
	        }
	    })
	    .setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		})
	    .show();
	}
	
	public void uploadFile(final File file) {
        if (file.getName().length() > 40) {
			Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.DOCUMENTS_MAXLENGTH);	
            return;
        }
        if (file.length() > 2*1024*1024) {
			Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.DOCUMENTS_MAXSIZE);	
            return;
        }
		LayoutInflater inflater = (LayoutInflater) Activa.myApp.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		final View layout = inflater.inflate(R.layout.toastcreatequestion,
		                               (ViewGroup) Activa.myApp.findViewById(R.id.toastcreatequestionroot));
		TextView title = (TextView) layout.findViewById(R.id.addtitle);
		title.setText(Activa.myLanguageManager.DOCUMENTS_SELACCOUNT);
		TextView namereq = (TextView) layout.findViewById(R.id.typerequest);
		namereq.setText("Account : ");
		final Spinner account = (Spinner) layout.findViewById(R.id.type);
		ArrayList<String> accountstrings = new ArrayList<String>();
		final ArrayList<com.o2hlink.activ8.client.entity.Account> accounts = new ArrayList<com.o2hlink.activ8.client.entity.Account>();
		accountstrings.add("ActivaCentral");
		accounts.add(null);
		Enumeration<com.o2hlink.activ8.client.entity.Account> accs = Activa.myMessageManager.accountList.elements();
		while (accs.hasMoreElements()) {
			com.o2hlink.activ8.client.entity.Account acc = accs.nextElement();
			if (acc.getType().equals(AccountType.GOOGLE)) {
				accountstrings.add("Google - " + acc.getDomain());
				accounts.add(acc);
			}
		}
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(Activa.myApp, android.R.layout.simple_spinner_item, accountstrings);
	    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    account.setAdapter(adapter);
	    account.setSelection(0);
		ImageButton image = (ImageButton) layout.findViewById(R.id.addeventok);
		Builder builder = new AlertDialog.Builder(Activa.myApp);
		builder.setView(layout);
		final AlertDialog alertDialog = builder.create();
		alertDialog.show();
		image.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				alertDialog.cancel();
				com.o2hlink.activ8.client.entity.Account acc = accounts.get(account.getSelectedItemPosition());
				Document document = new Document();
				document.setName(file.getName());
				document.setContentType(DocumentsManager.getMIMEType(file));
				document.setAccount(acc);
				new FileUploadingTask().execute(new FileLoadingStructure(file, document));
			}
		});
	}
	
	//TODO
	public void loadDocument(final Document doc) {
		final TextLoadingTask loadingtext = new TextLoadingTask();
		final ImageLoadingTask loadingimage = new ImageLoadingTask();
		final FileLoadingTask loadingfile = new FileLoadingTask();
		state = UI_STATE_DOCUMENT;
		Activa.myApp.setContentView(R.layout.documentviewer);
		((TextView) Activa.myApp.findViewById(R.id.startText)).setText(Activa.myLanguageManager.DOCUMENTS_TITLE);
		((TextView) Activa.myApp.findViewById(R.id.documentTitle)).setText(doc.getName());
		String type = "";
		try {
			type = doc.getContentType().substring(0, doc.getContentType().indexOf("/")).toLowerCase();
		} catch (Exception e) {
			type = "";
		}
		Object[] idarray = Activa.myDocumentsManager.myDocuments.keySet().toArray();
		int totaldocs = idarray.length;
		int currentdoc = 0;
		String prevdoc = null, nextdoc = null;
		for (int i = 0; i < idarray.length; i++) {
			if (((String)idarray[i]).equalsIgnoreCase(doc.getId())) {
				currentdoc = i + 1;
				if ((i - 1) < 0) prevdoc = (String)idarray[idarray.length - 1];
				else prevdoc = (String)idarray[i - 1];
				if ((i + 1) >= idarray.length) nextdoc = (String)idarray[0];
				else nextdoc = (String)idarray[i + 1];
			}
		}
		final String finalprevdoc = prevdoc;
		final String finalnextdoc = nextdoc;
		ImageButton next = (ImageButton) Activa.myApp.findViewById(R.id.nextnew);
		if (finalnextdoc != null) next.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (loadingtext.getStatus().equals(Status.RUNNING)) loadingtext.cancel(true);
				if (loadingimage.getStatus().equals(Status.RUNNING)) loadingimage.cancel(true);
				if (loadingfile.getStatus().equals(Status.RUNNING)) loadingfile.cancel(true);
				Activa.myUIManager.loadDocument(Activa.myDocumentsManager.myDocuments.get(finalnextdoc));
			}
		});
		ImageButton prev = (ImageButton) Activa.myApp.findViewById(R.id.previousnew);
		if (finalprevdoc != null) prev.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (loadingtext.getStatus().equals(Status.RUNNING)) loadingtext.cancel(true);
				if (loadingimage.getStatus().equals(Status.RUNNING)) loadingimage.cancel(true);
				if (loadingfile.getStatus().equals(Status.RUNNING)) loadingfile.cancel(true);
				Activa.myUIManager.loadDocument(Activa.myDocumentsManager.myDocuments.get(finalprevdoc));
			}
		});
		((TextView) Activa.myApp.findViewById(R.id.newcount)).setText(currentdoc + "/" + totaldocs);
		if (type.equals("text")) {
			TextView text = new TextView(Activa.myApp);
			text.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT));
			text.setText(Activa.myLanguageManager.DOCUMENTS_LOADING_PREVIEW);
			text.setTextColor(Color.BLACK);
			((LinearLayout) Activa.myApp.findViewById(R.id.documentView)).addView(text);
			loadingtext.execute(doc);
		} else if (type.equals("image")) {
			TextView text = new TextView(Activa.myApp);
			text.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT));
			text.setText(Activa.myLanguageManager.DOCUMENTS_LOADING_PREVIEW);
			text.setTextColor(Color.BLACK);
			((LinearLayout) Activa.myApp.findViewById(R.id.documentView)).addView(text);
			loadingimage.execute(doc);
		} else {
			TextView text = new TextView(Activa.myApp);
			text.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT));
			text.setText(Activa.myLanguageManager.DOCUMENTS_PREVIEW_AVAIL);
			text.setTextColor(Color.BLACK);
			Button button = new Button(Activa.myApp);
			button.setText(Activa.myLanguageManager.DOCUMENTS_PREVIEW_AVAIL_BUT);
			button.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					((LinearLayout) Activa.myApp.findViewById(R.id.documentView)).removeAllViews();
					TextView text = new TextView(Activa.myApp);
					text.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT));
					text.setText(Activa.myLanguageManager.DOCUMENTS_LOADING_PREVIEW);
					text.setTextColor(Color.BLACK);
					((LinearLayout) Activa.myApp.findViewById(R.id.documentView)).addView(text);
					loadingfile.execute(doc);
				}
			});
			((LinearLayout) Activa.myApp.findViewById(R.id.documentView)).addView(text);
			((LinearLayout) Activa.myApp.findViewById(R.id.documentView)).addView(button);
		}
		ImageButton back = (ImageButton) Activa.myApp.findViewById(R.id.previous);
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (loadingtext.getStatus().equals(Status.RUNNING)) loadingtext.cancel(true);
				if (loadingimage.getStatus().equals(Status.RUNNING)) loadingimage.cancel(true);
				if (loadingfile.getStatus().equals(Status.RUNNING)) loadingfile.cancel(true);
				loadDocumentScreen();
			}
		});
		ImageButton download = (ImageButton) Activa.myApp.findViewById(R.id.download);
		download.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (Activa.myDocumentsManager.activeDownloads > 3) loadInfoPopup(Activa.myLanguageManager.DOCUMENTS_MAXDOWNLOADS);
				else downloadDocument(Environment.getExternalStorageDirectory(), doc);
			}
		});
		ImageButton remove = (ImageButton) Activa.myApp.findViewById(R.id.remove);
		remove.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Runnable run = new Runnable() {
					Document prevdoc;
					@Override
					public void run() {
						prevdoc = Activa.myDocumentsManager.myDocuments.get(finalnextdoc);
						handler.sendEmptyMessage(0);
						if (Activa.myDocumentsManager.removeDocument(doc.getDocumentForExporting(), doc.getAccount())) {
							if (loadingtext != null) loadingtext.cancel(true);
							if (loadingimage != null) loadingimage.cancel(true);
							if (loadingfile != null) loadingfile.cancel(true);
							handler.sendEmptyMessage(1);
						}
						else handler.sendEmptyMessage(2);
					}
					private Handler handler = new Handler(){
						@Override
						public void handleMessage(Message msg) {
							ImageView animationFrame;
							AnimationDrawable animation;
							switch (msg.what) {
								case 0:
									((RelativeLayout) Activa.myApp.findViewById(R.id.popupView)).setVisibility(View.VISIBLE);
									((TextView) Activa.myApp.findViewById(R.id.popupText)).setText("Removing document ...");
									animationFrame = (ImageView) Activa.myApp.findViewById(R.id.popupImage);
									animationFrame.setVisibility(View.VISIBLE);
									animationFrame.setBackgroundResource(R.drawable.loading);
									animation = (AnimationDrawable) animationFrame.getBackground();
									animation.start();
									break;
								case 1:
									animationFrame = (ImageView) Activa.myApp.findViewById(R.id.popupImage);
									animation = (AnimationDrawable) animationFrame.getBackground();
									animation.stop();
									Activa.myUIManager.loadDocument(prevdoc);
									break;
								case 2:
									animationFrame = (ImageView) Activa.myApp.findViewById(R.id.popupImage);
									animation = (AnimationDrawable) animationFrame.getBackground();
									animation.stop();
									Activa.myUIManager.loadInfoPopup("Removing failed");
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
	
	public void downloadDocument(File position, final Document doc) {
		if (position == null) position = Environment.getExternalStorageDirectory();
		if (position.getAbsolutePath().length() < Environment.getExternalStorageDirectory().getAbsolutePath().length()) position = Environment.getExternalStorageDirectory();
		final File finposition = position;
		final File[] files = new File[position.listFiles().length + 1];
		int i = 0;
		files[i] = position;
		i++;
		for (File file : position.listFiles()) {
			files[i] = file;
			i++;
		}
		ListAdapter adapter = new ArrayAdapter<File>(Activa.myApp,android.R.layout.select_dialog_item,
			    android.R.id.text1,files){
			public View getView(int position, View convertView, ViewGroup parent) {
				//User super class to create the View
				LinearLayout layout = new LinearLayout(Activa.myApp);
				layout.setLayoutParams(new ListView.LayoutParams(ListView.LayoutParams.FILL_PARENT, ListView.LayoutParams.WRAP_CONTENT));
			    layout.setOrientation(LinearLayout.HORIZONTAL);
				TextView tv = new TextView(Activa.myApp);
			    //Add margin between image and text (support various screen densities)
			    int dp5 = (int) (5 * Activa.myApp.getResources().getDisplayMetrics().density + 0.5f);
			    tv.setCompoundDrawablePadding(dp5);
			    tv.setTextColor(Color.BLACK);
			    tv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
			    if (position == 0) tv.setText("/..");
			    else tv.setText(files[position].getName());
			    ImageView image = new ImageView(Activa.myApp);
			    image.setScaleType(ScaleType.FIT_XY);
			    if (position == 0) image.setImageResource(R.drawable.folder);
			    else if (files[position].isDirectory()) image.setImageResource(R.drawable.folder);
			    else image.setImageResource(R.drawable.document);
				image.setMaxWidth(45);
			    image.setMaxHeight(45);
			    image.setLayoutParams(new TableLayout.LayoutParams(45, 45));
			    Drawable draw = image.getDrawable();
			    draw.setBounds(0, 0, 45, 45);
			    tv.setCompoundDrawables(draw, null, null, null);
			    layout.addView(tv);
			    return layout;
			}
		};
		new AlertDialog.Builder(Activa.myApp)
	    .setTitle(Activa.myLanguageManager.DOCUMENTS_DOWNLOAD)
	    .setAdapter(adapter, new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int item) {
			    if (item == 0) downloadDocument(files[item].getParentFile(), doc);
			    else if (files[item].isDirectory()) downloadDocument(files[item], doc);
	        }
	    })
	    .setPositiveButton(Activa.myLanguageManager.DOCUMENTS_PREVIEW_LOADED_BUT, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				new FileDownloadingTask().execute(new FileLoadingStructure(finposition, doc));
			}
		})
	    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		})
	    .show();
	}
	
	public void downloadFile(final File file, final Document doc) {
		Runnable run = new Runnable() {
			@Override
			public void run() {
				try {
		            handler.sendEmptyMessage(0);
					if (Activa.myDocumentsManager.downloadDocument(file, doc, doc.getAccount())) handler.sendEmptyMessage(1);
			        else handler.sendEmptyMessage(2);
			   } catch (Exception e) {
					e.printStackTrace();
			   }
			}
			private Handler handler = new Handler() {
				@Override
				public void handleMessage(Message msg) {
					Intent contentIntent;
					PendingIntent pendingIntent;
					CharSequence message;
					Notification notification;
					switch (msg.what) {
						case 0:
							contentIntent = new Intent(Activa.myApp, Activa.class);
							pendingIntent = PendingIntent.getActivity(Activa.myApp, 0,contentIntent, 0); 
							message = doc.getName() + Activa.myLanguageManager.DOCUMENTS_DOWNLOADING;
							notification = new Notification(R.drawable.icon, message, System.currentTimeMillis());
							notification.defaults = Notification.DEFAULT_ALL;
							notification.setLatestEventInfo(Activa.myApp, Activa.myLanguageManager.NOTIFICATION_TITLE, message, pendingIntent);
							Activa.myNotificationManager.notify(0, notification);		
							break;
						case 1:
							contentIntent = new Intent(Activa.myApp, Activa.class);
							pendingIntent = PendingIntent.getActivity(Activa.myApp, 0,contentIntent, 0); 
							message = doc.getName() + Activa.myLanguageManager.DOCUMENTS_DOWNLOADED;
							notification = new Notification(R.drawable.icon, message, System.currentTimeMillis());
							notification.flags = Notification.FLAG_AUTO_CANCEL;
							notification.defaults = Notification.DEFAULT_ALL;
							notification.setLatestEventInfo(Activa.myApp, Activa.myLanguageManager.NOTIFICATION_TITLE, message, pendingIntent);
							Activa.myNotificationManager.notify(0, notification);	
							if (Activa.myUIManager.state == UIManager.UI_STATE_DOCUMENTS) Activa.myUIManager.loadDocumentScreen();
							break;
						case 2:
							contentIntent = new Intent(Activa.myApp, Activa.class);
							pendingIntent = PendingIntent.getActivity(Activa.myApp, 0,contentIntent, 0); 
							message = doc.getName() + Activa.myLanguageManager.DOCUMENTS_DOWNLOAD_FAIL;
							notification = new Notification(R.drawable.icon, message, System.currentTimeMillis());
							notification.flags = Notification.FLAG_AUTO_CANCEL;
							notification.defaults = Notification.DEFAULT_ALL;
							notification.setLatestEventInfo(Activa.myApp, Activa.myLanguageManager.NOTIFICATION_TITLE, message, pendingIntent);
							Activa.myNotificationManager.notify(0, notification);	
							break;
					}
				}
			};
		};
		Thread thread = new Thread(run);
		thread.start();
	}
	
	public void saveDocument(File position, final File file, final String filename) {
		if (position == null) position = Environment.getExternalStorageDirectory();
		if (position.getAbsolutePath().length() < Environment.getExternalStorageDirectory().getAbsolutePath().length()) position = Environment.getExternalStorageDirectory();
		final File finposition = position;
		final File[] files = new File[position.listFiles().length + 1];
		int i = 0;
		files[i] = position;
		i++;
		for (File fil : position.listFiles()) {
			files[i] = fil;
			i++;
		}
		ListAdapter adapter = new ArrayAdapter<File>(Activa.myApp,android.R.layout.select_dialog_item,
			    android.R.id.text1,files){
			public View getView(int position, View convertView, ViewGroup parent) {
				//User super class to create the View
				LinearLayout layout = new LinearLayout(Activa.myApp);
				layout.setLayoutParams(new ListView.LayoutParams(ListView.LayoutParams.FILL_PARENT, ListView.LayoutParams.WRAP_CONTENT));
			    layout.setOrientation(LinearLayout.HORIZONTAL);
				TextView tv = new TextView(Activa.myApp);
			    //Add margin between image and text (support various screen densities)
			    int dp5 = (int) (5 * Activa.myApp.getResources().getDisplayMetrics().density + 0.5f);
			    tv.setCompoundDrawablePadding(dp5);
			    tv.setTextColor(Color.BLACK);
			    tv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
			    if (position == 0) tv.setText("/..");
			    else tv.setText(files[position].getName());
			    ImageView image = new ImageView(Activa.myApp);
			    image.setScaleType(ScaleType.FIT_XY);
			    if (position == 0) image.setImageResource(R.drawable.folder);
			    else if (files[position].isDirectory()) image.setImageResource(R.drawable.folder);
			    else image.setImageResource(R.drawable.document);
				image.setMaxWidth(45);
			    image.setMaxHeight(45);
			    image.setLayoutParams(new TableLayout.LayoutParams(45, 45));
			    Drawable draw = image.getDrawable();
			    draw.setBounds(0, 0, 45, 45);
			    tv.setCompoundDrawables(draw, null, null, null);
			    layout.addView(tv);
			    return layout;
			}
		};
		new AlertDialog.Builder(Activa.myApp)
	    .setTitle(Activa.myLanguageManager.DOCUMENTS_DOWNLOAD)
	    .setAdapter(adapter, new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int item) {
			    if (item == 0) saveDocument(files[item].getParentFile(), file, filename);
			    else if (files[item].isDirectory()) saveDocument(files[item], file, filename);
	        }
	    })
	    .setPositiveButton(Activa.myLanguageManager.DOCUMENTS_PREVIEW_LOADED_BUT, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				try {
					File newfile = new File(finposition, filename);
					if (!newfile.exists()) newfile.createNewFile();
					FileInputStream fin = new FileInputStream(file);
					FileOutputStream fout = new FileOutputStream(newfile);
				    byte[] buffer = new byte[1024];
				    int len1 = 0;
				    while ( (len1 = fin.read(buffer)) > 0 ) {
				    	fout.write(buffer,0, len1);
				    }
				    fin.close();
				    fout.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		})
	    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		})
	    .show();
	}
	
	class TextLoadingTask extends AsyncTask<Document, Void, String> {

    	@Override
    	public String doInBackground(Document... docs) {
    		Document doc = docs[0];
    		String text = Activa.myDocumentsManager.getDocumentText(doc.getId(), doc.getAccount());
    		return text;
    	}

    	@Override
    	public void onPostExecute(String text) {
    		if (text != null) {
    			TextView textview = new TextView(Activa.myApp);
    			textview.setTextColor(Color.BLACK);
    			textview.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT));
    			textview.setText(text);
    			LinearLayout layout = ((LinearLayout) Activa.myApp.findViewById(R.id.documentView));
    			if (layout == null) return;
    			layout.removeAllViews();
    			layout.addView(textview);
    			layout.invalidate();
    		} else {
    			TextView textview = new TextView(Activa.myApp);
    			textview.setTextColor(Color.BLACK);
    			textview.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT));
    			textview.setText(Activa.myLanguageManager.DOCUMENTS_PREVIEW_FAILED);
    			LinearLayout layout = ((LinearLayout) Activa.myApp.findViewById(R.id.documentView));
    			if (layout == null) return;
    			layout.removeAllViews();
    			layout.addView(textview);
    			layout.invalidate();
    			
    		}
    	}
    	
    }
	
	class ImageLoadingTask extends AsyncTask<Document, Void, Drawable> {

    	@Override
    	public Drawable doInBackground(Document... files) {
    		System.gc();
    		String fileURL = Activa.myDocumentsManager.getDocumentURL(files[0].getId(), files[0].getAccount());
    		Drawable image = ImageOperations(Activa.myApp, fileURL);   
    		if (image == null) {
    			String extension = files[0].getName().substring(files[0].getName().lastIndexOf(".")+1, files[0].getName().length()).toLowerCase();
    			if (!Activa.myDocumentsManager.downloadDocumentForPreview(files[0], files[0].getAccount(), extension)) return null;
    			File originalfolder = new File(Environment.getExternalStorageDirectory(), ActivaConfig.ENVIRONMENT_FOLDER);
    			File file = new File(originalfolder, "test." + extension);
    			try {
    	    		System.gc();
    				image = Drawable.createFromStream(new FileInputStream(file), "src");
    			} catch (FileNotFoundException e) {
    				e.printStackTrace();
    			} catch (OutOfMemoryError e) {
    				e.printStackTrace();
    			}
    		}
    		return image;
    	}

    	@Override
    	public void onPostExecute(Drawable image) {
    		if (image != null) {
        		ImageView view = new ImageView(Activa.myApp);
        		view.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT));
    			view.setImageDrawable(image);
    			view.setScaleType(ScaleType.FIT_START);
    			LinearLayout layout = ((LinearLayout) Activa.myApp.findViewById(R.id.documentView));
    			if (layout == null) return;
    			layout.removeAllViews();
    			layout.addView(view);
    			layout.invalidate();
    		} else {
    			TextView textview = new TextView(Activa.myApp);
    			textview.setTextColor(Color.BLACK);
    			textview.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT));
    			textview.setText(Activa.myLanguageManager.DOCUMENTS_PREVIEW_FAILED);
    			LinearLayout layout = ((LinearLayout) Activa.myApp.findViewById(R.id.documentView));
    			if (layout == null) return;
    			layout.removeAllViews();
    			layout.addView(textview);
    			layout.invalidate();
    			
    		}
    	}
    	
  	  	// Additional method to load images from web
 	   	private Drawable ImageOperations(Context ctx,  String fileURL) {
 		    try {
 		    	URL imageUrl = new URL(fileURL);
 			    InputStream is = (InputStream) imageUrl.getContent();
 			    Drawable d = Drawable.createFromStream(is, "src");
 			    return d;
 		    } catch (MalformedURLException e) {
 			    e.printStackTrace();
 			    return null;
 		    } catch (IOException e) {
 			    e.printStackTrace();
 			    return null;
 		    } catch (OutOfMemoryError e) {
				e.printStackTrace();
 			    return null;
			}
 	   	}
    }
	
	class FileLoadingTask extends AsyncTask<Document, Void, File> {
		
		String filename;

    	@Override
    	public File doInBackground(Document... files) {
    		filename = files[0].getName();
    		String extension = files[0].getName().substring(files[0].getName().lastIndexOf(".")+1, files[0].getName().length()).toLowerCase();
    		Activa.myDocumentsManager.downloadDocumentForPreview(files[0], files[0].getAccount(), extension);
    		File originalfolder = new File(Environment.getExternalStorageDirectory(), ActivaConfig.ENVIRONMENT_FOLDER);
    		File file = new File(originalfolder, "test." + extension);
    		return file;
    	}

    	@Override
    	public void onPostExecute(final File file) {
			TextView text = new TextView(Activa.myApp);
			text.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT));
			text.setText(Activa.myLanguageManager.DOCUMENTS_PREVIEW_LOADED);
			text.setTextColor(Color.BLACK);
			Button button = new Button(Activa.myApp);
			button.setText(Activa.myLanguageManager.DOCUMENTS_PREVIEW_LOADED_BUT);
			button.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					saveDocument(Environment.getExternalStorageDirectory(), file, filename);
				}
			});
    		LinearLayout layout = ((LinearLayout) Activa.myApp.findViewById(R.id.documentView));
			if (layout == null) return;
			layout.removeAllViews();
			layout.addView(text);
			layout.addView(button);
			layout.invalidate();
			Intent intent = new Intent();
			intent.setAction(android.content.Intent.ACTION_VIEW);
			intent.setDataAndType(Uri.fromFile(file), DocumentsManager.getMIMEType(file));
			try {
				Activa.myApp.startActivity(intent);
			} catch (ActivityNotFoundException e) {
    			TextView textview = new TextView(Activa.myApp);
    			textview.setTextColor(Color.BLACK);
    			textview.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT));
    			textview.setText(Activa.myLanguageManager.DOCUMENTS_PREVIEW_FAILED);
    			layout = ((LinearLayout) Activa.myApp.findViewById(R.id.documentView));
    			if (layout == null) return;
    			layout.removeAllViews();
    			layout.addView(textview);
    			layout.invalidate();
			}
    	}
    	
    }
	
	class FileDownloadingTask extends AsyncTask<FileLoadingStructure, Void, Boolean> {
		
		Document document;

    	@Override
    	public Boolean doInBackground(FileLoadingStructure... files) {
			Activa.myDocumentsManager.activeDownloads++;
    		document = files[0].document;
			Intent contentIntent = new Intent(Activa.myApp, Activa.class);
			PendingIntent pendingIntent = PendingIntent.getActivity(Activa.myApp, 0,contentIntent, 0); 
			String message = files[0].document.getName() + Activa.myLanguageManager.DOCUMENTS_DOWNLOADING;
			Notification notification = new Notification(R.drawable.icon, message, System.currentTimeMillis());
			notification.defaults = Notification.DEFAULT_ALL;
			notification.setLatestEventInfo(Activa.myApp, Activa.myLanguageManager.NOTIFICATION_TITLE, message, pendingIntent);
			Activa.myNotificationManager.notify(0, notification);	
    		return Activa.myDocumentsManager.downloadDocument(files[0].position, files[0].document, files[0].document.getAccount());
    	}

    	@Override
    	public void onPostExecute(final Boolean success) {
    		if (success) {
    			Intent contentIntent = new Intent(Activa.myApp, Activa.class);
    			PendingIntent pendingIntent = PendingIntent.getActivity(Activa.myApp, 0,contentIntent, 0); 
    			String message = document.getName() + Activa.myLanguageManager.DOCUMENTS_DOWNLOADED;
    			Notification notification = new Notification(R.drawable.icon, message, System.currentTimeMillis());
    			notification.defaults = Notification.DEFAULT_ALL;
    			notification.setLatestEventInfo(Activa.myApp, Activa.myLanguageManager.NOTIFICATION_TITLE, message, pendingIntent);
    			Activa.myNotificationManager.notify(0, notification);	
    		} else {
    			Intent contentIntent = new Intent(Activa.myApp, Activa.class);
    			PendingIntent pendingIntent = PendingIntent.getActivity(Activa.myApp, 0,contentIntent, 0); 
    			String message = document.getName() + Activa.myLanguageManager.DOCUMENTS_DOWNLOAD_FAIL;
    			Notification notification = new Notification(R.drawable.icon, message, System.currentTimeMillis());
    			notification.defaults = Notification.DEFAULT_ALL;
    			notification.setLatestEventInfo(Activa.myApp, Activa.myLanguageManager.NOTIFICATION_TITLE, message, pendingIntent);
    			Activa.myNotificationManager.notify(0, notification);	
    		}
			Activa.myDocumentsManager.activeDownloads--;
    	}
    	
    }
	
	class FileUploadingTask extends AsyncTask<FileLoadingStructure, Void, Boolean> {
		
		Document document;

    	@Override
    	public Boolean doInBackground(FileLoadingStructure... files) {
			Activa.myDocumentsManager.activeDownloads++;
    		document = files[0].document;
			Intent contentIntent = new Intent(Activa.myApp, Activa.class);
			PendingIntent pendingIntent = PendingIntent.getActivity(Activa.myApp, 0,contentIntent, 0); 
			String message = files[0].document.getName() + Activa.myLanguageManager.DOCUMENTS_UPLOADING;
			Notification notification = new Notification(R.drawable.icon, message, System.currentTimeMillis());
			notification.defaults = Notification.DEFAULT_ALL;
			notification.setLatestEventInfo(Activa.myApp, Activa.myLanguageManager.NOTIFICATION_TITLE, message, pendingIntent);
			Activa.myNotificationManager.notify(0, notification);	
    		return Activa.myDocumentsManager.uploadDocument(document.getDocumentForExporting(), document.getAccount(), files[0].position);
    	}

    	@Override
    	public void onPostExecute(final Boolean success) {
    		if (success) {
    			Intent contentIntent = new Intent(Activa.myApp, Activa.class);
    			PendingIntent pendingIntent = PendingIntent.getActivity(Activa.myApp, 0,contentIntent, 0); 
    			String message = document.getName() + Activa.myLanguageManager.DOCUMENTS_UPLOADED;
    			Notification notification = new Notification(R.drawable.icon, message, System.currentTimeMillis());
    			notification.defaults = Notification.DEFAULT_ALL;
    			notification.setLatestEventInfo(Activa.myApp, Activa.myLanguageManager.NOTIFICATION_TITLE, message, pendingIntent);
    			Activa.myNotificationManager.notify(0, notification);	
    		} else {
    			Intent contentIntent = new Intent(Activa.myApp, Activa.class);
    			PendingIntent pendingIntent = PendingIntent.getActivity(Activa.myApp, 0,contentIntent, 0); 
    			String message = document.getName() + Activa.myLanguageManager.DOCUMENTS_UPLOAD_FAIL;
    			Notification notification = new Notification(R.drawable.icon, message, System.currentTimeMillis());
    			notification.defaults = Notification.DEFAULT_ALL;
    			notification.setLatestEventInfo(Activa.myApp, Activa.myLanguageManager.NOTIFICATION_TITLE, message, pendingIntent);
    			Activa.myNotificationManager.notify(0, notification);	
    		}
			Activa.myDocumentsManager.activeDownloads--;
    	}
    	
    }
	
	class FileSyncImagesTask extends AsyncTask<ArrayList<File>, Void, Void> {
		
		boolean success;
		
    	@Override
    	public Void doInBackground(ArrayList<File>... files) {
    		ArrayList<File> filelist = files[0];
			Intent contentIntent = new Intent(Activa.myApp, Activa.class);
			PendingIntent pendingIntent = PendingIntent.getActivity(Activa.myApp, 0,contentIntent, 0); 
			String message;
			success = true;
    		for (File file : filelist) {
    			Activa.myDocumentsManager.activeDownloads++;
    			message = file.getName() + Activa.myLanguageManager.DOCUMENTS_DOWNLOADING;
    			Notification notification = new Notification(R.drawable.icon, message, System.currentTimeMillis());
    			notification.defaults = Notification.DEFAULT_ALL;
    			notification.setLatestEventInfo(Activa.myApp, Activa.myLanguageManager.NOTIFICATION_TITLE, message, pendingIntent);
    			Activa.myNotificationManager.notify(0, notification);	
				Document doc = new Document();
				doc.setName(file.getName());
				doc.setContentType(DocumentsManager.getMIMEType(file));
				if (!Activa.myDocumentsManager.uploadDocument(doc, null, file)) success = false;
				Activa.myDocumentsManager.activeDownloads--;
			}
    		return null;
    	}

    	@Override
    	public void onPostExecute(Void unused) {
    		if (success) {
    			Intent contentIntent = new Intent(Activa.myApp, Activa.class);
    			PendingIntent pendingIntent = PendingIntent.getActivity(Activa.myApp, 0,contentIntent, 0); 
    			String message = Activa.myLanguageManager.DOCUMENTS_FILES + Activa.myLanguageManager.DOCUMENTS_DOWNLOADED;
    			Notification notification = new Notification(R.drawable.icon, message, System.currentTimeMillis());
    			notification.defaults = Notification.DEFAULT_ALL;
    			notification.setLatestEventInfo(Activa.myApp, Activa.myLanguageManager.NOTIFICATION_TITLE, message, pendingIntent);
    			Activa.myNotificationManager.notify(0, notification);	
    		} else {
    			Intent contentIntent = new Intent(Activa.myApp, Activa.class);
    			PendingIntent pendingIntent = PendingIntent.getActivity(Activa.myApp, 0,contentIntent, 0); 
    			String message = Activa.myLanguageManager.DOCUMENTS_FILES + Activa.myLanguageManager.DOCUMENTS_DOWNLOAD_FAIL;
    			Notification notification = new Notification(R.drawable.icon, message, System.currentTimeMillis());
    			notification.defaults = Notification.DEFAULT_ALL;
    			notification.setLatestEventInfo(Activa.myApp, Activa.myLanguageManager.NOTIFICATION_TITLE, message, pendingIntent);
    			Activa.myNotificationManager.notify(0, notification);	
    		}
    	}
    	
    }
	
	class FileLoadingStructure {
		
		File position;
		
		Document document;

		public FileLoadingStructure(File position, Document document) {
			super();
			this.position = position;
			this.document = document;
		}
		
	}

}

class MyWebView extends WebView {

	AlertDialog alertDialog;
	
	public MyWebView(Context context , AlertDialog alertDialog) {
		super(context);
		this.alertDialog = alertDialog;
	}

	@Override
	public boolean onCheckIsTextEditor() {
		return true; 
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if (keyCode == KeyEvent.KEYCODE_BACK) {
	        this.alertDialog.cancel();
	        return true;
	    }
	    return super.onKeyDown(keyCode, event);
	}
}

class ImageFileFilter implements FileFilter
{
  private final String[] okFileExtensions = 
    new String[] {"jpg", "png", "gif"};

  public boolean accept(File file)
  {
    for (String extension : okFileExtensions)
    {
      if (file.getName().toLowerCase().endsWith(extension))
      {
        return true;
      }
    }
    return false;
  }
}

class FolderFileFilter implements FileFilter {
  public boolean accept(File file)
  {
      if (file.isDirectory())
      {
        return true;
      }
    return false;
  }
}

class FileFileFilter implements FileFilter {
  public boolean accept(File file)
  {
	if (!file.isDirectory())
      {
        return true;
      }
	else return false;
  }
}