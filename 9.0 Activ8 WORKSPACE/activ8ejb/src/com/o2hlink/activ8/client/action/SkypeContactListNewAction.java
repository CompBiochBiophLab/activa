package com.o2hlink.activ8.client.action;

import com.o2hlink.activ8.client.entity.User;
import com.o2hlink.activ8.client.response.VoidResponse;

/**
 * As there is no web service to retrieve the skype contacts (at least not known by July/2010), we upload the file with contacts and later on send a request to update.
 * @author Miguel Angel Hernandez
 **/
public class SkypeContactListNewAction implements Action<VoidResponse> {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = -5215564819098861200L;
	/**
	 * 
	 **/
	private User user;
	/**
	 * 
	 **/
	private long file;
//CONSTRUCTOR
	/**
	 * 
	 **/
	public SkypeContactListNewAction(){
		
	}
	/**
	 * 
	 **/
	public SkypeContactListNewAction(User user, long file){
		setUser(user);
		setFile(file);
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
	 * @param file the file to set
	 */
	public void setFile(long file) {
		this.file = file;
	}
	/**
	 * @return the file
	 */
	public long getFile() {
		return file;
	}
}
