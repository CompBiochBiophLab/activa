package com.o2hlink.pimtools.background;

import com.o2hlink.pimtools.R;
import com.o2hlink.pimtools.Activa;
import com.o2hlink.pimtools.patient.Patient;

import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class GetDocuments implements Runnable {
	
	boolean success = true;
	
	boolean returning;
	
	AnimationDrawable animation;
	
	public GetDocuments(boolean returning) {
		ImageView animationFrame = (ImageView) Activa.myApp.findViewById(R.id.popupImage);
		animationFrame.setVisibility(View.VISIBLE);
		animationFrame.setBackgroundResource(R.drawable.loading);
		this.animation = (AnimationDrawable) animationFrame.getBackground();
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
		if (Activa.myDocumentsManager.getDocuments()) handler.sendEmptyMessage(1);
		else handler.sendEmptyMessage(2);
	}

	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case 0:
					((RelativeLayout) Activa.myApp.findViewById(R.id.popupView)).setVisibility(View.VISIBLE);
					((TextView) Activa.myApp.findViewById(R.id.popupText)).setText(Activa.myLanguageManager.CONNECTION_DOCUMENTS);
					break;
				case 1:
					Activa.myUIManager.loadDocumentScreen();
					break;
				case 2:
					Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.CONNECTION_PROBLEM);
					break;
			}
		}

	};

}

