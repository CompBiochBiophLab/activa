package com.o2hlink.activa.data.message;

import java.util.Date;

public class O2Message {

	public static int count = 0;
	
	public long id;
	
	public long sender;
	
	public long receiver;
	
	public Date date;
	
	public String topic;
	
	public String text;

	public O2Message(long id, long sender, long receiver, Date date, String topic, String text) {
		this.id = O2Message.count;
		O2Message.count++;
		this.sender = sender;
		this.receiver = receiver;
		this.date = date;
		this.topic = topic;
		this.text = text;
	}
	
}
