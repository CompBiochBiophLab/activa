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

import com.o2hlink.activ8.common.entity.HasName;

/**
 * Localized texts of country
 * @author Miguel Angel Hernandez
 **/
@Entity
@Table(name="country_detail")
public class CountryDetail implements Serializable, HasLocale, HasName {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = -1288655336195192975L;
	/**
	 * 
	 **/
	@Embeddable
	public static class CountryDetailId implements Serializable {
	//FIELDS
		/**
		 * 
		 */
		private static final long serialVersionUID = -3665846779163917554L;
		/**
		 * 
		 **/
		private String locale;
		/**
		 * 
		 **/
		private Country country;
	//CONSTRUCTOR
		/**
		 * 
		 **/
		public CountryDetailId(){
			
		}
		/**
		 * 
		 **/
		public CountryDetailId(String locale, Country country){
			setLocale(locale);
			setCountry(country);
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
				cascade={},
				optional=false)
		@JoinColumn(name="country", nullable=false, updatable=false)
		public Country getCountry() {
			return country;
		}
		/**
		 * 
		 **/
		@Override
		public boolean equals(Object obj){
			if (obj instanceof CountryDetailId){
				CountryDetailId instance = (CountryDetailId)obj;
				return getLocale().equals(instance.getLocale()) && getCountry().equals(instance.getCountry());
			}
			return false;
		}
		/**
		 * 
		 **/
		@Override
		public int hashCode(){
			return getLocale().hashCode() ^ getCountry().hashCode();
		}
	}
	/**
	 * 
	 **/
	private CountryDetailId id;
	/**
	 * 
	 **/
	private String name;
//CONSTRUCTOR
	/**
	 * 
	 **/
	public CountryDetail(){
		
	}
//METHODS
	/**
	 * @param id the id to set
	 */
	public void setId(CountryDetailId id) {
		this.id = id;
	}
	/**
	 * @return the id
	 */
	@EmbeddedId
	public CountryDetailId getId() {
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
	 * 
	 **/
	@Transient
	public String getLocale() {
		return getId().getLocale();
	}
}
