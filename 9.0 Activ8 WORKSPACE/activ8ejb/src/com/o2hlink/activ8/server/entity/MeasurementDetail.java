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
 * Internationalization of measurements
 * @author Miguel Angel Hernandez
 **/
@Entity
@Table(name="measurement_detail")
public class MeasurementDetail implements Serializable, HasName, HasDescription, HasLocale {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = -9130826475691230979L;
	/**
	 * 
	 **/
	@Embeddable
	public static class MeasurementDetailId implements Serializable {
	//FIELDS
		/**
		 * 
		 */
		private static final long serialVersionUID = 7011986983218470748L;
		/**
		 * 
		 **/
		private String locale;
		/**
		 * 
		 **/
		private Measurement measurement;
	//CONSTRUCTOR
		/**
		 * 
		 **/
		public MeasurementDetailId(){
			
		}
		/**
		 * 
		 **/
		public MeasurementDetailId(String locale, Measurement measurement){
			setLocale(locale);
			setMeasurement(measurement);
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
		 * @param measurement the measurement to set
		 */
		public void setMeasurement(Measurement measurement) {
			this.measurement = measurement;
		}
		/**
		 * @return the measurement
		 */
		@ManyToOne(
				targetEntity=Measurement.class,
				cascade={},
				optional=false)
		@JoinColumn(name="measurement", nullable=false, updatable=false)
		public Measurement getMeasurement() {
			return measurement;
		}
		/**
		 * 
		 **/
		@Override
		public boolean equals(Object obj){
			if (obj instanceof MeasurementDetailId){
				MeasurementDetailId instance = (MeasurementDetailId)obj;
				return getLocale().equals(instance.getLocale()) && getMeasurement().equals(instance.getMeasurement());
			}
			return false;
		}
		/**
		 * 
		 **/
		@Override
		public int hashCode(){
			return getLocale().hashCode() ^ getMeasurement().hashCode();
		}
	}
	/**
	 * 
	 **/
	private MeasurementDetailId id;
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
	public MeasurementDetail(){
		
	}
//METHODS
	/**
	 * @param id the id to set
	 */
	public void setId(MeasurementDetailId id) {
		this.id = id;
	}
	/**
	 * @return the id
	 */
	@EmbeddedId
	public MeasurementDetailId getId() {
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
