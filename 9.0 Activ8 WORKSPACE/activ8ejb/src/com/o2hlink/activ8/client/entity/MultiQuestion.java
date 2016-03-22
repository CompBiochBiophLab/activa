package com.o2hlink.activ8.client.entity;

import java.util.ArrayList;
import java.util.List;

import com.o2hlink.activ8.common.entity.MappingClass;
import com.o2hlink.activ8.common.entity.MappingProperty;

/**
 * A question with multiple answers and multiple conditions
 * @author Miguel Angel Hernandez
 **/
@MappingClass(name="com.o2hlink.activ8.server.entity.MultiQuestion")
public class MultiQuestion extends Question {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = 4532749041334171877L;
	/**
	 * 
	 **/
	private boolean multiple;
	/**
	 *
	 **/
	private List<Answer> answers = new ArrayList<Answer>();
	/**
	 *
	 **/
	private List<MultiCondition> conditions = new ArrayList<MultiCondition>();
//CONSTRUCTOR
	/**
	 * 
	 **/
	public MultiQuestion(){
		
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
	/**
	 * @param conditions the conditions to set
	 */
	public void setConditions(List<MultiCondition> conditions) {
		this.conditions = conditions;
	}
	/**
	 * @return the conditions
	 */
	@MappingProperty
	public List<MultiCondition> getConditions() {
		return conditions;
	}
	/**
	 * @param multiple the multiple to set
	 */
	public void setMultiple(boolean multiple) {
		this.multiple = multiple;
	}
	/**
	 * @return the multiple
	 */
	@MappingProperty
	public boolean isMultiple() {
		return multiple;
	}
	/**
	 * 
	 **/
	@Override
	public boolean isMultipleType(){
		return true;
	}
}
