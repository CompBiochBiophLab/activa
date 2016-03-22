package com.o2hlink.activa.data.questionnaire;

import java.io.IOException;
import java.io.StringReader;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import com.o2hlink.activa.data.calendar.Event;

/**
 * @author Adrian Rejas<P>
 * 
 * The following class represents a possible answer to a question.
 */
public class Answer {
	
	/**
	 * Text of the answer.
	 */
	public String text;
	
	/**
	 * Weight of the answer.
	 */
	public float weight;
	
	/**
	 * Public constructor for the answer.
	 */
	public Answer() {
		text = null;
		weight = -10;
	}
	
	/**
	 * Public constructor with parameters.
	 * 
	 * @param text
	 * @param weight
	 */
	public Answer(String text, float weight) {
		this.text = text;
		this.weight = weight;
	}

	/**
	 * This method extracts an answer from a XML structure.
	 * 
	 * @param xml The XML structure.
	 * @return The Answer object.
	 */
//	public static Answer extractFromXML(String xml) {
//		Answer returned;
//		int index1, index2;
//		float weight;
//		String text;
//		index1 = xml.indexOf("NOMBRE=\"");
//		index2 = xml.indexOf("\"", index1+8);
//		text = xml.substring(index1 + 8, index2);
//		index1 = xml.indexOf("PESO=\"");
//		index2 = xml.indexOf("\"", index1+6);
//		if (index1 == -1) weight = -1.0f;
//		else weight = Float.parseFloat(xml.substring(index1 + 6, index2));
//		returned = new Answer(text, weight);
//		return returned;
//	}
	
	public static Answer extractFromXML(XmlPullParser info) {
		Answer answer = new Answer();
		try {
	        boolean insideEvent = false;
	        boolean end = true; 
	        int event = info.getEventType();
	        while (event != XmlPullParser.END_DOCUMENT) {
	            if(event == XmlPullParser.START_DOCUMENT) {
	            } else if(event == XmlPullParser.END_DOCUMENT) {    	
	            } else if(event == XmlPullParser.START_TAG) {
	                if (info.getName().equalsIgnoreCase("RESPUESTA")) {
		                	answer.text = info.getAttributeValue(info.getNamespace(), "NOMBRE");
		                	if (info.getAttributeValue(info.getNamespace(), "PESO") != null)
		                		answer.weight = Float.parseFloat(info.getAttributeValue(info.getNamespace(), "PESO"));
	                }
	            } else if(event == XmlPullParser.END_TAG) {
	                if (info.getName().equalsIgnoreCase("RESPUESTA")) {
	                	break;
	                }
	            }
	            event = info.next();
	        }
		} catch (XmlPullParserException e) {
			answer = null;
			e.printStackTrace();
		} catch (IOException e) {
			answer = null;
			e.printStackTrace();
		} catch (NullPointerException e) {
			answer = null;
			e.printStackTrace();
		} catch (NumberFormatException e) {
			answer = null;
			e.printStackTrace();
		}
		return answer;
	}
	
}
