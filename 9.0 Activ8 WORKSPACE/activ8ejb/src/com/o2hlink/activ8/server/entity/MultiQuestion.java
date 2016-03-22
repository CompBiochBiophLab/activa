package com.o2hlink.activ8.server.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.o2hlink.activ8.common.entity.MappingClass;
import com.o2hlink.activ8.common.entity.MappingProperty;



/**
 * A question with multiple answers and multiple conditions
 * @author Miguel Angel Hernandez
 **/
@Entity
@Table(name="multiquestion")
@PrimaryKeyJoinColumn(name="id", referencedColumnName="id")
@MappingClass(type=com.o2hlink.activ8.client.entity.MultiQuestion.class)
public class MultiQuestion extends Question {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = -6607116529739330863L;
	/**
	 * 
	 **/
	private boolean multiple = false;
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
	@OneToMany(
			cascade={CascadeType.ALL},
			targetEntity=Answer.class,
			mappedBy="question")
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
	@OneToMany(
			cascade={CascadeType.ALL},
			targetEntity=Condition.class,
			mappedBy="question")
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
	@Column(name="multiple")
	@MappingProperty
	public boolean isMultiple() {
		return multiple;
	}
}
