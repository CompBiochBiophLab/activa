package com.o2hlink.activ8.server.command;

import com.o2hlink.activ8.client.action.SubsetListGetAction;
import com.o2hlink.activ8.client.exception.ServerException;
import com.o2hlink.activ8.client.response.SubsetListResponse;

/**
 * 
 **/
public class SubsetListGetCommand extends Command<SubsetListGetAction, SubsetListResponse> {
	/**
	 * 
	 **/
	@Override
	public SubsetListResponse execute(SubsetListGetAction action) throws ServerException {
		return new SubsetListResponse(getDispatcher().getDatasetSession().getSubsets(action.getDataset()));
	}

}
