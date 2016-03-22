package com.o2hlink.activ8.server.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.o2hlink.activ8.common.entity.HasDescription;
import com.o2hlink.activ8.common.entity.HasName;

/**
 * @author Miguel Angel Hernandez
 **/
@Entity
@Table(name="disease_detail")
public class DiseaseDetail implements Serializable, HasName, HasDescription, HasLocale {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = -5405911246602533644L;
	/**
	 * 
	 **/
	@Embeddable
	public static class Id implements Serializable {
	//FIELDS
		/**
		 * 
		 */
		private static final long serialVersionUID = -5825009753405041055L;
		/**
		 * 
		 **/
		private String locale;
		/**
		 * 
		 **/
		private Disease disease;
	//CONSTRUCTOR
		/**
		 * 
		 **/
		public Id(){
			
		}
		/**
		 * 
		 **/
		public Id(String locale, Disease disease){
			setLocale(locale);
			setDisease(disease);
		}
	//METHODS
		/**
		 * @param locale the locale to set
		 */
		public void setLocale(String locale) {
			this.locale = locale;
		}
		/**
		 * @return the locale
		 */
		@Column(name="locale", length=8, nullable=false, updatable=false)
		public String getLocale() {
			return locale;
		}
		/**
		 * @param disease the disease to set
		 */
		public void setDisease(Disease disease) {
			this.disease = disease;
		}
		/**
		 * @return the disease
		 */
		@ManyToOne(
				cascade={},
				optional=false,
				targetEntity=Disease.class)
		@JoinColumn(name="disease", nullable=false, updatable=false)
		public Disease getDisease() {
			return disease;
		}
		/**
		 * 
		 **/
		@Override
		public boolean equals(Object obj){
			if (obj instanceof Id){
				Id instance = (Id)obj;
				return getLocale().equals(instance.getLocale()) && getDisease().equals(instance.getDisease());
			}
			return false;
		}
		/**
		 * 
		 **/
		@Override
		public int hashCode(){
			return getLocale().hashCode() ^ getDisease().hashCode();
		}
	}
	/**
	 * 
	 **/
	private Id id;
	/**
	 * 
	 **/
	private String name;
	/**
	 * 
	 **/
	private String description;
//CONSTRUCTOR
	/**
	 * 
	 **/
	public DiseaseDetail(){
		
	}
//METHODS
	/**
	 * @param id the id to set
	 */
	public void setId(Id id) {
		this.id = id;
	}
	/**
	 * @return the id
	 */
	@EmbeddedId
	public Id getId() {
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
	@Column(name="name", length=127)
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
	@Column(name="description", length=511)
	public String getDescription() {
		return description;
	}
	/**
	 * 
	 **/
	@Transient
	public String getLocale() {
		return getId().getLocale();
	}
}
