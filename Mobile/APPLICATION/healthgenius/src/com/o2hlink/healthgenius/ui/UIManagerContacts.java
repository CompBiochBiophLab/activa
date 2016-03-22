package com.o2hlink.healthgenius.ui;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;

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
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageView.ScaleType;
import android.widget.TableLayout.LayoutParams;

import com.o2hlink.activ8.client.entity.Array;
import com.o2hlink.activ8.client.entity.Contact;
import com.o2hlink.activ8.client.entity.ContactContactRequest;
import com.o2hlink.healthgenius.HealthGenius;
import com.o2hlink.healthgenius.R;

public class UIManagerContacts {
	
	public UIManager myUIManager;
	
	public UIManagerContacts(UIManager ui) {
		myUIManager = ui;
	}
	
	public void loadContactList(final boolean returntomain) {
		myUIManager.state = UIManager.UI_STATE_CONTACTS;
//		if (HealthGenius.myMenu != null) {
//			HealthGenius.myMenu.clear();
//			HealthGenius.myInflater.inflate(R.menu.messages, HealthGenius.myMenu);
//		}
		HealthGenius.myApp.setContentView(R.layout.list);
		((TextView) HealthGenius.myApp.findViewById(R.id.startText)).setText(HealthGenius.myLanguageManager.MESSAGES_CONTACTS);
		int realwidth = HealthGenius.myApp.getResources().getDisplayMetrics().widthPixels;
		//TABS
		//FOLLOW
		TableLayout questlisting = (TableLayout)HealthGenius.myApp.findViewById(R.id.listing);
		Enumeration<Contact> enumer = HealthGenius.myContactsManager.contactList.elements();
		while (enumer.hasMoreElements()) {			
			final Contact contact = enumer.nextElement();
			if (contact.getId().equals(Long.toString(HealthGenius.myMobileManager.userForServices.getId()))) continue;
			TableRow buttonLayout = new TableRow(HealthGenius.myApp);
			buttonLayout.setOrientation(TableRow.HORIZONTAL);
			buttonLayout.setGravity(Gravity.CENTER_VERTICAL);
			buttonLayout.setLayoutParams(new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));			
			ImageButton button = new ImageButton(HealthGenius.myApp);
			button.setBackgroundResource(R.drawable.iconbg);
			button.setImageResource(R.drawable.accounts);
			OnClickListener listener2 = new OnClickListener() {
				@Override
				public void onClick(View v) {
				}
			};
			TextView text = new TextView(HealthGenius.myApp);
			text.append(contact.getFirstName() + " " + contact.getLastName());
			text.setWidth(realwidth/2);
			text.setTextSize((float) 20);
			text.setTextColor(Color.BLACK);
			text.setTypeface(Typeface.SANS_SERIF);
			ImageButton delete = new ImageButton(HealthGenius.myApp);
			delete.setBackgroundResource(R.drawable.iconbg);
			delete.setImageResource(R.drawable.delete);
			delete.setScaleType(ScaleType.FIT_XY);
			delete.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					AlertDialog.Builder builder = new AlertDialog.Builder(HealthGenius.myApp);
					builder.setMessage(HealthGenius.myLanguageManager.TEXT_REMOVECONTACT)
					       .setCancelable(false)
					       .setPositiveButton(HealthGenius.myLanguageManager.MAIN_QUIT_YES, new DialogInterface.OnClickListener() {
					           public void onClick(DialogInterface dialog, int id) {
					        	   Runnable run = new Runnable() {
										@Override
										public void run() {
											handler.sendEmptyMessage(0);
											if (HealthGenius.myContactsManager.removeContact(contact)) {
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
														loadContactList(true);
														break;
													case 2:
														animationFrame = (ImageView) HealthGenius.myApp.findViewById(R.id.animation);
														animation = (AnimationDrawable) animationFrame.getBackground();
														animation.stop();
														animationFrame.setVisibility(View.GONE);
														HealthGenius.myUIManager.UImisc.loadInfoPopup("Remove failed");
														break;
												}
											}
										};
									};
									Thread thread = new Thread(run);
									thread.start();
					           }
					       })
					       .setNegativeButton(HealthGenius.myLanguageManager.MAIN_QUIT_NO, new DialogInterface.OnClickListener() {
					           public void onClick(DialogInterface dialog, int id) {
					                dialog.cancel();
					           }
					       });
					AlertDialog alert = builder.create();
					alert.show();
				}
			});
			buttonLayout.addView(button);
			buttonLayout.addView(text);
			buttonLayout.addView(delete, 50, 50);
			button.setOnClickListener(listener2);
			text.setOnClickListener(listener2);
			questlisting.addView(buttonLayout);
		}
		ImageButton back = (ImageButton) HealthGenius.myApp.findViewById(R.id.previous);
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (returntomain) HealthGenius.myUIManager.UIquest.loadSharedQuestionnaires();
				else HealthGenius.myUIManager.UIoptions.showOptions();
			}
		});
		ImageButton change = (ImageButton) HealthGenius.myApp.findViewById(R.id.help);
		change.setImageResource(R.drawable.searchcontact);
		change.setVisibility(View.VISIBLE);
		change.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				searchContacts();			
			}
		});
		ImageView addquest = (ImageView) HealthGenius.myApp.findViewById(R.id.animation);
		addquest.setVisibility(View.GONE);
		ImageView toentrycontacts = (ImageView) HealthGenius.myApp.findViewById(R.id.animation);
		toentrycontacts.setVisibility(View.VISIBLE);
		toentrycontacts.setImageResource(R.drawable.msgcontactsentering);
		toentrycontacts.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				loadEntryContactList(true);
			}
		});
	}
	
	public void loadEntryContactList (boolean returntocontacts) {
		final ArrayList<ContactContactRequest> ContactsToAccept = new ArrayList<ContactContactRequest>();
		final ArrayList<ContactContactRequest> ContactsToReject = new ArrayList<ContactContactRequest>();
		final ContactContactRequest[] items = new ContactContactRequest[HealthGenius.myContactsManager.entryContactList.size()];
		Iterator<ContactContactRequest> cons = HealthGenius.myContactsManager.entryContactList.iterator();
		int i = 0;
		while (cons.hasNext()) {
			items[i] = cons.next();
			i++;
		}
		ListView list = new ListView(HealthGenius.myApp);
		ListAdapter adapter = new ArrayAdapter<ContactContactRequest>(HealthGenius.myApp,android.R.layout.select_dialog_item,
			    android.R.id.text1,items){
			public View getView(final int position, View convertView, ViewGroup parent) {
				int realwidth = HealthGenius.myApp.getResources().getDisplayMetrics().widthPixels;
				LinearLayout layout = new LinearLayout(HealthGenius.myApp);
				layout.setLayoutParams(new ListView.LayoutParams(ListView.LayoutParams.FILL_PARENT, ListView.LayoutParams.WRAP_CONTENT));
			    layout.setOrientation(LinearLayout.HORIZONTAL);
				TextView tv = new TextView(HealthGenius.myApp);
				tv.setText(items[position].getRequester().getFirstName() + " " + items[position].getRequester().getLastName());
			    //Add margin between image and text (support various screen densities)
			    int dp5 = (int) (5 * HealthGenius.myApp.getResources().getDisplayMetrics().density + 0.5f);
			    tv.setCompoundDrawablePadding(dp5);
			    tv.setTextColor(Color.BLACK);
			    tv.setLayoutParams(new LinearLayout.LayoutParams(realwidth*1/2, LinearLayout.LayoutParams.WRAP_CONTENT));
			    final CheckBox checktrue = new CheckBox(HealthGenius.myApp);
			    checktrue.setLayoutParams(new LinearLayout.LayoutParams(60, 60));
			    final CheckBox checkfalse = new CheckBox(HealthGenius.myApp);
			    checkfalse.setLayoutParams(new LinearLayout.LayoutParams(60, 60));
			    checktrue.setButtonDrawable(R.drawable.checkboxtruewid);
			    checktrue.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
						if (isChecked) ContactsToAccept.add(items[position]);
						else if (ContactsToAccept.contains(items[position])) ContactsToAccept.remove(items[position]);
					}
				});
			    checkfalse.setButtonDrawable(R.drawable.checkboxfalsewid);
			    checkfalse.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//						if (checktrue.isChecked()) {
