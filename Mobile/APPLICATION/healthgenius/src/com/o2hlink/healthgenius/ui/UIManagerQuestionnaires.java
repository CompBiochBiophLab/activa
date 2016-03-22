package com.o2hlink.healthgenius.ui;

import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageView.ScaleType;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TableLayout.LayoutParams;

import com.o2hlink.activ8.client.entity.Answer;
import com.o2hlink.activ8.client.entity.Array;
import com.o2hlink.activ8.client.entity.Condition;
import com.o2hlink.activ8.client.entity.Contact;
import com.o2hlink.activ8.client.entity.MultiAnswer;
import com.o2hlink.activ8.client.entity.MultiCondition;
import com.o2hlink.activ8.client.entity.MultiQuestion;
import com.o2hlink.activ8.client.entity.NumericAnswer;
import com.o2hlink.activ8.client.entity.NumericCondition;
import com.o2hlink.activ8.client.entity.NumericQuestion;
import com.o2hlink.activ8.client.entity.Question;
import com.o2hlink.activ8.client.entity.Questionnaire;
import com.o2hlink.activ8.client.entity.TextAnswer;
import com.o2hlink.activ8.client.entity.TextQuestion;
import com.o2hlink.healthgenius.HealthGenius;
import com.o2hlink.healthgenius.R;
import com.o2hlink.healthgenius.background.SendQuestionnaire;

public class UIManagerQuestionnaires {
	
	public UIManager myUIManager;
	
	public UIManagerQuestionnaires(UIManager ui) {
		myUIManager = ui;
	}
	
