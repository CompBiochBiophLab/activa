package com.o2hlink.healthgenius.ui;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.provider.Contacts.Intents.UI;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.TableLayout.LayoutParams;

import com.o2hlink.activ8.common.entity.EventFrequency;
import com.o2hlink.healthgenius.HealthGenius;
import com.o2hlink.healthgenius.HealthGeniusUtil;
import com.o2hlink.healthgenius.R;
import com.o2hlink.healthgenius.data.calendar.Event;
import com.o2hlink.healthgenius.exterior.ExteriorManager;

public class UIManagerCalendar {
	
	public UIManager myUIManager;
	
	public UIManagerCalendar(UIManager ui) {
		myUIManager = ui;
	}
	
	public void loadScheduleDay(final Date dategiven) {
		TextView time;
		Event event = null; 
		Date date = new Date((dategiven.getTime()/3600000)*3600000);
		date.setHours(0);
		Date dateLater = new Date(date.getTime());
		dateLater.setHours(dateLater.getHours() + 1);
		TableRow buttonLayout;
		myUIManager.state = UIManager.UI_STATE_SCHEDULE;
		if (HealthGenius.myMenu != null) {
			HealthGenius.myMenu.clear();
			HealthGenius.myInflater.inflate(R.menu.schedule, HealthGenius.myMenu);
		}
		Hashtable<Date, Event> eventsOrdered = new Hashtable<Date,Event>();
		Vector<Date> datesOrdered = new Vector<Date>();
		Enumeration<Event> enumer = HealthGenius.myCalendarManager.events.elements();
		while (enumer.hasMoreElements()) {
			Event temp = enumer.nextElement();
			while (datesOrdered.contains(temp.date)) temp.date.setTime(temp.date.getTime() + 1);
			Timestamp stamp = new Timestamp(temp.date.getTime());
			datesOrdered.add(stamp);
			eventsOrdered.put(stamp, temp);
		}
		Collections.sort(datesOrdered);
		HealthGenius.myApp.setContentView(R.layout.scheduleday);
		((TextView) HealthGenius.myApp.findViewById(R.id.startText)).setText(HealthGenius.myLanguageManager.CALENDAR_TITLE);
		ImageButton back = (ImageButton) HealthGenius.myApp.findViewById(R.id.previous);
		ImageButton prev = (ImageButton) HealthGenius.myApp.findViewById(R.id.previousday);
		ImageButton next = (ImageButton) HealthGenius.myApp.findViewById(R.id.nextday);
		TextView dateText = (TextView) HealthGenius.myApp.findViewById(R.id.date);
		dateText.setText(HealthGeniusUtil.dateToReadableString(date));
		TableLayout schedule = (TableLayout)HealthGenius.myApp.findViewById(R.id.schedule);
		for (int i = 0; i < 24; i++) {	
			buttonLayout = new TableRow(HealthGenius.myApp);
			buttonLayout.setOrientation(TableRow.HORIZONTAL);
			buttonLayout.setGravity(Gravity.CENTER_VERTICAL);
			buttonLayout.setLayoutParams(new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, 40));			
			time = new TextView(HealthGenius.myApp);
			time.setText(String.format("%02d:00", date.getHours()));
			time.setPadding(5, 10, 5, 10);
			time.setTextSize(20);
			time.setTextColor(Color.BLACK);
			time.setTypeface(Typeface.SANS_SERIF);
			buttonLayout.addView(time);
			for(int j = 0; j < datesOrdered.size(); j++) {
				event = eventsOrdered.get(datesOrdered.get(j));
				final String id = event.id;
				if (date.compareTo(event.getDate()) == 0) {
					TextView activity = new TextView(HealthGenius.myApp);
					activity.setLayoutParams(new TableRow.LayoutParams(200, LayoutParams.WRAP_CONTENT));
					activity.setText(event.name);
					activity.setPadding(5, 10, 5, 10);
					activity.setTextSize(20);
					activity.setTextColor(Color.BLACK);
					activity.setTypeface(Typeface.SANS_SERIF);
					ImageView button = new ImageView(HealthGenius.myApp);
					switch (event.state) {
						case 0:
							button.setBackgroundResource(R.drawable.oksmall);
							break;
						case 1:
							button.setBackgroundResource(R.drawable.cancel);
							break;
						case 2:
							button.setBackgroundResource(R.drawable.nextsmall);
							break;
					}
					if (event.type == 2) button.setBackgroundResource(R.drawable.event);
					buttonLayout.addView(activity);
					buttonLayout.addView(button);	
					time.setOnClickListener(new OnClickListener() {				
						@Override
						public void onClick(View v) {
							if (HealthGenius.myMenu != null) HealthGenius.myMenu.clear();
							HealthGenius.myCalendarManager.events.get(id).doActivity();
						}
					});	
					activity.setOnClickListener(new OnClickListener() {				
						@Override
						public void onClick(View v) {
							if (HealthGenius.myMenu != null) HealthGenius.myMenu.clear();
							HealthGenius.myCalendarManager.events.get(id).doActivity();
						}
					});
					button.setOnClickListener(new OnClickListener() {				
						@Override
						public void onClick(View v) {
							if (HealthGenius.myMenu != null) HealthGenius.myMenu.clear();
							HealthGenius.myCalendarManager.events.get(id).doActivity();
						}
					});	
					buttonLayout.setOnClickListener(new OnClickListener() {				
						@Override
						public void onClick(View v) {
							if (HealthGenius.myMenu != null) HealthGenius.myMenu.clear();
							HealthGenius.myCalendarManager.events.get(id).doActivity();
						}
					});
				}
				else if ((date.compareTo(event.getDate()) < 0)&&(dateLater.compareTo(event.getDate()) > 0)) {
					schedule.addView(buttonLayout);
					View separator = new View(HealthGenius.myApp);
					LayoutParams separatorLayout = new LayoutParams(android.view.ViewGroup.LayoutParams.FILL_PARENT, 1);
					separator.setLayoutParams(separatorLayout);
					separator.setBackgroundColor(Color.BLACK);
					schedule.addView(separator);
					buttonLayout = new TableRow(HealthGenius.myApp);
					buttonLayout.setOrientation(TableRow.HORIZONTAL);
					buttonLayout.setGravity(Gravity.CENTER_VERTICAL);
					buttonLayout.setLayoutParams(new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, 40));			
					time = new TextView(HealthGenius.myApp);
					time.setPadding(5, 10, 5, 10);
					time.setTextSize(20);
					time.setTextColor(Color.BLACK);
					time.setTypeface(Typeface.SANS_SERIF);
					buttonLayout.addView(time);
					time.setText(String.format("%02d:%02d", event.date.getHours(), event.date.getMinutes()));
					TextView activity = new TextView(HealthGenius.myApp);
					activity.setLayoutParams(new TableRow.LayoutParams(200, LayoutParams.WRAP_CONTENT));
					activity.setText(event.name);
					activity.setWidth(200);
					activity.setPadding(5, 10, 5, 10);
					activity.setTextSize(20);
					activity.setTextColor(Color.BLACK);
					activity.setTypeface(Typeface.SANS_SERIF);
					ImageView button = new ImageView(HealthGenius.myApp);
					switch (event.state) {
						case 0:
							button.setBackgroundResource(R.drawable.oksmall);
							break;
						case 1:
							button.setBackgroundResource(R.drawable.cancel);
							break;
						case 2:
							button.setBackgroundResource(R.drawable.nextsmall);
							break;
					}
					if (event.type == 2) button.setBackgroundResource(R.drawable.event);
					buttonLayout.addView(activity);
					buttonLayout.addView(button);	
					time.setOnClickListener(new OnClickListener() {				
						@Override
						public void onClick(View v) {
							if (HealthGenius.myMenu != null) HealthGenius.myMenu.clear();
							HealthGenius.myCalendarManager.events.get(id).doActivity();
						}
					});	
					activity.setOnClickListener(new OnClickListener() {				
						@Override
						public void onClick(View v) {
							if (HealthGenius.myMenu != null) HealthGenius.myMenu.clear();
							HealthGenius.myCalendarManager.events.get(id).doActivity();
						}
					});
					button.setOnClickListener(new OnClickListener() {				
						@Override
						public void onClick(View v) {
							if (HealthGenius.myMenu != null) HealthGenius.myMenu.clear();
							HealthGenius.myCalendarManager.events.get(id).doActivity();
						}
					});	
					buttonLayout.setOnClickListener(new OnClickListener() {				
						@Override
						public void onClick(View v) {
							if (HealthGenius.myMenu != null) HealthGenius.myMenu.clear();
							HealthGenius.myCalendarManager.events.get(id).doActivity();
						}
					});	
				}
			}
			schedule.addView(buttonLayout);
			View separator = new View(HealthGenius.myApp);
			LayoutParams separatorLayout = new LayoutParams(android.view.ViewGroup.LayoutParams.FILL_PARENT, 1);
			separator.setLayoutParams(separatorLayout);
			separator.setBackgroundColor(Color.BLACK);
			