//							checktrue.setChecked(false);
//						}
						if (isChecked) ContactsToReject.add(items[position]);
						else if (ContactsToReject.contains(items[position])) ContactsToReject.remove(items[position]);
					}
				});
			    LinearLayout separator = new LinearLayout(HealthGenius.myApp);
			    separator.setLayoutParams(new LinearLayout.LayoutParams(dp5, LinearLayout.LayoutParams.WRAP_CONTENT));
			    layout.addView(tv);
			    layout.addView(checktrue);
			    layout.addView(separator);
			    layout.addView(checkfalse);
			    return layout;
			}
		};
		list.setAdapter(adapter);
		new AlertDialog.Builder(HealthGenius.myApp)
	    .setTitle(HealthGenius.myLanguageManager.MESSAGES_ENTRYCONTACTS)
	    .setView(list)
	    .setInverseBackgroundForced(true)
	    .setPositiveButton(HealthGenius.myApp.getResources().getString(R.string.oklink), new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Iterator<ContactContactRequest> iterator = ContactsToAccept.iterator();
				while (iterator.hasNext()) {
					ContactContactRequest request = iterator.next();
					HealthGenius.myContactsManager.AcceptContact(request);
				}
				iterator = ContactsToReject.iterator();
				while (iterator.hasNext()) {
					HealthGenius.myContactsManager.RejectContact(iterator.next());
				}
			}
		})
	    .setNegativeButton(HealthGenius.myApp.getResources().getString(R.string.cancellink), new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		})
	    .show();
	}
	
	public void searchContacts() {
		LayoutInflater inflater = (LayoutInflater) HealthGenius.myApp.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		final View layout = inflater.inflate(R.layout.toastsearch,
		                               (ViewGroup) HealthGenius.myApp.findViewById(R.id.toastsearchroot));
		TextView text = (TextView) layout.findViewById(R.id.searchexpl);
		text.setText(HealthGenius.myLanguageManager.NOTIFICATION_SEARCH_EXPL);
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
				final ProgressDialog dialog = ProgressDialog.show(HealthGenius.myApp, HealthGenius.myLanguageManager.NOTIFICATION_SEARCH_TITLE, 
						HealthGenius.myLanguageManager.NOTIFICATION_SEARCH_SEARCHING, true);
				dialog.show();
				Runnable run =  new Runnable() {
					Array<Contact> users;
					@Override
					public void run() {
						users = HealthGenius.myContactsManager.SearchContacts(query);
						dialog.cancel();
						if (users == null) {
							handler.sendEmptyMessage(0);
						} else if (users.size() == 0) {
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
									Toast toast2 = Toast.makeText(HealthGenius.myApp, HealthGenius.myLanguageManager.NOTIFICATION_SEARCH_NOTFOUND, Toast.LENGTH_SHORT);
									toast2.show();
									break;
								case 2:
									final CharSequence[] items = new CharSequence[users.size()];
									final Array<Contact>usersToAdd = new Array<Contact>();
									for (int i = 0; i < users.size(); i++) {
										items[i] = users.get(i).getFirstName() + " " + users.get(i).getLastName();
									}
									AlertDialog.Builder builder = new AlertDialog.Builder(HealthGenius.myApp);
									builder.setTitle(HealthGenius.myLanguageManager.NOTIFICATION_SEARCH_ADD);
									builder.setMultiChoiceItems(items, null, new OnMultiChoiceClickListener() {
										@Override
										public void onClick(DialogInterface dialog, int which, boolean isChecked) {
											if (isChecked) usersToAdd.add(users.get(which));
											else usersToAdd.remove(users.get(which));
										}
									});
									builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
								           public void onClick(DialogInterface dialog, int id) {
								        	   for (int i = 0; i < usersToAdd.size(); i++) {
								        		   HealthGenius.myContactsManager.AddContact(usersToAdd.get(i));
								        		   loadContactList(true);
								        	   }
								               dialog.cancel();
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

	public void removeContacts() {
		int i = 0;
		final CharSequence[] items = new CharSequence[HealthGenius.myContactsManager.contactList.size()];
		final Contact[] contactitems = new Contact[HealthGenius.myContactsManager.contactList.size()];
		final Array<Contact>usersToRem = new Array<Contact>();
		Enumeration<Contact> elements = HealthGenius.myContactsManager.contactList.elements();
		while (elements.hasMoreElements()) {
			Contact user = elements.nextElement();
			items[i] = user.getFirstName() + " " + user.getLastName();
			contactitems[i] = user;
			i++;
		}
		AlertDialog.Builder builder = new AlertDialog.Builder(HealthGenius.myApp);
		builder.setTitle(HealthGenius.myLanguageManager.NOTIFICATION_PATIENT_ADD);
		builder.setMultiChoiceItems(items, null, new OnMultiChoiceClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which, boolean isChecked) {
				if (isChecked) usersToRem.add(contactitems[which]);
				else usersToRem.remove(contactitems[which]);
			}
		});
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
	           public void onClick(DialogInterface dialog, int id) {
	        	   for (int i = 0; i < usersToRem.size(); i++) {
	        		   HealthGenius.myContactsManager.removeContact(usersToRem.get(i));
	        	   }
	               dialog.cancel();
	           }
	    });
		AlertDialog alert = builder.create();
		alert.show();
	}
	
}
