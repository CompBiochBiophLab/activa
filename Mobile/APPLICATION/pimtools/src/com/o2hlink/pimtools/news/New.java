package com.o2hlink.pimtools.news;

public class New {
	
	public int id;
	
	public String title;
	
	public String author;
	
	public String content;
	
	public String link;
	
	public String snippet;
	
	public String date;
	
	public String photoURL;
	
	public int photoHeight;

	public int photoWidth;
	
	public New(int id) {
		this.id = id;
		this.photoHeight = 160;
		this.photoWidth = 160;
	}
	
}
