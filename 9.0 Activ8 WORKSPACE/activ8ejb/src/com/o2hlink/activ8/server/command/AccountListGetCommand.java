package com.o2hlink.activ8.server.command;

import com.o2hlink.activ8.client.action.AccountListGetAction;
import com.o2hlink.activ8.client.exception.ServerException;
import com.o2hlink.activ8.client.response.AccountListResponse;

/**
 * @author Miguel Angel Hernandez
 **/
public class AccountListGetCommand extends Command<AccountListGetAction, AccountListResponse> {
	/**
	 * 
	 **/
	@Override
	public AccountListResponse execute(AccountListGetAction action) throws ServerException {
		return new AccountListResponse(getDispatcher().getUserSession().getAccounts(action.getUser()));
	}
}
