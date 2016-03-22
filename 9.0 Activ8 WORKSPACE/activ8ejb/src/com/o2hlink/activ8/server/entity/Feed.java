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
 * @author Miguel Angel Hernandez
 **/
@Entity
@Table(name="feed")
@MappingClass(type=com.o2hlink.activ8.client.entity.Feed.class)
public class Feed implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7023061449635471230L;
	/**
	 * 
	 **/
	private long id;
	/**
	 * 
	 **/
	private String url;
	/**
	 * 
	 **/
	private User user;
//CONSTRUCTOR
	/**
	 * 
	 **/
	public Feed(){
		
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
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	/**
	 * @return the url
	 */
	@Column(name="url", nullable=false)
	@MappingProperty
	public String getUrl() {
		return url;
	}
	/**
	 * 
	 **/
	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}
	/**
	 * @return the user
	 */
	@ManyToOne(
			targetEntity=User.class,
			cascade={CascadeType.REFRESH})
	@JoinColumn(name="user", nullable=false, updatable=false)	
	public User getUser() {
		return user;
	}
}
