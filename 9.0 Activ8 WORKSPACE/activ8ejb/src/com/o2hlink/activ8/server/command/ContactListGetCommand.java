package com.o2hlink.activ8.server.command;

import com.o2hlink.activ8.client.action.CardListGetAction;
import com.o2hlink.activ8.client.exception.ServerException;
import com.o2hlink.activ8.client.response.CardListResponse;

/**
 * @author Miguel Angel Hernandez
 **/
public class ContactListGetCommand extends Command<CardListGetAction, CardListResponse> {
	/**
	 * 
	 **/
	@Override
	public CardListResponse execute(CardListGetAction action) throws ServerException {
		return new CardListResponse(getDispatcher().getInstitutionSession().getContacts(action.getProvider()));
	}
}
