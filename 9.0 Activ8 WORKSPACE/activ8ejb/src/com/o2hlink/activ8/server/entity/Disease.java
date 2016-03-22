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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.o2hlink.activ8.common.entity.MappingClass;
import com.o2hlink.activ8.common.entity.MappingProperties;
import com.o2hlink.activ8.common.entity.MappingProperty;

/**
 * Diseases
 * @author Miguel Angel Hernandez
 **/
@Entity
@Table(name="disease")
@MappingClass(type=com.o2hlink.activ8.client.entity.Disease.class)
public class Disease implements Serializable, HasLocales {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = -8189029912239117790L;
	/**
	 * 
	 **/
	private long id;
	/**
	 * 
	 **/
	private Map<String, HasLocale> locales = new HashMap<String, HasLocale>();
	/**
	 * 
	 **/
	private List<Institution> institutions = new ArrayList<Institution>();
//CONSTRUCTOR
	/**
	 * 
	 **/
	public Disease(){
		
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
	 * @param institutions the institutions to set
	 */
	public void setInstitutions(List<Institution> institutions) {
		this.institutions = institutions;
	}
	/**
	 * @return the institutions
	 */
	@ManyToMany(
			targetEntity=Institution.class,
			cascade={CascadeType.PERSIST})
	@JoinTable(name="disease_institution",
			joinColumns={@JoinColumn(name="disease")},
			inverseJoinColumns={@JoinColumn(name="institution")})
	public List<Institution> getInstitutions() {
		return institutions;
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
			targetEntity=DiseaseDetail.class,
			mappedBy="id.disease")
	@MapKey(name="id.locale")
	@MappingProperties({
		@MappingProperty(function=TranslateName.class, maps="name"),
		@MappingProperty(function=TranslateDescription.class, maps="description")
	})
	public Map<String, HasLocale> getLocales() {
		return locales;
	}
}
