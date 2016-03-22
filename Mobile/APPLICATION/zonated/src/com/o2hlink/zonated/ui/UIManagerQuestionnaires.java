package com.o2hlink.zonated.ui;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.Spanned;
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
import com.o2hlink.activ8.client.entity.QuestionAnswer;
import com.o2hlink.activ8.client.entity.Questionnaire;
import com.o2hlink.activ8.client.entity.QuestionnaireSample;
import com.o2hlink.activ8.client.entity.TextAnswer;
import com.o2hlink.activ8.client.entity.TextQuestion;
import com.o2hlink.zonated.R;
import com.o2hlink.zonated.Zonated;
import com.o2hlink.zonated.background.InitialConnection;
import com.o2hlink.zonated.background.RefreshingConnection;
import com.o2hlink.zonated.background.SendQuestionnaire;
import com.o2hlink.zonated.data.questionnaire.QuestGlobalResult;
import com.o2hlink.zonated.data.questionnaire.QuestionResult;
import com.o2hlink.zonated.exceptions.NotUpdatedException;

public class UIManagerQuestionnaires {
	
	public UIManager myUIManager;
	
	public UIManagerQuestionnaires(UIManager ui) {
		myUIManager = ui;
	}

	public void loadSharedQuestionnaires() {
		loadSharedQuestionnaires(false);
	}
	
