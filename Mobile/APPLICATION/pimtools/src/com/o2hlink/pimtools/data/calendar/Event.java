package com.o2hlink.pimtools.data.calendar;

import java.util.Date;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.o2hlink.activ8.client.entity.Questionnaire;
import com.o2hlink.activ8.client.entity.Sample;
import com.o2hlink.pimtools.R;
import com.o2hlink.pimtools.Activa;
import com.o2hlink.pimtools.ActivaConfig;
import com.o2hlink.pimtools.ActivaUtil;
import com.o2hlink.pimtools.data.sensor.Sensor;
import com.o2hlink.pimtools.ui.UIManager;

/**
 * @author Adrian Rejas<P>
 * 
 * This class will represent the appointments marked at the calendar, dealing with their
 * creation, destruction, management and the initialization of the activities associated
 * with the appointment.
 */
public class Event {

	/**
	 * The ID of the appointment, describing the date of creation and a character informing
	 * about if the appointment was created by O2H middleware ("0") of by the mobile device
	 * ("1").
	 */
	public String id;
	
	/**
	 * The name for the appointment.
	 */
	public String name;
	
	/**
	 * The description for the appointment.
	 */
	public String description;
	
	/**
	 * The date associated with the appointment for being done.
	 */
	public Date date;
	
	/**
	 * The date associated with the appointment for being finished.
	 */
	public Date dateEnd;
	
	/**
	 * The type, indicating if it's a sensor measurement (0) or a questionnaire (1) or a undefined event (2)
	 */
	public int type;
	
	/**
	 * The subtype, indicating the ID of the sensor or questionnaire related with the
	 * appointment (depending of the type).
	 */
	public Long subtype;
	
	/**
	 * The state of the event (0 for done, 1 for not done and 2 for pending).
	 */
	public int state;
	
	/**
	 * The sample of the event if it was yet done.
	 */
	public Sample sample;
	
	/**
	 * The sample of the quest if it was yet done.
	 */
	public QuestionnaireSample questsample;
	
	/**
	 * public basic constructor.
	 */
	public Event() {
		this.id = "0";
		this.name = null;
		this.date = null;
		this.type = -1;
		this.subtype = -1l;
		this.state = 1;
	}

