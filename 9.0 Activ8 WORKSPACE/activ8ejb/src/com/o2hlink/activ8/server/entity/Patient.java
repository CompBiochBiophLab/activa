/**
 * 
 */
package com.o2hlink.activ8.server.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.o2hlink.activ8.common.entity.MappingClass;
import com.o2hlink.activ8.common.entity.MappingProperty;

/**
 * A patient user
 * @author Miguel Angel Hernandez
 **/
@Entity
@Table(name="patient")
@PrimaryKeyJoinColumn(name="id", referencedColumnName="id")
@MappingClass(type=com.o2hlink.activ8.client.entity.Patient.class)
@NamedQueries({
	@NamedQuery(name="patient.bluetooth", query="from Patient where bluetoothAddress=:bluetoothAddress")
})
public class Patient extends User {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = 2606722699650796117L;
	/**
	 * 
	 **/
	private String bluetoothAddress;
	/**
	 * 
	 **/
	private List<HistoryRecord> history = new ArrayList<HistoryRecord>();
	/**
	 * 
	 **/
	private List<AssignedMeasurement> measurements = new ArrayList<AssignedMeasurement>();
//CONSTRUCTOR
	/**
	 * 
	 **/
	public Patient(){
		
	}
//METHODS
	/**
	 * @param measurements the measurements to set
	 */
	public void setMeasurements(List<AssignedMeasurement> measurements) {
		this.measurements = measurements;
	}
	/**
	 * @return the measurements
	 */
	@OneToMany(
			cascade=CascadeType.ALL,
			targetEntity=AssignedMeasurement.class,
			mappedBy="id.patient")
	public List<AssignedMeasurement> getMeasurements() {
		return measurements;
	}
	/**
	 * @param history the history to set
	 */
	public void setHistory(List<HistoryRecord> history) {
		this.history = history;
	}
	/**
	 * @return the history
	 */
	@OneToMany(
			cascade={CascadeType.ALL},
			fetch=FetchType.LAZY,
			mappedBy="patient")
	public List<HistoryRecord> getHistory() {
		return history;
	}
	/**
	 * @param bluetoothAddress the bluetoothAddress to set
	 */
	public void setBluetoothAddress(String bluetoothAddress) {
		this.bluetoothAddress = bluetoothAddress;
	}
	/**
	 * @return the bluetoothAddress
	 */
	@Column(name="bluetoothAddress", length=20, unique=true)
	@MappingProperty
	public String getBluetoothAddress() {
		return bluetoothAddress;
	}
}
