package com.o2hlink.activ8.server.local;

import java.util.List;

import javax.ejb.Local;

import com.o2hlink.activ8.client.entity.Account;
import com.o2hlink.activ8.client.entity.Group;
import com.o2hlink.activ8.client.entity.HasPapers;
import com.o2hlink.activ8.client.entity.HasUsers;
import com.o2hlink.activ8.client.entity.Feed;
import com.o2hlink.activ8.client.entity.IncomingContactRequest;
import com.o2hlink.activ8.client.entity.Paper;
import com.o2hlink.activ8.client.entity.User;
import com.o2hlink.activ8.client.entity.UserUserRequest;
import com.o2hlink.activ8.client.exception.AuthenticationException;
import com.o2hlink.activ8.client.exception.InsertException;
import com.o2hlink.activ8.client.exception.RemoveException;
import com.o2hlink.activ8.client.exception.RetrievalException;
import com.o2hlink.activ8.client.exception.UpdateException;

/**
 * Local operations on a user
 * @author Miguel Angel Hernandez
 **/
@Local
public interface UserLocal {
//METHODS
	/**
	 * 
	 **/
	public User get(String username, String password) throws AuthenticationException;
	/**
	 * @return	All users
	 **/
	public List<User> getUsers();
	/**
	 * @param	user The user to save
	 * @param	password The password
	 * @return	The user saved
	 **/
	public User save(User user, String password) throws InsertException;
	/**
	 * @param	provider The user provider
	 * @return	The list of users
	 **/
	public List<User> getUsers(HasUsers provider);
	/**
	 * @param	requester The user requesting to join
	 * @param	requested The user requested to join
	 * @param	message An optional message
	 **/
	public void createRequest(User requester, User requested, String message);
	/**
	 * @param	requester The user requesting to join
	 * @param	requested The user requested to join
	 **/
	public void acceptRequest(User requester, User requested);
	/**
	 * @param	requester The user requesting to join
	 * @param	requested The user requested to join
	 **/
	public void rejectRequest(User requester, User requested);
	/**
	 * @param	user The user
	 * @param	group The group
	 * @param	message
	 **/
	public void createRequest(User user, Group group, String message);
	/**
	 * @param	user The user
	 **/
	public List<IncomingContactRequest> getIncomingContactRequests(User user);
	/**
	 * @param	user The user
	 * @return
	 **/
	public List<UserUserRequest> getOutgoingContactRequests(User user);
	/**
	 * @param	user The user
	 **/
	public void update(User user) throws UpdateException;
	/**
	 * @param	user The user
	 * @param	currentPassword The current password
	 * @param	newPassword The new password
	 **/
	public void resetPassword(User user, String currentPassword, String newPassword);
	/**
	 * @param	user The user
	 * @param	rss The rss
	 **/
	public Feed addFeed(User user, Feed rss);
	/**
	 * @param	user The user
	 * @param	rss The rss
	 **/
	public void removeFeed(User user, Feed rss);
	/**
	 * @return	The list of rss the user has
	 **/
	public List<Feed> getFeeds(User user);
	/**
	 * @param	user The user
	 * @param	rss The rss
	 **/
	public Account addAccount(User user, Account account, String password) throws InsertException;
	/**
	 * @param	user The user
	 * @param	rss The rss
	 **/
	public void removeAccount(User user, Account account) throws RemoveException;
	/**
	 * @return	The list of rss the user has
	 **/
	public List<Account> getAccounts(User user) throws RetrievalException;
	/**
	 * 
	 **/
	public List<Paper> getPapers(HasPapers provider) throws RetrievalException;
	/**
	 * 
	 **/
	public void addAccountBuddies(User user, long fileId) throws InsertException;
	/**
	 * 
	 **/
	public List<String> getAccountBuddies(User user) throws RetrievalException;
}
