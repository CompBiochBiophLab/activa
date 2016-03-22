package com.o2hlink.activ8.server.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.o2hlink.activ8.common.entity.MappingClass;
import com.o2hlink.activ8.common.entity.MappingProperty;

/**
 * @author Miguel Angel Hernandez
 **/
@Entity
@Table(name="multicondition")
@PrimaryKeyJoinColumn(name="id", referencedColumnName="id")
@MappingClass(type=com.o2hlink.activ8.client.entity.MultiCondition.class)
public class MultiCondition extends Condition {
//FIELDS
	/**
	 * 
	 **/
	private static final long serialVersionUID = 1511100663289954011L;
	/**
	 * 
	 **/
	private List<Answer> answers;
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
	@ManyToMany(
			cascade={CascadeType.PERSIST},
			fetch=FetchType.EAGER,
			targetEntity=Answer.class)
	@JoinTable(
			name="answer_condition",
			joinColumns={@JoinColumn(name="`condition`")},
			inverseJoinColumns={@JoinColumn(name="answer")})
	@MappingProperty
	public List<Answer> getAnswers() {
		return answers;
	}
}
