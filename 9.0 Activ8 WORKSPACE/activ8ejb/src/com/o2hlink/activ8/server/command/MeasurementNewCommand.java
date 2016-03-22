/**
 * 
 */
package com.o2hlink.activ8.server.command;

import com.o2hlink.activ8.client.action.MeasurementNewAction;
import com.o2hlink.activ8.client.exception.ServerException;
import com.o2hlink.activ8.client.response.MeasurementResponse;

/**
 * @author Miguel Angel Hernandez
 **/
public class MeasurementNewCommand extends Command<MeasurementNewAction, MeasurementResponse> {
	/**
	 * 
	 **/
	public MeasurementResponse execute(MeasurementNewAction action) throws ServerException {
		return new MeasurementResponse(getDispatcher().getMeasurementSession().save(action.getMeasurement()));
	}
}
