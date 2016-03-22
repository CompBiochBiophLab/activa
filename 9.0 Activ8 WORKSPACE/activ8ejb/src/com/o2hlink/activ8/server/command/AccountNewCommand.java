package com.o2hlink.activ8.server.command;

import com.o2hlink.activ8.client.action.AccountNewAction;
import com.o2hlink.activ8.client.exception.ServerException;
import com.o2hlink.activ8.client.response.AccountResponse;

/**
 * 
 **/
public class AccountNewCommand extends Command<AccountNewAction, AccountResponse> {
	/**
	 * 
	 **/
	@Override
	public AccountResponse execute(AccountNewAction action) throws ServerException {
		return new AccountResponse(getDispatcher().getUserSession().addAccount(action.getUser(), action.getAccount(), action.getPassword()));
	}
}
