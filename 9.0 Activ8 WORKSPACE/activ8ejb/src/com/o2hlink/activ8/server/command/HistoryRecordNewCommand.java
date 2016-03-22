package com.o2hlink.activ8.server.command;

import com.o2hlink.activ8.client.action.HistoryRecordNewAction;
import com.o2hlink.activ8.client.exception.ServerException;
import com.o2hlink.activ8.client.response.HistoryRecordResponse;

/**
 * 
 **/
public class HistoryRecordNewCommand extends Command<HistoryRecordNewAction, HistoryRecordResponse> {
	/**
	 * 
	 **/
	@Override
	public HistoryRecordResponse execute(HistoryRecordNewAction action) throws ServerException {
		return new HistoryRecordResponse(getDispatcher().getPatientSession().addHistory(action.getPatient(), action.getHistoryRecord()));
	}

}
