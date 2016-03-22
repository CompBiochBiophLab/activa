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
 * @author Miguel Angel Hernandez
 **/
@Entity
@Table(name="province_detail")
final public class ProvinceDetail implements Serializable, HasName, HasLocale {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = 1841331554292193766L;
	/**
	 * 
	 **/
	@Embeddable
	public static class Id implements Serializable {
	//FIELDS
		/**
		 * 
		 */
		private static final long serialVersionUID = -3972631563284306344L;
		/**
		 * 
		 **/
		private Province province;
		/**
		 * 
		 **/
		private String locale;
	//CONSTRUCTOR
		/**
		 * 
		 **/
		public Id(){
			
		}
	//METHODS
		/**
		 * @param province the province to set
		 */
		public void setProvince(Province province) {
			this.province = province;
		}
		/**
		 * @return the province
		 */
		@ManyToOne(
				targetEntity=Province.class,
				cascade={},
				optional=false)
		@JoinColumn(name="province")
		public Province getProvince() {
			return province;
		}
		/**
		 * @param locale the locale to set
		 */
		public void setLocale(String locale) {
			this.locale = locale;
		}
		/**
		 * @return the locale
		 */
		@Column(name="locale", length=8)
		public String getLocale() {
			return locale;
		}
		/**
		 * 
		 **/
		@Override
		public boolean equals(Object obj){
			if (obj instanceof Id){
				Id instance = (Id)obj;
				return getLocale().equals(instance.getLocale()) && getProvince().equals(instance.getProvince());
			}
			return false;
		}
		/**
		 * 
		 **/
		@Override
		public int hashCode(){
			return getLocale().hashCode() ^ getProvince().hashCode();
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
	public ProvinceDetail() {
		
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
	public Id getId() {
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
	@Column(name="name", length=64)
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
