package com.o2hlink.zonated.data.questionnaire;

import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

import com.o2hlink.activ8.client.entity.Answer;
import com.o2hlink.activ8.client.entity.Array;
import com.o2hlink.activ8.client.entity.Condition;
import com.o2hlink.activ8.client.entity.Contact;
import com.o2hlink.activ8.client.entity.MultiAnswer;
import com.o2hlink.activ8.client.entity.MultiCondition;
import com.o2hlink.activ8.client.entity.MultiQuestion;
import com.o2hlink.activ8.client.entity.NumericAnswer;
import com.o2hlink.activ8.client.entity.NumericCondition;
import com.o2hlink.activ8.client.entity.NumericQuestion;
import com.o2hlink.activ8.client.entity.QuestionAnswer;
import com.o2hlink.activ8.client.entity.Questionnaire;
import com.o2hlink.activ8.client.entity.Question;
import com.o2hlink.activ8.client.entity.QuestionnaireSample;
import com.o2hlink.activ8.client.entity.TextAnswer;
import com.o2hlink.zonated.Zonated;
import com.o2hlink.zonated.ZonatedUtil;
import com.o2hlink.zonated.exceptions.NotUpdatedException;

public class QuestControlManager {

	private static QuestControlManager instance;
	
	public Hashtable<Long, Questionnaire> questionnaires;
	
	public Hashtable<Long, Questionnaire> createdQuest;
	
	public Hashtable<Long, Question> activeQuestions;
	
	public Hashtable<Long, Array<Answer>> activeAnswers;
	
	public Hashtable<Long, Array<Condition>> activeConditions;
	
	public Stack<QuestionAnswer> activeAnswersAnswered;
	
	public Stack<Question> activeQuestionsAnswered;
	
	public Questionnaire activeQuestionnaire;
	
	public long lastQuestionId;
	
	private QuestControlManager () {
		this.activeQuestionnaire = null;
		this.questionnaires = new Hashtable<Long, Questionnaire>();
		this.createdQuest = new Hashtable<Long, Questionnaire>();
		this.activeQuestions = new Hashtable<Long, Question>();
		this.activeAnswers = new Hashtable<Long, Array<Answer>>();
		this.activeConditions = new Hashtable<Long, Array<Condition>>();
		this.activeAnswersAnswered = new Stack<QuestionAnswer>();
		this.activeQuestionsAnswered = new Stack<Question>();
	}
	
	public static QuestControlManager getInstance() {
		if (instance == null) instance = new QuestControlManager();
		return instance;
	}
	
	public static void freeQuestControlManager() {
		instance = null;
	}
	
	public void getQuestionnaires() {
		try {
			Zonated.myProtocolManager.getQuestList();
			Zonated.myProtocolManager.getAssignedQuestList();
		} catch (NotUpdatedException e) {
			Zonated.myUIManager.UImisc.loadTextOnWindow(Zonated.myLanguageManager.TEXT_UPDATEVERSION);
			e.printStackTrace();
		}
	}
	
	public void startQuestionnaire(Questionnaire quest) {
		this.activeQuestionnaire = quest;
		this.activeAnswersAnswered = new Stack<QuestionAnswer>();
		this.activeQuestionsAnswered = new Stack<Question>();
		Zonated.myUIManager.UIquest.loadQuestion(this.activeQuestions.get(this.activeQuestionnaire.getFirst()));
	}
	
	public void finishQuestionnaire() {
		double weight = this.calcTotalWeight(this.activeQuestionnaire.getId());
		Zonated.myUIManager.UIquest.loadQuestResult(weight);
	}
	
