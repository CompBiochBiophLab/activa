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
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.o2hlink.activ8.common.entity.MappingClass;
import com.o2hlink.activ8.common.entity.MappingProperties;
import com.o2hlink.activ8.common.entity.MappingProperty;


/**
 * A measurement to be done by a patient
 * @author Miguel Angel Hernandez
 **/
@Entity
@Table(name="measurement")
@Inheritance(strategy=InheritanceType.JOINED)
@MappingClass(type=com.o2hlink.activ8.client.entity.Measurement.class)
public class Measurement implements Serializable, HasLocales {
//FIELDS
	/**
	 * 
	 **/
	private static final long serialVersionUID = 2425416092572488127L;
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
	public Measurement(){
		
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
	public void setLocales(Map<String, HasLocale> details) {
		this.locales = details;
	}
	/**
	 * @return the details
	 */
	@OneToMany(
			cascade={CascadeType.ALL},
			fetch=FetchType.LAZY,
			targetEntity=MeasurementDetail.class,
			mappedBy="id.measurement")
	@MapKey(name="id.locale")
	@MappingProperties({
		@MappingProperty(function=TranslateName.class, maps="name"),
		@MappingProperty(function=TranslateDescription.class, maps="description")
	})
	public Map<String, HasLocale> getLocales() {
		return locales;
	}
	/**
	 * TODO: CHANGE!!!
	 **/
	@Transient
	public int getDevice() {
		MeasurementDetail detail = null;
		if (getLocales().containsKey(""))
			detail = (MeasurementDetail) getLocales().get("");
		else if (getLocales().containsKey("es"))
			detail = (MeasurementDetail) getLocales().get("es");
		if (detail != null){
			if (detail.getName().indexOf("uls")>=0)
				return 5;
			if (detail.getName().indexOf("inut")>=0)
				return 6;
			if (detail.getName().indexOf("pirometr")>=0)
				return 2;
		}
		return 0;
	}
}
