package com.o2hlink.activ8.server.command;

import com.o2hlink.activ8.client.action.QuestionnaireNewAction;
import com.o2hlink.activ8.client.exception.ServerException;
import com.o2hlink.activ8.client.response.VoidResponse;

/**
 * @author Miguel Angel Hernandez
 **/
public class QuestionnaireNewCommand extends Command<QuestionnaireNewAction, VoidResponse> {
	/**
	 * @param	action The action to perform
	 * @return	The response to the action
	 **/
	public VoidResponse execute(QuestionnaireNewAction action) throws ServerException {
		getDispatcher().getQuestionnaireSession().save(action.getQuestionnaire());
		return new VoidResponse();
	}
}
