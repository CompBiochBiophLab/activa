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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.o2hlink.activ8.common.entity.EventFrequency;
import com.o2hlink.activ8.common.entity.MappingClass;
import com.o2hlink.activ8.common.entity.MappingProperty;
import com.o2hlink.activ8.common.entity.PrivacyLevel;


/**
 * Event is an action which has an start and end time
 * @author Miguel Angel Hernandez
 **/
@Entity
@Table(name="event")
@MappingClass(type=com.o2hlink.activ8.client.entity.Event.class)
@NamedQueries({
	@NamedQuery(name="event.search", query="from Event where name like concat('%', concat(:query, '%'))")
})
public class Event implements Serializable, HasLog, HasPrivacyPolicy {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = 8749337008732958709L;
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
	private EventFrequency frequency = EventFrequency.NONE;
	/**
	 * 
	 **/
	private Date endFrequency = null;
	/**
	 * 
	 **/
	private List<LogRecord> log = new ArrayList<LogRecord>();
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
	private List<User> users = new ArrayList<User>();
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
	 * @param start the start to set
	 */
	public void setStart(Date start) {
		this.start = start;
	}
	/**
	 * @return the start
	 */
	@Column(name="start", nullable=false)
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
	@Column(name="end", nullable=false)
	@MappingProperty
	public Date getEnd() {
		return end;
	}
	/**
	 * @param frequency the frequency to set
	 */
	public void setFrequency(EventFrequency frequency) {
		this.frequency = frequency;
	}
	/**
	 * @return the frequency
	 */
	@Column(name="frequency", nullable=false)
	@Enumerated(EnumType.ORDINAL)
	@MappingProperty
	public EventFrequency getFrequency() {
		return frequency;
	}
	/**
	 * @param endFrequency the endFrequency to set
	 */
	public void setEndFrequency(Date endFrequency) {
		this.endFrequency = endFrequency;
	}
	/**
	 * @return the endFrequency
	 */
	@Column(name="endfrequency", nullable=true)
	@MappingProperty
	public Date getEndFrequency() {
		return endFrequency;
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
			name="event_log",
			joinColumns={@JoinColumn(name="event")},
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
			mappedBy="events",
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
			cascade={},
			targetEntity=User.class)
	@JoinColumn(name="owner", nullable=false)
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
