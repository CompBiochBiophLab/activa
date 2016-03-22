package com.o2hlink.activa.background;

import java.security.KeyStore.LoadStoreParameter;

import com.o2hlink.activa.Activa;
import com.o2hlink.activa.R;
import com.o2hlink.activa.data.PendingDataManager;
import com.o2hlink.activa.data.questionnaire.Question;
import com.o2hlink.activa.data.questionnaire.Questionnaire;
import com.o2hlink.activa.data.sensor.Sensor;

import android.graphics.drawable.AnimationDrawable;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SendSensorResult implements Runnable {

	AnimationDrawable animation;
	
	Sensor sensor;
	
	
	
	boolean success = true;
	
	public SendSensorResult(Sensor sensor) {
		this.sensor = sensor;
		((TextView) Activa.myApp.findViewById(R.id.infoText)).setText(Activa.myLanguageManager.CONNECTION_SENDING);
		ImageView animationFrame = (ImageView) Activa.myApp.findViewById(R.id.progress);
		animationFrame.setVisibility(View.VISIBLE);
		animationFrame.setBackgroundResource(R.drawable.loadingbig);
		this.animation = (AnimationDrawable) animationFrame.getBackground();
	}

	@Override
	public void run() {			
		success = true;
		if (!Activa.myMobileManager.identified) {
			if (Activa.mySensorManager.eventAssociated != null) {
				handler.sendEmptyMessage(1);
			}
			else handler.sendEmptyMessage(0);
			return; 
		}
//		else if (Activa.mySensorManager.programassociated != null) {
//			handler.sendEmptyMessage(2);
//		}
		this.animation.start();
		if (Activa.mySensorManager.eventAssociated != null) {
			success = Activa.myProtocolManager.sendSensorMeasurement(this.sensor);
			handler.sendEmptyMessage(1);
		}
		else if (Activa.mySensorManager.programassociated != null) {
			handler.sendEmptyMessage(2);
		}
		else {
			success = Activa.myProtocolManager.sendSensorMeasurement(this.sensor);
			handler.sendEmptyMessage(0);
		}
	}

	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case 0:
//					animation = (AnimationDrawable)((ImageView) Activa.myApp.findViewById(R.id.progress)).getBackground();
//					animation.stop();
					Activa.myUIManager.loadSensorList();
					if (!success)  Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.CONNECTION_MESSAGE_NOTSENT);
					break;
				case 1:
					animation = (AnimationDrawable)((ImageView) Activa.myApp.findViewById(R.id.progress)).getBackground();
					animation.stop();
					Activa.myUIManager.loadScheduleDay(Activa.mySensorManager.eventAssociated.date);
					if (!success)  Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.CONNECTION_MESSAGE_NOTSENT);
					break;
				case 2:
					animation = (AnimationDrawable)((ImageView) Activa.myApp.findViewById(R.id.progress)).getBackground();
					animation.stop();
					Activa.mySensorManager.programassociated.nextStep();
					break;
			}
		}

	};

}

