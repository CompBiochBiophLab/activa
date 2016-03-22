/**
 * @author Adrian Rejas
 * 
 * This class deals with the creation of messages reporting about the outcome of an 
 * event programmed for the patient.
*/

package com.o2hlink.activa.connection.message;

import java.util.Enumeration;

import com.o2hlink.activa.Activa;
import com.o2hlink.activa.ActivaUtil;
import com.o2hlink.activa.data.calendar.Event;
import com.o2hlink.activa.data.message.O2Message;
import com.o2hlink.activa.data.message.O2UnregisteredMessage;
import com.o2hlink.activa.mobile.Device;

public class O2MessageSent {
	
	public O2UnregisteredMessage message; 
	
	/**
	 * The constructor to the object without parameters.	
	 */
	public O2MessageSent() {
		this.message = null;
	}
	
	/**
	 * The constructor to the object with parameters.	
	 */
	public O2MessageSent(O2UnregisteredMessage message) {
		this.message = message;
	}
	
	/**
	 * Pass the object to a string, used for testing purposes.
	 * 
	 * @return The String representation.
	 */
	public String toString() {
		String returned;
		returned = "Sending of O2Message: " + this.message.topic + "\n";
		returned += "Done by " + Activa.myMobileManager.device.toString();
		return returned;	
	}
	
	/**
	 * Create the data field of the corresponding message\.
	 * 
	 * @return The message data field.
	 */
	public String toMessageString() {
		String returned = "";
		returned += "<MESSAGETOSEND><MOBILEDEVICE>" +
				"<IDNUMBER VALUE=\"" + Activa.myMobileManager.device.getIdnumber() + "\"/>";
		returned += "<DATETIME VALUE=\"" + Activa.myMobileManager.device.getDateTime() + "\">" +
				"</MOBILEDEVICE><PATIENT ID=\"" + Activa.myMobileManager.user.getId() + "\">";
		returned += "<MESSAGE>";
		returned += "<SENDER VALUE=\"" + this.message.sender + "\"/><RECEIVER VALUE=\"";
		for (int i = 0; i < this.message.receivers.length; i++) {
			if (i != 0) returned +=",";
			returned += this.message.receivers[i];
		} 
		returned += "\"/>" + "<DATE VALUE=\"" + ActivaUtil.dateToXMLDate(this.message.date) + "\"/>" +
				"<TOPIC VALUE=\"" + this.message.topic + "\"/><TEXT value=\"" + this.message.text + 
				"\"/></MESSAGE></PATIENT></MESSAGETOSEND>";
		return returned;	
	}

	/**
	 * Getter for event of the message.
	 * 
	 * @return
	 */
	public O2UnregisteredMessage getMessage() {
		return this.message;
	}

	/**
	 * Setter for event.
	 * 
	 * @param
	 */
	public void setMessage(O2UnregisteredMessage message) {
		this.message = message;
	}

}
