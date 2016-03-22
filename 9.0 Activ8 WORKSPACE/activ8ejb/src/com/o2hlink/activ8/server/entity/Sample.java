package com.o2hlink.activ8.server.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.o2hlink.activ8.common.entity.MappingProperty;


/**
 * Sample base class.
 * @author Miguel Angel Hernandez
 **/
@Entity
@Table(name="sample")
@Inheritance(strategy=InheritanceType.JOINED)
abstract public class Sample implements Serializable {
//FIELDS
	/**
	 * 
	 **/
	private static final long serialVersionUID = -4284543033506485273L;
	/**
	 * 
	 **/
	@Embeddable
	public static class Id implements Serializable {
	//FIELDS
		/**
		 * 
		 */
		private static final long serialVersionUID = 8417049552902548258L;
		/**
		 * 
		 **/
		private Patient patient;
		/**
		 * 
		 **/
		private Measurement measurement;
		/**
		 * 
		 **/
		private Date date;
	//CONSTRUCTOR
		/**
		 * 
		 **/
		public Id(){
			
		}
		/**
		 * 
		 **/
		public Id(Measurement measurement, Date date, Patient patient){
			setMeasurement(measurement);
			setDate(date);
			setPatient(patient);
		}
	//METHODS
		/**
		 * @param patient the patient to set
		 */
		public void setPatient(Patient patient) {
			this.patient = patient;
		}
		/**
		 * @return the patient
		 */
		@ManyToOne(
				targetEntity=Patient.class,
				cascade={CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
		@JoinColumn(name="patient", nullable=false, updatable=false)
		public Patient getPatient() {
			return patient;
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
				cascade={CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
		@JoinColumn(name="measurement", nullable=false, updatable=false)
		public Measurement getMeasurement() {
			return measurement;
		}
		/**
		 * @param date the date to set
		 */
		public void setDate(Date date) {
			this.date = date;
		}
		/**
		 * @return the date
		 */
		@Column(name="date", nullable=false, updatable=false)
		public Date getDate() {
			return date;
		}
		/**
		 * 
		 **/
		@Override
		public boolean equals(Object obj){
			if (obj instanceof Id){
				Id id = (Id)obj;
				return (measurement.equals(id.measurement) && patient.equals(patient) && date.equals(date));
			}
			return false;
		}
		/**
		 * 
		 **/
		@Override
		public int hashCode(){
			return measurement.hashCode() ^ patient.hashCode() ^ date.hashCode();
		}
	}
	/**
	 * 
	 **/
	private Id id;
//CONSTRUCTOR
	/**
	 * 
	 **/
	public Sample(){
		
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
	@MappingProperty(maps="date", name="date")
	public Id getId() {
		return id;
	}
}
