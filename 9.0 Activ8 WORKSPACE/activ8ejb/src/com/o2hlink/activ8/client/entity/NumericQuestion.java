package com.o2hlink.activ8.client.entity;

import java.util.ArrayList;
import java.util.List;

import com.o2hlink.activ8.common.entity.MappingClass;
import com.o2hlink.activ8.common.entity.MappingProperty;

/**
 * A question whose value is expected to be a number. It may contain numeric conditions.
 * @author Miguel Angel Hernandez
 **/
@MappingClass(name="com.o2hlink.activ8.server.entity.NumericQuestion")
public class NumericQuestion extends Question {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = 4532749041334171877L;
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
	@MappingProperty
	public List<NumericCondition> getConditions() {
		return conditions;
	}
	/**
	 * 
	 **/
	@Override
	public boolean isNumericType() {
		return true;
	}
}
