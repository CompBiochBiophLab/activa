/**
 * @author Adrian Rejas
 * 
 * This class deals with the creation of messages reporting the answers given to a 
 * concrete questionnaire.
*/

package com.o2hlink.activa.connection.message;

import java.util.Enumeration;

import com.o2hlink.activa.Activa;
import com.o2hlink.activa.data.calendar.Event;
import com.o2hlink.activa.data.questionnaire.Questionnaire;
import com.o2hlink.activa.data.sensor.Sensor;
import com.o2hlink.activa.mobile.Device;

public class SensorMessage {
	
	/**
	 * The questionnaire to be sent.
	 */
	public Sensor sensor;
	
	/**
	 * The constructor to the object without parameters.	
	 */
	public SensorMessage() {
		this.sensor = null;
	}
	
	/**
	 * The constructor to the object with parameters.
	 * 
	 * @param type
	 */
	public SensorMessage(Sensor sensor) {
		this.sensor = sensor;
	}
	
	/**
	 * Pass the object to a string, used for testing purposes.
	 * 
	 * @return The String representation.
	 */
	public String toString() {
		String returned;
		returned = "Sending of Sensor measurement: " + this.sensor.name + "\n";
		returned += "Done by " + Activa.myMobileManager.device.toString();
		return returned;	
	}
	
	/**
	 * Create the data field of the corresponding message.
	 * 
	 * @return The message data field.
	 */
	public String toMessageString() {
		Activa.myMobileManager.device.updateDateTime();
		String returned = "";
		returned += "<PESMOBILE COMPRESSED=\"0\"><MOBILEDEVICE>" +
				"<IDNUMBER VALUE=\"" + Activa.myMobileManager.device.getIdnumber() + "\"/>";
		returned += "<DATETIME VALUE=\"" + Activa.myMobileManager.device.getDateTime() + "\"/>" +
				"</MOBILEDEVICE><PATIENT ID=\"" + Activa.myMobileManager.user.getId() + "\">";
		returned += "<DEVICE ID=\"" + sensor.id + "\">";
		returned += "<EVENTS COUNT=\"1\">";
		returned += sensor.passSensorResultToXML();
		returned += "</EVENTS></DEVICE></PATIENT></PESMOBILE>";
		return returned;	
	}
	
	/**
	 * Create the data field of the corresponding message.
	 * 
	 * @param The ID of the device to be sent.
	 * @return The message data field.
	 */
	public String toMessageString(int deviceId) {
		Activa.myMobileManager.device.updateDateTime();
		String returned = "";
		returned += "<PESMOBILE COMPRESSED=\"0\"><MOBILEDEVICE>" +
				"<IDNUMBER VALUE=\"" + Activa.myMobileManager.device.getIdnumber() + "\"/>";
		returned += "<DATETIME VALUE=\"" + Activa.myMobileManager.device.getDateTime() + "\"/>" +
				"</MOBILEDEVICE><PATIENT ID=\"" + Activa.myMobileManager.user.getId() + "\">";
		returned += "<DEVICE ID=\"" + deviceId + "\">";
		returned += "<EVENTS COUNT=\"1\">";
		returned += sensor.passSensorResultToXML();
		returned += "</EVENTS></DEVICE></PATIENT></PESMOBILE>";
		return returned;	
	}

	/**
	 * Getter for sensor of the message.
	 * 
	 * @return 
	 */
	public Sensor getSensor() {
		return this.sensor;
	}

	/**
	 * Setter for sensor of the message.
	 * 
	 * @param
	 */
	public void setSensor(Sensor sensor) {
		this.sensor = sensor;
	}

}
