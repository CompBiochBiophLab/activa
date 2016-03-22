package com.o2hlink.activa.connection.test;

import java.util.Date;

import junit.framework.TestCase;

import com.google.gwt.user.client.rpc.IncompatibleRemoteServiceException;
import com.google.gwt.user.client.rpc.InvocationException;
import com.o2hlink.activa.Activa;
import com.o2hlink.activ8.client.action.EventListGetAction;
import com.o2hlink.activ8.client.action.MeasurementEventListGetAction;
import com.o2hlink.activ8.client.action.MessageContentGetAction;
import com.o2hlink.activ8.client.action.MessageListGetAction;
import com.o2hlink.activ8.client.action.MessageNewAction;
import com.o2hlink.activ8.client.action.MessageSentListGetAction;
import com.o2hlink.activ8.client.action.QuestionnaireAssignListGetAction;
import com.o2hlink.activ8.client.action.QuestionnaireEventListGetAction;
import com.o2hlink.activ8.client.action.SiteListGetAction;
import com.o2hlink.activ8.client.action.UserListGetAction;
import com.o2hlink.activ8.client.entity.Array;
import com.o2hlink.activ8.client.entity.Event;
import com.o2hlink.activ8.client.entity.Institution;
import com.o2hlink.activ8.client.entity.Patient;
import com.o2hlink.activ8.client.entity.Questionnaire;
import com.o2hlink.activ8.client.entity.SimpleMessage;
import com.o2hlink.activ8.client.entity.StringResponse;
import com.o2hlink.activ8.client.entity.User;
import com.o2hlink.activ8.client.exception.ServerException;
import com.o2hlink.activ8.common.entity.Measurement;
import com.o2hlink.activa.client.service.test.MockDataService;
import com.o2hlink.activa.client.service.test.MockService;
import com.o2hlink.activa.client.service.test.ResponseHandler;
import com.o2hlink.activa.connection.ProtocolManager;
import com.o2hlink.activa.data.PendingDataManager;
import com.o2hlink.activa.data.calendar.CalendarManager;
import com.o2hlink.activa.data.message.MessageManager;
import com.o2hlink.activa.data.sensor.SensorManager;
import com.o2hlink.activa.exceptions.NotUpdatedException;
import com.o2hlink.activa.map.MapManager;

import android.test.ActivityInstrumentationTestCase2;
import android.test.AndroidTestCase;
import android.test.InstrumentationTestCase;
import android.test.suitebuilder.annotation.LargeTest;
import android.test.suitebuilder.annotation.MediumTest;
import android.test.suitebuilder.annotation.SmallTest;

public class ProtocolManagerTest extends AndroidTestCase {
//	public ProtocolManagerTest() {
//		super("com.o2hlink.activ8", Activa.class);
//	}

	/**
	 * 
	 **/
	ProtocolManager protocol;
	/**
	 * 
	 **/
	private MockDataService data;

	protected void setUp() throws Exception {
		super.setUp();
		protocol = new ProtocolManager(true);
		protocol.user = new Patient();
		protocol.user.setId(0);
		data = new MockDataService();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		protocol = null;
	}
	
	@SmallTest
	public void testDispatchOk() throws NotUpdatedException {
		MockService service = new MockService();
		service.setResponseHandler(EventListGetAction.class, new ResponseHandler<EventListGetAction, Array<Event>>() {
			@Override
			public Array<Event> get(EventListGetAction action) {
				return data.createArrayEvents();
			}
		});
		protocol.service = service;
		boolean result = protocol.dispatch(new EventListGetAction());
		assertTrue(result);
	}

	@SmallTest
	public void testDispatchWrong() throws NotUpdatedException {
		MockService service = new MockService();
		service.setResponseHandler(EventListGetAction.class, new ResponseHandler<EventListGetAction, Array<Event>>() {
			@Override
			public Array<Event> get(EventListGetAction action) throws ServerException {
				throw new ServerException();
			}
		});
		protocol.service = service;
		boolean result = protocol.dispatch(new EventListGetAction());
		assertFalse(result);
	}

	@SmallTest
	public void testDispatchNotUpdated() throws NotUpdatedException {
		MockService service = new MockService();
		service.setResponseHandler(EventListGetAction.class, new ResponseHandler<EventListGetAction, Array<Event>>() {
			@Override
			public Array<Event> get(EventListGetAction action) {
				InvocationException ex = new InvocationException("Fail", new IncompatibleRemoteServiceException());
				throw ex;
			}
		});
		protocol.service = service;
		try {
			protocol.dispatch(new EventListGetAction());
		} catch (Exception e) {
			assertTrue(e instanceof NotUpdatedException);
		}
	}
	
	@MediumTest
	public void testGetMapMarksOk() throws NotUpdatedException {
		MockService service = new MockService();
		service.setResponseHandler(SiteListGetAction.class, new ResponseHandler<SiteListGetAction, Array<Institution>>() {
			@Override
			public Array<Institution> get(SiteListGetAction action) {
				return data.createArrayInstitutions();
			}
		});
		protocol.service = service;
		MapManager map = new MapManager(false);
		assertTrue(map.institutions.size() == 0);
		protocol.getMapMarks(map);
		assertTrue(map.institutions.size() == 2);
	}

