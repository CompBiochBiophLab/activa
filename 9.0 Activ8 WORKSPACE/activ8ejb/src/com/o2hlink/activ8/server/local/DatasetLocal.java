/**
 * 
 */
package com.o2hlink.activ8.server.local;

import java.util.List;

import javax.ejb.Local;

import com.o2hlink.activ8.client.entity.Dataset;
import com.o2hlink.activ8.client.entity.HasDatasets;
import com.o2hlink.activ8.client.entity.Subset;
import com.o2hlink.activ8.client.entity.User;

/**
 * @author Miguel Angel Hernandez
 **/
@Local
public interface DatasetLocal {
	/**
	 * @param	Dataset The dataset to be stored
	 * @return	Dataset The dataset stored
	 **/
	public Dataset save(Dataset dataset, User owner);
	/**
	 * @param	provider The provider of datasets
	 * @return	The list of datasets
	 **/
	public List<Dataset> getDatasets(HasDatasets provider);
	/**
	 * 
	 **/
	public List<Subset> getSubsets(Dataset dataset);
}
