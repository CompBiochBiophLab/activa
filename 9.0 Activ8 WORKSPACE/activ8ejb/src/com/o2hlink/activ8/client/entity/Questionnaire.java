package com.o2hlink.activ8.client.entity;

import java.util.ArrayList;
import java.util.List;

import com.o2hlink.activ8.common.entity.MappingClass;
import com.o2hlink.activ8.common.entity.MappingProperty;

/**
 * A questionnaire is a measurement, it is composed of multiple questions, answers and conditions.
 * @author Miguel Angel Hernandez
 **/
@MappingClass(name="com.o2hlink.activ8.server.entity.Questionnaire")
public class Questionnaire extends Measurement {
//FIELDS
	/**
	 * 
	 **/
	private static final long serialVersionUID = -4829263412906146510L;
	/**
	 * 
	 **/
	private List<Question> questions = new ArrayList<Question>();
//CONSTRUCTOR
	/**
	 * 
	 **/
	public Questionnaire(){
		
	}
//METHODS
	/**
	 * @param questions the questions to set
	 */
	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}
	/**
	 * @return the questions
	 */
	@MappingProperty
	public List<Question> getQuestions() {
		return questions;
	}
}
