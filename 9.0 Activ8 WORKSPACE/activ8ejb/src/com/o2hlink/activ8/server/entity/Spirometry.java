/**
 * 
 */
package com.o2hlink.activ8.server.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.PrimaryKeyJoinColumns;
import javax.persistence.Table;


/**
 * Spirometry of a patient
 * @author Miguel Angel Hernandez
 **/
@Entity
@Table(name="spirometry")
@PrimaryKeyJoinColumns(
		{@PrimaryKeyJoinColumn(name="patient", referencedColumnName="patient"),
		 @PrimaryKeyJoinColumn(name="date", referencedColumnName="date"),
		 @PrimaryKeyJoinColumn(name="measurement", referencedColumnName="measurement")})
public class Spirometry extends Sample {
//FIELDS
	/**
	 * 
	 **/
	private static final long serialVersionUID = 104472397962306059L;
	/**
	 * 
	 **/
	private double fvcbasalpt;
	/**
	 * 
	 **/
	private double fvcbasalpostbdpt;
	/**
	 * 
	 **/
	private double fev1basalpt;
	/**
	 * 
	 **/
	private double fev1basalpostbdpt;
	/**
	 * 
	 **/
	private double fev1fvcbasalpt;
	/**
	 * 
	 **/
	private double fev1fvcbasalpostbdpt;
	/**
	 * 
	 **/
	private double mmef7525basalpt;
	/**
	 * 
	 **/
	private double mmef7525basalpostbdpt;
//CONSTRUCTOR
	/**
	 * 
	 **/
	public Spirometry(){
		
	}
//METHODS
	/**
	 * Capacidad vital forzada basal pre-training
	 * @param fvcbasalpt the fvcbasalpt to set
	 */
	public void setFVCBasalPT(double fvcbasalpt) {
		this.fvcbasalpt = fvcbasalpt;
	}
	/**
	 * Capacidad vital forzada basal pre-training
	 * @return the fvcbasalpt
	 */
	@Column(name="fvc_basal_pt")
	public double getFVCBasalPT() {
		return fvcbasalpt;
	}
	/**
	 * Capacidad vital forzada basal post-broncodilatador pre-training
	 * @param fvcbasalpostbdpt the fvcbasalpostbdpt to set
	 */
	public void setFVCBasalPostBDPT(double fvcbasalpostbdpt) {
		this.fvcbasalpostbdpt = fvcbasalpostbdpt;
	}
	/**
	 * Capacidad vital forzada basal post-broncodilatador pre-training
	 * @return the fvcbasalpostbdpt
	 */
	@Column(name="fvc_basal_post_bd_pt")
	public double getFVCBasalPostBDPT() {
		return fvcbasalpostbdpt;
	}
	/**
	 * Volumen espiratorio basal pre-training 
	 * @param fev1basalpt the fev1basalpt to set
	 */
	public void setFEV1BasalPT(double fev1basalpt) {
		this.fev1basalpt = fev1basalpt;
	}
	/**
	 * Volumen espiratorio basal pre-training 
	 * @return the fev1basalpt
	 */
	@Column(name="fev1_basal_pt")
	public double getFEV1BasalPT() {
		return fev1basalpt;
	}
	/**
	 * Volumen espiratorio basal post-broncodilatador pre-training
	 * @param fev1basalpostbdpt the fev1basalpostbdpt to set
	 */
	public void setFEV1BasalPostBDPT(double fev1basalpostbdpt) {
		this.fev1basalpostbdpt = fev1basalpostbdpt;
	}
	/**
	 * Volumen espiratorio basal post-broncodilatador pre-training
	 * @return the fev1basalpostbdpt
	 */
	@Column(name="fev1_basal_post_bd_pt")
	public double getFEV1BasalPostBDPT() {
		return fev1basalpostbdpt;
	}
	/**
	 * Índice basal pre-training
	 * @param fev1fvcbasalpt the fev1fvcbasalpt to set
	 */
	public void setFEV1FVCBasalPT(double fev1fvcbasalpt) {
		this.fev1fvcbasalpt = fev1fvcbasalpt;
	}
	/**
	 * Índice basal pre-training
	 * @return the fev1fvcbasalpt
	 */
	@Column(name="fev1_fvc_basal_pt")
	public double getFEV1FVCBasalPT() {
		return fev1fvcbasalpt;
	}
	/**
	 * Índice basal post-broncodilatador_pre-training
	 * @param fev1fvcbasalpostbdpt the fev1fvcbasalpostbdpt to set
	 */
	public void setFEV1FVCBasalPostBDPT(double fev1fvcbasalpostbdpt) {
		this.fev1fvcbasalpostbdpt = fev1fvcbasalpostbdpt;
	}
	/**
	 * Índice basal post-broncodilatador_pre-training
	 * @return the fev1fvcbasalpostbdpt
	 */
	@Column(name="fev1_fvc_basal_post_bd_pt")
	public double getFEV1FVCBasalPostBDPT() {
		return fev1fvcbasalpostbdpt;
	}
	/**
	 * Flujo espiratorio medio basal pre-training
	 * @param mmef7525basalpt the mmef7525basalpt to set
	 */
	public void setMMEF7525BasalPT(double mmef7525basalpt) {
		this.mmef7525basalpt = mmef7525basalpt;
	}
	/**
	 * Flujo espiratorio medio basal pre-training
	 * @return the mmef7525basalpt
	 */
	@Column(name="mmef_75_25_basal_pt")
	public double getMMEF7525BasalPT() {
		return mmef7525basalpt;
	}
	/**
	 * Flujo espiratorio medio basal post-broncodilatador pre-training
	 * @param mmef7525basalpostbdpt the mmef7525basalpostbdpt to set
	 */
	public void setMMEF7525BasalPostBDPT(double mmef7525basalpostbdpt) {
		this.mmef7525basalpostbdpt = mmef7525basalpostbdpt;
	}
	/**
	 * Flujo espiratorio medio basal post-broncodilatador pre-training
	 * @return the mmef7525basalpostbdpt
	 */
	@Column(name="mmef_75_25_basal_post_bd_pt")
	public double getMMEF7525BasalPostBDPT() {
		return mmef7525basalpostbdpt;
	}
}
