package com.o2hlink.activa.background;

import com.o2hlink.activa.Activa;
import com.o2hlink.activa.ActivaConfig;
import com.o2hlink.activa.exceptions.NotUpdatedException;
import com.o2hlink.activa.ui.UIManager;

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
				try {
					Activa.myProtocolManager.refresh();
				} catch (NotUpdatedException e) {
					Activa.myUIManager.loadTextOnWindow(Activa.myLanguageManager.TEXT_UPDATEVERSION);
					e.printStackTrace();
				}
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

