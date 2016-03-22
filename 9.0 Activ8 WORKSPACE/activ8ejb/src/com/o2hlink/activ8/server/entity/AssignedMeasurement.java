package com.o2hlink.activ8.server.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.o2hlink.activ8.common.entity.MappingClass;
import com.o2hlink.activ8.common.entity.MappingProperty;

/**
 * @author Miguel Angel Hernandez
 **/
@Entity
@Table(name="assigned_measurement")
@MappingClass(type=com.o2hlink.activ8.client.entity.AssignedMeasurement.class)
public class AssignedMeasurement implements Serializable {
//FIELDS
	/**
	 * 
	 **/
	private static final long serialVersionUID = -1230371702082708156L;
	/**
	 * @author Miguel Angel Hernandez
	 **/
	@Embeddable
	public static class Id implements Serializable {
	//FIELDS
		/**
		 * 
		 **/
		private static final long serialVersionUID = -1230371702082708156L;
		/**
		 * 
		 **/
		private Patient patient;
		/**
		 * 
		 **/
		private Measurement measurement;
	//CONSTRUCTOR
		/**
		 * 
		 **/
		public Id(){
			
		}
		/**
		 * @param	patient The patient
		 * @param	measurement The measurement
		 **/
		public Id(Patient patient, Measurement measurement){
			setPatient(patient);
			setMeasurement(measurement);
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
		 * 
		 **/
		@Override
		public boolean equals(Object obj){
			if (obj instanceof Id){
				Id id = (Id)obj;
				return id.getPatient().equals(getPatient()) && id.getMeasurement().equals(getMeasurement());
			}
			return false;
		}
		/**
		 * 
		 **/
		@Override
		public int hashCode(){
			return getPatient().hashCode() ^ getMeasurement().hashCode();
		}
	}	
	/**
	 * 
	 **/
	private Id id;
	/**
	 * 
	 **/
	private List<Event> events = new ArrayList<Event>();
	/**
	 * 
	 **/
	private List<Sample> samples = new ArrayList<Sample>();
//CONSTRUCTOR
	/**
	 * 
	 **/
	public AssignedMeasurement(){
		
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
	 **/
	@EmbeddedId
	@MappingProperty(maps="measurement", name="measurement")
	public Id getId() {
		return id;
	}
	/**
	 * @param event the event to set
	 */
	public void setEvents(List<Event> events) {
		this.events = events;
	}
	/**
	 * @return the event
	 */
	@OneToMany(
			targetEntity=Event.class,
			cascade=CascadeType.ALL,
			fetch=FetchType.LAZY)
	@JoinTable(
			name="assigned_measurement_event",
			joinColumns={@JoinColumn(name="patient"), 
						 @JoinColumn(name="measurement")},
			inverseJoinColumns={@JoinColumn(name="event")})
	@MappingProperty
	public List<Event> getEvents() {
		return events;
	}
	/**
	 * @param samples the samples to set
	 */
	public void setSamples(List<Sample> samples) {
		this.samples = samples;
	}
	/**
	 * @return the samples
	 */
	@OneToMany(
			targetEntity=Sample.class,
			cascade={CascadeType.ALL},
			fetch=FetchType.LAZY)
	@JoinColumns({@JoinColumn(name="patient"),
				  @JoinColumn(name="measurement")})
	public List<Sample> getSamples() {
		return samples;
	}
}
