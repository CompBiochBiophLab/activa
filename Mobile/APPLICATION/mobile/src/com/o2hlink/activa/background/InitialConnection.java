package com.o2hlink.activa.background;

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
import com.o2hlink.activa.Activa;
import com.o2hlink.activa.R;
import com.o2hlink.activa.data.PendingDataManager;
import com.o2hlink.activa.data.calendar.Event;
import com.o2hlink.activa.data.sensor.SensorManager;
import com.o2hlink.activa.exceptions.NotUpdatedException;
import com.o2hlink.activa.notes.Note;
import com.o2hlink.activa.ui.UIManager;

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
		popupView = (RelativeLayout) Activa.myApp.findViewById(R.id.popupView);
		popupView.setVisibility(View.VISIBLE);
		popupText = (TextView) Activa.myApp.findViewById(R.id.popupText);
		ImageView animationFrame = (ImageView) Activa.myApp.findViewById(R.id.popupImage);
		animationFrame.setVisibility(View.VISIBLE);
		animationFrame.setBackgroundResource(R.drawable.loading);
		this.animation = (AnimationDrawable) animationFrame.getBackground();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void run() {	
		Activa.myApp.refreshing_foreground = true;
		animation.start();
		handler.sendEmptyMessage(100);
		boolean success = false;
		if (!Activa.myMobileManager.user.isCreated()) {
			success = Activa.myMobileManager.register();
		}
		else success = Activa.myMobileManager.login();
		if (success) {
			if (!Activa.myMobileManager.user.isCreated()) {
				Activa.myMobileManager.user.setCreated(true);
				Activa.myMobileManager.addUserWithPassword(Activa.myMobileManager.user);
			}
			Activa.myMobileManager.checkMeasurements();
		    /* Send pending data */
	        Activa.myPendingDataManager = PendingDataManager.getInstance();
			if (!Activa.myPendingDataManager.pendingNotes.isEmpty()) handler.sendEmptyMessage(8);
		    ArrayList<Note> tempNotes = (ArrayList<Note>) Activa.myPendingDataManager.pendingNotes.clone();
		    Activa.myPendingDataManager.pendingNotes.clear();
		    Iterator<Note> it2 = tempNotes.iterator();
		    while (it2.hasNext()) {
		    	Note pendData = it2.next();
		    	if (Activa.myNotesManager.sendNote(pendData.text) == null) Activa.myPendingDataManager.pendingNotes.add(pendData);
		    }
			if (!Activa.myPendingDataManager.pendingEvent.isEmpty()) handler.sendEmptyMessage(8);
		    ArrayList<Event> tempEvent = (ArrayList<Event>) Activa.myPendingDataManager.pendingEvent.clone();
		    Activa.myPendingDataManager.pendingEvent.clear();
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
		    	if (Activa.myCalendarManager.AddEvent(eventtoadd)) Activa.myPendingDataManager.pendingEvent.add(pendData);
		    }
			if (!Activa.myPendingDataManager.pendingPatientEvent.isEmpty()) handler.sendEmptyMessage(8);
		    ArrayList<com.o2hlink.activa.patient.Event> tempPatEvent = (ArrayList<com.o2hlink.activa.patient.Event>) Activa.myPendingDataManager.pendingPatientEvent.clone();
		    Activa.myPendingDataManager.pendingPatientEvent.clear();
		    Iterator<com.o2hlink.activa.patient.Event> it4 = tempPatEvent.iterator();
		    while (it4.hasNext()) {
		    	com.o2hlink.activa.patient.Event pendData = it4.next();
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
						if (meas != null) suc = Activa.myPatientManager.addMeasEvent(pendData.userId, meas, eventtoadd);
						break;
					case 1:
						suc = Activa.myPatientManager.addQuestEvent(pendData.userId, pendData.subtype, eventtoadd);
						break;
					default:
						break;
				}
		    	if (suc) Activa.myPendingDataManager.pendingPatientEvent.add(pendData);
		    }
		    if (!Activa.myPendingDataManager.pendingMessages.isEmpty()) handler.sendEmptyMessage(8);
		    ArrayList<com.o2hlink.activa.data.Message> tempMessages = (ArrayList<com.o2hlink.activa.data.Message>) Activa.myPendingDataManager.pendingMessages.clone();
		    Activa.myPendingDataManager.pendingMessages.clear();
		    Iterator<com.o2hlink.activa.data.Message> it5 = tempMessages.iterator();
		    while (it5.hasNext()) {
		    	com.o2hlink.activa.data.Message pendMsg = it5.next();
		    	if (!Activa.myMessageManager.sendO2Message(pendMsg.message, pendMsg.content)) Activa.myPendingDataManager.pendingMessages.add(pendMsg);
		    }
		    if (!Activa.myPendingDataManager.pendingActions.isEmpty()) handler.sendEmptyMessage(8);
		    ArrayList<Action<?>> tempActions = (ArrayList<Action<?>>) Activa.myPendingDataManager.pendingActions.clone();
		    Activa.myPendingDataManager.pendingActions.clear();
		    Iterator<Action<?>> it6 = tempActions.iterator();
		    while (it6.hasNext()) {
		    	Action<?> pendAction = it6.next();
		    	try {
					if (!Activa.myProtocolManager.dispatch(pendAction)) Activa.myPendingDataManager.pendingActions.add(pendAction);
				} catch (NotUpdatedException e) {
					Activa.myUIManager.loadTextOnWindow(Activa.myLanguageManager.TEXT_UPDATEVERSION);
					e.printStackTrace();
				}
		    }
		    Activa.myPendingDataManager.passPendingDataToFile();
		    Activa.myPendingDataManager.passPendingActionsToFile();
		    /* Send the elements */
			handler.sendEmptyMessage(0);
			Activa.myQuestControlManager.getQuestionnaires();
		    handler.sendEmptyMessage(15);
		    Activa.myPatientManager.getPatientList();
			handler.sendEmptyMessage(1);
		    Activa.myCalendarManager.getCalendar();
			handler.sendEmptyMessage(5);
		    Activa.myNewsManager.getNews();
		    handler.sendEmptyMessage(55);
		    Activa.myMessageManager.requestContactList();
			Activa.myMessageManager.requestEntryContactList();
			handler.sendEmptyMessage(6);
			Date now = new Date();
			now.setDate(now.getDate() - 15);
		    Activa.myMessageManager.requestReceivedMessages(0);
		    Activa.myMessageManager.requestSentMessages(0);
			handler.sendEmptyMessage(66);
		    Activa.myNotesManager.getNotes();
