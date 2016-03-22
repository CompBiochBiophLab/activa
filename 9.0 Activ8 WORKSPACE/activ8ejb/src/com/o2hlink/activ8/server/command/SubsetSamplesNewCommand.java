/**
 * 
 */
package com.o2hlink.activ8.server.command;

import com.o2hlink.activ8.client.action.SubsetSamplesNewAction;
import com.o2hlink.activ8.client.exception.ServerException;
import com.o2hlink.activ8.client.response.VoidResponse;

/**
 * @author Miguel Angel Hernandez
 **/
public class SubsetSamplesNewCommand extends Command<SubsetSamplesNewAction, VoidResponse> {
	/**
	 * 
	 **/
	public VoidResponse execute(SubsetSamplesNewAction action) throws ServerException {
		return null;
	}
}