	public Long calcNextQuestion (long question, MultiAnswer answer) {
		Long next = this.activeQuestions.get(question).getNext();
		if (answer.getAnswers().size() != 1) return next;
		Long answerId = answer.getAnswers().get(0);
		Array<Condition> conditions = this.activeConditions.get(question);
		for (int i = 0; i < conditions.size(); i++) {
			MultiCondition condition = (MultiCondition) conditions.get(i);
			ArrayList<Long> conditionAnsIds = new ArrayList<Long>();
			for (Iterator<Answer> iterator = condition.getAnswers().iterator(); iterator.hasNext();) {
				conditionAnsIds.add(iterator.next().getId());
			}
			if (conditionAnsIds.contains(answerId)) {
				next = condition.getNext();
				return next;
			}
		}
		return next;
	}
	
	public Long calcMultiNextQuestion (long question, MultiAnswer answer) {
		Long next = this.activeQuestions.get(question).getNext();
		Array<Condition> conditions = this.activeConditions.get(question);
		for (int i = 0; i < conditions.size(); i++) {
			MultiCondition condition = (MultiCondition) conditions.get(i);
			ArrayList<Long> conditionAnsIds = new ArrayList<Long>();
			for (Iterator<Answer> iterator = condition.getAnswers().iterator(); iterator.hasNext();) {
				conditionAnsIds.add(iterator.next().getId());
			}
			if (conditionAnsIds.containsAll(answer.getAnswers())) {
				conditionAnsIds.removeAll(answer.getAnswers());
				if (conditionAnsIds.isEmpty()) {
					next = condition.getNext();
					return next;
				}
			}
		}
		return next;
	}
	
	public Long calcNumericNextQuestion (long question, NumericAnswer answer) {
		Long next = this.activeQuestions.get(question).getNext();
		Array<Condition> conditions = this.activeConditions.get(question);
		if (conditions == null) return next;
		for (int i = 0; i < conditions.size(); i++) {
			NumericCondition condition = (NumericCondition) conditions.get(i);
			if (answer.getValue() >= condition.getThreshold()) {
				next = condition.getNext();
				return next;
			}
		}
		return next;
	}
	
	private double calcMaxWeight(long questionnaire) {
		double maxWeight = 0;
		Enumeration<Question> allquestions = this.activeQuestions.elements();
		while (allquestions.hasMoreElements()) {
			double contribution = 0;
			Question question = allquestions.nextElement();
			if (question instanceof MultiQuestion) {
				if (!((MultiQuestion)question).isMultiple()) {
					Iterator<Answer> allanswers = this.activeAnswers.get(question.getId()).iterator();
					while (allanswers.hasNext()) {
						Answer answer = allanswers.next();
						if (contribution < answer.getValue()) contribution = answer.getValue();
					}
				}
				else {
					Iterator<Answer> allanswers = this.activeAnswers.get(question.getId()).iterator();
					while (allanswers.hasNext()) {
						Answer answer = allanswers.next();
						contribution += answer.getValue();
					}
				}
			}
			else if (question instanceof NumericQuestion) {
					contribution = 10d;
			}
			maxWeight += contribution;
		}
		return maxWeight;
	}
	
	private double calcTotalWeight(long questionnaire) {
		double totalWeight = 0;
		double avgWeight;
		double maxWeight = calcMaxWeight(questionnaire);
//		if (maxWeight == 0.0d) return -1.0f;
		Enumeration<QuestionAnswer> allAnswers = this.activeAnswersAnswered.elements();
		while (allAnswers.hasMoreElements()) {
			QuestionAnswer answers = allAnswers.nextElement();
			if (answers instanceof MultiAnswer) {
				Iterator<Long> it = ((MultiAnswer)answers).getAnswers().iterator();
				while (it.hasNext()) {
					Long answerId = it.next();
					for (Answer answer:this.activeAnswers.get(answers.getQuestion())) {
						if (answer.getId().equals(answerId)) {
							totalWeight += answer.getValue();
							break;
						}
					}
				}
			}
			if (answers instanceof NumericAnswer) {
				totalWeight += ((NumericAnswer)answers).getValue();
			}
		}
		if (maxWeight != 0.0f)
			avgWeight = ((totalWeight)/(maxWeight))*100;
		else 
			avgWeight = -1.0f;
		return avgWeight;
	}
	
