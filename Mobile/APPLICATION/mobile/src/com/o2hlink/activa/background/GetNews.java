package com.o2hlink.activa.background;

import java.util.Enumeration;

import com.o2hlink.activa.Activa;
import com.o2hlink.activa.R;
import com.o2hlink.activa.news.Feed;

import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class GetNews implements Runnable {
	
	boolean success = true;
	
	int progress;
	
	AnimationDrawable animation;
	
	public GetNews() {
		ImageView animationFrame = (ImageView) Activa.myApp.findViewById(R.id.popupImage);
		animationFrame.setVisibility(View.VISIBLE);
		animationFrame.setBackgroundResource(R.drawable.loading);
		this.animation = (AnimationDrawable) animationFrame.getBackground();
		this.progress = 0;
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
		int i = 0;
		int feedsize = Activa.myNewsManager.feedslist.size();
		Enumeration<Feed> enumeration = Activa.myNewsManager.feedslist.elements();
		while (enumeration.hasMoreElements()) {
			Feed feed = enumeration.nextElement();
			if (!feed.getFeedFromRSS()) {
				Activa.myNewsManager.feedslist.remove(feed.id);
			}
			i++;
			progress = Math.round(((float)i/(float)feedsize)*100);
			handler.sendEmptyMessage(1);
		}
		handler.sendEmptyMessage(2);
	}

	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case 0:
					((RelativeLayout) Activa.myApp.findViewById(R.id.popupView)).setVisibility(View.VISIBLE);
					((TextView) Activa.myApp.findViewById(R.id.popupText)).setText(Activa.myLanguageManager.CONNECTION_NEWS + "\n" + progress + " %");
					break;
				case 1:
					((TextView) Activa.myApp.findViewById(R.id.popupText)).setText(Activa.myLanguageManager.CONNECTION_NEWS + "\n" + progress + " %");
					break;
				case 2:
//					animation = (AnimationDrawable)((ImageView) Activa.myApp.findViewById(R.id.progress)).getBackground();
//					animation.stop();
					Activa.myUIManager.loadNewsList(false);
					break;
			}
		}

	};

}

