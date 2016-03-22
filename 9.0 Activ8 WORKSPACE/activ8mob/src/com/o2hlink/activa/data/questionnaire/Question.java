package com.o2hlink.activa.data.questionnaire;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import com.o2hlink.activa.data.calendar.Event;

/**
 * @author Adrian Rejas<P>
 * 
 * This class represents a simple question which is part of a questionnaire.
 */
public class Question {
	
	/** 
	 * The following constants describes the possible question types.  
	 */
	public static final int MONO_QUESTION = 0;
	public static final int POLI_QUESTION = 1;
	public static final int NUMERIC_QUESTION = 2;
	public static final int TEXT_QUESTION = 3;
	public static final int INFO_QUESTION = 4;
	public static final int RESULT_QUESTION = 5;
	
	/**
	 * A boolean value for validating the question
	 */
	public boolean valid;
	
	/**
	 * The ID of the question.
	 */
	public int id;
	
	/**
	 * The question text.
	 */
	public String text;
	
	/**
	 * The ID of the next question by default.
	 */
	public int nextIdByDefault;
	
	/**
	 * The type of the question
	 */
	public int type;
	
	/**
	 * A boolean value representing the possibility for the question to be jumped.
	 */
	public boolean jumpable;
	
	/**
	 * The set of possible answers.
	 */	
	public Hashtable<Integer, Answer> answerSet;
	
	/**
	 * If the question has been answered.
	 */
	public boolean answered;
	
	/**
	 * The mono answer.
	 */
	public int monoAnswer;
	
	/**
	 * The poli answer.
	 */
	public HashSet<Integer> poliAnswer;
	
	/**
	 * The numeric answer.
	 */
	public float numericAnswer;
	
	/**
	 * The text answer.
	 */
	public String textAnswer;
	
	/**
	 * A boolean value set to true if it's a numeric question with Borg representation.
	 */
	public boolean borg;
	
	/**
	 * The set of conditions to be evaluated.
	 */
	public HashSet<Condition> conditionSet;
	
	/**
	 * The public constructor.
	 * @param id
	 * @param text
	 * @param next
	 * @param type
	 * @param jumpable
	 */
	public Question(int id, String text, int next, int type, boolean jumpable) {
		this.id = id;
		this.text = text;
		this.nextIdByDefault = next;
		this.type = type;
		this.answered = false;
		this.jumpable = jumpable;
		this.valid = true;
		this.borg = true;
		if ((type == 0)||(type == 1)) this.answerSet = new Hashtable<Integer, Answer>();
		this.conditionSet = new HashSet<Condition>();
	}
	
	/**
	 * This method extract the info related with a question from the related
	 * XML structure.
	 * 
	 * @param xml The XML structure.
	 * @return A Question object.
	 */
//	public static Question extractFromXML(String xml) {
//		Question returned;
//		int index1, index2, end;
//		int count, mycount;
//		int id, nextid, type;
//		String text;
//		boolean jumpable;
//		Answer auxAnswer;
//		Condition auxCond;
//		mycount = 0;
//		index1 = xml.indexOf("ID=\"");
//		index2 = xml.indexOf("\"", index1+4);
//		id = Integer.parseInt(xml.substring(index1 + 4, index2));
//		index1 = xml.indexOf("NOMBRE=\"");
//		index2 = xml.indexOf("\"", index1+8);
//		text = xml.substring(index1 + 8, index2);
//		index1 = xml.indexOf("IDSIGUIENTE=\"");
//		index2 = xml.indexOf("\"", index1+13);
//		nextid = Integer.parseInt(xml.substring(index1 + 13, index2));
//		index1 = xml.indexOf("IDTIPO=\"");
//		index2 = xml.indexOf("\"", index1+8);
//		type = Integer.parseInt(xml.substring(index1 + 8, index2));
//		index1 = xml.indexOf("SALTABLE=\"");
//		index2 = xml.indexOf("\"", index1+10);
//		jumpable = (Integer.parseInt(xml.substring(index1 + 10, index2)) != 0);
//		returned = new Question(id, text, nextid, type, jumpable);
//		index1 = xml.indexOf("BORG=\"");
//		if (index1 != -1) {
//			index2 = xml.indexOf("\"", index1 + 6);
//			returned.borg = (Integer.parseInt(xml.substring(index1 + 6, index2)) != 0);
//		}
//		index1 = xml.indexOf("<RESPUESTAS COUNT=\"");
//		if (index1 == -1) {
//			end = index2;
//		}
//		else {
//			index2 = xml.indexOf("\">", index1);
//			end = xml.indexOf("</RESPUESTAS>", index1);
//			count = Integer.parseInt(xml.substring(index1 + 19, index2));
//			while (true) {
//				index1 = xml.indexOf("<RESPUESTA", index2);
//				if (index1 == -1) break;
//				index2 = xml.indexOf("/>", index1);
//				if (index2 == -1) break;
//				auxAnswer = Answer.extractFromXML(xml.substring(index1, index2 + 2));
//				returned.answerSet.put(mycount+1, auxAnswer);
//				mycount++;
//			}
//			if (mycount != count) {
//				returned.valid = false;
//			}
//		}
//		mycount = 0;
//		index1 = xml.indexOf("<CONDICIONES COUNT=\"", end);
//		if (index1 == -1) return returned;
//		index2 = xml.indexOf("\">", index1);
//		count = Integer.parseInt(xml.substring(index1 + 20, index2));
//		while (true) {
//			index1 = xml.indexOf("<CONDICION", index2);
//			if (index1 == -1) break;
//			index2 = xml.indexOf("</CONDICION>", index1);
//			if (index2 == -1) break;
//			auxCond = Condition.extractFromXML(xml.substring(index1, index2 + 12));
//			returned.conditionSet.add(auxCond);
//			mycount++;
//		}
//		if (mycount != count) {
//			returned.valid = false;
//		}
//		return returned;
//	}
	
