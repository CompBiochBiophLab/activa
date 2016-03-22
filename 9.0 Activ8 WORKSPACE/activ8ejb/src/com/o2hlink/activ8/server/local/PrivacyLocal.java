package com.o2hlink.activ8.server.local;

import java.util.List;

import javax.ejb.Local;

import com.o2hlink.activ8.client.entity.HasPrivacyPolicy;
import com.o2hlink.activ8.client.entity.User;

/**
 * Remote operations with privacy
 * @author Miguel Angel Hernandez
 **/
@Local
public interface PrivacyLocal {
	/**
	 * @param	provider A class implementing privacy
	 * @return	The users to which provide access
	 **/
	public List<User> getAllowedUsers(HasPrivacyPolicy provider);
	/**
	 * @param	provider A class implementing privacy
	 * @param	users The users to which provide access
	 **/
	public void addAllowedUser(HasPrivacyPolicy provider, User users);	
	/**
	 * @param	provider A class implementing privacy
	 * @param	users The users to which remove access
	 **/
	public void removeAllowedUser(HasPrivacyPolicy provider, User users);
}
