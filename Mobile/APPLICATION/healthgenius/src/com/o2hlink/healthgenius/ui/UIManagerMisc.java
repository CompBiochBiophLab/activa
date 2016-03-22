package com.o2hlink.healthgenius.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Message;
import android.text.Html;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.o2hlink.healthgenius.HealthGenius;
import com.o2hlink.healthgenius.HealthGeniusConfig;
import com.o2hlink.healthgenius.R;

public class UIManagerMisc {
	
	public UIManager myUIManager;
	
	public UIManagerMisc(UIManager ui) {
		myUIManager = ui;
	}
	
	/**
	 * Load a message in a text outside the app
	 * @param text
	 */
	public void loadTextOnWindow(final String text) {
		Message msg = new Message();
		Bundle data = new Bundle();
		data.putString("0", text);
		msg.setData(data);
		HealthGenius.myApp.handler.sendMessage(msg);
	}

	/**
	 * Load a message in a text inside the app
	 * @param string
	 */
	public void loadInfoPopup(String string) {
		final RelativeLayout popupView = (RelativeLayout) HealthGenius.myApp.findViewById(R.id.popupView);
		popupView.setVisibility(View.VISIBLE);
		TextView popupText = (TextView) HealthGenius.myApp.findViewById(R.id.popupText);
		popupText.setText(string);
		CountDownTimer timer = new CountDownTimer(5000, 1000) {
			@Override
			public void onTick(long millisUntilFinished) {
			}
			@Override
			public void onFinish() {
				popupView.setVisibility(View.INVISIBLE);			
			}
		};
		timer.start();
	}

	/**
	 * Load a message in a text inside the app of long duration
	 * @param string
	 */
	public void loadInfoPopupLong(String string) {
		final RelativeLayout popupView = (RelativeLayout) HealthGenius.myApp.findViewById(R.id.popupView);
		popupView.setVisibility(View.VISIBLE);
		TextView popupText = (TextView) HealthGenius.myApp.findViewById(R.id.popupText);
		popupText.setText(string);
		CountDownTimer timer = new CountDownTimer(10000, 1000) {
			@Override
			public void onTick(long millisUntilFinished) {
			}
			@Override
			public void onFinish() {
				popupView.setVisibility(View.INVISIBLE);			
			}
		};
		timer.start();
	}
	
	/**
	 * Load a message in a text inside the app of infinite duration
	 * @param string
	 */
	public void loadInfoPopupWithoutExiting(String string) {
		final RelativeLayout popupView = (RelativeLayout) HealthGenius.myApp.findViewById(R.id.popupView);
		popupView.setVisibility(View.VISIBLE);
		TextView popupText = (TextView) HealthGenius.myApp.findViewById(R.id.popupText);
		popupText.setText(string);
	}
	

	/**
	 * Remove a message in a text inside the
	 */
	public void removeInfoPopup() {
		final RelativeLayout popupView = (RelativeLayout) HealthGenius.myApp.findViewById(R.id.popupView);
		popupView.setVisibility(View.GONE);
	}
	
	/**
	 * Method for going to O2H Link page
	 */
	public void goToO2HLinkWebPage() {
		HealthGenius.myApp.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.o2hlink.com")));
	}
	
	public void loadContactWithUs() {
		final CharSequence[] items = {HealthGenius.myLanguageManager.MAIN_SUPPORT, 
				HealthGenius.myLanguageManager.MAIN_FACEBOOKPROFILE};
		AlertDialog.Builder builder = new AlertDialog.Builder(HealthGenius.myApp);
		builder.setTitle(HealthGenius.myLanguageManager.MAIN_CONTACUS);
		builder.setItems(items, new DialogInterface.OnClickListener() {
		    public void onClick(DialogInterface dialog, int item) {
		        switch (item) {
					case 0:
						String url="mailto:" + HealthGeniusConfig.ACTIV8SUPPORT_URL + "?subject=ActivA%20Beta%20support";
						Intent in = new Intent(Intent.ACTION_VIEW);
						in.setData(Uri.parse(url));
						HealthGenius.myApp.startActivity(in);
						break;
					case 1:
						Intent in2 = new Intent(Intent.ACTION_VIEW);
						in2.setData(Uri.parse(HealthGeniusConfig.ACTIV8FACEBOOK_URL));
						HealthGenius.myApp.startActivity(in2);
						break;
					default:
						break;
				}
		    }
		});
		AlertDialog alert = builder.create();
		alert.show();
	}
	
	public void loadAboutUs() {
		AlertDialog.Builder builder = new AlertDialog.Builder(HealthGenius.myApp);
		builder.setMessage(Html.fromHtml(String.format(HealthGenius.myLanguageManager.TEXT_ABOUTUS, HealthGenius.myApp.getResources().getString(R.string.app_version))));
		builder.setPositiveButton(HealthGenius.myLanguageManager.MAIN_TERMSANDCONDS, new Dialog.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
	        	Intent in = new Intent(Intent.ACTION_VIEW);
				in.setData(Uri.parse(HealthGeniusConfig.ACTIV8TERMSANDCONDS_URL));
				HealthGenius.myApp.startActivity(in);
			}
		});
		builder.setNegativeButton(HealthGenius.myLanguageManager.MAIN_PRIVACYCONDS, new Dialog.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
	        	Intent in = new Intent(Intent.ACTION_VIEW);
				in.setData(Uri.parse(HealthGeniusConfig.ACTIV8TERMSANDCONDS_URL));
				HealthGenius.myApp.startActivity(in);
			}
		});
		builder.show();
	}

}
