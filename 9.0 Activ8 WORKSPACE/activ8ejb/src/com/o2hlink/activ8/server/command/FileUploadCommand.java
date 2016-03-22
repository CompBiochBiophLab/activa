package com.o2hlink.activ8.server.command;

import com.o2hlink.activ8.client.action.FileUploadAction;
import com.o2hlink.activ8.client.exception.ServerException;
import com.o2hlink.activ8.client.response.LongResponse;

/**
 * @author Miguel Angel Hernandez
 **/
public class FileUploadCommand extends Command<FileUploadAction, LongResponse> {
	/**
	 * 
	 **/
	@Override
	public LongResponse execute(FileUploadAction action) throws ServerException {
		return new LongResponse(getDispatcher().getFileSession().save(action.getContent()));
	}
}
