package com.o2hlink.activ8.server.entity;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.o2hlink.activ8.common.entity.MappingClass;



/**
 * A question whose answer is a text
 * @author Miguel Angel Hernandez
 **/
@Entity
@Table(name="textquestion")
@PrimaryKeyJoinColumn(name="id", referencedColumnName="id")
@MappingClass(type=com.o2hlink.activ8.client.entity.TextQuestion.class)
public class TextQuestion extends Question {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = -6044584581645536042L;
//CONSTRUCTOR
	/**
	 * 
	 **/
	public TextQuestion(){
		
	}
}
