package com.o2hlink.activ8.server.entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.o2hlink.activ8.common.entity.MappingProperty;

/**
 * @author Miguel Angel Hernandez
 **/
@Entity
@Table(name="`procedure`")
public class Procedure implements Serializable, HasLocales {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = 3737490800312433052L;
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
	public Procedure(){
		
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
	@Column(name="id", unique=true, updatable=true)
	public long getId() {
		return id;
	}
	/**
	 * @param locales the locales to set
	 */
	public void setLocales(Map<String, HasLocale> locales) {
		this.locales = locales;
	}
	/**
	 * @return the locales
	 */
	@OneToMany(
			cascade={CascadeType.ALL},
			fetch=FetchType.LAZY,
			targetEntity=ProcedureDetail.class,
			mappedBy="id.procedure")
	@MapKey(name="id.locale")
	@MappingProperty(function=TranslateName.class, maps="name")
	public Map<String, HasLocale> getLocales() {
		return locales;
	}
}
