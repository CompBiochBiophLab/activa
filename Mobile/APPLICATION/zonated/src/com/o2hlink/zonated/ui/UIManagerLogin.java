package com.o2hlink.zonated.ui;

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
import com.o2hlink.zonated.R;
import com.o2hlink.zonated.Zonated;
import com.o2hlink.zonated.ZonatedConfig;
import com.o2hlink.zonated.ZonatedUtil;
import com.o2hlink.zonated.background.MainThread;
import com.o2hlink.zonated.background.UserCheckout;
import com.o2hlink.zonated.background.UserCheckoutForRegister;
import com.o2hlink.zonated.connection.ProtocolManager;
import com.o2hlink.zonated.data.contacts.ContactsManager;
import com.o2hlink.zonated.data.questionnaire.QuestControlManager;
import com.o2hlink.zonated.mobile.MobileManager;
import com.o2hlink.zonated.mobile.User;

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
		Zonated.myApp.setContentView(R.layout.init);
		CountDownTimer timer = new CountDownTimer(1000,1000) {
			@Override
			public void onTick(long millisUntilFinished) {
			}
			@Override
			public void onFinish() {
		        Zonated.myLanguageManager = Zonated.setLanguage(Zonated.myApp.getResources().getConfiguration().locale);
		        Zonated.rootFile = Environment.getExternalStorageDirectory();
		        Zonated.myTelephonyManager = (TelephonyManager) Zonated.myApp.getSystemService(Context.TELEPHONY_SERVICE);
		        Zonated.myMobileManager = MobileManager.getInstance(); 
		        Zonated.myProtocolManager = ProtocolManager.getInstance();
		        Zonated.myMainBackgroundThread = new MainThread();
		        Zonated.myQuestControlManager = QuestControlManager.getInstance();
		        Zonated.myContactsManager = ContactsManager.getInstance();
		        if (Zonated.myMobileManager.users.isEmpty()) 
					loadInitialProcessScreen();
				else 
					loadLoginScreen();
			}
		};
		timer.start();
	}
	
	public void loadInitialProcessScreen() {
		int width = Zonated.myApp.getWindowManager().getDefaultDisplay().getWidth();
		myUIManager.state = UIManager.UI_STATE_USERINFO;
		Zonated.myApp.setContentView(R.layout.empty);
		LinearLayout block = (LinearLayout) Zonated.myApp.findViewById(R.id.info);
		TextView title = new TextView(Zonated.myApp);
		title.setTextColor(Color.BLACK);
		title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		title.setTypeface(Typeface.DEFAULT_BOLD);
		title.setText(Zonated.myApp.getResources().getString(R.string.app_name) + " " + Zonated.myApp.getResources().getString(R.string.app_version) + "\n");
		TextView text = new TextView(Zonated.myApp);
		text.setTextColor(Color.BLACK);
		text.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
		text.setTypeface(Typeface.SANS_SERIF);
		text.setText(Zonated.myApp.getResources().getString(R.string.registerwarning));
		RelativeLayout rel = new RelativeLayout(Zonated.myApp);
		LinearLayout.LayoutParams par = new LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		par.setMargins(0, 10, 0, 10);
		rel.setLayoutParams(par);
		ImageButton create = new ImageButton(Zonated.myApp);
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width*4/10, android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
		params.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
		create.setLayoutParams(params);
		create.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				loadRegisterScreen(0);
			}
		});
		create.setBackgroundResource(R.drawable.iconbg);
		create.setImageResource(Zonated.myLanguageManager.createAccFile);
		ImageButton enter = new ImageButton(Zonated.myApp);
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
		enter.setImageResource(Zonated.myLanguageManager.accessAccFile);
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
		Zonated.myMobileManager.password = "";
		myUIManager.state = UIManager.UI_STATE_LOGIN;
		Zonated.myApp.setContentView(R.layout.password);
		((TextView) Zonated.myApp.findViewById(R.id.startText)).setText(Zonated.myLanguageManager.PSW_REQUEST);
		final FrameLayout board = (FrameLayout) Zonated.myApp.findViewById(R.id.passLayout);
		board.addView(new PasswordView(Zonated.myApp, 0));
		ImageButton add = (ImageButton) Zonated.myApp.findViewById(R.id.add);
		add.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				loadInitialProcessScreen();
			}
		});
	}
	
	public void loadCheckUserScreen() {
		myUIManager.state = UIManager.UI_STATE_CHECKING;
		Zonated.myApp.setContentView(R.layout.register);
		((TextView) Zonated.myApp.findViewById(R.id.startText)).setText(Zonated.myLanguageManager.PSW_REG_REQUEST);
		((TextView) Zonated.myApp.findViewById(R.id.requestText)).setText(Zonated.myLanguageManager.PSW_REG_ENTERDATA);
		((TextView) Zonated.myApp.findViewById(R.id.requestUser)).setText(Zonated.myLanguageManager.PSW_REG_USERNAME);
		((TextView) Zonated.myApp.findViewById(R.id.requestPass)).setText(Zonated.myLanguageManager.PSW_REG_PASSWORD);
		((TextView) Zonated.myApp.findViewById(R.id.requestPassagain)).setVisibility(View.INVISIBLE);
		final EditText username = (EditText) Zonated.myApp.findViewById(R.id.loginText);
		final EditText password = (EditText) Zonated.myApp.findViewById(R.id.passwordText);
		final EditText passwordAgain = (EditText) Zonated.myApp.findViewById(R.id.passwordTextagain);
		password.setOnKeyListener(new OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_ENTER) {
					InputMethodManager imm = (InputMethodManager)Zonated.myApp.getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(passwordAgain.getWindowToken(), 0);
				}
				return false;
			}
		});
		passwordAgain.setVisibility(View.INVISIBLE);
		ImageButton ok = (ImageButton) Zonated.myApp.findViewById(R.id.ok);
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
					Zonated.myMobileManager.user = new User(userText, passText);
					Zonated.myMobileManager.user.setCreated(false);
					Thread trd = new Thread(new UserCheckout());
					trd.start();
				} catch (Exception e) {
					myUIManager.UImisc.loadInfoPopup(Zonated.myLanguageManager.PSW_REG_BADDATA);
				}
			}
		});
	}
	
	public void loadRegisterScreen(final int usertype) {
		myUIManager.state = UIManager.UI_STATE_REGISTER;
		Zonated.myApp.setContentView(R.layout.register);
		((TextView) Zonated.myApp.findViewById(R.id.startText)).setText(Zonated.myLanguageManager.PSW_REG_TITLE);
		((TextView) Zonated.myApp.findViewById(R.id.requestText)).setText(Zonated.myLanguageManager.PSW_REG_ENTERDATA);
		((TextView) Zonated.myApp.findViewById(R.id.requestUser)).setText(Zonated.myLanguageManager.PSW_REG_USERNAME);
		((TextView) Zonated.myApp.findViewById(R.id.requestPass)).setText(Zonated.myLanguageManager.PSW_REG_PASSWORD);
		((TextView) Zonated.myApp.findViewById(R.id.requestPassagain)).setText(Zonated.myLanguageManager.PSW_REG_PASSWORD_AGAIN);
		((RelativeLayout) Zonated.myApp.findViewById(R.id.userterms)).setVisibility(View.VISIBLE);
		Spanned userterms = Html.fromHtml(Zonated.myLanguageManager.PSW_REG_ACCEPT + "<a href=\"" + ZonatedConfig.ACTIV8TERMSANDCONDS_URL + "\">" + Zonated.myLanguageManager.MAIN_TERMSANDCONDS + "</a>");
		((TextView) Zonated.myApp.findViewById(R.id.usertermstext)).setText(userterms);
		((TextView) Zonated.myApp.findViewById(R.id.usertermstext)).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
		    	Intent in = new Intent(Intent.ACTION_VIEW);
				in.setData(Uri.parse(ZonatedConfig.ACTIV8TERMSANDCONDS_URL));
				Zonated.myApp.startActivity(in);
			}
		});
		final EditText username = (EditText) Zonated.myApp.findViewById(R.id.loginText);
		final EditText password = (EditText) Zonated.myApp.findViewById(R.id.passwordText);
		final EditText passwordAgain = (EditText) Zonated.myApp.findViewById(R.id.passwordTextagain);
		passwordAgain.setOnKeyListener(new OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_ENTER) {
					InputMethodManager imm = (InputMethodManager)Zonated.myApp.getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(passwordAgain.getWindowToken(), 0);
				}
				return false;
			}
		});
		ImageButton ok = (ImageButton) Zonated.myApp.findViewById(R.id.ok);
		ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					CheckBox usertermscheck = (CheckBox) Zonated.myApp.findViewById(R.id.usertermscheck);
					if (!usertermscheck.isChecked()) {
						myUIManager.UImisc.loadInfoPopup(Zonated.myLanguageManager.PSW_REG_ACCEPTTERMS);
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
					Zonated.myMobileManager.user = new User(userText, passText);
					Zonated.myMobileManager.user.setType(usertype);
					Zonated.myMobileManager.user.setCreated(false);
					Thread trd = new Thread(new UserCheckoutForRegister(usertype));
					trd.start();
				} catch (Exception e) {
					myUIManager.UImisc.loadInfoPopup(Zonated.myLanguageManager.PSW_REG_BADDATA);
				}
			}
		});
	}
	
	public void loadRegisterDataScreen(final int type) {
		myUIManager.state = UIManager.UI_STATE_REGISTERDATA;
		Zonated.myApp.setContentView(R.layout.registerdata);
		((TextView) Zonated.myApp.findViewById(R.id.startText)).setText(Zonated.myLanguageManager.PSW_REG_TITLE);
		((TextView) Zonated.myApp.findViewById(R.id.requestText)).setText(Zonated.myLanguageManager.PSW_REG_DATAREQUEST);
		((TextView) Zonated.myApp.findViewById(R.id.requestFirst)).setText(Zonated.myLanguageManager.PSW_REG_FIRSTNAME);
		((TextView) Zonated.myApp.findViewById(R.id.requestLast)).setText(Zonated.myLanguageManager.PSW_REG_LASTNAME);
		((TextView) Zonated.myApp.findViewById(R.id.requestDate)).setText(Zonated.myLanguageManager.PSW_REG_DATE);
		((TextView) Zonated.myApp.findViewById(R.id.requestEmail)).setText("E-Mail: ");
		((TextView) Zonated.myApp.findViewById(R.id.requestRepeatEmail)).setText(Zonated.myLanguageManager.PSW_REG_EMAILREPEAT);
		((TextView) Zonated.myApp.findViewById(R.id.sexrequest)).setText(Zonated.myLanguageManager.PSW_REG_SEX);
		((TextView) Zonated.myApp.findViewById(R.id.countryrequest)).setText(Zonated.myLanguageManager.PSW_REG_COUNTRY);
		final EditText first = (EditText) Zonated.myApp.findViewById(R.id.firstText);
		final EditText last = (EditText) Zonated.myApp.findViewById(R.id.lastText);
		final EditText mail = (EditText) Zonated.myApp.findViewById(R.id.emailText);
		final EditText repeatmail = (EditText) Zonated.myApp.findViewById(R.id.repeatEmailText);
		repeatmail.setOnKeyListener(new OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_ENTER) {
					InputMethodManager imm = (InputMethodManager)Zonated.myApp.getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(repeatmail.getWindowToken(), 0);
				}
				return false;
			}
		});
		final DatePicker date = (DatePicker) Zonated.myApp.findViewById(R.id.birthdate);
		final Spinner sex = (Spinner) Zonated.myApp.findViewById(R.id.sex);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(Zonated.myApp, R.array.sextype, android.R.layout.simple_spinner_item);
	    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    sex.setAdapter(adapter);
	    sex.setSelection(0);
		final Country[] countries = Country.values();
		final Spinner country = (Spinner) Zonated.myApp.findViewById(R.id.country);
		ArrayList<String> countrystrings = new ArrayList<String>();
		for (Country ctr : countries) {
			countrystrings.add(ctr.getName());
		}
		ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(Zonated.myApp, android.R.layout.simple_spinner_item, countrystrings);
	    adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    country.setAdapter(adapter3);
	    country.setSelection(0);
		ImageButton ok = (ImageButton) Zonated.myApp.findViewById(R.id.ok);
		ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try{
					if (first.getText().toString().equals("")) throw new Exception();
					if (last.getText().toString().equals("")) throw new Exception();
					if (ZonatedUtil.isAMail(mail.getText().toString())) throw new Exception();
					Zonated.myMobileManager.user.setFirstname(first.getText().toString());
					Zonated.myMobileManager.user.setLastname(last.getText().toString());
					if (countries != null) Zonated.myMobileManager.user.setCountry(countries[country.getSelectedItemPosition()]);
					Zonated.myMobileManager.user.setMail(mail.getText().toString());
					Date birthdate = new Date(0);
					birthdate.setYear(date.getYear() - 1900);
					birthdate.setMonth(date.getMonth());
					birthdate.setDate(date.getDayOfMonth());
					Date minbirthdate = new Date();
					minbirthdate.setMonth(minbirthdate.getMonth() - 1);
					if (mail.getText().equals(repeatmail.getText())) {
						myUIManager.UImisc.loadInfoPopup(Zonated.myLanguageManager.PSW_REG_EMAILDISMATCH);
						return;
					}
					if (birthdate.after(minbirthdate)) {
						myUIManager.UImisc.loadInfoPopup(Zonated.myLanguageManager.PSW_REG_MINBIRTH);
						return;
					}
					Zonated.myMobileManager.user.setBirthdate(birthdate);
					switch (sex.getSelectedItemPosition()) {
						case 0:
							Zonated.myMobileManager.user.setSex(Sex.NOT_SPECIFIED);
							break;
						case 1:
							Zonated.myMobileManager.user.setSex(Sex.MALE);
							break;
						default:
							Zonated.myMobileManager.user.setSex(Sex.FEMALE);
							break;
					}
					Zonated.myMobileManager.user.setType(type);
					loadPasswordExplanation();
				} catch (Exception e) {
					myUIManager.UImisc.loadInfoPopup(Zonated.myLanguageManager.PSW_REG_BADDATA);
				} 
			}
		});
	}
	
	public void loadPasswordExplanation() {
		Zonated.myApp.setContentView(R.layout.info);
		((TextView) Zonated.myApp.findViewById(R.id.textInfo)).setText(Zonated.myApp.getResources().getString(R.string.passwordwarning));
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
		Zonated.myApp.setContentView(R.layout.registerwebdata);
		final Spinner language = (Spinner) Zonated.myApp.findViewById(R.id.language);
		final ArrayList<TimeZone> timezonesviewed = new ArrayList<TimeZone>();
		((TextView)Zonated.myApp.findViewById(R.id.startText)).setText(Zonated.myApp.getResources().getString(R.string.registerewebdatastart));
		((TextView)Zonated.myApp.findViewById(R.id.languagerequest)).setText(Zonated.myApp.getResources().getString(R.string.languagerequest));
		((TextView)Zonated.myApp.findViewById(R.id.timezonerequest)).setText(Zonated.myApp.getResources().getString(R.string.timezonerequest));
		((TextView)Zonated.myApp.findViewById(R.id.privacyrequest)).setText(Zonated.myApp.getResources().getString(R.string.privacyrequest));
		((TextView) Zonated.myApp.findViewById(R.id.countryrequest)).setText(Zonated.myLanguageManager.PSW_REG_COUNTRY);
		final com.o2hlink.activ8.common.entity.Language[] languages = com.o2hlink.activ8.common.entity.Language.values();
		ArrayList<String> languagestrings = new ArrayList<String>();
		int j = 0;
		int selected = 0;
		for (com.o2hlink.activ8.common.entity.Language ctr : languages) {
			languagestrings.add(ctr.getName());
			if ((Zonated.myMobileManager.userForServices.getLanguage() != null)&&(ctr.getName().equals(Zonated.myMobileManager.userForServices.getLanguage().getName()))) selected = j;
			j++;
		}
		ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(Zonated.myApp, android.R.layout.simple_spinner_item, languagestrings);
	    adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    language.setAdapter(adapter3);
	    language.setSelection(selected);
	    final Country[] countries = Country.values();
		final Spinner country = (Spinner) Zonated.myApp.findViewById(R.id.country);
		ArrayList<String> countrystrings = new ArrayList<String>();
		j = 0;
		selected = 0;
		for (Country ctr : countries) {
			String thecountry = ctr.getName();
			String mycountry;
			try {
				mycountry = Zonated.myMobileManager.userForServices.getCountry().getName();
			} catch (Exception e) {
				mycountry = null;
			}
			countrystrings.add(thecountry);
			if ((mycountry != null)&&(thecountry.equalsIgnoreCase(mycountry))) selected = j;
			j++;
		}
		ArrayAdapter<String> adapter5 = new ArrayAdapter<String>(Zonated.myApp, android.R.layout.simple_spinner_item, countrystrings);
	    adapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    country.setAdapter(adapter5);
	    country.setSelection(0);
	    if (countries != null) country.setSelection(selected);
	    country.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected (AdapterView<?> parent, View view, int position, long id) {
			    final Spinner timezone = (Spinner) Zonated.myApp.findViewById(R.id.timezone);
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
						mytimezone = Zonated.myMobileManager.userForServices.getTimeZone().getName();
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
							mytimezone = Zonated.myMobileManager.userForServices.getTimeZone().getName();
						} catch (Exception e) {
							mytimezone = null;
						}
						timezonestrings.add(thetimezone);
						timezonesviewed.add(tmz);
						if ((mytimezone != null)&&(thetimezone.equalsIgnoreCase(mytimezone))) selected = j;
						j++;
					}
				}
				ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(Zonated.myApp, android.R.layout.simple_spinner_item, timezonestrings);
			    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			    timezone.setAdapter(adapter2);
			    timezone.setSelection(selected);
			}
			@Override
			public void onNothingSelected (AdapterView<?> parent) {
			    final Spinner timezone = (Spinner) Zonated.myApp.findViewById(R.id.timezone);
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
						mytimezone = Zonated.myMobileManager.userForServices.getTimeZone().getName();
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
							mytimezone = Zonated.myMobileManager.userForServices.getTimeZone().getName();
						} catch (Exception e) {
							mytimezone = null;
						}
						timezonestrings.add(thetimezone);
						timezonesviewed.add(tmz);
						if ((mytimezone != null)&&(thetimezone.equalsIgnoreCase(mytimezone))) selected = j;
						j++;
					}
				}
				ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(Zonated.myApp, android.R.layout.simple_spinner_item, timezonestrings);
			    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			    timezone.setAdapter(adapter2);
			    timezone.setSelection(selected);
			}
		});
	    final Spinner timezone = (Spinner) Zonated.myApp.findViewById(R.id.timezone);
		final TimeZone[] timezones = TimeZone.values();
		final ArrayList<String> timezonestrings = new ArrayList<String>();
		j = 0;
		selected = 0;
		timezonesviewed.clear();
		for (TimeZone tmz : timezones) {
			Country thecountry = Zonated.myMobileManager.userForServices.getCountry();
			String thetimezone = tmz.getName();
			String mytimezone;
			try {
				mytimezone = Zonated.myMobileManager.userForServices.getTimeZone().getName();
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
					mytimezone = Zonated.myMobileManager.userForServices.getTimeZone().getName();
				} catch (Exception e) {
					mytimezone = null;
				}
				timezonestrings.add(thetimezone);
				timezonesviewed.add(tmz);
				if ((mytimezone != null)&&(thetimezone.equalsIgnoreCase(mytimezone))) selected = j;
				j++;
			}
		}
		ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(Zonated.myApp, android.R.layout.simple_spinner_item, timezonestrings);
	    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    timezone.setAdapter(adapter2);
	    timezone.setSelection(selected);
	    String[] privacylevel = Zonated.myApp.getResources().getStringArray(R.array.privacylevel);
	    ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(Zonated.myApp, android.R.layout.simple_spinner_item, privacylevel);
		final Spinner privacy = (Spinner) Zonated.myApp.findViewById(R.id.privacy);
		if (Zonated.myMobileManager.userForServices.getPrivacyLevel() == null) privacy.setSelection(0);
		else if (Zonated.myMobileManager.userForServices.getPrivacyLevel().equals(VisibilityLevel.PUBLIC_VIEW)) privacy.setSelection(0);
		else if (Zonated.myMobileManager.userForServices.getPrivacyLevel().equals(VisibilityLevel.PRIVATE)) privacy.setSelection(1);
		else privacy.setSelection(0);
		((ImageButton)Zonated.myApp.findViewById(R.id.ok)).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Zonated.myMobileManager.userForServices.setTimeZone(timezonesviewed.get(timezone.getSelectedItemPosition()));
				Zonated.myMobileManager.userForServices.setLanguage(languages[language.getSelectedItemPosition()]);
				if (countries != null) {
					Zonated.myMobileManager.userForServices.setCountry(countries[country.getSelectedItemPosition()]);
					Zonated.myMobileManager.user.setCountry(countries[country.getSelectedItemPosition()]);
				}
				if (privacy.getSelectedItemPosition() == 0) Zonated.myMobileManager.userForServices.setPrivacyLevel(VisibilityLevel.PUBLIC_VIEW);
				else Zonated.myMobileManager.userForServices.setPrivacyLevel(VisibilityLevel.PRIVATE);
				Zonated.myMobileManager.updateUserData();
				if (returntoopt) myUIManager.UIoptions.showOptions();
				else myUIManager.UIquest.loadSharedQuestionnaires(true);
			}
		});
	}
	
	public void loadMatrixPasswordForRegistering() {
		Zonated.myMobileManager.password = "";
		Zonated.myApp.setContentView(R.layout.password);
		((TextView) Zonated.myApp.findViewById(R.id.startText)).setText(Zonated.myLanguageManager.PSW_REQUEST);
		ImageButton add = (ImageButton) Zonated.myApp.findViewById(R.id.add);
		add.setVisibility(View.GONE);
		final FrameLayout board = (FrameLayout) Zonated.myApp.findViewById(R.id.passLayout);
		board.addView(new PasswordView(Zonated.myApp, 1, Zonated.myMobileManager.user));
	}
	
	public void loadRepeatPassword(User user, String prevPassword) {
		Zonated.myMobileManager.password = "";
		Zonated.myApp.setContentView(R.layout.password);
		((TextView) Zonated.myApp.findViewById(R.id.startText)).setText(Zonated.myLanguageManager.PSW_REQUEST_REPEAT);
		ImageButton add = (ImageButton) Zonated.myApp.findViewById(R.id.add);
		add.setVisibility(View.GONE);
		final FrameLayout board = (FrameLayout) Zonated.myApp.findViewById(R.id.passLayout);
		board.addView(new PasswordView(Zonated.myApp, 2, user, prevPassword));
	}
	
	public void loadMatrixPasswordForChanging() {
		Zonated.myMobileManager.password = "";
		Zonated.myApp.setContentView(R.layout.password);
		((TextView) Zonated.myApp.findViewById(R.id.startText)).setText(Zonated.myLanguageManager.PSW_REQUEST_OLD);
		ImageButton add = (ImageButton) Zonated.myApp.findViewById(R.id.add);
		add.setVisibility(View.GONE);
		final FrameLayout board = (FrameLayout) Zonated.myApp.findViewById(R.id.passLayout);
		board.addView(new PasswordView(Zonated.myApp, 3, Zonated.myMobileManager.user));
	}
	
	public void loadNewPasswordForChanging(User user, String prevPassword) {
		Zonated.myMobileManager.password = "";
		Zonated.myApp.setContentView(R.layout.password);
		((TextView) Zonated.myApp.findViewById(R.id.startText)).setText(Zonated.myLanguageManager.PSW_REQUEST_NEW);
		ImageButton add = (ImageButton) Zonated.myApp.findViewById(R.id.add);
		add.setVisibility(View.GONE);
		final FrameLayout board = (FrameLayout) Zonated.myApp.findViewById(R.id.passLayout);
		board.addView(new PasswordView(Zonated.myApp, 4, user, prevPassword));
	}
	
	public void loadRepeatPasswordForChanging(User user, String prevPassword) {
		Zonated.myMobileManager.password = "";
		Zonated.myApp.setContentView(R.layout.password);
		((TextView) Zonated.myApp.findViewById(R.id.startText)).setText(Zonated.myLanguageManager.PSW_REQUEST_NEW_REPEAT);
		ImageButton add = (ImageButton) Zonated.myApp.findViewById(R.id.add);
		add.setVisibility(View.GONE);
		final FrameLayout board = (FrameLayout) Zonated.myApp.findViewById(R.id.passLayout);
		board.addView(new PasswordView(Zonated.myApp, 5, user, prevPassword));
	}
	
	/**
	 * Load the info about the logged user.
	 */
	public void loadUserInfoScreen(final User user, final boolean register) {
		myUIManager.state = UIManager.UI_STATE_USERINFO;
		Zonated.myMobileManager.identified = true;
		Zonated.myApp.setContentView(R.layout.info);
		TextView text = (TextView) Zonated.myApp.findViewById(R.id.textInfo);
		text.append(Zonated.myLanguageManager.PSW_INFO_USER + user.getName() + Zonated.myLanguageManager.PSW_INFO_REGISTERED);
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
							if (Zonated.myMobileManager.register()) {
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
										myUIManager.UImisc.loadInfoPopupWithoutExiting(Zonated.myLanguageManager.CONNECTION_REGISTERING);
										animationFrame = (ImageView) Zonated.myApp.findViewById(R.id.popupImage);
										animationFrame.setVisibility(View.VISIBLE);
										animationFrame.setBackgroundResource(R.drawable.loading);
										animation = (AnimationDrawable) animationFrame.getBackground();
										animation.start();
										break;
									case 1:
										animationFrame = (ImageView) Zonated.myApp.findViewById(R.id.popupImage);
										animation = (AnimationDrawable) animationFrame.getBackground();
										animation.stop();
										animationFrame.setVisibility(View.GONE);
										myUIManager.UImisc.removeInfoPopup();
										loadAdditionalDataScreen(false);
										break;
									case 2:
										animationFrame = (ImageView) Zonated.myApp.findViewById(R.id.popupImage);
										animation = (AnimationDrawable) animationFrame.getBackground();
										animation.stop();
										animationFrame.setVisibility(View.GONE);
										myUIManager.UImisc.loadInfoPopup(Zonated.myLanguageManager.CONNECTION_FAILED);
										myUIManager.UIquest.loadSharedQuestionnaires(true);
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
							if (Zonated.myMobileManager.login()) {
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
										myUIManager.UImisc.loadInfoPopupWithoutExiting(Zonated.myLanguageManager.CONNECTION_CONNECTING);
										animationFrame = (ImageView) Zonated.myApp.findViewById(R.id.popupImage);
										animationFrame.setVisibility(View.VISIBLE);
										animationFrame.setBackgroundResource(R.drawable.loading);
										animation = (AnimationDrawable) animationFrame.getBackground();
										animation.start();
										break;
									case 1:
										animationFrame = (ImageView) Zonated.myApp.findViewById(R.id.popupImage);
										animation = (AnimationDrawable) animationFrame.getBackground();
										animation.stop();
										animationFrame.setVisibility(View.GONE);
										myUIManager.UImisc.removeInfoPopup();
										loadAdditionalDataScreen(false);
										break;
									case 2:
										animationFrame = (ImageView) Zonated.myApp.findViewById(R.id.popupImage);
										animation = (AnimationDrawable) animationFrame.getBackground();
										animation.stop();
										animationFrame.setVisibility(View.GONE);
										myUIManager.UImisc.loadInfoPopup(Zonated.myLanguageManager.CONNECTION_FAILED);
										myUIManager.UIquest.loadSharedQuestionnaires(true);
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
