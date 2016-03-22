package com.o2hlink.healthgenius.background;

import com.o2hlink.healthgenius.HealthGenius;
import com.o2hlink.healthgenius.R;
import com.o2hlink.healthgenius.patient.Patient;

import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class GetHistory implements Runnable {
	
	boolean success = true;
	
	boolean returning;
	
	AnimationDrawable animation;
	
	Patient patient;
	
	public GetHistory(Patient patient, boolean returning) {
		ImageView animationFrame = (ImageView) HealthGenius.myApp.findViewById(R.id.popupImage);
		animationFrame.setVisibility(View.VISIBLE);
		animationFrame.setBackgroundResource(R.drawable.loading);
		this.animation = (AnimationDrawable) animationFrame.getBackground();
		this.patient = patient;
		this.returning = returning;
	}

	@Override
	public void run() {		
		success = true;
		if (!HealthGenius.myMobileManager.identified) {
			handler.sendEmptyMessage(2);
			return; 
		}
		handler.sendEmptyMessage(0);
		this.animation.start();
		if (HealthGenius.myPatientManager.getPatientHistory(patient)) handler.sendEmptyMessage(1);
		else handler.sendEmptyMessage(2);
	}

	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case 0:
					((RelativeLayout) HealthGenius.myApp.findViewById(R.id.popupView)).setVisibility(View.VISIBLE);
					((TextView) HealthGenius.myApp.findViewById(R.id.popupText)).setText(HealthGenius.myLanguageManager.CONNECTION_HISTORY);
					break;
				case 1:
					HealthGenius.myUIManager.UIpatient.showHistory(patient, returning);
					break;
				case 2:
					HealthGenius.myUIManager.UImisc.loadInfoPopup(HealthGenius.myLanguageManager.CONNECTION_PROBLEM);
					break;
			}
		}

	};

}

