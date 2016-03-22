package com.o2hlink.zonated.data;

import com.o2hlink.activ8.client.entity.SimpleMessage;

public class Message {
	
	public SimpleMessage message;
	
	public String content;
	
	public Message() {
		this.message = new SimpleMessage();
		this.content = "";
	}
	
	public Message(SimpleMessage message, String content) {
		this.message = message;
		this.content = content;
	}
}
