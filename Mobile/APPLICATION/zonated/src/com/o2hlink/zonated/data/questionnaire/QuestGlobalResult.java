package com.o2hlink.zonated.data.questionnaire;

import java.util.Hashtable;

public class QuestGlobalResult {

	/**
	 * Hashtable with the keys representing the IDs of the questions of the questionnaire and the values 
	 * representing the result of the question.
	 */
	public Hashtable<Long, QuestionResult> results;
	
	/**
	 * Number of people has answered this questionnaire
	 */
	public int people;

	public QuestGlobalResult() {
		people = 0;
		results = new Hashtable<Long, QuestionResult>();
	}
	
}
