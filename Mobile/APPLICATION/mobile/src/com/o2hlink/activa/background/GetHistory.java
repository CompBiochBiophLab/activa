package com.o2hlink.activa.background;
import com.o2hlink.activa.Activa;
import com.o2hlink.activa.R;
import com.o2hlink.activa.patient.Patient;

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
		ImageView animationFrame = (ImageView) Activa.myApp.findViewById(R.id.popupImage);
		animationFrame.setVisibility(View.VISIBLE);
		animationFrame.setBackgroundResource(R.drawable.loading);
		this.animation = (AnimationDrawable) animationFrame.getBackground();
		this.patient = patient;
		this.returning = returning;
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
		if (Activa.myPatientManager.getPatientHistory(patient)) handler.sendEmptyMessage(1);
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
					Activa.myUIManager.showHistory(patient, returning);
					break;
				case 2:
					Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.CONNECTION_PROBLEM);
					break;
			}
		}

	};

}

