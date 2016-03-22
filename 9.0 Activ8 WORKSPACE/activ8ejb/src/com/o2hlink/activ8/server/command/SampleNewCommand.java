/**
 * 
 */
package com.o2hlink.activ8.server.command;

import com.o2hlink.activ8.client.action.SampleNewAction;
import com.o2hlink.activ8.client.exception.ServerException;
import com.o2hlink.activ8.client.response.SampleResponse;

/**
 * @author Miguel Angel Hernandez
 *
 */
public class SampleNewCommand extends Command<SampleNewAction, SampleResponse> {
	/**
	 * @param	action The action to perform
	 * @return	The response to the action
	 **/
	public SampleResponse execute(SampleNewAction action) throws ServerException {
		return new SampleResponse(getDispatcher().getSampleSession().save(action.getSample(), action.getMeasurement(), action.getPatient()));
	}
}
