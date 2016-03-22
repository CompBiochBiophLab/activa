package com.o2hlink.activa.data.program;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.SeekBar.OnSeekBarChangeListener;

import com.o2hlink.activa.Activa;
import com.o2hlink.activa.R;
import com.o2hlink.activa.data.questionnaire.Question;
import com.o2hlink.activa.data.sensor.Zephyr;
import com.o2hlink.activa.ui.UIManager;

public class Physical extends Program{
	
	Question borg;

	public Physical() {
		this.name = "Actividad Fisica";
		this.id = ProgramManager.PROG_PHYSICAL;
		this.type = ProgramManager.PROGTYPE_FOREGROUND;
		this.state = 0;
		this.icon = R.drawable.sport;
		this.description = "Este programa se compone de dos actividades:\n\n" +
			"1. Ejercicio con el sensor Zephyr durante 45 minutos.\n\n" +
			"2. Cuestionario de fin de actividad (Escala de Borg).";
		this.borg = new Question(0, "Escala de Borg", 0, 2, false);
		this.borg.borg = true;
	}
	
	@Override
	public void startProgram() {
		this.state = 1;
		nextStep();
	}
	
	@Override
	public void finishProgram() {
		this.state = 0;
		Activa.mySensorManager.programassociated = null;
		Activa.myUIManager.finishProgram();
	}

	@Override
	public void nextStep() {
		switch (this.state) {
			case 0:
				break;
			case 1:
				Activa.mySensorManager.programassociated = this;
				((Zephyr) Activa.mySensorManager.sensorList.get(com.o2hlink.activa.data.sensor.SensorManager.ID_ZEPHYR)).startTimedMeasurement(45000);
				break;	
			case 2:
				Activa.myUIManager.state = UIManager.UI_STATE_QUESTION;
				TextView questionText;
				ImageButton next;
				Activa.myApp.setContentView(R.layout.numquestion);
				final String representation[] = {"Nada", "Muy muy leve", "Muy leve", "Muy leve", 
						"Leve", "Leve", "Moderada", "Moderada", "Algo severa", "Algo severa", 
						"Severa", "Severa", "Severa", "Severa", 
						"Muy severa", "Muy severa", "Muy severa", "Muy severa", 
						"Muy muy severa", "Muy muy severa", "Maxima", "Maxima"};
				questionText = (TextView) Activa.myApp.findViewById(R.id.questionText);
				final TextView numText = (TextView) Activa.myApp.findViewById(R.id.numText);
				questionText.setText(this.borg.text);
				if (this.borg.borg)
					numText.setText("0 - Nada");
				else 
					numText.setText("0");
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
						if (borg.borg)
							numText.setText("" + progress/10 + " - " + representation[progress/5]);
						else 
							numText.setText("" + progress/10);						
					}
				});
				ImageButton prev = (ImageButton) Activa.myApp.findViewById(R.id.previous);
				prev.setVisibility(View.INVISIBLE);
				next = (ImageButton) Activa.myApp.findViewById(R.id.next);
				next.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						Activa.myProgramManager.programList.get(ProgramManager.PROG_PHYSICAL).finishProgram();
					}
				});
		}
	}

	@Override
	public void showProgress() {
		
	}

}
