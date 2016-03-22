package com.o2hlink.activa.background;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashSet;
import java.util.Iterator;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.widget.RemoteViews;

import com.o2hlink.activa.Activa;
import com.o2hlink.activa.ActivaConfig;
import com.o2hlink.activa.R;
import com.o2hlink.activa.exceptions.NotUpdatedException;
import com.o2hlink.activa.ui.UIManager;
import com.o2hlink.activa.ui.widget.Ambient;

public class DownloadFiles implements Runnable {

	String ambientname;
	String ambienturl;
	HashSet<String> files;
	
	public DownloadFiles(String ambientname, String ambienturl, HashSet<String> files) {
		this.ambientname = ambientname;
		this.ambienturl = ambienturl;
		this.files = files;
	}

	@Override
	public void run() {
		try{
			boolean successful = true;
			int progress = 0;
			
			Activa.ambientsdownloading.add(ambientname);
			
			NotificationManager mManager = (NotificationManager) Activa.myApp.getSystemService(Context.NOTIFICATION_SERVICE);
			Notification notification = new Notification(R.drawable.icon, "ActivA", System.currentTimeMillis());
			
			notification.contentView = new RemoteViews(Activa.myApp.getPackageName(), R.layout.progressstatusbar);
			notification.contentView.setProgressBar(R.id.progressbar, this.files.size(), progress, true);
			notification.contentView.setTextViewText(R.id.progresstext, String.format(Activa.myLanguageManager.NOTIFICATION_DOWNLOADING_FILES, progress, this.files.size()));
			
			Intent notificationIntent = new Intent(Activa.myApp, Activa.class);
			PendingIntent contentIntent = PendingIntent.getActivity(Activa.myApp, 0, notificationIntent, 0);
			notification.contentIntent = contentIntent;
			
			mManager.notify(42, notification);
			
			Iterator<String> it = files.iterator();
			File originalfolder = new File(Environment.getExternalStorageDirectory(), ActivaConfig.ENVIRONMENT_FOLDER);
			File userfolder = new File(originalfolder, Activa.myMobileManager.user.getName());
//			switch (Activa.myMobileManager.user.getType()) {
//				case 0:
//					userfolder = new File(originalfolder, FOLDER_PATIENT);
//					break;
//				case 1:
//					userfolder = new File(originalfolder, FOLDER_CLINICIAN);
//					break;
//				case 2:
//					userfolder = new File(originalfolder, FOLDER_RESEARCHER);
//					break;
//				default:
//					break;
//			}
			File ambient = new File(userfolder, this.ambientname);
			if (!ambient.exists()) {
				ambient.mkdirs();
			}
			while (it.hasNext()) {
				String file = it.next();
				boolean success = downloadFile(this.ambienturl, file, ambient);
				progress++;
				notification.contentView.setProgressBar(R.id.progressbar, files.size(), progress, false);        
				notification.contentView.setTextViewText(R.id.progresstext, String.format(Activa.myLanguageManager.NOTIFICATION_DOWNLOADING_FILES, progress, this.files.size()));
				mManager.notify(42, notification);
				if (!success) {
					successful = false;
					break;
				}
			}
			Activa.ambientsdownloading.remove(ambientname);
			
			notification.flags = Notification.FLAG_AUTO_CANCEL;
			if (successful) notification.contentView.setTextViewText(R.id.progresstext, Activa.myLanguageManager.NOTIFICATION_DOWNLOAD_COMPLETE);
			else notification.contentView.setTextViewText(R.id.progresstext, Activa.myLanguageManager.NOTIFICATION_DOWNLOAD_FAIL);
			mManager.notify(42, notification);
			
			if (Activa.myUIManager.state == UIManager.UI_STATE_AMBIENTS) handler.sendEmptyMessage(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean downloadFile(String folderurl, String filename, File folder) {
		File file = null;
		try {
			URL filetodownload = new URL(folderurl + "/" + filename);
		    HttpURLConnection connection = (HttpURLConnection) filetodownload.openConnection();
		    connection.setRequestMethod("GET");
		    connection.setDoOutput(true);
		    connection.connect();
		    file = new File(folder, filename);
		    if ((!file.exists())) file.createNewFile();
		    else file.delete();
			FileOutputStream fout = new FileOutputStream(file);
			InputStream in = connection.getInputStream();
			byte[] buffer = new byte[1024];
			int len1 = 0;
			while ( (len1 = in.read(buffer)) > 0 ) {
			    fout.write(buffer,0, len1);
			}
			fout.close();
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			if (file != null) file.delete();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			if (file != null) file.delete();
			return false;
		}
	}
	
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case 0:
					Activa.myUIManager.ambients = Ambient.getAmbientsForDownloading();
				try {
					Activa.myUIManager.pursached = Activa.myProtocolManager.getPursachedAmbients();
				} catch (NotUpdatedException e) {
					Activa.myUIManager.loadTextOnWindow(Activa.myLanguageManager.TEXT_UPDATEVERSION);
					e.printStackTrace();
				}
					if (Activa.myUIManager.ambient != null) Activa.myUIManager.showAmbientsForLoading();
					else Activa.myUIManager.showAmbientsForLoading();
					break;
				default:
					break;
			}
		}
	};
	
}
