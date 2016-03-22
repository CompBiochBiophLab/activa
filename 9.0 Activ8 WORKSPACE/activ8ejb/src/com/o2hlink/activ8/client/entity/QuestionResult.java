package com.o2hlink.activ8.client.entity;

import java.io.Serializable;


/**
 * A question result involves which answer has been supplied for which question
 * @author Miguel Angel Hernandez
 **/
public class QuestionResult implements Serializable {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = 1190652484845242721L;	
	/**
	 * 
	 **/
	private Question question;
	/**
	 * 
	 **/
	private Answer answer;
//CONSTRUCTOR
	/**
	 * 
	 **/
	public QuestionResult(){
		
	}
//METHODS
	/**
	 * @param question the question to set
	 */
	public void setQuestion(Question question) {
		this.question = question;
	}
	/**
	 * @return the question
	 */
	public Question getQuestion() {
		return question;
	}
	/**
	 * @param answer the answer to set
	 */
	public void setAnswer(Answer answer) {
		this.answer = answer;
	}
	/**
	 * @return the answer
	 */
	public Answer getAnswer() {
		return answer;
	}
}
