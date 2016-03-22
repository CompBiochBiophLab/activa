package com.o2hlink.healthgenius.ui;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Date;
import java.util.Enumeration;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.CountDownTimer;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.widget.AbsoluteLayout;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.ImageView.ScaleType;

import com.o2hlink.healthgenius.HealthGenius;
import com.o2hlink.healthgenius.R;
import com.o2hlink.healthgenius.background.GetHistory;
import com.o2hlink.healthgenius.background.InitialConnection;
import com.o2hlink.healthgenius.background.RefreshingConnection;
import com.o2hlink.healthgenius.exterior.ExternalApp;
import com.o2hlink.healthgenius.patient.Patient;

@SuppressWarnings("deprecation")
public class UIManager extends UIManagerStatics {
	
	/**
	 * The instance of the manager.
	 */
	private static UIManager instance;
	
	/**
	 * State of the UI (Screen is appearing).
	 */
	public int state;
	
	/**
	 * Previous state of the UI.
	 */
	public int prevState;
	
	/**
	 * Class in charge of login processes
	 */
	public UIManagerLogin UIlogin;
	
	/**
	 * Class in charge of misc processes
	 */
	public UIManagerMisc UImisc;
	
	/**
	 * Class in charge of options processes
	 */
	public UIManagerOptions UIoptions;
	
	/**
	 * Class in charge of questionnaire processes
	 */
	public UIManagerQuestionnaires UIquest;
	
	/**
	 * Class in charge of questionnaire processes
	 */
	public UIManagerMeasurements UImeas;
	
	/**
	 * Class in charge of questionnaire processes
	 */
	public UIManagerCalendar UIcalendar;
	
	/**
	 * Class in charge of questionnaire processes
	 */
	public UIManagerContacts UIcontacts;
	
	/**
	 * Class in charge of questionnaire processes
	 */
	public UIManagerPatient UIpatient;
	
	/**
	 * Class in charge of programs processes
	 */
	public UIManagerPrograms UIprograms;
	
	/**
	 * Static TextView for different purposes.
	 */
	public TextView genericTextView;
	
