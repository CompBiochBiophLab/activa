package com.o2hlink.activ8.server.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author Miguel Angel Hernandez
 **/
@Entity
@Table(name="user_user_request")
public class ContactRequest implements Serializable {
//FIELDS
	/**	 
	 *
	 **/
	private static final long serialVersionUID = -4119760291809159337L;	
	/**
	 * @author Miguel Angel Hernandez
	 **/
	@Embeddable
	public static class Id implements Serializable{
	//FIELDS
		/**
		 * 
		 */
		private static final long serialVersionUID = -6339127916005875000L;
		/**
		 * 
		 **/
		private User requester;
		/**
		 * 
		 **/
		private User requested;
	//CONSTRUCTOR
		/**
		 * 
		 **/
		public Id(){
			
		} 
		/**
		 * @param	requester The requester user
		 * @param	requested The requested user
		 **/
		public Id(User requester, User requested){
			setRequester(requester);
			setRequested(requested);
		}
	//METHODS
		/**
		 * @param requester the requester to set
		 */
		public void setRequester(User requester) {
			this.requester = requester;
		}
		/**
		 * @return the requester
		 */
		@ManyToOne(
				targetEntity=User.class,
				cascade={})
		@JoinColumn(name="requester", nullable=false, updatable=false)
		public User getRequester() {
			return requester;
		}
		/**
		 * @param requested the requested to set
		 */
		public void setRequested(User requested) {
			this.requested = requested;
		}
		/**
		 * @return the requested
		 */
		@ManyToOne(
				targetEntity=User.class,
				cascade={})
		@JoinColumn(name="requested", nullable=false, updatable=false)
		public User getRequested() {
			return requested;
		}
		/**
		 * 
		 **/
		@Override
		public boolean equals(Object obj){
			if (obj instanceof Id){
				Id id = (Id)obj;
				return (getRequester().equals(id.requester) && getRequested().equals(id.requested));
			}
			return false;
		}
		/**
		 * 
		 **/
		@Override
		public int hashCode(){
			return getRequester().hashCode() ^ getRequested().hashCode();
		}
	}
	/**
	 * 
	 **/
	private Id id;
	/**
	 * 
	 **/
	private String message;
//CONSTRUCTOR
	/**
	 * 
	 **/
	public ContactRequest(){
		
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
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	/**
	 * @return the message
	 */
	@Column(name="message", length=256)
	public String getMessage() {
		return message;
	}
}
