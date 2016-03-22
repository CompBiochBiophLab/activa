package com.o2hlink.activ8.client.entity;

/**
 * @author Miguel Angel Hernandez
 **/
public class QuestionaryResult extends Sample {
//FIELDS
	/**
	 * 
	 **/
	private static final long serialVersionUID = -4117979123338130184L;
	/**
	 * 
	 **/
	private Questionnaire questionary;
//CONSTRUCTOR
	/**
	 * 
	 **/
	public QuestionaryResult(){
		
	}
//METHODS
	/**
	 * @param questionary the questionary to set
	 */
	public void setQuestionary(Questionnaire questionary) {
		this.questionary = questionary;
	}
	/**
	 * @return the questionary
	 */
	public Questionnaire getQuestionary() {
		return questionary;
	}
}
