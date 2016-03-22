/**
 * @author Adrian Rejas
 * 
 * This class deals with the creation of request from the mobile device to the server. This is one
 * of the three basic messages a mobile Activa device have to treat.
*/

package com.o2hlink.activa.connection.message;

import com.o2hlink.activa.Activa;
import com.o2hlink.activa.mobile.Device;

public class MapRequest {
	
	/**
	 * The constructor to the object without parameters.	
	 */
	public MapRequest() {
		
	}
	
	/**
	 * Pass the object to a string, used for testing purposes.
	 * 
	 * @return The String representation.
	 */
	public String toString() {
		String returned;
		returned = "Request of type MAP\n";
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
		returned = "<MOBILEREQUEST><TYPEOFREQUEST VALUE=\"MAP\"/>";
		returned += "<PATIENT ID=\"" + Activa.myMobileManager.user.getId() + "\"/>";
		returned += "<MOBILEDEVICE><IDNUMBER VALUE=\"" + Activa.myMobileManager.device.getIdnumber() + "\"/>";
		returned += "<DATETIME VALUE=\"" + Activa.myMobileManager.device.getDateTime() + "\"/>";
		returned += "</MOBILEDEVICE>";
		returned += "<POSITION NORTHLATITUDE=\"" + Activa.myMapManager.northLatitude + "\" SOUTHLATITUDE=\"" + Activa.myMapManager.southLatitude + "\" WESTLONGITUDE=\"" + Activa.myMapManager.westLongitude + "\" EASTLONGITUDE=\"" + Activa.myMapManager.eastLongitude + "\"/>";
		returned +=	"</MOBILEREQUEST>";
		return returned;	
	}

}
