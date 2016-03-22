/**
 * @author Adrian Rejas
 * 
 * This class deals with the creation of messages reporting about the outcome of an 
 * event programmed for the patient.
*/

package com.o2hlink.activa.connection.message;

import java.util.Date;
import java.util.Enumeration;

import com.o2hlink.activa.Activa;
import com.o2hlink.activa.ActivaUtil;
import com.o2hlink.activa.data.calendar.Event;
import com.o2hlink.activa.data.message.O2Message;
import com.o2hlink.activa.data.message.O2UnregisteredMessage;
import com.o2hlink.activa.data.questionnaire.Questionnaire;
import com.o2hlink.activa.mobile.Device;

public class NoteMessage {
	
	public String text; 
	
	/**
	 * The constructor to the object without parameters.	
	 */
	public NoteMessage() {
		this.text = null;
	}
	
	/**
	 * The constructor to the object with parameters.	
	 */
	public NoteMessage(String text) {
		this.text = text;
	}
	
	/**
	 * Pass the object to a string, used for testing purposes.
	 * 
	 * @return The String representation.
	 */
	public String toString() {
		String returned;
		returned = "Sending of Note: " + this.text + "\n";
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
		returned += "<NOTETOSEND><MOBILEDEVICE>" +
				"<IDNUMBER VALUE=\"" + Activa.myMobileManager.device.getIdnumber() + "\"/>";
		returned += "<DATETIME VALUE=\"" + Activa.myMobileManager.device.getDateTime() + "\">" +
				"</MOBILEDEVICE><PATIENT ID=\"" + Activa.myMobileManager.user.getId() + "\">";
		returned += "<NOTE TEXT=\"" + text + "\" DATE=\"" + ActivaUtil.dateToXMLDate(new Date()) + "\"/>" 
				+ "</PATIENT></MESSAGETOSEND>";
		return returned;	
	}

}
