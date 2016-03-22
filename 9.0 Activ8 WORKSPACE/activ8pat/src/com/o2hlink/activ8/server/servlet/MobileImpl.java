package com.o2hlink.activ8.server.servlet;

import java.io.BufferedReader;
import java.io.PrintWriter;

import javax.ejb.EJB;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.o2hlink.activ8.client.action.MobileAction;
import com.o2hlink.activ8.server.remote.DispatcherRemote;

/**
 * @author Miguel Angel Hernandez
 **/
public class MobileImpl extends HttpServlet {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = 488520694120496468L;
	/**
	 * 
	 **/
	@EJB(mappedName="DispatcherSession/remote")
	private DispatcherRemote dispatcher;
//METHODS
	/**
	 * 
	 **/
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
    	try {
    		System.out.println("MobileImpl "+request.getQueryString());
    		
    		MobileAction action = new MobileAction();
    		action.setUser(request.getParameter("user"));
    		action.setPass(request.getParameter("pwd"));
    		BufferedReader reader = request.getReader();
    		String xml = "", line = "";
    		while ((line = reader.readLine())!=null)
    			xml += line;
    		action.setXml(xml);
    		
    		System.out.println("MobileImpl "+xml);
    		
    		try {
    			String sout = dispatcher.dispatch(action).getString();
    			
    			response.setHeader("Content-Type", "application/xml; charset=utf-8");
    			response.setHeader("Cache-Control", "no-cache");
    			PrintWriter out = response.getWriter();
    			out.write(sout);
    			out.close();
    		}
    		catch (Exception exception){
    			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    		}
    		
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
    }
}
