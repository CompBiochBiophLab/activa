package com.o2hlink.pimtools.background;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
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

import com.o2hlink.activ8.client.entity.Document;
import com.o2hlink.pimtools.R;
import com.o2hlink.pimtools.Activa;
import com.o2hlink.pimtools.ActivaConfig;
import com.o2hlink.pimtools.exceptions.NotUpdatedException;
import com.o2hlink.pimtools.ui.UIManager;
import com.o2hlink.pimtools.ui.widget.Ambient;

public class SyncFiles implements Runnable {

	ArrayList<File> files;
	
	public SyncFiles(ArrayList<File> files) {
		this.files = files;
	}

	@Override
	public void run() {
		try{
			boolean successful = true;
			int progress = 0;
			NotificationManager mManager = (NotificationManager) Activa.myApp.getSystemService(Context.NOTIFICATION_SERVICE);
			Notification notification = new Notification(R.drawable.icon, "ActivA", System.currentTimeMillis());
			notification.contentView = new RemoteViews(Activa.myApp.getPackageName(), R.layout.progressstatusbar);
			notification.contentView.setProgressBar(R.id.progressbar, this.files.size(), progress, true);
			notification.contentView.setTextViewText(R.id.progresstext, String.format("Synchronizing image %d/%d ...", progress, this.files.size()));
			Intent notificationIntent = new Intent(Activa.myApp, Activa.class);
			PendingIntent contentIntent = PendingIntent.getActivity(Activa.myApp, 0, notificationIntent, 0);
			notification.contentIntent = contentIntent;
			mManager.notify(42, notification);
			Iterator<File> it = files.iterator();
			while (it.hasNext()) {
				File file = it.next();
				Document doc = new Document();
				doc.setName(file.getName());
				doc.setContentType(Activa.myDocumentsManager.getMIMEType(file));
				boolean success = Activa.myDocumentsManager.uploadDocument(doc, null, file);
				progress++;
				notification.contentView.setProgressBar(R.id.progressbar, files.size(), progress, false);        
				notification.contentView.setTextViewText(R.id.progresstext, String.format("Synchronizing image %d/%d ...", progress, this.files.size()));
				mManager.notify(42, notification);
				if (!success) {
					successful = false;
					break;
				}
			}
			notification.flags = Notification.FLAG_AUTO_CANCEL;
			if (successful) notification.contentView.setTextViewText(R.id.progresstext, "Synchronization successful");
			else notification.contentView.setTextViewText(R.id.progresstext, "Synchronization failed");
			mManager.notify(42, notification);
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
	
}