	@MediumTest
	public void testGetMapMarksRewriteData() throws NotUpdatedException {
		MockService service = new MockService();
		service.setResponseHandler(SiteListGetAction.class, new ResponseHandler<SiteListGetAction, Array<Institution>>() {
			int i = 0;
			@Override
			public Array<Institution> get(SiteListGetAction action) {
				if (i == 0) {
					i++;
					return data.createArrayInstitutions();
				}
				else return data.createArrayInstitutions2();
			}
		});
		protocol.service = service;
		MapManager map = new MapManager(false);
		assertTrue(map.institutions.size() == 0);
		protocol.getMapMarks(map);
		assertTrue(map.institutions.size() == 2);
		protocol.getMapMarks(map);
		assertTrue(map.institutions.size() == 3);
	}
	
	@MediumTest
	public void testGetMapMarksWrong() throws NotUpdatedException {
		MockService service = new MockService();
		service.setResponseHandler(SiteListGetAction.class, new ResponseHandler<SiteListGetAction, Array<Institution>>() {
			@Override
			public Array<Institution> get(SiteListGetAction action) throws ServerException {
				throw new ServerException();
			}
		});
		protocol.service = service;
		MapManager map = new MapManager(false);
		assertTrue(map.institutions.size() == 0);
		boolean result = protocol.getMapMarks(map);
		assertFalse(result);
		assertTrue(map.institutions.size() == 0);
	}
	
	@MediumTest
	public void testGetMapMarksNotUpdated() {
		MockService service = new MockService();
		service.setResponseHandler(SiteListGetAction.class, new ResponseHandler<SiteListGetAction, Array<Institution>>() {
			@Override
			public Array<Institution> get(SiteListGetAction action) {
				InvocationException ex = new InvocationException("Fail", new IncompatibleRemoteServiceException());
				throw ex;
			}
		});
		protocol.service = service;
		MapManager map = new MapManager(false);
		assertTrue(map.institutions.size() == 0);
		try {
			protocol.getMapMarks(map);
		} catch (Exception e) {
			assertTrue(e instanceof NotUpdatedException);
		}
		assertTrue(map.institutions.size() == 0);
	}
	
	public void testGetContactsOk() throws NotUpdatedException {
		MockService service = new MockService();
		service.setResponseHandler(UserListGetAction.class, new ResponseHandler<UserListGetAction, Array<User>>() {
			@Override
			public Array<User> get(UserListGetAction action) {
				return data.createArrayUsers();
			}
		});
		protocol.service = service;
		MessageManager messages = new MessageManager(false);
		assertTrue(messages.contactList.size() == 0);
		protocol.getContacts(messages);
		assertTrue(messages.contactList.size() == 2);
	}

	@MediumTest
	public void testGetContactsRewriteData() throws NotUpdatedException {
		MockService service = new MockService();
		service.setResponseHandler(UserListGetAction.class, new ResponseHandler<UserListGetAction, Array<User>>() {
			int i = 0;
			@Override
			public Array<User> get(UserListGetAction action) {
				if (i == 0) {
					i++;
					return data.createArrayUsers();
				}
				else return data.createArrayUsers2();
			}
		});
		protocol.service = service;
		MessageManager messages = new MessageManager(false);
		assertTrue(messages.contactList.size() == 0);
		protocol.getContacts(messages);
		assertTrue(messages.contactList.size() == 2);
		protocol.getContacts(messages);
		assertTrue(messages.contactList.size() == 3);
	}
	
	@MediumTest
	public void testGetContactsWrong() throws NotUpdatedException {
		MockService service = new MockService();
		service.setResponseHandler(UserListGetAction.class, new ResponseHandler<UserListGetAction, Array<User>>() {
			@Override
			public Array<User> get(UserListGetAction action) throws ServerException {
				throw new ServerException();
			}
		});
		protocol.service = service;
		MessageManager messages = new MessageManager(false);
		assertTrue(messages.contactList.size() == 0);
		boolean result = protocol.getContacts(messages);
		assertFalse(result);
		assertTrue(messages.contactList.size() == 0);
	}
	
	@MediumTest
	public void testGetContactsNotUpdated() {
		MockService service = new MockService();
		service.setResponseHandler(UserListGetAction.class, new ResponseHandler<UserListGetAction, Array<User>>() {
			@Override
			public Array<User> get(UserListGetAction action) throws ServerException {
				InvocationException ex = new InvocationException("Fail", new IncompatibleRemoteServiceException());
				throw ex;
			}
		});
		protocol.service = service;
		MessageManager messages = new MessageManager(false);
		assertTrue(messages.contactList.size() == 0);
		try {
			protocol.getContacts(messages);
		} catch (Exception e) {
			assertTrue(e instanceof NotUpdatedException);
		}
		assertTrue(messages.contactList.size() == 0);
	}
	
