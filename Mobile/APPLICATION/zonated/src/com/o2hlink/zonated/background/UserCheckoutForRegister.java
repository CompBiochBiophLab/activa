package com.o2hlink.zonated.background;

import com.o2hlink.activ8.client.entity.Researcher;
import com.o2hlink.zonated.R;
import com.o2hlink.zonated.Zonated;

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
		popupView = (RelativeLayout) Zonated.myApp.findViewById(R.id.popupView);
		popupView.setVisibility(View.VISIBLE);
		popupText = (TextView) Zonated.myApp.findViewById(R.id.popupText);
		ImageView animationFrame = (ImageView) Zonated.myApp.findViewById(R.id.popupImage);
		animationFrame.setVisibility(View.VISIBLE);
		animationFrame.setBackgroundResource(R.drawable.loading);
		this.animation = (AnimationDrawable) animationFrame.getBackground();
	}

	@Override
	public void run() {			
		this.animation.start();
		handler.sendEmptyMessage(4);
		int success = Zonated.myMobileManager.checkUserExistance();
		handler.sendEmptyMessage(success);
	}

	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case 0:		
					animation.stop();
					Zonated.myUIManager.UIlogin.loadRegisterScreen(type);
					Zonated.myUIManager.UImisc.loadInfoPopup(Zonated.myLanguageManager.PSW_REG_USEREXISTS);
					break;
				case 1:
					animation.stop();
					Zonated.myUIManager.UIlogin.loadRegisterScreen(type);
					Zonated.myUIManager.UImisc.loadInfoPopup(Zonated.myLanguageManager.PSW_REG_USEREXISTS);
					break;
				case 2:
					animation.stop();
					Zonated.myUIManager.UIlogin.loadRegisterDataScreen(type);
					break;
				case 3:
					animation.stop();
					Zonated.myUIManager.UImisc.loadInfoPopupLong(Zonated.myLanguageManager.CONNECTION_FAILED + " " + Zonated.myLanguageManager.CONNECTION_LIMITED);
					break;
				case 4:
					popupText.setText(Zonated.myLanguageManager.CONNECTION_CHECKING);
					break;
			}
		}

	};

}

