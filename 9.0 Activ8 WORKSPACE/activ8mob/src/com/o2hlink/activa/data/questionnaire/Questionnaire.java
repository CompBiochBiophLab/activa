package com.o2hlink.activa.data.questionnaire;

import java.io.IOException;
import java.io.StringReader;
import java.security.KeyStore.LoadStoreParameter;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Stack;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import com.o2hlink.activa.Activa;
import com.o2hlink.activa.data.calendar.Event;

/**
 * @author Adrian Rejas<P>
 * 
 * This class represents a questionnaire.
 */
public class Questionnaire {
	
	/**
	 * Boolean value which validates the questionnaire.
	 */
	public boolean valid;
	
	/**
	 * The ID number for this questionnaire.
	 */
	public int id;
	
	/** 
	 * The name associated with this questionnaire.
	 */
	public String name;
	
	/**
	 * The description associated with this questionnaire.
	 */
	public String description;
	
	/**
	 * The set of questions which conform this questionnaire. All questions will be 
	 * defined by its ID.
	 */
	public Hashtable<Integer, Question> questionSet;
	
	/**
	 * The ID of the question treated at a concrete moment.
	 */
	public int currentQuestionId;
	
	/**
	 * The IDs of the previous questions.
	 */
	public Stack<Question> questionsAnswered;
	
	/**
	 * The public constructor.
	 * @param id
	 * @param name
	 * @param description
	 */
	public Questionnaire(int id, String name, String description) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.questionSet = new Hashtable<Integer, Question>();
		this.currentQuestionId = 1;
		this.valid = false;
	}
	
	/**
	 * Extract a Questionnaire object from an XML structure.
	 */
