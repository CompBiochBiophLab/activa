package com.o2hlink.activ8.server.search;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;

import org.jboss.ejb3.annotation.Service;

import com.o2hlink.activ8.common.entity.HasSummary;
import com.o2hlink.activ8.server.local.EntrezLocal;
import com.o2hlink.activ8.server.local.SearchLocal;
import com.o2hlink.activ8.server.remote.SearchPlugin;

/**
 * Search genes
 * @author Miguel Angel Hernandez
 **/
@Service
public class EntrezSearch implements SearchPlugin {
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
	private EntrezLocal geneSession;
//METHODS
	/**
	 * 
	 **/
	public List<HasSummary> search(String query, int start, int offset) {
		List<HasSummary> result = new ArrayList<HasSummary>();
		for (HasSummary protein:geneSession.getGenes(query, start, offset))
			result.add(protein);
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
		return "Entrez Gene";
	}
}
