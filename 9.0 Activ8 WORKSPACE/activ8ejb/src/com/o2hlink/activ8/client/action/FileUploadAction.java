package com.o2hlink.activ8.client.action;

import com.o2hlink.activ8.client.response.LongResponse;

/**
 * File new action. This action is intended to be used on server side (i.e. Servlets)
 * No response is expected.
 * @author Miguel Angel Hernandez
 **/
public class FileUploadAction implements Action<LongResponse> {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = 6636610388806356107L;
	/**
	 * 
	 **/
	private byte[] content;
//CONSTRUCTOR
	/**
	 * 
	 **/
	public FileUploadAction(){
		
	}
	/**
	 * 
	 **/
	public FileUploadAction(byte[] content){
		setContent(content);
	}
//METHODS
	/**
	 * @param content the content to set
	 */
	public void setContent(byte[] content) {
		this.content = content;
	}
	/**
	 * @return the content
	 */
	public byte[] getContent() {
		return content;
	}
}
