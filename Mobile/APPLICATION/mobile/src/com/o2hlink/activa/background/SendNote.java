package com.o2hlink.activa.background;

import com.o2hlink.activa.Activa;
import com.o2hlink.activa.R;

import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SendNote implements Runnable {
	
	public String note;
	
	boolean success = true;
	
	int progress;
	
	AnimationDrawable animation;
	
	public SendNote(String note) {
		this.note = note;
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
			handler.sendEmptyMessage(1);
			return; 
		}
		handler.sendEmptyMessage(0);
		this.animation.start();
		success = Activa.myNotesManager.addNote(note);
		handler.sendEmptyMessage(1);
	}

	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case 0:
					((RelativeLayout) Activa.myApp.findViewById(R.id.popupView)).setVisibility(View.VISIBLE);
					((TextView) Activa.myApp.findViewById(R.id.popupText)).setText(Activa.myLanguageManager.CONNECTION_NOTE_SEND);
					break;
				case 1:
					Activa.myUIManager.loadNotes();
					if (!success) Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.CONNECTION_FAILED);
					break;
			}
		}

	};

}

