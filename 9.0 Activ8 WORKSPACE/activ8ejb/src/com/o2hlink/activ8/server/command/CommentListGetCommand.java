package com.o2hlink.activ8.server.command;

import com.o2hlink.activ8.client.action.CommentListGetAction;
import com.o2hlink.activ8.client.exception.ServerException;
import com.o2hlink.activ8.client.response.CommentListResponse;

/**
 * @author Miguel Angel Hernandez
 **/
public class CommentListGetCommand extends Command<CommentListGetAction, CommentListResponse> {
	/**
	 * 
	 **/
	@Override
	public CommentListResponse execute(CommentListGetAction action) throws ServerException {
		return new CommentListResponse(getDispatcher().getCommentSession().getComments(action.getProvider()));
	}

}