	public void loadSharedQuestionnaires(boolean login) {
		myUIManager.state = UIManager.UI_STATE_TOTALQUESTIONNAIRE;
		int realwidth = Zonated.myApp.getResources().getDisplayMetrics().widthPixels;
		Zonated.myApp.setContentView(R.layout.list);
		((TextView) Zonated.myApp.findViewById(R.id.startText)).setText(Zonated.myLanguageManager.QUEST_ASSIGNED_TITLE);
		TableLayout questlisting = (TableLayout)Zonated.myApp.findViewById(R.id.listing);
		Enumeration<Questionnaire> enumer = Zonated.myQuestControlManager.questionnaires.elements();
		while (enumer.hasMoreElements()) {			
			final Questionnaire quest = enumer.nextElement();
			if (Zonated.myQuestControlManager.createdQuest.containsKey(quest.getId())) continue;
			TableRow buttonLayout = new TableRow(Zonated.myApp);
			buttonLayout.setOrientation(TableRow.HORIZONTAL);
			buttonLayout.setGravity(Gravity.CENTER_VERTICAL);
			buttonLayout.setLayoutParams(new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));			
			ImageButton button = new ImageButton(Zonated.myApp);
			button.setBackgroundResource(R.drawable.iconbg);
			button.setImageResource(R.drawable.quest);
			OnClickListener listener = new OnClickListener() {
				@Override
				public void onClick(View v) {
					Zonated.myQuestControlManager.activeQuestionnaire = quest;
					myUIManager.UIquest.startQuestionnaire(quest);
				}
			};
			TextView text = new TextView(Zonated.myApp);
			text.append(quest.getName());
			text.setWidth(realwidth/2);
			text.setTextSize((float) 20);
			text.setTextColor(Color.BLACK);
			text.setTypeface(Typeface.SANS_SERIF);
			ImageButton share = new ImageButton(Zonated.myApp);
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
			if (quest.getId() != Zonated.myLanguageManager.feedbackQuestId) buttonLayout.addView(share, 50, 50);
			button.setOnClickListener(listener);
			text.setOnClickListener(listener);
			questlisting.addView(buttonLayout);
		}
		Enumeration<Questionnaire> enumer2 = Zonated.myQuestControlManager.createdQuest.elements();
		while (enumer2.hasMoreElements()) {			
			final Questionnaire quest = enumer2.nextElement();
			TableRow buttonLayout = new TableRow(Zonated.myApp);
			buttonLayout.setOrientation(TableRow.HORIZONTAL);
			buttonLayout.setGravity(Gravity.CENTER_VERTICAL);
			buttonLayout.setLayoutParams(new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));			
			ImageButton button = new ImageButton(Zonated.myApp);
			button.setBackgroundResource(R.drawable.iconbg);
			button.setImageResource(R.drawable.quest);
			OnClickListener listener = new OnClickListener() {
				@Override
				public void onClick(View v) {
					Zonated.myQuestControlManager.activeQuestionnaire = quest;
					startQuestionnaire(quest);
				}
			};
			TextView text = new TextView(Zonated.myApp);
			text.append(quest.getName());
			text.setWidth(realwidth/2);
			text.setTextSize((float) 20);
			text.setTextColor(Color.BLACK);
			text.setTypeface(Typeface.SANS_SERIF);
			ImageButton share = new ImageButton(Zonated.myApp);
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
		ImageButton refresh = (ImageButton) Zonated.myApp.findViewById(R.id.previous);
		refresh.setImageResource(R.drawable.refreshing);
		refresh.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				v.setVisibility(View.GONE);
				Zonated.myUIManager.state = UIManager.UI_STATE_LOADING;
				final RelativeLayout popupView = (RelativeLayout) Zonated.myApp.findViewById(R.id.popupView);
				popupView.setVisibility(View.VISIBLE);
				RefreshingConnection refreshing = new RefreshingConnection();
				Thread thread = new Thread(refreshing, "REFRESH");
				thread.start();
			}
		});
		ImageButton change = (ImageButton) Zonated.myApp.findViewById(R.id.help);
		change.setImageResource(R.drawable.next);
		change.setVisibility(View.VISIBLE);
		change.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				loadAssignedQuestionnaires();			
			}
		});
		ImageView addquest = (ImageView) Zonated.myApp.findViewById(R.id.animation);
		addquest.setImageResource(R.drawable.searchcontact);
		addquest.setVisibility(View.VISIBLE);
		addquest.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				searchQuestionnaire();			
			}
		});
		if (login) {
			if (Zonated.myMobileManager.identified) {
				myUIManager.state = UIManager.UI_STATE_LOADING;
				((ImageButton)Zonated.myApp.findViewById(R.id.previous)).setVisibility(View.GONE);
				final RelativeLayout popupView = (RelativeLayout) Zonated.myApp.findViewById(R.id.popupView);
				popupView.setVisibility(View.VISIBLE);
				InitialConnection initial = new InitialConnection();
				Thread thread = new Thread(initial, "LOGIN");
				thread.start();
			}
			else{
				myUIManager.UImisc.loadInfoPopup(Zonated.myLanguageManager.USER_NOID);
				((ImageButton)Zonated.myApp.findViewById(R.id.previous)).setVisibility(View.VISIBLE);
			}
			try {
				((TextView)Zonated.myApp.findViewById(R.id.popupPromotionText)).setVisibility(View.VISIBLE);
				((TextView)Zonated.myApp.findViewById(R.id.popupPromotionText)).setText(Zonated.myMobileManager.promotionString);
				((ImageView)Zonated.myApp.findViewById(R.id.popupPromotion)).setVisibility(View.VISIBLE);
				((ImageView)Zonated.myApp.findViewById(R.id.popupPromotion)).setImageDrawable(Drawable.createFromStream(new FileInputStream(Zonated.myMobileManager.promotionMicro), "src"));
				((ImageView)Zonated.myApp.findViewById(R.id.popupPromotion)).setOnClickListener(new OnClickListener() {						
					@Override
					public void onClick(View v) {
						if (Zonated.myMobileManager.promotionUrl != null) {
							Intent in = new Intent(Intent.ACTION_VIEW);
							in.setData(Uri.parse(Zonated.myMobileManager.promotionUrl));
							try {
								Zonated.myApp.startActivity(in);
							} catch (Exception e) {}
						}
					}
				});
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void loadQuestionnairesResults() {
		myUIManager.state = UIManager.UI_STATE_TOTALQUESTIONNAIRE;
		int realwidth = Zonated.myApp.getResources().getDisplayMetrics().widthPixels;
		Zonated.myApp.setContentView(R.layout.list);
		((TextView) Zonated.myApp.findViewById(R.id.startText)).setText(Zonated.myLanguageManager.QUEST_ASSIGNED_TITLE);
		((TextView) Zonated.myApp.findViewById(R.id.startText)).setText(Zonated.myLanguageManager.CALENDAR_MEASUREMENT_RESULT);
		TableLayout questlisting = (TableLayout)Zonated.myApp.findViewById(R.id.listing);
		Enumeration<Questionnaire> enumer = Zonated.myQuestControlManager.questionnaires.elements();
		while (enumer.hasMoreElements()) {			
			final Questionnaire quest = enumer.nextElement();
			if (Zonated.myQuestControlManager.createdQuest.containsKey(quest.getId())) continue;
			TableRow buttonLayout = new TableRow(Zonated.myApp);
			buttonLayout.setOrientation(TableRow.HORIZONTAL);
			buttonLayout.setGravity(Gravity.CENTER_VERTICAL);
			buttonLayout.setLayoutParams(new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));			
			ImageButton button = new ImageButton(Zonated.myApp);
			button.setBackgroundResource(R.drawable.iconbg);
			button.setImageResource(R.drawable.quest);
			OnClickListener listener = new OnClickListener() {
				@Override
				public void onClick(View v) {
					Runnable run = new Runnable() {
						int i;
						Array<QuestionnaireSample> results;
						@Override
						public void run() {
							results = new Array<QuestionnaireSample>();
							handler.sendEmptyMessage(0);
							if (Zonated.myQuestControlManager.getQuestionnaire(quest.getId())) {
								Zonated.myQuestControlManager.activeQuestionnaire = quest;
								handler.sendEmptyMessage(4);
								i++;
								Enumeration<Contact> contacts = Zonated.myContactsManager.contactList.elements();
								while (contacts.hasMoreElements()) {
									Contact contact = contacts.nextElement();
									String name = contact.getFirstName() + " " + contact.getLastName();
									Long userId = Long.parseLong(contact.getId());
									QuestionnaireSample sample;
									try {
										sample = Zonated.myProtocolManager.getQuestionnaireSample(quest.getId(), userId);
									} catch (NotUpdatedException e) {
										Zonated.myUIManager.UImisc.loadTextOnWindow(Zonated.myLanguageManager.TEXT_UPDATEVERSION);
										e.printStackTrace();
										sample = null;
									}
									if (sample != null) results.add(sample);
									handler.sendEmptyMessage(4);
									i++;
								}
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
										myUIManager.UImisc.loadInfoPopupWithoutExiting(Zonated.myLanguageManager.NOTIFICATION_DOWNLOADING + quest.getName() + " ...");
										animationFrame = (ImageView) Zonated.myApp.findViewById(R.id.popupImage);
										animationFrame.setVisibility(View.VISIBLE);
										animationFrame.setBackgroundResource(R.drawable.loading);
										animation = (AnimationDrawable) animationFrame.getBackground();
										animation.start();
										break;
									case 1:
										animationFrame = (ImageView) Zonated.myApp.findViewById(R.id.popupImage);
										animation = (AnimationDrawable) animationFrame.getBackground();
										animation.stop();
										animationFrame.setVisibility(View.GONE);
										QuestGlobalResult result = Zonated.myQuestControlManager.getQuestionnaireGlobalResult(results);
										showQuestResults(quest, result);
										break;
									case 2:
										animationFrame = (ImageView) Zonated.myApp.findViewById(R.id.popupImage);
										animation = (AnimationDrawable) animationFrame.getBackground();
										animation.stop();
										animationFrame.setVisibility(View.GONE);
										loadSharedQuestionnaires();
										myUIManager.UImisc.loadInfoPopup(Zonated.myLanguageManager.CONNECTION_FAILED);
										break;
									case 4:
										myUIManager.UImisc.loadInfoPopup(Zonated.myLanguageManager.SENSORS_OBTAINING_DATA + "\n" + Math.round((i*100/Zonated.myContactsManager.contactList.size())) + " %");
										break;
								}
							}
						};
					};
					Thread thread = new Thread(run);
					thread.start();
				}
			};
			TextView text = new TextView(Zonated.myApp);
			text.append(quest.getName());
			text.setWidth(realwidth/2);
			text.setTextSize((float) 20);
			text.setTextColor(Color.BLACK);
			text.setTypeface(Typeface.SANS_SERIF);
			ImageButton share = new ImageButton(Zonated.myApp);
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
			if (quest.getId() != Zonated.myLanguageManager.feedbackQuestId) buttonLayout.addView(share, 50, 50);
			button.setOnClickListener(listener);
			text.setOnClickListener(listener);
			questlisting.addView(buttonLayout);
		}
		Enumeration<Questionnaire> enumer2 = Zonated.myQuestControlManager.createdQuest.elements();
		while (enumer2.hasMoreElements()) {			
			final Questionnaire quest = enumer2.nextElement();
			TableRow buttonLayout = new TableRow(Zonated.myApp);
			buttonLayout.setOrientation(TableRow.HORIZONTAL);
			buttonLayout.setGravity(Gravity.CENTER_VERTICAL);
			buttonLayout.setLayoutParams(new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));			
			ImageButton button = new ImageButton(Zonated.myApp);
			button.setBackgroundResource(R.drawable.iconbg);
			button.setImageResource(R.drawable.quest);
			OnClickListener listener = new OnClickListener() {
				@Override
				public void onClick(View v) {
					Runnable run = new Runnable() {
						int i;
						Array<QuestionnaireSample> results;
						@Override
						public void run() {
							results = new Array<QuestionnaireSample>();
							handler.sendEmptyMessage(0);
							if (Zonated.myQuestControlManager.getQuestionnaire(quest.getId())) {
								Zonated.myQuestControlManager.activeQuestionnaire = quest;
								handler.sendEmptyMessage(4);
								i++;
								Enumeration<Contact> contacts = Zonated.myContactsManager.contactList.elements();
								while (contacts.hasMoreElements()) {
									Contact contact = contacts.nextElement();
									String name = contact.getFirstName() + " " + contact.getLastName();
									Long userId = Long.parseLong(contact.getId());
									QuestionnaireSample sample;
									try {
										sample = Zonated.myProtocolManager.getQuestionnaireSample(quest.getId(), userId);
									} catch (NotUpdatedException e) {
										Zonated.myUIManager.UImisc.loadTextOnWindow(Zonated.myLanguageManager.TEXT_UPDATEVERSION);
										e.printStackTrace();
										sample = null;
									}
									if (sample != null) results.add(sample);
									handler.sendEmptyMessage(4);
									i++;
								}
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
										myUIManager.UImisc.loadInfoPopupWithoutExiting(Zonated.myLanguageManager.NOTIFICATION_DOWNLOADING + quest.getName() + " ...");
										animationFrame = (ImageView) Zonated.myApp.findViewById(R.id.popupImage);
										animationFrame.setVisibility(View.VISIBLE);
										animationFrame.setBackgroundResource(R.drawable.loading);
										animation = (AnimationDrawable) animationFrame.getBackground();
										animation.start();
										break;
									case 1:
										animationFrame = (ImageView) Zonated.myApp.findViewById(R.id.popupImage);
										animation = (AnimationDrawable) animationFrame.getBackground();
										animation.stop();
										animationFrame.setVisibility(View.GONE);
										QuestGlobalResult result = Zonated.myQuestControlManager.getQuestionnaireGlobalResult(results);
										showQuestResults(quest, result);
										break;
									case 2:
										animationFrame = (ImageView) Zonated.myApp.findViewById(R.id.popupImage);
										animation = (AnimationDrawable) animationFrame.getBackground();
										animation.stop();
										animationFrame.setVisibility(View.GONE);
										loadSharedQuestionnaires();
										myUIManager.UImisc.loadInfoPopup(Zonated.myLanguageManager.CONNECTION_FAILED);
										break;
									case 4:
										myUIManager.UImisc.loadInfoPopup(Zonated.myLanguageManager.SENSORS_OBTAINING_DATA + "\n" + Math.round((i*100/Zonated.myContactsManager.contactList.size())) + " %");
										break;
								}
							}
						};
					};
					Thread thread = new Thread(run);
					thread.start();
				}
			};
			TextView text = new TextView(Zonated.myApp);
			text.append(quest.getName());
			text.setWidth(realwidth/2);
			text.setTextSize((float) 20);
			text.setTextColor(Color.BLACK);
			text.setTypeface(Typeface.SANS_SERIF);
			ImageButton share = new ImageButton(Zonated.myApp);
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
		ImageButton refresh = (ImageButton) Zonated.myApp.findViewById(R.id.previous);
		refresh.setImageResource(R.drawable.refreshing);
		refresh.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				v.setVisibility(View.GONE);
				Zonated.myUIManager.state = UIManager.UI_STATE_LOADING;
				final RelativeLayout popupView = (RelativeLayout) Zonated.myApp.findViewById(R.id.popupView);
				popupView.setVisibility(View.VISIBLE);
				RefreshingConnection refreshing = new RefreshingConnection();
				Thread thread = new Thread(refreshing, "REFRESH");
				thread.start();
			}
		});
		ImageButton change = (ImageButton) Zonated.myApp.findViewById(R.id.help);
		change.setImageResource(R.drawable.next);
		change.setVisibility(View.VISIBLE);
		change.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				loadSharedQuestionnaires();			
			}
		});
		ImageView addquest = (ImageView) Zonated.myApp.findViewById(R.id.animation);
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
		Zonated.myApp.setContentView(R.layout.yesnoquestion);
		TextView question = (TextView)Zonated.myApp.findViewById(R.id.question);
		question.append(Zonated.myLanguageManager.QUEST_START + quest.getName() + "?");
		ImageButton yes = (ImageButton)Zonated.myApp.findViewById(R.id.yes);
		ImageButton no = (ImageButton)Zonated.myApp.findViewById(R.id.no);
		yes.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Runnable run = new Runnable() {
					@Override
					public void run() {
						handler.sendEmptyMessage(0);
						if (Zonated.myQuestControlManager.getQuestionnaire(quest.getId())) {
							if (Zonated.myQuestControlManager.validateQuestionnaire()) handler.sendEmptyMessage(1);
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
									myUIManager.UImisc.loadInfoPopupWithoutExiting(Zonated.myLanguageManager.NOTIFICATION_DOWNLOADING + quest.getName() + " ...");
									animationFrame = (ImageView) Zonated.myApp.findViewById(R.id.popupImage);
									animationFrame.setVisibility(View.VISIBLE);
									animationFrame.setBackgroundResource(R.drawable.loading);
									animation = (AnimationDrawable) animationFrame.getBackground();
									animation.start();
									break;
								case 1:
									animationFrame = (ImageView) Zonated.myApp.findViewById(R.id.popupImage);
									animation = (AnimationDrawable) animationFrame.getBackground();
									animation.stop();
									animationFrame.setVisibility(View.GONE);
									Zonated.myQuestControlManager.startQuestionnaire(quest);
									break;
								case 2:
									animationFrame = (ImageView) Zonated.myApp.findViewById(R.id.popupImage);
									animation = (AnimationDrawable) animationFrame.getBackground();
									animation.stop();
									animationFrame.setVisibility(View.GONE);
									loadSharedQuestionnaires();
									myUIManager.UImisc.loadInfoPopup(Zonated.myLanguageManager.CONNECTION_FAILED);
									break;
								case 3:
									animationFrame = (ImageView) Zonated.myApp.findViewById(R.id.popupImage);
									animation = (AnimationDrawable) animationFrame.getBackground();
									animation.stop();
									animationFrame.setVisibility(View.GONE);
									loadSharedQuestionnaires();
									myUIManager.UImisc.loadInfoPopup(Zonated.myLanguageManager.QUEST_NOTVALID);
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
				loadSharedQuestionnaires();
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
			if (Zonated.myQuestControlManager.activeAnswers.get(question.getId()).isEmpty()) {
				Zonated.myApp.setContentView(R.layout.info);
				((RelativeLayout)Zonated.myApp.findViewById(R.id.questcontrol)).setVisibility(View.VISIBLE);
				TextView text = (TextView) Zonated.myApp.findViewById(R.id.textInfo); 
				text.setText(question.getName());
				text.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
				((ImageButton) Zonated.myApp.findViewById(R.id.previous)).setVisibility(View.VISIBLE);
				next = (ImageButton) Zonated.myApp.findViewById(R.id.next);
				next.setVisibility(View.VISIBLE);
				next.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						if (question.getNext() != null) loadQuestion(Zonated.myQuestControlManager.activeQuestions.get(question.getNext()));	
						else Zonated.myQuestControlManager.finishQuestionnaire();
					}
				});
		    }
			else if (multiquest.isMultiple()) {
				Zonated.myApp.setContentView(R.layout.poliquestion);
				questionText = (TextView) Zonated.myApp.findViewById(R.id.questionText);
				questionText.setText(multiquest.getName());
				final Array<Answer> answers = Zonated.myQuestControlManager.activeAnswers.get(question.getId());
				next = (ImageButton) Zonated.myApp.findViewById(R.id.next);
				LinearLayout answersGroupPoli = (LinearLayout) Zonated.myApp.findViewById(R.id.options);
				for (int i = 0; i < answers.size(); i++) {
					CheckBox button = new CheckBox(Zonated.myApp);
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
						Zonated.myQuestControlManager.activeAnswersAnswered.push(answer);
						Zonated.myQuestControlManager.activeQuestionsAnswered.push(question);
						Long next = Zonated.myQuestControlManager.calcMultiNextQuestion(question.getId(), answer);
						if (next == null) Zonated.myQuestControlManager.finishQuestionnaire();
						else loadQuestion(Zonated.myQuestControlManager.activeQuestions.get(next));
					}
				});
			}
			else {
				final Array<Answer> answers = Zonated.myQuestControlManager.activeAnswers.get(question.getId());
		    	Zonated.myApp.setContentView(R.layout.monoquestion);
				questionText = (TextView) Zonated.myApp.findViewById(R.id.questionText);
				questionText.setText(multiquest.getName());
				RadioGroup answersGroup = (RadioGroup) Zonated.myApp.findViewById(R.id.options);
				next = (ImageButton) Zonated.myApp.findViewById(R.id.next);
				for (int i = 0; i < answers.size(); i++) {
					RadioButton button = new RadioButton(Zonated.myApp);
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
						Zonated.myQuestControlManager.activeAnswersAnswered.push(answer);
						Zonated.myQuestControlManager.activeQuestionsAnswered.push(question);
						Long next = Zonated.myQuestControlManager.calcNextQuestion(question.getId(), answer);
						if (next == null) Zonated.myQuestControlManager.finishQuestionnaire();
						else loadQuestion(Zonated.myQuestControlManager.activeQuestions.get(next));
					}
				});
			}
	    }
	    else if (question instanceof NumericQuestion) {
	    	NumericQuestion numquest = (NumericQuestion) question;
	    	final NumericAnswer answer = new NumericAnswer();
			answer.setQuestion(question.getId());
			Zonated.myApp.setContentView(R.layout.numquestion);
			questionText = (TextView) Zonated.myApp.findViewById(R.id.questionText);
			final TextView numText = (TextView) Zonated.myApp.findViewById(R.id.numText);
			questionText.setText(numquest.getName());
			numText.setText("0");
			SeekBar seekbar = (SeekBar) Zonated.myApp.findViewById(R.id.seekbar);
			seekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {				
				@Override
				public void onStopTrackingTouch(SeekBar seekBar) {
					
				}
				@Override
				public void onStartTrackingTouch(SeekBar seekBar) {
					
				}
				@Override
				public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
					numText.setText("" + progress/10);
				}
			});
			next = (ImageButton) Zonated.myApp.findViewById(R.id.next);
			next.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					SeekBar seekbar = (SeekBar) Zonated.myApp.findViewById(R.id.seekbar);
					if ((seekbar.getProgress()/5) == 1) answer.setValue(0.5f);
					else answer.setValue((double)(seekbar.getProgress()/10)); 
					Zonated.myQuestControlManager.activeAnswersAnswered.push(answer);
					Zonated.myQuestControlManager.activeQuestionsAnswered.push(question);
					Long next = Zonated.myQuestControlManager.calcNumericNextQuestion(question.getId(), answer);
					if (next != null) loadQuestion(Zonated.myQuestControlManager.activeQuestions.get(next));
					else Zonated.myQuestControlManager.finishQuestionnaire();
				}
			});
	    }
	    else if (question instanceof TextQuestion) {
	    	TextQuestion textquest = (TextQuestion) question;
	    	final TextAnswer answer = new TextAnswer();
			answer.setQuestion(question.getId());
			Zonated.myApp.setContentView(R.layout.textquestion);
			questionText = (TextView) Zonated.myApp.findViewById(R.id.questionText);
			questionText.setText(textquest.getName());
			next = (ImageButton) Zonated.myApp.findViewById(R.id.next);
			next.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					EditText editable = (EditText) Zonated.myApp.findViewById(R.id.editable);
					answer.setValue(editable.getText().toString());
					Zonated.myQuestControlManager.activeAnswersAnswered.push(answer);
					Zonated.myQuestControlManager.activeQuestionsAnswered.push(question);
					if (question.getNext() == null)	Zonated.myQuestControlManager.finishQuestionnaire();
					else loadQuestion(Zonated.myQuestControlManager.activeQuestions.get(question.getNext()));	
				}
			});
	    }
	    else {
			Zonated.myApp.setContentView(R.layout.info);
			((RelativeLayout)Zonated.myApp.findViewById(R.id.questcontrol)).setVisibility(View.VISIBLE);
			TextView text = (TextView) Zonated.myApp.findViewById(R.id.textInfo); 
			text.setText(question.getName());
			text.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
			((ImageButton) Zonated.myApp.findViewById(R.id.previous)).setVisibility(View.VISIBLE);
			next = (ImageButton) Zonated.myApp.findViewById(R.id.next);
			next.setVisibility(View.VISIBLE);
			next.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (question.getNext() != null) loadQuestion(Zonated.myQuestControlManager.activeQuestions.get(question.getNext()));	
					else Zonated.myQuestControlManager.finishQuestionnaire();
				}
			});
	    }
		ImageButton back = (ImageButton) Zonated.myApp.findViewById(R.id.previous);
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!Zonated.myQuestControlManager.activeQuestionsAnswered.isEmpty()) {
					Question prev = Zonated.myQuestControlManager.activeQuestionsAnswered.pop();
					Zonated.myQuestControlManager.activeAnswersAnswered.pop();
					loadQuestion(prev);
				}
				else {
					loadSharedQuestionnaires();
				}
			}
		});
		ImageButton quit = (ImageButton) Zonated.myApp.findViewById(R.id.quit);
		quit.setVisibility(View.VISIBLE);
		quit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				loadSharedQuestionnaires();
			}
		});
	}

	public void loadQuestResult(double result) {
		Zonated.myApp.setContentView(R.layout.resultimage);
		TextView text = (TextView) Zonated.myApp.findViewById(R.id.textInfo);
		if (result < 0.0f) text.setText(Zonated.myLanguageManager.QUEST_WEIGHT_NOTDONE);
		else text.setText(Zonated.myLanguageManager.QUEST_WEIGHT_DONE + String.format("%.1f", result) + " %. ");
		ImageView resultText = (ImageView) Zonated.myApp.findViewById(R.id.result);
		if (result < 0.0) {
		}
		else if (result < 33.33) {
//			text.append(Zonated.myLanguageManager.QUEST_WEIGHT_0);
			resultText.setBackgroundResource(R.drawable.lightgreen);
		}
		else if (result < 66.66) {
//			text.append(Zonated.myLanguageManager.QUEST_WEIGHT_1);
			resultText.setBackgroundResource(R.drawable.lightorange);
		}
		else {
//			text.append(Zonated.myLanguageManager.QUEST_WEIGHT_2);
			resultText.setBackgroundResource(R.drawable.lightwhite);
		}
		CountDownTimer timer = new CountDownTimer(3000, 1000) {
			@Override
			public void onTick(long millisUntilFinished) {}
			@Override
			public void onFinish() {
				Zonated.myApp.setContentView(R.layout.sending);
				SendQuestionnaire sending = new SendQuestionnaire(Zonated.myQuestControlManager.activeQuestionnaire.getId(), Zonated.myQuestControlManager.getQuestToSend());
				Thread thread = new Thread(sending, "SENDQUESTIONNAIRE");
				thread.start();
			}
		};
		timer.start();
	}
	
	public void searchQuestionnaire() {
		LayoutInflater inflater = (LayoutInflater) Zonated.myApp.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		final View layout = inflater.inflate(R.layout.toastsearch,
		                               (ViewGroup) Zonated.myApp.findViewById(R.id.toastsearchroot));
		TextView text = (TextView) layout.findViewById(R.id.searchexpl);
		text.setText(Zonated.myLanguageManager.NOTIFICATION_QUEST_EXPL);
		ImageButton image = (ImageButton) layout.findViewById(R.id.searchok);
		Builder builder = new AlertDialog.Builder(Zonated.myApp);
		builder.setView(layout);
		final AlertDialog alertDialog = builder.create();
		alertDialog.show();
		image.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				final String query = ((EditText)layout.findViewById(R.id.searchtext)).getText().toString();
				alertDialog.cancel();
				final ProgressDialog dialog = ProgressDialog.show(Zonated.myApp, Zonated.myLanguageManager.NOTIFICATION_QUEST_TITLE, 
						Zonated.myLanguageManager.NOTIFICATION_QUEST_SEARCHING, true);
				dialog.show();
				Runnable run =  new Runnable() {
					Array<Questionnaire> quests;
					@Override
					public void run() {
						quests = Zonated.myQuestControlManager.SearchQuests(query);
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
									Toast toast = Toast.makeText(Zonated.myApp, Zonated.myLanguageManager.CONNECTION_FAILED, Toast.LENGTH_SHORT);
									toast.show();
									break;
								case 1:
									Toast toast2 = Toast.makeText(Zonated.myApp, Zonated.myLanguageManager.NOTIFICATION_QUEST_NOTFOUND, Toast.LENGTH_SHORT);
									toast2.show();
									break;
								case 2:
									final CharSequence[] items = new CharSequence[quests.size()];
									final Array<Questionnaire> questsToAdd = new Array<Questionnaire>();
									for (int i = 0; i < quests.size(); i++) {
										items[i] = quests.get(i).getName();
									}
									AlertDialog.Builder builder = new AlertDialog.Builder(Zonated.myApp);
									builder.setTitle(Zonated.myLanguageManager.NOTIFICATION_QUEST_ADD);
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
								        		   users.add(Zonated.myMobileManager.userForServices.getId());
								        		   Zonated.myQuestControlManager.shareQuestionnaire(users, questsToAdd.get(i));
//									        	   Zonated.myProtocolManager.getPrivilegesOnQuestionnaire(questsToAdd.get(i));
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
		int realwidth = Zonated.myApp.getResources().getDisplayMetrics().widthPixels;
		Zonated.myApp.setContentView(R.layout.list);
		((TextView) Zonated.myApp.findViewById(R.id.startText)).setText(Zonated.myLanguageManager.QUEST_CREATED_TITLE);
		TableLayout questlisting = (TableLayout)Zonated.myApp.findViewById(R.id.listing);
		Enumeration<Questionnaire> enumer = Zonated.myQuestControlManager.createdQuest.elements();
		while (enumer.hasMoreElements()) {			
			final Questionnaire quest = enumer.nextElement();
			TableRow buttonLayout = new TableRow(Zonated.myApp);
			buttonLayout.setOrientation(TableRow.HORIZONTAL);
			buttonLayout.setGravity(Gravity.CENTER_VERTICAL);
			buttonLayout.setLayoutParams(new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));			
			ImageButton button = new ImageButton(Zonated.myApp);
			button.setBackgroundResource(R.drawable.iconbg);
			button.setImageResource(R.drawable.quest);
			OnClickListener listener = new OnClickListener() {
				@Override
				public void onClick(View v) {
					Runnable run = new Runnable() {
						@Override
						public void run() {
							handler.sendEmptyMessage(0);
							if (Zonated.myQuestControlManager.getQuestionnaire(quest.getId())) {
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
										myUIManager.UImisc.loadInfoPopupWithoutExiting(Zonated.myLanguageManager.NOTIFICATION_DOWNLOADING + quest.getName() + " ...");
										animationFrame = (ImageView) Zonated.myApp.findViewById(R.id.popupImage);
										animationFrame.setVisibility(View.VISIBLE);
										animationFrame.setBackgroundResource(R.drawable.loading);
										animation = (AnimationDrawable) animationFrame.getBackground();
										animation.start();
										break;
									case 1:
										animationFrame = (ImageView) Zonated.myApp.findViewById(R.id.popupImage);
										animation = (AnimationDrawable) animationFrame.getBackground();
										animation.stop();
										animationFrame.setVisibility(View.GONE);
										myUIManager.UImisc.removeInfoPopup();
										modifyQuestionnaire(quest);
										break;
									case 2:
										animationFrame = (ImageView) Zonated.myApp.findViewById(R.id.popupImage);
										animation = (AnimationDrawable) animationFrame.getBackground();
										animation.stop();
										animationFrame.setVisibility(View.GONE);
										myUIManager.UImisc.loadInfoPopup(Zonated.myLanguageManager.CONNECTION_FAILED);
										break;
								}
							}
						};
					};
					Thread thread = new Thread(run);
					thread.start();
				}
			};
			TextView text = new TextView(Zonated.myApp);
			text.append(quest.getName());
			text.setWidth(realwidth*2/5);
			text.setTextSize((float) 20);
			text.setTextColor(Color.BLACK);
			text.setTypeface(Typeface.SANS_SERIF);			
			ImageButton delete = new ImageButton(Zonated.myApp);
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
							if (Zonated.myQuestControlManager.removeQuestionnaire(quest)) {
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
										animationFrame = (ImageView) Zonated.myApp.findViewById(R.id.popupImage);
										animationFrame.setVisibility(View.VISIBLE);
										animationFrame.setBackgroundResource(R.drawable.loading);
										animation = (AnimationDrawable) animationFrame.getBackground();
										animation.start();
										break;
									case 1:
										animationFrame = (ImageView) Zonated.myApp.findViewById(R.id.popupImage);
										animation = (AnimationDrawable) animationFrame.getBackground();
										animation.stop();
										animationFrame.setVisibility(View.GONE);
										myUIManager.UImisc.removeInfoPopup();
										loadAssignedQuestionnaires();
										break;
									case 2:
										animationFrame = (ImageView) Zonated.myApp.findViewById(R.id.popupImage);
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
			ImageButton share = new ImageButton(Zonated.myApp);
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
		ImageButton refresh = (ImageButton) Zonated.myApp.findViewById(R.id.previous);
		refresh.setImageResource(R.drawable.refreshing);
		refresh.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				myUIManager.UIquest.loadSharedQuestionnaires();
			}
		});
		ImageButton change = (ImageButton) Zonated.myApp.findViewById(R.id.help);
		change.setImageResource(R.drawable.next);
		change.setVisibility(View.VISIBLE);
		change.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				loadQuestionnairesResults();
			}
		});
		ImageView addquest = (ImageView) Zonated.myApp.findViewById(R.id.animation);
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
		final Long[] users = new Long[Zonated.myContactsManager.contactList.size() + 1];
		final CharSequence[] items = new CharSequence[Zonated.myContactsManager.contactList.size() + 1];
		final ArrayList<Long>usersToAdd = new ArrayList<Long>();
		int i = 1;
		users[0] = -1l;
		items[0] = "Make it public";
		Enumeration<Contact> enumerator = Zonated.myContactsManager.contactList.elements();
		while (enumerator.hasMoreElements()) {
			Contact user = enumerator.nextElement();
			items[i] = user.getFirstName() + " " + user.getLastName();
			users[i] = Long.parseLong(user.getId());
			i++;
		}
		AlertDialog.Builder builder = new AlertDialog.Builder(Zonated.myApp);
		builder.setTitle(Zonated.myLanguageManager.NOTIFICATION_SEARCH_ADD);
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
	        		   Zonated.myQuestControlManager.publishQuestionnaire(quest);
	        	   }
	        	   Zonated.myQuestControlManager.shareQuestionnaire(usersToAdd, quest);
	        	   dialog.cancel();
	           }
	    });
		builder.setNegativeButton(Zonated.myLanguageManager.MAIN_CONTACTS, new DialogInterface.OnClickListener() {
	           public void onClick(DialogInterface dialog, int id) {
	        	   myUIManager.UIcontacts.loadContactList(true);
	           }
	    });
		AlertDialog alert = builder.create();
		alert.show();
	}
	
	public void createNewQuestionnaire() {
		LayoutInflater inflater = (LayoutInflater) Zonated.myApp.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		final View layout = inflater.inflate(R.layout.toastaddquestionnaire,
		                               (ViewGroup) Zonated.myApp.findViewById(R.id.toastaddquestionnaireroot));
		TextView title = (TextView) layout.findViewById(R.id.addtitle);
		title.setText(Zonated.myLanguageManager.QUEST_CREATE_QUESTIONNAIRE);
		TextView namereq = (TextView) layout.findViewById(R.id.namerequested);
		namereq.setText(Zonated.myLanguageManager.QUEST_NAME);
		ImageButton image = (ImageButton) layout.findViewById(R.id.addeventok);
		Builder builder = new AlertDialog.Builder(Zonated.myApp);
		builder.setView(layout);
		final AlertDialog alertDialog = builder.create();
		alertDialog.show();
		image.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				final String quest = ((EditText) layout.findViewById(R.id.name)).getText().toString();
				final ProgressDialog dialog = ProgressDialog.show(Zonated.myApp, Zonated.myLanguageManager.QUEST_CREATE_QUESTIONNAIRE, 
						Zonated.myLanguageManager.QUEST_CREATING_QUESTIONNAIRE, true);
				dialog.show();
				Runnable run =  new Runnable() {
					@Override
					public void run() {
						if (Zonated.myQuestControlManager.createQuestionnaire(quest))
							handler.sendEmptyMessage(1);
						else 
							handler.sendEmptyMessage(0);
					}
					private Handler handler = new Handler() {
						@Override
						public void handleMessage(Message msg) {
							switch (msg.what) {
								case 0:
									Toast toast = Toast.makeText(Zonated.myApp, Zonated.myLanguageManager.CONNECTION_FAILED, Toast.LENGTH_SHORT);
									modifyQuestionnaire(Zonated.myQuestControlManager.activeQuestionnaire);
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
		Zonated.myQuestControlManager.activeQuestionnaire = questionnaire;
		int realwidth = Zonated.myApp.getResources().getDisplayMetrics().widthPixels;
		myUIManager.state = UIManager.UI_STATE_TOTALQUESTIONNAIRE;
		Zonated.myApp.setContentView(R.layout.list);
		((TextView) Zonated.myApp.findViewById(R.id.startText)).setText(Zonated.myLanguageManager.QUEST_CREATED_TITLE);
		TableLayout questlisting = (TableLayout)Zonated.myApp.findViewById(R.id.listing);
		Enumeration<Question> enumer = Zonated.myQuestControlManager.activeQuestions.elements();
		while (enumer.hasMoreElements()) {			
			final Question quest = enumer.nextElement();
			TableRow buttonLayout = new TableRow(Zonated.myApp);
			buttonLayout.setOrientation(TableRow.HORIZONTAL);
			buttonLayout.setGravity(Gravity.CENTER_VERTICAL);
			TableLayout.LayoutParams layoutparams = new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
			layoutparams.setMargins(10, 0, 0, 0);
			buttonLayout.setLayoutParams(layoutparams);			
			ImageButton delete = new ImageButton(Zonated.myApp);
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
							if (Zonated.myQuestControlManager.removeQuestion(quest)) {
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
										animationFrame = (ImageView) Zonated.myApp.findViewById(R.id.popupImage);
										animationFrame.setVisibility(View.VISIBLE);
										animationFrame.setBackgroundResource(R.drawable.loading);
										animation = (AnimationDrawable) animationFrame.getBackground();
										animation.start();
										break;
									case 1:
										animationFrame = (ImageView) Zonated.myApp.findViewById(R.id.popupImage);
										animation = (AnimationDrawable) animationFrame.getBackground();
										animation.stop();
										animationFrame.setVisibility(View.GONE);
										myUIManager.UImisc.removeInfoPopup();
										modifyQuestionnaire(questionnaire);
										break;
									case 2:
										animationFrame = (ImageView) Zonated.myApp.findViewById(R.id.popupImage);
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
			ImageButton modify = new ImageButton(Zonated.myApp);
			modify.setBackgroundResource(R.drawable.iconbg);
			modify.setImageResource(R.drawable.modify);
			modify.setScaleType(ScaleType.FIT_XY);
			modify.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					modifyQuestion(quest);
				}
			});
			TextView text = new TextView(Zonated.myApp);
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
		ImageButton back = (ImageButton) Zonated.myApp.findViewById(R.id.previous);
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				myUIManager.UIquest.loadSharedQuestionnaires();
			}
		});
		ImageButton change = (ImageButton) Zonated.myApp.findViewById(R.id.help);
		change.setImageResource(R.drawable.first);
		change.setVisibility(View.VISIBLE);
		change.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setFirstQuestion();			
			}
		});
		ImageView addquest = (ImageView) Zonated.myApp.findViewById(R.id.animation);
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
		Enumeration<Question> questions = Zonated.myQuestControlManager.activeQuestions.elements();
		final CharSequence[] items = new CharSequence[Zonated.myQuestControlManager.activeQuestions.size()];
		final Long[] longitems = new Long[Zonated.myQuestControlManager.activeQuestions.size()];
		int j = 0;
		while (questions.hasMoreElements()) {
			Question question = questions.nextElement();
			items[j] = question.getName();
			longitems[j] = question.getId();
			j++;
		}
		AlertDialog.Builder builder = new AlertDialog.Builder(Zonated.myApp);
		builder.setTitle(Zonated.myLanguageManager.MESSAGES_CONTACT_REQUEST);
		builder.setItems(items, new DialogInterface.OnClickListener() {
		    public void onClick(DialogInterface dialog, int item) {
		    	Zonated.myQuestControlManager.activeQuestionnaire.setFirst(longitems[item]);
				Runnable run = new Runnable() {
					@Override
					public void run() {
						handler.sendEmptyMessage(0);
						if (Zonated.myQuestControlManager.updateQuestionnaire(Zonated.myQuestControlManager.activeQuestionnaire))
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
									animationFrame = (ImageView) Zonated.myApp.findViewById(R.id.popupImage);
									animationFrame.setVisibility(View.VISIBLE);
									animationFrame.setBackgroundResource(R.drawable.loading);
									animation = (AnimationDrawable) animationFrame.getBackground();
									animation.start();
									break;
								case 1:
									animationFrame = (ImageView) Zonated.myApp.findViewById(R.id.popupImage);
									animation = (AnimationDrawable) animationFrame.getBackground();
									animation.stop();
									animationFrame.setVisibility(View.GONE);
									myUIManager.UImisc.removeInfoPopup();
									modifyQuestionnaire(Zonated.myQuestControlManager.activeQuestionnaire);
									break;
								case 2:
									animationFrame = (ImageView) Zonated.myApp.findViewById(R.id.popupImage);
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
		LayoutInflater inflater = (LayoutInflater) Zonated.myApp.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		final View layout = inflater.inflate(R.layout.toastcreatequestion,
		                               (ViewGroup) Zonated.myApp.findViewById(R.id.toastcreatequestionroot));
		TextView title = (TextView) layout.findViewById(R.id.addtitle);
		title.setText(Zonated.myLanguageManager.QUEST_CREATE_QUESTION);
		TextView namereq = (TextView) layout.findViewById(R.id.typerequest);
		namereq.setText(Zonated.myLanguageManager.QUEST_TYPE);
		final Spinner type = (Spinner) layout.findViewById(R.id.type);
	    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(Zonated.myApp, R.array.questtype, android.R.layout.simple_spinner_item);
	    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    type.setAdapter(adapter);
		ImageButton image = (ImageButton) layout.findViewById(R.id.addeventok);
		Builder builder = new AlertDialog.Builder(Zonated.myApp);
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
						createdQuest = Zonated.myQuestControlManager.addQuestion(question);
						if (createdQuest != null) {
							if (Zonated.myQuestControlManager.activeQuestions.size() <= 1) {
								Zonated.myQuestControlManager.activeQuestionnaire.setFirst(createdQuest.getId());
								if (Zonated.myQuestControlManager.updateQuestionnaire(Zonated.myQuestControlManager.activeQuestionnaire))
									handler.sendEmptyMessage(3);
								else handler.sendEmptyMessage(4);
							}
							else {
								prevQest = Zonated.myQuestControlManager.activeQuestions.get(Zonated.myQuestControlManager.lastQuestionId);
								if (prevQest != null) {
									prevQest.setNext(createdQuest.getId());
									if (Zonated.myQuestControlManager.updateQuestion(prevQest))
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
									myUIManager.UImisc.loadInfoPopupWithoutExiting(Zonated.myLanguageManager.QUEST_CREATING_QUESTION);
									animationFrame = (ImageView) Zonated.myApp.findViewById(R.id.popupImage);
									animationFrame.setVisibility(View.VISIBLE);
									animationFrame.setBackgroundResource(R.drawable.loading);
									animation = (AnimationDrawable) animationFrame.getBackground();
									animation.start();
									break;
								case 1:
									animationFrame = (ImageView) Zonated.myApp.findViewById(R.id.popupImage);
									animation = (AnimationDrawable) animationFrame.getBackground();
									animation.stop();
									animationFrame.setVisibility(View.GONE);
									myUIManager.UImisc.removeInfoPopup();
									Zonated.myQuestControlManager.lastQuestionId = createdQuest.getId();
									modifyQuestion(createdQuest);
									myUIManager.UImisc.loadInfoPopup(String.format(Zonated.myLanguageManager.QUEST_LINKED, prevQest.getName()));
									break;
								case 2:
									animationFrame = (ImageView) Zonated.myApp.findViewById(R.id.popupImage);
									animation = (AnimationDrawable) animationFrame.getBackground();
									animation.stop();
									animationFrame.setVisibility(View.GONE);
									myUIManager.UImisc.loadInfoPopup(Zonated.myLanguageManager.QUEST_CREATING_FAILED);
									break;
								case 3:
									animationFrame = (ImageView) Zonated.myApp.findViewById(R.id.popupImage);
									animation = (AnimationDrawable) animationFrame.getBackground();
									animation.stop();
									animationFrame.setVisibility(View.GONE);
									myUIManager.UImisc.removeInfoPopup();
									Zonated.myQuestControlManager.lastQuestionId = createdQuest.getId();
									modifyQuestion(createdQuest);
									myUIManager.UImisc.loadInfoPopup(Zonated.myLanguageManager.QUEST_FIRST);
									break;
								case 4:
									animationFrame = (ImageView) Zonated.myApp.findViewById(R.id.popupImage);
									animation = (AnimationDrawable) animationFrame.getBackground();
									animation.stop();
									animationFrame.setVisibility(View.GONE);
									myUIManager.UImisc.removeInfoPopup();
									Zonated.myQuestControlManager.lastQuestionId = createdQuest.getId();
									modifyQuestion(createdQuest);
									myUIManager.UImisc.loadInfoPopup(Zonated.myLanguageManager.QUEST_NOTFIRST);
									break;
								case 5:
									animationFrame = (ImageView) Zonated.myApp.findViewById(R.id.popupImage);
									animation = (AnimationDrawable) animationFrame.getBackground();
									animation.stop();
									animationFrame.setVisibility(View.GONE);
									myUIManager.UImisc.removeInfoPopup();
									Zonated.myQuestControlManager.lastQuestionId = createdQuest.getId();
									modifyQuestion(createdQuest);
									myUIManager.UImisc.loadInfoPopup(Zonated.myLanguageManager.QUEST_LINKED_FAILED);
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
		((EditText) Zonated.myApp.findViewById(R.id.text)).setText(text);
		((Spinner) Zonated.myApp.findViewById(R.id.next)).setSelection(position);
		((CheckBox) Zonated.myApp.findViewById(R.id.optional)).setSelected(selected);
	}
	
	public void modifyQuestion(final Question question) {
		int selectedByFirst = 0, i = 0;
		int realwidth = Zonated.myApp.getResources().getDisplayMetrics().widthPixels;
		myUIManager.state = UIManager.UI_STATE_QUESTION;
		Zonated.myApp.setContentView(R.layout.createquestion);
		((TextView) Zonated.myApp.findViewById(R.id.startText)).setText(Zonated.myLanguageManager.QUEST_CREATE_QUESTIONNAIRE);
		((TextView) Zonated.myApp.findViewById(R.id.textrequest)).setText(Zonated.myLanguageManager.QUEST_TEXT + ": ");
		((EditText) Zonated.myApp.findViewById(R.id.text)).setText(question.getName());
		((TextView) Zonated.myApp.findViewById(R.id.nextrequest)).setText(Zonated.myLanguageManager.QUEST_NEXT + ": ");
		final Spinner next = (Spinner) Zonated.myApp.findViewById(R.id.next);
		ArrayList<String> spinnerArray = new ArrayList<String>();
		final ArrayList<Long> idsArray = new ArrayList<Long>();
		Enumeration<Question> questions = Zonated.myQuestControlManager.activeQuestions.elements();
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
	    ArrayAdapter<String> adapter = new ArrayAdapter<String>(Zonated.myApp, android.R.layout.simple_spinner_item, spinnerArray);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		next.setAdapter(adapter);
		next.setSelection(selectedByFirst);
	    if (question instanceof MultiQuestion) {
			((RelativeLayout) Zonated.myApp.findViewById(R.id.optionallayout)).setVisibility(View.VISIBLE);
			((TextView) Zonated.myApp.findViewById(R.id.optionalrequest)).setText(Zonated.myLanguageManager.QUEST_OPTIONAL + ": ");
			((CheckBox) Zonated.myApp.findViewById(R.id.optional)).setSelected(question.isOptional());
			((CheckBox) Zonated.myApp.findViewById(R.id.optional)).setOnCheckedChangeListener(new android.widget.CompoundButton.OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					question.setOptional(isChecked);
				}
			});
		}
		if (question instanceof MultiQuestion) {
			Array<Answer> answers = Zonated.myQuestControlManager.activeAnswers.get(question.getId());
			Array<Condition> conditions = Zonated.myQuestControlManager.activeConditions.get(question.getId());
			((LinearLayout) Zonated.myApp.findViewById(R.id.answers)).setVisibility(View.VISIBLE);
			((LinearLayout) Zonated.myApp.findViewById(R.id.conditions)).setVisibility(View.VISIBLE);
			((RelativeLayout) Zonated.myApp.findViewById(R.id.answershead)).setVisibility(View.VISIBLE);
			((RelativeLayout) Zonated.myApp.findViewById(R.id.conditionhead)).setVisibility(View.VISIBLE);
			((TextView) Zonated.myApp.findViewById(R.id.answersrequest)).setText(Zonated.myLanguageManager.QUEST_ANSWERS + ": ");
			((TextView) Zonated.myApp.findViewById(R.id.conditionrequest)).setText(Zonated.myLanguageManager.QUEST_CONDITIONS + ": ");
			Iterator<Answer> ansit = answers.iterator();
			while (ansit.hasNext()) {
				final Answer answer = ansit.next();
				TableRow buttonLayout = new TableRow(Zonated.myApp);
				buttonLayout.setOrientation(TableRow.HORIZONTAL);
				buttonLayout.setGravity(Gravity.CENTER_VERTICAL);
				buttonLayout.setLayoutParams(new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
				ImageButton delete = new ImageButton(Zonated.myApp);
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
								if (Zonated.myQuestControlManager.removeAnswer(question.getId(), answer)) {
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
											animationFrame = (ImageView) Zonated.myApp.findViewById(R.id.animation);
											animationFrame.setVisibility(View.VISIBLE);
											animationFrame.setBackgroundResource(R.drawable.loading);
											animation = (AnimationDrawable) animationFrame.getBackground();
											animation.start();
											break;
										case 1:
											animationFrame = (ImageView) Zonated.myApp.findViewById(R.id.animation);
											animation = (AnimationDrawable) animationFrame.getBackground();
											animation.stop();
											animationFrame.setVisibility(View.GONE);
											myUIManager.UImisc.removeInfoPopup();
											String text = ((EditText) Zonated.myApp.findViewById(R.id.text)).getText().toString();
											int position = ((Spinner) Zonated.myApp.findViewById(R.id.next)).getSelectedItemPosition();
											boolean checked = ((CheckBox) Zonated.myApp.findViewById(R.id.optional)).isChecked();
											modifyQuestion(question, text, position, checked);
											break;
										case 2:
											animationFrame = (ImageView) Zonated.myApp.findViewById(R.id.animation);
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
				ImageButton modify = new ImageButton(Zonated.myApp);
				modify.setImageResource(R.drawable.modify);
				modify.setBackgroundResource(R.drawable.iconbg);
				modify.setScaleType(ScaleType.FIT_XY);
				modify.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						modifyAnswer(question, answer);
					}
				});
				TextView text = new TextView(Zonated.myApp);
				text.append(answer.getName());
				text.setWidth(realwidth/2);
				text.setTextSize((float) 20);
				text.setTextColor(Color.BLACK);
				text.setTypeface(Typeface.SANS_SERIF);
				buttonLayout.addView(modify, 40, 40);
				buttonLayout.addView(delete, 40, 40);
				buttonLayout.addView(text);
				((LinearLayout) Zonated.myApp.findViewById(R.id.answers)).addView(buttonLayout);
			}
			ImageButton addanswer = (ImageButton) Zonated.myApp.findViewById(R.id.addanswer);
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
				TableRow buttonLayout = new TableRow(Zonated.myApp);
				buttonLayout.setOrientation(TableRow.HORIZONTAL);
				buttonLayout.setGravity(Gravity.CENTER_VERTICAL);
				buttonLayout.setLayoutParams(new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
				ImageButton delete = new ImageButton(Zonated.myApp);
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
								if (Zonated.myQuestControlManager.removeCondition(question.getId(), condition)) {
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
											animationFrame = (ImageView) Zonated.myApp.findViewById(R.id.animation);
											animationFrame.setVisibility(View.VISIBLE);
											animationFrame.setBackgroundResource(R.drawable.loading);
											animation = (AnimationDrawable) animationFrame.getBackground();
											animation.start();
											break;
										case 1:
											animationFrame = (ImageView) Zonated.myApp.findViewById(R.id.animation);
											animation = (AnimationDrawable) animationFrame.getBackground();
											animation.stop();
											animationFrame.setVisibility(View.GONE);
											myUIManager.UImisc.removeInfoPopup();
											String text = ((EditText) Zonated.myApp.findViewById(R.id.text)).getText().toString();
											int position = ((Spinner) Zonated.myApp.findViewById(R.id.next)).getSelectedItemPosition();
											boolean checked = ((CheckBox) Zonated.myApp.findViewById(R.id.optional)).isChecked();
											modifyQuestion(question, text, position, checked);
											break;
										case 2:
											animationFrame = (ImageView) Zonated.myApp.findViewById(R.id.animation);
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
				ImageButton modify = new ImageButton(Zonated.myApp);
				modify.setImageResource(R.drawable.modify);
				modify.setBackgroundResource(R.drawable.iconbg);
				modify.setScaleType(ScaleType.FIT_XY);
				modify.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						modifyMultiCondition(question, condition);
					}
				});
				TextView text = new TextView(Zonated.myApp);
				if (condition.getNext() == null) text.append("Finish");
				else text.append("Jump to " + Zonated.myQuestControlManager.activeQuestions.get(condition.getNext()).getName());
				text.setWidth(realwidth/2);
				text.setTextSize((float) 20);
				text.setTextColor(Color.BLACK);
				text.setTypeface(Typeface.SANS_SERIF);
				buttonLayout.addView(modify, 40, 40);
				buttonLayout.addView(delete, 40, 40);
				buttonLayout.addView(text);
				((LinearLayout) Zonated.myApp.findViewById(R.id.conditions)).addView(buttonLayout);
			}
			ImageButton addcondition = (ImageButton) Zonated.myApp.findViewById(R.id.addcondition);
			addcondition.setVisibility(View.VISIBLE);
			addcondition.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					createNewMultiCondition(question);
				}
			});
		}
		else if (question instanceof NumericQuestion) {
			Array<Condition> conditions = Zonated.myQuestControlManager.activeConditions.get(question.getId());
			if (conditions == null) conditions = new Array<Condition>();
			((LinearLayout) Zonated.myApp.findViewById(R.id.conditions)).setVisibility(View.VISIBLE);
			((RelativeLayout) Zonated.myApp.findViewById(R.id.conditionhead)).setVisibility(View.VISIBLE);
			((TextView) Zonated.myApp.findViewById(R.id.conditionrequest)).setText(Zonated.myLanguageManager.QUEST_CONDITIONS + ": ");
			Iterator<Condition> condit = conditions.iterator();
			while (condit.hasNext()) {
				final NumericCondition condition = (NumericCondition) condit.next();
				TableRow buttonLayout = new TableRow(Zonated.myApp);
				buttonLayout.setOrientation(TableRow.HORIZONTAL);
				buttonLayout.setGravity(Gravity.CENTER_VERTICAL);
				buttonLayout.setLayoutParams(new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
				ImageButton delete = new ImageButton(Zonated.myApp);
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
								if (Zonated.myQuestControlManager.removeCondition(question.getId(), condition)) {
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
											animationFrame = (ImageView) Zonated.myApp.findViewById(R.id.animation);
											animationFrame.setVisibility(View.VISIBLE);
											animationFrame.setBackgroundResource(R.drawable.loading);
											animation = (AnimationDrawable) animationFrame.getBackground();
											animation.start();
											break;
										case 1:
											animationFrame = (ImageView) Zonated.myApp.findViewById(R.id.animation);
											animation = (AnimationDrawable) animationFrame.getBackground();
											animation.stop();
											animationFrame.setVisibility(View.GONE);
											myUIManager.UImisc.removeInfoPopup();
											String text = ((EditText) Zonated.myApp.findViewById(R.id.text)).getText().toString();
											int position = ((Spinner) Zonated.myApp.findViewById(R.id.next)).getSelectedItemPosition();
											boolean checked = ((CheckBox) Zonated.myApp.findViewById(R.id.optional)).isChecked();
											modifyQuestion(question, text, position, checked);
											break;
										case 2:
											animationFrame = (ImageView) Zonated.myApp.findViewById(R.id.animation);
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
				ImageButton modify = new ImageButton(Zonated.myApp);
				modify.setImageResource(R.drawable.modify);
				modify.setBackgroundResource(R.drawable.iconbg);
				modify.setScaleType(ScaleType.FIT_XY);
				modify.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						modifyNumericCondition(question, condition);
					}
				});
				TextView text = new TextView(Zonated.myApp);
				if (condition.getNext() == null) text.append("Finish");
				else text.append("Jump to " + Zonated.myQuestControlManager.activeQuestions.get(condition.getNext()).getName());
				text.setWidth(realwidth/2);
				text.setTextSize((float) 20);
				text.setTextColor(Color.BLACK);
				text.setTypeface(Typeface.SANS_SERIF);
				buttonLayout.addView(modify, 40, 40);
				buttonLayout.addView(delete, 40, 40);
				buttonLayout.addView(text);
				((LinearLayout) Zonated.myApp.findViewById(R.id.conditions)).addView(buttonLayout);
			}
			ImageButton addcondition = (ImageButton) Zonated.myApp.findViewById(R.id.addcondition);
			addcondition.setVisibility(View.VISIBLE);
			addcondition.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					createNewNumericCondition(question);
				}
			});
		}
		ImageButton save = (ImageButton) Zonated.myApp.findViewById(R.id.help);
		save.setVisibility(View.VISIBLE);
		save.setImageResource(R.drawable.save);
		save.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Runnable run = new Runnable() {
					@Override
					public void run() {
						handler.sendEmptyMessage(0);
						question.setName(((EditText) Zonated.myApp.findViewById(R.id.text)).getText().toString());
						int position = next.getSelectedItemPosition();
						question.setNext(idsArray.get(position));
						question.setOptional(((CheckBox) Zonated.myApp.findViewById(R.id.optional)).isSelected());
						if (Zonated.myQuestControlManager.updateQuestion(question)) {
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
									animationFrame = (ImageView) Zonated.myApp.findViewById(R.id.animation);
									animationFrame.setVisibility(View.VISIBLE);
									animationFrame.setBackgroundResource(R.drawable.loading);
									animation = (AnimationDrawable) animationFrame.getBackground();
									animation.start();
									break;
								case 1:
									animationFrame = (ImageView) Zonated.myApp.findViewById(R.id.animation);
									animation = (AnimationDrawable) animationFrame.getBackground();
									animation.stop();
									animationFrame.setVisibility(View.GONE);
									myUIManager.UImisc.removeInfoPopup();
									modifyQuestionnaire(Zonated.myQuestControlManager.activeQuestionnaire);
									break;
								case 2:
									animationFrame = (ImageView) Zonated.myApp.findViewById(R.id.animation);
									animation = (AnimationDrawable) animationFrame.getBackground();
									animation.stop();
									animationFrame.setVisibility(View.GONE);
									modifyQuestionnaire(Zonated.myQuestControlManager.activeQuestionnaire);
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
		ImageButton exit = (ImageButton) Zonated.myApp.findViewById(R.id.previous);
		exit.setVisibility(View.VISIBLE);
		exit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				modifyQuestionnaire(Zonated.myQuestControlManager.activeQuestionnaire);
			}
		});
	}
	
	public void createNewAnswer(final Question question) {
		LayoutInflater inflater = (LayoutInflater) Zonated.myApp.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		final View layout = inflater.inflate(R.layout.toastaddanswer,
		                               (ViewGroup) Zonated.myApp.findViewById(R.id.toastaddanswerroot));
		TextView title = (TextView) layout.findViewById(R.id.addtitle);
		title.setText(Zonated.myLanguageManager.QUEST_CREATE_ANSWER);
		TextView namereq = (TextView) layout.findViewById(R.id.namerequested);
		namereq.setText(Zonated.myLanguageManager.QUEST_TEXT + ": ");
		TextView valuereq = (TextView) layout.findViewById(R.id.valuerequest);
		valuereq.setText(Zonated.myLanguageManager.QUEST_WEIGHT + ": ");
		ImageButton image = (ImageButton) layout.findViewById(R.id.addeventok);
		((EditText) layout.findViewById(R.id.value)).setText("0.0");
		Builder builder = new AlertDialog.Builder(Zonated.myApp);
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
					Toast toast = Toast.makeText(Zonated.myApp, "The value must be a number", 3000);
					toast.show();
				}
				Runnable run = new Runnable() {
					@Override
					public void run() {
						handler.sendEmptyMessage(0);
						if (Zonated.myQuestControlManager.addAnswer(question.getId(), answer)) {
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
									animationFrame = (ImageView) Zonated.myApp.findViewById(R.id.animation);
									animationFrame.setVisibility(View.VISIBLE);
									animationFrame.setBackgroundResource(R.drawable.loading);
									animation = (AnimationDrawable) animationFrame.getBackground();
									animation.start();
									break;
								case 1:
									animationFrame = (ImageView) Zonated.myApp.findViewById(R.id.animation);
									animation = (AnimationDrawable) animationFrame.getBackground();
									animation.stop();
									animationFrame.setVisibility(View.GONE);
									myUIManager.UImisc.removeInfoPopup();
									String text = ((EditText) Zonated.myApp.findViewById(R.id.text)).getText().toString();
									int position = ((Spinner) Zonated.myApp.findViewById(R.id.next)).getSelectedItemPosition();
									boolean checked = ((CheckBox) Zonated.myApp.findViewById(R.id.optional)).isChecked();
									modifyQuestion(question, text, position, checked);
									break;
								case 2:
									animationFrame = (ImageView) Zonated.myApp.findViewById(R.id.animation);
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
		LayoutInflater inflater = (LayoutInflater) Zonated.myApp.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		final View layout = inflater.inflate(R.layout.toastaddanswer,
		                               (ViewGroup) Zonated.myApp.findViewById(R.id.toastaddanswerroot));
		TextView title = (TextView) layout.findViewById(R.id.addtitle);
		title.setText(Zonated.myLanguageManager.QUEST_CREATE_ANSWER);
		TextView namereq = (TextView) layout.findViewById(R.id.namerequested);
		namereq.setText(Zonated.myLanguageManager.QUEST_TEXT + ": ");
		((EditText) layout.findViewById(R.id.name)).setText(answer.getName());
		TextView valuereq = (TextView) layout.findViewById(R.id.valuerequest);
		valuereq.setText(Zonated.myLanguageManager.QUEST_WEIGHT + ": ");
		((EditText) layout.findViewById(R.id.value)).setText("" + answer.getValue());
		ImageButton image = (ImageButton) layout.findViewById(R.id.addeventok);
		Builder builder = new AlertDialog.Builder(Zonated.myApp);
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
					Toast toast = Toast.makeText(Zonated.myApp, "The weight must be a number. Now set to 0.0", 3000);
					toast.show();
				}
				Runnable run = new Runnable() {
					@Override
					public void run() {
						handler.sendEmptyMessage(0);
						if (Zonated.myQuestControlManager.updateAnswer(question.getId(), answer)) {
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
									animationFrame = (ImageView) Zonated.myApp.findViewById(R.id.animation);
									animationFrame.setVisibility(View.VISIBLE);
									animationFrame.setBackgroundResource(R.drawable.loading);
									animation = (AnimationDrawable) animationFrame.getBackground();
									animation.start();
									break;
								case 1:
									animationFrame = (ImageView) Zonated.myApp.findViewById(R.id.animation);
									animation = (AnimationDrawable) animationFrame.getBackground();
									animation.stop();
									animationFrame.setVisibility(View.GONE);
									myUIManager.UImisc.removeInfoPopup();
									String text = ((EditText) Zonated.myApp.findViewById(R.id.text)).getText().toString();
									int position = ((Spinner) Zonated.myApp.findViewById(R.id.next)).getSelectedItemPosition();
									boolean checked = ((CheckBox) Zonated.myApp.findViewById(R.id.optional)).isChecked();
									modifyQuestion(question, text, position, checked);
									break;
								case 2:
									animationFrame = (ImageView) Zonated.myApp.findViewById(R.id.animation);
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
		LayoutInflater inflater = (LayoutInflater) Zonated.myApp.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		final View layout = inflater.inflate(R.layout.toastaddcondition,
		                               (ViewGroup) Zonated.myApp.findViewById(R.id.toastaddconditionroot));
		((TextView) layout.findViewById(R.id.addtitle)).setText(Zonated.myLanguageManager.QUEST_CREATE_CONDITION);
		((TextView) layout.findViewById(R.id.nextrequest)).setText(Zonated.myLanguageManager.QUEST_NEXT + ": ");
		((TextView) layout.findViewById(R.id.answersrequest)).setText(Zonated.myLanguageManager.QUEST_ANSWERS + ": ");
		final Spinner next = (Spinner)layout.findViewById(R.id.nextquest);
		ArrayList<String> spinnerArray = new ArrayList<String>();
		Enumeration<Question> questions = Zonated.myQuestControlManager.activeQuestions.elements();
	    spinnerArray.add("-");
	    while (questions.hasMoreElements()) {
	    	i++;
	    	Question questToAdd = questions.nextElement();
	    	if (question.getId() == questToAdd.getId()) continue;
	    	if (question.getName().equals("")) continue;
	    	spinnerArray.add(questToAdd.getName());
	    }
	    ArrayAdapter<String> adapter = new ArrayAdapter<String>(Zonated.myApp, android.R.layout.simple_spinner_item, spinnerArray);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		next.setAdapter(adapter);
		next.setSelection(selectedByFirst);
	    final LinearLayout answerList = (LinearLayout) layout.findViewById(R.id.answers);
	    Iterator<Answer> answers = Zonated.myQuestControlManager.activeAnswers.get(question.getId()).iterator();
	    while (answers.hasNext()) {
			final Answer answer = answers.next();
			CheckBox checkbox = new CheckBox(Zonated.myApp);
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
		Builder builder = new AlertDialog.Builder(Zonated.myApp);
		builder.setView(layout);
		final AlertDialog alertDialog = builder.create();
		alertDialog.show();
		image.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				condition.getAnswers().clear();
				condition.getAnswers().addAll(answersToAdd);
				int position = next.getSelectedItemPosition();
				condition.setNext(Zonated.myQuestControlManager.searchQuestionByName(next.getItemAtPosition(position).toString()));
				Runnable run = new Runnable() {
					@Override
					public void run() {
						handler.sendEmptyMessage(0);
						if (Zonated.myQuestControlManager.addCondition(question.getId(), condition)) {
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
									animationFrame = (ImageView) Zonated.myApp.findViewById(R.id.animation);
									animationFrame.setVisibility(View.VISIBLE);
									animationFrame.setBackgroundResource(R.drawable.loading);
									animation = (AnimationDrawable) animationFrame.getBackground();
									animation.start();
									break;
								case 1:
									animationFrame = (ImageView) Zonated.myApp.findViewById(R.id.animation);
									animation = (AnimationDrawable) animationFrame.getBackground();
									animation.stop();
									animationFrame.setVisibility(View.GONE);
									myUIManager.UImisc.removeInfoPopup();
									String text = ((EditText) Zonated.myApp.findViewById(R.id.text)).getText().toString();
									int position = ((Spinner) Zonated.myApp.findViewById(R.id.next)).getSelectedItemPosition();
									boolean checked = ((CheckBox) Zonated.myApp.findViewById(R.id.optional)).isChecked();
									modifyQuestion(question, text, position, checked);
									break;
								case 2:
									animationFrame = (ImageView) Zonated.myApp.findViewById(R.id.animation);
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
		LayoutInflater inflater = (LayoutInflater) Zonated.myApp.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		final View layout = inflater.inflate(R.layout.toastaddcondition,
		                               (ViewGroup) Zonated.myApp.findViewById(R.id.toastaddconditionroot));
		((TextView) layout.findViewById(R.id.addtitle)).setText(Zonated.myLanguageManager.QUEST_CREATE_CONDITION);
		((TextView) layout.findViewById(R.id.nextrequest)).setText(Zonated.myLanguageManager.QUEST_NEXT + ": ");
		((TextView) layout.findViewById(R.id.answersrequest)).setText(Zonated.myLanguageManager.QUEST_ANSWERS + ": ");
		final Spinner next = (Spinner) layout.findViewById(R.id.nextquest);
		ArrayList<String> spinnerArray = new ArrayList<String>();
		Enumeration<Question> questions = Zonated.myQuestControlManager.activeQuestions.elements();
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
	    ArrayAdapter<String> adapter = new ArrayAdapter<String>(Zonated.myApp, android.R.layout.simple_spinner_item, spinnerArray);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		next.setAdapter(adapter);
		next.setSelection(selectedByFirst);
	    final LinearLayout answerList = (LinearLayout) layout.findViewById(R.id.answers);
	    Iterator<Answer> answers = Zonated.myQuestControlManager.activeAnswers.get(question.getId()).iterator();
	    while (answers.hasNext()) {
			final Answer answer = answers.next();
			CheckBox checkbox = new CheckBox(Zonated.myApp);
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
		Builder builder = new AlertDialog.Builder(Zonated.myApp);
		builder.setView(layout);
		final AlertDialog alertDialog = builder.create();
		alertDialog.show();
		image.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				condition.getAnswers().clear();
				condition.getAnswers().addAll(answersToAdd);
				int position = next.getSelectedItemPosition();
				condition.setNext(Zonated.myQuestControlManager.searchQuestionByName(next.getItemAtPosition(position).toString()));
				Runnable run = new Runnable() {
					@Override
					public void run() {
						handler.sendEmptyMessage(0);
						if (Zonated.myQuestControlManager.updateCondition(question.getId(), condition)) {
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
									animationFrame = (ImageView) Zonated.myApp.findViewById(R.id.animation);
									animationFrame.setVisibility(View.VISIBLE);
									animationFrame.setBackgroundResource(R.drawable.loading);
									animation = (AnimationDrawable) animationFrame.getBackground();
									animation.start();
									break;
								case 1:
									animationFrame = (ImageView) Zonated.myApp.findViewById(R.id.animation);
									animation = (AnimationDrawable) animationFrame.getBackground();
									animation.stop();
									animationFrame.setVisibility(View.GONE);
									myUIManager.UImisc.removeInfoPopup();
									String text = ((EditText) Zonated.myApp.findViewById(R.id.text)).getText().toString();
									int position = ((Spinner) Zonated.myApp.findViewById(R.id.next)).getSelectedItemPosition();
									boolean checked = ((CheckBox) Zonated.myApp.findViewById(R.id.optional)).isChecked();
									modifyQuestion(question, text, position, checked);
									break;
								case 2:
									animationFrame = (ImageView) Zonated.myApp.findViewById(R.id.animation);
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
		LayoutInflater inflater = (LayoutInflater) Zonated.myApp.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		final View layout = inflater.inflate(R.layout.toastaddnumericcondition,
		                               (ViewGroup) Zonated.myApp.findViewById(R.id.toastaddnumericconditionroot));
		((TextView) layout.findViewById(R.id.addtitle)).setText(Zonated.myLanguageManager.QUEST_CREATE_CONDITION);
		((TextView) layout.findViewById(R.id.nextrequest)).setText(Zonated.myLanguageManager.QUEST_NEXT + ": ");
		((TextView) layout.findViewById(R.id.limitrequest)).setText(Zonated.myLanguageManager.QUEST_THREESHOLD + ": ");
		final Spinner next = (Spinner) layout.findViewById(R.id.nextquest);
		ArrayList<String> spinnerArray = new ArrayList<String>();
		Enumeration<Question> questions = Zonated.myQuestControlManager.activeQuestions.elements();
	    spinnerArray.add("-");
	    while (questions.hasMoreElements()) {
	    	i++;
	    	Question questToAdd = questions.nextElement();
	    	if (question.getId() == questToAdd.getId()) continue;
	    	if (question.getName().equals("")) continue;
	    	spinnerArray.add(questToAdd.getName());
	    }
	    ArrayAdapter<String> adapter = new ArrayAdapter<String>(Zonated.myApp, android.R.layout.simple_spinner_item, spinnerArray);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		next.setAdapter(adapter);
		next.setSelection(selectedByFirst);
	    final SeekBar threshold = (SeekBar)layout.findViewById(R.id.limitquest);
		ImageButton image = (ImageButton) layout.findViewById(R.id.addeventok);
		Builder builder = new AlertDialog.Builder(Zonated.myApp);
		builder.setView(layout);
		final AlertDialog alertDialog = builder.create();
		alertDialog.show();
		image.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				condition.setThreshold(((Integer)threshold.getProgress()).doubleValue()/10);
				int position = next.getSelectedItemPosition();
				condition.setNext(Zonated.myQuestControlManager.searchQuestionByName(next.getItemAtPosition(position).toString()));
				Runnable run = new Runnable() {
					@Override
					public void run() {
						handler.sendEmptyMessage(0);
						if (Zonated.myQuestControlManager.addCondition(question.getId(), condition)) {
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
									animationFrame = (ImageView) Zonated.myApp.findViewById(R.id.popupImage);
									animationFrame.setVisibility(View.VISIBLE);
									animationFrame.setBackgroundResource(R.drawable.loading);
									animation = (AnimationDrawable) animationFrame.getBackground();
									animation.start();
									break;
								case 1:
									animationFrame = (ImageView) Zonated.myApp.findViewById(R.id.popupImage);
									animation = (AnimationDrawable) animationFrame.getBackground();
									animation.stop();
									animationFrame.setVisibility(View.GONE);
									myUIManager.UImisc.removeInfoPopup();
									String text = ((EditText) Zonated.myApp.findViewById(R.id.text)).getText().toString();
									int position = ((Spinner) Zonated.myApp.findViewById(R.id.next)).getSelectedItemPosition();
									boolean checked = ((CheckBox) Zonated.myApp.findViewById(R.id.optional)).isChecked();
									modifyQuestion(question, text, position, checked);
									break;
								case 2:
									animationFrame = (ImageView) Zonated.myApp.findViewById(R.id.popupImage);
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
		LayoutInflater inflater = (LayoutInflater) Zonated.myApp.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		final View layout = inflater.inflate(R.layout.toastaddnumericcondition,
		                               (ViewGroup) Zonated.myApp.findViewById(R.id.toastaddnumericconditionroot));
		((TextView) layout.findViewById(R.id.addtitle)).setText(Zonated.myLanguageManager.QUEST_CREATE_CONDITION);
		((TextView) layout.findViewById(R.id.nextrequest)).setText(Zonated.myLanguageManager.QUEST_NEXT + ": ");
		((TextView) layout.findViewById(R.id.limitrequest)).setText(Zonated.myLanguageManager.QUEST_THREESHOLD + ": ");
		final Spinner next = (Spinner) layout.findViewById(R.id.nextquest);
		ArrayList<String> spinnerArray = new ArrayList<String>();
		Enumeration<Question> questions = Zonated.myQuestControlManager.activeQuestions.elements();
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
	    ArrayAdapter<String> adapter = new ArrayAdapter<String>(Zonated.myApp, android.R.layout.simple_spinner_item, spinnerArray);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		next.setAdapter(adapter);
		next.setSelection(selectedByFirst);
	    final SeekBar threshold = (SeekBar)layout.findViewById(R.id.limitquest);
	    threshold.setProgress((int)(condition.getThreshold()*10));
		ImageButton image = (ImageButton) layout.findViewById(R.id.addeventok);
		Builder builder = new AlertDialog.Builder(Zonated.myApp);
		builder.setView(layout);
		final AlertDialog alertDialog = builder.create();
		alertDialog.show();
		image.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				condition.setThreshold(((Integer)threshold.getProgress()).doubleValue()/10);
				int position = next.getSelectedItemPosition();
				condition.setNext(Zonated.myQuestControlManager.searchQuestionByName(next.getItemAtPosition(position).toString()));
				Runnable run = new Runnable() {
					@Override
					public void run() {
						handler.sendEmptyMessage(0);
						if (Zonated.myQuestControlManager.addCondition(question.getId(), condition)) {
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
									animationFrame = (ImageView) Zonated.myApp.findViewById(R.id.popupImage);
									animationFrame.setVisibility(View.VISIBLE);
									animationFrame.setBackgroundResource(R.drawable.loading);
									animation = (AnimationDrawable) animationFrame.getBackground();
									animation.start();
									break;
								case 1:
									animationFrame = (ImageView) Zonated.myApp.findViewById(R.id.popupImage);
									animation = (AnimationDrawable) animationFrame.getBackground();
									animation.stop();
									animationFrame.setVisibility(View.GONE);
									myUIManager.UImisc.removeInfoPopup();
									String text = ((EditText) Zonated.myApp.findViewById(R.id.text)).getText().toString();
									int position = ((Spinner) Zonated.myApp.findViewById(R.id.next)).getSelectedItemPosition();
									boolean checked = ((CheckBox) Zonated.myApp.findViewById(R.id.optional)).isChecked();
									modifyQuestion(question, text, position, checked);
									break;
								case 2:
									animationFrame = (ImageView) Zonated.myApp.findViewById(R.id.popupImage);
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
	
	public void showQuestResults(Questionnaire quest, QuestGlobalResult results) {                                                                           
		Zonated.myApp.setContentView(R.layout.resultscreen);
		((TextView) Zonated.myApp.findViewById(R.id.startText)).setText(Zonated.myLanguageManager.CALENDAR_MEASUREMENT_RESULT);
		((TextView) Zonated.myApp.findViewById(R.id.startText)).setTextSize(24);
		TextView result = ((TextView) Zonated.myApp.findViewById(R.id.result));
		result.setGravity(Gravity.LEFT);
		ImageButton back = (ImageButton) Zonated.myApp.findViewById(R.id.previous);
		result.setText(getQuestionnaireResultSpanData(quest, results));
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				loadQuestionnairesResults();
			}
		});
	}
	
	public Spanned getQuestionnaireResultSpanData(Questionnaire quest, QuestGlobalResult results) {
		Hashtable<Long, Answer> answerPos = new Hashtable<Long, Answer>();
		Array<Answer> answersPostemp = new Array<Answer>();
		Enumeration<Question> elements = Zonated.myQuestControlManager.activeQuestions.elements();
		while (elements.hasMoreElements()) {
			Array<Answer> ans = Zonated.myQuestControlManager.activeAnswers.get(elements.nextElement().getId());
			if (ans != null) answersPostemp.addAll(ans);
		}
		for (Answer answer : answersPostemp) {
			answerPos.put(answer.getId(), answer);
		}
		String spaning = "<big><b>" + Zonated.myLanguageManager.PATIENTS_HISTORY_QUESTIONNAIRE + " " + quest.getName() + "</b></big><br/><br/>";
		spaning += "<b>" + Zonated.myLanguageManager.QUEST_SAMPLES + "</b>" + results.people + "<br/><br/>";
		Enumeration<Long> keys = results.results.keys();
		while (keys.hasMoreElements()) {
			Long questId = keys.nextElement();
			QuestionResult result = results.results.get(questId);
			spaning += "<p><b>" + Zonated.myQuestControlManager.activeQuestions.get(questId).getName() + "</b><br/>";
			switch (result.type) {
				case 0:
					spaning += "<b>" + Zonated.myLanguageManager.QUEST_ANSWEREDBY + "</b>" + Math.round(((double)result.singlepeople*100)/(double) results.people) + " %" + "<br/>";
					for (Answer answer : Zonated.myQuestControlManager.activeAnswers.get(questId)) {
						int answersnum = 0;
						if (result.singleanswers.containsKey(answer.getId())) answersnum = result.singleanswers.get(answer.getId());
						spaning += "<b>" + answer.getName() + ": </b>" + Math.round(((double)answersnum*100)/(double) results.people) + " %" + "<br/>";
					}
					break;
				case 1:
					spaning += "<b>" + Zonated.myLanguageManager.QUEST_ANSWEREDBY + "</b>" + Math.round(((double)result.numpeople*100)/(double) results.people) + " %" + "<br/>";
					spaning += "<b>" + Zonated.myLanguageManager.QUEST_AVERAGE + "</b>" + result.numavrg + "<br/>";
					spaning += "<b>" + Zonated.myLanguageManager.QUEST_LESSOF5 + "</b>" + Math.round(((double)result.numlessof5*100)/(double) results.people) + " %" + "<br/>";
					spaning += "<b>" + Zonated.myLanguageManager.QUEST_5ORMORE + "</b>" + Math.round(((double)result.num5ormore*100)/(double) results.people) + " %" + "<br/>";
					break;
				case 2:
					spaning += "<b>" + Zonated.myLanguageManager.QUEST_ANSWEREDBY + "</b>" + Math.round(((double)result.textpeople*100)/(double) results.people) + " %" + "<br/>";
					spaning += Zonated.myLanguageManager.QUEST_ANSWERS + ": " + "<br/>";
					for (String text : result.textanswers) {
						spaning += " - " + text + "<br/>";
					}
					spaning += "</p>";
					break;
				default:
					break;
			}
		}
		return Html.fromHtml(spaning);
	}

}
