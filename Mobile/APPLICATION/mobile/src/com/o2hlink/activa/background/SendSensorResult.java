package com.o2hlink.activa.background;

import com.o2hlink.activa.Activa;
import com.o2hlink.activa.R;
import com.o2hlink.activa.data.sensor.Sensor;

import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
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
		this.animation.start();
		if (Activa.mySensorManager.eventAssociated != null) {
			success = Activa.mySensorManager.sendSensorMeasurement(this.sensor);
			handler.sendEmptyMessage(1);
		}
		else {
			success = Activa.mySensorManager.sendSensorMeasurement(this.sensor);
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
			}
		}

	};

}

