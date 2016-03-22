package com.o2hlink.activ8.client.action;

import com.o2hlink.activ8.client.entity.Questionnaire;
import com.o2hlink.activ8.client.response.VoidResponse;

/**
 * Create a new questionnaire. A questionnaire is a measurement and statistical tool.
 * @author Miguel Angel Hernandez
 **/
public class QuestionnaireNewAction implements Action<VoidResponse> {
//FIELDS
	/**
	 * 
	 **/
	private static final long serialVersionUID = -1518123138347916910L;
	/**
	 * 
	 **/
	private Questionnaire questionnaire;
//CONSTRUCTOR
	/**
	 * Empty constructor to allow serialization
	 **/
	public QuestionnaireNewAction(){
		
	}
	/**
	 * 
	 **/
	public QuestionnaireNewAction(Questionnaire questionnaire){
		this.setQuestionnaire(questionnaire);
	}
//METHODS
	/**
	 * @param questionnaire the questionnaire to set
	 */
	public void setQuestionnaire(Questionnaire questionnaire) {
		this.questionnaire = questionnaire;
	}
	/**
	 * @return the questionnaire
	 */
	public Questionnaire getQuestionnaire() {
		return questionnaire;
	}
}
