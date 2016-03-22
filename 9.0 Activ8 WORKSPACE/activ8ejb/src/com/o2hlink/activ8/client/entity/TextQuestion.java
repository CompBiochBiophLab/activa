package com.o2hlink.activ8.client.entity;

import com.o2hlink.activ8.common.entity.MappingClass;

/**
 * A question in a questionnaire whose answer is a text.
 * @author Miguel Angel Hernandez
 **/
@MappingClass(name="com.o2hlink.activ8.server.entity.TextQuestion")
public class TextQuestion extends Question {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = 4532749041334171877L;
//CONSTRUCTOR
	/**
	 * 
	 **/
	public TextQuestion(){
		
	}
//METHODS
	/**
	 * 
	 **/
	@Override
	public boolean isTextType() {
		return true;
	}
}
