/**
 * @author Adrian Rejas
 * 
 * This class deals with the creation of messages reporting the answers given to a 
 * concrete questionnaire.
*/

package com.o2hlink.activa.connection.message;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.Stack;

import com.o2hlink.activ8.client.entity.MultiAnswer;
import com.o2hlink.activ8.client.entity.NumericAnswer;
import com.o2hlink.activ8.client.entity.QuestionAnswer;
import com.o2hlink.activ8.client.entity.Questionnaire;
import com.o2hlink.activ8.client.entity.TextAnswer;
import com.o2hlink.activa.Activa;
import com.o2hlink.activa.data.calendar.Event;
import com.o2hlink.activa.mobile.Device;

public class QuestMessage {
	
	/**
	 * The questionnaire to be sent.
	 */
	public Questionnaire questionnaire;
	
	/**
	 * The answers to be sent.
	 */
	public Stack<QuestionAnswer> answers;
	
	/**
	 * The related event.
	 */
	public Event eventAssociated;
	
	/**
	 * The constructor to the object without parameters.	
	 */
	public QuestMessage() {
		this.questionnaire = null;
		this.answers = null;
		this.eventAssociated = null;
	}
	
	/**
	 * The constructor to the object with parameters.
	 * 
	 * @param type
	 */
	public QuestMessage(Questionnaire questionnaire, Stack<QuestionAnswer> answers) {
		this.questionnaire = questionnaire;
		this.answers = answers;
		this.eventAssociated = null;
	}
	
	/**
	 * The constructor to the object with parameters.
	 * 
	 * @param type
	 */
	public QuestMessage(Questionnaire questionnaire, Stack<QuestionAnswer> answers, Event event) {
		this.questionnaire = questionnaire;
		this.answers = answers;
		this.eventAssociated = event;
	}
	
	/**
	 * Pass the object to a string, used for testing purposes.
	 * 
	 * @return The String representation.
	 */
	public String toString() {
		String returned;
		returned = "Sending of Questionnaire result: " + this.questionnaire.getName() + "\n";
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
		returned += "<CUESTIONARIO ID=\"" + this.questionnaire.getId() + "\" DATETIME=\"" + 
		Activa.myMobileManager.device.getDateTime() + "\" IDAGENDA=\"";
		if (eventAssociated != null) returned += eventAssociated.id;
		returned += "\">";
		returned += "<ESTADOS COUNT=\"" + answers.size() + "\">";
		Iterator<QuestionAnswer> iterator = answers.iterator();
		while (iterator.hasNext()) {
			QuestionAnswer answer = iterator.next();
			returned += "<ESTADO ";
			returned += "ID=\"" + answer.getQuestion() + "\" ";
			returned += "RESPUESTAS=\"";
			if (answer instanceof MultiAnswer) {
				Iterator<Long> it = ((MultiAnswer)answer).getAnswers().iterator();
				while (it.hasNext()) {
					returned += "" + it.next();
					if (it.hasNext()) returned += "|";
				}
			}
			returned += "\" ";
			returned += "RESULTADO=\"";
			if (answer instanceof NumericAnswer) {
				returned += ((NumericAnswer)answer).getValue();
			}
			else if (answer instanceof TextAnswer) {
				returned += ((TextAnswer)answer).getValue();
			}
			returned += "\"/>";
		}
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
