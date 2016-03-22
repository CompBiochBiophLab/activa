package com.o2hlink.activa.background;

import java.security.KeyStore.LoadStoreParameter;

import com.o2hlink.activa.Activa;
import com.o2hlink.activa.R;
import com.o2hlink.activa.data.PendingDataManager;
import com.o2hlink.activa.data.message.O2Message;
import com.o2hlink.activa.data.message.O2UnregisteredMessage;
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

public class SendO2Message implements Runnable {
	
	O2UnregisteredMessage message;
	
	boolean success = true;
	
	AnimationDrawable animation;
	
	public SendO2Message(O2UnregisteredMessage message) {
		this.message = message;
		ImageView animationFrame = (ImageView) Activa.myApp.findViewById(R.id.progress);
		animationFrame.setVisibility(View.VISIBLE);
		animationFrame.setBackgroundResource(R.drawable.loadingbig);
		this.animation = (AnimationDrawable) animationFrame.getBackground();
	}

	@Override
	public void run() {		
		success = true;
		if (!Activa.myMobileManager.identified) {
			handler.sendEmptyMessage(0);
			return; 
		}
		this.animation.start();
		success = Activa.myProtocolManager.sendO2Message(this.message);
		handler.sendEmptyMessage(0);
	}

	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case 0:
//					animation = (AnimationDrawable)((ImageView) Activa.myApp.findViewById(R.id.progress)).getBackground();
//					animation.stop();
					Activa.myUIManager.loadMessageList();
					if (!success)  Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.CONNECTION_MESSAGE_NOTSENT);
					break;
			}
		}

	};

}