	private UIManager() {
		this.state = UI_STATE_LOADING;
		this.prevState = -1;
		UIlogin = new UIManagerLogin(this);
		UImisc = new UIManagerMisc(this);
		UIoptions = new UIManagerOptions(this);
		UIquest = new UIManagerQuestionnaires(this);
		UImeas = new UIManagerMeasurements(this);
		UIcalendar = new UIManagerCalendar(this);
		UIcontacts = new UIManagerContacts(this);
		UIpatient = new UIManagerPatient(this);
		UIprograms = new UIManagerPrograms(this);
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
	
	/**
	 * Method for loading generic screens
	 * @param screen
	 */
	public void loadScreen(int screen) {
		HealthGenius.myApp.setContentView(screen);
	}
	
	/**
	 * Load the main box closed
	 * @param login
	 * @param defading
	 */
	public void loadBoxClosed(final boolean login, boolean defading) {
		this.state = UI_STATE_MAIN;
		// Load scenario
		HealthGenius.myApp.setContentView(R.layout.scenario); 
		final AbsoluteLayout layout = (AbsoluteLayout) HealthGenius.myApp.findViewById(R.id.Background);
		// Get display settings
		Display display = HealthGenius.myApp.getWindowManager().getDefaultDisplay(); 
		int realwidth = display.getWidth();
		int realheight = display.getHeight();
		int barheight = (int) (((float)40/(float)defheight)*(float)realheight);
		float indexheight = ((float)realheight/(float)defheight);
		float indexwidth = ((float)realwidth/(float)defwidth);
		// Set if necessary the defading
		if (defading) {
			AnimationSet set = new AnimationSet(true);
			set.setFillAfter(true);
			AlphaAnimation animation = new AlphaAnimation(0.0f, 1.0f);
			animation.setDuration(1000);
			animation.setFillAfter(true);
			set.addAnimation(animation);
			LayoutAnimationController controller = new LayoutAnimationController(set, 0);
			layout.setAnimation(controller.getAnimation());
		}
		// Load the context menu
		if (HealthGenius.myMenu != null) {
			HealthGenius.myMenu.clear();
			HealthGenius.myInflater.inflate(R.menu.main, HealthGenius.myMenu);
		}
		//Load the outside button
		ImageButton miscbutton = (ImageButton) HealthGenius.myApp.findViewById(R.id.miscbutton);
		if (login) miscbutton.setVisibility(View.GONE);
		miscbutton.setBackgroundResource(R.drawable.refresh);
		miscbutton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				v.setVisibility(View.GONE);
				HealthGenius.myUIManager.state = UIManager.UI_STATE_LOADING;
				final RelativeLayout popupView = (RelativeLayout) HealthGenius.myApp.findViewById(R.id.popupView);
				popupView.setVisibility(View.VISIBLE);
				RefreshingConnection refreshing = new RefreshingConnection();
				Thread thread = new Thread(refreshing, "REFRESH");
				thread.start();
			}
		});
		// Load the info area
		ImageButton info = new ImageButton(HealthGenius.myApp);
		info.setBackgroundResource(R.drawable.bgtouch);
		int top = (int) ((infoy*indexheight) - barheight*((float)infoy/(float)defheight));
		int left = (int) (infox*indexwidth);
		int height = (int) (infoheight*indexheight);
		int width = (int) (infowidth*indexwidth);
		android.widget.AbsoluteLayout.LayoutParams params = new android.widget.AbsoluteLayout.LayoutParams(width, height, left, top);
		info.setLayoutParams(params);
		info.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				UImisc.loadAboutUs();
			}
		});
		layout.addView(info);
		// Load the back area
		ImageButton back = new ImageButton(HealthGenius.myApp);
		back.setBackgroundResource(R.drawable.bgtouch);
		top = (int) ((backopeny*indexheight) - barheight*((float)backopeny/(float)defheight));
		left = (int) (backopenx*indexwidth);
		height = (int) (backopenheight*indexheight);
		width = (int) (backopenwidth*indexwidth);
		params = new android.widget.AbsoluteLayout.LayoutParams(width, height, left, top);
		back.setLayoutParams(params);
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				AnimationSet set = new AnimationSet(true);
				set.setFillAfter(true);
				AlphaAnimation animation = new AlphaAnimation(1.0f, 0.0f);
				animation.setDuration(1000);
				animation.setFillAfter(true);
				set.addAnimation(animation);
				LayoutAnimationController controller = new LayoutAnimationController(set, 0);
				layout.setAnimation(controller.getAnimation());
				CountDownTimer timer = new CountDownTimer(1000, 1000) {
					@Override
					public void onTick(long millisUntilFinished) {}
					@Override
					public void onFinish() {
						UIlogin.loadLoginScreen();
					}
				};
				timer.start();
			}
		});
		layout.addView(back);
		// Load the options area
		ImageButton options = new ImageButton(HealthGenius.myApp);
		options.setBackgroundResource(R.drawable.bgtouch);
		top = (int) ((optionsy*indexheight) - barheight*((float)optionsy/(float)defheight));
		left = (int) (optionsx*indexwidth);
		height = (int) (optionsheight*indexheight);
		width = (int) (optionswidth*indexwidth);
		params = new android.widget.AbsoluteLayout.LayoutParams(width, height, left, top);
		options.setLayoutParams(params);
		options.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				UIoptions.showOptions();
			}
		});
		layout.addView(options);
		// Load the forward area
		ImageButton forward = new ImageButton(HealthGenius.myApp);
		forward.setBackgroundResource(R.drawable.bgtouch);
		top = (int) ((forwardy*indexheight) - barheight*((float)forwardy/(float)defheight));
		left = (int) (forwardx*indexwidth);
		height = (int) (forwardheight*indexheight);
		width = (int) (forwardwidth*indexwidth);
		params = new android.widget.AbsoluteLayout.LayoutParams(width, height, left, top);
		forward.setLayoutParams(params);
		forward.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				ImageView animationFrame = new ImageView(HealthGenius.myApp);
