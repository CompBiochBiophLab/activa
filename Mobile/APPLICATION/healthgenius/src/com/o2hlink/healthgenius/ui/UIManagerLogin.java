package com.o2hlink.healthgenius.ui;

import java.util.ArrayList;
import java.util.Date;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.media.AudioManager;
import android.net.Uri;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.text.Html;
import android.text.Spanned;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.TableLayout.LayoutParams;

import com.o2hlink.activ8.common.entity.Country;
import com.o2hlink.activ8.common.entity.Sex;
import com.o2hlink.activ8.common.entity.TimeZone;
import com.o2hlink.activ8.common.entity.VisibilityLevel;
import com.o2hlink.healthgenius.HealthGenius;
import com.o2hlink.healthgenius.HealthGeniusConfig;
import com.o2hlink.healthgenius.HealthGeniusUtil;
import com.o2hlink.healthgenius.R;
import com.o2hlink.healthgenius.background.MainThread;
import com.o2hlink.healthgenius.background.NotificationThread;
import com.o2hlink.healthgenius.background.UserCheckout;
import com.o2hlink.healthgenius.background.UserCheckoutForRegister;
import com.o2hlink.healthgenius.connection.ProtocolManager;
import com.o2hlink.healthgenius.data.calendar.CalendarManager;
import com.o2hlink.healthgenius.data.contacts.ContactsManager;
import com.o2hlink.healthgenius.data.questionnaire.control.QuestControlManager;
import com.o2hlink.healthgenius.data.sensor.SensorManager;
import com.o2hlink.healthgenius.exterior.ExteriorManager;
import com.o2hlink.healthgenius.mobile.MobileManager;
import com.o2hlink.healthgenius.mobile.User;
import com.o2hlink.healthgenius.patient.PatientManager;

public class UIManagerLogin {
	
	public UIManager myUIManager;
	
	public UIManagerLogin(UIManager ui) {
		myUIManager = ui;
	}

	/**
	 * Load the first screen on the app. This charges the managers
	 */
	public void loadInitScreen() {
		myUIManager.state = UIManager.UI_STATE_USERINFO;
		HealthGenius.myApp.setContentView(R.layout.init);
		CountDownTimer timer = new CountDownTimer(1000,1000) {
			@Override
			public void onTick(long millisUntilFinished) {
			}
			@Override
			public void onFinish() {
		        HealthGenius.myLanguageManager = HealthGenius.setLanguage(HealthGenius.myApp.getResources().getConfiguration().locale);
		        HealthGenius.rootFile = Environment.getExternalStorageDirectory();
		        HealthGenius.myTelephonyManager = (TelephonyManager) HealthGenius.myApp.getSystemService(Context.TELEPHONY_SERVICE);
		        HealthGenius.myMobileManager = MobileManager.getInstance(); 
		        HealthGenius.myProtocolManager = ProtocolManager.getInstance();
		        HealthGenius.myCalendarManager = CalendarManager.getInstance();
		        HealthGenius.mySensorManager = SensorManager.getInstance();
		        HealthGenius.myAlarmManager = (AlarmManager)HealthGenius.myApp.getSystemService(Context.ALARM_SERVICE);
		        HealthGenius.myNotificationManager = (NotificationManager) HealthGenius.myApp.getSystemService(Activity.NOTIFICATION_SERVICE);
		        HealthGenius.myMainBackgroundThread = new MainThread();
		        HealthGenius.myAccelerometerManager = (android.hardware.SensorManager)HealthGenius.myApp.getSystemService(Context.SENSOR_SERVICE);
		        HealthGenius.myAudioManager = (AudioManager)HealthGenius.myApp.getSystemService(Context.AUDIO_SERVICE);
		        HealthGenius.myNotificationThread = NotificationThread.getInstance();
		        HealthGenius.myExteriorManager = ExteriorManager.getInstance();
		        HealthGenius.myPatientManager = PatientManager.getInstance();
		        HealthGenius.myQuestControlManager = QuestControlManager.getInstance();
		        HealthGenius.myContactsManager = ContactsManager.getInstance();
		        if (HealthGenius.myMobileManager.users.isEmpty()) 
					loadInitialProcessScreen();
				else 
					loadLoginScreen();
			}
		};
		timer.start();
	}
	
