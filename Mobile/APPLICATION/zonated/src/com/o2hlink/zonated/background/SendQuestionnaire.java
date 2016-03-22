package com.o2hlink.zonated.background;

import com.o2hlink.activ8.client.entity.QuestionnaireSample;
import com.o2hlink.zonated.R;
import com.o2hlink.zonated.Zonated;

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
	boolean success = true;
	
	public SendQuestionnaire(Long questionnaire, QuestionnaireSample sample) {
		this.questionnaire = questionnaire;
		this.sample = sample;
		((TextView) Zonated.myApp.findViewById(R.id.infoText)).setText(Zonated.myLanguageManager.CONNECTION_SENDING);
		ImageView animationFrame = (ImageView) Zonated.myApp.findViewById(R.id.progress);
		animationFrame.setVisibility(View.VISIBLE);
		animationFrame.setBackgroundResource(R.drawable.loadingbig);
		this.animation = (AnimationDrawable) animationFrame.getBackground();
	}

	@Override
	public void run() {			
		success = true;
		if (!Zonated.myMobileManager.identified) {
			handler.sendEmptyMessage(0);
			return; 
		}
		this.animation.start();
		success = Zonated.myQuestControlManager.sendQuestionnaire(questionnaire, sample);
		handler.sendEmptyMessage(0);
	}

	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case 0:
//					animation = (AnimationDrawable)((ImageView) Zonated.myApp.findViewById(R.id.progress)).getBackground();
//					animation.stop();
					Zonated.myUIManager.UIquest.loadSharedQuestionnaires();
					if (!success)  Zonated.myUIManager.UImisc.loadInfoPopup(Zonated.myLanguageManager.CONNECTION_MESSAGE_NOTSENT);
					break;
			}
		}

	};

}

