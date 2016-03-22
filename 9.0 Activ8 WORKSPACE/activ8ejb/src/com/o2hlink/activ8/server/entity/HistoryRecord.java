package com.o2hlink.activ8.server.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.o2hlink.activ8.common.entity.MappingClass;
import com.o2hlink.activ8.common.entity.MappingProperty;

/**
 * An history record conforms the patient history
 * @author Miguel Angel Hernandez
 **/
@Entity
@Table(name="history_record")
@MappingClass(type=com.o2hlink.activ8.client.entity.HistoryRecord.class)
public class HistoryRecord implements Serializable {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = -1290077331294596870L;
	/**
	 * 
	 **/
	private long id;
	/**
	 * 
	 **/
	private Patient patient;
	/**
	 * 
	 **/
	private Disease disease;
	/**
	 * 
	 **/
	private List<Drug> drugs = new ArrayList<Drug>();
	/**
	 * 
	 **/
	private List<Procedure> procedures = new ArrayList<Procedure>();
//CONSTRUCTOR
	/**
	 * 
	 **/
	public HistoryRecord(){
		
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
	@Column(name="id", unique=true, updatable=false, nullable=false)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@MappingProperty
	public long getId() {
		return id;
	}
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
			cascade={},
			fetch=FetchType.EAGER,
			optional=false,
			targetEntity=Patient.class)
	@JoinColumn(name="patient")
	public Patient getPatient() {
		return patient;
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
	@OneToOne(
			cascade={},
			targetEntity=Disease.class)
	@JoinColumn(name="disease")
	@MappingProperty
	public Disease getDisease() {
		return disease;
	}
	/**
	 * @param drugs the drugs to set
	 */
	public void setDrugs(List<Drug> drugs) {
		this.drugs = drugs;
	}
	/**
	 * @return the drugs
	 */
	@OneToMany(
			cascade={},
			targetEntity=Drug.class)
	@JoinTable(
			name="history_record_drug",
			joinColumns={@JoinColumn(name="history_record")},
			inverseJoinColumns={@JoinColumn(name="drug")})
	public List<Drug> getDrugs() {
		return drugs;
	}
	/**
	 * @param procedures the procedures to set
	 */
	public void setProcedures(List<Procedure> procedures) {
		this.procedures = procedures;
	}
	/**
	 * @return the procedures
	 */
	@OneToMany(
			cascade={},
			targetEntity=Procedure.class)
	@JoinTable(
			name="history_record_procedure",
			joinColumns={@JoinColumn(name="history_record")},
			inverseJoinColumns={@JoinColumn(name="`procedure`")})
	public List<Procedure> getProcedures() {
		return procedures;
	}
}
