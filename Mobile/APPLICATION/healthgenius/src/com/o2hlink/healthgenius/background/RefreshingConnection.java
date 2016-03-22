package com.o2hlink.healthgenius.background;

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
import com.o2hlink.healthgenius.HealthGenius;
import com.o2hlink.healthgenius.R;
import com.o2hlink.healthgenius.data.calendar.Event;
import com.o2hlink.healthgenius.data.sensor.SensorManager;
import com.o2hlink.healthgenius.exceptions.NotUpdatedException;
import com.o2hlink.healthgenius.ui.UIManager;

import android.graphics.drawable.AnimationDrawable;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class RefreshingConnection implements Runnable {

	int currentImage = 0;
	int nextImage = 0;
	RelativeLayout popupView;
	TextView popupText;
	AnimationDrawable animation;
	
	public RefreshingConnection() {
		popupView = (RelativeLayout) HealthGenius.myApp.findViewById(R.id.popupView);
		popupView.setVisibility(View.VISIBLE);
		popupText = (TextView) HealthGenius.myApp.findViewById(R.id.popupText);
		ImageView animationFrame = (ImageView) HealthGenius.myApp.findViewById(R.id.popupImage);
		animationFrame.setVisibility(View.VISIBLE);
		animationFrame.setBackgroundResource(R.drawable.loading);
		this.animation = (AnimationDrawable) animationFrame.getBackground();
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public void run() {			
		HealthGenius.myApp.refreshing_foreground = true;
		animation.start();
		handler.sendEmptyMessage(9);
		boolean success = false;
		if (!HealthGenius.myMobileManager.user.isCreated()) {
			success = HealthGenius.myMobileManager.register();
		}
		else success = HealthGenius.myMobileManager.login();
		if (success) {
			if (!HealthGenius.myMobileManager.user.isCreated()) {
				HealthGenius.myMobileManager.user.setCreated(true);
				HealthGenius.myMobileManager.addUserWithPassword(HealthGenius.myMobileManager.user);
			}
			HealthGenius.myMobileManager.checkMeasurements();
			/* Send pending data */
			if (!HealthGenius.myPendingDataManager.pendingEvent.isEmpty()) handler.sendEmptyMessage(8);
		    ArrayList<Event> tempEvent = (ArrayList<Event>) HealthGenius.myPendingDataManager.pendingEvent.clone();
		    HealthGenius.myPendingDataManager.pendingEvent.clear();
		    Iterator<Event> it3 = tempEvent.iterator();
		    while (it3.hasNext()) {
		    	Event pendData = it3.next();
		    	com.o2hlink.activ8.client.entity.Event eventtoadd = new com.o2hlink.activ8.client.entity.Event();
				eventtoadd.setName(pendData.name);
				eventtoadd.setDescription(pendData.description);
				eventtoadd.setStart(pendData.date);
				eventtoadd.setEnd(pendData.dateEnd);
				eventtoadd.setFrequency(EventFrequency.NONE);
				eventtoadd.setEnd(pendData.dateEnd);
		    	if (HealthGenius.myCalendarManager.AddEvent(eventtoadd)) HealthGenius.myPendingDataManager.pendingEvent.add(pendData);
		    }
			if (!HealthGenius.myPendingDataManager.pendingPatientEvent.isEmpty()) handler.sendEmptyMessage(8);
		    ArrayList<com.o2hlink.healthgenius.patient.Event> tempPatEvent = (ArrayList<com.o2hlink.healthgenius.patient.Event>) HealthGenius.myPendingDataManager.pendingPatientEvent.clone();
		    HealthGenius.myPendingDataManager.pendingPatientEvent.clear();
		    Iterator<com.o2hlink.healthgenius.patient.Event> it4 = tempPatEvent.iterator();
		    while (it4.hasNext()) {
		    	com.o2hlink.healthgenius.patient.Event pendData = it4.next();
		    	com.o2hlink.activ8.client.entity.Event eventtoadd = new com.o2hlink.activ8.client.entity.Event();
				eventtoadd.setName(pendData.name);
				eventtoadd.setDescription(pendData.description);
				eventtoadd.setStart(pendData.date);
				eventtoadd.setEnd(pendData.dateEnd);
				eventtoadd.setFrequency(EventFrequency.NONE);
				eventtoadd.setEnd(pendData.dateEnd);
				boolean suc = false;
				switch (pendData.type) {
					case 0:
						Measurement meas = null;
						if (pendData.subtype.equals(SensorManager.ID_PULSIOXYMETER)) meas = Measurement.PULSEOXYMETRY; 
						else if (pendData.subtype.equals(SensorManager.ID_SPIROMETER)) meas = Measurement.SPIROMETRY;
						else if (pendData.subtype.equals(SensorManager.ID_EXERCISE)) meas = Measurement.SIX_MINUTES_WALK;
						if (meas != null) suc = HealthGenius.myPatientManager.addMeasEvent(pendData.userId, meas, eventtoadd);
						break;
					case 1:
						suc = HealthGenius.myPatientManager.addQuestEvent(pendData.userId, pendData.subtype, eventtoadd);
						break;
					default:
						break;
				}
		    	if (suc) HealthGenius.myPendingDataManager.pendingPatientEvent.add(pendData);
		    }
		    if (!HealthGenius.myPendingDataManager.pendingActions.isEmpty()) handler.sendEmptyMessage(8);
		    ArrayList<Action<?>> tempActions = (ArrayList<Action<?>>) HealthGenius.myPendingDataManager.pendingActions.clone();
		    HealthGenius.myPendingDataManager.pendingActions.clear();
		    Iterator<Action<?>> it6 = tempActions.iterator();
		    while (it6.hasNext()) {
		    	Action<?> pendAction = it6.next();
		    	try {
					if (!HealthGenius.myProtocolManager.dispatch(pendAction)) HealthGenius.myPendingDataManager.pendingActions.add(pendAction);
				} catch (NotUpdatedException e) {
					HealthGenius.myUIManager.UImisc.loadTextOnWindow(HealthGenius.myLanguageManager.TEXT_UPDATEVERSION);
					e.printStackTrace();
				}
		    }
		    HealthGenius.myPendingDataManager.passPendingDataToFile();
		    HealthGenius.myPendingDataManager.passPendingActionsToFile();
		    /* Send the elements */
			handler.sendEmptyMessage(0);
			HealthGenius.myQuestControlManager.getQuestionnaires();
			handler.sendEmptyMessage(15);
		    HealthGenius.myPatientManager.getPatientList();
			handler.sendEmptyMessage(1);
		    HealthGenius.myCalendarManager.getCalendar();
			handler.sendEmptyMessage(5);
		    HealthGenius.myContactsManager.requestContactList();
			HealthGenius.myContactsManager.requestEntryContactList();
			handler.sendEmptyMessage(2);
			HealthGenius.myApp.refreshing_foreground = false;
		}
		else {
			switch (HealthGenius.myMobileManager.user.getType()) {
				case 0:
					HealthGenius.myMobileManager.userForServices = new Patient();
					break;
				case 1:
					HealthGenius.myMobileManager.userForServices = new Clinician();
					break;
				case 2:
					HealthGenius.myMobileManager.userForServices = new Researcher();
					break;
				default:
					HealthGenius.myMobileManager.userForServices = new Patient();
					break;
			}
			HealthGenius.myMobileManager.userForServices.setId(HealthGenius.myMobileManager.user.getId());
			HealthGenius.myMobileManager.userForServices.setBirthdate(HealthGenius.myMobileManager.user.getBirthdate());
			HealthGenius.myMobileManager.userForServices.setUsername(HealthGenius.myMobileManager.user.getName());
			HealthGenius.myMobileManager.userForServices.setFirstName(HealthGenius.myMobileManager.user.getFirstname());
			HealthGenius.myMobileManager.userForServices.setLastName(HealthGenius.myMobileManager.user.getLastname());
			HealthGenius.myMobileManager.userForServices.setEmail(HealthGenius.myMobileManager.user.getMail());
			HealthGenius.myMobileManager.userForServices.setSex(HealthGenius.myMobileManager.user.getSex());
	        Iterator<Event> it3 = HealthGenius.myPendingDataManager.pendingEvent.iterator();
		    while (it3.hasNext()) {
		    	Event meeting = it3.next();
				HealthGenius.myCalendarManager.events.put(meeting.id, meeting);
		    }
			handler.sendEmptyMessage(3);
		}
        HealthGenius.mySensorManager.getSensorDBFromFile();
		handler.sendEmptyMessage(4);
	}

	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case 9:
					popupText.setText(HealthGenius.myLanguageManager.CONNECTION_CONNECTING);
					break;
				case 0:
					popupText.setText(HealthGenius.myLanguageManager.CONNECTION_QUESTIONNAIRES);
					break;
				case 1:
					popupText.setText(HealthGenius.myLanguageManager.CONNECTION_CALENDAR);
					break;
				case 5:
					popupText.setText(HealthGenius.myLanguageManager.CONNECTION_CONTACTS);
					break;
				case 15:
					popupText.setText(HealthGenius.myLanguageManager.CONNECTION_PATIENTS);
					break;
				case 6:
					popupText.setText(HealthGenius.myLanguageManager.CONNECTION_MESSAGES);
					break;
				case 7:
					popupText.setText(HealthGenius.myLanguageManager.CONNECTION_UPDATING);
					break;
				case 10:
					popupText.setText(HealthGenius.myLanguageManager.CONNECTION_FEEDS);
					break;
				case 11:
					popupText.setText(HealthGenius.myLanguageManager.CONNECTION_NOTES);
					break;
				case 8:
					popupText.setText(HealthGenius.myLanguageManager.CONNECTION_PENDING);
					break;
				case 2:
					((ImageView) HealthGenius.myApp.findViewById(R.id.popupImage)).setVisibility(View.GONE);
					animation.stop();
			    	popupText.setText(HealthGenius.myLanguageManager.CONNECTION_CONNECTED1 + HealthGenius.myMobileManager.user.getName() + HealthGenius.myLanguageManager.CONNECTION_CONNECTED2);
					break;
				case 3:
					((ImageView) HealthGenius.myApp.findViewById(R.id.popupImage)).setVisibility(View.GONE);
					animation.stop();
					popupText.setText(HealthGenius.myLanguageManager.CONNECTION_FAILED);
					break;
				case 4:
					BeginTimeTaskRefreshing biniter = new BeginTimeTaskRefreshing(3000, 1000);
					biniter.start();
					break;
			}
		}

	};

}

class BeginTimeTaskRefreshing extends CountDownTimer {
	
	public BeginTimeTaskRefreshing(long millisInFuture, long countDownInterval) {
		super(millisInFuture, countDownInterval);
	}

	long startTime;

	@Override
	public void onFinish() {
		HealthGenius.myUIManager.state = UIManager.UI_STATE_MAIN;
		RelativeLayout popupView = (RelativeLayout) HealthGenius.myApp.findViewById(R.id.popupView);
		if (popupView != null) popupView.setVisibility(View.INVISIBLE);
		ImageButton refresh = (ImageButton)HealthGenius.myApp.findViewById(R.id.miscbutton);
		if (refresh != null) refresh.setVisibility(View.VISIBLE);
		HealthGenius.myApp.refreshing_foreground = false;
	}

	@Override
	public void onTick(long millisUntilFinished) {
	}
}

