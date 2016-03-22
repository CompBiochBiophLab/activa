package com.o2hlink.healthgenius.background;

import com.o2hlink.activ8.client.entity.QuestionnaireSample;
import com.o2hlink.healthgenius.HealthGenius;
import com.o2hlink.healthgenius.R;
import com.o2hlink.healthgenius.data.calendar.Event;

import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class SendQuestionnaire implements Runnable {

	AnimationDrawable animation;
	Long questionnaire;
	QuestionnaireSample sample;
	Event event;
	boolean success = true;
	
	public SendQuestionnaire(Long questionnaire, QuestionnaireSample sample) {
		this.questionnaire = questionnaire;
		this.sample = sample;
		this.event = null;
		((TextView) HealthGenius.myApp.findViewById(R.id.infoText)).setText(HealthGenius.myLanguageManager.CONNECTION_SENDING);
		ImageView animationFrame = (ImageView) HealthGenius.myApp.findViewById(R.id.progress);
		animationFrame.setVisibility(View.VISIBLE);
		animationFrame.setBackgroundResource(R.drawable.loadingbig);
		this.animation = (AnimationDrawable) animationFrame.getBackground();
	}

	@Override
	public void run() {			
		success = true;
		if (!HealthGenius.myMobileManager.identified) {
			if (this.event != null) handler.sendEmptyMessage(1);
			else handler.sendEmptyMessage(0);
			return; 
		}
		this.animation.start();
		success = HealthGenius.myQuestControlManager.sendQuestionnaire(questionnaire, sample);
		if (this.event != null) handler.sendEmptyMessage(1);
		else handler.sendEmptyMessage(0);
	}

	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case 0:
//					animation = (AnimationDrawable)((ImageView) HealthGenius.myApp.findViewById(R.id.progress)).getBackground();
//					animation.stop();
					HealthGenius.myUIManager.UIquest.loadSharedQuestionnaires();
					if (!success)  HealthGenius.myUIManager.UImisc.loadInfoPopup(HealthGenius.myLanguageManager.CONNECTION_MESSAGE_NOTSENT);
					break;
				case 1:
//					animation = (AnimationDrawable)((ImageView) HealthGenius.myApp.findViewById(R.id.progress)).getBackground();
//					animation.stop();
					HealthGenius.myUIManager.UIcalendar.loadScheduleDay(HealthGenius.myQuestControlManager.eventAssociated.date);
					if (!success)  HealthGenius.myUIManager.UImisc.loadInfoPopup(HealthGenius.myLanguageManager.CONNECTION_MESSAGE_NOTSENT);
					break;
			}
		}

	};

}

