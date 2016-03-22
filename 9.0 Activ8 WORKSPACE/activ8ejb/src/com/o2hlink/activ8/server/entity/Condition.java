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
 * @author Miguel Angel Hernandez
 **/
@Entity
@Table(name="`condition`")
@Inheritance(strategy=InheritanceType.JOINED)
@MappingClass(type=com.o2hlink.activ8.client.entity.Condition.class)
public class Condition implements Serializable {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = 8699309037137393091L;
	/**
	 * 
	 **/
	private long id;
	/**
	 * 
	 **/
	private Question next;
	/**
	 * 
	 **/
	private Question question;
//CONSTRUCTOR
	/**
	 * 
	 **/
	public Condition(){
		
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
	@Column(name="id", nullable=false, updatable=false, unique=true)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@MappingProperty
	public long getId() {
		return id;
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
			targetEntity=Question.class)
	@JoinColumn(name="next", nullable=false)
	@MappingProperty
	public Question getNext() {
		return next;
	}
	/**
	 * @param question the question to set
	 */
	public void setQuestion(Question question) {
		this.question = question;
	}
	/**
	 * @return the question
	 */
	@ManyToOne(
			cascade={CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
			targetEntity=Question.class)
	@JoinColumn(name="question", nullable=false, updatable=false)
	public Question getQuestion() {
		return question;
	}
}

