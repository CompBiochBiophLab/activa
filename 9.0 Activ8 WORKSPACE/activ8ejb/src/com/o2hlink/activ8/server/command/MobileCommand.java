package com.o2hlink.activ8.server.command;

import com.o2hlink.activ8.client.action.MobileAction;
import com.o2hlink.activ8.client.exception.ServerException;
import com.o2hlink.activ8.client.response.StringResponse;

/**
 * @author Miguel Angel Hernandez
 **/
public class MobileCommand extends Command<MobileAction, StringResponse> {
	/**
	 * 
	 **/
	@Override
	public StringResponse execute(MobileAction action) throws ServerException {
		return new StringResponse(getDispatcher().getMobileSession().process(action.getUser(), action.getPass(), action.getXml()));
	}
}