	public void testGetReceivedMessagesOk() throws NotUpdatedException {
		MockService service = new MockService();
		service.setResponseHandler(MessageListGetAction.class, new ResponseHandler<MessageListGetAction, Array<SimpleMessage>>() {
			@Override
			public Array<SimpleMessage> get(MessageListGetAction action) {
				return data.createArrayMessages();
			}
		});
		protocol.service = service;
		MessageManager messages = new MessageManager(false);
		assertTrue(messages.messagesReceived.size() == 0);
		protocol.getReceivedMessages(messages);
		assertTrue(messages.messagesReceived.size() == 2);
	}

	@MediumTest
	public void testReceivedMessagesRewriteData() throws NotUpdatedException {
		MockService service = new MockService();
		service.setResponseHandler(MessageListGetAction.class, new ResponseHandler<MessageListGetAction, Array<SimpleMessage>>() {
			int i = 0;
			@Override
			public Array<SimpleMessage> get(MessageListGetAction action) {
				if (i == 0) {
					i++;
					return data.createArrayMessages();
				}
				else return data.createArrayMessages2();
			}
		});
		protocol.service = service;
		MessageManager messages = new MessageManager(false);
		assertTrue(messages.messagesReceived.size() == 0);
		protocol.getReceivedMessages(messages);
		assertTrue(messages.messagesReceived.size() == 2);
		protocol.getReceivedMessages(messages);
		assertTrue(messages.messagesReceived.size() == 3);
	}
	
	@MediumTest
	public void testReceivedMessagesWrong() throws NotUpdatedException {
		MockService service = new MockService();
		service.setResponseHandler(MessageListGetAction.class, new ResponseHandler<MessageListGetAction, Array<SimpleMessage>>() {
			@Override
			public Array<SimpleMessage> get(MessageListGetAction action) throws ServerException {
				throw new ServerException();
			}
		});
		protocol.service = service;
		MessageManager messages = new MessageManager(false);
		assertTrue(messages.messagesReceived.size() == 0);
		boolean result = protocol.getReceivedMessages(messages);
		assertFalse(result);
		assertTrue(messages.messagesReceived.size() == 0);
	}
	
	@MediumTest
	public void testReceivedMessagesNotUpdated() {
		MockService service = new MockService();
		service.setResponseHandler(MessageListGetAction.class, new ResponseHandler<MessageListGetAction, Array<SimpleMessage>>() {
			@Override
			public Array<SimpleMessage> get(MessageListGetAction action) throws ServerException {
				InvocationException ex = new InvocationException("Fail", new IncompatibleRemoteServiceException());
				throw ex;
			}
		});
		protocol.service = service;
		MessageManager messages = new MessageManager(false);
		assertTrue(messages.messagesReceived.size() == 0);
		try {
			protocol.getReceivedMessages(messages);
		} catch (Exception e) {
			assertTrue(e instanceof NotUpdatedException);
		}
		assertTrue(messages.messagesReceived.size() == 0);
	}
	
	public void testGetSentMessagesOk() throws NotUpdatedException {
		MockService service = new MockService();
		service.setResponseHandler(MessageSentListGetAction.class, new ResponseHandler<MessageSentListGetAction, Array<SimpleMessage>>() {
			@Override
			public Array<SimpleMessage> get(MessageSentListGetAction action) {
				return data.createArrayMessages();
			}
		});
		protocol.service = service;
		MessageManager messages = new MessageManager(false);
		assertTrue(messages.messagesSent.size() == 0);
		protocol.getSentMessages(messages);
		assertTrue(messages.messagesSent.size() == 2);
	}

	@MediumTest
	public void testSentMessagesRewriteData() throws NotUpdatedException {
		MockService service = new MockService();
		service.setResponseHandler(MessageSentListGetAction.class, new ResponseHandler<MessageSentListGetAction, Array<SimpleMessage>>() {
			int i = 0;
			@Override
			public Array<SimpleMessage> get(MessageSentListGetAction action) {
				if (i == 0) {
					i++;
					return data.createArrayMessages();
				}
				else return data.createArrayMessages2();
			}
		});
		protocol.service = service;
		MessageManager messages = new MessageManager(false);
		assertTrue(messages.messagesSent.size() == 0);
		protocol.getSentMessages(messages);
		assertTrue(messages.messagesSent.size() == 2);
		protocol.getSentMessages(messages);
		assertTrue(messages.messagesSent.size() == 3);
	}
	
	@MediumTest
	public void testSentMessagesWrong() throws NotUpdatedException {
		MockService service = new MockService();
		service.setResponseHandler(MessageSentListGetAction.class, new ResponseHandler<MessageSentListGetAction, Array<SimpleMessage>>() {
			@Override
			public Array<SimpleMessage> get(MessageSentListGetAction action) throws ServerException {
				throw new ServerException();
			}
		});
		protocol.service = service;
		MessageManager messages = new MessageManager(false);
		assertTrue(messages.messagesSent.size() == 0);
		boolean result = protocol.getSentMessages(messages);
		assertFalse(result);
		assertTrue(messages.messagesSent.size() == 0);
	}
	