	/**
	 * The public constructor using the parameters.
	 * 
	 * @param id
	 * @param date
	 * @param type
	 * @param subtype
	 */
	public Event(String id, String name, Date date, int type, long subtype, int state) {
		this.id = id;
		this.name = name;
		this.date = date;
		this.type = type;
		this.subtype = subtype;
		this.state = state;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * @return the type
	 */
	public int getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(int type) {
		this.type = type;
	}

	/**
	 * @return the subtype
	 */
	public long getSubtype() {
		return subtype;
	}

	/**
	 * @param subtype the subtype to set
	 */
	public void setSubtype(long subtype) {
		this.subtype = subtype;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the state
	 */
	public int getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(int state) {
		this.state = state;
	}
	
	/**
	 * Update the event state from pending to not done if it corresponds.
	 */
	public void updateState() {
		if (this.state == 2) {
			Date limitlate = new Date();
			if (this.dateEnd.after(limitlate)) {
				this.state = 1;
			}
		}
	}
	
	/**
	 * Execute the activity related with this event.
	 */
	public void doActivity(){
		Activa.mySensorManager.eventAssociated = null;
		Activa.myQuestControlManager.eventAssociated = null;
		Date now = new Date();
		Date timeToExecute = new Date();
		timeToExecute.setTime(this.date.getTime() - (ActivaConfig.UPDATES_TIMEOUT/2));
		if (now.before(timeToExecute)) {
			RelativeLayout popupView = (RelativeLayout) Activa.myApp.findViewById(R.id.popupView);
			popupView.setVisibility(View.VISIBLE);
			TextView popupText = (TextView) Activa.myApp.findViewById(R.id.popupText);
			popupText.setText(Activa.myLanguageManager.CALENDAR_EARLY);
			PopupTimeTask popuptimer = new PopupTimeTask(3000, 1000);
			popuptimer.start();
			return;
		}
		if (this.state == 0) {
			final Event event = this;
			Runnable run;
			Thread thread;
			switch (this.type) {
				case 0:
					run = new Runnable() {
						@Override
						public void run() {
							handler.sendEmptyMessage(0);
							if (Activa.mySensorManager.getMeasurementSample(event))
								handler.sendEmptyMessage(1);
							else handler.sendEmptyMessage(2);
						}
						private Handler handler = new Handler() {
							@Override
							public void handleMessage(Message msg) {
								ImageView animationFrame = null;
								AnimationDrawable animation = null;
								switch (msg.what) {
									case 0:
										animationFrame = (ImageView) Activa.myApp.findViewById(R.id.animation);
										animationFrame.setVisibility(View.VISIBLE);
										animationFrame.setBackgroundResource(R.drawable.loading);
										animation = (AnimationDrawable) animationFrame.getBackground();
										animation.start();
										break;
									case 1:
										if (subtype.equals(com.o2hlink.pimtools.data.sensor.SensorManager.ID_PULSIOXYMETER)) {
											Activa.myUIManager.showMeasResults(sample, true);
										}
										else if (subtype.equals(com.o2hlink.pimtools.data.sensor.SensorManager.ID_SPIROMETER)) {
											Activa.mySensorManager.getSpiroGraphs(sample.getDate(), sample);
											Activa.myUIManager.showMeasResults(sample, true);
										}
										else if (subtype.equals(com.o2hlink.pimtools.data.sensor.SensorManager.ID_SIXMINUTES)) {
											Activa.mySensorManager.getSixMinutesGraphs(sample.getDate(), sample);
											Activa.myUIManager.showMeasResults(sample, true);
										}	
										else if (subtype.equals(com.o2hlink.pimtools.data.sensor.SensorManager.ID_WEIGHT)) {
											Activa.myUIManager.showMeasResults(sample, true);
										}
										else if (subtype.equals(com.o2hlink.pimtools.data.sensor.SensorManager.ID_EXERCISE)) {
											Activa.myUIManager.showMeasResults(sample, true);
										}	
										break;
									case 2:
										animationFrame = (ImageView) Activa.myApp.findViewById(R.id.animation);
										animationFrame.setVisibility(View.GONE);
										animationFrame.setBackgroundResource(R.drawable.loading);
										animation = (AnimationDrawable) animationFrame.getBackground();
										animation.stop();
										Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.CONNECTION_FAILED);
										break;
								}
							}

						};
					};
					thread = new Thread(run);
					thread.start();
					break;
				case 1:
					run = new Runnable() {
						@Override
						public void run() {
							handler.sendEmptyMessage(0);
							boolean success = Activa.myQuestControlManager.getQuestSample(event);
							if (success&&(Activa.myQuestControlManager.getQuestionnaire(subtype))) {
								if (Activa.myQuestControlManager.validateQuestionnaire()) handler.sendEmptyMessage(1);
								else handler.sendEmptyMessage(3);
							}
							else handler.sendEmptyMessage(2);
						}
						private Handler handler = new Handler(){
							@Override
							public void handleMessage(Message msg) {
								ImageView animationFrame;
								AnimationDrawable animation;
								switch (msg.what) {
									case 0:
										Activa.myUIManager.loadInfoPopupWithoutExiting(Activa.myLanguageManager.NOTIFICATION_DOWNLOADING + " data ...");
										animationFrame = (ImageView) Activa.myApp.findViewById(R.id.popupImage);
										animationFrame.setVisibility(View.VISIBLE);
										animationFrame.setBackgroundResource(R.drawable.loading);
										animation = (AnimationDrawable) animationFrame.getBackground();
										animation.start();
										break;
									case 1:
										animationFrame = (ImageView) Activa.myApp.findViewById(R.id.popupImage);
										animation = (AnimationDrawable) animationFrame.getBackground();
										animation.stop();
										animationFrame.setVisibility(View.GONE);
										Activa.myUIManager.showQuestResults((com.o2hlink.activ8.client.entity.QuestionnaireSample) questsample, true, name);
										break;
									case 2:
										animationFrame = (ImageView) Activa.myApp.findViewById(R.id.popupImage);
										animation = (AnimationDrawable) animationFrame.getBackground();
										animation.stop();
										animationFrame.setVisibility(View.GONE);
										Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.CONNECTION_FAILED);
										break;
									case 3:
										animationFrame = (ImageView) Activa.myApp.findViewById(R.id.popupImage);
										animation = (AnimationDrawable) animationFrame.getBackground();
										animation.stop();
										animationFrame.setVisibility(View.GONE);
										Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.QUEST_NOTVALID);
										break;
								}
							}
						};
					};
					thread = new Thread(run);
					thread.start();
					break;
				case 2:
					Activa.myUIManager.showEventInfo(this);
					break;
			}
		}
		else switch (this.type) {
			case 0:
				if (!Activa.myUIManager.ambient.sensorservice) {
					Activa.myUIManager.loadScheduleDay(new Date());
					Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.MAIN_BUYAMBIENTWITHMONITOR);
					return;
				}
				Activa.mySensorManager.eventAssociated = this;
				Sensor sensor = Activa.mySensorManager.sensorList.get(this.subtype);
				if (sensor != null) {
					sensor.startMeasurement();
					break;
				}
				break;
			case 1:
				Activa.myQuestControlManager.eventAssociated = this;
				Questionnaire quest = Activa.myQuestControlManager.questionnaires.get(this.subtype);
				if (quest != null) {
					Activa.myUIManager.startQuestionnaire(quest);
				}
				else Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.QUEST_NOTVALID);
				break;
			case 2:
				Activa.myUIManager.showEventInfo(this);
				break;
		}
	}
	
	/**
	 * Show a notification for the event when the moment is the suitable.
	 */
	public void setAlert() {
		if ((type == 2)&&(state != 2)) {
			state = 0;
			return;
		}
		if (state != 0) {
			Intent contentIntent = new Intent(Activa.myApp, Activa.class);
			PendingIntent pendingIntent = PendingIntent.getActivity(Activa.myApp, 0,contentIntent, 0); 
			CharSequence message;
			if (state == 2) message = Activa.myLanguageManager.NOTIFICATION_TIMETO + this.name;
			else message =  this.name + Activa.myLanguageManager.NOTIFICATION_FORGOTTEN + ActivaUtil.dateToReadableString(this.date);
			Notification notification = new Notification(R.drawable.icon, message, System.currentTimeMillis());
			notification.flags = Notification.FLAG_AUTO_CANCEL;
			notification.defaults = Notification.DEFAULT_ALL;
			notification.setLatestEventInfo(Activa.myApp, Activa.myLanguageManager.NOTIFICATION_TITLE, message, pendingIntent);
			Activa.myNotificationManager.notify(0, notification);		
			Activa.myUIManager.state = UIManager.UI_STATE_SCHEDULE;			
		}
	}	
	
	/**
	 * Add this event to the Android Calendar with the given calendar ID.
	 * 
	 * @param calId
	 */
	public void synchronizeWithAndroidCalendar(String calId) {
		ContentValues event = new ContentValues();
		event.put("calendar_id", calId);
		event.put("title", this.name);
		event.put("description", Activa.myLanguageManager.NOTIFICATION_DESC + this.name);
		event.put("eventLocation", Activa.myMobileManager.user.getName());
		long startTime = this.date.getTime();
		long endTime = this.date.getTime() + 900000;
		event.put("dtstart", startTime);
		event.put("dtend", endTime);
		Uri eventsUri = Uri.parse("content://calendar/events");
		Activa.myApp.getContentResolver().insert(eventsUri, event);
	}
	
}

class PopupTimeTask extends CountDownTimer {
	
	public PopupTimeTask(long millisInFuture, long countDownInterval) {
		super(millisInFuture, countDownInterval);
	}

	long startTime;

	@Override
	public void onFinish() {
		RelativeLayout popupView = (RelativeLayout) Activa.myApp.findViewById(R.id.popupView);
		popupView.setVisibility(View.GONE);
	}

	@Override
	public void onTick(long millisUntilFinished) {
	}
}
