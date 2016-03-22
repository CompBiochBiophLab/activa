/**
 * 
 */
package com.o2hlink.activ8.client.entity;

import com.o2hlink.activ8.common.entity.MappingClass;
import com.o2hlink.activ8.common.entity.MappingProperty;

/**
 * Spirometry of a patient
 * @author Miguel Angel Hernandez
 **/
@MappingClass(name="com.o2hlink.activ8.server.entity.Spirometry")
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
	@MappingProperty
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
	@MappingProperty
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
	@MappingProperty
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
	@MappingProperty
	public double getFEV1BasalPostBDPT() {
		return fev1basalpostbdpt;
	}
	/**
	 * Indice basal pre-training
	 * @param fev1fvcbasalpt the fev1fvcbasalpt to set
	 */
	public void setFEV1FVCBasalPT(double fev1fvcbasalpt) {
		this.fev1fvcbasalpt = fev1fvcbasalpt;
	}
	/**
	 * Indice basal pre-training
	 * @return the fev1fvcbasalpt
	 */
	@MappingProperty
	public double getFEV1FVCBasalPT() {
		return fev1fvcbasalpt;
	}
	/**
	 * Indice basal post-broncodilatador_pre-training
	 * @param fev1fvcbasalpostbdpt the fev1fvcbasalpostbdpt to set
	 */
	public void setFEV1FVCBasalPostBDPT(double fev1fvcbasalpostbdpt) {
		this.fev1fvcbasalpostbdpt = fev1fvcbasalpostbdpt;
	}
	/**
	 * Indice basal post-broncodilatador_pre-training
	 * @return the fev1fvcbasalpostbdpt
	 */
	@MappingProperty
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
	@MappingProperty
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
	@MappingProperty
	public double getMMEF7525BasalPostBDPT() {
		return mmef7525basalpostbdpt;
	}
}
