package com.o2hlink.healthgenius.ui;

import java.util.ArrayList;
import java.util.Date;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TableLayout.LayoutParams;

import com.o2hlink.activ8.common.entity.Country;
import com.o2hlink.activ8.common.entity.Sex;
import com.o2hlink.healthgenius.HealthGenius;
import com.o2hlink.healthgenius.HealthGeniusUtil;
import com.o2hlink.healthgenius.R;
import com.o2hlink.healthgenius.mobile.User;

public class UIManagerOptions {
	
	public UIManager myUIManager;
	
	public UIManagerOptions(UIManager ui) {
		myUIManager = ui;
	}
	
	public void showOptions() {
		myUIManager.state = UIManager.UI_STATE_OPTIONS;
		int realwidth = HealthGenius.myApp.getResources().getDisplayMetrics().widthPixels;
		HealthGenius.myApp.setContentView(R.layout.list);
		((TextView) HealthGenius.myApp.findViewById(R.id.startText)).setText(HealthGenius.myLanguageManager.OPTIONS_TITLE);
		TableLayout listing = (TableLayout)HealthGenius.myApp.findViewById(R.id.listing);
		
		TableRow buttonLayout = new TableRow(HealthGenius.myApp);
		buttonLayout.setOrientation(TableRow.HORIZONTAL);
		buttonLayout.setGravity(Gravity.CENTER_VERTICAL);
		buttonLayout.setLayoutParams(new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));			
		ImageButton button = new ImageButton(HealthGenius.myApp);
		button.setBackgroundResource(R.drawable.iconbg);
		button.setImageResource(R.drawable.profile);
		TextView text = new TextView(HealthGenius.myApp);
		text.setLayoutParams(new android.widget.TableRow.LayoutParams(realwidth - 60, android.widget.TableRow.LayoutParams.WRAP_CONTENT));
		text.setPadding(10, 0, 10, 0);
		text.append(HealthGenius.myLanguageManager.OPTIONS_PROFILE);
		text.setTextSize((float) 20);
		text.setTextColor(Color.BLACK);
		text.setTypeface(Typeface.SANS_SERIF);	
		OnClickListener listener = new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (!HealthGenius.myMobileManager.identified) {
					myUIManager.UImisc.loadInfoPopupLong(HealthGenius.myLanguageManager.OPTIONS_USERNOTCONNECTED);
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
		View separator = new View(HealthGenius.myApp);
		separator.setLayoutParams(new LayoutParams(android.view.ViewGroup.LayoutParams.FILL_PARENT, 1));
		separator.setBackgroundColor(Color.BLACK);
		listing.addView(separator);
		
		buttonLayout = new TableRow(HealthGenius.myApp);
		buttonLayout.setOrientation(TableRow.HORIZONTAL);
		buttonLayout.setGravity(Gravity.CENTER_VERTICAL);
		buttonLayout.setLayoutParams(new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));			
		button = new ImageButton(HealthGenius.myApp);
		button.setBackgroundResource(R.drawable.iconbg);
		button.setImageResource(R.drawable.profile);
		text = new TextView(HealthGenius.myApp);
		text.setLayoutParams(new android.widget.TableRow.LayoutParams(realwidth - 60, android.widget.TableRow.LayoutParams.WRAP_CONTENT));
		text.setPadding(10, 0, 10, 0);
		text.append(HealthGenius.myLanguageManager.OPTIONS_WEB);
		text.setTextSize((float) 20);
		text.setTextColor(Color.BLACK);
		text.setTypeface(Typeface.SANS_SERIF);	
		listener = new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (!HealthGenius.myMobileManager.identified) {
					myUIManager.UImisc.loadInfoPopupLong(HealthGenius.myLanguageManager.OPTIONS_USERNOTCONNECTED);
					return;
				}
				//TODO
				myUIManager.UIlogin.loadAdditionalDataScreen(true);
			}
		};
		buttonLayout.setOnClickListener(listener);
		button.setOnClickListener(listener);
		text.setOnClickListener(listener);
		buttonLayout.addView(button, 60, 60);
		buttonLayout.addView(text);
		listing.addView(buttonLayout);
		separator = new View(HealthGenius.myApp);
		separator.setLayoutParams(new LayoutParams(android.view.ViewGroup.LayoutParams.FILL_PARENT, 1));
		separator.setBackgroundColor(Color.BLACK);
		listing.addView(separator);
		
		buttonLayout = new TableRow(HealthGenius.myApp);
		buttonLayout.setOrientation(TableRow.HORIZONTAL);
		buttonLayout.setGravity(Gravity.CENTER_VERTICAL);
		buttonLayout.setLayoutParams(new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));			
		button = new ImageButton(HealthGenius.myApp);
		button.setBackgroundResource(R.drawable.iconbg);
		button.setImageResource(R.drawable.password);
		text = new TextView(HealthGenius.myApp);
		text.setLayoutParams(new android.widget.TableRow.LayoutParams(realwidth - 60, android.widget.TableRow.LayoutParams.WRAP_CONTENT));
		text.setPadding(10, 0, 10, 0);
		text.append(HealthGenius.myLanguageManager.OPTIONS_USERPASS);
		text.setTextSize((float) 20);
		text.setTextColor(Color.BLACK);
		text.setTypeface(Typeface.SANS_SERIF);	
		listener = new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (!HealthGenius.myMobileManager.identified) {
					myUIManager.UImisc.loadInfoPopupLong(HealthGenius.myLanguageManager.OPTIONS_USERNOTCONNECTED);
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
		separator = new View(HealthGenius.myApp);
		separator.setLayoutParams(new LayoutParams(android.view.ViewGroup.LayoutParams.FILL_PARENT, 1));
		separator.setBackgroundColor(Color.BLACK);
		listing.addView(separator);
		
		buttonLayout = new TableRow(HealthGenius.myApp);
		buttonLayout.setOrientation(TableRow.HORIZONTAL);
		buttonLayout.setGravity(Gravity.CENTER_VERTICAL);
		buttonLayout.setLayoutParams(new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));			
		button = new ImageButton(HealthGenius.myApp);
		button.setBackgroundResource(R.drawable.iconbg);
		button.setImageResource(R.drawable.password);
		text = new TextView(HealthGenius.myApp);
		text.setLayoutParams(new android.widget.TableRow.LayoutParams(realwidth - 60, android.widget.TableRow.LayoutParams.WRAP_CONTENT));
		text.setPadding(10, 0, 10, 0);
		text.append(HealthGenius.myLanguageManager.OPTIONS_DRAWPASS);
		text.setTextSize((float) 20);
		text.setTextColor(Color.BLACK);
		text.setTypeface(Typeface.SANS_SERIF);	
		listener = new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (!HealthGenius.myMobileManager.identified) {
					myUIManager.UImisc.loadInfoPopupLong(HealthGenius.myLanguageManager.OPTIONS_USERNOTCONNECTED);
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
		separator = new View(HealthGenius.myApp);
		separator.setLayoutParams(new LayoutParams(android.view.ViewGroup.LayoutParams.FILL_PARENT, 1));
		separator.setBackgroundColor(Color.BLACK);
		listing.addView(separator);

		ImageButton back = (ImageButton) HealthGenius.myApp.findViewById(R.id.previous);
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				HealthGenius.myUIManager.loadBoxClosed(false, true);
			}
		});
	}
	
	public void loadDataScreenForChanging() {
		HealthGenius.myApp.setContentView(R.layout.registerdata);
		((TextView) HealthGenius.myApp.findViewById(R.id.startText)).setText("User data");
		((TextView) HealthGenius.myApp.findViewById(R.id.requestText)).setText(HealthGenius.myLanguageManager.PSW_REG_DATAREQUEST);
		((TextView) HealthGenius.myApp.findViewById(R.id.requestFirst)).setText(HealthGenius.myLanguageManager.PSW_REG_FIRSTNAME);
		((TextView) HealthGenius.myApp.findViewById(R.id.requestLast)).setText(HealthGenius.myLanguageManager.PSW_REG_LASTNAME);
		((TextView) HealthGenius.myApp.findViewById(R.id.requestEmail)).setText("E-Mail: ");
		((TextView) HealthGenius.myApp.findViewById(R.id.requestRepeatEmail)).setText(HealthGenius.myLanguageManager.PSW_REG_EMAILREPEAT);
		((TextView) HealthGenius.myApp.findViewById(R.id.requestDate)).setText(HealthGenius.myLanguageManager.PSW_REG_DATE);
		((TextView) HealthGenius.myApp.findViewById(R.id.sexrequest)).setText(HealthGenius.myLanguageManager.PSW_REG_SEX);
		((TextView) HealthGenius.myApp.findViewById(R.id.countryrequest)).setText(HealthGenius.myLanguageManager.PSW_REG_COUNTRY);
		final EditText first = (EditText) HealthGenius.myApp.findViewById(R.id.firstText);
		first.setText(HealthGenius.myMobileManager.user.firstname);
		final EditText last = (EditText) HealthGenius.myApp.findViewById(R.id.lastText);
		last.setText(HealthGenius.myMobileManager.user.lastname);
		final EditText mail = (EditText) HealthGenius.myApp.findViewById(R.id.emailText);
		final EditText repeatMail = (EditText) HealthGenius.myApp.findViewById(R.id.repeatEmailText);
		if (HealthGenius.myMobileManager.user.getMail() != null) {
			mail.setText(HealthGenius.myMobileManager.user.getMail());
			repeatMail.setText(HealthGenius.myMobileManager.user.getMail());
		}
		final DatePicker date = (DatePicker) HealthGenius.myApp.findViewById(R.id.birthdate);
		if (HealthGenius.myMobileManager.userForServices.getBirthdate() == null) date.init(1970, 1, 1, null);
		else date.init(HealthGenius.myMobileManager.userForServices.getBirthdate().getYear() + 1900, 
				HealthGenius.myMobileManager.userForServices.getBirthdate().getMonth(), 
				HealthGenius.myMobileManager.userForServices.getBirthdate().getDate(), null);
		final Spinner sex = (Spinner) HealthGenius.myApp.findViewById(R.id.sex);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(HealthGenius.myApp, R.array.sextype, android.R.layout.simple_spinner_item);
	    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    sex.setAdapter(adapter);
		if (HealthGenius.myMobileManager.user.getSex().equals(Sex.NOT_SPECIFIED)) sex.setSelection(0);
		else if (HealthGenius.myMobileManager.user.getSex().equals(Sex.MALE)) sex.setSelection(1);
		else if (HealthGenius.myMobileManager.user.getSex().equals(Sex.FEMALE)) sex.setSelection(2);
		else sex.setSelection(0);
		final Country[] countries = Country.values();
		final Spinner country = (Spinner) HealthGenius.myApp.findViewById(R.id.country);
		ArrayList<String> countrystrings = new ArrayList<String>();
		int j = 0;
		int selected = 0;
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
		ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(HealthGenius.myApp, android.R.layout.simple_spinner_item, countrystrings);
	    adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    country.setAdapter(adapter3);
	    country.setSelection(0);
	    if (countries != null) country.setSelection(selected);
//	    if (HealthGenius.myMobileManager.user.country != null) country.setEnabled(false);
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
					HealthGenius.myMobileManager.user.setMail(mail.getText().toString());
					if (countries != null) HealthGenius.myMobileManager.user.setCountry(countries[country.getSelectedItemPosition()]);
					Date birthdate = new Date(0);
					birthdate.setYear(date.getYear() - 1900);
					birthdate.setMonth(date.getMonth());
					birthdate.setDate(date.getDayOfMonth());
					Date minbirthdate = new Date();
					minbirthdate.setMonth(minbirthdate.getMonth() - 1);
					if (mail.getText().equals(repeatMail.getText())) {
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
					HealthGenius.myMobileManager.user.setCountry(countries[country.getSelectedItemPosition()]);
					Runnable run = new Runnable() {
						@Override
						public void run() {
							handler.sendEmptyMessage(0);
							if (HealthGenius.myMobileManager.updateUserData()) {
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
										HealthGenius.myMobileManager.saveUsers();
										showOptions();
										break;
									case 2:
										animationFrame = (ImageView) HealthGenius.myApp.findViewById(R.id.popupImage);
										animation = (AnimationDrawable) animationFrame.getBackground();
										animation.stop();
										animationFrame.setVisibility(View.GONE);
										myUIManager.UImisc.loadInfoPopup(HealthGenius.myLanguageManager.CONNECTION_FAILED);
										myUIManager.loadBoxClosed(false, true);
										break;
								}
							}
						};
					};
					Thread thread = new Thread(run);
					thread.start();
				} catch (Exception e) {
					myUIManager.UImisc.loadInfoPopup(HealthGenius.myLanguageManager.PSW_REG_BADDATA);
				} 
			}
		});
	}
	
	public void loadChangePassword() {
		HealthGenius.myApp.setContentView(R.layout.register);
		((TextView) HealthGenius.myApp.findViewById(R.id.startText)).setText(HealthGenius.myLanguageManager.PSW_REG_PASSWORD_CHANGE);
		((TextView) HealthGenius.myApp.findViewById(R.id.requestText)).setText(HealthGenius.myLanguageManager.PSW_REG_USERNAME + " " + HealthGenius.myMobileManager.user.getName());
		((TextView) HealthGenius.myApp.findViewById(R.id.requestUser)).setText(HealthGenius.myLanguageManager.PSW_REG_PASSWORD_OLD);
		((TextView) HealthGenius.myApp.findViewById(R.id.requestPass)).setText(HealthGenius.myLanguageManager.PSW_REG_PASSWORD_NEW);
		((TextView) HealthGenius.myApp.findViewById(R.id.requestPassagain)).setText(HealthGenius.myLanguageManager.PSW_REG_PASSWORD_NEW_AGAIN);
		final EditText username = (EditText) HealthGenius.myApp.findViewById(R.id.loginText);
		final EditText password = (EditText) HealthGenius.myApp.findViewById(R.id.passwordText);
		final EditText passwordAgain = (EditText) HealthGenius.myApp.findViewById(R.id.passwordTextagain);
		ImageButton ok = (ImageButton) HealthGenius.myApp.findViewById(R.id.ok);
		ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				final String oldpassText = username.getText().toString();
				final String newpassText = password.getText().toString();
				String newpassTextAgain = passwordAgain.getText().toString();
				if ((oldpassText.equals(""))||(newpassText.equals(""))||(newpassTextAgain.equals(""))) {
					myUIManager.UImisc.loadInfoPopup(HealthGenius.myLanguageManager.PSW_REG_BADDATA);
					return;
				}
				if (!oldpassText.equals(HealthGenius.myMobileManager.user.getPassword())) {
					username.setText("");
					password.setText("");
					passwordAgain.setText("");
					myUIManager.UImisc.loadInfoPopup(HealthGenius.myLanguageManager.PSW_REG_PASSWORD_NEW_NOTMATCH);
					return;
				}
				if (!newpassText.equals(newpassTextAgain)) {
					password.setText("");
					passwordAgain.setText("");
					myUIManager.UImisc.loadInfoPopup(HealthGenius.myLanguageManager.PSW_REG_PASSWORD_NEW_NOTMATCH);
					return;
				}
				Runnable run = new Runnable() {
					@Override
					public void run() {
						handler.sendEmptyMessage(0);
						if (HealthGenius.myMobileManager.updateUserPassword(oldpassText, newpassText)) {
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
									HealthGenius.myMobileManager.user.setPassword(newpassText);
									HealthGenius.myMobileManager.saveUsers();
									showOptions();
									break;
								case 2:
									animationFrame = (ImageView) HealthGenius.myApp.findViewById(R.id.popupImage);
									animation = (AnimationDrawable) animationFrame.getBackground();
									animation.stop();
									animationFrame.setVisibility(View.GONE);
									myUIManager.UImisc.loadInfoPopup(HealthGenius.myLanguageManager.CONNECTION_FAILED);
									myUIManager.loadBoxClosed(false, true);
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

}
