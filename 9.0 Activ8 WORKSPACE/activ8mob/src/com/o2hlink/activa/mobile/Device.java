package com.o2hlink.activa.mobile;

import java.util.Date;

import com.o2hlink.activa.Activa;

import android.util.Log;

/**
 * @author Adrian Rejas<P>
 * 
 * This class represents the mobile device used for executing the application. This class
 * will have just one instance, because there will be just one mobile device which uses 
 * the application.
 * 
*/

public class Device {
	
	/**
	 * The instance of the device.
	 */
	private static Device instance;
	
	/**
	 * The ID which identifies the mobile. It has been used the Bluetooth address,
	 * because it's unique at all phones. It can also be used the IMEI number.
	 */
	private String idnumber;
	
	/**
	 * The time the last message has been send by the mobile. At the beginning, it
	 * represents the time the application has started its running.
	 */
	private String dateTime;
	
	/**
	 * Private constructor for the class.
	 */
	private Device() {
		this.idnumber = Activa.myTelephonyManager.getDeviceId();
//		this.idnumber = Activa.myBluetoothAdapter.getAddress().replace(":", "");
		Date date = new Date();
		this.dateTime = date.getYear()+ 1900 + "";
		if (date.getMonth()<10) this.dateTime += "0" + date.getMonth();
		else this.dateTime += date.getMonth();
		if (date.getDate()<10) this.dateTime += "0" + date.getDay();
		else this.dateTime += date.getDate();
		if (date.getHours()<10) this.dateTime += "0" + date.getHours();
		else this.dateTime += date.getHours();
		if (date.getMinutes()<10) this.dateTime += "0" + date.getMinutes();
		else this.dateTime += date.getMinutes();
		if (date.getSeconds()<10) this.dateTime += "0" + date.getSeconds();
		else this.dateTime += date.getSeconds();
	}

	/**
	 * Method for getting the unique instance of the class.
	 * @return
	 */
	public static Device getInstance() {
		if (Device.instance == null) {
			Device.instance = new Device();
		}
		return Device.instance;	
	}
	
	/**
	 * Getter for ID.
	 * @return
	 */
	public String getIdnumber() {
		return idnumber;
	}

	/**
	 * Setter for ID.
	 * @param idnumber
	 */
	public void setIdnumber(String idnumber) {
		this.idnumber = idnumber;
	}

	/**
	 * Getter for dateTime.
	 * @return
	 */
	public String getDateTime() {
		return dateTime;
	}

	/**
	 * Setter for dateTime.
	 * @param dateTime
	 */
	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}
	
	/**
	 * This function updates the time associed with the mobile with the now one.
	 */
	public String updateDateTime() {
		Date date = new Date();
		this.dateTime = date.getYear()+ 1900 + "";
		if (date.getMonth()<10) this.dateTime += "0" + date.getMonth();
		else this.dateTime += date.getMonth();
		if (date.getDate()<10) this.dateTime += "0" + date.getDay();
		else this.dateTime += date.getDate();
		if (date.getHours()<10) this.dateTime += "0" + date.getHours();
		else this.dateTime += date.getHours();
		if (date.getMinutes()<10) this.dateTime += "0" + date.getMinutes();
		else this.dateTime += date.getMinutes();
		if (date.getSeconds()<10) this.dateTime += "0" + date.getSeconds();
		else this.dateTime += date.getSeconds();
		return this.dateTime;
	}
}
