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
import com.o2hlink.activ8.server.entity.MeasurementDetail.MeasurementDetailId;

/**
 * @author Miguel Angel Hernandez
 **/
@Entity
@Table(name="procedure_detail")
public class ProcedureDetail implements Serializable, HasName, HasLocale {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = 3737490800312433052L;
	/**
	 * 
	 **/
	@Embeddable
	public static class Id implements Serializable {
	//FIELDS
		/**
		 * 
		 */
		private static final long serialVersionUID = -8846121049377883000L;
		/**
		 * 
		 **/
		private String locale;
		/**
		 * 
		 **/
		private Procedure procedure;
	//CONSTRUCTOR
		/**
		 * 
		 **/
		public Id(){
			
		}
		/**
		 * 
		 **/
		public Id(String locale, Procedure procedure){
			setLocale(locale);
			setProcedure(procedure);
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
		 * @param procedure the procedure to set
		 */
		public void setProcedure(Procedure procedure) {
			this.procedure = procedure;
		}
		/**
		 * @return the procedure
		 */
		@ManyToOne(
				cascade={},
				optional=false,
				targetEntity=Procedure.class)
		@JoinColumn(name="`procedure`", nullable=false, updatable=false)
		public Procedure getProcedure() {
			return procedure;
		}
		/**
		 * 
		 **/
		@Override
		public boolean equals(Object obj){
			if (obj instanceof MeasurementDetailId){
				MeasurementDetailId instance = (MeasurementDetailId)obj;
				return getLocale().equals(instance.getLocale()) && getProcedure().equals(instance.getMeasurement());
			}
			return false;
		}
		/**
		 * 
		 **/
		@Override
		public int hashCode(){
			return getLocale().hashCode() ^ getProcedure().hashCode();
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
//CONSTRUCTOR
	/**
	 * 
	 **/
	public ProcedureDetail(){
		
	}
//METHODS
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
	 * 
	 **/
	@Transient
	public String getLocale() {
		return getId().getLocale();
	}
}
