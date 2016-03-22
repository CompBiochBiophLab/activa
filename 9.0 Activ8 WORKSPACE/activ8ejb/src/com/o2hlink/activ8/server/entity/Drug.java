package com.o2hlink.activ8.server.entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
@Table(name="drug")
@MappingClass(type=com.o2hlink.activ8.client.entity.Drug.class)
public class Drug implements Serializable, HasLocales {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = 4722204897388148474L;
	/**
	 * 
	 **/
	private long id;
	/**
	 * 
	 **/
	private Map<String, HasLocale> locales = new HashMap<String, HasLocale>();
//CONSTRUCTOR
	/**
	 * 
	 **/
	public Drug(){
		
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
			targetEntity=DrugDetail.class,
			mappedBy="id.drug")
	@MapKey(name="id.locale")
	@MappingProperty(function=TranslateName.class, maps="name")
	public Map<String, HasLocale> getLocales() {
		return locales;
	}
}
