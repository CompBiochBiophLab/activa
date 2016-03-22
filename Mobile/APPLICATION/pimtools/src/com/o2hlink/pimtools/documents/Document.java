package com.o2hlink.pimtools.documents;

import com.o2hlink.activ8.client.entity.Account;

public class Document extends com.o2hlink.activ8.client.entity.Document {

	private Account account;
	
	public Document() {
		super();
		account = null;
	}
	
	public Document(com.o2hlink.activ8.client.entity.Document document, Account account) {
		super();
		this.setId(document.getId());
		this.setName(document.getName());
		this.setContentType(document.getContentType());
		this.setAccount(account);
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}
	
	public com.o2hlink.activ8.client.entity.Document getDocumentForExporting() {
		com.o2hlink.activ8.client.entity.Document document = new com.o2hlink.activ8.client.entity.Document();
		document.setId(this.getId());
		document.setName(this.getName());
		document.setContentType(this.getContentType());
		return document;
	}
	
}
