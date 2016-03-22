package com.o2hlink.activa.background;

import com.o2hlink.activa.Activa;
import com.o2hlink.activa.R;
import com.o2hlink.activa.data.program.ProgramManager;
import com.o2hlink.activa.data.sensor.Sensor;

import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class SendWiiResult implements Runnable {

	AnimationDrawable animation;
	
	Sensor sensor;
	
	boolean success = true;
	
	public SendWiiResult(Sensor sensor) {
		this.sensor = sensor;
		((TextView) Activa.myApp.findViewById(R.id.infoText)).setText(Activa.myLanguageManager.CONNECTION_SENDING);
		ImageView animationFrame = (ImageView) Activa.myApp.findViewById(R.id.progress);
		animationFrame.setVisibility(View.VISIBLE);
		animationFrame.setBackgroundResource(R.drawable.loadingbig);
		this.animation = (AnimationDrawable) animationFrame.getBackground();
	}

	@Override
	public void run() {			
		success = false;
		if (!Activa.myMobileManager.identified) {
			Activa.myUIManager.finishProgram();
		}
		else {
			this.animation.start();
			success = Activa.myProtocolManager.sendSensorMeasurement(this.sensor, ProgramManager.PROG_WIIACTIVE);			
		}
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

