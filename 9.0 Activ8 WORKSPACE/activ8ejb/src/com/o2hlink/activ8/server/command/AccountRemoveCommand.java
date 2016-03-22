package com.o2hlink.activ8.server.command;

import com.o2hlink.activ8.client.action.AccountRemoveAction;
import com.o2hlink.activ8.client.exception.ServerException;
import com.o2hlink.activ8.client.response.VoidResponse;

/**
 * 
 **/
public class AccountRemoveCommand extends Command<AccountRemoveAction, VoidResponse> {
	/**
	 * 
	 **/
	@Override
	public VoidResponse execute(AccountRemoveAction action) throws ServerException {
		getDispatcher().getUserSession().removeAccount(action.getUser(), action.getAccount());
		return new VoidResponse();
	}
}
