package com.o2hlink.activa.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Iterator;

import android.os.Environment;

import com.o2hlink.activa.Activa;
import com.o2hlink.activa.ActivaConfig;

public class PendingDataManager {
	
	private static PendingDataManager instance = null;
	
	public ArrayList<String> pendingData;
	
	private PendingDataManager() {
		pendingData = new ArrayList<String>();
	}
	
	public static PendingDataManager getInstance() {
		if (PendingDataManager.instance == null) PendingDataManager.instance = new PendingDataManager();
		try {
			String filename = "activapendingdata" + Activa.myMobileManager.user.getName() + ".xml";
			String xmlPendingData = "";
    		File folder = Activa.myApp.getDir(ActivaConfig.FILES_FOLDER, 0);
			File pendingData = new File(folder, filename);
			if (!pendingData.exists()) {
				pendingData.createNewFile();
			}
			else {
				FileInputStream fin = new FileInputStream(pendingData);
				InputStreamReader isr = new InputStreamReader(fin,"UTF-8");          
		        char[] inputBuffer = new char[255];
		        while ( isr.read(inputBuffer) != -1) {
		        	xmlPendingData += new String(inputBuffer);    
		        }
		        if (!PendingDataManager.instance.extractPendingDataFromXML(xmlPendingData)) PendingDataManager.instance.pendingData = new ArrayList<String>();
		        isr.close();
			}
			FileOutputStream fout = Activa.myApp.openFileOutput(filename, 0);
			OutputStreamWriter osw = new OutputStreamWriter(fout);
			osw.write(PendingDataManager.instance.passPendingDataToXML());
			osw.close();
			fout.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return PendingDataManager.instance;
	}
	
	private boolean extractPendingDataFromXML(String xml) {
		int index1, index2;
		int count, mycount;
		mycount = 0;
		index1 = xml.indexOf("PENDINGDATA COUNT=\"");
		index2 = xml.indexOf("\"", index1 + 19);
		count = Integer.parseInt(xml.substring(index1 + 19, index2));
		index1 = xml.indexOf("COMPRESSED=\"", index2);
		index2 = xml.indexOf("\">", index1 + 12);
		while (true) {
			index1 = xml.indexOf("<DATA>", index2);
			if (index1 == -1) break;
			index2 = xml.indexOf("</DATA>", index1);
			if (index2 == -1) break;
			this.addPendingData(xml.substring(index1 + 6, index2));
			mycount++;
		}
		if (mycount != count) return false;
		return true;
	}

	public void addPendingData(String data) {
		pendingData.add(data);
	}
	
	public String passPendingDataToXML() {
		String returned = "";
		returned += "<PENDINGDATA COUNT=\"";
		returned += pendingData.size();
		returned += "\">\n";
		Iterator<String> iterator = pendingData.iterator();
		while (iterator.hasNext()) {
			returned += "<DATA>\n";
			returned += iterator.next();
			returned += "</DATA>\n";
		}
		returned += "</PENDINGDATA>";
		return returned;
	}
	
	public void passPendingDataToFile() {
		File folder = Activa.myApp.getDir(ActivaConfig.FILES_FOLDER, 0);
		String filename = "activapendingdata" + Activa.myMobileManager.user.getName() + ".xml";
		FileOutputStream fout;
		try {
			fout = new FileOutputStream(new File(folder, filename));
			OutputStreamWriter osw = new OutputStreamWriter(fout);
			osw.write(passPendingDataToXML());
			osw.close();
			fout.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}