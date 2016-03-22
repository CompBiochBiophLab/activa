package com.o2hlink.activ8.client.action;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;

import com.o2hlink.activ8.client.entity.Clinician;
import com.o2hlink.activ8.client.entity.Feed;
import com.o2hlink.activ8.client.entity.Patient;
import com.o2hlink.activ8.client.entity.Researcher;
import com.o2hlink.activ8.client.entity.User;
import com.o2hlink.activ8.client.response.FeedResponse;

/**
 * Create a new feeds (i.e. RSS, Atom)
 * @author Miguel Angel Hernandez
 **/
public class FeedNewAction implements Action<FeedResponse> {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = -9142882854808494304L;
	/**
	 * 
	 **/
	private User user;
	/**
	 * 
	 **/
	private Feed rss;
//CONSTRUCTOR
	/**
	 * 
	 **/
	public FeedNewAction(){
		
	}
	/**
	 * 
	 **/
	public FeedNewAction(User user, Feed rss){
		setUser(user);
		setRss(rss);
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
	@XmlElements({
		@XmlElement(name="patient", type=Patient.class),
		@XmlElement(name="clinician", type=Clinician.class),
		@XmlElement(name="researcher", type=Researcher.class)
	})
	public User getUser() {
		return user;
	}
	/**
	 * @param rss the rss to set
	 */
	public void setRss(Feed rss) {
		this.rss = rss;
	}
	/**
	 * @return the rss
	 */
	public Feed getRss() {
		return rss;
	}
}
