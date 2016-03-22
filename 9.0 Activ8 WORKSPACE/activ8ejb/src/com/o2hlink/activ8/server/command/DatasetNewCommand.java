/**
 * 
 */
package com.o2hlink.activ8.server.command;

import com.o2hlink.activ8.client.action.DatasetNewAction;
import com.o2hlink.activ8.client.exception.ServerException;
import com.o2hlink.activ8.client.response.DatasetResponse;

/**
 * @author Miguel Angel Hernandez
 **/
public class DatasetNewCommand extends Command<DatasetNewAction, DatasetResponse> {
	/**
	 * 
	 **/
	public DatasetResponse execute(DatasetNewAction action) throws ServerException {
		return new DatasetResponse(getDispatcher().getDatasetSession().save(action.getDataset(), action.getOwner()));
	}
}
