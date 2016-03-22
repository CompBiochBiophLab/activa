package com.o2hlink.activa.ui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyStore.LoadStoreParameter;
import java.text.BreakIterator;
import java.text.AttributedCharacterIterator.Attribute;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.TimerTask;
import java.util.Vector;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.opengl.Visibility;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.IBinder;
import android.text.Editable;
import android.text.Html;
import android.text.Layout;
import android.text.Spanned;
import android.text.Html.ImageGetter;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SubMenu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnKeyListener;
import android.view.View.OnTouchListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.AbsoluteLayout;
import android.widget.CheckBox;
import android.widget.Chronometer;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.RemoteViews;
import android.widget.ScrollView;
import android.widget.Scroller;
import android.widget.SeekBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageView.ScaleType;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TableLayout.LayoutParams;

import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.o2hlink.activa.ActivaConfig;
import com.o2hlink.activa.ActivaUtil;
import com.o2hlink.activa.R;
import com.o2hlink.activa.Activa;
import com.o2hlink.activa.background.DownloadFiles;
import com.o2hlink.activa.background.GetHistory;
import com.o2hlink.activa.background.GetNews;
import com.o2hlink.activa.background.InitialConnection;
import com.o2hlink.activa.background.RefreshingConnection;
import com.o2hlink.activa.background.SendNote;
import com.o2hlink.activa.background.SendO2Message;
import com.o2hlink.activa.background.SendQuestionnaire;
import com.o2hlink.activa.background.SendSensorResult;
import com.o2hlink.activa.background.UserCheckout;
import com.o2hlink.activa.data.calendar.Event;
import com.o2hlink.activa.data.message.Contact;
import com.o2hlink.activa.data.message.O2Message;
import com.o2hlink.activa.data.message.O2UnregisteredMessage;
//import com.o2hlink.activa.data.program.Physical;
import com.o2hlink.activa.data.program.Program;
import com.o2hlink.activa.data.program.ProgramManager;
import com.o2hlink.activa.data.questionnaire.Question;
import com.o2hlink.activa.data.questionnaire.Questionnaire;
import com.o2hlink.activa.data.sensor.PulseOximeter;
import com.o2hlink.activa.data.sensor.Sensor;
import com.o2hlink.activa.data.sensor.SensorManager;
import com.o2hlink.activa.data.sensor.Spirometer;
import com.o2hlink.activa.mobile.MobileManager;
import com.o2hlink.activa.mobile.User;
import com.o2hlink.activa.news.Feed;
import com.o2hlink.activa.news.New;
import com.o2hlink.activa.notes.Note;
import com.o2hlink.activa.patient.Patient;
import com.o2hlink.activa.ui.widget.Ambient;
import com.o2hlink.activa.ui.widget.Subenvironment;
import com.o2hlink.activa.ui.widget.Widget;
import com.o2hlink.activa.exterior.ExteriorManager;
import com.o2hlink.activa.exterior.ExternalApp;
import com.paypal.android.MEP.CheckoutButton;
import com.paypal.android.MEP.PayPal;
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
	public static final int UI_STATE_SCHEDULEWEEK = 17;
	public static final int UI_STATE_SCHEDULEMONTH = 18;
	public static final int UI_STATE_MAP = 19;
	public static final int UI_STATE_MESSAGE = 20;
	public static final int UI_STATE_MESSAGEWRITING = 21;
	public static final int UI_STATE_MESSAGEREADING = 22;
	public static final int UI_STATE_EXERCISE = 23;
	public static final int UI_STATE_NEWS = 24;
	public static final int UI_STATE_NOTES = 24;
	public static final int UI_STATE_PATIENTS = 25;
	
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

	public String ambienturl;
	
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
	}
	
	public void loadDefaultAmbient() {
		this.ambient = Ambient.getInstance();
		this.ambient.getDefaultEnvironment();
	}
	
	public void loadScreen(int screen) {
		Activa.myApp.setContentView(screen);
	}
	
	public void loadInitScreen() {
		this.state = UI_STATE_USERINFO;
		Activa.myApp.setContentView(R.layout.init);
		CountDownTimer timer = new CountDownTimer(3000,1000) {
			@Override
			public void onTick(long millisUntilFinished) {
			}
			
			@Override
			public void onFinish() {
				if (Activa.myMobileManager.users.isEmpty()) 
					Activa.myUIManager.loadRegisterScreen();
				else 
					Activa.myUIManager.loadLoginScreen();
			}
		};
		timer.start();
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
				Activa.myUIManager.loadRegisterScreen();
			}
		});
	}
	
	public void loadRegisterScreen() {
		this.state = UI_STATE_REGISTER;
		Activa.myApp.setContentView(R.layout.register);
		((TextView) Activa.myApp.findViewById(R.id.startText)).setText(Activa.myLanguageManager.PSW_REG_TITLE);
		((TextView) Activa.myApp.findViewById(R.id.requestText)).setText(Activa.myLanguageManager.PSW_REG_REQUEST);
		((TextView) Activa.myApp.findViewById(R.id.requestUser)).setText(Activa.myLanguageManager.PSW_REG_USERNAME);
		((TextView) Activa.myApp.findViewById(R.id.requestPass)).setText(Activa.myLanguageManager.PSW_REG_PASSWORD);
		((TextView) Activa.myApp.findViewById(R.id.requestPassagain)).setText(Activa.myLanguageManager.PSW_REG_PASSWORD_AGAIN);
		final EditText username = (EditText) Activa.myApp.findViewById(R.id.loginText);
		final EditText password = (EditText) Activa.myApp.findViewById(R.id.passwordText);
		final EditText passwordAgain = (EditText) Activa.myApp.findViewById(R.id.passwordTextagain);
		ImageButton ok = (ImageButton) Activa.myApp.findViewById(R.id.ok);
		ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
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
					Activa.myMobileManager.user.setCreated(false);
					Thread trd = new Thread(new UserCheckout());
					trd.start();
				} catch (Exception e) {
					loadInfoPopup(Activa.myLanguageManager.PSW_REG_BADDATA);
				}
			}
		});
	}
	
	public void loadRegisterDataScreen() {
		this.state = UI_STATE_REGISTER;
		Activa.myApp.setContentView(R.layout.registerdata);
		((TextView) Activa.myApp.findViewById(R.id.startText)).setText(Activa.myLanguageManager.PSW_REG_TITLE);
		((TextView) Activa.myApp.findViewById(R.id.requestText)).setText(Activa.myLanguageManager.PSW_REG_DATAREQUEST);
		((TextView) Activa.myApp.findViewById(R.id.requestFirst)).setText(Activa.myLanguageManager.PSW_REG_FIRSTNAME);
		((TextView) Activa.myApp.findViewById(R.id.requestLast)).setText(Activa.myLanguageManager.PSW_REG_LASTNAME);
		((TextView) Activa.myApp.findViewById(R.id.requestDate)).setText(Activa.myLanguageManager.PSW_REG_DATE);
		((RadioButton) Activa.myApp.findViewById(R.id.male)).setText(Activa.myLanguageManager.PSW_REG_MALE);
		((RadioButton) Activa.myApp.findViewById(R.id.female)).setText(Activa.myLanguageManager.PSW_REG_FEMALE);
		((RadioButton) Activa.myApp.findViewById(R.id.patient)).setText(Activa.myLanguageManager.PSW_REG_PATIENT);
		((RadioButton) Activa.myApp.findViewById(R.id.clinician)).setText(Activa.myLanguageManager.PSW_REG_CLINICIAN);
		((RadioButton) Activa.myApp.findViewById(R.id.researcher)).setText(Activa.myLanguageManager.PSW_REG_RESEARCHER);
		final EditText first = (EditText) Activa.myApp.findViewById(R.id.firstText);
		final EditText last = (EditText) Activa.myApp.findViewById(R.id.lastText);
		final EditText date = (EditText) Activa.myApp.findViewById(R.id.dateText);
		final RadioButton male = (RadioButton) Activa.myApp.findViewById(R.id.male);
		final RadioButton female = (RadioButton) Activa.myApp.findViewById(R.id.female);
		final RadioButton patient = (RadioButton) Activa.myApp.findViewById(R.id.patient);
		final RadioButton clinician = (RadioButton) Activa.myApp.findViewById(R.id.clinician);
		final RadioButton researcher = (RadioButton) Activa.myApp.findViewById(R.id.researcher);
		ImageButton ok = (ImageButton) Activa.myApp.findViewById(R.id.ok);
		ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try{
					if (first.getText().toString().equals("")) throw new Exception();
					if (last.getText().toString().equals("")) throw new Exception();
					Activa.myMobileManager.user.setFirstname(first.getText().toString());
					Activa.myMobileManager.user.setLastname(last.getText().toString());
					Activa.myMobileManager.user.setCountry(Activa.myApp.getResources().getConfiguration().locale.getDisplayCountry());
					Date birthdate;
					try {
						birthdate = ActivaUtil.universalReadableStringToDate(date.getText().toString());
					} catch (Exception e) {
						date.setText("");
						throw new Exception();
					}
					Activa.myMobileManager.user.setBirthdate(birthdate);
					boolean isMale = true;
					if (female.isChecked()) isMale = false;
					Activa.myMobileManager.user.setMale(isMale);
					int type;
					if (patient.isChecked()) type = 0;
					else if (clinician.isChecked()) type = 1;
					else type = 2;
					Activa.myMobileManager.user.setType(type);
					loadMatrixPasswordForRegistering();
				} catch (Exception e) {
					Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.PSW_REG_BADDATA);
				} 
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
	
	public void loadRequestDataScreen() {
		Activa.myApp.setContentView(R.layout.data);
		boolean birthAsked = false;
		if (((new Date()).getYear())-(Activa.myMobileManager.user.getBirthdate().getYear()) < 2) birthAsked = true;
		((TextView) Activa.myApp.findViewById(R.id.startText)).setText(Activa.myLanguageManager.PSW_REG_TITLE);
		((TextView) Activa.myApp.findViewById(R.id.requestText)).setText(Activa.myLanguageManager.PSW_REG_DATAREQUEST);
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
					Activa.myMobileManager.addUserWithPassword(Activa.myMobileManager.user);
					if (Activa.mySensorManager.programassociated != null) {
						Activa.mySensorManager.programassociated.state--;
						Activa.mySensorManager.programassociated.nextStep();
					}
					else if (Activa.mySensorManager.eventAssociated != null) {
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
				Activa.myUIManager.loadGeneratedMainScreen(false, false);
			}
		});
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
	
	/**
	 * Load the info about the logged user.
	 */
	public void loadUserInfoScreen(final User user) {
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
        		if (Activa.myMobileManager.user.getAmbient() == null) Activa.myUIManager.showAmbients(false);
        		else Activa.myUIManager.loadGeneratedMainScreen(true, false);
			}
		};
		timer.start();
	}
	
	/**
	 * Load the main screen
	 */
	
	@SuppressWarnings("deprecation")
	public void loadGeneratedMainScreen(boolean login, boolean animated) {
		if (this.ambient == null) loadAmbient();
		else if (!this.ambient.loaded) loadAmbient();
		this.state = UI_STATE_MAIN;
		if (this.ambient.loaded) {
			// Load scenario
			Activa.myApp.setContentView(R.layout.scenario);
			// Load main image
			final AbsoluteLayout layout = (AbsoluteLayout) Activa.myApp.findViewById(R.id.Background);
			layout.setBackgroundDrawable(this.ambient.getBackground());
			layout.invalidate();
			// Load the context menu
			if (Activa.myMenu != null) {
				Activa.myMenu.clear();
				switch (Activa.myMobileManager.user.getType()) {
					case 0:
						Activa.myInflater.inflate(R.menu.main, Activa.myMenu);
						break;
					case 1:
						Activa.myInflater.inflate(R.menu.mainclinician, Activa.myMenu);
						break;
					case 2:
						Activa.myInflater.inflate(R.menu.mainresearcher, Activa.myMenu);
						break;
					default:
						Activa.myInflater.inflate(R.menu.main, Activa.myMenu);
						break;
				}
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
			// Set user's complete name
			if (Activa.myMobileManager.identified) ((TextView)Activa.myApp.findViewById(R.id.userNameText)).setText(Activa.myMobileManager.user.firstname + " " + Activa.myMobileManager.user.lastname);
			else ((TextView)Activa.myApp.findViewById(R.id.userNameText)).setText("-");
			//Load the refresh button
			ImageButton miscbutton = (ImageButton) Activa.myApp.findViewById(R.id.miscbutton);
			miscbutton.setBackgroundResource(R.drawable.refresh);
			miscbutton.setVisibility(View.VISIBLE);
			miscbutton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (Activa.myMobileManager.identified) {
						v.setVisibility(View.GONE);
						final RelativeLayout popupView = (RelativeLayout) Activa.myApp.findViewById(R.id.popupView);
						popupView.setVisibility(View.VISIBLE);
						TextView popupText = (TextView) Activa.myApp.findViewById(R.id.popupText);
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
					Activa.myUIManager.showAmbients(true);
				}
			});
			// Load the links to the subenvironments
			Enumeration<Subenvironment> subenvironments = this.ambient.subenvironments.elements();
			while(subenvironments.hasMoreElements()) {
				final Subenvironment subenvironment = subenvironments.nextElement();
				ImageButton button = new ImageButton(Activa.myApp);
				button.setClickable(false);
				button.setBackgroundResource(R.drawable.bgtouch);
				int realheight = Activa.myApp.getResources().getDisplayMetrics().heightPixels;
//				int realwidth = layout.getWidth();
				int top = subenvironment.top*(realheight/this.ambient.height);
//				int left = subenvironment.left*(realwidth/this.ambient.width);
				int left = subenvironment.left;
				int height = subenvironment.height*(realheight/this.ambient.height);
//				int width = subenvironment.width*(realwidth/this.ambient.width);
				int width = subenvironment.width;
				android.widget.AbsoluteLayout.LayoutParams params = new android.widget.AbsoluteLayout.LayoutParams(width, height, left, top);
				android.widget.AbsoluteLayout.LayoutParams textparams = new android.widget.AbsoluteLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, left + 20, top + 40);
				final TextView text = new TextView(Activa.myApp);
				text.setLayoutParams(textparams);
				text.setTextSize((float) 15.0);
				text.setPadding(10, 0, 0, 0);
				text.setTextColor(Color.BLACK);
				text.setText(subenvironment.name);
				text.setGravity(Gravity.CENTER);
				text.setBackgroundResource(R.drawable.bubble);
				layout.addView(text);
				CountDownTimer timer = new CountDownTimer(10000, 1000) {
					@Override
					public void onTick(long millisUntilFinished) {}
					@Override
					public void onFinish() {
						text.setVisibility(View.GONE);
					}
				};
				timer.start();
				button.setLayoutParams(params);
				button.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						for (int i = 0; i < layout.getChildCount(); i++) {
							layout.getChildAt(i).setVisibility(View.GONE);
						}
						if (subenvironment.animationframes > 0) {
							final AnimationDrawable animation = subenvironment.getAnimationMainToSub();
							if (animation != null) {
								ImageView animationFrame = new ImageView(Activa.myApp);
								android.widget.FrameLayout.LayoutParams params = new android.widget.FrameLayout.LayoutParams(Activa.myUIManager.ambient.width, android.widget.FrameLayout.LayoutParams.FILL_PARENT);
								animationFrame.setLayoutParams(params);
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
								layout.setAnimation(controller.getAnimation());
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
							layout.setAnimation(controller.getAnimation());
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
			// Make the initial connection if necessary
			if (login) {
				if (Activa.myMobileManager.identified) {
					((ImageButton)Activa.myApp.findViewById(R.id.miscbutton)).setVisibility(View.GONE);
					final RelativeLayout popupView = (RelativeLayout) Activa.myApp.findViewById(R.id.popupView);
					popupView.setVisibility(View.VISIBLE);
					TextView popupText = (TextView) Activa.myApp.findViewById(R.id.popupText);
					InitialConnection initial = new InitialConnection();
					Thread thread = new Thread(initial, "LOGIN");
					thread.start();
				}
				else{
					Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.USER_NOID);
					((ImageButton)Activa.myApp.findViewById(R.id.miscbutton)).setVisibility(View.VISIBLE);
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
	
	@SuppressWarnings("deprecation")
	public void loadGeneratedSubenvironment(final Subenvironment sub, boolean animated) {
		this.state = UI_STATE_MAIN;
		if (sub == null) {
			loadGeneratedMainScreen(false, false);
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
			}
			else Activa.myApp.setContentView(R.layout.scenariocompressed); 
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
			layout.setBackgroundDrawable(sub.getBackground());
			layout.invalidate();
			// Load the context menu
			if (Activa.myMenu != null) {
				Activa.myMenu.clear();
				switch (Activa.myMobileManager.user.getType()) {
					case 0:
						Activa.myInflater.inflate(R.menu.main, Activa.myMenu);
						break;
					case 1:
						Activa.myInflater.inflate(R.menu.mainclinician, Activa.myMenu);
						break;
					case 2:
						Activa.myInflater.inflate(R.menu.mainresearcher, Activa.myMenu);
						break;
					default:
						Activa.myInflater.inflate(R.menu.main, Activa.myMenu);
						break;
				}
			}
			// Set user's complete name
			if (Activa.myMobileManager.identified) ((TextView)Activa.myApp.findViewById(R.id.userNameText)).setText(Activa.myMobileManager.user.firstname + " " + Activa.myMobileManager.user.lastname);
			else ((TextView)Activa.myApp.findViewById(R.id.userNameText)).setText("-");
			//Load the outside button
			ImageButton miscbutton = (ImageButton) Activa.myApp.findViewById(R.id.miscbutton);
			miscbutton.setVisibility(View.GONE);
			miscbutton.setBackgroundResource(R.drawable.outside);
			miscbutton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					for (int i = 0; i < layout.getChildCount(); i++) {
						layout.getChildAt(i).setVisibility(View.GONE);
					}
					if (sub.animationframes > 0) {
						final AnimationDrawable animation = sub.getAnimationSubToMain();
						if (animation != null) {
							ImageView animationFrame = new ImageView(Activa.myApp);
							android.widget.FrameLayout.LayoutParams params = new android.widget.FrameLayout.LayoutParams(Activa.myUIManager.ambient.width, android.widget.FrameLayout.LayoutParams.FILL_PARENT);
							animationFrame.setLayoutParams(params);
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
								int center = (Activa.myUIManager.ambient.width/2)-(sub.filewidth/2);
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
									loadGeneratedMainScreen(false, true);
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
							layout.setAnimation(controller.getAnimation());
							CountDownTimer timer = new CountDownTimer(2000,1000) {
								@Override
								public void onTick(long millisUntilFinished) {
								}
								@Override
								public void onFinish() {
									loadGeneratedMainScreen(false, false);
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
						layout.setAnimation(controller.getAnimation());
						CountDownTimer timer = new CountDownTimer(2000,1000) {
							@Override
							public void onTick(long millisUntilFinished) {
							}
							@Override
							public void onFinish() {
								loadGeneratedMainScreen(false, false);
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
			// Load the links to the subenvironments
			Enumeration<Widget> widgets = sub.widgets.elements();
			while(widgets.hasMoreElements()) {
				final Widget widget = widgets.nextElement();
				ImageButton button = new ImageButton(Activa.myApp);
				button.setBackgroundResource(R.drawable.bgtouch);
				int realheight = Activa.myApp.getResources().getDisplayMetrics().heightPixels;
//				int realwidth = layout.getWidth();
				int top = widget.top*(realheight/this.ambient.height);
//				int left = subenvironment.left*(realwidth/this.ambient.width);
				int left = widget.left;
				int height = widget.height*(realheight/this.ambient.height);
//				int width = subenvironment.width*(realwidth/this.ambient.width);
				int width = widget.width;
				android.widget.AbsoluteLayout.LayoutParams textparams = new android.widget.AbsoluteLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, left, top);
				final TextView text = new TextView(Activa.myApp);
				text.setLayoutParams(textparams);
				text.setTextSize((float) 15.0);
				text.setPadding(10, 0, 0, 0);
				text.setTextColor(Color.BLACK);
				text.setText(widget.name);
				text.setGravity(Gravity.CENTER);
				text.setBackgroundResource(R.drawable.bubble);
				layout.addView(text);
				CountDownTimer timer2 = new CountDownTimer(8000, 1000) {
					@Override
					public void onTick(long millisUntilFinished) {}
					@Override
					public void onFinish() {
						text.setVisibility(View.GONE);
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
				layout.addView(button);
			}
		}
	}
	
	private void scrollToCenter(View view, int time, int delay, int middle) {
		AnimationSet set = new AnimationSet(true);
		set.setFillAfter(true);
		HorizontalScrollView scroll = (HorizontalScrollView) Activa.myApp.findViewById(R.id.ScrollView);
		int x = scroll.getScrollX();
		int realwidth = Activa.myApp.getResources().getDisplayMetrics().widthPixels;
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
		HorizontalScrollView scroll = (HorizontalScrollView) Activa.myApp.findViewById(R.id.ScrollView);
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
		HorizontalScrollView scroll = (HorizontalScrollView) Activa.myApp.findViewById(R.id.ScrollView);
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
		this.state = UI_STATE_TOTALQUESTIONNAIRE;
		Activa.myApp.setContentView(R.layout.list);
		((TextView) Activa.myApp.findViewById(R.id.startText)).setText(Activa.myLanguageManager.QUEST_TITLE);
		TableLayout questlisting = (TableLayout)Activa.myApp.findViewById(R.id.listing);
		Enumeration<Questionnaire> enumer = Activa.myQuestManager.questionnaireSet.elements();
		while (enumer.hasMoreElements()) {			
			Questionnaire quest = enumer.nextElement();
			TableRow buttonLayout = new TableRow(Activa.myApp);
			buttonLayout.setId(quest.id);
			buttonLayout.setOrientation(TableRow.HORIZONTAL);
			buttonLayout.setGravity(Gravity.CENTER_VERTICAL);
			buttonLayout.setLayoutParams(new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));			
			ImageButton button = new ImageButton(Activa.myApp);
			button.setBackgroundResource(R.drawable.iconbg);
			button.setImageResource(R.drawable.quest);
			QuestionnaireButtonListenerDemo listener = new QuestionnaireButtonListenerDemo(quest.id);
			TextView text = new TextView(Activa.myApp);
			text.append(quest.name);
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
	}

	public void loadQuestion(final Question question) {
		Activa.myQuestManager.currentQuestion = question;
		this.state = UI_STATE_QUESTION;
		TextView questionText;
		ImageButton next;
		switch (question.type) {
			case Question.MONO_QUESTION:
				Activa.myApp.setContentView(R.layout.monoquestion);
				questionText = (TextView) Activa.myApp.findViewById(R.id.questionText);
				questionText.setText(question.text);
				RadioGroup answersGroup = (RadioGroup) Activa.myApp.findViewById(R.id.options);
				next = (ImageButton) Activa.myApp.findViewById(R.id.next);
				for (int i = 0; i < question.answerSet.size(); i++) {
					RadioButton button = new RadioButton(Activa.myApp);
					button.setText(question.answerSet.get(i).text);
					button.setTextColor(Color.BLACK);
					button.setId(i);
					button.setLayoutParams(new LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT));
					answersGroup.addView(button);
				}
				answersGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {					
					@Override
					public void onCheckedChanged(RadioGroup group, int checkedId) {
						Questionnaire questionnaire = Activa.myQuestManager.questionnaireSet.get(Activa.myQuestManager.activeQuestionnaireId);
						questionnaire.questionSet.get(questionnaire.currentQuestionId).monoAnswer = checkedId;
						questionnaire.questionSet.get(questionnaire.currentQuestionId).answered = true;							
					}
				});
				next.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						Questionnaire questionnaire = Activa.myQuestManager.questionnaireSet.get(Activa.myQuestManager.activeQuestionnaireId);
						if (questionnaire.questionSet.get(questionnaire.currentQuestionId).answered||questionnaire.questionSet.get(questionnaire.currentQuestionId).jumpable) {
							questionnaire.questionsAnswered.push(question);
							questionnaire.currentQuestionId = questionnaire.questionSet.get(questionnaire.currentQuestionId).calcNextQuestion();
							if (questionnaire.currentQuestionId > 0) Activa.myUIManager.loadQuestion(questionnaire.questionSet.get(questionnaire.currentQuestionId));	
							else Activa.myQuestManager.finishQuestionnaire();
						}
					}
				});
				break;
			case Question.POLI_QUESTION:
				question.poliAnswer = new HashSet<Integer>();
				Activa.myApp.setContentView(R.layout.poliquestion);
				questionText = (TextView) Activa.myApp.findViewById(R.id.questionText);
				questionText.setText(question.text);
				next = (ImageButton) Activa.myApp.findViewById(R.id.next);
				LinearLayout answersGroupPoli = (LinearLayout) Activa.myApp.findViewById(R.id.options);
				for (int i = 0; i < question.answerSet.size(); i++) {
					CheckBox button = new CheckBox(Activa.myApp);
					button.setText(question.answerSet.get(i).text);
					button.setTextColor(Color.BLACK);
					button.setId(i);
					button.setLayoutParams(new LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
					answersGroupPoli.addView(button);
					button.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {	
						@Override
						public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
							Questionnaire questionnaire = Activa.myQuestManager.questionnaireSet.get(Activa.myQuestManager.activeQuestionnaireId);
							Question question = questionnaire.questionSet.get(questionnaire.currentQuestionId);
							if (isChecked) question.poliAnswer.add(buttonView.getId()); 
							else question.poliAnswer.remove(buttonView.getId()); 
						}
					});
				}
				next.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						Questionnaire questionnaire = Activa.myQuestManager.questionnaireSet.get(Activa.myQuestManager.activeQuestionnaireId);
						if (!questionnaire.questionSet.get(questionnaire.currentQuestionId).poliAnswer.isEmpty()) 
							questionnaire.questionSet.get(questionnaire.currentQuestionId).answered = true;
						if (questionnaire.questionSet.get(questionnaire.currentQuestionId).answered||questionnaire.questionSet.get(questionnaire.currentQuestionId).jumpable) {
							questionnaire.questionsAnswered.push(question);
							questionnaire.currentQuestionId = questionnaire.questionSet.get(questionnaire.currentQuestionId).calcNextQuestion();
							if (questionnaire.currentQuestionId > 0) Activa.myUIManager.loadQuestion(questionnaire.questionSet.get(questionnaire.currentQuestionId));	
							else Activa.myQuestManager.finishQuestionnaire();
						}
					}
				});
				break;
			case Question.NUMERIC_QUESTION:
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
				questionText.setText(question.text);
				if (question.borg)
					numText.setText("0 - " + representation[0]);
				else 
					numText.setText("0");
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
						if (question.borg) {
							int selection = progress/5;
							if ((selection == 1)) numText.setText("0.5 - " + representation[1]);
							else numText.setText("" + progress/10 + " - " + representation[selection]);
						}
						else 
							numText.setText("" + progress/10);						
					}
				});
				next = (ImageButton) Activa.myApp.findViewById(R.id.next);
				next.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						Questionnaire questionnaire = Activa.myQuestManager.questionnaireSet.get(Activa.myQuestManager.activeQuestionnaireId);
						Question question = questionnaire.questionSet.get(questionnaire.currentQuestionId);
						SeekBar seekbar = (SeekBar) Activa.myApp.findViewById(R.id.seekbar);
						if (question.borg) {
							if ((seekbar.getProgress()/5) == 1) question.numericAnswer = 0.5f;
							else question.numericAnswer = (float) (seekbar.getProgress()/10); 
						}
						else question.numericAnswer = (float) (seekbar.getProgress()/10);
						question.answered = true;
						questionnaire.questionsAnswered.push(question);
						questionnaire.currentQuestionId = question.calcNextQuestion();
						if (questionnaire.currentQuestionId > 0) Activa.myUIManager.loadQuestion(questionnaire.questionSet.get(questionnaire.currentQuestionId));	
						else Activa.myQuestManager.finishQuestionnaire();
					}
				});
				break;
			case Question.TEXT_QUESTION:
				question.poliAnswer = new HashSet<Integer>();
				Activa.myApp.setContentView(R.layout.textquestion);
				questionText = (TextView) Activa.myApp.findViewById(R.id.questionText);
				questionText.setText(question.text);
				next = (ImageButton) Activa.myApp.findViewById(R.id.next);
				next.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						Questionnaire questionnaire = Activa.myQuestManager.questionnaireSet.get(Activa.myQuestManager.activeQuestionnaireId);
						EditText editable = (EditText) Activa.myApp.findViewById(R.id.editable);
						questionnaire.questionSet.get(questionnaire.currentQuestionId).textAnswer = editable.getText().toString();
						questionnaire.questionSet.get(questionnaire.currentQuestionId).answered = true;
						questionnaire.questionsAnswered.push(question);
						questionnaire.currentQuestionId = questionnaire.questionSet.get(questionnaire.currentQuestionId).calcNextQuestion();
						if (questionnaire.currentQuestionId > 0) Activa.myUIManager.loadQuestion(questionnaire.questionSet.get(questionnaire.currentQuestionId));	
						else Activa.myQuestManager.finishQuestionnaire();
					}
				});
				break;
			case Question.INFO_QUESTION:
				Activa.myApp.setContentView(R.layout.info);
				((RelativeLayout)Activa.myApp.findViewById(R.id.questcontrol)).setVisibility(View.VISIBLE);
				TextView text = (TextView) Activa.myApp.findViewById(R.id.textInfo); 
				text.setText(question.text);
				text.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
				((ImageButton) Activa.myApp.findViewById(R.id.previous)).setVisibility(View.VISIBLE);
				next = (ImageButton) Activa.myApp.findViewById(R.id.next);
				next.setVisibility(View.VISIBLE);
				next.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						Questionnaire questionnaire = Activa.myQuestManager.questionnaireSet.get(Activa.myQuestManager.activeQuestionnaireId);
						questionnaire.questionsAnswered.push(question);
						questionnaire.currentQuestionId = questionnaire.questionSet.get(questionnaire.currentQuestionId).calcNextQuestion();
						if (questionnaire.currentQuestionId > 0) Activa.myUIManager.loadQuestion(questionnaire.questionSet.get(questionnaire.currentQuestionId));	
						else Activa.myQuestManager.finishQuestionnaire();
					}
				});
				break;
			case Question.RESULT_QUESTION:
				break;			
		}
		ImageButton back = (ImageButton) Activa.myApp.findViewById(R.id.previous);
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Questionnaire questionnaire = Activa.myQuestManager.questionnaireSet.get(Activa.myQuestManager.activeQuestionnaireId);
				if (!questionnaire.questionsAnswered.empty()) {
					Question prev = Activa.myQuestManager.questionnaireSet.get(Activa.myQuestManager.activeQuestionnaireId).questionsAnswered.pop();
					questionnaire.currentQuestionId = prev.id;
					prev.answered = false;
					Activa.myUIManager.loadQuestion(prev);
				}
				else {
					Activa.myQuestManager.questionnaireSet.get(Activa.myQuestManager.activeQuestionnaireId).currentQuestionId = 1;
					Activa.myUIManager.loadGeneratedSubenvironment(Activa.myUIManager.lastSubenv, true);
				}
			}
		});
		ImageButton quit = (ImageButton) Activa.myApp.findViewById(R.id.quit);
		quit.setVisibility(View.VISIBLE);
		quit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Questionnaire questionnaire = Activa.myQuestManager.questionnaireSet.get(Activa.myQuestManager.activeQuestionnaireId);
				while (!questionnaire.questionsAnswered.empty()) {
					Question prev = Activa.myQuestManager.questionnaireSet.get(Activa.myQuestManager.activeQuestionnaireId).questionsAnswered.pop();
					prev.answered = false;
				}
				Activa.myQuestManager.questionnaireSet.get(Activa.myQuestManager.activeQuestionnaireId).currentQuestionId = 1;
				Activa.myUIManager.loadGeneratedSubenvironment(Activa.myUIManager.lastSubenv, true);
			}
		});
	}
	
	/**
	 * This method start the filling of the selected as active questionnaire.
	 */
	public void startActiveQuestionnaire() {
		Activa.myUIManager.state = UIManager.UI_STATE_QUESTIONNAIREINIT;
		Activa.myApp.setContentView(R.layout.yesnoquestion);
		Activa.myQuestManager.activeQuestionnaireId = Activa.myQuestManager.activeQuestionnaireId;
		TextView question = (TextView)Activa.myApp.findViewById(R.id.question);
		question.append(Activa.myLanguageManager.QUEST_START + Activa.myQuestManager.questionnaireSet.get(Activa.myQuestManager.activeQuestionnaireId).name + "?");
		ImageButton yes = (ImageButton)Activa.myApp.findViewById(R.id.yes);
		ImageButton no = (ImageButton)Activa.myApp.findViewById(R.id.no);
		yes.setOnClickListener(null);
		yes.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Activa.myQuestManager.questionnaireSet.get(Activa.myQuestManager.activeQuestionnaireId).start();
			}
		});
		no.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Activa.myUIManager.loadGeneratedSubenvironment(Activa.myUIManager.lastSubenv, true);
			}
		});
	}

	public void loadQuestResult(float result) {
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
				SendQuestionnaire sending = new SendQuestionnaire(Activa.myQuestManager.questionnaireSet.get(Activa.myQuestManager.activeQuestionnaireId));
				Thread thread = new Thread(sending, "SENDQUESTIONNAIRE");
				thread.start();
			}
		};
		timer.start();
	}
	
	public void loadSensorList() {
		this.state = UI_STATE_TOTALSENSOR;
		Activa.myApp.setContentView(R.layout.list);
		if (Activa.myMenu != null) {
			Activa.myMenu.clear();
			Activa.myInflater.inflate(R.menu.sensors, Activa.myMenu);
		}
		((TextView) Activa.myApp.findViewById(R.id.startText)).append(Activa.myLanguageManager.SENSORS_TITLE);
		TableLayout sensorlisting = (TableLayout)Activa.myApp.findViewById(R.id.listing);
		Enumeration<Sensor> enumer = Activa.mySensorManager.sensorList.elements();
		int[] sensorIDs = new int[Activa.mySensorManager.sensorList.size()];
		int index = 3;
		while (enumer.hasMoreElements()) {
			Sensor sensor = enumer.nextElement();
			if (sensor.id == SensorManager.ID_SPIROMETER) {
				sensorIDs[0] = sensor.id;
			}
			else if (sensor.id == SensorManager.ID_PULSIOXYMETER) {
				sensorIDs[1] = sensor.id;
			}
			else if (sensor.id == SensorManager.ID_EXERCISE) {
				sensorIDs[2] = sensor.id;
			}
			else {
				sensorIDs[index] = sensor.id;
				index++;
			}
		}
		for (int i = 0; i < sensorIDs.length; i++) {
			Sensor sensor = Activa.mySensorManager.sensorList.get(sensorIDs[i]);
			TableRow buttonLayout = new TableRow(Activa.myApp);
			buttonLayout.setId(sensor.id);
			buttonLayout.setOrientation(TableRow.HORIZONTAL);
			buttonLayout.setGravity(Gravity.CENTER_VERTICAL);
			buttonLayout.setLayoutParams(new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));			
			ImageButton button = new ImageButton(Activa.myApp);
			button.setBackgroundResource(R.drawable.iconbg);
			button.setImageResource(sensor.icon);
			button.setId(sensor.id);
			OnClickListener listener = new OnClickListener() {			
				@Override
				public void onClick(View v) {
					if (Activa.myMenu != null) Activa.myMenu.clear();
					Activa.mySensorManager.eventAssociated = null;
					Activa.myProgramManager.eventAssociated = null;
					Activa.mySensorManager.programassociated = null;
					Activa.mySensorManager.startSensorMeasurement(v.getId());
				}
			};
			button.setOnClickListener(listener);
			TextView text = new TextView(Activa.myApp);
			text.append(sensor.name);
			text.setTextSize((float) 20);
			text.setTextColor(Color.BLACK);
			text.setTypeface(Typeface.SANS_SERIF);
			text.setOnClickListener(listener);
			text.setId(sensor.id);
			buttonLayout.addView(button);
			buttonLayout.addView(text);
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
		Enumeration<String> enumeration;
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
			datesOrdered.add(temp.date);
			eventsOrdered.put(temp.date, temp);
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
				Date nextDate = new Date(dategiven.getTime());
				nextDate.setDate(nextDate.getDate() - 1);
				Activa.myUIManager.loadScheduleDay(nextDate);
			}
		});

		next.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Date nextDate = new Date(dategiven.getTime());
				nextDate.setDate(nextDate.getDate() + 1);
				Activa.myUIManager.loadScheduleDay(nextDate);
			}
		});
	}
	
	public void loadScheduleWeek(final Date dategiven) {
		TextView time;
		Enumeration<String> enumeration;
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
			datesOrdered.add(temp.date);
			eventsOrdered.put(temp.date, temp);
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
				Date nextDate = new Date(dategiven.getTime());
				nextDate.setDate(nextDate.getDate() - 7);
				Activa.myUIManager.loadScheduleWeek(nextDate);
			}
		});
		next.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Date nextDate = new Date(dategiven.getTime());
				nextDate.setDate(nextDate.getDate() + 7);
				Activa.myUIManager.loadScheduleWeek(nextDate);
			}
		});
	}
	
	public void loadScheduleMonth(final Date dategiven) {
		TextView time;
		View separator;
		LayoutParams separatorLayout;
		TableRow weekLayout;
		int days;
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
		int width = Activa.myApp.getResources().getDisplayMetrics().widthPixels;
		int height = Activa.myApp.getResources().getDisplayMetrics().heightPixels;
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
				Date nextDate = new Date(dategiven.getTime());
				nextDate.setMonth(nextDate.getMonth() - 1);
				Activa.myUIManager.loadScheduleMonth(nextDate);
			}
		});

		next.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Date nextDate = new Date(dategiven.getTime());
				nextDate.setMonth(nextDate.getMonth() + 1);
				Activa.myUIManager.loadScheduleMonth(nextDate);
			}
		});
	}
	
	
	public void loadProgramList() {
		this.state = UI_STATE_TOTALPROGRAM;
		Activa.myApp.setContentView(R.layout.list);
		((TextView) Activa.myApp.findViewById(R.id.startText)).append(Activa.myLanguageManager.PROGRAMS_TITLE);
		TableLayout programlisting = (TableLayout)Activa.myApp.findViewById(R.id.listing);
		Enumeration<Program> enumer = Activa.myProgramManager.programList.elements();
		while (enumer.hasMoreElements()) {
			Program program = enumer.nextElement();
			TableRow buttonLayout = new TableRow(Activa.myApp);
			buttonLayout.setId(program.id);
			buttonLayout.setOrientation(TableRow.HORIZONTAL);
			buttonLayout.setGravity(Gravity.CENTER_VERTICAL);
			buttonLayout.setLayoutParams(new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));			
			ImageButton button = new ImageButton(Activa.myApp);
			button.setBackgroundResource(R.drawable.iconbg);
			button.setImageResource(program.icon);
			button.setId(program.id);
			OnClickListener listener = new OnClickListener() {			
				@Override
				public void onClick(View v) {
					Activa.myProgramManager.eventAssociated = null;
					Activa.mySensorManager.eventAssociated = null;
					loadProgram(Activa.myProgramManager.programList.get(v.getId()));
				}
			};
			button.setOnClickListener(listener);
			TextView text = new TextView(Activa.myApp);
			text.append(program.name);
			text.setTextSize((float) 20);
			text.setTextColor(Color.BLACK);
			text.setTypeface(Typeface.SANS_SERIF);
			text.setOnClickListener(listener);
			text.setId(program.id);
			buttonLayout.addView(button);
			buttonLayout.addView(text);
			buttonLayout.setOnClickListener(listener);
			button.setOnClickListener(listener);
			text.setOnClickListener(listener);
			programlisting.addView(buttonLayout);
		}
		ImageButton back = (ImageButton) Activa.myApp.findViewById(R.id.previous);
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Activa.myUIManager.loadGeneratedSubenvironment(Activa.myUIManager.lastSubenv, true);
			}
		});
	}

	public void loadProgram(final Program program) {
		this.state = UI_STATE_PROGRAM;
		Activa.myApp.setContentView(R.layout.follow);
		TextView text = (TextView) Activa.myApp.findViewById(R.id.textInfo);
		text.setTextSize(19);
		text.setGravity(Gravity.LEFT|Gravity.CENTER_VERTICAL);
		text.append(program.description);
		ImageButton follow = (ImageButton) Activa.myApp.findViewById(R.id.follow);
		follow.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				program.startProgram();
			}
		});		
		ImageButton quit = (ImageButton) Activa.myApp.findViewById(R.id.quit);
		quit.setVisibility(View.VISIBLE);
		quit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Activa.myUIManager.loadGeneratedSubenvironment(Activa.myUIManager.lastSubenv, true);
			}
		});		
	}
	
	public void finishProgram() {
		this.state = UI_STATE_PROGRAM;
		Activa.myUIManager.loadScreen(R.layout.info);
		TextView text = (TextView) Activa.myApp.findViewById(R.id.textInfo);
		text.append(Activa.myLanguageManager.PROGRAMS_FINISH);
		CountDownTimer timer = new CountDownTimer(3000,1000) {
			@Override
			public void onTick(long millisUntilFinished) {
				// TODO Auto-generated method stub
			}
			@Override
			public void onFinish() {
				if (Activa.myProgramManager.eventAssociated != null) loadScheduleDay(Activa.myProgramManager.eventAssociated.date);
				Activa.myUIManager.loadGeneratedSubenvironment(Activa.myUIManager.lastSubenv, true);
			}
		};
//		BeginTimeTask timer = new BeginTimeTask(3000, 1000);
		timer.start();			
	}
	
	public void loadQuestResult2(float result) {
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
		CountDownTimer timer = new CountDownTimer(3000,1000) {
			@Override
			public void onTick(long millisUntilFinished) {
				// TODO Auto-generated method stub
			}
			@Override
			public void onFinish() {
				if (Activa.myProgramManager.eventAssociated != null) loadScheduleDay(Activa.myProgramManager.eventAssociated.date);
				else loadProgramList();
			}
		};
//		WaitTimeTask timer = new WaitTimeTask(3000, 1000, 2);
		timer.start();
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
			final LinearLayout layout = (LinearLayout) Activa.myApp.findViewById(R.id.mainLayout);
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
			CountDownTimer timer = new CountDownTimer(6000, 1000) {
				@Override
				public void onTick(long millisUntilFinished) {}
				@Override
				public void onFinish() {
//					Activa.myApp.setContentView(R.layout.sending);
//					SendSensorResult sending = new SendSensorResult(sensor);
//					Thread thread = new Thread(sending, "SENDQUESTIONNAIRE");
//					thread.start();
//					if (sensor.bluetoothPreviouslyConnected) 
//						Activa.myBluetoothAdapter.enable();
					String result = sensor.getSensorGlobalResult();
					int resultInt = Integer.parseInt(result.substring(0, 1));
					String resultString = result.substring(2);
					loadSensorGlobalResult(resultString, resultInt, sensor);
				}
			};
//			timer.start();
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
	
	public void finishSensorMeasurement(String sensorString, boolean outcome, final Sensor sensor, int[] order) {
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
			final LinearLayout layout = (LinearLayout) Activa.myApp.findViewById(R.id.mainLayout);
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
			CountDownTimer timer = new CountDownTimer(6000, 1000) {
				@Override
				public void onTick(long millisUntilFinished) {}
				@Override
				public void onFinish() {
//					Activa.myApp.setContentView(R.layout.sending);
//					SendSensorResult sending = new SendSensorResult(sensor);
//					Thread thread = new Thread(sending, "SENDQUESTIONNAIRE");
//					thread.start();
//					if (sensor.bluetoothPreviouslyConnected) 
//						Activa.myBluetoothAdapter.enable();
					String result = sensor.getSensorGlobalResult();
					int resultInt = Integer.parseInt(result.substring(0, 1));
					String resultString = result.substring(2);
					loadSensorGlobalResult(resultString, resultInt, sensor);
				}
			};
//			timer.start();
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
			final LinearLayout layout = (LinearLayout) Activa.myApp.findViewById(R.id.mainLayout);
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
			CountDownTimer timer = new CountDownTimer(12000, 1000) {
				@Override
				public void onTick(long millisUntilFinished) {}
				@Override
				public void onFinish() {
//					Activa.myApp.setContentView(R.layout.sending);
//					SendSensorResult sending = new SendSensorResult(sensor);
//					Thread thread = new Thread(sending, "SENDQUESTIONNAIRE");
//					thread.start();
//					if (sensor.bluetoothPreviouslyConnected) 
//						Activa.myBluetoothAdapter.enable();
					String result = sensor.getSensorGlobalResult();
					int resultInt = Integer.parseInt(result.substring(0, 1));
					String resultString = result.substring(2);
					loadSensorGlobalResult(resultString, resultInt, sensor);
				}
			};
//			timer.start();
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
	
	public void finishExercise(boolean outcome, final Sensor sensor) {
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
					String result = ((PulseOximeter)sensor).exercise.getSensorGlobalResult();
					int resultInt = Integer.parseInt(result.substring(0, 1));
					String resultString = result.substring(2);
					loadExerciseGlobalResult(resultString, resultInt, sensor);
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
				if (Activa.mySensorManager.programassociated != null) {
					Activa.mySensorManager.programassociated.nextStep();
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
	
	public void loadExerciseGlobalResult (String result, int resultInt, final Sensor sensor) {
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
				Activa.myProgramManager.programList.get(ProgramManager.PROG_WALKING).nextStep();
			}
		};
		timer.start();
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

	public void loadMessageList() {
		Activa.myUIManager.state = UIManager.UI_STATE_MESSAGE;
		Activa.myApp.setContentView(R.layout.messagelist);
		((TextView) Activa.myApp.findViewById(R.id.startText)).setText(Activa.myLanguageManager.MESSAGES_TITLE);
		Hashtable<Date, O2Message> messagesOrdered = new Hashtable<Date, O2Message>();
		Vector<Date> datesOrdered = new Vector<Date>();
		Enumeration<O2Message> enumer = Activa.myMessageManager.messageList.elements();
		while (enumer.hasMoreElements()) {
			O2Message temp = enumer.nextElement();
			if (datesOrdered.contains(temp.date)) temp.date.setTime(temp.date.getTime() + 1);
			datesOrdered.add(temp.date);
			messagesOrdered.put(temp.date, temp);
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
			final O2Message message = messagesOrdered.get(enumeration.nextElement());
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
			if (message.date.after(today)) time.setText(ActivaUtil.timeToReadableString(message.date));
			else time.setText(ActivaUtil.dateToReadableString(message.date));
			TextView topic = new TextView(Activa.myApp);	
			topic.setLayoutParams(new TableRow.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
			topic.setPadding(5, 10, 5, 10);
			topic.setTypeface(Typeface.DEFAULT);
			topic.setTextSize(20);
			topic.setGravity(Gravity.LEFT);
			topic.setTextColor(Color.BLACK);
			String topicText = message.topic;
			if (topicText.length() > 20) topicText = topicText.substring(0, 17) + "...";
			topic.setText(topicText);
			OnClickListener listener = new OnClickListener() {			
				@Override
				public void onClick(View v) {
					Activa.myUIManager.showMessage(message);
				}
			};
			topic.setOnClickListener(listener);
			time.setOnClickListener(listener);
			buttonLayout.addView(topic);	
			buttonLayout.addView(time);		
			buttonLayout.setOnClickListener(listener);
			messagelist.addView(buttonLayout);
			View separator = new View(Activa.myApp);
			LayoutParams separatorLayout = new LayoutParams(android.view.ViewGroup.LayoutParams.FILL_PARENT, 1);
			separator.setLayoutParams(separatorLayout);
			separator.setBackgroundColor(Color.BLACK);
			messagelist.addView(separator);
		}
		ImageButton back = (ImageButton) Activa.myApp.findViewById(R.id.previous);
		ImageButton create = (ImageButton) Activa.myApp.findViewById(R.id.create);
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Activa.myUIManager.loadGeneratedSubenvironment(Activa.myUIManager.lastSubenv, true);
			}
		});
		create.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Activa.myUIManager.createMessage();
			}
		});
	}

	public void showMessage(O2Message message) {
		Activa.myUIManager.state = UIManager.UI_STATE_MESSAGEREADING;
		Activa.myMessageManager.currentMessage = message;
		Activa.myApp.setContentView(R.layout.messagetoreceive);
		((TextView) Activa.myApp.findViewById(R.id.startText)).setText(Activa.myLanguageManager.MESSAGES_TITLE);
		((TextView) Activa.myApp.findViewById(R.id.senderText)).setText(Activa.myLanguageManager.MESSAGES_SENDER);
		((TextView) Activa.myApp.findViewById(R.id.topicText)).setText(Activa.myLanguageManager.MESSAGES_TOPIC);
		((TextView) Activa.myApp.findViewById(R.id.dateText)).setText(Activa.myLanguageManager.MESSAGES_DATE);
		((TextView) Activa.myApp.findViewById(R.id.sender)).setText(Activa.myMessageManager.contactList.get(message.sender).name);
		((TextView) Activa.myApp.findViewById(R.id.topic)).setText(message.topic);
		((TextView) Activa.myApp.findViewById(R.id.text)).setText(message.text);
		((TextView) Activa.myApp.findViewById(R.id.date)).setText(ActivaUtil.dateToReadableString(message.date) + ", " + ActivaUtil.timeToReadableString(message.date));
		ImageButton back = (ImageButton) Activa.myApp.findViewById(R.id.previous);
		ImageButton response = (ImageButton) Activa.myApp.findViewById(R.id.response);
		ImageButton resend = (ImageButton) Activa.myApp.findViewById(R.id.resend);
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Activa.myUIManager.loadMessageList();
			}
		});
		response.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Activa.myUIManager.createMessage();
				((TextView) Activa.myApp.findViewById(R.id.receiver)).setText(Activa.myMessageManager.contactList.get(Activa.myMessageManager.currentMessage.sender).name);
				String bwtopic = "RE: " + Activa.myMessageManager.currentMessage.topic;
				((EditText) Activa.myApp.findViewById(R.id.topic)).setText(bwtopic);
				String bwtext = "RE --------------------------------\n\n" 
					+ "Sender: " + Activa.myMessageManager.contactList.get(Activa.myMessageManager.currentMessage.sender).name + "\n"
					+ "Receiver: " + Activa.myMessageManager.contactList.get(Activa.myMessageManager.currentMessage.receiver).name + "\n"
					+ "Topic: " + Activa.myMessageManager.currentMessage.topic + "\n"
					+ "Date: " + ActivaUtil.dateToReadableString(Activa.myMessageManager.currentMessage.date) + ", " + ActivaUtil.timeToReadableString(Activa.myMessageManager.currentMessage.date)
					+ "\n\n\n" + Activa.myMessageManager.currentMessage.text;
				((EditText) Activa.myApp.findViewById(R.id.text)).setText(bwtext);
			}
		});
		resend.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Activa.myUIManager.createMessage();
				String bwtopic = "FW: " + Activa.myMessageManager.currentMessage.topic;
				((EditText) Activa.myApp.findViewById(R.id.topic)).setText(bwtopic);
				String bwtext = "FW --------------------------------\n\n" 
					+ "Sender: " + Activa.myMessageManager.contactList.get(Activa.myMessageManager.currentMessage.sender).name + "\n"
					+ "Receiver: " + Activa.myMessageManager.contactList.get(Activa.myMessageManager.currentMessage.receiver).name + "\n"
					+ "Topic: " + Activa.myMessageManager.currentMessage.topic + "\n"
					+ "Date: " + ActivaUtil.dateToReadableString(Activa.myMessageManager.currentMessage.date) + ", " + ActivaUtil.timeToReadableString(Activa.myMessageManager.currentMessage.date)
					+ "\n\n\n" + Activa.myMessageManager.currentMessage.text;
				((EditText) Activa.myApp.findViewById(R.id.text)).setText(bwtext);
			}
		});
	}

	public void createMessage() {
		Activa.myUIManager.state = UIManager.UI_STATE_MESSAGEWRITING;
		Activa.myApp.setContentView(R.layout.messagetosend);
		((TextView) Activa.myApp.findViewById(R.id.startText)).setText(Activa.myLanguageManager.MESSAGES_TITLE);
		((TextView) Activa.myApp.findViewById(R.id.receiverText)).setText(Activa.myLanguageManager.MESSAGES_RECEIVER);
		((TextView) Activa.myApp.findViewById(R.id.topicText)).setText(Activa.myLanguageManager.MESSAGES_TOPIC);
		ImageButton back = (ImageButton) Activa.myApp.findViewById(R.id.previous);
		ImageButton send = (ImageButton) Activa.myApp.findViewById(R.id.send);
		final TextView receiver = ((TextView) Activa.myApp.findViewById(R.id.receiver));
		receiver.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Enumeration<Contact> contacts = Activa.myMessageManager.contactList.elements();
				final CharSequence[] items = new CharSequence[Activa.myMessageManager.contactList.size() + 1];
				items[0] = Activa.myLanguageManager.MESSAGES_ERASE_RECEPTOR;
				String[] itemsTemp = new String[Activa.myMessageManager.contactList.size()];
				int j = 0;
				while (contacts.hasMoreElements()) {
					itemsTemp[j] = contacts.nextElement().name;
					j++;
				}
				Arrays.sort(itemsTemp);
				for (int i = 0; i < itemsTemp.length; i++) {
					items[i+1] = itemsTemp[i];
				}
				AlertDialog.Builder builder = new AlertDialog.Builder(Activa.myApp);
				builder.setTitle(Activa.myLanguageManager.MESSAGES_CONTACT_REQUEST);
				builder.setItems(items, new DialogInterface.OnClickListener() {
				    public void onClick(DialogInterface dialog, int item) {
				    	if (item == 0) receiver.setText("");
				    	else if (receiver.getText().toString().equals("")) receiver.setText(items[item]);
				    	else receiver.append("," + items[item]);
				    }
				});
				AlertDialog alert = builder.create();
				alert.show();
			}
		});
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Activa.myUIManager.loadMessageList();
			}
		});
		send.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					String receiverString = ((TextView) Activa.myApp.findViewById(R.id.receiver)).getText().toString();
					StringTokenizer token = new StringTokenizer(receiverString,",");
					long[] receivers = new long[token.countTokens()];
					int j = 0;
					while (token.hasMoreTokens()) {
						receivers[j] = Activa.myMessageManager.getContactByName(token.nextToken()).id;
					}
					String topic = ((EditText) Activa.myApp.findViewById(R.id.topic)).getText().toString();
					String text = ((EditText) Activa.myApp.findViewById(R.id.text)).getText().toString();
					Activa.myApp.setContentView(R.layout.sending);
					long sender = Activa.myMobileManager.user.getId();
					Date date = new Date();
					O2UnregisteredMessage message = new O2UnregisteredMessage(sender, receivers, date, topic, text);
					SendO2Message sending = new SendO2Message(message);
					Thread thread = new Thread(sending, "SENDO2MESSAGE");
					thread.start();
				} catch (Exception e) {
					((EditText) Activa.myApp.findViewById(R.id.receiver)).setText("");
					loadInfoPopup(Activa.myLanguageManager.MESSAGES_ERROR_RECEIVER);
				}
			}
		});
	}

	public void refreshCreatingMessage(String receiverText, String topicText, String textText) {
		createMessage();
		((EditText) Activa.myApp.findViewById(R.id.receiver)).setText(receiverText);
		((EditText) Activa.myApp.findViewById(R.id.topic)).setText(topicText);
		((EditText) Activa.myApp.findViewById(R.id.text)).setText(textText);
	}

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
	
	public void loadNewsList(boolean refresh) {
		//TODO
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
	}
	
	public void LoadFeedNewList(final int feedId) {
		//TODO
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
				public void onClick(View v) {
					Activa.myUIManager.loadInfoPopupWithoutExiting(Activa.myLanguageManager.NEWS_LOADING);
					ImageView animationFrame = (ImageView) Activa.myApp.findViewById(R.id.popupImage);
					animationFrame.setVisibility(View.VISIBLE);
					animationFrame.setBackgroundResource(R.drawable.loading);
					AnimationDrawable animation = (AnimationDrawable) animationFrame.getBackground();
					animation.start();
					((FrameLayout)Activa.myApp.findViewById(R.id.layout)).invalidate();
					loadNew(v.getId(), feedId, true);
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
		Activa.myApp.setContentView(R.layout.newviewer);
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
		((TextView) Activa.myApp.findViewById(R.id.startText)).setText(feed.title);
		TextView count = (TextView) Activa.myApp.findViewById(R.id.newcount);
		TextView title = (TextView) Activa.myApp.findViewById(R.id.newtitle);
		TextView snippet = (TextView) Activa.myApp.findViewById(R.id.newsnippet);
//		ImageView photo = (ImageView) Activa.myApp.findViewById(R.id.newphoto);
		TextView text = (TextView) Activa.myApp.findViewById(R.id.newtext);
		ImageButton prevnew = (ImageButton) Activa.myApp.findViewById(R.id.previousnew);
		ImageButton nextnew = (ImageButton) Activa.myApp.findViewById(R.id.nextnew);
		ImageButton back = (ImageButton) Activa.myApp.findViewById(R.id.previous);
		count.setText((newId + 1) + "/" + feed.newslist.size());
		title.setText(notice.title);
		snippet.setText(notice.date + "\n\n" + notice.snippet);
//		if (notice.photoURL != null) {
//			try {
//				photo.setImageDrawable(Drawable.createFromStream((InputStream)(new URL(notice.photoURL)).getContent(), "src"));
//			} catch (Exception e) {
//				photo.setVisibility(View.GONE);
//			}
//		}
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
		text.setText(span);
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				LoadFeedNewList(feedId);
			}
		});
		prevnew.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				loadNew(currentNewId - 1, feedId, false);
			}
		});
		nextnew.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				loadNew(currentNewId + 1, feedId, true);
			}
		});
	}
	
	public void loadNotes() {
		//TODO
		this.state = UI_STATE_NOTES;
		Activa.myApp.setContentView(R.layout.noteslist);
		((TextView) Activa.myApp.findViewById(R.id.startText)).setText(Activa.myLanguageManager.NOTES_TITLE);
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
		LinearLayout notelist = (LinearLayout)Activa.myApp.findViewById(R.id.control);
		for(int i = 0; i < datesOrdered.size(); i++) {
			Note note = notesOrdered.get(datesOrdered.get(i));
			TextView time = new TextView(Activa.myApp);
			time.setText(ActivaUtil.dateToReadableString(note.date) + ", " + ActivaUtil.timeToReadableString(note.date));
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
		final CharSequence[] items = {Activa.myExteriorManager.externalApplications.get(ExteriorManager.APP_FACEBOOK).name, 
				Activa.myExteriorManager.externalApplications.get(ExteriorManager.APP_TWITTER).name};
		AlertDialog.Builder builder = new AlertDialog.Builder(Activa.myApp);
		builder.setTitle(Activa.myLanguageManager.MAIN_SOCIAL);
		builder.setItems(items, new DialogInterface.OnClickListener() {
		    public void onClick(DialogInterface dialog, int item) {
		        switch (item) {
					case 0:
						Activa.myExteriorManager.runApplication(ExteriorManager.APP_FACEBOOK);
						break;
					case 1:
						Activa.myExteriorManager.runApplication(ExteriorManager.APP_TWITTER);
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
				Activa.myExteriorManager.externalApplications.get(ExteriorManager.APP_LASTFM).name};
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
					default:
						break;
				}
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
		final CharSequence[] items = {Activa.myExteriorManager.externalApplications.get(ExteriorManager.APP_VIDEOS).name, 
				Activa.myExteriorManager.externalApplications.get(ExteriorManager.APP_VIDEOCAMERA).name, 
				Activa.myExteriorManager.externalApplications.get(ExteriorManager.APP_YOUTUBE).name};
		AlertDialog.Builder builder = new AlertDialog.Builder(Activa.myApp);
		builder.setTitle(Activa.myLanguageManager.MAIN_VIDEOS);
		builder.setItems(items, new DialogInterface.OnClickListener() {
		    public void onClick(DialogInterface dialog, int item) {
		        switch (item) {
					case 0:
						Activa.myExteriorManager.runApplication(ExteriorManager.APP_VIDEOS);
						break;
					case 1:
						Activa.myExteriorManager.runApplication(ExteriorManager.APP_VIDEOCAMERA);
						break;
					case 2:
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
	
	public void showPatients () {
		this.state = UI_STATE_PATIENTS;
		Activa.myApp.setContentView(R.layout.patients);
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
					Activa.myProgramManager.eventAssociated = null;
					Activa.mySensorManager.eventAssociated = null;
					loadPatientMenu(patient);
				}
			};
			button.setOnClickListener(listener);
			TextView text = new TextView(Activa.myApp);
			text.append(patient.firstname + " " + patient.lastname);
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
	}
	
	public void loadPatientMenu(final Patient patient) {
		this.state = UI_STATE_PATIENTS;
		Activa.myApp.setContentView(R.layout.list);
		((TextView) Activa.myApp.findViewById(R.id.startText)).append(patient.firstname + " " + patient.lastname);
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
				GetHistory sending = new GetHistory(patient);
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
	
	@SuppressWarnings("deprecation")
	public void showHistory(final Patient patient) {
		this.state = UI_STATE_PATIENTS;
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
		text.append(patient.lastPulseoximetry.getPulsioximetrySpanData());
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
		text.append(patient.lastExercise.getExerciseSpanData());
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
		text.append(patient.lastSpirometry.getSpirometrySpanData());
		content.addView(text);
		FrameLayout frame = new FrameLayout(Activa.myApp);
		frame.setLayoutParams(new LayoutParams(200, 200));
		SpirometryGraphViewWithCustomData graph = new SpirometryGraphViewWithCustomData(Activa.myApp, patient.lastSpirometry, 200, 200);
		frame.addView(graph);
		content.addView(frame);
		separator = new AbsoluteLayout(Activa.myApp);
		separator.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, 1));
		separator.setBackgroundColor(Color.BLACK);
		content.addView(separator);
		
		ImageButton back = (ImageButton) Activa.myApp.findViewById(R.id.previous);
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				loadPatientMenu(patient);
			}
		});
	}
	
	public void showMap() {
		Activa.myMapManager.showingMap = true;
		Activa.myApp.startActivity(new Intent(Activa.myApp, com.o2hlink.activa.map.ActivaMap.class));
	}
	
	public void loadOptionsScreen () {
		
	}
	
	public void showAmbients(final boolean returningpossible) {
		Activa.myApp.setContentView(R.layout.ambientlist);
		int screenwidth = Activa.myApp.getResources().getDisplayMetrics().widthPixels;
		((TextView) Activa.myApp.findViewById(R.id.startText)).append(Activa.myLanguageManager.MAIN_SELECT_ENVIRONMENT);
		TableLayout ambientlisting = (TableLayout)Activa.myApp.findViewById(R.id.listing);
		File[] ambients = Ambient.getAmbientList();
		if (ambients == null) ambients = new File[0];
		for (int i = 0; i < ambients.length; i++) {
			final String ambientname = ambients[i].getName();
			if (!Ambient.checkEnvironment(ambientname)) break; 
			File ambientpicture = new File(ambients[i], "mini.jpg");
			TableRow buttonLayout = new TableRow(Activa.myApp);
			buttonLayout.setOrientation(TableRow.HORIZONTAL);
			buttonLayout.setGravity(Gravity.CENTER_VERTICAL);
			buttonLayout.setLayoutParams(new TableLayout.LayoutParams(new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT)));		
			try {
				TextView text = new TextView(Activa.myApp);
				text.setLayoutParams(new android.widget.TableRow.LayoutParams(250, android.widget.TableRow.LayoutParams.WRAP_CONTENT));
				text.append(ambientname);
				text.setTextSize((float) 20);
				text.setTextColor(Color.BLACK);
				text.setTypeface(Typeface.SANS_SERIF);
				ImageView image = new ImageView(Activa.myApp);
//				image.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
//				image.setBackgroundResource(R.drawable.iconbg);
				Drawable imagepic = Drawable.createFromStream(new FileInputStream(ambientpicture), "src");
				image.setImageDrawable(imagepic);
				OnClickListener listener = new OnClickListener() {			
					@Override
					public void onClick(View v) {
						Activa.myMobileManager.user.setAmbient(ambientname);
						Activa.myMobileManager.saveUsers();
						loadAmbient();
						loadGeneratedMainScreen(true, false);
					}
				};
				image.setPadding(5, 5, 5, 5);
				image.setOnClickListener(listener);
				image.setScaleType(ScaleType.FIT_XY);
				text.setOnClickListener(listener);
				buttonLayout.addView(image, 74, 58);
				buttonLayout.addView(text);
				buttonLayout.setOnClickListener(listener);
				image.setOnClickListener(listener);
				text.setOnClickListener(listener);
				ambientlisting.addView(buttonLayout);
				View separator = new View(Activa.myApp);
				LayoutParams separatorLayout = new LayoutParams(android.view.ViewGroup.LayoutParams.FILL_PARENT, 1);
				separator.setLayoutParams(separatorLayout);
				separator.setBackgroundColor(Color.BLACK);
				ambientlisting.addView(separator);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		TextView text = new TextView(Activa.myApp);
		text.setLayoutParams(new android.widget.TableRow.LayoutParams(android.widget.TableRow.LayoutParams.FILL_PARENT, android.widget.TableRow.LayoutParams.WRAP_CONTENT));
		text.setPadding(5, 10, 5, 10);
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
		((LinearLayout)Activa.myApp.findViewById(R.id.control)).addView(text);
		((ImageButton) Activa.myApp.findViewById(R.id.previous)).setVisibility(View.GONE);
		View separator = new View(Activa.myApp);
		LayoutParams separatorLayout = new LayoutParams(android.view.ViewGroup.LayoutParams.FILL_PARENT, 1);
		separator.setLayoutParams(separatorLayout);
		separator.setBackgroundColor(Color.BLACK);
		((LinearLayout)Activa.myApp.findViewById(R.id.control)).addView(separator);
		
		ImageButton back = (ImageButton) Activa.myApp.findViewById(R.id.previous);
		if (returningpossible) {
			back.setVisibility(View.VISIBLE);
			back.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					loadGeneratedMainScreen(false, false);
				}
			});
		}
		
		ImageButton plus = (ImageButton) Activa.myApp.findViewById(R.id.plus);
		plus.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showAmbientsForDownloading(returningpossible);
			}
		});
	}
	
	public void showAmbientsForDownloading(final boolean returningpossible) {
		Activa.myApp.setContentView(R.layout.ambientlist);
		((TextView) Activa.myApp.findViewById(R.id.startText)).append(Activa.myLanguageManager.MAIN_DOWNLOAD_ENVIRONMENT);
		TableLayout ambientlisting = (TableLayout)Activa.myApp.findViewById(R.id.listing);
		ArrayList<String[]> ambients = Ambient.getAmbientsForDownloading();
		for (int i = 0; i < ambients.size(); i++) {
			final String ambientname = ambients.get(i)[0];
			final String ambienturl = ambients.get(i)[1];
			final String ambientprice = ambients.get(i)[2];
			Drawable ambientpicture = Activa.myProtocolManager.getDrawableFromFile(ambienturl, "mini.jpg");
			TableRow buttonLayout = new TableRow(Activa.myApp);
			buttonLayout.setOrientation(TableRow.HORIZONTAL);
			buttonLayout.setGravity(Gravity.CENTER_VERTICAL);
			buttonLayout.setLayoutParams(new TableLayout.LayoutParams(new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT)));		
			ImageView image = new ImageView(Activa.myApp);
			image.setBackgroundResource(R.drawable.iconbg);
			image.setLayoutParams(new android.widget.TableRow.LayoutParams(android.widget.TableRow.LayoutParams.WRAP_CONTENT, android.widget.TableRow.LayoutParams.WRAP_CONTENT));
			image.setImageDrawable(ambientpicture);
			OnClickListener listener = new OnClickListener() {			
				@Override
				public void onClick(View v) {
					if (Activa.myMobileManager.identified) {
						loadInfoPopup(Activa.myLanguageManager.MAIN_NOTAVAILABLE);
						return;
					}
					Context mContext = Activa.myApp.getApplicationContext();
					if (!ambientprice.equalsIgnoreCase("null")) {
						AlertDialog.Builder builder = new AlertDialog.Builder(Activa.myApp);
						builder.setMessage("Este ambiente vale " + ambientprice + " EUR. Desea comprarlo a traves de Paypal y descargarlo?")
					       .setCancelable(false)
					       .setPositiveButton("Si, ir a PayPal", new DialogInterface.OnClickListener() {
					           public void onClick(DialogInterface dialog, int id) {
									PayPalPayment payment = new PayPalPayment();
									payment.setSubtotal(new BigDecimal(ambientprice));
									payment.setCurrencyType("EUR");
									payment.setRecipient("arejas@gmail.com");
									payment.setPaymentType(PayPal.PAYMENT_TYPE_GOODS);
									Intent checkoutIntent = PayPal.getInstance().checkout(payment, Activa.myApp);
									Activa.myUIManager.ambientname = ambientname;
									Activa.myUIManager.ambienturl = ambienturl;
									Activa.myApp.startActivityForResult(checkoutIntent, 100);
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
					}
					else {
						AlertDialog.Builder builder = new AlertDialog.Builder(Activa.myApp);
						builder.setMessage("Este ambiente es gratuito. Desea descargarlo?")
						       .setCancelable(false)
						       .setPositiveButton("Si", new DialogInterface.OnClickListener() {
						           public void onClick(DialogInterface dialog, int id) {
										HashSet<String> files = Ambient.getFilesForDownloading(ambienturl);
										DownloadFiles run = new DownloadFiles(ambientname, ambienturl, files);
										Thread thr = new Thread(run);
										thr.start();
										dialog.cancel();
										loadInfoPopup("Descargando el ambiente");
						           }
						       })
						       .setNegativeButton("No", new DialogInterface.OnClickListener() {
						           public void onClick(DialogInterface dialog, int id) {
						                dialog.cancel();
						           }
						       });
						AlertDialog alert = builder.create();
						alert.show();
					}
				}
			};
			image.setOnClickListener(listener);
			image.setPadding(5, 5, 5, 5);
			TextView text = new TextView(Activa.myApp);
			text.setLayoutParams(new android.widget.TableRow.LayoutParams(150, android.widget.TableRow.LayoutParams.WRAP_CONTENT));
			text.setPadding(5, 10, 5, 10);
			text.setText(ambientname);
			text.setTextSize((float) 20);
			text.setTextColor(Color.BLACK);
			text.setTypeface(Typeface.SANS_SERIF);
			text.setOnClickListener(listener);
			TextView price = new TextView(Activa.myApp);
			price.setLayoutParams(new android.widget.TableRow.LayoutParams(100, android.widget.TableRow.LayoutParams.WRAP_CONTENT));
			price.setPadding(5, 10, 5, 10);
			if (ambientprice.equalsIgnoreCase("null")) price.setText("FREE");
			else price.setText(ambientprice + " EUR");
			price.setTextSize((float) 16);
			price.setTextColor(Color.BLACK);
			price.setTypeface(Typeface.DEFAULT_BOLD);
			price.setOnClickListener(listener);
			buttonLayout.addView(image, 74, 58);
			buttonLayout.addView(text);
			buttonLayout.addView(price);
			buttonLayout.setOnClickListener(listener);
			image.setOnClickListener(listener);
			text.setOnClickListener(listener);
			ambientlisting.addView(buttonLayout);
			View separator = new View(Activa.myApp);
			LayoutParams separatorLayout = new LayoutParams(android.view.ViewGroup.LayoutParams.FILL_PARENT, 1);
			separator.setLayoutParams(separatorLayout);
			separator.setBackgroundColor(Color.BLACK);
			ambientlisting.addView(separator);
		}
		((ImageButton) Activa.myApp.findViewById(R.id.plus)).setVisibility(View.GONE);
		ImageButton back = (ImageButton) Activa.myApp.findViewById(R.id.previous);
		back.setVisibility(View.VISIBLE);
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showAmbients(returningpossible);
			}
		});
	}
	 
}

class QuestionnaireButtonListenerDemo implements OnClickListener {
	
	int questionnaireID;
	
	public QuestionnaireButtonListenerDemo(int id) {
		this.questionnaireID = id;
	}
	
	@Override
	public void onClick(View v) {
		boolean valid = Activa.myQuestManager.questionnaireSet.get(this.questionnaireID).validateQuestionnaire();
		if (valid) {
			Activa.myUIManager.state = UIManager.UI_STATE_QUESTIONNAIREINIT;
			Activa.myApp.setContentView(R.layout.yesnoquestion);
			Activa.myQuestManager.activeQuestionnaireId = this.questionnaireID;
			Activa.myQuestManager.eventAssociated = null;
			Activa.myUIManager.startActiveQuestionnaire();
		}
		else Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.QUEST_NOTVALID);
	}
	
}