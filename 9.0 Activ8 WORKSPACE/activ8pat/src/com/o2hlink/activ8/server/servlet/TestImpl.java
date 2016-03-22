package com.o2hlink.activ8.server.servlet;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Miguel Angel Hernandez
 **/
public class TestImpl extends HttpServlet {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = 5819754223551631502L;
//METHODS
	/**
	 * 
	 **/
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
    	String xml = "<?xml version=\"1.0\" encoding=\"iso-8859-1\"?>" +
    			"<MOBILEREQUEST>"+
				"<TYPEOFREQUEST VALUE=\"CONFIG\" />"+
				"<MOBILEDEVICE>"+
				"<IDNUMBER VALUE=\"9a518793714c4c3748b2c0b845f351e98443334e\" />"+
				"<DATETIME VALUE=\"20100426164553\" />"+
				"</MOBILEDEVICE>"+
				"</MOBILEREQUEST>";
    	try {
			response.setContentType("application/xml; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.write("<pre>");
			
    		URL search = new URL("http://www.o2hlink.com/patient/mobile");
			System.out.println("Connecting to http://www.o2hlink.com/patient/mobile");
    		URLConnection connection = search.openConnection();
    		{
    			connection.setDoOutput(true);
    			OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
    			writer.write(xml);
    			writer.flush();
    			writer.close();
    		}
    		System.out.println("now reading..");
    		{
    			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
    			String line = "";    			
    			while ((line=reader.readLine())!=null) 
    				out.write(line);
    			reader.close();				
    		}    		    		
    		
			out.write("</pre>");
			out.close();
    	}
    	catch (Exception e){
    		e.printStackTrace();
    		//System.out.println(e.printStackTrace());
    	}    	
    }
}
