package com.o2hlink.pimtools.data.message;

import java.util.Hashtable;

import com.o2hlink.activ8.client.entity.Account;
import com.o2hlink.activ8.client.entity.Array;
import com.o2hlink.activ8.client.entity.Contact;
import com.o2hlink.activ8.client.entity.ContactContactRequest;
import com.o2hlink.activ8.client.entity.SimpleMessage;
import com.o2hlink.activ8.client.entity.User;
import com.o2hlink.activ8.common.entity.AccountType;
import com.o2hlink.pimtools.Activa;
import com.o2hlink.pimtools.exceptions.NotUpdatedException;

/**
 * @author Adrian Rejas <P>
 * 
 * This class will deal with the receiving and sending of message by the user.
 */
public class MessageManager {

	/**
	 * Instance of the manager
	 */
	private static MessageManager instance;
	
	/**
	 * List of received messages.
	 */
	public Hashtable<String, SimpleMessage> messagesReceived;
	
	/**
	 * List of sent messages.
	 */
	public Hashtable<String, SimpleMessage> messagesSent;
	
	/**
	 * List of contacts used for services.
	 */
	public Hashtable<String, Contact> contactList;
	
	/**
	 * List of entrying contacts used for services.
	 */
	public Array<ContactContactRequest> entryContactList;
	
	/**
	 * List of synchronized accounts
	 */
	public Hashtable<Long, Account> accountList;
	
//	/**
//	 * List of real contacts.
//	 */
//	public Hashtable<Long, Con> realContactList;
	
	/**
	 * Message showing at the moment
	 */
	public SimpleMessage currentMessage;
	
	/**
	 * Message content showing at the moment
	 */
	public String currentContent;
	/**
	 * Private basic constructor
	 */
	private MessageManager() {
		this.messagesReceived = new Hashtable<String, SimpleMessage>();
		this.messagesSent = new Hashtable<String, SimpleMessage>();
		this.contactList = new Hashtable<String, Contact>();
		this.entryContactList = new Array<ContactContactRequest>();
		this.accountList = new Hashtable<Long, Account>();
		this.currentMessage = null;
		this.currentContent = null;
	}
	
	/**
	 * Public basic constructor for testing purposes
	 */
	public MessageManager(boolean set) {
		this.messagesReceived = new Hashtable<String, SimpleMessage>();
		this.messagesSent = new Hashtable<String, SimpleMessage>();
		this.contactList = new Hashtable<String, Contact>();
		this.accountList = new Hashtable<Long, Account>();
		this.entryContactList = new Array<ContactContactRequest>();
		this.currentMessage = null;
		this.currentContent = null;
	}
	
	/**
	 * Public method for getting instance
	 * 
	 * @return
	 */
	public static MessageManager getInstance() {
		if (MessageManager.instance == null) MessageManager.instance = new MessageManager();
		return MessageManager.instance;
	}
	
	/**
	 * Method for freeing the instance of the manager.
	 */
	public static void freeInstance() {
		MessageManager.instance = null;
	}
	
	/**
	 * Method for requesting to main server the received and sent messages
	 */
	public void requestReceivedMessages(int first) {
		try {
			this.messagesReceived = new Hashtable<String, SimpleMessage>();
			Activa.myProtocolManager.getReceivedMessages(this, first);
		} catch (NotUpdatedException e) {
			Activa.myUIManager.loadTextOnWindow(Activa.myLanguageManager.TEXT_UPDATEVERSION);
			e.printStackTrace();
		}
	}
	
	public void requestSentMessages(int first) {
		try {
			this.messagesSent = new Hashtable<String, SimpleMessage>();
			Activa.myProtocolManager.getSentMessages(this, first);
		} catch (NotUpdatedException e) {
			Activa.myUIManager.loadTextOnWindow(Activa.myLanguageManager.TEXT_UPDATEVERSION);
			e.printStackTrace();
		}
	}
	
	/**
	 * Method for requesting to main server the list of available contacts
	 */
	public void requestContactList() {
		this.contactList = new Hashtable<String, Contact>();
		try {
			Activa.myProtocolManager.getContacts(this);
		} catch (NotUpdatedException e) {
			Activa.myUIManager.loadTextOnWindow(Activa.myLanguageManager.TEXT_UPDATEVERSION);
			e.printStackTrace();
		}
	}

	public void requestEntryContactList() {
		this.entryContactList = new Array<ContactContactRequest>();
		try {
			Activa.myProtocolManager.getEntryContacts(this);
		} catch (NotUpdatedException e) {
			Activa.myUIManager.loadTextOnWindow(Activa.myLanguageManager.TEXT_UPDATEVERSION);
			e.printStackTrace();
		}
	}

