package com.o2hlink.activ8.server.entity;

import java.util.ArrayList;
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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.o2hlink.activ8.common.entity.MappingClass;
import com.o2hlink.activ8.common.entity.MappingProperty;
import com.o2hlink.activ8.common.entity.PrivacyLevel;

/**
 * A group of interest
 * @author Miguel Angel Hernandez
 **/
@Entity
@Table(name="`group`")
@MappingClass(type=com.o2hlink.activ8.client.entity.Group.class)
@NamedQueries({
	@NamedQuery(name="group.search", query="from Group where name like concat('%', concat(:query, '%'))")
})
public class Group implements HasLog, HasPrivacyPolicy {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = -2181478337142542663L;
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
	private PrivacyLevel privacyLevel;
	/**
	 * 
	 **/
	private User owner;
	/**
	 * 
	 **/
	private List<LogRecord> log = new ArrayList<LogRecord>();
	/**
	 * 
	 **/
	private List<User> users = new ArrayList<User>();
//CONSTRUCTOR
	/**
	 * 
	 **/
	public Group(){
		
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
	@Column(name="name", nullable=true, length=128)
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
	@Column(name="description", nullable=true, length=512)
	@MappingProperty
	public String getDescription() {
		return description;
	}
	/**
	 * @param log the log to set
	 */
	public void setLog(List<LogRecord> log) {
		this.log = log;
	}
	/**
	 * @return the log
	 */
	@OneToMany(
			targetEntity=LogRecord.class,
			cascade=CascadeType.ALL,
			fetch=FetchType.LAZY)
	@JoinTable(
			name="group_log",
			joinColumns={@JoinColumn(name="`group`")},
			inverseJoinColumns={@JoinColumn(name="log")})
	public List<LogRecord> getLog() {
		return log;
	}
	/**
	 * @param users the users to set
	 */
	public void setUsers(List<User> users) {
		this.users = users;
	}

	/**
	 * @return the users
	 */
	@ManyToMany(
			targetEntity=User.class,
			mappedBy="groups",
			cascade={CascadeType.PERSIST})
	public List<User> getUsers() {
		return users;
	}
	/**
	 * Set the privacy level of this particular object.
	 * @param	level The privacy level
	 **/
	public void setPrivacyLevel(PrivacyLevel level){
		this.privacyLevel = level;
	}
	/**
	 * Get the privacy level of this particular object.
	 * @return	The privacy level
	 **/
	@Column(name="privacylevel")
	@Enumerated(EnumType.ORDINAL)
	public PrivacyLevel getPrivacyLevel(){
		return privacyLevel;
	}
	/**
	 * 
	 **/
	@Transient
	public PrivacyLevel getDefaultPrivacyLevel(){
		return PrivacyLevel.PUBLIC;
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
	public User getOwner(){
		return owner;
	}
	/**
	 * If the privacy level is {@link PrivacyLevel#PRIVATE}, {@link PrivacyLevel#RESTRICTED} return the set of users that have access to this object. A privacy level of {@link PrivacyLevel#PUBLIC} will ignore the resulting value of this property.
	 **/
	@Transient
	public List<User> getAllowedUsers(){
		return getUsers();
	}
}
