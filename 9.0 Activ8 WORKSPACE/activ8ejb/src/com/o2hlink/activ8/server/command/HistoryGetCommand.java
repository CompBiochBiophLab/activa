package com.o2hlink.activ8.server.command;

import com.o2hlink.activ8.client.action.HistoryGetAction;
import com.o2hlink.activ8.client.exception.ServerException;
import com.o2hlink.activ8.client.response.HistoryRecordListResponse;

/**
 * 
 **/
public class HistoryGetCommand extends Command<HistoryGetAction, HistoryRecordListResponse> {
	/**
	 * 
	 **/
	@Override
	public HistoryRecordListResponse execute(HistoryGetAction action) throws ServerException {
		return new HistoryRecordListResponse(getDispatcher().getPatientSession().getHistory(action.getPatient()));
	}

}
