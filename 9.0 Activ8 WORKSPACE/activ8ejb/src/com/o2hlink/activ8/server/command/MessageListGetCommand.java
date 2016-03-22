package com.o2hlink.activ8.server.command;

import com.o2hlink.activ8.client.action.MessageListGetAction;
import com.o2hlink.activ8.client.exception.ServerException;
import com.o2hlink.activ8.client.response.MessageListResponse;

/**
 * 
 **/
public class MessageListGetCommand extends Command<MessageListGetAction, MessageListResponse> {
	/**
	 * 
	 **/
	@Override
	public MessageListResponse execute(MessageListGetAction action) throws ServerException {
		if (action.getAccount()!=null)
			return new MessageListResponse(getDispatcher().getMessageSession().getMessages(action.getUser(), action.getAccount()));
		return new MessageListResponse(getDispatcher().getMessageSession().getMessages(action.getUser()));
	}

}
