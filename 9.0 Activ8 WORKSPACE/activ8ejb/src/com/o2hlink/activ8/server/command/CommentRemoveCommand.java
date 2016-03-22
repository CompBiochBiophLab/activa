package com.o2hlink.activ8.server.command;

import com.o2hlink.activ8.client.action.CommentRemoveAction;
import com.o2hlink.activ8.client.exception.ServerException;
import com.o2hlink.activ8.client.response.VoidResponse;

/**
 * @author Miguel Angel Hernandez
 **/
public class CommentRemoveCommand extends Command<CommentRemoveAction, VoidResponse> {
	/**
	 * 
	 **/
	@Override
	public VoidResponse execute(CommentRemoveAction action) throws ServerException {
		getDispatcher().getCommentSession().removeComment(action.getProvider(), action.getComment());
		return new VoidResponse();
	}
}
