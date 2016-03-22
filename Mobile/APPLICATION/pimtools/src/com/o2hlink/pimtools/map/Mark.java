package com.o2hlink.pimtools.map;

public class Mark {
	
	public double longitude;
	public double latitude;
	public String name;
	public String text;
	
	public Mark() {
		this.longitude = 0.0;
		this.latitude = 0.0;
		this.name = null;
		this.text = null;
	}
	
	public Mark(double longitude, double latitude, String name, String text) {
		this.longitude = longitude;
		this.latitude = latitude;
		this.name = name;
		this.text = text;
	}
	
}