	public void loadInitialProcessScreen() {
		int width = HealthGenius.myApp.getWindowManager().getDefaultDisplay().getWidth();
		myUIManager.state = UIManager.UI_STATE_USERINFO;
		HealthGenius.myApp.setContentView(R.layout.empty2);
		LinearLayout block = (LinearLayout) HealthGenius.myApp.findViewById(R.id.info);
		LinearLayout.LayoutParams par = new LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		par.setMargins(25, 0, 25, 0);
		TextView title = new TextView(HealthGenius.myApp);
		title.setTextColor(Color.BLACK);
		title.setLayoutParams(par);
		title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		title.setTypeface(Typeface.DEFAULT_BOLD);
		title.setText(HealthGenius.myApp.getResources().getString(R.string.app_name) + " " + HealthGenius.myApp.getResources().getString(R.string.app_version) + "\n");
		TextView text = new TextView(HealthGenius.myApp);
		text.setLayoutParams(par);
		text.setTextColor(Color.BLACK);
		text.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
		text.setTypeface(Typeface.SANS_SERIF);
		text.setText(HealthGenius.myApp.getResources().getString(R.string.registerwarning));
		RelativeLayout rel = new RelativeLayout(HealthGenius.myApp);
		par = new LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		par.setMargins(0, 0, 0, 0);
		rel.setLayoutParams(par);
		ImageButton create = new ImageButton(HealthGenius.myApp);
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width*4/10, android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
		params.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
		create.setLayoutParams(params);
		create.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				loadUserTypeChoiceScreen(-1);
			}
		});
		create.setBackgroundResource(R.drawable.iconbg);
		create.setImageResource(HealthGenius.myLanguageManager.createAccFile);
		ImageButton enter = new ImageButton(HealthGenius.myApp);
		params = new RelativeLayout.LayoutParams(width*4/10, android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
		params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
		enter.setLayoutParams(params);
		enter.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				loadCheckUserScreen();
			}
		});
		enter.setBackgroundResource(R.drawable.iconbg);
		enter.setImageResource(HealthGenius.myLanguageManager.accessAccFile);
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
		HealthGenius.myMobileManager.password = "";
		myUIManager.state = UIManager.UI_STATE_LOGIN;
		HealthGenius.myApp.setContentView(R.layout.password);
		((TextView) HealthGenius.myApp.findViewById(R.id.startText)).setText(HealthGenius.myLanguageManager.PSW_REQUEST);
		final FrameLayout board = (FrameLayout) HealthGenius.myApp.findViewById(R.id.passLayout);
		board.addView(new PasswordView(HealthGenius.myApp, 0));
		ImageButton add = (ImageButton) HealthGenius.myApp.findViewById(R.id.add);
		add.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				loadInitialProcessScreen();
			}
		});
	}
	
	public void loadCheckUserScreen() {
		myUIManager.state = UIManager.UI_STATE_CHECKING;
		HealthGenius.myApp.setContentView(R.layout.register);
		((TextView) HealthGenius.myApp.findViewById(R.id.startText)).setText(HealthGenius.myLanguageManager.PSW_REG_REQUEST);
		((TextView) HealthGenius.myApp.findViewById(R.id.requestText)).setText(HealthGenius.myLanguageManager.PSW_REG_ENTERDATA);
		((TextView) HealthGenius.myApp.findViewById(R.id.requestUser)).setText(HealthGenius.myLanguageManager.PSW_REG_USERNAME);
		((TextView) HealthGenius.myApp.findViewById(R.id.requestPass)).setText(HealthGenius.myLanguageManager.PSW_REG_PASSWORD);
		((TextView) HealthGenius.myApp.findViewById(R.id.requestPassagain)).setVisibility(View.INVISIBLE);
		final EditText username = (EditText) HealthGenius.myApp.findViewById(R.id.loginText);
		final EditText password = (EditText) HealthGenius.myApp.findViewById(R.id.passwordText);
		final EditText passwordAgain = (EditText) HealthGenius.myApp.findViewById(R.id.passwordTextagain);
		password.setOnKeyListener(new OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_ENTER) {
					InputMethodManager imm = (InputMethodManager)HealthGenius.myApp.getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(passwordAgain.getWindowToken(), 0);
				}
				return false;
			}
		});
		passwordAgain.setVisibility(View.INVISIBLE);
		ImageButton ok = (ImageButton) HealthGenius.myApp.findViewById(R.id.ok);
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
					HealthGenius.myMobileManager.user = new User(userText, passText);
					HealthGenius.myMobileManager.user.setCreated(false);
					Thread trd = new Thread(new UserCheckout());
					trd.start();
				} catch (Exception e) {
					myUIManager.UImisc.loadInfoPopup(HealthGenius.myLanguageManager.PSW_REG_BADDATA);
				}
			}
		});
	}
	
	public void loadUserTypeChoiceScreen(final int type) {
		myUIManager.state = UIManager.UI_STATE_REGISTER;
		HealthGenius.myApp.setContentView(R.layout.usertype);
		((TextView) HealthGenius.myApp.findViewById(R.id.startText)).setText(HealthGenius.myApp.getResources().getString(R.string.usertypeexpl));
		if ((type >=0)&&(type <=2)) {
			((TextView) HealthGenius.myApp.findViewById(R.id.explanationtitle)).setText(HealthGenius.myApp.getResources().getStringArray(R.array.usertype)[type]);
			((TextView) HealthGenius.myApp.findViewById(R.id.explanation)).setText(HealthGenius.myApp.getResources().getStringArray(R.array.usertypewarn)[type]);
		}
		((ImageButton) HealthGenius.myApp.findViewById(R.id.patient)).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				loadUserTypeChoiceScreen(0);
			}
		});
		((ImageButton) HealthGenius.myApp.findViewById(R.id.clinician)).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				loadUserTypeChoiceScreen(1);
			}
		});
		((ImageButton) HealthGenius.myApp.findViewById(R.id.ok)).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (type >= 0) loadRegisterScreen(type);
			}
		});
	}
	
	public void loadRegisterScreen(final int usertype) {
		myUIManager.state = UIManager.UI_STATE_REGISTER;
		HealthGenius.myApp.setContentView(R.layout.register);
		((TextView) HealthGenius.myApp.findViewById(R.id.startText)).setText(HealthGenius.myLanguageManager.PSW_REG_TITLE);
		((TextView) HealthGenius.myApp.findViewById(R.id.requestText)).setText(HealthGenius.myLanguageManager.PSW_REG_ENTERDATA);
		((TextView) HealthGenius.myApp.findViewById(R.id.requestUser)).setText(HealthGenius.myLanguageManager.PSW_REG_USERNAME);
		((TextView) HealthGenius.myApp.findViewById(R.id.requestPass)).setText(HealthGenius.myLanguageManager.PSW_REG_PASSWORD);
		((TextView) HealthGenius.myApp.findViewById(R.id.requestPassagain)).setText(HealthGenius.myLanguageManager.PSW_REG_PASSWORD_AGAIN);
		((RelativeLayout) HealthGenius.myApp.findViewById(R.id.userterms)).setVisibility(View.VISIBLE);
		Spanned userterms = Html.fromHtml(HealthGenius.myLanguageManager.PSW_REG_ACCEPT + "<a href=\"" + HealthGeniusConfig.ACTIV8TERMSANDCONDS_URL + "\">" + HealthGenius.myLanguageManager.MAIN_TERMSANDCONDS + "</a>");
		((TextView) HealthGenius.myApp.findViewById(R.id.usertermstext)).setText(userterms);
		((TextView) HealthGenius.myApp.findViewById(R.id.usertermstext)).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
		    	Intent in = new Intent(Intent.ACTION_VIEW);
				in.setData(Uri.parse(HealthGeniusConfig.ACTIV8TERMSANDCONDS_URL));
				HealthGenius.myApp.startActivity(in);
			}
		});
		final EditText username = (EditText) HealthGenius.myApp.findViewById(R.id.loginText);
		final EditText password = (EditText) HealthGenius.myApp.findViewById(R.id.passwordText);
		final EditText passwordAgain = (EditText) HealthGenius.myApp.findViewById(R.id.passwordTextagain);
		passwordAgain.setOnKeyListener(new OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_ENTER) {
					InputMethodManager imm = (InputMethodManager)HealthGenius.myApp.getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(passwordAgain.getWindowToken(), 0);
				}
				return false;
			}
		});
		ImageButton ok = (ImageButton) HealthGenius.myApp.findViewById(R.id.ok);
		ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					CheckBox usertermscheck = (CheckBox) HealthGenius.myApp.findViewById(R.id.usertermscheck);
					if (!usertermscheck.isChecked()) {
						myUIManager.UImisc.loadInfoPopup(HealthGenius.myLanguageManager.PSW_REG_ACCEPTTERMS);
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
					HealthGenius.myMobileManager.user = new User(userText, passText);
					HealthGenius.myMobileManager.user.setType(usertype);
					HealthGenius.myMobileManager.user.setCreated(false);
					Thread trd = new Thread(new UserCheckoutForRegister(usertype));
					trd.start();
				} catch (Exception e) {
					myUIManager.UImisc.loadInfoPopup(HealthGenius.myLanguageManager.PSW_REG_BADDATA);
				}
			}
		});
	}
	
	public void loadRegisterDataScreen(final int type) {
		myUIManager.state = UIManager.UI_STATE_REGISTERDATA;
		HealthGenius.myApp.setContentView(R.layout.registerdata);
		((TextView) HealthGenius.myApp.findViewById(R.id.startText)).setText(HealthGenius.myLanguageManager.PSW_REG_TITLE);
		((TextView) HealthGenius.myApp.findViewById(R.id.requestText)).setText(HealthGenius.myLanguageManager.PSW_REG_DATAREQUEST);
		((TextView) HealthGenius.myApp.findViewById(R.id.requestFirst)).setText(HealthGenius.myLanguageManager.PSW_REG_FIRSTNAME);
		((TextView) HealthGenius.myApp.findViewById(R.id.requestLast)).setText(HealthGenius.myLanguageManager.PSW_REG_LASTNAME);
		((TextView) HealthGenius.myApp.findViewById(R.id.requestDate)).setText(HealthGenius.myLanguageManager.PSW_REG_DATE);
		((TextView) HealthGenius.myApp.findViewById(R.id.requestEmail)).setText("E-Mail: ");
		((TextView) HealthGenius.myApp.findViewById(R.id.requestRepeatEmail)).setText(HealthGenius.myLanguageManager.PSW_REG_EMAILREPEAT);
		((TextView) HealthGenius.myApp.findViewById(R.id.sexrequest)).setText(HealthGenius.myLanguageManager.PSW_REG_SEX);
		((TextView) HealthGenius.myApp.findViewById(R.id.countryrequest)).setText(HealthGenius.myLanguageManager.PSW_REG_COUNTRY);
		final EditText first = (EditText) HealthGenius.myApp.findViewById(R.id.firstText);
		final EditText last = (EditText) HealthGenius.myApp.findViewById(R.id.lastText);
		final EditText mail = (EditText) HealthGenius.myApp.findViewById(R.id.emailText);
		final EditText repeatmail = (EditText) HealthGenius.myApp.findViewById(R.id.repeatEmailText);
		repeatmail.setOnKeyListener(new OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_ENTER) {
					InputMethodManager imm = (InputMethodManager)HealthGenius.myApp.getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(repeatmail.getWindowToken(), 0);
				}
				return false;
			}
		});
		final DatePicker date = (DatePicker) HealthGenius.myApp.findViewById(R.id.birthdate);
		final Spinner sex = (Spinner) HealthGenius.myApp.findViewById(R.id.sex);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(HealthGenius.myApp, R.array.sextype, android.R.layout.simple_spinner_item);
	    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    sex.setAdapter(adapter);
	    sex.setSelection(0);
		final Country[] countries = Country.values();
		final Spinner country = (Spinner) HealthGenius.myApp.findViewById(R.id.country);
		ArrayList<String> countrystrings = new ArrayList<String>();
		for (Country ctr : countries) {
			countrystrings.add(ctr.getName());
		}
		ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(HealthGenius.myApp, android.R.layout.simple_spinner_item, countrystrings);
	    adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    country.setAdapter(adapter3);
	    country.setSelection(0);
		ImageButton ok = (ImageButton) HealthGenius.myApp.findViewById(R.id.ok);
		ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try{
					if (first.getText().toString().equals("")) throw new Exception();
					if (last.getText().toString().equals("")) throw new Exception();
					if (HealthGeniusUtil.isAMail(mail.getText().toString())) throw new Exception();
					HealthGenius.myMobileManager.user.setFirstname(first.getText().toString());
					HealthGenius.myMobileManager.user.setLastname(last.getText().toString());
					if (countries != null) HealthGenius.myMobileManager.user.setCountry(countries[country.getSelectedItemPosition()]);
					HealthGenius.myMobileManager.user.setMail(mail.getText().toString());
					Date birthdate = new Date(0);
					birthdate.setYear(date.getYear() - 1900);
					birthdate.setMonth(date.getMonth());
					birthdate.setDate(date.getDayOfMonth());
					Date minbirthdate = new Date();
					minbirthdate.setMonth(minbirthdate.getMonth() - 1);
					if (mail.getText().equals(repeatmail.getText())) {
						myUIManager.UImisc.loadInfoPopup(HealthGenius.myLanguageManager.PSW_REG_EMAILDISMATCH);
						return;
					}
					if (birthdate.after(minbirthdate)) {
						myUIManager.UImisc.loadInfoPopup(HealthGenius.myLanguageManager.PSW_REG_MINBIRTH);
						return;
					}
					HealthGenius.myMobileManager.user.setBirthdate(birthdate);
					switch (sex.getSelectedItemPosition()) {
						case 0:
							HealthGenius.myMobileManager.user.setSex(Sex.NOT_SPECIFIED);
							break;
						case 1:
							HealthGenius.myMobileManager.user.setSex(Sex.MALE);
							break;
						default:
							HealthGenius.myMobileManager.user.setSex(Sex.FEMALE);
							break;
					}
					HealthGenius.myMobileManager.user.setType(type);
					loadPasswordExplanation();
				} catch (Exception e) {
					myUIManager.UImisc.loadInfoPopup(HealthGenius.myLanguageManager.PSW_REG_BADDATA);
				} 
			}
		});
	}
	
	public void loadPasswordExplanation() {
		HealthGenius.myApp.setContentView(R.layout.info);
		((TextView) HealthGenius.myApp.findViewById(R.id.textInfo)).setText(HealthGenius.myApp.getResources().getString(R.string.passwordwarning));
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
	
	public void loadAdditionalDataScreen (final boolean returntoopt) {
		HealthGenius.myApp.setContentView(R.layout.registerwebdata);
		final Spinner language = (Spinner) HealthGenius.myApp.findViewById(R.id.language);
		final ArrayList<TimeZone> timezonesviewed = new ArrayList<TimeZone>();
		((TextView)HealthGenius.myApp.findViewById(R.id.startText)).setText(HealthGenius.myApp.getResources().getString(R.string.registerewebdatastart));
		((TextView)HealthGenius.myApp.findViewById(R.id.languagerequest)).setText(HealthGenius.myApp.getResources().getString(R.string.languagerequest));
		((TextView)HealthGenius.myApp.findViewById(R.id.timezonerequest)).setText(HealthGenius.myApp.getResources().getString(R.string.timezonerequest));
		((TextView)HealthGenius.myApp.findViewById(R.id.privacyrequest)).setText(HealthGenius.myApp.getResources().getString(R.string.privacyrequest));
		((TextView) HealthGenius.myApp.findViewById(R.id.countryrequest)).setText(HealthGenius.myLanguageManager.PSW_REG_COUNTRY);
		final com.o2hlink.activ8.common.entity.Language[] languages = com.o2hlink.activ8.common.entity.Language.values();
		ArrayList<String> languagestrings = new ArrayList<String>();
		int j = 0;
		int selected = 0;
		for (com.o2hlink.activ8.common.entity.Language ctr : languages) {
			languagestrings.add(ctr.getName());
			if ((HealthGenius.myMobileManager.userForServices.getLanguage() != null)&&(ctr.getName().equals(HealthGenius.myMobileManager.userForServices.getLanguage().getName()))) selected = j;
			j++;
		}
		ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(HealthGenius.myApp, android.R.layout.simple_spinner_item, languagestrings);
	    adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    language.setAdapter(adapter3);
	    language.setSelection(selected);
	    final Country[] countries = Country.values();
		final Spinner country = (Spinner) HealthGenius.myApp.findViewById(R.id.country);
		ArrayList<String> countrystrings = new ArrayList<String>();
		j = 0;
		selected = 0;
		for (Country ctr : countries) {
			String thecountry = ctr.getName();
			String mycountry;
			try {
				mycountry = HealthGenius.myMobileManager.userForServices.getCountry().getName();
			} catch (Exception e) {
				mycountry = null;
			}
			countrystrings.add(thecountry);
			if ((mycountry != null)&&(thecountry.equalsIgnoreCase(mycountry))) selected = j;
			j++;
		}
		ArrayAdapter<String> adapter5 = new ArrayAdapter<String>(HealthGenius.myApp, android.R.layout.simple_spinner_item, countrystrings);
	    adapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    country.setAdapter(adapter5);
	    country.setSelection(0);
	    if (countries != null) country.setSelection(selected);
	    country.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected (AdapterView<?> parent, View view, int position, long id) {
			    final Spinner timezone = (Spinner) HealthGenius.myApp.findViewById(R.id.timezone);
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
						mytimezone = HealthGenius.myMobileManager.userForServices.getTimeZone().getName();
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
							mytimezone = HealthGenius.myMobileManager.userForServices.getTimeZone().getName();
						} catch (Exception e) {
							mytimezone = null;
						}
						timezonestrings.add(thetimezone);
						timezonesviewed.add(tmz);
						if ((mytimezone != null)&&(thetimezone.equalsIgnoreCase(mytimezone))) selected = j;
						j++;
					}
				}
				ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(HealthGenius.myApp, android.R.layout.simple_spinner_item, timezonestrings);
			    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			    timezone.setAdapter(adapter2);
			    timezone.setSelection(selected);
			}
			@Override
			public void onNothingSelected (AdapterView<?> parent) {
			    final Spinner timezone = (Spinner) HealthGenius.myApp.findViewById(R.id.timezone);
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
						mytimezone = HealthGenius.myMobileManager.userForServices.getTimeZone().getName();
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
							mytimezone = HealthGenius.myMobileManager.userForServices.getTimeZone().getName();
						} catch (Exception e) {
							mytimezone = null;
						}
						timezonestrings.add(thetimezone);
						timezonesviewed.add(tmz);
						if ((mytimezone != null)&&(thetimezone.equalsIgnoreCase(mytimezone))) selected = j;
						j++;
					}
				}
				ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(HealthGenius.myApp, android.R.layout.simple_spinner_item, timezonestrings);
			    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			    timezone.setAdapter(adapter2);
			    timezone.setSelection(selected);
			}
		});
	    final Spinner timezone = (Spinner) HealthGenius.myApp.findViewById(R.id.timezone);
		final TimeZone[] timezones = TimeZone.values();
		final ArrayList<String> timezonestrings = new ArrayList<String>();
		j = 0;
		selected = 0;
		timezonesviewed.clear();
		for (TimeZone tmz : timezones) {
			Country thecountry = HealthGenius.myMobileManager.userForServices.getCountry();
			String thetimezone = tmz.getName();
			String mytimezone;
			try {
				mytimezone = HealthGenius.myMobileManager.userForServices.getTimeZone().getName();
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
					mytimezone = HealthGenius.myMobileManager.userForServices.getTimeZone().getName();
				} catch (Exception e) {
					mytimezone = null;
				}
				timezonestrings.add(thetimezone);
				timezonesviewed.add(tmz);
				if ((mytimezone != null)&&(thetimezone.equalsIgnoreCase(mytimezone))) selected = j;
				j++;
			}
		}
		ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(HealthGenius.myApp, android.R.layout.simple_spinner_item, timezonestrings);
	    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    timezone.setAdapter(adapter2);
	    timezone.setSelection(selected);
	    String[] privacylevel = HealthGenius.myApp.getResources().getStringArray(R.array.privacylevel);
	    ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(HealthGenius.myApp, android.R.layout.simple_spinner_item, privacylevel);
		final Spinner privacy = (Spinner) HealthGenius.myApp.findViewById(R.id.privacy);
		if (HealthGenius.myMobileManager.userForServices.getPrivacyLevel() == null) privacy.setSelection(0);
		else if (HealthGenius.myMobileManager.userForServices.getPrivacyLevel().equals(VisibilityLevel.PUBLIC_VIEW)) privacy.setSelection(0);
		else if (HealthGenius.myMobileManager.userForServices.getPrivacyLevel().equals(VisibilityLevel.PRIVATE)) privacy.setSelection(1);
		else privacy.setSelection(0);
		((ImageButton)HealthGenius.myApp.findViewById(R.id.ok)).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				HealthGenius.myMobileManager.userForServices.setTimeZone(timezonesviewed.get(timezone.getSelectedItemPosition()));
				HealthGenius.myMobileManager.userForServices.setLanguage(languages[language.getSelectedItemPosition()]);
				if (countries != null) {
					HealthGenius.myMobileManager.userForServices.setCountry(countries[country.getSelectedItemPosition()]);
					HealthGenius.myMobileManager.user.setCountry(countries[country.getSelectedItemPosition()]);
				}
				if (privacy.getSelectedItemPosition() == 0) HealthGenius.myMobileManager.userForServices.setPrivacyLevel(VisibilityLevel.PUBLIC_VIEW);
				else HealthGenius.myMobileManager.userForServices.setPrivacyLevel(VisibilityLevel.PRIVATE);
				HealthGenius.myMobileManager.updateUserData();
				if (returntoopt) myUIManager.UIoptions.showOptions();
				else myUIManager.loadBoxClosed(true, true);
			}
		});
	}
	
	public void loadRequestDataScreen() {
		HealthGenius.myApp.setContentView(R.layout.data);
		boolean birthAsked = false;
		birthAsked = true;
		((TextView) HealthGenius.myApp.findViewById(R.id.startText)).setText(HealthGenius.myLanguageManager.SENSORS_WEIGHTHEIGTH_TITLE);
		((TextView) HealthGenius.myApp.findViewById(R.id.requestText)).setText("");
		((TextView) HealthGenius.myApp.findViewById(R.id.requestWeight)).setText(HealthGenius.myLanguageManager.PSW_REG_WEIGHT);
		((TextView) HealthGenius.myApp.findViewById(R.id.requestHeight)).setText(HealthGenius.myLanguageManager.PSW_REG_HEIGHT);
		final EditText weight = (EditText) HealthGenius.myApp.findViewById(R.id.weightText);
		final EditText height = (EditText) HealthGenius.myApp.findViewById(R.id.heightText);
		final EditText date = (EditText) HealthGenius.myApp.findViewById(R.id.dateText);
		if (birthAsked) {
			((TextView) HealthGenius.myApp.findViewById(R.id.requestDate)).setText(HealthGenius.myLanguageManager.PSW_REG_DATE);
			((TextView) HealthGenius.myApp.findViewById(R.id.requestWeight)).setVisibility(View.VISIBLE);
			date.setVisibility(View.VISIBLE);
		}
		ImageButton ok = (ImageButton) HealthGenius.myApp.findViewById(R.id.next);
		ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try{
					int heightText = Integer.parseInt(height.getText().toString());
					float weightText = Float.parseFloat(weight.getText().toString().replaceAll(",", "."));
					HealthGenius.myMobileManager.user.setHeight(heightText);
					HealthGenius.myMobileManager.user.setWeight(weightText);
					if (date.getVisibility() == View.VISIBLE)
						try {
							HealthGenius.myMobileManager.user.setBirthdate(HealthGeniusUtil.universalReadableStringToDate(date.getText().toString()));
						} catch (Exception e) {
							e.printStackTrace();
						}
					HealthGenius.myMobileManager.user.setLastupdate(new Date());
					HealthGenius.myMobileManager.saveUsers();
					HealthGenius.myMobileManager.sendWeightAndHeight(weightText, heightText);
					if (HealthGenius.mySensorManager.eventAssociated != null) {
						myUIManager.UIcalendar.loadScheduleDay(new Date());
						HealthGenius.mySensorManager.sensorList.get(SensorManager.ID_SPIROMETER).startMeasurement();
					}
					else {
						myUIManager.UImeas.loadSensorList();
						HealthGenius.mySensorManager.sensorList.get(SensorManager.ID_SPIROMETER).startMeasurement();
					}
				} catch (NumberFormatException e) {
					myUIManager.UImisc.loadInfoPopup(HealthGenius.myLanguageManager.PSW_REG_BADDATA);
				}
			}
		});
		ImageButton no = (ImageButton) HealthGenius.myApp.findViewById(R.id.back);
		no.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				myUIManager.loadBoxOpen();
			}
		});
	}
	
	public void loadMatrixPasswordForRegistering() {
		HealthGenius.myMobileManager.password = "";
		HealthGenius.myApp.setContentView(R.layout.password);
		((TextView) HealthGenius.myApp.findViewById(R.id.startText)).setText(HealthGenius.myLanguageManager.PSW_REQUEST);
		ImageButton add = (ImageButton) HealthGenius.myApp.findViewById(R.id.add);
		add.setVisibility(View.GONE);
		final FrameLayout board = (FrameLayout) HealthGenius.myApp.findViewById(R.id.passLayout);
		board.addView(new PasswordView(HealthGenius.myApp, 1, HealthGenius.myMobileManager.user));
	}
	
	public void loadRepeatPassword(User user, String prevPassword) {
		HealthGenius.myMobileManager.password = "";
		HealthGenius.myApp.setContentView(R.layout.password);
		((TextView) HealthGenius.myApp.findViewById(R.id.startText)).setText(HealthGenius.myLanguageManager.PSW_REQUEST_REPEAT);
		ImageButton add = (ImageButton) HealthGenius.myApp.findViewById(R.id.add);
		add.setVisibility(View.GONE);
		final FrameLayout board = (FrameLayout) HealthGenius.myApp.findViewById(R.id.passLayout);
		board.addView(new PasswordView(HealthGenius.myApp, 2, user, prevPassword));
	}
	
	public void loadMatrixPasswordForChanging() {
		HealthGenius.myMobileManager.password = "";
		HealthGenius.myApp.setContentView(R.layout.password);
		((TextView) HealthGenius.myApp.findViewById(R.id.startText)).setText(HealthGenius.myLanguageManager.PSW_REQUEST_OLD);
		ImageButton add = (ImageButton) HealthGenius.myApp.findViewById(R.id.add);
		add.setVisibility(View.GONE);
		final FrameLayout board = (FrameLayout) HealthGenius.myApp.findViewById(R.id.passLayout);
		board.addView(new PasswordView(HealthGenius.myApp, 3, HealthGenius.myMobileManager.user));
	}
	
	public void loadNewPasswordForChanging(User user, String prevPassword) {
		HealthGenius.myMobileManager.password = "";
		HealthGenius.myApp.setContentView(R.layout.password);
		((TextView) HealthGenius.myApp.findViewById(R.id.startText)).setText(HealthGenius.myLanguageManager.PSW_REQUEST_NEW);
		ImageButton add = (ImageButton) HealthGenius.myApp.findViewById(R.id.add);
		add.setVisibility(View.GONE);
		final FrameLayout board = (FrameLayout) HealthGenius.myApp.findViewById(R.id.passLayout);
		board.addView(new PasswordView(HealthGenius.myApp, 4, user, prevPassword));
	}
	
	public void loadRepeatPasswordForChanging(User user, String prevPassword) {
		HealthGenius.myMobileManager.password = "";
		HealthGenius.myApp.setContentView(R.layout.password);
		((TextView) HealthGenius.myApp.findViewById(R.id.startText)).setText(HealthGenius.myLanguageManager.PSW_REQUEST_NEW_REPEAT);
		ImageButton add = (ImageButton) HealthGenius.myApp.findViewById(R.id.add);
		add.setVisibility(View.GONE);
		final FrameLayout board = (FrameLayout) HealthGenius.myApp.findViewById(R.id.passLayout);
		board.addView(new PasswordView(HealthGenius.myApp, 5, user, prevPassword));
	}
	
	/**
	 * Load the info about the logged user.
	 */
	public void loadUserInfoScreen(final User user, final boolean register) {
		myUIManager.state = UIManager.UI_STATE_USERINFO;
		HealthGenius.myMobileManager.identified = true;
		HealthGenius.myApp.setContentView(R.layout.info);
		TextView text = (TextView) HealthGenius.myApp.findViewById(R.id.textInfo);
		text.append(HealthGenius.myLanguageManager.PSW_INFO_USER + user.getName() + HealthGenius.myLanguageManager.PSW_INFO_REGISTERED);
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
							if (HealthGenius.myMobileManager.register()) {
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
										myUIManager.UImisc.loadInfoPopupWithoutExiting(HealthGenius.myLanguageManager.CONNECTION_REGISTERING);
										animationFrame = (ImageView) HealthGenius.myApp.findViewById(R.id.popupImage);
										animationFrame.setVisibility(View.VISIBLE);
										animationFrame.setBackgroundResource(R.drawable.loading);
										animation = (AnimationDrawable) animationFrame.getBackground();
										animation.start();
										break;
									case 1:
										animationFrame = (ImageView) HealthGenius.myApp.findViewById(R.id.popupImage);
										animation = (AnimationDrawable) animationFrame.getBackground();
										animation.stop();
										animationFrame.setVisibility(View.GONE);
										myUIManager.UImisc.removeInfoPopup();
										loadAdditionalDataScreen(false);
										break;
									case 2:
										animationFrame = (ImageView) HealthGenius.myApp.findViewById(R.id.popupImage);
										animation = (AnimationDrawable) animationFrame.getBackground();
										animation.stop();
										animationFrame.setVisibility(View.GONE);
										myUIManager.UImisc.loadInfoPopup(HealthGenius.myLanguageManager.CONNECTION_FAILED);
										myUIManager.loadBoxClosed(true, true);
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
							if (HealthGenius.myMobileManager.login()) {
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
										myUIManager.UImisc.loadInfoPopupWithoutExiting(HealthGenius.myLanguageManager.CONNECTION_CONNECTING);
										animationFrame = (ImageView) HealthGenius.myApp.findViewById(R.id.popupImage);
										animationFrame.setVisibility(View.VISIBLE);
										animationFrame.setBackgroundResource(R.drawable.loading);
										animation = (AnimationDrawable) animationFrame.getBackground();
										animation.start();
										break;
									case 1:
										animationFrame = (ImageView) HealthGenius.myApp.findViewById(R.id.popupImage);
										animation = (AnimationDrawable) animationFrame.getBackground();
										animation.stop();
										animationFrame.setVisibility(View.GONE);
										myUIManager.UImisc.removeInfoPopup();
										loadAdditionalDataScreen(false);
										break;
									case 2:
										animationFrame = (ImageView) HealthGenius.myApp.findViewById(R.id.popupImage);
										animation = (AnimationDrawable) animationFrame.getBackground();
										animation.stop();
										animationFrame.setVisibility(View.GONE);
										myUIManager.UImisc.loadInfoPopup(HealthGenius.myLanguageManager.CONNECTION_FAILED);
										myUIManager.loadBoxClosed(true, true);
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
}