	@MediumTest
	public void testSentMessagesNotUpdated() {
		MockService service = new MockService();
		service.setResponseHandler(MessageSentListGetAction.class, new ResponseHandler<MessageSentListGetAction, Array<SimpleMessage>>() {
			@Override
			public Array<SimpleMessage> get(MessageSentListGetAction action) throws ServerException {
				InvocationException ex = new InvocationException("Fail", new IncompatibleRemoteServiceException());
				throw ex;
			}
		});
		protocol.service = service;
		MessageManager messages = new MessageManager(false);
		assertTrue(messages.messagesSent.size() == 0);
		try {
			protocol.getSentMessages(messages);
		} catch (Exception e) {
			assertTrue(e instanceof NotUpdatedException);
		}
		assertTrue(messages.messagesSent.size() == 0);
	}
	
	@MediumTest
	public void testGetMessageOk() throws NotUpdatedException {
		MockService service = new MockService();
		service.setResponseHandler(MessageContentGetAction.class, new ResponseHandler<MessageContentGetAction, StringResponse>() {
			@Override
			public StringResponse get(MessageContentGetAction action) throws ServerException {
				return data.getString();
			}
		});
		protocol.service = service;
		SimpleMessage message = new SimpleMessage();
		String result = protocol.getMessage(message);
		assertTrue(result.equals("Test"));
	}
	
	@MediumTest
	public void testGetMessageWrong() throws NotUpdatedException {
		MockService service = new MockService();
		service.setResponseHandler(MessageContentGetAction.class, new ResponseHandler<MessageContentGetAction, StringResponse>() {
			@Override
			public StringResponse get(MessageContentGetAction action) throws ServerException {
				throw new ServerException();
			}
		});
		protocol.service = service;
		SimpleMessage message = new SimpleMessage();
		String result = protocol.getMessage(message);
		assertTrue(result == null);
	}
	
	@MediumTest
	public void testGetMessageNotUpdated() {
		MockService service = new MockService();
		service.setResponseHandler(MessageContentGetAction.class, new ResponseHandler<MessageContentGetAction, StringResponse>() {
			@Override
			public StringResponse get(MessageContentGetAction action) throws ServerException {
				InvocationException ex = new InvocationException("Fail", new IncompatibleRemoteServiceException());
				throw ex;
			}
		});
		protocol.service = service;
		SimpleMessage message = new SimpleMessage();
		try {
			protocol.getMessage(message);
			throw new Exception();
		} catch (Exception e) {
			assertTrue(e instanceof NotUpdatedException);
		}
	}
	
	@MediumTest
	public void testSendO2MessageOk () throws NotUpdatedException {
		MockService service = new MockService();
		service.setResponseHandler(MessageNewAction.class, new ResponseHandler<MessageNewAction, SimpleMessage>() {
			@Override
			public SimpleMessage get(MessageNewAction action) throws ServerException {
				return data.createMessage();
			}
		});
		protocol.service = service;
		MessageManager messages = new MessageManager(true);
		PendingDataManager pending = new PendingDataManager(true);
		assertTrue(messages.messagesSent.size() == 0);
		boolean result = protocol.sendO2Message(messages, pending, data.createMessage(), "Test", false);
		assertTrue(result);
		assertTrue(messages.messagesSent.size() == 1);
		assertTrue(messages.messagesSent.containsKey("0"));
	}
	
	@MediumTest
	public void testSendO2MessageWrong () throws NotUpdatedException {
		MockService service = new MockService();
		service.setResponseHandler(MessageNewAction.class, new ResponseHandler<MessageNewAction, SimpleMessage>() {
			@Override
			public SimpleMessage get(MessageNewAction action) throws ServerException {
				throw new ServerException();
			}
		});
		protocol.service = service;
		MessageManager messages = new MessageManager(true);
		PendingDataManager pending = new PendingDataManager(true);
		assertTrue(messages.messagesSent.size() == 0);
		assertTrue(pending.pendingMessages.size() == 0);
		boolean result = protocol.sendO2Message(messages, pending, data.createMessage(), "Test", false);
		assertFalse(result);
		assertTrue(pending.pendingMessages.size() == 1);
		assertTrue(messages.messagesSent.size() == 1);
	}
	
	@MediumTest
	public void testSendO2MessageNotUpdated () {
		MockService service = new MockService();
		service.setResponseHandler(MessageNewAction.class, new ResponseHandler<MessageNewAction, SimpleMessage>() {
			@Override
			public SimpleMessage get(MessageNewAction action) throws ServerException {
				InvocationException ex = new InvocationException("Fail", new IncompatibleRemoteServiceException());
				throw ex;
			}
		});
		protocol.service = service;
		MessageManager messages = new MessageManager(true);
		PendingDataManager pending = new PendingDataManager(true);
		assertTrue(messages.messagesSent.size() == 0);
		try {
			protocol.sendO2Message(messages, pending, data.createMessage(), "Test", false);
			throw new Exception();
		} catch (Exception e) {
			assertTrue(e instanceof NotUpdatedException);
			e.printStackTrace();
		}
		assertTrue(pending.pendingMessages.size() == 1);
		assertTrue(messages.messagesSent.size() == 1);
	}
	
	@LargeTest
	public void testGetCalendarOk() {
		//TODO
	}
	
	@LargeTest
	public void testGetCalendarWrong() {
		//TODO
	}
	
