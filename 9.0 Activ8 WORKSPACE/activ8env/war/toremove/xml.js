function CreateHTTPRequestObject () {
	if (typeof (XMLHttpRequest) == "undefined") {
		if (typeof (ActiveXObject) != "undefined") {
			var progIDs = new Array (
									  "Msxml2.XMLHTTP.6.0", 
									  "Msxml2.XMLHTTP.5.0", 
									  "Msxml2.XMLHTTP.4.0", 
									  "Msxml2.XMLHTTP.3.0", 
									  "Msxml2.XMLHTTP", 
									  "Microsoft.XMLHTTP"
									);
			for (i in progIDs) {
				try { 
					return new ActiveXObject(progIDs[i]); 
				} catch(e) {};
			}
		}
	}
	else {
		return new XMLHttpRequest;
	}
	alert ("Your browser doesn't support XML handling!");
	return null;
}

function CreateMSXMLDocumentObject () {
	if (typeof (ActiveXObject) != "undefined") {
		var progIDs = new Array (
								  "Msxml2.DOMDocument.6.0", 
								  "Msxml2.DOMDocument.5.0", 
								  "Msxml2.DOMDocument.4.0", 
								  "Msxml2.DOMDocument.3.0", 
								  "MSXML2.DOMDocument", 
								  "MSXML.DOMDocument"
								);
		for (i in progIDs) {
			try { 
				return new ActiveXObject(progIDs[i]); 
			} catch(e) {};
		}
	}
	alert ("Your browser doesn't support XML handling!");
	return null;
}

function CreateXMLDocumentObject (rootName) {
	if (rootName == null)
		rootName = "";
	var xmlDoc = null;
		//Firefox, Opera, Safari
	if (document.implementation.createDocument) {
		xmlDoc = document.implementation.createDocument ("", rootName, null);
	}
	else {
			// Internet Explorer
		xmlDoc = CreateMSXMLDocumentObject ();
		if (rootName != "") {
			var rootNode = xmlDoc.createElement (rootName);
			xmlDoc.appendChild (rootNode);
		}
	}
	
	return xmlDoc;
}

function ParseHTTPResponse (httpRequest) {
	var xmlDoc = httpRequest.responseXML;

			// if responseXML is not valid, try to create the XML document from the responseText property
	if (!xmlDoc || !xmlDoc.documentElement) {
		if (window.DOMParser) {
			var parser = new DOMParser();
			try {
				xmlDoc = parser.parseFromString(httpRequest.responseText, "text/xml");
			} catch (e) {};
		}
		else {
			xmlDoc = CreateMSXMLDocumentObject ();
			if (!xmlDoc) {
				return null;
			}
			xmlDoc.loadXML (httpRequest.responseText);

		}
	}

			// if there was an error while parsing the XML document
	var errorMsg = null;
	if (xmlDoc.parseError && xmlDoc.parseError.errorCode != 0) {
		errorMsg = "XML Parsing Error: " + xmlDoc.parseError.reason
				  + " at line " + xmlDoc.parseError.line
				  + " at position " + xmlDoc.parseError.linepos;
	}
	else {
		if (xmlDoc.documentElement) {
			if (xmlDoc.documentElement.nodeName == "parsererror") {
				errorMsg = xmlDoc.documentElement.childNodes[0].nodeValue;
			}
		}
	}
	if (errorMsg) {
		alert (errorMsg);
		return null;
	}

		// ok, the XML document is valid
	return xmlDoc;
}