/**
 * 
 */
package com.o2hlink.activ8.server.command;

import com.o2hlink.activ8.client.action.MeasurementListGetAction;
import com.o2hlink.activ8.client.exception.ServerException;
import com.o2hlink.activ8.client.response.MeasurementListResponse;

/**
 * @author Miguel Angel Hernandez
 *
 */
public class MeasurementsGetCommand extends Command<MeasurementListGetAction, MeasurementListResponse> {
	/**
	 * 
	 **/
	public MeasurementListResponse execute(MeasurementListGetAction action) throws ServerException {
		return new MeasurementListResponse(getDispatcher().getMeasurementSession().getMeasurements());
	}
}
