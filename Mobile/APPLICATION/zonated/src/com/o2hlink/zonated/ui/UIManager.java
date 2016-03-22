package com.o2hlink.zonated.ui;

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

import com.o2hlink.zonated.R;
import com.o2hlink.zonated.Zonated;
import com.o2hlink.zonated.background.InitialConnection;
import com.o2hlink.zonated.background.RefreshingConnection;

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
	public UIManagerContacts UIcontacts;
	
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
		UIcontacts = new UIManagerContacts(this);
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
		Zonated.myApp.setContentView(screen);
	}

}
