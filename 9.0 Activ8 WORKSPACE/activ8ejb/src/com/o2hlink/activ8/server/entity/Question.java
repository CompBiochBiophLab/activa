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
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.o2hlink.activ8.common.entity.MappingClass;
import com.o2hlink.activ8.common.entity.MappingProperty;



/**
 * Any question
 * @author Miguel Angel Hernandez
 **/
@Entity
@Table(name="question")
@Inheritance(strategy=InheritanceType.JOINED)
@MappingClass(type=com.o2hlink.activ8.client.entity.Question.class)
public class Question implements Serializable {
//FIELDS
	/**
	 * 
	 **/
	private static final long serialVersionUID = 520062413598005268L;
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
	private boolean optional;
	/**
	 * 
	 **/
	private Question next = null;
	/**
	 * 
	 **/
	private Questionnaire questionnaire;
//CONSTRUCTOR
	/**
	 * 
	 **/
	public Question(){
		
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
	@Column(name="id", nullable=false, updatable=false)
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
	 * @param questionnaire the questionnaire to set
	 */
	public void setQuestionnaire(Questionnaire questionnaire) {
		this.questionnaire = questionnaire;
	}
	/**
	 * @return the questionnaire
	 */
	@ManyToOne(
			cascade={CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
			targetEntity=Questionnaire.class)
	@JoinColumn(name="questionnaire", nullable=false, updatable=false)
	public Questionnaire getQuestionnaire() {
		return questionnaire;
	}
	/**
	 * @param next the next to set
	 */
	public void setNext(Question next) {
		this.next = next;
	}
	/**
	 * @return the next
	 */
	@OneToOne(
			cascade={CascadeType.ALL},
			targetEntity=Question.class,
			optional=true)
	@JoinColumn(name="next", nullable=true)
	@MappingProperty
	public Question getNext() {
		return next;
	}
	/**
	 * @param optional the optional to set
	 */
	public void setOptional(boolean optional) {
		this.optional = optional;
	}
	/**
	 * @return the optional
	 */
	@Column(name="optional")
	@MappingProperty
	public boolean isOptional() {
		return optional;
	}
}
