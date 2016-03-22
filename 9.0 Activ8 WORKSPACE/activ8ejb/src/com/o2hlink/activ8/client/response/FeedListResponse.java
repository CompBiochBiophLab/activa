/**
 * 
 */
package com.o2hlink.activ8.client.response;

import java.util.List;

import com.o2hlink.activ8.client.entity.Feed;

/**
 * Return RSS list
 * @author Miguel Angel Hernandez
 **/
public class FeedListResponse implements Response {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = -8534080196704428387L;
	/**
	 * 
	 **/
	private List<Feed> feeds;
//CONSTRUCTOR
	/**
	 * Empty constructor to allow serialization
	 */
	public FeedListResponse(){
		
	}
	/**
	 * 
	 */
	public FeedListResponse(List<Feed> feeds){
		setFeeds(feeds);
	}
//METHODS
	/**
	 * @param feeds the feeds to set
	 */
	public void setFeeds(List<Feed> feeds) {
		this.feeds = feeds;
	}
	/**
	 * @return the feeds
	 */
	public List<Feed> getFeeds() {
		return feeds;
	}
}
