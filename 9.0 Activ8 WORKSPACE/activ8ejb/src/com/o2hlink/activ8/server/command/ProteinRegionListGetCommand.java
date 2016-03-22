package com.o2hlink.activ8.server.command;

import com.o2hlink.activ8.client.action.ProteinRegionListGetAction;
import com.o2hlink.activ8.client.exception.ServerException;
import com.o2hlink.activ8.client.response.ProteinRegionListResponse;

/**
 * @author Miguel Angel Hernandez
 **/
public class ProteinRegionListGetCommand extends Command<ProteinRegionListGetAction, ProteinRegionListResponse> {
	/**
	 * 
	 **/
	@Override
	public ProteinRegionListResponse execute(ProteinRegionListGetAction action) throws ServerException {
		return new ProteinRegionListResponse(getDispatcher().getUniprotSession().getRegions(action.getProtein()));
	}
}
