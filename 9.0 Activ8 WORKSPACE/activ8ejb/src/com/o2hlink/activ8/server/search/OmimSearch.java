package com.o2hlink.activ8.server.search;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;

import org.jboss.ejb3.annotation.Service;

import com.o2hlink.activ8.common.entity.HasSummary;
import com.o2hlink.activ8.server.local.OmimLocal;
import com.o2hlink.activ8.server.local.SearchLocal;
import com.o2hlink.activ8.server.remote.SearchPlugin;

/**
 * @author Miguel Angel Hernandez
 **/
@Service
public class OmimSearch implements SearchPlugin {
//FIELDS
	/**
	 * 
	 **/
	@EJB
	private SearchLocal searchSession;	
	/**
	 * 
	 **/
	@EJB
	private OmimLocal omimSession;
//METHODS
	/**
	 * 
	 **/
	public List<HasSummary> search(String query, int start, int offset) {
		List<HasSummary> result = new ArrayList<HasSummary>();
		for (HasSummary paper:omimSession.getDiseases(query, start, offset))
			result.add(paper);
		return result;
	}
	/**
	 * 
	 **/
	public void start() throws Exception {
		searchSession.addPlugin(this);
	}
	/**
	 * 
	 **/
	public void stop() {
		searchSession.removePlugin(this);
	}
	/**
	 * 
	 **/
	public String getDatabaseName() {
		return "Online Mendelian Inheritance in Man";
	}
}
