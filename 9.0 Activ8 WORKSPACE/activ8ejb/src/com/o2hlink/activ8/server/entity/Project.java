package com.o2hlink.activ8.server.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.o2hlink.activ8.common.entity.MappingClass;
import com.o2hlink.activ8.common.entity.MappingProperty;
import com.o2hlink.activ8.common.entity.PrivacyLevel;

/**
 * @author Miguel Angel Hernandez
 **/
@Entity
@Table(name="project")
@MappingClass(type=com.o2hlink.activ8.client.entity.Project.class)
public class Project implements Serializable, HasLog, HasPrivacyPolicy {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = -6445195995534454804L;	
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
	private String description;
	/**
	 * 
	 **/
	private Date start;
	/**
	 * 
	 **/
	private Date end;
	/**
	 * 
	 **/
	private User owner;
	/**
	 * 
	 **/
	private PrivacyLevel privacyLevel;
	/**
	 * 
	 **/
	private List<LogRecord> log = new ArrayList<LogRecord>();
	/**
	 * 
	 **/
	private List<User> allowedUsers = new ArrayList<User>();
	/**
	 * 
	 **/
	private List<Workpackage> workpackages = new ArrayList<Workpackage>();
//METHODS
	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}
	/**
	 * 
	 **/
	@Id 
	@Column(name="id", unique=true, nullable=false, updatable=false) 
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
	@Column(name="name", length=52, updatable=false)
	@MappingProperty
	public String getName() {
		return name;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the description
	 */
	@Column(name="username", unique=true, length=24, updatable=false)
	@MappingProperty
	public String getDescription() {
		return description;
	}
	/**
	 * 
	 **/
	public void setPrivacyLevel(PrivacyLevel level) {
		this.privacyLevel = level;
	}
	/**
	 * 
	 **/
	@Column(name="privacylevel")
	@Enumerated(EnumType.ORDINAL)
	public PrivacyLevel getPrivacyLevel() {
		return privacyLevel;
	}
	/**
	 * 
	 **/
	@Transient
	public PrivacyLevel getDefaultPrivacyLevel() {
		return PrivacyLevel.RESTRICTED;
	}
	/**
	 * 
	 **/
	public void setOwner(User user){
		this.owner = user;
	}
	/**
	 * 
	 **/
	@ManyToOne(
			cascade={CascadeType.REFRESH},
			targetEntity=User.class)
	@JoinColumn(name="owner")
	public User getOwner() {
		return owner;
	}
	/**
	 * 
	 **/
	public void setLog(List<LogRecord> log){
		this.log = log;
	}
	/**
	 * 
	 **/
	@OneToMany(
			targetEntity=LogRecord.class,
			cascade=CascadeType.ALL,
			fetch=FetchType.LAZY)
	@JoinTable(
			name="project_log",
			joinColumns={@JoinColumn(name="project")},
			inverseJoinColumns={@JoinColumn(name="log")})
	public List<LogRecord> getLog() {
		return log;
	}
	/**
	 * 
	 **/
	public void setAllowedUsers(List<User> users) {
		this.allowedUsers = users;
	}	
	/**
	 * 
	 **/
	@ManyToMany(
			cascade={CascadeType.PERSIST},
			targetEntity=User.class,
			fetch=FetchType.LAZY)
	@JoinTable(
			name="project_user",
			joinColumns={@JoinColumn(name="project")},
			inverseJoinColumns={@JoinColumn(name="user")})
	public List<User> getAllowedUsers() {
		return allowedUsers;
	}
	/**
	 * @param start the start to set
	 */
	public void setStart(Date start) {
		this.start = start;
	}
	/**
	 * @return the start
	 */
	@Column(name="start")
	@MappingProperty
	public Date getStart() {
		return start;
	}
	/**
	 * @param end the end to set
	 */
	public void setEnd(Date end) {
		this.end = end;
	}
	/**
	 * @return the end
	 */
	@Column(name="end")
	@MappingProperty
	public Date getEnd() {
		return end;
	}
	/**
	 * @param workpackages the workpackages to set
	 */
	public void setWorkpackages(List<Workpackage> workpackages) {
		this.workpackages = workpackages;
	}
	/**
	 * @return the workpackages
	 */
	@OneToMany(
			cascade={CascadeType.ALL},
			fetch=FetchType.LAZY,
			mappedBy="project",
			targetEntity=Workpackage.class)
	public List<Workpackage> getWorkpackages() {
		return workpackages;
	}
}
