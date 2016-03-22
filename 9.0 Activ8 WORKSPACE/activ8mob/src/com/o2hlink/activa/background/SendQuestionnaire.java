package com.o2hlink.activa.background;

import java.security.KeyStore.LoadStoreParameter;

import com.o2hlink.activa.Activa;
import com.o2hlink.activa.R;
import com.o2hlink.activa.data.PendingDataManager;
import com.o2hlink.activa.data.questionnaire.Question;
import com.o2hlink.activa.data.questionnaire.Questionnaire;

import android.graphics.drawable.AnimationDrawable;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SendQuestionnaire implements Runnable {

	AnimationDrawable animation;
	Questionnaire questionnaire;
	boolean success = true;
	
	public SendQuestionnaire(Questionnaire questionnaire) {
		this.questionnaire = questionnaire;
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
			if (Activa.myQuestManager.eventAssociated != null) handler.sendEmptyMessage(1);
			else handler.sendEmptyMessage(0);
			return; 
		}
		this.animation.start();
		if (Activa.myQuestManager.eventAssociated != null) {
			success = Activa.myProtocolManager.sendQuestAnswered(this.questionnaire);
//			Activa.myProtocolManager.sendEventOutcome(Activa.myQuestManager.eventAssociated);
			handler.sendEmptyMessage(1);
		}
		else {
			success = Activa.myProtocolManager.sendQuestAnswered(this.questionnaire);
			handler.sendEmptyMessage(0);
		}
		this.questionnaire.clean();
	}

	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case 0:
//					animation = (AnimationDrawable)((ImageView) Activa.myApp.findViewById(R.id.progress)).getBackground();
//					animation.stop();
					Activa.myUIManager.loadTotalQuestList();
					if (!success)  Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.CONNECTION_MESSAGE_NOTSENT);
					break;
				case 1:
//					animation = (AnimationDrawable)((ImageView) Activa.myApp.findViewById(R.id.progress)).getBackground();
//					animation.stop();
					Activa.myUIManager.loadScheduleDay(Activa.myQuestManager.eventAssociated.date);
					if (!success)  Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.CONNECTION_MESSAGE_NOTSENT);
					break;
			}
		}

	};

}

