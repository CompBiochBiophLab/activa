package com.o2hlink.activa.background;

import java.security.KeyStore.LoadStoreParameter;

import com.o2hlink.activa.Activa;
import com.o2hlink.activa.R;
import com.o2hlink.activa.data.PendingDataManager;
import com.o2hlink.activa.data.program.ProgramManager;
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

public class SendWalkingResult implements Runnable {

	AnimationDrawable animation;
	
	Sensor sensor;
	
	boolean success = true;
	
	public SendWalkingResult(Sensor sensor) {
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
			Activa.myUIManager.finishProgram();
		}
		this.animation.start();
//		if (Activa.myProgramManager.eventAssociated != null) {
			success = Activa.myProtocolManager.sendSensorMeasurement(this.sensor, ProgramManager.PROG_WALKING);
//			success = Activa.myProtocolManager.sendEventOutcome(Activa.mySensorManager.eventAssociated);
//		}
		handler.sendEmptyMessage(0);
	}

	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case 0:
//					animation = (AnimationDrawable)((ImageView) Activa.myApp.findViewById(R.id.progress)).getBackground();
//					animation.stop();
					Activa.myUIManager.finishProgram();
					if (!success) {
						TextView text = (TextView) Activa.myApp.findViewById(R.id.textInfo);
						text.append(Activa.myLanguageManager.CONNECTION_MESSAGE_PENDING);
					}
					break;
			}
		}

	};

}

