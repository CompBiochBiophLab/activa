package com.o2hlink.activa.background;

import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;

import com.o2hlink.activa.Activa;
import com.o2hlink.activa.ActivaConfig;
import com.o2hlink.activa.R;
import com.o2hlink.activa.data.calendar.Event;
import com.o2hlink.activa.ui.UIManager;

import android.content.Context;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainThread implements Runnable {
	
	public boolean running;
	
	public MainThread() {
		running = false;
	}

	@Override
	public void run() {		
		running = true;	
		while(running) {
			try {
				/* Wait to the next time to be updated */
				long times = ActivaConfig.UPDATES_TIMEOUT/1000;
				for (int i = 0; i < times; i++) {
					Thread.sleep(1000);
					if (!running) break;
				}
				while (Activa.myUIManager.state != UIManager.UI_STATE_MAIN) Thread.sleep(1000);
				if (!running) break;
				/* Refreshing of the whole state */
				Activa.myProtocolManager.refresh();
//				RefreshingConnection refreshing = new RefreshingConnection();
//				Thread thread = new Thread(refreshing, "REFRESH");
//				thread.start();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void stop() {
		this.running = false;
//		throw new InterruptedException();
	}

}

