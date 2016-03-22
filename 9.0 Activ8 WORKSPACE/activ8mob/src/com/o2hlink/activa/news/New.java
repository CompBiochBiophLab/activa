package com.o2hlink.activa.news;

import java.io.IOException;
import java.io.StringWriter;

import org.xmlpull.v1.XmlSerializer;

import android.util.Xml;

public class New {
	
	public int id;
	
	public String title;
	
	public String author;
	
	public String content;
	
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
	
	public void passNewToXML(XmlSerializer serializer, StringWriter writer) {
//	    XmlSerializer serializer = Xml.newSerializer();
//	    StringWriter writer = new StringWriter();
        try {
			serializer.setOutput(writer);
	        serializer.startDocument("UTF-8", true);
	        serializer.startTag("", "NEW");
	        serializer.attribute("", "ID", String.valueOf(this.id));
	        if (this.author != null) serializer.attribute("", "AUTHOR", this.author);
	        else serializer.attribute("", "AUTHOR", "");
	        if (this.author != null) serializer.attribute("", "CONTENT", this.content);
	        else serializer.attribute("", "CONTENT", "");
	        if (this.author != null) serializer.attribute("", "SNIPPET", this.snippet);
	        else serializer.attribute("", "SNIPPET", "");
	        if (this.author != null) serializer.attribute("", "DATE", this.date);
	        else serializer.attribute("", "DATE", "");
            serializer.endTag("", "NEW");
            serializer.endDocument();

		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
