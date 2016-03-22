package com.o2hlink.activa.client.service.test;

import java.util.Date;

import com.o2hlink.activ8.client.entity.Account;
import com.o2hlink.activ8.client.entity.Array;
import com.o2hlink.activ8.client.entity.Clinician;
import com.o2hlink.activ8.client.entity.Contact;
import com.o2hlink.activ8.client.entity.Dataset;
import com.o2hlink.activ8.client.entity.Disease;
import com.o2hlink.activ8.client.entity.Document;
import com.o2hlink.activ8.client.entity.Event;
import com.o2hlink.activ8.client.entity.Folder;
import com.o2hlink.activ8.client.entity.HistoryRecord;
import com.o2hlink.activ8.client.entity.Institution;
import com.o2hlink.activ8.client.entity.Patient;
import com.o2hlink.activ8.client.entity.Questionnaire;
import com.o2hlink.activ8.client.entity.Researcher;
import com.o2hlink.activ8.client.entity.SimpleMessage;
import com.o2hlink.activ8.client.entity.StringResponse;
import com.o2hlink.activ8.client.entity.User;
import com.o2hlink.activ8.client.entity.Video;
import com.o2hlink.activ8.client.entity.Webservice;
import com.o2hlink.activ8.common.entity.AccountType;

/**
 * Provides mock information
 **/
public class MockDataService {
//FIELDS
	/**
	 * 
	 **/
	private Long id = 0L;
//METHODS
	/**
	 * Get the user object
	 **/
	@SuppressWarnings("serial")
	public User getUser(){
		return new User() {
			public long getId(){
				return 1L;
			}
			@SuppressWarnings("unused")
			public String getUserName(){
				return "mock";
			}
			public String getFirstName(){
				return "Mock";
			}
		};
	}
	
	/**
	 * Events functions
	 */

	public Array<Event> createArrayEvents() {
		Array<Event> events = new Array<Event>();
		Event event = new Event();
		event.setId("0");
		event.setStart(new Date(110, 4, 30, 0, 0));
		event.setEnd(new Date(event.getStart().getTime() + 3600000));
		event.setName("Event 0");
		event.setDone(true);
		events.add(event);
		event = new Event();
		event.setId("1");
		event.setStart(new Date(110, 4, 30, 2, 0));
		event.setEnd(new Date(event.getStart().getTime() + 3600000));
		event.setName("Event 1");
		event.setDone(false);
		events.add(event);
		event = new Event();
		event.setId("2");
		event.setStart(new Date());
		event.setEnd(new Date(event.getStart().getTime() + 3600000));
		event.setName("Event 2");
		event.setDone(false);
		events.add(event);
		return events;
	}

	public Array<Event> createArrayEvents2() {
		Array<Event> events = new Array<Event>();
		Event event = new Event();
		event.setId("0");
		event.setStart(new Date(110, 4, 30, 0, 0));
		event.setEnd(new Date(event.getStart().getTime() + 3600000));
		event.setName("Event 0");
		event.setDone(true);
		events.add(event);
		event = new Event();
		event.setId("1");
		event.setStart(new Date(110, 4, 30, 2, 0));
		event.setEnd(new Date(event.getStart().getTime() + 3600000));
		event.setName("Event 1");
		event.setDone(false);
		events.add(event);
		event = new Event();
		event.setId("2");
		event.setStart(new Date());
		event.setEnd(new Date(event.getStart().getTime() + 3600000));
		event.setName("Event 2");
		event.setDone(false);
		events.add(event);
		event = new Event();
		event.setId("3");
		event.setStart(new Date(113, 5, 30, 2, 0));
		event.setEnd(new Date(event.getStart().getTime() + 3600000));
		event.setName("Event 3");
		event.setDone(false);
		events.add(event);
		event = new Event();
		event.setId("4");
		event.setStart(new Date(110, 6, 30, 2, 0));
		event.setEnd(new Date(event.getStart().getTime() + 3600000));
		event.setName("Event 4");
		event.setDone(false);
		events.add(event);
		return events;
	}
	
	public Array<Event> createArrayEventsAlternative1() {
		Array<Event> events = new Array<Event>();
		Event event = new Event();
		event.setId("5");
		event.setStart(new Date(110, 4, 30, 0, 0));
		event.setEnd(new Date(event.getStart().getTime() + 3600000));
		event.setName("Event 5");
		event.setDone(true);
		events.add(event);
		return events;
	}
	
	public Array<Event> createArrayEventsAlternative2() {
		Array<Event> events = new Array<Event>();
		Event event = new Event();
		event.setId("6");
		event.setStart(new Date(110, 4, 30, 0, 0));
		event.setEnd(new Date(event.getStart().getTime() + 3600000));
		event.setName("Event 6");
		event.setDone(false);
		events.add(event);
		return events;
	}
	
	public Array<Event> createArrayEventsAlternative3() {
		Array<Event> events = new Array<Event>();
		Event event = new Event();
		event.setId("7");
		event.setStart(new Date(System.currentTimeMillis() + 604800000));
		event.setEnd(new Date(event.getStart().getTime() + 3600000));
		event.setName("Event 7");
		event.setDone(false);
		events.add(event);
		return events;
	}
	
