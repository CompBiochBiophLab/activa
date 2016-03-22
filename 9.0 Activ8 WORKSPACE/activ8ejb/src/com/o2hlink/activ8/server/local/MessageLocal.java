package com.o2hlink.activ8.server.local;

import java.util.List;

import javax.ejb.Local;

import com.o2hlink.activ8.client.entity.Account;
import com.o2hlink.activ8.client.entity.User;
import com.o2hlink.activ8.common.entity.IsMessage;

/**
 * @author Miguel Angel Hernandez
 **/
@Local
public interface MessageLocal {
	/**
	 * 
	 **/
	public List<IsMessage> getMessages(User user);
	/**
	 * 
	 **/
	public List<IsMessage> getMessages(User user, Account account);
}
