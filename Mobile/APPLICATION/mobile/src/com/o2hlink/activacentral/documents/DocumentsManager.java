package com.o2hlink.activacentral.documents;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Hashtable;

import android.webkit.MimeTypeMap;

import com.o2hlink.activ8.client.entity.Account;
import com.o2hlink.activ8.client.entity.Contact;
import com.o2hlink.activacentral.Activa;
import com.o2hlink.activacentral.exceptions.NotUpdatedException;

public class DocumentsManager {

	private static DocumentsManager instance;
	
	public Hashtable<String, Document> myDocuments;

	public static DocumentsManager getInstance() {
		if (instance == null) instance = new DocumentsManager();
		return instance;
	}
	
	private DocumentsManager () {
		this.myDocuments = new Hashtable<String, Document>();
	}

	public boolean getDocuments() {
		boolean result;
		try {
			result = Activa.myProtocolManager.getDocumentList();
		} catch (NotUpdatedException e) {
			Activa.myUIManager.loadTextOnWindow(Activa.myLanguageManager.TEXT_UPDATEVERSION);
			e.printStackTrace();
			result = false;
		}
		return result;
	}

	public boolean getDocuments(Account account) {
		boolean result;
		try {
			result = Activa.myProtocolManager.getDocumentList(account);
		} catch (NotUpdatedException e) {
			Activa.myUIManager.loadTextOnWindow(Activa.myLanguageManager.TEXT_UPDATEVERSION);
			e.printStackTrace();
			result = false;
		}
		return result;
	}
	
	public boolean uploadDocument(com.o2hlink.activ8.client.entity.Document file, Account account, byte[] content) {
		boolean result;
		try {
			result = Activa.myProtocolManager.uploadDocument(file, account, content);
		} catch (NotUpdatedException e) {
			Activa.myUIManager.loadTextOnWindow(Activa.myLanguageManager.TEXT_UPDATEVERSION);
			e.printStackTrace();
			result = false;
		}
		return result;
	}
	
	public boolean removeDocument(com.o2hlink.activ8.client.entity.Document file, Account account) {
		boolean result;
		try {
			result = Activa.myProtocolManager.removeDocument(file, account);
		} catch (NotUpdatedException e) {
			Activa.myUIManager.loadTextOnWindow(Activa.myLanguageManager.TEXT_UPDATEVERSION);
			e.printStackTrace();
			result = false;
		}
		return result;
	}
	
	public String getDocumentText (String file, Account account) {
		String result;
		try {
			result = Activa.myProtocolManager.getDocumentText(file, account);
		} catch (NotUpdatedException e) {
			Activa.myUIManager.loadTextOnWindow(Activa.myLanguageManager.TEXT_UPDATEVERSION);
			e.printStackTrace();
			result = null;
		}
		return result;
	}
	
	public byte[] getDocumentBinary (String file, Account account) {
		byte[] result;
		try {
			result = Activa.myProtocolManager.getDocumentBinary(file, account);
		} catch (NotUpdatedException e) {
			Activa.myUIManager.loadTextOnWindow(Activa.myLanguageManager.TEXT_UPDATEVERSION);
			e.printStackTrace();
			result = null;
		}
		return result;
	}
	
	public boolean downloadDocument(File position, Document doc, Account account) {
		try {
			byte[] info = Activa.myProtocolManager.getDocumentBinary(doc.getId(), account);
			if (info == null) return false;
			File file = new File(position, doc.getName());
			file.createNewFile();
			FileOutputStream fout = new FileOutputStream(file);
			fout.write(info);
			fout.close();
			return true;
		} catch (NotUpdatedException e) {
			Activa.myUIManager.loadTextOnWindow(Activa.myLanguageManager.TEXT_UPDATEVERSION);
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			return false;
		}
	}
	
	public boolean shareDocument(com.o2hlink.activ8.client.entity.Document file, Account account, ArrayList<Contact> contacts) {
		boolean result;
		try {
			result = Activa.myProtocolManager.shareDocument(file, account, contacts);
		} catch (NotUpdatedException e) {
			Activa.myUIManager.loadTextOnWindow(Activa.myLanguageManager.TEXT_UPDATEVERSION);
			e.printStackTrace();
			result = false;
		}
		return result;
	}
	
	public static String getMIMEType(File f) {
        String end = f.getName().substring(f.getName().lastIndexOf(".")+1, f.getName().length()).toLowerCase();
//        String type = "";
//        if (end.equals("mp3") || end.equals("aac") || end.equals("aac") || end.equals("amr") || end.equals("mpeg") || end.equals("mp4")) type = "audio";
// 		else if (end.equals("jpg") || end.equals("gif") || end.equals("png") || end.equals("jpeg")) type = "image";
// 		else if (end.equals("doc") || end.equals("docx") || end.equals("txt") || end.equals("pdf") || end.equals("html")) return "text/plain";
// 		type += "/*";
		MimeTypeMap mimemap = MimeTypeMap.getSingleton();
		String mime = mimemap.getMimeTypeFromExtension(end);
		return mime;
    }
	
}
