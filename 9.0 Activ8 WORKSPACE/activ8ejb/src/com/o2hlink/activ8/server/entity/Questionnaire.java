/**
 * 
 */
package com.o2hlink.activ8.server.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.o2hlink.activ8.common.entity.MappingClass;


/**
 * Questionnaire is a measurement
 * @author Miguel Angel Hernandez
 **/
@Entity
@Table(name="questionnaire")
@PrimaryKeyJoinColumn(name="id", referencedColumnName="id")
@MappingClass(type=com.o2hlink.activ8.client.entity.Questionnaire.class)
public class Questionnaire extends Measurement {
//FIELDS
	/**
	 * 
	 **/
	private static final long serialVersionUID = 5328033058932219241L;
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
	@OneToMany(
			cascade={CascadeType.ALL},
			targetEntity=Question.class,
			mappedBy="questionnaire")
	public List<Question> getQuestions() {
		return questions;
	}
}
