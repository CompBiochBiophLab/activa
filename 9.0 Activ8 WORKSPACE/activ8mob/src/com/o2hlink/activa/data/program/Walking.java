package com.o2hlink.activa.data.program;

import java.util.Hashtable;

import android.os.CountDownTimer;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.SeekBar.OnSeekBarChangeListener;

import com.o2hlink.activa.Activa;
import com.o2hlink.activa.R;
import com.o2hlink.activa.background.SendSensorResult;
import com.o2hlink.activa.background.SendWalkingResult;
import com.o2hlink.activa.data.questionnaire.Question;
import com.o2hlink.activa.data.sensor.Exercise;
import com.o2hlink.activa.data.sensor.PulseOximeter;
import com.o2hlink.activa.data.sensor.SensorManager;
import com.o2hlink.activa.ui.UIManager;

public class Walking extends Program{
	
	Question borgAir;
	
	Question borgFatigue;
	
	long time;
	
	Hashtable<Long,String> timedMessages;
	
	float borgAirPre = 0.0f;
	
	float borgAirPost = 0.0f;
	
	float borgFatiguePre = 0.0f;
	
	float borgFatiguePost = 0.0f;
	
	boolean walking6minutes = false;
	
	Walking instance;

	public Walking() {
		this.name = Activa.myLanguageManager.PROGRAMS_WALKING_TITLE;
		this.id = ProgramManager.PROG_WALKING;
		this.type = ProgramManager.PROGTYPE_FOREGROUND;
		this.state = 0;
		this.icon = R.drawable.sport;
		this.time=360000;
		this.description = Activa.myLanguageManager.PROGRAMS_WALKING_DESC;
		this.borgAir = new Question(0, Activa.myLanguageManager.PROGRAMS_WALKING_BORGAIR, 0, 2, false);
		this.borgAir.borg = true;
		this.borgFatigue = new Question(0, Activa.myLanguageManager.PROGRAMS_WALKING_BORGFATIGUE, 0, 2, false);
		this.borgFatigue.borg = true;
		this.timedMessages = new Hashtable<Long, String>();
		this.timedMessages.put(0l, Activa.myLanguageManager.PROGRAMS_WALKING_MESSAGE0);
		this.timedMessages.put(60000l, Activa.myLanguageManager.PROGRAMS_WALKING_MESSAGE1);
		this.timedMessages.put(120000l, Activa.myLanguageManager.PROGRAMS_WALKING_MESSAGE2);
		this.timedMessages.put(180000l, Activa.myLanguageManager.PROGRAMS_WALKING_MESSAGE3);
		this.timedMessages.put(300000l, Activa.myLanguageManager.PROGRAMS_WALKING_MESSAGE4);
		this.walking6minutes = true;
		this.instance = this;
	}

	public Walking(long time) {
		this.name = Activa.myLanguageManager.PROGRAMS_WALKING_TITLE;
		this.id = ProgramManager.PROG_WALKING;
		this.type = ProgramManager.PROGTYPE_FOREGROUND;
		this.state = 0;
		this.icon = R.drawable.sport;
		this.time=time;
		this.description = Activa.myLanguageManager.PROGRAMS_WALKING_DESC;
		this.borgAir = new Question(0, Activa.myLanguageManager.PROGRAMS_WALKING_BORGAIR, 0, 2, false);
		this.borgAir.borg = true;
		this.borgFatigue = new Question(0, Activa.myLanguageManager.PROGRAMS_WALKING_BORGFATIGUE, 0, 2, false);
		this.borgFatigue.borg = true;
		this.timedMessages = null;
		this.instance = this;
	}
	
	@Override
	public void startProgram() {
		this.state = 0;
		Activa.myUIManager.state = UIManager.UI_STATE_PROGRAM;
		nextStep();
	}
	
