package com.o2hlink.activ8.server.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.o2hlink.activ8.common.entity.MappingClass;
import com.o2hlink.activ8.common.entity.MappingProperty;

/**
 * A question whose answer is a number (integer or real)
 * @author Miguel Angel Hernandez
 **/
@Entity
@Table(name="numquestion")
@PrimaryKeyJoinColumn(name="id", referencedColumnName="id")
@MappingClass(type=com.o2hlink.activ8.client.entity.NumericQuestion.class)
public class NumericQuestion extends Question {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = -6044584581645536042L;
	/**
	 * 
	 **/
	private List<NumericCondition> conditions = new ArrayList<NumericCondition>();
//CONSTRUCTOR
	/**
	 * 
	 **/
	public NumericQuestion(){
		
	}
//METHODS
	/**
	 * @param conditions the conditions to set
	 */
	public void setConditions(List<NumericCondition> conditions) {
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
	public List<NumericCondition> getConditions() {
		return conditions;
	}
}
