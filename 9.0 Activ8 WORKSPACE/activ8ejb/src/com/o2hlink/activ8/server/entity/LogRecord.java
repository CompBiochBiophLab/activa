package com.o2hlink.activ8.server.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.o2hlink.activ8.common.entity.MappingClass;
import com.o2hlink.activ8.common.entity.MappingProperty;

/**
 * A set of log records compose a log
 * @author Miguel Angel Hernandez
 **/
@Entity
@Table(name="logrecord")
@MappingClass(type=com.o2hlink.activ8.client.entity.LogRecord.class)
public class LogRecord implements Serializable, HasComments {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = -3322893912865089752L;
	/**
	 * 
	 **/
	private long id;
	/**
	 * 
	 **/
	private Date date;
	/**
	 * 
	 **/
	private String name;
	/**
	 * 
	 **/
	private List<Comment> comments = new ArrayList<Comment>();
//CONSTRUCTOR
	/**
	 * 
	 **/
	public LogRecord(){
		
	}
//METHODS
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the name
	 */
	@Column(name="name", length=64)
	@MappingProperty
	public String getName() {
		return name;
	}
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
	@Column(name="id", unique=true, nullable=false, updatable=false)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@MappingProperty
	public long getId() {
		return id;
	}
	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}
	/**
	 * @return the date
	 */
	@Column(name="`date`")
	@MappingProperty
	public Date getDate() {
		return date;
	}
	/**
	 * @param comments the comments to set
	 */
	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
	/**
	 * @return the comments
	 */
	@OneToMany(
			targetEntity=Comment.class,
			cascade=CascadeType.ALL,
			fetch=FetchType.LAZY)
	@JoinTable(
			name="log_comment",
			joinColumns={@JoinColumn(name="log")},
			inverseJoinColumns={@JoinColumn(name="comment")})
	public List<Comment> getComments() {
		return comments;
	}
}
