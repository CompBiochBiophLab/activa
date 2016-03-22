package com.o2hlink.zonated.data.questionnaire;

import java.util.ArrayList;
import java.util.Hashtable;

import com.o2hlink.activ8.client.entity.Array;

public class QuestionResult {

	/**
	 * Type of question (0 for single or multiple, 1 for numeric and 2 for text question)
	 */
	public int type;
	
	/**
	 * Hashtable representing with the keys representing the possible answers IDs and the values representing 
	 * the percentage of people has answered this question (if single question)
	 */
	public Hashtable<Long, Integer> singleanswers;
	
	/**
	 * Number of people has answered 5 or more (if single question)
	 */
	public int singlepeople;
	
	/**
	 * Average value answered (if numeric question)
	 */
	public Double numavrg;
	
	/**
	 * Percentage of people has answered less of 5 (if numeric question)
	 */
	public int numlessof5;
	
	/**
	 * Percentage of people has answered 5 or more (if numeric question)
	 */
	public int num5ormore;
	
	/**
	 * Percentage of people has answered 5 or more (if numeric question)
	 */
	public int numpeople;
	
	
	/**
	 * Array representing the whole set of values responded (if text question)
	 */
	public ArrayList<String> textanswers;
	
	/**
	 * Number of people has answered 5 or more (if text question)
	 */
	public int textpeople;

	public QuestionResult() {
		singleanswers = new Hashtable<Long, Integer>();
		singlepeople = 0;
		numavrg = 0d;
		numlessof5 = 0;
		num5ormore = 0;
		numpeople = 0;
		textanswers = new ArrayList<String>();
		textpeople = 0;
	}
	
}
