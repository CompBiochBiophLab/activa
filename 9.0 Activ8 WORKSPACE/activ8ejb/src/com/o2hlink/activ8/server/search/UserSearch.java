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
 * Search service for users
 * @author Miguel Angel Hernandez
 **/
@Service
public class UserSearch implements SearchPlugin {
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
//CONSTRUCTOR
	/**
	 * 
	 **/
	public void start(){
		searchSession.addPlugin(this);
	}
	/**
	 * 
	 **/
	public void stop(){
		searchSession.removePlugin(this);
	}
//METHODS
	/**
	 * 
	 **/
	public List<HasSummary> search(String query, int first, int last) {
		Query q = em.createQuery(" from User " +
				" where username like '%"+query+"%' " +
				" or firstName like '%"+query+"%' " +
				" or lastName like '%"+query+"%' ");
		q.setFirstResult(first);
		q.setMaxResults(last);
		return Mapper.map(q.getResultList());
	}
	/**
	 * 
	 **/
	public String getDatabaseName() {
		return "ActivA users";
	}
}
