package com.o2hlink.pimtools.patient;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import android.text.Html;
import android.text.Spanned;

import com.o2hlink.activ8.client.entity.Answer;
import com.o2hlink.activ8.client.entity.Array;
import com.o2hlink.activ8.client.entity.MultiAnswer;
import com.o2hlink.activ8.client.entity.NumericAnswer;
import com.o2hlink.activ8.client.entity.Question;
import com.o2hlink.activ8.client.entity.QuestionAnswer;
import com.o2hlink.activ8.client.entity.TextAnswer;
import com.o2hlink.pimtools.Activa;
import com.o2hlink.pimtools.ActivaUtil;

public class QuestionnaireSample extends com.o2hlink.activ8.client.entity.QuestionnaireSample {
	
	/**
	 * The public constructor.
	 * @param eventId
	 * @param date
	 */
	
	Long questId;
	
	public QuestionnaireSample() {
		super();
	}	
	
	public QuestionnaireSample(Long quest) {
		super();
		this.questId = quest;
	}	
	
	public QuestionnaireSample(com.o2hlink.activ8.client.entity.QuestionnaireSample sample, Long quest) {
		super();
		this.questId = quest;
		this.setDate(sample.getDate());
		this.setEvent(sample.getEvent());
		this.getAnswers().clear();
		this.getAnswers().addAll(sample.getAnswers());
	}
	
	public Spanned getQuestionnaireSpanData(String name) {
		Hashtable<Long, Answer> answerPos = new Hashtable<Long, Answer>();
		Array<Answer> answersPostemp = new Array<Answer>();
		Enumeration<Question> elements = Activa.myQuestControlManager.activeQuestions.elements();
		while (elements.hasMoreElements()) {
			Array<Answer> ans = Activa.myQuestControlManager.activeAnswers.get(elements.nextElement().getId());
			if (ans != null) answersPostemp.addAll(ans);
		}
		for (Answer answer : answersPostemp) {
			answerPos.put(answer.getId(), answer);
		}
		String spaning = "<big><b>" + Activa.myLanguageManager.PATIENTS_HISTORY_QUESTIONNAIRE + " " + name + ", " + ActivaUtil.dateToReadableString(this.getDate()) + " " + ActivaUtil.timeToReadableString(this.getDate()) + "</b></big><br/><br/>";
		for (QuestionAnswer answer : this.getAnswers()) {
			if (answer instanceof MultiAnswer) {
				String answerText = "";
				List<Long> answerIds = ((MultiAnswer)answer).getAnswers();
				for (Iterator<Long> iterator = answerIds.iterator(); iterator.hasNext();) {
					Long id = (Long) iterator.next();
					answerText += answerPos.get(id).getName();
					if (iterator.hasNext()) answerText += ", ";
				}
				spaning += "<p><b>" + Activa.myQuestControlManager.activeQuestions.get(answer.getQuestion()).getName() + "</b><br/>" + answerText + "<p>";
			}
			else if (answer instanceof NumericAnswer) {
				String answerText = String.format("%2d", ((NumericAnswer)answer).getValue());
				spaning += "<p><b>" + Activa.myQuestControlManager.activeQuestions.get(answer.getQuestion()).getName() + "</b><br/>" + answerText + "<p>";					
			}
			else if (answer instanceof TextAnswer) {
				String answerText = ((TextAnswer)answer).getValue();
				spaning += "<p><b>" + Activa.myQuestControlManager.activeQuestions.get(answer.getQuestion()).getName() + "</b><br/>" + answerText + "<p>";					
			}
		}
		return Html.fromHtml(spaning);
	}

}
