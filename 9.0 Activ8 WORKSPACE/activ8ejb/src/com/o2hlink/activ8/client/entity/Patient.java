/**
 * 
 */
package com.o2hlink.activ8.client.entity;

import com.o2hlink.activ8.common.entity.MappingClass;

/**
 * A patient user.
 * @author Miguel
 **/
@MappingClass(name="com.o2hlink.activ8.server.entity.Patient")
public class Patient extends User implements HasSamples {
//FIELDS
	/**
	 * 
	 **/
	private static final long serialVersionUID = -7137195719494407708L;
	/**
	 *
	 **/
	private String bluetoothAddress;
//CONSTRUCTOR
	/**
	 * 
	 **/
	public Patient(){
		
	}
//METHODS
	/**
	 * @param bluetoothAddress the bluetoothAddress to set
	 */
	public void setBluetoothAddress(String bluetoothAddress) {
		this.bluetoothAddress = bluetoothAddress;
	}
	/**
	 * @return the bluetoothAddress
	 */
	public String getBluetoothAddress() {
		return bluetoothAddress;
	}
}
