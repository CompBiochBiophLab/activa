package com.o2hlink.activ8.server.command;

import com.o2hlink.activ8.client.action.CardNewAction;
import com.o2hlink.activ8.client.exception.ServerException;
import com.o2hlink.activ8.client.response.CardResponse;

/**
 * 
 **/
public class CardNewCommand extends Command<CardNewAction, CardResponse> {
	/**
	 * 
	 **/
	@Override
	public CardResponse execute(CardNewAction action) throws ServerException {
		return new CardResponse(getDispatcher().getInstitutionSession().addContact(action.getProvider(), action.getContact()));
	}
}
