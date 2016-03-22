package com.o2hlink.activ8.client.entity;

import java.util.ArrayList;
import java.util.List;

import com.o2hlink.activ8.common.entity.MappingClass;
import com.o2hlink.activ8.common.entity.MappingProperty;

/**
 * A multiple condition is a condition fired by a set of answers
 * @author Miguel Angel Hernandez
 **/
@MappingClass(name="com.o2hlink.activ8.server.entity.MultiCondition")
public class MultiCondition extends Condition {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = 2827006645466015731L;
	/**
	 * 
	 **/
	private List<Answer> answers = new ArrayList<Answer>();
//CONSTRUCTOR
	/**
	 * 
	 **/
	public MultiCondition(){
		
	}
//METHODS
	/**
	 * @param answers the answers to set
	 */
	public void setAnswers(List<Answer> answers) {
		this.answers = answers;
	}
	/**
	 * @return the answers
	 */
	@MappingProperty
	public List<Answer> getAnswers() {
		return answers;
	}
}