	public QuestionnaireSample getQuestToSend() {
		QuestionnaireSample sample = new QuestionnaireSample();
		ArrayList<QuestionAnswer> answers = new ArrayList<QuestionAnswer>();
		QuestionAnswer answer = null;
		while (!this.activeAnswersAnswered.empty()) {
			answer = this.activeAnswersAnswered.remove(0);
			answers.add(answer);
		}
		sample.getAnswers().clear();
		sample.getAnswers().addAll(answers);
		sample.setDate(new Date());
		sample.setEvent(null);
		return sample;
	}
	
	public boolean validateQuestionnaire() {
		if (this.activeQuestionnaire.getFirst() == null) return false;
		if (!this.activeQuestions.keySet().contains(this.activeQuestionnaire.getFirst())) return false;
		Enumeration<Question> enumeration = this.activeQuestions.elements();
		while (enumeration.hasMoreElements()) {
			Question question = enumeration.nextElement();
			if (question == null) {
				return false;
			}
			Long next = question.getNext();
			if (next == null) break;
			else if (!this.activeQuestions.containsKey(next)) {
				return false;
			}
			if (question instanceof MultiQuestion) {
				Iterator<Condition> it = this.activeConditions.get(question.getId()).iterator();
				while (it.hasNext()) {
					Condition condition = it.next();
					if (next == null) break;
					else if(!this.activeQuestions.containsKey(condition.getNext())) {
							return false;
					}
				}
			}
		}
		return true;
	}
	
	public Long searchQuestionByName(String name) {
		Long questId = null;
		if (name.equals("-")) return questId;
		Enumeration<Question> questions = this.activeQuestions.elements();
		while (questions.hasMoreElements()) {
			Question question = questions.nextElement();
			if (question.getName().equals(name)) return question.getId();
		}
		return questId;
	}
	
	public static String getSensorSampleForPending(Long questId, QuestionnaireSample sample) {
		String returned = "<QUEST ID=\"" + questId;
		returned += "\" DATE=\"" + ZonatedUtil.dateToXMLDate(sample.getDate());
		returned += "\" EVENT=\"";
		if (sample.getEvent() != null) {
			returned +=  sample.getEvent() + "\">";		
		}
		else returned += "\">";
		for (QuestionAnswer answer : sample.getAnswers()) {
			if (answer instanceof MultiAnswer) {
				returned += "<MULTIANSWER QUEST=\"" + ((MultiAnswer)answer).getQuestion() + "\"";
				returned += "VALUE=\"";
				Iterator<Long> it = ((MultiAnswer)answer).getAnswers().iterator();
				while (it.hasNext()) {
					returned += it.next();
					if (it.hasNext()) returned += ",";
				}
				returned += "\"/>";
			}
			else if (answer instanceof NumericAnswer) {
				returned += "<NUMERICANSWER QUEST=\"" + ((NumericAnswer)answer).getQuestion() + "\"";
				returned += "VALUE=\"" + ((NumericAnswer)answer).getValue() + "\"/>";
			}
			else if (answer instanceof TextAnswer) {
				returned += "<TEXTANSWER QUEST=\"" + ((TextAnswer)answer).getQuestion() + "\"";
				returned += "VALUE=\"" + ((TextAnswer)answer).getValue() + "\"/>";
			}
		}
		returned += "</QUEST>";
		return returned;
	}

	public boolean getAssignedQuestList() {
		boolean result;
		try {
			result = Zonated.myProtocolManager.getAssignedQuestList();
		} catch (NotUpdatedException e) {
			Zonated.myUIManager.UImisc.loadTextOnWindow(Zonated.myLanguageManager.TEXT_UPDATEVERSION);
			e.printStackTrace();
			result = false;
		}
		return result;
	}

	public boolean getQuestionnaire(long id) {
		boolean result;
		try {
			result = Zonated.myProtocolManager.getQuestionnaire(id);
		} catch (NotUpdatedException e) {
			Zonated.myUIManager.UImisc.loadTextOnWindow(Zonated.myLanguageManager.TEXT_UPDATEVERSION);
			e.printStackTrace();
			result = false;
		}
		return result;
	}

