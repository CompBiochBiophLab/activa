package com.o2hlink.activ8.server.entity;

import java.util.List;

/**
 * Anyone who generates datasets
 * @author Miguel Angel Hernandez
 **/
public interface HasDatasets {
	/**
	 * 
	 **/
	public List<Dataset> getDatasets();
}
