package com.o2hlink.healthgenius.ui;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.os.Message;
import android.provider.Contacts.Intents.UI;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AbsoluteLayout;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
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

import com.google.api.translate.Language;
import com.google.api.translate.Translate;
import com.o2hlink.activ8.client.entity.Array;
import com.o2hlink.activ8.client.entity.Clinician;
import com.o2hlink.activ8.client.entity.Disease;
import com.o2hlink.activ8.client.entity.Questionnaire;
import com.o2hlink.activ8.client.entity.QuestionnaireSample;
import com.o2hlink.activ8.client.entity.Sample;
import com.o2hlink.activ8.client.entity.Spirometry;
import com.o2hlink.activ8.common.entity.EventFrequency;
import com.o2hlink.activ8.common.entity.Measurement;
import com.o2hlink.healthgenius.HealthGenius;
import com.o2hlink.healthgenius.HealthGeniusUtil;
import com.o2hlink.healthgenius.R;
import com.o2hlink.healthgenius.background.GetHistory;
import com.o2hlink.healthgenius.data.calendar.ExerciseSample;
import com.o2hlink.healthgenius.data.calendar.PulseoximetrySample;
import com.o2hlink.healthgenius.data.calendar.SixMinutesWalkSample;
import com.o2hlink.healthgenius.data.calendar.SpirometrySample;
import com.o2hlink.healthgenius.patient.HistoryRecord;
import com.o2hlink.healthgenius.patient.Patient;
import com.o2hlink.healthgenius.patient.WeightHeightSample;

public class UIManagerPatient {
	
	public UIManager myUIManager;
	
	public UIManagerPatient(UIManager ui) {
		myUIManager = ui;
	}
	
