package com.o2hlink.activ8.server.command;

import com.o2hlink.activ8.client.action.CountryListGetAction;
import com.o2hlink.activ8.client.exception.ServerException;
import com.o2hlink.activ8.client.response.CountryListResponse;

/**
 * @author Miguel Angel Hernandez
 **/
public class CountryListGetCommand extends Command<CountryListGetAction, CountryListResponse> {
	/**
	 * 
	 **/
	@Override
	public CountryListResponse execute(CountryListGetAction action) throws ServerException {
		return new CountryListResponse(getDispatcher().getCountrySession().getCountries());
	}
}