	public void loadSharedQuestionnaires() {
		myUIManager.state = UIManager.UI_STATE_TOTALQUESTIONNAIRE;
		int realwidth = HealthGenius.myApp.getResources().getDisplayMetrics().widthPixels;
		HealthGenius.myApp.setContentView(R.layout.list);
		((TextView) HealthGenius.myApp.findViewById(R.id.startText)).setText(HealthGenius.myLanguageManager.QUEST_ASSIGNED_TITLE);
		TableLayout questlisting = (TableLayout)HealthGenius.myApp.findViewById(R.id.listing);
		Enumeration<Questionnaire> enumer = HealthGenius.myQuestControlManager.questionnaires.elements();
		while (enumer.hasMoreElements()) {			
			final Questionnaire quest = enumer.nextElement();
			TableRow buttonLayout = new TableRow(HealthGenius.myApp);
			buttonLayout.setOrientation(TableRow.HORIZONTAL);
			buttonLayout.setGravity(Gravity.CENTER_VERTICAL);
			buttonLayout.setLayoutParams(new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));			
			ImageButton button = new ImageButton(HealthGenius.myApp);
			button.setBackgroundResource(R.drawable.iconbg);
			button.setImageResource(R.drawable.quest);
			OnClickListener listener = new OnClickListener() {
				@Override
				public void onClick(View v) {
					HealthGenius.myQuestControlManager.eventAssociated = null;
					HealthGenius.myQuestControlManager.activeQuestionnaire = quest;
					myUIManager.UIquest.startQuestionnaire(quest);
				}
			};
			TextView text = new TextView(HealthGenius.myApp);
			text.append(quest.getName());
			text.setWidth(realwidth/2);
			text.setTextSize((float) 20);
			text.setTextColor(Color.BLACK);
			text.setTypeface(Typeface.SANS_SERIF);
			ImageButton share = new ImageButton(HealthGenius.myApp);
			share.setBackgroundResource(R.drawable.iconbg);
			share.setImageResource(R.drawable.contacts);
			share.setScaleType(ScaleType.FIT_XY);
			share.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					shareQuestionnaire(quest);
				}
			});
			buttonLayout.addView(button);
			buttonLayout.addView(text);
			if (quest.getId() != HealthGenius.myLanguageManager.feedbackQuestId) buttonLayout.addView(share, 50, 50);
			button.setOnClickListener(listener);
			text.setOnClickListener(listener);
			questlisting.addView(buttonLayout);
		}
		Enumeration<Questionnaire> enumer2 = HealthGenius.myQuestControlManager.createdQuest.elements();
		while (enumer2.hasMoreElements()) {			
			final Questionnaire quest = enumer2.nextElement();
			TableRow buttonLayout = new TableRow(HealthGenius.myApp);
			buttonLayout.setOrientation(TableRow.HORIZONTAL);
			buttonLayout.setGravity(Gravity.CENTER_VERTICAL);
			buttonLayout.setLayoutParams(new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));			
			ImageButton button = new ImageButton(HealthGenius.myApp);
			button.setBackgroundResource(R.drawable.iconbg);
			button.setImageResource(R.drawable.quest);
			OnClickListener listener = new OnClickListener() {
				@Override
				public void onClick(View v) {
					HealthGenius.myQuestControlManager.eventAssociated = null;
					HealthGenius.myQuestControlManager.activeQuestionnaire = quest;
					startQuestionnaire(quest);
				}
			};
			TextView text = new TextView(HealthGenius.myApp);
			text.append(quest.getName());
			text.setWidth(realwidth/2);
			text.setTextSize((float) 20);
			text.setTextColor(Color.BLACK);
			text.setTypeface(Typeface.SANS_SERIF);
			ImageButton share = new ImageButton(HealthGenius.myApp);
			share.setBackgroundResource(R.drawable.iconbg);
			share.setImageResource(R.drawable.contacts);
			share.setScaleType(ScaleType.FIT_XY);
			share.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					shareQuestionnaire(quest);
				}
			});
			buttonLayout.addView(button);
			buttonLayout.addView(text);
			buttonLayout.addView(share, 50, 50);
			button.setOnClickListener(listener);
			text.setOnClickListener(listener);
			questlisting.addView(buttonLayout);
		}
		ImageButton back = (ImageButton) HealthGenius.myApp.findViewById(R.id.previous);
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				myUIManager.loadBoxOpen();
			}
		});
		ImageButton change = (ImageButton) HealthGenius.myApp.findViewById(R.id.help);
		change.setImageResource(R.drawable.refreshing);
		change.setVisibility(View.VISIBLE);
		change.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				loadAssignedQuestionnaires();			
			}
		});
		ImageView addquest = (ImageView) HealthGenius.myApp.findViewById(R.id.animation);
		addquest.setImageResource(R.drawable.searchcontact);
		addquest.setVisibility(View.VISIBLE);
		addquest.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				searchQuestionnaire();			
			}
		});
	}
	
	public void startQuestionnaire(final Questionnaire quest) {
		myUIManager.state = UIManager.UI_STATE_QUESTIONNAIREINIT;
		HealthGenius.myApp.setContentView(R.layout.yesnoquestion);
		TextView question = (TextView)HealthGenius.myApp.findViewById(R.id.question);
		question.append(HealthGenius.myLanguageManager.QUEST_START + quest.getName() + "?");
		ImageButton yes = (ImageButton)HealthGenius.myApp.findViewById(R.id.yes);
		ImageButton no = (ImageButton)HealthGenius.myApp.findViewById(R.id.no);
		yes.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Runnable run = new Runnable() {
					@Override
					public void run() {
						handler.sendEmptyMessage(0);
						if (HealthGenius.myQuestControlManager.getQuestionnaire(quest.getId())) {
							if (HealthGenius.myQuestControlManager.validateQuestionnaire()) handler.sendEmptyMessage(1);
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
									myUIManager.UImisc.loadInfoPopupWithoutExiting(HealthGenius.myLanguageManager.NOTIFICATION_DOWNLOADING + quest.getName() + " ...");
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
									HealthGenius.myQuestControlManager.startQuestionnaire(quest);
									break;
								case 2:
									animationFrame = (ImageView) HealthGenius.myApp.findViewById(R.id.popupImage);
									animation = (AnimationDrawable) animationFrame.getBackground();
									animation.stop();
									animationFrame.setVisibility(View.GONE);
									if (HealthGenius.myQuestControlManager.eventAssociated == null) loadSharedQuestionnaires();
									else myUIManager.UIcalendar.loadScheduleDay(new Date());
									myUIManager.UImisc.loadInfoPopup(HealthGenius.myLanguageManager.CONNECTION_FAILED);
									break;
								case 3:
									animationFrame = (ImageView) HealthGenius.myApp.findViewById(R.id.popupImage);
									animation = (AnimationDrawable) animationFrame.getBackground();
									animation.stop();
									animationFrame.setVisibility(View.GONE);
									if (HealthGenius.myQuestControlManager.eventAssociated == null) loadSharedQuestionnaires();
									else myUIManager.UIcalendar.loadScheduleDay(new Date());
									myUIManager.UImisc.loadInfoPopup(HealthGenius.myLanguageManager.QUEST_NOTVALID);
									break;
							}
						}
					};
				};
				Thread thread = new Thread(run);
				thread.start();
			}
		});
		no.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (HealthGenius.mySensorManager.eventAssociated != null) myUIManager.UIcalendar.loadScheduleDay(HealthGenius.mySensorManager.eventAssociated.getDate());
				else loadSharedQuestionnaires();
			}
		});
	}

	public void loadQuestion(final Question question) {
		myUIManager.state = UIManager.UI_STATE_QUESTION;
		TextView questionText;
		ImageButton next;
		if (question == null) {
			loadSharedQuestionnaires();
			myUIManager.UImisc.loadInfoPopup("QUESTIONNAIRE MALFORMED");
		}
		if (question instanceof MultiQuestion) {
			MultiQuestion multiquest = (MultiQuestion) question;
			final MultiAnswer answer = new MultiAnswer();
			answer.setQuestion(question.getId());
			if (HealthGenius.myQuestControlManager.activeAnswers.get(question.getId()).isEmpty()) {
				HealthGenius.myApp.setContentView(R.layout.info);
				((RelativeLayout)HealthGenius.myApp.findViewById(R.id.questcontrol)).setVisibility(View.VISIBLE);
				TextView text = (TextView) HealthGenius.myApp.findViewById(R.id.textInfo); 
				text.setText(question.getName());
				text.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
				((ImageButton) HealthGenius.myApp.findViewById(R.id.previous)).setVisibility(View.VISIBLE);
				next = (ImageButton) HealthGenius.myApp.findViewById(R.id.next);
				next.setVisibility(View.VISIBLE);
				next.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						if (question.getNext() != null) loadQuestion(HealthGenius.myQuestControlManager.activeQuestions.get(question.getNext()));	
						else HealthGenius.myQuestControlManager.finishQuestionnaire();
					}
				});
		    }
			else if (multiquest.isMultiple()) {
				HealthGenius.myApp.setContentView(R.layout.poliquestion);
				questionText = (TextView) HealthGenius.myApp.findViewById(R.id.questionText);
				questionText.setText(multiquest.getName());
				final Array<Answer> answers = HealthGenius.myQuestControlManager.activeAnswers.get(question.getId());
				next = (ImageButton) HealthGenius.myApp.findViewById(R.id.next);
				LinearLayout answersGroupPoli = (LinearLayout) HealthGenius.myApp.findViewById(R.id.options);
				for (int i = 0; i < answers.size(); i++) {
					CheckBox button = new CheckBox(HealthGenius.myApp);
					button.setText(answers.get(i).getName());
					button.setTextColor(Color.BLACK);
					button.setId(i);
					button.setLayoutParams(new LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
					answersGroupPoli.addView(button);
					button.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {	
						@Override
						public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
							if (isChecked) {
								if (!answer.getAnswers().contains(answers.get(buttonView.getId()).getId())) answer.getAnswers().add(answers.get(buttonView.getId()).getId());
							}
							else if (answer.getAnswers().contains(answers.get(buttonView.getId()).getId())) answer.getAnswers().remove(answers.get(buttonView.getId()).getId()); 
						}
					});
				}
				next.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						if (answer.getAnswers().isEmpty()) if (!question.isOptional()) return;
						HealthGenius.myQuestControlManager.activeAnswersAnswered.push(answer);
						HealthGenius.myQuestControlManager.activeQuestionsAnswered.push(question);
						Long next = HealthGenius.myQuestControlManager.calcMultiNextQuestion(question.getId(), answer);
						if (next == null) HealthGenius.myQuestControlManager.finishQuestionnaire();
						else loadQuestion(HealthGenius.myQuestControlManager.activeQuestions.get(next));
					}
				});
			}
			else {
				final Array<Answer> answers = HealthGenius.myQuestControlManager.activeAnswers.get(question.getId());
		    	HealthGenius.myApp.setContentView(R.layout.monoquestion);
				questionText = (TextView) HealthGenius.myApp.findViewById(R.id.questionText);
				questionText.setText(multiquest.getName());
				RadioGroup answersGroup = (RadioGroup) HealthGenius.myApp.findViewById(R.id.options);
				next = (ImageButton) HealthGenius.myApp.findViewById(R.id.next);
				for (int i = 0; i < answers.size(); i++) {
					RadioButton button = new RadioButton(HealthGenius.myApp);
					button.setText(answers.get(i).getName());
					button.setTextColor(Color.BLACK);
					button.setId(i);
					button.setLayoutParams(new LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT));
					answersGroup.addView(button);
				}
				answersGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {					
					@Override
					public void onCheckedChanged(RadioGroup group, int checkedId) {
						answer.getAnswers().clear();
						answer.getAnswers().add(answers.get(checkedId).getId());
					}
				});
				next.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						if (answer.getAnswers().isEmpty()) if (!question.isOptional()) return;
						HealthGenius.myQuestControlManager.activeAnswersAnswered.push(answer);
						HealthGenius.myQuestControlManager.activeQuestionsAnswered.push(question);
						Long next = HealthGenius.myQuestControlManager.calcNextQuestion(question.getId(), answer);
						if (next == null) HealthGenius.myQuestControlManager.finishQuestionnaire();
						else loadQuestion(HealthGenius.myQuestControlManager.activeQuestions.get(next));
					}
				});
			}
	    }
	    else if (question instanceof NumericQuestion) {
	    	NumericQuestion numquest = (NumericQuestion) question;
	    	final NumericAnswer answer = new NumericAnswer();
			answer.setQuestion(question.getId());
			HealthGenius.myApp.setContentView(R.layout.numquestion);
			final String representation[] = {HealthGenius.myLanguageManager.BORG_0, HealthGenius.myLanguageManager.BORG_05, 
					HealthGenius.myLanguageManager.BORG_1, HealthGenius.myLanguageManager.BORG_1, 
					HealthGenius.myLanguageManager.BORG_2, HealthGenius.myLanguageManager.BORG_2, 
					HealthGenius.myLanguageManager.BORG_3, HealthGenius.myLanguageManager.BORG_3, 
					HealthGenius.myLanguageManager.BORG_4, HealthGenius.myLanguageManager.BORG_4, 
					HealthGenius.myLanguageManager.BORG_5, HealthGenius.myLanguageManager.BORG_5, 
					HealthGenius.myLanguageManager.BORG_5, HealthGenius.myLanguageManager.BORG_5, 
					HealthGenius.myLanguageManager.BORG_7, HealthGenius.myLanguageManager.BORG_7, 
					HealthGenius.myLanguageManager.BORG_7, HealthGenius.myLanguageManager.BORG_7, 
					HealthGenius.myLanguageManager.BORG_9, HealthGenius.myLanguageManager.BORG_9, 
					HealthGenius.myLanguageManager.BORG_10, HealthGenius.myLanguageManager.BORG_10};
			questionText = (TextView) HealthGenius.myApp.findViewById(R.id.questionText);
			final TextView numText = (TextView) HealthGenius.myApp.findViewById(R.id.numText);
			questionText.setText(numquest.getName());
			numText.setText("0 - " + representation[0]);
			SeekBar seekbar = (SeekBar) HealthGenius.myApp.findViewById(R.id.seekbar);
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
					if ((selection == 1)) numText.setText("0.5 - " + representation[1]);
					else numText.setText("" + progress/10 + " - " + representation[selection]);
				}
			});
			next = (ImageButton) HealthGenius.myApp.findViewById(R.id.next);
			next.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					SeekBar seekbar = (SeekBar) HealthGenius.myApp.findViewById(R.id.seekbar);
					if ((seekbar.getProgress()/5) == 1) answer.setValue(0.5f);
					else answer.setValue((double)(seekbar.getProgress()/10)); 
					HealthGenius.myQuestControlManager.activeAnswersAnswered.push(answer);
					HealthGenius.myQuestControlManager.activeQuestionsAnswered.push(question);
					Long next = HealthGenius.myQuestControlManager.calcNumericNextQuestion(question.getId(), answer);
					if (next != null) loadQuestion(HealthGenius.myQuestControlManager.activeQuestions.get(next));
					else HealthGenius.myQuestControlManager.finishQuestionnaire();
				}
			});
	    }
	    else if (question instanceof TextQuestion) {
	    	TextQuestion textquest = (TextQuestion) question;
	    	final TextAnswer answer = new TextAnswer();
			answer.setQuestion(question.getId());
			HealthGenius.myApp.setContentView(R.layout.textquestion);
			questionText = (TextView) HealthGenius.myApp.findViewById(R.id.questionText);
			questionText.setText(textquest.getName());
			next = (ImageButton) HealthGenius.myApp.findViewById(R.id.next);
			next.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					EditText editable = (EditText) HealthGenius.myApp.findViewById(R.id.editable);
					answer.setValue(editable.getText().toString());
					HealthGenius.myQuestControlManager.activeAnswersAnswered.push(answer);
					HealthGenius.myQuestControlManager.activeQuestionsAnswered.push(question);
					if (question.getNext() == null)	HealthGenius.myQuestControlManager.finishQuestionnaire();
					else loadQuestion(HealthGenius.myQuestControlManager.activeQuestions.get(question.getNext()));	
				}
			});
	    }
	    else {
			HealthGenius.myApp.setContentView(R.layout.info);
			((RelativeLayout)HealthGenius.myApp.findViewById(R.id.questcontrol)).setVisibility(View.VISIBLE);
			TextView text = (TextView) HealthGenius.myApp.findViewById(R.id.textInfo); 
			text.setText(question.getName());
			text.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
			((ImageButton) HealthGenius.myApp.findViewById(R.id.previous)).setVisibility(View.VISIBLE);
			next = (ImageButton) HealthGenius.myApp.findViewById(R.id.next);
			next.setVisibility(View.VISIBLE);
			next.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (question.getNext() != null) loadQuestion(HealthGenius.myQuestControlManager.activeQuestions.get(question.getNext()));	
					else HealthGenius.myQuestControlManager.finishQuestionnaire();
				}
			});
	    }
		ImageButton back = (ImageButton) HealthGenius.myApp.findViewById(R.id.previous);
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!HealthGenius.myQuestControlManager.activeQuestionsAnswered.isEmpty()) {
					Question prev = HealthGenius.myQuestControlManager.activeQuestionsAnswered.pop();
					HealthGenius.myQuestControlManager.activeAnswersAnswered.pop();
					loadQuestion(prev);
				}
				else {
					loadSharedQuestionnaires();
				}
			}
		});
		ImageButton quit = (ImageButton) HealthGenius.myApp.findViewById(R.id.quit);
		quit.setVisibility(View.VISIBLE);
		quit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				loadSharedQuestionnaires();
			}
		});
	}

	public void loadQuestResult(double result) {
		HealthGenius.myApp.setContentView(R.layout.resultimage);
		TextView text = (TextView) HealthGenius.myApp.findViewById(R.id.textInfo);
		if (result < 0.0f) text.setText(HealthGenius.myLanguageManager.QUEST_WEIGHT_NOTDONE);
		else text.setText(HealthGenius.myLanguageManager.QUEST_WEIGHT_DONE + String.format("%.1f", result) + " %. ");
		ImageView resultText = (ImageView) HealthGenius.myApp.findViewById(R.id.result);
		if (result < 0.0) {
		}
		else if (result < 33.33) {
			text.append(HealthGenius.myLanguageManager.QUEST_WEIGHT_0);
			resultText.setBackgroundResource(R.drawable.lightgreen);
		}
		else if (result < 66.66) {
			text.append(HealthGenius.myLanguageManager.QUEST_WEIGHT_1);
			resultText.setBackgroundResource(R.drawable.lightorange);
		}
		else {
			text.append(HealthGenius.myLanguageManager.QUEST_WEIGHT_2);
			resultText.setBackgroundResource(R.drawable.lightwhite);
		}
		CountDownTimer timer = new CountDownTimer(3000, 1000) {
			@Override
			public void onTick(long millisUntilFinished) {}
			@Override
			public void onFinish() {
				HealthGenius.myApp.setContentView(R.layout.sending);
				SendQuestionnaire sending = new SendQuestionnaire(HealthGenius.myQuestControlManager.activeQuestionnaire.getId(), HealthGenius.myQuestControlManager.getQuestToSend());
				Thread thread = new Thread(sending, "SENDQUESTIONNAIRE");
				thread.start();
			}
		};
		timer.start();
	}
	
	public void searchQuestionnaire() {
		LayoutInflater inflater = (LayoutInflater) HealthGenius.myApp.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		final View layout = inflater.inflate(R.layout.toastsearch,
		                               (ViewGroup) HealthGenius.myApp.findViewById(R.id.toastsearchroot));
		TextView text = (TextView) layout.findViewById(R.id.searchexpl);
		text.setText(HealthGenius.myLanguageManager.NOTIFICATION_QUEST_EXPL);
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
				final ProgressDialog dialog = ProgressDialog.show(HealthGenius.myApp, HealthGenius.myLanguageManager.NOTIFICATION_QUEST_TITLE, 
						HealthGenius.myLanguageManager.NOTIFICATION_QUEST_SEARCHING, true);
				dialog.show();
				Runnable run =  new Runnable() {
					Array<Questionnaire> quests;
					@Override
					public void run() {
						quests = HealthGenius.myQuestControlManager.SearchQuests(query);
						dialog.cancel();
						if (quests == null) {
							handler.sendEmptyMessage(0);
						} else if (quests.size() == 0) {
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
									Toast toast2 = Toast.makeText(HealthGenius.myApp, HealthGenius.myLanguageManager.NOTIFICATION_QUEST_NOTFOUND, Toast.LENGTH_SHORT);
									toast2.show();
									break;
								case 2:
									final CharSequence[] items = new CharSequence[quests.size()];
									final Array<Questionnaire> questsToAdd = new Array<Questionnaire>();
									for (int i = 0; i < quests.size(); i++) {
										items[i] = quests.get(i).getName();
									}
									AlertDialog.Builder builder = new AlertDialog.Builder(HealthGenius.myApp);
									builder.setTitle(HealthGenius.myLanguageManager.NOTIFICATION_QUEST_ADD);
									builder.setMultiChoiceItems(items, null, new OnMultiChoiceClickListener() {
										@Override
										public void onClick(DialogInterface dialog, int which, boolean isChecked) {
											if (isChecked) questsToAdd.add(quests.get(which));
											else questsToAdd.remove(quests.get(which));
										}
									});
									builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
								           public void onClick(DialogInterface dialog, int id) {
								        	   for (int i = 0; i < questsToAdd.size(); i++) {
								        		   ArrayList<Long> users = new ArrayList<Long>();
								        		   users.add(HealthGenius.myMobileManager.userForServices.getId());
								        		   HealthGenius.myQuestControlManager.shareQuestionnaire(users, questsToAdd.get(i));
//									        	   HealthGenius.myProtocolManager.getPrivilegesOnQuestionnaire(questsToAdd.get(i));
								        	   }
								               dialog.cancel();
								               loadSharedQuestionnaires();
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
	
	public void loadAssignedQuestionnaires() {
		myUIManager.state = UIManager.UI_STATE_TOTALQUESTIONNAIRE;
		int realwidth = HealthGenius.myApp.getResources().getDisplayMetrics().widthPixels;
		HealthGenius.myApp.setContentView(R.layout.list);
		((TextView) HealthGenius.myApp.findViewById(R.id.startText)).setText(HealthGenius.myLanguageManager.QUEST_CREATED_TITLE);
		TableLayout questlisting = (TableLayout)HealthGenius.myApp.findViewById(R.id.listing);
		Enumeration<Questionnaire> enumer = HealthGenius.myQuestControlManager.createdQuest.elements();
		while (enumer.hasMoreElements()) {			
			final Questionnaire quest = enumer.nextElement();
			TableRow buttonLayout = new TableRow(HealthGenius.myApp);
			buttonLayout.setOrientation(TableRow.HORIZONTAL);
			buttonLayout.setGravity(Gravity.CENTER_VERTICAL);
			buttonLayout.setLayoutParams(new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));			
			ImageButton button = new ImageButton(HealthGenius.myApp);
			button.setBackgroundResource(R.drawable.iconbg);
			button.setImageResource(R.drawable.quest);
			OnClickListener listener = new OnClickListener() {
				@Override
				public void onClick(View v) {
					Runnable run = new Runnable() {
						@Override
						public void run() {
							handler.sendEmptyMessage(0);
							if (HealthGenius.myQuestControlManager.getQuestionnaire(quest.getId())) {
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
										myUIManager.UImisc.loadInfoPopupWithoutExiting(HealthGenius.myLanguageManager.NOTIFICATION_DOWNLOADING + quest.getName() + " ...");
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
										modifyQuestionnaire(quest);
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
			TextView text = new TextView(HealthGenius.myApp);
			text.append(quest.getName());
			text.setWidth(realwidth*2/5);
			text.setTextSize((float) 20);
			text.setTextColor(Color.BLACK);
			text.setTypeface(Typeface.SANS_SERIF);			
			ImageButton delete = new ImageButton(HealthGenius.myApp);
			delete.setBackgroundResource(R.drawable.iconbg);
			delete.setScaleType(ScaleType.FIT_XY);
			delete.setImageResource(R.drawable.delete);
			delete.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Runnable run = new Runnable() {
						@Override
						public void run() {
							handler.sendEmptyMessage(0);
							if (HealthGenius.myQuestControlManager.removeQuestionnaire(quest)) {
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
										myUIManager.UImisc.loadInfoPopupWithoutExiting("Removing questionnaire ...");
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
										loadAssignedQuestionnaires();
										break;
									case 2:
										animationFrame = (ImageView) HealthGenius.myApp.findViewById(R.id.popupImage);
										animation = (AnimationDrawable) animationFrame.getBackground();
										animation.stop();
										animationFrame.setVisibility(View.GONE);
										myUIManager.UImisc.loadInfoPopup("Remove failed");
										break;
								}
							}
						};
					};
					Thread thread = new Thread(run);
					thread.start();
				}
			});			
			ImageButton share = new ImageButton(HealthGenius.myApp);
			share.setBackgroundResource(R.drawable.iconbg);
			share.setImageResource(R.drawable.contacts);
			share.setScaleType(ScaleType.FIT_XY);
			share.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					shareQuestionnaire(quest);
				}
			});
			buttonLayout.addView(button);
			buttonLayout.addView(text);
			buttonLayout.addView(share, 50, 50);
			buttonLayout.addView(delete, 50, 50);
			button.setOnClickListener(listener);
			text.setOnClickListener(listener);
			questlisting.addView(buttonLayout);
		}
		ImageButton back = (ImageButton) HealthGenius.myApp.findViewById(R.id.previous);
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				myUIManager.loadBoxOpen();
			}
		});
		ImageButton change = (ImageButton) HealthGenius.myApp.findViewById(R.id.help);
		change.setImageResource(R.drawable.refreshing);
		change.setVisibility(View.VISIBLE);
		change.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				loadSharedQuestionnaires();
			}
		});
		ImageView addquest = (ImageView) HealthGenius.myApp.findViewById(R.id.animation);
		addquest.setImageResource(R.drawable.plus);
		addquest.setVisibility(View.VISIBLE);
		addquest.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				createNewQuestionnaire();			
			}
		});
	}
	
	public void shareQuestionnaire(final Questionnaire quest) {
		final Long[] users = new Long[HealthGenius.myContactsManager.contactList.size() + 1];
		final CharSequence[] items = new CharSequence[HealthGenius.myContactsManager.contactList.size() + 1];
		final ArrayList<Long>usersToAdd = new ArrayList<Long>();
		int i = 1;
		users[0] = -1l;
		items[0] = "Make it public";
		Enumeration<Contact> enumerator = HealthGenius.myContactsManager.contactList.elements();
		while (enumerator.hasMoreElements()) {
			Contact user = enumerator.nextElement();
			items[i] = user.getFirstName() + " " + user.getLastName();
			users[i] = Long.parseLong(user.getId());
			i++;
		}
		AlertDialog.Builder builder = new AlertDialog.Builder(HealthGenius.myApp);
		builder.setTitle(HealthGenius.myLanguageManager.NOTIFICATION_SEARCH_ADD);
		builder.setMultiChoiceItems(items, null, new OnMultiChoiceClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which, boolean isChecked) {
				if (isChecked) usersToAdd.add(users[which]);
				else usersToAdd.remove(users[which]);
			}
		});
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
	           public void onClick(DialogInterface dialog, int id) {
	        	   if (usersToAdd.contains(users[0])) {
	        		   usersToAdd.remove(users[0]);
	        		   HealthGenius.myQuestControlManager.publishQuestionnaire(quest);
	        	   }
	        	   HealthGenius.myQuestControlManager.shareQuestionnaire(usersToAdd, quest);
	        	   dialog.cancel();
	           }
	    });
		builder.setNegativeButton(HealthGenius.myLanguageManager.MAIN_CONTACTS, new DialogInterface.OnClickListener() {
	           public void onClick(DialogInterface dialog, int id) {
	        	   myUIManager.UIcontacts.loadContactList(true);
	           }
	    });
		AlertDialog alert = builder.create();
		alert.show();
	}
	
	public void createNewQuestionnaire() {
		LayoutInflater inflater = (LayoutInflater) HealthGenius.myApp.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		final View layout = inflater.inflate(R.layout.toastaddquestionnaire,
		                               (ViewGroup) HealthGenius.myApp.findViewById(R.id.toastaddquestionnaireroot));
		TextView title = (TextView) layout.findViewById(R.id.addtitle);
		title.setText(HealthGenius.myLanguageManager.QUEST_CREATE_QUESTIONNAIRE);
		TextView namereq = (TextView) layout.findViewById(R.id.namerequested);
		namereq.setText(HealthGenius.myLanguageManager.QUEST_NAME);
		ImageButton image = (ImageButton) layout.findViewById(R.id.addeventok);
		Builder builder = new AlertDialog.Builder(HealthGenius.myApp);
		builder.setView(layout);
		final AlertDialog alertDialog = builder.create();
		alertDialog.show();
		image.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				final String quest = ((EditText) layout.findViewById(R.id.name)).getText().toString();
				final ProgressDialog dialog = ProgressDialog.show(HealthGenius.myApp, HealthGenius.myLanguageManager.QUEST_CREATE_QUESTIONNAIRE, 
						HealthGenius.myLanguageManager.QUEST_CREATING_QUESTIONNAIRE, true);
				dialog.show();
				Runnable run =  new Runnable() {
					@Override
					public void run() {
						if (HealthGenius.myQuestControlManager.createQuestionnaire(quest))
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
									modifyQuestionnaire(HealthGenius.myQuestControlManager.activeQuestionnaire);
									dialog.cancel();
									toast.show();
									break;
								case 1:
									loadAssignedQuestionnaires();
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
	
	public void modifyQuestionnaire(final Questionnaire questionnaire) {
		HealthGenius.myQuestControlManager.activeQuestionnaire = questionnaire;
		int realwidth = HealthGenius.myApp.getResources().getDisplayMetrics().widthPixels;
		myUIManager.state = UIManager.UI_STATE_TOTALQUESTIONNAIRE;
		HealthGenius.myApp.setContentView(R.layout.list);
		((TextView) HealthGenius.myApp.findViewById(R.id.startText)).setText(HealthGenius.myLanguageManager.QUEST_CREATED_TITLE);
		TableLayout questlisting = (TableLayout)HealthGenius.myApp.findViewById(R.id.listing);
		Enumeration<Question> enumer = HealthGenius.myQuestControlManager.activeQuestions.elements();
		while (enumer.hasMoreElements()) {			
			final Question quest = enumer.nextElement();
			TableRow buttonLayout = new TableRow(HealthGenius.myApp);
			buttonLayout.setOrientation(TableRow.HORIZONTAL);
			buttonLayout.setGravity(Gravity.CENTER_VERTICAL);
			TableLayout.LayoutParams layoutparams = new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
			layoutparams.setMargins(10, 0, 0, 0);
			buttonLayout.setLayoutParams(layoutparams);			
			ImageButton delete = new ImageButton(HealthGenius.myApp);
			delete.setBackgroundResource(R.drawable.iconbg);
			delete.setScaleType(ScaleType.FIT_XY);
			delete.setImageResource(R.drawable.delete);
			delete.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Runnable run = new Runnable() {
						@Override
						public void run() {
							handler.sendEmptyMessage(0);
							if (HealthGenius.myQuestControlManager.removeQuestion(quest)) {
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
										myUIManager.UImisc.loadInfoPopupWithoutExiting("Removing question ...");
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
										modifyQuestionnaire(questionnaire);
										break;
									case 2:
										animationFrame = (ImageView) HealthGenius.myApp.findViewById(R.id.popupImage);
										animation = (AnimationDrawable) animationFrame.getBackground();
										animation.stop();
										animationFrame.setVisibility(View.GONE);
										myUIManager.UImisc.loadInfoPopup("Remove failed");
										break;
								}
							}
						};
					};
					Thread thread = new Thread(run);
					thread.start();
				}
			});			
			ImageButton modify = new ImageButton(HealthGenius.myApp);
			modify.setBackgroundResource(R.drawable.iconbg);
			modify.setImageResource(R.drawable.modify);
			modify.setScaleType(ScaleType.FIT_XY);
			modify.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					modifyQuestion(quest);
				}
			});
			TextView text = new TextView(HealthGenius.myApp);
			text.append(quest.getName());
			if ((questionnaire.getFirst() != null)&&(questionnaire.getFirst().equals(quest.getId()))) text.append("[FIRST QUESTION]");
			text.setWidth(realwidth*3/5);
			text.setTextSize((float) 20);
			text.setTextColor(Color.BLACK);
			text.setTypeface(Typeface.SANS_SERIF);
			buttonLayout.addView(text);
			buttonLayout.addView(modify, 50, 50);
			buttonLayout.addView(delete, 50, 50);
			questlisting.addView(buttonLayout);
		}
		ImageButton back = (ImageButton) HealthGenius.myApp.findViewById(R.id.previous);
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				myUIManager.loadBoxOpen();
			}
		});
		ImageButton change = (ImageButton) HealthGenius.myApp.findViewById(R.id.help);
		change.setImageResource(R.drawable.first);
		change.setVisibility(View.VISIBLE);
		change.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setFirstQuestion();			
			}
		});
		ImageView addquest = (ImageView) HealthGenius.myApp.findViewById(R.id.animation);
		addquest.setImageResource(R.drawable.plus);
		addquest.setVisibility(View.VISIBLE);
		addquest.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				createNewQuestion();			
			}
		});
	}
	
	public void setFirstQuestion() {
		Enumeration<Question> questions = HealthGenius.myQuestControlManager.activeQuestions.elements();
		final CharSequence[] items = new CharSequence[HealthGenius.myQuestControlManager.activeQuestions.size()];
		final Long[] longitems = new Long[HealthGenius.myQuestControlManager.activeQuestions.size()];
		int j = 0;
		while (questions.hasMoreElements()) {
			Question question = questions.nextElement();
			items[j] = question.getName();
			longitems[j] = question.getId();
			j++;
		}
		AlertDialog.Builder builder = new AlertDialog.Builder(HealthGenius.myApp);
		builder.setTitle(HealthGenius.myLanguageManager.MESSAGES_CONTACT_REQUEST);
		builder.setItems(items, new DialogInterface.OnClickListener() {
		    public void onClick(DialogInterface dialog, int item) {
		    	HealthGenius.myQuestControlManager.activeQuestionnaire.setFirst(longitems[item]);
				Runnable run = new Runnable() {
					@Override
					public void run() {
						handler.sendEmptyMessage(0);
						if (HealthGenius.myQuestControlManager.updateQuestionnaire(HealthGenius.myQuestControlManager.activeQuestionnaire))
							handler.sendEmptyMessage(1);
						else handler.sendEmptyMessage(2);
					}
					private Handler handler = new Handler(){
						@Override
						public void handleMessage(Message msg) {
							ImageView animationFrame;
							AnimationDrawable animation;
							switch (msg.what) {
								case 0:
									myUIManager.UImisc.loadInfoPopupWithoutExiting("Updating questionnaire ...");
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
									modifyQuestionnaire(HealthGenius.myQuestControlManager.activeQuestionnaire);
									break;
								case 2:
									animationFrame = (ImageView) HealthGenius.myApp.findViewById(R.id.popupImage);
									animation = (AnimationDrawable) animationFrame.getBackground();
									animation.stop();
									animationFrame.setVisibility(View.GONE);
									myUIManager.UImisc.loadInfoPopup("Update failed");
									break;
							}
						}
					};
				};
				Thread thread = new Thread(run);
				thread.start();
		    }
		});
		AlertDialog alert = builder.create();
		alert.show();
	}
	
	public void createNewQuestion() {
		LayoutInflater inflater = (LayoutInflater) HealthGenius.myApp.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		final View layout = inflater.inflate(R.layout.toastcreatequestion,
		                               (ViewGroup) HealthGenius.myApp.findViewById(R.id.toastcreatequestionroot));
		TextView title = (TextView) layout.findViewById(R.id.addtitle);
		title.setText(HealthGenius.myLanguageManager.QUEST_CREATE_QUESTION);
		TextView namereq = (TextView) layout.findViewById(R.id.typerequest);
		namereq.setText(HealthGenius.myLanguageManager.QUEST_TYPE);
		final Spinner type = (Spinner) layout.findViewById(R.id.type);
	    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(HealthGenius.myApp, R.array.questtype, android.R.layout.simple_spinner_item);
	    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    type.setAdapter(adapter);
		ImageButton image = (ImageButton) layout.findViewById(R.id.addeventok);
		Builder builder = new AlertDialog.Builder(HealthGenius.myApp);
		builder.setView(layout);
		final AlertDialog alertDialog = builder.create();
		alertDialog.show();
		image.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				final Question question;
				switch (type.getSelectedItemPosition()) {
					case 0:
						question = new MultiQuestion();
						((MultiQuestion)question).setMultiple(false);
						break;
					case 1:
						question = new MultiQuestion();
						((MultiQuestion)question).setMultiple(true);
						break;
					case 2:
						question = new NumericQuestion();
						break;
					case 3:
						question = new TextQuestion();
						break;
					default:
						question = new Question();
						break;
				}
				question.setName("");
				Runnable run = new Runnable() {
					Question createdQuest;
					Question prevQest;
					@Override
					public void run() {
						handler.sendEmptyMessage(0);
						createdQuest = HealthGenius.myQuestControlManager.addQuestion(question);
						if (createdQuest != null) {
							if (HealthGenius.myQuestControlManager.activeQuestions.size() <= 1) {
								HealthGenius.myQuestControlManager.activeQuestionnaire.setFirst(createdQuest.getId());
								if (HealthGenius.myQuestControlManager.updateQuestionnaire(HealthGenius.myQuestControlManager.activeQuestionnaire))
									handler.sendEmptyMessage(3);
								else handler.sendEmptyMessage(4);
							}
							else {
								prevQest = HealthGenius.myQuestControlManager.activeQuestions.get(HealthGenius.myQuestControlManager.lastQuestionId);
								if (prevQest != null) {
									prevQest.setNext(createdQuest.getId());
									if (HealthGenius.myQuestControlManager.updateQuestion(prevQest))
										handler.sendEmptyMessage(1);
									else handler.sendEmptyMessage(5);
								}
								else handler.sendEmptyMessage(5);
							}
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
									myUIManager.UImisc.loadInfoPopupWithoutExiting(HealthGenius.myLanguageManager.QUEST_CREATING_QUESTION);
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
									HealthGenius.myQuestControlManager.lastQuestionId = createdQuest.getId();
									modifyQuestion(createdQuest);
									myUIManager.UImisc.loadInfoPopup(String.format(HealthGenius.myLanguageManager.QUEST_LINKED, prevQest.getName()));
									break;
								case 2:
									animationFrame = (ImageView) HealthGenius.myApp.findViewById(R.id.popupImage);
									animation = (AnimationDrawable) animationFrame.getBackground();
									animation.stop();
									animationFrame.setVisibility(View.GONE);
									myUIManager.UImisc.loadInfoPopup(HealthGenius.myLanguageManager.QUEST_CREATING_FAILED);
									break;
								case 3:
									animationFrame = (ImageView) HealthGenius.myApp.findViewById(R.id.popupImage);
									animation = (AnimationDrawable) animationFrame.getBackground();
									animation.stop();
									animationFrame.setVisibility(View.GONE);
									myUIManager.UImisc.removeInfoPopup();
									HealthGenius.myQuestControlManager.lastQuestionId = createdQuest.getId();
									modifyQuestion(createdQuest);
									myUIManager.UImisc.loadInfoPopup(HealthGenius.myLanguageManager.QUEST_FIRST);
									break;
								case 4:
									animationFrame = (ImageView) HealthGenius.myApp.findViewById(R.id.popupImage);
									animation = (AnimationDrawable) animationFrame.getBackground();
									animation.stop();
									animationFrame.setVisibility(View.GONE);
									myUIManager.UImisc.removeInfoPopup();
									HealthGenius.myQuestControlManager.lastQuestionId = createdQuest.getId();
									modifyQuestion(createdQuest);
									myUIManager.UImisc.loadInfoPopup(HealthGenius.myLanguageManager.QUEST_NOTFIRST);
									break;
								case 5:
									animationFrame = (ImageView) HealthGenius.myApp.findViewById(R.id.popupImage);
									animation = (AnimationDrawable) animationFrame.getBackground();
									animation.stop();
									animationFrame.setVisibility(View.GONE);
									myUIManager.UImisc.removeInfoPopup();
									HealthGenius.myQuestControlManager.lastQuestionId = createdQuest.getId();
									modifyQuestion(createdQuest);
									myUIManager.UImisc.loadInfoPopup(HealthGenius.myLanguageManager.QUEST_LINKED_FAILED);
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
	
	public void modifyQuestion(Question question, String text, int position, boolean selected) {
		modifyQuestion(question);
		((EditText) HealthGenius.myApp.findViewById(R.id.text)).setText(text);
		((Spinner) HealthGenius.myApp.findViewById(R.id.next)).setSelection(position);
		((CheckBox) HealthGenius.myApp.findViewById(R.id.optional)).setSelected(selected);
	}
	
	public void modifyQuestion(final Question question) {
		int selectedByFirst = 0, i = 0;
		int realwidth = HealthGenius.myApp.getResources().getDisplayMetrics().widthPixels;
		myUIManager.state = UIManager.UI_STATE_QUESTION;
		HealthGenius.myApp.setContentView(R.layout.createquestion);
		((TextView) HealthGenius.myApp.findViewById(R.id.startText)).setText(HealthGenius.myLanguageManager.QUEST_CREATE_QUESTIONNAIRE);
		((TextView) HealthGenius.myApp.findViewById(R.id.textrequest)).setText(HealthGenius.myLanguageManager.QUEST_TEXT + ": ");
		((EditText) HealthGenius.myApp.findViewById(R.id.text)).setText(question.getName());
		((TextView) HealthGenius.myApp.findViewById(R.id.nextrequest)).setText(HealthGenius.myLanguageManager.QUEST_NEXT + ": ");
		final Spinner next = (Spinner) HealthGenius.myApp.findViewById(R.id.next);
		ArrayList<String> spinnerArray = new ArrayList<String>();
		final ArrayList<Long> idsArray = new ArrayList<Long>();
		Enumeration<Question> questions = HealthGenius.myQuestControlManager.activeQuestions.elements();
	    spinnerArray.add("-");
	    idsArray.add(null);
	    while (questions.hasMoreElements()) {
	    	Question questToAdd = questions.nextElement();
	    	if (question.getId() == questToAdd.getId()) continue;
	    	if (question.getName().equals("")) continue;
	    	i++;
	    	if (question.getNext() != null)
	    		if (questToAdd.getId() == question.getNext()) selectedByFirst = i;
	    	spinnerArray.add(questToAdd.getName());
	    	idsArray.add(questToAdd.getId());
	    }
	    ArrayAdapter<String> adapter = new ArrayAdapter<String>(HealthGenius.myApp, android.R.layout.simple_spinner_item, spinnerArray);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		next.setAdapter(adapter);
		next.setSelection(selectedByFirst);
	    if (question instanceof MultiQuestion) {
			((RelativeLayout) HealthGenius.myApp.findViewById(R.id.optionallayout)).setVisibility(View.VISIBLE);
			((TextView) HealthGenius.myApp.findViewById(R.id.optionalrequest)).setText(HealthGenius.myLanguageManager.QUEST_OPTIONAL + ": ");
			((CheckBox) HealthGenius.myApp.findViewById(R.id.optional)).setSelected(question.isOptional());
			((CheckBox) HealthGenius.myApp.findViewById(R.id.optional)).setOnCheckedChangeListener(new android.widget.CompoundButton.OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					question.setOptional(isChecked);
				}
			});
		}
		if (question instanceof MultiQuestion) {
			Array<Answer> answers = HealthGenius.myQuestControlManager.activeAnswers.get(question.getId());
			Array<Condition> conditions = HealthGenius.myQuestControlManager.activeConditions.get(question.getId());
			((LinearLayout) HealthGenius.myApp.findViewById(R.id.answers)).setVisibility(View.VISIBLE);
			((LinearLayout) HealthGenius.myApp.findViewById(R.id.conditions)).setVisibility(View.VISIBLE);
			((RelativeLayout) HealthGenius.myApp.findViewById(R.id.answershead)).setVisibility(View.VISIBLE);
			((RelativeLayout) HealthGenius.myApp.findViewById(R.id.conditionhead)).setVisibility(View.VISIBLE);
			((TextView) HealthGenius.myApp.findViewById(R.id.answersrequest)).setText(HealthGenius.myLanguageManager.QUEST_ANSWERS + ": ");
			((TextView) HealthGenius.myApp.findViewById(R.id.conditionrequest)).setText(HealthGenius.myLanguageManager.QUEST_CONDITIONS + ": ");
			Iterator<Answer> ansit = answers.iterator();
			while (ansit.hasNext()) {
				final Answer answer = ansit.next();
				TableRow buttonLayout = new TableRow(HealthGenius.myApp);
				buttonLayout.setOrientation(TableRow.HORIZONTAL);
				buttonLayout.setGravity(Gravity.CENTER_VERTICAL);
				buttonLayout.setLayoutParams(new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
				ImageButton delete = new ImageButton(HealthGenius.myApp);
				delete.setImageResource(R.drawable.delete);
				delete.setScaleType(ScaleType.FIT_XY);
				delete.setBackgroundResource(R.drawable.iconbg);
				delete.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						Runnable run = new Runnable() {
							@Override
							public void run() {
								handler.sendEmptyMessage(0);
								if (HealthGenius.myQuestControlManager.removeAnswer(question.getId(), answer)) {
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
											animationFrame = (ImageView) HealthGenius.myApp.findViewById(R.id.animation);
											animationFrame.setVisibility(View.VISIBLE);
											animationFrame.setBackgroundResource(R.drawable.loading);
											animation = (AnimationDrawable) animationFrame.getBackground();
											animation.start();
											break;
										case 1:
											animationFrame = (ImageView) HealthGenius.myApp.findViewById(R.id.animation);
											animation = (AnimationDrawable) animationFrame.getBackground();
											animation.stop();
											animationFrame.setVisibility(View.GONE);
											myUIManager.UImisc.removeInfoPopup();
											String text = ((EditText) HealthGenius.myApp.findViewById(R.id.text)).getText().toString();
											int position = ((Spinner) HealthGenius.myApp.findViewById(R.id.next)).getSelectedItemPosition();
											boolean checked = ((CheckBox) HealthGenius.myApp.findViewById(R.id.optional)).isChecked();
											modifyQuestion(question, text, position, checked);
											break;
										case 2:
											animationFrame = (ImageView) HealthGenius.myApp.findViewById(R.id.animation);
											animation = (AnimationDrawable) animationFrame.getBackground();
											animation.stop();
											animationFrame.setVisibility(View.GONE);
											myUIManager.UImisc.loadInfoPopup("Remove failed");
											break;
									}
								}
							};
						};
						Thread thread = new Thread(run);
						thread.start();
					}
				});			
				ImageButton modify = new ImageButton(HealthGenius.myApp);
				modify.setImageResource(R.drawable.modify);
				modify.setBackgroundResource(R.drawable.iconbg);
				modify.setScaleType(ScaleType.FIT_XY);
				modify.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						modifyAnswer(question, answer);
					}
				});
				TextView text = new TextView(HealthGenius.myApp);
				text.append(answer.getName());
				text.setWidth(realwidth/2);
				text.setTextSize((float) 20);
				text.setTextColor(Color.BLACK);
				text.setTypeface(Typeface.SANS_SERIF);
				buttonLayout.addView(modify, 40, 40);
				buttonLayout.addView(delete, 40, 40);
				buttonLayout.addView(text);
				((LinearLayout) HealthGenius.myApp.findViewById(R.id.answers)).addView(buttonLayout);
			}
			ImageButton addanswer = (ImageButton) HealthGenius.myApp.findViewById(R.id.addanswer);
			addanswer.setVisibility(View.VISIBLE);
			addanswer.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					createNewAnswer(question);
				}
			});
			Iterator<Condition> condit = conditions.iterator();
			while (condit.hasNext()) {
				final MultiCondition condition = (MultiCondition) condit.next();
				TableRow buttonLayout = new TableRow(HealthGenius.myApp);
				buttonLayout.setOrientation(TableRow.HORIZONTAL);
				buttonLayout.setGravity(Gravity.CENTER_VERTICAL);
				buttonLayout.setLayoutParams(new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
				ImageButton delete = new ImageButton(HealthGenius.myApp);
				delete.setImageResource(R.drawable.delete);
				delete.setScaleType(ScaleType.FIT_XY);
				delete.setBackgroundResource(R.drawable.iconbg);
				delete.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						Runnable run = new Runnable() {
							@Override
							public void run() {
								handler.sendEmptyMessage(0);
								if (HealthGenius.myQuestControlManager.removeCondition(question.getId(), condition)) {
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
											animationFrame = (ImageView) HealthGenius.myApp.findViewById(R.id.animation);
											animationFrame.setVisibility(View.VISIBLE);
											animationFrame.setBackgroundResource(R.drawable.loading);
											animation = (AnimationDrawable) animationFrame.getBackground();
											animation.start();
											break;
										case 1:
											animationFrame = (ImageView) HealthGenius.myApp.findViewById(R.id.animation);
											animation = (AnimationDrawable) animationFrame.getBackground();
											animation.stop();
											animationFrame.setVisibility(View.GONE);
											myUIManager.UImisc.removeInfoPopup();
											String text = ((EditText) HealthGenius.myApp.findViewById(R.id.text)).getText().toString();
											int position = ((Spinner) HealthGenius.myApp.findViewById(R.id.next)).getSelectedItemPosition();
											boolean checked = ((CheckBox) HealthGenius.myApp.findViewById(R.id.optional)).isChecked();
											modifyQuestion(question, text, position, checked);
											break;
										case 2:
											animationFrame = (ImageView) HealthGenius.myApp.findViewById(R.id.animation);
											animation = (AnimationDrawable) animationFrame.getBackground();
											animation.stop();
											animationFrame.setVisibility(View.GONE);
											myUIManager.UImisc.loadInfoPopup("Remove failed");
											break;
									}
								}
							};
						};
						Thread thread = new Thread(run);
						thread.start();
					}
				});			
				ImageButton modify = new ImageButton(HealthGenius.myApp);
				modify.setImageResource(R.drawable.modify);
				modify.setBackgroundResource(R.drawable.iconbg);
				modify.setScaleType(ScaleType.FIT_XY);
				modify.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						modifyMultiCondition(question, condition);
					}
				});
				TextView text = new TextView(HealthGenius.myApp);
				if (condition.getNext() == null) text.append("Finish");
				else text.append("Jump to " + HealthGenius.myQuestControlManager.activeQuestions.get(condition.getNext()).getName());
				text.setWidth(realwidth/2);
				text.setTextSize((float) 20);
				text.setTextColor(Color.BLACK);
				text.setTypeface(Typeface.SANS_SERIF);
				buttonLayout.addView(modify, 40, 40);
				buttonLayout.addView(delete, 40, 40);
				buttonLayout.addView(text);
				((LinearLayout) HealthGenius.myApp.findViewById(R.id.conditions)).addView(buttonLayout);
			}
			ImageButton addcondition = (ImageButton) HealthGenius.myApp.findViewById(R.id.addcondition);
			addcondition.setVisibility(View.VISIBLE);
			addcondition.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					createNewMultiCondition(question);
				}
			});
		}
		else if (question instanceof NumericQuestion) {
			Array<Condition> conditions = HealthGenius.myQuestControlManager.activeConditions.get(question.getId());
			if (conditions == null) conditions = new Array<Condition>();
			((LinearLayout) HealthGenius.myApp.findViewById(R.id.conditions)).setVisibility(View.VISIBLE);
			((RelativeLayout) HealthGenius.myApp.findViewById(R.id.conditionhead)).setVisibility(View.VISIBLE);
			((TextView) HealthGenius.myApp.findViewById(R.id.conditionrequest)).setText(HealthGenius.myLanguageManager.QUEST_CONDITIONS + ": ");
			Iterator<Condition> condit = conditions.iterator();
			while (condit.hasNext()) {
				final NumericCondition condition = (NumericCondition) condit.next();
				TableRow buttonLayout = new TableRow(HealthGenius.myApp);
				buttonLayout.setOrientation(TableRow.HORIZONTAL);
				buttonLayout.setGravity(Gravity.CENTER_VERTICAL);
				buttonLayout.setLayoutParams(new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
				ImageButton delete = new ImageButton(HealthGenius.myApp);
				delete.setImageResource(R.drawable.delete);
				delete.setScaleType(ScaleType.FIT_XY);
				delete.setBackgroundResource(R.drawable.iconbg);
				delete.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						Runnable run = new Runnable() {
							@Override
							public void run() {
								handler.sendEmptyMessage(0);
								if (HealthGenius.myQuestControlManager.removeCondition(question.getId(), condition)) {
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
											animationFrame = (ImageView) HealthGenius.myApp.findViewById(R.id.animation);
											animationFrame.setVisibility(View.VISIBLE);
											animationFrame.setBackgroundResource(R.drawable.loading);
											animation = (AnimationDrawable) animationFrame.getBackground();
											animation.start();
											break;
										case 1:
											animationFrame = (ImageView) HealthGenius.myApp.findViewById(R.id.animation);
											animation = (AnimationDrawable) animationFrame.getBackground();
											animation.stop();
											animationFrame.setVisibility(View.GONE);
											myUIManager.UImisc.removeInfoPopup();
											String text = ((EditText) HealthGenius.myApp.findViewById(R.id.text)).getText().toString();
											int position = ((Spinner) HealthGenius.myApp.findViewById(R.id.next)).getSelectedItemPosition();
											boolean checked = ((CheckBox) HealthGenius.myApp.findViewById(R.id.optional)).isChecked();
											modifyQuestion(question, text, position, checked);
											break;
										case 2:
											animationFrame = (ImageView) HealthGenius.myApp.findViewById(R.id.animation);
											animation = (AnimationDrawable) animationFrame.getBackground();
											animation.stop();
											animationFrame.setVisibility(View.GONE);
											myUIManager.UImisc.loadInfoPopup("Remove failed");
											break;
									}
								}
							};
						};
						Thread thread = new Thread(run);
						thread.start();
					}
				});			
				ImageButton modify = new ImageButton(HealthGenius.myApp);
				modify.setImageResource(R.drawable.modify);
				modify.setBackgroundResource(R.drawable.iconbg);
				modify.setScaleType(ScaleType.FIT_XY);
				modify.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						modifyNumericCondition(question, condition);
					}
				});
				TextView text = new TextView(HealthGenius.myApp);
				if (condition.getNext() == null) text.append("Finish");
				else text.append("Jump to " + HealthGenius.myQuestControlManager.activeQuestions.get(condition.getNext()).getName());
				text.setWidth(realwidth/2);
				text.setTextSize((float) 20);
				text.setTextColor(Color.BLACK);
				text.setTypeface(Typeface.SANS_SERIF);
				buttonLayout.addView(modify, 40, 40);
				buttonLayout.addView(delete, 40, 40);
				buttonLayout.addView(text);
				((LinearLayout) HealthGenius.myApp.findViewById(R.id.conditions)).addView(buttonLayout);
			}
			ImageButton addcondition = (ImageButton) HealthGenius.myApp.findViewById(R.id.addcondition);
			addcondition.setVisibility(View.VISIBLE);
			addcondition.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					createNewNumericCondition(question);
				}
			});
		}
		ImageButton save = (ImageButton) HealthGenius.myApp.findViewById(R.id.help);
		save.setVisibility(View.VISIBLE);
		save.setImageResource(R.drawable.save);
		save.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Runnable run = new Runnable() {
					@Override
					public void run() {
						handler.sendEmptyMessage(0);
						question.setName(((EditText) HealthGenius.myApp.findViewById(R.id.text)).getText().toString());
						int position = next.getSelectedItemPosition();
						question.setNext(idsArray.get(position));
						question.setOptional(((CheckBox) HealthGenius.myApp.findViewById(R.id.optional)).isSelected());
						if (HealthGenius.myQuestControlManager.updateQuestion(question)) {
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
									animationFrame = (ImageView) HealthGenius.myApp.findViewById(R.id.animation);
									animationFrame.setVisibility(View.VISIBLE);
									animationFrame.setBackgroundResource(R.drawable.loading);
									animation = (AnimationDrawable) animationFrame.getBackground();
									animation.start();
									break;
								case 1:
									animationFrame = (ImageView) HealthGenius.myApp.findViewById(R.id.animation);
									animation = (AnimationDrawable) animationFrame.getBackground();
									animation.stop();
									animationFrame.setVisibility(View.GONE);
									myUIManager.UImisc.removeInfoPopup();
									modifyQuestionnaire(HealthGenius.myQuestControlManager.activeQuestionnaire);
									break;
								case 2:
									animationFrame = (ImageView) HealthGenius.myApp.findViewById(R.id.animation);
									animation = (AnimationDrawable) animationFrame.getBackground();
									animation.stop();
									animationFrame.setVisibility(View.GONE);
									modifyQuestionnaire(HealthGenius.myQuestControlManager.activeQuestionnaire);
									myUIManager.UImisc.loadInfoPopup("Update failed");
									break;
							}
						}
					};
				};
				Thread thread = new Thread(run);
				thread.start();
			}
		});
		ImageButton exit = (ImageButton) HealthGenius.myApp.findViewById(R.id.previous);
		exit.setVisibility(View.VISIBLE);
		exit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				modifyQuestionnaire(HealthGenius.myQuestControlManager.activeQuestionnaire);
			}
		});
	}
	
	public void createNewAnswer(final Question question) {
		LayoutInflater inflater = (LayoutInflater) HealthGenius.myApp.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		final View layout = inflater.inflate(R.layout.toastaddanswer,
		                               (ViewGroup) HealthGenius.myApp.findViewById(R.id.toastaddanswerroot));
		TextView title = (TextView) layout.findViewById(R.id.addtitle);
		title.setText(HealthGenius.myLanguageManager.QUEST_CREATE_ANSWER);
		TextView namereq = (TextView) layout.findViewById(R.id.namerequested);
		namereq.setText(HealthGenius.myLanguageManager.QUEST_TEXT + ": ");
		TextView valuereq = (TextView) layout.findViewById(R.id.valuerequest);
		valuereq.setText(HealthGenius.myLanguageManager.QUEST_WEIGHT + ": ");
		ImageButton image = (ImageButton) layout.findViewById(R.id.addeventok);
		((EditText) layout.findViewById(R.id.value)).setText("0.0");
		Builder builder = new AlertDialog.Builder(HealthGenius.myApp);
		builder.setView(layout);
		final AlertDialog alertDialog = builder.create();
		alertDialog.show();
		image.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				final Answer answer = new Answer();
				answer.setName(((EditText) layout.findViewById(R.id.name)).getText().toString());
				try {
					double value = Double.parseDouble(((EditText) layout.findViewById(R.id.value)).getText().toString());
					answer.setValue(value);
				} catch (Exception e) {
					((EditText) layout.findViewById(R.id.value)).setText("");
					Toast toast = Toast.makeText(HealthGenius.myApp, "The value must be a number", 3000);
					toast.show();
				}
				Runnable run = new Runnable() {
					@Override
					public void run() {
						handler.sendEmptyMessage(0);
						if (HealthGenius.myQuestControlManager.addAnswer(question.getId(), answer)) {
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
									animationFrame = (ImageView) HealthGenius.myApp.findViewById(R.id.animation);
									animationFrame.setVisibility(View.VISIBLE);
									animationFrame.setBackgroundResource(R.drawable.loading);
									animation = (AnimationDrawable) animationFrame.getBackground();
									animation.start();
									break;
								case 1:
									animationFrame = (ImageView) HealthGenius.myApp.findViewById(R.id.animation);
									animation = (AnimationDrawable) animationFrame.getBackground();
									animation.stop();
									animationFrame.setVisibility(View.GONE);
									myUIManager.UImisc.removeInfoPopup();
									String text = ((EditText) HealthGenius.myApp.findViewById(R.id.text)).getText().toString();
									int position = ((Spinner) HealthGenius.myApp.findViewById(R.id.next)).getSelectedItemPosition();
									boolean checked = ((CheckBox) HealthGenius.myApp.findViewById(R.id.optional)).isChecked();
									modifyQuestion(question, text, position, checked);
									break;
								case 2:
									animationFrame = (ImageView) HealthGenius.myApp.findViewById(R.id.animation);
									animation = (AnimationDrawable) animationFrame.getBackground();
									animation.stop();
									animationFrame.setVisibility(View.GONE);
									myUIManager.UImisc.loadInfoPopup("Add failed");
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
	
	public void modifyAnswer(final Question question, final Answer answer) {
		LayoutInflater inflater = (LayoutInflater) HealthGenius.myApp.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		final View layout = inflater.inflate(R.layout.toastaddanswer,
		                               (ViewGroup) HealthGenius.myApp.findViewById(R.id.toastaddanswerroot));
		TextView title = (TextView) layout.findViewById(R.id.addtitle);
		title.setText(HealthGenius.myLanguageManager.QUEST_CREATE_ANSWER);
		TextView namereq = (TextView) layout.findViewById(R.id.namerequested);
		namereq.setText(HealthGenius.myLanguageManager.QUEST_TEXT + ": ");
		((EditText) layout.findViewById(R.id.name)).setText(answer.getName());
		TextView valuereq = (TextView) layout.findViewById(R.id.valuerequest);
		valuereq.setText(HealthGenius.myLanguageManager.QUEST_WEIGHT + ": ");
		((EditText) layout.findViewById(R.id.value)).setText("" + answer.getValue());
		ImageButton image = (ImageButton) layout.findViewById(R.id.addeventok);
		Builder builder = new AlertDialog.Builder(HealthGenius.myApp);
		builder.setView(layout);
		final AlertDialog alertDialog = builder.create();
		alertDialog.show();
		image.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				answer.setName(((EditText) layout.findViewById(R.id.name)).getText().toString());
				try {
					double value = Double.parseDouble(((EditText) layout.findViewById(R.id.value)).getText().toString());
					answer.setValue(value);
				} catch (Exception e) {
					((EditText) layout.findViewById(R.id.value)).setText("");
					Toast toast = Toast.makeText(HealthGenius.myApp, "The weight must be a number. Now set to 0.0", 3000);
					toast.show();
				}
				Runnable run = new Runnable() {
					@Override
					public void run() {
						handler.sendEmptyMessage(0);
						if (HealthGenius.myQuestControlManager.updateAnswer(question.getId(), answer)) {
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
									animationFrame = (ImageView) HealthGenius.myApp.findViewById(R.id.animation);
									animationFrame.setVisibility(View.VISIBLE);
									animationFrame.setBackgroundResource(R.drawable.loading);
									animation = (AnimationDrawable) animationFrame.getBackground();
									animation.start();
									break;
								case 1:
									animationFrame = (ImageView) HealthGenius.myApp.findViewById(R.id.animation);
									animation = (AnimationDrawable) animationFrame.getBackground();
									animation.stop();
									animationFrame.setVisibility(View.GONE);
									myUIManager.UImisc.removeInfoPopup();
									String text = ((EditText) HealthGenius.myApp.findViewById(R.id.text)).getText().toString();
									int position = ((Spinner) HealthGenius.myApp.findViewById(R.id.next)).getSelectedItemPosition();
									boolean checked = ((CheckBox) HealthGenius.myApp.findViewById(R.id.optional)).isChecked();
									modifyQuestion(question, text, position, checked);
									break;
								case 2:
									animationFrame = (ImageView) HealthGenius.myApp.findViewById(R.id.animation);
									animation = (AnimationDrawable) animationFrame.getBackground();
									animation.stop();
									animationFrame.setVisibility(View.GONE);
									myUIManager.UImisc.loadInfoPopup("Updating failed");
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
	
	public void createNewMultiCondition(final Question question) {
		int selectedByFirst = 0, i = 0;
		final MultiCondition condition = new MultiCondition();
		final List<Answer> answersToAdd = new ArrayList<Answer>();
		LayoutInflater inflater = (LayoutInflater) HealthGenius.myApp.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		final View layout = inflater.inflate(R.layout.toastaddcondition,
		                               (ViewGroup) HealthGenius.myApp.findViewById(R.id.toastaddconditionroot));
		((TextView) layout.findViewById(R.id.addtitle)).setText(HealthGenius.myLanguageManager.QUEST_CREATE_CONDITION);
		((TextView) layout.findViewById(R.id.nextrequest)).setText(HealthGenius.myLanguageManager.QUEST_NEXT + ": ");
		((TextView) layout.findViewById(R.id.answersrequest)).setText(HealthGenius.myLanguageManager.QUEST_ANSWERS + ": ");
		final Spinner next = (Spinner)layout.findViewById(R.id.nextquest);
		ArrayList<String> spinnerArray = new ArrayList<String>();
		Enumeration<Question> questions = HealthGenius.myQuestControlManager.activeQuestions.elements();
	    spinnerArray.add("-");
	    while (questions.hasMoreElements()) {
	    	i++;
	    	Question questToAdd = questions.nextElement();
	    	if (question.getId() == questToAdd.getId()) continue;
	    	if (question.getName().equals("")) continue;
	    	spinnerArray.add(questToAdd.getName());
	    }
	    ArrayAdapter<String> adapter = new ArrayAdapter<String>(HealthGenius.myApp, android.R.layout.simple_spinner_item, spinnerArray);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		next.setAdapter(adapter);
		next.setSelection(selectedByFirst);
	    final LinearLayout answerList = (LinearLayout) layout.findViewById(R.id.answers);
	    Iterator<Answer> answers = HealthGenius.myQuestControlManager.activeAnswers.get(question.getId()).iterator();
	    while (answers.hasNext()) {
			final Answer answer = answers.next();
			CheckBox checkbox = new CheckBox(HealthGenius.myApp);
			checkbox.setText(answer.getName());
			checkbox.setOnCheckedChangeListener(new android.widget.CompoundButton.OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					if (isChecked) answersToAdd.add(answer);
					else answersToAdd.remove(answer);
				}
			});
			answerList.addView(checkbox);
		} 
		ImageButton image = (ImageButton) layout.findViewById(R.id.addeventok);
		Builder builder = new AlertDialog.Builder(HealthGenius.myApp);
		builder.setView(layout);
		final AlertDialog alertDialog = builder.create();
		alertDialog.show();
		image.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				condition.getAnswers().clear();
				condition.getAnswers().addAll(answersToAdd);
				int position = next.getSelectedItemPosition();
				condition.setNext(HealthGenius.myQuestControlManager.searchQuestionByName(next.getItemAtPosition(position).toString()));
				Runnable run = new Runnable() {
					@Override
					public void run() {
						handler.sendEmptyMessage(0);
						if (HealthGenius.myQuestControlManager.addCondition(question.getId(), condition)) {
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
									animationFrame = (ImageView) HealthGenius.myApp.findViewById(R.id.animation);
									animationFrame.setVisibility(View.VISIBLE);
									animationFrame.setBackgroundResource(R.drawable.loading);
									animation = (AnimationDrawable) animationFrame.getBackground();
									animation.start();
									break;
								case 1:
									animationFrame = (ImageView) HealthGenius.myApp.findViewById(R.id.animation);
									animation = (AnimationDrawable) animationFrame.getBackground();
									animation.stop();
									animationFrame.setVisibility(View.GONE);
									myUIManager.UImisc.removeInfoPopup();
									String text = ((EditText) HealthGenius.myApp.findViewById(R.id.text)).getText().toString();
									int position = ((Spinner) HealthGenius.myApp.findViewById(R.id.next)).getSelectedItemPosition();
									boolean checked = ((CheckBox) HealthGenius.myApp.findViewById(R.id.optional)).isChecked();
									modifyQuestion(question, text, position, checked);
									break;
								case 2:
									animationFrame = (ImageView) HealthGenius.myApp.findViewById(R.id.animation);
									animation = (AnimationDrawable) animationFrame.getBackground();
									animation.stop();
									animationFrame.setVisibility(View.GONE);
									myUIManager.UImisc.loadInfoPopup("Add failed");
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
	
	public void modifyMultiCondition(final Question question, final MultiCondition condition) {
		int selectedByFirst = 0, i = 0;
		final List<Answer> answersToAdd = new ArrayList<Answer>();
		LayoutInflater inflater = (LayoutInflater) HealthGenius.myApp.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		final View layout = inflater.inflate(R.layout.toastaddcondition,
		                               (ViewGroup) HealthGenius.myApp.findViewById(R.id.toastaddconditionroot));
		((TextView) layout.findViewById(R.id.addtitle)).setText(HealthGenius.myLanguageManager.QUEST_CREATE_CONDITION);
		((TextView) layout.findViewById(R.id.nextrequest)).setText(HealthGenius.myLanguageManager.QUEST_NEXT + ": ");
		((TextView) layout.findViewById(R.id.answersrequest)).setText(HealthGenius.myLanguageManager.QUEST_ANSWERS + ": ");
		final Spinner next = (Spinner) layout.findViewById(R.id.nextquest);
		ArrayList<String> spinnerArray = new ArrayList<String>();
		Enumeration<Question> questions = HealthGenius.myQuestControlManager.activeQuestions.elements();
	    spinnerArray.add("-");
	    while (questions.hasMoreElements()) {
	    	i++;
	    	Question questToAdd = questions.nextElement();
	    	if (condition.getNext() != null)
	    		if (questToAdd.getId().equals(condition.getNext())) selectedByFirst = i;
	    	if (question.getId() == questToAdd.getId()) continue;
	    	if (question.getName().equals("")) continue;
	    	spinnerArray.add(questToAdd.getName());
	    }
	    ArrayAdapter<String> adapter = new ArrayAdapter<String>(HealthGenius.myApp, android.R.layout.simple_spinner_item, spinnerArray);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		next.setAdapter(adapter);
		next.setSelection(selectedByFirst);
	    final LinearLayout answerList = (LinearLayout) layout.findViewById(R.id.answers);
	    Iterator<Answer> answers = HealthGenius.myQuestControlManager.activeAnswers.get(question.getId()).iterator();
	    while (answers.hasNext()) {
			final Answer answer = answers.next();
			CheckBox checkbox = new CheckBox(HealthGenius.myApp);
			checkbox.setText(answer.getName());
			for (Answer ans : condition.getAnswers()) {
				if (ans.getId().equals(answer.getId())) {
					checkbox.setChecked(true);
					answersToAdd.add(answer);
				}
			}
			checkbox.setOnCheckedChangeListener(new android.widget.CompoundButton.OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					if (isChecked) answersToAdd.add(answer);
					else answersToAdd.remove(answer);
				}
			});
			answerList.addView(checkbox);
		} 
		ImageButton image = (ImageButton) layout.findViewById(R.id.addeventok);
		Builder builder = new AlertDialog.Builder(HealthGenius.myApp);
		builder.setView(layout);
		final AlertDialog alertDialog = builder.create();
		alertDialog.show();
		image.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				condition.getAnswers().clear();
				condition.getAnswers().addAll(answersToAdd);
				int position = next.getSelectedItemPosition();
				condition.setNext(HealthGenius.myQuestControlManager.searchQuestionByName(next.getItemAtPosition(position).toString()));
				Runnable run = new Runnable() {
					@Override
					public void run() {
						handler.sendEmptyMessage(0);
						if (HealthGenius.myQuestControlManager.updateCondition(question.getId(), condition)) {
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
									animationFrame = (ImageView) HealthGenius.myApp.findViewById(R.id.animation);
									animationFrame.setVisibility(View.VISIBLE);
									animationFrame.setBackgroundResource(R.drawable.loading);
									animation = (AnimationDrawable) animationFrame.getBackground();
									animation.start();
									break;
								case 1:
									animationFrame = (ImageView) HealthGenius.myApp.findViewById(R.id.animation);
									animation = (AnimationDrawable) animationFrame.getBackground();
									animation.stop();
									animationFrame.setVisibility(View.GONE);
									myUIManager.UImisc.removeInfoPopup();
									String text = ((EditText) HealthGenius.myApp.findViewById(R.id.text)).getText().toString();
									int position = ((Spinner) HealthGenius.myApp.findViewById(R.id.next)).getSelectedItemPosition();
									boolean checked = ((CheckBox) HealthGenius.myApp.findViewById(R.id.optional)).isChecked();
									modifyQuestion(question, text, position, checked);
									break;
								case 2:
									animationFrame = (ImageView) HealthGenius.myApp.findViewById(R.id.animation);
									animation = (AnimationDrawable) animationFrame.getBackground();
									animation.stop();
									animationFrame.setVisibility(View.GONE);
									myUIManager.UImisc.loadInfoPopup("Add failed");
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
	
	public void createNewNumericCondition(final Question question) {
		int selectedByFirst = 0, i = 0;
		final NumericCondition condition = new NumericCondition();
		LayoutInflater inflater = (LayoutInflater) HealthGenius.myApp.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		final View layout = inflater.inflate(R.layout.toastaddnumericcondition,
		                               (ViewGroup) HealthGenius.myApp.findViewById(R.id.toastaddnumericconditionroot));
		((TextView) layout.findViewById(R.id.addtitle)).setText(HealthGenius.myLanguageManager.QUEST_CREATE_CONDITION);
		((TextView) layout.findViewById(R.id.nextrequest)).setText(HealthGenius.myLanguageManager.QUEST_NEXT + ": ");
		((TextView) layout.findViewById(R.id.limitrequest)).setText(HealthGenius.myLanguageManager.QUEST_THREESHOLD + ": ");
		final Spinner next = (Spinner) layout.findViewById(R.id.nextquest);
		ArrayList<String> spinnerArray = new ArrayList<String>();
		Enumeration<Question> questions = HealthGenius.myQuestControlManager.activeQuestions.elements();
	    spinnerArray.add("-");
	    while (questions.hasMoreElements()) {
	    	i++;
	    	Question questToAdd = questions.nextElement();
	    	if (question.getId() == questToAdd.getId()) continue;
	    	if (question.getName().equals("")) continue;
	    	spinnerArray.add(questToAdd.getName());
	    }
	    ArrayAdapter<String> adapter = new ArrayAdapter<String>(HealthGenius.myApp, android.R.layout.simple_spinner_item, spinnerArray);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		next.setAdapter(adapter);
		next.setSelection(selectedByFirst);
	    final SeekBar threshold = (SeekBar)layout.findViewById(R.id.limitquest);
		ImageButton image = (ImageButton) layout.findViewById(R.id.addeventok);
		Builder builder = new AlertDialog.Builder(HealthGenius.myApp);
		builder.setView(layout);
		final AlertDialog alertDialog = builder.create();
		alertDialog.show();
		image.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				condition.setThreshold(((Integer)threshold.getProgress()).doubleValue()/10);
				int position = next.getSelectedItemPosition();
				condition.setNext(HealthGenius.myQuestControlManager.searchQuestionByName(next.getItemAtPosition(position).toString()));
				Runnable run = new Runnable() {
					@Override
					public void run() {
						handler.sendEmptyMessage(0);
						if (HealthGenius.myQuestControlManager.addCondition(question.getId(), condition)) {
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
									myUIManager.UImisc.loadInfoPopupWithoutExiting("Adding answer ...");
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
									String text = ((EditText) HealthGenius.myApp.findViewById(R.id.text)).getText().toString();
									int position = ((Spinner) HealthGenius.myApp.findViewById(R.id.next)).getSelectedItemPosition();
									boolean checked = ((CheckBox) HealthGenius.myApp.findViewById(R.id.optional)).isChecked();
									modifyQuestion(question, text, position, checked);
									break;
								case 2:
									animationFrame = (ImageView) HealthGenius.myApp.findViewById(R.id.popupImage);
									animation = (AnimationDrawable) animationFrame.getBackground();
									animation.stop();
									animationFrame.setVisibility(View.GONE);
									myUIManager.UImisc.loadInfoPopup("Add failed");
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
	
	public void modifyNumericCondition(final Question question, final NumericCondition condition) {
		int selectedByFirst = 0, i = 0;
		LayoutInflater inflater = (LayoutInflater) HealthGenius.myApp.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		final View layout = inflater.inflate(R.layout.toastaddnumericcondition,
		                               (ViewGroup) HealthGenius.myApp.findViewById(R.id.toastaddnumericconditionroot));
		((TextView) layout.findViewById(R.id.addtitle)).setText(HealthGenius.myLanguageManager.QUEST_CREATE_CONDITION);
		((TextView) layout.findViewById(R.id.nextrequest)).setText(HealthGenius.myLanguageManager.QUEST_NEXT + ": ");
		((TextView) layout.findViewById(R.id.limitrequest)).setText(HealthGenius.myLanguageManager.QUEST_THREESHOLD + ": ");
		final Spinner next = (Spinner) layout.findViewById(R.id.nextquest);
		ArrayList<String> spinnerArray = new ArrayList<String>();
		Enumeration<Question> questions = HealthGenius.myQuestControlManager.activeQuestions.elements();
	    spinnerArray.add("-");
	    while (questions.hasMoreElements()) {
	    	i++;
	    	Question questToAdd = questions.nextElement();
	    	if (question.getNext() != null)
	    		if (questToAdd.getId().equals(question.getNext())) selectedByFirst = i;
	    	if (question.getId() == questToAdd.getId()) continue;
	    	if (question.getName().equals("")) continue;
	    	spinnerArray.add(questToAdd.getName());
	    }
	    ArrayAdapter<String> adapter = new ArrayAdapter<String>(HealthGenius.myApp, android.R.layout.simple_spinner_item, spinnerArray);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		next.setAdapter(adapter);
		next.setSelection(selectedByFirst);
	    final SeekBar threshold = (SeekBar)layout.findViewById(R.id.limitquest);
	    threshold.setProgress((int)(condition.getThreshold()*10));
		ImageButton image = (ImageButton) layout.findViewById(R.id.addeventok);
		Builder builder = new AlertDialog.Builder(HealthGenius.myApp);
		builder.setView(layout);
		final AlertDialog alertDialog = builder.create();
		alertDialog.show();
		image.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				condition.setThreshold(((Integer)threshold.getProgress()).doubleValue()/10);
				int position = next.getSelectedItemPosition();
				condition.setNext(HealthGenius.myQuestControlManager.searchQuestionByName(next.getItemAtPosition(position).toString()));
				Runnable run = new Runnable() {
					@Override
					public void run() {
						handler.sendEmptyMessage(0);
						if (HealthGenius.myQuestControlManager.addCondition(question.getId(), condition)) {
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
									myUIManager.UImisc.loadInfoPopupWithoutExiting("Adding answer ...");
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
									String text = ((EditText) HealthGenius.myApp.findViewById(R.id.text)).getText().toString();
									int position = ((Spinner) HealthGenius.myApp.findViewById(R.id.next)).getSelectedItemPosition();
									boolean checked = ((CheckBox) HealthGenius.myApp.findViewById(R.id.optional)).isChecked();
									modifyQuestion(question, text, position, checked);
									break;
								case 2:
									animationFrame = (ImageView) HealthGenius.myApp.findViewById(R.id.popupImage);
									animation = (AnimationDrawable) animationFrame.getBackground();
									animation.stop();
									animationFrame.setVisibility(View.GONE);
									myUIManager.UImisc.loadInfoPopup("Add failed");
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

}
