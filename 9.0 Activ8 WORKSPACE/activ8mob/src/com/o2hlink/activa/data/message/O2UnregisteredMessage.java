package com.o2hlink.activa.data.message;

import java.util.Date;

public class O2UnregisteredMessage {
	
	public long sender;
	
	public long[] receivers;
	
	public Date date;
	
	public String topic;
	
	public String text;

	public O2UnregisteredMessage(long sender, long[] receivers, Date date,
			String topic, String text) {
		this.sender = sender;
		this.receivers = receivers;
		this.date = date;
		this.topic = topic;
		this.text = text;
	}
	
	public void Response(String message) {
		
	}
	
	public void Resend (String receiver) {
		
	}
	
}
