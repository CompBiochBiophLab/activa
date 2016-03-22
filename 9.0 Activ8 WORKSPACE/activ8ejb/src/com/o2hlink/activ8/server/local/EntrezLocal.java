package com.o2hlink.activ8.server.local;

import java.util.List;

import javax.ejb.Local;

import com.o2hlink.activ8.client.entity.Gene;
import com.o2hlink.activ8.client.entity.Exon;

/**
 * Expected operations with genes
 * @author Miguel Angel Hernandez
 **/
@Local
public interface EntrezLocal {
	/**
	 * Get the list of all the genes meeting a certain criteria
	 * @param	query The query
	 * @param	start The start index
	 * @param	length The length of the search
	 * @return	The list of genes found
	 **/
	public List<Gene> getGenes(String query, int start, int length);
	/**
	 * Get the list of exons found in a gene
	 * @param	gene The gene to search
	 * @return  The list of exons
	 **/
	public List<Exon> getExons(Gene gene);
}