	public Array<Questionnaire> SearchQuests(String query) {
		Array<Questionnaire> result;
		try {
			result = Zonated.myProtocolManager.SearchQuests(query);
		} catch (NotUpdatedException e) {
			Zonated.myUIManager.UImisc.loadTextOnWindow(Zonated.myLanguageManager.TEXT_UPDATEVERSION);
			e.printStackTrace();
			result = null;
		}
		return result;
	}

	public boolean shareQuestionnaire(ArrayList<Long> users, Questionnaire questionnaire) {
		boolean result;
		try {
			result = Zonated.myProtocolManager.shareQuestionnaire(users, questionnaire);
		} catch (NotUpdatedException e) {
			Zonated.myUIManager.UImisc.loadTextOnWindow(Zonated.myLanguageManager.TEXT_UPDATEVERSION);
			e.printStackTrace();
			result = false;
		}
		return result;
	}

	public boolean removeQuestionnaire(Questionnaire quest) {
		boolean result;
		try {
			result = Zonated.myProtocolManager.removeQuestionnaire(quest);
		} catch (NotUpdatedException e) {
			Zonated.myUIManager.UImisc.loadTextOnWindow(Zonated.myLanguageManager.TEXT_UPDATEVERSION);
			e.printStackTrace();
			result = false;
		}
		return result;
	}

	public boolean createQuestionnaire(String quest) {
		boolean result;
		try {
			result = Zonated.myProtocolManager.createQuestionnaire(quest);
		} catch (NotUpdatedException e) {
			Zonated.myUIManager.UImisc.loadTextOnWindow(Zonated.myLanguageManager.TEXT_UPDATEVERSION);
			e.printStackTrace();
			result = false;
		}
		return result;
	}

	public boolean publishQuestionnaire(Questionnaire quest) {
		boolean result;
		try {
			result = Zonated.myProtocolManager.publishQuestionnaire(quest);
		} catch (NotUpdatedException e) {
			Zonated.myUIManager.UImisc.loadTextOnWindow(Zonated.myLanguageManager.TEXT_UPDATEVERSION);
			e.printStackTrace();
			result = false;
		}
		return result;
	}

	public boolean removeQuestion(Question quest) {
		boolean result;
		try {
			result = Zonated.myProtocolManager.removeQuestion(quest);
		} catch (NotUpdatedException e) {
			Zonated.myUIManager.UImisc.loadTextOnWindow(Zonated.myLanguageManager.TEXT_UPDATEVERSION);
			e.printStackTrace();
			result = false;
		}
		return result;
	}

	public boolean updateQuestionnaire(Questionnaire questionnaire) {
		boolean result;
		try {
			result = Zonated.myProtocolManager.updateQuestionnaire(questionnaire);
		} catch (NotUpdatedException e) {
			Zonated.myUIManager.UImisc.loadTextOnWindow(Zonated.myLanguageManager.TEXT_UPDATEVERSION);
			e.printStackTrace();
			result = false;
		}
		return result;
	}

	public Question addQuestion(Question question) {
		Question result;
		try {
			result = Zonated.myProtocolManager.addQuestion(question);
		} catch (NotUpdatedException e) {
			Zonated.myUIManager.UImisc.loadTextOnWindow(Zonated.myLanguageManager.TEXT_UPDATEVERSION);
			e.printStackTrace();
			result = null;
		}
		return result;
	}

	public boolean updateQuestion(Question prevQest) {
		boolean result;
		try {
			result = Zonated.myProtocolManager.updateQuestion(prevQest);
		} catch (NotUpdatedException e) {
			Zonated.myUIManager.UImisc.loadTextOnWindow(Zonated.myLanguageManager.TEXT_UPDATEVERSION);
			e.printStackTrace();
			result = false;
		}
		return result;
	}

