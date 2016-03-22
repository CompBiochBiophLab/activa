package com.o2hlink.activ8.server.entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.o2hlink.activ8.common.entity.MappingClass;
import com.o2hlink.activ8.common.entity.MappingProperty;

/**
 * @author Miguel Angel Hernandez
 **/
@Entity
@Table(name="province")
@MappingClass(type=com.o2hlink.activ8.client.entity.Province.class)
final public class Province implements Serializable, HasLocales {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = 1841331554292193766L;
	/**
	 * 
	 **/
	private String id;
	/**
	 * 
	 **/
	private Map<String, HasLocale> locales = new HashMap<String, HasLocale>();
	/**
	 * 
	 **/
	private Country country;
//CONSTRUCTOR
	/**
	 * 
	 **/
	public Province() {
		
	}
//METHODS
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * Get the id of the province, defined by the ISO 3166-2.
	 * @return the id
	 */
	@Id
	@Column(name="id", length=3, updatable=false, unique=true)
	@MappingProperty
	public String getId() {
		return id;
	}
	/**
	 * @param details the details to set
	 */
	public void setLocales(Map<String, HasLocale> details) {
		this.locales = details;
	}
	/**
	 * @return the details
	 */
	@OneToMany(
			cascade={CascadeType.ALL},
			fetch=FetchType.LAZY,
			targetEntity=ProvinceDetail.class,
			mappedBy="id.province")
	@MapKey(name="id.locale")
	@MappingProperty(function=TranslateName.class, maps="name")
	public Map<String, HasLocale> getLocales() {
		return locales;
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
	@JoinColumn(name="country", nullable=false, updatable=false)
	public Country getCountry() {
		return country;
	}
}
