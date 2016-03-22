package com.o2hlink.activa.background;

import com.o2hlink.activ8.client.entity.QuestionnaireSample;
import com.o2hlink.activa.Activa;
import com.o2hlink.activa.R;
import com.o2hlink.activa.data.calendar.Event;

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
			if (this.event != null) handler.sendEmptyMessage(1);
			else handler.sendEmptyMessage(0);
			return; 
		}
		this.animation.start();
		success = Activa.myQuestControlManager.sendQuestionnaire(questionnaire, sample);
		if (this.event != null) handler.sendEmptyMessage(1);
		else handler.sendEmptyMessage(0);
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
					Activa.myUIManager.loadScheduleDay(Activa.myQuestControlManager.eventAssociated.date);
					if (!success)  Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.CONNECTION_MESSAGE_NOTSENT);
					break;
			}
		}

	};

}

