/**
 * 
 */
package com.o2hlink.activ8.client.response;

import java.util.List;

import com.o2hlink.activ8.client.entity.Account;

/**
 * Return a list of communication channels
 * @author Miguel Angel Hernandez
 **/
public class AccountListResponse implements Response {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = -8534080196704428387L;
	/**
	 * 
	 **/
	private List<Account> accounts;
//CONSTRUCTOR
	/**
	 * Empty constructor to allow serialization
	 */
	public AccountListResponse(){
		
	}
	/**
	 * 
	 */
	public AccountListResponse(List<Account> communications){
		setAccounts(communications);
	}
//METHODS
	/**
	 * @param feeds the feeds to set
	 */
	public void setAccounts(List<Account> communications) {
		this.accounts = communications;
	}
	/**
	 * @return the feeds
	 */
	public List<Account> getAccounts() {
		return accounts;
	}
}
