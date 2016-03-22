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

public class UserCheckout implements Runnable {

	int currentImage = 0;
	int nextImage = 0;
	RelativeLayout popupView;
	TextView popupText;
	AnimationDrawable animation;
	
	public UserCheckout() {
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
					if (HealthGenius.myMobileManager.userForServices instanceof Researcher) HealthGenius.myUIManager.UImisc.loadInfoPopup(HealthGenius.myLanguageManager.TEXT_NOTRESEARCHERS);
					else {
						HealthGenius.myMobileManager.user.setCreated(true);
						HealthGenius.myUIManager.UIlogin.loadMatrixPasswordForRegistering();
					}
					break;
				case 1:
					animation.stop();
					HealthGenius.myUIManager.UIlogin.loadCheckUserScreen();
					HealthGenius.myUIManager.UImisc.loadInfoPopup(HealthGenius.myLanguageManager.PSW_REG_BADPASSWRD);
					break;
				case 2:
					animation.stop();
					HealthGenius.myUIManager.UIlogin.loadCheckUserScreen();
					HealthGenius.myUIManager.UImisc.loadInfoPopup(HealthGenius.myLanguageManager.PSW_REG_USEREXISTS);
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

