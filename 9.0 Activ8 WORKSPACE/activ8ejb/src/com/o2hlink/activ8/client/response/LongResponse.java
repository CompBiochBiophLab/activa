package com.o2hlink.activ8.client.response;

/**
 * @author Miguel Angel Hernandez
 **/
public class LongResponse implements Response {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = -8037636118894492013L;
	/**
	 * 
	 **/
	private long response;
//CONSTRUCTOR
	/**
	 * 
	 **/
	public LongResponse(){
		
	}
	/**
	 * 
	 **/
	public LongResponse(long id){
		setResponse(id);
	}
//METHODS
	/**
	 * @param response the response to set
	 */
	public void setResponse(long response) {
		this.response = response;
	}
	/**
	 * @return the response
	 */
	public long getResponse() {
		return response;
	}
}
