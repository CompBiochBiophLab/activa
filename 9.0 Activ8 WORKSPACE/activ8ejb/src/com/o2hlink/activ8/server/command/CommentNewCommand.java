package com.o2hlink.activ8.server.command;

import com.o2hlink.activ8.client.action.CommentNewAction;
import com.o2hlink.activ8.client.exception.ServerException;
import com.o2hlink.activ8.client.response.CommentResponse;

/**
 * @author Miguel Angel Hernandez
 **/
public class CommentNewCommand extends Command<CommentNewAction, CommentResponse> {
	/**
	 * 
	 **/
	@Override
	public CommentResponse execute(CommentNewAction action) throws ServerException {
		return new CommentResponse(getDispatcher().getCommentSession().addComment(action.getProvider(), action.getComment()));
	}
}
