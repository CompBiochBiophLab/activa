package com.o2hlink.activ8.server.local;

import java.util.List;

import javax.ejb.Local;

import com.o2hlink.activ8.client.entity.Disease;

/**
 * Local operations with disease
 * @author Miguel Angel Hernandez
 **/
@Local
public interface DiseaseLocal {
	/**
	 * @param	disease The disease to persist
	 **/
	public void save(Disease disease);
	/**
	 * Get all diseases
	 * @return	List of all diseases
	 **/
	public List<Disease> getDiseases();
}
