package com.o2hlink.activ8.client.action;

import com.o2hlink.activ8.client.entity.User;
import com.o2hlink.activ8.client.response.StringListResponse;

/**
 * @author Miguel Angel Hernandez
 **/
public class SkypeContactListGetAction implements Action<StringListResponse>, Cacheable {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = -2871723212732254051L;
	/**
	 * 
	 **/
	private User user;
//CONSTRUCT
	/**
	 * 
	 **/
	public SkypeContactListGetAction(){
		
	}
	/**
	 * 
	 **/
	public SkypeContactListGetAction(User user){
		setUser(user);
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
	public User getUser() {
		return user;
	}
	/**
	 * 
	 **/
	public long getMaxAge() {
		return 6000000;
	}
	/**
	 * 
	 **/
	@Override
	public boolean equals(Object obj){
		if (obj instanceof SkypeContactListGetAction){
			SkypeContactListGetAction action = (SkypeContactListGetAction)obj;
			return getUser().equals(action.getUser());
		}
		return false;
	}
	/**
	 * 
	 **/
	@Override
	public int hashCode() {
		return getUser().hashCode();
	}
}
