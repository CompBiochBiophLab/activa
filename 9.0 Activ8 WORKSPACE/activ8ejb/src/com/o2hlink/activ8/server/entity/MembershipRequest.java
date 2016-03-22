package com.o2hlink.activ8.server.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
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
@Table(name="user_group_request")
public class MembershipRequest implements Serializable{
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = -369056788650238499L;
	/**
	 * @author Miguel Angel Hernandez
	 **/
	@Embeddable
	static public class Id implements Serializable{
	//FIELDS
		/**
		 * 
		 */
		private static final long serialVersionUID = -9092590279227044097L;
		/**
		 * 
		 **/
		private User user;
		/**
		 * 
		 **/
		private Group group;
	//CONSTRUCTOR
		/**
		 * 
		 **/
		public Id(){
			
		}
		/**
		 * @param	user The user
		 * @param	group The group
		 **/
		public Id(User user, Group group){
			setUser(user);
			setGroup(group);
		}
	//METHODS
		/**
		 * @param user the user to set
		 */
		public void setUser(User user) {
			this.user = user;
		}
		/**
		 * @return the user
		 */
		@ManyToOne(
				targetEntity=User.class,
				cascade={CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
		@JoinColumn(name="user", updatable=false, nullable=false)
		public User getUser() {
			return user;
		}
		/**
		 * @param group the group to set
		 */
		public void setGroup(Group group) {
			this.group = group;
		}
		/**
		 * @return the group
		 */
		@ManyToOne(
				targetEntity=Group.class,
				cascade={CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
		@JoinColumn(name="`group`", updatable=false, nullable=false)
		public Group getGroup() {
			return group;
		}
		/**
		 * 
		 **/
		@Override
		public boolean equals(Object obj){
			if (obj instanceof Id){
				Id id = (Id)obj;
				return (user.equals(id.user) && group.equals(id.group));
			}
			return false;
		}
		/**
		 * 
		 **/
		@Override
		public int hashCode(){
			return user.hashCode() ^ group.hashCode();
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
	public MembershipRequest(){
		
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
