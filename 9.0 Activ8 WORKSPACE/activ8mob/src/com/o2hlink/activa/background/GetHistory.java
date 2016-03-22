package com.o2hlink.activa.background;

import java.security.KeyStore.LoadStoreParameter;
import java.util.Enumeration;

import com.o2hlink.activa.Activa;
import com.o2hlink.activa.R;
import com.o2hlink.activa.data.PendingDataManager;
import com.o2hlink.activa.data.message.O2Message;
import com.o2hlink.activa.data.message.O2UnregisteredMessage;
import com.o2hlink.activa.data.questionnaire.Question;
import com.o2hlink.activa.data.questionnaire.Questionnaire;
import com.o2hlink.activa.data.sensor.Sensor;
import com.o2hlink.activa.news.Feed;
import com.o2hlink.activa.patient.Patient;

import android.graphics.drawable.AnimationDrawable;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class GetHistory implements Runnable {
	
	boolean success = true;
	
	AnimationDrawable animation;
	
	Patient patient;
	
	public GetHistory(Patient patient) {
		ImageView animationFrame = (ImageView) Activa.myApp.findViewById(R.id.popupImage);
		animationFrame.setVisibility(View.VISIBLE);
		animationFrame.setBackgroundResource(R.drawable.loading);
		this.animation = (AnimationDrawable) animationFrame.getBackground();
		this.patient = patient;
	}

	@Override
	public void run() {		
		success = true;
		if (!Activa.myMobileManager.identified) {
			handler.sendEmptyMessage(2);
			return; 
		}
		handler.sendEmptyMessage(0);
		this.animation.start();
		if (Activa.myProtocolManager.getPatientHistory(patient)) handler.sendEmptyMessage(1);
		else handler.sendEmptyMessage(2);
	}

	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case 0:
					((RelativeLayout) Activa.myApp.findViewById(R.id.popupView)).setVisibility(View.VISIBLE);
					((TextView) Activa.myApp.findViewById(R.id.popupText)).setText(Activa.myLanguageManager.CONNECTION_HISTORY);
					break;
				case 1:
					Activa.myUIManager.showHistory(patient);
					break;
				case 2:
					Activa.myUIManager.loadPatientMenu(patient);
					Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.CONNECTION_PROBLEM);
					break;
			}
		}

	};

}

