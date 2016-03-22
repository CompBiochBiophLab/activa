package com.o2hlink.activ8.server.search;

import java.util.List;

import javax.ejb.EJB;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.jboss.ejb3.annotation.Service;

import com.o2hlink.activ8.common.entity.HasSummary;
import com.o2hlink.activ8.common.util.Mapper;
import com.o2hlink.activ8.server.local.SearchLocal;
import com.o2hlink.activ8.server.remote.SearchPlugin;

/**
 * @author Miguel Angel Hernandez
 **/
@Service
public class InstitutionSearch implements SearchPlugin {
//FIELDS
	/**
	 * 
	 **/
	@PersistenceContext(unitName="Activ8EJB3")
	private EntityManager em;
	/**
	 * 
	 **/
	@EJB
	private SearchLocal searchSession;
//METHODS
	/**
	 * 
	 **/
	public List<HasSummary> search(String query, int start, int offset) {
		Query q = em.createQuery("from Institution where name like '%"+query+"%'");
		q.setFirstResult(start);
		q.setMaxResults(offset);
		return Mapper.map(q.getResultList());
	}
	/**
	 * 
	 **/
	public void start() {
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
		return "ActivA institutions";
	}
}
