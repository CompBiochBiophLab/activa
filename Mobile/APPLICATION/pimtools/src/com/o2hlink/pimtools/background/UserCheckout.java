package com.o2hlink.pimtools.background;

import com.o2hlink.pimtools.R;
import com.o2hlink.pimtools.Activa;

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
		popupView = (RelativeLayout) Activa.myApp.findViewById(R.id.popupView);
		popupView.setVisibility(View.VISIBLE);
		popupText = (TextView) Activa.myApp.findViewById(R.id.popupText);
		ImageView animationFrame = (ImageView) Activa.myApp.findViewById(R.id.popupImage);
		animationFrame.setVisibility(View.VISIBLE);
		animationFrame.setBackgroundResource(R.drawable.loading);
		this.animation = (AnimationDrawable) animationFrame.getBackground();
	}

	@Override
	public void run() {			
		this.animation.start();
		handler.sendEmptyMessage(4);
		int success = Activa.myMobileManager.checkUserExistance();
		handler.sendEmptyMessage(success);
	}

	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case 0:		
					animation.stop();
					Activa.myMobileManager.user.setCreated(true);
					Activa.myUIManager.loadMatrixPasswordForRegistering();
					break;
				case 1:
					animation.stop();
					Activa.myUIManager.loadCheckUserScreen();
					Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.PSW_REG_BADPASSWRD);
					break;
				case 2:
					animation.stop();
					Activa.myUIManager.loadCheckUserScreen();
					Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.PSW_REG_USEREXISTS);
					break;
				case 3:
					animation.stop();
					Activa.myUIManager.loadInfoPopupLong(Activa.myLanguageManager.CONNECTION_FAILED + " " + Activa.myLanguageManager.CONNECTION_LIMITED);
					break;
				case 4:
					popupText.setText(Activa.myLanguageManager.CONNECTION_CHECKING);
					break;
			}
		}

	};

}

