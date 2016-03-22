package com.o2hlink.activa.background;

import com.o2hlink.activ8.client.entity.SimpleMessage;
import com.o2hlink.activa.Activa;
import com.o2hlink.activa.R;

import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;

public class SendO2Message implements Runnable {
	
	SimpleMessage message;
	String content;
	
	boolean success = true;
	
	AnimationDrawable animation;
	
	public SendO2Message(SimpleMessage message, String content) {
		this.message = message;
		this.content = content;
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
		success = Activa.myMessageManager.sendO2Message(message, content);
		handler.sendEmptyMessage(0);
	}

	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case 0:
//					animation = (AnimationDrawable)((ImageView) Activa.myApp.findViewById(R.id.progress)).getBackground();
//					animation.stop();
					Activa.myUIManager.loadReceivedMessageList(0);
					if (!success)  Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.CONNECTION_MESSAGE_NOTSENT);
					break;
			}
		}

	};

}

