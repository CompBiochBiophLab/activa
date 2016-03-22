package com.o2hlink.activ8.server.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.o2hlink.activ8.common.entity.AccountType;
import com.o2hlink.activ8.common.entity.MappingClass;
import com.o2hlink.activ8.common.entity.MappingProperty;

/**
 * @author Miguel Angel Hernandez
 **/
@Entity
@Table(name="communication")
@MappingClass(type=com.o2hlink.activ8.client.entity.Account.class)
public class Account implements Serializable {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = 5970345091920365232L;
	/**
	 * 
	 **/
	private long id;
	/**
	 * 
	 **/
	private AccountType type;
	/**
	 * 
	 **/
	private String username;
	/**
	 * 
	 **/
	private String password;
	/**
	 * 
	 **/
	private User user;
	/**
	 * 
	 **/
	private List<AccountBuddy> skypeContacts = new ArrayList<AccountBuddy>();
//CONSTRUCTOR
	/**
	 * 
	 **/
	public Account(){
		
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
	@Column(name="id", unique=true, nullable=false, updatable=false) 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@MappingProperty
	public long getId() {
		return id;
	}
	/**
	 * @param account the account to set
	 */
	public void setUsername(String account) {
		this.username = account;
	}
	/**
	 * @return the account
	 */
	@Column(name="account", nullable=false, updatable=false)
	@MappingProperty
	public String getUsername() {
		return username;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return the password
	 */
	@Column(name="password", nullable=false)
	public String getPassword() {
		return password;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(AccountType type) {
		this.type = type;
	}
	/**
	 * @return the type
	 */
	@Column(name="type")
	@Enumerated(EnumType.ORDINAL)
	@MappingProperty
	public AccountType getType() {
		return type;
	}
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
			cascade={CascadeType.REFRESH})
	@JoinColumn(name="user")	
	public User getUser() {
		return user;
	}
	/**
	 * @param skypeContacts the skypeContacts to set
	 */
	public void setSkypeContacts(List<AccountBuddy> skypeContacts) {
		this.skypeContacts = skypeContacts;
	}
	/**
	 * @return the skypeContacts
	 */
	@OneToMany(
			targetEntity=AccountBuddy.class,
			cascade={},
			fetch=FetchType.LAZY)
	@JoinTable(
			name="account_skype_contact",
			joinColumns={@JoinColumn(name="account")},
			inverseJoinColumns={@JoinColumn(name="skype_contact")})
	public List<AccountBuddy> getSkypeContacts() {
		return skypeContacts;
	}
}
