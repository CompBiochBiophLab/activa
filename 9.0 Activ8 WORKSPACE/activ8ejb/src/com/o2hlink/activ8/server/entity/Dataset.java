/**
 * 
 */
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.o2hlink.activ8.common.entity.HasDescription;
import com.o2hlink.activ8.common.entity.HasName;
import com.o2hlink.activ8.common.entity.MappingClass;
import com.o2hlink.activ8.common.entity.MappingProperty;
import com.o2hlink.activ8.common.entity.PrivacyLevel;


/**
 * Experimental data defined as a set of data
 * @author Miguel Angel Hernandez
 **/
@Entity
@Table(name="dataset")
@MappingClass(type=com.o2hlink.activ8.client.entity.Dataset.class)
public class Dataset implements HasLog, HasPrivacyPolicy, HasName, HasDescription, HasPatients, HasSubsets {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = 3453420369525577938L;
	/**
	 * 
	 */
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
	private List<User> allowedUsers = new ArrayList<User>();
	/**
	 * 
	 **/
	private List<Patient> patients = new ArrayList<Patient>();
	/**
	 * 
	 **/
	private List<Subset> subsets = new ArrayList<Subset>();
//CONSTRUCTOR
	/**
	 * 
	 **/
	public Dataset(){
		
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
	@Column(name="description")
	@MappingProperty
	public String getDescription() {
		return description;
	}
	/**
	 * @param patients the patients to set
	 */
	public void setPatients(List<Patient> patients) {
		this.patients = patients;
	}
	/**
	 * @return the patients
	 */
	@ManyToMany(
			cascade={CascadeType.PERSIST},
			targetEntity=Patient.class)
	@JoinTable(name="dataset_patient",
			joinColumns=@JoinColumn(name="dataset"),
			inverseJoinColumns=@JoinColumn(name="patient"))
	public List<Patient> getPatients() {
		return patients;
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
			name="dataset_log",
			joinColumns={@JoinColumn(name="dataset")},
			inverseJoinColumns={@JoinColumn(name="log")})
	public List<LogRecord> getLog() {
		return log;
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
	@JoinColumn(name="owner", nullable=false)
	public User getOwner(){
		return owner;
	}
	/**
	 * @param users the users to set
	 */
	public void setAllowedUsers(List<User> users) {
		this.allowedUsers = users;
	}
	/**
	 * If the privacy level is {@link PrivacyLevel#PRIVATE}, {@link PrivacyLevel#RESTRICTED} return the set of users that have access to this object. A privacy level of {@link PrivacyLevel#PUBLIC} will ignore the resulting value of this property.
	 **/
	@ManyToMany(
			cascade={CascadeType.PERSIST},
			targetEntity=User.class,
			fetch=FetchType.LAZY)
	@JoinTable(
			name="dataset_user",
			joinColumns={@JoinColumn(name="dataset")},
			inverseJoinColumns={@JoinColumn(name="user")})
	public List<User> getAllowedUsers(){
		return allowedUsers;
	}
	/**
	 * @param subsets the subsets to set
	 */
	public void setSubsets(List<Subset> subsets) {
		this.subsets = subsets;
	}
	/**
	 * @return the subsets
	 */
	@OneToMany(
			cascade={CascadeType.ALL},
			fetch=FetchType.LAZY,
			targetEntity=Subset.class,
			mappedBy="dataset")
	public List<Subset> getSubsets() {
		return subsets;
	}
}
