package com.o2hlink.zonated.data.contacts;

import java.util.Hashtable;

import com.o2hlink.activ8.client.entity.Account;
import com.o2hlink.activ8.client.entity.Array;
import com.o2hlink.activ8.client.entity.Contact;
import com.o2hlink.activ8.client.entity.ContactContactRequest;
import com.o2hlink.activ8.client.entity.SimpleMessage;
import com.o2hlink.activ8.client.entity.User;
import com.o2hlink.activ8.common.entity.AccountType;
import com.o2hlink.zonated.Zonated;
import com.o2hlink.zonated.exceptions.NotUpdatedException;

/**
 * @author Adrian Rejas <P>
 * 
 * This class will deal with the receiving and sending of message by the user.
 */
public class ContactsManager {

	/**
	 * Instance of the manager
	 */
	private static ContactsManager instance;
	
	/**
	 * List of contacts used for services.
	 */
	public Hashtable<String, Contact> contactList;
	
	/**
	 * List of entrying contacts used for services.
	 */
	public Array<ContactContactRequest> entryContactList;
	
	/**
	 * Private basic constructor
	 */
	private ContactsManager() {
		this.contactList = new Hashtable<String, Contact>();
		this.entryContactList = new Array<ContactContactRequest>();
	}
	
	/**
	 * Public method for getting instance
	 * 
	 * @return
	 */
	public static ContactsManager getInstance() {
		if (ContactsManager.instance == null) ContactsManager.instance = new ContactsManager();
		return ContactsManager.instance;
	}
	
	/**
	 * Method for freeing the instance of the manager.
	 */
	public static void freeInstance() {
		ContactsManager.instance = null;
	}
	
	/**
	 * Method for requesting to main server the list of available contacts
	 */
	public void requestContactList() {
		this.contactList = new Hashtable<String, Contact>();
		try {
			Zonated.myProtocolManager.getContacts(this);
		} catch (NotUpdatedException e) {
			Zonated.myUIManager.UImisc.loadTextOnWindow(Zonated.myLanguageManager.TEXT_UPDATEVERSION);
			e.printStackTrace();
		}
	}

	public void requestEntryContactList() {
		this.entryContactList = new Array<ContactContactRequest>();
		try {
			Zonated.myProtocolManager.getEntryContacts(this);
		} catch (NotUpdatedException e) {
			Zonated.myUIManager.UImisc.loadTextOnWindow(Zonated.myLanguageManager.TEXT_UPDATEVERSION);
			e.printStackTrace();
		}
	}
	
	public boolean removeContact(Contact contact) {
		boolean result;
		try {
			result = Zonated.myProtocolManager.removeContact(contact);
		} catch (NotUpdatedException e) {
			Zonated.myUIManager.UImisc.loadTextOnWindow(Zonated.myLanguageManager.TEXT_UPDATEVERSION);
			e.printStackTrace();
			result = false;
		}
		return result;
	}

	public boolean AddContact(Contact user) {
		boolean result;
		try {
			result = Zonated.myProtocolManager.addContact(user);
		} catch (NotUpdatedException e) {
			Zonated.myUIManager.UImisc.loadTextOnWindow(Zonated.myLanguageManager.TEXT_UPDATEVERSION);
			e.printStackTrace();
			result = false;
		}
		return result;
	}

	public boolean AcceptContact(ContactContactRequest contactRequest) {
		boolean result;
		try {
			result = Zonated.myProtocolManager.AcceptContact(this, contactRequest);
		} catch (NotUpdatedException e) {
			Zonated.myUIManager.UImisc.loadTextOnWindow(Zonated.myLanguageManager.TEXT_UPDATEVERSION);
			e.printStackTrace();
			result = false;
		}
		return result;
	}

	public boolean RejectContact(ContactContactRequest contactRequest) {
		boolean result;
		try {
			result = Zonated.myProtocolManager.RejectContact(this, contactRequest);
		} catch (NotUpdatedException e) {
			Zonated.myUIManager.UImisc.loadTextOnWindow(Zonated.myLanguageManager.TEXT_UPDATEVERSION);
			e.printStackTrace();
			result = false;
		}
		return result;
	}

	public Array<Contact> SearchContacts(String query) {
		Array<Contact> result;
		try {
			result = Zonated.myProtocolManager.SearchContacts(query);
		} catch (NotUpdatedException e) {
			Zonated.myUIManager.UImisc.loadTextOnWindow(Zonated.myLanguageManager.TEXT_UPDATEVERSION);
			e.printStackTrace();
			result = null;
		}
		return result;
	}
	
}
