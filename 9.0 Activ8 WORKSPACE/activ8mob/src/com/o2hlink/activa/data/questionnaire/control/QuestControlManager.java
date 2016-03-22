package com.o2hlink.activa.data.questionnaire.control;

import java.util.Hashtable;

import com.o2hlink.activ8.client.entity.Questionnaire;
import com.o2hlink.activa.Activa;
import com.o2hlink.activa.patient.PatientManager;

public class QuestControlManager {

	private static QuestControlManager instance;
	
	public Hashtable<Long, Questionnaire> questionnaires;
	
	private QuestControlManager () {
		this.questionnaires = new Hashtable<Long, Questionnaire>();
	}
	
	public static QuestControlManager getInstance() {
		if (instance == null) instance = new QuestControlManager();
		return instance;
	}
	
	public static void QuestControlManager() {
		instance = null;
	}
	
	public void getQuestionnaires() {
		if (Activa.myMobileManager.user.getType() != 1) return;
//		else Activa.myProtocolManager.getQuestionnairesForClinician();
	}
	
}