//			handler.sendEmptyMessage(7);
//			/* Update event status and send event outcomes for canceled events */
//		    Enumeration<Event> enumeration = Activa.myCalendarManager.events.elements();
//		    while (enumeration.hasMoreElements()) {
//		    	Event event = enumeration.nextElement();
//		    	event.updateState();
//		    }
			handler.sendEmptyMessage(2);
			Activa.myApp.refreshing_foreground = false;
		}
		else {
			switch (Activa.myMobileManager.user.getType()) {
				case 0:
					Activa.myMobileManager.userForServices = new Patient();
					break;
				case 1:
					Activa.myMobileManager.userForServices = new Clinician();
					break;
				case 2:
					Activa.myMobileManager.userForServices = new Researcher();
					break;
				default:
					Activa.myMobileManager.userForServices = new Patient();
					break;
			}
			Activa.myMobileManager.userForServices.setId(Activa.myMobileManager.user.getId());
			Activa.myMobileManager.userForServices.setBirthdate(Activa.myMobileManager.user.getBirthdate());
			Activa.myMobileManager.userForServices.setUsername(Activa.myMobileManager.user.getName());
			Activa.myMobileManager.userForServices.setFirstName(Activa.myMobileManager.user.getFirstname());
			Activa.myMobileManager.userForServices.setLastName(Activa.myMobileManager.user.getLastname());
			Activa.myMobileManager.userForServices.setEmail(Activa.myMobileManager.user.getMail());
			Activa.myMobileManager.userForServices.setSex(Activa.myMobileManager.user.getSex());
	        Activa.myPendingDataManager = PendingDataManager.getInstance();
		    Iterator<Note> it2 = Activa.myPendingDataManager.pendingNotes.iterator();
		    while (it2.hasNext()) {
		    	Note noted = it2.next();
				Activa.myNotesManager.notelist.put(noted.id, noted);
		    }
		    Iterator<Event> it3 = Activa.myPendingDataManager.pendingEvent.iterator();
		    while (it3.hasNext()) {
		    	Event meeting = it3.next();
				Activa.myCalendarManager.events.put(meeting.id, meeting);
		    }
			handler.sendEmptyMessage(3);
		}
        Activa.mySensorManager.getSensorDBFromFile();
        if (Activa.myMainBackgroundThread.running) Activa.myMainBackgroundThread.stop();
		Thread thread = new Thread(Activa.myMainBackgroundThread, "MAIN");
		thread.start();
		handler.sendEmptyMessage(4);
	}

	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case 0:
					popupText.setText(Activa.myLanguageManager.CONNECTION_QUESTIONNAIRES);
					break;
				case 100:
					if (!Activa.myMobileManager.user.isCreated()) popupText.setText(Activa.myLanguageManager.CONNECTION_REGISTERING);
					else popupText.setText(Activa.myLanguageManager.CONNECTION_CONNECTING);
					break;
				case 1:
					popupText.setText(Activa.myLanguageManager.CONNECTION_CALENDAR);
					break;
				case 15:
					popupText.setText(Activa.myLanguageManager.CONNECTION_PATIENTS);
					break;
				case 5:
					popupText.setText(Activa.myLanguageManager.CONNECTION_FEEDS);
					break;
				case 55:
					popupText.setText(Activa.myLanguageManager.CONNECTION_CONTACTS);
					break;
				case 6:
					popupText.setText(Activa.myLanguageManager.CONNECTION_MESSAGES);
					break;
				case 66:
					popupText.setText(Activa.myLanguageManager.CONNECTION_NOTES);
					break;
				case 7:
					popupText.setText(Activa.myLanguageManager.CONNECTION_UPDATING);
					break;
				case 8:
					popupText.setText(Activa.myLanguageManager.CONNECTION_PENDING);
					break;
				case 2:
					((ImageView) Activa.myApp.findViewById(R.id.popupImage)).setVisibility(View.GONE);
					animation.stop();
			    	popupText.setText(Activa.myLanguageManager.CONNECTION_CONNECTED1 + Activa.myMobileManager.user.getName() + Activa.myLanguageManager.CONNECTION_CONNECTED2);
					break;
				case 3:
					((ImageView) Activa.myApp.findViewById(R.id.popupImage)).setVisibility(View.GONE);
					animation.stop();
					popupText.setText(Activa.myLanguageManager.CONNECTION_FAILED);
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
		RelativeLayout popupView = (RelativeLayout) Activa.myApp.findViewById(R.id.popupView);
		if (popupView != null) popupView.setVisibility(View.INVISIBLE);
		ImageView promotionView = (ImageView) Activa.myApp.findViewById(R.id.popupPromotion);
		if (promotionView != null) promotionView.setVisibility(View.GONE);
		ImageView promotion = (ImageView) Activa.myApp.findViewById(R.id.promotion);
		if (promotion != null) promotion.setVisibility(View.GONE);
		ImageButton refresh = (ImageButton)Activa.myApp.findViewById(R.id.miscbutton);
		if (refresh != null) refresh.setVisibility(View.VISIBLE);
		Activa.myApp.refreshing_foreground = false;
		Activa.myUIManager.state = UIManager.UI_STATE_MAIN;
		if (Activa.myMessageManager.entryContactList.size() >= 1) Activa.myUIManager.loadTextOnWindow(Activa.myLanguageManager.TEXT_ENTRYCONTACTS);
	}

	@Override
	public void onTick(long millisUntilFinished) {
	}
}