	@Override
	public void finishProgram() {
		this.state = 0;
		((Exercise) Activa.mySensorManager.sensorList.get(com.o2hlink.activa.data.sensor.SensorManager.ID_EXERCISE)).results.put(SensorManager.DATAID_BORG_AIR_PRE, borgAirPre);
		((Exercise) Activa.mySensorManager.sensorList.get(com.o2hlink.activa.data.sensor.SensorManager.ID_EXERCISE)).results.put(SensorManager.DATAID_BORG_AIR_POST, borgAirPost);
		((Exercise) Activa.mySensorManager.sensorList.get(com.o2hlink.activa.data.sensor.SensorManager.ID_EXERCISE)).results.put(SensorManager.DATAID_BORG_FATIGUE_PRE, borgFatiguePre);
		((Exercise) Activa.mySensorManager.sensorList.get(com.o2hlink.activa.data.sensor.SensorManager.ID_EXERCISE)).results.put(SensorManager.DATAID_BORG_FATIGUE_POST, borgFatiguePost);
		Activa.myApp.setContentView(R.layout.sending);
		if (Activa.myProgramManager.eventAssociated != null) Activa.myProgramManager.eventAssociated.state = 0;
		SendWalkingResult sending = new SendWalkingResult( Activa.mySensorManager.sensorList.get(com.o2hlink.activa.data.sensor.SensorManager.ID_EXERCISE));
		Thread thread = new Thread(sending, "SENDWALKING");
		thread.start();
	}

