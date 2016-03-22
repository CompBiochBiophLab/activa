package com.o2hlink.activ8.server.entity;

import java.util.List;

import com.o2hlink.activ8.common.entity.HasIdentifier;

/**
 * Identifies if it has patients
 * @author Miguel Angel Hernandez
 * @see	com.o2hlink.activ8.client.entity.HasPatients
 **/
public interface HasPatients extends HasIdentifier {
	/**
	 * 
	 **/
	public List<Patient> getPatients();
}