	@LargeTest
	public void testGetCalendarNotUpdated() {
		//TODO
	}
	
	@MediumTest
	public void testGetNonMeasuringEventsOk() throws NotUpdatedException {
		MockService service = new MockService();
		service.setResponseHandler(EventListGetAction.class, new ResponseHandler<EventListGetAction, Array<Event>>() {
			@Override
			public Array<Event> get(EventListGetAction action) {
				return data.createArrayEvents();
			}
		});
		protocol.service = service;
		CalendarManager calendar = new CalendarManager(true);
		assertTrue(calendar.events.size() == 0);
		protocol.getNonMeasuringEvents(calendar, new Date(0), new Date());
		assertTrue(calendar.events.size() == 3);
		assertTrue(calendar.events.get("0").type == 2);
		assertTrue(calendar.events.get("1").type == 2);
		assertTrue(calendar.events.get("2").type == 2);
		assertTrue(calendar.events.get("0").state == 0);
		assertTrue(calendar.events.get("1").state == 0);
		assertTrue(calendar.events.get("2").state == 2);
		assertTrue(calendar.events.get("0").date.equals(new Date(110, 4, 30, 0, 0)));
		assertTrue(calendar.events.get("1").date.equals(new Date(110, 4, 30, 2, 0)));
		assertTrue(calendar.events.get("0").dateEnd.equals(new Date(110, 4, 30, 1, 0)));
		assertTrue(calendar.events.get("1").dateEnd.equals(new Date(110, 4, 30, 3, 0)));
	}
	
	@MediumTest
	public void testGetNonMeasuringEventsRewriteData() throws NotUpdatedException {
		MockService service = new MockService();
		service.setResponseHandler(EventListGetAction.class, new ResponseHandler<EventListGetAction, Array<Event>>() {
			@Override
			public Array<Event> get(EventListGetAction action) {
				if (action.getStart().getTime() >= 86400000l) return data.createArrayEvents();
				else return data.createArrayEvents2();
			}
		});
		protocol.service = service;
		CalendarManager calendar = new CalendarManager(true);
		assertTrue(calendar.events.size() == 0);
		protocol.getNonMeasuringEvents(calendar, new Date(), new Date());
		assertTrue(calendar.events.size() == 3);
		protocol.getNonMeasuringEvents(calendar, new Date(0), new Date());
		assertTrue(calendar.events.size() == 5);
	}
	
	@MediumTest
	public void testGetNonMeasuringEventsDataNotErased() throws NotUpdatedException {
		MockService service = new MockService();
		service.setResponseHandler(EventListGetAction.class, new ResponseHandler<EventListGetAction, Array<Event>>() {
			@Override
			public Array<Event> get(EventListGetAction action) {
				if (action.getStart().getTime() >= 86400000l) return data.createArrayEvents();
				else return data.createArrayEvents2();
			}
		});
		protocol.service = service;
		CalendarManager calendar = new CalendarManager(true);
		assertTrue(calendar.events.size() == 0);
		protocol.getNonMeasuringEvents(calendar, new Date(0), new Date());
		assertTrue(calendar.events.size() == 5);
		protocol.getNonMeasuringEvents(calendar, new Date(), new Date());
		assertTrue(calendar.events.size() == 5);
	}
	
	@MediumTest
	public void testGetNonMeasuringEventsWrong() throws NotUpdatedException {
		MockService service = new MockService();
		service.setResponseHandler(EventListGetAction.class, new ResponseHandler<EventListGetAction, Array<Event>>() {
			@Override
			public Array<Event> get(EventListGetAction action) throws ServerException {
				throw new ServerException();
			}
		});
		protocol.service = service;
		CalendarManager calendar = new CalendarManager(true);
		assertTrue(calendar.events.size() == 0);
		boolean result = protocol.getNonMeasuringEvents(calendar, new Date(0), new Date());
		assertFalse(result);
		assertTrue(calendar.events.size() == 0);
	}
	
	@MediumTest
	public void testGetNonMeasuringEventsNotUpdated() throws NotUpdatedException {
		MockService service = new MockService();
		service.setResponseHandler(EventListGetAction.class, new ResponseHandler<EventListGetAction, Array<Event>>() {
			@Override
			public Array<Event> get(EventListGetAction action) throws ServerException {
				InvocationException ex = new InvocationException("Fail", new IncompatibleRemoteServiceException());
				throw ex;
			}
		});
		protocol.service = service;
		CalendarManager calendar = new CalendarManager(true);
		assertTrue(calendar.events.size() == 0);
		try {
			protocol.getNonMeasuringEvents(calendar, new Date(0), new Date());
		} catch (Exception e) {
			assertTrue(e instanceof NotUpdatedException);
		}
		assertTrue(calendar.events.size() == 0);
	}

