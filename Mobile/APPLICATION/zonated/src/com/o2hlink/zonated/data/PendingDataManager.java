package com.o2hlink.zonated.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.StringTokenizer;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import com.o2hlink.activ8.client.action.Action;
import com.o2hlink.zonated.Zonated;
import com.o2hlink.zonated.ZonatedConfig;
import com.o2hlink.zonated.ZonatedUtil;

public class PendingDataManager {
	
	private static PendingDataManager instance = null;
	
	public ArrayList<Action<?>> pendingActions;
	
	private PendingDataManager() {
		pendingActions = new ArrayList<Action<?>>();
	}
	
	public PendingDataManager(boolean done) {
		pendingActions = new ArrayList<Action<?>>();
	}
	
	public static PendingDataManager getInstance() {
		if (PendingDataManager.instance == null) PendingDataManager.instance = new PendingDataManager();
		PendingDataManager.instance.pendingActions = new ArrayList<Action<?>>();
		return PendingDataManager.instance;
	}
	
	public void passPendingActionsToFile() {
		File folder = Zonated.myApp.getDir(ZonatedConfig.FILES_FOLDER, 0);
		String filename = "activapendingactions" + Zonated.myMobileManager.user.getName() + ".xml";
		FileOutputStream fout;
		try {
			fout = new FileOutputStream(new File(folder, filename));
			ObjectOutputStream os = new ObjectOutputStream(fout);
			for (Action<?> action : this.pendingActions) {
				os.writeObject(action);
			}
			os.close();
			fout.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void getPendingActionsFromFile() {
		File folder = Zonated.myApp.getDir(ZonatedConfig.FILES_FOLDER, 0);
		String filename = "activapendingactions" + Zonated.myMobileManager.user.getName() + ".xml";
		FileInputStream fin;
		try {
			File actions = new File(folder, filename);
			if (!actions.exists()) actions.createNewFile();
			else {
				fin = new FileInputStream(actions);
				ObjectInputStream is = new ObjectInputStream(fin);
				Action<?> action = (Action<?>)is.readObject();
				while(action != null) {
					this.pendingActions.add(action);
					action = (Action<?>)is.readObject();
				}
				is.close();
				fin.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
}
