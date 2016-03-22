package com.o2hlink.activ8.server.entity;

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
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import com.o2hlink.activ8.common.entity.MappingClass;
import com.o2hlink.activ8.common.entity.MappingProperty;
import com.o2hlink.activ8.common.entity.PrivacyLevel;
import com.o2hlink.activ8.common.entity.Sex;

/**
 * A user in the application
 * @author Miguel Angel Hernandez
 **/
@Entity
@Table(name="`user`")
@Inheritance(strategy=InheritanceType.JOINED)
@MappingClass(type=com.o2hlink.activ8.client.entity.User.class)
@NamedQueries({
	@NamedQuery(name="user.found", query="from User where username=:username"),
	@NamedQuery(name="user.login", query="from User where username=:username and password=:password")
})
abstract public class User implements HasLog, HasPrivacyPolicy, HasUsers, HasGroups, HasComments {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = -820820892321748560L;
	/**
	 * 
	 **/
	private long id;
	/**
	 * 
	 **/
	private String username;
	/**
	 * 
	 **/
	private String password;
	/**
	 * 
	 **/
	private String firstName;
	/**
	 * 
	 **/
	private String lastName;
	/**
	 * 
	 **/
	private String email;
	/**
	 * 
	 **/
	private Date birthdate;
	/**
	 * 
	 **/
	private Sex sex;
	/**
	 * 
	 **/
	private Country country;
	/**
	 * 
	 **/
	private Province province;
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
	private List<User> users = new ArrayList<User>();
	/**
	 * 
	 **/
	private List<Group> groups = new ArrayList<Group>();
	/**
	 * 
	 **/
	private List<Event> events = new ArrayList<Event>();
	/**
	 * 
	 **/
	private List<ContactRequest> receivedRequests = new ArrayList<ContactRequest>();
	/**
	 * 
	 **/
	private List<ContactRequest> sentRequests = new ArrayList<ContactRequest>();
	/**
	 * 
	 **/
	private List<Feed> feeds = new ArrayList<Feed>();
	/**
	 * 
	 **/
	private List<Account> communications = new ArrayList<Account>();
	/**
	 * 
	 **/
	private String locale;
	/**
	 * 
	 **/
	private List<Comment> comments = new ArrayList<Comment>();
//CONSTRUCTOR
	/**
	 * 
	 **/
	public User(){
		
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
	 * @param username the user name to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * @return the user name
	 */
	@Column(name="username", unique=true, length=24, nullable=false, updatable=false)
	@MappingProperty
	public String getUsername() {
		return username;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return the password
	 */
	@Column(name="password", length=24, nullable=false)
	public String getPassword() {
		return password;
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
			cascade={})
	@JoinTable(name="user_user",
			joinColumns=@JoinColumn(name="source"),
			inverseJoinColumns=@JoinColumn(name="end"),
			uniqueConstraints=@UniqueConstraint(columnNames={"source", "end"}))
	public List<User> getUsers() {
		return users;
	}
	/**
	 * @param groups the groups to set
	 */
	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}
	/**
	 * @return the groups
	 */
	@ManyToMany(
			targetEntity=Group.class,
			cascade={})
	@JoinTable(name="user_group",
			joinColumns={@JoinColumn(name="user")},
			inverseJoinColumns={@JoinColumn(name="`group`")})
	public List<Group> getGroups() {
		return groups;
	}
	/**
	 * @param events the events to set
	 */
	public void setEvents(List<Event> events) {
		this.events = events;
	}
	/**
	 * @return the events
	 */
	@ManyToMany(
			targetEntity=Event.class,
			cascade={})
	@JoinTable(name="user_event",
			joinColumns={@JoinColumn(name="user")},
			inverseJoinColumns={@JoinColumn(name="event")})
	public List<Event> getEvents() {
		return events;
	}
	/**
	 * @param receivedRequests the receivedRequests to set
	 */
	public void setIncomingContactRequests(List<ContactRequest> receivedRequests) {
		this.receivedRequests = receivedRequests;
	}
	/**
	 * @return the receivedRequests
	 */
	@OneToMany(
			cascade=CascadeType.ALL,
			targetEntity=ContactRequest.class,
			mappedBy="id.requested")
	public List<ContactRequest> getIncomingContactRequests() {
		return receivedRequests;
	}
	/**
	 * @param sentRequests the sentRequests to set
	 */
	public void setOutgoingContactRequests(List<ContactRequest> sentRequests) {
		this.sentRequests = sentRequests;
	}
	/**
	 * @return the sentRequests
	 */
	@OneToMany(
			cascade=CascadeType.ALL,
			targetEntity=ContactRequest.class,
			mappedBy="id.requester")
	public List<ContactRequest> getOutgoingContactRequests() {
		return sentRequests;
	}
	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	/**
	 * @return the firstName
	 */
	@Column(name="firstName", length=24)
	@MappingProperty
	public String getFirstName() {
		return firstName;
	}
	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	/**
	 * @return the lastName
	 */
	@Column(name="lastName", length=24)
	@MappingProperty
	public String getLastName() {
		return lastName;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the email
	 */
	@Column(name="email", length=127)
	@MappingProperty
	public String getEmail() {
		return email;
	}
	/**
	 * @param birthdate the birthdate to set
	 */
	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}
	/**
	 * @return the birthdate
	 */
	@Column(name="birthdate")
	public Date getBirthdate() {
		return birthdate;
	}
	/**
	 * @param sex the sex to set
	 */
	public void setSex(Sex sex) {
		this.sex = sex;
	}
	/**
	 * @return the sex
	 */
	@Column(name="sex")
	@Enumerated(EnumType.ORDINAL)
	@MappingProperty
	public Sex getSex() {
		return sex;
	}
	/**
	 * @param country the country to set
	 */
	public void setCountry(Country country) {
		this.country = country;
	}
	/**
	 * @return the country
	 */
	@ManyToOne(
			targetEntity=Country.class,
			cascade={CascadeType.REFRESH})
	@JoinColumn(name="country")
	@MappingProperty
	public Country getCountry() {
		return country;
	}
	/**
	 * @param province the province to set
	 */
	public void setProvince(Province province) {
		this.province = province;
	}
	/**
	 * @return the province
	 */
	@ManyToOne(
			targetEntity=Province.class,
			cascade={CascadeType.REFRESH})
	@JoinColumn(name="province", nullable=true)
	@MappingProperty
	public Province getProvince() {
		return province;
	}
	/**
	 * @param log the actions to set
	 */
	public void setLog(List<LogRecord> log) {
		this.log = log;
	}
	/**
	 * @return the actions
	 */
	@OneToMany(
			targetEntity=LogRecord.class,
			cascade=CascadeType.ALL,
			fetch=FetchType.LAZY)
	@JoinTable(
			name="user_log",
			joinColumns={@JoinColumn(name="user")},
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
		return PrivacyLevel.PUBLIC;
	}
	/**
	 * 
	 **/
	@Transient
	public User getOwner(){
		return this;
	}
	/**
	 * If the privacy level is {@link PrivacyLevel#PRIVATE}, {@link PrivacyLevel#RESTRICTED} return the set of users that have access to this object. A privacy level of {@link PrivacyLevel#PUBLIC} will ignore the resulting value of this property.
	 **/
	@Transient
	public List<User> getAllowedUsers(){
		return getUsers();
	}
	/**
	 * @param feeds the feeds to set
	 */
	public void setFeeds(List<Feed> feeds) {
		this.feeds = feeds;
	}
	/**
	 * @return the feeds
	 **/
	@OneToMany(
			cascade=CascadeType.ALL,
			targetEntity=Feed.class,
			mappedBy="user")
	public List<Feed> getFeeds() {
		return feeds;
	}
	/**
	 * @param communications the communications to set
	 */
	public void setAccounts(List<Account> communications) {
		this.communications = communications;
	}
	/**
	 * @return the communications
	 */
	@OneToMany(
			cascade=CascadeType.ALL,
			targetEntity=Account.class,
			mappedBy="user")
	public List<Account> getAccounts() {
		return communications;
	}
	/**
	 * @param locale the locale to set
	 */
	public void setLocale(String locale) {
		this.locale = locale;
	}
	/**
	 * @return the locale
	 */
	@Column(name="locale", length=8)
	public String getLocale() {
		return locale;
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
			name="user_comment",
			joinColumns={@JoinColumn(name="user")},
			inverseJoinColumns={@JoinColumn(name="comment")})
	public List<Comment> getComments() {
		return comments;
	}
}
