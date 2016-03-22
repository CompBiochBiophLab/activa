package com.o2hlink.zonated.background;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import com.o2hlink.activ8.client.action.Action;
import com.o2hlink.activ8.client.entity.Clinician;
import com.o2hlink.activ8.client.entity.Patient;
import com.o2hlink.activ8.client.entity.Researcher;
import com.o2hlink.activ8.common.entity.EventFrequency;
import com.o2hlink.activ8.common.entity.Measurement;
import com.o2hlink.activ8.common.entity.Sex;
import com.o2hlink.zonated.R;
import com.o2hlink.zonated.Zonated;
import com.o2hlink.zonated.data.PendingDataManager;
import com.o2hlink.zonated.exceptions.NotUpdatedException;
import com.o2hlink.zonated.ui.UIManager;

import android.graphics.drawable.AnimationDrawable;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class InitialConnection implements Runnable {

	int currentImage = 0;
	int nextImage = 0;
	RelativeLayout popupView;
	TextView popupText;
	AnimationDrawable animation;
	
	public InitialConnection() {
		popupView = (RelativeLayout) Zonated.myApp.findViewById(R.id.popupView);
		popupView.setVisibility(View.VISIBLE);
		popupText = (TextView) Zonated.myApp.findViewById(R.id.popupText);
		ImageView animationFrame = (ImageView) Zonated.myApp.findViewById(R.id.popupImage);
		animationFrame.setVisibility(View.VISIBLE);
		animationFrame.setBackgroundResource(R.drawable.loading);
		this.animation = (AnimationDrawable) animationFrame.getBackground();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void run() {	
		Zonated.myApp.refreshing_foreground = true;
		animation.start();
		handler.sendEmptyMessage(100);
		boolean success = false;
		if (!Zonated.myMobileManager.user.isCreated()) {
			success = Zonated.myMobileManager.register();
		}
		else success = Zonated.myMobileManager.login();
		if (success) {
			if (!Zonated.myMobileManager.user.isCreated()) {
				Zonated.myMobileManager.user.setCreated(true);
				Zonated.myMobileManager.addUserWithPassword(Zonated.myMobileManager.user);
			}
			Zonated.myMobileManager.checkMeasurements();
		    /* Send pending data */
	        Zonated.myPendingDataManager = PendingDataManager.getInstance();
			if (!Zonated.myPendingDataManager.pendingActions.isEmpty()) handler.sendEmptyMessage(8);
		    ArrayList<Action<?>> tempActions = (ArrayList<Action<?>>) Zonated.myPendingDataManager.pendingActions.clone();
		    Zonated.myPendingDataManager.pendingActions.clear();
		    Iterator<Action<?>> it6 = tempActions.iterator();
		    while (it6.hasNext()) {
		    	Action<?> pendAction = it6.next();
		    	try {
					if (!Zonated.myProtocolManager.dispatch(pendAction)) Zonated.myPendingDataManager.pendingActions.add(pendAction);
				} catch (NotUpdatedException e) {
					Zonated.myUIManager.UImisc.loadTextOnWindow(Zonated.myLanguageManager.TEXT_UPDATEVERSION);
					e.printStackTrace();
				}
		    }
		    Zonated.myPendingDataManager.passPendingActionsToFile();
		    /* Send the elements */
			handler.sendEmptyMessage(0);
			Zonated.myQuestControlManager.getQuestionnaires();
		    handler.sendEmptyMessage(5);
		    Zonated.myContactsManager.requestContactList();
			Zonated.myContactsManager.requestEntryContactList();
			handler.sendEmptyMessage(2);
			Zonated.myApp.refreshing_foreground = false;
		}
		else {
			switch (Zonated.myMobileManager.user.getType()) {
				case 0:
					Zonated.myMobileManager.userForServices = new Patient();
					break;
				case 1:
					Zonated.myMobileManager.userForServices = new Clinician();
					break;
				case 2:
					Zonated.myMobileManager.userForServices = new Researcher();
					break;
				default:
					Zonated.myMobileManager.userForServices = new Patient();
					break;
			}
			Zonated.myMobileManager.userForServices.setId(Zonated.myMobileManager.user.getId());
			Zonated.myMobileManager.userForServices.setBirthdate(Zonated.myMobileManager.user.getBirthdate());
			Zonated.myMobileManager.userForServices.setUsername(Zonated.myMobileManager.user.getName());
			Zonated.myMobileManager.userForServices.setFirstName(Zonated.myMobileManager.user.getFirstname());
			Zonated.myMobileManager.userForServices.setLastName(Zonated.myMobileManager.user.getLastname());
			Zonated.myMobileManager.userForServices.setEmail(Zonated.myMobileManager.user.getMail());
			Zonated.myMobileManager.userForServices.setSex(Zonated.myMobileManager.user.getSex());
	        Zonated.myPendingDataManager = PendingDataManager.getInstance();
		   handler.sendEmptyMessage(3);
		}
        if (Zonated.myMainBackgroundThread.running) Zonated.myMainBackgroundThread.stop();
		Thread thread = new Thread(Zonated.myMainBackgroundThread, "MAIN");
		thread.start();
		handler.sendEmptyMessage(4);
	}

	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case 0:
					popupText.setText(Zonated.myLanguageManager.CONNECTION_QUESTIONNAIRES);
					break;
				case 100:
					if (!Zonated.myMobileManager.user.isCreated()) popupText.setText(Zonated.myLanguageManager.CONNECTION_REGISTERING);
					else popupText.setText(Zonated.myLanguageManager.CONNECTION_CONNECTING);
					break;
				case 1:
					popupText.setText(Zonated.myLanguageManager.CONNECTION_CALENDAR);
					break;
				case 15:
					popupText.setText(Zonated.myLanguageManager.CONNECTION_PATIENTS);
					break;
				case 5:
					popupText.setText(Zonated.myLanguageManager.CONNECTION_CONTACTS);
					break;
				case 7:
					popupText.setText(Zonated.myLanguageManager.CONNECTION_UPDATING);
					break;
				case 8:
					popupText.setText(Zonated.myLanguageManager.CONNECTION_PENDING);
					break;
				case 2:
					((ImageView) Zonated.myApp.findViewById(R.id.popupImage)).setVisibility(View.GONE);
					animation.stop();
			    	popupText.setText(Zonated.myLanguageManager.CONNECTION_CONNECTED1 + Zonated.myMobileManager.user.getName() + Zonated.myLanguageManager.CONNECTION_CONNECTED2);
					break;
				case 3:
					((ImageView) Zonated.myApp.findViewById(R.id.popupImage)).setVisibility(View.GONE);
					animation.stop();
					popupText.setText(Zonated.myLanguageManager.CONNECTION_FAILED);
					break;
				case 4:
					BeginTimeTask biniter = new BeginTimeTask(3000, 1000);
					biniter.start();
					break;
			}
		}

	};

}