	public String getMessage(SimpleMessage message) {
		String result;
		try {
			result = Activa.myProtocolManager.getMessage(message);
		} catch (NotUpdatedException e) {
			Activa.myUIManager.loadTextOnWindow(Activa.myLanguageManager.TEXT_UPDATEVERSION);
			e.printStackTrace();
			result = null;
		}
		return result;
	}

	public boolean removeContact(Contact contact) {
		boolean result;
		try {
			result = Activa.myProtocolManager.removeContact(contact);
		} catch (NotUpdatedException e) {
			Activa.myUIManager.loadTextOnWindow(Activa.myLanguageManager.TEXT_UPDATEVERSION);
			e.printStackTrace();
			result = false;
		}
		return result;
	}

	public boolean AddContact(Contact user) {
		boolean result;
		try {
			result = Activa.myProtocolManager.addContact(user);
		} catch (NotUpdatedException e) {
			Activa.myUIManager.loadTextOnWindow(Activa.myLanguageManager.TEXT_UPDATEVERSION);
			e.printStackTrace();
			result = false;
		}
		return result;
	}

	public boolean AcceptContact(ContactContactRequest contactRequest) {
		boolean result;
		try {
			result = Activa.myProtocolManager.AcceptContact(this, contactRequest);
		} catch (NotUpdatedException e) {
			Activa.myUIManager.loadTextOnWindow(Activa.myLanguageManager.TEXT_UPDATEVERSION);
			e.printStackTrace();
			result = false;
		}
		return result;
	}

	public boolean RejectContact(ContactContactRequest contactRequest) {
		boolean result;
		try {
			result = Activa.myProtocolManager.RejectContact(this, contactRequest);
		} catch (NotUpdatedException e) {
			Activa.myUIManager.loadTextOnWindow(Activa.myLanguageManager.TEXT_UPDATEVERSION);
			e.printStackTrace();
			result = false;
		}
		return result;
	}

	public Array<Contact> SearchContacts(String query) {
		Array<Contact> result;
		try {
			result = Activa.myProtocolManager.SearchContacts(query);
		} catch (NotUpdatedException e) {
			Activa.myUIManager.loadTextOnWindow(Activa.myLanguageManager.TEXT_UPDATEVERSION);
			e.printStackTrace();
			result = null;
		}
		return result;
	}

	public boolean getAccountList() {
		boolean result;
		try {
			result = Activa.myProtocolManager.getAccountList();
		} catch (NotUpdatedException e) {
			result = false;
			Activa.myUIManager.loadTextOnWindow(Activa.myLanguageManager.TEXT_UPDATEVERSION);
			e.printStackTrace();
		}
		return result;
	}

	public boolean AddAccounts(Hashtable<Integer, Account> accountsToSend,
			Hashtable<Integer, String> usernameToSend,
			Hashtable<Integer, String> passwordToSend) {
		boolean result;
		try {
			result = Activa.myProtocolManager.AddAccounts(accountsToSend, usernameToSend, passwordToSend);
		} catch (NotUpdatedException e) {
			result = false;
			Activa.myUIManager.loadTextOnWindow(Activa.myLanguageManager.TEXT_UPDATEVERSION);
			e.printStackTrace();
		}
		return result;
		
	}

	public boolean deleteAccount(Account account) {
		boolean result;
		try {
			result = Activa.myProtocolManager.deleteAccount(account);
		} catch (NotUpdatedException e) {
			result = false;
			Activa.myUIManager.loadTextOnWindow(Activa.myLanguageManager.TEXT_UPDATEVERSION);
			e.printStackTrace();
		}
		return result;
	}

	public boolean AddMailAccount(Account account, String mail, String password) {
		boolean result;
		try {
			result = Activa.myProtocolManager.AddMailAccount(account, mail, password);
		} catch (NotUpdatedException e) {
			result = false;
			Activa.myUIManager.loadTextOnWindow(Activa.myLanguageManager.TEXT_UPDATEVERSION);
			e.printStackTrace();
		}
		return result;
	}

	public String requestAccountToken(AccountType type, String callback) {
		String result;
		try {
			result = Activa.myProtocolManager.requestAccountToken(type, callback);
		} catch (NotUpdatedException e) {
			result = null;
			Activa.myUIManager.loadTextOnWindow(Activa.myLanguageManager.TEXT_UPDATEVERSION);
			e.printStackTrace();
		}
		return result;
	}

	public boolean sendO2Message(SimpleMessage message, String content) {
		boolean result;
		try {
			result = Activa.myProtocolManager.sendO2Message(this, Activa.myPendingDataManager, message, content, true);
		} catch (NotUpdatedException e) {
			result = false;
			Activa.myUIManager.loadTextOnWindow(Activa.myLanguageManager.TEXT_UPDATEVERSION);
			e.printStackTrace();
		}
		return result;
	}
	
}