	@MediumTest
	public void testGetMeasuringEventsOk() throws NotUpdatedException {
		MockService service = new MockService();
		service.setResponseHandler(MeasurementEventListGetAction.class, new ResponseHandler<MeasurementEventListGetAction, Array<Event>>() {
			@Override
			public Array<Event> get(MeasurementEventListGetAction action) {
				if (action.getMeasurement().equals(Measurement.PULSEOXYMETRY))
					return data.createArrayEvents();
				else if (action.getMeasurement().equals(Measurement.SPIROMETRY))
					return data.createArrayEventsAlternative1();
				else if (action.getMeasurement().equals(Measurement.SIX_MINUTES_WALK))
					return data.createArrayEventsAlternative2();
				else if (action.getMeasurement().equals(Measurement.EXERCISE))
					return data.createArrayEventsAlternative3();
				else if (action.getMeasurement().equals(Measurement.WEIGHT_HEIGHT))
					return data.createArrayEventsAlternative4();
				else
					return data.createArrayEvents2();
			}
		});
		protocol.service = service;
		CalendarManager calendar = new CalendarManager(true);
		assertTrue(calendar.events.size() == 0);
		protocol.getMeasuringEvents(calendar, new Date(0), new Date());
		assertTrue(calendar.events.size() == 7);
		assertTrue(calendar.events.get("0").type == 0);
		assertTrue(calendar.events.get("1").type == 0);
		assertTrue(calendar.events.get("2").type == 0);
		assertTrue(calendar.events.get("5").type == 0);
		assertTrue(calendar.events.get("6").type == 0);
		assertTrue(calendar.events.get("7").type == 0);
		assertTrue(calendar.events.get("8").type == 0);
		assertTrue(calendar.events.get("0").subtype == SensorManager.ID_PULSIOXYMETER);
		assertTrue(calendar.events.get("1").subtype == SensorManager.ID_PULSIOXYMETER);
		assertTrue(calendar.events.get("2").subtype == SensorManager.ID_PULSIOXYMETER);
		assertTrue(calendar.events.get("5").subtype == SensorManager.ID_SPIROMETER);
		assertTrue(calendar.events.get("6").subtype == SensorManager.ID_SIXMINUTES);
		assertTrue(calendar.events.get("7").subtype == SensorManager.ID_EXERCISE);
		assertTrue(calendar.events.get("8").subtype == SensorManager.ID_WEIGHT);
		assertTrue(calendar.events.get("0").state == 0);
		assertTrue(calendar.events.get("1").state == 1);
		assertTrue(calendar.events.get("2").state == 2);
		assertTrue(calendar.events.get("5").state == 0);
		assertTrue(calendar.events.get("6").state == 1);
		assertTrue(calendar.events.get("7").state == 2);
		assertTrue(calendar.events.get("8").state == 2);
		assertTrue(calendar.events.get("0").date.equals(new Date(110, 4, 30, 0, 0)));
		assertTrue(calendar.events.get("1").date.equals(new Date(110, 4, 30, 2, 0)));
		assertTrue(calendar.events.get("5").date.equals(new Date(110, 4, 30, 0, 0)));
		assertTrue(calendar.events.get("6").date.equals(new Date(110, 4, 30, 0, 0)));
		assertTrue(calendar.events.get("0").dateEnd.equals(new Date(110, 4, 30, 1, 0)));
		assertTrue(calendar.events.get("1").dateEnd.equals(new Date(110, 4, 30, 3, 0)));
		assertTrue(calendar.events.get("5").dateEnd.equals(new Date(110, 4, 30, 1, 0)));
		assertTrue(calendar.events.get("6").dateEnd.equals(new Date(110, 4, 30, 1, 0)));
	}

	@MediumTest
	public void testGetMeasuringEventsRewriteData() throws NotUpdatedException {
		MockService service = new MockService();
		service.setResponseHandler(MeasurementEventListGetAction.class, new ResponseHandler<MeasurementEventListGetAction, Array<Event>>() {
			@Override
			public Array<Event> get(MeasurementEventListGetAction action) {
				if (action.getMeasurement().equals(Measurement.PULSEOXYMETRY))
					if (action.getStart().getTime() >= 86400000l) return data.createArrayEvents();
					else return data.createArrayEvents2();
				else if (action.getMeasurement().equals(Measurement.SPIROMETRY))
					return data.createArrayEventsAlternative1();
				else if (action.getMeasurement().equals(Measurement.SIX_MINUTES_WALK))
					return data.createArrayEventsAlternative2();
				else if (action.getMeasurement().equals(Measurement.EXERCISE))
					return data.createArrayEventsAlternative3();
				else if (action.getMeasurement().equals(Measurement.WEIGHT_HEIGHT))
					return data.createArrayEventsAlternative4();
				else
					return data.createArrayEvents2();
			}
		});
		protocol.service = service;
		CalendarManager calendar = new CalendarManager(true);
		assertTrue(calendar.events.size() == 0);
		protocol.getMeasuringEvents(calendar, new Date(), new Date());
		assertTrue(calendar.events.size() == 7);
		protocol.getMeasuringEvents(calendar, new Date(0), new Date());
		assertTrue(calendar.events.size() == 9);
	}