	public void showHistory(final Patient patient, final boolean returning) {
		myUIManager.state = UIManager.UI_STATE_PATIENTS;
		int width = HealthGenius.myApp.getResources().getDisplayMetrics().widthPixels;
		HealthGenius.myApp.setContentView(R.layout.history);
		((TextView) HealthGenius.myApp.findViewById(R.id.startText)).append(HealthGenius.myLanguageManager.PATIENTS_HISTORY);
		
		LinearLayout content = (LinearLayout) HealthGenius.myApp.findViewById(R.id.content);
		TextView text = new TextView(HealthGenius.myApp);
		text.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		text.setTextSize((float) 14.0);
		text.setPadding(10, 0, 0, 0);
		text.setTextColor(Color.BLACK);
		text.setText(patient.getPatientPersonalData());
		content.addView(text);
		AbsoluteLayout separator = new AbsoluteLayout(HealthGenius.myApp);
		separator.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, 1));
		separator.setBackgroundColor(Color.BLACK);
		content.addView(separator);

		content = (LinearLayout) HealthGenius.myApp.findViewById(R.id.content);
		text = new TextView(HealthGenius.myApp);
		text.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		text.setTextSize((float) 14.0);
		text.setPadding(10, 0, 0, 0);
		text.setTextColor(Color.BLACK);
		text.append(patient.getMedicalRecordsSpan());
		content.addView(text);
		if (HealthGenius.myMobileManager.userForServices instanceof Clinician) {
			text = new TextView(HealthGenius.myApp);
			text.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
			text.setTextSize((float) 14.0);
			text.setPadding(10, 0, 0, 0);
			text.setLinksClickable(true);
			text.setTextColor(Color.MAGENTA);
			text.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					addExploration(patient.getId(), returning);
				}
			});
			text.setText(HealthGenius.myLanguageManager.PATIENTS_HISTORY_ADDEXPLORATION);
			content.addView(text);
			text = new TextView(HealthGenius.myApp);
			text.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
			text.setTextSize((float) 14.0);
			text.setPadding(10, 0, 0, 0);
			text.setLinksClickable(true);
			text.setTextColor(Color.MAGENTA);
			text.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					addHistoryRecord(patient.getId(), returning);
				}
			});
			text.setText(HealthGenius.myLanguageManager.PATIENTS_HISTORY_ADDRECORD);
			content.addView(text);
		}
		separator = new AbsoluteLayout(HealthGenius.myApp);
		separator.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, 1));
		separator.setBackgroundColor(Color.BLACK);
		content.addView(separator);

		content = (LinearLayout) HealthGenius.myApp.findViewById(R.id.content);
		text = new TextView(HealthGenius.myApp);
		text.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		text.setTextSize((float) 14.0);
		text.setPadding(10, 0, 0, 0);
		text.setTextColor(Color.BLACK);
		if (patient.lastWeightHeight == null) text.append("No weight and height done.");
		else text.append(patient.lastWeightHeight.getWeightHeightSpanData());
		content.addView(text);
		separator = new AbsoluteLayout(HealthGenius.myApp);
		separator.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, 1));
		separator.setBackgroundColor(Color.BLACK);
		content.addView(separator);

		content = (LinearLayout) HealthGenius.myApp.findViewById(R.id.content);
		text = new TextView(HealthGenius.myApp);
		text.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		text.setTextSize((float) 14.0);
		text.setPadding(10, 0, 0, 0);
		text.setTextColor(Color.BLACK);
		if (patient.lastPulseoximetry == null) text.append("No pulseoximetry done.");
		else text.append(patient.lastPulseoximetry.getPulsioximetrySpanData());
		content.addView(text);
		separator = new AbsoluteLayout(HealthGenius.myApp);
		separator.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, 1));
		separator.setBackgroundColor(Color.BLACK);
		content.addView(separator);

		content = (LinearLayout) HealthGenius.myApp.findViewById(R.id.content);
		text = new TextView(HealthGenius.myApp);
		text.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		text.setTextSize((float) 14.0);
		text.setPadding(10, 0, 0, 0);
		text.setTextColor(Color.BLACK);
		if (patient.lastSixMinutes == null) text.append("No six minutes walk done.");
		else text.append(patient.lastSixMinutes.getExerciseSpanData());
		content.addView(text);
		if (patient.lastSixMinutes != null) {
			FrameLayout frame = new FrameLayout(HealthGenius.myApp);
			frame.setLayoutParams(new LayoutParams(width*4/5, width*4/5));
			SixMinutesGraphViewWithCustomData graph = new SixMinutesGraphViewWithCustomData(HealthGenius.myApp, patient.lastSixMinutes.hrtrack, patient.lastSixMinutes.so2track, patient.lastSixMinutes.time, width*4/5, width*4/5);
			frame.addView(graph);
			content.addView(frame);
		}
		separator = new AbsoluteLayout(HealthGenius.myApp);
		separator.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, 1));
		separator.setBackgroundColor(Color.BLACK);
		content.addView(separator);

		content = (LinearLayout) HealthGenius.myApp.findViewById(R.id.content);
		text = new TextView(HealthGenius.myApp);
		text.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		text.setTextSize((float) 14.0);
		text.setPadding(10, 0, 0, 0);
		text.setTextColor(Color.BLACK);
		if (patient.lastSpirometry == null) text.append("No spirometry done.");
		else text.append(patient.lastSpirometry.getSpirometrySpanData());
		content.addView(text);
		if (patient.lastSpirometry != null) {
			FrameLayout frame = new FrameLayout(HealthGenius.myApp);
			frame.setLayoutParams(new LayoutParams(width*4/5, width*4/5));
			SpirometryGraphViewWithCustomData graph = new SpirometryGraphViewWithCustomData(HealthGenius.myApp, (Spirometry) patient.lastSpirometry, patient.lastSpirometry.flow, patient.lastSpirometry.time, width*4/5, width*4/5);
			frame.addView(graph);
			content.addView(frame);
		}
		separator = new AbsoluteLayout(HealthGenius.myApp);
		separator.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, 1));
		separator.setBackgroundColor(Color.BLACK);
		content.addView(separator);

		content = (LinearLayout) HealthGenius.myApp.findViewById(R.id.content);
		text = new TextView(HealthGenius.myApp);
		text.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		text.setTextSize((float) 14.0);
		text.setPadding(10, 0, 0, 0);
		text.setTextColor(Color.BLACK);
		if (patient.lastExercise == null) text.append("No exercise done.");
		else text.append(patient.lastExercise.getExerciseSpanData());
		content.addView(text);
		separator = new AbsoluteLayout(HealthGenius.myApp);
		separator.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, 1));
		separator.setBackgroundColor(Color.BLACK);
		content.addView(separator);
		
		ImageButton back = (ImageButton) HealthGenius.myApp.findViewById(R.id.previous);
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!returning) loadPatientMenu(patient);
				else myUIManager.loadBoxOpen();
			}
		});
	}
	
	public void addHistoryRecord(final Long patientId, final boolean returning) {
		LayoutInflater inflater = (LayoutInflater) HealthGenius.myApp.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		final View layout = inflater.inflate(R.layout.toastsearch,
		                               (ViewGroup) HealthGenius.myApp.findViewById(R.id.toastsearchroot));
		TextView text = (TextView) layout.findViewById(R.id.searchexpl);
		text.setText(HealthGenius.myLanguageManager.PATIENTS_HISTORY_SEARCHDIS);
		ImageButton image = (ImageButton) layout.findViewById(R.id.searchok);
		Builder builder = new AlertDialog.Builder(HealthGenius.myApp);
		builder.setView(layout);
		final AlertDialog alertDialog = builder.create();
		alertDialog.show();
		image.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				alertDialog.cancel();
				final ProgressDialog dialog = ProgressDialog.show(HealthGenius.myApp, HealthGenius.myLanguageManager.PATIENTS_HISTORY, 
						HealthGenius.myLanguageManager.PATIENTS_HISTORY_SEARCHINGDIS, true);
				dialog.show();
				String querytemp;
				String selectedLang = HealthGenius.myApp.getResources().getConfiguration().locale.getLanguage();
				if (selectedLang.equals("es")) {
					Translate.setHttpReferrer("http://www.o2hlink.com");
					try {
						querytemp = Translate.execute(((EditText)layout.findViewById(R.id.searchtext)).getText().toString(), Language.SPANISH, Language.ENGLISH);
					} catch (Exception e) {
						querytemp = ((EditText)layout.findViewById(R.id.searchtext)).getText().toString();
						e.printStackTrace();
					}
				}
				else querytemp = ((EditText)layout.findViewById(R.id.searchtext)).getText().toString();
				final String query = querytemp;
				Runnable run =  new Runnable() {
					Array<Disease> diseases;
					Disease dis;
					@Override
					public void run() {
						diseases = HealthGenius.myPatientManager.searchDiseases(query);
						dialog.cancel();
						if (diseases == null) {
							handler.sendEmptyMessage(0);
						} else if (diseases.size() == 0) {
							handler.sendEmptyMessage(1);
						} else {
							handler.sendEmptyMessage(2);
						}
					}
					private Handler handler = new Handler() {
						@Override
						public void handleMessage(Message msg) {
							switch (msg.what) {
								case 0:
									Toast toast = Toast.makeText(HealthGenius.myApp, HealthGenius.myLanguageManager.CONNECTION_FAILED, Toast.LENGTH_SHORT);
									toast.show();
									break;
								case 1:
									Toast toast2 = Toast.makeText(HealthGenius.myApp, HealthGenius.myLanguageManager.PATIENTS_HISTORY_NORECORDSFOUND, Toast.LENGTH_SHORT);
									toast2.show();
									break;
								case 2:
									String[] itemstemp = new String[diseases.size()];
									for (int i = 0; i < diseases.size(); i++) {
										itemstemp[i] = diseases.get(i).getName();
									}
									String selectedLang = HealthGenius.myApp.getResources().getConfiguration().locale.getLanguage();
									if (selectedLang.equals("es")) {
										Translate.setHttpReferrer("http://www.o2hlink.com");
										try {
											itemstemp = Translate.execute(itemstemp, Language.ENGLISH, Language.SPANISH);
										} catch (Exception e) {
											e.printStackTrace();
										}
									}
									final String[] items = itemstemp;
									AlertDialog.Builder builder = new AlertDialog.Builder(HealthGenius.myApp);
									builder.setTitle(HealthGenius.myLanguageManager.PATIENTS_HISTORY_SELECTDIS);
									builder.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {								
										@Override
										public void onClick(DialogInterface dialog, int which) {
											dis = diseases.get(which);
										}
									});
									builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
								           public void onClick(DialogInterface dialog, int id) {
								        	   if (!HealthGenius.myPatientManager.addHistoryRecord(patientId, dis)) {
								        		   Toast toast2 = Toast.makeText(HealthGenius.myApp, HealthGenius.myLanguageManager.PATIENTS_HISTORY_NORECORDSFOUND, Toast.LENGTH_SHORT);
								        		   toast2.show();
								        	   }
								               dialog.cancel();
								               showHistory(HealthGenius.myPatientManager.patientSet.get(patientId), returning);
								           }
								    });
									AlertDialog alert = builder.create();
									alert.show();
									break;
							}
						}
					};
				};
				Thread thread = new Thread(run);
				thread.start();
			}
		});
	}
	
	public void addExploration(final Long patientId, final boolean returning) {
		if (HealthGenius.myPatientManager.patientSet.get(patientId).history.isEmpty()) {
			Toast toast = Toast.makeText(HealthGenius.myApp, HealthGenius.myLanguageManager.PATIENTS_HISTORY_NORECORDS, Toast.LENGTH_SHORT);
			toast.show();
			return;
		}
		LayoutInflater inflater = (LayoutInflater) HealthGenius.myApp.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		final View layout = inflater.inflate(R.layout.toastaddexploration,
		                               (ViewGroup) HealthGenius.myApp.findViewById(R.id.toastaddexplorationroot));
		((TextView)layout.findViewById(R.id.addtitle)).setText(HealthGenius.myLanguageManager.PATIENTS_HISTORY);
		((TextView)layout.findViewById(R.id.spinnerrequest)).setText(HealthGenius.myLanguageManager.PATIENTS_HISTORY_SELECTREC);
		((TextView)layout.findViewById(R.id.textrequest)).setText(HealthGenius.myLanguageManager.PATIENTS_HISTORY_TYPEEXPL);
		final EditText text = (EditText) layout.findViewById(R.id.text);
		final Spinner spinner = (Spinner) layout.findViewById(R.id.spinner);
		ImageButton ok = (ImageButton) layout.findViewById(R.id.addeventok);
		final ArrayList<String> spinnerArray = new ArrayList<String>();
		final ArrayList<com.o2hlink.activ8.client.entity.HistoryRecord> records = new ArrayList<com.o2hlink.activ8.client.entity.HistoryRecord>();
		Enumeration<HistoryRecord> recs = HealthGenius.myPatientManager.patientSet.get(patientId).history.elements();
		while (recs.hasMoreElements()) {
			HistoryRecord hr = recs.nextElement();
			spinnerArray.add(hr.diseaseName + " - " + HealthGeniusUtil.dateToReadableString(hr.date));
			records.add(hr.historyRecordForServices);
		}
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(HealthGenius.myApp, android.R.layout.simple_spinner_item, spinnerArray );
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		Builder builder = new AlertDialog.Builder(HealthGenius.myApp);
		builder.setView(layout);
		final AlertDialog alertDialog = builder.create();
		alertDialog.show();
		ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				alertDialog.cancel();
				final ProgressDialog dialog = ProgressDialog.show(HealthGenius.myApp, HealthGenius.myLanguageManager.PATIENTS_HISTORY, 
						HealthGenius.myLanguageManager.PATIENTS_HISTORY_ADDINGEXPL, true);
				dialog.show();
				Runnable run = new Runnable() {
					@Override
					public void run() {
						Long recordId = records.get(spinner.getSelectedItemPosition()).getId();
						String description = text.getText().toString();
						if (HealthGenius.myPatientManager.addExploration(patientId, recordId, description))
							handler.sendEmptyMessage(0);
						else handler.sendEmptyMessage(1);
					}
					private Handler handler = new Handler(){
						@Override
						public void handleMessage(Message msg) {
							switch (msg.what) {
								case 0:
									dialog.cancel();
									showHistory(HealthGenius.myPatientManager.patientSet.get(patientId), returning);
									break;
								case 1:
									dialog.cancel();
									Toast toast = Toast.makeText(HealthGenius.myApp, HealthGenius.myLanguageManager.CONNECTION_FAILED, Toast.LENGTH_SHORT);
									toast.show();
									break;
								default:
									break;
							}
						}
					};
				};
				Thread thread = new Thread(run);
				thread.start();
			}
		});
	}
	
	public void showPatients () {
		myUIManager.state = UIManager.UI_STATE_PATIENTS;
		HealthGenius.myApp.setContentView(R.layout.list);
		((TextView) HealthGenius.myApp.findViewById(R.id.startText)).append(HealthGenius.myLanguageManager.PATIENTS_TITLE);
		TableLayout patientlisting = (TableLayout)HealthGenius.myApp.findViewById(R.id.listing);
		Enumeration<Patient> enumer = HealthGenius.myPatientManager.patientSet.elements();
		while (enumer.hasMoreElements()) {
			final Patient patient = enumer.nextElement();
			TableRow buttonLayout = new TableRow(HealthGenius.myApp);
			buttonLayout.setOrientation(TableRow.HORIZONTAL);
			buttonLayout.setGravity(Gravity.CENTER_VERTICAL);
			buttonLayout.setLayoutParams(new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));			
			ImageButton button = new ImageButton(HealthGenius.myApp);
			button.setBackgroundResource(R.drawable.iconbg);
			button.setImageResource(R.drawable.patient);
			OnClickListener listener = new OnClickListener() {			
				@Override
				public void onClick(View v) {
					HealthGenius.myPatientManager.currentPat = patient;
					loadPatientMenu(patient);
				}
			};
			button.setOnClickListener(listener);
			TextView text = new TextView(HealthGenius.myApp);
			text.append(patient.getFirstName() + " " + patient.getLastName());
			text.setTextSize((float) 20);
			text.setTextColor(Color.BLACK);
			text.setTypeface(Typeface.SANS_SERIF);
			text.setOnClickListener(listener);
			buttonLayout.addView(button);
			buttonLayout.addView(text);
			buttonLayout.setOnClickListener(listener);
			button.setOnClickListener(listener);
			text.setOnClickListener(listener);
			patientlisting.addView(buttonLayout);
		}
		ImageButton back = (ImageButton) HealthGenius.myApp.findViewById(R.id.previous);
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				myUIManager.loadBoxOpen();
			}
		});
		ImageButton add = (ImageButton) HealthGenius.myApp.findViewById(R.id.help);
		add.setImageResource(R.drawable.plus);
		add.setVisibility(View.VISIBLE);
		add.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				addPatients();			
			}
		});
		ImageView delete = (ImageView) HealthGenius.myApp.findViewById(R.id.animation);
		delete.setImageResource(R.drawable.delete);
		delete.setVisibility(View.VISIBLE);
		delete.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				removePatients();			
			}
		});
	}
	
	public void addPatients() {
		LayoutInflater inflater = (LayoutInflater) HealthGenius.myApp.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		final View layout = inflater.inflate(R.layout.toastsearch,
		                               (ViewGroup) HealthGenius.myApp.findViewById(R.id.toastsearchroot));
		TextView text = (TextView) layout.findViewById(R.id.searchexpl);
		text.setText(HealthGenius.myLanguageManager.NOTIFICATION_PATIENT_EXPL);
		ImageButton image = (ImageButton) layout.findViewById(R.id.searchok);
		Builder builder = new AlertDialog.Builder(HealthGenius.myApp);
		builder.setView(layout);
		final AlertDialog alertDialog = builder.create();
		alertDialog.show();
		image.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				final String query = ((EditText)layout.findViewById(R.id.searchtext)).getText().toString();
				alertDialog.cancel();
				final ProgressDialog dialog = ProgressDialog.show(HealthGenius.myApp, HealthGenius.myLanguageManager.NOTIFICATION_PATIENT_TITLE, 
						HealthGenius.myLanguageManager.NOTIFICATION_PATIENT_SEARCHING, true);
				dialog.show();
				Runnable run =  new Runnable() {
					Array<com.o2hlink.activ8.client.entity.User> users;
					Array<Patient> patients;
					@Override
					public void run() {
						patients = HealthGenius.myPatientManager.SearchPatients(query);
						dialog.cancel();
						if (patients == null) {
							handler.sendEmptyMessage(0);
							return;
						}
						if (patients.size() == 0) {
							handler.sendEmptyMessage(1);
						} else {
							handler.sendEmptyMessage(2);
						}
					}
					private Handler handler = new Handler() {
						@Override
						public void handleMessage(Message msg) {
							switch (msg.what) {
								case 0:
									Toast toast = Toast.makeText(HealthGenius.myApp, HealthGenius.myLanguageManager.CONNECTION_FAILED, Toast.LENGTH_SHORT);
									toast.show();
									break;
								case 1:
									Toast toast2 = Toast.makeText(HealthGenius.myApp, HealthGenius.myLanguageManager.NOTIFICATION_PATIENT_NOTFOUND, Toast.LENGTH_SHORT);
									toast2.show();
									break;
								case 2:
									final CharSequence[] items = new CharSequence[patients.size()];
									final Array<com.o2hlink.activ8.client.entity.Patient>usersToAdd = new Array<com.o2hlink.activ8.client.entity.Patient>();
									for (int i = 0; i < patients.size(); i++) {
										items[i] = patients.get(i).getFirstName() + " " + patients.get(i).getLastName();
									}
									AlertDialog.Builder builder = new AlertDialog.Builder(HealthGenius.myApp);
									builder.setTitle(HealthGenius.myLanguageManager.NOTIFICATION_PATIENT_ADD);
									builder.setMultiChoiceItems(items, null, new OnMultiChoiceClickListener() {
										@Override
										public void onClick(DialogInterface dialog, int which, boolean isChecked) {
											if (isChecked) usersToAdd.add(patients.get(which));
											else usersToAdd.remove(patients.get(which));
										}
									});
									builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
								           public void onClick(DialogInterface dialog, int id) {
								        	   for (int i = 0; i < usersToAdd.size(); i++) {
								        		   HealthGenius.myPatientManager.AddPatient(usersToAdd.get(i));
								        	   }
								               dialog.cancel();
								               showPatients();
								           }
								    });
									AlertDialog alert = builder.create();
									alert.show();
									break;
							}
						}
					};
				};
				Thread thread = new Thread(run);
				thread.start();
			}
		});
	}
	
	public void removePatients() {
		int i = 0;
		final CharSequence[] items = new CharSequence[HealthGenius.myPatientManager.patientSet.size()];
		final com.o2hlink.activ8.client.entity.Patient[] patitems = new com.o2hlink.activ8.client.entity.Patient[HealthGenius.myPatientManager.patientSet.size()];
		final Array<com.o2hlink.activ8.client.entity.Patient>usersToRem = new Array<com.o2hlink.activ8.client.entity.Patient>();
		Enumeration<Patient> elements = HealthGenius.myPatientManager.patientSet.elements();
		while (elements.hasMoreElements()) {
			Patient patient = elements.nextElement();
			items[i] = patient.getFirstName() + " " + patient.getLastName();
			patitems[i] = patient;
			i++;
		}
		AlertDialog.Builder builder = new AlertDialog.Builder(HealthGenius.myApp);
		builder.setTitle(HealthGenius.myLanguageManager.NOTIFICATION_PATIENT_ADD);
		builder.setMultiChoiceItems(items, null, new OnMultiChoiceClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which, boolean isChecked) {
				if (isChecked) usersToRem.add(patitems[which]);
				else usersToRem.remove(patitems[which]);
			}
		});
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
	           public void onClick(DialogInterface dialog, int id) {
	        	   for (int i = 0; i < usersToRem.size(); i++) {
	        		   HealthGenius.myPatientManager.AddPatient(usersToRem.get(i));
	        	   }
	               dialog.cancel();
	               showPatients();
	           }
	    });
		AlertDialog alert = builder.create();
		alert.show();
	}
	
	public void loadPatientMenu(final Patient patient) {
		myUIManager.state = UIManager.UI_STATE_PATIENTS;
		HealthGenius.myApp.setContentView(R.layout.list);
		((TextView) HealthGenius.myApp.findViewById(R.id.startText)).append(patient.getFirstName() + " " + patient.getLastName());
		TableLayout options = (TableLayout)HealthGenius.myApp.findViewById(R.id.listing);
		
		TableRow buttonLayout = new TableRow(HealthGenius.myApp);
		buttonLayout.setOrientation(TableRow.HORIZONTAL);
		buttonLayout.setGravity(Gravity.CENTER_VERTICAL);
		buttonLayout.setLayoutParams(new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));			
		ImageButton button = new ImageButton(HealthGenius.myApp);
		button.setBackgroundResource(R.drawable.iconbg);
		button.setImageResource(R.drawable.history);
		OnClickListener listener = new OnClickListener() {			
			@Override
			public void onClick(View v) {
				GetHistory sending = new GetHistory(patient, false);
				Thread thread = new Thread(sending, "GETHISTORY");
				thread.start();
			}
		};
		button.setOnClickListener(listener);
		TextView text = new TextView(HealthGenius.myApp);
		text.append(HealthGenius.myLanguageManager.PATIENTS_HISTORY);
		text.setTextSize((float) 20);
		text.setTextColor(Color.BLACK);
		text.setTypeface(Typeface.SANS_SERIF);
		text.setOnClickListener(listener);
		buttonLayout.addView(button);
		buttonLayout.addView(text);
		buttonLayout.setOnClickListener(listener);
		button.setOnClickListener(listener);
		text.setOnClickListener(listener);
		options.addView(buttonLayout);
		
		buttonLayout = new TableRow(HealthGenius.myApp);
		buttonLayout.setOrientation(TableRow.HORIZONTAL);
		buttonLayout.setGravity(Gravity.CENTER_VERTICAL);
		buttonLayout.setLayoutParams(new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));			
		button = new ImageButton(HealthGenius.myApp);
		button.setBackgroundResource(R.drawable.iconbg);
		button.setImageResource(R.drawable.sensor);
		listener = new OnClickListener() {			
			@Override
			public void onClick(View v) {
				Runnable run = new Runnable() {
					@Override
					public void run() {
						handler.sendEmptyMessage(0);
						Date start = new Date();
						start.setMonth(start.getMonth() - 1);
						Date end = new Date();
						end.setMonth(end.getMonth() + 1);
						boolean success = HealthGenius.myProtocolManager.getMeasuringEvents(patient.getId(), start, end);
						if (success) {
							handler.sendEmptyMessage(1);
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
									myUIManager.UImisc.loadInfoPopupWithoutExiting(HealthGenius.myLanguageManager.CONNECTION_MEAS);
									animationFrame = (ImageView) HealthGenius.myApp.findViewById(R.id.popupImage);
									animationFrame.setVisibility(View.VISIBLE);
									animationFrame.setBackgroundResource(R.drawable.loading);
									animation = (AnimationDrawable) animationFrame.getBackground();
									animation.start();
									break;
								case 1:
									animationFrame = (ImageView) HealthGenius.myApp.findViewById(R.id.popupImage);
									animation = (AnimationDrawable) animationFrame.getBackground();
									animation.stop();
									animationFrame.setVisibility(View.GONE);
									myUIManager.UImisc.removeInfoPopup();
									loadScheduleDayForPatientMeas(new Date());
									break;
								case 2:
									animationFrame = (ImageView) HealthGenius.myApp.findViewById(R.id.popupImage);
									animation = (AnimationDrawable) animationFrame.getBackground();
									animation.stop();
									animationFrame.setVisibility(View.GONE);
									myUIManager.UImisc.loadInfoPopup(HealthGenius.myLanguageManager.CONNECTION_FAILED);
									break;
							}
						}
					};
				};
				Thread thread = new Thread(run);
				thread.start();
			}
		};
		button.setOnClickListener(listener);
		text = new TextView(HealthGenius.myApp);
		text.append(HealthGenius.myLanguageManager.PATIENTS_MEAS);
		text.setTextSize((float) 20);
		text.setTextColor(Color.BLACK);
		text.setTypeface(Typeface.SANS_SERIF);
		text.setOnClickListener(listener);
		buttonLayout.addView(button);
		buttonLayout.addView(text);
		buttonLayout.setOnClickListener(listener);
		button.setOnClickListener(listener);
		text.setOnClickListener(listener);
		options.addView(buttonLayout);
		
		buttonLayout = new TableRow(HealthGenius.myApp);
		buttonLayout.setOrientation(TableRow.HORIZONTAL);
		buttonLayout.setGravity(Gravity.CENTER_VERTICAL);
		buttonLayout.setLayoutParams(new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));			
		button = new ImageButton(HealthGenius.myApp);
		button.setBackgroundResource(R.drawable.iconbg);
		button.setImageResource(R.drawable.quest);
		listener = new OnClickListener() {			
			@Override
			public void onClick(View v) {
				Runnable run = new Runnable() {
					@Override
					public void run() {
						handler.sendEmptyMessage(0);
						Date start = new Date();
						start.setMonth(start.getMonth() - 1);
						Date end = new Date();
						end.setMonth(end.getMonth() + 1);
						boolean success = HealthGenius.myProtocolManager.getQuestEvents(patient.getId(), start, end);
						if (success) {
							handler.sendEmptyMessage(1);
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
									myUIManager.UImisc.loadInfoPopupWithoutExiting(HealthGenius.myLanguageManager.CONNECTION_QUEST);
									animationFrame = (ImageView) HealthGenius.myApp.findViewById(R.id.popupImage);
									animationFrame.setVisibility(View.VISIBLE);
									animationFrame.setBackgroundResource(R.drawable.loading);
									animation = (AnimationDrawable) animationFrame.getBackground();
									animation.start();
									break;
								case 1:
									animationFrame = (ImageView) HealthGenius.myApp.findViewById(R.id.popupImage);
									animation = (AnimationDrawable) animationFrame.getBackground();
									animation.stop();
									animationFrame.setVisibility(View.GONE);
									myUIManager.UImisc.removeInfoPopup();
									loadScheduleDayForPatientQuest(new Date());
									break;
								case 2:
									animationFrame = (ImageView) HealthGenius.myApp.findViewById(R.id.popupImage);
									animation = (AnimationDrawable) animationFrame.getBackground();
									animation.stop();
									animationFrame.setVisibility(View.GONE);
									myUIManager.UImisc.loadInfoPopup(HealthGenius.myLanguageManager.CONNECTION_FAILED);
									break;
							}
						}
					};
				};
				Thread thread = new Thread(run);
				thread.start();
			}
		};
		button.setOnClickListener(listener);
		text = new TextView(HealthGenius.myApp);
		text.append(HealthGenius.myLanguageManager.PATIENTS_QUEST);
		text.setTextSize((float) 20);
		text.setTextColor(Color.BLACK);
		text.setTypeface(Typeface.SANS_SERIF);
		text.setOnClickListener(listener);
		buttonLayout.addView(button);
		buttonLayout.addView(text);
		buttonLayout.setOnClickListener(listener);
		button.setOnClickListener(listener);
		text.setOnClickListener(listener);
		options.addView(buttonLayout);
		
		ImageButton back = (ImageButton) HealthGenius.myApp.findViewById(R.id.previous);
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showPatients();
			}
		});
	}
	
	public void loadScheduleDayForPatientMeas(final Date dategiven) {
		TextView time;
		com.o2hlink.healthgenius.patient.Event event = null; 
		Date date = new Date((dategiven.getTime()/3600000)*3600000);
		date.setHours(0);
		Date dateLater = new Date(date.getTime());
		dateLater.setHours(dateLater.getHours() + 1);
		TableRow buttonLayout;
		myUIManager.state = UIManager.UI_STATE_SCHEDULEFORPATIENTMEAS;
		if (HealthGenius.myMenu != null) {
			HealthGenius.myMenu.clear();
			HealthGenius.myInflater.inflate(R.menu.scheduleforpatientformeas, HealthGenius.myMenu);
		}
		Hashtable<Date, com.o2hlink.healthgenius.patient.Event> eventsOrdered = new Hashtable<Date,com.o2hlink.healthgenius.patient.Event>();
		Vector<Date> datesOrdered = new Vector<Date>();
		Enumeration<com.o2hlink.healthgenius.patient.Event> enumer = HealthGenius.myPatientManager.currentPatMeasEventSet.elements();
		while (enumer.hasMoreElements()) {
			com.o2hlink.healthgenius.patient.Event temp = enumer.nextElement();
			while (datesOrdered.contains(temp.date)) temp.date.setTime(temp.date.getTime() + 1);
			Timestamp stamp = new Timestamp(temp.date.getTime());
			datesOrdered.add(stamp);
			eventsOrdered.put(stamp, temp);
		}
		Collections.sort(datesOrdered);
		HealthGenius.myApp.setContentView(R.layout.scheduleday);
		((TextView) HealthGenius.myApp.findViewById(R.id.startText)).setText(HealthGenius.myLanguageManager.CALENDAR_MEAS);
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
					if (event.type == 2) button.setBackgroundResource(R.drawable.event);
					else switch (event.state) {
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
					buttonLayout.addView(activity);
					buttonLayout.addView(button);	
					time.setOnClickListener(new OnClickListener() {				
						@Override
						public void onClick(View v) {
							if (HealthGenius.myMenu != null) HealthGenius.myMenu.clear();
							HealthGenius.myPatientManager.currentPatMeasEventSet.get(id).doActivity();
						}
					});	
					activity.setOnClickListener(new OnClickListener() {				
						@Override
						public void onClick(View v) {
							if (HealthGenius.myMenu != null) HealthGenius.myMenu.clear();
							HealthGenius.myPatientManager.currentPatMeasEventSet.get(id).doActivity();
						}
					});
					button.setOnClickListener(new OnClickListener() {				
						@Override
						public void onClick(View v) {
							if (HealthGenius.myMenu != null) HealthGenius.myMenu.clear();
							HealthGenius.myPatientManager.currentPatMeasEventSet.get(id).doActivity();
						}
					});	
					buttonLayout.setOnClickListener(new OnClickListener() {				
						@Override
						public void onClick(View v) {
							if (HealthGenius.myMenu != null) HealthGenius.myMenu.clear();
							HealthGenius.myPatientManager.currentPatMeasEventSet.get(id).doActivity();
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
					if (event.type == 2) button.setBackgroundResource(R.drawable.event);
					else switch (event.state) {
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
					buttonLayout.addView(activity);
					buttonLayout.addView(button);	
					time.setOnClickListener(new OnClickListener() {				
						@Override
						public void onClick(View v) {
							if (HealthGenius.myMenu != null) HealthGenius.myMenu.clear();
							HealthGenius.myPatientManager.currentPatMeasEventSet.get(id).doActivity();
						}
					});	
					activity.setOnClickListener(new OnClickListener() {				
						@Override
						public void onClick(View v) {
							if (HealthGenius.myMenu != null) HealthGenius.myMenu.clear();
							HealthGenius.myPatientManager.currentPatMeasEventSet.get(id).doActivity();
						}
					});
					button.setOnClickListener(new OnClickListener() {				
						@Override
						public void onClick(View v) {
							if (HealthGenius.myMenu != null) HealthGenius.myMenu.clear();
							HealthGenius.myPatientManager.currentPatMeasEventSet.get(id).doActivity();
						}
					});	
					buttonLayout.setOnClickListener(new OnClickListener() {				
						@Override
						public void onClick(View v) {
							if (HealthGenius.myMenu != null) HealthGenius.myMenu.clear();
							HealthGenius.myPatientManager.currentPatMeasEventSet.get(id).doActivity();
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
				loadPatientMenu(HealthGenius.myPatientManager.currentPat);
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
						if (HealthGenius.myProtocolManager.getMeasuringEvents(HealthGenius.myPatientManager.currentPat.getId(), start, end))
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
									loadScheduleDayForPatientMeas(nextDate);
									break;
								case 2:
									nextDate = new Date(dategiven.getTime());
									nextDate.setDate(nextDate.getDate() - 1);
									loadScheduleDayForPatientMeas(nextDate);
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
						start.setDate(start.getDate() + 1);
						if (HealthGenius.myProtocolManager.getMeasuringEvents(HealthGenius.myPatientManager.currentPat.getId(), start, end))
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
									loadScheduleDayForPatientMeas(nextDate);
									break;
								case 2:
									nextDate = new Date(dategiven.getTime());
									nextDate.setDate(nextDate.getDate() + 1);
									loadScheduleDayForPatientMeas(nextDate);
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
		ImageButton add = (ImageButton) HealthGenius.myApp.findViewById(R.id.help);
		add.setImageResource(R.drawable.plus);
		add.setVisibility(View.VISIBLE);
		add.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				LayoutInflater inflater = (LayoutInflater) HealthGenius.myApp.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				final View layout = inflater.inflate(R.layout.toastaddpatevent,
				                               (ViewGroup) HealthGenius.myApp.findViewById(R.id.toastpataddeventroot));
				TextView title = (TextView) layout.findViewById(R.id.addtitle);
				title.setText(HealthGenius.myLanguageManager.NOTIFICATION_EVENT_TITLE);
				TextView namereq = (TextView) layout.findViewById(R.id.namerequested);
				namereq.setText(HealthGenius.myLanguageManager.NOTIFICATION_EVENT_NAMEREQ);
				TextView descreq = (TextView) layout.findViewById(R.id.descrequest);
				descreq.setText(HealthGenius.myLanguageManager.NOTIFICATION_EVENT_DESC);
				TextView typereq = (TextView) layout.findViewById(R.id.typerequest);
				typereq.setText(HealthGenius.myLanguageManager.NOTIFICATION_EVENT_TYPE);
				final Spinner type = (Spinner) layout.findViewById(R.id.type);
				ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(HealthGenius.myApp, R.array.meastype, android.R.layout.simple_spinner_item);
			    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			    type.setAdapter(adapter);
			    type.setSelection(0);
				TextView startreq = (TextView) layout.findViewById(R.id.startrequest);
				startreq.setText(HealthGenius.myLanguageManager.NOTIFICATION_EVENT_START);
				TextView endreq = (TextView) layout.findViewById(R.id.endrequest);
				endreq.setText(HealthGenius.myLanguageManager.NOTIFICATION_EVENT_END);
				TextView freqreq = (TextView) layout.findViewById(R.id.freqrequest);
				freqreq.setText(HealthGenius.myLanguageManager.NOTIFICATION_EVENT_FREQ);
				final Spinner frequency = (Spinner) layout.findViewById(R.id.freq);
			    ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(HealthGenius.myApp, R.array.freqarray, android.R.layout.simple_spinner_item);
			    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			    frequency.setAdapter(adapter2);
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
						final Measurement meas;
						final com.o2hlink.activ8.client.entity.Event eventtoadd = new com.o2hlink.activ8.client.entity.Event();
						eventtoadd.setName(((EditText) layout.findViewById(R.id.name)).getText().toString());
						eventtoadd.setDescription(((EditText) layout.findViewById(R.id.description)).getText().toString());
						switch (type.getSelectedItemPosition()) {
							case 0:
								meas = Measurement.PULSEOXYMETRY;
								break;
							case 1:
								meas = Measurement.SPIROMETRY;
								break;
							case 2:
								meas = Measurement.SIX_MINUTES_WALK;
								break;
							case 3:
								meas = Measurement.WEIGHT_HEIGHT;
								break;
							case 4:
								meas = Measurement.EXERCISE;
								break;
							default:
								meas = null;
								break;
						}
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
								if (HealthGenius.myPatientManager.addMeasEvent(HealthGenius.myPatientManager.currentPat.getId(), meas, eventtoadd))
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
											loadScheduleDayForPatientMeas(dategiven);
											dialog.cancel();
											toast.show();
											break;
										case 1:
											loadScheduleDayForPatientMeas(dategiven);
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
	
	public void loadScheduleWeekForPatientMeas(final Date dategiven){
		TextView time;
		com.o2hlink.healthgenius.patient.Event event = null; 
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
		myUIManager.state = UIManager.UI_STATE_SCHEDULEWEEKFORPATIENTMEAS;
		if (HealthGenius.myMenu != null) {
			HealthGenius.myMenu.clear();
			HealthGenius.myInflater.inflate(R.menu.scheduleforpatientformeas, HealthGenius.myMenu);
		}
		Hashtable<Date, com.o2hlink.healthgenius.patient.Event> eventsOrdered = new Hashtable<Date, com.o2hlink.healthgenius.patient.Event>();
		Vector<Date> datesOrdered = new Vector<Date>();
		Enumeration<com.o2hlink.healthgenius.patient.Event> enumer = HealthGenius.myPatientManager.currentPatMeasEventSet.elements();
		while (enumer.hasMoreElements()) {
			com.o2hlink.healthgenius.patient.Event temp = enumer.nextElement();
			while (datesOrdered.contains(temp.date)) temp.date.setTime(temp.date.getTime() + 1);
			Timestamp stamp = new Timestamp(temp.date.getTime());
			datesOrdered.add(stamp);
			eventsOrdered.put(stamp, temp);
		}
		Collections.sort(datesOrdered);
		HealthGenius.myApp.setContentView(R.layout.scheduleday);
		((TextView) HealthGenius.myApp.findViewById(R.id.startText)).setText(HealthGenius.myLanguageManager.CALENDAR_MEAS);
		ImageButton back = (ImageButton) HealthGenius.myApp.findViewById(R.id.previous);
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				myUIManager.loadBoxOpen();
			}
		});
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
					if (event.type == 2) button.setBackgroundResource(R.drawable.event);
					else switch (event.state) {
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
					buttonLayout.addView(button);	
					schedule.addView(buttonLayout);
					time.setOnClickListener(new OnClickListener() {				
						@Override
						public void onClick(View v) {
							if (HealthGenius.myMenu != null) HealthGenius.myMenu.clear();
							HealthGenius.myPatientManager.currentPatMeasEventSet.get(id).doActivity();
						}
					});	
					activity.setOnClickListener(new OnClickListener() {				
						@Override
						public void onClick(View v) {
							if (HealthGenius.myMenu != null) HealthGenius.myMenu.clear();
							HealthGenius.myPatientManager.currentPatMeasEventSet.get(id).doActivity();
						}
					});
					button.setOnClickListener(new OnClickListener() {				
						@Override
						public void onClick(View v) {
							if (HealthGenius.myMenu != null) HealthGenius.myMenu.clear();
							HealthGenius.myPatientManager.currentPatMeasEventSet.get(id).doActivity();
						}
					});	
					buttonLayout.setOnClickListener(new OnClickListener() {				
						@Override
						public void onClick(View v) {
							if (HealthGenius.myMenu != null) HealthGenius.myMenu.clear();
							HealthGenius.myPatientManager.currentPatMeasEventSet.get(id).doActivity();
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
						if (HealthGenius.myProtocolManager.getMeasuringEvents(HealthGenius.myPatientManager.currentPat.getId(), start, end))
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
									loadScheduleWeekForPatientMeas(nextDate);
									break;
								case 2:
									nextDate = new Date(dategiven.getTime());
									nextDate.setDate(nextDate.getDate() - 7);
									loadScheduleWeekForPatientMeas(nextDate);
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
						int day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)-2;
						if (day == -1) day = 6;
						Date start = new Date((dategiven.getTime()/86400000)*86400000);
						start.setDate(start.getDate() - day + 7);
						Date end = new Date(start.getTime());
						end.setDate(end.getDate() + 7);
						if (HealthGenius.myProtocolManager.getMeasuringEvents(HealthGenius.myPatientManager.currentPat.getId(), start, end))
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
									loadScheduleWeekForPatientMeas(nextDate);
									break;
								case 2:
									nextDate = new Date(dategiven.getTime());
									nextDate.setDate(nextDate.getDate() + 7);
									loadScheduleWeekForPatientMeas(nextDate);
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
		ImageButton add = (ImageButton) HealthGenius.myApp.findViewById(R.id.help);
		add.setImageResource(R.drawable.plus);
		add.setVisibility(View.VISIBLE);
		add.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				LayoutInflater inflater = (LayoutInflater) HealthGenius.myApp.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				final View layout = inflater.inflate(R.layout.toastaddpatevent,
                        (ViewGroup) HealthGenius.myApp.findViewById(R.id.toastpataddeventroot));
				TextView title = (TextView) layout.findViewById(R.id.addtitle);
				title.setText(HealthGenius.myLanguageManager.NOTIFICATION_EVENT_TITLE);
				TextView namereq = (TextView) layout.findViewById(R.id.namerequested);
				namereq.setText(HealthGenius.myLanguageManager.NOTIFICATION_EVENT_NAMEREQ);
				TextView descreq = (TextView) layout.findViewById(R.id.descrequest);
				descreq.setText(HealthGenius.myLanguageManager.NOTIFICATION_EVENT_DESC);
				TextView typereq = (TextView) layout.findViewById(R.id.typerequest);
				typereq.setText(HealthGenius.myLanguageManager.NOTIFICATION_EVENT_TYPE);
				final Spinner type = (Spinner) layout.findViewById(R.id.type);
				ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(HealthGenius.myApp, R.array.meastype, android.R.layout.simple_spinner_item);
			    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			    type.setAdapter(adapter);
			    type.setSelection(0);
				TextView startreq = (TextView) layout.findViewById(R.id.startrequest);
				startreq.setText(HealthGenius.myLanguageManager.NOTIFICATION_EVENT_START);
				TextView endreq = (TextView) layout.findViewById(R.id.endrequest);
				endreq.setText(HealthGenius.myLanguageManager.NOTIFICATION_EVENT_END);
				TextView freqreq = (TextView) layout.findViewById(R.id.freqrequest);
				freqreq.setText(HealthGenius.myLanguageManager.NOTIFICATION_EVENT_FREQ);
				final Spinner frequency = (Spinner) layout.findViewById(R.id.freq);
			    ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(HealthGenius.myApp, R.array.freqarray, android.R.layout.simple_spinner_item);
			    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			    frequency.setAdapter(adapter2);
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
						final Measurement meas;
						final com.o2hlink.activ8.client.entity.Event eventtoadd = new com.o2hlink.activ8.client.entity.Event();
						eventtoadd.setName(((EditText) layout.findViewById(R.id.name)).getText().toString());
						eventtoadd.setDescription(((EditText) layout.findViewById(R.id.description)).getText().toString());
						switch (type.getSelectedItemPosition()) {
							case 0:
								meas = Measurement.PULSEOXYMETRY;
								break;
							case 1:
								meas = Measurement.SPIROMETRY;
								break;
							case 2:
								meas = Measurement.SIX_MINUTES_WALK;
								break;
							case 3:
								meas = Measurement.WEIGHT_HEIGHT;
								break;
							case 4:
								meas = Measurement.EXERCISE;
								break;
							default:
								meas = null;
								break;
						}
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
								if (HealthGenius.myPatientManager.addMeasEvent(HealthGenius.myPatientManager.currentPat.getId(), meas, eventtoadd))
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
											loadScheduleWeekForPatientMeas(dategiven);
											dialog.cancel();
											toast.show();
											break;
										case 1:
											loadScheduleWeekForPatientMeas(dategiven);
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
	
	public void loadScheduleMonthForPatientMeas(final Date dategiven) {
		TextView time;
		View separator;
		LayoutParams separatorLayout;
		TableRow weekLayout;
		Enumeration<com.o2hlink.healthgenius.patient.Event> enumeration;
		com.o2hlink.healthgenius.patient.Event event = null; 
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
		myUIManager.state = UIManager.UI_STATE_SCHEDULEMONTHFORPATIENTMEAS;
		if (HealthGenius.myMenu != null) {
			HealthGenius.myMenu.clear();
			HealthGenius.myInflater.inflate(R.menu.scheduleforpatientformeas, HealthGenius.myMenu);
		}
		HealthGenius.myApp.setContentView(R.layout.schedulemonth);
		((TextView) HealthGenius.myApp.findViewById(R.id.startText)).setText(HealthGenius.myLanguageManager.CALENDAR_MEAS);
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
			enumeration = HealthGenius.myPatientManager.currentPatMeasEventSet.elements();
			while (enumeration.hasMoreElements()) {
				event = enumeration.nextElement();
				if ((date.compareTo(event.getDate()) <= 0)&&(dateLater.compareTo(event.getDate()) > 0)) {
					if (((event.type == 2)||(event.state == 0))&&(state == 3)) {
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
					loadScheduleDayForPatientMeas(newDate);
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
				loadPatientMenu(HealthGenius.myPatientManager.currentPat);
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
						if (HealthGenius.myProtocolManager.getMeasuringEvents(HealthGenius.myPatientManager.currentPat.getId(), start, end))
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
									loadScheduleMonthForPatientMeas(nextDate);
									break;
								case 2:
									nextDate = new Date(dategiven.getTime());
									nextDate.setMonth(nextDate.getMonth() - 1);
									loadScheduleMonthForPatientMeas(nextDate);
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
						start.setDate(1);
						start.setMonth(start.getMonth() + 1);
						Date end = new Date(start.getTime());
						end.setMonth(end.getMonth() + 1);
						if (HealthGenius.myProtocolManager.getMeasuringEvents(HealthGenius.myPatientManager.currentPat.getId(), start, end))
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
									loadScheduleMonthForPatientMeas(nextDate);
									break;
								case 2:
									nextDate = new Date(dategiven.getTime());
									nextDate.setMonth(nextDate.getMonth() + 1);
									loadScheduleMonthForPatientMeas(nextDate);
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
		ImageButton add = (ImageButton) HealthGenius.myApp.findViewById(R.id.help);
		add.setImageResource(R.drawable.plus);
		add.setVisibility(View.VISIBLE);
		add.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				LayoutInflater inflater = (LayoutInflater) HealthGenius.myApp.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				final View layout = inflater.inflate(R.layout.toastaddpatevent,
                        (ViewGroup) HealthGenius.myApp.findViewById(R.id.toastpataddeventroot));
				TextView title = (TextView) layout.findViewById(R.id.addtitle);
				title.setText(HealthGenius.myLanguageManager.NOTIFICATION_EVENT_TITLE);
				TextView namereq = (TextView) layout.findViewById(R.id.namerequested);
				namereq.setText(HealthGenius.myLanguageManager.NOTIFICATION_EVENT_NAMEREQ);
				TextView descreq = (TextView) layout.findViewById(R.id.descrequest);
				descreq.setText(HealthGenius.myLanguageManager.NOTIFICATION_EVENT_DESC);
				TextView typereq = (TextView) layout.findViewById(R.id.typerequest);
				typereq.setText(HealthGenius.myLanguageManager.NOTIFICATION_EVENT_TYPE);
				final Spinner type = (Spinner) layout.findViewById(R.id.type);
				ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(HealthGenius.myApp, R.array.meastype, android.R.layout.simple_spinner_item);
			    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			    type.setAdapter(adapter);
			    type.setSelection(0);
				TextView startreq = (TextView) layout.findViewById(R.id.startrequest);
				startreq.setText(HealthGenius.myLanguageManager.NOTIFICATION_EVENT_START);
				TextView endreq = (TextView) layout.findViewById(R.id.endrequest);
				endreq.setText(HealthGenius.myLanguageManager.NOTIFICATION_EVENT_END);
				TextView freqreq = (TextView) layout.findViewById(R.id.freqrequest);
				freqreq.setText(HealthGenius.myLanguageManager.NOTIFICATION_EVENT_FREQ);
				final Spinner frequency = (Spinner) layout.findViewById(R.id.freq);
			    ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(HealthGenius.myApp, R.array.freqarray, android.R.layout.simple_spinner_item);
			    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			    frequency.setAdapter(adapter2);
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
						final Measurement meas;
						final com.o2hlink.activ8.client.entity.Event eventtoadd = new com.o2hlink.activ8.client.entity.Event();
						eventtoadd.setName(((EditText) layout.findViewById(R.id.name)).getText().toString());
						eventtoadd.setDescription(((EditText) layout.findViewById(R.id.description)).getText().toString());
						switch (type.getSelectedItemPosition()) {
							case 0:
								meas = Measurement.PULSEOXYMETRY;
								break;
							case 1:
								meas = Measurement.SPIROMETRY;
								break;
							case 2:
								meas = Measurement.SIX_MINUTES_WALK;
								break;
							case 3:
								meas = Measurement.WEIGHT_HEIGHT;
								break;
							case 4:
								meas = Measurement.EXERCISE;
								break;
							default:
								meas = null;
								break;
						}
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
								if (HealthGenius.myPatientManager.addMeasEvent(HealthGenius.myPatientManager.currentPat.getId(), meas, eventtoadd))
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
											loadScheduleWeekForPatientMeas(dategiven);
											dialog.cancel();
											toast.show();
											break;
										case 1:
											loadScheduleWeekForPatientMeas(dategiven);
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

	public void loadScheduleDayForPatientQuest(final Date dategiven) {
		TextView time;
		com.o2hlink.healthgenius.patient.Event event = null; 
		Date date = new Date((dategiven.getTime()/3600000)*3600000);
		date.setHours(0);
		Date dateLater = new Date(date.getTime());
		dateLater.setHours(dateLater.getHours() + 1);
		TableRow buttonLayout;
		myUIManager.state = UIManager.UI_STATE_SCHEDULEFORPATIENTQUEST;
		if (HealthGenius.myMenu != null) {
			HealthGenius.myMenu.clear();
			HealthGenius.myInflater.inflate(R.menu.scheduleforpatientforquest, HealthGenius.myMenu);
		}
		Hashtable<Date, com.o2hlink.healthgenius.patient.Event> eventsOrdered = new Hashtable<Date,com.o2hlink.healthgenius.patient.Event>();
		Vector<Date> datesOrdered = new Vector<Date>();
		Enumeration<com.o2hlink.healthgenius.patient.Event> enumer = HealthGenius.myPatientManager.currentPatQuestEventSet.elements();
		while (enumer.hasMoreElements()) {
			com.o2hlink.healthgenius.patient.Event temp = enumer.nextElement();
			while (datesOrdered.contains(temp.date)) temp.date.setTime(temp.date.getTime() + 1);
			Timestamp stamp = new Timestamp(temp.date.getTime());
			datesOrdered.add(stamp);
			eventsOrdered.put(stamp, temp);
		}
		Collections.sort(datesOrdered);
		HealthGenius.myApp.setContentView(R.layout.scheduleday);
		((TextView) HealthGenius.myApp.findViewById(R.id.startText)).setText(HealthGenius.myLanguageManager.CALENDAR_QUEST);
		((TextView) HealthGenius.myApp.findViewById(R.id.startText)).setTextSize(24);
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
					if (event.type == 2) button.setBackgroundResource(R.drawable.event);
					else switch (event.state) {
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
					buttonLayout.addView(activity);
					buttonLayout.addView(button);	
					time.setOnClickListener(new OnClickListener() {				
						@Override
						public void onClick(View v) {
							if (HealthGenius.myMenu != null) HealthGenius.myMenu.clear();
							HealthGenius.myPatientManager.currentPatQuestEventSet.get(id).doActivity();
						}
					});	
					activity.setOnClickListener(new OnClickListener() {				
						@Override
						public void onClick(View v) {
							if (HealthGenius.myMenu != null) HealthGenius.myMenu.clear();
							HealthGenius.myPatientManager.currentPatQuestEventSet.get(id).doActivity();
						}
					});
					button.setOnClickListener(new OnClickListener() {				
						@Override
						public void onClick(View v) {
							if (HealthGenius.myMenu != null) HealthGenius.myMenu.clear();
							HealthGenius.myPatientManager.currentPatQuestEventSet.get(id).doActivity();
						}
					});	
					buttonLayout.setOnClickListener(new OnClickListener() {				
						@Override
						public void onClick(View v) {
							if (HealthGenius.myMenu != null) HealthGenius.myMenu.clear();
							HealthGenius.myPatientManager.currentPatQuestEventSet.get(id).doActivity();
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
					if (event.type == 2) button.setBackgroundResource(R.drawable.event);
					else switch (event.state) {
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
					buttonLayout.addView(activity);
					buttonLayout.addView(button);	
					time.setOnClickListener(new OnClickListener() {				
						@Override
						public void onClick(View v) {
							if (HealthGenius.myMenu != null) HealthGenius.myMenu.clear();
							HealthGenius.myPatientManager.currentPatQuestEventSet.get(id).doActivity();
						}
					});	
					activity.setOnClickListener(new OnClickListener() {				
						@Override
						public void onClick(View v) {
							if (HealthGenius.myMenu != null) HealthGenius.myMenu.clear();
							HealthGenius.myPatientManager.currentPatQuestEventSet.get(id).doActivity();
						}
					});
					button.setOnClickListener(new OnClickListener() {				
						@Override
						public void onClick(View v) {
							if (HealthGenius.myMenu != null) HealthGenius.myMenu.clear();
							HealthGenius.myPatientManager.currentPatQuestEventSet.get(id).doActivity();
						}
					});	
					buttonLayout.setOnClickListener(new OnClickListener() {				
						@Override
						public void onClick(View v) {
							if (HealthGenius.myMenu != null) HealthGenius.myMenu.clear();
							HealthGenius.myPatientManager.currentPatQuestEventSet.get(id).doActivity();
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
				loadPatientMenu(HealthGenius.myPatientManager.currentPat);
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
						if (HealthGenius.myProtocolManager.getQuestEvents(HealthGenius.myPatientManager.currentPat.getId(), start, end))
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
									loadScheduleDayForPatientQuest(nextDate);
									break;
								case 2:
									nextDate = new Date(dategiven.getTime());
									nextDate.setDate(nextDate.getDate() - 1);
									loadScheduleDayForPatientQuest(nextDate);
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
						start.setDate(start.getDate() + 1);
						if (HealthGenius.myProtocolManager.getQuestEvents(HealthGenius.myPatientManager.currentPat.getId(), start, end))
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
									loadScheduleDayForPatientQuest(nextDate);
									break;
								case 2:
									nextDate = new Date(dategiven.getTime());
									nextDate.setDate(nextDate.getDate() + 1);
									loadScheduleDayForPatientQuest(nextDate);
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
		ImageButton add = (ImageButton) HealthGenius.myApp.findViewById(R.id.help);
		add.setImageResource(R.drawable.plus);
		add.setVisibility(View.VISIBLE);
		add.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				LayoutInflater inflater = (LayoutInflater) HealthGenius.myApp.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				final View layout = inflater.inflate(R.layout.toastaddpatevent,
                        (ViewGroup) HealthGenius.myApp.findViewById(R.id.toastpataddeventroot));
				TextView title = (TextView) layout.findViewById(R.id.addtitle);
				title.setText(HealthGenius.myLanguageManager.NOTIFICATION_EVENT_TITLE);
				TextView namereq = (TextView) layout.findViewById(R.id.namerequested);
				namereq.setText(HealthGenius.myLanguageManager.NOTIFICATION_EVENT_NAMEREQ);
				TextView descreq = (TextView) layout.findViewById(R.id.descrequest);
				descreq.setText(HealthGenius.myLanguageManager.NOTIFICATION_EVENT_DESC);
				TextView typereq = (TextView) layout.findViewById(R.id.typerequest);
				typereq.setText(HealthGenius.myLanguageManager.NOTIFICATION_EVENT_TYPE);
				final Spinner type = (Spinner) layout.findViewById(R.id.type);
				ArrayList<String> spinnerStrings = new ArrayList<String>();
				final ArrayList<Long> spinnerQuestIds = new ArrayList<Long>();
				Enumeration<Questionnaire> elements = HealthGenius.myPatientManager.currentPatQuestSet.elements();
				while (elements.hasMoreElements()) {
					Questionnaire quest = elements.nextElement();
					spinnerStrings.add(quest.getName());
					spinnerQuestIds.add(quest.getId());
				}
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(HealthGenius.myApp, android.R.layout.simple_spinner_item, spinnerStrings);
			    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			    type.setAdapter(adapter);
			    type.setSelection(0);
				TextView startreq = (TextView) layout.findViewById(R.id.startrequest);
				startreq.setText(HealthGenius.myLanguageManager.NOTIFICATION_EVENT_START);
				TextView endreq = (TextView) layout.findViewById(R.id.endrequest);
				endreq.setText(HealthGenius.myLanguageManager.NOTIFICATION_EVENT_END);
				TextView freqreq = (TextView) layout.findViewById(R.id.freqrequest);
				freqreq.setText(HealthGenius.myLanguageManager.NOTIFICATION_EVENT_FREQ);
				final Spinner frequency = (Spinner) layout.findViewById(R.id.freq);
			    ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(HealthGenius.myApp, R.array.freqarray, android.R.layout.simple_spinner_item);
			    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			    frequency.setAdapter(adapter2);
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
						final Long quest;
						final com.o2hlink.activ8.client.entity.Event eventtoadd = new com.o2hlink.activ8.client.entity.Event();
						eventtoadd.setName(((EditText) layout.findViewById(R.id.name)).getText().toString());
						eventtoadd.setDescription(((EditText) layout.findViewById(R.id.description)).getText().toString());
						quest = spinnerQuestIds.get(type.getSelectedItemPosition());
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
								if (HealthGenius.myPatientManager.addQuestEvent(HealthGenius.myPatientManager.currentPat.getId(), quest, eventtoadd))
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
											loadScheduleDayForPatientQuest(dategiven);
											dialog.cancel();
											toast.show();
											break;
										case 1:
											loadScheduleDayForPatientQuest(dategiven);
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
	
	public void loadScheduleWeekForPatientQuest(final Date dategiven){
		TextView time;
		com.o2hlink.healthgenius.patient.Event event = null; 
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
		myUIManager.state = UIManager.UI_STATE_SCHEDULEWEEKFORPATIENTQUEST;
		if (HealthGenius.myMenu != null) {
			HealthGenius.myMenu.clear();
			HealthGenius.myInflater.inflate(R.menu.scheduleforpatientforquest, HealthGenius.myMenu);
		}
		Hashtable<Date, com.o2hlink.healthgenius.patient.Event> eventsOrdered = new Hashtable<Date, com.o2hlink.healthgenius.patient.Event>();
		Vector<Date> datesOrdered = new Vector<Date>();
		Enumeration<com.o2hlink.healthgenius.patient.Event> enumer = HealthGenius.myPatientManager.currentPatQuestEventSet.elements();
		while (enumer.hasMoreElements()) {
			com.o2hlink.healthgenius.patient.Event temp = enumer.nextElement();
			while (datesOrdered.contains(temp.date)) temp.date.setTime(temp.date.getTime() + 1);
			Timestamp stamp = new Timestamp(temp.date.getTime());
			datesOrdered.add(stamp);
			eventsOrdered.put(stamp, temp);
		}
		Collections.sort(datesOrdered);
		HealthGenius.myApp.setContentView(R.layout.scheduleday);
		((TextView) HealthGenius.myApp.findViewById(R.id.startText)).setText(HealthGenius.myLanguageManager.CALENDAR_TITLE);
		((TextView) HealthGenius.myApp.findViewById(R.id.startText)).setTextSize(24);
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
					if (event.type == 2) button.setBackgroundResource(R.drawable.event);
					else switch (event.state) {
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
					buttonLayout.addView(button);	
					schedule.addView(buttonLayout);
					time.setOnClickListener(new OnClickListener() {				
						@Override
						public void onClick(View v) {
							if (HealthGenius.myMenu != null) HealthGenius.myMenu.clear();
							HealthGenius.myPatientManager.currentPatQuestEventSet.get(id).doActivity();
						}
					});	
					activity.setOnClickListener(new OnClickListener() {				
						@Override
						public void onClick(View v) {
							if (HealthGenius.myMenu != null) HealthGenius.myMenu.clear();
							HealthGenius.myPatientManager.currentPatQuestEventSet.get(id).doActivity();
						}
					});
					button.setOnClickListener(new OnClickListener() {				
						@Override
						public void onClick(View v) {
							if (HealthGenius.myMenu != null) HealthGenius.myMenu.clear();
							HealthGenius.myPatientManager.currentPatQuestEventSet.get(id).doActivity();
						}
					});	
					buttonLayout.setOnClickListener(new OnClickListener() {				
						@Override
						public void onClick(View v) {
							if (HealthGenius.myMenu != null) HealthGenius.myMenu.clear();
							HealthGenius.myPatientManager.currentPatQuestEventSet.get(id).doActivity();
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
				loadPatientMenu(HealthGenius.myPatientManager.currentPat);
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
						if (HealthGenius.myProtocolManager.getQuestEvents(HealthGenius.myPatientManager.currentPat.getId(), start, end))
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
									loadScheduleWeekForPatientQuest(nextDate);
									break;
								case 2:
									nextDate = new Date(dategiven.getTime());
									nextDate.setDate(nextDate.getDate() - 7);
									loadScheduleWeekForPatientQuest(nextDate);
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
						int day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)-2;
						if (day == -1) day = 6;
						Date start = new Date((dategiven.getTime()/86400000)*86400000);
						start.setDate(start.getDate() - day + 7);
						Date end = new Date(start.getTime());
						end.setDate(end.getDate() + 7);
						if (HealthGenius.myProtocolManager.getQuestEvents(HealthGenius.myPatientManager.currentPat.getId(), start, end))
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
									loadScheduleWeekForPatientQuest(nextDate);
									break;
								case 2:
									nextDate = new Date(dategiven.getTime());
									nextDate.setDate(nextDate.getDate() + 7);
									loadScheduleWeekForPatientQuest(nextDate);
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
		ImageButton add = (ImageButton) HealthGenius.myApp.findViewById(R.id.help);
		add.setImageResource(R.drawable.plus);
		add.setVisibility(View.VISIBLE);
		add.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				LayoutInflater inflater = (LayoutInflater) HealthGenius.myApp.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				final View layout = inflater.inflate(R.layout.toastaddpatevent,
                        (ViewGroup) HealthGenius.myApp.findViewById(R.id.toastpataddeventroot));
				TextView title = (TextView) layout.findViewById(R.id.addtitle);
				title.setText(HealthGenius.myLanguageManager.NOTIFICATION_EVENT_TITLE);
				TextView namereq = (TextView) layout.findViewById(R.id.namerequested);
				namereq.setText(HealthGenius.myLanguageManager.NOTIFICATION_EVENT_NAMEREQ);
				TextView descreq = (TextView) layout.findViewById(R.id.descrequest);
				descreq.setText(HealthGenius.myLanguageManager.NOTIFICATION_EVENT_DESC);
				TextView typereq = (TextView) layout.findViewById(R.id.typerequest);
				typereq.setText(HealthGenius.myLanguageManager.NOTIFICATION_EVENT_TYPE);
				final Spinner type = (Spinner) layout.findViewById(R.id.type);
				ArrayList<String> spinnerStrings = new ArrayList<String>();
				final ArrayList<Long> spinnerQuestIds = new ArrayList<Long>();
				Enumeration<Questionnaire> elements = HealthGenius.myPatientManager.currentPatQuestSet.elements();
				while (elements.hasMoreElements()) {
					Questionnaire quest = elements.nextElement();
					spinnerStrings.add(quest.getName());
					spinnerQuestIds.add(quest.getId());
				}
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(HealthGenius.myApp, android.R.layout.simple_spinner_item, spinnerStrings);
			    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			    type.setAdapter(adapter);
			    type.setSelection(0);
				TextView startreq = (TextView) layout.findViewById(R.id.startrequest);
				startreq.setText(HealthGenius.myLanguageManager.NOTIFICATION_EVENT_START);
				TextView endreq = (TextView) layout.findViewById(R.id.endrequest);
				endreq.setText(HealthGenius.myLanguageManager.NOTIFICATION_EVENT_END);
				TextView freqreq = (TextView) layout.findViewById(R.id.freqrequest);
				freqreq.setText(HealthGenius.myLanguageManager.NOTIFICATION_EVENT_FREQ);
				final Spinner frequency = (Spinner) layout.findViewById(R.id.freq);
			    ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(HealthGenius.myApp, R.array.freqarray, android.R.layout.simple_spinner_item);
			    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			    frequency.setAdapter(adapter2);
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
						final Long quest;
						final com.o2hlink.activ8.client.entity.Event eventtoadd = new com.o2hlink.activ8.client.entity.Event();
						eventtoadd.setName(((EditText) layout.findViewById(R.id.name)).getText().toString());
						eventtoadd.setDescription(((EditText) layout.findViewById(R.id.description)).getText().toString());
						quest = spinnerQuestIds.get(type.getSelectedItemPosition());
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
								if (HealthGenius.myPatientManager.addQuestEvent(HealthGenius.myPatientManager.currentPat.getId(), quest, eventtoadd))
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
											loadScheduleWeekForPatientQuest(dategiven);
											dialog.cancel();
											toast.show();
											break;
										case 1:
											loadScheduleWeekForPatientQuest(dategiven);
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
	
	public void loadScheduleMonthForPatientQuest(final Date dategiven) {
		TextView time;
		View separator;
		LayoutParams separatorLayout;
		TableRow weekLayout;
		Enumeration<com.o2hlink.healthgenius.patient.Event> enumeration;
		com.o2hlink.healthgenius.patient.Event event = null; 
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
		myUIManager.state = UIManager.UI_STATE_SCHEDULEMONTHFORPATIENTQUEST;
		if (HealthGenius.myMenu != null) {
			HealthGenius.myMenu.clear();
			HealthGenius.myInflater.inflate(R.menu.scheduleforpatientforquest, HealthGenius.myMenu);
		}
		HealthGenius.myApp.setContentView(R.layout.schedulemonth);
		((TextView) HealthGenius.myApp.findViewById(R.id.startText)).setText(HealthGenius.myLanguageManager.CALENDAR_TITLE);
		((TextView) HealthGenius.myApp.findViewById(R.id.startText)).setTextSize(24);
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
			enumeration = HealthGenius.myPatientManager.currentPatQuestEventSet.elements();
			while (enumeration.hasMoreElements()) {
				event = enumeration.nextElement();
				if ((date.compareTo(event.getDate()) <= 0)&&(dateLater.compareTo(event.getDate()) > 0)) {
					if (((event.type == 2)||(event.state == 0))&&(state == 3)) {
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
					loadScheduleDayForPatientQuest(newDate);
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
				loadPatientMenu(HealthGenius.myPatientManager.currentPat);
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
						if (HealthGenius.myProtocolManager.getQuestEvents(HealthGenius.myPatientManager.currentPat.getId(), start, end))
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
									loadScheduleMonthForPatientQuest(nextDate);
									break;
								case 2:
									nextDate = new Date(dategiven.getTime());
									nextDate.setMonth(nextDate.getMonth() - 1);
									loadScheduleMonthForPatientQuest(nextDate);
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
						start.setDate(1);
						start.setMonth(start.getMonth() + 1);
						Date end = new Date(start.getTime());
						end.setMonth(end.getMonth() + 1);
						if (HealthGenius.myProtocolManager.getQuestEvents(HealthGenius.myPatientManager.currentPat.getId(), start, end))
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
									loadScheduleMonthForPatientQuest(nextDate);
									break;
								case 2:
									nextDate = new Date(dategiven.getTime());
									nextDate.setMonth(nextDate.getMonth() + 1);
									loadScheduleMonthForPatientQuest(nextDate);
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
		ImageButton add = (ImageButton) HealthGenius.myApp.findViewById(R.id.help);
		add.setImageResource(R.drawable.plus);
		add.setVisibility(View.VISIBLE);
		add.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				LayoutInflater inflater = (LayoutInflater) HealthGenius.myApp.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				final View layout = inflater.inflate(R.layout.toastaddpatevent,
                        (ViewGroup) HealthGenius.myApp.findViewById(R.id.toastpataddeventroot));
				TextView title = (TextView) layout.findViewById(R.id.addtitle);
				title.setText(HealthGenius.myLanguageManager.NOTIFICATION_EVENT_TITLE);
				TextView namereq = (TextView) layout.findViewById(R.id.namerequested);
				namereq.setText(HealthGenius.myLanguageManager.NOTIFICATION_EVENT_NAMEREQ);
				TextView descreq = (TextView) layout.findViewById(R.id.descrequest);
				descreq.setText(HealthGenius.myLanguageManager.NOTIFICATION_EVENT_DESC);
				TextView typereq = (TextView) layout.findViewById(R.id.typerequest);
				typereq.setText(HealthGenius.myLanguageManager.NOTIFICATION_EVENT_TYPE);
				final Spinner type = (Spinner) layout.findViewById(R.id.type);
				ArrayList<String> spinnerStrings = new ArrayList<String>();
				final ArrayList<Long> spinnerQuestIds = new ArrayList<Long>();
				Enumeration<Questionnaire> elements = HealthGenius.myPatientManager.currentPatQuestSet.elements();
				while (elements.hasMoreElements()) {
					Questionnaire quest = elements.nextElement();
					spinnerStrings.add(quest.getName());
					spinnerQuestIds.add(quest.getId());
				}
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(HealthGenius.myApp, android.R.layout.simple_spinner_item, spinnerStrings);
			    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			    type.setAdapter(adapter);
			    type.setSelection(0);
				TextView startreq = (TextView) layout.findViewById(R.id.startrequest);
				startreq.setText(HealthGenius.myLanguageManager.NOTIFICATION_EVENT_START);
				TextView endreq = (TextView) layout.findViewById(R.id.endrequest);
				endreq.setText(HealthGenius.myLanguageManager.NOTIFICATION_EVENT_END);
				TextView freqreq = (TextView) layout.findViewById(R.id.freqrequest);
				freqreq.setText(HealthGenius.myLanguageManager.NOTIFICATION_EVENT_FREQ);
				final Spinner frequency = (Spinner) layout.findViewById(R.id.freq);
			    ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(HealthGenius.myApp, R.array.freqarray, android.R.layout.simple_spinner_item);
			    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			    frequency.setAdapter(adapter2);
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
						final Long quest;
						final com.o2hlink.activ8.client.entity.Event eventtoadd = new com.o2hlink.activ8.client.entity.Event();
						eventtoadd.setName(((EditText) layout.findViewById(R.id.name)).getText().toString());
						eventtoadd.setDescription(((EditText) layout.findViewById(R.id.description)).getText().toString());
						quest = spinnerQuestIds.get(type.getSelectedItemPosition());
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
								if (HealthGenius.myPatientManager.addQuestEvent(HealthGenius.myPatientManager.currentPat.getId(), quest, eventtoadd))
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
											loadScheduleWeekForPatientMeas(dategiven);
											dialog.cancel();
											toast.show();
											break;
										case 1:
											loadScheduleWeekForPatientMeas(dategiven);
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
	
	public void showMeasResults(final Sample sample, final boolean goback) {                                                            
		HealthGenius.myApp.setContentView(R.layout.resultscreen);
		int width = HealthGenius.myApp.getResources().getDisplayMetrics().widthPixels;
		((TextView) HealthGenius.myApp.findViewById(R.id.startText)).setText(HealthGenius.myLanguageManager.CALENDAR_MEASUREMENT_RESULT);
		((TextView) HealthGenius.myApp.findViewById(R.id.startText)).setTextSize(24);
		TextView result = ((TextView) HealthGenius.myApp.findViewById(R.id.result));
		ImageButton back = (ImageButton) HealthGenius.myApp.findViewById(R.id.previous);
		if (sample instanceof PulseoximetrySample) {
			result.setText(((PulseoximetrySample)sample).getPulsioximetrySpanData());
		}
		if (sample instanceof com.o2hlink.healthgenius.patient.PulseoximetrySample) {
			result.setText(((com.o2hlink.healthgenius.patient.PulseoximetrySample)sample).getPulsioximetrySpanData());
		}
		if (sample instanceof SixMinutesWalkSample) {
			result.setText(((SixMinutesWalkSample)sample).getExerciseSpanData());
			LinearLayout content = (LinearLayout) HealthGenius.myApp.findViewById(R.id.mainLayout);
			FrameLayout frame = new FrameLayout(HealthGenius.myApp);
			frame.setLayoutParams(new LayoutParams(width*4/5, width*4/5));
			SixMinutesGraphViewWithCustomData graph = new SixMinutesGraphViewWithCustomData(HealthGenius.myApp, ((SixMinutesWalkSample) sample).hrtrack, ((SixMinutesWalkSample) sample).so2track, ((SixMinutesWalkSample) sample).time, width*4/5, width*4/5);
			frame.addView(graph);
			content.addView(frame);
		}
		if (sample instanceof com.o2hlink.healthgenius.patient.SixMinutesWalkSample) {
			result.setText(((com.o2hlink.healthgenius.patient.SixMinutesWalkSample)sample).getExerciseSpanData());
			LinearLayout content = (LinearLayout) HealthGenius.myApp.findViewById(R.id.mainLayout);
			FrameLayout frame = new FrameLayout(HealthGenius.myApp);
			frame.setLayoutParams(new LayoutParams(width*4/5, width*4/5));
			SixMinutesGraphViewWithCustomData graph = new SixMinutesGraphViewWithCustomData(HealthGenius.myApp, ((com.o2hlink.healthgenius.patient.SixMinutesWalkSample) sample).hrtrack, ((com.o2hlink.healthgenius.patient.SixMinutesWalkSample) sample).so2track, ((com.o2hlink.healthgenius.patient.SixMinutesWalkSample) sample).time, width*4/5, width*4/5);
			frame.addView(graph);
			content.addView(frame);
		}
		if (sample instanceof SpirometrySample) {
			result.setText(((SpirometrySample)sample).getSpirometrySpanData());
			LinearLayout content = (LinearLayout) HealthGenius.myApp.findViewById(R.id.mainLayout);
			FrameLayout frame = new FrameLayout(HealthGenius.myApp);
			frame.setLayoutParams(new LayoutParams(width*4/5, width*4/5));
			SpirometryGraphViewWithCustomData graph = new SpirometryGraphViewWithCustomData(HealthGenius.myApp, (Spirometry)sample, ((SpirometrySample) sample).flow, ((SpirometrySample) sample).time, width*4/5, width*4/5);
			frame.addView(graph);
			content.addView(frame);
		}
		if (sample instanceof com.o2hlink.healthgenius.patient.SpirometrySample) {
			result.setText(((com.o2hlink.healthgenius.patient.SpirometrySample)sample).getSpirometrySpanData());
			LinearLayout content = (LinearLayout) HealthGenius.myApp.findViewById(R.id.mainLayout);
			FrameLayout frame = new FrameLayout(HealthGenius.myApp);
			frame.setLayoutParams(new LayoutParams(width*4/5, width*4/5));
			SpirometryGraphViewWithCustomData graph = new SpirometryGraphViewWithCustomData(HealthGenius.myApp, (Spirometry)sample, ((com.o2hlink.healthgenius.patient.SpirometrySample) sample).flow, ((com.o2hlink.healthgenius.patient.SpirometrySample) sample).time, width*4/5, width*4/5);
			frame.addView(graph);
			content.addView(frame);
		}
		if (sample instanceof WeightHeightSample) {
			result.setText(((WeightHeightSample)sample).getWeightHeightSpanData());
		}
		if (sample instanceof com.o2hlink.healthgenius.patient.PulseoximetrySample) {
			result.setText(((com.o2hlink.healthgenius.patient.WeightHeightSample)sample).getWeightHeightSpanData());
		}
		if (sample instanceof ExerciseSample) {
			result.setText(((ExerciseSample)sample).getExerciseSpanData());
		}
		if (sample instanceof com.o2hlink.healthgenius.patient.ExerciseSample) {
			result.setText(((com.o2hlink.healthgenius.patient.ExerciseSample)sample).getExerciseSpanData());
		}
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (goback) myUIManager.UIcalendar.loadScheduleDay(sample.getDate());
				else loadScheduleDayForPatientMeas(sample.getDate());
			}
		});
	}
	
	public void showQuestResults(final QuestionnaireSample sample, final boolean goback, String name) {                                                                           
		HealthGenius.myApp.setContentView(R.layout.resultscreen);
		((TextView) HealthGenius.myApp.findViewById(R.id.startText)).setText(HealthGenius.myLanguageManager.CALENDAR_MEASUREMENT_RESULT);
		((TextView) HealthGenius.myApp.findViewById(R.id.startText)).setTextSize(24);
		TextView result = ((TextView) HealthGenius.myApp.findViewById(R.id.result));
		result.setGravity(Gravity.LEFT);
		ImageButton back = (ImageButton) HealthGenius.myApp.findViewById(R.id.previous);
		if (sample instanceof com.o2hlink.healthgenius.patient.QuestionnaireSample) {
			result.setText(((com.o2hlink.healthgenius.patient.QuestionnaireSample)sample).getQuestionnaireSpanData(name));
		}
		if (sample instanceof com.o2hlink.healthgenius.data.calendar.QuestionnaireSample) {
			result.setText(((com.o2hlink.healthgenius.data.calendar.QuestionnaireSample)sample).getQuestionnaireSpanData(name));
		}
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (goback) myUIManager.UIcalendar.loadScheduleDay(sample.getDate());
				else loadScheduleDayForPatientQuest(sample.getDate());
			}
		});
	}

}
