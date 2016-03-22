/**
 * 
 */
package com.o2hlink.activ8.server.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.o2hlink.activ8.common.entity.MappingClass;
import com.o2hlink.activ8.common.entity.MappingProperty;

/**
 * Answer to a Question in a Questionnaire
 * @author Miguel Angel Hernandez
 **/
@Entity
@Table(name="answer")
@MappingClass(type=com.o2hlink.activ8.client.entity.Answer.class)
public class Answer implements Serializable {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = -6675765585865192416L;
	/**
	 * 
	 **/
	private long id;
	/**
	 * 
	 **/
	private String name;
	/**
	 * 
	 **/
	private double value = 0;
	/**
	 * 
	 **/
	private MultiQuestion question;
//CONSTRUCTOR
	/**
	 * 
	 **/
	public Answer(){
		
	}
//METHODS
	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}
	/**
	 * @return the id
	 */
	@Id
	@Column(name="id", updatable=false, nullable=false, unique=true)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@MappingProperty
	public long getId() {
		return id;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the name
	 */
	@Column(name="name", length=256)
	@MappingProperty
	public String getName() {
		return name;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(double value) {
		this.value = value;
	}
	/**
	 * @return the value
	 */
	@Column(name="value", nullable=false)
	@MappingProperty
	public double getValue() {
		return value;
	}
	/**
	 * @param question the question to set
	 */
	public void setQuestion(MultiQuestion question) {
		this.question = question;
	}
	/**
	 * @return the question
	 */
	@ManyToOne(
			cascade={CascadeType.PERSIST},
			targetEntity=MultiQuestion.class)
	@JoinColumn(name="question", nullable=false, updatable=false)
	public MultiQuestion getQuestion() {
		return question;
	}
}