	public static Question extractFromXML(XmlPullParser info) {
		int answerscount = 0, myanswerscount = 0;
		int conditionscount = 0, myconditionscount = 0;
		Question question = new Question(0, null, 0, 0, false);
    	question.borg = true;
		try {
	        int event = info.getEventType();
	        while (event != XmlPullParser.END_DOCUMENT) {
	            if(event == XmlPullParser.START_DOCUMENT) {
	            } else if(event == XmlPullParser.END_DOCUMENT) {    	
	            } else if(event == XmlPullParser.START_TAG) {
	                if (info.getName().equalsIgnoreCase("ESTADO")) {
	                	question.id = Integer.parseInt(info.getAttributeValue(info.getNamespace(), "ID"));
	                	question.text = info.getAttributeValue(info.getNamespace(), "NOMBRE");
	                	question.nextIdByDefault = Integer.parseInt(info.getAttributeValue(info.getNamespace(), "IDSIGUIENTE"));
	                	question.type = Integer.parseInt(info.getAttributeValue(info.getNamespace(), "IDTIPO"));
	                	int aux;
	                	if (info.getAttributeValue(info.getNamespace(), "SALTABLE") != null) {
		                	aux = Integer.parseInt(info.getAttributeValue(info.getNamespace(), "SALTABLE")); 
		                	if (aux != 0) question.jumpable = true; 
//	                		String auxStr = info.getAttributeValue(info.getNamespace(), "SALTABLE");
//	                		if (auxStr.equalsIgnoreCase("true")) question.jumpable = true; 
	                	}
	                	if (info.getAttributeValue(info.getNamespace(), "BORG") != null) {
		                	aux = Integer.parseInt(info.getAttributeValue(info.getNamespace(), "BORG"));
		                	if (aux == 0) question.borg = false;
	                	}
	                }
	                else if (info.getName().equalsIgnoreCase("RESPUESTAS")) {
	                	answerscount = Integer.parseInt(info.getAttributeValue(info.getNamespace(), "COUNT"));
	                }
	                else if (info.getName().equalsIgnoreCase("RESPUESTA")) {
	                	Answer answer = Answer.extractFromXML(info);
	                	if (answer != null) {
	                		question.answerSet.put(myanswerscount, answer);
	                		myanswerscount++;	             
	                	}
	                }
	                else if (info.getName().equalsIgnoreCase("CONDICIONES")) {
	                	conditionscount = Integer.parseInt(info.getAttributeValue(info.getNamespace(), "COUNT"));
	                }
	                else if (info.getName().equalsIgnoreCase("CONDICION")) {
	                	Condition condition = Condition.extractFromXML(info);
	                	if (condition != null) {
	                		question.conditionSet.add(condition);
	                		myconditionscount++;	 	      
	                	}
	                }
	            } else if(event == XmlPullParser.END_TAG) {
	                if (info.getName().equalsIgnoreCase("ESTADO")) {
	                	if (myanswerscount != answerscount) question = null;
//	                	if (myconditionscount != conditionscount) question = null;
	                	break;
	                }
	            }
	            event = info.next();
	        }
		} catch (XmlPullParserException e) {
			question = null;
			e.printStackTrace();
		} catch (IOException e) {
			question = null;
			e.printStackTrace();
		} catch (NullPointerException e) {
			question = null;
			e.printStackTrace();
		} catch (NumberFormatException e) {
			question = null;
			e.printStackTrace();
		}
		return question;
	}

	public int calcNextQuestion() {
		int returned = this.nextIdByDefault;
		Iterator<Condition> it = this.conditionSet.iterator();
		switch (type) {
			case MONO_QUESTION:
				while (it.hasNext()) {
					Condition cond = (Condition) it.next();
					if (cond.rangeValues.contains(monoAnswer)) {
						returned = cond.nextId;
						break;
					}
				}
				break;
			case POLI_QUESTION:
				boolean pass = false;
				Iterator<Integer> it2 = this.poliAnswer.iterator();
				while (it.hasNext()) {
					while (it2.hasNext()) {
						Condition cond = (Condition) it.next();
						if (cond.rangeValues.contains(it2.next())) {
							returned = cond.nextId;
							pass = true;
							break;
						}						
					}
					if (pass) break;
				}
				break;
			case NUMERIC_QUESTION:
				while (it.hasNext()) {
					Condition cond = (Condition) it.next();
					if (cond.comparatorValue > numericAnswer) {
						returned = cond.nextId;
						break;
					}						
				}
				break;
		}
		return returned;
	}
	
	public String getXMLAnswers() {
		String returned = "";
		switch (this.type) {
			case Question.MONO_QUESTION:
				returned += "" + this.monoAnswer;
				break;
			case Question.POLI_QUESTION:
				Iterator<Integer> it = this.poliAnswer.iterator();
				while (it.hasNext()){
					returned += "" + it.next();
					if (it.hasNext()) returned += "|";
				}
				break;
		}
		return returned;
	}
	

	
	public String getXMLResult() {
		String returned = "";
		switch (this.type) {
			case Question.NUMERIC_QUESTION:
				returned += "" + this.numericAnswer;
				break;
			case Question.TEXT_QUESTION:
				returned += "" + this.textAnswer;
				break;
		}
		return returned;
	}

}