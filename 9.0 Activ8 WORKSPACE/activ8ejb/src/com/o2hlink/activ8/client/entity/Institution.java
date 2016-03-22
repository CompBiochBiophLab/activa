package com.o2hlink.activ8.client.entity;

import java.io.Serializable;

import com.o2hlink.activ8.common.entity.HasSummary;
import com.o2hlink.activ8.common.entity.InstitutionCategory;
import com.o2hlink.activ8.common.entity.MappingClass;
import com.o2hlink.activ8.common.entity.MappingProperty;

/**
 * An institution.
 * @author Miguel Angel Hernandez
 **/
@MappingClass(name="com.o2hlink.activ8.server.entity.Institution")
public class Institution implements Serializable, HasSummary, HasCards {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = -8966527108147460091L;
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
	private String url;
	/**
	 * 
	 **/
	private String address;
	/**
	 * 
	 **/
	private String phone;
	/**
	 * 
	 **/
	private String email;
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
	private double latitude;
	/**
	 * 
	 **/
	private double longitude;
	/**
	 * 
	 **/
	private InstitutionCategory category;
//CONSTRUCTOR
	/**
	 * 
	 **/
	public Institution(){
		
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
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the name
	 */
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
	@MappingProperty
	public String getDescription() {
		return description;
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
	@MappingProperty
	public String getURL() {
		return url;
	}
	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * @return the address
	 */
	@MappingProperty
	public String getAddress() {
		return address;
	}
	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	/**
	 * @return the phone
	 */
	@MappingProperty
	public String getPhone() {
		return phone;
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
	 * @param longitude the longitude to set
	 */
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	/**
	 * @return the longitude
	 */
	@MappingProperty
	public double getLongitude() {
		return longitude;
	}
	/**
	 * @param latitude the latitude to set
	 */
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	/**
	 * @return the latitude
	 */
	@MappingProperty
	public double getLatitude() {
		return latitude;
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
	@MappingProperty
	public Province getProvince() {
		return province;
	}
	/**
	 * @param category the category to set
	 */
	public void setCategory(InstitutionCategory category) {
		this.category = category;
	}
	/**
	 * @return the category
	 */
	@MappingProperty
	public InstitutionCategory getCategory() {
		return category;
	}
}
