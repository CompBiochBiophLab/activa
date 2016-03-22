package com.o2hlink.activ8.client.entity;

import java.io.Serializable;

import com.o2hlink.activ8.common.entity.HasSummary;
import com.o2hlink.activ8.common.entity.MappingProperty;
import com.o2hlink.activ8.common.entity.Sex;

/**
 * A user definition. Notice password is never stored.
 * @author Miguel Angel Hernandez
 **/
abstract public class User implements Serializable, HasLog, HasUsers, HasGroups, HasSummary, HasComments {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = 5045191364841212233L;
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
	private String firstName;
	/**
	 * 
	 **/
	private String lastName;
	/**
	 * 
	 **/
	/*private Date birthdate;*/
	/**
	 * 
	 **/
	private String email;
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
	private String url;
	/**
	 * 
	 **/
	private String theme;
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
	@MappingProperty
	public long getId() {
		return id;
	}
	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * @return the username
	 */
	@MappingProperty
	public String getUsername() {
		return username;
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
	@MappingProperty
	public String getEmail() {
		return email;
	}
	/**
	 * @param birthdate the birthdate to set
	 */
	/*public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}*/
	/**
	 * @return the birthdate
	 */
	/*public Date getBirthdate() {
		return birthdate;
	}*/
	/**
	 * @param sex the sex to set
	 */
	public void setSex(Sex sex) {
		this.sex = sex;
	}
	/**
	 * @return the sex
	 */
	@MappingProperty
	public Sex getSex() {
		return sex;
	}
	/**
	 * 
	 **/
	public String getName(){
		return getFirstName()+" "+getLastName();
	}
	/**
	 * 
	 **/
	public String getDescription(){
		return getFirstName()+" "+getLastName();
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
	public Province getProvince() {
		return province;
	}
	/**
	 * @param url the url to set
	 */
	public void setURL(String url) {
		this.url = url;
	}
	/**
	 * @return the url
	 */
	public String getURL() {
		return url;
	}
	/**
	 * @param theme the theme to set
	 */
	public void setTheme(String theme) {
		this.theme = theme;
	}
	/**
	 * @return the theme
	 */
	public String getTheme() {
		return theme;
	}
}
