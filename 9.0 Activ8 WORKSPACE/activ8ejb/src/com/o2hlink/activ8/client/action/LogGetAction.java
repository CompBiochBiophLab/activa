/**
 * 
 */
package com.o2hlink.activ8.client.action;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;

import com.o2hlink.activ8.client.entity.Clinician;
import com.o2hlink.activ8.client.entity.Dataset;
import com.o2hlink.activ8.client.entity.Event;
import com.o2hlink.activ8.client.entity.Group;
import com.o2hlink.activ8.client.entity.HasLog;
import com.o2hlink.activ8.client.entity.HistoryRecord;
import com.o2hlink.activ8.client.entity.Patient;
import com.o2hlink.activ8.client.entity.Project;
import com.o2hlink.activ8.client.entity.Researcher;
import com.o2hlink.activ8.client.response.LogResponse;

/**
 * Get a list of log records for a particular item.
 * @author Miguel Angel Hernandez
 **/
public class LogGetAction implements Action<LogResponse> {
//FIELDS
	/**
	 * 
	 **/
	private static final long serialVersionUID = -3557106804686795594L;
	/**
	 * 
	 **/
	private HasLog object;
//CONSTRUCTOR
	/**
	 * Empty constructor to allow serialization
	 **/
	public LogGetAction(){
		
	}
	/**
	 * 
	 **/
	public LogGetAction(HasLog item){
		this.setProvider(item);
	}
//METHODS
	/**
	 * @param item the item to set
	 */
	public void setProvider(HasLog item) {
		this.object = item;
	}
	/**
	 * @return the item
	 */
	@XmlElements({
		@XmlElement(name="dataset", type=Dataset.class),
		@XmlElement(name="event", type=Event.class),
		@XmlElement(name="group", type=Group.class),
		@XmlElement(name="history", type=HistoryRecord.class),
		@XmlElement(name="project", type=Project.class),
		@XmlElement(name="patient", type=Patient.class),
		@XmlElement(name="clinician", type=Clinician.class),
		@XmlElement(name="researcher", type=Researcher.class)
	})
	public HasLog getProvider() {
		return object;
	}
}