	public boolean removeAnswer(Long id, Answer answer) {
		boolean result;
		try {
			result = Zonated.myProtocolManager.removeAnswer(id, answer);
		} catch (NotUpdatedException e) {
			Zonated.myUIManager.UImisc.loadTextOnWindow(Zonated.myLanguageManager.TEXT_UPDATEVERSION);
			e.printStackTrace();
			result = false;
		}
		return result;
	}

	public boolean removeCondition(Long id, Condition condition) {
		boolean result;
		try {
			result = Zonated.myProtocolManager.removeCondition(id, condition);
		} catch (NotUpdatedException e) {
			Zonated.myUIManager.UImisc.loadTextOnWindow(Zonated.myLanguageManager.TEXT_UPDATEVERSION);
			e.printStackTrace();
			result = false;
		}
		return result;
	}

	public boolean addAnswer(Long id, Answer answer) {
		boolean result;
		try {
			result = Zonated.myProtocolManager.addAnswer(id, answer);
		} catch (NotUpdatedException e) {
			Zonated.myUIManager.UImisc.loadTextOnWindow(Zonated.myLanguageManager.TEXT_UPDATEVERSION);
			e.printStackTrace();
			result = false;
		}
		return result;
	}

	public boolean updateAnswer(Long id, Answer answer) {
		boolean result;
		try {
			result = Zonated.myProtocolManager.updateAnswer(id, answer);
		} catch (NotUpdatedException e) {
			Zonated.myUIManager.UImisc.loadTextOnWindow(Zonated.myLanguageManager.TEXT_UPDATEVERSION);
			e.printStackTrace();
			result = false;
		}
		return result;
	}

	public boolean addCondition(Long id, Condition condition) {
		boolean result;
		try {
			result = Zonated.myProtocolManager.addCondition(id, condition);
		} catch (NotUpdatedException e) {
			Zonated.myUIManager.UImisc.loadTextOnWindow(Zonated.myLanguageManager.TEXT_UPDATEVERSION);
			e.printStackTrace();
			result = false;
		}
		return result;
	}

	public boolean updateCondition(Long id, Condition condition) {
		boolean result;
		try {
			result = Zonated.myProtocolManager.updateCondition(id, condition);
		} catch (NotUpdatedException e) {
			Zonated.myUIManager.UImisc.loadTextOnWindow(Zonated.myLanguageManager.TEXT_UPDATEVERSION);
			e.printStackTrace();
			result = false;
		}
		return result;
	}

	public boolean sendQuestionnaire(Long questionnaire, QuestionnaireSample sample) {
		boolean result;
		try {
			result = Zonated.myProtocolManager.sendQuestionnaire(questionnaire, sample);
		} catch (NotUpdatedException e) {
			Zonated.myUIManager.UImisc.loadTextOnWindow(Zonated.myLanguageManager.TEXT_UPDATEVERSION);
			e.printStackTrace();
			result = false;
		}
		return result;
	}

