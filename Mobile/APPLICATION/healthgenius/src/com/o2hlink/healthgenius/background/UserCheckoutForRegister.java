package com.o2hlink.healthgenius.background;

import com.o2hlink.activ8.client.entity.Researcher;
import com.o2hlink.healthgenius.HealthGenius;
import com.o2hlink.healthgenius.R;

import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class UserCheckoutForRegister implements Runnable {

	int currentImage = 0;
	int nextImage = 0;
	int type;
	RelativeLayout popupView;
	TextView popupText;
	AnimationDrawable animation;
	
	public UserCheckoutForRegister(int type) {
		this.type = type;
		popupView = (RelativeLayout) HealthGenius.myApp.findViewById(R.id.popupView);
		popupView.setVisibility(View.VISIBLE);
		popupText = (TextView) HealthGenius.myApp.findViewById(R.id.popupText);
		ImageView animationFrame = (ImageView) HealthGenius.myApp.findViewById(R.id.popupImage);
		animationFrame.setVisibility(View.VISIBLE);
		animationFrame.setBackgroundResource(R.drawable.loading);
		this.animation = (AnimationDrawable) animationFrame.getBackground();
	}

	@Override
	public void run() {			
		this.animation.start();
		handler.sendEmptyMessage(4);
		int success = HealthGenius.myMobileManager.checkUserExistance();
		handler.sendEmptyMessage(success);
	}

	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case 0:		
					animation.stop();
					HealthGenius.myUIManager.UIlogin.loadRegisterScreen(type);
					HealthGenius.myUIManager.UImisc.loadInfoPopup(HealthGenius.myLanguageManager.PSW_REG_USEREXISTS);
					break;
				case 1:
					animation.stop();
					HealthGenius.myUIManager.UIlogin.loadRegisterScreen(type);
					HealthGenius.myUIManager.UImisc.loadInfoPopup(HealthGenius.myLanguageManager.PSW_REG_USEREXISTS);
					break;
				case 2:
					animation.stop();
					HealthGenius.myUIManager.UIlogin.loadRegisterDataScreen(type);
					break;
				case 3:
					animation.stop();
					HealthGenius.myUIManager.UImisc.loadInfoPopupLong(HealthGenius.myLanguageManager.CONNECTION_FAILED + " " + HealthGenius.myLanguageManager.CONNECTION_LIMITED);
					break;
				case 4:
					popupText.setText(HealthGenius.myLanguageManager.CONNECTION_CHECKING);
					break;
			}
		}

	};

}

