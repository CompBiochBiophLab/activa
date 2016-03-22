package com.o2hlink.activa.data.questionnaire;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashSet;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import com.o2hlink.activa.data.calendar.Event;

/**
 * @author Adrian Rejas<P>
 * 
 * The following class represents a condition for jumping to another question which
 * is not the default question.
 */
public class Condition {

	/**
	 * ID of the next question to jump if condition true.
	 */
	public int nextId;
	
	/**
	 * Type of the question to be applied this condition.
	 */
	public int type;
	
	/**
	 * Range of IDs of the questions which make the condition true.
	 * This field is used at mono and poli answers.
	 */
	public HashSet<Integer> rangeValues;
	
	/**
	 * The value below which the condition is true. This field is used at numeric
	 * answers.
	 */
	public float comparatorValue;
	
	/**
	 * Public constructor for the condition.
	 */
	public Condition() {
		this.nextId = 0;
		this.type = 0;
		this.comparatorValue = (float) 0.0;
		this.rangeValues = null;
	}

	/**
	 * Public constructor for the condition with parameters.
	 * 
	 * @param count The number of answers which makes the condition true.
	 */
	public Condition(int nextId, int type, float comparatorValue, HashSet<Integer> rangeValues) {
		this.nextId = nextId;
		this.type = type;
		this.comparatorValue = comparatorValue;
		this.rangeValues = rangeValues;
	}
	
	/**
	 * This method extracts a condition from a XML structure.
	 * 
	 * @param xml The XML structure.
	 * @return The Condition object.
	 */
//	public static Condition extractFromXML(String xml) {
//		Condition returned;
//		int index1, index2;
//		int type, nextid;
//		HashSet<Integer> range = null;
//		float value = 0;
//		index1 = xml.indexOf("TIPO=\"");
//		index2 = xml.indexOf("\"", index1+6);
//		type = Integer.parseInt(xml.substring(index1 + 6, index2));
//		index1 = xml.indexOf("IDESTADOSIGUIENTE=\"");
//		index2 = xml.indexOf("\"", index1+19);
//		nextid = Integer.parseInt(xml.substring(index1 + 19, index2));
//		if ((type == 0)||(type == 1)) {
//			index1 = xml.indexOf("RESPUESTAS=\"");
//			index2 = xml.indexOf("\"", index1+12);
//			range = Condition.extractRangeValues(xml.substring(index1 + 12, index2));
//		}
//		else if (type == 2) {
//			index1 = xml.indexOf("VALORCOMPARADOR=\"");
//			index2 = xml.indexOf("\"", index1+15);
//			value = Float.parseFloat(xml.substring(index1 + 15, index2));
//		}
//		returned = new Condition(nextid, type, value, range);
//		return returned;
//	}
	
	public static Condition extractFromXML(XmlPullParser info) {
		Condition condition = new Condition();
		try {
	        int event = info.getEventType();
	        while (event != XmlPullParser.END_DOCUMENT) {
	            if(event == XmlPullParser.START_DOCUMENT) {
	            } else if(event == XmlPullParser.END_DOCUMENT) {    	
	            } else if(event == XmlPullParser.START_TAG) {
	                if (info.getName().equalsIgnoreCase("CONDICION")) {
	                	condition.nextId = Integer.parseInt(info.getAttributeValue(info.getNamespace(), "IDESTADOSIGUIENTE"));
	                	if (info.getAttributeValue(info.getNamespace(), "RESPUESTAS") != null) {
	                		condition.rangeValues = Condition.extractRangeValues(info.getAttributeValue(info.getNamespace(), "RESPUESTAS"));
	                	}
	                	else if (info.getAttributeValue(info.getNamespace(), "VALORCOMPARADOR") != null) {
	                		condition.comparatorValue = Float.parseFloat(info.getAttributeValue(info.getNamespace(), "VALORCOMPARADOR"));
	                	}
	                }
	            } else if(event == XmlPullParser.END_TAG) {
	                if (info.getName().equalsIgnoreCase("CONDICION")) {
	                	break;
	                }
	            }
	            event = info.next();
	        }
		} catch (XmlPullParserException e) {
			condition = null;
			e.printStackTrace();
		} catch (IOException e) {
			condition = null;
			e.printStackTrace();
		} catch (NullPointerException e) {
			condition = null;
			e.printStackTrace();
		} catch (NumberFormatException e) {
			condition = null;
			e.printStackTrace();
		}
		return condition;
	}

	/**
	 * This method extrac the range of values which makes a condition to be true.
	 * 
	 * @param range The range of values at String format.
	 * @return The range of values at int[] format.
	 */
	private static HashSet<Integer> extractRangeValues(String range) {
		HashSet<Integer> returned = null;
		String[] middle = range.split("|");
		returned = new HashSet<Integer>();
		int j=0;
		for (int i = 1; i < middle.length; i=i+2) {
			returned.add(Integer.parseInt(middle[i]));
			j++;
		}
		return returned;
	}
	
}