//	public static Questionnaire extractFromXML(String xml) {
//		Questionnaire returned = null;
//		int index1, index2;
//		int count, mycount;
//		int id;
//		String name, description;
//		boolean compressed;
//		Question auxQuest;
//		int end = xml.length();
//		mycount = 0;
//		index1 = xml.indexOf("ID=\"");
//		index2 = xml.indexOf("\"", index1+4);
//		id = Integer.parseInt(xml.substring(index1 + 4, index2));
//		index1 = xml.indexOf("NOMBRE=\"", index2);
//		index2 = xml.indexOf("\"", index1+8);
//		name = xml.substring(index1 + 8, index2);
//		index1 = xml.indexOf("DESCRIPCION=\"", index2);
//		index2 = xml.indexOf("\"", index1+13);
//		description = xml.substring(index1 + 13, index2);
//		returned = new Questionnaire(id, name, description);
//		index1 = xml.indexOf("<ESTADOS COUNT=\"", index2);
//		index2 = xml.indexOf("\">", index1+16);
//		count = Integer.parseInt(xml.substring(index1 + 16, index2));
//		while (true) {
//			index1 = xml.indexOf("<ESTADO",index2);
//			if (index1 == -1) break;
//			index2 = xml.indexOf("</ESTADO>", index1);
//			if (index2 == -1) break;
//			auxQuest = Question.extractFromXML(xml.substring(index1, index2 + 9));
//			returned.questionSet.put(auxQuest.id, auxQuest);
//			mycount++;
//		}
//		if (mycount == count) {
//			returned.valid = true;
//		}
//		return returned;
//	}
	
	public static Questionnaire extractFromXML(XmlPullParser info) {
		Questionnaire questionnaire = new Questionnaire(0, null, null);
		try {
	        int statescount = 0, mystatescount = 0;
	        int event = info.getEventType();
	        while (event != XmlPullParser.END_DOCUMENT) {
	            if(event == XmlPullParser.START_DOCUMENT) {
	            } else if(event == XmlPullParser.END_DOCUMENT) {    	
	            } else if(event == XmlPullParser.START_TAG) {
	                if (info.getName().equalsIgnoreCase("CUESTIONARIO")) {
	                	questionnaire.id = Integer.parseInt(info.getAttributeValue(info.getNamespace(), "ID"));
	                	questionnaire.name = info.getAttributeValue(info.getNamespace(), "NOMBRE");
	                	questionnaire.description = info.getAttributeValue(info.getNamespace(), "DESCRIPCION");
	                }
	                else if (info.getName().equalsIgnoreCase("ESTADOS")) {
	                	statescount = Integer.parseInt(info.getAttributeValue(info.getNamespace(), "COUNT"));
	                }
	                else if (info.getName().equalsIgnoreCase("ESTADO")) {
	                	Question question = Question.extractFromXML(info);
	                	if (question != null) {
	                		questionnaire.questionSet.put(question.id, question);
		                	mystatescount++;
	                	}
	                }
	            } else if(event == XmlPullParser.END_TAG) {
	                if (info.getName().equalsIgnoreCase("CUESTIONARIO")) {
	                	if (mystatescount != statescount) questionnaire = null;
	                	break;
	                }
	            }
	            event = info.next();
	        }
		} catch (XmlPullParserException e) {
			questionnaire = null;
			e.printStackTrace();
		} catch (IOException e) {
			questionnaire = null;
			e.printStackTrace();
		} catch (NullPointerException e) {
			questionnaire = null;
			e.printStackTrace();
		} catch (NumberFormatException e) {
			questionnaire = null;
			e.printStackTrace();
		}
		return questionnaire;
	}

	public void start() {
		this.currentQuestionId = 1;
		Question activeQuestion = questionSet.get(this.currentQuestionId);
		this.questionsAnswered = new Stack<Question>();
		if (activeQuestion != null) Activa.myUIManager.loadQuestion(activeQuestion);
		else {
			if (Activa.myQuestManager.eventAssociated != null) Activa.myUIManager.loadScheduleDay(Activa.myQuestManager.eventAssociated.getDate()); 
			else Activa.myUIManager.loadTotalQuestList();
			Activa.myUIManager.loadInfoPopup("El cuestionario no contiene ninguna pregunta");
		}
	}

	public float finish() {
		this.currentQuestionId = 1;
		return calcTotalWeight();
	}
	
	public void clean() {
		while (!this.questionsAnswered.empty()) {
			Question prev = this.questionsAnswered.pop();
			prev.answered = false;
		}
		this.currentQuestionId = 1;
	}

	private float calcTotalWeight() {
		float totalWeight = 0;
		float maxWeight = 0;
		float avgWeight;
		int returned = 3;
		Enumeration<Question> allquestions = this.questionSet.elements();
		while (allquestions.hasMoreElements()) {
			float contribution = 0;
			Question currentQuestion = allquestions.nextElement();
			switch (currentQuestion.type) {
				case Question.MONO_QUESTION:
					Enumeration<Answer> allanswers = currentQuestion.answerSet.elements();
					while (allanswers.hasMoreElements()) {
						Answer answer = allanswers.nextElement();
						if (contribution < answer.weight) contribution = answer.weight;
					}
					break;
				case Question.POLI_QUESTION:
					Enumeration<Answer> allanswers2 = currentQuestion.answerSet.elements();
					while (allanswers2.hasMoreElements()) {
						Answer answer = allanswers2.nextElement();
						contribution += answer.weight;
					}
					break;
				case Question.NUMERIC_QUESTION:
					contribution = 10f;
					break;
			}	
			maxWeight += contribution;
		}
		Enumeration<Question> questions = this.questionsAnswered.elements();
		while (questions.hasMoreElements()) {
			Question currentQuestion = questions.nextElement();
			if (currentQuestion.answered)
				switch (currentQuestion.type) {
					case Question.MONO_QUESTION:
						totalWeight += currentQuestion.answerSet.get(currentQuestion.monoAnswer).weight;
						break;
					case Question.POLI_QUESTION:
						Iterator<Integer> it = currentQuestion.poliAnswer.iterator();
						while (it.hasNext()) {
							totalWeight += currentQuestion.answerSet.get(it.next()).weight;
						}
						break;
					case Question.NUMERIC_QUESTION:
						totalWeight += currentQuestion.numericAnswer;
				}
		}
		if (maxWeight != 0.0f)
			avgWeight = ((totalWeight)/(maxWeight))*100;
		else 
			avgWeight = -1.0f;
		return avgWeight;
	}
	
	public String passQuestResultToXML () {
		String returned = "";
		returned += "<CUESTIONARIO ID=\"" + this.id + "\" DATETIME=\"" + 
			Activa.myMobileManager.device.getDateTime() + "\" IDAGENDA=\"";
		if (Activa.myQuestManager.eventAssociated != null) returned += Activa.myQuestManager.eventAssociated.id;
		returned += "\">";
		returned += "<ESTADOS COUNT=\"" + this.questionsAnswered.size() + "\">";
		for (int i = 0; i < this.questionsAnswered.size(); i++) {
			Question currentQuestion = this.questionsAnswered.get(i);
			returned += "<ESTADO ";
			returned += "ID=\"" + currentQuestion.id + "\" ";
			returned += "RESPUESTAS=\"" + currentQuestion.getXMLAnswers() + "\" ";
			returned += "RESULTADO=\"" + currentQuestion.getXMLResult() + "\"/>";
		}
		returned += "</ESTADOS></CUESTIONARIO>";
		return returned;
	}
	
	public boolean validateQuestionnaire() {
		Enumeration<Question> enumeration = this.questionSet.elements();
		while (enumeration.hasMoreElements()) {
			Question question = enumeration.nextElement();
			if (question == null) {
				return false;
			}
			int next = question.nextIdByDefault;
			if (!this.questionSet.containsKey(next)) {
				if (next != -1)	
					return false;
			}
			Iterator<Condition> it = question.conditionSet.iterator();
			while (it.hasNext()) {
				Condition condition = it.next();
				if(!this.questionSet.containsKey(condition.nextId)) {
					if (next != -1)	
						return false;
				}
			}
		}
		return true;
	}

}
