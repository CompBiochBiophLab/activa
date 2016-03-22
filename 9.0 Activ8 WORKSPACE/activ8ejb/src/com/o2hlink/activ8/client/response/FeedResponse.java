/**
 * 
 */
package com.o2hlink.activ8.client.response;

import com.o2hlink.activ8.client.entity.Feed;

/**
 * Return a list of communication channels
 * @author Miguel Angel Hernandez
 **/
public class FeedResponse implements Response {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = -8534080196704428387L;
	/**
	 * 
	 **/
	private Feed feed;
//CONSTRUCTOR
	/**
	 * Empty constructor to allow serialization
	 */
	public FeedResponse(){
		
	}
	/**
	 * 
	 */
	public FeedResponse(Feed feed){
		setFeed(feed);
	}
//METHODS
	/**
	 * @param feed the feed to set
	 */
	public void setFeed(Feed feed) {
		this.feed = feed;
	}
	/**
	 * @return the feed
	 */
	public Feed getFeed() {
		return feed;
	}
}
