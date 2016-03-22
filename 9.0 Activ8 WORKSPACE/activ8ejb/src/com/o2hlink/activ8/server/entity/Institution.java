package com.o2hlink.activ8.server.entity;

import java.io.Serializable;
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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.o2hlink.activ8.common.entity.InstitutionCategory;
import com.o2hlink.activ8.common.entity.MappingClass;
import com.o2hlink.activ8.common.entity.MappingProperty;

/**
 * Institutions
 * @author Miguel Angel Hernandez
 **/
@Entity
@Table(name="institution")
@MappingClass(type=com.o2hlink.activ8.client.entity.Institution.class)
@NamedQueries({
	@NamedQuery(name="institution.latlng", query="FROM Institution WHERE (latitude BETWEEN :south AND :north) AND (longitude BETWEEN :west AND :east)")
})
public class Institution implements Serializable {
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
	private double latitude;
	/**
	 * 
	 **/
	private double longitude;
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
	private InstitutionCategory category;
	/**
	 * 
	 **/
	private List<Disease> diseases = new ArrayList<Disease>();
	/**
	 * 
	 **/
	private List<Contact> contacts = new ArrayList<Contact>();
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
	@Column(name="name", length=64)
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
	@Column(name="description", length=512)
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
	@Column(name="url", length=256)
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
	@Column(name="address", length=512)
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
	@Column(name="phone", length=32)
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
	@Column(name="email", length=64)
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
	@Column(name="longitude")
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
	@Column(name="latitude")
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
	@OneToOne(
			targetEntity=Country.class,
			cascade={CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
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
	@OneToOne(
			targetEntity=Province.class,
			cascade={CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinColumn(name="province")
	@MappingProperty
	public Province getProvince() {
		return province;
	}
	/**
	 * @param diseases the diseases to set
	 */
	public void setDiseases(List<Disease> diseases) {
		this.diseases = diseases;
	}
	/**
	 * @return the diseases
	 */
	@ManyToMany(
			targetEntity=Disease.class,
			mappedBy="institutions",
			cascade={CascadeType.PERSIST})
	public List<Disease> getDiseases() {
		return diseases;
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
	@Column(name="category")
	@Enumerated(EnumType.ORDINAL)
	@MappingProperty
	public InstitutionCategory getCategory() {
		return category;
	}
	/**
	 * @param contacts the contacts to set
	 */
	public void setContacts(List<Contact> contacts) {
		this.contacts = contacts;
	}
	/**
	 * @return the contacts
	 */
	@OneToMany(
			fetch=FetchType.LAZY, 
			cascade={}, 
			targetEntity=Contact.class)
	@JoinTable(name="institution_contact",
			joinColumns={@JoinColumn(name="institution")},
			inverseJoinColumns={@JoinColumn(name="contact")})
	public List<Contact> getContacts() {
		return contacts;
	}
}
