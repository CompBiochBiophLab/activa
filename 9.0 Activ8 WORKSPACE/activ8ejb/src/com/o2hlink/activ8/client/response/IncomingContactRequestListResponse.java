/**
 * 
 */
package com.o2hlink.activ8.client.response;

import java.util.List;

import com.o2hlink.activ8.client.entity.IncomingContactRequest;

/**
 * Returns a list of user to user requests
 * @author Miguel Angel Hernandez
 **/
public class IncomingContactRequestListResponse implements Response {
//METHODS
	/**
	 * 
	 */
	private static final long serialVersionUID = -1004972265287756083L;
	/**
	 * 
	 **/
	private List<IncomingContactRequest> result;
//CONSTRUCTOR
	/**
	 * Empty constructor to allow serialization
	 **/
	public IncomingContactRequestListResponse(){
		
	}
	/**
	 * 
	 **/
	public IncomingContactRequestListResponse(List<IncomingContactRequest> result){
		this.setResult(result);
	}
//METHODS
	/**
	 * @param result the result to set
	 */
	public void setResult(List<IncomingContactRequest> result) {
		this.result = result;
	}
	/**
	 * @return the result
	 */
	public List<IncomingContactRequest> getResult() {
		return result;
	}
}