	public Array<Event> createArrayEventsAlternative4() {
		Array<Event> events = new Array<Event>();
		Event event = new Event();
		event.setId("8");
		event.setStart(new Date(System.currentTimeMillis() - 1800000));
		event.setEnd(new Date(event.getStart().getTime() + 3600000));
		event.setName("Event 8");
		event.setDone(false);
		events.add(event);
		return events;
	}

	/**
	 * Institution functions
	 */
	
	public Array<Institution> createArrayInstitutions() {
		Array<Institution> institutions = new Array<Institution>();
		Institution inst = new Institution();
		inst.setId(0l);
		inst.setName("Institution 1");
		inst.setLatitude(0d);
		inst.setLongitude(0d);
		institutions.add(inst);
		inst = new Institution();
		inst.setId(1l);
		inst.setName("Institution 2");
		inst.setLatitude(0d);
		inst.setLongitude(0d);
		institutions.add(inst);
		return institutions;
	}
	
	public Array<Institution> createArrayInstitutions2() {
		Array<Institution> institutions = new Array<Institution>();
		Institution inst = new Institution();
		inst.setId(0l);
		inst.setName("Institution 1");
		inst.setLatitude(0d);
		inst.setLongitude(0d);
		institutions.add(inst);
		inst = new Institution();
		inst.setId(1l);
		inst.setName("Institution 2");
		inst.setLatitude(0d);
		inst.setLongitude(0d);
		institutions.add(inst);
		inst = new Institution();
		inst.setId(2l);
		inst.setName("Institution 3");
		inst.setLatitude(0d);
		inst.setLongitude(0d);
		institutions.add(inst);
		return institutions;
	}
	
	/**
	 * Functions for generating contacts
	 */
	public Array<User> createArrayUsers() {
		Array<User> users = new Array<User>();
		User user = new Patient();
		user.setId(0l);
		user.setUsername("patient1");
		users.add(user);
		user = new Clinician();
		user.setId(1l);
		user.setUsername("clinician1");
		users.add(user);
		return users;
	}

	public Array<User> createArrayUsers2() {
		Array<User> users = new Array<User>();
		User user = new Patient();
		user.setId(0l);
		user.setUsername("patient1");
		users.add(user);
		user = new Clinician();
		user.setId(1l);
		user.setUsername("clinician1");
		users.add(user);
		user = new Researcher();
		user.setId(2l);
		user.setUsername("researcher1");
		users.add(user);
		return users;
	}
	
	/**
	 * Functions for generating messages
	 */
	public Array<SimpleMessage> createArrayMessages() {
		Array<SimpleMessage> msgs = new Array<SimpleMessage>();
		SimpleMessage msg = new SimpleMessage();
		msg.setId("0");
		msg.setDate(new Date());
		msg.setSender("1");
		msg.setSubject("Test");
		msg.getReceivers().add("2");
		msg.getReceivers().add("3");
		msg.getReceivers().add("4");
		msgs.add(msg);
		msg = new SimpleMessage();
		msg.setId("1");
		msg.setDate(new Date());
		msg.setSender("1");
		msg.setSubject("Test 2");
		msg.getReceivers().add("2");
		msgs.add(msg);
		return msgs;
	}

	public Array<SimpleMessage> createArrayMessages2() {
		Array<SimpleMessage> msgs = new Array<SimpleMessage>();
		SimpleMessage msg = new SimpleMessage();
		msg.setId("0");
		msg.setDate(new Date());
		msg.setSender("1");
		msg.setSubject("Test");
		msg.getReceivers().add("2");
		msg.getReceivers().add("3");
		msg.getReceivers().add("4");
		msgs.add(msg);
		msg = new SimpleMessage();
		msg.setId("1");
		msg.setDate(new Date());
		msg.setSender("1");
		msg.setSubject("Test 2");
		msg.getReceivers().add("2");
		msgs.add(msg);
		msg = new SimpleMessage();
		msg.setId("2");
		msg.setDate(new Date());
		msg.setSender("1");
		msg.setSubject("Test 3");
		msg.getReceivers().add("3");
		msgs.add(msg);
		return msgs;
	}
	
	public SimpleMessage createMessage() {
		SimpleMessage message = new SimpleMessage();
		message.setId("0");
		message.setDate(new Date());
		message.setSender("0");
		message.setSubject("Test");
		message.getReceivers().add("1");
		return message;
	}
	
	/**
	 * Functions for generating questionnaire responses
	 */
	public Array<Questionnaire> createArrayQuestionnaires () {
		Array<Questionnaire> quests = new Array<Questionnaire>();
		Questionnaire quest = new Questionnaire();
		quest.setId(1l);
		quest.setFirst(0l);
		quest.setName("Test 1");
		quest.setDescription("This is a test");
		quests.add(quest);
		quest = new Questionnaire();
		quest.setId(2l);
		quest.setFirst(10l);
		quest.setName("Test 2");
		quest.setDescription("This is a test");
		quests.add(quest);
		return quests;
	}
	
	/**
	 * Functions for generating string responses
	 */
	public StringResponse getString() {
		return new StringResponse("Test");
	}
	
}
