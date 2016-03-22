package com.o2hlink.zonated.ui;

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

import com.o2hlink.zonated.R;
import com.o2hlink.zonated.Zonated;
import com.o2hlink.zonated.ZonatedConfig;

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
		Zonated.myApp.handler.sendMessage(msg);
	}

	/**
	 * Load a message in a text inside the app
	 * @param string
	 */
	public void loadInfoPopup(String string) {
		final RelativeLayout popupView = (RelativeLayout) Zonated.myApp.findViewById(R.id.popupView);
		popupView.setVisibility(View.VISIBLE);
		TextView popupText = (TextView) Zonated.myApp.findViewById(R.id.popupText);
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
		final RelativeLayout popupView = (RelativeLayout) Zonated.myApp.findViewById(R.id.popupView);
		popupView.setVisibility(View.VISIBLE);
		TextView popupText = (TextView) Zonated.myApp.findViewById(R.id.popupText);
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
		final RelativeLayout popupView = (RelativeLayout) Zonated.myApp.findViewById(R.id.popupView);
		popupView.setVisibility(View.VISIBLE);
		TextView popupText = (TextView) Zonated.myApp.findViewById(R.id.popupText);
		popupText.setText(string);
	}
	

	/**
	 * Remove a message in a text inside the
	 */
	public void removeInfoPopup() {
		final RelativeLayout popupView = (RelativeLayout) Zonated.myApp.findViewById(R.id.popupView);
		popupView.setVisibility(View.GONE);
	}
	
	/**
	 * Method for going to O2H Link page
	 */
	public void goToO2HLinkWebPage() {
		Zonated.myApp.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.o2hlink.com")));
	}
	
	public void loadContactWithUs() {
		final CharSequence[] items = {Zonated.myLanguageManager.MAIN_SUPPORT, 
				Zonated.myLanguageManager.MAIN_FACEBOOKPROFILE};
		AlertDialog.Builder builder = new AlertDialog.Builder(Zonated.myApp);
		builder.setTitle(Zonated.myLanguageManager.MAIN_CONTACUS);
		builder.setItems(items, new DialogInterface.OnClickListener() {
		    public void onClick(DialogInterface dialog, int item) {
		        switch (item) {
					case 0:
						String url="mailto:" + ZonatedConfig.ACTIV8SUPPORT_URL + "?subject=ActivA%20Beta%20support";
						Intent in = new Intent(Intent.ACTION_VIEW);
						in.setData(Uri.parse(url));
						Zonated.myApp.startActivity(in);
						break;
					case 1:
						Intent in2 = new Intent(Intent.ACTION_VIEW);
						in2.setData(Uri.parse(ZonatedConfig.ACTIV8FACEBOOK_URL));
						Zonated.myApp.startActivity(in2);
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
		AlertDialog.Builder builder = new AlertDialog.Builder(Zonated.myApp);
		builder.setMessage(Html.fromHtml(String.format(Zonated.myLanguageManager.TEXT_ABOUTUS, Zonated.myApp.getResources().getString(R.string.app_version))));
		builder.setPositiveButton(Zonated.myLanguageManager.MAIN_TERMSANDCONDS, new Dialog.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
	        	Intent in = new Intent(Intent.ACTION_VIEW);
				in.setData(Uri.parse(ZonatedConfig.ACTIV8TERMSANDCONDS_URL));
				Zonated.myApp.startActivity(in);
			}
		});
		builder.setNegativeButton(Zonated.myLanguageManager.MAIN_PRIVACYCONDS, new Dialog.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
	        	Intent in = new Intent(Intent.ACTION_VIEW);
				in.setData(Uri.parse(ZonatedConfig.ACTIV8TERMSANDCONDS_URL));
				Zonated.myApp.startActivity(in);
			}
		});
		builder.show();
	}

}
