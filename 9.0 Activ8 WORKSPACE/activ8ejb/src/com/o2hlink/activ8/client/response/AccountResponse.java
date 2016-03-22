/**
 * 
 */
package com.o2hlink.activ8.client.response;

import com.o2hlink.activ8.client.entity.Account;

/**
 * Return a list of communication channels
 * @author Miguel Angel Hernandez
 **/
public class AccountResponse implements Response {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = -8534080196704428387L;
	/**
	 * 
	 **/
	private Account account;
//CONSTRUCTOR
	/**
	 * Empty constructor to allow serialization
	 */
	public AccountResponse(){
		
	}
	/**
	 * 
	 */
	public AccountResponse(Account account){
		setAccount(account);
	}
//METHODS
	/**
	 * @param feeds the feeds to set
	 */
	public void setAccount(Account account) {
		this.account = account;
	}
	/**
	 * @return the feeds
	 */
	public Account getAccount() {
		return account;
	}
}
