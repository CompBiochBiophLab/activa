package com.o2hlink.activa.background;

import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Vector;

import com.o2hlink.activa.Activa;
import com.o2hlink.activa.ActivaConfig;
import com.o2hlink.activa.data.calendar.Event;

public class NotificationThread implements Runnable {

	private static NotificationThread instance = null;
	
	public boolean running;
	
	private NotificationThread() {
		running = false;
	}
	
	public static NotificationThread getInstance() {
		if (NotificationThread.instance == null) {
			NotificationThread.instance = new NotificationThread();
		}
		return NotificationThread.instance;
	}
	
	@Override
	public void run() {
		running = true;
		Date now = new Date();
		Date later = new Date(now.getTime() + ActivaConfig.UPDATES_TIMEOUT);
		HashMap<Date, Event> hashInter = new HashMap<Date,Event>();
		Vector<Date> dates = new Vector<Date>();
		Enumeration<Event> enumer = Activa.myCalendarManager.events.elements();
		while (enumer.hasMoreElements()) {
			Event temp = enumer.nextElement();
			if ((now.compareTo(temp.getDate()) <= 0)&&(later.compareTo(temp.getDate()) > 0)&&(temp.state == 2)) {
				Date aux = new Date(temp.date.getTime());
				if (dates.contains(aux)) {
					aux.setTime(aux.getTime() + 1);
				}
				dates.add(aux);
				hashInter.put(aux, temp);
			}
			else {
				Date aux = new Date(temp.date.getTime());
				aux.setDate(now.getDate());
				aux.setMonth(now.getMonth());
				aux.setYear(now.getYear());
				if ((now.compareTo(aux) <= 0)&&(later.compareTo(aux) > 0)&&(temp.state == 1)) {
					if (dates.contains(aux)) {
						aux.setTime(aux.getTime() + 1);
					}
					dates.add(aux);
					hashInter.put(aux, temp);
				}
				else {
					aux.setDate(aux.getDate() + 1);
					if ((now.compareTo(aux) <= 0)&&(later.compareTo(aux) > 0)&&(temp.state == 1)) {
						if (dates.contains(aux)) {
							aux.setTime(aux.getTime() + 1);
						}
						dates.add(aux);
						hashInter.put(aux, temp);
					}
				}
			}
		}
		Collections.sort(dates);
		HashMap<Date, Event> hashPast = new HashMap<Date,Event>();
		enumer = Activa.myCalendarManager.events.elements();
		while (enumer.hasMoreElements()) {
			Event temp = enumer.nextElement();
			Date aux = new Date(temp.date.getTime());
			aux.setDate(now.getDate());
			aux.setMonth(now.getMonth());
			aux.setYear(now.getYear());
			if ((now.compareTo(temp.getDate()) <= 0)&&(later.compareTo(temp.getDate()) > 0)&&(temp.state == 2)) {
				aux = new Date(temp.date.getTime());
				if (dates.contains(aux)) {
					aux.setTime(aux.getTime() + 1);
				}
				dates.add(aux);
				hashPast.put(aux, temp);
			}
		}
		Collections.sort(dates);
		try {
			for (int i = 0; i < dates.size(); i++) {
				Event currentEvent = hashInter.get(dates.get(i));
				if (currentEvent == null) currentEvent = hashPast.get(dates.get(i));
				if (currentEvent == null) continue;
				long times = (dates.get(i).getTime() - System.currentTimeMillis())/60000;
				for (int j = 0; j < times; j++) {
					Thread.sleep(60000);
					if (!running) break;
				}
//				long time = (currentEvent.date.getTime() - System.currentTimeMillis());
//				if (time > 0) Thread.sleep(time);
				if (!running) break;
				currentEvent.setAlert();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void stop()  {
		this.running = false;
		try {
			this.finalize();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		Exception e = new Exception();
//		throw e;
	}

}
