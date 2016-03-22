package com.o2hlink.activ8.client.action;

import com.o2hlink.activ8.client.response.StringListResponse;

/**
 * @author Miguel Angel Hernandez
 **/
public class DatabaseListGetAction implements Action<StringListResponse>, Cacheable {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = 6148363889236008979L;
//CONSTRUCTOR
	/**
	 * 
	 **/
	public DatabaseListGetAction(){
		
	}
//METHODS
	/**
	 * 
	 **/
	public long getMaxAge() {
		return 24*60*1000;
	}
	/**
	 * 
	 **/
	@Override
	public boolean equals(Object obj){
		if (obj instanceof DatabaseListGetAction)
			return true;
		return false;
	}
	/**
	 * 
	 **/
	@Override
	public int hashCode() {
		return 0;
	}
}