//				animationFrame.setLayoutParams(new android.widget.FrameLayout.LayoutParams((int) (((float)defwidth)*indexwidth), android.widget.FrameLayout.LayoutParams.FILL_PARENT));
				animationFrame.setLayoutParams(new android.widget.FrameLayout.LayoutParams(android.widget.FrameLayout.LayoutParams.FILL_PARENT, android.widget.FrameLayout.LayoutParams.FILL_PARENT));
				animationFrame.setScaleType(ScaleType.FIT_XY);
				((FrameLayout)HealthGenius.myApp.findViewById(R.id.basiclayout)).addView(animationFrame);
				animationFrame.setBackgroundResource(R.drawable.openbox);
				AnimationDrawable animation = (AnimationDrawable) animationFrame.getBackground();
				animation.start();
				CountDownTimer timer = new CountDownTimer(750, 150) {
					@Override
					public void onTick(long millisUntilFinished) {}
					@Override
					public void onFinish() {
						loadBoxOpen();
					}
				};
				timer.start();
			}
		});
		layout.addView(forward);
		// Make the initial connection if necessary
		if (login) {
			if (HealthGenius.myMobileManager.identified) {
				this.state = UI_STATE_LOADING;
				((ImageButton)HealthGenius.myApp.findViewById(R.id.miscbutton)).setVisibility(View.GONE);
				final RelativeLayout popupView = (RelativeLayout) HealthGenius.myApp.findViewById(R.id.popupView);
				popupView.setVisibility(View.VISIBLE);
				InitialConnection initial = new InitialConnection();
				Thread thread = new Thread(initial, "LOGIN");
				thread.start();
			}
			else{
				UImisc.loadInfoPopup(HealthGenius.myLanguageManager.USER_NOID);
				((ImageButton)HealthGenius.myApp.findViewById(R.id.miscbutton)).setVisibility(View.VISIBLE);
			}
			try {
				((TextView)HealthGenius.myApp.findViewById(R.id.popupPromotionText)).setVisibility(View.VISIBLE);
				((TextView)HealthGenius.myApp.findViewById(R.id.popupPromotionText)).setText(HealthGenius.myMobileManager.promotionString);
				((ImageView)HealthGenius.myApp.findViewById(R.id.popupPromotion)).setVisibility(View.VISIBLE);
				((ImageView)HealthGenius.myApp.findViewById(R.id.popupPromotion)).setImageDrawable(Drawable.createFromStream(new FileInputStream(HealthGenius.myMobileManager.promotionMicro), "src"));
				((ImageView)HealthGenius.myApp.findViewById(R.id.popupPromotion)).setOnClickListener(new OnClickListener() {						
					@Override
					public void onClick(View v) {
						if (HealthGenius.myMobileManager.promotionUrl != null) {
							Intent in = new Intent(Intent.ACTION_VIEW);
							in.setData(Uri.parse(HealthGenius.myMobileManager.promotionUrl));
							try {
								HealthGenius.myApp.startActivity(in);
							} catch (Exception e) {}
						}
					}
				});
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Load the main box opened
	 */
	public void loadBoxOpen() {
		this.state = UI_STATE_SUBENVIRONMENT;
		// Load scenario
		HealthGenius.myApp.setContentView(R.layout.scenarioopen); 
		final AbsoluteLayout layout = (AbsoluteLayout) HealthGenius.myApp.findViewById(R.id.Background);
		// Get display settings
		Display display = HealthGenius.myApp.getWindowManager().getDefaultDisplay(); 
		int realwidth = display.getWidth();
		int realheight = display.getHeight();
		int barheight = (int) (((float)40/(float)defheight)*(float)realheight);
		float indexheight = ((float)realheight/(float)defheight);
		float indexwidth = ((float)realwidth/(float)defwidth);
		// Set if necessary the defading
		/*if (defading) {
			AnimationSet set = new AnimationSet(true);
			set.setFillAfter(true);
			AlphaAnimation animation = new AlphaAnimation(0.0f, 1.0f);
			animation.setDuration(1000);
			animation.setFillAfter(true);
			set.addAnimation(animation);
			LayoutAnimationController controller = new LayoutAnimationController(set, 0);
			layout.setAnimation(controller.getAnimation());
		}*/
		// Load the context menu
		if (HealthGenius.myMenu != null) {
			HealthGenius.myMenu.clear();
			HealthGenius.myInflater.inflate(R.menu.main, HealthGenius.myMenu);
		}
		//Load the outside button
		ImageButton miscbutton = (ImageButton) HealthGenius.myApp.findViewById(R.id.miscbutton);
		miscbutton.setVisibility(View.VISIBLE);
		miscbutton.setBackgroundResource(R.drawable.refresh);
		miscbutton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				v.setVisibility(View.GONE);
				HealthGenius.myUIManager.state = UIManager.UI_STATE_LOADING;
				final RelativeLayout popupView = (RelativeLayout) HealthGenius.myApp.findViewById(R.id.popupView);
				popupView.setVisibility(View.VISIBLE);
				RefreshingConnection refreshing = new RefreshingConnection();
				Thread thread = new Thread(refreshing, "REFRESH");
				thread.start();
			}
		});
		// Load the plus area
		ImageButton plus = new ImageButton(HealthGenius.myApp);
		plus.setBackgroundResource(R.drawable.bgtouch);
		int top = (int) ((plusy*indexheight) - barheight*((float)plusy/(float)defheight));
		int left = (int) (plusx*indexwidth);
		int height = (int) (plusheight*indexheight);
		int width = (int) (pluswidth*indexwidth);
		android.widget.AbsoluteLayout.LayoutParams params = new android.widget.AbsoluteLayout.LayoutParams(width, height, left, top);
		plus.setLayoutParams(params);
		plus.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				UIprograms.loadAddLinks(HealthGenius.myExteriorManager.externalApplicationsLinked,
						HealthGenius.myLanguageManager.MAIN_SELECT_LINKEDPROG, "");
			}
		});
		layout.addView(plus);
		// Load the minus area
		ImageButton minus = new ImageButton(HealthGenius.myApp);
		minus.setBackgroundResource(R.drawable.bgtouch);
		top = (int) ((minusy*indexheight) - barheight*((float)minusy/(float)defheight));
		left = (int) (minusx*indexwidth);
		height = (int) (minusheight*indexheight);
		width = (int) (minuswidth*indexwidth);
		params = new android.widget.AbsoluteLayout.LayoutParams(width, height, left, top);
		minus.setLayoutParams(params);
		minus.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				UIprograms.loadRemoveLinks(HealthGenius.myExteriorManager.externalApplicationsLinked,
						HealthGenius.myLanguageManager.MAIN_SELECT_LINKEDPROG, "");
			}
		});
		layout.addView(minus);
		// Load the back area
		ImageButton back = new ImageButton(HealthGenius.myApp);
		back.setBackgroundResource(R.drawable.bgtouch);
		top = (int) ((backclosedy*indexheight) - barheight*((float)backclosedy/(float)defheight));
		left = (int) (backclosedx*indexwidth);
		height = (int) (backclosedheight*indexheight);
		width = (int) (backclosedwidth*indexwidth);
		params = new android.widget.AbsoluteLayout.LayoutParams(width, height, left, top);
		back.setLayoutParams(params);
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				ImageView animationFrame = new ImageView(HealthGenius.myApp);
//				animationFrame.setLayoutParams(new android.widget.FrameLayout.LayoutParams((int) (((float)defwidth)*indexwidth), android.widget.FrameLayout.LayoutParams.FILL_PARENT));
				animationFrame.setLayoutParams(new android.widget.FrameLayout.LayoutParams(android.widget.FrameLayout.LayoutParams.FILL_PARENT, android.widget.FrameLayout.LayoutParams.FILL_PARENT));
				animationFrame.setScaleType(ScaleType.FIT_XY);
				((FrameLayout)HealthGenius.myApp.findViewById(R.id.basiclayout)).addView(animationFrame);
				animationFrame.setBackgroundResource(R.drawable.closebox);
				AnimationDrawable animation = (AnimationDrawable) animationFrame.getBackground();
				animation.start();
				CountDownTimer timer = new CountDownTimer(750, 150) {
					@Override
					public void onTick(long millisUntilFinished) {}
					@Override
					public void onFinish() {
						loadBoxClosed(false, false);
					}
				};
				timer.start();
			}
		});
		layout.addView(back);
		// Load the quest area
		ImageButton quest = new ImageButton(HealthGenius.myApp);
		quest.setBackgroundResource(R.drawable.bgtouch);
		top = (int) ((questy*indexheight) - barheight*((float)questy/(float)defheight));
		left = (int) (questx*indexwidth);
		height = (int) (questheight*indexheight);
		width = (int) (questwidth*indexwidth);
		params = new android.widget.AbsoluteLayout.LayoutParams(width, height, left, top);
		quest.setLayoutParams(params);
		quest.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				UIquest.loadSharedQuestionnaires();
			}
		});
		layout.addView(quest);
		// Load the cal area
		ImageButton cal = new ImageButton(HealthGenius.myApp);
		cal.setBackgroundResource(R.drawable.bgtouch);
		top = (int) ((caly*indexheight) - barheight*((float)caly/(float)defheight));
		left = (int) (calx*indexwidth);
		height = (int) (calheight*indexheight);
		width = (int) (calwidth*indexwidth);
		params = new android.widget.AbsoluteLayout.LayoutParams(width, height, left, top);
		cal.setLayoutParams(params);
		cal.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				UIcalendar.loadScheduleDay(new Date());
			}
		});
		layout.addView(cal);
		// Load the hist area
		ImageButton hist = new ImageButton(HealthGenius.myApp);
		hist.setBackgroundResource(R.drawable.bgtouch);
		top = (int) ((histy*indexheight) - barheight*((float)histy/(float)defheight));
		left = (int) (histx*indexwidth);
		height = (int) (histheight*indexheight);
		width = (int) (histwidth*indexwidth);
		params = new android.widget.AbsoluteLayout.LayoutParams(width, height, left, top);
		hist.setLayoutParams(params);
		hist.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (HealthGenius.myMobileManager.userForServices instanceof com.o2hlink.activ8.client.entity.Patient) {
					GetHistory sending = new GetHistory(new Patient((com.o2hlink.activ8.client.entity.Patient) HealthGenius.myMobileManager.userForServices), true);
					Thread thread = new Thread(sending, "GETHISTORY");
					thread.start();
				}
				else UIpatient.showPatients();
			}
		});
		layout.addView(hist);
		// Load the meas area
		ImageButton meas = new ImageButton(HealthGenius.myApp);
		meas.setBackgroundResource(R.drawable.bgtouch);
		top = (int) ((measy*indexheight) - barheight*((float)measy/(float)defheight));
		left = (int) (measx*indexwidth);
		height = (int) (measheight*indexheight);
		width = (int) (measwidth*indexwidth);
		params = new android.widget.AbsoluteLayout.LayoutParams(width, height, left, top);
		meas.setLayoutParams(params);
		meas.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				UImeas.loadSensorList();
			}
		});
		layout.addView(meas);
		// Adding the programs widgets
		Enumeration<ExternalApp> enumeration = HealthGenius.myExteriorManager.externalApplicationsLinked.elements();
		final PackageManager manager = HealthGenius.myApp.getPackageManager();
		int i = 0;
		while (enumeration.hasMoreElements()) {
			final ExternalApp app = enumeration.nextElement();
//			Drawable draw;
			ImageButton image = new ImageButton(HealthGenius.myApp);
			try {
//			    draw = manager.getActivityIcon(new ComponentName(app.packageName, app.activityName));
			    image.setScaleType(ScaleType.FIT_XY);
			    image.setImageDrawable(manager.getActivityIcon(new ComponentName(app.packageName, app.activityName)));
			    image.setBackgroundResource(R.drawable.bgtouch);
			    image.setMaxWidth(45);
		    	image.setMaxHeight(45);
				top = (int) ((progsy[i]*indexheight) - barheight*((float)progsy[i]/(float)defheight));
				left = (int) (progsx[i]*indexwidth);
				height = (int) (60*indexheight);
				width = (int) (60*indexwidth);
				params = new android.widget.AbsoluteLayout.LayoutParams(width, height, left, top);
				image.setLayoutParams(params);
			} catch (NameNotFoundException e) {
//			   	draw = null;
				image = null;
				e.printStackTrace();
			}
//			draw.setBounds(0, 0, 45, 45);
			image.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					app.startApplication();
				}
			});
			layout.addView(image);
			i++;
		}
	}

}
