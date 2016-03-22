package com.o2hlink.activa.data.program;

import java.util.Hashtable;

import android.os.CountDownTimer;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.SeekBar.OnSeekBarChangeListener;

import com.o2hlink.activa.Activa;
import com.o2hlink.activa.R;
import com.o2hlink.activa.background.SendSensorResult;
import com.o2hlink.activa.background.SendWalkingResult;
import com.o2hlink.activa.data.questionnaire.Question;
import com.o2hlink.activa.data.sensor.Accelerometer;
import com.o2hlink.activa.data.sensor.Exercise;
import com.o2hlink.activa.data.sensor.PulseOximeter;
import com.o2hlink.activa.data.sensor.SensorManager;
import com.o2hlink.activa.data.sensor.Zephyr;
import com.o2hlink.activa.ui.UIManager;

public class Distance extends Program{

	Accelerometer accelerometer;
	
	Thread thread;
	
	public Distance() {
		this.name = "Medicion de distancia";
		this.id = ProgramManager.PROG_DISTANCE;
		this.type = ProgramManager.PROGTYPE_FOREGROUND;
		this.state = 0;
		this.icon = R.drawable.sport;
		this.description = "Este programa realiza una medicion de la distancia andada.";
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
		Activa.myApp.setContentView(R.layout.sending);
		if (Activa.myProgramManager.eventAssociated != null) Activa.myProgramManager.eventAssociated.state = 0;
		Activa.myUIManager.loadMainScreen(false);
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
				text.setText("Inicio de la medicion");
				CountDownTimer timer = new CountDownTimer(3000,1000) {
					@Override
					public void onTick(long millisUntilFinished) {
					}
					@Override
					public void onFinish() {
						nextStep();
					}
				};
				timer.start();
				break;	
			case 2:
				this.accelerometer = new Accelerometer(60000);
				this.thread = new Thread(this.accelerometer);
				this.thread.start();
			case 3:
				this.thread.interrupt();
				finishProgram();
		}
	}

	@Override
	public void showProgress() {
		
	}

}
