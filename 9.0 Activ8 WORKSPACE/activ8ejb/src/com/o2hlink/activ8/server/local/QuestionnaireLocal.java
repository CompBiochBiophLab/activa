package com.o2hlink.activ8.server.local;

import javax.ejb.Local;

import com.o2hlink.activ8.client.entity.Questionnaire;

/**
 * @author Miguel Angel Hernandez
 **/
@Local
public interface QuestionnaireLocal {
	/**
	 * Persist a questionnaire
	 **/
	public Questionnaire save(Questionnaire questionnaire);
	/**
	 * Persist a questionnaire
	 **/
	public void remove(Questionnaire questionnaire);
}
