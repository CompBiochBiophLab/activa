package com.o2hlink.activ8.server.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.o2hlink.activ8.common.entity.MappingClass;
import com.o2hlink.activ8.common.entity.MappingProperty;

/**
 * @author Miguel Angel Hernandez
 **/
@Entity
@Table(name="country")
@MappingClass(type=com.o2hlink.activ8.client.entity.Country.class)
final public class Country implements Serializable, HasLocales {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = 2111310253464064580L;
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
	private List<Province> provinces = new ArrayList<Province>();
//CONSTRUCTOR
	/**
	 * 
	 **/
	public Country() {
		
	}
//METHODS
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * Get the id of the country, defined by the ISO 3166-1 alpha-3.
	 * @return the id
	 */
	@Id
	@Column(name="id", length=3, updatable=false, nullable=false, unique=true)
	@MappingProperty
	public String getId() {
		return id;
	}
	/**
	 * @param details the details to set
	 */
	public void setLocales(Map<String, HasLocale> locales) {
		this.locales = locales;
	}
	/**
	 * @return the details
	 */
	@OneToMany(
			cascade={CascadeType.ALL},
			fetch=FetchType.LAZY,
			targetEntity=CountryDetail.class,
			mappedBy="id.country")
	@MapKey(name="id.locale")
	@MappingProperty(function=TranslateName.class, maps="name")
	public Map<String, HasLocale> getLocales() {
		return locales;
	}
	/**
	 * @param provinces the provinces to set
	 */
	public void setProvinces(List<Province> provinces) {
		this.provinces = provinces;
	}
	/**
	 * @return the provinces
	 */
	@OneToMany(
			cascade=CascadeType.ALL,
			targetEntity=Province.class,
			mappedBy="country")
	public List<Province> getProvinces() {
		return provinces;
	}
}