	@Override
	public void nextStep() {
		this.state++;
		switch (this.state) {
			case 0:
				break;
			case 1:
				Activa.myUIManager.loadScreen(R.layout.info);
				TextView text = (TextView) Activa.myApp.findViewById(R.id.textInfo);
				text.setText(Activa.myLanguageManager.PROGRAMS_WALKING_ADVERT0);
				CountDownTimer timer = new CountDownTimer(3000,1000) {
					@Override
					public void onTick(long millisUntilFinished) {
					}
					@Override
					public void onFinish() {
						loadBorgAir(true);
					}
				};
				timer.start();
				break;	
			case 2:
				loadBorgFatigue(true);
				break;	
			case 3:
				Activa.mySensorManager.programassociated = this;
				Activa.myUIManager.loadScreen(R.layout.info);
				TextView text2 = (TextView) Activa.myApp.findViewById(R.id.textInfo);
				if (this.walking6minutes) text2.setText(Activa.myLanguageManager.PROGRAMS_WALKING_ADVERT1);
				else text2.setText(Activa.myLanguageManager.PROGRAMS_WALKING_ADVERT01);
				LinearLayout board = (LinearLayout) Activa.myApp.findViewById(R.id.mainLayout);
				board.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						((Exercise) Activa.mySensorManager.sensorList.get(com.o2hlink.activa.data.sensor.SensorManager.ID_EXERCISE)).track = true;
						((Exercise) Activa.mySensorManager.sensorList.get(com.o2hlink.activa.data.sensor.SensorManager.ID_EXERCISE)).startMeasurement(time, timedMessages);
					}
				});
				break;	
			case 4:
				Activa.myUIManager.loadScreen(R.layout.info);
				TextView text3 = (TextView) Activa.myApp.findViewById(R.id.textInfo);
				text3.setText(Activa.myLanguageManager.PROGRAMS_WALKING_ADVERT2);
				CountDownTimer timer3 = new CountDownTimer(3000,1000) {
					@Override
					public void onTick(long millisUntilFinished) {
					}
					@Override
					public void onFinish() {
						loadBorgAir(false);
					}
				};
				timer3.start();
				break;
			case 5:
				loadBorgFatigue(false);
				break;	
			case 6:
				finishProgram();
		}
	}

	@Override
	public void showProgress() {
		
	}
	
	public void loadBorgAir(final boolean pre) {
		TextView questionText;
		ImageButton next;
		Activa.myApp.setContentView(R.layout.numquestion);
		final String representation[] = {Activa.myLanguageManager.BORG_0, Activa.myLanguageManager.BORG_05, 
				Activa.myLanguageManager.BORG_1, Activa.myLanguageManager.BORG_1, 
				Activa.myLanguageManager.BORG_2, Activa.myLanguageManager.BORG_2, 
				Activa.myLanguageManager.BORG_3, Activa.myLanguageManager.BORG_3, 
				Activa.myLanguageManager.BORG_4, Activa.myLanguageManager.BORG_4, 
				Activa.myLanguageManager.BORG_5, Activa.myLanguageManager.BORG_5, 
				Activa.myLanguageManager.BORG_5, Activa.myLanguageManager.BORG_5, 
				Activa.myLanguageManager.BORG_7, Activa.myLanguageManager.BORG_7, 
				Activa.myLanguageManager.BORG_7, Activa.myLanguageManager.BORG_7, 
				Activa.myLanguageManager.BORG_9, Activa.myLanguageManager.BORG_9, 
				Activa.myLanguageManager.BORG_10, Activa.myLanguageManager.BORG_10};
		questionText = (TextView) Activa.myApp.findViewById(R.id.questionText);
		final TextView numText = (TextView) Activa.myApp.findViewById(R.id.numText);
		questionText.setText(this.borgAir.text);
		numText.setText("0 - " + representation[0]);
		if (pre) borgAirPre = 0.0f;
		else borgAirPost = 0.0f;
		SeekBar seekbar = (SeekBar) Activa.myApp.findViewById(R.id.seekbar);
		seekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {				
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				
			}
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				
			}
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				int selection = progress/5;
				if ((selection == 1)) {
					numText.setText("0.5 - " + representation[1]);
					if (pre) borgAirPre = 0.5f;
					else borgAirPost = 0.5f;
				}
				else {
					numText.setText("" + progress/10 + " - " + representation[selection]);	
					if (pre) borgAirPre = progress/10;
					else borgAirPost = progress/10;
				}
			}
		});
		ImageButton prev = (ImageButton) Activa.myApp.findViewById(R.id.previous);
		prev.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				instance.prevStep();
			}
		});
		next = (ImageButton) Activa.myApp.findViewById(R.id.next);
		next.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				instance.nextStep();
			}
		});
	}
	
	public void loadBorgFatigue(final boolean pre) {
		TextView questionText;
		ImageButton next;
		Activa.myApp.setContentView(R.layout.numquestion);
		final String representation[] = {Activa.myLanguageManager.BORG_0, Activa.myLanguageManager.BORG_05, 
				Activa.myLanguageManager.BORG_1, Activa.myLanguageManager.BORG_1, 
				Activa.myLanguageManager.BORG_2, Activa.myLanguageManager.BORG_2, 
				Activa.myLanguageManager.BORG_3, Activa.myLanguageManager.BORG_3, 
				Activa.myLanguageManager.BORG_4, Activa.myLanguageManager.BORG_4, 
				Activa.myLanguageManager.BORG_5, Activa.myLanguageManager.BORG_5, 
				Activa.myLanguageManager.BORG_5, Activa.myLanguageManager.BORG_5, 
				Activa.myLanguageManager.BORG_7, Activa.myLanguageManager.BORG_7, 
				Activa.myLanguageManager.BORG_7, Activa.myLanguageManager.BORG_7, 
				Activa.myLanguageManager.BORG_9, Activa.myLanguageManager.BORG_9, 
				Activa.myLanguageManager.BORG_10, Activa.myLanguageManager.BORG_10};
		questionText = (TextView) Activa.myApp.findViewById(R.id.questionText);
		final TextView numText = (TextView) Activa.myApp.findViewById(R.id.numText);
		questionText.setText(this.borgFatigue.text);
		numText.setText("0 - " + representation[0]);
		if (pre) borgFatiguePre = 0.0f;
		else borgFatiguePost = 0.0f;
		SeekBar seekbar = (SeekBar) Activa.myApp.findViewById(R.id.seekbar);
		seekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {				
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				
			}
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				
			}
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				int selection = progress/5;
				if ((selection == 1)) {
					numText.setText("0.5 - " + representation[1]);
					if (pre) borgFatiguePre = 0.5f;
					else borgFatiguePost = 0.5f;
				}
				else {
					numText.setText("" + progress/10 + " - " + representation[selection]);	
					if (pre) borgFatiguePre = progress/10;
					else borgFatiguePost = progress/10;
				}
			}
		});
		ImageButton prev = (ImageButton) Activa.myApp.findViewById(R.id.previous);
		prev.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				instance.prevStep();
			}
		});
		next = (ImageButton) Activa.myApp.findViewById(R.id.next);
		next.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				instance.nextStep();
			}
		});
	}
	
	public void prevStep() {
		if (this.state <= 1) {
			if (Activa.myProgramManager.eventAssociated != null) Activa.myUIManager.loadScheduleDay(Activa.myProgramManager.eventAssociated.date);
			Activa.myUIManager.loadGeneratedSubenvironment(Activa.myUIManager.lastSubenv, true);
		}
		this.state -= 2;
		this.nextStep();
	}

}
