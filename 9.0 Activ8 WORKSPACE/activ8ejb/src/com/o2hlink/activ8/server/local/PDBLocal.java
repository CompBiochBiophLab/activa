package com.o2hlink.activ8.server.local;

import java.util.List;

import javax.ejb.Local;

import com.o2hlink.activ8.client.entity.Protein;

/**
 * Expected operations on proteins
 * @author Miguel Angel Hernandez
 **/
@Local
public interface PDBLocal {
	/**
	 * 
	 **/
	public List<Protein> getProteins(String query, int start, int offset);
}