class BeginTimeTask extends CountDownTimer {
	
	public BeginTimeTask(long millisInFuture, long countDownInterval) {
		super(millisInFuture, countDownInterval);
	}

	long startTime;

	@Override
	public void onFinish() {
//		RelativeLayout popupView = (RelativeLayout) Zonated.myApp.findViewById(R.id.popupView);
//		if (popupView != null) popupView.setVisibility(View.INVISIBLE);
//		ImageView promotionView = (ImageView) Zonated.myApp.findViewById(R.id.popupPromotion);
//		if (promotionView != null) promotionView.setVisibility(View.GONE);
//		TextView promotion = (TextView) Zonated.myApp.findViewById(R.id.popupPromotionText);
//		if (promotion != null) promotion.setVisibility(View.GONE);
//		ImageButton refresh = (ImageButton)Zonated.myApp.findViewById(R.id.previous);
//		if (refresh != null) refresh.setVisibility(View.VISIBLE);
//		Zonated.myApp.refreshing_foreground = false;
//		Zonated.myUIManager.state = UIManager.UI_STATE_MAIN;
		Zonated.myUIManager.UIquest.loadSharedQuestionnaires();
		if (Zonated.myContactsManager.entryContactList.size() >= 1) Zonated.myUIManager.UIcontacts.loadEntryContactList(false);
	}

	@Override
	public void onTick(long millisUntilFinished) {
	}
}

