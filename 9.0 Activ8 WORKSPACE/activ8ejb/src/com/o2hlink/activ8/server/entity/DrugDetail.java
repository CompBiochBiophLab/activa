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
 * Localizable information of drug
 * @author Miguel Angel Hernandez
 **/
@Entity
@Table(name="drug_detail")
public class DrugDetail implements Serializable, HasName, HasLocale {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = 4722204897388148474L;
	/**
	 * 
	 **/
	@Embeddable
	public static class DrugDetailId implements Serializable {
	//FIELDS
		/**
		 * 
		 */
		private static final long serialVersionUID = -8225962607796724856L;
		/**
		 * 
		 **/
		private String locale;
		/**
		 * 
		 **/
		private Drug drug;
	//CONSTRUCTOR
		/**
		 * 
		 **/
		public DrugDetailId(){
			
		}
		/**
		 * 
		 **/
		public DrugDetailId(String locale, Drug drug){
			setLocale(locale);
			setDrug(drug);
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
		 * @param drug the drug to set
		 */
		public void setDrug(Drug drug) {
			this.drug = drug;
		}
		/**
		 * @return the drug
		 */
		@ManyToOne(
				targetEntity=Drug.class,
				cascade={},
				optional=false)
		@JoinColumn(name="drug", nullable=false, updatable=false)
		public Drug getDrug() {
			return drug;
		}
		/**
		 * 
		 **/
		@Override
		public boolean equals(Object obj){
			if (obj instanceof DrugDetailId){
				DrugDetailId instance = (DrugDetailId)obj;
				return getLocale().equals(instance.getLocale()) && getDrug().equals(instance.getDrug());
			}
			return false;
		}
		/**
		 * 
		 **/
		@Override
		public int hashCode(){
			return getLocale().hashCode() ^ getDrug().hashCode();
		}
	}
	/**
	 * 
	 **/
	private DrugDetailId id;
	/**
	 * 
	 **/
	private String name;
//CONSTRUCTOR
	/**
	 * 
	 **/
	public DrugDetail(){
		
	}
//METHODS
	/**
	 * @param id the id to set
	 */
	public void setId(DrugDetailId id) {
		this.id = id;
	}
	/**
	 * @return the id
	 */
	@EmbeddedId
	public DrugDetailId getId() {
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