			schedule.addView(separator);

			date.setHours(date.getHours() + 1);
			dateLater.setHours(dateLater.getHours() + 1);
		}
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				HealthGenius.myUIManager.loadBoxOpen();
			}
		});
		prev.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Runnable run = new Runnable() {
					@Override
					public void run() {
						handler.sendEmptyMessage(0);
						Date start = new Date((dategiven.getTime()/86400000)*86400000);
						Date end = new Date(start.getTime());
						start.setDate(start.getDate() - 1);
						if (HealthGenius.myCalendarManager.getCalendar(start, end))
							handler.sendEmptyMessage(1);
						else handler.sendEmptyMessage(2);
					}
					private Handler handler = new Handler() {
						@Override
						public void handleMessage(Message msg) {
							Date nextDate;
							switch (msg.what) {
								case 0:
									ImageView animationFrame = (ImageView) HealthGenius.myApp.findViewById(R.id.animation);
									animationFrame.setVisibility(View.VISIBLE);
									animationFrame.setBackgroundResource(R.drawable.loading);
									final AnimationDrawable animation = (AnimationDrawable) animationFrame.getBackground();
									animation.start();
									break;
								case 1:
									nextDate = new Date(dategiven.getTime());
									nextDate.setDate(nextDate.getDate() - 1);
									myUIManager.UIcalendar.loadScheduleDay(nextDate);
									break;
								case 2:
									nextDate = new Date(dategiven.getTime());
									nextDate.setDate(nextDate.getDate() - 1);
									myUIManager.UIcalendar.loadScheduleDay(nextDate);
									myUIManager.UImisc.loadInfoPopup(HealthGenius.myLanguageManager.CONNECTION_FAILED);
									break;
							}
						}

					};
				};
				Thread thread = new Thread(run);
				thread.start();
			}
		});
		next.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Runnable run = new Runnable() {
					@Override
					public void run() {
						handler.sendEmptyMessage(0);
						Date start = new Date((dategiven.getTime()/86400000)*86400000);
						start.setDate(start.getDate() + 1);
						Date end = new Date(start.getTime());
						end.setDate(end.getDate() + 1);
						if (HealthGenius.myCalendarManager.getCalendar(start, end))
							handler.sendEmptyMessage(1);
						else handler.sendEmptyMessage(2);
					}
					private Handler handler = new Handler() {
						@Override
						public void handleMessage(Message msg) {
							Date nextDate;
							switch (msg.what) {
								case 0:
									ImageView animationFrame = (ImageView) HealthGenius.myApp.findViewById(R.id.animation);
									animationFrame.setVisibility(View.VISIBLE);
									animationFrame.setBackgroundResource(R.drawable.loading);
									final AnimationDrawable animation = (AnimationDrawable) animationFrame.getBackground();
									animation.start();
									break;
								case 1:
									nextDate = new Date(dategiven.getTime());
									nextDate.setDate(nextDate.getDate() + 1);
									HealthGenius.myUIManager.UIcalendar.loadScheduleDay(nextDate);
									break;
								case 2:
									nextDate = new Date(dategiven.getTime());
									nextDate.setDate(nextDate.getDate() + 1);
									HealthGenius.myUIManager.UIcalendar.loadScheduleDay(nextDate);
									HealthGenius.myUIManager.UImisc.loadInfoPopup(HealthGenius.myLanguageManager.CONNECTION_FAILED);
									break;
							}
						}

					};
				};
				Thread thread = new Thread(run);
				thread.start();
			}
		});
		ImageView tocal = (ImageView) HealthGenius.myApp.findViewById(R.id.animation);
		tocal.setVisibility(View.VISIBLE);
		try {
	        PackageManager manager = HealthGenius.myApp.getPackageManager();
	        Drawable icon = manager.getActivityIcon(new ComponentName(HealthGenius.myExteriorManager.calendar.packageName, HealthGenius.myExteriorManager.calendar.activityName));
			if (icon != null) tocal.setBackgroundDrawable(icon);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		tocal.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				HealthGenius.myExteriorManager.calendar.startApplication();
			}
		});
		ImageButton add = (ImageButton) HealthGenius.myApp.findViewById(R.id.help);
		add.setImageResource(R.drawable.plus);
		add.setVisibility(View.VISIBLE);
		add.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				LayoutInflater inflater = (LayoutInflater) HealthGenius.myApp.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				final View layout = inflater.inflate(R.layout.toastaddevent,
				                               (ViewGroup) HealthGenius.myApp.findViewById(R.id.toastaddeventroot));
				TextView title = (TextView) layout.findViewById(R.id.addtitle);
				title.setText(HealthGenius.myLanguageManager.NOTIFICATION_EVENT_TITLE);
				TextView namereq = (TextView) layout.findViewById(R.id.namerequested);
				namereq.setText(HealthGenius.myLanguageManager.NOTIFICATION_EVENT_NAMEREQ);
				TextView descreq = (TextView) layout.findViewById(R.id.descrequest);
				descreq.setText(HealthGenius.myLanguageManager.NOTIFICATION_EVENT_DESC);
				TextView startreq = (TextView) layout.findViewById(R.id.startrequest);
				startreq.setText(HealthGenius.myLanguageManager.NOTIFICATION_EVENT_START);
				TextView endreq = (TextView) layout.findViewById(R.id.endrequest);
				endreq.setText(HealthGenius.myLanguageManager.NOTIFICATION_EVENT_END);
				TextView freqreq = (TextView) layout.findViewById(R.id.freqrequest);
				freqreq.setText(HealthGenius.myLanguageManager.NOTIFICATION_EVENT_FREQ);
				final Spinner frequency = (Spinner) layout.findViewById(R.id.freq);
			    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(HealthGenius.myApp, R.array.freqarray, android.R.layout.simple_spinner_item);
			    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			    frequency.setAdapter(adapter);
			    ((LinearLayout)layout.findViewById(R.id.freqend)).setVisibility(View.GONE);
			    frequency.setOnItemSelectedListener(new OnItemSelectedListener() {
					@Override
					public void onItemSelected (AdapterView<?> parent, View view, int position, long id) {
						if (position == 0) ((LinearLayout)layout.findViewById(R.id.freqend)).setVisibility(View.GONE);
						else ((LinearLayout)layout.findViewById(R.id.freqend)).setVisibility(View.VISIBLE);
					}
					@Override
					public void onNothingSelected (AdapterView<?> parent) {
						((LinearLayout)layout.findViewById(R.id.freqend)).setVisibility(View.GONE);
					}
				});
				TextView freqendreq = (TextView) layout.findViewById(R.id.freqendrequest);
				freqendreq.setText(HealthGenius.myLanguageManager.NOTIFICATION_EVENT_FREQEND);
				ImageButton image = (ImageButton) layout.findViewById(R.id.addeventok);
				Builder builder = new AlertDialog.Builder(HealthGenius.myApp);
				builder.setView(layout);
				final AlertDialog alertDialog = builder.create();
				alertDialog.show();
				image.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						final com.o2hlink.activ8.client.entity.Event eventtoadd = new com.o2hlink.activ8.client.entity.Event();
						eventtoadd.setName(((EditText) layout.findViewById(R.id.name)).getText().toString());
						eventtoadd.setDescription(((EditText) layout.findViewById(R.id.description)).getText().toString());
						Date start = new Date(0);
						start.setYear(((DatePicker) layout.findViewById(R.id.startdate)).getYear() - 1900);
						start.setMonth(((DatePicker) layout.findViewById(R.id.startdate)).getMonth());
						start.setDate(((DatePicker) layout.findViewById(R.id.startdate)).getDayOfMonth());
						start.setHours(((TimePicker) layout.findViewById(R.id.starttime)).getCurrentHour());
						start.setMinutes(((TimePicker) layout.findViewById(R.id.starttime)).getCurrentMinute());
						eventtoadd.setStart(start);
						Date end = new Date(0);
						end.setYear(((DatePicker) layout.findViewById(R.id.enddate)).getYear() - 1900);
						end.setMonth(((DatePicker) layout.findViewById(R.id.enddate)).getMonth());
						end.setDate(((DatePicker) layout.findViewById(R.id.enddate)).getDayOfMonth());
						end.setHours(((TimePicker) layout.findViewById(R.id.endtime)).getCurrentHour());
						end.setMinutes(((TimePicker) layout.findViewById(R.id.endtime)).getCurrentMinute());
						eventtoadd.setEnd(end);
						switch (frequency.getSelectedItemPosition()) {
							case 0:
								eventtoadd.setFrequency(EventFrequency.NONE);
								break;
							case 1:
								eventtoadd.setFrequency(EventFrequency.DAILY);
								break;
							case 2:
								eventtoadd.setFrequency(EventFrequency.WEEKLY);
								break;
							case 3:
								eventtoadd.setFrequency(EventFrequency.MONTHLY);
								break;
							case 4:
								eventtoadd.setFrequency(EventFrequency.YEARLY);
								break;
							case 5:
								eventtoadd.setFrequency(EventFrequency.MONWEDFRI);
								break;
							case 6:
								eventtoadd.setFrequency(EventFrequency.TUETHUR);
								break;
							case 7:
								eventtoadd.setFrequency(EventFrequency.WEEKDAY);
								break;
							default:
								eventtoadd.setFrequency(EventFrequency.NONE);
								break;
						}
						Date freqend = new Date(0);
						freqend.setYear(((DatePicker) layout.findViewById(R.id.freqenddate)).getYear() - 1900);
						freqend.setMonth(((DatePicker) layout.findViewById(R.id.freqenddate)).getMonth());
						freqend.setDate(((DatePicker) layout.findViewById(R.id.freqenddate)).getDayOfMonth());
						freqend.setHours(((TimePicker) layout.findViewById(R.id.freqendtime)).getCurrentHour());
						freqend.setMinutes(((TimePicker) layout.findViewById(R.id.freqendtime)).getCurrentMinute());
						eventtoadd.setEndFrequency(freqend);
						final ProgressDialog dialog = ProgressDialog.show(HealthGenius.myApp, HealthGenius.myLanguageManager.NOTIFICATION_EVENT_TITLE, 
								HealthGenius.myLanguageManager.NOTIFICATION_EVENT_ADDING, true);
						dialog.show();
						Runnable run =  new Runnable() {
							@Override
							public void run() {
								if (HealthGenius.myCalendarManager.AddEvent(eventtoadd))
									handler.sendEmptyMessage(1);
								else 
									handler.sendEmptyMessage(0);
							}
							private Handler handler = new Handler() {
								@Override
								public void handleMessage(Message msg) {
									switch (msg.what) {
										case 0:
											Toast toast = Toast.makeText(HealthGenius.myApp, HealthGenius.myLanguageManager.CONNECTION_FAILED, Toast.LENGTH_LONG);
											HealthGenius.myUIManager.UIcalendar.loadScheduleDay(dategiven);
											dialog.cancel();
											toast.show();
											break;
										case 1:
											HealthGenius.myUIManager.UIcalendar.loadScheduleDay(dategiven);
											dialog.cancel();
											break;
									}
								}

							};
						};
						Thread thread = new Thread(run);
						thread.start();
						alertDialog.cancel();
					}
				});
			}
		});
	}
	
	public void loadScheduleWeek(final Date dategiven) {
		TextView time;
		Event event = null; 
		Calendar cal = Calendar.getInstance();
		cal.setTime(dategiven);
		Date date = new Date((dategiven.getTime()/3600000)*3600000);
		date.setHours(0);
		int day = cal.get(Calendar.DAY_OF_WEEK)-2;
		if (day == -1) day = 6;
		date.setDate(date.getDate() - day);
		Date dateLater = new Date(date.getTime());
		dateLater.setDate(date.getDate() + 1);
		TableRow buttonLayout;
		myUIManager.state = UIManager.UI_STATE_SCHEDULEWEEK;
		if (HealthGenius.myMenu != null) {
			HealthGenius.myMenu.clear();
			HealthGenius.myInflater.inflate(R.menu.schedule, HealthGenius.myMenu);
		}
		Hashtable<Date, Event> eventsOrdered = new Hashtable<Date,Event>();
		Vector<Date> datesOrdered = new Vector<Date>();
		Enumeration<Event> enumer = HealthGenius.myCalendarManager.events.elements();
		while (enumer.hasMoreElements()) {
			Event temp = enumer.nextElement();
			while (datesOrdered.contains(temp.date)) temp.date.setTime(temp.date.getTime() + 1);
			Timestamp stamp = new Timestamp(temp.date.getTime());
			datesOrdered.add(stamp);
			eventsOrdered.put(stamp, temp);
		}
		Collections.sort(datesOrdered);
		HealthGenius.myApp.setContentView(R.layout.scheduleday);
		((TextView) HealthGenius.myApp.findViewById(R.id.startText)).setText(HealthGenius.myLanguageManager.CALENDAR_TITLE);
		ImageButton back = (ImageButton) HealthGenius.myApp.findViewById(R.id.previous);
		ImageButton prev = (ImageButton) HealthGenius.myApp.findViewById(R.id.previousday);
		ImageButton next = (ImageButton) HealthGenius.myApp.findViewById(R.id.nextday);
		TextView dateText = (TextView) HealthGenius.myApp.findViewById(R.id.date);
		dateText.setText(HealthGenius.myLanguageManager.CALENDAR_WEEK + " " + cal.get(Calendar.WEEK_OF_YEAR) + " "+ HealthGenius.myLanguageManager.CALENDAR_OF + " " + cal.get(Calendar.YEAR));
		TableLayout schedule = (TableLayout)HealthGenius.myApp.findViewById(R.id.schedule);
		for (int i = 0; i < 7; i++) {	
			time = new TextView(HealthGenius.myApp);	
			time.setText(HealthGeniusUtil.dateToReadableString(date));
			time.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
			time.setPadding(5, 10, 5, 10);
			time.setTypeface(Typeface.DEFAULT_BOLD);
			time.setTextSize(20);
			time.setGravity(Gravity.CENTER);
			time.setTextColor(Color.BLACK);
			schedule.addView(time);		
			View separator = new View(HealthGenius.myApp);
			LayoutParams separatorLayout = new LayoutParams(android.view.ViewGroup.LayoutParams.FILL_PARENT, 1);
			separator.setLayoutParams(separatorLayout);
			separator.setBackgroundColor(Color.BLACK);				
			schedule.addView(separator);
			for(int j = 0; j < datesOrdered.size(); j++) {
				event = eventsOrdered.get(datesOrdered.get(j));
				final String id = event.id;
				if ((date.compareTo(event.getDate()) <= 0)&&(dateLater.compareTo(event.getDate()) > 0)) {
					buttonLayout = new TableRow(HealthGenius.myApp);
					buttonLayout.setOrientation(TableRow.HORIZONTAL);
					buttonLayout.setGravity(Gravity.CENTER_VERTICAL);
					buttonLayout.setLayoutParams(new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, 40));			
					time = new TextView(HealthGenius.myApp);
					time.setPadding(5, 10, 5, 10);
					time.setTextSize(20);
					time.setTextColor(Color.BLACK);
					time.setTypeface(Typeface.SANS_SERIF);
					time.setText(String.format("%02d:%02d", event.date.getHours(), event.date.getMinutes()));
					buttonLayout.addView(time);
					TextView activity = new TextView(HealthGenius.myApp);
					activity.setLayoutParams(new TableRow.LayoutParams(200, LayoutParams.WRAP_CONTENT));
					activity.setText(event.name);
					activity.setPadding(5, 10, 5, 10);
					activity.setTextSize(20);
					activity.setTextColor(Color.BLACK);
					activity.setTypeface(Typeface.SANS_SERIF);
					buttonLayout.addView(activity);
					ImageView button = new ImageView(HealthGenius.myApp);
					switch (event.state) {
						case 0:
							button.setBackgroundResource(R.drawable.oksmall);
							break;
						case 1:
							button.setBackgroundResource(R.drawable.cancel);
							break;
						case 2:
							button.setBackgroundResource(R.drawable.nextsmall);
							break;
					}
					if (event.type == 2) button.setBackgroundResource(R.drawable.event);
					buttonLayout.addView(button);	
					schedule.addView(buttonLayout);
					time.setOnClickListener(new OnClickListener() {				
						@Override
						public void onClick(View v) {
							if (HealthGenius.myMenu != null) HealthGenius.myMenu.clear();
							HealthGenius.myCalendarManager.events.get(id).doActivity();
						}
					});	
					activity.setOnClickListener(new OnClickListener() {				
						@Override
						public void onClick(View v) {
							if (HealthGenius.myMenu != null) HealthGenius.myMenu.clear();
							HealthGenius.myCalendarManager.events.get(id).doActivity();
						}
					});
					button.setOnClickListener(new OnClickListener() {				
						@Override
						public void onClick(View v) {
							if (HealthGenius.myMenu != null) HealthGenius.myMenu.clear();
							HealthGenius.myCalendarManager.events.get(id).doActivity();
						}
					});	
					buttonLayout.setOnClickListener(new OnClickListener() {				
						@Override
						public void onClick(View v) {
							if (HealthGenius.myMenu != null) HealthGenius.myMenu.clear();
							HealthGenius.myCalendarManager.events.get(id).doActivity();
						}
					});	
					separator = new View(HealthGenius.myApp);
					separatorLayout = new LayoutParams(android.view.ViewGroup.LayoutParams.FILL_PARENT, 1);
					separator.setLayoutParams(separatorLayout);
					separator.setBackgroundColor(Color.BLACK);
					schedule.addView(separator);
				}
			}
			date.setDate(date.getDate() + 1);
			dateLater.setDate(dateLater.getDate() + 1);
		}
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				HealthGenius.myUIManager.loadBoxOpen();
			}
		});
		prev.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Runnable run = new Runnable() {
					@Override
					public void run() {
						handler.sendEmptyMessage(0);
						int day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)-2;
						if (day == -1) day = 6;
						Date start = new Date((dategiven.getTime()/86400000)*86400000);
						start.setDate(start.getDate() - day - 7);
						Date end = new Date(start.getTime());
						end.setDate(end.getDate() + 7);
						if (HealthGenius.myCalendarManager.getCalendar(start, end))
							handler.sendEmptyMessage(1);
						else handler.sendEmptyMessage(2);
					}
					private Handler handler = new Handler() {
						@Override
						public void handleMessage(Message msg) {
							Date nextDate;
							switch (msg.what) {
								case 0:
									ImageView animationFrame = (ImageView) HealthGenius.myApp.findViewById(R.id.animation);
									animationFrame.setVisibility(View.VISIBLE);
									animationFrame.setBackgroundResource(R.drawable.loading);
									final AnimationDrawable animation = (AnimationDrawable) animationFrame.getBackground();
									animation.start();
									break;
								case 1:
									nextDate = new Date(dategiven.getTime());
									nextDate.setDate(nextDate.getDate() - 7);
									HealthGenius.myUIManager.UIcalendar.loadScheduleWeek(nextDate);
									break;
								case 2:
									nextDate = new Date(dategiven.getTime());
									nextDate.setDate(nextDate.getDate() - 7);
									HealthGenius.myUIManager.UIcalendar.loadScheduleWeek(nextDate);
									HealthGenius.myUIManager.UImisc.loadInfoPopup(HealthGenius.myLanguageManager.CONNECTION_FAILED);
									break;
							}
						}

					};
				};
				Thread thread = new Thread(run);
				thread.start();
			}
		});
		next.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Runnable run = new Runnable() {
					@Override
					public void run() {
						handler.sendEmptyMessage(0);
						int day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)-2;
						if (day == -1) day = 6;
						Date start = new Date((dategiven.getTime()/86400000)*86400000);
						start.setDate(start.getDate() - day + 7);
						Date end = new Date(start.getTime());
						end.setDate(end.getDate() + 7);
						if (HealthGenius.myCalendarManager.getCalendar(start, end))
							handler.sendEmptyMessage(1);
						else handler.sendEmptyMessage(2);
					}
					private Handler handler = new Handler() {
						@Override
						public void handleMessage(Message msg) {
							Date nextDate;
							switch (msg.what) {
								case 0:
									ImageView animationFrame = (ImageView) HealthGenius.myApp.findViewById(R.id.animation);
									animationFrame.setVisibility(View.VISIBLE);
									animationFrame.setBackgroundResource(R.drawable.loading);
									final AnimationDrawable animation = (AnimationDrawable) animationFrame.getBackground();
									animation.start();
									break;
								case 1:
									nextDate = new Date(dategiven.getTime());
									nextDate.setDate(nextDate.getDate() + 7);
									HealthGenius.myUIManager.UIcalendar.loadScheduleWeek(nextDate);
									break;
								case 2:
									nextDate = new Date(dategiven.getTime());
									nextDate.setDate(nextDate.getDate() + 7);
									HealthGenius.myUIManager.UIcalendar.loadScheduleWeek(nextDate);
									HealthGenius.myUIManager.UImisc.loadInfoPopup(HealthGenius.myLanguageManager.CONNECTION_FAILED);
									break;
							}
						}

					};
				};
				Thread thread = new Thread(run);
				thread.start();
			}
		});
		ImageView tocal = (ImageView) HealthGenius.myApp.findViewById(R.id.animation);
		tocal.setVisibility(View.VISIBLE);
		try {
	        PackageManager manager = HealthGenius.myApp.getPackageManager();
	        Drawable icon = manager.getActivityIcon(new ComponentName(HealthGenius.myExteriorManager.calendar.packageName, HealthGenius.myExteriorManager.calendar.activityName));
			if (icon != null) tocal.setBackgroundDrawable(icon);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		tocal.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				HealthGenius.myExteriorManager.calendar.startApplication();
			}
		});
		ImageButton add = (ImageButton) HealthGenius.myApp.findViewById(R.id.help);
		add.setImageResource(R.drawable.plus);
		add.setVisibility(View.VISIBLE);
		add.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				LayoutInflater inflater = (LayoutInflater) HealthGenius.myApp.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				final View layout = inflater.inflate(R.layout.toastaddevent,
				                               (ViewGroup) HealthGenius.myApp.findViewById(R.id.toastaddeventroot));
				TextView title = (TextView) layout.findViewById(R.id.addtitle);
				title.setText(HealthGenius.myLanguageManager.NOTIFICATION_EVENT_TITLE);
				TextView namereq = (TextView) layout.findViewById(R.id.namerequested);
				namereq.setText(HealthGenius.myLanguageManager.NOTIFICATION_EVENT_NAMEREQ);
				TextView descreq = (TextView) layout.findViewById(R.id.descrequest);
				descreq.setText(HealthGenius.myLanguageManager.NOTIFICATION_EVENT_DESC);
				TextView startreq = (TextView) layout.findViewById(R.id.startrequest);
				startreq.setText(HealthGenius.myLanguageManager.NOTIFICATION_EVENT_START);
				TextView endreq = (TextView) layout.findViewById(R.id.endrequest);
				endreq.setText(HealthGenius.myLanguageManager.NOTIFICATION_EVENT_END);
				TextView freqreq = (TextView) layout.findViewById(R.id.freqrequest);
				freqreq.setText(HealthGenius.myLanguageManager.NOTIFICATION_EVENT_FREQ);
				final Spinner frequency = (Spinner) layout.findViewById(R.id.freq);
			    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(HealthGenius.myApp, R.array.freqarray, android.R.layout.simple_spinner_item);
			    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			    frequency.setAdapter(adapter);
			    ((LinearLayout)layout.findViewById(R.id.freqend)).setVisibility(View.GONE);
			    frequency.setOnItemSelectedListener(new OnItemSelectedListener() {
					@Override
					public void onItemSelected (AdapterView<?> parent, View view, int position, long id) {
						if (position == 0) ((LinearLayout)layout.findViewById(R.id.freqend)).setVisibility(View.GONE);
						else ((LinearLayout)layout.findViewById(R.id.freqend)).setVisibility(View.VISIBLE);
					}
					@Override
					public void onNothingSelected (AdapterView<?> parent) {
						((LinearLayout)layout.findViewById(R.id.freqend)).setVisibility(View.GONE);
					}
				});
			    TextView freqendreq = (TextView) layout.findViewById(R.id.freqendrequest);
				freqendreq.setText(HealthGenius.myLanguageManager.NOTIFICATION_EVENT_FREQEND);
				ImageButton image = (ImageButton) layout.findViewById(R.id.addeventok);
				Builder builder = new AlertDialog.Builder(HealthGenius.myApp);
				builder.setView(layout);
				final AlertDialog alertDialog = builder.create();
				alertDialog.show();
				image.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						final com.o2hlink.activ8.client.entity.Event eventtoadd = new com.o2hlink.activ8.client.entity.Event();
						eventtoadd.setName(((EditText) layout.findViewById(R.id.name)).getText().toString());
						eventtoadd.setDescription(((EditText) layout.findViewById(R.id.description)).getText().toString());
						Date start = new Date(0);
						start.setYear(((DatePicker) layout.findViewById(R.id.startdate)).getYear() - 1900);
						start.setMonth(((DatePicker) layout.findViewById(R.id.startdate)).getMonth());
						start.setDate(((DatePicker) layout.findViewById(R.id.startdate)).getDayOfMonth());
						start.setHours(((TimePicker) layout.findViewById(R.id.starttime)).getCurrentHour());
						start.setMinutes(((TimePicker) layout.findViewById(R.id.starttime)).getCurrentMinute());
						eventtoadd.setStart(start);
						Date end = new Date(0);
						end.setYear(((DatePicker) layout.findViewById(R.id.enddate)).getYear() - 1900);
						end.setMonth(((DatePicker) layout.findViewById(R.id.enddate)).getMonth());
						end.setDate(((DatePicker) layout.findViewById(R.id.enddate)).getDayOfMonth());
						end.setHours(((TimePicker) layout.findViewById(R.id.endtime)).getCurrentHour());
						end.setMinutes(((TimePicker) layout.findViewById(R.id.endtime)).getCurrentMinute());
						eventtoadd.setEnd(end);
						switch (frequency.getSelectedItemPosition()) {
							case 0:
								eventtoadd.setFrequency(EventFrequency.NONE);
								break;
							case 1:
								eventtoadd.setFrequency(EventFrequency.DAILY);
								break;
							case 2:
								eventtoadd.setFrequency(EventFrequency.WEEKLY);
								break;
							case 3:
								eventtoadd.setFrequency(EventFrequency.MONTHLY);
								break;
							case 4:
								eventtoadd.setFrequency(EventFrequency.YEARLY);
								break;
							case 5:
								eventtoadd.setFrequency(EventFrequency.MONWEDFRI);
								break;
							case 6:
								eventtoadd.setFrequency(EventFrequency.TUETHUR);
								break;
							case 7:
								eventtoadd.setFrequency(EventFrequency.WEEKDAY);
								break;
							default:
								eventtoadd.setFrequency(EventFrequency.NONE);
								break;
						}
						Date freqend = new Date(0);
						freqend.setYear(((DatePicker) layout.findViewById(R.id.freqenddate)).getYear() - 1900);
						freqend.setMonth(((DatePicker) layout.findViewById(R.id.freqenddate)).getMonth());
						freqend.setDate(((DatePicker) layout.findViewById(R.id.freqenddate)).getDayOfMonth());
						freqend.setHours(((TimePicker) layout.findViewById(R.id.freqendtime)).getCurrentHour());
						freqend.setMinutes(((TimePicker) layout.findViewById(R.id.freqendtime)).getCurrentMinute());
						eventtoadd.setEndFrequency(freqend);
						final ProgressDialog dialog = ProgressDialog.show(HealthGenius.myApp, HealthGenius.myLanguageManager.NOTIFICATION_EVENT_TITLE, 
								HealthGenius.myLanguageManager.NOTIFICATION_EVENT_ADDING, true);
						dialog.show();
						Runnable run =  new Runnable() {
							@Override
							public void run() {
								if (HealthGenius.myCalendarManager.AddEvent(eventtoadd))
									handler.sendEmptyMessage(1);
								else 
									handler.sendEmptyMessage(0);
							}
							private Handler handler = new Handler() {
								@Override
								public void handleMessage(Message msg) {
									switch (msg.what) {
										case 0:
											Toast toast = Toast.makeText(HealthGenius.myApp, HealthGenius.myLanguageManager.CONNECTION_FAILED, Toast.LENGTH_SHORT);
											HealthGenius.myUIManager.UIcalendar.loadScheduleDay(dategiven);
											dialog.cancel();
											toast.show();
											break;
										case 1:
											int day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)-2;
											if (day == -1) day = 6;
											Date start = new Date((dategiven.getTime()/86400000)*86400000);
											start.setDate(start.getDate() - day + 7);
											Date end = new Date(start.getTime());
											end.setDate(end.getDate() + 7);
											HealthGenius.myCalendarManager.getNonMeasuringEvents(start, end);
											HealthGenius.myUIManager.UIcalendar.loadScheduleWeek(dategiven);
											dialog.cancel();
											break;
									}
								}

							};
						};
						Thread thread = new Thread(run);
						thread.start();
						alertDialog.cancel();
					}
				});
			}
		});
	}
	
	public void loadScheduleMonth(final Date dategiven) {
		TextView time;
		View separator;
		LayoutParams separatorLayout;
		TableRow weekLayout;
		Enumeration<Event> enumeration;
		Event event = null; 
		Date date = new Date((dategiven.getTime()/3600000)*3600000);
		date.setHours(0);
		date.setDate(1);
		Date dateLater = new Date(date.getTime());
		dateLater.setDate(2);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int weekday = cal.get(Calendar.DAY_OF_WEEK)-2;
		if (weekday == -1) weekday = 6;
		Date dateLimit = new Date(date.getTime());
		dateLimit.setMonth(date.getMonth() + 1);
		myUIManager.state = UIManager.UI_STATE_SCHEDULEMONTH;
		if (HealthGenius.myMenu != null) {
			HealthGenius.myMenu.clear();
			HealthGenius.myInflater.inflate(R.menu.schedule, HealthGenius.myMenu);
		}
		HealthGenius.myApp.setContentView(R.layout.schedulemonth);
		((TextView) HealthGenius.myApp.findViewById(R.id.startText)).setText(HealthGenius.myLanguageManager.CALENDAR_TITLE);
		ImageButton back = (ImageButton) HealthGenius.myApp.findViewById(R.id.previous);
		ImageButton prev = (ImageButton) HealthGenius.myApp.findViewById(R.id.previousday);
		ImageButton next = (ImageButton) HealthGenius.myApp.findViewById(R.id.nextday);
		TextView dateText = (TextView) HealthGenius.myApp.findViewById(R.id.date);
		dateText.setText(HealthGeniusUtil.monthOfDate(date) + " " + cal.get(Calendar.YEAR));
		TableLayout schedule = (TableLayout)HealthGenius.myApp.findViewById(R.id.schedule);
		weekLayout = new TableRow(HealthGenius.myApp);
		weekLayout.setOrientation(TableRow.HORIZONTAL);
		weekLayout.setGravity(Gravity.CENTER_VERTICAL);
		weekLayout.setLayoutParams(new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		separator = new View(HealthGenius.myApp);
		separator.setLayoutParams(new TableRow.LayoutParams(1, 36));
		separator.setBackgroundColor(Color.BLACK);
		weekLayout.addView(separator);
		String[] weekdaynames = {HealthGenius.myLanguageManager.WEEK_MONDAY,
				HealthGenius.myLanguageManager.WEEK_TUESDAY,
				HealthGenius.myLanguageManager.WEEK_WEDNESDAY,
				HealthGenius.myLanguageManager.WEEK_THURSDAY,
				HealthGenius.myLanguageManager.WEEK_FRYDAY,
				HealthGenius.myLanguageManager.WEEK_SATURDAY,
				HealthGenius.myLanguageManager.WEEK_SUNDAY}; 
		for (int i = 0; i < weekdaynames.length; i++) {
			time = new TextView(HealthGenius.myApp);
			time.setGravity(Gravity.CENTER);
			time.setTextSize(20);
			time.setText(weekdaynames[i]);
			time.setTag(date.getDate());
			time.setTypeface(Typeface.DEFAULT_BOLD);
			time.setLayoutParams(new TableRow.LayoutParams(36, 36));	
			time.setTextColor(Color.BLACK);
			weekLayout.addView(time);
		}
		separator = new View(HealthGenius.myApp);
		separator.setLayoutParams(new TableRow.LayoutParams(1, 36));
		separator.setBackgroundColor(Color.BLACK);
		weekLayout.addView(separator);
		schedule.addView(weekLayout);	
		separator = new View(HealthGenius.myApp);
		separatorLayout = new LayoutParams(android.view.ViewGroup.LayoutParams.FILL_PARENT, 1);
		separator.setLayoutParams(separatorLayout);
		separator.setBackgroundColor(Color.BLACK);
		schedule.addView(separator);	
		weekLayout = new TableRow(HealthGenius.myApp);
		weekLayout.setOrientation(TableRow.HORIZONTAL);
		weekLayout.setGravity(Gravity.CENTER_VERTICAL);
		weekLayout.setLayoutParams(new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		separator = new View(HealthGenius.myApp);
		separator.setLayoutParams(new TableRow.LayoutParams(1, 36));
		separator.setBackgroundColor(Color.BLACK);
		weekLayout.addView(separator);
		for (int i = 0; i < weekday; i++) {		
			View space = new View(HealthGenius.myApp);
			space.setLayoutParams(new TableRow.LayoutParams(36, 36));	
			weekLayout.addView(space);
		}
		while(date.before(dateLimit)) {	
			time = new TextView(HealthGenius.myApp);
			time.setGravity(Gravity.CENTER);
			time.setTextSize(20);
			time.setText("" + date.getDate());
			time.setTag(date.getDate());
			time.setTypeface(Typeface.SANS_SERIF);
			time.setLayoutParams(new TableRow.LayoutParams(36, 36));	
			int state = 3;
			enumeration = HealthGenius.myCalendarManager.events.elements();
			while (enumeration.hasMoreElements()) {
				event = enumeration.nextElement();
				if (event.type == 2) continue;
				if ((date.compareTo(event.getDate()) <= 0)&&(dateLater.compareTo(event.getDate()) > 0)) {
					if ((event.state == 0)&&(state == 3)) {
						state = 0;
						continue;
					}
					if ((event.state == 2)&&((state == 3)||(state == 0))) {
						state = 2;
						continue;
					}
					if ((event.state == 1)&&((state == 3)||(state == 0)||(state == 2))) {
						state = 1;
						break;
					}
				}
			}
			switch (state) {
				case 0:
					time.setTextColor(Color.GREEN);
					break;
				case 1:
					time.setTextColor(Color.RED);
					break;
				case 2:
					time.setTextColor(Color.parseColor("#ffad00"));
					break;
				case 3:
					time.setTextColor(Color.BLACK);
					break;
			}
			time.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Date newDate = new Date(dategiven.getTime());
					newDate.setDate((Integer)v.getTag());
					HealthGenius.myUIManager.UIcalendar.loadScheduleDay(newDate);
				}
			});
			weekLayout.addView(time);
			date.setDate(date.getDate() + 1);
			dateLater.setDate(dateLater.getDate() + 1);
			weekday++;
			if (weekday == 7) {		
				separator = new View(HealthGenius.myApp);
				separator.setLayoutParams(new TableRow.LayoutParams(1, 36));
				separator.setBackgroundColor(Color.BLACK);
				weekLayout.addView(separator);
				schedule.addView(weekLayout);	
				separator = new View(HealthGenius.myApp);
				separatorLayout = new LayoutParams(android.view.ViewGroup.LayoutParams.FILL_PARENT, 1);
				separator.setLayoutParams(separatorLayout);
				separator.setBackgroundColor(Color.BLACK);
				schedule.addView(separator);	
				weekday = 0;
				weekLayout = new TableRow(HealthGenius.myApp);
				weekLayout.setOrientation(TableRow.HORIZONTAL);
				weekLayout.setGravity(Gravity.CENTER_VERTICAL);
				weekLayout.setLayoutParams(new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
				separator = new View(HealthGenius.myApp);
				separator.setLayoutParams(new TableRow.LayoutParams(1, 36));
				separator.setBackgroundColor(Color.BLACK);
				weekLayout.addView(separator);
			}
		}
		for (int i = weekday; i < 7; i++) {	
			time = new TextView(HealthGenius.myApp);
			time.setGravity(Gravity.CENTER);
			time.setTextSize(20);
			time.setTextColor(Color.BLACK);
			time.setTypeface(Typeface.SANS_SERIF);
			time.setLayoutParams(new TableRow.LayoutParams(36, 36));	
			weekLayout.addView(time);
		}	
		separator = new View(HealthGenius.myApp);
		separator.setLayoutParams(new TableRow.LayoutParams(1, 36));
		separator.setBackgroundColor(Color.BLACK);
		weekLayout.addView(separator);
		schedule.addView(weekLayout);
		separator = new View(HealthGenius.myApp);
		separatorLayout = new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, 1);
		separator.setLayoutParams(separatorLayout);
		separator.setBackgroundColor(Color.BLACK);
		schedule.addView(separator);	
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				HealthGenius.myUIManager.loadBoxOpen();
			}
		});
		prev.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Runnable run = new Runnable() {
					@Override
					public void run() {
						handler.sendEmptyMessage(0);
						Date start = new Date((dategiven.getTime()/86400000)*86400000);
						start.setDate(1);
						start.setMonth(start.getMonth() - 1);
						Date end = new Date(start.getTime());
						end.setMonth(end.getMonth() + 1);
						if (HealthGenius.myCalendarManager.getCalendar(start, end))
							handler.sendEmptyMessage(1);
						else handler.sendEmptyMessage(2);
					}
					private Handler handler = new Handler() {
						@Override
						public void handleMessage(Message msg) {
							Date nextDate;
							switch (msg.what) {
								case 0:
									ImageView animationFrame = (ImageView) HealthGenius.myApp.findViewById(R.id.animation);
									animationFrame.setVisibility(View.VISIBLE);
									animationFrame.setBackgroundResource(R.drawable.loading);
									final AnimationDrawable animation = (AnimationDrawable) animationFrame.getBackground();
									animation.start();
									break;
								case 1:
									nextDate = new Date(dategiven.getTime());
									nextDate.setMonth(nextDate.getMonth() - 1);
									HealthGenius.myUIManager.UIcalendar.loadScheduleMonth(nextDate);
									break;
								case 2:
									nextDate = new Date(dategiven.getTime());
									nextDate.setMonth(nextDate.getMonth() - 1);
									HealthGenius.myUIManager.UIcalendar.loadScheduleMonth(nextDate);
									HealthGenius.myUIManager.UImisc.loadInfoPopup(HealthGenius.myLanguageManager.CONNECTION_FAILED);
									break;
							}
						}

					};
				};
				Thread thread = new Thread(run);
				thread.start();
			}
		});
		next.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Runnable run = new Runnable() {
					@Override
					public void run() {
						handler.sendEmptyMessage(0);
						Date start = new Date((dategiven.getTime()/86400000)*86400000);
						start.setDate(1);
						start.setMonth(start.getMonth() + 1);
						Date end = new Date(start.getTime());
						end.setMonth(end.getMonth() + 1);
						if (HealthGenius.myCalendarManager.getCalendar(start, end))
							handler.sendEmptyMessage(1);
						else handler.sendEmptyMessage(2);
					}
					private Handler handler = new Handler() {
						@Override
						public void handleMessage(Message msg) {
							Date nextDate;
							switch (msg.what) {
								case 0:
									ImageView animationFrame = (ImageView) HealthGenius.myApp.findViewById(R.id.animation);
									animationFrame.setVisibility(View.VISIBLE);
									animationFrame.setBackgroundResource(R.drawable.loading);
									final AnimationDrawable animation = (AnimationDrawable) animationFrame.getBackground();
									animation.start();
									break;
								case 1:
									nextDate = new Date(dategiven.getTime());
									nextDate.setMonth(nextDate.getMonth() + 1);
									HealthGenius.myUIManager.UIcalendar.loadScheduleMonth(nextDate);
									break;
								case 2:
									nextDate = new Date(dategiven.getTime());
									nextDate.setMonth(nextDate.getMonth() + 1);
									HealthGenius.myUIManager.UIcalendar.loadScheduleMonth(nextDate);
									HealthGenius.myUIManager.UImisc.loadInfoPopup(HealthGenius.myLanguageManager.CONNECTION_FAILED);
									break;
							}
						}

					};
				};
				Thread thread = new Thread(run);
				thread.start();
			}
		});
		ImageView tocal = (ImageView) HealthGenius.myApp.findViewById(R.id.animation);
		tocal.setVisibility(View.VISIBLE);
		try {
	        PackageManager manager = HealthGenius.myApp.getPackageManager();
	        Drawable icon = manager.getActivityIcon(new ComponentName(HealthGenius.myExteriorManager.calendar.packageName, HealthGenius.myExteriorManager.calendar.activityName));
			if (icon != null) tocal.setBackgroundDrawable(icon);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		tocal.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				HealthGenius.myExteriorManager.calendar.startApplication();
			}
		});
		ImageButton add = (ImageButton) HealthGenius.myApp.findViewById(R.id.help);
		add.setImageResource(R.drawable.plus);
		add.setVisibility(View.VISIBLE);
		add.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				LayoutInflater inflater = (LayoutInflater) HealthGenius.myApp.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				final View layout = inflater.inflate(R.layout.toastaddevent,
				                               (ViewGroup) HealthGenius.myApp.findViewById(R.id.toastaddeventroot));
				TextView title = (TextView) layout.findViewById(R.id.addtitle);
				title.setText(HealthGenius.myLanguageManager.NOTIFICATION_EVENT_TITLE);
				TextView namereq = (TextView) layout.findViewById(R.id.namerequested);
				namereq.setText(HealthGenius.myLanguageManager.NOTIFICATION_EVENT_NAMEREQ);
				TextView descreq = (TextView) layout.findViewById(R.id.descrequest);
				descreq.setText(HealthGenius.myLanguageManager.NOTIFICATION_EVENT_DESC);
				TextView startreq = (TextView) layout.findViewById(R.id.startrequest);
				startreq.setText(HealthGenius.myLanguageManager.NOTIFICATION_EVENT_START);
				TextView endreq = (TextView) layout.findViewById(R.id.endrequest);
				endreq.setText(HealthGenius.myLanguageManager.NOTIFICATION_EVENT_END);
				TextView freqreq = (TextView) layout.findViewById(R.id.freqrequest);
				freqreq.setText(HealthGenius.myLanguageManager.NOTIFICATION_EVENT_FREQ);
			    final Spinner frequency = (Spinner) layout.findViewById(R.id.freq);
			    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(HealthGenius.myApp, R.array.freqarray, android.R.layout.simple_spinner_item);
			    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			    frequency.setAdapter(adapter);
				((LinearLayout)layout.findViewById(R.id.freqend)).setVisibility(View.GONE);
			    frequency.setOnItemSelectedListener(new OnItemSelectedListener() {
					@Override
					public void onItemSelected (AdapterView<?> parent, View view, int position, long id) {
						if (position == 0) ((LinearLayout)layout.findViewById(R.id.freqend)).setVisibility(View.GONE);
						else ((LinearLayout)layout.findViewById(R.id.freqend)).setVisibility(View.VISIBLE);
					}
					@Override
					public void onNothingSelected (AdapterView<?> parent) {
						((LinearLayout)layout.findViewById(R.id.freqend)).setVisibility(View.GONE);
					}
				});
				TextView freqendreq = (TextView) layout.findViewById(R.id.freqendrequest);
				freqendreq.setText(HealthGenius.myLanguageManager.NOTIFICATION_EVENT_FREQEND);
				ImageButton image = (ImageButton) layout.findViewById(R.id.addeventok);
				Builder builder = new AlertDialog.Builder(HealthGenius.myApp);
				builder.setView(layout);
				final AlertDialog alertDialog = builder.create();
				alertDialog.show();
				image.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						final com.o2hlink.activ8.client.entity.Event eventtoadd = new com.o2hlink.activ8.client.entity.Event();
						eventtoadd.setName(((EditText) layout.findViewById(R.id.name)).getText().toString());
						eventtoadd.setDescription(((EditText) layout.findViewById(R.id.description)).getText().toString());
						Date start = new Date(0);
						start.setYear(((DatePicker) layout.findViewById(R.id.startdate)).getYear() - 1900);
						start.setMonth(((DatePicker) layout.findViewById(R.id.startdate)).getMonth());
						start.setDate(((DatePicker) layout.findViewById(R.id.startdate)).getDayOfMonth());
						start.setHours(((TimePicker) layout.findViewById(R.id.starttime)).getCurrentHour());
						start.setMinutes(((TimePicker) layout.findViewById(R.id.starttime)).getCurrentMinute());
						eventtoadd.setStart(start);
						Date end = new Date(0);
						end.setYear(((DatePicker) layout.findViewById(R.id.enddate)).getYear() - 1900);
						end.setMonth(((DatePicker) layout.findViewById(R.id.enddate)).getMonth());
						end.setDate(((DatePicker) layout.findViewById(R.id.enddate)).getDayOfMonth());
						end.setHours(((TimePicker) layout.findViewById(R.id.endtime)).getCurrentHour());
						end.setMinutes(((TimePicker) layout.findViewById(R.id.endtime)).getCurrentMinute());
						eventtoadd.setEnd(end);
						switch (frequency.getSelectedItemPosition()) {
							case 0:
								eventtoadd.setFrequency(EventFrequency.NONE);
								break;
							case 1:
								eventtoadd.setFrequency(EventFrequency.DAILY);
								break;
							case 2:
								eventtoadd.setFrequency(EventFrequency.WEEKLY);
								break;
							case 3:
								eventtoadd.setFrequency(EventFrequency.MONTHLY);
								break;
							case 4:
								eventtoadd.setFrequency(EventFrequency.YEARLY);
								break;
							case 5:
								eventtoadd.setFrequency(EventFrequency.MONWEDFRI);
								break;
							case 6:
								eventtoadd.setFrequency(EventFrequency.TUETHUR);
								break;
							case 7:
								eventtoadd.setFrequency(EventFrequency.WEEKDAY);
								break;
							default:
								eventtoadd.setFrequency(EventFrequency.NONE);
								break;
						}
						Date freqend = new Date(0);
						freqend.setYear(((DatePicker) layout.findViewById(R.id.freqenddate)).getYear() - 1900);
						freqend.setMonth(((DatePicker) layout.findViewById(R.id.freqenddate)).getMonth());
						freqend.setDate(((DatePicker) layout.findViewById(R.id.freqenddate)).getDayOfMonth());
						freqend.setHours(((TimePicker) layout.findViewById(R.id.freqendtime)).getCurrentHour());
						freqend.setMinutes(((TimePicker) layout.findViewById(R.id.freqendtime)).getCurrentMinute());
						eventtoadd.setEndFrequency(freqend);
						final ProgressDialog dialog = ProgressDialog.show(HealthGenius.myApp, HealthGenius.myLanguageManager.NOTIFICATION_EVENT_TITLE, 
								HealthGenius.myLanguageManager.NOTIFICATION_EVENT_ADDING, true);
						dialog.show();
						Runnable run =  new Runnable() {
							@Override
							public void run() {
								if (HealthGenius.myCalendarManager.AddEvent(eventtoadd))
									handler.sendEmptyMessage(1);
								else 
									handler.sendEmptyMessage(0);
							}
							private Handler handler = new Handler() {
								@Override
								public void handleMessage(Message msg) {
									switch (msg.what) {
										case 0:
											Toast toast = Toast.makeText(HealthGenius.myApp, HealthGenius.myLanguageManager.CONNECTION_FAILED, Toast.LENGTH_SHORT);
											HealthGenius.myUIManager.UIcalendar.loadScheduleDay(dategiven);
											dialog.cancel();
											toast.show();
											break;
										case 1:
											Date start = new Date((dategiven.getTime()/86400000)*86400000);
											start.setDate(1);
											start.setMonth(start.getMonth() + 1);
											Date end = new Date(start.getTime());
											end.setMonth(end.getMonth() + 1);
											HealthGenius.myCalendarManager.getNonMeasuringEvents(start, end);
											HealthGenius.myUIManager.UIcalendar.loadScheduleDay(dategiven);
											dialog.cancel();
											break;
									}
								}

							};
						};
						Thread thread = new Thread(run);
						thread.start();
						alertDialog.cancel();
					}
				});
			}
		});
	}
	
	public void showEventInfo(final Event event) {                                                                           
		HealthGenius.myApp.setContentView(R.layout.resultscreen);
		((TextView) HealthGenius.myApp.findViewById(R.id.startText)).setText(HealthGenius.myLanguageManager.CALENDAR_MEASUREMENT_RESULT);
		((TextView) HealthGenius.myApp.findViewById(R.id.startText)).setTextSize(24);
		TextView result = ((TextView) HealthGenius.myApp.findViewById(R.id.result));
		result.setGravity(Gravity.LEFT);
		ImageButton back = (ImageButton) HealthGenius.myApp.findViewById(R.id.previous);
		result.setText(Html.fromHtml("<b>" + HealthGenius.myLanguageManager.NOTIFICATION_EVENT_NAMEREQ + "</b>" + event.name + "<br/>" + 
				"<b>" + HealthGenius.myLanguageManager.NOTIFICATION_EVENT_DESC + "</b>" + event.description + "<br/>" + 
				"<b>" + HealthGenius.myLanguageManager.NOTIFICATION_EVENT_START + "</b>" + HealthGeniusUtil.dateToReadableString(event.date) + " " + HealthGeniusUtil.timeToReadableString(event.date) + "<br/>" +
				"<b>" + HealthGenius.myLanguageManager.NOTIFICATION_EVENT_END + "</b>" + HealthGeniusUtil.dateToReadableString(event.dateEnd) + " " + HealthGeniusUtil.timeToReadableString(event.dateEnd) + "<br/>"
		));
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				loadScheduleDay(event.date);
			}
		});
	}

}
