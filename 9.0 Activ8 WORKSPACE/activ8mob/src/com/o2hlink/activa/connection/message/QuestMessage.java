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
import com.o2hlink.activa.mobile.Device;

public class QuestMessage {
	
	/**
	 * The questionnaire to be sent.
	 */
	public Questionnaire questionnaire;
	
	/**
	 * The constructor to the object without parameters.	
	 */
	public QuestMessage() {
		this.questionnaire = null;
	}
	
	/**
	 * The constructor to the object with parameters.
	 * 
	 * @param type
	 */
	public QuestMessage(Questionnaire questionnaire) {
		this.questionnaire = questionnaire;
	}
	
	/**
	 * Pass the object to a string, used for testing purposes.
	 * 
	 * @return The String representation.
	 */
	public String toString() {
		String returned;
		returned = "Sending of Questionnaire result: " + this.questionnaire.name + "\n";
		returned += "Done by " + Activa.myMobileManager.device.toString();
		return returned;	
	}
	
	/**
	 * Create the data field of the corresponding message.
	 * 
	 * @return The message data field.
	 */
	public String toMessageString() {
		String returned = "";
		returned += "<RESULTCUESTIONARIOS COMPRESSED=\"0\"><MOBILEDEVICE>" +
				"<IDNUMBER VALUE=\"" + Activa.myMobileManager.device.getIdnumber() + "\"/>";
		returned += "<DATETIME VALUE=\"" + Activa.myMobileManager.device.getDateTime() + "\"/>" +
				"</MOBILEDEVICE><PATIENT ID=\"" + Activa.myMobileManager.user.getId() + "\">";
		returned += "<CUESTIONARIOS COUNT=\"1\">";
		returned += questionnaire.passQuestResultToXML();
		returned += "</CUESTIONARIOS></PATIENT></RESULTCUESTIONARIOS>";
		return returned;	
	}

	/**
	 * Getter for questionnaire of the message.
	 * 
	 * @return 
	 */
	public Questionnaire getQuestionnaire() {
		return this.questionnaire;
	}

	/**
	 * Setter for questionnaire.
	 * 
	 * @param
	 */
	public void setQuestionnaire(Questionnaire questionnaire) {
		this.questionnaire = questionnaire;
	}

}
