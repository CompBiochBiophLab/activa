package com.o2hlink.healthgenius.ui;

import java.util.Enumeration;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.CountDownTimer;
import android.text.Html;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.ImageView.ScaleType;
import android.widget.TableLayout.LayoutParams;

import com.o2hlink.healthgenius.HealthGenius;
import com.o2hlink.healthgenius.R;
import com.o2hlink.healthgenius.background.SendSensorResult;
import com.o2hlink.healthgenius.data.sensor.Exercise;
import com.o2hlink.healthgenius.data.sensor.Sensor;
import com.o2hlink.healthgenius.data.sensor.SensorManager;
import com.o2hlink.healthgenius.data.sensor.SixMinutes;
import com.o2hlink.healthgenius.data.sensor.Spirometer;

public class UIManagerMeasurements {
	
	public UIManager myUIManager;
	
	public UIManagerMeasurements(UIManager ui) {
		myUIManager = ui;
	}
	
	public void loadSensorList() {
		myUIManager.state = UIManager.UI_STATE_TOTALSENSOR;
		int width = HealthGenius.myApp.getResources().getDisplayMetrics().widthPixels - 130;
		HealthGenius.myApp.setContentView(R.layout.list);
		if (HealthGenius.myMenu != null) {
			HealthGenius.myMenu.clear();
			HealthGenius.myInflater.inflate(R.menu.sensors, HealthGenius.myMenu);
		}
		((TextView) HealthGenius.myApp.findViewById(R.id.startText)).append(HealthGenius.myLanguageManager.SENSORS_TITLE);
		TableLayout sensorlisting = (TableLayout)HealthGenius.myApp.findViewById(R.id.listing);
		Enumeration<Sensor> enumer = HealthGenius.mySensorManager.sensorList.elements();
		Enumeration<Long> sensorIDs = HealthGenius.mySensorManager.sensorList.keys();
		while (sensorIDs.hasMoreElements()) {
			final Sensor sensor = HealthGenius.mySensorManager.sensorList.get(sensorIDs.nextElement());
			TableRow buttonLayout = new TableRow(HealthGenius.myApp);
			buttonLayout.setOrientation(TableRow.HORIZONTAL);
			buttonLayout.setGravity(Gravity.CENTER_VERTICAL);
			buttonLayout.setLayoutParams(new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));			
			ImageButton button = new ImageButton(HealthGenius.myApp);
			button.setBackgroundResource(R.drawable.iconbg);
			button.setImageResource(sensor.icon);
			OnClickListener listener = new OnClickListener() {			
				@Override
				public void onClick(View v) {
					if (HealthGenius.myMenu != null) HealthGenius.myMenu.clear();
					HealthGenius.mySensorManager.eventAssociated = null;
					HealthGenius.mySensorManager.startSensorMeasurement(sensor.id);
				}
			};
			button.setOnClickListener(listener);
			TextView text = new TextView(HealthGenius.myApp);
			text.setLayoutParams(new android.widget.TableRow.LayoutParams(width, android.widget.TableRow.LayoutParams.WRAP_CONTENT));
			text.append(sensor.name);
			text.setTextSize((float) 20);
			text.setTextColor(Color.BLACK);
			text.setTypeface(Typeface.SANS_SERIF);
			text.setOnClickListener(listener);
			ImageButton helpsensor = new ImageButton(HealthGenius.myApp);
			helpsensor.setLayoutParams(new android.widget.TableRow.LayoutParams(45, 45));
			helpsensor.setBackgroundResource(R.drawable.iconbg);
			helpsensor.setImageResource(R.drawable.help);
			helpsensor.setScaleType(ScaleType.FIT_XY);
			helpsensor.setOnClickListener(new OnClickListener() {			
				@Override
				public void onClick(View v) {
					String textToPut = "";
					if (sensor.id == SensorManager.ID_PULSIOXYMETER) {
						textToPut = HealthGenius.myLanguageManager.TEXT_SENSORPAIR + HealthGenius.myLanguageManager.TEXT_PULSEOXYMETRY;
					}
					else if (sensor.id == SensorManager.ID_SPIROMETER) {
						textToPut = HealthGenius.myLanguageManager.TEXT_SENSORPAIR + HealthGenius.myLanguageManager.TEXT_SPIROMETRY;
					}
					else if (sensor.id == SensorManager.ID_EXERCISE) {
						textToPut = HealthGenius.myLanguageManager.TEXT_SENSORPAIR + HealthGenius.myLanguageManager.TEXT_EXERCISE;
					}
					else if (sensor.id == SensorManager.ID_SIXMINUTES) {
						textToPut = HealthGenius.myLanguageManager.TEXT_SENSORPAIR + HealthGenius.myLanguageManager.TEXT_SIXMINUTESWALK;
					}
					else if (sensor.id == SensorManager.ID_WEIGHT) {
						textToPut = HealthGenius.myLanguageManager.TEXT_SENSORPAIR + HealthGenius.myLanguageManager.TEXT_WEIGHTHEIGHT;
					}
					LayoutInflater inflater = (LayoutInflater) HealthGenius.myApp.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
					final View layout = inflater.inflate(R.layout.toasttext,
							(ViewGroup) HealthGenius.myApp.findViewById(R.id.toasttextroot));
					TextView text = (TextView) layout.findViewById(R.id.searchexpl);
					text.setText(Html.fromHtml(textToPut));
					Builder builder = new AlertDialog.Builder(HealthGenius.myApp);
					builder.setView(layout);
					final AlertDialog alertDialog = builder.create();
					alertDialog.show();
				}
			});
			button.setOnClickListener(listener);
			buttonLayout.addView(button);
			buttonLayout.addView(text);
			buttonLayout.addView(helpsensor);
			buttonLayout.setOnClickListener(listener);
			button.setOnClickListener(listener);
			text.setOnClickListener(listener);
			sensorlisting.addView(buttonLayout);
		}
		ImageButton help = (ImageButton) HealthGenius.myApp.findViewById(R.id.help);
		help.setVisibility(View.INVISIBLE);
		ImageButton back = (ImageButton) HealthGenius.myApp.findViewById(R.id.previous);
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				HealthGenius.myUIManager.loadBoxOpen();
			}
		});
	}

	public void finishSensorMeasurement(String sensorString, boolean outcome, final Sensor sensor) {
//        HealthGenius.myBluetoothAdapter.disable();
		if (outcome) {
			HealthGenius.myApp.setContentView(R.layout.sensorresult);
			TextView textTitle = (TextView) HealthGenius.myApp.findViewById(R.id.startText);
			TextView text = (TextView) HealthGenius.myApp.findViewById(R.id.textResult);
			((TextView) HealthGenius.myApp.findViewById(R.id.resultsWord)).setText(HealthGenius.myLanguageManager.SENSORS_RESULTSWORD);
			textTitle.setText(sensorString);
			text.setText("");
			Enumeration<Integer> keys = sensor.results.keys();
			while (keys.hasMoreElements()) {
				int key = keys.nextElement();
				String meas = SensorManager.getMeasurementName(key);
				if (meas != null) {
					text.append(meas + ": " + String.format("%.1f", (float)sensor.results.get(key)));
					text.append(" " + SensorManager.getUnitForMeasurement(SensorManager.getUnitIDForMeasurementID(key)));
					if (keys.hasMoreElements()) text.append("\n");
					if (key > 1000) sensor.results.remove(key);
				}
			}
			final ImageButton layout = (ImageButton) HealthGenius.myApp.findViewById(R.id.ok);
			OnClickListener listener = new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					String result = sensor.getSensorGlobalResult();
					int resultInt = Integer.parseInt(result.substring(0, 1));
					String resultString = result.substring(2);
					loadSensorGlobalResult(resultString, resultInt, sensor);
				}
			};
			layout.setOnClickListener(listener);
			text.setOnClickListener(listener);
		}
		else {
			HealthGenius.myApp.setContentView(R.layout.info);
			TextView text = (TextView) HealthGenius.myApp.findViewById(R.id.textInfo);
			text.setText(HealthGenius.myLanguageManager.SENSORS_CANCELLED);
			CountDownTimer timer = new CountDownTimer(6000, 1000) {
				@Override
				public void onTick(long millisUntilFinished) {}
				@Override
				public void onFinish() {
//					if (sensor.bluetoothPreviouslyConnected) 
//						HealthGenius.myBluetoothAdapter.enable();
					HealthGenius.myUIManager.loadBoxOpen();
				}
			};
			timer.start();
		}
	}
	
	public void finishSensorMeasurement(final String sensorString, boolean outcome, final Sensor sensor, int[] order) {
//      HealthGenius.myBluetoothAdapter.disable();
		if (outcome) {
			HealthGenius.myApp.setContentView(R.layout.sensorresult);
			TextView textTitle = (TextView) HealthGenius.myApp.findViewById(R.id.startText);
			TextView text = (TextView) HealthGenius.myApp.findViewById(R.id.textResult);
			((TextView) HealthGenius.myApp.findViewById(R.id.resultsWord)).setText(HealthGenius.myLanguageManager.SENSORS_RESULTSWORD);
			textTitle.setText(sensorString);
			text.setText("");
			for (int i=0; i < order.length; i++) {
				int key = order[i];
				if (key == -1) {
					text.append("\n");
					continue;
				}
				String meas = SensorManager.getMeasurementName(key);
				if (meas != null) {
					if ((SensorManager.getUnitIDForMeasurementID(key) != SensorManager.UNIT_SEC)&&(SensorManager.getUnitIDForMeasurementID(key) != SensorManager.UNIT_NULL)) text.append(meas + ": " + String.format("%.1f", (float)sensor.results.get(key)));
					else text.append(meas + ": " + Math.round((float)sensor.results.get(key)));
					text.append(" " + SensorManager.getUnitForMeasurement(SensorManager.getUnitIDForMeasurementID(key)));
					if (i != (order.length - 1)) text.append("\n");
					if (key > 1000) sensor.results.remove(key);
				}
			}
			final ImageButton layout = (ImageButton) HealthGenius.myApp.findViewById(R.id.ok);
			OnClickListener listener = new OnClickListener() {
				@Override
				public void onClick(View v) {
					String result = sensor.getSensorGlobalResult();
					int resultInt = Integer.parseInt(result.substring(0, 1));
					String resultString = result.substring(2);
					loadSensorGlobalResult(resultString, resultInt, sensor);
				}
			};
			layout.setOnClickListener(listener);
			text.setOnClickListener(listener);
		}
		else {
			HealthGenius.myApp.setContentView(R.layout.info);
			TextView text = (TextView) HealthGenius.myApp.findViewById(R.id.textInfo);
			text.setText(HealthGenius.myLanguageManager.SENSORS_CANCELLED);
			CountDownTimer timer = new CountDownTimer(6000, 1000) {
				@Override
				public void onTick(long millisUntilFinished) {}
				@Override
				public void onFinish() {
//					if (sensor.bluetoothPreviouslyConnected) 
//						HealthGenius.myBluetoothAdapter.enable();
					HealthGenius.myUIManager.loadBoxOpen();
				}
			};
			timer.start();
		}
	}
	
	public void finishWalkingTest(final String sensorString, boolean outcome, final SixMinutes sensor, int[] order) {
		if (outcome) {
			HealthGenius.myApp.setContentView(R.layout.sensorresultwithgraph);
			TextView textTitle = (TextView) HealthGenius.myApp.findViewById(R.id.startText);
			TextView text = (TextView) HealthGenius.myApp.findViewById(R.id.textResult);
			((TextView) HealthGenius.myApp.findViewById(R.id.resultsWord)).setText(HealthGenius.myLanguageManager.SENSORS_RESULTSWORD);
			textTitle.setText(sensorString);
			text.setText("");
			Enumeration<Integer> keys = sensor.results.keys();
			while (keys.hasMoreElements()) {
				int key = keys.nextElement();
				String meas = SensorManager.getMeasurementName(key);
				text.append(meas + ": " + String.format("%.2f", (float)sensor.results.get(key)));
				text.append(" " + SensorManager.getUnitForMeasurement(SensorManager.getUnitIDForMeasurementID(key)));
				if (keys.hasMoreElements()) text.append("\n");
			}
			// Draw the spirometry graph
			final FrameLayout board = (FrameLayout) HealthGenius.myApp.findViewById(R.id.graph);
			board.addView(new SixMinutesGraphViewWithCustomData(HealthGenius.myApp, sensor.getGraphs().getHeartRate(), sensor.getGraphs().getOxygenSaturation(), sensor.getGraphs().getTime(), 250, 250));
			// A count down
			final ImageButton layout = (ImageButton) HealthGenius.myApp.findViewById(R.id.ok);
			OnClickListener listener = new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					String result = sensor.getSensorGlobalResult();
					int resultInt = Integer.parseInt(result.substring(0, 1));
					String resultString = result.substring(2);
					loadSensorGlobalResult(resultString, resultInt, sensor);
				}
			};
			layout.setOnClickListener(listener);
		}
		else {
			HealthGenius.myApp.setContentView(R.layout.info);
			TextView text = (TextView) HealthGenius.myApp.findViewById(R.id.textInfo);
			text.setText(HealthGenius.myLanguageManager.SENSORS_CANCELLED);
			CountDownTimer timer = new CountDownTimer(6000, 1000) {
				@Override
				public void onTick(long millisUntilFinished) {}
				@Override
				public void onFinish() {
//					if (sensor.bluetoothPreviouslyConnected) 
//						HealthGenius.myBluetoothAdapter.enable();
					HealthGenius.myUIManager.loadBoxOpen();
				}
			};
			timer.start();
		}
	}
	
	public void finishSpirometry(String sensorString, boolean outcome, final Spirometer sensor) {
//        HealthGenius.myBluetoothAdapter.disable();
		if (outcome) {
			HealthGenius.myApp.setContentView(R.layout.sensorresultwithgraph);
			TextView textTitle = (TextView) HealthGenius.myApp.findViewById(R.id.startText);
			TextView text = (TextView) HealthGenius.myApp.findViewById(R.id.textResult);
			((TextView) HealthGenius.myApp.findViewById(R.id.resultsWord)).setText(HealthGenius.myLanguageManager.SENSORS_RESULTSWORD);
			textTitle.setText(sensorString);
			text.setText("");
			Enumeration<Integer> keys = sensor.results.keys();
			while (keys.hasMoreElements()) {
				int key = keys.nextElement();
				String meas = SensorManager.getMeasurementName(key);
				text.append(meas + ": " + String.format("%.2f", (float)sensor.results.get(key)));
				text.append(" " + SensorManager.getUnitForMeasurement(SensorManager.getUnitIDForMeasurementID(key)));
				if (keys.hasMoreElements()) text.append("\n");
			}
			// Draw the spirometry graph
			final FrameLayout board = (FrameLayout) HealthGenius.myApp.findViewById(R.id.graph);
			board.addView(new SpirometryGraphView(HealthGenius.myApp, sensor));
			// A count down
			final ImageButton layout = (ImageButton) HealthGenius.myApp.findViewById(R.id.ok);
			OnClickListener listener = new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					String result = sensor.getSensorGlobalResult();
					int resultInt = Integer.parseInt(result.substring(0, 1));
					String resultString = result.substring(2);
					loadSensorGlobalResult(resultString, resultInt, sensor);
				}
			};
			layout.setOnClickListener(listener);
		}
		else {
			HealthGenius.myApp.setContentView(R.layout.info);
			TextView text = (TextView) HealthGenius.myApp.findViewById(R.id.textInfo);
			text.setText(HealthGenius.myLanguageManager.SENSORS_CANCELLED);
			CountDownTimer timer = new CountDownTimer(6000, 1000) {
				@Override
				public void onTick(long millisUntilFinished) {}
				@Override
				public void onFinish() {
//					if (sensor.bluetoothPreviouslyConnected) 
//						HealthGenius.myBluetoothAdapter.enable();
					HealthGenius.myUIManager.loadBoxOpen();
				}
			};
			timer.start();
		}
	}
	
	public void finishExercise(boolean outcome, final Exercise sensor) {
		if (outcome) {
			HealthGenius.myApp.setContentView(R.layout.sensorresult);
			TextView textTitle = (TextView) HealthGenius.myApp.findViewById(R.id.startText);
			TextView text = (TextView) HealthGenius.myApp.findViewById(R.id.textResult);
			textTitle.setText(HealthGenius.myLanguageManager.PROGRAMS_EXERCISE_TITLE);
			text.setText("");
			Enumeration<Integer> keys = sensor.results.keys();
			while (keys.hasMoreElements()) {
				int key = keys.nextElement();
				String meas = SensorManager.getMeasurementName(key);
				text.append(meas + ": " + String.format("%.1f", (float)sensor.results.get(key)));
				text.append(" " + SensorManager.getUnitForMeasurement(SensorManager.getUnitIDForMeasurementID(key)));
				if (keys.hasMoreElements()) text.append("\n");
			}
			CountDownTimer timer = new CountDownTimer(6000, 1000) {
				@Override
				public void onTick(long millisUntilFinished) {}
				@Override
				public void onFinish() {
//					HealthGenius.myApp.setContentView(R.layout.sending);
//					SendSensorResult sending = new SendSensorResult(sensor);
//					Thread thread = new Thread(sending, "SENDQUESTIONNAIRE");
//					thread.start();
					String result = sensor.getSensorGlobalResult();
					int resultInt = Integer.parseInt(result.substring(0, 1));
					String resultString = result.substring(2);
					loadSensorGlobalResult(resultString, resultInt, sensor);
				}
			};
			timer.start();
		}
		else {
			HealthGenius.myApp.setContentView(R.layout.info);
			TextView text = (TextView) HealthGenius.myApp.findViewById(R.id.textInfo);
			text.setText(HealthGenius.myLanguageManager.PROGRAMS_EXERCISE_CANCELLED);
			CountDownTimer timer = new CountDownTimer(6000, 1000) {
				@Override
				public void onTick(long millisUntilFinished) {}
				@Override
				public void onFinish() {
					HealthGenius.myUIManager.loadBoxOpen();
				}
			};
			timer.start();
		}
	}
	
	public void loadSensorGlobalResult (String result, int resultInt, final Sensor sensor) {
		HealthGenius.myApp.setContentView(R.layout.resultimage);
		TextView text = (TextView) HealthGenius.myApp.findViewById(R.id.textInfo);
		text.setText(result);
		if (result.length() > 60) text.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18.0f); 
		ImageView image = (ImageView) HealthGenius.myApp.findViewById(R.id.result);
		switch (resultInt) {
			case 2:
				image.setBackgroundResource(R.drawable.lightgreen);
				break;
			case 1:
				image.setBackgroundResource(R.drawable.lightorange);
				break;
			case 0:
				image.setBackgroundResource(R.drawable.lightwhite);
				break;
		}
		CountDownTimer timer = new CountDownTimer(4000, 1000) {
			@Override
			public void onTick(long millisUntilFinished) {}
			@Override
			public void onFinish() {
				if (sensor instanceof SixMinutes) {
					((SixMinutes)sensor).nextStep();
				}
				else if (sensor instanceof Exercise) {
					((Exercise)sensor).nextStep();
				}
				else {
					HealthGenius.myApp.setContentView(R.layout.sending);
					SendSensorResult sending = new SendSensorResult(sensor);
					Thread thread = new Thread(sending, "SENDSENSOR");
					thread.start();
				}
			}
		};
		timer.start();
	}
	
	public void loadSixMinutesScreen (String resultGlobal, final long time) {
		myUIManager.state = UIManager.UI_STATE_EXERCISE;
		HealthGenius.myApp.setContentView(R.layout.sixminutes);
		TextView hrtitle = (TextView) HealthGenius.myApp.findViewById(R.id.hrTitle);
		TextView hrtext = (TextView) HealthGenius.myApp.findViewById(R.id.hrText);
		TextView so2title = (TextView) HealthGenius.myApp.findViewById(R.id.so2Title);
		TextView so2text = (TextView) HealthGenius.myApp.findViewById(R.id.so2Text);
		TextView stepstitle = (TextView) HealthGenius.myApp.findViewById(R.id.stepsTitle);
		TextView stepstext = (TextView) HealthGenius.myApp.findViewById(R.id.stepsText);
		TextView global = (TextView) HealthGenius.myApp.findViewById(R.id.textGlobal);
		hrtitle.setText(HealthGenius.myLanguageManager.SENSORS_EXERCISE_HEARTRATE);
		so2title.setText(HealthGenius.myLanguageManager.SENSORS_EXERCISE_O2SAT);
		stepstitle.setText(HealthGenius.myLanguageManager.SENSORS_EXERCISE_STEPSTOP);
		hrtext.setText("-");
		so2text.setText("-");
		stepstext.setText("0/0");
		global.setText(resultGlobal);
		ImageView image = (ImageView) HealthGenius.myApp.findViewById(R.id.resultgreen);
		image.setVisibility(View.VISIBLE);
		int seconds = (int) (time / 1000);
	    int minutes = seconds / 60;
	    seconds     = seconds % 60;
	    ((TextView) HealthGenius.myApp.findViewById(R.id.timerText)).setText(String.format("%02d:%02d", minutes, seconds));			
	}
	
	public void loadExerciseScreen (String resultGlobal, final long time) {
		myUIManager.state = UIManager.UI_STATE_EXERCISE;
		HealthGenius.myApp.setContentView(R.layout.exercise);
		TextView hrtitle = (TextView) HealthGenius.myApp.findViewById(R.id.hrTitle);
		TextView hrtext = (TextView) HealthGenius.myApp.findViewById(R.id.hrText);
		TextView so2title = (TextView) HealthGenius.myApp.findViewById(R.id.so2Title);
		TextView so2text = (TextView) HealthGenius.myApp.findViewById(R.id.so2Text);
		TextView stepstitle = (TextView) HealthGenius.myApp.findViewById(R.id.stepsTitle);
		TextView stepstext = (TextView) HealthGenius.myApp.findViewById(R.id.stepsText);
		TextView global = (TextView) HealthGenius.myApp.findViewById(R.id.textGlobal);
		TextView speedtext = (TextView) HealthGenius.myApp.findViewById(R.id.speedText);
		TextView distancetext = (TextView) HealthGenius.myApp.findViewById(R.id.distanceText);
		hrtitle.setText(HealthGenius.myLanguageManager.SENSORS_EXERCISE_HEARTRATE);
		so2title.setText(HealthGenius.myLanguageManager.SENSORS_EXERCISE_O2SAT);
		stepstitle.setText(HealthGenius.myLanguageManager.SENSORS_EXERCISE_STEPSTOP);
		hrtext.setText("-");
		so2text.setText("-");
		stepstext.setText("0/0");
		speedtext.setText("-");
		distancetext.setText("-");
		global.setText(resultGlobal);
		ImageView image = (ImageView) HealthGenius.myApp.findViewById(R.id.resultgreen);
		image.setVisibility(View.VISIBLE);
		int seconds = (int) (time / 1000);
	    int minutes = seconds / 60;
	    seconds     = seconds % 60;
	    int hours = minutes / 60;
	    minutes = minutes % 60;
	    ((TextView) HealthGenius.myApp.findViewById(R.id.timerText)).setText(String.format("%01d:%02d:%02d", hours, minutes, seconds));			
	    		
	}
	
	public void updateExerciseScreen (int hr, int so2, int steps, int stops, String resultGlobal) {
		try {
			TextView global = (TextView) HealthGenius.myApp.findViewById(R.id.textGlobal);
			int resultImage = Integer.parseInt(resultGlobal.substring(0,1));
			resultGlobal = resultGlobal.substring(2);
			global.setText(resultGlobal);
			TextView hrtext = (TextView) HealthGenius.myApp.findViewById(R.id.hrText);
			TextView so2text = (TextView) HealthGenius.myApp.findViewById(R.id.so2Text);
			TextView stepstext = (TextView) HealthGenius.myApp.findViewById(R.id.stepsText);
			if (hr != 0) hrtext.setText(hr + " bpm");
			else hrtext.setText("-");
			if (so2 != 0) so2text.setText(so2 + " %");
			else so2text.setText("-");
			stepstext.setText(steps + "/" + stops);
			ImageView imagegreen = (ImageView) HealthGenius.myApp.findViewById(R.id.resultgreen);
			ImageView imageorange = (ImageView) HealthGenius.myApp.findViewById(R.id.resultorange);
			ImageView imagewhite = (ImageView) HealthGenius.myApp.findViewById(R.id.resultwhite);
			switch (resultImage) {
				case 2:
					imagegreen.setVisibility(View.VISIBLE);
					imageorange.setVisibility(View.INVISIBLE);
					imagewhite.setVisibility(View.INVISIBLE);
					break;
				case 1:
					imagegreen.setVisibility(View.INVISIBLE);
					imageorange.setVisibility(View.VISIBLE);
					imagewhite.setVisibility(View.INVISIBLE);
					break;
				case 0:
					imagegreen.setVisibility(View.INVISIBLE);
					imageorange.setVisibility(View.INVISIBLE);
					imagewhite.setVisibility(View.VISIBLE);
					break;
			}
		} catch (NullPointerException e) {
		}
	}
	
	public void updateExerciseScreen (Float speed, Float distance) {
		try {
			TextView speedtext = (TextView) HealthGenius.myApp.findViewById(R.id.speedText);
			TextView distancetext = (TextView) HealthGenius.myApp.findViewById(R.id.distanceText);
			if (speed != null) speedtext.setText(String.format(".1f", speed) + " km/h");
			else speedtext.setText("-");
			if (distance != 0) distancetext.setText(Math.round(distance) + " m");
			else distancetext.setText("-");
		} catch (NullPointerException e) {
		}
	}

}
