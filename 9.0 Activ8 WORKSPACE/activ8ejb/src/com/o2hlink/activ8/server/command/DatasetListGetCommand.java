/**
 * 
 */
package com.o2hlink.activ8.server.command;

import com.o2hlink.activ8.client.action.DatasetListGetAction;
import com.o2hlink.activ8.client.exception.ServerException;
import com.o2hlink.activ8.client.response.DatasetListResponse;

/**
 * @author Miguel Angel Hernandez
 **/
public class DatasetListGetCommand extends Command<DatasetListGetAction, DatasetListResponse> {
	/**
	 * @param	action The action to be performed
	 * @return	The response to the action
	 **/
	public DatasetListResponse execute(DatasetListGetAction action) throws ServerException {
		return new DatasetListResponse(getDispatcher().getDatasetSession().getDatasets(action.getProvider()));
	}
}
