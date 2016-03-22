package com.o2hlink.zonated.ui;

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
import com.o2hlink.zonated.R;
import com.o2hlink.zonated.Zonated;
import com.o2hlink.zonated.ZonatedUtil;
import com.o2hlink.zonated.mobile.User;

public class UIManagerOptions {
	
	public UIManager myUIManager;
	
	public UIManagerOptions(UIManager ui) {
		myUIManager = ui;
	}
	
	public void showOptions() {
		myUIManager.state = UIManager.UI_STATE_OPTIONS;
		int realwidth = Zonated.myApp.getResources().getDisplayMetrics().widthPixels;
		Zonated.myApp.setContentView(R.layout.list);
		((TextView) Zonated.myApp.findViewById(R.id.startText)).setText(Zonated.myLanguageManager.OPTIONS_TITLE);
		TableLayout listing = (TableLayout)Zonated.myApp.findViewById(R.id.listing);
		
		TableRow buttonLayout = new TableRow(Zonated.myApp);
		buttonLayout.setOrientation(TableRow.HORIZONTAL);
		buttonLayout.setGravity(Gravity.CENTER_VERTICAL);
		buttonLayout.setLayoutParams(new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));			
		ImageButton button = new ImageButton(Zonated.myApp);
		button.setBackgroundResource(R.drawable.iconbg);
		button.setImageResource(R.drawable.profile);
		TextView text = new TextView(Zonated.myApp);
		text.setLayoutParams(new android.widget.TableRow.LayoutParams(realwidth - 60, android.widget.TableRow.LayoutParams.WRAP_CONTENT));
		text.setPadding(10, 0, 10, 0);
		text.append(Zonated.myLanguageManager.OPTIONS_PROFILE);
		text.setTextSize((float) 20);
		text.setTextColor(Color.BLACK);
		text.setTypeface(Typeface.SANS_SERIF);	
		OnClickListener listener = new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (!Zonated.myMobileManager.identified) {
					myUIManager.UImisc.loadInfoPopupLong(Zonated.myLanguageManager.OPTIONS_USERNOTCONNECTED);
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
		View separator = new View(Zonated.myApp);
		separator.setLayoutParams(new LayoutParams(android.view.ViewGroup.LayoutParams.FILL_PARENT, 1));
		separator.setBackgroundColor(Color.BLACK);
		listing.addView(separator);
		
		buttonLayout = new TableRow(Zonated.myApp);
		buttonLayout.setOrientation(TableRow.HORIZONTAL);
		buttonLayout.setGravity(Gravity.CENTER_VERTICAL);
		buttonLayout.setLayoutParams(new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));			
		button = new ImageButton(Zonated.myApp);
		button.setBackgroundResource(R.drawable.iconbg);
		button.setImageResource(R.drawable.profile);
		text = new TextView(Zonated.myApp);
		text.setLayoutParams(new android.widget.TableRow.LayoutParams(realwidth - 60, android.widget.TableRow.LayoutParams.WRAP_CONTENT));
		text.setPadding(10, 0, 10, 0);
		text.append(Zonated.myLanguageManager.OPTIONS_WEB);
		text.setTextSize((float) 20);
		text.setTextColor(Color.BLACK);
		text.setTypeface(Typeface.SANS_SERIF);	
		listener = new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (!Zonated.myMobileManager.identified) {
					myUIManager.UImisc.loadInfoPopupLong(Zonated.myLanguageManager.OPTIONS_USERNOTCONNECTED);
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
		separator = new View(Zonated.myApp);
		separator.setLayoutParams(new LayoutParams(android.view.ViewGroup.LayoutParams.FILL_PARENT, 1));
		separator.setBackgroundColor(Color.BLACK);
		listing.addView(separator);
		
		buttonLayout = new TableRow(Zonated.myApp);
		buttonLayout.setOrientation(TableRow.HORIZONTAL);
		buttonLayout.setGravity(Gravity.CENTER_VERTICAL);
		buttonLayout.setLayoutParams(new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));			
		button = new ImageButton(Zonated.myApp);
		button.setBackgroundResource(R.drawable.iconbg);
		button.setImageResource(R.drawable.password);
		text = new TextView(Zonated.myApp);
		text.setLayoutParams(new android.widget.TableRow.LayoutParams(realwidth - 60, android.widget.TableRow.LayoutParams.WRAP_CONTENT));
		text.setPadding(10, 0, 10, 0);
		text.append(Zonated.myLanguageManager.OPTIONS_USERPASS);
		text.setTextSize((float) 20);
		text.setTextColor(Color.BLACK);
		text.setTypeface(Typeface.SANS_SERIF);	
		listener = new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (!Zonated.myMobileManager.identified) {
					myUIManager.UImisc.loadInfoPopupLong(Zonated.myLanguageManager.OPTIONS_USERNOTCONNECTED);
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
		separator = new View(Zonated.myApp);
		separator.setLayoutParams(new LayoutParams(android.view.ViewGroup.LayoutParams.FILL_PARENT, 1));
		separator.setBackgroundColor(Color.BLACK);
		listing.addView(separator);
		
		buttonLayout = new TableRow(Zonated.myApp);
		buttonLayout.setOrientation(TableRow.HORIZONTAL);
		buttonLayout.setGravity(Gravity.CENTER_VERTICAL);
		buttonLayout.setLayoutParams(new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));			
		button = new ImageButton(Zonated.myApp);
		button.setBackgroundResource(R.drawable.iconbg);
		button.setImageResource(R.drawable.password);
		text = new TextView(Zonated.myApp);
		text.setLayoutParams(new android.widget.TableRow.LayoutParams(realwidth - 60, android.widget.TableRow.LayoutParams.WRAP_CONTENT));
		text.setPadding(10, 0, 10, 0);
		text.append(Zonated.myLanguageManager.OPTIONS_DRAWPASS);
		text.setTextSize((float) 20);
		text.setTextColor(Color.BLACK);
		text.setTypeface(Typeface.SANS_SERIF);	
		listener = new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (!Zonated.myMobileManager.identified) {
					myUIManager.UImisc.loadInfoPopupLong(Zonated.myLanguageManager.OPTIONS_USERNOTCONNECTED);
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
		separator = new View(Zonated.myApp);
		separator.setLayoutParams(new LayoutParams(android.view.ViewGroup.LayoutParams.FILL_PARENT, 1));
		separator.setBackgroundColor(Color.BLACK);
		listing.addView(separator);

		ImageButton back = (ImageButton) Zonated.myApp.findViewById(R.id.previous);
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Zonated.myUIManager.UIquest.loadSharedQuestionnaires();
			}
		});
	}
	
	public void loadDataScreenForChanging() {
		Zonated.myApp.setContentView(R.layout.registerdata);
		((TextView) Zonated.myApp.findViewById(R.id.startText)).setText("User data");
		((TextView) Zonated.myApp.findViewById(R.id.requestText)).setText(Zonated.myLanguageManager.PSW_REG_DATAREQUEST);
		((TextView) Zonated.myApp.findViewById(R.id.requestFirst)).setText(Zonated.myLanguageManager.PSW_REG_FIRSTNAME);
		((TextView) Zonated.myApp.findViewById(R.id.requestLast)).setText(Zonated.myLanguageManager.PSW_REG_LASTNAME);
		((TextView) Zonated.myApp.findViewById(R.id.requestEmail)).setText("E-Mail: ");
		((TextView) Zonated.myApp.findViewById(R.id.requestRepeatEmail)).setText(Zonated.myLanguageManager.PSW_REG_EMAILREPEAT);
		((TextView) Zonated.myApp.findViewById(R.id.requestDate)).setText(Zonated.myLanguageManager.PSW_REG_DATE);
		((TextView) Zonated.myApp.findViewById(R.id.sexrequest)).setText(Zonated.myLanguageManager.PSW_REG_SEX);
		((TextView) Zonated.myApp.findViewById(R.id.countryrequest)).setText(Zonated.myLanguageManager.PSW_REG_COUNTRY);
		final EditText first = (EditText) Zonated.myApp.findViewById(R.id.firstText);
		first.setText(Zonated.myMobileManager.user.firstname);
		final EditText last = (EditText) Zonated.myApp.findViewById(R.id.lastText);
		last.setText(Zonated.myMobileManager.user.lastname);
		final EditText mail = (EditText) Zonated.myApp.findViewById(R.id.emailText);
		final EditText repeatMail = (EditText) Zonated.myApp.findViewById(R.id.repeatEmailText);
		if (Zonated.myMobileManager.user.getMail() != null) {
			mail.setText(Zonated.myMobileManager.user.getMail());
			repeatMail.setText(Zonated.myMobileManager.user.getMail());
		}
		final DatePicker date = (DatePicker) Zonated.myApp.findViewById(R.id.birthdate);
		if (Zonated.myMobileManager.userForServices.getBirthdate() == null) date.init(1970, 1, 1, null);
		else date.init(Zonated.myMobileManager.userForServices.getBirthdate().getYear() + 1900, 
				Zonated.myMobileManager.userForServices.getBirthdate().getMonth(), 
				Zonated.myMobileManager.userForServices.getBirthdate().getDate(), null);
		final Spinner sex = (Spinner) Zonated.myApp.findViewById(R.id.sex);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(Zonated.myApp, R.array.sextype, android.R.layout.simple_spinner_item);
	    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    sex.setAdapter(adapter);
		if (Zonated.myMobileManager.user.getSex().equals(Sex.NOT_SPECIFIED)) sex.setSelection(0);
		else if (Zonated.myMobileManager.user.getSex().equals(Sex.MALE)) sex.setSelection(1);
		else if (Zonated.myMobileManager.user.getSex().equals(Sex.FEMALE)) sex.setSelection(2);
		else sex.setSelection(0);
		final Country[] countries = Country.values();
		final Spinner country = (Spinner) Zonated.myApp.findViewById(R.id.country);
		ArrayList<String> countrystrings = new ArrayList<String>();
		int j = 0;
		int selected = 0;
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
		ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(Zonated.myApp, android.R.layout.simple_spinner_item, countrystrings);
	    adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    country.setAdapter(adapter3);
	    country.setSelection(0);
	    if (countries != null) country.setSelection(selected);
//	    if (Zonated.myMobileManager.user.country != null) country.setEnabled(false);
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
					Zonated.myMobileManager.user.setMail(mail.getText().toString());
					if (countries != null) Zonated.myMobileManager.user.setCountry(countries[country.getSelectedItemPosition()]);
					Date birthdate = new Date(0);
					birthdate.setYear(date.getYear() - 1900);
					birthdate.setMonth(date.getMonth());
					birthdate.setDate(date.getDayOfMonth());
					Date minbirthdate = new Date();
					minbirthdate.setMonth(minbirthdate.getMonth() - 1);
					if (mail.getText().equals(repeatMail.getText())) {
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
					Zonated.myMobileManager.user.setCountry(countries[country.getSelectedItemPosition()]);
					Runnable run = new Runnable() {
						@Override
						public void run() {
							handler.sendEmptyMessage(0);
							if (Zonated.myMobileManager.updateUserData()) {
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
										Zonated.myMobileManager.saveUsers();
										showOptions();
										break;
									case 2:
										animationFrame = (ImageView) Zonated.myApp.findViewById(R.id.popupImage);
										animation = (AnimationDrawable) animationFrame.getBackground();
										animation.stop();
										animationFrame.setVisibility(View.GONE);
										myUIManager.UImisc.loadInfoPopup(Zonated.myLanguageManager.CONNECTION_FAILED);
										myUIManager.UIquest.loadSharedQuestionnaires();
										break;
								}
							}
						};
					};
					Thread thread = new Thread(run);
					thread.start();
				} catch (Exception e) {
					myUIManager.UImisc.loadInfoPopup(Zonated.myLanguageManager.PSW_REG_BADDATA);
				} 
			}
		});
	}
	
	public void loadChangePassword() {
		Zonated.myApp.setContentView(R.layout.register);
		((TextView) Zonated.myApp.findViewById(R.id.startText)).setText(Zonated.myLanguageManager.PSW_REG_PASSWORD_CHANGE);
		((TextView) Zonated.myApp.findViewById(R.id.requestText)).setText(Zonated.myLanguageManager.PSW_REG_USERNAME + " " + Zonated.myMobileManager.user.getName());
		((TextView) Zonated.myApp.findViewById(R.id.requestUser)).setText(Zonated.myLanguageManager.PSW_REG_PASSWORD_OLD);
		((TextView) Zonated.myApp.findViewById(R.id.requestPass)).setText(Zonated.myLanguageManager.PSW_REG_PASSWORD_NEW);
		((TextView) Zonated.myApp.findViewById(R.id.requestPassagain)).setText(Zonated.myLanguageManager.PSW_REG_PASSWORD_NEW_AGAIN);
		final EditText username = (EditText) Zonated.myApp.findViewById(R.id.loginText);
		final EditText password = (EditText) Zonated.myApp.findViewById(R.id.passwordText);
		final EditText passwordAgain = (EditText) Zonated.myApp.findViewById(R.id.passwordTextagain);
		ImageButton ok = (ImageButton) Zonated.myApp.findViewById(R.id.ok);
		ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				final String oldpassText = username.getText().toString();
				final String newpassText = password.getText().toString();
				String newpassTextAgain = passwordAgain.getText().toString();
				if ((oldpassText.equals(""))||(newpassText.equals(""))||(newpassTextAgain.equals(""))) {
					myUIManager.UImisc.loadInfoPopup(Zonated.myLanguageManager.PSW_REG_BADDATA);
					return;
				}
				if (!oldpassText.equals(Zonated.myMobileManager.user.getPassword())) {
					username.setText("");
					password.setText("");
					passwordAgain.setText("");
					myUIManager.UImisc.loadInfoPopup(Zonated.myLanguageManager.PSW_REG_PASSWORD_NEW_NOTMATCH);
					return;
				}
				if (!newpassText.equals(newpassTextAgain)) {
					password.setText("");
					passwordAgain.setText("");
					myUIManager.UImisc.loadInfoPopup(Zonated.myLanguageManager.PSW_REG_PASSWORD_NEW_NOTMATCH);
					return;
				}
				Runnable run = new Runnable() {
					@Override
					public void run() {
						handler.sendEmptyMessage(0);
						if (Zonated.myMobileManager.updateUserPassword(oldpassText, newpassText)) {
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
									Zonated.myMobileManager.user.setPassword(newpassText);
									Zonated.myMobileManager.saveUsers();
									showOptions();
									break;
								case 2:
									animationFrame = (ImageView) Zonated.myApp.findViewById(R.id.popupImage);
									animation = (AnimationDrawable) animationFrame.getBackground();
									animation.stop();
									animationFrame.setVisibility(View.GONE);
									myUIManager.UImisc.loadInfoPopup(Zonated.myLanguageManager.CONNECTION_FAILED);
									myUIManager.UIquest.loadSharedQuestionnaires();
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

}
