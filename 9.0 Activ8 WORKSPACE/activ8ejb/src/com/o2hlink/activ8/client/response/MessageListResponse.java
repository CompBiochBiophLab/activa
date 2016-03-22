package com.o2hlink.activ8.client.response;

import java.util.List;

import com.o2hlink.activ8.common.entity.IsMessage;

/**
 * A response consisting of a set of messages
 * @author Miguel Angel Hernandez
 **/
public class MessageListResponse implements Response {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = 5399721760561312682L;
	/**
	 * 
	 **/
	private List<IsMessage> messages;
//CONSTRUCTOR
	/**
	 * 
	 **/
	public MessageListResponse(){
		
	}
	/**
	 * 
	 **/
	public MessageListResponse(List<IsMessage> messages){
		setMessages(messages);
	}
//METHODS
	/**
	 * @param messages the messages to set
	 */
	public void setMessages(List<IsMessage> messages) {
		this.messages = messages;
	}
	/**
	 * @return the messages
	 */
	public List<IsMessage> getMessages() {
		return messages;
	}
}