	public QuestGlobalResult getQuestionnaireGlobalResult(Long questionnaire) {
		QuestGlobalResult result = new QuestGlobalResult();
		Array<QuestionnaireSample> results = new Array<QuestionnaireSample>();
		if (getQuestionnaire(questionnaire)) {
			try {
				Enumeration<Contact> contacts = Zonated.myContactsManager.contactList.elements();
				while (contacts.hasMoreElements()) {
					Contact contact = contacts.nextElement();
					String name = contact.getFirstName() + " " + contact.getLastName();
					Long userId = Long.parseLong(contact.getId());
					QuestionnaireSample sample = Zonated.myProtocolManager.getQuestionnaireSample(questionnaire, userId);
					if (sample != null) results.add(sample);
				}
				if (results.size() == 0) result = null;
				else {
					result.people = results.size();
					for (QuestionnaireSample sample : results) {
						for (QuestionAnswer answer : sample.getAnswers()) {
							if (!result.results.containsKey(answer.getQuestion())) result.results.put(answer.getQuestion(), new QuestionResult());
							if (answer instanceof MultiAnswer) {
								result.results.get(answer.getQuestion()).type = 0;
								for (Long ans : ((MultiAnswer) answer).getAnswers()) {
									if (!result.results.get(answer.getQuestion()).singleanswers.containsKey(ans)) 
										result.results.get(answer.getQuestion()).singleanswers.put(ans, 1);
									else {
										int prev = result.results.get(answer.getQuestion()).singleanswers.get(ans);
										result.results.get(answer.getQuestion()).singleanswers.put(ans, prev + 1);
								
									}
								}
								result.results.get(answer.getQuestion()).singlepeople++;
							}
							else if (answer instanceof NumericAnswer) {
								result.results.get(answer.getQuestion()).type = 1;	
								Double avrg = result.results.get(answer.getQuestion()).numavrg;
								Integer people = result.results.get(answer.getQuestion()).numpeople;
								Double newAvrg = ((avrg*people) + ((NumericAnswer)answer).getValue())/(people + 1);
								result.results.get(answer.getQuestion()).numavrg = newAvrg;
								result.results.get(answer.getQuestion()).numpeople++;
								if (((NumericAnswer)answer).getValue() < 5)result.results.get(answer.getQuestion()).numlessof5++;
								else result.results.get(answer.getQuestion()).num5ormore++;
							}
							else if (answer instanceof TextAnswer) {
								result.results.get(answer.getQuestion()).type = 2;
								result.results.get(answer.getQuestion()).textanswers.add(((TextAnswer)answer).getValue());
								result.results.get(answer.getQuestion()).textpeople++;
							}
						}
					}
				}
			} catch (NotUpdatedException e) {
				Zonated.myUIManager.UImisc.loadTextOnWindow(Zonated.myLanguageManager.TEXT_UPDATEVERSION);
				e.printStackTrace();
				result = null;
			}
		} else result = null;
		return result;
	}

	public QuestGlobalResult getQuestionnaireGlobalResult(Array<QuestionnaireSample> results) {
		QuestGlobalResult result = new QuestGlobalResult();
		if (results.size() == 0) result = null;
		else {
			result.people = results.size();
			for (QuestionnaireSample sample : results) {
				for (QuestionAnswer answer : sample.getAnswers()) {
				if (!result.results.containsKey(answer.getQuestion())) result.results.put(answer.getQuestion(), new QuestionResult());
					if (answer instanceof MultiAnswer) {
						result.results.get(answer.getQuestion()).type = 0;
						for (Long ans : ((MultiAnswer) answer).getAnswers()) {
							if (!result.results.get(answer.getQuestion()).singleanswers.containsKey(ans)) 
								result.results.get(answer.getQuestion()).singleanswers.put(ans, 1);
							else {
								int prev = result.results.get(answer.getQuestion()).singleanswers.get(ans);
								result.results.get(answer.getQuestion()).singleanswers.put(ans, prev + 1);
							}
						}
						result.results.get(answer.getQuestion()).singlepeople++;
					}
					else if (answer instanceof NumericAnswer) {
						result.results.get(answer.getQuestion()).type = 1;	
						Double avrg = result.results.get(answer.getQuestion()).numavrg;
						Integer people = result.results.get(answer.getQuestion()).numpeople;
						Double newAvrg = ((avrg*people) + ((NumericAnswer)answer).getValue())/(people + 1);
						result.results.get(answer.getQuestion()).numavrg = newAvrg;
						result.results.get(answer.getQuestion()).numpeople++;
						if (((NumericAnswer)answer).getValue() < 5)result.results.get(answer.getQuestion()).numlessof5++;
						else result.results.get(answer.getQuestion()).num5ormore++;
					}
					else if (answer instanceof TextAnswer) {
						result.results.get(answer.getQuestion()).type = 2;
						result.results.get(answer.getQuestion()).textanswers.add(((TextAnswer)answer).getValue());
						result.results.get(answer.getQuestion()).textpeople++;
					}
				}
			}
		}
		return result;
	}
	
}