	@MediumTest
	public void testGetMeasuringEventsDataNotErasing() throws NotUpdatedException {
		MockService service = new MockService();
		service.setResponseHandler(MeasurementEventListGetAction.class, new ResponseHandler<MeasurementEventListGetAction, Array<Event>>() {
			@Override
			public Array<Event> get(MeasurementEventListGetAction action) {
				if (action.getMeasurement().equals(Measurement.PULSEOXYMETRY))
					if (action.getStart().getTime() >= 86400000l) return data.createArrayEvents();
					else return data.createArrayEvents2();
				else if (action.getMeasurement().equals(Measurement.SPIROMETRY))
					return data.createArrayEventsAlternative1();
				else if (action.getMeasurement().equals(Measurement.SIX_MINUTES_WALK))
					return data.createArrayEventsAlternative2();
				else if (action.getMeasurement().equals(Measurement.EXERCISE))
					return data.createArrayEventsAlternative3();
				else if (action.getMeasurement().equals(Measurement.WEIGHT_HEIGHT))
					return data.createArrayEventsAlternative4();
				else
					return data.createArrayEvents2();
			}
		});
		protocol.service = service;
		CalendarManager calendar = new CalendarManager(true);
		assertTrue(calendar.events.size() == 0);
		protocol.getMeasuringEvents(calendar, new Date(0), new Date());
		assertTrue(calendar.events.size() == 9);
		protocol.getMeasuringEvents(calendar, new Date(), new Date());
		assertTrue(calendar.events.size() == 9);
	}

	@MediumTest
	public void testGetMeasuringEventsWrong() throws NotUpdatedException {
		MockService service = new MockService();
		service.setResponseHandler(MeasurementEventListGetAction.class, new ResponseHandler<MeasurementEventListGetAction, Array<Event>>() {
			@Override
			public Array<Event> get(MeasurementEventListGetAction action) throws ServerException {
				throw new ServerException();
			}
		});
		protocol.service = service;
		CalendarManager calendar = new CalendarManager(true);
		assertTrue(calendar.events.size() == 0);
		boolean result = protocol.getMeasuringEvents(calendar, new Date(0), new Date());
		assertFalse(result);
		assertTrue(calendar.events.size() == 0);
	}

	@MediumTest
	public void testGetMeasuringEventsNotUpdated() throws NotUpdatedException {
		MockService service = new MockService();
		service.setResponseHandler(MeasurementEventListGetAction.class, new ResponseHandler<MeasurementEventListGetAction, Array<Event>>() {
			@Override
			public Array<Event> get(MeasurementEventListGetAction action) throws ServerException {
				InvocationException ex = new InvocationException("Fail", new IncompatibleRemoteServiceException());
				throw ex;
			}
		});
		protocol.service = service;
		CalendarManager calendar = new CalendarManager(true);
		assertTrue(calendar.events.size() == 0);
		try {
			protocol.getMeasuringEvents(calendar, new Date(0), new Date());
			throw new Exception();
		} catch (Exception e) {
			assertTrue(e instanceof NotUpdatedException);
		}
		assertTrue(calendar.events.size() == 0);
	}

	@MediumTest
	public void testGetQuestEventsOk() throws NotUpdatedException {
		MockService service = new MockService();
		service.setResponseHandler(QuestionnaireEventListGetAction.class, new ResponseHandler<QuestionnaireEventListGetAction, Array<Event>>() {
			@Override
			public Array<Event> get(QuestionnaireEventListGetAction action) {
				if (action.getQuestionnaire().equals(1l))
					return data.createArrayEvents();
				else if (action.getQuestionnaire().equals(2l))
					return data.createArrayEventsAlternative1();
				else
					return data.createArrayEventsAlternative2();
			}
		});
		service.setResponseHandler(QuestionnaireAssignListGetAction.class, new ResponseHandler<QuestionnaireAssignListGetAction, Array<Questionnaire>>() {
			@Override
			public Array<Questionnaire> get(QuestionnaireAssignListGetAction action) {
				return data.createArrayQuestionnaires();
			}
		});
		protocol.service = service;
		CalendarManager calendar = new CalendarManager(true);
		assertTrue(calendar.events.size() == 0);
		protocol.getQuestEvents(calendar, new Date(0), new Date());
		assertTrue(calendar.events.size() == 4);
		assertTrue(calendar.events.get("0").type == 1);
		assertTrue(calendar.events.get("1").type == 1);
		assertTrue(calendar.events.get("2").type == 1);
		assertTrue(calendar.events.get("5").type == 1);
		assertTrue(calendar.events.get("0").subtype == 1);
		assertTrue(calendar.events.get("1").subtype == 1);
		assertTrue(calendar.events.get("2").subtype == 1);
		assertTrue(calendar.events.get("5").subtype == 2);
		assertTrue(calendar.events.get("0").state == 0);
		assertTrue(calendar.events.get("1").state == 1);
		assertTrue(calendar.events.get("2").state == 2);
		assertTrue(calendar.events.get("5").state == 0);
		assertTrue(calendar.events.get("0").date.equals(new Date(110, 4, 30, 0, 0)));
		assertTrue(calendar.events.get("1").date.equals(new Date(110, 4, 30, 2, 0)));
		assertTrue(calendar.events.get("5").date.equals(new Date(110, 4, 30, 0, 0)));
		assertTrue(calendar.events.get("0").dateEnd.equals(new Date(110, 4, 30, 1, 0)));
		assertTrue(calendar.events.get("1").dateEnd.equals(new Date(110, 4, 30, 3, 0)));
		assertTrue(calendar.events.get("5").dateEnd.equals(new Date(110, 4, 30, 1, 0)));
	}

