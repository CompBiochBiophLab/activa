/**
 * 
 */
package com.o2hlink.activ8.client.action;

import com.o2hlink.activ8.client.response.SearchResponse;

/**
 * Class defining a search action
 * @author Miguel Angel Hernandez
 **/
final public class SearchAction implements Action<SearchResponse>, Cacheable, Scrollable {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = 1865068935516547392L;
	/**
	 * 
	 **/
	private String query = "";
	/**
	 * 
	 **/
	private int first = 0;
	/**
	 * 
	 **/
	private int last = 10;
//CONSTRUCTOR
	/**
	 * Empty constructor to allow serialization
	 **/
	public SearchAction(){
		
	}
	/**
	 * 
	 **/
	public SearchAction(String query){
		setQuery(query);
	}
	/**
	 * 
	 **/
	public SearchAction(String query, int first, int last){
		this(query);
		setFirst(first);
		setLast(last);
	}
//METHODS
	/**
	 * @param query the query to set
	 */
	public void setQuery(String query) {
		this.query = query;
	}
	/**
	 * @return the query
	 */
	public String getQuery() {
		return query;
	}
	/**
	 * @param first the first to set
	 */
	public void setFirst(int first) {
		this.first = first;
	}
	/**
	 * 
	 * @return the first
	 */
	public int getFirst() {
		return first;
	}
	/**
	 * @param last the last to set
	 */
	public void setLast(int last) {
		this.last = last;
	}
	/**
	 * @return the last
	 */
	public int getLast() {
		return last;
	}
	/**
	 * 
	 **/
	public long getMaxAge() {
		return 5*60*1000;
	}
	/**
	 * 
	 **/
	@Override
	public boolean equals(Object obj){
		if (obj instanceof SearchAction){
			SearchAction action = (SearchAction)obj;
			return getQuery().equals(action.getQuery()) && getFirst()==action.getFirst() && getLast()==action.getLast();
		}
		return false;
	}
	/**
	 * 
	 **/
	@Override
	public int hashCode(){
		return getQuery().hashCode() ^ getFirst() ^ getLast();
	}
}
