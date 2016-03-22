package com.o2hlink.activ8.server.local;

import java.util.List;

import javax.ejb.Local;

import com.o2hlink.activ8.client.entity.ProteinAminoacidModification;
import com.o2hlink.activ8.client.entity.Protein;
import com.o2hlink.activ8.client.entity.ProteinRegion;
import com.o2hlink.activ8.client.entity.ProteinSecondaryStructure;

/**
 * @author Miguel Angel Hernandez
 **/
@Local
public interface UniprotLocal {
	/**
	 * @param	query The query
	 * @param	start The start
	 * @param	offset The ..
	 **/
	public List<Protein> getProteins(String query, int start, int offset);
	/**
	 * @param	protein The protein
	 * @return	The protein regions
	 **/
	public List<ProteinRegion> getRegions(Protein protein);
	/**
	 * @param	protein The protein
	 **/
	public List<ProteinAminoacidModification> getAminoacidModifications(Protein protein);
	/**
	 * @param	protein The protein
	 * @return	The protein secondary structure
	 **/
	public List<ProteinSecondaryStructure> getSecondaryStructures(Protein protein);
}