	@MediumTest
	public void testGetQuestEventsRewriteData() throws NotUpdatedException {
		MockService service = new MockService();
		service.setResponseHandler(QuestionnaireEventListGetAction.class, new ResponseHandler<QuestionnaireEventListGetAction, Array<Event>>() {
			@Override
			public Array<Event> get(QuestionnaireEventListGetAction action) {
				if (action.getQuestionnaire().equals(1l))
					if (action.getStart().getTime() >= 86400000l) return data.createArrayEvents();
					else return data.createArrayEvents2();
				else if (action.getQuestionnaire().equals(2l))
					return data.createArrayEventsAlternative1();
				else
					return data.createArrayEventsAlternative2();
			}
		});
		service.setResponseHandler(QuestionnaireAssignListGetAction.class, new ResponseHandler<QuestionnaireAssignListGetAction, Array<Questionnaire>>() {
			@Override
			public Array<Questionnaire> get(QuestionnaireAssignListGetAction action) {
				return data.createArrayQuestionnaires();
			}
		});
		protocol.service = service;
		CalendarManager calendar = new CalendarManager(true);
		assertTrue(calendar.events.size() == 0);
		protocol.getQuestEvents(calendar, new Date(), new Date());
		assertTrue(calendar.events.size() == 4);
		protocol.getQuestEvents(calendar, new Date(0), new Date());
		assertTrue(calendar.events.size() == 6);
	}

	@MediumTest
	public void testGetQuestEventsDataNotErasing() throws NotUpdatedException {
		MockService service = new MockService();
		service.setResponseHandler(QuestionnaireEventListGetAction.class, new ResponseHandler<QuestionnaireEventListGetAction, Array<Event>>() {
			@Override
			public Array<Event> get(QuestionnaireEventListGetAction action) {
				if (action.getQuestionnaire().equals(1l))
					if (action.getStart().getTime() >= 86400000l) return data.createArrayEvents();
					else return data.createArrayEvents2();
				else if (action.getQuestionnaire().equals(2l))
					return data.createArrayEventsAlternative1();
				else
					return data.createArrayEventsAlternative2();
			}
		});
		service.setResponseHandler(QuestionnaireAssignListGetAction.class, new ResponseHandler<QuestionnaireAssignListGetAction, Array<Questionnaire>>() {
			@Override
			public Array<Questionnaire> get(QuestionnaireAssignListGetAction action) {
				return data.createArrayQuestionnaires();
			}
		});
		protocol.service = service;
		CalendarManager calendar = new CalendarManager(true);
		assertTrue(calendar.events.size() == 0);
		protocol.getQuestEvents(calendar, new Date(0), new Date());
		assertTrue(calendar.events.size() == 6);
		protocol.getQuestEvents(calendar, new Date(), new Date());
		assertTrue(calendar.events.size() == 6);
	}

	@MediumTest
	public void testGetQuestEventsWrong() throws NotUpdatedException {
		MockService service = new MockService();
		service.setResponseHandler(QuestionnaireEventListGetAction.class, new ResponseHandler<QuestionnaireEventListGetAction, Array<Event>>() {
			@Override
			public Array<Event> get(QuestionnaireEventListGetAction action) throws ServerException {
				throw new ServerException();
			}
		});
		service.setResponseHandler(QuestionnaireAssignListGetAction.class, new ResponseHandler<QuestionnaireAssignListGetAction, Array<Questionnaire>>() {
			@Override
			public Array<Questionnaire> get(QuestionnaireAssignListGetAction action) {
				return data.createArrayQuestionnaires();
			}
		});
		protocol.service = service;
		CalendarManager calendar = new CalendarManager(true);
		assertTrue(calendar.events.size() == 0);
		boolean result = protocol.getQuestEvents(calendar, new Date(0), new Date());
		assertFalse(result);
		assertTrue(calendar.events.size() == 0);
	}

	@MediumTest
	public void testGetQuestEventsNotUpdated() throws NotUpdatedException {
		MockService service = new MockService();
		service.setResponseHandler(QuestionnaireEventListGetAction.class, new ResponseHandler<QuestionnaireEventListGetAction, Array<Event>>() {
			@Override
			public Array<Event> get(QuestionnaireEventListGetAction action) throws ServerException {
				throw new ServerException();
			}
		});
		service.setResponseHandler(QuestionnaireAssignListGetAction.class, new ResponseHandler<QuestionnaireAssignListGetAction, Array<Questionnaire>>() {
			@Override
			public Array<Questionnaire> get(QuestionnaireAssignListGetAction action) {
				return data.createArrayQuestionnaires();
			}
		});
		protocol.service = service;
		CalendarManager calendar = new CalendarManager(true);
		assertTrue(calendar.events.size() == 0);
		boolean result = protocol.getQuestEvents(calendar, new Date(0), new Date());
		assertFalse(result);
		assertTrue(calendar.events.size() == 0);
	}

}
