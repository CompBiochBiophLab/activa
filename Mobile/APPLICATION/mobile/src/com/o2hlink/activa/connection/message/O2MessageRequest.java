/**
 * @author Adrian Rejas
 * 
 * This class deals with the creation of request from the mobile device to the server. This is one
 * of the three basic messages a mobile Activa device have to treat.
*/

package com.o2hlink.activa.connection.message;

import java.util.Date;

import com.o2hlink.activa.Activa;
import com.o2hlink.activa.ActivaUtil;
import com.o2hlink.activa.mobile.Device;

public class O2MessageRequest {
	
	
	public Date date;
	/**
	 * The constructor to the object without parameters.	
	 */
	public O2MessageRequest(Date date) {
		this.date = date;
	}
	
	/**
	 * Pass the object to a string, used for testing purposes.
	 * 
	 * @return The String representation.
	 */
	public String toString() {
		String returned;
		returned = "Request of type MESSAGE\n";
		returned += "Done by " + Activa.myMobileManager.device.toString();
		return returned;	
	}
	
	/**
	 * Create the data field of the corresponding message to the request.
	 * 
	 * @return The message data field.
	 */
	public String toMessageString() {
		String returned;
		returned = "<MOBILEREQUEST><TYPEOFREQUEST VALUE=\"MESSAGE\"/>";
		returned += "<PATIENT ID=\"" + Activa.myMobileManager.user.getId() + "\"/>";
		returned += "<MOBILEDEVICE><IDNUMBER VALUE=\"" + Activa.myMobileManager.device.getIdnumber() + "\"/>";
		returned += "<DATETIME VALUE=\"" + ActivaUtil.dateToXMLDate(this.date) + "\"/>";
		returned += "</MOBILEDEVICE>";
		returned +=	"</MOBILEREQUEST>";
		return returned;	
	}

}
